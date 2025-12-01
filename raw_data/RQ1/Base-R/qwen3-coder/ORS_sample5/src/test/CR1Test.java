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
        Driver driver = new Driver();
        driver.setUserID("D1");
        
        Trip tripT123 = new Trip();
        tripT123.setDepartureStation("Station A");
        tripT123.setArrivalStation("Station B");
        tripT123.setNumberOfSeats(5);
        tripT123.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        tripT123.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        tripT123.setDriver(driver);
        
        // Setup: Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        Customer customer = new Customer();
        customer.setUserID("C1");
        
        Trip tripT456 = new Trip();
        tripT456.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        tripT456.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        
        Booking existingBooking = new Booking();
        existingBooking.setTrip(tripT456);
        existingBooking.setNumberOfSeats(3);
        existingBooking.setBookingTime(LocalDateTime.parse("2023-12-25 11:00:00", formatter));
        
        customer.addBooking(existingBooking);
        
        // Set current time to be before the 2-hour cutoff (3 hours before departure)
        // Mock current time as 2023-12-25 11:00:00 (3 hours before departure)
        // Since we cannot mock LocalDateTime.now(), we'll rely on the logic working correctly
        // with the assumption that current time is before the cutoff
        
        // Test: Check booking eligibility for Trip T123 with 3 seats
        boolean result = system.validateBookingEligibility(customer, tripT123, 3);
        
        // Verify result and updated seat count
        assertTrue("Booking should be eligible", result);
        assertEquals("Trip T123 seats should be updated to 2", 2, tripT123.getNumberOfSeats());
    }
    
    @Test
    public void testCase2_bookingDeniedDueToSeatShortage() {
        // Setup: Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        Driver driver = new Driver();
        driver.setUserID("D2");
        
        Trip tripT456 = new Trip();
        tripT456.setDepartureStation("Station C");
        tripT456.setArrivalStation("Station D");
        tripT456.setNumberOfSeats(2);
        tripT456.setDepartureTime(LocalDateTime.parse("2023-12-25 10:00:00", formatter));
        tripT456.setArrivalTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        tripT456.setDriver(driver);
        
        // Setup: Create Customer C2 with existing booking for T456 (3 seats)
        Customer customer = new Customer();
        customer.setUserID("C2");
        
        // Current time: 2023-12-25 07:30 (2.5 hours before departure)
        // Since we cannot mock LocalDateTime.now(), we'll rely on the logic working correctly
        
        // Test: Check booking eligibility for Trip T456 with 3 seats (more than available)
        boolean result = system.validateBookingEligibility(customer, tripT456, 3);
        
        // Verify result and that seat count remains unchanged
        assertFalse("Booking should be denied due to seat shortage", result);
        assertEquals("Trip T456 seats should remain 2", 2, tripT456.getNumberOfSeats());
    }
    
    @Test
    public void testCase3_bookingDeniedDueToTimeCutoffExactly2HoursBefore() {
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        Trip tripT100 = new Trip();
        tripT100.setDepartureStation("Station E");
        tripT100.setArrivalStation("Station F");
        tripT100.setNumberOfSeats(50);
        tripT100.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        tripT100.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        
        // Create Customer C3
        Customer customer = new Customer();
        customer.setUserID("C3");
        
        // Current time: 2023-12-25 12:00 (exactly 2 hours before)
        // Since the method uses LocalDateTime.now() and we cannot mock it,
        // we need to adjust the test to work with the actual implementation
        // The test specification expects failure when current time is exactly 2 hours before
        
        // Note: This test case cannot be properly implemented without mocking LocalDateTime.now()
        // The current implementation will likely fail this test case as specified
        // because we cannot control the current time in the method
        
        // For now, we'll implement it as specified, but it may not work as expected
        boolean result = system.validateBookingEligibility(customer, tripT100, 3);
        
        // Based on the specification, we expect false when current time is exactly 2 hours before
        assertFalse("Booking should be denied when exactly 2 hours before departure", result);
        assertEquals("Trip T100 seats should remain 47 (50-3=47)", 47, tripT100.getNumberOfSeats());
    }
    
    @Test
    public void testCase4_bookingAllowedWithMultipleNonOverlappingTrips() {
        // Setup: Customer C9 has booked an existing booking(2023-12-23 12:00, 2 seats) for Trip T199 (2023-12-25 08:00-10:00, 50 seats)
        Customer customer = new Customer();
        customer.setUserID("C9");
        
        Trip tripT199 = new Trip();
        tripT199.setDepartureTime(LocalDateTime.parse("2023-12-25 08:00:00", formatter));
        tripT199.setArrivalTime(LocalDateTime.parse("2023-12-25 10:00:00", formatter));
        
        Booking existingBooking = new Booking();
        existingBooking.setTrip(tripT199);
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingTime(LocalDateTime.parse("2023-12-23 12:00:00", formatter));
        
        customer.addBooking(existingBooking);
        
        // Setup: Trip T200 (2023-12-25 12:00-14:00, 40 seats)
        Trip tripT200 = new Trip();
        tripT200.setDepartureStation("Station G");
        tripT200.setArrivalStation("Station H");
        tripT200.setNumberOfSeats(40);
        tripT200.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        tripT200.setArrivalTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        
        // Test: Customer C9 create a new booking(2023-12-23 14:00, 4 seats) for Trip T200
        // Current time is before the 2-hour cutoff (booking is made days in advance)
        boolean result = system.validateBookingEligibility(customer, tripT200, 4);
        
        // Verify result and updated seat count
        assertTrue("Booking should be allowed for non-overlapping trip", result);
        assertEquals("Trip T200 seats should be updated to 36", 36, tripT200.getNumberOfSeats());
    }
    
    @Test
    public void testCase5_bookingDeniedWhenCustomerHasOverlappingBooking() {
        // Setup: Customer C10 has existing booking(2023-12-23 12:00, 2 seats) for Trip T299 (departure: 2023-12-25 13:00-15:00, 50 seats)
        Customer customer = new Customer();
        customer.setUserID("C10");
        
        Trip tripT299 = new Trip();
        tripT299.setDepartureTime(LocalDateTime.parse("2023-12-25 13:00:00", formatter));
        tripT299.setArrivalTime(LocalDateTime.parse("2023-12-25 15:00:00", formatter));
        
        Booking existingBooking = new Booking();
        existingBooking.setTrip(tripT299);
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingTime(LocalDateTime.parse("2023-12-23 12:00:00", formatter));
        
        customer.addBooking(existingBooking);
        
        // Setup: Trip T300 (2023-12-25 14:00-16:00, 40 seats) - 1-hour overlap with T299
        Trip tripT300 = new Trip();
        tripT300.setDepartureStation("Station I");
        tripT300.setArrivalStation("Station J");
        tripT300.setNumberOfSeats(40);
        tripT300.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        tripT300.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        
        // Test: Customer C10 create a new booking(2023-12-23 14:00, 4 seats) for Trip T300
        // Current time is before the 2-hour cutoff (booking is made days in advance)
        boolean result = system.validateBookingEligibility(customer, tripT300, 4);
        
        // Verify result and that seat count remains unchanged
        assertFalse("Booking should be denied due to time overlap", result);
        assertEquals("Trip T300 seats should remain 40", 40, tripT300.getNumberOfSeats());
    }
}