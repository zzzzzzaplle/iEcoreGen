import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR4Test {
    
    private Customer customer;
    private MembershipPackage membershipPackage;
    
    @Before
    public void setUp() {
        customer = new Customer();
        membershipPackage = new MembershipPackage();
    }
    
    @Test
    public void testCase1_PointsCalculationWithMultipleBookings() throws ParseException {
        // Setup: Customer C5 has membership with POINTS award
        membershipPackage.setAwards(new Award[]{Award.POINTS});
        customer.setMembershipPackage(membershipPackage);
        
        // Create Booking1 (seats:2) for trip (price: 200.0, departure: 2023-12-25 12:00)
        Trip trip1 = new Trip();
        trip1.setDepartureTime("2023-12-25 12:00");
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(2);
        booking1.setTrip(trip1);
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date bookingDate1 = format.parse("2023-12-01 10:00");
        booking1.setBookingDate(bookingDate1);
        
        // Create Booking2 (seats:3) for trip (price: 100.0, departure: 2023-12-26 12:00)
        Trip trip2 = new Trip();
        trip2.setDepartureTime("2023-12-26 12:00");
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(3);
        booking2.setTrip(trip2);
        
        Date bookingDate2 = format.parse("2023-12-02 10:00");
        booking2.setBookingDate(bookingDate2);
        
        // Add bookings to customer
        customer.setBookings(Arrays.asList(booking1, booking2));
        
        // Test: Compute reward points for Customer C5 (current Month: 2023-12)
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 25. (2+3)*5=25
        assertEquals(25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() throws ParseException {
        // Setup: Customer C6 has membership with POINTS award
        membershipPackage.setAwards(new Award[]{Award.POINTS});
        customer.setMembershipPackage(membershipPackage);
        
        // Create Booking3 (seats:4) for trip (price: 100.0, departure: 2024-12-26 12:00)
        Trip trip = new Trip();
        trip.setDepartureTime("2024-12-26 12:00");
        Booking booking = new Booking();
        booking.setNumberOfSeats(4);
        booking.setTrip(trip);
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date bookingDate = format.parse("2024-12-01 10:00");
        booking.setBookingDate(bookingDate);
        
        // Add booking to customer
        customer.setBookings(Arrays.asList(booking));
        
        // Test: Compute reward points for Customer C6 (current Month: 2023-12)
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() throws ParseException {
        // Setup: Customer C7 has membership with POINTS award
        membershipPackage.setAwards(new Award[]{Award.POINTS});
        customer.setMembershipPackage(membershipPackage);
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        // C7 create Booking1: 2023-11-30 10:00 (seats:2) for trip (price: 200.0, departure: 2023-12-25 12:00)
        Trip trip1 = new Trip();
        trip1.setDepartureTime("2023-12-25 12:00");
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(2);
        booking1.setTrip(trip1);
        Date bookingDate1 = format.parse("2023-11-30 10:00");
        booking1.setBookingDate(bookingDate1);
        
        // C7 create Booking2: 2023-12-01 10:00 (seats:3) for trip (price: 200.0, departure: 2023-12-25 12:00)
        Trip trip2 = new Trip();
        trip2.setDepartureTime("2023-12-25 12:00");
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(3);
        booking2.setTrip(trip2);
        Date bookingDate2 = format.parse("2023-12-01 10:00");
        booking2.setBookingDate(bookingDate2);
        
        // Add bookings to customer
        customer.setBookings(Arrays.asList(booking1, booking2));
        
        // Test: Compute reward points for Customer C7 (current Month: 2023-12)
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 15. 3*5=15
        assertEquals(15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() throws ParseException {
        // Setup: Customer C8 has membership with POINTS award
        membershipPackage.setAwards(new Award[]{Award.POINTS});
        customer.setMembershipPackage(membershipPackage);
        
        // C8 create Booking: 2023-12-10 10:00 (seats:2) for trip (price: 200.0, departure: 2024-03-25 12:00)
        Trip trip = new Trip();
        trip.setDepartureTime("2024-03-25 12:00");
        Booking booking = new Booking();
        booking.setNumberOfSeats(2);
        booking.setTrip(trip);
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date bookingDate = format.parse("2023-12-10 10:00");
        booking.setBookingDate(bookingDate);
        
        // Add booking to customer
        customer.setBookings(Arrays.asList(booking));
        
        // Test: Compute reward points for Customer C8 (current Month: 2023-12)
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 10. 2*5=10
        assertEquals(10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() throws ParseException {
        // Test C8 reward points
        Customer customer8 = new Customer();
        MembershipPackage membershipPackage8 = new MembershipPackage();
        membershipPackage8.setAwards(new Award[]{Award.POINTS});
        customer8.setMembershipPackage(membershipPackage8);
        
        // C8 create Booking: 2024-01-10 10:00 (seats:50) for trip (price: 150.0, departure: 2024-05-25 12:00)
        Trip trip1 = new Trip();
        trip1.setDepartureTime("2024-05-25 12:00");
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(50);
        booking1.setTrip(trip1);
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date bookingDate1 = format.parse("2024-01-10 10:00");
        booking1.setBookingDate(bookingDate1);
        
        // C8 create Booking: 2024-01-15 10:00 (seats:50) for trip (price: 200.0, departure: 2024-06-25 12:00)
        Trip trip2 = new Trip();
        trip2.setDepartureTime("2024-06-25 12:00");
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(50);
        booking2.setTrip(trip2);
        
        Date bookingDate2 = format.parse("2024-01-15 10:00");
        booking2.setBookingDate(bookingDate2);
        
        // Add bookings to customer C8
        customer8.setBookings(Arrays.asList(booking1, booking2));
        
        int resultC8 = customer8.computeMonthlyRewardPoints("2024-01");
        
        // Test C9 reward points
        Customer customer9 = new Customer();
        MembershipPackage membershipPackage9 = new MembershipPackage();
        membershipPackage9.setAwards(new Award[]{Award.POINTS});
        customer9.setMembershipPackage(membershipPackage9);
        
        // C9 create Booking: 2024-01-10 10:00 (seats:50) for trip (price: 200.0, departure: 2024-07-25 12:00)
        Trip trip3 = new Trip();
        trip3.setDepartureTime("2024-07-25 12:00");
        Booking booking3 = new Booking();
        booking3.setNumberOfSeats(50);
        booking3.setTrip(trip3);
        
        Date bookingDate3 = format.parse("2024-01-10 10:00");
        booking3.setBookingDate(bookingDate3);
        
        // Add booking to customer C9
        customer9.setBookings(Arrays.asList(booking3));
        
        int resultC9 = customer9.computeMonthlyRewardPoints("2024-01");
        
        // Expected Output: C8 reward points: 500. C9 reward points: 250.
        assertEquals(500, resultC8);
        assertEquals(250, resultC9);
    }
}