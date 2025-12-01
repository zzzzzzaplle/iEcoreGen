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
        User customerC5 = new User("C5", "c5@example.com", "555-5555");
        Membership membership = new Membership("M5", "points", 0.1);
        customerC5.setMembership(membership);
        system.addUser(customerC5);
        
        // Create trips
        Trip trip1 = new Trip("T1", new User(), "StationA", "StationB", 5, 
                             LocalDateTime.parse("2023-12-25 12:00:00", formatter), 
                             LocalDateTime.now(), 200.0);
        Trip trip2 = new Trip("T2", new User(), "StationC", "StationD", 5, 
                             LocalDateTime.parse("2023-12-26 12:00:00", formatter), 
                             LocalDateTime.now(), 100.0);
        system.addTrip(trip1);
        system.addTrip(trip2);
        
        // Create bookings for C5
        Booking booking1 = new Booking("B1", customerC5, trip1, 2, 
                                     LocalDateTime.parse("2023-12-01 10:00:00", formatter));
        Booking booking2 = new Booking("B2", customerC5, trip2, 3, 
                                     LocalDateTime.parse("2023-12-02 10:00:00", formatter));
        system.addBooking(booking1);
        system.addBooking(booking2);
        
        // Test: Compute reward points for Customer C5 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints(customerC5, 12);
        
        // Expected Output: 25. (2+3)*5=25
        assertEquals(25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() {
        // Setup: Customer C6 has membership with POINTS award
        User customerC6 = new User("C6", "c6@example.com", "666-6666");
        Membership membership = new Membership("M6", "points", 0.1);
        customerC6.setMembership(membership);
        system.addUser(customerC6);
        
        // Create trip with departure in future year
        Trip trip = new Trip("T3", new User(), "StationA", "StationB", 5, 
                           LocalDateTime.parse("2024-12-26 12:00:00", formatter), 
                           LocalDateTime.now(), 100.0);
        system.addTrip(trip);
        
        // Create booking for C6 in 2023-12 but trip is in 2024
        Booking booking = new Booking("B3", customerC6, trip, 4, 
                                    LocalDateTime.parse("2023-12-01 10:00:00", formatter));
        system.addBooking(booking);
        
        // Test: Compute reward points for Customer C6 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints(customerC6, 12);
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() {
        // Setup: Customer C7 has membership with POINTS award
        User customerC7 = new User("C7", "c7@example.com", "777-7777");
        Membership membership = new Membership("M7", "points", 0.1);
        customerC7.setMembership(membership);
        system.addUser(customerC7);
        
        // Create trip
        Trip trip = new Trip("T4", new User(), "StationA", "StationB", 5, 
                           LocalDateTime.parse("2023-12-25 12:00:00", formatter), 
                           LocalDateTime.now(), 200.0);
        system.addTrip(trip);
        
        // Create bookings: one in November, one in December
        Booking booking1 = new Booking("B4", customerC7, trip, 2, 
                                     LocalDateTime.parse("2023-11-30 10:00:00", formatter));
        Booking booking2 = new Booking("B5", customerC7, trip, 3, 
                                     LocalDateTime.parse("2023-12-01 10:00:00", formatter));
        system.addBooking(booking1);
        system.addBooking(booking2);
        
        // Test: Compute reward points for Customer C7 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints(customerC7, 12);
        
        // Expected Output: 15. 3*5=15
        assertEquals(15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() {
        // Setup: Customer C8 has membership with POINTS award
        User customerC8 = new User("C8", "c8@example.com", "888-8888");
        Membership membership = new Membership("M8", "points", 0.1);
        customerC8.setMembership(membership);
        system.addUser(customerC8);
        
        // Create trip with future departure
        Trip trip = new Trip("T5", new User(), "StationA", "StationB", 5, 
                           LocalDateTime.parse("2024-03-25 12:00:00", formatter), 
                           LocalDateTime.now(), 200.0);
        system.addTrip(trip);
        
        // Create booking for C8 in December 2023
        Booking booking = new Booking("B6", customerC8, trip, 2, 
                                    LocalDateTime.parse("2023-12-10 10:00:00", formatter));
        system.addBooking(booking);
        
        // Test: Compute reward points for Customer C8 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints(customerC8, 12);
        
        // Expected Output: 10. 2*5=10
        assertEquals(10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() {
        // Setup: Customers C8 and C9 have membership with POINTS award
        User customerC8 = new User("C8", "c8@example.com", "888-8888");
        User customerC9 = new User("C9", "c9@example.com", "999-9999");
        
        Membership membership8 = new Membership("M8", "points", 0.1);
        Membership membership9 = new Membership("M9", "points", 0.1);
        
        customerC8.setMembership(membership8);
        customerC9.setMembership(membership9);
        
        system.addUser(customerC8);
        system.addUser(customerC9);
        
        // Create trips
        Trip trip1 = new Trip("T6", new User(), "StationA", "StationB", 100, 
                             LocalDateTime.parse("2024-05-25 12:00:00", formatter), 
                             LocalDateTime.now(), 150.0);
        Trip trip2 = new Trip("T7", new User(), "StationC", "StationD", 100, 
                             LocalDateTime.parse("2024-06-25 12:00:00", formatter), 
                             LocalDateTime.now(), 200.0);
        Trip trip3 = new Trip("T8", new User(), "StationE", "StationF", 100, 
                             LocalDateTime.parse("2024-07-25 12:00:00", formatter), 
                             LocalDateTime.now(), 200.0);
        
        system.addTrip(trip1);
        system.addTrip(trip2);
        system.addTrip(trip3);
        
        // Create bookings
        Booking booking1 = new Booking("B7", customerC8, trip1, 50, 
                                     LocalDateTime.parse("2024-01-10 10:00:00", formatter));
        Booking booking2 = new Booking("B8", customerC8, trip2, 50, 
                                     LocalDateTime.parse("2024-01-15 10:00:00", formatter));
        Booking booking3 = new Booking("B9", customerC9, trip3, 50, 
                                     LocalDateTime.parse("2024-01-10 10:00:00", formatter));
        
        system.addBooking(booking1);
        system.addBooking(booking2);
        system.addBooking(booking3);
        
        // Test: Compute reward points for Customer C8 and C9 (current Month: 2024-01)
        int resultC8 = system.computeMonthlyRewardPoints(customerC8, 1);
        int resultC9 = system.computeMonthlyRewardPoints(customerC9, 1);
        
        // Expected Output: C8 reward points: 500. C9 reward points: 250.
        assertEquals(500, resultC8);
        assertEquals(250, resultC9);
    }
}