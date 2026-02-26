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
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }
    
    @Test
    public void testCase1_validBookingWithAvailableSeatsAndNoOverlap() {
        // Create Trip T123 (2023-12-25 14:00-16:00, seats:5)
        Trip trip = new Trip();
        trip.setId("T123");
        trip.setDriverId("D1");
        trip.setDepartureStation("StationA");
        trip.setArrivalStation("StationB");
        trip.setNumberOfSeats(5);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00", formatter));
        trip.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00", formatter));
        trip.setPrice(50.0);
        
        // Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        // This booking is for a different trip, so it won't conflict with T123
        Booking existingBooking = new Booking();
        existingBooking.setCustomerId("C1");
        existingBooking.setNumberOfSeats(3);
        existingBooking.setBookingTime(LocalDateTime.parse("2023-12-25 11:00", formatter));
        
        // Add booking to a different trip (T456) - not to T123
        Trip otherTrip = new Trip();
        otherTrip.setId("T456");
        otherTrip.addBooking(existingBooking);
        
        // Test booking eligibility for Trip T123
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 11:00", formatter);
        boolean result = system.validateBookingEligibility(trip, "C1", 2, bookingTime);
        
        // Verify result and seat count
        assertTrue("Booking should be allowed", result);
        assertEquals("Trip seats should remain unchanged after validation", 5, trip.getNumberOfSeats());
        
        // Actually add the booking to update seat count (as per specification)
        if (result) {
            Booking newBooking = new Booking();
            newBooking.setCustomerId("C1");
            newBooking.setNumberOfSeats(2);
            newBooking.setBookingTime(bookingTime);
            trip.addBooking(newBooking);
            assertEquals("Trip seats should be updated to 2", 2, trip.getNumberOfSeats());
        }
    }
    
    @Test
    public void testCase2_bookingDeniedDueToSeatShortage() {
        // Create Trip T456 (2023-12-25 10:00-12:00, seats:2)
        Trip trip = new Trip();
        trip.setId("T456");
        trip.setDriverId("D2");
        trip.setDepartureStation("StationA");
        trip.setArrivalStation("StationB");
        trip.setNumberOfSeats(2);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-25 10:00", formatter));
        trip.setArrivalTime(LocalDateTime.parse("2023-12-25 12:00", formatter));
        trip.setPrice(50.0);
        
        // Create Customer C2 with existing booking for T456 (3 seats)
        // This should fail validation since trip only has 2 seats
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 07:30", formatter);
        boolean result = system.validateBookingEligibility(trip, "C2", 3, bookingTime);
        
        // Verify result and seat count
        assertFalse("Booking should be denied due to insufficient seats", result);
        assertEquals("Trip seats should remain 2", 2, trip.getNumberOfSeats());
    }
    
    @Test
    public void testCase3_bookingDeniedDueToTimeCutoffExactly2HoursBefore() {
        // Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        Trip trip = new Trip();
        trip.setId("T100");
        trip.setDriverId("D3");
        trip.setDepartureStation("StationA");
        trip.setArrivalStation("StationB");
        trip.setNumberOfSeats(50);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00", formatter));
        trip.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00", formatter));
        trip.setPrice(50.0);
        
        // Current time: 2023-12-25 12:00 (exactly 2 hours before)
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 12:00", formatter);
        boolean result = system.validateBookingEligibility(trip, "C3", 3, bookingTime);
        
        // Verify result and seat count
        assertFalse("Booking should be denied when exactly 2 hours before departure", result);
        assertEquals("Trip seats should remain 50", 50, trip.getNumberOfSeats());
    }
    
    @Test
    public void testCase4_bookingAllowedWithMultipleNonOverlappingTrips() {
        // Create Trip T200 (2023-12-25 12:00-14:00, 40 seats)
        Trip trip = new Trip();
        trip.setId("T200");
        trip.setDriverId("D4");
        trip.setDepartureStation("StationA");
        trip.setArrivalStation("StationB");
        trip.setNumberOfSeats(40);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00", formatter));
        trip.setArrivalTime(LocalDateTime.parse("2023-12-25 14:00", formatter));
        trip.setPrice(50.0);
        
        // Customer C9 has existing booking for Trip T199 (2023-12-25 08:00-10:00)
        Trip trip199 = new Trip();
        trip199.setId("T199");
        trip199.setDepartureTime(LocalDateTime.parse("2023-12-25 08:00", formatter));
        trip199.setArrivalTime(LocalDateTime.parse("2023-12-25 10:00", formatter));
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomerId("C9");
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingTime(LocalDateTime.parse("2023-12-23 12:00", formatter));
        trip199.addBooking(existingBooking);
        
        // Test new booking for Trip T200 (booking time: 2023-12-23 14:00, 4 seats)
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-23 14:00", formatter);
        boolean result = system.validateBookingEligibility(trip, "C9", 4, bookingTime);
        
        // Verify result and seat count
        assertTrue("Booking should be allowed for non-overlapping trips", result);
        assertEquals("Trip seats should remain unchanged after validation", 40, trip.getNumberOfSeats());
        
        // Actually add the booking to update seat count
        if (result) {
            Booking newBooking = new Booking();
            newBooking.setCustomerId("C9");
            newBooking.setNumberOfSeats(4);
            newBooking.setBookingTime(bookingTime);
            trip.addBooking(newBooking);
            assertEquals("Trip seats should be updated to 36", 36, trip.getNumberOfSeats());
        }
    }
    
    @Test
    public void testCase5_bookingDeniedWhenCustomerHasOverlappingBooking() {
        // Create Trip T300 (2023-12-25 14:00-16:00, 40 seats)
        Trip trip = new Trip();
        trip.setId("T300");
        trip.setDriverId("D5");
        trip.setDepartureStation("StationA");
        trip.setArrivalStation("StationB");
        trip.setNumberOfSeats(40);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00", formatter));
        trip.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00", formatter));
        trip.setPrice(50.0);
        
        // Customer C10 has existing booking for Trip T299 (2023-12-25 13:00-15:00)
        Trip trip299 = new Trip();
        trip299.setId("T299");
        trip299.setDepartureTime(LocalDateTime.parse("2023-12-25 13:00", formatter));
        trip299.setArrivalTime(LocalDateTime.parse("2023-12-25 15:00", formatter));
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomerId("C10");
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingTime(LocalDateTime.parse("2023-12-23 12:00", formatter));
        trip299.addBooking(existingBooking);
        
        // Add the existing booking to Trip T300's booking list to simulate conflict check
        trip.addBooking(existingBooking);
        
        // Test new booking for Trip T300 (booking time: 2023-12-23 14:00, 4 seats)
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-23 14:00", formatter);
        boolean result = system.validateBookingEligibility(trip, "C10", 4, bookingTime);
        
        // Verify result and seat count
        assertFalse("Booking should be denied due to overlapping booking time on same day", result);
        assertEquals("Trip seats should remain 40", 40, trip.getNumberOfSeats());
    }
}