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
        User customerC5 = new User("C5", "c5@example.com", "1234567890");
        customerC5.setMember(true);
        system.getUsers().add(customerC5);

        // Setup: Create trips
        Trip trip1 = new Trip("T1", "D1", "Station A", "Station B", 10, 
            LocalDateTime.parse("2023-12-25 12:00:00", formatter), 
            LocalDateTime.parse("2023-12-25 14:00:00", formatter), 200.0);
        Trip trip2 = new Trip("T2", "D2", "Station C", "Station D", 10, 
            LocalDateTime.parse("2023-12-26 12:00:00", formatter), 
            LocalDateTime.parse("2023-12-26 14:00:00", formatter), 100.0);
        system.getTrips().add(trip1);
        system.getTrips().add(trip2);

        // Setup: Create bookings for C5
        Booking booking1 = new Booking("B1", "C5", "T1", 2, LocalDateTime.parse("2023-12-01 10:00:00", formatter));
        Booking booking2 = new Booking("B2", "C5", "T2", 3, LocalDateTime.parse("2023-12-01 10:00:00", formatter));
        system.getBookings().add(booking1);
        system.getBookings().add(booking2);

        // Input: Compute reward points for Customer C5 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints("C5", 2023, 12);

        // Expected Output: 25. (2+3)*5=25
        assertEquals(25, result);
    }

    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() {
        // Setup: Customer C6 has membership with POINTS award
        User customerC6 = new User("C6", "c6@example.com", "1234567890");
        customerC6.setMember(true);
        system.getUsers().add(customerC6);

        // Setup: Create trip
        Trip trip3 = new Trip("T3", "D3", "Station E", "Station F", 10, 
            LocalDateTime.parse("2024-12-26 12:00:00", formatter), 
            LocalDateTime.parse("2024-12-26 14:00:00", formatter), 100.0);
        system.getTrips().add(trip3);

        // Setup: Create booking for C6 (in different year)
        Booking booking3 = new Booking("B3", "C6", "T3", 4, LocalDateTime.parse("2024-12-01 10:00:00", formatter));
        system.getBookings().add(booking3);

        // Input: Compute reward points for Customer C6 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints("C6", 2023, 12);

        // Expected Output: 0
        assertEquals(0, result);
    }

    @Test
    public void testCase3_PartialMonthInclusion() {
        // Setup: Customer C7 has membership with POINTS award
        User customerC7 = new User("C7", "c7@example.com", "1234567890");
        customerC7.setMember(true);
        system.getUsers().add(customerC7);

        // Setup: Create trips
        Trip trip1 = new Trip("T1", "D1", "Station A", "Station B", 10, 
            LocalDateTime.parse("2023-12-25 12:00:00", formatter), 
            LocalDateTime.parse("2023-12-25 14:00:00", formatter), 200.0);
        Trip trip2 = new Trip("T2", "D2", "Station C", "Station D", 10, 
            LocalDateTime.parse("2023-12-25 12:00:00", formatter), 
            LocalDateTime.parse("2023-12-25 14:00:00", formatter), 200.0);
        system.getTrips().add(trip1);
        system.getTrips().add(trip2);

        // Setup: Create bookings for C7
        // Booking1: 2023-11-30 10:00 (seats:2) - NOT in target month
        Booking booking1 = new Booking("B1", "C7", "T1", 2, LocalDateTime.parse("2023-11-30 10:00:00", formatter));
        // Booking2: 2023-12-01 10:00 (seats:3) - IN target month
        Booking booking2 = new Booking("B2", "C7", "T2", 3, LocalDateTime.parse("2023-12-01 10:00:00", formatter));
        system.getBookings().add(booking1);
        system.getBookings().add(booking2);

        // Input: Compute reward points for Customer C7 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints("C7", 2023, 12);

        // Expected Output: 15. 3*5=15
        assertEquals(15, result);
    }

    @Test
    public void testCase4_MultipleSeatsEdgeCase() {
        // Setup: Customer C8 has membership with POINTS award
        User customerC8 = new User("C8", "c8@example.com", "1234567890");
        customerC8.setMember(true);
        system.getUsers().add(customerC8);

        // Setup: Create trip
        Trip trip = new Trip("T1", "D1", "Station A", "Station B", 10, 
            LocalDateTime.parse("2024-03-25 12:00:00", formatter), 
            LocalDateTime.parse("2024-03-25 14:00:00", formatter), 200.0);
        system.getTrips().add(trip);

        // Setup: Create booking for C8
        Booking booking = new Booking("B1", "C8", "T1", 2, LocalDateTime.parse("2023-12-10 10:00:00", formatter));
        system.getBookings().add(booking);

        // Input: Compute reward points for Customer C8 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints("C8", 2023, 12);

        // Expected Output: 10. 2*5=10
        assertEquals(10, result);
    }

    @Test
    public void testCase5_LargeSeatQuantity() {
        // Setup: Customer C8 and C9 have membership with POINTS award
        User customerC8 = new User("C8", "c8@example.com", "1234567890");
        customerC8.setMember(true);
        User customerC9 = new User("C9", "c9@example.com", "1234567890");
        customerC9.setMember(true);
        system.getUsers().add(customerC8);
        system.getUsers().add(customerC9);

        // Setup: Create trips
        Trip trip1 = new Trip("T1", "D1", "Station A", "Station B", 100, 
            LocalDateTime.parse("2024-05-25 12:00:00", formatter), 
            LocalDateTime.parse("2024-05-25 14:00:00", formatter), 150.0);
        Trip trip2 = new Trip("T2", "D2", "Station C", "Station D", 100, 
            LocalDateTime.parse("2024-06-25 12:00:00", formatter), 
            LocalDateTime.parse("2024-06-25 14:00:00", formatter), 200.0);
        Trip trip3 = new Trip("T3", "D3", "Station E", "Station F", 100, 
            LocalDateTime.parse("2024-07-25 12:00:00", formatter), 
            LocalDateTime.parse("2024-07-25 14:00:00", formatter), 200.0);
        system.getTrips().add(trip1);
        system.getTrips().add(trip2);
        system.getTrips().add(trip3);

        // Setup: Create bookings
        // C8 bookings
        Booking booking1 = new Booking("B1", "C8", "T1", 50, LocalDateTime.parse("2024-01-10 10:00:00", formatter));
        Booking booking2 = new Booking("B2", "C8", "T2", 50, LocalDateTime.parse("2024-01-15 10:00:00", formatter));
        // C9 booking
        Booking booking3 = new Booking("B3", "C9", "T3", 50, LocalDateTime.parse("2024-01-10 10:00:00", formatter));
        system.getBookings().add(booking1);
        system.getBookings().add(booking2);
        system.getBookings().add(booking3);

        // Input: Compute reward points for Customer C8 and C9 (current Month: 2024-01)
        int resultC8 = system.computeMonthlyRewardPoints("C8", 2024, 1);
        int resultC9 = system.computeMonthlyRewardPoints("C9", 2024, 1);

        // Expected Output: C8 reward points: 500. C9 reward points: 250.
        assertEquals("C8 reward points should be 500", 500, resultC8);
        assertEquals("C9 reward points should be 250", 250, resultC9);
    }
}