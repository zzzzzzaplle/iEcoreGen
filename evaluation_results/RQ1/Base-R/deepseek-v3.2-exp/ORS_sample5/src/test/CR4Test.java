import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private ORSService orsService;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        orsService = new ORSService();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_pointsCalculationWithMultipleBookings() {
        // Setup: Customer C5 has membership with POINTS award
        Customer customerC5 = new Customer();
        Membership membershipC5 = new Membership();
        membershipC5.setRewardType(RewardType.POINTS);
        customerC5.setMembership(membershipC5);
        
        // Create bookings for C5
        Booking booking1 = new Booking();
        booking1.setSeats(2);
        booking1.setBookingTime(LocalDateTime.parse("2023-12-01 10:00:00", formatter));
        
        Booking booking2 = new Booking();
        booking2.setSeats(3);
        booking2.setBookingTime(LocalDateTime.parse("2023-12-15 10:00:00", formatter));
        
        List<Booking> bookingsC5 = new ArrayList<>();
        bookingsC5.add(booking1);
        bookingsC5.add(booking2);
        customerC5.setBookings(bookingsC5);
        
        // Test: Compute reward points for Customer C5 (current Month: 2023-12)
        int result = orsService.computeMonthlyRewardPoints(customerC5, "2023-12");
        
        // Expected Output: 25. (2+3)*5=25
        assertEquals(25, result);
    }
    
    @Test
    public void testCase2_zeroPointsWithExpiredBookings() {
        // Setup: Customer C6 has membership with POINTS award
        Customer customerC6 = new Customer();
        Membership membershipC6 = new Membership();
        membershipC6.setRewardType(RewardType.POINTS);
        customerC6.setMembership(membershipC6);
        
        // Create booking for C6 (booking date is in different month)
        Booking booking3 = new Booking();
        booking3.setSeats(4);
        booking3.setBookingTime(LocalDateTime.parse("2024-01-10 10:00:00", formatter));
        
        List<Booking> bookingsC6 = new ArrayList<>();
        bookingsC6.add(booking3);
        customerC6.setBookings(bookingsC6);
        
        // Test: Compute reward points for Customer C6 (current Month: 2023-12)
        int result = orsService.computeMonthlyRewardPoints(customerC6, "2023-12");
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_partialMonthInclusion() {
        // Setup: Customer C7 has membership with POINTS award
        Customer customerC7 = new Customer();
        Membership membershipC7 = new Membership();
        membershipC7.setRewardType(RewardType.POINTS);
        customerC7.setMembership(membershipC7);
        
        // Create bookings for C7 - one in November, one in December
        Booking booking1 = new Booking();
        booking1.setSeats(2);
        booking1.setBookingTime(LocalDateTime.parse("2023-11-30 10:00:00", formatter));
        
        Booking booking2 = new Booking();
        booking2.setSeats(3);
        booking2.setBookingTime(LocalDateTime.parse("2023-12-01 10:00:00", formatter));
        
        List<Booking> bookingsC7 = new ArrayList<>();
        bookingsC7.add(booking1);
        bookingsC7.add(booking2);
        customerC7.setBookings(bookingsC7);
        
        // Test: Compute reward points for Customer C7 (current Month: 2023-12)
        int result = orsService.computeMonthlyRewardPoints(customerC7, "2023-12");
        
        // Expected Output: 15. 3*5=15
        assertEquals(15, result);
    }
    
    @Test
    public void testCase4_multipleSeatsEdgeCase() {
        // Setup: Customer C8 has membership with POINTS award
        Customer customerC8 = new Customer();
        Membership membershipC8 = new Membership();
        membershipC8.setRewardType(RewardType.POINTS);
        customerC8.setMembership(membershipC8);
        
        // Create booking for C8
        Booking booking = new Booking();
        booking.setSeats(2);
        booking.setBookingTime(LocalDateTime.parse("2023-12-10 10:00:00", formatter));
        
        List<Booking> bookingsC8 = new ArrayList<>();
        bookingsC8.add(booking);
        customerC8.setBookings(bookingsC8);
        
        // Test: Compute reward points for Customer C8 (current Month: 2023-12)
        int result = orsService.computeMonthlyRewardPoints(customerC8, "2023-12");
        
        // Expected Output: 10. 2*5=10
        assertEquals(10, result);
    }
    
    @Test
    public void testCase5_largeSeatQuantity() {
        // Setup Customer C8: has membership with POINTS award
        Customer customerC8 = new Customer();
        Membership membershipC8 = new Membership();
        membershipC8.setRewardType(RewardType.POINTS);
        customerC8.setMembership(membershipC8);
        
        // Create bookings for C8
        Booking booking1 = new Booking();
        booking1.setSeats(50);
        booking1.setBookingTime(LocalDateTime.parse("2024-01-10 10:00:00", formatter));
        
        Booking booking2 = new Booking();
        booking2.setSeats(50);
        booking2.setBookingTime(LocalDateTime.parse("2024-01-15 10:00:00", formatter));
        
        List<Booking> bookingsC8 = new ArrayList<>();
        bookingsC8.add(booking1);
        bookingsC8.add(booking2);
        customerC8.setBookings(bookingsC8);
        
        // Setup Customer C9: has membership with POINTS award
        Customer customerC9 = new Customer();
        Membership membershipC9 = new Membership();
        membershipC9.setRewardType(RewardType.POINTS);
        customerC9.setMembership(membershipC9);
        
        // Create booking for C9
        Booking booking3 = new Booking();
        booking3.setSeats(50);
        booking3.setBookingTime(LocalDateTime.parse("2024-01-10 10:00:00", formatter));
        
        List<Booking> bookingsC9 = new ArrayList<>();
        bookingsC9.add(booking3);
        customerC9.setBookings(bookingsC9);
        
        // Test: Compute reward points for Customer C8 (current Month: 2024-01)
        int resultC8 = orsService.computeMonthlyRewardPoints(customerC8, "2024-01");
        
        // Test: Compute reward points for Customer C9 (current Month: 2024-01)
        int resultC9 = orsService.computeMonthlyRewardPoints(customerC9, "2024-01");
        
        // Expected Output: C8 reward points: 500. C9 reward points: 250.
        assertEquals(500, resultC8);
        assertEquals(250, resultC9);
    }

}