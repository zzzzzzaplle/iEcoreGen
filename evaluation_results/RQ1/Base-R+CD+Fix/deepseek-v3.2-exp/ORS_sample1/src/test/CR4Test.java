import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR4Test {
    
    private Customer customer;
    private Trip trip1, trip2, trip3;
    private Booking booking1, booking2, booking3, booking4, booking5;
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
        trip1.setNumberOfSeats(10);
        
        trip2 = new Trip();
        trip2.setDepartureDate("2023-12-26");
        trip2.setDepartureTime("12:00");
        trip2.setNumberOfSeats(10);
        
        trip3 = new Trip();
        trip3.setDepartureDate("2024-03-25");
        trip3.setDepartureTime("12:00");
        trip3.setNumberOfSeats(100);
        
        // Initialize bookings list for each trip
        trip1.setBookings(new ArrayList<>());
        trip2.setBookings(new ArrayList<>());
        trip3.setBookings(new ArrayList<>());
    }
    
    @Test
    public void testCase1_PointsCalculationWithMultipleBookings() throws Exception {
        // Setup: Customer C5 has membership with POINTS award
        customer = new Customer();
        customer.setMembershipPackage(membership);
        customer.setID("C5");
        
        // Create booking1: 2 seats for trip1
        booking1 = new Booking();
        booking1.setCustomer(customer);
        booking1.setTrip(trip1);
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(createDate("2023-12-10 10:00"));
        trip1.getBookings().add(booking1);
        
        // Create booking2: 3 seats for trip2
        booking2 = new Booking();
        booking2.setCustomer(customer);
        booking2.setTrip(trip2);
        booking2.setNumberOfSeats(3);
        booking2.setBookingDate(createDate("2023-12-11 10:00"));
        trip2.getBookings().add(booking2);
        
        // Test: Compute reward points for December 2023
        int result = trip1.calculateMonthlyPoints(customer, "2023-12");
        
        // Expected: (2 + 3) * 5 = 25 points
        assertEquals("Points should be calculated correctly for multiple bookings", 25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() throws Exception {
        // Setup: Customer C6 has membership with POINTS award
        customer = new Customer();
        customer.setMembershipPackage(membership);
        customer.setID("C6");
        
        // Create booking3: 4 seats for trip with departure in December 2024
        Trip futureTrip = new Trip();
        futureTrip.setDepartureDate("2024-12-26");
        futureTrip.setDepartureTime("12:00");
        futureTrip.setNumberOfSeats(10);
        futureTrip.setBookings(new ArrayList<>());
        
        booking3 = new Booking();
        booking3.setCustomer(customer);
        booking3.setTrip(futureTrip);
        booking3.setNumberOfSeats(4);
        booking3.setBookingDate(createDate("2023-12-10 10:00"));
        futureTrip.getBookings().add(booking3);
        
        // Test: Compute reward points for December 2023
        int result = futureTrip.calculateMonthlyPoints(customer, "2023-12");
        
        // Expected: 0 points (booking is for future month)
        assertEquals("Points should be 0 for expired bookings", 0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() throws Exception {
        // Setup: Customer C7 has membership with POINTS award
        customer = new Customer();
        customer.setMembershipPackage(membership);
        customer.setID("C7");
        
        // Create booking1: 2 seats booked on November 30, 2023
        booking1 = new Booking();
        booking1.setCustomer(customer);
        booking1.setTrip(trip1);
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(createDate("2023-11-30 10:00"));
        trip1.getBookings().add(booking1);
        
        // Create booking2: 3 seats booked on December 1, 2023
        booking2 = new Booking();
        booking2.setCustomer(customer);
        booking2.setTrip(trip1);
        booking2.setNumberOfSeats(3);
        booking2.setBookingDate(createDate("2023-12-01 10:00"));
        trip1.getBookings().add(booking2);
        
        // Test: Compute reward points for December 2023
        int result = trip1.calculateMonthlyPoints(customer, "2023-12");
        
        // Expected: 3 * 5 = 15 points (only December booking counts)
        assertEquals("Points should only include bookings from target month", 15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() throws Exception {
        // Setup: Customer C8 has membership with POINTS award
        customer = new Customer();
        customer.setMembershipPackage(membership);
        customer.setID("C8");
        
        // Create booking: 2 seats booked on December 10, 2023 for March 2024 trip
        booking1 = new Booking();
        booking1.setCustomer(customer);
        booking1.setTrip(trip3);
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(createDate("2023-12-10 10:00"));
        trip3.getBookings().add(booking1);
        
        // Test: Compute reward points for December 2023
        int result = trip3.calculateMonthlyPoints(customer, "2023-12");
        
        // Expected: 2 * 5 = 10 points
        assertEquals("Points should be calculated correctly for edge case", 10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() throws Exception {
        // Setup: Two customers with membership packages
        Customer customer8 = new Customer();
        customer8.setMembershipPackage(membership);
        customer8.setID("C8");
        
        Customer customer9 = new Customer();
        customer9.setMembershipPackage(membership);
        customer9.setID("C9");
        
        // Create trips for 2024
        Trip trip4 = new Trip();
        trip4.setDepartureDate("2024-05-25");
        trip4.setDepartureTime("12:00");
        trip4.setNumberOfSeats(100);
        trip4.setBookings(new ArrayList<>());
        
        Trip trip5 = new Trip();
        trip5.setDepartureDate("2024-06-25");
        trip5.setDepartureTime("12:00");
        trip5.setNumberOfSeats(100);
        trip5.setBookings(new ArrayList<>());
        
        Trip trip6 = new Trip();
        trip6.setDepartureDate("2024-07-25");
        trip6.setDepartureTime("12:00");
        trip6.setNumberOfSeats(100);
        trip6.setBookings(new ArrayList<>());
        
        // C8 bookings
        booking1 = new Booking();
        booking1.setCustomer(customer8);
        booking1.setTrip(trip4);
        booking1.setNumberOfSeats(50);
        booking1.setBookingDate(createDate("2024-01-10 10:00"));
        trip4.getBookings().add(booking1);
        
        booking2 = new Booking();
        booking2.setCustomer(customer8);
        booking2.setTrip(trip5);
        booking2.setNumberOfSeats(50);
        booking2.setBookingDate(createDate("2024-01-15 10:00"));
        trip5.getBookings().add(booking2);
        
        // C9 booking
        booking3 = new Booking();
        booking3.setCustomer(customer9);
        booking3.setTrip(trip6);
        booking3.setNumberOfSeats(50);
        booking3.setBookingDate(createDate("2024-01-10 10:00"));
        trip6.getBookings().add(booking3);
        
        // Test: Compute reward points for January 2024
        int resultC8Trip4 = trip4.calculateMonthlyPoints(customer8, "2024-01");
        int resultC8Trip5 = trip5.calculateMonthlyPoints(customer8, "2024-01");
        int resultC9 = trip6.calculateMonthlyPoints(customer9, "2024-01");
        
        int totalC8 = resultC8Trip4 + resultC8Trip5;
        
        // Expected: C8: (50 + 50) * 5 = 500 points, C9: 50 * 5 = 250 points
        assertEquals("C8 should have 500 points for large seat bookings", 500, totalC8);
        assertEquals("C9 should have 250 points for large seat booking", 250, resultC9);
    }
    
    // Helper method to create Date objects from string
    private Date createDate(String dateTime) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return formatter.parse(dateTime);
    }
}