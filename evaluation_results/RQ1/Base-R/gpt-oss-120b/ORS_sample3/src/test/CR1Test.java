import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Driver driver1;
    private Driver driver2;
    private Customer customer1;
    private Customer customer2;
    private Customer customer3;
    private Customer customer9;
    private Customer customer10;
    private Trip trip123;
    private Trip trip456;
    private Trip trip100;
    private Trip trip199;
    private Trip trip200;
    private Trip trip299;
    private Trip trip300;
    
    @Before
    public void setUp() {
        // Initialize drivers
        driver1 = new Driver("D1", "d1@example.com", "1234567890");
        driver2 = new Driver("D2", "d2@example.com", "0987654321");
        
        // Initialize customers
        customer1 = new Customer("C1", "c1@example.com", "1111111111");
        customer2 = new Customer("C2", "c2@example.com", "2222222222");
        customer3 = new Customer("C3", "c3@example.com", "3333333333");
        customer9 = new Customer("C9", "c9@example.com", "9999999999");
        customer10 = new Customer("C10", "c10@example.com", "1010101010");
        
        // Initialize trips
        trip123 = new DirectTrip("StationA", "StationB", 5, 
            LocalDateTime.of(2023, 12, 25, 14, 0), 
            LocalDateTime.of(2023, 12, 25, 16, 0), 
            50.0, driver1);
        
        trip456 = new DirectTrip("StationC", "StationD", 2, 
            LocalDateTime.of(2023, 12, 25, 10, 0), 
            LocalDateTime.of(2023, 12, 25, 12, 0), 
            40.0, driver2);
        
        trip100 = new DirectTrip("StationE", "StationF", 50, 
            LocalDateTime.of(2023, 12, 25, 14, 0), 
            LocalDateTime.of(2023, 12, 25, 16, 0), 
            60.0, driver1);
        
        trip199 = new DirectTrip("StationG", "StationH", 50, 
            LocalDateTime.of(2023, 12, 25, 8, 0), 
            LocalDateTime.of(2023, 12, 25, 10, 0), 
            70.0, driver2);
        
        trip200 = new DirectTrip("StationI", "StationJ", 40, 
            LocalDateTime.of(2023, 12, 25, 12, 0), 
            LocalDateTime.of(2023, 12, 25, 14, 0), 
            80.0, driver1);
        
        trip299 = new DirectTrip("StationK", "StationL", 50, 
            LocalDateTime.of(2023, 12, 25, 13, 0), 
            LocalDateTime.of(2023, 12, 25, 15, 0), 
            90.0, driver2);
        
        trip300 = new DirectTrip("StationM", "StationN", 40, 
            LocalDateTime.of(2023, 12, 25, 14, 0), 
            LocalDateTime.of(2023, 12, 25, 16, 0), 
            100.0, driver1);
        
        // Set up existing bookings
        Booking booking1 = new Booking(customer2, trip456, 3, 
            LocalDateTime.of(2023, 12, 25, 7, 30));
        customer2.addBooking(booking1);
        trip456.addBooking(booking1);
        trip456.reduceAvailableSeats(3);
        
        Booking booking2 = new Booking(customer3, trip100, 3, 
            LocalDateTime.of(2023, 12, 25, 12, 0));
        customer3.addBooking(booking2);
        trip100.addBooking(booking2);
        trip100.reduceAvailableSeats(3);
        
        Booking booking3 = new Booking(customer9, trip199, 2, 
            LocalDateTime.of(2023, 12, 23, 12, 0));
        customer9.addBooking(booking3);
        trip199.addBooking(booking3);
        trip199.reduceAvailableSeats(2);
        
        Booking booking4 = new Booking(customer10, trip299, 2, 
            LocalDateTime.of(2023, 12, 23, 12, 0));
        customer10.addBooking(booking4);
        trip299.addBooking(booking4);
        trip299.reduceAvailableSeats(2);
    }
    
    @Test
    public void testCase1_validBookingWithAvailableSeatsAndNoOverlap() {
        // Test Case 1: Valid booking with available seats and no overlap
        // Setup: Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        LocalDateTime bookingTime = LocalDateTime.of(2023, 12, 25, 11, 0);
        
        // Execute validation
        boolean result = customer1.validateBookingEligibility(trip123, 3, bookingTime);
        
        // Verify result and seat count
        assertTrue("Booking should be successful", result);
        assertEquals("Available seats should be reduced to 2", 2, trip123.getAvailableSeats());
        
        // Verify booking was added
        assertEquals("Customer should have 1 booking", 1, customer1.getBookings().size());
        assertEquals("Trip should have 1 booking", 1, trip123.getBookings().size());
    }
    
    @Test
    public void testCase2_bookingDeniedDueToSeatShortage() {
        // Test Case 2: Booking denied due to seat shortage
        // Setup: Customer C2 already has booking for T456 (3 seats), current time: 2023-12-25 07:30
        LocalDateTime bookingTime = LocalDateTime.of(2023, 12, 25, 7, 30);
        
        // Execute validation (trying to book more seats than available)
        boolean result = customer2.validateBookingEligibility(trip456, 3, bookingTime);
        
        // Verify result and seat count remains unchanged
        assertFalse("Booking should be denied due to insufficient seats", result);
        assertEquals("Available seats should remain at 2", 2, trip456.getAvailableSeats());
    }
    
    @Test
    public void testCase3_bookingDeniedDueToTimeCutoffExactly2Hours() {
        // Test Case 3: Booking denied due to time cutoff (exactly 2 hours before)
        // Setup: Current time: 2023-12-25 12:00 (exactly 2 hours before departure)
        LocalDateTime bookingTime = LocalDateTime.of(2023, 12, 25, 12, 0);
        
        // Execute validation
        boolean result = customer3.validateBookingEligibility(trip100, 3, bookingTime);
        
        // Verify result and seat count remains unchanged
        assertFalse("Booking should be denied due to exactly 2 hours cutoff", result);
        assertEquals("Available seats should remain at 47", 47, trip100.getAvailableSeats());
    }
    
    @Test
    public void testCase4_bookingAllowedWithMultipleNonOverlappingTrips() {
        // Test Case 4: Booking allowed with multiple non-overlapping trips
        // Setup: Customer C9 has existing booking for Trip T199, creates new booking for Trip T200
        LocalDateTime bookingTime = LocalDateTime.of(2023, 12, 23, 14, 0);
        
        // Execute validation
        boolean result = customer9.validateBookingEligibility(trip200, 4, bookingTime);
        
        // Verify result and seat count
        assertTrue("Booking should be successful with non-overlapping trips", result);
        assertEquals("Available seats should be reduced to 36", 36, trip200.getAvailableSeats());
        
        // Verify booking was added
        assertEquals("Customer should have 2 bookings", 2, customer9.getBookings().size());
        assertEquals("Trip should have 1 booking", 1, trip200.getBookings().size());
    }
    
    @Test
    public void testCase5_bookingDeniedWhenCustomerHasOverlappingBooking() {
        // Test Case 5: Booking denied when customer has overlapping booking
        // Setup: Customer C10 has existing booking for Trip T299, tries to book Trip T300 (1-hour overlap)
        LocalDateTime bookingTime = LocalDateTime.of(2023, 12, 23, 14, 0);
        
        // Execute validation
        boolean result = customer10.validateBookingEligibility(trip300, 4, bookingTime);
        
        // Verify result and seat count remains unchanged
        assertFalse("Booking should be denied due to time overlap", result);
        assertEquals("Available seats should remain at 40", 40, trip300.getAvailableSeats());
    }
}