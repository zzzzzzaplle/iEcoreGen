import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    private Trip trip;
    private List<Booking> bookings;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        trip = new Trip();
        bookings = new ArrayList<>();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        trip.setBookings(bookings);
    }
    
    @Test
    public void testCase1_validBookingWithAvailableSeatsAndNoOverlap() {
        // Setup: Create Driver D1 with Trip T123 (2023-12-25 14:00-16:00, seats:5)
        trip.setTripId("T123");
        trip.setNumberOfSeats(5);
        trip.setDepartureDateTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        trip.setArrivalDateTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        
        // Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        Booking existingBooking = new Booking();
        existingBooking.setCustomerId("C1");
        existingBooking.setTripId("T456");
        existingBooking.setNumberOfSeats(3);
        existingBooking.setBookingDateTime(LocalDateTime.parse("2023-12-25 11:00:00", formatter));
        bookings.add(existingBooking);
        
        // Test booking eligibility for Trip T123
        boolean result = trip.validateBookingEligibility("C1", 3);
        
        // Verify results
        assertTrue("Booking should be eligible", result);
        assertEquals("Seats should be reduced from 5 to 2", 2, trip.getNumberOfSeats());
    }
    
    @Test
    public void testCase2_bookingDeniedDueToSeatShortage() {
        // Setup: Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        trip.setTripId("T456");
        trip.setNumberOfSeats(2);
        trip.setDepartureDateTime(LocalDateTime.parse("2023-12-25 10:00:00", formatter));
        trip.setArrivalDateTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        
        // Create Customer C2 with existing booking for T456 (3 seats)
        Booking existingBooking = new Booking();
        existingBooking.setCustomerId("C2");
        existingBooking.setTripId("T456");
        existingBooking.setNumberOfSeats(3);
        existingBooking.setBookingDateTime(LocalDateTime.parse("2023-12-25 07:30:00", formatter));
        bookings.add(existingBooking);
        
        // Test booking eligibility for Trip T456
        boolean result = trip.validateBookingEligibility("C2", 3);
        
        // Verify results
        assertFalse("Booking should be denied due to seat shortage", result);
        assertEquals("Seats should remain unchanged at 2", 2, trip.getNumberOfSeats());
    }
    
    @Test
    public void testCase3_bookingDeniedDueToTimeCutoffExactly2HoursBefore() {
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        trip.setTripId("T100");
        trip.setNumberOfSeats(50);
        trip.setDepartureDateTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        trip.setArrivalDateTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        
        // Create Customer C3 with booking for T100 (3 seats) at exactly 2 hours before departure
        Booking existingBooking = new Booking();
        existingBooking.setCustomerId("C3");
        existingBooking.setTripId("T100");
        existingBooking.setNumberOfSeats(3);
        existingBooking.setBookingDateTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        bookings.add(existingBooking);
        
        // Test booking eligibility for Trip T100
        boolean result = trip.validateBookingEligibility("C3", 3);
        
        // Verify results
        assertFalse("Booking should be denied due to exactly 2 hours cutoff", result);
        assertEquals("Seats should remain unchanged at 47", 47, trip.getNumberOfSeats());
    }
    
    @Test
    public void testCase4_bookingAllowedWithMultipleNonOverlappingTrips() {
        // Setup: Customer C9 has booked an existing booking(2023-12-23 12:00, 2 seats) for Trip T199
        Booking existingBooking = new Booking();
        existingBooking.setCustomerId("C9");
        existingBooking.setTripId("T199");
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingDateTime(LocalDateTime.parse("2023-12-23 12:00:00", formatter));
        bookings.add(existingBooking);
        
        // Setup Trip T200 (2023-12-25 12:00-14:00, 40 seats)
        trip.setTripId("T200");
        trip.setNumberOfSeats(40);
        trip.setDepartureDateTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        trip.setArrivalDateTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        
        // Test booking eligibility for Trip T200
        boolean result = trip.validateBookingEligibility("C9", 4);
        
        // Verify results
        assertTrue("Booking should be allowed for non-overlapping trip", result);
        assertEquals("Seats should be reduced from 40 to 36", 36, trip.getNumberOfSeats());
    }
    
    @Test
    public void testCase5_bookingDeniedWhenCustomerHasOverlappingBooking() {
        // Setup: Customer C10 has existing booking(2023-12-23 12:00, 2 seats) for Trip T299
        Booking existingBooking = new Booking();
        existingBooking.setCustomerId("C10");
        existingBooking.setTripId("T299");
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingDateTime(LocalDateTime.parse("2023-12-23 12:00:00", formatter));
        bookings.add(existingBooking);
        
        // Setup Trip T300 (2023-12-25 14:00-16:00, 40 seats)
        trip.setTripId("T300");
        trip.setNumberOfSeats(40);
        trip.setDepartureDateTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        trip.setArrivalDateTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        
        // Test booking eligibility for Trip T300
        boolean result = trip.validateBookingEligibility("C10", 4);
        
        // Verify results
        assertFalse("Booking should be denied due to overlapping booking", result);
        assertEquals("Seats should remain unchanged at 40", 40, trip.getNumberOfSeats());
    }
}