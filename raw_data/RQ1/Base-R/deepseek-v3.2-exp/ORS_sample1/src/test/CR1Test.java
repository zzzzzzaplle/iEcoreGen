import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    private Trip trip;
    private User customer;
    private List<Booking> bookings;
    private DateTimeFormatter formatter;

    @Before
    public void setUp() {
        trip = new Trip();
        customer = new User();
        bookings = new ArrayList<>();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    public void testCase1_ValidBookingWithAvailableSeatsAndNoOverlap() {
        // Setup: Create Driver D1 with Trip T123 (2023-12-25 14:00-16:00, seats:5)
        trip.setId("T123");
        trip.setAvailableSeats(5);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        trip.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        
        // Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        User driverD1 = new User();
        driverD1.setId("D1");
        trip.setDriver(driverD1);
        
        customer.setId("C1");
        
        // Create existing booking for T456 (different trip, no overlap)
        Trip otherTrip = new Trip();
        otherTrip.setId("T456");
        otherTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 10:00:00", formatter));
        otherTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(otherTrip);
        existingBooking.setSeats(3);
        existingBooking.setBookingTime(LocalDateTime.parse("2023-12-25 11:00:00", formatter));
        
        trip.getBookings().add(existingBooking);
        
        // Test: Check booking eligibility for Trip T123
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 11:00:00", formatter);
        boolean result = trip.validateBookingEligibility(customer, 3, bookingTime);
        
        // Verify expected output: true, updated Trip T123 seats = 2
        assertTrue("Booking should be eligible", result);
        assertEquals("Available seats should be updated to 2", 2, trip.getAvailableSeats());
    }

    @Test
    public void testCase2_BookingDeniedDueToSeatShortage() {
        // Setup: Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        trip.setId("T456");
        trip.setAvailableSeats(2);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-25 10:00:00", formatter));
        trip.setArrivalTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        
        User driverD2 = new User();
        driverD2.setId("D2");
        trip.setDriver(driverD2);
        
        customer.setId("C2");
        
        // Test: Check booking eligibility for Trip T456 with 3 seats
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 07:30:00", formatter);
        boolean result = trip.validateBookingEligibility(customer, 3, bookingTime);
        
        // Verify expected output: false, Trip T456 seats = 2
        assertFalse("Booking should be denied due to seat shortage", result);
        assertEquals("Available seats should remain 2", 2, trip.getAvailableSeats());
    }

    @Test
    public void testCase3_BookingDeniedDueToTimeCutoffExactly2HoursBefore() {
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        trip.setId("T100");
        trip.setAvailableSeats(50);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        trip.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        
        User driver = new User();
        driver.setId("D3");
        trip.setDriver(driver);
        
        customer.setId("C3");
        
        // Test: Check booking eligibility exactly 2 hours before departure
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 12:00:00", formatter);
        boolean result = trip.validateBookingEligibility(customer, 3, bookingTime);
        
        // Verify expected output: False, Trip T100 seats = 50
        assertFalse("Booking should be denied due to exactly 2 hours cutoff", result);
        assertEquals("Available seats should remain 50", 50, trip.getAvailableSeats());
    }

    @Test
    public void testCase4_BookingAllowedWithMultipleNonOverlappingTrips() {
        // Setup: Customer C9 has booked an existing booking for Trip T199
        customer.setId("C9");
        
        // Create existing booking for Trip T199 (2023-12-25 08:00-10:00)
        Trip tripT199 = new Trip();
        tripT199.setId("T199");
        tripT199.setDepartureTime(LocalDateTime.parse("2023-12-25 08:00:00", formatter));
        tripT199.setArrivalTime(LocalDateTime.parse("2023-12-25 10:00:00", formatter));
        tripT199.setAvailableSeats(50);
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(tripT199);
        existingBooking.setSeats(2);
        existingBooking.setBookingTime(LocalDateTime.parse("2023-12-23 12:00:00", formatter));
        
        // Setup Trip T200 for new booking (2023-12-25 12:00-14:00, no overlap)
        trip.setId("T200");
        trip.setAvailableSeats(40);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        trip.setArrivalTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        
        User driver = new User();
        driver.setId("D4");
        trip.setDriver(driver);
        
        // Add existing booking to trip's bookings
        trip.getBookings().add(existingBooking);
        
        // Test: Check booking eligibility for Trip T200
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-23 14:00:00", formatter);
        boolean result = trip.validateBookingEligibility(customer, 4, bookingTime);
        
        // Verify expected output: true, Trip T200 seats = 36
        assertTrue("Booking should be allowed with non-overlapping trips", result);
        assertEquals("Available seats should be updated to 36", 36, trip.getAvailableSeats());
    }

    @Test
    public void testCase5_BookingDeniedWhenCustomerHasOverlappingBooking() {
        // Setup: Customer C10 has existing booking for Trip T299
        customer.setId("C10");
        
        // Create existing booking for Trip T299 (2023-12-25 13:00-15:00)
        Trip tripT299 = new Trip();
        tripT299.setId("T299");
        tripT299.setDepartureTime(LocalDateTime.parse("2023-12-25 13:00:00", formatter));
        tripT299.setArrivalTime(LocalDateTime.parse("2023-12-25 15:00:00", formatter));
        tripT299.setAvailableSeats(50);
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(tripT299);
        existingBooking.setSeats(2);
        existingBooking.setBookingTime(LocalDateTime.parse("2023-12-23 12:00:00", formatter));
        
        // Setup Trip T300 for new booking (2023-12-25 14:00-16:00, 1-hour overlap)
        trip.setId("T300");
        trip.setAvailableSeats(40);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        trip.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        
        User driver = new User();
        driver.setId("D5");
        trip.setDriver(driver);
        
        // Add existing booking to trip's bookings
        trip.getBookings().add(existingBooking);
        
        // Test: Check booking eligibility for Trip T300
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-23 14:00:00", formatter);
        boolean result = trip.validateBookingEligibility(customer, 4, bookingTime);
        
        // Verify expected output: false, Trip T300 seats = 40
        assertFalse("Booking should be denied due to overlapping booking", result);
        assertEquals("Available seats should remain 40", 40, trip.getAvailableSeats());
    }
}