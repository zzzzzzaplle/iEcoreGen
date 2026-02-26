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
    public void setUp() throws Exception {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_twoNewPassengersSucceed() throws Exception {
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
        
        customer = new Customer();
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Input: passenger list
        List<String> passengerNames = Arrays.asList("Peter", "Beck");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertTrue("Booking should succeed for two new passengers", result);
        assertEquals("Customer should have 1 booking", 1, customer.getBookings().size());
        assertEquals("Booking should have 2 reservations", 2, customer.getBookings().get(0).getReservations().size());
        
        // Verify reservations
        List<Reservation> reservations = customer.getBookings().get(0).getReservations();
        assertEquals("Peter should be in reservations", "Peter", reservations.get(0).getPassenger().getName());
        assertEquals("Beck should be in reservations", "Beck", reservations.get(1).getPassenger().getName());
        assertEquals("Reservation status should be PENDING", ReservationStatus.PENDING, reservations.get(0).getStatus());
        assertEquals("Reservation status should be PENDING", ReservationStatus.PENDING, reservations.get(1).getStatus());
    }
    
    @Test
    public void testCase2_duplicatePassengerInSameRequest() throws Exception {
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
        
        customer = new Customer();
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Input: duplicate passenger names
        List<String> passengerNames = Arrays.asList("Alice", "Alice");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passengers", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
    }
    
    @Test
    public void testCase3_passengerAlreadyBookedEarlier() throws Exception {
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
        
        customer = new Customer();
        
        // Create pre-existing booking with passenger "Jucy"
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        
        Passenger existingPassenger = new Passenger();
        existingPassenger.setName("Jucy");
        
        Reservation existingReservation = new Reservation();
        existingReservation.setId("R302-A");
        existingReservation.setPassenger(existingPassenger);
        existingReservation.setFlight(flight);
        existingReservation.setStatus(ReservationStatus.PENDING);
        
        existingBooking.getReservations().add(existingReservation);
        flight.addReservation(existingReservation);
        customer.getBookings().add(existingBooking);
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Input: passenger "Jucy" who is already booked
        List<String> passengerNames = Arrays.asList("Jucy");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passenger on same flight", result);
        assertEquals("Customer should still have only 1 booking", 1, customer.getBookings().size());
        assertEquals("Flight should still have only 1 reservation", 1, flight.getReservations().size());
    }
    
    @Test
    public void testCase4_flightClosedBlocksBooking() throws Exception {
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
        
        customer = new Customer();
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Input: passenger "Ruby"
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail because flight is closed", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
    }
    
    @Test
    public void testCase5_timeAfterDepartureTime() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports
        Airport departureAirport = new Airport();
        departureAirport.setId("AP104");
        City departureCity = new City();
        departureCity.setName("CityI");
        departureAirport.addCity(departureCity);
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP105");
        City arrivalCity = new City();
        arrivalCity.setName("CityJ");
        arrivalAirport.addCity(arrivalCity);
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        customer = new Customer();
        
        // Current time is after departure time
        Date currentTime = dateFormat.parse("2025-10-06 09:00:00");
        
        // Input: passenger "Ruby"
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail because current time is after departure time", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
    }
}