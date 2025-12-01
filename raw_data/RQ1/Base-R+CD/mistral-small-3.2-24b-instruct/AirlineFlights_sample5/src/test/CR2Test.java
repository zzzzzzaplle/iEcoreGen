import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
    public void testCase1_TwoNewPassengersSucceed() throws Exception {
        // Setup
        airline = new Airline();
        customer = new Customer();
        
        // Create airports
        Airport departureAirport = new Airport();
        Airport arrivalAirport = new Airport();
        
        // Create cities and add to airports
        City departureCity = new City();
        departureCity.setName("CityA");
        departureAirport.addCity(departureCity);
        
        City arrivalCity = new City();
        arrivalCity.setName("CityB");
        arrivalAirport.addCity(arrivalCity);
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F300");
        flight.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-05 12:00:00"));
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        List<String> passengers = Arrays.asList("Peter", "Beck");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengers);
        
        // Verify
        assertTrue("Booking should succeed for two new passengers", result);
        assertEquals("Customer should have 1 booking", 1, customer.getBookings().size());
        assertEquals("Booking should have 2 reservations", 2, customer.getBookings().get(0).getReservations().size());
        assertEquals("Flight should have 2 reservations", 2, flight.getReservations().size());
    }
    
    @Test
    public void testCase2_DuplicatePassengerInSameRequest() throws Exception {
        // Setup
        airline = new Airline();
        customer = new Customer();
        
        // Create airports
        Airport departureAirport = new Airport();
        Airport arrivalAirport = new Airport();
        
        // Create cities and add to airports
        City departureCity = new City();
        departureCity.setName("CityC");
        departureAirport.addCity(departureCity);
        
        City arrivalCity = new City();
        arrivalCity.setName("CityD");
        arrivalAirport.addCity(arrivalCity);
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F301");
        flight.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-05 10:00:00"));
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        List<String> passengers = Arrays.asList("Alice", "Alice"); // Duplicate passenger
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengers);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passenger", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
        assertEquals("Flight should have no reservations", 0, flight.getReservations().size());
    }
    
    @Test
    public void testCase3_PassengerAlreadyBookedEarlier() throws Exception {
        // Setup
        airline = new Airline();
        customer = new Customer();
        
        // Create airports
        Airport departureAirport = new Airport();
        Airport arrivalAirport = new Airport();
        
        // Create cities and add to airports
        City departureCity = new City();
        departureCity.setName("CityE");
        departureAirport.addCity(departureCity);
        
        City arrivalCity = new City();
        arrivalCity.setName("CityF");
        arrivalAirport.addCity(arrivalCity);
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F302");
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // Create pre-existing booking with passenger "Jucy"
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        
        Reservation existingReservation = new Reservation();
        existingReservation.setId("R302-A");
        existingReservation.setStatus(ReservationStatus.PENDING);
        
        Passenger existingPassenger = new Passenger();
        existingPassenger.setName("Jucy");
        existingReservation.setPassenger(existingPassenger);
        existingReservation.setFlight(flight);
        
        existingBooking.getReservations().add(existingReservation);
        flight.getReservations().add(existingReservation);
        customer.getBookings().add(existingBooking);
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        List<String> passengers = Arrays.asList("Jucy"); // Same passenger as existing booking
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengers);
        
        // Verify
        assertFalse("Booking should fail due to passenger already booked", result);
        assertEquals("Customer should have only 1 booking", 1, customer.getBookings().size());
        assertEquals("Flight should have only 1 reservation", 1, flight.getReservations().size());
    }
    
    @Test
    public void testCase4_FlightClosedBlocksBooking() throws Exception {
        // Setup
        airline = new Airline();
        customer = new Customer();
        
        // Create airports
        Airport departureAirport = new Airport();
        Airport arrivalAirport = new Airport();
        
        // Create cities and add to airports
        City departureCity = new City();
        departureCity.setName("CityG");
        departureAirport.addCity(departureCity);
        
        City arrivalCity = new City();
        arrivalCity.setName("CityH");
        arrivalAirport.addCity(arrivalCity);
        
        // Create flight (closed for booking)
        Flight flight = new Flight();
        flight.setId("F303");
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setOpenForBooking(false); // Flight is closed
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        List<String> passengers = Arrays.asList("Ruby");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengers);
        
        // Verify
        assertFalse("Booking should fail due to flight being closed", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
        assertEquals("Flight should have no reservations", 0, flight.getReservations().size());
    }
    
    @Test
    public void testCase5_TimeAfterDepartureTime() throws Exception {
        // Setup
        airline = new Airline();
        customer = new Customer();
        
        // Create airports
        Airport departureAirport = new Airport();
        Airport arrivalAirport = new Airport();
        
        // Create cities and add to airports
        City departureCity = new City();
        departureCity.setName("CityI");
        departureAirport.addCity(departureCity);
        
        City arrivalCity = new City();
        arrivalCity.setName("CityJ");
        arrivalAirport.addCity(arrivalCity);
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F303");
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-10-06 09:00:00"); // After departure time
        List<String> passengers = Arrays.asList("Ruby");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengers);
        
        // Verify
        assertFalse("Booking should fail due to current time after departure time", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
        assertEquals("Flight should have no reservations", 0, flight.getReservations().size());
    }
}