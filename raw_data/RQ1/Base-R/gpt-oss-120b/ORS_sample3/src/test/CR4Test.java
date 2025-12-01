import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;

public class CR4Test {
    
    private Customer customer;
    private Membership pointsMembership;
    
    @Before
    public void setUp() {
        // Create a membership with POINTS reward type for common setup
        pointsMembership = new Membership(true, RewardType.POINTS);
    }
    
    @Test
    public void testCase1_PointsCalculationWithMultipleBookings() {
        // Setup: Customer C5 has membership with POINTS award
        customer = new Customer("C5", "c5@test.com", "1234567890");
        customer.setMembership(pointsMembership);
        
        // Create trips
        Trip trip1 = new DirectTrip("Station A", "Station B", 10, 
            LocalDateTime.of(2023, 12, 25, 12, 0), 
            LocalDateTime.of(2023, 12, 25, 14, 0), 200.0, null);
        
        Trip trip2 = new DirectTrip("Station C", "Station D", 10, 
            LocalDateTime.of(2023, 12, 26, 12, 0), 
            LocalDateTime.of(2023, 12, 26, 14, 0), 100.0, null);
        
        // Create bookings with booking dates in December 2023
        Booking booking1 = new Booking(customer, trip1, 2, 
            LocalDateTime.of(2023, 12, 1, 10, 0));
        Booking booking2 = new Booking(customer, trip2, 3, 
            LocalDateTime.of(2023, 12, 2, 10, 0));
        
        // Add bookings to customer
        customer.addBooking(booking1);
        customer.addBooking(booking2);
        
        // Test: Compute reward points for December 2023
        int result = customer.computeMonthlyRewardPoints(YearMonth.of(2023, 12));
        
        // Expected: (2+3)*5=25
        assertEquals("Points calculation with multiple bookings should return 25", 25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() {
        // Setup: Customer C6 has membership with POINTS award
        customer = new Customer("C6", "c6@test.com", "1234567890");
        customer.setMembership(pointsMembership);
        
        // Create trip with departure in December 2024 (booking date will be in 2024)
        Trip trip = new DirectTrip("Station A", "Station B", 10, 
            LocalDateTime.of(2024, 12, 26, 12, 0), 
            LocalDateTime.of(2024, 12, 26, 14, 0), 100.0, null);
        
        // Create booking with booking date in December 2024
        Booking booking = new Booking(customer, trip, 4, 
            LocalDateTime.of(2024, 12, 1, 10, 0));
        
        // Add booking to customer
        customer.addBooking(booking);
        
        // Test: Compute reward points for December 2023 (target month is 2023-12)
        int result = customer.computeMonthlyRewardPoints(YearMonth.of(2023, 12));
        
        // Expected: 0 (booking is in 2024, not 2023)
        assertEquals("Zero points should be returned for bookings outside target month", 0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() {
        // Setup: Customer C7 has membership with POINTS award
        customer = new Customer("C7", "c7@test.com", "1234567890");
        customer.setMembership(pointsMembership);
        
        // Create trips
        Trip trip1 = new DirectTrip("Station A", "Station B", 10, 
            LocalDateTime.of(2023, 12, 25, 12, 0), 
            LocalDateTime.of(2023, 12, 25, 14, 0), 200.0, null);
        
        Trip trip2 = new DirectTrip("Station C", "Station D", 10, 
            LocalDateTime.of(2023, 12, 25, 12, 0), 
            LocalDateTime.of(2023, 12, 25, 14, 0), 200.0, null);
        
        // Create bookings - one in November 2023, one in December 2023
        Booking booking1 = new Booking(customer, trip1, 2, 
            LocalDateTime.of(2023, 11, 30, 10, 0)); // November booking
        Booking booking2 = new Booking(customer, trip2, 3, 
            LocalDateTime.of(2023, 12, 1, 10, 0));  // December booking
        
        // Add bookings to customer
        customer.addBooking(booking1);
        customer.addBooking(booking2);
        
        // Test: Compute reward points for December 2023
        int result = customer.computeMonthlyRewardPoints(YearMonth.of(2023, 12));
        
        // Expected: 15 (only the December booking counts: 3*5=15)
        assertEquals("Only December bookings should be counted", 15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() {
        // Setup: Customer C8 has membership with POINTS award
        customer = new Customer("C8", "c8@test.com", "1234567890");
        customer.setMembership(pointsMembership);
        
        // Create trip with departure in March 2024
        Trip trip = new DirectTrip("Station A", "Station B", 10, 
            LocalDateTime.of(2024, 3, 25, 12, 0), 
            LocalDateTime.of(2024, 3, 25, 14, 0), 200.0, null);
        
        // Create booking with booking date in December 2023
        Booking booking = new Booking(customer, trip, 2, 
            LocalDateTime.of(2023, 12, 10, 10, 0));
        
        // Add booking to customer
        customer.addBooking(booking);
        
        // Test: Compute reward points for December 2023
        int result = customer.computeMonthlyRewardPoints(YearMonth.of(2023, 12));
        
        // Expected: 10 (2*5=10)
        assertEquals("Multiple seats edge case should return 10", 10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() {
        // Setup Customer C8
        Customer customer8 = new Customer("C8", "c8@test.com", "1234567890");
        customer8.setMembership(pointsMembership);
        
        // Setup Customer C9
        Customer customer9 = new Customer("C9", "c9@test.com", "1234567890");
        customer9.setMembership(pointsMembership);
        
        // Create trips for C8
        Trip trip1 = new DirectTrip("Station A", "Station B", 100, 
            LocalDateTime.of(2024, 5, 25, 12, 0), 
            LocalDateTime.of(2024, 5, 25, 14, 0), 150.0, null);
        
        Trip trip2 = new DirectTrip("Station C", "Station D", 100, 
            LocalDateTime.of(2024, 6, 25, 12, 0), 
            LocalDateTime.of(2024, 6, 25, 14, 0), 200.0, null);
        
        // Create trip for C9
        Trip trip3 = new DirectTrip("Station E", "Station F", 100, 
            LocalDateTime.of(2024, 7, 25, 12, 0), 
            LocalDateTime.of(2024, 7, 25, 14, 0), 200.0, null);
        
        // Create bookings for C8 (both in January 2024)
        Booking booking1 = new Booking(customer8, trip1, 50, 
            LocalDateTime.of(2024, 1, 10, 10, 0));
        Booking booking2 = new Booking(customer8, trip2, 50, 
            LocalDateTime.of(2024, 1, 15, 10, 0));
        
        // Create booking for C9 (in January 2024)
        Booking booking3 = new Booking(customer9, trip3, 50, 
            LocalDateTime.of(2024, 1, 10, 10, 0));
        
        // Add bookings to respective customers
        customer8.addBooking(booking1);
        customer8.addBooking(booking2);
        customer9.addBooking(booking3);
        
        // Test: Compute reward points for January 2024 for both customers
        int resultC8 = customer8.computeMonthlyRewardPoints(YearMonth.of(2024, 1));
        int resultC9 = customer9.computeMonthlyRewardPoints(YearMonth.of(2024, 1));
        
        // Expected: C8 reward points: 500, C9 reward points: 250
        assertEquals("C8 should have 500 reward points", 500, resultC8);
        assertEquals("C9 should have 250 reward points", 250, resultC9);
    }
}