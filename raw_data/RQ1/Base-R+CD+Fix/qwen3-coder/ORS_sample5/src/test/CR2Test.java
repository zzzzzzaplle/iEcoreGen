import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR2Test {
    
    private Trip trip;
    private Customer customer;
    private MembershipPackage membershipPackage;
    
    @Before
    public void setUp() {
        // Common setup that can be reused across tests
        trip = new Trip();
        customer = new Customer();
        membershipPackage = new MembershipPackage();
    }
    
    @Test
    public void testCase1_20PercentDiscountAppliedForEarlyBooking() {
        // Setup: Create Trip T789 (price: 100.0, departure: 2023-12-25 08:00)
        trip.setPrice(100.0);
        trip.setDepartureTime("2023-12-25 08:00");
        
        // Setup: Customer C3 has membership with 20% DISCOUNTS award
        membershipPackage.setAwards(new Award[]{Award.DISCOUNTS});
        customer.setMembershipPackage(membershipPackage);
        
        // Setup: Booking made at 2023-12-24 07:00 (25 hours before)
        String bookingTime = "2023-12-24 07:00";
        
        // Execute: Calculate discounted price
        double result = trip.calculateDiscountedPrice(customer, bookingTime);
        
        // Verify: Expected Output: 80.0
        assertEquals(80.0, result, 0.001);
    }
    
    @Test
    public void testCase2_DiscountDeniedForLateBooking() {
        // Setup: Create Trip T999 (price: 200.0, departure: 2023-12-25 12:00)
        trip.setPrice(200.0);
        trip.setDepartureTime("2023-12-25 12:00");
        
        // Setup: Customer C4 has membership with 20% DISCOUNTS award
        membershipPackage.setAwards(new Award[]{Award.DISCOUNTS});
        customer.setMembershipPackage(membershipPackage);
        
        // Setup: Booking made at 2023-12-25 10:30 (1.5 hours before)
        String bookingTime = "2023-12-25 10:30";
        
        // Execute: Calculate discounted price
        double result = trip.calculateDiscountedPrice(customer, bookingTime);
        
        // Verify: Expected Output: 200.0
        assertEquals(200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_Exact24HourBoundaryForDiscount() {
        // Setup: Trip T800 (price: 100.0, departure: 2023-12-25 08:00)
        trip.setPrice(100.0);
        trip.setDepartureTime("2023-12-25 08:00");
        
        // Setup: Customer has membership with 20% DISCOUNTS award
        membershipPackage.setAwards(new Award[]{Award.DISCOUNTS});
        customer.setMembershipPackage(membershipPackage);
        
        // Setup: Booking made at 2023-12-24 08:00 (exactly 24 hours before)
        String bookingTime = "2023-12-24 08:00";
        
        // Execute: Calculate discounted price
        double result = trip.calculateDiscountedPrice(customer, bookingTime);
        
        // Verify: Expected Output: 80.0
        assertEquals(80.0, result, 0.001);
    }
    
    @Test
    public void testCase4_NoDiscountWithoutMembership() {
        // Setup: Trip T900 (price: 200.0, departure: 2023-12-26 12:00)
        trip.setPrice(200.0);
        trip.setDepartureTime("2023-12-26 12:00");
        
        // Setup: Customer has no membership
        customer.setMembershipPackage(null);
        
        // Setup: Booking made 48 hours before (2023-12-24 12:00)
        String bookingTime = "2023-12-24 12:00";
        
        // Execute: Calculate discounted price
        double result = trip.calculateDiscountedPrice(customer, bookingTime);
        
        // Verify: Expected Output: 200.0
        assertEquals(200.0, result, 0.001);
    }
    
    @Test
    public void testCase5_DiscountAppliesOnlyToEligibleMembershipType() {
        // Setup: Trip T950 (price: 150.0)
        trip.setPrice(150.0);
        trip.setDepartureTime("2023-12-26 12:00");
        
        // Setup: Customer has membership with CASHBACK only (no DISCOUNTS)
        membershipPackage.setAwards(new Award[]{Award.CASHBACK});
        customer.setMembershipPackage(membershipPackage);
        
        // Setup: Booking made 30 hours before
        String bookingTime = "2023-12-25 06:00";
        
        // Execute: Calculate discounted price
        double result = trip.calculateDiscountedPrice(customer, bookingTime);
        
        // Verify: Expected Output: 150.0
        assertEquals(150.0, result, 0.001);
    }
}