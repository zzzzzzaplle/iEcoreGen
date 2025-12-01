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
        // Setup
        User driverD1 = new User();
        driverD1.setId("D1");
        driverD1.setDriver(true);
        
        Trip tripT123 = new Trip();
        tripT123.setId("T123");
        tripT123.setDriverId("D1");
        tripT123.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        tripT123.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        tripT123.setNumberOfSeats(5);
        
        User customerC1 = new User();
        customerC1.setId("C1");
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomerId("C1");
        existingBooking.setTripId("T456");
        existingBooking.setNumberOfSeats(3);
        existingBooking.setBookingTime(LocalDateTime.parse("2023-12-25 11:00:00", formatter));
        
        system.getUsers().add(driverD1);
        system.getUsers().add(customerC1);
        system.getTrips().add(tripT123);
        system.getBookings().add(existingBooking);
        
        // Test execution - current time is 3 hours before departure (11:00)
        boolean result = system.validateBookingEligibility("C1", "T123", 3);
        
        // Verification
        assertTrue("Booking should be allowed", result);
        assertEquals("Trip seats should be updated to 2", 2, tripT123.getNumberOfSeats());
    }
    
    @Test
    public void testCase2_bookingDeniedDueToSeatShortage() {
        // Setup
        User driverD2 = new User();
        driverD2.setId("D2");
        driverD2.setDriver(true);
        
        Trip tripT456 = new Trip();
        tripT456.setId("T456");
        tripT456.setDriverId("D2");
        tripT456.setDepartureTime(LocalDateTime.parse("2023-12-25 10:00:00", formatter));
        tripT456.setArrivalTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        tripT456.setNumberOfSeats(2);
        
        User customerC2 = new User();
        customerC2.setId("C2");
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomerId("C2");
        existingBooking.setTripId("T456");
        existingBooking.setNumberOfSeats(3);
        
        system.getUsers().add(driverD2);
        system.getUsers().add(customerC2);
        system.getTrips().add(tripT456);
        system.getBookings().add(existingBooking);
        
        // Test execution - current time is 2.5 hours before departure (07:30)
        // Trip only has 2 seats but booking requests 3 seats
        boolean result = system.validateBookingEligibility("C2", "T456", 3);
        
        // Verification
        assertFalse("Booking should be denied due to seat shortage", result);
        assertEquals("Trip seats should remain 2", 2, tripT456.getNumberOfSeats());
    }
    
    @Test
    public void testCase3_bookingDeniedDueToTimeCutoffExactly2HoursBefore() {
        // Setup
        Trip tripT100 = new Trip();
        tripT100.setId("T100");
        tripT100.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        tripT100.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        tripT100.setNumberOfSeats(50);
        
        User customerC3 = new User();
        customerC3.setId("C3");
        
        system.getUsers().add(customerC3);
        system.getTrips().add(tripT100);
        
        // Test execution - current time is exactly 2 hours before departure (12:00)
        boolean result = system.validateBookingEligibility("C3", "T100", 3);
        
        // Verification
        assertFalse("Booking should be denied when exactly 2 hours before departure", result);
        assertEquals("Trip seats should remain 47", 47, tripT100.getNumberOfSeats());
    }
    
    @Test
    public void testCase4_bookingAllowedWithMultipleNonOverlappingTrips() {
        // Setup
        User customerC9 = new User();
        customerC9.setId("C9");
        
        Trip tripT199 = new Trip();
        tripT199.setId("T199");
        tripT199.setDepartureTime(LocalDateTime.parse("2023-12-25 08:00:00", formatter));
        tripT199.setArrivalTime(LocalDateTime.parse("2023-12-25 10:00:00", formatter));
        tripT199.setNumberOfSeats(50);
        
        Trip tripT200 = new Trip();
        tripT200.setId("T200");
        tripT200.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        tripT200.setArrivalTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        tripT200.setNumberOfSeats(40);
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomerId("C9");
        existingBooking.setTripId("T199");
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingTime(LocalDateTime.parse("2023-12-23 12:00:00", formatter));
        
        system.getUsers().add(customerC9);
        system.getTrips().add(tripT199);
        system.getTrips().add(tripT200);
        system.getBookings().add(existingBooking);
        
        // Test execution - current time is 2 days before departure
        boolean result = system.validateBookingEligibility("C9", "T200", 4);
        
        // Verification
        assertTrue("Booking should be allowed for non-overlapping trips", result);
        assertEquals("Trip seats should be updated to 36", 36, tripT200.getNumberOfSeats());
    }
    
    @Test
    public void testCase5_bookingDeniedWhenCustomerHasOverlappingBooking() {
        // Setup
        User customerC10 = new User();
        customerC10.setId("C10");
        
        Trip tripT299 = new Trip();
        tripT299.setId("T299");
        tripT299.setDepartureTime(LocalDateTime.parse("2023-12-25 13:00:00", formatter));
        tripT299.setArrivalTime(LocalDateTime.parse("2023-12-25 15:00:00", formatter));
        tripT299.setNumberOfSeats(50);
        
        Trip tripT300 = new Trip();
        tripT300.setId("T300");
        tripT300.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        tripT300.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        tripT300.setNumberOfSeats(40);
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomerId("C10");
        existingBooking.setTripId("T299");
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingTime(LocalDateTime.parse("2023-12-23 12:00:00", formatter));
        
        system.getUsers().add(customerC10);
        system.getTrips().add(tripT299);
        system.getTrips().add(tripT300);
        system.getBookings().add(existingBooking);
        
        // Test execution - current time is 2 days before departure
        boolean result = system.validateBookingEligibility("C10", "T300", 4);
        
        // Verification
        assertFalse("Booking should be denied due to overlapping trips", result);
        assertEquals("Trip seats should remain 40", 40, tripT300.getNumberOfSeats());
    }
}