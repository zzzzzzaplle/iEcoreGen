import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    
    @Before
    public void setUp() {
        // Initialize drivers
        driver1 = new Driver();
        driver1.setID("D1");
        
        driver2 = new Driver();
        driver2.setID("D2");
        
        // Initialize customers
        customer1 = new Customer();
        customer1.setID("C1");
        
        customer2 = new Customer();
        customer2.setID("C2");
        
        customer3 = new Customer();
        customer3.setID("C3");
        
        customer9 = new Customer();
        customer9.setID("C9");
        
        customer10 = new Customer();
        customer10.setID("C10");
        
        // Initialize trips
        tripT123 = new Trip();
        tripT123.setDepartureDate("2023-12-25");
        tripT123.setDepartureTime("14:00");
        tripT123.setArrivalTime("16:00");
        tripT123.setNumberOfSeats(5);
        
        tripT456 = new Trip();
        tripT456.setDepartureDate("2023-12-25");
        tripT456.setDepartureTime("10:00");
        tripT456.setArrivalTime("12:00");
        tripT456.setNumberOfSeats(2);
        
        tripT100 = new Trip();
        tripT100.setDepartureDate("2023-12-25");
        tripT100.setDepartureTime("14:00");
        tripT100.setArrivalTime("16:00");
        tripT100.setNumberOfSeats(50);
        
        tripT199 = new Trip();
        tripT199.setDepartureDate("2023-12-25");
        tripT199.setDepartureTime("08:00");
        tripT199.setArrivalTime("10:00");
        tripT199.setNumberOfSeats(50);
        
        tripT200 = new Trip();
        tripT200.setDepartureDate("2023-12-25");
        tripT200.setDepartureTime("12:00");
        tripT200.setArrivalTime("14:00");
        tripT200.setNumberOfSeats(40);
        
        tripT299 = new Trip();
        tripT299.setDepartureDate("2023-12-25");
        tripT299.setDepartureTime("13:00");
        tripT299.setArrivalTime("15:00");
        tripT299.setNumberOfSeats(50);
        
        tripT300 = new Trip();
        tripT300.setDepartureDate("2023-12-25");
        tripT300.setDepartureTime("14:00");
        tripT300.setArrivalTime("16:00");
        tripT300.setNumberOfSeats(40);
        
        // Set up driver trips
        driver1.setTrips(Arrays.asList(tripT123));
        driver2.setTrips(Arrays.asList(tripT456));
    }
    
    @Test
    public void testCase1_ValidBookingWithAvailableSeatsAndNoOverlap() throws Exception {
        // Setup: Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer1);
        existingBooking.setTrip(tripT456);
        existingBooking.setNumberOfSeats(3);
        
        // Set booking date to 2023-12-25 11:00 (3 hours before departure)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date bookingDate = sdf.parse("2023-12-25 11:00");
        existingBooking.setBookingDate(bookingDate);
        
        customer1.setBookings(Arrays.asList(existingBooking));
        
        // Create booking for Trip T123
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer1);
        newBooking.setTrip(tripT123);
        newBooking.setNumberOfSeats(3);
        
        // Set current time to 2023-12-25 11:00 (3 hours before departure)
        Date currentTime = sdf.parse("2023-12-25 11:00");
        newBooking.setBookingDate(currentTime);
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify results
        assertTrue("Booking should be eligible", result);
        assertEquals("Trip T123 seats should be updated to 2", 2, tripT123.getNumberOfSeats());
    }
    
    @Test
    public void testCase2_BookingDeniedDueToSeatShortage() throws Exception {
        // Setup: Create Customer C2 with existing booking for T456 (3 seats)
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer2);
        existingBooking.setTrip(tripT456);
        existingBooking.setNumberOfSeats(3);
        
        // Set booking date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date bookingDate = sdf.parse("2023-12-25 07:30");
        existingBooking.setBookingDate(bookingDate);
        
        customer2.setBookings(Arrays.asList(existingBooking));
        
        // Create new booking for Trip T456 (same trip)
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer2);
        newBooking.setTrip(tripT456);
        newBooking.setNumberOfSeats(3);
        
        // Set current time to 2023-12-25 07:30 (2.5 hours before departure)
        Date currentTime = sdf.parse("2023-12-25 07:30");
        newBooking.setBookingDate(currentTime);
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify results
        assertFalse("Booking should be denied due to seat shortage", result);
        assertEquals("Trip T456 seats should remain 2", 2, tripT456.getNumberOfSeats());
    }
    
    @Test
    public void testCase3_BookingDeniedDueToTimeCutoffExactly2HoursBefore() throws Exception {
        // Setup: Create Customer C3 with booking for T100 (3 seats)
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer3);
        newBooking.setTrip(tripT100);
        newBooking.setNumberOfSeats(3);
        
        // Set current time to exactly 2 hours before departure (2023-12-25 12:00)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date currentTime = sdf.parse("2023-12-25 12:00");
        newBooking.setBookingDate(currentTime);
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify results
        assertFalse("Booking should be denied when exactly 2 hours before departure", result);
        assertEquals("Trip T100 seats should remain 50", 50, tripT100.getNumberOfSeats());
    }
    
    @Test
    public void testCase4_BookingAllowedWithMultipleNonOverlappingTrips() throws Exception {
        // Setup: Customer C9 has existing booking for Trip T199
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer9);
        existingBooking.setTrip(tripT199);
        existingBooking.setNumberOfSeats(2);
        
        // Set booking date to 2023-12-23 12:00
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date bookingDate = sdf.parse("2023-12-23 12:00");
        existingBooking.setBookingDate(bookingDate);
        
        customer9.setBookings(Arrays.asList(existingBooking));
        
        // Create new booking for Trip T200
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer9);
        newBooking.setTrip(tripT200);
        newBooking.setNumberOfSeats(4);
        
        // Set booking date to 2023-12-23 14:00
        Date newBookingDate = sdf.parse("2023-12-23 14:00");
        newBooking.setBookingDate(newBookingDate);
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify results
        assertTrue("Booking should be allowed for non-overlapping trips", result);
        assertEquals("Trip T200 seats should be updated to 36", 36, tripT200.getNumberOfSeats());
    }
    
    @Test
    public void testCase5_BookingDeniedWhenCustomerHasOverlappingBooking() throws Exception {
        // Setup: Customer C10 has existing booking for Trip T299
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer10);
        existingBooking.setTrip(tripT299);
        existingBooking.setNumberOfSeats(2);
        
        // Set booking date to 2023-12-23 12:00
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date bookingDate = sdf.parse("2023-12-23 12:00");
        existingBooking.setBookingDate(bookingDate);
        
        customer10.setBookings(Arrays.asList(existingBooking));
        
        // Create new booking for Trip T300
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer10);
        newBooking.setTrip(tripT300);
        newBooking.setNumberOfSeats(4);
        
        // Set booking date to 2023-12-23 14:00
        Date newBookingDate = sdf.parse("2023-12-23 14:00");
        newBooking.setBookingDate(newBookingDate);
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify results
        assertFalse("Booking should be denied due to time overlap", result);
        assertEquals("Trip T300 seats should remain 40", 40, tripT300.getNumberOfSeats());
    }
}