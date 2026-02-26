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
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
    
    @Test
    public void testCase1_TwoNewPassengersSucceed() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP100");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP101");
        
        Flight flight = new Flight();
        flight.setId("F300");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-05 12:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        customer = new Customer();
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        List<String> passengerNames = Arrays.asList("Peter", "Beck");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertTrue("Booking should succeed", result);
        assertEquals("Customer should have 1 booking", 1, customer.getBookings().size());
        assertEquals("Booking should have 2 reservations", 2, customer.getBookings().get(0).getReservations().size());
        assertEquals("Flight should have 2 reservations", 2, flight.getReservations().size());
        
        // Verify reservation details
        List<Reservation> reservations = customer.getBookings().get(0).getReservations();
        assertEquals("First passenger should be Peter", "Peter", reservations.get(0).getPassenger().getName());
        assertEquals("Second passenger should be Beck", "Beck", reservations.get(1).getPassenger().getName());
        assertEquals("Reservation status should be PENDING", ReservationStatus.PENDING, reservations.get(0).getStatus());
        assertEquals("Reservation status should be PENDING", ReservationStatus.PENDING, reservations.get(1).getStatus());
    }
    
    @Test
    public void testCase2_DuplicatePassengerInSameRequest() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP102");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP103");
        
        Flight flight = new Flight();
        flight.setId("F301");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-05 10:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        customer = new Customer();
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        List<String> passengerNames = Arrays.asList("Alice", "Alice"); // Duplicate passenger
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passenger", result);
        assertTrue("Customer should have no bookings", customer.getBookings().isEmpty());
        assertTrue("Flight should have no reservations", flight.getReservations().isEmpty());
    }
    
    @Test
    public void testCase3_PassengerAlreadyBookedEarlier() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP104");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP105");
        
        Flight flight = new Flight();
        flight.setId("F302");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        customer = new Customer();
        
        // Create pre-existing booking for passenger "Jucy"
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        
        Passenger existingPassenger = new Passenger();
        existingPassenger.setName("Jucy");
        
        Reservation existingReservation = new Reservation();
        existingReservation.setId("R302-A");
        existingReservation.setStatus(ReservationStatus.PENDING);
        existingReservation.setPassenger(existingPassenger);
        existingReservation.setFlight(flight);
        
        existingBooking.getReservations().add(existingReservation);
        flight.getReservations().add(existingReservation);
        customer.getBookings().add(existingBooking);
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        List<String> passengerNames = Arrays.asList("Jucy"); // Same passenger as existing booking
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passenger on same flight", result);
        assertEquals("Customer should have only 1 booking", 1, customer.getBookings().size());
        assertEquals("Flight should have only 1 reservation", 1, flight.getReservations().size());
    }
    
    @Test
    public void testCase4_FlightClosedBlocksBooking() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP106");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP107");
        
        Flight flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(false); // Flight is closed
        
        airline.addFlight(flight);
        customer = new Customer();
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to closed flight", result);
        assertTrue("Customer should have no bookings", customer.getBookings().isEmpty());
        assertTrue("Flight should have no reservations", flight.getReservations().isEmpty());
    }
    
    @Test
    public void testCase5_TimeIsAfterDepartureTime() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP104");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP105");
        
        Flight flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        customer = new Customer();
        
        Date currentTime = dateFormat.parse("2025-10-06 09:00:00"); // After departure time
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to current time after departure time", result);
        assertTrue("Customer should have no bookings", customer.getBookings().isEmpty());
        assertTrue("Flight should have no reservations", flight.getReservations().isEmpty());
    }
}