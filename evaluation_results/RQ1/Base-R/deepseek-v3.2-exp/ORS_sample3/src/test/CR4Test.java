import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.YearMonth;
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
    private DateTimeFormatter formatter;

    @Before
    public void setUp() {
        system = new OnlineRideshareSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        // Initialize customers with memberships
        customerC5 = createCustomerWithPointsMembership("C5");
        customerC6 = createCustomerWithPointsMembership("C6");
        customerC7 = createCustomerWithPointsMembership("C7");
        customerC8 = createCustomerWithPointsMembership("C8");
        customerC9 = createCustomerWithPointsMembership("C9");
        
        system.getUsers().add(customerC5);
        system.getUsers().add(customerC6);
        system.getUsers().add(customerC7);
        system.getUsers().add(customerC8);
        system.getUsers().add(customerC9);
    }

    private User createCustomerWithPointsMembership(String userId) {
        User customer = new User();
        customer.setUserId(userId);
        
        Membership membership = new Membership();
        membership.setRewardType(RewardType.POINTS);
        
        customer.setMembership(membership);
        return customer;
    }

    @Test
    public void testCase1_pointsCalculationWithMultipleBookings() {
        // Setup: Customer C5 has membership with POINTS award
        // C5 create Booking1 (seats:2) for trip (price: 200.0, departure: 2023-12-25 12:00)
        // C5 create Booking2 (seats:3) for trip (price: 100.0, departure: 2023-12-26 12:00)
        
        Trip trip1 = createTrip("T1", 200.0, LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        Trip trip2 = createTrip("T2", 100.0, LocalDateTime.parse("2023-12-26 12:00:00", formatter));
        
        system.getTrips().add(trip1);
        system.getTrips().add(trip2);
        
        Booking booking1 = createBooking("B1", customerC5, trip1, 2, LocalDateTime.parse("2023-12-01 10:00:00", formatter));
        Booking booking2 = createBooking("B2", customerC5, trip2, 3, LocalDateTime.parse("2023-12-01 11:00:00", formatter));
        
        system.getBookings().add(booking1);
        system.getBookings().add(booking2);
        
        // Input: Compute reward points for Customer C5 (current Month: 2023-12)
        YearMonth targetMonth = YearMonth.of(2023, 12);
        int result = system.computeMonthlyRewardPoints(customerC5, targetMonth);
        
        // Expected Output: 25. (2+3)*5=25
        assertEquals("Points should be 25 for 5 seats (2+3)", 25, result);
    }

    @Test
    public void testCase2_zeroPointsWithExpiredBookings() {
        // Setup: Customer C6 has membership with POINTS award
        // C6 create Booking3 (seats:4) for trip (price: 100.0, departure: 2024-12-26 12:00)
        
        Trip trip = createTrip("T3", 100.0, LocalDateTime.parse("2024-12-26 12:00:00", formatter));
        system.getTrips().add(trip);
        
        Booking booking = createBooking("B3", customerC6, trip, 4, LocalDateTime.parse("2023-12-01 10:00:00", formatter));
        system.getBookings().add(booking);
        
        // Input: Compute reward points for Customer C6 (current Month: 2023-12)
        YearMonth targetMonth = YearMonth.of(2023, 12);
        int result = system.computeMonthlyRewardPoints(customerC6, targetMonth);
        
        // Expected Output: 0
        assertEquals("Points should be 0 for booking in different year", 0, result);
    }

    @Test
    public void testCase3_partialMonthInclusion() {
        // Setup:
        // Customer C7 has membership with POINTS award
        // C7 create Booking1: 2023-11-30 10:00 (seats:2) for trip (price: 200.0, departure: 2023-12-25 12:00)
        // C7 create Booking2: 2023-12-01 10:00 (seats:3) for trip (price: 200.0, departure: 2023-12-25 12:00)
        
        Trip trip1 = createTrip("T4", 200.0, LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        Trip trip2 = createTrip("T5", 200.0, LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        
        system.getTrips().add(trip1);
        system.getTrips().add(trip2);
        
        Booking booking1 = createBooking("B4", customerC7, trip1, 2, LocalDateTime.parse("2023-11-30 10:00:00", formatter));
        Booking booking2 = createBooking("B5", customerC7, trip2, 3, LocalDateTime.parse("2023-12-01 10:00:00", formatter));
        
        system.getBookings().add(booking1);
        system.getBookings().add(booking2);
        
        // Input: Compute reward points for Customer C7 (current Month: 2023-12)
        YearMonth targetMonth = YearMonth.of(2023, 12);
        int result = system.computeMonthlyRewardPoints(customerC7, targetMonth);
        
        // Expected Output: 15. 3*5=15
        assertEquals("Points should be 15 for 3 seats in December booking", 15, result);
    }

    @Test
    public void testCase4_multipleSeatsEdgeCase() {
        // Setup:
        // Customer C8 has membership with POINTS award
        // C8 create Booking: 2023-12-10 10:00 (seats:2) for trip (price: 200.0, departure: 2024-03-25 12:00)
        
        Trip trip = createTrip("T6", 200.0, LocalDateTime.parse("2024-03-25 12:00:00", formatter));
        system.getTrips().add(trip);
        
        Booking booking = createBooking("B6", customerC8, trip, 2, LocalDateTime.parse("2023-12-10 10:00:00", formatter));
        system.getBookings().add(booking);
        
        // Input: Compute reward points for Customer C8 (current Month: 2023-12)
        YearMonth targetMonth = YearMonth.of(2023, 12);
        int result = system.computeMonthlyRewardPoints(customerC8, targetMonth);
        
        // Expected Output: 10. 2*5=10
        assertEquals("Points should be 10 for 2 seats", 10, result);
    }

    @Test
    public void testCase5_largeSeatQuantity() {
        // Setup:
        // Customer C8 has membership with POINTS award
        // C8 create Booking: 2024-01-10 10:00 (seats:50) for trip (price: 150.0, departure: 2024-05-25 12:00)
        // C8 create Booking: 2024-01-15 10:00 (seats:50) for trip (price: 200.0, departure: 2024-06-25 12:00)
        // C9 create Booking: 2024-01-10 10:00 (seats:50) for trip (price: 200.0, departure: 2024-07-25 12:00)
        
        Trip trip1 = createTrip("T7", 150.0, LocalDateTime.parse("2024-05-25 12:00:00", formatter));
        Trip trip2 = createTrip("T8", 200.0, LocalDateTime.parse("2024-06-25 12:00:00", formatter));
        Trip trip3 = createTrip("T9", 200.0, LocalDateTime.parse("2024-07-25 12:00:00", formatter));
        
        system.getTrips().add(trip1);
        system.getTrips().add(trip2);
        system.getTrips().add(trip3);
        
        Booking booking1 = createBooking("B7", customerC8, trip1, 50, LocalDateTime.parse("2024-01-10 10:00:00", formatter));
        Booking booking2 = createBooking("B8", customerC8, trip2, 50, LocalDateTime.parse("2024-01-15 10:00:00", formatter));
        Booking booking3 = createBooking("B9", customerC9, trip3, 50, LocalDateTime.parse("2024-01-10 10:00:00", formatter));
        
        system.getBookings().add(booking1);
        system.getBookings().add(booking2);
        system.getBookings().add(booking3);
        
        // Input: Compute reward points for Customer C8, C9 (current Month: 2024-01)
        YearMonth targetMonth = YearMonth.of(2024, 1);
        
        int resultC8 = system.computeMonthlyRewardPoints(customerC8, targetMonth);
        int resultC9 = system.computeMonthlyRewardPoints(customerC9, targetMonth);
        
        // Expected Output: C8 reward points: 500. C9 reward points: 250.
        assertEquals("C8 points should be 500 for 100 seats", 500, resultC8);
        assertEquals("C9 points should be 250 for 50 seats", 250, resultC9);
    }

    private Trip createTrip(String tripId, double price, LocalDateTime departureTime) {
        Trip trip = new Trip();
        trip.setTripId(tripId);
        trip.setPrice(price);
        trip.setDepartureTime(departureTime);
        trip.setArrivalTime(departureTime.plusHours(2)); // Assuming 2-hour trip
        trip.setTotalSeats(100);
        trip.setAvailableSeats(100);
        
        // Create a driver for the trip
        User driver = new User();
        driver.setUserId("DRIVER_" + tripId);
        trip.setDriver(driver);
        
        return trip;
    }

    private Booking createBooking(String bookingId, User customer, Trip trip, int seatsBooked, LocalDateTime bookingTime) {
        Booking booking = new Booking();
        booking.setBookingId(bookingId);
        booking.setCustomer(customer);
        booking.setTrip(trip);
        booking.setSeatsBooked(seatsBooked);
        booking.setBookingTime(bookingTime);
        booking.setFinalPrice(trip.getPrice());
        return booking;
    }
}