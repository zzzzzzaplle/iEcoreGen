import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class CR2Test {
    private Flight flight;
    private Booking booking;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Before
    public void setUp() {
        booking = new Booking();
    }
    
    @Test
    public void testCase1_twoNewPassengersSucceed() {
        // Setup
        Flight f300 = new Flight();
        f300.setDepartureAirportId("AP100");
        f300.setArrivalAirportId("AP101");
        f300.setDepartureTime(LocalDateTime.parse("2025-10-05 08:00:00", formatter));
        f300.setArrivalTime(LocalDateTime.parse("2025-10-05 12:00:00", formatter));
        f300.setOpenForBooking(true);
        
        List<String> passengerNames = Arrays.asList("Peter", "Beck");
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-01 09:00:00", formatter);
        
        // Execute
        boolean result = booking.createBooking(f300, passengerNames);
        
        // Verify
        assertTrue("Booking should succeed for two new passengers", result);
        assertEquals("Should create 2 reservations", 2, booking.getReservations().size());
        assertEquals("First reservation should be for Peter", "Peter", booking.getReservations().get(0).getPassengerName());
        assertEquals("Second reservation should be for Beck", "Beck", booking.getReservations().get(1).getPassengerName());
        assertEquals("Flight should have 2 reservations", 2, f300.getReservations().size());
    }
    
    @Test
    public void testCase2_duplicatePassengerInSameRequest() {
        // Setup
        Flight f301 = new Flight();
        f301.setDepartureAirportId("AP102");
        f301.setArrivalAirportId("AP103");
        f301.setDepartureTime(LocalDateTime.parse("2025-10-05 08:00:00", formatter));
        f301.setArrivalTime(LocalDateTime.parse("2025-10-05 10:00:00", formatter));
        f301.setOpenForBooking(true);
        
        List<String> passengerNames = Arrays.asList("Alice", "Alice");
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-01 09:00:00", formatter);
        
        // Execute
        boolean result = booking.createBooking(f301, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passengers", result);
        assertTrue("No reservations should be created", booking.getReservations().isEmpty());
        assertTrue("Flight should have no reservations", f301.getReservations().isEmpty());
    }
    
    @Test
    public void testCase3_passengerAlreadyBookedEarlier() {
        // Setup
        Flight f302 = new Flight();
        f302.setDepartureAirportId("AP104");
        f302.setArrivalAirportId("AP105");
        f302.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", formatter));
        f302.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", formatter));
        f302.setOpenForBooking(true);
        
        // Create pre-existing booking for passenger "Jucy"
        Booking existingBooking = new Booking();
        List<String> existingPassenger = Arrays.asList("Jucy");
        existingBooking.createBooking(f302, existingPassenger);
        
        List<String> passengerNames = Arrays.asList("Jucy");
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-01 09:00:00", formatter);
        
        // Execute - try to create another booking for the same passenger
        boolean result = booking.createBooking(f302, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passenger on same flight", result);
        assertTrue("New booking should have no reservations", booking.getReservations().isEmpty());
        assertEquals("Flight should have only 1 reservation from existing booking", 1, f302.getReservations().size());
    }
    
    @Test
    public void testCase4_flightClosedBlocksBooking() {
        // Setup
        Flight f303 = new Flight();
        f303.setDepartureAirportId("AP106");
        f303.setArrivalAirportId("AP107");
        f303.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", formatter));
        f303.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", formatter));
        f303.setOpenForBooking(false); // Flight is closed
        
        List<String> passengerNames = Arrays.asList("Ruby");
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-01 09:00:00", formatter);
        
        // Execute
        boolean result = booking.createBooking(f303, passengerNames);
        
        // Verify
        assertFalse("Booking should fail because flight is closed", result);
        assertTrue("No reservations should be created", booking.getReservations().isEmpty());
        assertTrue("Flight should have no reservations", f303.getReservations().isEmpty());
    }
    
    @Test
    public void testCase5_timeIsAfterDepartureTime() {
        // Setup
        Flight f303 = new Flight();
        f303.setDepartureAirportId("AP104");
        f303.setArrivalAirportId("AP105");
        f303.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", formatter));
        f303.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", formatter));
        f303.setOpenForBooking(true);
        
        List<String> passengerNames = Arrays.asList("Ruby");
        // Current time is after departure time (2025-10-06 09:00:00 vs 2025-10-05 18:00:00)
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-06 09:00:00", formatter);
        
        // Execute
        boolean result = booking.createBooking(f303, passengerNames);
        
        // Verify
        assertFalse("Booking should fail because current time is after departure time", result);
        assertTrue("No reservations should be created", booking.getReservations().isEmpty());
        assertTrue("Flight should have no reservations", f303.getReservations().isEmpty());
    }
}