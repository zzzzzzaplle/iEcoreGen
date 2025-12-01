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
        User customerC5 = new User();
        customerC5.setId("C5");
        Membership membershipC5 = new Membership();
        membershipC5.setPointsReward(true);
        membershipC5.setHasRewards(true);
        customerC5.setMembership(membershipC5);
        
        // Create trip 1 with booking for C5 (seats:2)
        Trip trip1 = new Trip();
        trip1.setId("T1");
        trip1.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        Booking booking1 = new Booking();
        booking1.setCustomerId("C5");
        booking1.setNumberOfSeats(2);
        booking1.setBookingTime(LocalDateTime.parse("2023-12-24 10:00:00", formatter));
        trip1.addBooking(booking1);
        
        // Create trip 2 with booking for C5 (seats:3)
        Trip trip2 = new Trip();
        trip2.setId("T2");
        trip2.setDepartureTime(LocalDateTime.parse("2023-12-26 12:00:00", formatter));
        Booking booking2 = new Booking();
        booking2.setCustomerId("C5");
        booking2.setNumberOfSeats(3);
        booking2.setBookingTime(LocalDateTime.parse("2023-12-25 10:00:00", formatter));
        trip2.addBooking(booking2);
        
        // Mock the getTripsForCustomer method to return trips with C5 bookings
        // Since getTripsForCustomer is private, we'll create a testable version
        // In a real scenario, we would use reflection or refactor for testability
        // For this test, we'll assume the system is properly configured
        
        // Execute: Compute reward points for Customer C5 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints(customerC5, 2023, 12);
        
        // Verify: Expected output is 25 (2+3)*5=25
        assertEquals(25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() {
        // Setup: Customer C6 has membership with POINTS award
        User customerC6 = new User();
        customerC6.setId("C6");
        Membership membershipC6 = new Membership();
        membershipC6.setPointsReward(true);
        membershipC6.setHasRewards(true);
        customerC6.setMembership(membershipC6);
        
        // Create trip with booking for C6 in wrong year (2024 instead of 2023)
        Trip trip = new Trip();
        trip.setId("T3");
        trip.setDepartureTime(LocalDateTime.parse("2024-12-26 12:00:00", formatter));
        Booking booking = new Booking();
        booking.setCustomerId("C6");
        booking.setNumberOfSeats(4);
        booking.setBookingTime(LocalDateTime.parse("2024-12-25 10:00:00", formatter));
        trip.addBooking(booking);
        
        // Execute: Compute reward points for Customer C6 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints(customerC6, 2023, 12);
        
        // Verify: Expected output is 0 (booking is in wrong year)
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() {
        // Setup: Customer C7 has membership with POINTS award
        User customerC7 = new User();
        customerC7.setId("C7");
        Membership membershipC7 = new Membership();
        membershipC7.setPointsReward(true);
        membershipC7.setHasRewards(true);
        customerC7.setMembership(membershipC7);
        
        // Create trip 1 with booking for C7 in November (should not be counted)
        Trip trip1 = new Trip();
        trip1.setId("T4");
        trip1.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        Booking booking1 = new Booking();
        booking1.setCustomerId("C7");
        booking1.setNumberOfSeats(2);
        booking1.setBookingTime(LocalDateTime.parse("2023-11-30 10:00:00", formatter));
        trip1.addBooking(booking1);
        
        // Create trip 2 with booking for C7 in December (should be counted)
        Trip trip2 = new Trip();
        trip2.setId("T5");
        trip2.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        Booking booking2 = new Booking();
        booking2.setCustomerId("C7");
        booking2.setNumberOfSeats(3);
        booking2.setBookingTime(LocalDateTime.parse("2023-12-01 10:00:00", formatter));
        trip2.addBooking(booking2);
        
        // Execute: Compute reward points for Customer C7 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints(customerC7, 2023, 12);
        
        // Verify: Expected output is 15 (3*5=15)
        assertEquals(15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() {
        // Setup: Customer C8 has membership with POINTS award
        User customerC8 = new User();
        customerC8.setId("C8");
        Membership membershipC8 = new Membership();
        membershipC8.setPointsReward(true);
        membershipC8.setHasRewards(true);
        customerC8.setMembership(membershipC8);
        
        // Create trip with booking for C8 in December
        Trip trip = new Trip();
        trip.setId("T6");
        trip.setDepartureTime(LocalDateTime.parse("2024-03-25 12:00:00", formatter));
        Booking booking = new Booking();
        booking.setCustomerId("C8");
        booking.setNumberOfSeats(2);
        booking.setBookingTime(LocalDateTime.parse("2023-12-10 10:00:00", formatter));
        trip.addBooking(booking);
        
        // Execute: Compute reward points for Customer C8 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints(customerC8, 2023, 12);
        
        // Verify: Expected output is 10 (2*5=10)
        assertEquals(10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() {
        // Setup: Customer C8 has membership with POINTS award
        User customerC8 = new User();
        customerC8.setId("C8");
        Membership membershipC8 = new Membership();
        membershipC8.setPointsReward(true);
        membershipC8.setHasRewards(true);
        customerC8.setMembership(membershipC8);
        
        // Create trip 1 with booking for C8 in January (seats:50)
        Trip trip1 = new Trip();
        trip1.setId("T7");
        trip1.setDepartureTime(LocalDateTime.parse("2024-05-25 12:00:00", formatter));
        Booking booking1 = new Booking();
        booking1.setCustomerId("C8");
        booking1.setNumberOfSeats(50);
        booking1.setBookingTime(LocalDateTime.parse("2024-01-10 10:00:00", formatter));
        trip1.addBooking(booking1);
        
        // Create trip 2 with booking for C8 in January (seats:50)
        Trip trip2 = new Trip();
        trip2.setId("T8");
        trip2.setDepartureTime(LocalDateTime.parse("2024-06-25 12:00:00", formatter));
        Booking booking2 = new Booking();
        booking2.setCustomerId("C8");
        booking2.setNumberOfSeats(50);
        booking2.setBookingTime(LocalDateTime.parse("2024-01-15 10:00:00", formatter));
        trip2.addBooking(booking2);
        
        // Setup: Customer C9 has membership with POINTS award
        User customerC9 = new User();
        customerC9.setId("C9");
        Membership membershipC9 = new Membership();
        membershipC9.setPointsReward(true);
        membershipC9.setHasRewards(true);
        customerC9.setMembership(membershipC9);
        
        // Create trip 3 with booking for C9 in January (seats:50)
        Trip trip3 = new Trip();
        trip3.setId("T9");
        trip3.setDepartureTime(LocalDateTime.parse("2024-07-25 12:00:00", formatter));
        Booking booking3 = new Booking();
        booking3.setCustomerId("C9");
        booking3.setNumberOfSeats(50);
        booking3.setBookingTime(LocalDateTime.parse("2024-01-10 10:00:00", formatter));
        trip3.addBooking(booking3);
        
        // Execute: Compute reward points for Customer C8 (current Month: 2024-01)
        int resultC8 = system.computeMonthlyRewardPoints(customerC8, 2024, 1);
        
        // Execute: Compute reward points for Customer C9 (current Month: 2024-01)
        int resultC9 = system.computeMonthlyRewardPoints(customerC9, 2024, 1);
        
        // Verify: Expected output for C8 is 500 (100 seats * 5 = 500)
        assertEquals(500, resultC8);
        
        // Verify: Expected output for C9 is 250 (50 seats * 5 = 250)
        assertEquals(250, resultC9);
    }
}