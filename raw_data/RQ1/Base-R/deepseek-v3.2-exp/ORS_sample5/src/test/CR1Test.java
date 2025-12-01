import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private ORSService orsService;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        orsService = new ORSService();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_validBookingWithAvailableSeatsAndNoOverlap() {
        // Setup: Create Driver D1 with Trip T123 (2023-12-25 14:00-16:00, seats:5)
        Driver driver = new Driver();
        driver.setUserId("D1");
        
        Trip tripT123 = new Trip();
        tripT123.setTripId("T123");
        tripT123.setDriver(driver);
        tripT123.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        tripT123.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        tripT123.setAvailableSeats(5);
        tripT123.setPrice(100.0);
        
        driver.getTrips().add(tripT123);
        
        // Setup: Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        Customer customer = new Customer();
        customer.setUserId("C1");
        
        Trip tripT456 = new Trip();
        tripT456.setTripId("T456");
        tripT456.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        tripT456.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        
        Booking existingBooking = new Booking();
        existingBooking.setBookingId("B001");
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(tripT456);
        existingBooking.setSeats(3);
        existingBooking.setBookingTime(LocalDateTime.parse("2023-12-25 11:00:00", formatter));
        
        customer.getBookings().add(existingBooking);
        
        // Test: Check booking eligibility for Trip T123
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 11:00:00", formatter);
        boolean result = orsService.validateBookingEligibility(customer, tripT123, 3, bookingTime);
        
        // Verify: Expected Output: true, updated Trip T123 seats = 2
        assertTrue("Booking should be eligible", result);
        assertEquals("Available seats should be updated to 2", 2, tripT123.getAvailableSeats());
    }
    
    @Test
    public void testCase2_bookingDeniedDueToSeatShortage() {
        // Setup: Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        Driver driver = new Driver();
        driver.setUserId("D2");
        
        Trip tripT456 = new Trip();
        tripT456.setTripId("T456");
        tripT456.setDriver(driver);
        tripT456.setDepartureTime(LocalDateTime.parse("2023-12-25 10:00:00", formatter));
        tripT456.setArrivalTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        tripT456.setAvailableSeats(2);
        tripT456.setPrice(100.0);
        
        driver.getTrips().add(tripT456);
        
        // Setup: Create Customer C2 with existing booking for T456 (3 seats)
        Customer customer = new Customer();
        customer.setUserId("C2");
        
        Booking existingBooking = new Booking();
        existingBooking.setBookingId("B002");
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(tripT456);
        existingBooking.setSeats(3);
        existingBooking.setBookingTime(LocalDateTime.parse("2023-12-25 07:00:00", formatter));
        
        customer.getBookings().add(existingBooking);
        
        // Test: Check booking eligibility for Trip T456
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 07:30:00", formatter);
        boolean result = orsService.validateBookingEligibility(customer, tripT456, 3, bookingTime);
        
        // Verify: Expected Output: false, Trip T456 seats = 2
        assertFalse("Booking should be denied due to seat shortage", result);
        assertEquals("Available seats should remain 2", 2, tripT456.getAvailableSeats());
    }
    
    @Test
    public void testCase3_bookingDeniedDueToTimeCutoffExactly2HoursBefore() {
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        Trip tripT100 = new Trip();
        tripT100.setTripId("T100");
        tripT100.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        tripT100.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        tripT100.setAvailableSeats(50);
        tripT100.setPrice(100.0);
        
        // Setup: Create Customer C3 with booking for T100 (3 seats)
        Customer customer = new Customer();
        customer.setUserId("C3");
        
        // Test: Check booking eligibility for Trip T100 with booking time exactly 2 hours before
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 12:00:00", formatter);
        boolean result = orsService.validateBookingEligibility(customer, tripT100, 3, bookingTime);
        
        // Verify: Expected Output: False, Trip T100 seats = 47
        assertFalse("Booking should be denied due to time cutoff", result);
        assertEquals("Available seats should remain 50", 50, tripT100.getAvailableSeats());
    }
    
    @Test
    public void testCase4_bookingAllowedWithMultipleNonOverlappingTrips() {
        // Setup: Customer C9 has booked an existing booking(2023-12-23 12:00, 2 seats) for Trip T199 (2023-12-25 08:00-10:00, 50 seats)
        Customer customer = new Customer();
        customer.setUserId("C9");
        
        Trip tripT199 = new Trip();
        tripT199.setTripId("T199");
        tripT199.setDepartureTime(LocalDateTime.parse("2023-12-25 08:00:00", formatter));
        tripT199.setArrivalTime(LocalDateTime.parse("2023-12-25 10:00:00", formatter));
        tripT199.setAvailableSeats(50);
        tripT199.setPrice(100.0);
        
        Booking existingBooking = new Booking();
        existingBooking.setBookingId("B003");
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(tripT199);
        existingBooking.setSeats(2);
        existingBooking.setBookingTime(LocalDateTime.parse("2023-12-23 12:00:00", formatter));
        
        customer.getBookings().add(existingBooking);
        
        // Setup: Trip T200 (2023-12-25 12:00-14:00, 40 seats)
        Trip tripT200 = new Trip();
        tripT200.setTripId("T200");
        tripT200.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        tripT200.setArrivalTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        tripT200.setAvailableSeats(40);
        tripT200.setPrice(100.0);
        
        // Test: Customer C9 create a new booking(2023-12-23 14:00, 4 seats) for Trip T200
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-23 14:00:00", formatter);
        boolean result = orsService.validateBookingEligibility(customer, tripT200, 4, bookingTime);
        
        // Verify: Expected Output: true, Trip T200 seats = 36
        assertTrue("Booking should be allowed with non-overlapping trips", result);
        assertEquals("Available seats should be updated to 36", 36, tripT200.getAvailableSeats());
    }
    
    @Test
    public void testCase5_bookingDeniedWhenCustomerHasOverlappingBooking() {
        // Setup: Customer C10 has existing booking(2023-12-23 12:00, 2 seats) for Trip T299 (departure: 2023-12-25 13:00-15:00, 50 seats)
        Customer customer = new Customer();
        customer.setUserId("C10");
        
        Trip tripT299 = new Trip();
        tripT299.setTripId("T299");
        tripT299.setDepartureTime(LocalDateTime.parse("2023-12-25 13:00:00", formatter));
        tripT299.setArrivalTime(LocalDateTime.parse("2023-12-25 15:00:00", formatter));
        tripT299.setAvailableSeats(50);
        tripT299.setPrice(100.0);
        
        Booking existingBooking = new Booking();
        existingBooking.setBookingId("B004");
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(tripT299);
        existingBooking.setSeats(2);
        existingBooking.setBookingTime(LocalDateTime.parse("2023-12-23 12:00:00", formatter));
        
        customer.getBookings().add(existingBooking);
        
        // Setup: Trip T300 (2023-12-25 14:00-16:00, 40 seats)
        Trip tripT300 = new Trip();
        tripT300.setTripId("T300");
        tripT300.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        tripT300.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        tripT300.setAvailableSeats(40);
        tripT300.setPrice(100.0);
        
        // Test: Customer C10 create a new booking(2023-12-23 14:00, 4 seats) for Trip T300
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-23 14:00:00", formatter);
        boolean result = orsService.validateBookingEligibility(customer, tripT300, 4, bookingTime);
        
        // Verify: Expected Output: false, Trip T300 seats = 40
        assertFalse("Booking should be denied due to overlapping time periods", result);
        assertEquals("Available seats should remain 40", 40, tripT300.getAvailableSeats());
    }
}