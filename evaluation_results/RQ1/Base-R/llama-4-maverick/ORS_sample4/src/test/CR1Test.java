import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private OnlineRideshareSystem system;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        system = new OnlineRideshareSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ValidBookingWithAvailableSeatsAndNoOverlap() {
        // Setup: Create Driver D1 with Trip T123 (2023-12-25 14:00-16:00, seats:5)
        Trip tripT123 = new Trip("T123", "D1", "Station A", "Station B", 5, 
            LocalDateTime.parse("2023-12-25 14:00:00", formatter), 
            LocalDateTime.parse("2023-12-25 16:00:00", formatter), 100.0);
        system.getTrips().add(tripT123);
        
        // Setup: Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        User customerC1 = new User("C1", "c1@example.com", "1234567890");
        system.getUsers().add(customerC1);
        
        Booking existingBooking = new Booking("B1", "C1", "T456", 3, 
            LocalDateTime.parse("2023-12-25 11:00:00", formatter));
        system.getBookings().add(existingBooking);
        
        // Set current time to be before the 2-hour cutoff (3 hours before departure)
        // Mock current time: 2023-12-25 11:00:00
        
        // Test: Check booking eligibility for Trip T123 with 3 seats
        boolean result = system.validateBookingEligibility("C1", "T123", 3);
        
        // Verify result and updated seats
        assertTrue("Booking should be eligible", result);
        assertEquals("Trip T123 seats should be updated to 2", 2, tripT123.getNumberOfSeats());
    }
    
    @Test
    public void testCase2_BookingDeniedDueToSeatShortage() {
        // Setup: Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        Trip tripT456 = new Trip("T456", "D2", "Station C", "Station D", 2, 
            LocalDateTime.parse("2023-12-25 10:00:00", formatter), 
            LocalDateTime.parse("2023-12-25 12:00:00", formatter), 80.0);
        system.getTrips().add(tripT456);
        
        // Setup: Create Customer C2
        User customerC2 = new User("C2", "c2@example.com", "2345678901");
        system.getUsers().add(customerC2);
        
        // Set current time to be before the 2-hour cutoff (2.5 hours before departure)
        // Mock current time: 2023-12-25 07:30:00
        
        // Test: Check booking eligibility for Trip T456 with 3 seats
        boolean result = system.validateBookingEligibility("C2", "T456", 3);
        
        // Verify result and unchanged seats
        assertFalse("Booking should be denied due to seat shortage", result);
        assertEquals("Trip T456 seats should remain 2", 2, tripT456.getNumberOfSeats());
    }
    
    @Test
    public void testCase3_BookingDeniedDueToTimeCutoffExactly2HoursBefore() {
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        Trip tripT100 = new Trip("T100", "D3", "Station E", "Station F", 50, 
            LocalDateTime.parse("2023-12-25 14:00:00", formatter), 
            LocalDateTime.parse("2023-12-25 16:00:00", formatter), 120.0);
        system.getTrips().add(tripT100);
        
        // Setup: Create Customer C3
        User customerC3 = new User("C3", "c3@example.com", "3456789012");
        system.getUsers().add(customerC3);
        
        // Set current time to be exactly 2 hours before departure
        // Mock current time: 2023-12-25 12:00:00
        
        // Test: Check booking eligibility for Trip T100 with 3 seats
        boolean result = system.validateBookingEligibility("C3", "T100", 3);
        
        // Verify result and unchanged seats
        assertFalse("Booking should be denied due to time cutoff", result);
        assertEquals("Trip T100 seats should remain 50", 50, tripT100.getNumberOfSeats());
    }
    
    @Test
    public void testCase4_BookingAllowedWithMultipleNonOverlappingTrips() {
        // Setup: Create Trip T199 (2023-12-25 08:00-10:00, 50 seats)
        Trip tripT199 = new Trip("T199", "D4", "Station G", "Station H", 50, 
            LocalDateTime.parse("2023-12-25 08:00:00", formatter), 
            LocalDateTime.parse("2023-12-25 10:00:00", formatter), 90.0);
        system.getTrips().add(tripT199);
        
        // Setup: Create Trip T200 (2023-12-25 12:00-14:00, 40 seats)
        Trip tripT200 = new Trip("T200", "D5", "Station I", "Station J", 40, 
            LocalDateTime.parse("2023-12-25 12:00:00", formatter), 
            LocalDateTime.parse("2023-12-25 14:00:00", formatter), 110.0);
        system.getTrips().add(tripT200);
        
        // Setup: Create Customer C9 with existing booking for T199
        User customerC9 = new User("C9", "c9@example.com", "4567890123");
        system.getUsers().add(customerC9);
        
        Booking existingBooking = new Booking("B2", "C9", "T199", 2, 
            LocalDateTime.parse("2023-12-23 12:00:00", formatter));
        system.getBookings().add(existingBooking);
        
        // Set current time to be before the 2-hour cutoff
        // Mock current time: 2023-12-23 14:00:00
        
        // Test: Check booking eligibility for Trip T200 with 4 seats
        boolean result = system.validateBookingEligibility("C9", "T200", 4);
        
        // Verify result and updated seats
        assertTrue("Booking should be allowed with non-overlapping trips", result);
        assertEquals("Trip T200 seats should be updated to 36", 36, tripT200.getNumberOfSeats());
    }
    
    @Test
    public void testCase5_BookingDeniedWhenCustomerHasOverlappingBooking() {
        // Setup: Create Trip T299 (2023-12-25 13:00-15:00, 50 seats)
        Trip tripT299 = new Trip("T299", "D6", "Station K", "Station L", 50, 
            LocalDateTime.parse("2023-12-25 13:00:00", formatter), 
            LocalDateTime.parse("2023-12-25 15:00:00", formatter), 95.0);
        system.getTrips().add(tripT299);
        
        // Setup: Create Trip T300 (2023-12-25 14:00-16:00, 40 seats)
        Trip tripT300 = new Trip("T300", "D7", "Station M", "Station N", 40, 
            LocalDateTime.parse("2023-12-25 14:00:00", formatter), 
            LocalDateTime.parse("2023-12-25 16:00:00", formatter), 105.0);
        system.getTrips().add(tripT300);
        
        // Setup: Create Customer C10 with existing booking for T299
        User customerC10 = new User("C10", "c10@example.com", "5678901234");
        system.getUsers().add(customerC10);
        
        Booking existingBooking = new Booking("B3", "C10", "T299", 2, 
            LocalDateTime.parse("2023-12-23 12:00:00", formatter));
        system.getBookings().add(existingBooking);
        
        // Set current time to be before the 2-hour cutoff
        // Mock current time: 2023-12-23 14:00:00
        
        // Test: Check booking eligibility for Trip T300 with 4 seats
        boolean result = system.validateBookingEligibility("C10", "T300", 4);
        
        // Verify result and unchanged seats
        assertFalse("Booking should be denied due to overlapping booking", result);
        assertEquals("Trip T300 seats should remain 40", 40, tripT300.getNumberOfSeats());
    }
}