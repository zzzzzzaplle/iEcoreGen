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
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }
    
    @Test
    public void testCase1_validBookingWithAvailableSeatsAndNoOverlap() {
        // Setup: Create Driver D1 with Trip T123 (2023-12-25 14:00-16:00, seats:5)
        User driver1 = new User("D1", "driver1@example.com", "1234567890");
        Trip trip123 = new Trip("T123", driver1, "Station A", "Station B", 5,
                               LocalDateTime.parse("2023-12-25 14:00", formatter),
                               LocalDateTime.parse("2023-12-25 16:00", formatter), 50.0);
        system.addTrip(trip123);
        
        // Setup: Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        User customer1 = new User("C1", "customer1@example.com", "0987654321");
        User driver2 = new User("D2", "driver2@example.com", "1111111111");
        Trip trip456 = new Trip("T456", driver2, "Station C", "Station D", 10,
                               LocalDateTime.parse("2023-12-25 14:00", formatter),
                               LocalDateTime.parse("2023-12-25 16:00", formatter), 40.0);
        system.addTrip(trip456);
        
        Booking existingBooking = new Booking("B1", customer1, trip456, 3, 
                                            LocalDateTime.parse("2023-12-25 11:00", formatter));
        system.addBooking(existingBooking);
        
        // Input: Check booking eligibility for Trip T123 with 3 seats at 2023-12-25 11:00 (3 hours before departure)
        boolean result = system.validateBookingEligibility(customer1, trip123, 3, 
                                                         LocalDateTime.parse("2023-12-25 11:00", formatter));
        
        // Expected Output: true, updated Trip T123 seats = 2
        assertTrue("Booking should be eligible", result);
        assertEquals("Trip T123 available seats should be updated to 2", 2, trip123.getAvailableSeats());
    }
    
    @Test
    public void testCase2_bookingDeniedDueToSeatShortage() {
        // Setup: Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        User driver2 = new User("D2", "driver2@example.com", "1111111111");
        Trip trip456 = new Trip("T456", driver2, "Station C", "Station D", 2,
                               LocalDateTime.parse("2023-12-25 10:00", formatter),
                               LocalDateTime.parse("2023-12-25 12:00", formatter), 40.0);
        system.addTrip(trip456);
        
        // Setup: Create Customer C2 with existing booking for T456 (3 seats)
        User customer2 = new User("C2", "customer2@example.com", "2222222222");
        
        // Input: Check booking eligibility for Trip T456 with 3 seats at 2023-12-25 07:30 (2.5 hours before departure)
        boolean result = system.validateBookingEligibility(customer2, trip456, 3, 
                                                         LocalDateTime.parse("2023-12-25 07:30", formatter));
        
        // Expected Output: false, Trip T456 seats = 2
        assertFalse("Booking should be denied due to seat shortage", result);
        assertEquals("Trip T456 available seats should remain 2", 2, trip456.getAvailableSeats());
    }
    
    @Test
    public void testCase3_bookingDeniedDueToTimeCutoffExactly2HoursBefore() {
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        User driver = new User("D100", "driver100@example.com", "3333333333");
        Trip trip100 = new Trip("T100", driver, "Station E", "Station F", 50,
                               LocalDateTime.parse("2023-12-25 14:00", formatter),
                               LocalDateTime.parse("2023-12-25 16:00", formatter), 60.0);
        system.addTrip(trip100);
        
        User customer3 = new User("C3", "customer3@example.com", "3333333333");
        
        // Input: Check booking eligibility for Trip T100 with 3 seats at exactly 2 hours before departure
        boolean result = system.validateBookingEligibility(customer3, trip100, 3, 
                                                         LocalDateTime.parse("2023-12-25 12:00", formatter));
        
        // Expected Output: False, Trip T100 seats = 47 (but since booking is denied, seats should remain 50)
        assertFalse("Booking should be denied when exactly 2 hours before departure", result);
        assertEquals("Trip T100 available seats should remain 50", 50, trip100.getAvailableSeats());
    }
    
    @Test
    public void testCase4_bookingAllowedWithMultipleNonOverlappingTrips() {
        // Setup: Customer C9 has booked an existing booking(2023-12-23 12:00, 2 seats) for Trip T199
        User customer9 = new User("C9", "customer9@example.com", "9999999999");
        User driver199 = new User("D199", "driver199@example.com", "1991991991");
        Trip trip199 = new Trip("T199", driver199, "Station G", "Station H", 50,
                               LocalDateTime.parse("2023-12-25 08:00", formatter),
                               LocalDateTime.parse("2023-12-25 10:00", formatter), 70.0);
        system.addTrip(trip199);
        
        Booking existingBooking = new Booking("B199", customer9, trip199, 2, 
                                            LocalDateTime.parse("2023-12-23 12:00", formatter));
        system.addBooking(existingBooking);
        
        // Setup: Trip T200 (2023-12-25 12:00-14:00, 40 seats)
        User driver200 = new User("D200", "driver200@example.com", "2002002002");
        Trip trip200 = new Trip("T200", driver200, "Station I", "Station J", 40,
                               LocalDateTime.parse("2023-12-25 12:00", formatter),
                               LocalDateTime.parse("2023-12-25 14:00", formatter), 80.0);
        system.addTrip(trip200);
        
        // Input: Customer C9 create a new booking(2023-12-23 14:00, 4 seats) for Trip T200
        boolean result = system.validateBookingEligibility(customer9, trip200, 4, 
                                                         LocalDateTime.parse("2023-12-23 14:00", formatter));
        
        // Expected Output: true, Trip T200 seats = 36
        assertTrue("Booking should be allowed for non-overlapping trip", result);
        assertEquals("Trip T200 available seats should be updated to 36", 36, trip200.getAvailableSeats());
    }
    
    @Test
    public void testCase5_bookingDeniedWhenCustomerHasOverlappingBooking() {
        // Setup: Customer C10 has existing booking(2023-12-23 12:00, 2 seats) for Trip T299
        User customer10 = new User("C10", "customer10@example.com", "1010101010");
        User driver299 = new User("D299", "driver299@example.com", "2992992992");
        Trip trip299 = new Trip("T299", driver299, "Station K", "Station L", 50,
                               LocalDateTime.parse("2023-12-25 13:00", formatter),
                               LocalDateTime.parse("2023-12-25 15:00", formatter), 90.0);
        system.addTrip(trip299);
        
        Booking existingBooking = new Booking("B299", customer10, trip299, 2, 
                                            LocalDateTime.parse("2023-12-23 12:00", formatter));
        system.addBooking(existingBooking);
        
        // Setup: Trip T300 (2023-12-25 14:00-16:00, 40 seats)
        User driver300 = new User("D300", "driver300@example.com", "3003003003");
        Trip trip300 = new Trip("T300", driver300, "Station M", "Station N", 40,
                               LocalDateTime.parse("2023-12-25 14:00", formatter),
                               LocalDateTime.parse("2023-12-25 16:00", formatter), 100.0);
        system.addTrip(trip300);
        
        // Input: Customer C10 create a new booking(2023-12-23 14:00, 4 seats) for Trip T300
        boolean result = system.validateBookingEligibility(customer10, trip300, 4, 
                                                         LocalDateTime.parse("2023-12-23 14:00", formatter));
        
        // Expected Output: false, Trip T300 seats = 40
        assertFalse("Booking should be denied due to overlapping booking on same day", result);
        assertEquals("Trip T300 available seats should remain 40", 40, trip300.getAvailableSeats());
    }
}