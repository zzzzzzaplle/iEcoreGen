import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.YearMonth;

public class CR4Test {
    
    private Customer customerC5;
    private Customer customerC6;
    private Customer customerC7;
    private Customer customerC8;
    private Customer customerC9;
    
    @Before
    public void setUp() {
        // Initialize customers and their memberships
        customerC5 = new Customer();
        Membership membershipC5 = new Membership();
        membershipC5.setActive(true);
        membershipC5.setRewardType(RewardType.POINTS);
        customerC5.setMembership(membershipC5);
        
        customerC6 = new Customer();
        Membership membershipC6 = new Membership();
        membershipC6.setActive(true);
        membershipC6.setRewardType(RewardType.POINTS);
        customerC6.setMembership(membershipC6);
        
        customerC7 = new Customer();
        Membership membershipC7 = new Membership();
        membershipC7.setActive(true);
        membershipC7.setRewardType(RewardType.POINTS);
        customerC7.setMembership(membershipC7);
        
        customerC8 = new Customer();
        Membership membershipC8 = new Membership();
        membershipC8.setActive(true);
        membershipC8.setRewardType(RewardType.POINTS);
        customerC8.setMembership(membershipC8);
        
        customerC9 = new Customer();
        Membership membershipC9 = new Membership();
        membershipC9.setActive(true);
        membershipC9.setRewardType(RewardType.POINTS);
        customerC9.setMembership(membershipC9);
    }
    
    @Test
    public void testCase1_PointsCalculationWithMultipleBookings() {
        // Setup: Create bookings for customer C5 in December 2023
        Booking booking1 = new Booking();
        booking1.setSeatsBooked(2);
        booking1.setBookingDateTime(LocalDateTime.of(2023, 12, 10, 10, 0)); // Any date in Dec 2023
        customerC5.getBookings().add(booking1);
        
        Booking booking2 = new Booking();
        booking2.setSeatsBooked(3);
        booking2.setBookingDateTime(LocalDateTime.of(2023, 12, 15, 10, 0)); // Any date in Dec 2023
        customerC5.getBookings().add(booking2);
        
        // Test: Compute points for December 2023
        YearMonth targetMonth = YearMonth.of(2023, 12);
        int result = ORSService.computeMonthlyRewardPoints(customerC5, targetMonth);
        
        // Verify: (2 + 3) * 5 = 25 points
        assertEquals("Points should be 25 for 5 seats booked in December 2023", 25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() {
        // Setup: Create booking for customer C6 in December 2024 (not in target month)
        Booking booking3 = new Booking();
        booking3.setSeatsBooked(4);
        booking3.setBookingDateTime(LocalDateTime.of(2024, 12, 26, 10, 0)); // December 2024
        customerC6.getBookings().add(booking3);
        
        // Test: Compute points for December 2023
        YearMonth targetMonth = YearMonth.of(2023, 12);
        int result = ORSService.computeMonthlyRewardPoints(customerC6, targetMonth);
        
        // Verify: 0 points since booking is not in target month
        assertEquals("Points should be 0 for bookings outside target month", 0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() {
        // Setup: Create bookings for customer C7 - one in November, one in December
        Booking booking1 = new Booking();
        booking1.setSeatsBooked(2);
        booking1.setBookingDateTime(LocalDateTime.of(2023, 11, 30, 10, 0)); // November 2023
        customerC7.getBookings().add(booking1);
        
        Booking booking2 = new Booking();
        booking2.setSeatsBooked(3);
        booking2.setBookingDateTime(LocalDateTime.of(2023, 12, 1, 10, 0)); // December 2023
        customerC7.getBookings().add(booking2);
        
        // Test: Compute points for December 2023
        YearMonth targetMonth = YearMonth.of(2023, 12);
        int result = ORSService.computeMonthlyRewardPoints(customerC7, targetMonth);
        
        // Verify: Only December booking counts: 3 * 5 = 15 points
        assertEquals("Points should be 15 for December booking only", 15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() {
        // Setup: Create booking for customer C8 in December 2023
        Booking booking = new Booking();
        booking.setSeatsBooked(2);
        booking.setBookingDateTime(LocalDateTime.of(2023, 12, 10, 10, 0)); // December 2023
        customerC8.getBookings().add(booking);
        
        // Test: Compute points for December 2023
        YearMonth targetMonth = YearMonth.of(2023, 12);
        int result = ORSService.computeMonthlyRewardPoints(customerC8, targetMonth);
        
        // Verify: 2 * 5 = 10 points
        assertEquals("Points should be 10 for 2 seats booked in December 2023", 10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() {
        // Setup: Create bookings for customer C8 in January 2024
        Booking booking1 = new Booking();
        booking1.setSeatsBooked(50);
        booking1.setBookingDateTime(LocalDateTime.of(2024, 1, 10, 10, 0)); // January 2024
        customerC8.getBookings().add(booking1);
        
        Booking booking2 = new Booking();
        booking2.setSeatsBooked(50);
        booking2.setBookingDateTime(LocalDateTime.of(2024, 1, 15, 10, 0)); // January 2024
        customerC8.getBookings().add(booking2);
        
        // Setup: Create booking for customer C9 in January 2024
        Booking booking3 = new Booking();
        booking3.setSeatsBooked(50);
        booking3.setBookingDateTime(LocalDateTime.of(2024, 1, 10, 10, 0)); // January 2024
        customerC9.getBookings().add(booking3);
        
        // Test: Compute points for January 2024 for both customers
        YearMonth targetMonth = YearMonth.of(2024, 1);
        int resultC8 = ORSService.computeMonthlyRewardPoints(customerC8, targetMonth);
        int resultC9 = ORSService.computeMonthlyRewardPoints(customerC9, targetMonth);
        
        // Verify: C8: (50 + 50) * 5 = 500 points, C9: 50 * 5 = 250 points
        assertEquals("Customer C8 should have 500 points", 500, resultC8);
        assertEquals("Customer C9 should have 250 points", 250, resultC9);
    }
}