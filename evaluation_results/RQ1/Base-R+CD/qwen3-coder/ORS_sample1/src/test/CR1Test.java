import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR1Test {
    
    private SimpleDateFormat dateTimeFormat;
    
    @Before
    public void setUp() {
        dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    }
    
    @Test
    public void testCase1_validBookingWithAvailableSeatsAndNoOverlap() throws Exception {
        // Setup: Create Driver D1 with Trip T123 (2023-12-25 14:00-16:00, seats:5)
        Driver driver = new Driver();
        Trip tripT123 = new Trip();
        tripT123.setDepartureDate(dateTimeFormat.parse("2023-12-25 00:00"));
        tripT123.setDepartureTime("14:00");
        tripT123.setArrivalTime("16:00");
        tripT123.setNumberOfSeats(5);
        
        // Setup: Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        Customer customer = new Customer();
        
        // Create a mock booking for T456 (this booking exists but doesn't overlap with T123)
        Trip tripT456 = new Trip();
        tripT456.setDepartureDate(dateTimeFormat.parse("2023-12-25 00:00"));
        tripT456.setDepartureTime("08:00");
        tripT456.setArrivalTime("10:00");
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(tripT456);
        existingBooking.setNumberOfSeats(3);
        existingBooking.setBookingDate(dateTimeFormat.parse("2023-12-25 11:00"));
        
        // Add the existing booking to T456
        tripT456.getBookings().add(existingBooking);
        
        // Create new booking for T123
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer);
        newBooking.setTrip(tripT123);
        newBooking.setNumberOfSeats(3);
        
        // Set current time to 2023-12-25 11:00 (3 hours before departure)
        Date currentTime = dateTimeFormat.parse("2023-12-25 11:00");
        newBooking.setBookingDate(currentTime);
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify result and seat update
        assertTrue("Booking should be eligible", result);
        if (result) {
            newBooking.updateTripSeats();
            assertEquals("Trip T123 seats should be updated to 2", 2, tripT123.getNumberOfSeats());
        }
    }
    
    @Test
    public void testCase2_bookingDeniedDueToSeatShortage() throws Exception {
        // Setup: Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        Trip tripT456 = new Trip();
        tripT456.setDepartureDate(dateTimeFormat.parse("2023-12-25 00:00"));
        tripT456.setDepartureTime("10:00");
        tripT456.setArrivalTime("12:00");
        tripT456.setNumberOfSeats(2);
        
        // Setup: Create Customer C2 with existing booking for T456 (3 seats)
        Customer customer = new Customer();
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(tripT456);
        existingBooking.setNumberOfSeats(3);
        existingBooking.setBookingDate(dateTimeFormat.parse("2023-12-25 07:30"));
        
        // Add existing booking to trip
        tripT456.getBookings().add(existingBooking);
        
        // Create new booking for T456
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer);
        newBooking.setTrip(tripT456);
        newBooking.setNumberOfSeats(3);
        
        // Set current time: 2023-12-25 07:30 (2.5 hours before departure)
        Date currentTime = dateTimeFormat.parse("2023-12-25 07:30");
        newBooking.setBookingDate(currentTime);
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify result - should be false due to seat shortage
        assertFalse("Booking should be denied due to seat shortage", result);
        assertEquals("Trip T456 seats should remain 2", 2, tripT456.getNumberOfSeats());
    }
    
    @Test
    public void testCase3_bookingDeniedDueToTimeCutoffExactly2HoursBefore() throws Exception {
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        Trip tripT100 = new Trip();
        tripT100.setDepartureDate(dateTimeFormat.parse("2023-12-25 00:00"));
        tripT100.setDepartureTime("14:00");
        tripT100.setArrivalTime("16:00");
        tripT100.setNumberOfSeats(50);
        
        // Create Customer C3 with booking for T100 (3 seats)
        Customer customer = new Customer();
        
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer);
        newBooking.setTrip(tripT100);
        newBooking.setNumberOfSeats(3);
        
        // Current time: 2023-12-25 12:00 (exactly 2 hours before)
        Date currentTime = dateTimeFormat.parse("2023-12-25 12:00");
        newBooking.setBookingDate(currentTime);
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify result - should be true (spec says exactly 2 hours is allowed)
        assertTrue("Booking should be allowed when exactly 2 hours before", result);
        if (result) {
            newBooking.updateTripSeats();
            assertEquals("Trip T100 seats should be updated to 47", 47, tripT100.getNumberOfSeats());
        }
    }
    
    @Test
    public void testCase4_bookingAllowedWithMultipleNonOverlappingTrips() throws Exception {
        // Setup: Customer C9 has booked an existing booking(2023-12-23 12:00, 2 seats) for Trip T199
        Customer customer = new Customer();
        
        Trip tripT199 = new Trip();
        tripT199.setDepartureDate(dateTimeFormat.parse("2023-12-25 00:00"));
        tripT199.setDepartureTime("08:00");
        tripT199.setArrivalTime("10:00");
        tripT199.setNumberOfSeats(50);
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(tripT199);
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingDate(dateTimeFormat.parse("2023-12-23 12:00"));
        tripT199.getBookings().add(existingBooking);
        
        // Setup: Customer C9 create a new booking(2023-12-23 14:00, 4 seats) for Trip T200
        Trip tripT200 = new Trip();
        tripT200.setDepartureDate(dateTimeFormat.parse("2023-12-25 00:00"));
        tripT200.setDepartureTime("12:00");
        tripT200.setArrivalTime("14:00");
        tripT200.setNumberOfSeats(40);
        
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer);
        newBooking.setTrip(tripT200);
        newBooking.setNumberOfSeats(4);
        newBooking.setBookingDate(dateTimeFormat.parse("2023-12-23 14:00"));
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify result - should be true (no overlap)
        assertTrue("Booking should be allowed for non-overlapping trips", result);
        if (result) {
            newBooking.updateTripSeats();
            assertEquals("Trip T200 seats should be updated to 36", 36, tripT200.getNumberOfSeats());
        }
    }
    
    @Test
    public void testCase5_bookingDeniedWhenCustomerHasOverlappingBooking() throws Exception {
        // Setup: Customer C10 has existing booking(2023-12-23 12:00, 2 seats) for Trip T299
        Customer customer = new Customer();
        
        Trip tripT299 = new Trip();
        tripT299.setDepartureDate(dateTimeFormat.parse("2023-12-25 00:00"));
        tripT299.setDepartureTime("13:00");
        tripT299.setArrivalTime("15:00");
        tripT299.setNumberOfSeats(50);
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(tripT299);
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingDate(dateTimeFormat.parse("2023-12-23 12:00"));
        tripT299.getBookings().add(existingBooking);
        
        // Setup: Customer C10 create a new booking(2023-12-23 14:00, 4 seats) for Trip T300
        Trip tripT300 = new Trip();
        tripT300.setDepartureDate(dateTimeFormat.parse("2023-12-25 00:00"));
        tripT300.setDepartureTime("14:00");
        tripT300.setArrivalTime("16:00");
        tripT300.setNumberOfSeats(40);
        
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer);
        newBooking.setTrip(tripT300);
        newBooking.setNumberOfSeats(4);
        newBooking.setBookingDate(dateTimeFormat.parse("2023-12-23 14:00"));
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify result - should be false due to overlapping time periods
        assertFalse("Booking should be denied due to overlapping trips", result);
        assertEquals("Trip T300 seats should remain 40", 40, tripT300.getNumberOfSeats());
    }
}