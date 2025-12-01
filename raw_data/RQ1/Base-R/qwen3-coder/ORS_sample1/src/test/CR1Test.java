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
    public void testCase1_validBookingWithAvailableSeatsAndNoOverlap() {
        // Setup: Create Driver D1 with Trip T123 (2023-12-25 14:00-16:00, seats:5)
        Driver driverD1 = new Driver();
        driverD1.setUserID("D1");
        
        Trip tripT123 = new Trip();
        tripT123.setDepartureStation("Station A");
        tripT123.setArrivalStation("Station B");
        tripT123.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        tripT123.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        tripT123.setNumberOfSeats(5);
        tripT123.setDriver(driverD1);
        
        driverD1.addTrip(tripT123);
        
        // Setup: Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        Customer customerC1 = new Customer();
        customerC1.setUserID("C1");
        
        Trip tripT456 = new Trip();
        tripT456.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter)); // Same day different trip
        tripT456.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customerC1);
        existingBooking.setTrip(tripT456);
        existingBooking.setNumberOfSeats(3);
        existingBooking.setBookingTime(LocalDateTime.parse("2023-12-25 11:00:00", formatter));
        
        customerC1.addBooking(existingBooking);
        
        // Input: Check booking eligibility for Trip T123
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 11:00:00", formatter); // 3 hours before departure
        int numberOfSeats = 3;
        
        boolean result = system.validateBookingEligibility(customerC1, tripT123, numberOfSeats, bookingTime);
        
        // Expected Output: true, updated Trip T123 seats = 2
        assertTrue("Booking should be eligible", result);
        assertEquals("Trip T123 seats should be updated to 2", 2, tripT123.getNumberOfSeats());
    }
    
    @Test
    public void testCase2_bookingDeniedDueToSeatShortage() {
        // Setup: Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        Driver driverD2 = new Driver();
        driverD2.setUserID("D2");
        
        Trip tripT456 = new Trip();
        tripT456.setDepartureStation("Station C");
        tripT456.setArrivalStation("Station D");
        tripT456.setDepartureTime(LocalDateTime.parse("2023-12-25 10:00:00", formatter));
        tripT456.setArrivalTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        tripT456.setNumberOfSeats(2);
        tripT456.setDriver(driverD2);
        
        driverD2.addTrip(tripT456);
        
        // Setup: Create Customer C2 with existing booking for T456 (3 seats)
        Customer customerC2 = new Customer();
        customerC2.setUserID("C2");
        
        // Current time: 2023-12-25 07:30 (2.5 hours before departure)
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 07:30:00", formatter);
        int numberOfSeats = 3; // Trying to book more seats than available
        
        boolean result = system.validateBookingEligibility(customerC2, tripT456, numberOfSeats, bookingTime);
        
        // Expected Output: false, Trip T456 seats = 2
        assertFalse("Booking should be denied due to seat shortage", result);
        assertEquals("Trip T456 seats should remain 2", 2, tripT456.getNumberOfSeats());
    }
    
    @Test
    public void testCase3_bookingDeniedDueToTimeCutoffExactly2HoursBefore() {
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        Trip tripT100 = new Trip();
        tripT100.setDepartureStation("Station E");
        tripT100.setArrivalStation("Station F");
        tripT456.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        tripT456.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        tripT100.setNumberOfSeats(50);
        
        // Setup: Create Customer C3
        Customer customerC3 = new Customer();
        customerC3.setUserID("C3");
        
        // Current time: 2023-12-25 12:00 (exactly 2 hours before)
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 12:00:00", formatter);
        int numberOfSeats = 3;
        
        boolean result = system.validateBookingEligibility(customerC3, tripT100, numberOfSeats, bookingTime);
        
        // Expected Output: False, Trip T100 seats = 50
        assertFalse("Booking should be denied when exactly 2 hours before departure", result);
        assertEquals("Trip T100 seats should remain 50", 50, tripT100.getNumberOfSeats());
    }
    
    @Test
    public void testCase4_bookingAllowedWithMultipleNonOverlappingTrips() {
        // Setup: Customer C9 has booked an existing booking(2023-12-23 12:00, 2 seats) for Trip T199 (2023-12-25 08:00-10:00, 50 seats)
        Customer customerC9 = new Customer();
        customerC9.setUserID("C9");
        
        Trip tripT199 = new Trip();
        tripT199.setDepartureTime(LocalDateTime.parse("2023-12-25 08:00:00", formatter));
        tripT199.setArrivalTime(LocalDateTime.parse("2023-12-25 10:00:00", formatter));
        tripT199.setNumberOfSeats(50);
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customerC9);
        existingBooking.setTrip(tripT199);
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingTime(LocalDateTime.parse("2023-12-23 12:00:00", formatter));
        
        customerC9.addBooking(existingBooking);
        
        // Setup: Trip T200 (2023-12-25 12:00-14:00 (no overlap), 40 seats)
        Trip tripT200 = new Trip();
        tripT200.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        tripT200.setArrivalTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        tripT200.setNumberOfSeats(40);
        
        // Customer C9 create a new booking(2023-12-23 14:00, 4 seats) for Trip T200
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-23 14:00:00", formatter);
        int numberOfSeats = 4;
        
        boolean result = system.validateBookingEligibility(customerC9, tripT200, numberOfSeats, bookingTime);
        
        // Expected Output: true, Trip T200 seats = 36
        assertTrue("Booking should be allowed for non-overlapping trips", result);
        assertEquals("Trip T200 seats should be updated to 36", 36, tripT200.getNumberOfSeats());
    }
    
    @Test
    public void testCase5_bookingDeniedWhenCustomerHasOverlappingBooking() {
        // Setup: Customer C10 has existing booking(2023-12-23 12:00, 2 seats) for Trip T299 (departure: 2023-12-25 13:00-15:00, 50 seats)
        Customer customerC10 = new Customer();
        customerC10.setUserID("C10");
        
        Trip tripT299 = new Trip();
        tripT299.setDepartureTime(LocalDateTime.parse("2023-12-25 13:00:00", formatter));
        tripT299.setArrivalTime(LocalDateTime.parse("2023-12-25 15:00:00", formatter));
        tripT299.setNumberOfSeats(50);
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customerC10);
        existingBooking.setTrip(tripT299);
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingTime(LocalDateTime.parse("2023-12-23 12:00:00", formatter));
        
        customerC10.addBooking(existingBooking);
        
        // Setup: Trip T300 (2023-12-25 14:00-16:00 (1-hour overlap), 40 seats)
        Trip tripT300 = new Trip();
        tripT300.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        tripT300.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        tripT300.setNumberOfSeats(40);
        
        // Customer C10 create a new booking(2023-12-23 14:00, 4 seats) for Trip T300
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-23 14:00:00", formatter);
        int numberOfSeats = 4;
        
        boolean result = system.validateBookingEligibility(customerC10, tripT300, numberOfSeats, bookingTime);
        
        // Expected Output: false, Trip T300 seats = 40
        assertFalse("Booking should be denied due to overlapping time periods", result);
        assertEquals("Trip T300 seats should remain 40", 40, tripT300.getNumberOfSeats());
    }
}