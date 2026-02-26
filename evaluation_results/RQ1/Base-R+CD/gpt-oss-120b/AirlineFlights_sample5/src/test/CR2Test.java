import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR2Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() throws Exception {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_TwoNewPassengersSucceed() throws ParseException {
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
        
        Booking booking = customer.getBookings().get(0);
        assertEquals("Booking should have two reservations", 2, booking.getReservations().size());
        
        // Check that both passengers have reservations
        Set<String> reservedPassengerNames = new HashSet<>();
        for (Reservation reservation : booking.getReservations()) {
            reservedPassengerNames.add(reservation.getPassenger().getName());
            assertEquals("Reservation should be in PENDING status", 
                         ReservationStatus.PENDING, reservation.getStatus());
            assertEquals("Reservation should be for the correct flight", 
                         flight, reservation.getFlight());
        }
        
        assertTrue("Peter should have a reservation", reservedPassengerNames.contains("Peter"));
        assertTrue("Beck should have a reservation", reservedPassengerNames.contains("Beck"));
    }
    
    @Test
    public void testCase2_DuplicatePassengerInSameRequest() throws ParseException {
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
        assertFalse("Booking should fail due to duplicate passenger in request", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
    }
    
    @Test
    public void testCase3_PassengerAlreadyBookedEarlier() throws ParseException {
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
        flight.getReservations().add(existingReservation);
        customer.getBookings().add(existingBooking);
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        List<String> passengerNames = Arrays.asList("Jucy"); // Same passenger as existing booking
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to passenger already booked", result);
        assertEquals("Customer should still have only one booking", 1, customer.getBookings().size());
    }
    
    @Test
    public void testCase4_FlightClosedBlocksBooking() throws ParseException {
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
        assertFalse("Booking should fail due to flight being closed", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
    }
    
    @Test
    public void testCase5_TimeAfterDepartureTimeBlocksBooking() throws ParseException {
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
        flight.setOpenForBooking(true); // Flight is open for booking
        
        airline.addFlight(flight);
        
        Customer customer = new Customer();
        
        Date currentTime = dateFormat.parse("2025-10-06 09:00:00"); // After departure time
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to current time being after departure time", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
    }
}