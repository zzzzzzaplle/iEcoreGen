import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    private OnlineRideshareSystem system;
    private User customerC5;
    private User customerC6;
    private User customerC7;
    private User customerC8;
    private User customerC9;
    
    @Before
    public void setUp() {
        system = new OnlineRideshareSystem();
        
        // Create customers with membership having POINTS reward
        customerC5 = createCustomerWithPointsMembership("C5");
        customerC6 = createCustomerWithPointsMembership("C6");
        customerC7 = createCustomerWithPointsMembership("C7");
        customerC8 = createCustomerWithPointsMembership("C8");
        customerC9 = createCustomerWithPointsMembership("C9");
    }
    
    private User createCustomerWithPointsMembership(String userId) {
        User customer = new User();
        customer.setUserId(userId);
        
        Membership membership = new Membership();
        membership.setMembershipId("MEM_" + userId);
        
        Reward reward = new Reward();
        reward.setRewardId("REWARD_" + userId);
        reward.setType(RewardType.POINTS);
        
        membership.setReward(reward);
        customer.setMembership(membership);
        
        return customer;
    }
    
    private Booking createBooking(User customer, LocalDateTime bookingTime, int seats, LocalDateTime departureTime) {
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setSeatsBooked(seats);
        booking.setBookingTime(bookingTime);
        
        Trip trip = new Trip();
        trip.setDepartureTime(departureTime);
        booking.setTrip(trip);
        
        return booking;
    }
    
    @Test
    public void testCase1_PointsCalculationWithMultipleBookings() {
        // Setup: Customer C5 has membership with POINTS award
        // C5 create Booking1 (seats:2) for trip (price: 200.0, departure: 2023-12-25 12:00)
        // C5 create Booking2 (seats:3) for trip (price: 100.0, departure: 2023-12-26 12:00)
        
        LocalDateTime bookingTime1 = LocalDateTime.of(2023, 12, 1, 10, 0);
        LocalDateTime departureTime1 = LocalDateTime.of(2023, 12, 25, 12, 0);
        Booking booking1 = createBooking(customerC5, bookingTime1, 2, departureTime1);
        
        LocalDateTime bookingTime2 = LocalDateTime.of(2023, 12, 2, 10, 0);
        LocalDateTime departureTime2 = LocalDateTime.of(2023, 12, 26, 12, 0);
        Booking booking2 = createBooking(customerC5, bookingTime2, 3, departureTime2);
        
        system.getBookings().add(booking1);
        system.getBookings().add(booking2);
        
        // Test: Compute reward points for Customer C5 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints(customerC5, "2023-12");
        
        // Expected Output: 25. (2+3)*5=25
        assertEquals(25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() {
        // Setup: Customer C6 has membership with POINTS award
        // C6 create Booking3 (seats:4) for trip (price: 100.0, departure: 2024-12-26 12:00)
        
        LocalDateTime bookingTime = LocalDateTime.of(2023, 12, 10, 10, 0);
        LocalDateTime departureTime = LocalDateTime.of(2024, 12, 26, 12, 0);
        Booking booking = createBooking(customerC6, bookingTime, 4, departureTime);
        
        system.getBookings().add(booking);
        
        // Test: Compute reward points for Customer C6 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints(customerC6, "2023-12");
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() {
        // Setup: Customer C7 has membership with POINTS award
        // C7 create Booking1: 2023-11-30 10:00 (seats:2) for trip (price: 200.0, departure: 2023-12-25 12:00)
        // C7 create Booking2: 2023-12-01 10:00 (seats:3) for trip (price: 200.0, departure: 2023-12-25 12:00)
        
        LocalDateTime bookingTime1 = LocalDateTime.of(2023, 11, 30, 10, 0);
        LocalDateTime departureTime1 = LocalDateTime.of(2023, 12, 25, 12, 0);
        Booking booking1 = createBooking(customerC7, bookingTime1, 2, departureTime1);
        
        LocalDateTime bookingTime2 = LocalDateTime.of(2023, 12, 1, 10, 0);
        LocalDateTime departureTime2 = LocalDateTime.of(2023, 12, 25, 12, 0);
        Booking booking2 = createBooking(customerC7, bookingTime2, 3, departureTime2);
        
        system.getBookings().add(booking1);
        system.getBookings().add(booking2);
        
        // Test: Compute reward points for Customer C7 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints(customerC7, "2023-12");
        
        // Expected Output: 15. 3*5=15.
        assertEquals(15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() {
        // Setup: Customer C8 has membership with POINTS award
        // C8 create Booking: 2023-12-10 10:00 (seats:2) for trip (price: 200.0, departure: 2024-03-25 12:00)
        
        LocalDateTime bookingTime = LocalDateTime.of(2023, 12, 10, 10, 0);
        LocalDateTime departureTime = LocalDateTime.of(2024, 3, 25, 12, 0);
        Booking booking = createBooking(customerC8, bookingTime, 2, departureTime);
        
        system.getBookings().add(booking);
        
        // Test: Compute reward points for Customer C8 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints(customerC8, "2023-12");
        
        // Expected Output: 10. 2*5=10
        assertEquals(10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() {
        // Setup: Customer C8 has membership with POINTS award
        // C8 create Booking: 2024-01-10 10:00 (seats:50) for trip (price: 150.0, departure: 2024-05-25 12:00)
        // C8 create Booking: 2024-01-15 10:00 (seats:50) for trip (price: 200.0, departure: 2024-06-25 12:00)
        // C9 create Booking: 2024-01-10 10:00 (seats:50) for trip (price: 200.0, departure: 2024-07-25 12:00)
        
        LocalDateTime bookingTime1 = LocalDateTime.of(2024, 1, 10, 10, 0);
        LocalDateTime departureTime1 = LocalDateTime.of(2024, 5, 25, 12, 0);
        Booking booking1 = createBooking(customerC8, bookingTime1, 50, departureTime1);
        
        LocalDateTime bookingTime2 = LocalDateTime.of(2024, 1, 15, 10, 0);
        LocalDateTime departureTime2 = LocalDateTime.of(2024, 6, 25, 12, 0);
        Booking booking2 = createBooking(customerC8, bookingTime2, 50, departureTime2);
        
        LocalDateTime bookingTime3 = LocalDateTime.of(2024, 1, 10, 10, 0);
        LocalDateTime departureTime3 = LocalDateTime.of(2024, 7, 25, 12, 0);
        Booking booking3 = createBooking(customerC9, bookingTime3, 50, departureTime3);
        
        system.getBookings().add(booking1);
        system.getBookings().add(booking2);
        system.getBookings().add(booking3);
        
        // Test: Compute reward points for Customer C8 (current Month: 2024-01)
        int resultC8 = system.computeMonthlyRewardPoints(customerC8, "2024-01");
        
        // Test: Compute reward points for Customer C9 (current Month: 2024-01)
        int resultC9 = system.computeMonthlyRewardPoints(customerC9, "2024-01");
        
        // Expected Output: C8 reward points: 500. C9 reward points: 250.
        assertEquals("C8 should have 500 points", 500, resultC8);
        assertEquals("C9 should have 250 points", 250, resultC9);
    }

}