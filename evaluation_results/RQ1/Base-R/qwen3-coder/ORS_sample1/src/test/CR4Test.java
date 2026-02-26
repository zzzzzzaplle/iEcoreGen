import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        // Setup: Customer C5 has membership with POINTS award
        Customer customerC5 = new Customer();
        Membership membershipC5 = new Membership();
        membershipC5.setRewardType(RewardType.POINTS);
        customerC5.setMembership(membershipC5);
        
        // Create Booking1 (seats:2) for trip (price: 200.0, departure: 2023-12-25 12:00)
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(2);
        booking1.setBookingTime(LocalDateTime.parse("2023-12-01 10:00:00", formatter));
        customerC5.addBooking(booking1);
        
        // Create Booking2 (seats:3) for trip (price: 100.0, departure: 2023-12-26 12:00)
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(3);
        booking2.setBookingTime(LocalDateTime.parse("2023-12-02 10:00:00", formatter));
        customerC5.addBooking(booking2);
        
        // Input: Compute reward points for Customer C5 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints(customerC5, 2023, 12);
        
        // Expected Output: 25. (2+3)*5=25
        assertEquals("Points calculation with multiple bookings should return 25", 25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() {
        // Setup: Customer C6 has membership with POINTS award
        Customer customerC6 = new Customer();
        Membership membershipC6 = new Membership();
        membershipC6.setRewardType(RewardType.POINTS);
        customerC6.setMembership(membershipC6);
        
        // C6 create Booking3 (seats:4) for trip (price: 100.0, departure: 2024-12-26 12:00)
        Booking booking3 = new Booking();
        booking3.setNumberOfSeats(4);
        booking3.setBookingTime(LocalDateTime.parse("2024-01-01 10:00:00", formatter));
        customerC6.addBooking(booking3);
        
        // Input: Compute reward points for Customer C6 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints(customerC6, 2023, 12);
        
        // Expected Output: 0
        assertEquals("Bookings from different year should return 0 points", 0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() {
        // Setup: Customer C7 has membership with POINTS award
        Customer customerC7 = new Customer();
        Membership membershipC7 = new Membership();
        membershipC7.setRewardType(RewardType.POINTS);
        customerC7.setMembership(membershipC7);
        
        // C7 create Booking1: 2023-11-30 10:00 (seats:2) for trip (price: 200.0, departure: 2023-12-25 12:00)
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(2);
        booking1.setBookingTime(LocalDateTime.parse("2023-11-30 10:00:00", formatter));
        customerC7.addBooking(booking1);
        
        // C7 create Booking2: 2023-12-01 10:00 (seats:3) for trip (price: 200.0, departure: 2023-12-25 12:00)
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(3);
        booking2.setBookingTime(LocalDateTime.parse("2023-12-01 10:00:00", formatter));
        customerC7.addBooking(booking2);
        
        // Input: Compute reward points for Customer C7 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints(customerC7, 2023, 12);
        
        // Expected Output: 15. 3*5=15
        assertEquals("Only December bookings should be counted", 15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() {
        // Setup: Customer C8 has membership with POINTS award
        Customer customerC8 = new Customer();
        Membership membershipC8 = new Membership();
        membershipC8.setRewardType(RewardType.POINTS);
        customerC8.setMembership(membershipC8);
        
        // C8 create Booking: 2023-12-10 10:00 (seats:2) for trip (price: 200.0, departure: 2024-03-25 12:00)
        Booking booking = new Booking();
        booking.setNumberOfSeats(2);
        booking.setBookingTime(LocalDateTime.parse("2023-12-10 10:00:00", formatter));
        customerC8.addBooking(booking);
        
        // Input: Compute reward points for Customer C8 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints(customerC8, 2023, 12);
        
        // Expected Output: 10. 2*5=10
        assertEquals("Single booking with 2 seats should return 10 points", 10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() {
        // Setup Customer C8
        Customer customerC8 = new Customer();
        Membership membershipC8 = new Membership();
        membershipC8.setRewardType(RewardType.POINTS);
        customerC8.setMembership(membershipC8);
        
        // C8 create Booking: 2024-01-10 10:00 (seats:50) for trip (price: 150.0, departure: 2024-05-25 12:00)
        Booking booking1C8 = new Booking();
        booking1C8.setNumberOfSeats(50);
        booking1C8.setBookingTime(LocalDateTime.parse("2024-01-10 10:00:00", formatter));
        customerC8.addBooking(booking1C8);
        
        // C8 create Booking: 2024-01-15 10:00 (seats:50) for trip (price: 200.0, departure: 2024-06-25 12:00)
        Booking booking2C8 = new Booking();
        booking2C8.setNumberOfSeats(50);
        booking2C8.setBookingTime(LocalDateTime.parse("2024-01-15 10:00:00", formatter));
        customerC8.addBooking(booking2C8);
        
        // Setup Customer C9
        Customer customerC9 = new Customer();
        Membership membershipC9 = new Membership();
        membershipC9.setRewardType(RewardType.POINTS);
        customerC9.setMembership(membershipC9);
        
        // C9 create Booking: 2024-01-10 10:00 (seats:50) for trip (price: 200.0, departure: 2024-07-25 12:00)
        Booking bookingC9 = new Booking();
        bookingC9.setNumberOfSeats(50);
        bookingC9.setBookingTime(LocalDateTime.parse("2024-01-10 10:00:00", formatter));
        customerC9.addBooking(bookingC9);
        
        // Input: Compute reward points for Customer C8 (current Month: 2024-01)
        int resultC8 = system.computeMonthlyRewardPoints(customerC8, 2024, 1);
        
        // Input: Compute reward points for Customer C9 (current Month: 2024-01)
        int resultC9 = system.computeMonthlyRewardPoints(customerC9, 2024, 1);
        
        // Expected Output: C8 reward points: 500. C9 reward points: 250.
        assertEquals("C8 should have 500 points (100 seats * 5)", 500, resultC8);
        assertEquals("C9 should have 250 points (50 seats * 5)", 250, resultC9);
    }

}