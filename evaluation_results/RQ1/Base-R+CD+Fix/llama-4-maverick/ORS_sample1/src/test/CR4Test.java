import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR4Test {
    
    private Customer customer;
    private SimpleDateFormat sdf;
    
    @Before
    public void setUp() {
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_PointsCalculationWithMultipleBookings() throws ParseException {
        // Setup
        customer = new Customer();
        MembershipPackage membershipPackage = new MembershipPackage();
        membershipPackage.setAwards(new Award[]{Award.POINTS});
        customer.setMembershipPackage(membershipPackage);
        
        // Mock getBookings() to return test bookings
        List<Booking> testBookings = new ArrayList<>();
        
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(2);
        Trip trip1 = new Trip();
        trip1.setDepartureDate(sdf.parse("2023-12-25 12:00:00"));
        booking1.setTrip(trip1);
        booking1.setBookingDate(sdf.parse("2023-12-01 10:00:00"));
        testBookings.add(booking1);
        
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(3);
        Trip trip2 = new Trip();
        trip2.setDepartureDate(sdf.parse("2023-12-26 12:00:00"));
        booking2.setTrip(trip2);
        booking2.setBookingDate(sdf.parse("2023-12-01 10:00:00"));
        testBookings.add(booking2);
        
        // Use reflection to set bookings for customer
        java.lang.reflect.Field bookingsField;
        try {
            bookingsField = Customer.class.getDeclaredField("bookings");
            bookingsField.setAccessible(true);
            bookingsField.set(customer, testBookings);
        } catch (Exception e) {
            fail("Failed to set bookings field: " + e.getMessage());
        }
        
        // Test
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("Total points should be (2+3)*5=25", 25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() throws ParseException {
        // Setup
        customer = new Customer();
        MembershipPackage membershipPackage = new MembershipPackage();
        membershipPackage.setAwards(new Award[]{Award.POINTS});
        customer.setMembershipPackage(membershipPackage);
        
        // Mock getBookings() to return test booking
        List<Booking> testBookings = new ArrayList<>();
        
        Booking booking3 = new Booking();
        booking3.setNumberOfSeats(4);
        Trip trip3 = new Trip();
        trip3.setDepartureDate(sdf.parse("2024-12-26 12:00:00"));
        booking3.setTrip(trip3);
        booking3.setBookingDate(sdf.parse("2023-12-01 10:00:00"));
        testBookings.add(booking3);
        
        // Use reflection to set bookings for customer
        java.lang.reflect.Field bookingsField;
        try {
            bookingsField = Customer.class.getDeclaredField("bookings");
            bookingsField.setAccessible(true);
            bookingsField.set(customer, testBookings);
        } catch (Exception e) {
            fail("Failed to set bookings field: " + e.getMessage());
        }
        
        // Test
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("No points should be earned for booking in different month", 0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() throws ParseException {
        // Setup
        customer = new Customer();
        MembershipPackage membershipPackage = new MembershipPackage();
        membershipPackage.setAwards(new Award[]{Award.POINTS});
        customer.setMembershipPackage(membershipPackage);
        
        // Mock getBookings() to return test bookings
        List<Booking> testBookings = new ArrayList<>();
        
        // Booking in November (should not be counted)
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(2);
        Trip trip1 = new Trip();
        trip1.setDepartureDate(sdf.parse("2023-12-25 12:00:00"));
        booking1.setTrip(trip1);
        booking1.setBookingDate(sdf.parse("2023-11-30 10:00:00"));
        testBookings.add(booking1);
        
        // Booking in December (should be counted)
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(3);
        Trip trip2 = new Trip();
        trip2.setDepartureDate(sdf.parse("2023-12-25 12:00:00"));
        booking2.setTrip(trip2);
        booking2.setBookingDate(sdf.parse("2023-12-01 10:00:00"));
        testBookings.add(booking2);
        
        // Use reflection to set bookings for customer
        java.lang.reflect.Field bookingsField;
        try {
            bookingsField = Customer.class.getDeclaredField("bookings");
            bookingsField.setAccessible(true);
            bookingsField.set(customer, testBookings);
        } catch (Exception e) {
            fail("Failed to set bookings field: " + e.getMessage());
        }
        
        // Test
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("Only December booking should be counted: 3*5=15", 15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() throws ParseException {
        // Setup
        customer = new Customer();
        MembershipPackage membershipPackage = new MembershipPackage();
        membershipPackage.setAwards(new Award[]{Award.POINTS});
        customer.setMembershipPackage(membershipPackage);
        
        // Mock getBookings() to return test booking
        List<Booking> testBookings = new ArrayList<>();
        
        Booking booking = new Booking();
        booking.setNumberOfSeats(2);
        Trip trip = new Trip();
        trip.setDepartureDate(sdf.parse("2024-03-25 12:00:00"));
        booking.setTrip(trip);
        booking.setBookingDate(sdf.parse("2023-12-10 10:00:00"));
        testBookings.add(booking);
        
        // Use reflection to set bookings for customer
        java.lang.reflect.Field bookingsField;
        try {
            bookingsField = Customer.class.getDeclaredField("bookings");
            bookingsField.setAccessible(true);
            bookingsField.set(customer, testBookings);
        } catch (Exception e) {
            fail("Failed to set bookings field: " + e.getMessage());
        }
        
        // Test
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("Points should be calculated based on seats: 2*5=10", 10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() throws ParseException {
        // Test Customer C8
        Customer customerC8 = new Customer();
        MembershipPackage membershipPackageC8 = new MembershipPackage();
        membershipPackageC8.setAwards(new Award[]{Award.POINTS});
        customerC8.setMembershipPackage(membershipPackageC8);
        
        // Mock getBookings() to return test bookings for C8
        List<Booking> testBookingsC8 = new ArrayList<>();
        
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(50);
        Trip trip1 = new Trip();
        trip1.setDepartureDate(sdf.parse("2024-05-25 12:00:00"));
        booking1.setTrip(trip1);
        booking1.setBookingDate(sdf.parse("2024-01-10 10:00:00"));
        testBookingsC8.add(booking1);
        
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(50);
        Trip trip2 = new Trip();
        trip2.setDepartureDate(sdf.parse("2024-06-25 12:00:00"));
        booking2.setTrip(trip2);
        booking2.setBookingDate(sdf.parse("2024-01-15 10:00:00"));
        testBookingsC8.add(booking2);
        
        // Use reflection to set bookings for customer C8
        java.lang.reflect.Field bookingsField;
        try {
            bookingsField = Customer.class.getDeclaredField("bookings");
            bookingsField.setAccessible(true);
            bookingsField.set(customerC8, testBookingsC8);
        } catch (Exception e) {
            fail("Failed to set bookings field: " + e.getMessage());
        }
        
        // Test Customer C9
        Customer customerC9 = new Customer();
        MembershipPackage membershipPackageC9 = new MembershipPackage();
        membershipPackageC9.setAwards(new Award[]{Award.POINTS});
        customerC9.setMembershipPackage(membershipPackageC9);
        
        // Mock getBookings() to return test bookings for C9
        List<Booking> testBookingsC9 = new ArrayList<>();
        
        Booking booking3 = new Booking();
        booking3.setNumberOfSeats(50);
        Trip trip3 = new Trip();
        trip3.setDepartureDate(sdf.parse("2024-07-25 12:00:00"));
        booking3.setTrip(trip3);
        booking3.setBookingDate(sdf.parse("2024-01-10 10:00:00"));
        testBookingsC9.add(booking3);
        
        // Use reflection to set bookings for customer C9
        try {
            bookingsField = Customer.class.getDeclaredField("bookings");
            bookingsField.setAccessible(true);
            bookingsField.set(customerC9, testBookingsC9);
        } catch (Exception e) {
            fail("Failed to set bookings field: " + e.getMessage());
        }
        
        // Test
        int resultC8 = customerC8.computeMonthlyRewardPoints("2024-01");
        int resultC9 = customerC9.computeMonthlyRewardPoints("2024-01");
        
        // Verify
        assertEquals("C8 points should be (50+50)*5=500", 500, resultC8);
        assertEquals("C9 points should be 50*5=250", 250, resultC9);
    }
}