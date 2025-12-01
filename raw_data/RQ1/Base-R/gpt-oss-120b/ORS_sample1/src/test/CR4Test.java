import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Customer customerC5;
    private Customer customerC6;
    private Customer customerC7;
    private Customer customerC8;
    private Customer customerC9;
    private Membership pointsMembership;
    private Trip trip1, trip2, trip3, trip4, trip5, trip6;
    private Booking booking1, booking2, booking3, booking4, booking5, booking6, booking7;
    
    @Before
    public void setUp() {
        // Create membership with points reward for all test cases
        pointsMembership = new Membership();
        pointsMembership.setPointsReward(true);
        
        // Initialize customers
        customerC5 = new Customer();
        customerC5.setId("C5");
        customerC5.setMembership(pointsMembership);
        customerC5.setBookings(new ArrayList<>());
        
        customerC6 = new Customer();
        customerC6.setId("C6");
        customerC6.setMembership(pointsMembership);
        customerC6.setBookings(new ArrayList<>());
        
        customerC7 = new Customer();
        customerC7.setId("C7");
        customerC7.setMembership(pointsMembership);
        customerC7.setBookings(new ArrayList<>());
        
        customerC8 = new Customer();
        customerC8.setId("C8");
        customerC8.setMembership(pointsMembership);
        customerC8.setBookings(new ArrayList<>());
        
        customerC9 = new Customer();
        customerC9.setId("C9");
        customerC9.setMembership(pointsMembership);
        customerC9.setBookings(new ArrayList<>());
        
        // Initialize trips
        trip1 = new Trip();
        trip1.setId("T1");
        trip1.setPrice(200.0);
        trip1.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 12, 0));
        
        trip2 = new Trip();
        trip2.setId("T2");
        trip2.setPrice(100.0);
        trip2.setDepartureDateTime(LocalDateTime.of(2023, 12, 26, 12, 0));
        
        trip3 = new Trip();
        trip3.setId("T3");
        trip3.setPrice(100.0);
        trip3.setDepartureDateTime(LocalDateTime.of(2024, 12, 26, 12, 0));
        
        trip4 = new Trip();
        trip4.setId("T4");
        trip4.setPrice(200.0);
        trip4.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 12, 0));
        
        trip5 = new Trip();
        trip5.setId("T5");
        trip5.setPrice(200.0);
        trip5.setDepartureDateTime(LocalDateTime.of(2024, 3, 25, 12, 0));
        
        trip6 = new Trip();
        trip6.setId("T6");
        trip6.setPrice(150.0);
        trip6.setDepartureDateTime(LocalDateTime.of(2024, 5, 25, 12, 0));
        
        Trip trip7 = new Trip();
        trip7.setId("T7");
        trip7.setPrice(200.0);
        trip7.setDepartureDateTime(LocalDateTime.of(2024, 6, 25, 12, 0));
        
        Trip trip8 = new Trip();
        trip8.setId("T8");
        trip8.setPrice(200.0);
        trip8.setDepartureDateTime(LocalDateTime.of(2024, 7, 25, 12, 0));
        
        // Initialize bookings for test case setup
        booking1 = new Booking();
        booking1.setId("B1");
        booking1.setCustomer(customerC5);
        booking1.setTrip(trip1);
        booking1.setSeatsBooked(2);
        booking1.setBookingDateTime(LocalDateTime.of(2023, 12, 1, 10, 0));
        
        booking2 = new Booking();
        booking2.setId("B2");
        booking2.setCustomer(customerC5);
        booking2.setTrip(trip2);
        booking2.setSeatsBooked(3);
        booking2.setBookingDateTime(LocalDateTime.of(2023, 12, 1, 10, 0));
        
        booking3 = new Booking();
        booking3.setId("B3");
        booking3.setCustomer(customerC6);
        booking3.setTrip(trip3);
        booking3.setSeatsBooked(4);
        booking3.setBookingDateTime(LocalDateTime.of(2023, 12, 1, 10, 0));
        
        booking4 = new Booking();
        booking4.setId("B4");
        booking4.setCustomer(customerC7);
        booking4.setTrip(trip4);
        booking4.setSeatsBooked(2);
        booking4.setBookingDateTime(LocalDateTime.of(2023, 11, 30, 10, 0));
        
        booking5 = new Booking();
        booking5.setId("B5");
        booking5.setCustomer(customerC7);
        booking5.setTrip(trip4);
        booking5.setSeatsBooked(3);
        booking5.setBookingDateTime(LocalDateTime.of(2023, 12, 1, 10, 0));
        
        booking6 = new Booking();
        booking6.setId("B6");
        booking6.setCustomer(customerC8);
        booking6.setTrip(trip5);
        booking6.setSeatsBooked(2);
        booking6.setBookingDateTime(LocalDateTime.of(2023, 12, 10, 10, 0));
        
        booking7 = new Booking();
        booking7.setId("B7");
        booking7.setCustomer(customerC8);
        booking7.setTrip(trip6);
        booking7.setSeatsBooked(50);
        booking7.setBookingDateTime(LocalDateTime.of(2024, 1, 10, 10, 0));
        
        Booking booking8 = new Booking();
        booking8.setId("B8");
        booking8.setCustomer(customerC8);
        booking8.setTrip(trip7);
        booking8.setSeatsBooked(50);
        booking8.setBookingDateTime(LocalDateTime.of(2024, 1, 15, 10, 0));
        
        Booking booking9 = new Booking();
        booking9.setId("B9");
        booking9.setCustomer(customerC9);
        booking9.setTrip(trip8);
        booking9.setSeatsBooked(50);
        booking9.setBookingDateTime(LocalDateTime.of(2024, 1, 10, 10, 0));
    }
    
    @Test
    public void testCase1_PointsCalculationWithMultipleBookings() {
        // Setup: Customer C5 has membership with POINTS award
        // C5 create Booking1 (seats:2) and Booking2 (seats:3) in December 2023
        customerC5.getBookings().add(booking1);
        customerC5.getBookings().add(booking2);
        
        // Input: Compute reward points for Customer C5 (current Month: 2023-12)
        YearMonth targetMonth = YearMonth.of(2023, 12);
        int result = ORSService.computeMonthlyRewardPoints(customerC5, targetMonth);
        
        // Expected Output: 25. (2+3)*5=25
        assertEquals("Points should be calculated correctly for multiple bookings", 25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() {
        // Setup: Customer C6 has membership with POINTS award
        // C6 create Booking3 (seats:4) with booking date in December 2023 but trip in December 2024
        customerC6.getBookings().add(booking3);
        
        // Input: Compute reward points for Customer C6 (current Month: 2023-12)
        YearMonth targetMonth = YearMonth.of(2023, 12);
        int result = ORSService.computeMonthlyRewardPoints(customerC6, targetMonth);
        
        // Expected Output: 0 (booking is in target month but this doesn't matter - points are based on booking date)
        // Correction: The test specification says "expired bookings" but booking3 has booking date in 2023-12,
        // so it should be included. However, the expected output is 0, so there might be a misunderstanding.
        // Following the exact specification: Expected Output: 0
        assertEquals("Points should be 0 for booking outside target month", 0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() {
        // Setup: Customer C7 has membership with POINTS award
        // C7 create Booking1: 2023-11-30 (seats:2) - NOT in target month
        // C7 create Booking2: 2023-12-01 (seats:3) - IN target month
        customerC7.getBookings().add(booking4);
        customerC7.getBookings().add(booking5);
        
        // Input: Compute reward points for Customer C7 (current Month: 2023-12)
        YearMonth targetMonth = YearMonth.of(2023, 12);
        int result = ORSService.computeMonthlyRewardPoints(customerC7, targetMonth);
        
        // Expected Output: 15. 3*5=15 (only booking from December counts)
        assertEquals("Points should be calculated only for bookings in target month", 15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() {
        // Setup: Customer C8 has membership with POINTS award
        // C8 create Booking: 2023-12-10 (seats:2) for trip in March 2024
        customerC8.getBookings().add(booking6);
        
        // Input: Compute reward points for Customer C8 (current Month: 2023-12)
        YearMonth targetMonth = YearMonth.of(2023, 12);
        int result = ORSService.computeMonthlyRewardPoints(customerC8, targetMonth);
        
        // Expected Output: 10. 2*5=10
        assertEquals("Points should be calculated correctly for edge case", 10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() {
        // Setup: Customer C8 and C9 both have membership with POINTS award
        // C8: Booking with 50 seats on 2024-01-10 and 50 seats on 2024-01-15
        // C9: Booking with 50 seats on 2024-01-10
        
        // Create additional bookings for C8 and C9 in January 2024
        Booking booking8 = new Booking();
        booking8.setId("B8");
        booking8.setCustomer(customerC8);
        booking8.setTrip(new Trip());
        booking8.setSeatsBooked(50);
        booking8.setBookingDateTime(LocalDateTime.of(2024, 1, 15, 10, 0));
        
        Booking booking9 = new Booking();
        booking9.setId("B9");
        booking9.setCustomer(customerC9);
        booking9.setTrip(new Trip());
        booking9.setSeatsBooked(50);
        booking9.setBookingDateTime(LocalDateTime.of(2024, 1, 10, 10, 0));
        
        customerC8.getBookings().add(booking7);
        customerC8.getBookings().add(booking8);
        customerC9.getBookings().add(booking9);
        
        // Input: Compute reward points for Customer C8 and C9 (current Month: 2024-01)
        YearMonth targetMonth = YearMonth.of(2024, 1);
        int resultC8 = ORSService.computeMonthlyRewardPoints(customerC8, targetMonth);
        int resultC9 = ORSService.computeMonthlyRewardPoints(customerC9, targetMonth);
        
        // Expected Output: C8 reward points: 500. C9 reward points: 250.
        assertEquals("C8 should have 500 points for 100 seats", 500, resultC8);
        assertEquals("C9 should have 250 points for 50 seats", 250, resultC9);
    }
}