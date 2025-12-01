import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    private ORS ors;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        ors = new ORS();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_PointsCalculationWithMultipleBookings() {
        // Setup: Customer C5 has membership with POINTS award
        User customerC5 = new User();
        customerC5.setId("C5");
        Membership membershipC5 = new Membership();
        membershipC5.setHasMembership(true);
        membershipC5.setRewardType("POINTS");
        customerC5.setMembership(membershipC5);
        ors.users.add(customerC5);
        
        // Setup: C5 create Booking1 (seats:2) for trip (price: 200.0, departure: 2023-12-25 12:00)
        Trip trip1 = new Trip();
        trip1.setId("T1");
        trip1.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        trip1.setPrice(200.0);
        
        Booking booking1 = new Booking();
        booking1.setCustomerId("C5");
        booking1.setTripId("T1");
        booking1.setNumberOfSeats(2);
        booking1.setBookingTime(LocalDateTime.parse("2023-12-01 10:00:00", formatter));
        trip1.getBookings().add(booking1);
        ors.trips.add(trip1);
        
        // Setup: C5 create Booking2 (seats:3) for trip (price: 100.0, departure: 2023-12-26 12:00)
        Trip trip2 = new Trip();
        trip2.setId("T2");
        trip2.setDepartureTime(LocalDateTime.parse("2023-12-26 12:00:00", formatter));
        trip2.setPrice(100.0);
        
        Booking booking2 = new Booking();
        booking2.setCustomerId("C5");
        booking2.setTripId("T2");
        booking2.setNumberOfSeats(3);
        booking2.setBookingTime(LocalDateTime.parse("2023-12-02 10:00:00", formatter));
        trip2.getBookings().add(booking2);
        ors.trips.add(trip2);
        
        // Input: Compute reward points for Customer C5 (current Month: 2023-12)
        int result = ors.computeMonthlyRewardPoints("C5", 2023, 12);
        
        // Expected Output: 25. (2+3)*5=25
        assertEquals(25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() {
        // Setup: Customer C6 has membership with POINTS award
        User customerC6 = new User();
        customerC6.setId("C6");
        Membership membershipC6 = new Membership();
        membershipC6.setHasMembership(true);
        membershipC6.setRewardType("POINTS");
        customerC6.setMembership(membershipC6);
        ors.users.add(customerC6);
        
        // Setup: C6 create Booking3 (seats:4) for trip (price: 100.0, departure: 2024-12-26 12:00)
        Trip trip = new Trip();
        trip.setId("T3");
        trip.setDepartureTime(LocalDateTime.parse("2024-12-26 12:00:00", formatter));
        trip.setPrice(100.0);
        
        Booking booking = new Booking();
        booking.setCustomerId("C6");
        booking.setTripId("T3");
        booking.setNumberOfSeats(4);
        booking.setBookingTime(LocalDateTime.parse("2023-12-01 10:00:00", formatter));
        trip.getBookings().add(booking);
        ors.trips.add(trip);
        
        // Input: Compute reward points for Customer C6 (current Month: 2023-12)
        int result = ors.computeMonthlyRewardPoints("C6", 2023, 12);
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() {
        // Setup: Customer C7 has membership with POINTS award
        User customerC7 = new User();
        customerC7.setId("C7");
        Membership membershipC7 = new Membership();
        membershipC7.setHasMembership(true);
        membershipC7.setRewardType("POINTS");
        customerC7.setMembership(membershipC7);
        ors.users.add(customerC7);
        
        // Setup: C7 create Booking1: 2023-11-30 10:00 (seats:2) for trip (price: 200.0, departure: 2023-12-25 12:00)
        Trip trip1 = new Trip();
        trip1.setId("T4");
        trip1.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        trip1.setPrice(200.0);
        
        Booking booking1 = new Booking();
        booking1.setCustomerId("C7");
        booking1.setTripId("T4");
        booking1.setNumberOfSeats(2);
        booking1.setBookingTime(LocalDateTime.parse("2023-11-30 10:00:00", formatter));
        trip1.getBookings().add(booking1);
        ors.trips.add(trip1);
        
        // Setup: C7 create Booking2: 2023-12-01 10:00 (seats:3) for trip (price: 200.0, departure: 2023-12-25 12:00)
        Trip trip2 = new Trip();
        trip2.setId("T5");
        trip2.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        trip2.setPrice(200.0);
        
        Booking booking2 = new Booking();
        booking2.setCustomerId("C7");
        booking2.setTripId("T5");
        booking2.setNumberOfSeats(3);
        booking2.setBookingTime(LocalDateTime.parse("2023-12-01 10:00:00", formatter));
        trip2.getBookings().add(booking2);
        ors.trips.add(trip2);
        
        // Input: Compute reward points for Customer C7 (current Month: 2023-12)
        int result = ors.computeMonthlyRewardPoints("C7", 2023, 12);
        
        // Expected Output: 15. 3*5=15
        assertEquals(15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() {
        // Setup: Customer C8 has membership with POINTS award
        User customerC8 = new User();
        customerC8.setId("C8");
        Membership membershipC8 = new Membership();
        membershipC8.setHasMembership(true);
        membershipC8.setRewardType("POINTS");
        customerC8.setMembership(membershipC8);
        ors.users.add(customerC8);
        
        // Setup: C8 create Booking: 2023-12-10 10:00 (seats:2) for trip (price: 200.0, departure: 2024-03-25 12:00)
        Trip trip = new Trip();
        trip.setId("T6");
        trip.setDepartureTime(LocalDateTime.parse("2024-03-25 12:00:00", formatter));
        trip.setPrice(200.0);
        
        Booking booking = new Booking();
        booking.setCustomerId("C8");
        booking.setTripId("T6");
        booking.setNumberOfSeats(2);
        booking.setBookingTime(LocalDateTime.parse("2023-12-10 10:00:00", formatter));
        trip.getBookings().add(booking);
        ors.trips.add(trip);
        
        // Input: Compute reward points for Customer C8 (current Month: 2023-12)
        int result = ors.computeMonthlyRewardPoints("C8", 2023, 12);
        
        // Expected Output: 10. 2*5=10
        assertEquals(10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() {
        // Setup: Customer C8 has membership with POINTS award
        User customerC8 = new User();
        customerC8.setId("C8");
        Membership membershipC8 = new Membership();
        membershipC8.setHasMembership(true);
        membershipC8.setRewardType("POINTS");
        customerC8.setMembership(membershipC8);
        ors.users.add(customerC8);
        
        // Setup: Customer C9 has membership with POINTS award
        User customerC9 = new User();
        customerC9.setId("C9");
        Membership membershipC9 = new Membership();
        membershipC9.setHasMembership(true);
        membershipC9.setRewardType("POINTS");
        customerC9.setMembership(membershipC9);
        ors.users.add(customerC9);
        
        // Setup: C8 create Booking: 2024-01-10 10:00 (seats:50) for trip (price: 150.0, departure: 2024-05-25 12:00)
        Trip trip1 = new Trip();
        trip1.setId("T7");
        trip1.setDepartureTime(LocalDateTime.parse("2024-05-25 12:00:00", formatter));
        trip1.setPrice(150.0);
        
        Booking booking1 = new Booking();
        booking1.setCustomerId("C8");
        booking1.setTripId("T7");
        booking1.setNumberOfSeats(50);
        booking1.setBookingTime(LocalDateTime.parse("2024-01-10 10:00:00", formatter));
        trip1.getBookings().add(booking1);
        ors.trips.add(trip1);
        
        // Setup: C8 create Booking: 2024-01-15 10:00 (seats:50) for trip (price: 200.0, departure: 2024-06-25 12:00)
        Trip trip2 = new Trip();
        trip2.setId("T8");
        trip2.setDepartureTime(LocalDateTime.parse("2024-06-25 12:00:00", formatter));
        trip2.setPrice(200.0);
        
        Booking booking2 = new Booking();
        booking2.setCustomerId("C8");
        booking2.setTripId("T8");
        booking2.setNumberOfSeats(50);
        booking2.setBookingTime(LocalDateTime.parse("2024-01-15 10:00:00", formatter));
        trip2.getBookings().add(booking2);
        ors.trips.add(trip2);
        
        // Setup: C9 create Booking: 2024-01-10 10:00 (seats:50) for trip (price: 200.0, departure: 2024-07-25 12:00)
        Trip trip3 = new Trip();
        trip3.setId("T9");
        trip3.setDepartureTime(LocalDateTime.parse("2024-07-25 12:00:00", formatter));
        trip3.setPrice(200.0);
        
        Booking booking3 = new Booking();
        booking3.setCustomerId("C9");
        booking3.setTripId("T9");
        booking3.setNumberOfSeats(50);
        booking3.setBookingTime(LocalDateTime.parse("2024-01-10 10:00:00", formatter));
        trip3.getBookings().add(booking3);
        ors.trips.add(trip3);
        
        // Input: Compute reward points for Customer C8 (current Month: 2024-01)
        int resultC8 = ors.computeMonthlyRewardPoints("C8", 2024, 1);
        
        // Input: Compute reward points for Customer C9 (current Month: 2024-01)
        int resultC9 = ors.computeMonthlyRewardPoints("C9", 2024, 1);
        
        // Expected Output: C8 reward points: 500. C9 reward points: 250.
        assertEquals("C8 reward points should be 500", 500, resultC8);
        assertEquals("C9 reward points should be 250", 250, resultC9);
    }
}