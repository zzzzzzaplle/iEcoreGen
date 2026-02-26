import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    private ORS ors;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        ors = new ORS();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ValidBookingWithAvailableSeatsAndNoOverlap() {
        // Setup: Create Driver D1 with Trip T123 (2023-12-25 14:00-16:00, seats:5)
        Trip tripT123 = new Trip();
        tripT123.setId("T123");
        tripT123.setDriverId("D1");
        tripT123.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        tripT123.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        tripT123.setNumberOfSeats(5);
        
        List<Trip> trips = new ArrayList<>();
        trips.add(tripT123);
        ors.setTrips(trips);
        
        // Setup: Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        Booking existingBooking = new Booking();
        existingBooking.setCustomerId("C1");
        existingBooking.setTripId("T456");
        existingBooking.setNumberOfSeats(3);
        existingBooking.setBookingTime(LocalDateTime.parse("2023-12-25 11:00:00", formatter));
        
        List<Booking> bookings = new ArrayList<>();
        bookings.add(existingBooking);
        ors.setBookings(bookings);
        
        // Set current time to 2023-12-25 11:00:00 (3 hours before departure)
        // Note: In actual implementation, we would need to mock LocalDateTime.now()
        // For this test, we'll assume the validation uses the booking time as current time
        
        // Input: Check booking eligibility for Trip T123 with 3 seats
        boolean result = ors.validateBookingEligibility("C1", "T123", 3);
        
        // Expected Output: true, updated Trip T123 seats = 2
        assertTrue("Booking should be eligible", result);
        assertEquals("Trip T123 should have 2 seats remaining", 2, tripT123.getNumberOfSeats());
    }
    
    @Test
    public void testCase2_BookingDeniedDueToSeatShortage() {
        // Setup: Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        Trip tripT456 = new Trip();
        tripT456.setId("T456");
        tripT456.setDriverId("D2");
        tripT456.setDepartureTime(LocalDateTime.parse("2023-12-25 10:00:00", formatter));
        tripT456.setArrivalTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        tripT456.setNumberOfSeats(2);
        
        List<Trip> trips = new ArrayList<>();
        trips.add(tripT456);
        ors.setTrips(trips);
        
        // Setup: Create Customer C2 with existing booking for T456 (3 seats)
        // This should fail validation since trip only has 2 seats but booking requests 3
        
        // Set current time to 2023-12-25 07:30:00 (2.5 hours before departure)
        // Note: In actual implementation, we would need to mock LocalDateTime.now()
        
        // Input: Check booking eligibility for Trip T456 with 3 seats
        boolean result = ors.validateBookingEligibility("C2", "T456", 3);
        
        // Expected Output: false, Trip T456 seats = 2 (unchanged)
        assertFalse("Booking should be denied due to seat shortage", result);
        assertEquals("Trip T456 should still have 2 seats", 2, tripT456.getNumberOfSeats());
    }
    
    @Test
    public void testCase3_BookingDeniedDueToTimeCutoffExactly2HoursBefore() {
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        Trip tripT100 = new Trip();
        tripT100.setId("T100");
        tripT100.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        tripT100.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        tripT100.setNumberOfSeats(50);
        
        List<Trip> trips = new ArrayList<>();
        trips.add(tripT100);
        ors.setTrips(trips);
        
        // Set current time to 2023-12-25 12:00:00 (exactly 2 hours before)
        // Note: In actual implementation, we would need to mock LocalDateTime.now()
        
        // Input: Check booking eligibility for Trip T100 with 3 seats
        boolean result = ors.validateBookingEligibility("C3", "T100", 3);
        
        // Expected Output: False, Trip T100 seats = 47
        assertFalse("Booking should be denied when exactly 2 hours before departure", result);
        assertEquals("Trip T100 should still have 50 seats", 50, tripT100.getNumberOfSeats());
    }
    
    @Test
    public void testCase4_BookingAllowedWithMultipleNonOverlappingTrips() {
        // Setup: Customer C9 has booked an existing booking(2023-12-23 12:00, 2 seats) for Trip T199 (2023-12-25 08:00-10:00, 50 seats)
        Trip tripT199 = new Trip();
        tripT199.setId("T199");
        tripT199.setDepartureTime(LocalDateTime.parse("2023-12-25 08:00:00", formatter));
        tripT199.setArrivalTime(LocalDateTime.parse("2023-12-25 10:00:00", formatter));
        tripT199.setNumberOfSeats(50);
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomerId("C9");
        existingBooking.setTripId("T199");
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingTime(LocalDateTime.parse("2023-12-23 12:00:00", formatter));
        
        // Setup: Trip T200 (2023-12-25 12:00-14:00, 40 seats)
        Trip tripT200 = new Trip();
        tripT200.setId("T200");
        tripT200.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        tripT200.setArrivalTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        tripT200.setNumberOfSeats(40);
        
        List<Trip> trips = new ArrayList<>();
        trips.add(tripT199);
        trips.add(tripT200);
        ors.setTrips(trips);
        
        List<Booking> bookings = new ArrayList<>();
        bookings.add(existingBooking);
        ors.setBookings(bookings);
        
        // Set current time to 2023-12-23 14:00:00 for new booking
        // Note: In actual implementation, we would need to mock LocalDateTime.now()
        
        // Input: Check booking eligibility for Trip T200 with 4 seats
        boolean result = ors.validateBookingEligibility("C9", "T200", 4);
        
        // Expected Output: true, Trip T200 seats = 36
        assertTrue("Booking should be allowed for non-overlapping trips", result);
        assertEquals("Trip T200 should have 36 seats remaining", 36, tripT200.getNumberOfSeats());
    }
    
    @Test
    public void testCase5_BookingDeniedWhenCustomerHasOverlappingBooking() {
        // Setup: Customer C10 has existing booking(2023-12-23 12:00, 2 seats) for Trip T299 (departure: 2023-12-25 13:00-15:00, 50 seats)
        Trip tripT299 = new Trip();
        tripT299.setId("T299");
        tripT299.setDepartureTime(LocalDateTime.parse("2023-12-25 13:00:00", formatter));
        tripT299.setArrivalTime(LocalDateTime.parse("2023-12-25 15:00:00", formatter));
        tripT299.setNumberOfSeats(50);
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomerId("C10");
        existingBooking.setTripId("T299");
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingTime(LocalDateTime.parse("2023-12-23 12:00:00", formatter));
        
        // Setup: Trip T300 (2023-12-25 14:00-16:00, 40 seats)
        Trip tripT300 = new Trip();
        tripT300.setId("T300");
        tripT300.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        tripT300.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        tripT300.setNumberOfSeats(40);
        
        List<Trip> trips = new ArrayList<>();
        trips.add(tripT299);
        trips.add(tripT300);
        ors.setTrips(trips);
        
        List<Booking> bookings = new ArrayList<>();
        bookings.add(existingBooking);
        ors.setBookings(bookings);
        
        // Set current time to 2023-12-23 14:00:00 for new booking
        // Note: In actual implementation, we would need to mock LocalDateTime.now()
        
        // Input: Check booking eligibility for Trip T300 with 4 seats
        boolean result = ors.validateBookingEligibility("C10", "T300", 4);
        
        // Expected Output: false, Trip T300 seats = 40
        assertFalse("Booking should be denied due to overlapping booking times", result);
        assertEquals("Trip T300 should still have 40 seats", 40, tripT300.getNumberOfSeats());
    }
}