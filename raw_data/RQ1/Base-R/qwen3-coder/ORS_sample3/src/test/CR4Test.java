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
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }
    
    @Test
    public void testCase1_PointsCalculationWithMultipleBookings() {
        // Setup: Customer C5 has membership with POINTS award
        Customer c5 = new Customer();
        Membership membership = new Membership();
        membership.setType("points");
        membership.setActive(true);
        c5.setMembership(membership);
        
        // Create Booking1 (seats:2) for trip (price: 200.0, departure: 2023-12-25 12:00)
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(2);
        booking1.setBookingTime(LocalDateTime.parse("2023-12-01 10:00", formatter));
        
        Trip trip1 = new Trip();
        trip1.setPrice(200.0);
        trip1.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00", formatter));
        booking1.setTrip(trip1);
        
        // Create Booking2 (seats:3) for trip (price: 100.0, departure: 2023-12-26 12:00)
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(3);
        booking2.setBookingTime(LocalDateTime.parse("2023-12-02 10:00", formatter));
        
        Trip trip2 = new Trip();
        trip2.setPrice(100.0);
        trip2.setDepartureTime(LocalDateTime.parse("2023-12-26 12:00", formatter));
        booking2.setTrip(trip2);
        
        c5.addBooking(booking1);
        c5.addBooking(booking2);
        
        // Execute: Compute reward points for Customer C5 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints(c5, 12, 2023);
        
        // Verify: Expected Output: 25. (2+3)*5=25
        assertEquals(25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() {
        // Setup: Customer C6 has membership with POINTS award
        Customer c6 = new Customer();
        Membership membership = new Membership();
        membership.setType("points");
        membership.setActive(true);
        c6.setMembership(membership);
        
        // C6 create Booking3 (seats:4) for trip (price: 100.0, departure: 2024-12-26 12:00)
        Booking booking = new Booking();
        booking.setNumberOfSeats(4);
        booking.setBookingTime(LocalDateTime.parse("2023-12-01 10:00", formatter));
        
        Trip trip = new Trip();
        trip.setPrice(100.0);
        trip.setDepartureTime(LocalDateTime.parse("2024-12-26 12:00", formatter));
        booking.setTrip(trip);
        
        c6.addBooking(booking);
        
        // Execute: Compute reward points for Customer C6 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints(c6, 12, 2023);
        
        // Verify: Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() {
        // Setup: Customer C7 has membership with POINTS award
        Customer c7 = new Customer();
        Membership membership = new Membership();
        membership.setType("points");
        membership.setActive(true);
        c7.setMembership(membership);
        
        // C7 create Booking1: 2023-11-30 10:00 (seats:2) for trip (price: 200.0, departure: 2023-12-25 12:00)
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(2);
        booking1.setBookingTime(LocalDateTime.parse("2023-11-30 10:00", formatter));
        
        Trip trip1 = new Trip();
        trip1.setPrice(200.0);
        trip1.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00", formatter));
        booking1.setTrip(trip1);
        
        // C7 create Booking2: 2023-12-01 10:00 (seats:3) for trip (price: 200.0, departure: 2023-12-25 12:00)
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(3);
        booking2.setBookingTime(LocalDateTime.parse("2023-12-01 10:00", formatter));
        
        Trip trip2 = new Trip();
        trip2.setPrice(200.0);
        trip2.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00", formatter));
        booking2.setTrip(trip2);
        
        c7.addBooking(booking1);
        c7.addBooking(booking2);
        
        // Execute: Compute reward points for Customer C7 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints(c7, 12, 2023);
        
        // Verify: Expected Output: 15. 3*5=15
        assertEquals(15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() {
        // Setup: Customer C8 has membership with POINTS award
        Customer c8 = new Customer();
        Membership membership = new Membership();
        membership.setType("points");
        membership.setActive(true);
        c8.setMembership(membership);
        
        // C8 create Booking: 2023-12-10 10:00 (seats:2) for trip (price: 200.0, departure: 2024-03-25 12:00)
        Booking booking = new Booking();
        booking.setNumberOfSeats(2);
        booking.setBookingTime(LocalDateTime.parse("2023-12-10 10:00", formatter));
        
        Trip trip = new Trip();
        trip.setPrice(200.0);
        trip.setDepartureTime(LocalDateTime.parse("2024-03-25 12:00", formatter));
        booking.setTrip(trip);
        
        c8.addBooking(booking);
        
        // Execute: Compute reward points for Customer C8 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints(c8, 12, 2023);
        
        // Verify: Expected Output: 10. 2*5=10
        assertEquals(10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() {
        // Setup: Customer C8 has membership with POINTS award
        Customer c8 = new Customer();
        Membership membership8 = new Membership();
        membership8.setType("points");
        membership8.setActive(true);
        c8.setMembership(membership8);
        
        // C8 create Booking: 2024-01-10 10:00 (seats:50) for trip (price: 150.0, departure: 2024-05-25 12:00)
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(50);
        booking1.setBookingTime(LocalDateTime.parse("2024-01-10 10:00", formatter));
        
        Trip trip1 = new Trip();
        trip1.setPrice(150.0);
        trip1.setDepartureTime(LocalDateTime.parse("2024-05-25 12:00", formatter));
        booking1.setTrip(trip1);
        
        // C8 create Booking: 2024-01-15 10:00 (seats:50) for trip (price: 200.0, departure: 2024-06-25 12:00)
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(50);
        booking2.setBookingTime(LocalDateTime.parse("2024-01-15 10:00", formatter));
        
        Trip trip2 = new Trip();
        trip2.setPrice(200.0);
        trip2.setDepartureTime(LocalDateTime.parse("2024-06-25 12:00", formatter));
        booking2.setTrip(trip2);
        
        c8.addBooking(booking1);
        c8.addBooking(booking2);
        
        // Setup: Customer C9 has membership with POINTS award
        Customer c9 = new Customer();
        Membership membership9 = new Membership();
        membership9.setType("points");
        membership9.setActive(true);
        c9.setMembership(membership9);
        
        // C9 create Booking: 2024-01-10 10:00 (seats:50) for trip (price: 200.0, departure: 2024-07-25 12:00)
        Booking booking3 = new Booking();
        booking3.setNumberOfSeats(50);
        booking3.setBookingTime(LocalDateTime.parse("2024-01-10 10:00", formatter));
        
        Trip trip3 = new Trip();
        trip3.setPrice(200.0);
        trip3.setDepartureTime(LocalDateTime.parse("2024-07-25 12:00", formatter));
        booking3.setTrip(trip3);
        
        c9.addBooking(booking3);
        
        // Execute: Compute reward points for Customer C8, C9 (current Month: 2024-01)
        int resultC8 = system.computeMonthlyRewardPoints(c8, 1, 2024);
        int resultC9 = system.computeMonthlyRewardPoints(c9, 1, 2024);
        
        // Verify: Expected Output: C8 reward points: 500. C9 reward points: 250.
        assertEquals(500, resultC8);
        assertEquals(250, resultC9);
    }
}