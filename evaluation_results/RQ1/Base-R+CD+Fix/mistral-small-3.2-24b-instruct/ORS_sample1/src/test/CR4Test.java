import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR4Test {
    private Customer customer;
    private Trip trip1;
    private Trip trip2;
    private Trip trip3;
    private Booking booking1;
    private Booking booking2;
    private Booking booking3;
    private MembershipPackage membershipPackage;
    
    @Before
    public void setUp() {
        // Create membership package with POINTS award
        membershipPackage = new MembershipPackage();
        membershipPackage.setAwards(new Award[]{Award.POINTS});
        
        // Create customer
        customer = new Customer();
        customer.setMembershipPackage(membershipPackage);
        customer.setTrips(new ArrayList<>());
    }
    
    @Test
    public void testCase1_PointsCalculationWithMultipleBookings() throws Exception {
        // Setup: Customer C5 has membership with POINTS award
        Customer c5 = new Customer();
        c5.setMembershipPackage(membershipPackage);
        c5.setTrips(new ArrayList<>());
        
        // Create trips
        Trip trip1 = new Trip();
        trip1.setBookings(new ArrayList<>());
        
        Trip trip2 = new Trip();
        trip2.setBookings(new ArrayList<>());
        
        // Create bookings for December 2023
        Booking booking1 = new Booking();
        booking1.setCustomer(c5);
        booking1.setTrip(trip1);
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(createDate("2023-12-01 10:00:00"));
        
        Booking booking2 = new Booking();
        booking2.setCustomer(c5);
        booking2.setTrip(trip2);
        booking2.setNumberOfSeats(3);
        booking2.setBookingDate(createDate("2023-12-02 10:00:00"));
        
        // Add bookings to trips
        trip1.getBookings().add(booking1);
        trip2.getBookings().add(booking2);
        
        // Add trips to customer
        c5.getTrips().add(trip1);
        c5.getTrips().add(trip2);
        
        // Test: Compute reward points for December 2023
        int result = c5.computeMonthlyRewardPoints("2023-12");
        
        // Verify: (2+3)*5=25
        assertEquals("Points calculation with multiple bookings should return 25", 25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() throws Exception {
        // Setup: Customer C6 has membership with POINTS award
        Customer c6 = new Customer();
        c6.setMembershipPackage(membershipPackage);
        c6.setTrips(new ArrayList<>());
        
        // Create trip for December 2024 (outside target month)
        Trip trip = new Trip();
        trip.setBookings(new ArrayList<>());
        
        // Create booking for December 2024
        Booking booking = new Booking();
        booking.setCustomer(c6);
        booking.setTrip(trip);
        booking.setNumberOfSeats(4);
        booking.setBookingDate(createDate("2024-12-26 10:00:00"));
        
        // Add booking to trip
        trip.getBookings().add(booking);
        
        // Add trip to customer
        c6.getTrips().add(trip);
        
        // Test: Compute reward points for December 2023
        int result = c6.computeMonthlyRewardPoints("2023-12");
        
        // Verify: 0 points (booking is in different year)
        assertEquals("Zero points with expired bookings should return 0", 0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() throws Exception {
        // Setup: Customer C7 has membership with POINTS award
        Customer c7 = new Customer();
        c7.setMembershipPackage(membershipPackage);
        c7.setTrips(new ArrayList<>());
        
        // Create trips
        Trip trip1 = new Trip();
        trip1.setBookings(new ArrayList<>());
        
        Trip trip2 = new Trip();
        trip2.setBookings(new ArrayList<>());
        
        // Create bookings - one in November, one in December
        Booking booking1 = new Booking();
        booking1.setCustomer(c7);
        booking1.setTrip(trip1);
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(createDate("2023-11-30 10:00:00"));
        
        Booking booking2 = new Booking();
        booking2.setCustomer(c7);
        booking2.setTrip(trip2);
        booking2.setNumberOfSeats(3);
        booking2.setBookingDate(createDate("2023-12-01 10:00:00"));
        
        // Add bookings to trips
        trip1.getBookings().add(booking1);
        trip2.getBookings().add(booking2);
        
        // Add trips to customer
        c7.getTrips().add(trip1);
        c7.getTrips().add(trip2);
        
        // Test: Compute reward points for December 2023
        int result = c7.computeMonthlyRewardPoints("2023-12");
        
        // Verify: Only December booking counts - 3*5=15
        assertEquals("Partial month inclusion should return 15", 15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() throws Exception {
        // Setup: Customer C8 has membership with POINTS award
        Customer c8 = new Customer();
        c8.setMembershipPackage(membershipPackage);
        c8.setTrips(new ArrayList<>());
        
        // Create trip
        Trip trip = new Trip();
        trip.setBookings(new ArrayList<>());
        
        // Create booking for December 2023
        Booking booking = new Booking();
        booking.setCustomer(c8);
        booking.setTrip(trip);
        booking.setNumberOfSeats(2);
        booking.setBookingDate(createDate("2023-12-10 10:00:00"));
        
        // Add booking to trip
        trip.getBookings().add(booking);
        
        // Add trip to customer
        c8.getTrips().add(trip);
        
        // Test: Compute reward points for December 2023
        int result = c8.computeMonthlyRewardPoints("2023-12");
        
        // Verify: 2*5=10
        assertEquals("Multiple seats edge case should return 10", 10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() throws Exception {
        // Setup: Customer C8 and C9 have membership with POINTS award
        Customer c8 = new Customer();
        c8.setMembershipPackage(membershipPackage);
        c8.setTrips(new ArrayList<>());
        
        Customer c9 = new Customer();
        c9.setMembershipPackage(membershipPackage);
        c9.setTrips(new ArrayList<>());
        
        // Create trips for C8
        Trip trip1_c8 = new Trip();
        trip1_c8.setBookings(new ArrayList<>());
        
        Trip trip2_c8 = new Trip();
        trip2_c8.setBookings(new ArrayList<>());
        
        // Create trip for C9
        Trip trip_c9 = new Trip();
        trip_c9.setBookings(new ArrayList<>());
        
        // Create bookings for C8 (both in January 2024)
        Booking booking1_c8 = new Booking();
        booking1_c8.setCustomer(c8);
        booking1_c8.setTrip(trip1_c8);
        booking1_c8.setNumberOfSeats(50);
        booking1_c8.setBookingDate(createDate("2024-01-10 10:00:00"));
        
        Booking booking2_c8 = new Booking();
        booking2_c8.setCustomer(c8);
        booking2_c8.setTrip(trip2_c8);
        booking2_c8.setNumberOfSeats(50);
        booking2_c8.setBookingDate(createDate("2024-01-15 10:00:00"));
        
        // Create booking for C9 (in January 2024)
        Booking booking_c9 = new Booking();
        booking_c9.setCustomer(c9);
        booking_c9.setTrip(trip_c9);
        booking_c9.setNumberOfSeats(50);
        booking_c9.setBookingDate(createDate("2024-01-10 10:00:00"));
        
        // Add bookings to trips
        trip1_c8.getBookings().add(booking1_c8);
        trip2_c8.getBookings().add(booking2_c8);
        trip_c9.getBookings().add(booking_c9);
        
        // Add trips to customers
        c8.getTrips().add(trip1_c8);
        c8.getTrips().add(trip2_c8);
        c9.getTrips().add(trip_c9);
        
        // Test: Compute reward points for January 2024
        int result_c8 = c8.computeMonthlyRewardPoints("2024-01");
        int result_c9 = c9.computeMonthlyRewardPoints("2024-01");
        
        // Verify: C8: (50+50)*5=500, C9: 50*5=250
        assertEquals("C8 large seat quantity should return 500", 500, result_c8);
        assertEquals("C9 large seat quantity should return 250", 250, result_c9);
    }
    
    // Helper method to create Date objects from string
    private Date createDate(String dateString) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.parse(dateString);
    }
}