import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR2Test {
    
    private SimpleDateFormat dateFormat;
    private Airline airline;
    private Customer customer;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_TwoNewPassengersSucceed() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport departureAirport = new Airport();
        departureAirport.setId("AP100");
        City departureCity = new City();
        departureCity.setName("CityA");
        departureAirport.addCity(departureCity);
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP101");
        City arrivalCity = new City();
        arrivalCity.setName("CityB");
        arrivalAirport.addCity(arrivalCity);
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F300");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-05 12:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // Create customer
        customer = new Customer();
        
        // Create passenger list
        List<String> passengerNames = Arrays.asList("Peter", "Beck");
        
        // Current time
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Execute test
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify results
        assertTrue("Booking should succeed for two new passengers", result);
        assertEquals("Customer should have 1 booking", 1, customer.getBookings().size());
        assertEquals("Booking should have 2 reservations", 2, customer.getBookings().get(0).getReservations().size());
        assertEquals("Flight should have 2 reservations", 2, flight.getReservations().size());
    }
    
    @Test
    public void testCase2_DuplicatePassengerInSameRequest() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport departureAirport = new Airport();
        departureAirport.setId("AP102");
        City departureCity = new City();
        departureCity.setName("CityC");
        departureAirport.addCity(departureCity);
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP103");
        City arrivalCity = new City();
        arrivalCity.setName("CityD");
        arrivalAirport.addCity(arrivalCity);
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F301");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-05 10:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // Create customer
        customer = new Customer();
        
        // Create passenger list with duplicate
        List<String> passengerNames = Arrays.asList("Alice", "Alice");
        
        // Current time
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Execute test
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify results
        assertFalse("Booking should fail for duplicate passengers in same request", result);
        assertEquals("Customer should have 0 bookings", 0, customer.getBookings().size());
        assertEquals("Flight should have 0 reservations", 0, flight.getReservations().size());
    }
    
    @Test
    public void testCase3_PassengerAlreadyBookedEarlier() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport departureAirport = new Airport();
        departureAirport.setId("AP104");
        City departureCity = new City();
        departureCity.setName("CityE");
        departureAirport.addCity(departureCity);
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP105");
        City arrivalCity = new City();
        arrivalCity.setName("CityF");
        arrivalAirport.addCity(arrivalCity);
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F302");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // Create customer
        customer = new Customer();
        
        // Create pre-existing booking for passenger "Jucy"
        List<String> passengerNames = Arrays.asList("Jucy");
        Date currentTime = dateFormat.parse("2025-10-01 08:00:00");
        customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify pre-existing booking exists
        assertEquals("Pre-existing booking should exist", 1, customer.getBookings().size());
        assertEquals("Pre-existing reservation should exist", 1, flight.getReservations().size());
        
        // Execute test - try to book same passenger again
        currentTime = dateFormat.parse("2025-10-01 09:00:00");
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify results
        assertFalse("Booking should fail for passenger already booked earlier", result);
        assertEquals("Customer should still have only 1 booking", 1, customer.getBookings().size());
        assertEquals("Flight should still have only 1 reservation", 1, flight.getReservations().size());
    }
    
    @Test
    public void testCase4_FlightClosedBlocksBooking() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport departureAirport = new Airport();
        departureAirport.setId("AP106");
        City departureCity = new City();
        departureCity.setName("CityG");
        departureAirport.addCity(departureCity);
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP107");
        City arrivalCity = new City();
        arrivalCity.setName("CityH");
        arrivalAirport.addCity(arrivalCity);
        
        // Create flight (closed for booking)
        Flight flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(false); // Closed for booking
        
        airline.addFlight(flight);
        
        // Create customer
        customer = new Customer();
        
        // Create passenger list
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Current time
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Execute test
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify results
        assertFalse("Booking should fail for closed flight", result);
        assertEquals("Customer should have 0 bookings", 0, customer.getBookings().size());
        assertEquals("Flight should have 0 reservations", 0, flight.getReservations().size());
    }
    
    @Test
    public void testCase5_TimeAfterDepartureTime() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport departureAirport = new Airport();
        departureAirport.setId("AP104");
        City departureCity = new City();
        departureCity.setName("CityE");
        departureAirport.addCity(departureCity);
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP105");
        City arrivalCity = new City();
        arrivalCity.setName("CityF");
        arrivalAirport.addCity(arrivalCity);
        
        // Create flight (open for booking)
        Flight flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // Create customer
        customer = new Customer();
        
        // Create passenger list
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Current time (after departure time)
        Date currentTime = dateFormat.parse("2025-10-06 09:00:00");
        
        // Execute test
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify results
        assertFalse("Booking should fail when current time is after departure time", result);
        assertEquals("Customer should have 0 bookings", 0, customer.getBookings().size());
        assertEquals("Flight should have 0 reservations", 0, flight.getReservations().size());
    }
}