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
        User driverD1 = new User();
        driverD1.setUserId("D1");
        
        Trip tripT123 = new Trip();
        tripT123.setTripId("T123");
        tripT123.setDriver(driverD1);
        tripT123.setDepartureTime(LocalDateTime.parse("2023-12-25T14:00:00"));
        tripT123.setArrivalTime(LocalDateTime.parse("2023-12-25T16:00:00"));
        tripT123.setAvailableSeats(5);
        tripT123.setPrice(50.0);
        
        // Setup: Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        User customerC1 = new User();
        customerC1.setUserId("C1");
        
        Trip tripT456 = new Trip();
        tripT456.setTripId("T456");
        tripT456.setDepartureTime(LocalDateTime.parse("2023-12-25T14:00:00")); // Same day different trip
        tripT456.setArrivalTime(LocalDateTime.parse("2023-12-25T16:00:00"));
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customerC1);
        existingBooking.setTrip(tripT456);
        existingBooking.setSeatsBooked(3);
        existingBooking.setBookingTime(LocalDateTime.parse("2023-12-25T11:00:00"));
        
        // Add trip and booking to system
        system.getTrips().add(tripT123);
        system.getBookings().add(existingBooking);
        
        // Test: Check booking eligibility for Trip T123
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25T11:00:00"); // 3 hours before departure
        boolean result = system.validateBookingEligibility(customerC1, tripT123, 3, bookingTime);
        
        // Verify result and updated seats
        assertTrue("Booking should be eligible", result);
        assertEquals("Trip T123 seats should be updated to 2", 2, tripT123.getAvailableSeats());
    }
    
    @Test
    public void testCase2_BookingDeniedDueToSeatShortage() {
        // Setup: Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        User driverD2 = new User();
        driverD2.setUserId("D2");
        
        Trip tripT456 = new Trip();
        tripT456.setTripId("T456");
        tripT456.setDriver(driverD2);
        tripT456.setDepartureTime(LocalDateTime.parse("2023-12-25T10:00:00"));
        tripT456.setArrivalTime(LocalDateTime.parse("2023-12-25T12:00:00"));
        tripT456.setAvailableSeats(2);
        tripT456.setPrice(40.0);
        
        // Setup: Create Customer C2 with existing booking for T456 (3 seats)
        User customerC2 = new User();
        customerC2.setUserId("C2");
        
        // Note: This booking should not affect the test as it's for a different trip
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customerC2);
        existingBooking.setTrip(tripT456);
        existingBooking.setSeatsBooked(3); // This exceeds available seats
        
        // Add trip to system (booking not added as it would fail validation)
        system.getTrips().add(tripT456);
        
        // Test: Check booking eligibility for Trip T456
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25T07:30:00"); // 2.5 hours before departure
        boolean result = system.validateBookingEligibility(customerC2, tripT456, 3, bookingTime);
        
        // Verify result and unchanged seats
        assertFalse("Booking should be denied due to seat shortage", result);
        assertEquals("Trip T456 seats should remain 2", 2, tripT456.getAvailableSeats());
    }
    
    @Test
    public void testCase3_BookingDeniedDueToTimeCutoffExactly2HoursBefore() {
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        User driver = new User();
        driver.setUserId("D3");
        
        Trip tripT100 = new Trip();
        tripT100.setTripId("T100");
        tripT100.setDriver(driver);
        tripT100.setDepartureTime(LocalDateTime.parse("2023-12-25T14:00:00"));
        tripT100.setArrivalTime(LocalDateTime.parse("2023-12-25T16:00:00"));
        tripT100.setAvailableSeats(50);
        tripT100.setPrice(60.0);
        
        // Setup: Create Customer C3
        User customerC3 = new User();
        customerC3.setUserId("C3");
        
        // Add trip to system
        system.getTrips().add(tripT100);
        
        // Test: Check booking eligibility for Trip T100 at exactly 2 hours before departure
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25T12:00:00"); // Exactly 2 hours before
        boolean result = system.validateBookingEligibility(customerC3, tripT100, 3, bookingTime);
        
        // Verify result and unchanged seats
        assertFalse("Booking should be denied due to time cutoff", result);
        assertEquals("Trip T100 seats should remain 47", 47, tripT100.getAvailableSeats());
    }
    
    @Test
    public void testCase4_BookingAllowedWithMultipleNonOverlappingTrips() {
        // Setup: Customer C9 has booked an existing booking(2023-12-23 12:00, 2 seats) for Trip T199
        User customerC9 = new User();
        customerC9.setUserId("C9");
        
        User driver1 = new User();
        driver1.setUserId("D4");
        
        Trip tripT199 = new Trip();
        tripT199.setTripId("T199");
        tripT199.setDriver(driver1);
        tripT199.setDepartureTime(LocalDateTime.parse("2023-12-25T08:00:00"));
        tripT199.setArrivalTime(LocalDateTime.parse("2023-12-25T10:00:00"));
        tripT199.setAvailableSeats(50);
        tripT199.setPrice(45.0);
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customerC9);
        existingBooking.setTrip(tripT199);
        existingBooking.setSeatsBooked(2);
        existingBooking.setBookingTime(LocalDateTime.parse("2023-12-23T12:00:00"));
        
        // Setup: Trip T200 (2023-12-25 12:00-14:00, 40 seats)
        User driver2 = new User();
        driver2.setUserId("D5");
        
        Trip tripT200 = new Trip();
        tripT200.setTripId("T200");
        tripT200.setDriver(driver2);
        tripT200.setDepartureTime(LocalDateTime.parse("2023-12-25T12:00:00"));
        tripT200.setArrivalTime(LocalDateTime.parse("2023-12-25T14:00:00"));
        tripT200.setAvailableSeats(40);
        tripT200.setPrice(55.0);
        
        // Add trips and booking to system
        system.getTrips().add(tripT199);
        system.getTrips().add(tripT200);
        system.getBookings().add(existingBooking);
        
        // Test: Customer C9 create a new booking(2023-12-23 14:00, 4 seats) for Trip T200
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-23T14:00:00");
        boolean result = system.validateBookingEligibility(customerC9, tripT200, 4, bookingTime);
        
        // Verify result and updated seats
        assertTrue("Booking should be allowed with non-overlapping trips", result);
        assertEquals("Trip T200 seats should be updated to 36", 36, tripT200.getAvailableSeats());
    }
    
    @Test
    public void testCase5_BookingDeniedWhenCustomerHasOverlappingBooking() {
        // Setup: Customer C10 has existing booking(2023-12-23 12:00, 2 seats) for Trip T299
        User customerC10 = new User();
        customerC10.setUserId("C10");
        
        User driver1 = new User();
        driver1.setUserId("D6");
        
        Trip tripT299 = new Trip();
        tripT299.setTripId("T299");
        tripT299.setDriver(driver1);
        tripT299.setDepartureTime(LocalDateTime.parse("2023-12-25T13:00:00"));
        tripT299.setArrivalTime(LocalDateTime.parse("2023-12-25T15:00:00"));
        tripT299.setAvailableSeats(50);
        tripT299.setPrice(48.0);
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customerC10);
        existingBooking.setTrip(tripT299);
        existingBooking.setSeatsBooked(2);
        existingBooking.setBookingTime(LocalDateTime.parse("2023-12-23T12:00:00"));
        
        // Setup: Trip T300 (2023-12-25 14:00-16:00, 40 seats)
        User driver2 = new User();
        driver2.setUserId("D7");
        
        Trip tripT300 = new Trip();
        tripT300.setTripId("T300");
        tripT300.setDriver(driver2);
        tripT300.setDepartureTime(LocalDateTime.parse("2023-12-25T14:00:00"));
        tripT300.setArrivalTime(LocalDateTime.parse("2023-12-25T16:00:00"));
        tripT300.setAvailableSeats(40);
        tripT300.setPrice(52.0);
        
        // Add trips and booking to system
        system.getTrips().add(tripT299);
        system.getTrips().add(tripT300);
        system.getBookings().add(existingBooking);
        
        // Test: Customer C10 create a new booking(2023-12-23 14:00, 4 seats) for Trip T300
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-23T14:00:00");
        boolean result = system.validateBookingEligibility(customerC10, tripT300, 4, bookingTime);
        
        // Verify result and unchanged seats
        assertFalse("Booking should be denied due to overlapping booking", result);
        assertEquals("Trip T300 seats should remain 40", 40, tripT300.getAvailableSeats());
    }
}