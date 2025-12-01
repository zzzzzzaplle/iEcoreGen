import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private Trip trip;
    private Customer customer;
    private MembershipPackage membership;
    
    @Before
    public void setUp() {
        // Common setup for test objects
        trip = new Trip();
        customer = new Customer();
        membership = new MembershipPackage();
    }
    
    @Test
    public void testCase1_20PercentDiscountAppliedForEarlyBooking() {
        // Setup: Trip T789 (price: 100.0, departure: 2023-12-25 08:00)
        trip.setPrice(100.0);
        trip.setDepartureTime("2023-12-25 08:00");
        
        // Setup: Customer C3 has membership with 20% DISCOUNTS award
        Award[] awards = {Award.DISCOUNTS};
        membership.setAwards(awards);
        customer.setMembershipPackage(membership);
        
        // Booking made at 2023-12-24 07:00 (25 hours before)
        String bookingTime = "2023-12-24 07:00";
        
        // Expected Output: 80.0
        double result = trip.calculateDiscountedPrice(customer, bookingTime);
        assertEquals(80.0, result, 0.001);
    }
    
    @Test
    public void testCase2_DiscountDeniedForLateBooking() {
        // Setup: Trip T999 (price: 200.0, departure: 2023-12-25 12:00)
        trip.setPrice(200.0);
        trip.setDepartureTime("2023-12-25 12:00");
        
        // Setup: Customer C4 has membership with 20% DISCOUNTS award
        Award[] awards = {Award.DISCOUNTS};
        membership.setAwards(awards);
        customer.setMembershipPackage(membership);
        
        // Booking made at 2023-12-25 10:30 (1.5 hours before)
        String bookingTime = "2023-12-25 10:30";
        
        // Expected Output: 200.0
        double result = trip.calculateDiscountedPrice(customer, bookingTime);
        assertEquals(200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_Exact24HourBoundaryForDiscount() {
        // Setup: Trip T800 (price: 100.0, departure: 2023-12-25 08:00)
        trip.setPrice(100.0);
        trip.setDepartureTime("2023-12-25 08:00");
        
        // Setup: Customer has membership with 20% DISCOUNTS award
        Award[] awards = {Award.DISCOUNTS};
        membership.setAwards(awards);
        customer.setMembershipPackage(membership);
        
        // Booking made at 2023-12-24 08:00 (exactly 24 hours before)
        String bookingTime = "2023-12-24 08:00";
        
        // Expected Output: 80.0
        double result = trip.calculateDiscountedPrice(customer, bookingTime);
        assertEquals(80.0, result, 0.001);
    }
    
    @Test
    public void testCase4_NoDiscountWithoutMembership() {
        // Setup: Trip T900 (price: 200.0, departure: 2023-12-26 12:00)
        trip.setPrice(200.0);
        trip.setDepartureTime("2023-12-26 12:00");
        
        // Setup: Customer has no membership
        customer.setMembershipPackage(null);
        
        // Booking made 48 hours before (2023-12-24 12:00)
        String bookingTime = "2023-12-24 12:00";
        
        // Expected Output: 200.0
        double result = trip.calculateDiscountedPrice(customer, bookingTime);
        assertEquals(200.0, result, 0.001);
    }
    
    @Test
    public void testCase5_DiscountAppliesOnlyToEligibleMembershipType() {
        // Setup: Trip T950 (price: 150.0)
        trip.setPrice(150.0);
        trip.setDepartureTime("2023-12-26 10:00"); // Need departure time for calculation
        
        // Setup: Customer has membership with CASHBACK only (no DISCOUNTS)
        Award[] awards = {Award.CASHBACK};
        membership.setAwards(awards);
        customer.setMembershipPackage(membership);
        
        // Booking made 30 hours before
        String bookingTime = "2023-12-25 04:00"; // 30 hours before departure
        
        // Expected Output: 150.0
        double result = trip.calculateDiscountedPrice(customer, bookingTime);
        assertEquals(150.0, result, 0.001);
    }
}