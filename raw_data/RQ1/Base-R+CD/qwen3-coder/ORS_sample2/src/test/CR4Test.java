import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR4Test {
    
    private Customer customer;
    private Trip trip1, trip2, trip3, trip4, trip5, trip6;
    private Booking booking1, booking2, booking3, booking4, booking5, booking6, booking7;
    private MembershipPackage membership;
    
    @Before
    public void setUp() {
        // Create membership package with POINTS award
        membership = new MembershipPackage();
        membership.setAwards(new Award[]{Award.POINTS});
        
        // Create trips
        trip1 = new Trip();
        trip1.setDepartureDate("2023-12-25");
        trip1.setDepartureTime("12:00");
        trip1.setPrice(200.0);
        
        trip2 = new Trip();
        trip2.setDepartureDate("2023-12-26");
        trip2.setDepartureTime("12:00");
        trip2.setPrice(100.0);
        
        trip3 = new Trip();
        trip3.setDepartureDate("2024-12-26");
        trip3.setDepartureTime("12:00");
        trip3.setPrice(100.0);
        
        trip4 = new Trip();
        trip4.setDepartureDate("2024-03-25");
        trip4.setDepartureTime("12:00");
        trip4.setPrice(200.0);
        
        trip5 = new Trip();
        trip5.setDepartureDate("2024-05-25");
        trip5.setDepartureTime("12:00");
        trip5.setPrice(150.0);
        
        trip6 = new Trip();
        trip6.setDepartureDate("2024-06-25");
        trip6.setDepartureTime("12:00");
        trip6.setPrice(200.0);
        
        trip7 = new Trip();
        trip7.setDepartureDate("2024-07-25");
        trip7.setDepartureTime("12:00");
        trip7.setPrice(200.0);
    }
    
    @Test
    public void testCase1_PointsCalculationWithMultipleBookings() {
        // Setup: Customer C5 has membership with POINTS award
        Customer C5 = new Customer();
        C5.setMembershipPackage(membership);
        
        // Create bookings for C5
        Booking booking1 = new Booking();
        booking1.setCustomer(C5);
        booking1.setTrip(trip1);
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(createDate("2023-12-01 10:00:00"));
        
        Booking booking2 = new Booking();
        booking2.setCustomer(C5);
        booking2.setTrip(trip2);
        booking2.setNumberOfSeats(3);
        booking2.setBookingDate(createDate("2023-12-02 10:00:00"));
        
        // Add bookings to trips
        trip1.getBookings().add(booking1);
        trip2.getBookings().add(booking2);
        
        // Test: Compute reward points for Customer C5 (current Month: 2023-12)
        int result = C5.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 25. (2+3)*5=25
        assertEquals("Points calculation with multiple bookings should be 25", 25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() {
        // Setup: Customer C6 has membership with POINTS award
        Customer C6 = new Customer();
        C6.setMembershipPackage(membership);
        
        // Create booking for C6 in future month
        Booking booking3 = new Booking();
        booking3.setCustomer(C6);
        booking3.setTrip(trip3);
        booking3.setNumberOfSeats(4);
        booking3.setBookingDate(createDate("2023-12-01 10:00:00"));
        
        // Add booking to trip
        trip3.getBookings().add(booking3);
        
        // Test: Compute reward points for Customer C6 (current Month: 2023-12)
        int result = C6.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 0
        assertEquals("Zero points with expired bookings should be 0", 0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() {
        // Setup: Customer C7 has membership with POINTS award
        Customer C7 = new Customer();
        C7.setMembershipPackage(membership);
        
        // Create bookings for C7 - one in November, one in December
        Booking booking1 = new Booking();
        booking1.setCustomer(C7);
        booking1.setTrip(trip1);
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(createDate("2023-11-30 10:00:00"));
        
        Booking booking2 = new Booking();
        booking2.setCustomer(C7);
        booking2.setTrip(trip1);
        booking2.setNumberOfSeats(3);
        booking2.setBookingDate(createDate("2023-12-01 10:00:00"));
        
        // Add bookings to trip
        trip1.getBookings().add(booking1);
        trip1.getBookings().add(booking2);
        
        // Test: Compute reward points for Customer C7 (current Month: 2023-12)
        int result = C7.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 15. 3*5=15
        assertEquals("Partial month inclusion should only count December bookings", 15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() {
        // Setup: Customer C8 has membership with POINTS award
        Customer C8 = new Customer();
        C8.setMembershipPackage(membership);
        
        // Create booking for C8
        Booking booking = new Booking();
        booking.setCustomer(C8);
        booking.setTrip(trip4);
        booking.setNumberOfSeats(2);
        booking.setBookingDate(createDate("2023-12-10 10:00:00"));
        
        // Add booking to trip
        trip4.getBookings().add(booking);
        
        // Test: Compute reward points for Customer C8 (current Month: 2023-12)
        int result = C8.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 10. 2*5=10
        assertEquals("Multiple seats edge case should be 10", 10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() {
        // Setup: Customer C8 has membership with POINTS award
        Customer C8 = new Customer();
        C8.setMembershipPackage(membership);
        
        // Setup: Customer C9 has membership with POINTS award
        Customer C9 = new Customer();
        C9.setMembershipPackage(membership);
        
        // Create bookings for C8
        Booking booking1 = new Booking();
        booking1.setCustomer(C8);
        booking1.setTrip(trip5);
        booking1.setNumberOfSeats(50);
        booking1.setBookingDate(createDate("2024-01-10 10:00:00"));
        
        Booking booking2 = new Booking();
        booking2.setCustomer(C8);
        booking2.setTrip(trip6);
        booking2.setNumberOfSeats(50);
        booking2.setBookingDate(createDate("2024-01-15 10:00:00"));
        
        // Create booking for C9
        Booking booking3 = new Booking();
        booking3.setCustomer(C9);
        booking3.setTrip(trip7);
        booking3.setNumberOfSeats(50);
        booking3.setBookingDate(createDate("2024-01-10 10:00:00"));
        
        // Add bookings to trips
        trip5.getBookings().add(booking1);
        trip6.getBookings().add(booking2);
        trip7.getBookings().add(booking3);
        
        // Test: Compute reward points for Customer C8 (current Month: 2024-01)
        int resultC8 = C8.computeMonthlyRewardPoints("2024-01");
        
        // Test: Compute reward points for Customer C9 (current Month: 2024-01)
        int resultC9 = C9.computeMonthlyRewardPoints("2024-01");
        
        // Expected Output: C8 reward points: 500. C9 reward points: 250.
        assertEquals("C8 reward points for large seat quantity should be 500", 500, resultC8);
        assertEquals("C9 reward points for large seat quantity should be 250", 250, resultC9);
    }
    
    // Helper method to create Date objects from string
    private Date createDate(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.parse(dateString);
        } catch (Exception e) {
            return new Date();
        }
    }
}