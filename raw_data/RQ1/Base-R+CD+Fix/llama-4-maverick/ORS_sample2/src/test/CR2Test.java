import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR2Test {
    private Trip trip;
    private Customer customer;
    private MembershipPackage membershipPackage;
    private SimpleDateFormat sdf;

    @Before
    public void setUp() {
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    }

    @Test
    public void testCase1_20PercentDiscountAppliedForEarlyBooking() throws ParseException {
        // Create Trip T789 (price: 100.0, departure: 2023-12-25 08:00)
        trip = new Trip();
        trip.setPrice(100.0);
        trip.setDepartureDate(sdf.parse("2023-12-25 08:00"));
        trip.setDepartureTime("2023-12-25 08:00");
        
        // Customer C3 has membership with 20% DISCOUNTS award
        customer = new Customer();
        membershipPackage = new MembershipPackage();
        membershipPackage.setAwards(new Award[]{Award.DISCOUNTS});
        customer.setMembershipPackage(membershipPackage);
        
        // Booking made at 2023-12-24 07:00 (25 hours before)
        String bookingTime = "2023-12-24 07:00";
        
        // Expected Output: 80.0
        double result = trip.calculateDiscountedPrice(customer, bookingTime);
        assertEquals(80.0, result, 0.001);
    }

    @Test
    public void testCase2_DiscountDeniedForLateBooking() throws ParseException {
        // Create Trip T999 (price: 200.0, departure: 2023-12-25 12:00)
        trip = new Trip();
        trip.setPrice(200.0);
        trip.setDepartureDate(sdf.parse("2023-12-25 12:00"));
        trip.setDepartureTime("2023-12-25 12:00");
        
        // Customer C4 has membership with 20% DISCOUNTS award
        customer = new Customer();
        membershipPackage = new MembershipPackage();
        membershipPackage.setAwards(new Award[]{Award.DISCOUNTS});
        customer.setMembershipPackage(membershipPackage);
        
        // Booking made at 2023-12-25 10:30 (1.5 hours before)
        String bookingTime = "2023-12-25 10:30";
        
        // Expected Output: 200.0
        double result = trip.calculateDiscountedPrice(customer, bookingTime);
        assertEquals(200.0, result, 0.001);
    }

    @Test
    public void testCase3_Exact24HourBoundaryForDiscount() throws ParseException {
        // Trip T800 (price: 100.0, departure: 2023-12-25 08:00)
        trip = new Trip();
        trip.setPrice(100.0);
        trip.setDepartureDate(sdf.parse("2023-12-25 08:00"));
        trip.setDepartureTime("2023-12-25 08:00");
        
        // Customer has membership with 20% DISCOUNTS award
        customer = new Customer();
        membershipPackage = new MembershipPackage();
        membershipPackage.setAwards(new Award[]{Award.DISCOUNTS});
        customer.setMembershipPackage(membershipPackage);
        
        // Booking made at 2023-12-24 08:00 (exactly 24 hours before)
        String bookingTime = "2023-12-24 08:00";
        
        // Expected Output: 80.0
        double result = trip.calculateDiscountedPrice(customer, bookingTime);
        assertEquals(80.0, result, 0.001);
    }

    @Test
    public void testCase4_NoDiscountWithoutMembership() throws ParseException {
        // Trip T900 (price: 200.0, departure: 2023-12-26 12:00)
        trip = new Trip();
        trip.setPrice(200.0);
        trip.setDepartureDate(sdf.parse("2023-12-26 12:00"));
        trip.setDepartureTime("2023-12-26 12:00");
        
        // Customer has no membership
        customer = new Customer();
        customer.setMembershipPackage(null);
        
        // Booking made 48 hours before (2023-12-24 12:00)
        String bookingTime = "2023-12-24 12:00";
        
        // Expected Output: 200.0
        double result = trip.calculateDiscountedPrice(customer, bookingTime);
        assertEquals(200.0, result, 0.001);
    }

    @Test
    public void testCase5_DiscountAppliesOnlyToEligibleMembershipType() throws ParseException {
        // Trip T950 (price: 150.0)
        trip = new Trip();
        trip.setPrice(150.0);
        trip.setDepartureDate(sdf.parse("2023-12-26 12:00"));
        trip.setDepartureTime("2023-12-26 12:00");
        
        // Customer has membership with CASHBACK only (no DISCOUNTS)
        customer = new Customer();
        membershipPackage = new MembershipPackage();
        membershipPackage.setAwards(new Award[]{Award.CASHBACK});
        customer.setMembershipPackage(membershipPackage);
        
        // Booking made 30 hours before
        String bookingTime = "2023-12-25 06:00"; // 30 hours before departure
        
        // Expected Output: 150.0
        double result = trip.calculateDiscountedPrice(customer, bookingTime);
        assertEquals(150.0, result, 0.001);
    }
}