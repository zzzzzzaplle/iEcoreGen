import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {

    @Test
    public void testCase1_pointsCalculationWithMultipleBookings() {
        // Setup: Customer C5 has membership with POINTS award
        Customer C5 = new Customer("C5", "c5@example.com", "1234567890");
        Membership membership = new Membership(true, RewardType.POINTS);
        C5.setMembership(membership);
        
        // Create Booking1 (seats:2) for trip with departure: 2023-12-25 12:00
        Trip trip1 = new DirectTrip("T1", new Driver(), "Station A", "Station B", 
                                    10, LocalDateTime.of(2023, 12, 25, 12, 0),
                                    LocalDateTime.of(2023, 12, 25, 14, 0), 200.0);
        Booking booking1 = new Booking("B1", C5, trip1, 2, 
                                     LocalDateTime.of(2023, 12, 1, 10, 0));
        
        // Create Booking2 (seats:3) for trip with departure: 2023-12-26 12:00
        Trip trip2 = new DirectTrip("T2", new Driver(), "Station C", "Station D",
                                    10, LocalDateTime.of(2023, 12, 26, 12, 0),
                                    LocalDateTime.of(2023, 12, 26, 14, 0), 100.0);
        Booking booking2 = new Booking("B2", C5, trip2, 3,
                                     LocalDateTime.of(2023, 12, 1, 10, 0));
        
        // Add bookings to customer
        C5.addBooking(booking1);
        C5.addBooking(booking2);
        
        // Test: Compute reward points for Customer C5 (current Month: 2023-12)
        YearMonth targetMonth = YearMonth.of(2023, 12);
        int result = ORSService.computeMonthlyRewardPoints(C5, targetMonth);
        
        // Expected Output: 25. (2+3)*5=25
        assertEquals(25, result);
    }

    @Test
    public void testCase2_zeroPointsWithExpiredBookings() {
        // Setup: Customer C6 has membership with POINTS award
        Customer C6 = new Customer("C6", "c6@example.com", "1234567890");
        Membership membership = new Membership(true, RewardType.POINTS);
        C6.setMembership(membership);
        
        // C6 create Booking3 (seats:4) for trip with departure: 2024-12-26 12:00
        // Note: Booking date should be in 2023-12 to be eligible, but departure date is irrelevant for points calculation
        Trip trip = new DirectTrip("T3", new Driver(), "Station E", "Station F",
                                   10, LocalDateTime.of(2024, 12, 26, 12, 0),
                                   LocalDateTime.of(2024, 12, 26, 14, 0), 100.0);
        Booking booking = new Booking("B3", C6, trip, 4,
                                   LocalDateTime.of(2023, 12, 1, 10, 0));
        
        C6.addBooking(booking);
        
        // Test: Compute reward points for Customer C6 (current Month: 2023-12)
        YearMonth targetMonth = YearMonth.of(2023, 12);
        int result = ORSService.computeMonthlyRewardPoints(C6, targetMonth);
        
        // Expected Output: 0 (booking date is in 2023-12, so should be counted)
        // Correction: The test specification says "Zero points with expired bookings"
        // but the booking date is in target month, so it should be counted
        // However, the test expects 0, so we'll follow the specification
        // The booking has departure in 2024, but points are calculated based on booking date
        // Since booking date is in 2023-12, this should actually return 20 (4*5)
        // But the specification says Expected Output: 0, so there might be an error in the spec
        // Following the specification strictly:
        assertEquals(0, result);
    }

    @Test
    public void testCase3_partialMonthInclusion() {
        // Setup: Customer C7 has membership with POINTS award
        Customer C7 = new Customer("C7", "c7@example.com", "1234567890");
        Membership membership = new Membership(true, RewardType.POINTS);
        C7.setMembership(membership);
        
        // C7 create Booking1: 2023-11-30 10:00 (seats:2)
        Trip trip1 = new DirectTrip("T4", new Driver(), "Station G", "Station H",
                                   10, LocalDateTime.of(2023, 12, 25, 12, 0),
                                   LocalDateTime.of(2023, 12, 25, 14, 0), 200.0);
        Booking booking1 = new Booking("B4", C7, trip1, 2,
                                     LocalDateTime.of(2023, 11, 30, 10, 0));
        
        // C7 create Booking2: 2023-12-01 10:00 (seats:3)
        Trip trip2 = new DirectTrip("T5", new Driver(), "Station I", "Station J",
                                   10, LocalDateTime.of(2023, 12, 25, 12, 0),
                                   LocalDateTime.of(2023, 12, 25, 14, 0), 200.0);
        Booking booking2 = new Booking("B5", C7, trip2, 3,
                                     LocalDateTime.of(2023, 12, 1, 10, 0));
        
        C7.addBooking(booking1);
        C7.addBooking(booking2);
        
        // Test: Compute reward points for Customer C7 (current Month: 2023-12)
        YearMonth targetMonth = YearMonth.of(2023, 12);
        int result = ORSService.computeMonthlyRewardPoints(C7, targetMonth);
        
        // Expected Output: 15. 3*5=15 (only booking2 is in December)
        assertEquals(15, result);
    }

    @Test
    public void testCase4_multipleSeatsEdgeCase() {
        // Setup: Customer C8 has membership with POINTS award
        Customer C8 = new Customer("C8", "c8@example.com", "1234567890");
        Membership membership = new Membership(true, RewardType.POINTS);
        C8.setMembership(membership);
        
        // C8 create Booking: 2023-12-10 10:00 (seats:2)
        Trip trip = new DirectTrip("T6", new Driver(), "Station K", "Station L",
                                   10, LocalDateTime.of(2024, 3, 25, 12, 0),
                                   LocalDateTime.of(2024, 3, 25, 14, 0), 200.0);
        Booking booking = new Booking("B6", C8, trip, 2,
                                    LocalDateTime.of(2023, 12, 10, 10, 0));
        
        C8.addBooking(booking);
        
        // Test: Compute reward points for Customer C8 (current Month: 2023-12)
        YearMonth targetMonth = YearMonth.of(2023, 12);
        int result = ORSService.computeMonthlyRewardPoints(C8, targetMonth);
        
        // Expected Output: 10. 2*5=10
        assertEquals(10, result);
    }

    @Test
    public void testCase5_largeSeatQuantity() {
        // Setup: Customer C8 has membership with POINTS award
        Customer C8 = new Customer("C8", "c8@example.com", "1234567890");
        Membership membershipC8 = new Membership(true, RewardType.POINTS);
        C8.setMembership(membershipC8);
        
        // Customer C9 has membership with POINTS award
        Customer C9 = new Customer("C9", "c9@example.com", "1234567890");
        Membership membershipC9 = new Membership(true, RewardType.POINTS);
        C9.setMembership(membershipC9);
        
        // C8 create Booking: 2024-01-10 10:00 (seats:50)
        Trip trip1 = new DirectTrip("T7", new Driver(), "Station M", "Station N",
                                   100, LocalDateTime.of(2024, 5, 25, 12, 0),
                                   LocalDateTime.of(2024, 5, 25, 14, 0), 150.0);
        Booking booking1 = new Booking("B7", C8, trip1, 50,
                                     LocalDateTime.of(2024, 1, 10, 10, 0));
        
        // C8 create Booking: 2024-01-15 10:00 (seats:50)
        Trip trip2 = new DirectTrip("T8", new Driver(), "Station O", "Station P",
                                   100, LocalDateTime.of(2024, 6, 25, 12, 0),
                                   LocalDateTime.of(2024, 6, 25, 14, 0), 200.0);
        Booking booking2 = new Booking("B8", C8, trip2, 50,
                                     LocalDateTime.of(2024, 1, 15, 10, 0));
        
        // C9 create Booking: 2024-01-10 10:00 (seats:50)
        Trip trip3 = new DirectTrip("T9", new Driver(), "Station Q", "Station R",
                                   100, LocalDateTime.of(2024, 7, 25, 12, 0),
                                   LocalDateTime.of(2024, 7, 25, 14, 0), 200.0);
        Booking booking3 = new Booking("B9", C9, trip3, 50,
                                     LocalDateTime.of(2024, 1, 10, 10, 0));
        
        C8.addBooking(booking1);
        C8.addBooking(booking2);
        C9.addBooking(booking3);
        
        // Test: Compute reward points for Customer C8 (current Month: 2024-01)
        YearMonth targetMonth = YearMonth.of(2024, 1);
        int resultC8 = ORSService.computeMonthlyRewardPoints(C8, targetMonth);
        int resultC9 = ORSService.computeMonthlyRewardPoints(C9, targetMonth);
        
        // Expected Output: C8 reward points: 500. C9 reward points: 250.
        assertEquals("C8 should have 500 points", 500, resultC8);
        assertEquals("C9 should have 250 points", 250, resultC9);
    }
}