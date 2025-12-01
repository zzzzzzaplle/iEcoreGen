import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CR2Test {
    
    private Trip tripT789;
    private Trip tripT999;
    private Trip tripT800;
    private Trip tripT900;
    private Trip tripT950;
    private Customer customerWithDiscount;
    private Customer customerWithCashback;
    private Customer customerWithoutMembership;
    private Booking booking1;
    private Booking booking2;
    private Booking booking3;
    private Booking booking4;
    private Booking booking5;
    
    @Before
    public void setUp() {
        // Create trips with specified parameters
        tripT789 = new DirectTrip("Station A", "Station B", 50, 
            LocalDateTime.of(2023, 12, 25, 8, 0), 
            LocalDateTime.of(2023, 12, 25, 10, 0), 
            100.0, null);
            
        tripT999 = new DirectTrip("Station C", "Station D", 40,
            LocalDateTime.of(2023, 12, 25, 12, 0),
            LocalDateTime.of(2023, 12, 25, 14, 0),
            200.0, null);
            
        tripT800 = new DirectTrip("Station E", "Station F", 30,
            LocalDateTime.of(2023, 12, 25, 8, 0),
            LocalDateTime.of(2023, 12, 25, 9, 30),
            100.0, null);
            
        tripT900 = new DirectTrip("Station G", "Station H", 35,
            LocalDateTime.of(2023, 12, 26, 12, 0),
            LocalDateTime.of(2023, 12, 26, 14, 0),
            200.0, null);
            
        tripT950 = new DirectTrip("Station I", "Station J", 25,
            LocalDateTime.of(2023, 12, 27, 10, 0),
            LocalDateTime.of(2023, 12, 27, 12, 0),
            150.0, null);
        
        // Create customers with different membership types
        customerWithDiscount = new Customer("C3", "c3@example.com", "1234567890");
        Membership discountMembership = new Membership(true, RewardType.DISCOUNT);
        customerWithDiscount.setMembership(discountMembership);
        
        customerWithCashback = new Customer("C5", "c5@example.com", "0987654321");
        Membership cashbackMembership = new Membership(true, RewardType.CASHBACK);
        customerWithCashback.setMembership(cashbackMembership);
        
        customerWithoutMembership = new Customer("C6", "c6@example.com", "1112223333");
        // No membership set
        
        // Create bookings with different timing conditions
        booking1 = new Booking(customerWithDiscount, tripT789, 1, 
            LocalDateTime.of(2023, 12, 24, 7, 0)); // 25 hours before
        
        booking2 = new Booking(customerWithDiscount, tripT999, 1,
            LocalDateTime.of(2023, 12, 25, 10, 30)); // 1.5 hours before
        
        booking3 = new Booking(customerWithDiscount, tripT800, 1,
            LocalDateTime.of(2023, 12, 24, 8, 0)); // exactly 24 hours before
        
        booking4 = new Booking(customerWithoutMembership, tripT900, 1,
            LocalDateTime.of(2023, 12, 24, 12, 0)); // 48 hours before
        
        booking5 = new Booking(customerWithCashback, tripT950, 1,
            LocalDateTime.of(2023, 12, 25, 16, 0)); // 30 hours before
    }
    
    @Test
    public void testCase1_20PercentDiscountAppliedForEarlyBooking() {
        // Test Case 1: "20% discount applied for early booking"
        // Input: Calculate price for Trip T789
        // Setup: Customer C3 has membership with DISCOUNT reward, booking made 25 hours before
        // Expected Output: 80.0
        
        double finalPrice = booking1.calculateFinalPrice();
        assertEquals("20% discount should be applied for early booking", 80.0, finalPrice, 0.001);
    }
    
    @Test
    public void testCase2_DiscountDeniedForLateBooking() {
        // Test Case 2: "Discount denied for late booking"
        // Input: Calculate price for Trip T999
        // Setup: Customer C4 has membership with DISCOUNT reward, booking made 1.5 hours before
        // Expected Output: 200.0
        
        double finalPrice = booking2.calculateFinalPrice();
        assertEquals("Discount should be denied for late booking", 200.0, finalPrice, 0.001);
    }
    
    @Test
    public void testCase3_Exact24HourBoundaryForDiscount() {
        // Test Case 3: "Exact 24-hour boundary for discount"
        // Input: Calculate price for Trip T800
        // Setup: Customer has membership with DISCOUNT reward, booking made exactly 24 hours before
        // Expected Output: 80.0
        
        double finalPrice = booking3.calculateFinalPrice();
        assertEquals("Discount should apply at exact 24-hour boundary", 80.0, finalPrice, 0.001);
    }
    
    @Test
    public void testCase4_NoDiscountWithoutMembership() {
        // Test Case 4: "No discount without membership"
        // Input: Calculate price for Trip T900
        // Setup: Customer has no membership, booking made 48 hours before
        // Expected Output: 200.0
        
        double finalPrice = booking4.calculateFinalPrice();
        assertEquals("No discount should apply without membership", 200.0, finalPrice, 0.001);
    }
    
    @Test
    public void testCase5_DiscountAppliesOnlyToEligibleMembershipType() {
        // Test Case 5: "Discount applies only to eligible membership type"
        // Input: Calculate price for Trip T950
        // Setup: Customer has membership with CASHBACK only (no DISCOUNT), booking made 30 hours before
        // Expected Output: 150.0
        
        double finalPrice = booking5.calculateFinalPrice();
        assertEquals("Discount should only apply to DISCOUNT membership type", 150.0, finalPrice, 0.001);
    }
}