import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    private OnlineRideshareSystem system;
    private DateTimeFormatter formatter;

    @Before
    public void setUp() {
        system = new OnlineRideshareSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    public void testCase1_ValidBookingWithAvailableSeatsAndNoOverlap() {
        // Setup: Create Driver D1 with Trip T123 (2023-12-25 14:00-16:00, seats:5)
        Driver driver = new Driver();
        driver.setId("D1");
        
        Trip tripT123 = new Trip();
        tripT123.setId("T123");
        tripT123.setDriver(driver);
        tripT123.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        tripT123.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        tripT123.setTotalSeats(5);
        tripT123.setAvailableSeats(5);
        
        driver.getTrips().add(tripT123);
        
        // Setup: Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        Customer customer = new Customer();
        customer.setId("C1");
        
        Trip tripT456 = new Trip();
        tripT456.setId("T456");
        tripT456.setDepartureTime(LocalDateTime.parse("2023-12-25 10:00:00", formatter));
        tripT456.setArrivalTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        
        Booking existingBooking = new Booking();
        existingBooking.setId("B1");
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(tripT456);
        existingBooking.setSeatsBooked(3);
        existingBooking.setBookingTime(LocalDateTime.parse("2023-12-25 11:00:00", formatter));
        
        customer.getBookings().add(existingBooking);
        
        // Input: Check booking eligibility for Trip T123
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 11:00:00", formatter);
        boolean result = system.validateBookingEligibility(customer, tripT123, 3, bookingTime);
        
        // Expected Output: true, updated Trip T123 seats = 2
        assertTrue("Booking should be eligible", result);
        assertEquals("Available seats should be updated to 2", 2, tripT123.getAvailableSeats());
    }

    @Test
    public void testCase2_BookingDeniedDueToSeatShortage() {
        // Setup: Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        Driver driver = new Driver();
        driver.setId("D2");
        
        Trip tripT456 = new Trip();
        tripT456.setId("T456");
        tripT456.setDriver(driver);
        tripT456.setDepartureTime(LocalDateTime.parse("2023-12-25 10:00:00", formatter));
        tripT456.setArrivalTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        tripT456.setTotalSeats(2);
        tripT456.setAvailableSeats(2);
        
        driver.getTrips().add(tripT456);
        
        // Setup: Create Customer C2 with existing booking for T456 (3 seats)
        Customer customer = new Customer();
        customer.setId("C2");
        
        // Input: Check booking eligibility for Trip T456
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 07:30:00", formatter);
        boolean result = system.validateBookingEligibility(customer, tripT456, 3, bookingTime);
        
        // Expected Output: false, Trip T123 seats = 2
        assertFalse("Booking should be denied due to seat shortage", result);
        assertEquals("Available seats should remain 2", 2, tripT456.getAvailableSeats());
    }

    @Test
    public void testCase3_BookingDeniedDueToTimeCutoffExactlyTwoHoursBefore() {
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        Trip tripT100 = new Trip();
        tripT100.setId("T100");
        tripT100.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        tripT100.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        tripT100.setTotalSeats(50);
        tripT100.setAvailableSeats(50);
        
        // Setup: Create Customer C3 with booking for T100 (3 seats)
        Customer customer = new Customer();
        customer.setId("C3");
        
        // Input: Check booking eligibility for Trip T100
        // Current time: 2023-12-25 12:00 (exactly 2 hours before)
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 12:00:00", formatter);
        boolean result = system.validateBookingEligibility(customer, tripT100, 3, bookingTime);
        
        // Expected Output: False, Trip T123 seats = 47
        assertFalse("Booking should be denied due to exactly 2 hours cutoff", result);
        assertEquals("Available seats should remain 50", 50, tripT100.getAvailableSeats());
    }

    @Test
    public void testCase4_BookingAllowedWithMultipleNonOverlappingTrips() {
        // Setup: Customer C9 has booked an existing booking(2023-12-23 12:00, 2 seats) for Trip T199 (2023-12-25 08:00-10:00, 50 seats)
        Customer customer = new Customer();
        customer.setId("C9");
        
        Trip tripT199 = new Trip();
        tripT199.setId("T199");
        tripT199.setDepartureTime(LocalDateTime.parse("2023-12-25 08:00:00", formatter));
        tripT199.setArrivalTime(LocalDateTime.parse("2023-12-25 10:00:00", formatter));
        tripT199.setTotalSeats(50);
        tripT199.setAvailableSeats(50);
        
        Booking existingBooking = new Booking();
        existingBooking.setId("B199");
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(tripT199);
        existingBooking.setSeatsBooked(2);
        existingBooking.setBookingTime(LocalDateTime.parse("2023-12-23 12:00:00", formatter));
        
        customer.getBookings().add(existingBooking);
        
        // Setup: Trip T200 (2023-12-25 12:00-14:00 (no overlap), 40 seats)
        Trip tripT200 = new Trip();
        tripT200.setId("T200");
        tripT200.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        tripT200.setArrivalTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        tripT200.setTotalSeats(40);
        tripT200.setAvailableSeats(40);
        
        // Input: Check booking eligibility for Trip T200
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-23 14:00:00", formatter);
        boolean result = system.validateBookingEligibility(customer, tripT200, 4, bookingTime);
        
        // Expected Output: true, Trip T200 seats = 36
        assertTrue("Booking should be allowed with non-overlapping trips", result);
        assertEquals("Available seats should be updated to 36", 36, tripT200.getAvailableSeats());
    }

    @Test
    public void testCase5_BookingDeniedWhenCustomerHasOverlappingBooking() {
        // Setup: Customer C10 has existing booking(2023-12-23 12:00, 2 seats) for Trip T299 (departure: 2023-12-25 13:00-15:00, 50 seats)
        Customer customer = new Customer();
        customer.setId("C10");
        
        Trip tripT299 = new Trip();
        tripT299.setId("T299");
        tripT299.setDepartureTime(LocalDateTime.parse("2023-12-25 13:00:00", formatter));
        tripT299.setArrivalTime(LocalDateTime.parse("2023-12-25 15:00:00", formatter));
        tripT299.setTotalSeats(50);
        tripT299.setAvailableSeats(50);
        
        Booking existingBooking = new Booking();
        existingBooking.setId("B299");
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(tripT299);
        existingBooking.setSeatsBooked(2);
        existingBooking.setBookingTime(LocalDateTime.parse("2023-12-23 12:00:00", formatter));
        
        customer.getBookings().add(existingBooking);
        
        // Setup: Trip T300 (2023-12-25 14:00-16:00 (1-hour overlap), 40 seats)
        Trip tripT300 = new Trip();
        tripT300.setId("T300");
        tripT300.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        tripT300.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        tripT300.setTotalSeats(40);
        tripT300.setAvailableSeats(40);
        
        // Input: Check booking eligibility for Trip T300
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-23 14:00:00", formatter);
        boolean result = system.validateBookingEligibility(customer, tripT300, 4, bookingTime);
        
        // Expected Output: false, Trip T300 seats = 40
        assertFalse("Booking should be denied due to overlapping booking", result);
        assertEquals("Available seats should remain 40", 40, tripT300.getAvailableSeats());
    }
}