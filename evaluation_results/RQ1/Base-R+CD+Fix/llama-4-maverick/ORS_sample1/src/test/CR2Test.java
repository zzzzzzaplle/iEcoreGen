import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR2Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    }
    
    @Test
    public void testCase1_20PercentDiscountAppliedForEarlyBooking() throws ParseException {
        // Setup
        Trip trip = new Trip();
        trip.setPrice(100.0);
        trip.setDepartureDate(dateFormat.parse("2023-12-25 08:00"));
        trip.setDepartureTime("08:00");
        
        Customer customer = new Customer();
        MembershipPackage membership = new MembershipPackage();
        membership.setAwards(new Award[]{Award.DISCOUNTS});
        customer.setMembershipPackage(membership);
        
        // Test
        double result = trip.calculateDiscountedPrice(customer, "2023-12-24 07:00");
        
        // Verify
        assertEquals(80.0, result, 0.001);
    }
    
    @Test
    public void testCase2_DiscountDeniedForLateBooking() throws ParseException {
        // Setup
        Trip trip = new Trip();
        trip.setPrice(200.0);
        trip.setDepartureDate(dateFormat.parse("2023-12-25 12:00"));
        trip.setDepartureTime("12:00");
        
        Customer customer = new Customer();
        MembershipPackage membership = new MembershipPackage();
        membership.setAwards(new Award[]{Award.DISCOUNTS});
        customer.setMembershipPackage(membership);
        
        // Test
        double result = trip.calculateDiscountedPrice(customer, "2023-12-25 10:30");
        
        // Verify
        assertEquals(200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_Exact24HourBoundaryForDiscount() throws ParseException {
        // Setup
        Trip trip = new Trip();
        trip.setPrice(100.0);
        trip.setDepartureDate(dateFormat.parse("2023-12-25 08:00"));
        trip.setDepartureTime("08:00");
        
        Customer customer = new Customer();
        MembershipPackage membership = new MembershipPackage();
        membership.setAwards(new Award[]{Award.DISCOUNTS});
        customer.setMembershipPackage(membership);
        
        // Test
        double result = trip.calculateDiscountedPrice(customer, "2023-12-24 08:00");
        
        // Verify
        assertEquals(80.0, result, 0.001);
    }
    
    @Test
    public void testCase4_NoDiscountWithoutMembership() throws ParseException {
        // Setup
        Trip trip = new Trip();
        trip.setPrice(200.0);
        trip.setDepartureDate(dateFormat.parse("2023-12-26 12:00"));
        trip.setDepartureTime("12:00");
        
        Customer customer = new Customer();
        // Customer has no membership (null)
        
        // Test
        double result = trip.calculateDiscountedPrice(customer, "2023-12-24 12:00");
        
        // Verify
        assertEquals(200.0, result, 0.001);
    }
    
    @Test
    public void testCase5_DiscountAppliesOnlyToEligibleMembershipType() throws ParseException {
        // Setup
        Trip trip = new Trip();
        trip.setPrice(150.0);
        trip.setDepartureDate(dateFormat.parse("2023-12-25 12:00"));
        trip.setDepartureTime("12:00");
        
        Customer customer = new Customer();
        MembershipPackage membership = new MembershipPackage();
        membership.setAwards(new Award[]{Award.CASHBACK}); // Only CASHBACK, no DISCOUNTS
        customer.setMembershipPackage(membership);
        
        // Test - Booking made 30 hours before (2023-12-24 06:00)
        double result = trip.calculateDiscountedPrice(customer, "2023-12-24 06:00");
        
        // Verify
        assertEquals(150.0, result, 0.001);
    }
}