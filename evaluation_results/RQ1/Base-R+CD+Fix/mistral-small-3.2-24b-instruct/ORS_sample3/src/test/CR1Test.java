import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

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
    private Booking booking1;
    private Booking booking2;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() throws Exception {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
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
        tripT123.setDepartureDate(dateFormat.parse("2023-12-25 00:00:00"));
        tripT123.setDepartureTime("14:00");
        tripT123.setArrivalTime("16:00");
        tripT123.setNumberOfSeats(5);
        
        tripT456 = new Trip();
        tripT456.setDepartureDate(dateFormat.parse("2023-12-25 00:00:00"));
        tripT456.setDepartureTime("10:00");
        tripT456.setArrivalTime("12:00");
        tripT456.setNumberOfSeats(2);
        
        tripT100 = new Trip();
        tripT100.setDepartureDate(dateFormat.parse("2023-12-25 00:00:00"));
        tripT100.setDepartureTime("14:00");
        tripT100.setArrivalTime("16:00");
        tripT100.setNumberOfSeats(50);
        
        tripT199 = new Trip();
        tripT199.setDepartureDate(dateFormat.parse("2023-12-25 00:00:00"));
        tripT199.setDepartureTime("08:00");
        tripT199.setArrivalTime("10:00");
        tripT199.setNumberOfSeats(50);
        
        tripT200 = new Trip();
        tripT200.setDepartureDate(dateFormat.parse("2023-12-25 00:00:00"));
        tripT200.setDepartureTime("12:00");
        tripT200.setArrivalTime("14:00");
        tripT200.setNumberOfSeats(40);
        
        tripT299 = new Trip();
        tripT299.setDepartureDate(dateFormat.parse("2023-12-25 00:00:00"));
        tripT299.setDepartureTime("13:00");
        tripT299.setArrivalTime("15:00");
        tripT299.setNumberOfSeats(50);
        
        tripT300 = new Trip();
        tripT300.setDepartureDate(dateFormat.parse("2023-12-25 00:00:00"));
        tripT300.setDepartureTime("14:00");
        tripT300.setArrivalTime("16:00");
        tripT300.setNumberOfSeats(40);
        
        // Initialize bookings
        booking1 = new Booking();
        booking2 = new Booking();
    }

    @Test
    public void testCase1_ValidBookingWithAvailableSeatsAndNoOverlap() throws Exception {
        // Setup: Create Driver D1 with Trip T123 (2023-12-25 14:00-16:00, seats:5)
        driver1.getTrips().add(tripT123);
        
        // Setup: Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00 (3 hours before departure)
        Trip tripT456ForCustomer1 = new Trip();
        tripT456ForCustomer1.setDepartureDate(dateFormat.parse("2023-12-25 00:00:00"));
        tripT456ForCustomer1.setDepartureTime("14:00");
        tripT456ForCustomer1.setArrivalTime("16:00");
        tripT456ForCustomer1.setNumberOfSeats(10);
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer1);
        existingBooking.setTrip(tripT456ForCustomer1);
        existingBooking.setNumberOfSeats(3);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-25 11:00:00"));
        
        customer1.getBookings().add(existingBooking);
        tripT456ForCustomer1.getBookings().add(existingBooking);
        
        // Test booking eligibility for Trip T123
        // Current time: 2023-12-25 11:00 (3 hours before departure)
        Date currentBookingTime = dateFormat.parse("2023-12-25 11:00:00");
        
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer1);
        newBooking.setTrip(tripT123);
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDate(currentBookingTime);
        
        // Expected Output: true, updated Trip T123 seats = 2
        assertTrue("Booking should be eligible", newBooking.isBookingEligible());
        assertEquals("Trip T123 seats should be updated to 2", 2, tripT123.getNumberOfSeats());
    }

    @Test
    public void testCase2_BookingDeniedDueToSeatShortage() throws Exception {
        // Setup: Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        driver2.getTrips().add(tripT456);
        
        // Setup: Create Customer C2 with existing booking for T456 (3 seats)
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer2);
        existingBooking.setTrip(tripT456);
        existingBooking.setNumberOfSeats(3);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-25 07:00:00"));
        
        customer2.getBookings().add(existingBooking);
        tripT456.getBookings().add(existingBooking);
        
        // Current time: 2023-12-25 07:30 (2.5 hours before departure)
        Date currentBookingTime = dateFormat.parse("2023-12-25 07:30:00");
        
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer2);
        newBooking.setTrip(tripT456);
        newBooking.setNumberOfSeats(1);
        newBooking.setBookingDate(currentBookingTime);
        
        // Expected Output: false, Trip T456 seats = 2
        assertFalse("Booking should be denied due to seat shortage", newBooking.isBookingEligible());
        assertEquals("Trip T456 seats should remain 2", 2, tripT456.getNumberOfSeats());
    }

    @Test
    public void testCase3_BookingDeniedDueToTimeCutoffExactly2HoursBefore() throws Exception {
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        // Current time: 2023-12-25 12:00 (exactly 2 hours before)
        Date currentBookingTime = dateFormat.parse("2023-12-25 12:00:00");
        
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer3);
        newBooking.setTrip(tripT100);
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDate(currentBookingTime);
        
        // Expected Output: False, Trip T100 seats = 50
        assertFalse("Booking should be denied due to exactly 2 hours cutoff", newBooking.isBookingEligible());
        assertEquals("Trip T100 seats should remain 50", 50, tripT100.getNumberOfSeats());
    }

    @Test
    public void testCase4_BookingAllowedWithMultipleNonOverlappingTrips() throws Exception {
        // Setup: Customer C9 has booked an existing booking(2023-12-23 12:00, 2 seats) for Trip T199 (2023-12-25 08:00-10:00, 50 seats)
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer9);
        existingBooking.setTrip(tripT199);
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-23 12:00:00"));
        
        customer9.getBookings().add(existingBooking);
        tripT199.getBookings().add(existingBooking);
        
        // Setup: Customer C9 create a new booking(2023-12-23 14:00, 4 seats) for Trip T200 (2023-12-25 12:00-14:00 (no overlap), 40 seats)
        Date currentBookingTime = dateFormat.parse("2023-12-23 14:00:00");
        
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer9);
        newBooking.setTrip(tripT200);
        newBooking.setNumberOfSeats(4);
        newBooking.setBookingDate(currentBookingTime);
        
        // Expected Output: true, Trip T200 seats = 36
        assertTrue("Booking should be allowed with non-overlapping trips", newBooking.isBookingEligible());
        assertEquals("Trip T200 seats should be updated to 36", 36, tripT200.getNumberOfSeats());
    }

    @Test
    public void testCase5_BookingDeniedWhenCustomerHasOverlappingBooking() throws Exception {
        // Setup: Customer C10 has existing booking(2023-12-23 12:00, 2 seats) for Trip T299 (departure: 2023-12-25 13:00-15:00, 50 seats)
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer10);
        existingBooking.setTrip(tripT299);
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-23 12:00:00"));
        
        customer10.getBookings().add(existingBooking);
        tripT299.getBookings().add(existingBooking);
        
        // Setup: Customer C10 create a new booking(2023-12-23 14:00, 4 seats) for Trip T300 (2023-12-25 14:00-16:00 (1-hour overlap), 40 seats)
        Date currentBookingTime = dateFormat.parse("2023-12-23 14:00:00");
        
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer10);
        newBooking.setTrip(tripT300);
        newBooking.setNumberOfSeats(4);
        newBooking.setBookingDate(currentBookingTime);
        
        // Expected Output: false, Trip T300 seats = 40
        assertFalse("Booking should be denied due to overlapping trips", newBooking.isBookingEligible());
        assertEquals("Trip T300 seats should remain 40", 40, tripT300.getNumberOfSeats());
    }
}