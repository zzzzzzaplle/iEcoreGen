import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private OnlineRideshareSystem system;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        system = new OnlineRideshareSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }
    
    @Test
    public void testCase1_pointsCalculationWithMultipleBookings() {
        // Setup
        Customer customer = new Customer();
        customer.setUserID("C5");
        
        Membership membership = new Membership();
        membership.setType("points");
        membership.setActive(true);
        customer.setMembership(membership);
        
        // Create booking 1
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(2);
        booking1.setBookingTime(LocalDateTime.parse("2023-12-15 10:00", formatter));
        
        Trip trip1 = new Trip();
        trip1.setPrice(200.0);
        trip1.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00", formatter));
        booking1.setTrip(trip1);
        
        // Create booking 2
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(3);
        booking2.setBookingTime(LocalDateTime.parse("2023-12-16 10:00", formatter));
        
        Trip trip2 = new Trip();
        trip2.setPrice(100.0);
        trip2.setDepartureTime(LocalDateTime.parse("2023-12-26 12:00", formatter));
        booking2.setTrip(trip2);
        
        // Add bookings to customer
        customer.addBooking(booking1);
        customer.addBooking(booking2);
        
        // Execute
        int result = system.computeMonthlyRewardPoints(customer, 2023, 12);
        
        // Verify
        assertEquals("Total points should be 25 for 2+3 seats", 25, result);
    }
    
    @Test
    public void testCase2_zeroPointsWithExpiredBookings() {
        // Setup
        Customer customer = new Customer();
        customer.setUserID("C6");
        
        Membership membership = new Membership();
        membership.setType("points");
        membership.setActive(true);
        customer.setMembership(membership);
        
        // Create booking for next year (2024)
        Booking booking = new Booking();
        booking.setNumberOfSeats(4);
        booking.setBookingTime(LocalDateTime.parse("2024-01-10 10:00", formatter));
        
        Trip trip = new Trip();
        trip.setPrice(100.0);
        trip.setDepartureTime(LocalDateTime.parse("2024-12-26 12:00", formatter));
        booking.setTrip(trip);
        
        customer.addBooking(booking);
        
        // Execute
        int result = system.computeMonthlyRewardPoints(customer, 2023, 12);
        
        // Verify
        assertEquals("No points should be awarded for bookings outside target month", 0, result);
    }
    
    @Test
    public void testCase3_partialMonthInclusion() {
        // Setup
        Customer customer = new Customer();
        customer.setUserID("C7");
        
        Membership membership = new Membership();
        membership.setType("points");
        membership.setActive(true);
        customer.setMembership(membership);
        
        // Booking 1: November booking (should not count)
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(2);
        booking1.setBookingTime(LocalDateTime.parse("2023-11-30 10:00", formatter));
        
        Trip trip1 = new Trip();
        trip1.setPrice(200.0);
        trip1.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00", formatter));
        booking1.setTrip(trip1);
        
        // Booking 2: December booking (should count)
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(3);
        booking2.setBookingTime(LocalDateTime.parse("2023-12-01 10:00", formatter));
        
        Trip trip2 = new Trip();
        trip2.setPrice(200.0);
        trip2.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00", formatter));
        booking2.setTrip(trip2);
        
        customer.addBooking(booking1);
        customer.addBooking(booking2);
        
        // Execute
        int result = system.computeMonthlyRewardPoints(customer, 2023, 12);
        
        // Verify
        assertEquals("Only December bookings should count, 3 seats * 5 = 15 points", 15, result);
    }
    
    @Test
    public void testCase4_multipleSeatsEdgeCase() {
        // Setup
        Customer customer = new Customer();
        customer.setUserID("C8");
        
        Membership membership = new Membership();
        membership.setType("points");
        membership.setActive(true);
        customer.setMembership(membership);
        
        // Single booking with 2 seats
        Booking booking = new Booking();
        booking.setNumberOfSeats(2);
        booking.setBookingTime(LocalDateTime.parse("2023-12-10 10:00", formatter));
        
        Trip trip = new Trip();
        trip.setPrice(200.0);
        trip.setDepartureTime(LocalDateTime.parse("2024-03-25 12:00", formatter));
        booking.setTrip(trip);
        
        customer.addBooking(booking);
        
        // Execute
        int result = system.computeMonthlyRewardPoints(customer, 2023, 12);
        
        // Verify
        assertEquals("2 seats * 5 = 10 points", 10, result);
    }
    
    @Test
    public void testCase5_largeSeatQuantity() {
        // Setup customer C8
        Customer customerC8 = new Customer();
        customerC8.setUserID("C8");
        
        Membership membershipC8 = new Membership();
        membershipC8.setType("points");
        membershipC8.setActive(true);
        customerC8.setMembership(membershipC8);
        
        // C8 booking 1
        Booking bookingC8_1 = new Booking();
        bookingC8_1.setNumberOfSeats(50);
        bookingC8_1.setBookingTime(LocalDateTime.parse("2024-01-10 10:00", formatter));
        
        Trip tripC8_1 = new Trip();
        tripC8_1.setPrice(150.0);
        tripC8_1.setDepartureTime(LocalDateTime.parse("2024-05-25 12:00", formatter));
        bookingC8_1.setTrip(tripC8_1);
        
        // C8 booking 2
        Booking bookingC8_2 = new Booking();
        bookingC8_2.setNumberOfSeats(50);
        bookingC8_2.setBookingTime(LocalDateTime.parse("2024-01-15 10:00", formatter));
        
        Trip tripC8_2 = new Trip();
        tripC8_2.setPrice(200.0);
        tripC8_2.setDepartureTime(LocalDateTime.parse("2024-06-25 12:00", formatter));
        bookingC8_2.setTrip(tripC8_2);
        
        customerC8.addBooking(bookingC8_1);
        customerC8.addBooking(bookingC8_2);
        
        // Setup customer C9
        Customer customerC9 = new Customer();
        customerC9.setUserID("C9");
        
        Membership membershipC9 = new Membership();
        membershipC9.setType("points");
        membershipC9.setActive(true);
        customerC9.setMembership(membershipC9);
        
        // C9 booking
        Booking bookingC9 = new Booking();
        bookingC9.setNumberOfSeats(50);
        bookingC9.setBookingTime(LocalDateTime.parse("2024-01-10 10:00", formatter));
        
        Trip tripC9 = new Trip();
        tripC9.setPrice(200.0);
        tripC9.setDepartureTime(LocalDateTime.parse("2024-07-25 12:00", formatter));
        bookingC9.setTrip(tripC9);
        
        customerC9.addBooking(bookingC9);
        
        // Execute and verify C8
        int resultC8 = system.computeMonthlyRewardPoints(customerC8, 2024, 1);
        assertEquals("C8 should have 500 points (50+50 seats * 5)", 500, resultC8);
        
        // Execute and verify C9
        int resultC9 = system.computeMonthlyRewardPoints(customerC9, 2024, 1);
        assertEquals("C9 should have 250 points (50 seats * 5)", 250, resultC9);
    }
}