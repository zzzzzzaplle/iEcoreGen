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
    private Trip tripT123;
    private Trip tripT456;
    private Trip tripT100;
    private Trip tripT199;
    private Trip tripT200;
    private Trip tripT299;
    private Trip tripT300;
    
    @Before
    public void setUp() {
        // Initialize test data that can be reused across test cases
        driver1 = new Driver("D1", "d1@example.com", "1234567890");
        driver2 = new Driver("D2", "d2@example.com", "0987654321");
        
        customer1 = new Customer("C1", "c1@example.com", "1111111111");
        customer2 = new Customer("C2", "c2@example.com", "2222222222");
        customer3 = new Customer("C3", "c3@example.com", "3333333333");
        customer9 = new Customer("C9", "c9@example.com", "9999999999");
        customer10 = new Customer("C10", "c10@example.com", "1010101010");
    }
    
    @Test
    public void testCase1_validBookingWithAvailableSeatsAndNoOverlap() {
        // Setup: Create Driver D1 with Trip T123 (2023-12-25 14:00-16:00, seats:5)
        tripT123 = new DirectTrip("T123", driver1, "StationA", "StationB", 
            5, LocalDateTime.of(2023, 12, 25, 14, 0), 
            LocalDateTime.of(2023, 12, 25, 16, 0), 50.0);
        driver1.addTrip(tripT123);
        
        // Setup: Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        Trip tripT456 = new DirectTrip("T456", driver2, "StationC", "StationD", 
            10, LocalDateTime.of(2023, 12, 25, 14, 0), 
            LocalDateTime.of(2023, 12, 25, 16, 0), 60.0);
        Booking existingBooking = new Booking("B456", customer1, tripT456, 3, 
            LocalDateTime.of(2023, 12, 25, 11, 0));
        customer1.addBooking(existingBooking);
        
        // Input: Check booking eligibility for Trip T123 with 3 seats at booking time 2023-12-25 11:00
        LocalDateTime bookingTime = LocalDateTime.of(2023, 12, 25, 11, 0);
        boolean result = ORSService.validateBookingEligibility(customer1, tripT123, 3, bookingTime);
        
        // Expected Output: true, updated Trip T123 seats = 2
        assertTrue("Booking should be eligible", result);
        assertEquals("Available seats should be updated to 2", 2, tripT123.getAvailableSeats());
    }
    
    @Test
    public void testCase2_bookingDeniedDueToSeatShortage() {
        // Setup: Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        tripT456 = new DirectTrip("T456", driver2, "StationE", "StationF", 
            2, LocalDateTime.of(2023, 12, 25, 10, 0), 
            LocalDateTime.of(2023, 12, 25, 12, 0), 40.0);
        driver2.addTrip(tripT456);
        
        // Setup: Create Customer C2 with existing booking for T456 (3 seats)
        // Note: This setup seems inconsistent since trip only has 2 seats but booking shows 3
        // Assuming the booking is for a different trip with same ID, or this is a test of the seat availability check
        Booking existingBooking = new Booking("B456", customer2, tripT456, 3, 
            LocalDateTime.of(2023, 12, 25, 7, 0));
        customer2.addBooking(existingBooking);
        
        // Input: Check booking eligibility for Trip T456 with 3 seats at booking time 2023-12-25 07:30
        LocalDateTime bookingTime = LocalDateTime.of(2023, 12, 25, 7, 30);
        boolean result = ORSService.validateBookingEligibility(customer2, tripT456, 3, bookingTime);
        
        // Expected Output: false, Trip T123 seats = 2
        assertFalse("Booking should be denied due to seat shortage", result);
        assertEquals("Available seats should remain 2", 2, tripT456.getAvailableSeats());
    }
    
    @Test
    public void testCase3_bookingDeniedDueToTimeCutoffExactly2HoursBefore() {
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        tripT100 = new DirectTrip("T100", driver1, "StationG", "StationH", 
            50, LocalDateTime.of(2023, 12, 25, 14, 0), 
            LocalDateTime.of(2023, 12, 25, 16, 0), 70.0);
        
        // Input: Check booking eligibility for Trip T100 with 3 seats at booking time 2023-12-25 12:00
        LocalDateTime bookingTime = LocalDateTime.of(2023, 12, 25, 12, 0);
        boolean result = ORSService.validateBookingEligibility(customer3, tripT100, 3, bookingTime);
        
        // Expected Output: False, Trip T123 seats = 47
        assertFalse("Booking should be denied due to exactly 2 hours cutoff", result);
        assertEquals("Available seats should remain 50", 50, tripT100.getAvailableSeats());
    }
    
    @Test
    public void testCase4_bookingAllowedWithMultipleNonOverlappingTrips() {
        // Setup: Customer C9 has booked an existing booking(2023-12-23 12:00, 2 seats) for Trip T199 (2023-12-25 08:00-10:00, 50 seats)
        tripT199 = new DirectTrip("T199", driver1, "StationI", "StationJ", 
            50, LocalDateTime.of(2023, 12, 25, 8, 0), 
            LocalDateTime.of(2023, 12, 25, 10, 0), 80.0);
        Booking existingBooking = new Booking("B199", customer9, tripT199, 2, 
            LocalDateTime.of(2023, 12, 23, 12, 0));
        customer9.addBooking(existingBooking);
        
        // Setup: Customer C9 create a new booking(2023-12-23 14:00, 4 seats) for Trip T200 (2023-12-25 12:00-14:00, 40 seats)
        tripT200 = new DirectTrip("T200", driver2, "StationK", "StationL", 
            40, LocalDateTime.of(2023, 12, 25, 12, 0), 
            LocalDateTime.of(2023, 12, 25, 14, 0), 90.0);
        
        // Input: Check booking eligibility for Trip T200 with 4 seats at booking time 2023-12-23 14:00
        LocalDateTime bookingTime = LocalDateTime.of(2023, 12, 23, 14, 0);
        boolean result = ORSService.validateBookingEligibility(customer9, tripT200, 4, bookingTime);
        
        // Expected Output: true, Trip T200 seats = 36
        assertTrue("Booking should be eligible with non-overlapping trips", result);
        assertEquals("Available seats should be updated to 36", 36, tripT200.getAvailableSeats());
    }
    
    @Test
    public void testCase5_bookingDeniedWhenCustomerHasOverlappingBooking() {
        // Setup: Customer C10 has existing booking(2023-12-23 12:00, 2 seats) for Trip T299 (departure: 2023-12-25 13:00-15:00, 50 seats)
        tripT299 = new DirectTrip("T299", driver1, "StationM", "StationN", 
            50, LocalDateTime.of(2023, 12, 25, 13, 0), 
            LocalDateTime.of(2023, 12, 25, 15, 0), 100.0);
        Booking existingBooking = new Booking("B299", customer10, tripT299, 2, 
            LocalDateTime.of(2023, 12, 23, 12, 0));
        customer10.addBooking(existingBooking);
        
        // Setup: Customer C10 create a new booking(2023-12-23 14:00, 4 seats) for Trip T300 (2023-12-25 14:00-16:00, 40 seats)
        tripT300 = new DirectTrip("T300", driver2, "StationO", "StationP", 
            40, LocalDateTime.of(2023, 12, 25, 14, 0), 
            LocalDateTime.of(2023, 12, 25, 16, 0), 110.0);
        
        // Input: Check booking eligibility for Trip T300 with 4 seats at booking time 2023-12-23 14:00
        LocalDateTime bookingTime = LocalDateTime.of(2023, 12, 23, 14, 0);
        boolean result = ORSService.validateBookingEligibility(customer10, tripT300, 4, bookingTime);
        
        // Expected Output: false, Trip T300 seats = 40
        assertFalse("Booking should be denied due to overlapping booking", result);
        assertEquals("Available seats should remain 40", 40, tripT300.getAvailableSeats());
    }
}