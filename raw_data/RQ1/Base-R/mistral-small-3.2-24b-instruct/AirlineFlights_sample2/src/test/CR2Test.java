import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class CR2Test {
    private Flight flightF300;
    private Flight flightF301;
    private Flight flightF302;
    private Flight flightF303;
    private Booking existingBooking;
    
    @Before
    public void setUp() {
        // Create flights for testing
        flightF300 = new Flight();
        flightF300.setId("F300");
        flightF300.setDepartureAirportId("AP100");
        flightF300.setArrivalAirportId("AP101");
        flightF300.setDepartureTime(LocalDateTime.parse("2025-10-05 08:00:00", 
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        flightF300.setArrivalTime(LocalDateTime.parse("2025-10-05 12:00:00", 
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        flightF300.setOpenForBooking(true);
        
        flightF301 = new Flight();
        flightF301.setId("F301");
        flightF301.setDepartureAirportId("AP102");
        flightF301.setArrivalAirportId("AP103");
        flightF301.setDepartureTime(LocalDateTime.parse("2025-10-05 08:00:00", 
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        flightF301.setArrivalTime(LocalDateTime.parse("2025-10-05 10:00:00", 
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        flightF301.setOpenForBooking(true);
        
        flightF302 = new Flight();
        flightF302.setId("F302");
        flightF302.setDepartureAirportId("AP104");
        flightF302.setArrivalAirportId("AP105");
        flightF302.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", 
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        flightF302.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", 
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        flightF302.setOpenForBooking(true);
        
        flightF303 = new Flight();
        flightF303.setId("F303");
        flightF303.setDepartureAirportId("AP106");
        flightF303.setArrivalAirportId("AP107");
        flightF303.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", 
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        flightF303.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", 
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        flightF303.setOpenForBooking(false);
        
        // Create existing booking for test case 3
        existingBooking = new Booking();
        existingBooking.setId("BK-OLD");
        existingBooking.setCustomerId("CUC");
        existingBooking.setFlightId("F302");
        
        Reservation existingReservation = new Reservation();
        existingReservation.setId("R302-A");
        existingReservation.setPassengerName("Jucy");
        existingReservation.setFlightId("F302");
        existingReservation.setConfirmed(false);
        
        existingBooking.getReservations().add(existingReservation);
    }
    
    @Test
    public void testCase1_TwoNewPassengersSucceed() {
        // Set current time to 2025-10-01 09:00:00
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-01 09:00:00", 
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Create booking for customer CUA
        Booking booking = new Booking();
        booking.setCustomerId("CUA");
        
        // Create passenger list
        List<String> passengerNames = Arrays.asList("Peter", "Beck");
        
        // Execute booking creation
        boolean result = booking.createBooking(flightF300, passengerNames);
        
        // Verify result is true and reservations are created
        assertTrue("Booking should succeed for two new passengers", result);
        assertEquals("Should have 2 reservations", 2, booking.getReservations().size());
        assertEquals("Flight ID should be set", "F300", booking.getFlightId());
        
        // Verify reservation details
        assertEquals("First passenger should be Peter", "Peter", booking.getReservations().get(0).getPassengerName());
        assertEquals("Second passenger should be Beck", "Beck", booking.getReservations().get(1).getPassengerName());
        
        // Verify reservations are in pending status (not confirmed)
        assertFalse("First reservation should be pending", booking.getReservations().get(0).isConfirmed());
        assertFalse("Second reservation should be pending", booking.getReservations().get(1).isConfirmed());
    }
    
    @Test
    public void testCase2_DuplicatePassengerInSameRequest() {
        // Set current time to 2025-10-01 09:00:00
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-01 09:00:00", 
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Create booking for customer CUB
        Booking booking = new Booking();
        booking.setCustomerId("CUB");
        
        // Create passenger list with duplicate names
        List<String> passengerNames = Arrays.asList("Alice", "Alice");
        
        // Execute booking creation
        boolean result = booking.createBooking(flightF301, passengerNames);
        
        // Verify result is false due to duplicate passengers
        assertFalse("Booking should fail for duplicate passengers", result);
    }
    
    @Test
    public void testCase3_PassengerAlreadyBookedEarlier() {
        // Set current time to 2025-10-01 09:00:00
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-01 09:00:00", 
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Create a new booking for customer CUC
        Booking newBooking = new Booking();
        newBooking.setCustomerId("CUC");
        
        // Create passenger list with passenger who already has a booking
        List<String> passengerNames = Arrays.asList("Jucy");
        
        // Execute booking creation - this should fail due to duplicate passenger
        boolean result = newBooking.createBooking(flightF302, passengerNames);
        
        // Verify result is false
        assertFalse("Booking should fail for passenger already booked", result);
    }
    
    @Test
    public void testCase4_FlightClosedBlocksBooking() {
        // Set current time to 2025-10-01 09:00:00
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-01 09:00:00", 
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Create booking for customer CUD
        Booking booking = new Booking();
        booking.setCustomerId("CUD");
        
        // Create passenger list
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute booking creation
        boolean result = booking.createBooking(flightF303, passengerNames);
        
        // Verify result is false due to flight being closed
        assertFalse("Booking should fail for closed flight", result);
    }
    
    @Test
    public void testCase5_TimeIsAfterDepartureTime() {
        // Set current time to 2025-10-06 09:00:00 (after departure)
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-06 09:00:00", 
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Create booking for customer CUD
        Booking booking = new Booking();
        booking.setCustomerId("CUD");
        
        // Create passenger list
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Create a flight that has departure time in the past relative to current time
        Flight pastFlight = new Flight();
        pastFlight.setId("F303");
        pastFlight.setDepartureAirportId("AP104");
        pastFlight.setArrivalAirportId("AP105");
        pastFlight.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", 
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        pastFlight.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", 
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        pastFlight.setOpenForBooking(true);
        
        // Execute booking creation
        boolean result = booking.createBooking(pastFlight, passengerNames);
        
        // Verify result is false due to current time being after departure time
        assertFalse("Booking should fail when current time is after departure", result);
    }
}