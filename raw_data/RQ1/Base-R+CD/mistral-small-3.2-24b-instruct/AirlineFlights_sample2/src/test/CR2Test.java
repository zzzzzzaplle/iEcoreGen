import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CR2Test {
    
    private SimpleDateFormat dateFormat;
    private Airline airline;
    private Customer customer;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_TwoNewPassengersSucceed() throws ParseException {
        // Setup
        airline = new Airline();
        customer = new Customer();
        
        // Create airports
        Airport departureAirport = new Airport();
        Airport arrivalAirport = new Airport();
        
        // Create cities and add to airports
        City departureCity = new City("CityA");
        City arrivalCity = new City("CityB");
        departureAirport.addCity(departureCity);
        arrivalAirport.addCity(arrivalCity);
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F300");
        flight.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-05 12:00:00"));
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        List<String> passengerNames = Arrays.asList("Peter", "Beck");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertTrue("Booking should succeed for two new passengers", result);
        assertEquals("Customer should have 1 booking", 1, customer.getBookings().size());
        assertEquals("Booking should have 2 reservations", 2, customer.getBookings().get(0).getReservations().size());
    }
    
    @Test
    public void testCase2_DuplicatePassengerInSameRequest() throws ParseException {
        // Setup
        airline = new Airline();
        customer = new Customer();
        
        // Create airports
        Airport departureAirport = new Airport();
        Airport arrivalAirport = new Airport();
        
        // Create cities and add to airports
        City departureCity = new City("CityC");
        City arrivalCity = new City("CityD");
        departureAirport.addCity(departureCity);
        arrivalAirport.addCity(arrivalCity);
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F301");
        flight.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-05 10:00:00"));
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        List<String> passengerNames = Arrays.asList("Alice", "Alice"); // Duplicate passenger
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passenger", result);
        assertEquals("Customer should have 0 bookings", 0, customer.getBookings().size());
    }
    
    @Test
    public void testCase3_PassengerAlreadyBookedEarlier() throws ParseException {
        // Setup
        airline = new Airline();
        customer = new Customer();
        
        // Create airports
        Airport departureAirport = new Airport();
        Airport arrivalAirport = new Airport();
        
        // Create cities and add to airports
        City departureCity = new City("CityE");
        City arrivalCity = new City("CityF");
        departureAirport.addCity(departureCity);
        arrivalAirport.addCity(arrivalCity);
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F302");
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // Create pre-existing booking with passenger "Jucy"
        Booking existingBooking = new Booking(customer);
        existingBooking.createReservation(flight, "Jucy", dateFormat.parse("2025-10-01 08:00:00"));
        customer.getBookings().add(existingBooking);
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        List<String> passengerNames = Arrays.asList("Jucy");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to passenger already booked", result);
        assertEquals("Customer should still have only 1 booking", 1, customer.getBookings().size());
    }
    
    @Test
    public void testCase4_FlightClosedBlocksBooking() throws ParseException {
        // Setup
        airline = new Airline();
        customer = new Customer();
        
        // Create airports
        Airport departureAirport = new Airport();
        Airport arrivalAirport = new Airport();
        
        // Create cities and add to airports
        City departureCity = new City("CityG");
        City arrivalCity = new City("CityH");
        departureAirport.addCity(departureCity);
        arrivalAirport.addCity(arrivalCity);
        
        // Create flight that is closed for booking
        Flight flight = new Flight();
        flight.setId("F303");
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setOpenForBooking(false); // Flight is closed
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to flight being closed", result);
        assertEquals("Customer should have 0 bookings", 0, customer.getBookings().size());
    }
    
    @Test
    public void testCase5_TimeAfterDepartureTime() throws ParseException {
        // Setup
        airline = new Airline();
        customer = new Customer();
        
        // Create airports
        Airport departureAirport = new Airport();
        Airport arrivalAirport = new Airport();
        
        // Create cities and add to airports
        City departureCity = new City("CityI");
        City arrivalCity = new City("CityJ");
        departureAirport.addCity(departureCity);
        arrivalAirport.addCity(arrivalCity);
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F303");
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-10-06 09:00:00"); // After departure time
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to current time after departure time", result);
        assertEquals("Customer should have 0 bookings", 0, customer.getBookings().size());
    }
}