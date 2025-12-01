import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR4Test {
    
    private Customer customer;
    private MembershipPackage membershipPackage;
    private SimpleDateFormat monthFormat;
    private SimpleDateFormat dateTimeFormat;
    
    @Before
    public void setUp() {
        membershipPackage = new MembershipPackage();
        customer = new Customer();
        monthFormat = new SimpleDateFormat("yyyy-MM");
        dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_PointsCalculationWithMultipleBookings() throws Exception {
        // Setup
        membershipPackage.setAwards(new Award[]{Award.POINTS});
        customer.setMembershipPackage(membershipPackage);
        
        // Create trips and bookings
        Trip trip1 = new Trip();
        trip1.setPrice(200.0);
        trip1.setDepartureDate(dateTimeFormat.parse("2023-12-25 12:00:00"));
        
        Trip trip2 = new Trip();
        trip2.setPrice(100.0);
        trip2.setDepartureDate(dateTimeFormat.parse("2023-12-26 12:00:00"));
        
        // Book trips
        customer.bookTrip(trip1, 2);
        customer.bookTrip(trip2, 3);
        
        // Test
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("Total points should be 25 for 5 seats across two bookings", 25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() throws Exception {
        // Setup
        membershipPackage.setAwards(new Award[]{Award.POINTS});
        customer.setMembershipPackage(membershipPackage);
        
        // Create trip and booking for next year
        Trip trip = new Trip();
        trip.setPrice(100.0);
        trip.setDepartureDate(dateTimeFormat.parse("2024-12-26 12:00:00"));
        
        // Book trip
        customer.bookTrip(trip, 4);
        
        // Test
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("No points should be awarded for bookings outside current month", 0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() throws Exception {
        // Setup
        membershipPackage.setAwards(new Award[]{Award.POINTS});
        customer.setMembershipPackage(membershipPackage);
        
        // Create trips
        Trip trip1 = new Trip();
        trip1.setPrice(200.0);
        trip1.setDepartureDate(dateTimeFormat.parse("2023-12-25 12:00:00"));
        
        Trip trip2 = new Trip();
        trip2.setPrice(200.0);
        trip2.setDepartureDate(dateTimeFormat.parse("2023-12-25 12:00:00"));
        
        // Create bookings with different dates
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(2);
        booking1.setCustomer(customer);
        booking1.setTrip(trip1);
        booking1.setBookingDate(dateTimeFormat.parse("2023-11-30 10:00:00"));
        
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(3);
        booking2.setCustomer(customer);
        booking2.setTrip(trip2);
        booking2.setBookingDate(dateTimeFormat.parse("2023-12-01 10:00:00"));
        
        // Add bookings to trips
        trip1.getBookings().add(booking1);
        trip2.getBookings().add(booking2);
        
        // Test
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("Only December bookings should be counted (3 seats)", 15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() throws Exception {
        // Setup
        membershipPackage.setAwards(new Award[]{Award.POINTS});
        customer.setMembershipPackage(membershipPackage);
        
        // Create trip
        Trip trip = new Trip();
        trip.setPrice(200.0);
        trip.setDepartureDate(dateTimeFormat.parse("2024-03-25 12:00:00"));
        
        // Create booking
        Booking booking = new Booking();
        booking.setNumberOfSeats(2);
        booking.setCustomer(customer);
        booking.setTrip(trip);
        booking.setBookingDate(dateTimeFormat.parse("2023-12-10 10:00:00"));
        
        // Add booking to trip
        trip.getBookings().add(booking);
        
        // Test
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("Should get 10 points for 2 seats booked in December", 10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() throws Exception {
        // Setup
        Customer customerC8 = new Customer();
        Customer customerC9 = new Customer();
        
        MembershipPackage mpC8 = new MembershipPackage();
        mpC8.setAwards(new Award[]{Award.POINTS});
        customerC8.setMembershipPackage(mpC8);
        
        MembershipPackage mpC9 = new MembershipPackage();
        mpC9.setAwards(new Award[]{Award.POINTS});
        customerC9.setMembershipPackage(mpC9);
        
        // Create trips
        Trip trip1 = new Trip();
        trip1.setPrice(150.0);
        trip1.setDepartureDate(dateTimeFormat.parse("2024-05-25 12:00:00"));
        
        Trip trip2 = new Trip();
        trip2.setPrice(200.0);
        trip2.setDepartureDate(dateTimeFormat.parse("2024-06-25 12:00:00"));
        
        Trip trip3 = new Trip();
        trip3.setPrice(200.0);
        trip3.setDepartureDate(dateTimeFormat.parse("2024-07-25 12:00:00"));
        
        // Create bookings
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(50);
        booking1.setCustomer(customerC8);
        booking1.setTrip(trip1);
        booking1.setBookingDate(dateTimeFormat.parse("2024-01-10 10:00:00"));
        
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(50);
        booking2.setCustomer(customerC8);
        booking2.setTrip(trip2);
        booking2.setBookingDate(dateTimeFormat.parse("2024-01-15 10:00:00"));
        
        Booking booking3 = new Booking();
        booking3.setNumberOfSeats(50);
        booking3.setCustomer(customerC9);
        booking3.setTrip(trip3);
        booking3.setBookingDate(dateTimeFormat.parse("2024-01-10 10:00:00"));
        
        // Add bookings to trips
        trip1.getBookings().add(booking1);
        trip2.getBookings().add(booking2);
        trip3.getBookings().add(booking3);
        
        // Test
        int resultC8 = customerC8.computeMonthlyRewardPoints("2024-01");
        int resultC9 = customerC9.computeMonthlyRewardPoints("2024-01");
        
        // Verify
        assertEquals("Customer C8 should get 500 points for 100 seats", 500, resultC8);
        assertEquals("Customer C9 should get 250 points for 50 seats", 250, resultC9);
    }
}