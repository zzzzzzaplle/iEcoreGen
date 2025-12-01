import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR4Test {
    
    private Customer customer;
    private MembershipPackage membershipPackage;
    
    @Before
    public void setUp() {
        customer = new Customer();
        membershipPackage = new MembershipPackage();
        membershipPackage.setAwards(new Award[]{Award.POINTS});
        customer.setMembershipPackage(membershipPackage);
        customer.setBookings(new ArrayList<>());
    }
    
    @Test
    public void testCase1_PointsCalculationWithMultipleBookings() {
        // Setup: Customer C5 has membership with POINTS award
        Customer c5 = new Customer();
        MembershipPackage mp = new MembershipPackage();
        mp.setAwards(new Award[]{Award.POINTS});
        c5.setMembershipPackage(mp);
        c5.setBookings(new ArrayList<>());
        
        // Create Booking1 (seats:2) for trip with departure in 2023-12
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(2);
        Trip trip1 = new Trip();
        try {
            trip1.setDepartureDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-12-25"));
            booking1.setBookingDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-12-20"));
        } catch (Exception e) {}
        booking1.setCustomer(c5);
        c5.getBookings().add(booking1);
        
        // Create Booking2 (seats:3) for trip with departure in 2023-12
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(3);
        Trip trip2 = new Trip();
        try {
            trip2.setDepartureDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-12-26"));
            booking2.setBookingDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-12-21"));
        } catch (Exception e) {}
        booking2.setCustomer(c5);
        c5.getBookings().add(booking2);
        
        // Compute reward points for current Month: 2023-12
        int result = c5.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 25 (2+3)*5=25
        assertEquals(25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() {
        // Setup: Customer C6 has membership with POINTS award
        Customer c6 = new Customer();
        MembershipPackage mp = new MembershipPackage();
        mp.setAwards(new Award[]{Award.POINTS});
        c6.setMembershipPackage(mp);
        c6.setBookings(new ArrayList<>());
        
        // Create Booking3 (seats:4) for trip with departure in 2024-12 (different year)
        Booking booking3 = new Booking();
        booking3.setNumberOfSeats(4);
        Trip trip3 = new Trip();
        try {
            trip3.setDepartureDate(new SimpleDateFormat("yyyy-MM-dd").parse("2024-12-26"));
            booking3.setBookingDate(new SimpleDateFormat("yyyy-MM-dd").parse("2024-12-20"));
        } catch (Exception e) {}
        booking3.setCustomer(c6);
        c6.getBookings().add(booking3);
        
        // Compute reward points for current Month: 2023-12
        int result = c6.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 0 (booking is in different year)
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() {
        // Setup: Customer C7 has membership with POINTS award
        Customer c7 = new Customer();
        MembershipPackage mp = new MembershipPackage();
        mp.setAwards(new Award[]{Award.POINTS});
        c7.setMembershipPackage(mp);
        c7.setBookings(new ArrayList<>());
        
        // Create Booking1: 2023-11-30 (previous month)
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(2);
        Trip trip1 = new Trip();
        try {
            trip1.setDepartureDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-12-25"));
            booking1.setBookingDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-11-30"));
        } catch (Exception e) {}
        booking1.setCustomer(c7);
        c7.getBookings().add(booking1);
        
        // Create Booking2: 2023-12-01 (current month)
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(3);
        Trip trip2 = new Trip();
        try {
            trip2.setDepartureDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-12-25"));
            booking2.setBookingDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-12-01"));
        } catch (Exception e) {}
        booking2.setCustomer(c7);
        c7.getBookings().add(booking2);
        
        // Compute reward points for current Month: 2023-12
        int result = c7.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 15 (3*5=15, only booking2 counts)
        assertEquals(15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() {
        // Setup: Customer C8 has membership with POINTS award
        Customer c8 = new Customer();
        MembershipPackage mp = new MembershipPackage();
        mp.setAwards(new Award[]{Award.POINTS});
        c8.setMembershipPackage(mp);
        c8.setBookings(new ArrayList<>());
        
        // Create Booking: 2023-12-10 (current month)
        Booking booking = new Booking();
        booking.setNumberOfSeats(2);
        Trip trip = new Trip();
        try {
            trip.setDepartureDate(new SimpleDateFormat("yyyy-MM-dd").parse("2024-03-25"));
            booking.setBookingDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-12-10"));
        } catch (Exception e) {}
        booking.setCustomer(c8);
        c8.getBookings().add(booking);
        
        // Compute reward points for current Month: 2023-12
        int result = c8.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 10 (2*5=10)
        assertEquals(10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() {
        // Setup: Customer C8 has membership with POINTS award
        Customer c8 = new Customer();
        MembershipPackage mp8 = new MembershipPackage();
        mp8.setAwards(new Award[]{Award.POINTS});
        c8.setMembershipPackage(mp8);
        c8.setBookings(new ArrayList<>());
        
        // Create Booking1: 2024-01-10 (current month)
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(50);
        Trip trip1 = new Trip();
        try {
            trip1.setDepartureDate(new SimpleDateFormat("yyyy-MM-dd").parse("2024-05-25"));
            booking1.setBookingDate(new SimpleDateFormat("yyyy-MM-dd").parse("2024-01-10"));
        } catch (Exception e) {}
        booking1.setCustomer(c8);
        c8.getBookings().add(booking1);
        
        // Create Booking2: 2024-01-15 (current month)
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(50);
        Trip trip2 = new Trip();
        try {
            trip2.setDepartureDate(new SimpleDateFormat("yyyy-MM-dd").parse("2024-06-25"));
            booking2.setBookingDate(new SimpleDateFormat("yyyy-MM-dd").parse("2024-01-15"));
        } catch (Exception e) {}
        booking2.setCustomer(c8);
        c8.getBookings().add(booking2);
        
        // Setup: Customer C9 has membership with POINTS award
        Customer c9 = new Customer();
        MembershipPackage mp9 = new MembershipPackage();
        mp9.setAwards(new Award[]{Award.POINTS});
        c9.setMembershipPackage(mp9);
        c9.setBookings(new ArrayList<>());
        
        // Create Booking: 2024-01-10 (current month)
        Booking booking3 = new Booking();
        booking3.setNumberOfSeats(50);
        Trip trip3 = new Trip();
        try {
            trip3.setDepartureDate(new SimpleDateFormat("yyyy-MM-dd").parse("2024-07-25"));
            booking3.setBookingDate(new SimpleDateFormat("yyyy-MM-dd").parse("2024-01-10"));
        } catch (Exception e) {}
        booking3.setCustomer(c9);
        c9.getBookings().add(booking3);
        
        // Compute reward points for C8 (current Month: 2024-01)
        int resultC8 = c8.computeMonthlyRewardPoints("2024-01");
        
        // Compute reward points for C9 (current Month: 2024-01)
        int resultC9 = c9.computeMonthlyRewardPoints("2024-01");
        
        // Expected Output: C8 reward points: 500, C9 reward points: 250
        assertEquals(500, resultC8);
        assertEquals(250, resultC9);
    }
}