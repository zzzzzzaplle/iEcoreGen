import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR1Test {
    
    private Driver driver1;
    private Driver driver2;
    private Customer customer1;
    private Customer customer2;
    private Customer customer3;
    private Customer customer9;
    private Customer customer10;
    private Trip tripT123;
    private Trip tripT456;
    private Trip tripT100;
    private Trip tripT199;
    private Trip tripT200;
    private Trip tripT299;
    private Trip tripT300;
    private Booking bookingC1T456;
    private Booking bookingC2T456;
    private Booking bookingC9T199;
    private Booking bookingC10T299;
    
    @Before
    public void setUp() throws Exception {
        // Initialize drivers
        driver1 = new Driver();
        driver2 = new Driver();
        
        // Initialize customers
        customer1 = new Customer();
        customer2 = new Customer();
        customer3 = new Customer();
        customer9 = new Customer();
        customer10 = new Customer();
        
        // Initialize trips
        tripT123 = new Trip();
        tripT123.setDepartureTime("2023-12-25 14:00");
        tripT123.setArrivalTime("2023-12-25 16:00");
        tripT123.setNumberOfSeats(5);
        
        tripT456 = new Trip();
        tripT456.setDepartureTime("2023-12-25 10:00");
        tripT456.setArrivalTime("2023-12-25 12:00");
        tripT456.setNumberOfSeats(2);
        
        tripT100 = new Trip();
        tripT100.setDepartureTime("2023-12-25 14:00");
        tripT100.setArrivalTime("2023-12-25 16:00");
        tripT100.setNumberOfSeats(50);
        
        tripT199 = new Trip();
        tripT199.setDepartureTime("2023-12-25 08:00");
        tripT199.setArrivalTime("2023-12-25 10:00");
        tripT199.setNumberOfSeats(50);
        
        tripT200 = new Trip();
        tripT200.setDepartureTime("2023-12-25 12:00");
        tripT200.setArrivalTime("2023-12-25 14:00");
        tripT200.setNumberOfSeats(40);
        
        tripT299 = new Trip();
        tripT299.setDepartureTime("2023-12-25 13:00");
        tripT299.setArrivalTime("2023-12-25 15:00");
        tripT299.setNumberOfSeats(50);
        
        tripT300 = new Trip();
        tripT300.setDepartureTime("2023-12-25 14:00");
        tripT300.setArrivalTime("2023-12-25 16:00");
        tripT300.setNumberOfSeats(40);
        
        // Initialize bookings
        bookingC1T456 = new Booking();
        bookingC1T456.setCustomer(customer1);
        bookingC1T456.setTrip(tripT456);
        bookingC1T456.setNumberOfSeats(3);
        
        bookingC2T456 = new Booking();
        bookingC2T456.setCustomer(customer2);
        bookingC2T456.setTrip(tripT456);
        bookingC2T456.setNumberOfSeats(3);
        
        bookingC9T199 = new Booking();
        bookingC9T199.setCustomer(customer9);
        bookingC9T199.setTrip(tripT199);
        bookingC9T199.setNumberOfSeats(2);
        
        bookingC10T299 = new Booking();
        bookingC10T299.setCustomer(customer10);
        bookingC10T299.setTrip(tripT299);
        bookingC10T299.setNumberOfSeats(2);
        
        // Set up trip bookings
        List<Booking> bookingsT456 = new ArrayList<>();
        bookingsT456.add(bookingC2T456);
        tripT456.setBookings(bookingsT456);
        
        List<Booking> bookingsT199 = new ArrayList<>();
        bookingsT199.add(bookingC9T199);
        tripT199.setBookings(bookingsT199);
        
        List<Booking> bookingsT299 = new ArrayList<>();
        bookingsT299.add(bookingC10T299);
        tripT299.setBookings(bookingsT299);
    }
    
    @Test
    public void testCase1_ValidBookingWithAvailableSeatsAndNoOverlap() {
        // Test Case 1: "Valid booking with available seats and no overlap"
        // Setup: Create Driver D1 with Trip T123 (2023-12-25 14:00-16:00, seats:5)
        //         Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        // Expected Output: true, updated Trip T123 seats = 2
        
        driver1.getTrips().add(tripT123);
        
        // Create booking for T123
        Booking booking = new Booking();
        booking.setCustomer(customer1);
        booking.setTrip(tripT123);
        booking.setNumberOfSeats(3);
        
        // Set booking time to 2023-12-25 11:00 (3 hours before departure)
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date bookingDate = format.parse("2023-12-25 11:00");
            booking.setBookingDate(bookingDate);
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Test booking eligibility
        boolean result = booking.isBookingEligible();
        
        // Verify results
        assertTrue("Booking should be eligible", result);
        assertEquals("Trip T123 seats should be updated to 2", 2, tripT123.getNumberOfSeats());
    }
    
    @Test
    public void testCase2_BookingDeniedDueToSeatShortage() {
        // Test Case 2: "Booking denied due to seat shortage"
        // Setup: Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        //         Create Customer C2 with existing booking for T456 (3 seats)
        //         Current time: 2023-12-25 07:30 (2.5 hours before departure)
        // Expected Output: false, Trip T123 seats = 2
        
        driver2.getTrips().add(tripT456);
        
        // Create new booking for T456
        Booking booking = new Booking();
        booking.setCustomer(customer2);
        booking.setTrip(tripT456);
        booking.setNumberOfSeats(3);
        
        // Set booking time to 2023-12-25 07:30 (2.5 hours before departure)
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date bookingDate = format.parse("2023-12-25 07:30");
            booking.setBookingDate(bookingDate);
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Test booking eligibility
        boolean result = booking.isBookingEligible();
        
        // Verify results
        assertFalse("Booking should be denied due to seat shortage", result);
        assertEquals("Trip T456 seats should remain 2", 2, tripT456.getNumberOfSeats());
    }
    
    @Test
    public void testCase3_BookingDeniedDueToTimeCutoffExactly2HoursBefore() {
        // Test Case 3: "Booking denied due to time cutoff (exactly 2 hours before)"
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        //         Current time: 2023-12-25 12:00 (exactly 2 hours before)
        //         Create Customer C3 with booking for T100 (3 seats)
        // Expected Output: False, Trip T123 seats = 50
        
        // Create booking for T100
        Booking booking = new Booking();
        booking.setCustomer(customer3);
        booking.setTrip(tripT100);
        booking.setNumberOfSeats(3);
        
        // Set booking time to 2023-12-25 12:00 (exactly 2 hours before departure)
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date bookingDate = format.parse("2023-12-25 12:00");
            booking.setBookingDate(bookingDate);
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Test booking eligibility
        boolean result = booking.isBookingEligible();
        
        // Verify results
        assertFalse("Booking should be denied due to time cutoff", result);
        assertEquals("Trip T100 seats should remain 50", 50, tripT100.getNumberOfSeats());
    }
    
    @Test
    public void testCase4_BookingAllowedWithMultipleNonOverlappingTrips() {
        // Test Case 4: "Booking allowed with multiple non-overlapping trips"
        // Setup: Customer C9 has booked an existing booking(2023-12-23 12:00, 2 seats) for Trip T199 (2023-12-25 08:00-10:00, 50 seats)
        //         Customer C9 create a new booking(2023-12-23 14:00, 4 seats) for Trip T200 (2023-12-25 12:00-14:00 (no overlap), 40 seats)
        // Expected Output: true, Trip T200 seats = 36
        
        // Create new booking for T200
        Booking booking = new Booking();
        booking.setCustomer(customer9);
        booking.setTrip(tripT200);
        booking.setNumberOfSeats(4);
        
        // Set booking time to 2023-12-23 14:00
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date bookingDate = format.parse("2023-12-23 14:00");
            booking.setBookingDate(bookingDate);
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Add existing booking to customer's bookings
        List<Booking> customerBookings = new ArrayList<>();
        customerBookings.add(bookingC9T199);
        // Note: Customer class doesn't have a bookings field, so we'll rely on trip's booking list
        
        // Test booking eligibility
        boolean result = booking.isBookingEligible();
        
        // Verify results
        assertTrue("Booking should be allowed with non-overlapping trips", result);
        assertEquals("Trip T200 seats should be updated to 36", 36, tripT200.getNumberOfSeats());
    }
    
    @Test
    public void testCase5_BookingDeniedWhenCustomerHasOverlappingBooking() {
        // Test Case 5: "Booking denied when customer has overlapping booking"
        // Setup: Customer C10 has existing booking(2023-12-23 12:00, 2 seats) for Trip T299 (departure: 2023-12-25 13:00-15:00, 50 seats)
        //         Customer C10 create a new booking(2023-12-23 14:00, 4 seats) for Trip T300 (2023-12-25 14:00-16:00 (1-hour overlap), 40 seats)
        // Expected Output: false, Trip T300 seats = 40
        
        // Create new booking for T300
        Booking booking = new Booking();
        booking.setCustomer(customer10);
        booking.setTrip(tripT300);
        booking.setNumberOfSeats(4);
        
        // Set booking time to 2023-12-23 14:00
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date bookingDate = format.parse("2023-12-23 14:00");
            booking.setBookingDate(bookingDate);
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Add existing booking to customer's bookings
        List<Booking> customerBookings = new ArrayList<>();
        customerBookings.add(bookingC10T299);
        // Note: Customer class doesn't have a bookings field, so we'll rely on trip's booking list
        
        // Test booking eligibility
        boolean result = booking.isBookingEligible();
        
        // Verify results
        assertFalse("Booking should be denied due to overlapping trips", result);
        assertEquals("Trip T300 seats should remain 40", 40, tripT300.getNumberOfSeats());
    }
}