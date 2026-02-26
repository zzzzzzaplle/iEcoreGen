import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Driver driver;
    private Customer customer;
    private Trip trip;
    private Booking booking;
    
    @Before
    public void setUp() {
        driver = new Driver();
        customer = new Customer();
        trip = new Trip();
        booking = new Booking();
    }
    
    @Test
    public void testCase1_ValidBookingWithAvailableSeatsAndNoOverlap() throws Exception {
        // Setup: Create Driver D1 with Trip T123 (2023-12-25 14:00-16:00, seats:5)
        driver.setID("D1");
        trip.setDepartureStation("Station A");
        trip.setArrivalStation("Station B");
        trip.setNumberOfSeats(5);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date departureDate = dateFormat.parse("2023-12-25 00:00:00");
        trip.setDepartureDate(departureDate);
        trip.setDepartureTime("14:00");
        trip.setArrivalTime("16:00");
        
        // Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        customer.setID("C1");
        
        // Create a mock booking for T456 that doesn't conflict with T123
        Trip otherTrip = new Trip();
        otherTrip.setDepartureTime("11:00");
        otherTrip.setArrivalTime("13:00");
        
        // Test booking eligibility for Trip T123
        boolean result = trip.bookSeats(3);
        
        // Expected Output: true, updated Trip T123 seats = 2
        assertTrue("Booking should be successful", result);
        assertEquals("Available seats should be updated to 2", 2, trip.getNumberOfSeats());
    }
    
    @Test
    public void testCase2_BookingDeniedDueToSeatShortage() throws Exception {
        // Setup: Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        driver.setID("D2");
        trip.setDepartureStation("Station C");
        trip.setArrivalStation("Station D");
        trip.setNumberOfSeats(2);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date departureDate = dateFormat.parse("2023-12-25 00:00:00");
        trip.setDepartureDate(departureDate);
        trip.setDepartureTime("10:00");
        trip.setArrivalTime("12:00");
        
        // Create Customer C2 with existing booking for T456 (3 seats)
        customer.setID("C2");
        
        // Test booking eligibility - trying to book 3 seats when only 2 available
        boolean result = trip.bookSeats(3);
        
        // Expected Output: false, Trip T456 seats = 2
        assertFalse("Booking should be denied due to insufficient seats", result);
        assertEquals("Available seats should remain 2", 2, trip.getNumberOfSeats());
    }
    
    @Test
    public void testCase3_BookingDeniedDueToTimeCutoffExactly2HoursBefore() throws Exception {
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        trip.setDepartureStation("Station E");
        trip.setArrivalStation("Station F");
        trip.setNumberOfSeats(50);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date departureDate = dateFormat.parse("2023-12-25 00:00:00");
        trip.setDepartureDate(departureDate);
        trip.setDepartureTime("14:00");
        trip.setArrivalTime("16:00");
        
        // Create Customer C3 with booking for T100 (3 seats)
        customer.setID("C3");
        
        // Set booking time to exactly 2 hours before departure
        Date bookingTime = dateFormat.parse("2023-12-25 12:00:00");
        booking.setBookingDate(bookingTime);
        booking.setCustomer(customer);
        booking.setTrip(trip);
        booking.setNumberOfSeats(3);
        
        // Test booking eligibility
        boolean result = booking.isBookingEligible();
        
        // Expected Output: False, Trip T100 seats = 50
        assertFalse("Booking should be denied due to exactly 2 hours cutoff", result);
        assertEquals("Available seats should remain 50", 50, trip.getNumberOfSeats());
    }
    
    @Test
    public void testCase4_BookingAllowedWithMultipleNonOverlappingTrips() throws Exception {
        // Setup: Customer C9 has booked an existing booking(2023-12-23 12:00, 2 seats) for Trip T199 (2023-12-25 08:00-10:00, 50 seats)
        customer.setID("C9");
        
        Trip tripT199 = new Trip();
        tripT199.setDepartureTime("08:00");
        tripT199.setArrivalTime("10:00");
        tripT199.setNumberOfSeats(50);
        
        // Create Trip T200 (2023-12-25 12:00-14:00 (no overlap), 40 seats)
        trip.setDepartureStation("Station G");
        trip.setArrivalStation("Station H");
        trip.setNumberOfSeats(40);
        trip.setDepartureTime("12:00");
        trip.setArrivalTime("14:00");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date departureDate = dateFormat.parse("2023-12-25 00:00:00");
        trip.setDepartureDate(departureDate);
        
        // Test booking eligibility for Trip T200
        boolean result = trip.bookSeats(4);
        
        // Expected Output: true, Trip T200 seats = 36
        assertTrue("Booking should be successful for non-overlapping trip", result);
        assertEquals("Available seats should be updated to 36", 36, trip.getNumberOfSeats());
    }
    
    @Test
    public void testCase5_BookingDeniedWhenCustomerHasOverlappingBooking() throws Exception {
        // Setup: Customer C10 has existing booking(2023-12-23 12:00, 2 seats) for Trip T299 (departure: 2023-12-25 13:00-15:00, 50 seats)
        customer.setID("C10");
        
        Trip tripT299 = new Trip();
        tripT299.setDepartureTime("13:00");
        tripT299.setArrivalTime("15:00");
        
        // Create Trip T300 (2023-12-25 14:00-16:00 (1-hour overlap), 40 seats)
        trip.setDepartureStation("Station I");
        trip.setArrivalStation("Station J");
        trip.setNumberOfSeats(40);
        trip.setDepartureTime("14:00");
        trip.setArrivalTime("16:00");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date departureDate = dateFormat.parse("2023-12-25 00:00:00");
        trip.setDepartureDate(departureDate);
        
        // Add existing booking to trip's booking list
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(trip);
        existingBooking.setNumberOfSeats(2);
        List<Booking> existingBookings = new ArrayList<>();
        existingBookings.add(existingBooking);
        trip.setBookings(existingBookings);
        
        // Create new booking for the same customer
        booking.setCustomer(customer);
        booking.setTrip(trip);
        booking.setNumberOfSeats(4);
        
        // Test booking eligibility
        boolean result = booking.isBookingEligible();
        
        // Expected Output: false, Trip T300 seats = 40
        assertFalse("Booking should be denied due to overlapping booking", result);
        assertEquals("Available seats should remain 40", 40, trip.getNumberOfSeats());
    }
}