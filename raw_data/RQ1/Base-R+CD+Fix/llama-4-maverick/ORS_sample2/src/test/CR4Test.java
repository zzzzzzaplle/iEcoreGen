import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR4Test {
    
    private Customer customer;
    private MembershipPackage membershipPackage;
    
    @Before
    public void setUp() {
        customer = new Customer();
        membershipPackage = new MembershipPackage();
        membershipPackage.setAwards(new Award[]{Award.POINTS});
        customer.setMembershipPackage(membershipPackage);
    }
    
    @Test
    public void testCase1_PointsCalculationWithMultipleBookings() throws Exception {
        // Setup: Customer C5 has membership with POINTS award
        Customer c5 = new Customer();
        MembershipPackage mp = new MembershipPackage();
        mp.setAwards(new Award[]{Award.POINTS});
        c5.setMembershipPackage(mp);
        
        // Create bookings for C5
        List<Booking> bookings = new ArrayList<>();
        
        // Booking1: seats:2, booking date in December 2023
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(2);
        booking1.setCustomer(c5);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        booking1.setBookingDate(sdf.parse("2023-12-01"));
        bookings.add(booking1);
        
        // Booking2: seats:3, booking date in December 2023
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(3);
        booking2.setCustomer(c5);
        booking2.setBookingDate(sdf.parse("2023-12-15"));
        bookings.add(booking2);
        
        // Mock getBookings method to return our test bookings
        java.lang.reflect.Field bookingsField = Customer.class.getDeclaredField("bookings");
        bookingsField.setAccessible(true);
        bookingsField.set(c5, bookings);
        
        // Test: Compute reward points for current month 2023-12
        int result = c5.computeMonthlyRewardPoints("2023-12");
        
        // Expected: (2+3)*5=25
        assertEquals(25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() throws Exception {
        // Setup: Customer C6 has membership with POINTS award
        Customer c6 = new Customer();
        MembershipPackage mp = new MembershipPackage();
        mp.setAwards(new Award[]{Award.POINTS});
        c6.setMembershipPackage(mp);
        
        // Create booking for C6 with booking date in future month
        List<Booking> bookings = new ArrayList<>();
        
        Booking booking3 = new Booking();
        booking3.setNumberOfSeats(4);
        booking3.setCustomer(c6);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        booking3.setBookingDate(sdf.parse("2024-12-26")); // Booking date in December 2024
        bookings.add(booking3);
        
        // Mock getBookings method
        java.lang.reflect.Field bookingsField = Customer.class.getDeclaredField("bookings");
        bookingsField.setAccessible(true);
        bookingsField.set(c6, bookings);
        
        // Test: Compute reward points for current month 2023-12
        int result = c6.computeMonthlyRewardPoints("2023-12");
        
        // Expected: 0 (booking date not in target month)
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() throws Exception {
        // Setup: Customer C7 has membership with POINTS award
        Customer c7 = new Customer();
        MembershipPackage mp = new MembershipPackage();
        mp.setAwards(new Award[]{Award.POINTS});
        c7.setMembershipPackage(mp);
        
        // Create bookings for C7
        List<Booking> bookings = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        // Booking1: booking date in November 2023 (should not be counted)
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(2);
        booking1.setCustomer(c7);
        booking1.setBookingDate(sdf.parse("2023-11-30"));
        bookings.add(booking1);
        
        // Booking2: booking date in December 2023 (should be counted)
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(3);
        booking2.setCustomer(c7);
        booking2.setBookingDate(sdf.parse("2023-12-01"));
        bookings.add(booking2);
        
        // Mock getBookings method
        java.lang.reflect.Field bookingsField = Customer.class.getDeclaredField("bookings");
        bookingsField.setAccessible(true);
        bookingsField.set(c7, bookings);
        
        // Test: Compute reward points for current month 2023-12
        int result = c7.computeMonthlyRewardPoints("2023-12");
        
        // Expected: 3*5=15 (only December booking counts)
        assertEquals(15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() throws Exception {
        // Setup: Customer C8 has membership with POINTS award
        Customer c8 = new Customer();
        MembershipPackage mp = new MembershipPackage();
        mp.setAwards(new Award[]{Award.POINTS});
        c8.setMembershipPackage(mp);
        
        // Create booking for C8
        List<Booking> bookings = new ArrayList<>();
        
        Booking booking = new Booking();
        booking.setNumberOfSeats(2);
        booking.setCustomer(c8);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        booking.setBookingDate(sdf.parse("2023-12-10")); // Booking date in December 2023
        bookings.add(booking);
        
        // Mock getBookings method
        java.lang.reflect.Field bookingsField = Customer.class.getDeclaredField("bookings");
        bookingsField.setAccessible(true);
        bookingsField.set(c8, bookings);
        
        // Test: Compute reward points for current month 2023-12
        int result = c8.computeMonthlyRewardPoints("2023-12");
        
        // Expected: 2*5=10
        assertEquals(10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() throws Exception {
        // Setup Customer C8
        Customer c8 = new Customer();
        MembershipPackage mp8 = new MembershipPackage();
        mp8.setAwards(new Award[]{Award.POINTS});
        c8.setMembershipPackage(mp8);
        
        // Create bookings for C8
        List<Booking> bookings8 = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(50);
        booking1.setCustomer(c8);
        booking1.setBookingDate(sdf.parse("2024-01-10"));
        bookings8.add(booking1);
        
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(50);
        booking2.setCustomer(c8);
        booking2.setBookingDate(sdf.parse("2024-01-15"));
        bookings8.add(booking2);
        
        // Mock getBookings method for C8
        java.lang.reflect.Field bookingsField = Customer.class.getDeclaredField("bookings");
        bookingsField.setAccessible(true);
        bookingsField.set(c8, bookings8);
        
        // Setup Customer C9
        Customer c9 = new Customer();
        MembershipPackage mp9 = new MembershipPackage();
        mp9.setAwards(new Award[]{Award.POINTS});
        c9.setMembershipPackage(mp9);
        
        // Create booking for C9
        List<Booking> bookings9 = new ArrayList<>();
        
        Booking booking3 = new Booking();
        booking3.setNumberOfSeats(50);
        booking3.setCustomer(c9);
        booking3.setBookingDate(sdf.parse("2024-01-10"));
        bookings9.add(booking3);
        
        // Mock getBookings method for C9
        bookingsField.set(c9, bookings9);
        
        // Test: Compute reward points for current month 2024-01
        int resultC8 = c8.computeMonthlyRewardPoints("2024-01");
        int resultC9 = c9.computeMonthlyRewardPoints("2024-01");
        
        // Expected: C8: (50+50)*5=500, C9: 50*5=250
        assertEquals(500, resultC8);
        assertEquals(250, resultC9);
    }
}