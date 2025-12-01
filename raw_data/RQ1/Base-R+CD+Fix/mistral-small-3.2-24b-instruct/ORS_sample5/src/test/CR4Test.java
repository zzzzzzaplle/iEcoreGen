import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR4Test {
    private Customer customer;
    private Trip trip1;
    private Trip trip2;
    private Booking booking1;
    private Booking booking2;
    private MembershipPackage membershipPackage;
    private SimpleDateFormat dateFormat;

    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        membershipPackage = new MembershipPackage();
        membershipPackage.setAwards(new Award[]{Award.POINTS});
    }

    @Test
    public void testCase1_PointsCalculationWithMultipleBookings() throws Exception {
        // Setup
        customer = new Customer();
        customer.setMembershipPackage(membershipPackage);
        
        trip1 = new Trip();
        trip1.setPrice(200.0);
        trip1.setDepartureDate(dateFormat.parse("2023-12-25 12:00:00"));
        
        trip2 = new Trip();
        trip2.setPrice(100.0);
        trip2.setDepartureDate(dateFormat.parse("2023-12-26 12:00:00"));
        
        booking1 = new Booking();
        booking1.setCustomer(customer);
        booking1.setTrip(trip1);
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(dateFormat.parse("2023-12-01 10:00:00"));
        
        booking2 = new Booking();
        booking2.setCustomer(customer);
        booking2.setTrip(trip2);
        booking2.setNumberOfSeats(3);
        booking2.setBookingDate(dateFormat.parse("2023-12-01 10:00:00"));
        
        trip1.setBookings(java.util.Arrays.asList(booking1));
        trip2.setBookings(java.util.Arrays.asList(booking2));
        
        // Execute
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("Total points should be (2+3)*5=25", 25, result);
    }

    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() throws Exception {
        // Setup
        customer = new Customer();
        customer.setMembershipPackage(membershipPackage);
        
        trip1 = new Trip();
        trip1.setPrice(100.0);
        trip1.setDepartureDate(dateFormat.parse("2024-12-26 12:00:00"));
        
        booking1 = new Booking();
        booking1.setCustomer(customer);
        booking1.setTrip(trip1);
        booking1.setNumberOfSeats(4);
        booking1.setBookingDate(dateFormat.parse("2024-12-01 10:00:00"));
        
        trip1.setBookings(java.util.Arrays.asList(booking1));
        
        // Execute
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("Points should be 0 for bookings outside target month", 0, result);
    }

    @Test
    public void testCase3_PartialMonthInclusion() throws Exception {
        // Setup
        customer = new Customer();
        customer.setMembershipPackage(membershipPackage);
        
        trip1 = new Trip();
        trip1.setPrice(200.0);
        trip1.setDepartureDate(dateFormat.parse("2023-12-25 12:00:00"));
        
        booking1 = new Booking();
        booking1.setCustomer(customer);
        booking1.setTrip(trip1);
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(dateFormat.parse("2023-11-30 10:00:00"));
        
        booking2 = new Booking();
        booking2.setCustomer(customer);
        booking2.setTrip(trip1);
        booking2.setNumberOfSeats(3);
        booking2.setBookingDate(dateFormat.parse("2023-12-01 10:00:00"));
        
        trip1.setBookings(java.util.Arrays.asList(booking1, booking2));
        
        // Execute
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("Only December bookings should count: 3*5=15", 15, result);
    }

    @Test
    public void testCase4_MultipleSeatsEdgeCase() throws Exception {
        // Setup
        customer = new Customer();
        customer.setMembershipPackage(membershipPackage);
        
        trip1 = new Trip();
        trip1.setPrice(200.0);
        trip1.setDepartureDate(dateFormat.parse("2024-03-25 12:00:00"));
        
        booking1 = new Booking();
        booking1.setCustomer(customer);
        booking1.setTrip(trip1);
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(dateFormat.parse("2023-12-10 10:00:00"));
        
        trip1.setBookings(java.util.Arrays.asList(booking1));
        
        // Execute
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("Points should be 2*5=10", 10, result);
    }

    @Test
    public void testCase5_LargeSeatQuantity() throws Exception {
        // Setup customers
        Customer customer8 = new Customer();
        customer8.setMembershipPackage(membershipPackage);
        
        Customer customer9 = new Customer();
        customer9.setMembershipPackage(membershipPackage);
        
        // Setup trips
        Trip trip1 = new Trip();
        trip1.setPrice(150.0);
        trip1.setDepartureDate(dateFormat.parse("2024-05-25 12:00:00"));
        
        Trip trip2 = new Trip();
        trip2.setPrice(200.0);
        trip2.setDepartureDate(dateFormat.parse("2024-06-25 12:00:00"));
        
        Trip trip3 = new Trip();
        trip3.setPrice(200.0);
        trip3.setDepartureDate(dateFormat.parse("2024-07-25 12:00:00"));
        
        // Setup bookings for customer8
        Booking booking1 = new Booking();
        booking1.setCustomer(customer8);
        booking1.setTrip(trip1);
        booking1.setNumberOfSeats(50);
        booking1.setBookingDate(dateFormat.parse("2024-01-10 10:00:00"));
        
        Booking booking2 = new Booking();
        booking2.setCustomer(customer8);
        booking2.setTrip(trip2);
        booking2.setNumberOfSeats(50);
        booking2.setBookingDate(dateFormat.parse("2024-01-15 10:00:00"));
        
        // Setup booking for customer9
        Booking booking3 = new Booking();
        booking3.setCustomer(customer9);
        booking3.setTrip(trip3);
        booking3.setNumberOfSeats(50);
        booking3.setBookingDate(dateFormat.parse("2024-01-10 10:00:00"));
        
        trip1.setBookings(java.util.Arrays.asList(booking1));
        trip2.setBookings(java.util.Arrays.asList(booking2));
        trip3.setBookings(java.util.Arrays.asList(booking3));
        
        // Execute
        int result8 = customer8.computeMonthlyRewardPoints("2024-01");
        int result9 = customer9.computeMonthlyRewardPoints("2024-01");
        
        // Verify
        assertEquals("Customer8 points should be (50+50)*5=500", 500, result8);
        assertEquals("Customer9 points should be 50*5=250", 250, result9);
    }
}