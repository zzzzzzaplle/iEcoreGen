import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Customer customer;
    private Trip trip1;
    private Trip trip2;
    private Trip trip3;
    private MembershipPackage membershipWithPoints;
    
    @Before
    public void setUp() {
        // Create membership package with POINTS award
        membershipWithPoints = new MembershipPackage();
        membershipWithPoints.setAwards(new Award[]{Award.POINTS});
        
        // Create trips
        trip1 = new Trip();
        trip1.setPrice(200.0);
        trip1.setDepartureTime("2023-12-25 12:00");
        
        trip2 = new Trip();
        trip2.setPrice(100.0);
        trip2.setDepartureTime("2023-12-26 12:00");
        
        trip3 = new Trip();
        trip3.setPrice(200.0);
        trip3.setDepartureTime("2024-03-25 12:00");
    }
    
    @Test
    public void testCase1_PointsCalculationWithMultipleBookings() {
        // Setup: Customer C5 has membership with POINTS award
        customer = new Customer();
        customer.setMembershipPackage(membershipWithPoints);
        
        // Create Booking1 (seats:2) for trip1
        Booking booking1 = new Booking();
        booking1.setCustomer(customer);
        booking1.setTrip(trip1);
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(LocalDateTime.of(2023, 12, 10, 10, 0));
        
        // Create Booking2 (seats:3) for trip2
        Booking booking2 = new Booking();
        booking2.setCustomer(customer);
        booking2.setTrip(trip2);
        booking2.setNumberOfSeats(3);
        booking2.setBookingDate(LocalDateTime.of(2023, 12, 11, 10, 0));
        
        // Add bookings to customer
        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking1);
        bookings.add(booking2);
        customer.setBookings(bookings);
        
        // Test: Compute reward points for current month 2023-12
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 25 (2+3)*5=25
        assertEquals(25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() {
        // Setup: Customer C6 has membership with POINTS award
        customer = new Customer();
        customer.setMembershipPackage(membershipWithPoints);
        
        // Create Booking3 (seats:4) for trip with departure in 2024
        Trip futureTrip = new Trip();
        futureTrip.setPrice(100.0);
        futureTrip.setDepartureTime("2024-12-26 12:00");
        
        Booking booking3 = new Booking();
        booking3.setCustomer(customer);
        booking3.setTrip(futureTrip);
        booking3.setNumberOfSeats(4);
        booking3.setBookingDate(LocalDateTime.of(2023, 12, 10, 10, 0));
        
        // Add booking to customer
        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking3);
        customer.setBookings(bookings);
        
        // Test: Compute reward points for current month 2023-12
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() {
        // Setup: Customer C7 has membership with POINTS award
        customer = new Customer();
        customer.setMembershipPackage(membershipWithPoints);
        
        // Create Booking1: 2023-11-30 10:00 (seats:2) - NOT in target month
        Booking booking1 = new Booking();
        booking1.setCustomer(customer);
        booking1.setTrip(trip1);
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(LocalDateTime.of(2023, 11, 30, 10, 0));
        
        // Create Booking2: 2023-12-01 10:00 (seats:3) - IN target month
        Booking booking2 = new Booking();
        booking2.setCustomer(customer);
        booking2.setTrip(trip1);
        booking2.setNumberOfSeats(3);
        booking2.setBookingDate(LocalDateTime.of(2023, 12, 1, 10, 0));
        
        // Add bookings to customer
        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking1);
        bookings.add(booking2);
        customer.setBookings(bookings);
        
        // Test: Compute reward points for current month 2023-12
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 15 (3*5=15)
        assertEquals(15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() {
        // Setup: Customer C8 has membership with POINTS award
        customer = new Customer();
        customer.setMembershipPackage(membershipWithPoints);
        
        // Create Booking: 2023-12-10 10:00 (seats:2) for trip
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setTrip(trip3);
        booking.setNumberOfSeats(2);
        booking.setBookingDate(LocalDateTime.of(2023, 12, 10, 10, 0));
        
        // Add booking to customer
        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);
        customer.setBookings(bookings);
        
        // Test: Compute reward points for current month 2023-12
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 10 (2*5=10)
        assertEquals(10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() {
        // Setup for Customer C8
        Customer customerC8 = new Customer();
        customerC8.setMembershipPackage(membershipWithPoints);
        
        // C8 create Booking: 2024-01-10 10:00 (seats:50)
        Trip trip4 = new Trip();
        trip4.setPrice(150.0);
        trip4.setDepartureTime("2024-05-25 12:00");
        
        Booking bookingC8_1 = new Booking();
        bookingC8_1.setCustomer(customerC8);
        bookingC8_1.setTrip(trip4);
        bookingC8_1.setNumberOfSeats(50);
        bookingC8_1.setBookingDate(LocalDateTime.of(2024, 1, 10, 10, 0));
        
        // C8 create Booking: 2024-01-15 10:00 (seats:50)
        Trip trip5 = new Trip();
        trip5.setPrice(200.0);
        trip5.setDepartureTime("2024-06-25 12:00");
        
        Booking bookingC8_2 = new Booking();
        bookingC8_2.setCustomer(customerC8);
        bookingC8_2.setTrip(trip5);
        bookingC8_2.setNumberOfSeats(50);
        bookingC8_2.setBookingDate(LocalDateTime.of(2024, 1, 15, 10, 0));
        
        List<Booking> bookingsC8 = new ArrayList<>();
        bookingsC8.add(bookingC8_1);
        bookingsC8.add(bookingC8_2);
        customerC8.setBookings(bookingsC8);
        
        // Setup for Customer C9
        Customer customerC9 = new Customer();
        customerC9.setMembershipPackage(membershipWithPoints);
        
        // C9 create Booking: 2024-01-10 10:00 (seats:50)
        Trip trip6 = new Trip();
        trip6.setPrice(200.0);
        trip6.setDepartureTime("2024-07-25 12:00");
        
        Booking bookingC9 = new Booking();
        bookingC9.setCustomer(customerC9);
        bookingC9.setTrip(trip6);
        bookingC9.setNumberOfSeats(50);
        bookingC9.setBookingDate(LocalDateTime.of(2024, 1, 10, 10, 0));
        
        List<Booking> bookingsC9 = new ArrayList<>();
        bookingsC9.add(bookingC9);
        customerC9.setBookings(bookingsC9);
        
        // Test: Compute reward points for current month 2024-01
        int resultC8 = customerC8.computeMonthlyRewardPoints("2024-01");
        int resultC9 = customerC9.computeMonthlyRewardPoints("2024-01");
        
        // Expected Output: C8 reward points: 500, C9 reward points: 250
        assertEquals(500, resultC8);
        assertEquals(250, resultC9);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMonthFormat() {
        // Test invalid month format
        customer = new Customer();
        customer.setMembershipPackage(membershipWithPoints);
        
        customer.computeMonthlyRewardPoints("2023/12"); // Invalid format
    }
    
    @Test
    public void testNoMembershipPackage() {
        // Test when customer has no membership package
        customer = new Customer();
        customer.setMembershipPackage(null);
        
        // Add a booking
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setTrip(trip1);
        booking.setNumberOfSeats(2);
        booking.setBookingDate(LocalDateTime.of(2023, 12, 10, 10, 0));
        
        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);
        customer.setBookings(bookings);
        
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 0 (no membership package)
        assertEquals(0, result);
    }
    
    @Test
    public void testMembershipWithoutPointsAward() {
        // Test when customer has membership but without POINTS award
        customer = new Customer();
        MembershipPackage membershipWithoutPoints = new MembershipPackage();
        membershipWithoutPoints.setAwards(new Award[]{Award.CASHBACK, Award.DISCOUNTS});
        customer.setMembershipPackage(membershipWithoutPoints);
        
        // Add a booking
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setTrip(trip1);
        booking.setNumberOfSeats(2);
        booking.setBookingDate(LocalDateTime.of(2023, 12, 10, 10, 0));
        
        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);
        customer.setBookings(bookings);
        
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 0 (membership doesn't have POINTS award)
        assertEquals(0, result);
    }
}