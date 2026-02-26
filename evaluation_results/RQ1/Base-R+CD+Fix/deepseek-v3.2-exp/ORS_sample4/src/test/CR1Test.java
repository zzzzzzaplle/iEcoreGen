import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR1Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    }
    
    @Test
    public void testCase1_validBookingWithAvailableSeatsAndNoOverlap() throws Exception {
        // Setup: Create Driver D1 with Trip T123 (2023-12-25 14:00-16:00, seats:5)
        Driver driver = new Driver();
        Trip tripT123 = new Trip();
        tripT123.setDepartureTime("2023-12-25 14:00");
        tripT123.setArrivalTime("2023-12-25 16:00");
        tripT123.setNumberOfSeats(5);
        
        // Setup: Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        Customer customer = new Customer();
        Trip tripT456 = new Trip();
        tripT456.setDepartureTime("2023-12-25 10:00"); // Different trip, earlier time
        tripT456.setArrivalTime("2023-12-25 12:00");
        tripT456.setNumberOfSeats(10);
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(tripT456);
        existingBooking.setNumberOfSeats(3);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-25 11:00"));
        
        customer.getBookings().add(existingBooking);
        tripT456.getBookings().add(existingBooking);
        
        // Set booking time to 3 hours before departure (valid)
        Date bookingDate = dateFormat.parse("2023-12-25 11:00"); // 3 hours before 14:00
        
        // Create booking for T123
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer);
        newBooking.setTrip(tripT123);
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDate(bookingDate);
        
        // Test eligibility - should return true
        assertTrue("Booking should be eligible", newBooking.isBookingEligible());
        
        // Update trip seats - should reduce from 5 to 2
        newBooking.updateTripSeats();
        assertEquals("Trip seats should be updated to 2", 2, tripT123.getNumberOfSeats());
    }
    
    @Test
    public void testCase2_bookingDeniedDueToSeatShortage() throws Exception {
        // Setup: Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        Driver driver = new Driver();
        Trip tripT456 = new Trip();
        tripT456.setDepartureTime("2023-12-25 10:00");
        tripT456.setArrivalTime("2023-12-25 12:00");
        tripT456.setNumberOfSeats(2);
        
        // Setup: Create Customer C2 with existing booking for T456 (3 seats)
        Customer customer = new Customer();
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(tripT456);
        existingBooking.setNumberOfSeats(3); // This exceeds available seats
        existingBooking.setBookingDate(dateFormat.parse("2023-12-25 07:30"));
        
        customer.getBookings().add(existingBooking);
        tripT456.getBookings().add(existingBooking);
        
        // Current time: 2023-12-25 07:30 (2.5 hours before departure)
        Date bookingDate = dateFormat.parse("2023-12-25 07:30");
        
        // Create new booking for same trip
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer);
        newBooking.setTrip(tripT456);
        newBooking.setNumberOfSeats(1);
        newBooking.setBookingDate(bookingDate);
        
        // Test eligibility - should return false due to seat shortage
        assertFalse("Booking should be denied due to seat shortage", newBooking.isBookingEligible());
        
        // Trip seats should remain 2
        assertEquals("Trip seats should remain 2", 2, tripT456.getNumberOfSeats());
    }
    
    @Test
    public void testCase3_bookingDeniedDueToTimeCutoffExactly2HoursBefore() throws Exception {
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        Trip tripT100 = new Trip();
        tripT100.setDepartureTime("2023-12-25 14:00");
        tripT100.setArrivalTime("2023-12-25 16:00");
        tripT100.setNumberOfSeats(50);
        
        // Current time: 2023-12-25 12:00 (exactly 2 hours before)
        Date bookingDate = dateFormat.parse("2023-12-25 12:00");
        
        // Create Customer C3 with booking for T100 (3 seats)
        Customer customer = new Customer();
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer);
        newBooking.setTrip(tripT100);
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDate(bookingDate);
        
        // Test eligibility - should return false due to exactly 2 hours cutoff
        assertFalse("Booking should be denied due to exactly 2 hours cutoff", newBooking.isBookingEligible());
        
        // Trip seats should remain 50
        assertEquals("Trip seats should remain 50", 50, tripT100.getNumberOfSeats());
    }
    
    @Test
    public void testCase4_bookingAllowedWithMultipleNonOverlappingTrips() throws Exception {
        // Setup: Customer C9 has booked an existing booking(2023-12-23 12:00, 2 seats) for Trip T199 (2023-12-25 08:00-10:00, 50 seats)
        Customer customer = new Customer();
        
        Trip tripT199 = new Trip();
        tripT199.setDepartureTime("2023-12-25 08:00");
        tripT199.setArrivalTime("2023-12-25 10:00");
        tripT199.setNumberOfSeats(50);
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(tripT199);
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-23 12:00"));
        
        customer.getBookings().add(existingBooking);
        tripT199.getBookings().add(existingBooking);
        
        // Setup: Customer C9 create a new booking(2023-12-23 14:00, 4 seats) for Trip T200 (2023-12-25 12:00-14:00, 40 seats)
        Trip tripT200 = new Trip();
        tripT200.setDepartureTime("2023-12-25 12:00");
        tripT200.setArrivalTime("2023-12-25 14:00");
        tripT200.setNumberOfSeats(40);
        
        Date newBookingDate = dateFormat.parse("2023-12-23 14:00");
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer);
        newBooking.setTrip(tripT200);
        newBooking.setNumberOfSeats(4);
        newBooking.setBookingDate(newBookingDate);
        
        // Test eligibility - should return true (no overlap between 08:00-10:00 and 12:00-14:00)
        assertTrue("Booking should be allowed with non-overlapping trips", newBooking.isBookingEligible());
        
        // Update trip seats - should reduce from 40 to 36
        newBooking.updateTripSeats();
        assertEquals("Trip seats should be updated to 36", 36, tripT200.getNumberOfSeats());
    }
    
    @Test
    public void testCase5_bookingDeniedWhenCustomerHasOverlappingBooking() throws Exception {
        // Setup: Customer C10 has existing booking(2023-12-23 12:00, 2 seats) for Trip T299 (departure: 2023-12-25 13:00-15:00, 50 seats)
        Customer customer = new Customer();
        
        Trip tripT299 = new Trip();
        tripT299.setDepartureTime("2023-12-25 13:00");
        tripT299.setArrivalTime("2023-12-25 15:00");
        tripT299.setNumberOfSeats(50);
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(tripT299);
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-23 12:00"));
        
        customer.getBookings().add(existingBooking);
        tripT299.getBookings().add(existingBooking);
        
        // Setup: Customer C10 create a new booking(2023-12-23 14:00, 4 seats) for Trip T300 (2023-12-25 14:00-16:00, 40 seats)
        Trip tripT300 = new Trip();
        tripT300.setDepartureTime("2023-12-25 14:00");
        tripT300.setArrivalTime("2023-12-25 16:00");
        tripT300.setNumberOfSeats(40);
        
        Date newBookingDate = dateFormat.parse("2023-12-23 14:00");
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer);
        newBooking.setTrip(tripT300);
        newBooking.setNumberOfSeats(4);
        newBooking.setBookingDate(newBookingDate);
        
        // Test eligibility - should return false due to 1-hour overlap (14:00-15:00)
        assertFalse("Booking should be denied due to overlapping time periods", newBooking.isBookingEligible());
        
        // Trip seats should remain 40
        assertEquals("Trip seats should remain 40", 40, tripT300.getNumberOfSeats());
    }
}