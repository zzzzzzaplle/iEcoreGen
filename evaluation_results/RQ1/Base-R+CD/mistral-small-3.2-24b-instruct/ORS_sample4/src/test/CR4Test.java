import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR4Test {
    
    private Customer customer;
    private MembershipPackage membershipPackage;
    private Trip trip1, trip2, trip3;
    private Booking booking1, booking2, booking3, booking4, booking5, booking6;
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
        trip1.setDepartureDate(dateFormat.parse("2023-12-25 12:00:00"));
        
        trip2 = new Trip();
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
        booking2.setBookingDate(dateFormat.parse("2023-12-02 10:00:00"));
        
        // Add bookings to trips
        trip1.getBookings().add(booking1);
        trip2.getBookings().add(booking2);
        
        // Test
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("Total points should be 25", 25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() throws Exception {
        // Setup
        customer = new Customer();
        customer.setMembershipPackage(membershipPackage);
        
        trip1 = new Trip();
        trip1.setDepartureDate(dateFormat.parse("2024-12-26 12:00:00"));
        
        booking1 = new Booking();
        booking1.setCustomer(customer);
        booking1.setTrip(trip1);
        booking1.setNumberOfSeats(4);
        booking1.setBookingDate(dateFormat.parse("2024-12-01 10:00:00"));
        
        // Add booking to trip
        trip1.getBookings().add(booking1);
        
        // Test
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("Points should be 0 for bookings outside current month", 0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() throws Exception {
        // Setup
        customer = new Customer();
        customer.setMembershipPackage(membershipPackage);
        
        trip1 = new Trip();
        trip1.setDepartureDate(dateFormat.parse("2023-12-25 12:00:00"));
        
        // Booking from previous month (should not count)
        booking1 = new Booking();
        booking1.setCustomer(customer);
        booking1.setTrip(trip1);
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(dateFormat.parse("2023-11-30 10:00:00"));
        
        // Booking from current month (should count)
        booking2 = new Booking();
        booking2.setCustomer(customer);
        booking2.setTrip(trip1);
        booking2.setNumberOfSeats(3);
        booking2.setBookingDate(dateFormat.parse("2023-12-01 10:00:00"));
        
        // Add bookings to trip
        trip1.getBookings().add(booking1);
        trip1.getBookings().add(booking2);
        
        // Test
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("Only bookings from current month should count", 15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() throws Exception {
        // Setup
        customer = new Customer();
        customer.setMembershipPackage(membershipPackage);
        
        trip1 = new Trip();
        trip1.setDepartureDate(dateFormat.parse("2024-03-25 12:00:00"));
        
        booking1 = new Booking();
        booking1.setCustomer(customer);
        booking1.setTrip(trip1);
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(dateFormat.parse("2023-12-10 10:00:00"));
        
        // Add booking to trip
        trip1.getBookings().add(booking1);
        
        // Test
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("Points should be 10 for 2 seats", 10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() throws Exception {
        // Setup for customer C8
        Customer customerC8 = new Customer();
        customerC8.setMembershipPackage(membershipPackage);
        
        // Setup for customer C9
        Customer customerC9 = new Customer();
        customerC9.setMembershipPackage(membershipPackage);
        
        trip1 = new Trip();
        trip1.setDepartureDate(dateFormat.parse("2024-05-25 12:00:00"));
        
        trip2 = new Trip();
        trip2.setDepartureDate(dateFormat.parse("2024-06-25 12:00:00"));
        
        trip3 = new Trip();
        trip3.setDepartureDate(dateFormat.parse("2024-07-25 12:00:00"));
        
        // Bookings for C8
        booking1 = new Booking();
        booking1.setCustomer(customerC8);
        booking1.setTrip(trip1);
        booking1.setNumberOfSeats(50);
        booking1.setBookingDate(dateFormat.parse("2024-01-10 10:00:00"));
        
        booking2 = new Booking();
        booking2.setCustomer(customerC8);
        booking2.setTrip(trip2);
        booking2.setNumberOfSeats(50);
        booking2.setBookingDate(dateFormat.parse("2024-01-15 10:00:00"));
        
        // Bookings for C9
        booking3 = new Booking();
        booking3.setCustomer(customerC9);
        booking3.setTrip(trip3);
        booking3.setNumberOfSeats(50);
        booking3.setBookingDate(dateFormat.parse("2024-01-10 10:00:00"));
        
        // Add bookings to trips
        trip1.getBookings().add(booking1);
        trip2.getBookings().add(booking2);
        trip3.getBookings().add(booking3);
        
        // Test C8
        int resultC8 = customerC8.computeMonthlyRewardPoints("2024-01");
        
        // Test C9
        int resultC9 = customerC9.computeMonthlyRewardPoints("2024-01");
        
        // Verify
        assertEquals("C8 should have 500 points", 500, resultC8);
        assertEquals("C9 should have 250 points", 250, resultC9);
    }
}