import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR1Test {
    
    private SimpleDateFormat sdf;
    
    @Before
    public void setUp() {
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ValidBookingWithAvailableSeatsAndNoOverlap() throws ParseException {
        // Setup: Create Driver D1 with Trip T123 (2023-12-25 14:00-16:00, seats:5)
        Driver driver = new Driver();
        Trip tripT123 = createTrip("T123", "2023-12-25 14:00:00", "2023-12-25 16:00:00", 5);
        
        // Setup: Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        Customer customer = new Customer();
        Trip tripT456 = createTrip("T456", "2023-12-25 14:00:00", "2023-12-25 16:00:00", 10);
        
        Booking existingBooking = createBooking(customer, tripT456, 3, "2023-12-25 11:00:00");
        tripT456.getBookings().add(existingBooking);
        
        // Create new booking for T123
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer);
        newBooking.setTrip(tripT123);
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDate(sdf.parse("2023-12-25 11:00:00"));
        
        // Test eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify results
        assertTrue("Booking should be eligible", result);
        assertEquals("Trip seats should be updated to 2", 2, tripT123.getNumberOfSeats());
    }
    
    @Test
    public void testCase2_BookingDeniedDueToSeatShortage() throws ParseException {
        // Setup: Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        Trip tripT456 = createTrip("T456", "2023-12-25 10:00:00", "2023-12-25 12:00:00", 2);
        
        // Setup: Create Customer C2 with existing booking for T456 (3 seats)
        Customer customer = new Customer();
        Booking existingBooking = createBooking(customer, tripT456, 3, "2023-12-25 07:30:00");
        tripT456.getBookings().add(existingBooking);
        
        // Create new booking for T456
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer);
        newBooking.setTrip(tripT456);
        newBooking.setNumberOfSeats(2);
        newBooking.setBookingDate(sdf.parse("2023-12-25 07:30:00"));
        
        // Test eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify results
        assertFalse("Booking should be denied due to seat shortage", result);
        assertEquals("Trip seats should remain 2", 2, tripT456.getNumberOfSeats());
    }
    
    @Test
    public void testCase3_BookingDeniedDueToTimeCutoffExactly2HoursBefore() throws ParseException {
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        Trip tripT100 = createTrip("T100", "2023-12-25 14:00:00", "2023-12-25 16:00:00", 50);
        
        // Setup: Current time: 2023-12-25 12:00 (exactly 2 hours before)
        Customer customer = new Customer();
        
        // Create new booking for T100
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer);
        newBooking.setTrip(tripT100);
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDate(sdf.parse("2023-12-25 12:00:00"));
        
        // Test eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify results
        assertFalse("Booking should be denied due to exactly 2 hours cutoff", result);
        assertEquals("Trip seats should remain 50", 50, tripT100.getNumberOfSeats());
    }
    
    @Test
    public void testCase4_BookingAllowedWithMultipleNonOverlappingTrips() throws ParseException {
        // Setup: Customer C9 has booked existing booking(2023-12-23 12:00, 2 seats) for Trip T199 (2023-12-25 08:00-10:00, 50 seats)
        Customer customer = new Customer();
        Trip tripT199 = createTrip("T199", "2023-12-25 08:00:00", "2023-12-25 10:00:00", 50);
        
        Booking existingBooking = createBooking(customer, tripT199, 2, "2023-12-23 12:00:00");
        tripT199.getBookings().add(existingBooking);
        
        // Setup: Customer C9 create new booking(2023-12-23 14:00, 4 seats) for Trip T200 (2023-12-25 12:00-14:00, 40 seats)
        Trip tripT200 = createTrip("T200", "2023-12-25 12:00:00", "2023-12-25 14:00:00", 40);
        
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer);
        newBooking.setTrip(tripT200);
        newBooking.setNumberOfSeats(4);
        newBooking.setBookingDate(sdf.parse("2023-12-23 14:00:00"));
        
        // Test eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify results
        assertTrue("Booking should be allowed with non-overlapping trips", result);
        assertEquals("Trip T200 seats should be updated to 36", 36, tripT200.getNumberOfSeats());
    }
    
    @Test
    public void testCase5_BookingDeniedWhenCustomerHasOverlappingBooking() throws ParseException {
        // Setup: Customer C10 has existing booking(2023-12-23 12:00, 2 seats) for Trip T299 (departure: 2023-12-25 13:00-15:00, 50 seats)
        Customer customer = new Customer();
        Trip tripT299 = createTrip("T299", "2023-12-25 13:00:00", "2023-12-25 15:00:00", 50);
        
        Booking existingBooking = createBooking(customer, tripT299, 2, "2023-12-23 12:00:00");
        tripT299.getBookings().add(existingBooking);
        
        // Setup: Customer C10 create new booking(2023-12-23 14:00, 4 seats) for Trip T300 (2023-12-25 14:00-16:00, 40 seats)
        Trip tripT300 = createTrip("T300", "2023-12-25 14:00:00", "2023-12-25 16:00:00", 40);
        
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer);
        newBooking.setTrip(tripT300);
        newBooking.setNumberOfSeats(4);
        newBooking.setBookingDate(sdf.parse("2023-12-23 14:00:00"));
        
        // Test eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify results
        assertFalse("Booking should be denied due to overlapping booking", result);
        assertEquals("Trip T300 seats should remain 40", 40, tripT300.getNumberOfSeats());
    }
    
    // Helper method to create a trip with specified parameters
    private Trip createTrip(String tripId, String departureTime, String arrivalTime, int seats) throws ParseException {
        Trip trip = new Trip();
        trip.setDepartureTime(departureTime);
        trip.setArrivalTime(arrivalTime);
        trip.setNumberOfSeats(seats);
        return trip;
    }
    
    // Helper method to create a booking with specified parameters
    private Booking createBooking(Customer customer, Trip trip, int seats, String bookingTime) throws ParseException {
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setTrip(trip);
        booking.setNumberOfSeats(seats);
        booking.setBookingDate(sdf.parse(bookingTime));
        return booking;
    }
}