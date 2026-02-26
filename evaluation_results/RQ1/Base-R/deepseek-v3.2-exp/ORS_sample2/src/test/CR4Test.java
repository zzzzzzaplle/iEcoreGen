import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private OnlineRideshareSystem system;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        system = new OnlineRideshareSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_PointsCalculationWithMultipleBookings() {
        // Setup
        Customer customerC5 = new Customer();
        Membership membership = new Membership();
        Reward reward = new Reward();
        reward.setRewardType("points");
        membership.setReward(reward);
        customerC5.setMembership(membership);
        
        List<Booking> bookings = new ArrayList<>();
        
        // Booking 1: 2 seats in December 2023
        Booking booking1 = new Booking();
        booking1.setSeatsBooked(2);
        booking1.setBookingTime(LocalDateTime.parse("2023-12-25 10:00:00", formatter));
        bookings.add(booking1);
        
        // Booking 2: 3 seats in December 2023  
        Booking booking2 = new Booking();
        booking2.setSeatsBooked(3);
        booking2.setBookingTime(LocalDateTime.parse("2023-12-26 10:00:00", formatter));
        bookings.add(booking2);
        
        customerC5.setBookings(bookings);
        
        // Execute
        int result = system.computeMonthlyRewardPoints(customerC5, "2023-12");
        
        // Verify
        assertEquals("Total points should be (2+3)*5=25", 25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() {
        // Setup
        Customer customerC6 = new Customer();
        Membership membership = new Membership();
        Reward reward = new Reward();
        reward.setRewardType("points");
        membership.setReward(reward);
        customerC6.setMembership(membership);
        
        List<Booking> bookings = new ArrayList<>();
        
        // Booking in December 2024 (outside target month 2023-12)
        Booking booking3 = new Booking();
        booking3.setSeatsBooked(4);
        booking3.setBookingTime(LocalDateTime.parse("2024-12-26 10:00:00", formatter));
        bookings.add(booking3);
        
        customerC6.setBookings(bookings);
        
        // Execute
        int result = system.computeMonthlyRewardPoints(customerC6, "2023-12");
        
        // Verify
        assertEquals("No points should be earned for bookings outside target month", 0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() {
        // Setup
        Customer customerC7 = new Customer();
        Membership membership = new Membership();
        Reward reward = new Reward();
        reward.setRewardType("points");
        membership.setReward(reward);
        customerC7.setMembership(membership);
        
        List<Booking> bookings = new ArrayList<>();
        
        // Booking 1: November 2023 (outside target month)
        Booking booking1 = new Booking();
        booking1.setSeatsBooked(2);
        booking1.setBookingTime(LocalDateTime.parse("2023-11-30 10:00:00", formatter));
        bookings.add(booking1);
        
        // Booking 2: December 2023 (within target month)
        Booking booking2 = new Booking();
        booking2.setSeatsBooked(3);
        booking2.setBookingTime(LocalDateTime.parse("2023-12-01 10:00:00", formatter));
        bookings.add(booking2);
        
        customerC7.setBookings(bookings);
        
        // Execute
        int result = system.computeMonthlyRewardPoints(customerC7, "2023-12");
        
        // Verify
        assertEquals("Only December bookings should count: 3*5=15", 15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() {
        // Setup
        Customer customerC8 = new Customer();
        Membership membership = new Membership();
        Reward reward = new Reward();
        reward.setRewardType("points");
        membership.setReward(reward);
        customerC8.setMembership(membership);
        
        List<Booking> bookings = new ArrayList<>();
        
        // Booking in December 2023 with 2 seats
        Booking booking = new Booking();
        booking.setSeatsBooked(2);
        booking.setBookingTime(LocalDateTime.parse("2023-12-10 10:00:00", formatter));
        bookings.add(booking);
        
        customerC8.setBookings(bookings);
        
        // Execute
        int result = system.computeMonthlyRewardPoints(customerC8, "2023-12");
        
        // Verify
        assertEquals("Points should be 2*5=10", 10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() {
        // Setup
        Customer customerC8 = new Customer();
        Customer customerC9 = new Customer();
        
        // Setup customer C8
        Membership membershipC8 = new Membership();
        Reward rewardC8 = new Reward();
        rewardC8.setRewardType("points");
        membershipC8.setReward(rewardC8);
        customerC8.setMembership(membershipC8);
        
        List<Booking> bookingsC8 = new ArrayList<>();
        
        // Booking 1 for C8: January 2024, 50 seats
        Booking booking1C8 = new Booking();
        booking1C8.setSeatsBooked(50);
        booking1C8.setBookingTime(LocalDateTime.parse("2024-01-10 10:00:00", formatter));
        bookingsC8.add(booking1C8);
        
        // Booking 2 for C8: January 2024, 50 seats
        Booking booking2C8 = new Booking();
        booking2C8.setSeatsBooked(50);
        booking2C8.setBookingTime(LocalDateTime.parse("2024-01-15 10:00:00", formatter));
        bookingsC8.add(booking2C8);
        
        customerC8.setBookings(bookingsC8);
        
        // Setup customer C9
        Membership membershipC9 = new Membership();
        Reward rewardC9 = new Reward();
        rewardC9.setRewardType("points");
        membershipC9.setReward(rewardC9);
        customerC9.setMembership(membershipC9);
        
        List<Booking> bookingsC9 = new ArrayList<>();
        
        // Booking for C9: January 2024, 50 seats
        Booking bookingC9 = new Booking();
        bookingC9.setSeatsBooked(50);
        bookingC9.setBookingTime(LocalDateTime.parse("2024-01-10 10:00:00", formatter));
        bookingsC9.add(bookingC9);
        
        customerC9.setBookings(bookingsC9);
        
        // Execute
        int resultC8 = system.computeMonthlyRewardPoints(customerC8, "2024-01");
        int resultC9 = system.computeMonthlyRewardPoints(customerC9, "2024-01");
        
        // Verify
        assertEquals("C8 should have (50+50)*5=500 points", 500, resultC8);
        assertEquals("C9 should have 50*5=250 points", 250, resultC9);
    }
}