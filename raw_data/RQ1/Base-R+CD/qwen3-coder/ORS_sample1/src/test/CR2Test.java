import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR2Test {
    
    private Trip trip;
    private Customer customer;
    private MembershipPackage membership;
    
    @Before
    public void setUp() {
        // Common setup for test cases
        trip = new Trip();
        customer = new Customer();
    }
    
    @Test
    public void testCase1_20PercentDiscountAppliedForEarlyBooking() {
        // Setup: Trip T789 (price: 100.0, departure: 2023-12-25 08:00)
        trip.setPrice(100.0);
        
        // Create membership with DISCOUNTS award for customer C3
        membership = new MembershipPackage();
        membership.setAwards(new Award[]{Award.DISCOUNTS});
        customer.setMembershipPackage(membership);
        
        // Booking made at 2023-12-24 07:00 (25 hours before)
        String bookingTime = "2023-12-24 07:00";
        
        // Set trip departure date and time
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            trip.setDepartureDate(dateFormat.parse("2023-12-25"));
            trip.setDepartureTime("08:00");
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Calculate discounted price
        double result = trip.calculateDiscountedPrice(customer, bookingTime);
        
        // Expected Output: 80.0
        assertEquals(80.0, result, 0.001);
    }
    
    @Test
    public void testCase2_DiscountDeniedForLateBooking() {
        // Setup: Trip T999 (price: 200.0, departure: 2023-12-25 12:00)
        trip.setPrice(200.0);
        
        // Create membership with DISCOUNTS award for customer C4
        membership = new MembershipPackage();
        membership.setAwards(new Award[]{Award.DISCOUNTS});
        customer.setMembershipPackage(membership);
        
        // Booking made at 2023-12-25 10:30 (1.5 hours before)
        String bookingTime = "2023-12-25 10:30";
        
        // Set trip departure date and time
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            trip.setDepartureDate(dateFormat.parse("2023-12-25"));
            trip.setDepartureTime("12:00");
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Calculate discounted price
        double result = trip.calculateDiscountedPrice(customer, bookingTime);
        
        // Expected Output: 200.0
        assertEquals(200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_Exact24HourBoundaryForDiscount() {
        // Setup: Trip T800 (price: 100.0, departure: 2023-12-25 08:00)
        trip.setPrice(100.0);
        
        // Create membership with DISCOUNTS award
        membership = new MembershipPackage();
        membership.setAwards(new Award[]{Award.DISCOUNTS});
        customer.setMembershipPackage(membership);
        
        // Booking made at 2023-12-24 08:00 (exactly 24 hours before)
        String bookingTime = "2023-12-24 08:00";
        
        // Set trip departure date and time
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            trip.setDepartureDate(dateFormat.parse("2023-12-25"));
            trip.setDepartureTime("08:00");
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Calculate discounted price
        double result = trip.calculateDiscountedPrice(customer, bookingTime);
        
        // Expected Output: 80.0 (Tests the boundary condition)
        assertEquals(80.0, result, 0.001);
    }
    
    @Test
    public void testCase4_NoDiscountWithoutMembership() {
        // Setup: Trip T900 (price: 200.0, departure: 2023-12-26 12:00)
        trip.setPrice(200.0);
        
        // Customer has no membership (membershipPackage remains null)
        
        // Booking made 48 hours before (2023-12-24 12:00)
        String bookingTime = "2023-12-24 12:00";
        
        // Set trip departure date and time
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            trip.setDepartureDate(dateFormat.parse("2023-12-26"));
            trip.setDepartureTime("12:00");
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Calculate discounted price
        double result = trip.calculateDiscountedPrice(customer, bookingTime);
        
        // Expected Output: 200.0 (Verifies membership requirement)
        assertEquals(200.0, result, 0.001);
    }
    
    @Test
    public void testCase5_DiscountAppliesOnlyToEligibleMembershipType() {
        // Setup: Trip T950 (price: 150.0)
        trip.setPrice(150.0);
        
        // Customer has membership with CASHBACK only (no DISCOUNTS)
        membership = new MembershipPackage();
        membership.setAwards(new Award[]{Award.CASHBACK});
        customer.setMembershipPackage(membership);
        
        // Booking made 30 hours before
        String bookingTime = "2023-12-24 02:00"; // Assuming departure is 2023-12-25 08:00 (30 hours before)
        
        // Set trip departure date and time
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            trip.setDepartureDate(dateFormat.parse("2023-12-25"));
            trip.setDepartureTime("08:00");
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Calculate discounted price
        double result = trip.calculateDiscountedPrice(customer, bookingTime);
        
        // Expected Output: 150.0 (Tests award type validation)
        assertEquals(150.0, result, 0.001);
    }
}