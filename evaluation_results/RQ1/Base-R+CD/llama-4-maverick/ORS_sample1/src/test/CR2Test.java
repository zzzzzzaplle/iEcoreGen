import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR2Test {
    private Trip trip;
    private Customer customer;
    private MembershipPackage membershipPackage;
    private SimpleDateFormat dateFormat;

    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    }

    @Test
    public void testCase1_twentyPercentDiscountAppliedForEarlyBooking() throws Exception {
        // Setup
        trip = new Trip();
        trip.setPrice(100.0);
        trip.setDepartureDate(dateFormat.parse("2023-12-25 08:00"));
        
        customer = new Customer();
        membershipPackage = new MembershipPackage();
        membershipPackage.setAwards(new Award[]{Award.DISCOUNTS});
        customer.setMembershipPackage(membershipPackage);
        
        // Test
        double result = trip.calculateDiscountedPrice(customer, "2023-12-24 07:00");
        
        // Verify
        assertEquals(80.0, result, 0.001);
    }

    @Test
    public void testCase2_discountDeniedForLateBooking() throws Exception {
        // Setup
        trip = new Trip();
        trip.setPrice(200.0);
        trip.setDepartureDate(dateFormat.parse("2023-12-25 12:00"));
        
        customer = new Customer();
        membershipPackage = new MembershipPackage();
        membershipPackage.setAwards(new Award[]{Award.DISCOUNTS});
        customer.setMembershipPackage(membershipPackage);
        
        // Test
        double result = trip.calculateDiscountedPrice(customer, "2023-12-25 10:30");
        
        // Verify
        assertEquals(200.0, result, 0.001);
    }

    @Test
    public void testCase3_exact24HourBoundaryForDiscount() throws Exception {
        // Setup
        trip = new Trip();
        trip.setPrice(100.0);
        trip.setDepartureDate(dateFormat.parse("2023-12-25 08:00"));
        
        customer = new Customer();
        membershipPackage = new MembershipPackage();
        membershipPackage.setAwards(new Award[]{Award.DISCOUNTS});
        customer.setMembershipPackage(membershipPackage);
        
        // Test
        double result = trip.calculateDiscountedPrice(customer, "2023-12-24 08:00");
        
        // Verify
        assertEquals(80.0, result, 0.001);
    }

    @Test
    public void testCase4_noDiscountWithoutMembership() throws Exception {
        // Setup
        trip = new Trip();
        trip.setPrice(200.0);
        trip.setDepartureDate(dateFormat.parse("2023-12-26 12:00"));
        
        customer = new Customer();
        // No membership package set
        
        // Test
        double result = trip.calculateDiscountedPrice(customer, "2023-12-24 12:00");
        
        // Verify
        assertEquals(200.0, result, 0.001);
    }

    @Test
    public void testCase5_discountAppliesOnlyToEligibleMembershipType() throws Exception {
        // Setup
        trip = new Trip();
        trip.setPrice(150.0);
        trip.setDepartureDate(dateFormat.parse("2023-12-25 12:00")); // Assuming departure date for time calculation
        
        customer = new Customer();
        membershipPackage = new MembershipPackage();
        membershipPackage.setAwards(new Award[]{Award.CASHBACK}); // Only CASHBACK, no DISCOUNTS
        customer.setMembershipPackage(membershipPackage);
        
        // Test - booking made 30 hours before departure
        double result = trip.calculateDiscountedPrice(customer, "2023-12-24 06:00");
        
        // Verify
        assertEquals(150.0, result, 0.001);
    }
}