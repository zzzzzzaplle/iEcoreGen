import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR4Test {
    
    private Customer customerC5;
    private Customer customerC6;
    private Customer customerC7;
    private Customer customerC8;
    private Customer customerC9;
    private MembershipPackage pointsMembership;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() throws Exception {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        // Setup membership package with POINTS award
        pointsMembership = new MembershipPackage();
        pointsMembership.setAwards(new Award[]{Award.POINTS});
        
        // Initialize customers
        customerC5 = new Customer();
        customerC5.setMembershipPackage(pointsMembership);
        
        customerC6 = new Customer();
        customerC6.setMembershipPackage(pointsMembership);
        
        customerC7 = new Customer();
        customerC7.setMembershipPackage(pointsMembership);
        
        customerC8 = new Customer();
        customerC8.setMembershipPackage(pointsMembership);
        
        customerC9 = new Customer();
        customerC9.setMembershipPackage(pointsMembership);
    }
    
    @Test
    public void testCase1_pointsCalculationWithMultipleBookings() throws Exception {
        // Setup: Customer C5 has membership with POINTS award
        // C5 create Booking1 (seats:2) for trip (price: 200.0, departure: 2023-12-25 12:00)
        // C5 create Booking2 (seats:3) for trip (price: 100.0, departure: 2023-12-26 12:00)
        
        // Create trips
        Trip trip1 = new Trip();
        trip1.setDepartureTime("2023-12-25 12:00");
        trip1.setPrice(200.0);
        
        Trip trip2 = new Trip();
        trip2.setDepartureTime("2023-12-26 12:00");
        trip2.setPrice(100.0);
        
        // Create bookings for C5
        Booking booking1 = new Booking();
        booking1.setCustomer(customerC5);
        booking1.setTrip(trip1);
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(dateFormat.parse("2023-12-01 10:00"));
        
        Booking booking2 = new Booking();
        booking2.setCustomer(customerC5);
        booking2.setTrip(trip2);
        booking2.setNumberOfSeats(3);
        booking2.setBookingDate(dateFormat.parse("2023-12-01 10:00"));
        
        // Add bookings to trips
        trip1.getBookings().add(booking1);
        trip2.getBookings().add(booking2);
        
        // Test computation for current month: 2023-12
        int result = customerC5.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 25. (2+3)*5=25
        assertEquals("Points calculation with multiple bookings should return 25", 25, result);
    }
    
    @Test
    public void testCase2_zeroPointsWithExpiredBookings() throws Exception {
        // Setup: Customer C6 has membership with POINTS award
        // C6 create Booking3 (seats:4) for trip (price: 100.0, departure: 2024-12-26 12:00)
        
        // Create trip with future departure
        Trip trip3 = new Trip();
        trip3.setDepartureTime("2024-12-26 12:00");
        trip3.setPrice(100.0);
        
        // Create booking for C6
        Booking booking3 = new Booking();
        booking3.setCustomer(customerC6);
        booking3.setTrip(trip3);
        booking3.setNumberOfSeats(4);
        booking3.setBookingDate(dateFormat.parse("2023-12-01 10:00"));
        
        // Add booking to trip
        trip3.getBookings().add(booking3);
        
        // Test computation for current month: 2023-12
        int result = customerC6.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 0
        assertEquals("Zero points with expired bookings should return 0", 0, result);
    }
    
    @Test
    public void testCase3_partialMonthInclusion() throws Exception {
        // Setup: Customer C7 has membership with POINTS award
        // C7 create Booking1: 2023-11-30 10:00 (seats:2) for trip (price: 200.0, departure: 2023-12-25 12:00)
        // C7 create Booking2: 2023-12-01 10:00 (seats:3) for trip (price: 200.0, departure: 2023-12-25 12:00)
        
        // Create trips
        Trip trip1 = new Trip();
        trip1.setDepartureTime("2023-12-25 12:00");
        trip1.setPrice(200.0);
        
        Trip trip2 = new Trip();
        trip2.setDepartureTime("2023-12-25 12:00");
        trip2.setPrice(200.0);
        
        // Create bookings for C7
        Booking booking1 = new Booking();
        booking1.setCustomer(customerC7);
        booking1.setTrip(trip1);
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(dateFormat.parse("2023-11-30 10:00"));
        
        Booking booking2 = new Booking();
        booking2.setCustomer(customerC7);
        booking2.setTrip(trip2);
        booking2.setNumberOfSeats(3);
        booking2.setBookingDate(dateFormat.parse("2023-12-01 10:00"));
        
        // Add bookings to trips
        trip1.getBookings().add(booking1);
        trip2.getBookings().add(booking2);
        
        // Test computation for current month: 2023-12
        int result = customerC7.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 15. 3*5=15
        assertEquals("Partial month inclusion should return 15", 15, result);
    }
    
    @Test
    public void testCase4_multipleSeatsEdgeCase() throws Exception {
        // Setup: Customer C8 has membership with POINTS award
        // C8 create Booking: 2023-12-10 10:00 (seats:2) for trip (price: 200.0, departure: 2024-03-25 12:00)
        
        // Create trip
        Trip trip = new Trip();
        trip.setDepartureTime("2024-03-25 12:00");
        trip.setPrice(200.0);
        
        // Create booking for C8
        Booking booking = new Booking();
        booking.setCustomer(customerC8);
        booking.setTrip(trip);
        booking.setNumberOfSeats(2);
        booking.setBookingDate(dateFormat.parse("2023-12-10 10:00"));
        
        // Add booking to trip
        trip.getBookings().add(booking);
        
        // Test computation for current month: 2023-12
        int result = customerC8.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 10. 2*5=10
        assertEquals("Multiple seats edge case should return 10", 10, result);
    }
    
    @Test
    public void testCase5_largeSeatQuantity() throws Exception {
        // Setup: Customer C8 has membership with POINTS award
        // C8 create Booking: 2024-01-10 10:00 (seats:50) for trip (price: 150.0, departure: 2024-05-25 12:00)
        // C8 create Booking: 2024-01-15 10:00 (seats:50) for trip (price: 200.0, departure: 2024-06-25 12:00)
        // C9 create Booking: 2024-01-10 10:00 (seats:50) for trip (price: 200.0, departure: 2024-07-25 12:00)
        
        // Create trips for C8
        Trip trip1C8 = new Trip();
        trip1C8.setDepartureTime("2024-05-25 12:00");
        trip1C8.setPrice(150.0);
        
        Trip trip2C8 = new Trip();
        trip2C8.setDepartureTime("2024-06-25 12:00");
        trip2C8.setPrice(200.0);
        
        // Create trips for C9
        Trip tripC9 = new Trip();
        tripC9.setDepartureTime("2024-07-25 12:00");
        tripC9.setPrice(200.0);
        
        // Create bookings for C8
        Booking booking1C8 = new Booking();
        booking1C8.setCustomer(customerC8);
        booking1C8.setTrip(trip1C8);
        booking1C8.setNumberOfSeats(50);
        booking1C8.setBookingDate(dateFormat.parse("2024-01-10 10:00"));
        
        Booking booking2C8 = new Booking();
        booking2C8.setCustomer(customerC8);
        booking2C8.setTrip(trip2C8);
        booking2C8.setNumberOfSeats(50);
        booking2C8.setBookingDate(dateFormat.parse("2024-01-15 10:00"));
        
        // Create booking for C9
        Booking bookingC9 = new Booking();
        bookingC9.setCustomer(customerC9);
        bookingC9.setTrip(tripC9);
        bookingC9.setNumberOfSeats(50);
        bookingC9.setBookingDate(dateFormat.parse("2024-01-10 10:00"));
        
        // Add bookings to trips
        trip1C8.getBookings().add(booking1C8);
        trip2C8.getBookings().add(booking2C8);
        tripC9.getBookings().add(bookingC9);
        
        // Test computation for current month: 2024-01
        int resultC8 = customerC8.computeMonthlyRewardPoints("2024-01");
        int resultC9 = customerC9.computeMonthlyRewardPoints("2024-01");
        
        // Expected Output: C8 reward points: 500. C9 reward points: 250.
        assertEquals("C8 large seat quantity should return 500", 500, resultC8);
        assertEquals("C9 large seat quantity should return 250", 250, resultC9);
    }
}