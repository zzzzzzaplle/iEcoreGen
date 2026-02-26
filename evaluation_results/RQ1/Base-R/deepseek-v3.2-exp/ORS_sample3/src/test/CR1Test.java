import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.YearMonth;
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
    public void testCase1_validBookingWithAvailableSeatsAndNoOverlap() {
        // Setup: Create Driver D1 with Trip T123 (2023-12-25 14:00-16:00, seats:5)
        User driverD1 = new User();
        driverD1.setUserId("D1");
        
        Trip tripT123 = new Trip();
        tripT123.setTripId("T123");
        tripT123.setDriver(driverD1);
        tripT123.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        tripT123.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        tripT123.setTotalSeats(5);
        tripT123.setAvailableSeats(5);
        tripT123.setPrice(50.0);
        
        // Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        User customerC1 = new User();
        customerC1.setUserId("C1");
        
        Trip tripT456 = new Trip();
        tripT456.setTripId("T456");
        tripT456.setDepartureTime(LocalDateTime.parse("2023-12-25 08:00:00", formatter));
        tripT456.setArrivalTime(LocalDateTime.parse("2023-12-25 10:00:00", formatter));
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customerC1);
        existingBooking.setTrip(tripT456);
        existingBooking.setSeatsBooked(3);
        existingBooking.setBookingTime(LocalDateTime.parse("2023-12-25 11:00:00", formatter));
        
        // Add trips and booking to system
        system.getTrips().add(tripT123);
        system.getTrips().add(tripT456);
        system.getBookings().add(existingBooking);
        
        // Test: Check booking eligibility for Trip T123
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 11:00:00", formatter);
        boolean result = system.validateBookingEligibility(customerC1, tripT123, 3, bookingTime);
        
        // Verify result and updated seats
        assertTrue("Booking should be eligible", result);
        assertEquals("Trip T123 seats should be updated to 2", 2, tripT123.getAvailableSeats());
    }
    
    @Test
    public void testCase2_bookingDeniedDueToSeatShortage() {
        // Setup: Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        User driverD2 = new User();
        driverD2.setUserId("D2");
        
        Trip tripT456 = new Trip();
        tripT456.setTripId("T456");
        tripT456.setDriver(driverD2);
        tripT456.setDepartureTime(LocalDateTime.parse("2023-12-25 10:00:00", formatter));
        tripT456.setArrivalTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        tripT456.setTotalSeats(2);
        tripT456.setAvailableSeats(2);
        tripT456.setPrice(40.0);
        
        // Create Customer C2 with existing booking for T456 (3 seats) - but only 2 available seats
        User customerC2 = new User();
        customerC2.setUserId("C2");
        
        // Add trip to system
        system.getTrips().add(tripT456);
        
        // Test: Check booking eligibility for Trip T456
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 07:30:00", formatter);
        boolean result = system.validateBookingEligibility(customerC2, tripT456, 3, bookingTime);
        
        // Verify result and that seats remain unchanged
        assertFalse("Booking should be denied due to seat shortage", result);
        assertEquals("Trip T456 seats should remain 2", 2, tripT456.getAvailableSeats());
    }
    
    @Test
    public void testCase3_bookingDeniedDueToTimeCutoffExactly2HoursBefore() {
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        Trip tripT100 = new Trip();
        tripT100.setTripId("T100");
        tripT100.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        tripT100.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        tripT100.setTotalSeats(50);
        tripT100.setAvailableSeats(50);
        tripT100.setPrice(60.0);
        
        User customerC3 = new User();
        customerC3.setUserId("C3");
        
        // Add trip to system
        system.getTrips().add(tripT100);
        
        // Test: Check booking eligibility for Trip T100 at exactly 2 hours before departure
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 12:00:00", formatter);
        boolean result = system.validateBookingEligibility(customerC3, tripT100, 3, bookingTime);
        
        // Verify result and that seats remain unchanged
        assertFalse("Booking should be denied due to exactly 2 hours cutoff", result);
        assertEquals("Trip T100 seats should remain 47", 50, tripT100.getAvailableSeats());
    }
    
    @Test
    public void testCase4_bookingAllowedWithMultipleNonOverlappingTrips() {
        // Setup: Customer C9 has booked existing booking for Trip T199 (2023-12-25 08:00-10:00, 50 seats)
        User customerC9 = new User();
        customerC9.setUserId("C9");
        
        Trip tripT199 = new Trip();
        tripT199.setTripId("T199");
        tripT199.setDepartureTime(LocalDateTime.parse("2023-12-25 08:00:00", formatter));
        tripT199.setArrivalTime(LocalDateTime.parse("2023-12-25 10:00:00", formatter));
        tripT199.setTotalSeats(50);
        tripT199.setAvailableSeats(48); // 2 seats already booked
        tripT199.setPrice(45.0);
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customerC9);
        existingBooking.setTrip(tripT199);
        existingBooking.setSeatsBooked(2);
        existingBooking.setBookingTime(LocalDateTime.parse("2023-12-23 12:00:00", formatter));
        
        // Setup: Trip T200 (2023-12-25 12:00-14:00, 40 seats) - no overlap with T199
        Trip tripT200 = new Trip();
        tripT200.setTripId("T200");
        tripT200.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        tripT200.setArrivalTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        tripT200.setTotalSeats(40);
        tripT200.setAvailableSeats(40);
        tripT200.setPrice(55.0);
        
        // Add trips and booking to system
        system.getTrips().add(tripT199);
        system.getTrips().add(tripT200);
        system.getBookings().add(existingBooking);
        
        // Test: Customer C9 create new booking for Trip T200
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-23 14:00:00", formatter);
        boolean result = system.validateBookingEligibility(customerC9, tripT200, 4, bookingTime);
        
        // Verify result and updated seats
        assertTrue("Booking should be allowed with non-overlapping trips", result);
        assertEquals("Trip T200 seats should be updated to 36", 36, tripT200.getAvailableSeats());
    }
    
    @Test
    public void testCase5_bookingDeniedWhenCustomerHasOverlappingBooking() {
        // Setup: Customer C10 has existing booking for Trip T299 (2023-12-25 13:00-15:00, 50 seats)
        User customerC10 = new User();
        customerC10.setUserId("C10");
        
        Trip tripT299 = new Trip();
        tripT299.setTripId("T299");
        tripT299.setDepartureTime(LocalDateTime.parse("2023-12-25 13:00:00", formatter));
        tripT299.setArrivalTime(LocalDateTime.parse("2023-12-25 15:00:00", formatter));
        tripT299.setTotalSeats(50);
        tripT299.setAvailableSeats(48); // 2 seats already booked
        tripT299.setPrice(65.0);
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customerC10);
        existingBooking.setTrip(tripT299);
        existingBooking.setSeatsBooked(2);
        existingBooking.setBookingTime(LocalDateTime.parse("2023-12-23 12:00:00", formatter));
        
        // Setup: Trip T300 (2023-12-25 14:00-16:00, 40 seats) - 1-hour overlap with T299
        Trip tripT300 = new Trip();
        tripT300.setTripId("T300");
        tripT300.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        tripT300.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        tripT300.setTotalSeats(40);
        tripT300.setAvailableSeats(40);
        tripT300.setPrice(70.0);
        
        // Add trips and booking to system
        system.getTrips().add(tripT299);
        system.getTrips().add(tripT300);
        system.getBookings().add(existingBooking);
        
        // Test: Customer C10 create new booking for Trip T300
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-23 14:00:00", formatter);
        boolean result = system.validateBookingEligibility(customerC10, tripT300, 4, bookingTime);
        
        // Verify result and that seats remain unchanged
        assertFalse("Booking should be denied due to overlapping booking", result);
        assertEquals("Trip T300 seats should remain 40", 40, tripT300.getAvailableSeats());
    }
}