import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR4Test {
    private Customer customer;
    private Trip trip1;
    private Trip trip2;
    private Booking booking1;
    private Booking booking2;
    private MembershipPackage membership;
    
    @Before
    public void setUp() {
        customer = new Customer();
        membership = new MembershipPackage();
        membership.setAwards(new Award[]{Award.POINTS});
        customer.setMembershipPackage(membership);
        
        trip1 = new Trip();
        trip2 = new Trip();
        
        booking1 = new Booking();
        booking2 = new Booking();
    }
    
    @Test
    public void testCase1_PointsCalculationWithMultipleBookings() {
        // Setup: Customer C5 has membership with POINTS award
        Customer c5 = new Customer();
        c5.setMembershipPackage(membership);
        
        // Create bookings for trips in December 2023
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(2);
        booking1.setCustomer(c5);
        
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(3);
        booking2.setCustomer(c5);
        
        // Create trips and add bookings
        Trip trip1 = new Trip();
        trip1.setDepartureTime("2023-12-25 12:00");
        trip1.getBookings().add(booking1);
        trip1.getBookings().add(booking2);
        
        // Mock the getAllBookedTrips method to return our test trips
        // Since getAllBookedTrips is private, we'll create a test helper
        // For this test, we assume the customer has these bookings
        
        // Expected: (2+3)*5 = 25
        int result = c5.computeMonthlyRewardPoints("2023-12");
        assertEquals(25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() {
        // Setup: Customer C6 has membership with POINTS award
        Customer c6 = new Customer();
        c6.setMembershipPackage(membership);
        
        // Create booking for trip in December 2024 (outside target month)
        Booking booking3 = new Booking();
        booking3.setNumberOfSeats(4);
        booking3.setCustomer(c6);
        
        Trip trip = new Trip();
        trip.setDepartureTime("2024-12-26 12:00");
        trip.getBookings().add(booking3);
        
        // Expected: 0 (booking is for different year)
        int result = c6.computeMonthlyRewardPoints("2023-12");
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() {
        // Setup: Customer C7 has membership with POINTS award
        Customer c7 = new Customer();
        c7.setMembershipPackage(membership);
        
        // Create bookings - one in November, one in December
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(2);
        booking1.setCustomer(c7);
        
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(3);
        booking2.setCustomer(c7);
        
        // Set booking dates
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            booking1.setBookingDate(dateFormat.parse("2023-11-30"));
            booking2.setBookingDate(dateFormat.parse("2023-12-01"));
        } catch (Exception e) {
            // Ignore for test
        }
        
        Trip trip1 = new Trip();
        trip1.setDepartureTime("2023-12-25 12:00");
        trip1.getBookings().add(booking1);
        trip1.getBookings().add(booking2);
        
        // Expected: Only December booking counts = 3*5 = 15
        int result = c7.computeMonthlyRewardPoints("2023-12");
        assertEquals(15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() {
        // Setup: Customer C8 has membership with POINTS award
        Customer c8 = new Customer();
        c8.setMembershipPackage(membership);
        
        // Create booking in December 2023
        Booking booking = new Booking();
        booking.setNumberOfSeats(2);
        booking.setCustomer(c8);
        
        // Set booking date to December 2023
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            booking.setBookingDate(dateFormat.parse("2023-12-10"));
        } catch (Exception e) {
            // Ignore for test
        }
        
        Trip trip = new Trip();
        trip.setDepartureTime("2024-03-25 12:00");
        trip.getBookings().add(booking);
        
        // Expected: 2*5 = 10
        int result = c8.computeMonthlyRewardPoints("2023-12");
        assertEquals(10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() {
        // Setup: Customers C8 and C9 have membership with POINTS award
        Customer c8 = new Customer();
        Customer c9 = new Customer();
        c8.setMembershipPackage(membership);
        c9.setMembershipPackage(membership);
        
        // Create bookings for C8
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(50);
        booking1.setCustomer(c8);
        
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(50);
        booking2.setCustomer(c8);
        
        // Create booking for C9
        Booking booking3 = new Booking();
        booking3.setNumberOfSeats(50);
        booking3.setCustomer(c9);
        
        // Set booking dates to January 2024
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            booking1.setBookingDate(dateFormat.parse("2024-01-10"));
            booking2.setBookingDate(dateFormat.parse("2024-01-15"));
            booking3.setBookingDate(dateFormat.parse("2024-01-10"));
        } catch (Exception e) {
            // Ignore for test
        }
        
        Trip trip1 = new Trip();
        trip1.setDepartureTime("2024-05-25 12:00");
        trip1.getBookings().add(booking1);
        
        Trip trip2 = new Trip();
        trip2.setDepartureTime("2024-06-25 12:00");
        trip2.getBookings().add(booking2);
        
        Trip trip3 = new Trip();
        trip3.setDepartureTime("2024-07-25 12:00");
        trip3.getBookings().add(booking3);
        
        // Expected: C8 = (50+50)*5 = 500, C9 = 50*5 = 250
        int resultC8 = c8.computeMonthlyRewardPoints("2024-01");
        int resultC9 = c9.computeMonthlyRewardPoints("2024-01");
        
        assertEquals(500, resultC8);
        assertEquals(250, resultC9);
    }
}