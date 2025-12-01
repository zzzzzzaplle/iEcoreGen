import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Customer customer;
    private MembershipPackage membershipPackage;
    
    @Before
    public void setUp() {
        customer = new Customer();
        membershipPackage = new MembershipPackage();
        customer.setMembershipPackage(membershipPackage);
        customer.setBookings(new ArrayList<>());
    }
    
    @Test
    public void testCase1_PointsCalculationWithMultipleBookings() throws Exception {
        // Setup: Customer C5 has membership with POINTS award
        membershipPackage.setAwards(new Award[]{Award.POINTS});
        
        // Create Booking1 (seats:2) for trip with departure: 2023-12-25 12:00
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(createDate("2023-12-01 10:00:00"));
        
        // Create Booking2 (seats:3) for trip with departure: 2023-12-26 12:00
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(3);
        booking2.setBookingDate(createDate("2023-12-02 10:00:00"));
        
        // Add bookings to customer
        customer.getBookings().add(booking1);
        customer.getBookings().add(booking2);
        
        // Compute reward points for current Month: 2023-12
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 25. (2+3)*5=25
        assertEquals(25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() throws Exception {
        // Setup: Customer C6 has membership with POINTS award
        membershipPackage.setAwards(new Award[]{Award.POINTS});
        
        // Create Booking3 (seats:4) for trip with departure: 2024-12-26 12:00
        Booking booking3 = new Booking();
        booking3.setNumberOfSeats(4);
        booking3.setBookingDate(createDate("2023-11-01 10:00:00")); // Booking in different month
        
        // Add booking to customer
        customer.getBookings().add(booking3);
        
        // Compute reward points for current Month: 2023-12
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() throws Exception {
        // Setup: Customer C7 has membership with POINTS award
        membershipPackage.setAwards(new Award[]{Award.POINTS});
        
        // Create Booking1: 2023-11-30 10:00 (seats:2)
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(createDate("2023-11-30 10:00:00"));
        
        // Create Booking2: 2023-12-01 10:00 (seats:3)
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(3);
        booking2.setBookingDate(createDate("2023-12-01 10:00:00"));
        
        // Add bookings to customer
        customer.getBookings().add(booking1);
        customer.getBookings().add(booking2);
        
        // Compute reward points for current Month: 2023-12
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 15. 3*5=15
        assertEquals(15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() throws Exception {
        // Setup: Customer C8 has membership with POINTS award
        membershipPackage.setAwards(new Award[]{Award.POINTS});
        
        // Create Booking: 2023-12-10 10:00 (seats:2)
        Booking booking = new Booking();
        booking.setNumberOfSeats(2);
        booking.setBookingDate(createDate("2023-12-10 10:00:00"));
        
        // Add booking to customer
        customer.getBookings().add(booking);
        
        // Compute reward points for current Month: 2023-12
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 10. 2*5=10
        assertEquals(10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() throws Exception {
        // Setup Customer C8
        Customer customer8 = new Customer();
        MembershipPackage mp8 = new MembershipPackage();
        mp8.setAwards(new Award[]{Award.POINTS});
        customer8.setMembershipPackage(mp8);
        customer8.setBookings(new ArrayList<>());
        
        // C8 create Booking: 2024-01-10 10:00 (seats:50)
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(50);
        booking1.setBookingDate(createDate("2024-01-10 10:00:00"));
        
        // C8 create Booking: 2024-01-15 10:00 (seats:50)
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(50);
        booking2.setBookingDate(createDate("2024-01-15 10:00:00"));
        
        // Add bookings to customer C8
        customer8.getBookings().add(booking1);
        customer8.getBookings().add(booking2);
        
        // Setup Customer C9
        Customer customer9 = new Customer();
        MembershipPackage mp9 = new MembershipPackage();
        mp9.setAwards(new Award[]{Award.POINTS});
        customer9.setMembershipPackage(mp9);
        customer9.setBookings(new ArrayList<>());
        
        // C9 create Booking: 2024-01-10 10:00 (seats:50)
        Booking booking3 = new Booking();
        booking3.setNumberOfSeats(50);
        booking3.setBookingDate(createDate("2024-01-10 10:00:00"));
        
        // Add booking to customer C9
        customer9.getBookings().add(booking3);
        
        // Compute reward points for current Month: 2024-01
        int resultC8 = customer8.computeMonthlyRewardPoints("2024-01");
        int resultC9 = customer9.computeMonthlyRewardPoints("2024-01");
        
        // Expected Output: C8 reward points: 500. C9 reward points: 250.
        assertEquals(500, resultC8);
        assertEquals(250, resultC9);
    }
    
    @Test
    public void testCase_NoMembershipPackage() throws Exception {
        // Setup: Customer without membership package
        Customer customerNoMembership = new Customer();
        customerNoMembership.setMembershipPackage(null);
        customerNoMembership.setBookings(new ArrayList<>());
        
        // Add a booking
        Booking booking = new Booking();
        booking.setNumberOfSeats(5);
        booking.setBookingDate(createDate("2023-12-01 10:00:00"));
        customerNoMembership.getBookings().add(booking);
        
        // Compute reward points
        int result = customerNoMembership.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 0 (no membership package)
        assertEquals(0, result);
    }
    
    @Test
    public void testCase_MembershipWithoutPointsAward() throws Exception {
        // Setup: Customer with membership but without POINTS award
        membershipPackage.setAwards(new Award[]{Award.CASHBACK, Award.DISCOUNTS});
        
        // Add a booking
        Booking booking = new Booking();
        booking.setNumberOfSeats(5);
        booking.setBookingDate(createDate("2023-12-01 10:00:00"));
        customer.getBookings().add(booking);
        
        // Compute reward points
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 0 (membership doesn't have POINTS award)
        assertEquals(0, result);
    }
    
    @Test
    public void testCase_NoBookings() throws Exception {
        // Setup: Customer with POINTS award but no bookings
        membershipPackage.setAwards(new Award[]{Award.POINTS});
        
        // Compute reward points
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 0 (no bookings)
        assertEquals(0, result);
    }
    
    /**
     * Helper method to create Date objects from string
     */
    private Date createDate(String dateString) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(dateString);
    }
}