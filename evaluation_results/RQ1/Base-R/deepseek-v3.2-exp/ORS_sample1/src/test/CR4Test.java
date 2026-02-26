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
        User customerC5 = createUserWithPointsMembership("C5");
        system.getUsers().add(customerC5);
        
        // Create trips
        Trip trip1 = createTrip("T1", 200.0, "2023-12-25 12:00:00");
        Trip trip2 = createTrip("T2", 100.0, "2023-12-26 12:00:00");
        system.getTrips().add(trip1);
        system.getTrips().add(trip2);
        
        // Create bookings for C5
        Booking booking1 = createBooking("B1", customerC5, trip1, 2, "2023-12-01 10:00:00");
        Booking booking2 = createBooking("B2", customerC5, trip2, 3, "2023-12-02 10:00:00");
        system.getBookings().add(booking1);
        system.getBookings().add(booking2);
        
        // Test: Compute reward points for Customer C5 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints(customerC5, "2023-12");
        
        // Verify: Expected Output: 25. (2+3)*5=25
        assertEquals(25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() {
        // Setup: Customer C6 has membership with POINTS award
        User customerC6 = createUserWithPointsMembership("C6");
        system.getUsers().add(customerC6);
        
        // Create trip with future departure
        Trip trip3 = createTrip("T3", 100.0, "2024-12-26 12:00:00");
        system.getTrips().add(trip3);
        
        // Create booking for C6 with booking date in target month but trip in different year
        Booking booking3 = createBooking("B3", customerC6, trip3, 4, "2023-12-01 10:00:00");
        system.getBookings().add(booking3);
        
        // Test: Compute reward points for Customer C6 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints(customerC6, "2023-12");
        
        // Verify: Expected Output: 0 (booking is in target month but trip departure irrelevant for points calculation)
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() {
        // Setup: Customer C7 has membership with POINTS award
        User customerC7 = createUserWithPointsMembership("C7");
        system.getUsers().add(customerC7);
        
        // Create trips
        Trip trip1 = createTrip("T1", 200.0, "2023-12-25 12:00:00");
        Trip trip2 = createTrip("T2", 200.0, "2023-12-25 12:00:00");
        system.getTrips().add(trip1);
        system.getTrips().add(trip2);
        
        // Create bookings: one in November, one in December
        Booking booking1 = createBooking("B1", customerC7, trip1, 2, "2023-11-30 10:00:00");
        Booking booking2 = createBooking("B2", customerC7, trip2, 3, "2023-12-01 10:00:00");
        system.getBookings().add(booking1);
        system.getBookings().add(booking2);
        
        // Test: Compute reward points for Customer C7 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints(customerC7, "2023-12");
        
        // Verify: Expected Output: 15. 3*5=15 (only December booking counts)
        assertEquals(15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() {
        // Setup: Customer C8 has membership with POINTS award
        User customerC8 = createUserWithPointsMembership("C8");
        system.getUsers().add(customerC8);
        
        // Create trip with future departure
        Trip trip = createTrip("T1", 200.0, "2024-03-25 12:00:00");
        system.getTrips().add(trip);
        
        // Create booking for C8 in target month
        Booking booking = createBooking("B1", customerC8, trip, 2, "2023-12-10 10:00:00");
        system.getBookings().add(booking);
        
        // Test: Compute reward points for Customer C8 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints(customerC8, "2023-12");
        
        // Verify: Expected Output: 10. 2*5=10
        assertEquals(10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() {
        // Setup: Customers C8 and C9 have membership with POINTS award
        User customerC8 = createUserWithPointsMembership("C8");
        User customerC9 = createUserWithPointsMembership("C9");
        system.getUsers().add(customerC8);
        system.getUsers().add(customerC9);
        
        // Create trips
        Trip trip1 = createTrip("T1", 150.0, "2024-05-25 12:00:00");
        Trip trip2 = createTrip("T2", 200.0, "2024-06-25 12:00:00");
        Trip trip3 = createTrip("T3", 200.0, "2024-07-25 12:00:00");
        system.getTrips().add(trip1);
        system.getTrips().add(trip2);
        system.getTrips().add(trip3);
        
        // Create bookings for C8 and C9
        Booking booking1 = createBooking("B1", customerC8, trip1, 50, "2024-01-10 10:00:00");
        Booking booking2 = createBooking("B2", customerC8, trip2, 50, "2024-01-15 10:00:00");
        Booking booking3 = createBooking("B3", customerC9, trip3, 50, "2024-01-10 10:00:00");
        system.getBookings().add(booking1);
        system.getBookings().add(booking2);
        system.getBookings().add(booking3);
        
        // Test: Compute reward points for Customer C8 (current Month: 2024-01)
        int resultC8 = system.computeMonthlyRewardPoints(customerC8, "2024-01");
        
        // Test: Compute reward points for Customer C9 (current Month: 2024-01)
        int resultC9 = system.computeMonthlyRewardPoints(customerC9, "2024-01");
        
        // Verify: Expected Output: C8 reward points: 500. C9 reward points: 250.
        assertEquals(500, resultC8); // (50 + 50) * 5 = 500
        assertEquals(250, resultC9); // 50 * 5 = 250
    }
    // Helper methods to create test objects
    private User createUserWithPointsMembership(String userId) {
        User user = new User();
        user.setId(userId);
        
        Membership membership = new Membership();
        Reward reward = new Reward();
        reward.setRewardType("points");
        membership.setReward(reward);
        user.setMembership(membership);
        
        return user;
    }
    
    private Trip createTrip(String tripId, double price, String departureTimeStr) {
        Trip trip = new Trip();
        trip.setId(tripId);
        trip.setPrice(price);
        trip.setDepartureTime(LocalDateTime.parse(departureTimeStr, formatter));
        
        // Set arrival time as 2 hours after departure for simplicity
        trip.setArrivalTime(trip.getDepartureTime().plusHours(2));
        
        // Create a dummy driver
        User driver = new User();
        driver.setId("D" + tripId);
        trip.setDriver(driver);
        
        return trip;
    }
    
    private Booking createBooking(String bookingId, User customer, Trip trip, int seats, String bookingTimeStr) {
        Booking booking = new Booking();
        booking.setId(bookingId);
        booking.setCustomer(customer);
        booking.setTrip(trip);
        booking.setSeats(seats);
        booking.setBookingTime(LocalDateTime.parse(bookingTimeStr, formatter));
        return booking;
    }
}