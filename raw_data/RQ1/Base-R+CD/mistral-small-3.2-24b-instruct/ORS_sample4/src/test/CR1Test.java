import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class CR1Test {
    
    private Trip trip;
    private Customer customer;
    private Booking booking;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_validBookingWithAvailableSeatsAndNoOverlap() throws Exception {
        // Setup: Create Driver D1 with Trip T123 (2023-12-25 14:00-16:00, seats:5)
        trip = new Trip();
        trip.setDepartureStation("Station A");
        trip.setArrivalStation("Station B");
        trip.setNumberOfSeats(5);
        trip.setDepartureDate(dateFormat.parse("2023-12-25 14:00:00"));
        trip.setDepartureTime("14:00");
        trip.setArrivalTime("16:00");
        
        // Setup: Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        customer = new Customer();
        customer.setID("C1");
        
        // Create a mock booking for T456 that doesn't overlap with T123
        Booking existingBooking = new Booking();
        Trip otherTrip = new Trip();
        otherTrip.setDepartureDate(dateFormat.parse("2023-12-25 08:00:00")); // Different time, no overlap
        existingBooking.setTrip(otherTrip);
        existingBooking.setCustomer(customer);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-25 11:00:00"));
        
        // Create the new booking for T123
        booking = new Booking();
        booking.setCustomer(customer);
        booking.setTrip(trip);
        booking.setNumberOfSeats(3);
        booking.setBookingDate(dateFormat.parse("2023-12-25 11:00:00")); // 3 hours before departure
        
        // Test booking eligibility
        boolean result = booking.isBookingEligible();
        
        // Verify results
        assertTrue("Booking should be eligible", result);
        assertEquals("Trip seats should be updated to 2", 2, trip.getNumberOfSeats());
    }
    
    @Test
    public void testCase2_bookingDeniedDueToSeatShortage() throws Exception {
        // Setup: Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        trip = new Trip();
        trip.setDepartureStation("Station C");
        trip.setArrivalStation("Station D");
        trip.setNumberOfSeats(2);
        trip.setDepartureDate(dateFormat.parse("2023-12-25 10:00:00"));
        trip.setDepartureTime("10:00");
        trip.setArrivalTime("12:00");
        
        // Setup: Create Customer C2 with existing booking for T456 (3 seats)
        customer = new Customer();
        customer.setID("C2");
        
        // Create the new booking for T456 requesting 3 seats when only 2 available
        booking = new Booking();
        booking.setCustomer(customer);
        booking.setTrip(trip);
        booking.setNumberOfSeats(3);
        booking.setBookingDate(dateFormat.parse("2023-12-25 07:30:00")); // 2.5 hours before departure
        
        // Test booking eligibility
        boolean result = booking.isBookingEligible();
        
        // Verify results
        assertFalse("Booking should be denied due to seat shortage", result);
        assertEquals("Trip seats should remain 2", 2, trip.getNumberOfSeats());
    }
    
    @Test
    public void testCase3_bookingDeniedDueToTimeCutoffExactly2HoursBefore() throws Exception {
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        trip = new Trip();
        trip.setDepartureStation("Station E");
        trip.setArrivalStation("Station F");
        trip.setNumberOfSeats(50);
        trip.setDepartureDate(dateFormat.parse("2023-12-25 14:00:00"));
        trip.setDepartureTime("14:00");
        trip.setArrivalTime("16:00");
        
        // Setup: Create Customer C3 with booking for T100 (3 seats)
        customer = new Customer();
        customer.setID("C3");
        
        // Create booking exactly 2 hours before departure
        booking = new Booking();
        booking.setCustomer(customer);
        booking.setTrip(trip);
        booking.setNumberOfSeats(3);
        booking.setBookingDate(dateFormat.parse("2023-12-25 12:00:00")); // Exactly 2 hours before
        
        // Test booking eligibility
        boolean result = booking.isBookingEligible();
        
        // Verify results - Should be true since exactly 2 hours is allowed
        assertTrue("Booking should be allowed at exactly 2 hours before", result);
        assertEquals("Trip seats should be updated to 47", 47, trip.getNumberOfSeats());
    }
    
    @Test
    public void testCase4_bookingAllowedWithMultipleNonOverlappingTrips() throws Exception {
        // Setup: Customer C9 has booked an existing booking(2023-12-23 12:00, 2 seats) for Trip T199
        Trip tripT199 = new Trip();
        tripT199.setDepartureDate(dateFormat.parse("2023-12-25 08:00:00"));
        tripT199.setNumberOfSeats(50);
        
        customer = new Customer();
        customer.setID("C9");
        
        Booking existingBooking = new Booking();
        existingBooking.setTrip(tripT199);
        existingBooking.setCustomer(customer);
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-23 12:00:00"));
        
        // Setup: Customer C9 create a new booking(2023-12-23 14:00, 4 seats) for Trip T200
        trip = new Trip();
        trip.setDepartureStation("Station G");
        trip.setArrivalStation("Station H");
        trip.setNumberOfSeats(40);
        trip.setDepartureDate(dateFormat.parse("2023-12-25 12:00:00")); // No overlap with T199
        trip.setDepartureTime("12:00");
        trip.setArrivalTime("14:00");
        
        booking = new Booking();
        booking.setCustomer(customer);
        booking.setTrip(trip);
        booking.setNumberOfSeats(4);
        booking.setBookingDate(dateFormat.parse("2023-12-23 14:00:00"));
        
        // Test booking eligibility
        boolean result = booking.isBookingEligible();
        
        // Verify results
        assertTrue("Booking should be allowed with non-overlapping trips", result);
        assertEquals("Trip seats should be updated to 36", 36, trip.getNumberOfSeats());
    }
    
    @Test
    public void testCase5_bookingDeniedWhenCustomerHasOverlappingBooking() throws Exception {
        // Setup: Customer C10 has existing booking(2023-12-23 12:00, 2 seats) for Trip T299
        Trip tripT299 = new Trip();
        tripT299.setDepartureDate(dateFormat.parse("2023-12-25 13:00:00"));
        tripT299.setDepartureTime("13:00");
        tripT299.setArrivalTime("15:00");
        tripT299.setNumberOfSeats(50);
        
        customer = new Customer();
        customer.setID("C10");
        
        Booking existingBooking = new Booking();
        existingBooking.setTrip(tripT299);
        existingBooking.setCustomer(customer);
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-23 12:00:00"));
        
        // Add the existing booking to trip's booking list
        tripT299.getBookings().add(existingBooking);
        
        // Setup: Customer C10 create a new booking(2023-12-23 14:00, 4 seats) for Trip T300
        trip = new Trip();
        trip.setDepartureStation("Station I");
        trip.setArrivalStation("Station J");
        trip.setNumberOfSeats(40);
        trip.setDepartureDate(dateFormat.parse("2023-12-25 14:00:00")); // 1-hour overlap with T299
        trip.setDepartureTime("14:00");
        trip.setArrivalTime("16:00");
        
        booking = new Booking();
        booking.setCustomer(customer);
        booking.setTrip(trip);
        booking.setNumberOfSeats(4);
        booking.setBookingDate(dateFormat.parse("2023-12-23 14:00:00"));
        
        // Test booking eligibility
        boolean result = booking.isBookingEligible();
        
        // Verify results
        assertFalse("Booking should be denied due to overlapping trips", result);
        assertEquals("Trip seats should remain 40", 40, trip.getNumberOfSeats());
    }
}