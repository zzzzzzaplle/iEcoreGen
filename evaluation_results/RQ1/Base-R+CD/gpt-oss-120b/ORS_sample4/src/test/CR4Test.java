import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        customer.setBookings(new ArrayList<>());
    }
    
    @Test
    public void testCase1_PointsCalculationWithMultipleBookings() {
        // Setup: Customer C5 has membership with POINTS award
        Customer c5 = new Customer();
        c5.setMembershipPackage(membershipPackage);
        c5.setBookings(new ArrayList<>());
        
        // Create Booking1 (seats:2) for trip with departure in 2023-12
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(2);
        booking1.setBookingDateTime(LocalDateTime.of(2023, 12, 10, 10, 0));
        
        // Create Booking2 (seats:3) for trip with departure in 2023-12
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(3);
        booking2.setBookingDateTime(LocalDateTime.of(2023, 12, 15, 10, 0));
        
        c5.getBookings().add(booking1);
        c5.getBookings().add(booking2);
        
        // Compute reward points for current month: 2023-12
        int result = c5.computeMonthlyRewardPoints("2023-12");
        
        // Expected: (2+3)*5 = 25
        assertEquals(25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() {
        // Setup: Customer C6 has membership with POINTS award
        Customer c6 = new Customer();
        c6.setMembershipPackage(membershipPackage);
        c6.setBookings(new ArrayList<>());
        
        // Create Booking3 (seats:4) for trip with departure in 2024-12
        Booking booking3 = new Booking();
        booking3.setNumberOfSeats(4);
        booking3.setBookingDateTime(LocalDateTime.of(2023, 11, 10, 10, 0)); // Booking in different month
        
        c6.getBookings().add(booking3);
        
        // Compute reward points for current month: 2023-12
        int result = c6.computeMonthlyRewardPoints("2023-12");
        
        // Expected: 0 (booking not in target month)
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() {
        // Setup: Customer C7 has membership with POINTS award
        Customer c7 = new Customer();
        c7.setMembershipPackage(membershipPackage);
        c7.setBookings(new ArrayList<>());
        
        // Create Booking1: 2023-11-30 10:00 (seats:2) - NOT in target month
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(2);
        booking1.setBookingDateTime(LocalDateTime.of(2023, 11, 30, 10, 0));
        
        // Create Booking2: 2023-12-01 10:00 (seats:3) - IN target month
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(3);
        booking2.setBookingDateTime(LocalDateTime.of(2023, 12, 1, 10, 0));
        
        c7.getBookings().add(booking1);
        c7.getBookings().add(booking2);
        
        // Compute reward points for current month: 2023-12
        int result = c7.computeMonthlyRewardPoints("2023-12");
        
        // Expected: 3*5 = 15 (only booking2 counts)
        assertEquals(15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() {
        // Setup: Customer C8 has membership with POINTS award
        Customer c8 = new Customer();
        c8.setMembershipPackage(membershipPackage);
        c8.setBookings(new ArrayList<>());
        
        // Create Booking: 2023-12-10 10:00 (seats:2) for trip
        Booking booking = new Booking();
        booking.setNumberOfSeats(2);
        booking.setBookingDateTime(LocalDateTime.of(2023, 12, 10, 10, 0));
        
        c8.getBookings().add(booking);
        
        // Compute reward points for current month: 2023-12
        int result = c8.computeMonthlyRewardPoints("2023-12");
        
        // Expected: 2*5 = 10
        assertEquals(10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() {
        // Setup: Customer C8 has membership with POINTS award
        Customer c8 = new Customer();
        c8.setMembershipPackage(membershipPackage);
        c8.setBookings(new ArrayList<>());
        
        // Setup: Customer C9 has membership with POINTS award
        Customer c9 = new Customer();
        c9.setMembershipPackage(membershipPackage);
        c9.setBookings(new ArrayList<>());
        
        // C8 create Booking: 2024-01-10 10:00 (seats:50)
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(50);
        booking1.setBookingDateTime(LocalDateTime.of(2024, 1, 10, 10, 0));
        
        // C8 create Booking: 2024-01-15 10:00 (seats:50)
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(50);
        booking2.setBookingDateTime(LocalDateTime.of(2024, 1, 15, 10, 0));
        
        // C9 create Booking: 2024-01-10 10:00 (seats:50)
        Booking booking3 = new Booking();
        booking3.setNumberOfSeats(50);
        booking3.setBookingDateTime(LocalDateTime.of(2024, 1, 10, 10, 0));
        
        c8.getBookings().add(booking1);
        c8.getBookings().add(booking2);
        c9.getBookings().add(booking3);
        
        // Compute reward points for current month: 2024-01
        int c8Result = c8.computeMonthlyRewardPoints("2024-01");
        int c9Result = c9.computeMonthlyRewardPoints("2024-01");
        
        // Expected: C8 reward points: 500, C9 reward points: 250
        assertEquals(500, c8Result);
        assertEquals(250, c9Result);
    }
    
    @Test
    public void testCase_NoMembershipPackage() {
        // Setup: Customer without membership package
        Customer customerNoMembership = new Customer();
        customerNoMembership.setBookings(new ArrayList<>());
        
        // Create booking in target month
        Booking booking = new Booking();
        booking.setNumberOfSeats(5);
        booking.setBookingDateTime(LocalDateTime.of(2023, 12, 10, 10, 0));
        customerNoMembership.getBookings().add(booking);
        
        // Compute reward points - should return 0 without membership
        int result = customerNoMembership.computeMonthlyRewardPoints("2023-12");
        
        assertEquals(0, result);
    }
    
    @Test
    public void testCase_MembershipWithoutPointsAward() {
        // Setup: Customer with membership but without POINTS award
        Customer customerNoPoints = new Customer();
        customerNoPoints.setBookings(new ArrayList<>());
        
        MembershipPackage noPointsPackage = new MembershipPackage();
        noPointsPackage.setAwards(new Award[]{Award.CASHBACK, Award.DISCOUNTS}); // No POINTS
        customerNoPoints.setMembershipPackage(noPointsPackage);
        
        // Create booking in target month
        Booking booking = new Booking();
        booking.setNumberOfSeats(5);
        booking.setBookingDateTime(LocalDateTime.of(2023, 12, 10, 10, 0));
        customerNoPoints.getBookings().add(booking);
        
        // Compute reward points - should return 0 without POINTS award
        int result = customerNoPoints.computeMonthlyRewardPoints("2023-12");
        
        assertEquals(0, result);
    }
}