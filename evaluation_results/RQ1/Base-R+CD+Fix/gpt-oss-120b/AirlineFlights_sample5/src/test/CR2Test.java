import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR2Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_TwoNewPassengersSucceed() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP100");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP101");
        
        Flight flight = new Flight();
        flight.setId("F300");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-05 12:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Customer customer = new Customer();
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        List<String> passengerNames = Arrays.asList("Peter", "Beck");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertTrue("Booking should succeed for two new passengers", result);
        assertEquals("Customer should have one booking", 1, customer.getBookings().size());
        assertEquals("Booking should contain two reservations", 2, customer.getBookings().get(0).getReservations().size());
        
        // Check reservations
        List<Reservation> reservations = customer.getBookings().get(0).getReservations();
        Set<String> reservedPassengers = new HashSet<>();
        for (Reservation r : reservations) {
            assertNotNull("Reservation ID should not be null", r.getId());
            assertEquals("Reservation status should be PENDING", ReservationStatus.PENDING, r.getStatus());
            assertNotNull("Passenger should not be null", r.getPassenger());
            reservedPassengers.add(r.getPassenger().getName());
        }
        
        assertTrue("Reservation should contain Peter", reservedPassengers.contains("Peter"));
        assertTrue("Reservation should contain Beck", reservedPassengers.contains("Beck"));
    }
    
    @Test
    public void testCase2_DuplicatePassengerInSameRequest() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP102");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP103");
        
        Flight flight = new Flight();
        flight.setId("F301");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-05 10:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Customer customer = new Customer();
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        List<String> passengerNames = Arrays.asList("Alice", "Alice"); // Duplicate passenger
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passenger in same request", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
    }
    
    @Test
    public void testCase3_PassengerAlreadyBookedEarlier() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP104");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP105");
        
        Flight flight = new Flight();
        flight.setId("F302");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Customer customer = new Customer();
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Create pre-existing booking with reservation for "Jucy"
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        
        Passenger existingPassenger = new Passenger();
        existingPassenger.setName("Jucy");
        
        Reservation existingReservation = new Reservation();
        existingReservation.setId("R302-A");
        existingReservation.setPassenger(existingPassenger);
        existingReservation.setFlight(flight);
        existingReservation.setStatus(ReservationStatus.PENDING);
        
        existingBooking.setReservations(Arrays.asList(existingReservation));
        customer.setBookings(Arrays.asList(existingBooking));
        
        // Add reservation to flight as well
        flight.getReservations().add(existingReservation);
        
        List<String> passengerNames = Arrays.asList("Jucy");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail because passenger already has reservation", result);
        assertEquals("Customer should still have only one booking", 1, customer.getBookings().size());
    }
    
    @Test
    public void testCase4_FlightClosedBlocksBooking() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP106");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP107");
        
        Flight flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(false); // Flight is closed for booking
        
        airline.addFlight(flight);
        
        Customer customer = new Customer();
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail because flight is closed", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
    }
    
    @Test
    public void testCase5_TimeAfterDepartureTime() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP104");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP105");
        
        Flight flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Customer customer = new Customer();
        Date currentTime = dateFormat.parse("2025-10-06 09:00:00"); // After departure time
        
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail because current time is after departure time", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
    }
}