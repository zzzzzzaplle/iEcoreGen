import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Driver driverD1;
    private Trip tripT123;
    private Customer customerC1;
    private Booking existingBookingT456;
    private Trip tripT456;
    
    private Driver driverD2;
    private Customer customerC2;
    
    private Trip tripT100;
    private Customer customerC3;
    
    private Customer customerC9;
    private Trip tripT199;
    private Booking existingBookingT199;
    private Trip tripT200;
    
    private Customer customerC10;
    private Trip tripT299;
    private Booking existingBookingT299;
    private Trip tripT300;
    
    @Before
    public void setUp() {
        // Setup for all test cases
        driverD1 = new Driver();
        driverD1.setId("D1");
        
        tripT123 = new Trip();
        tripT123.setId("T123");
        tripT123.setDriver(driverD1);
        tripT123.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 14, 0));
        tripT123.setArrivalDateTime(LocalDateTime.of(2023, 12, 25, 16, 0));
        tripT123.setTotalSeats(5);
        tripT123.setAvailableSeats(5);
        tripT123.setPrice(50.0);
        
        driverD1.addTrip(tripT123);
        
        customerC1 = new Customer();
        customerC1.setId("C1");
        
        Trip tripT456Existing = new Trip();
        tripT456Existing.setId("T456");
        tripT456Existing.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 11, 0));
        tripT456Existing.setArrivalDateTime(LocalDateTime.of(2023, 12, 25, 13, 0));
        
        existingBookingT456 = new Booking();
        existingBookingT456.setId("B456");
        existingBookingT456.setCustomer(customerC1);
        existingBookingT456.setTrip(tripT456Existing);
        existingBookingT456.setSeatsBooked(3);
        existingBookingT456.setBookingDateTime(LocalDateTime.of(2023, 12, 25, 8, 0));
        
        customerC1.addBooking(existingBookingT456);
        
        // Setup for Test Case 2
        driverD2 = new Driver();
        driverD2.setId("D2");
        
        tripT456 = new Trip();
        tripT456.setId("T456");
        tripT456.setDriver(driverD2);
        tripT456.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 10, 0));
        tripT456.setArrivalDateTime(LocalDateTime.of(2023, 12, 25, 12, 0));
        tripT456.setTotalSeats(2);
        tripT456.setAvailableSeats(2);
        tripT456.setPrice(40.0);
        
        driverD2.addTrip(tripT456);
        
        customerC2 = new Customer();
        customerC2.setId("C2");
        
        Booking existingBookingForT456 = new Booking();
        existingBookingForT456.setId("B456_2");
        existingBookingForT456.setCustomer(customerC2);
        existingBookingForT456.setTrip(tripT456);
        existingBookingForT456.setSeatsBooked(3);
        existingBookingForT456.setBookingDateTime(LocalDateTime.of(2023, 12, 25, 7, 0));
        
        customerC2.addBooking(existingBookingForT456);
        
        // Setup for Test Case 3
        tripT100 = new Trip();
        tripT100.setId("T100");
        tripT100.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 14, 0));
        tripT100.setArrivalDateTime(LocalDateTime.of(2023, 12, 25, 16, 0));
        tripT100.setTotalSeats(50);
        tripT100.setAvailableSeats(50);
        tripT100.setPrice(60.0);
        
        customerC3 = new Customer();
        customerC3.setId("C3");
        
        // Setup for Test Case 4
        customerC9 = new Customer();
        customerC9.setId("C9");
        
        tripT199 = new Trip();
        tripT199.setId("T199");
        tripT199.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 8, 0));
        tripT199.setArrivalDateTime(LocalDateTime.of(2023, 12, 25, 10, 0));
        tripT199.setTotalSeats(50);
        tripT199.setAvailableSeats(50);
        tripT199.setPrice(45.0);
        
        existingBookingT199 = new Booking();
        existingBookingT199.setId("B199");
        existingBookingT199.setCustomer(customerC9);
        existingBookingT199.setTrip(tripT199);
        existingBookingT199.setSeatsBooked(2);
        existingBookingT199.setBookingDateTime(LocalDateTime.of(2023, 12, 23, 12, 0));
        
        customerC9.addBooking(existingBookingT199);
        
        tripT200 = new Trip();
        tripT200.setId("T200");
        tripT200.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 12, 0));
        tripT200.setArrivalDateTime(LocalDateTime.of(2023, 12, 25, 14, 0));
        tripT200.setTotalSeats(40);
        tripT200.setAvailableSeats(40);
        tripT200.setPrice(55.0);
        
        // Setup for Test Case 5
        customerC10 = new Customer();
        customerC10.setId("C10");
        
        tripT299 = new Trip();
        tripT299.setId("T299");
        tripT299.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 13, 0));
        tripT299.setArrivalDateTime(LocalDateTime.of(2023, 12, 25, 15, 0));
        tripT299.setTotalSeats(50);
        tripT299.setAvailableSeats(50);
        tripT299.setPrice(48.0);
        
        existingBookingT299 = new Booking();
        existingBookingT299.setId("B299");
        existingBookingT299.setCustomer(customerC10);
        existingBookingT299.setTrip(tripT299);
        existingBookingT299.setSeatsBooked(2);
        existingBookingT299.setBookingDateTime(LocalDateTime.of(2023, 12, 23, 12, 0));
        
        customerC10.addBooking(existingBookingT299);
        
        tripT300 = new Trip();
        tripT300.setId("T300");
        tripT300.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 14, 0));
        tripT300.setArrivalDateTime(LocalDateTime.of(2023, 12, 25, 16, 0));
        tripT300.setTotalSeats(40);
        tripT300.setAvailableSeats(40);
        tripT300.setPrice(52.0);
    }
    
    @Test
    public void testCase1_validBookingWithAvailableSeatsAndNoOverlap() {
        // Test Case 1: "Valid booking with available seats and no overlap"
        // Input: Check booking eligibility for Trip T123
        // Setup: 1. Create Driver D1 with Trip T123 (2023-12-25 14:00-16:00, seats:5)
        //        2. Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00 (3 hours before departure)
        // Expected Output: true, updated Trip T123 seats = 2
        
        LocalDateTime bookingTime = LocalDateTime.of(2023, 12, 25, 11, 0);
        int seatsToBook = 3;
        
        boolean result = ORSService.validateBookingEligibility(customerC1, tripT123, seatsToBook, bookingTime);
        
        assertTrue("Booking should be eligible", result);
        assertEquals("Trip T123 available seats should be updated to 2", 2, tripT123.getAvailableSeats());
    }
    
    @Test
    public void testCase2_bookingDeniedDueToSeatShortage() {
        // Test Case 2: "Booking denied due to seat shortage"
        // Input: Check booking eligibility for Trip T456
        // Setup: 1. Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        //        2. Create Customer C2 with existing booking for T456 (3 seats)
        //        3. Current time: 2023-12-25 07:30 (2.5 hours before departure)
        // Expected Output: false, Trip T123 seats = 2
        
        LocalDateTime bookingTime = LocalDateTime.of(2023, 12, 25, 7, 30);
        int seatsToBook = 3;
        
        boolean result = ORSService.validateBookingEligibility(customerC2, tripT456, seatsToBook, bookingTime);
        
        assertFalse("Booking should be denied due to seat shortage", result);
        assertEquals("Trip T456 available seats should remain 2", 2, tripT456.getAvailableSeats());
    }
    
    @Test
    public void testCase3_bookingDeniedDueToTimeCutoffExactly2HoursBefore() {
        // Test Case 3: "Booking denied due to time cutoff (exactly 2 hours before)"
        // Input: Check booking eligibility for Trip T100
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        //        Current time: 2023-12-25 12:00 (exactly 2 hours before)
        //        Create Customer C3 with booking for T100 (3 seats)
        // Expected Output: False, Trip T123 seats = 47
        
        LocalDateTime bookingTime = LocalDateTime.of(2023, 12, 25, 12, 0);
        int seatsToBook = 3;
        
        boolean result = ORSService.validateBookingEligibility(customerC3, tripT100, seatsToBook, bookingTime);
        
        assertFalse("Booking should be denied due to exactly 2 hours cutoff", result);
        assertEquals("Trip T100 available seats should remain 50", 50, tripT100.getAvailableSeats());
    }
    
    @Test
    public void testCase4_bookingAllowedWithMultipleNonOverlappingTrips() {
        // Test Case 4: "Booking allowed with multiple non-overlapping trips"
        // Input: Check booking eligibility for Trip T200
        // Setup: Customer C9 has booked an existing booking(2023-12-23 12:00, 2 seats) for Trip T199 (2023-12-25 08:00-10:00, 50 seats)
        //        Customer C9 create a new booking(2023-12-23 14:00, 4 seats) for Trip T200 (2023-12-25 12:00-14:00 (no overlap), 40 seats)
        // Expected Output: true, Trip T200 seats = 36
        
        LocalDateTime bookingTime = LocalDateTime.of(2023, 12, 23, 14, 0);
        int seatsToBook = 4;
        
        boolean result = ORSService.validateBookingEligibility(customerC9, tripT200, seatsToBook, bookingTime);
        
        assertTrue("Booking should be eligible with non-overlapping trips", result);
        assertEquals("Trip T200 available seats should be updated to 36", 36, tripT200.getAvailableSeats());
    }
    
    @Test
    public void testCase5_bookingDeniedWhenCustomerHasOverlappingBooking() {
        // Test Case 5: "Booking denied when customer has overlapping booking"
        // Input: Check booking eligibility for Trip T300
        // Setup: Customer C10 has existing booking(2023-12-23 12:00, 2 seats) for Trip T299 (departure: 2023-12-25 13:00-15:00, 50 seats)
        //        Customer C10 create a new booking(2023-12-23 14:00, 4 seats) for Trip T300 (2023-12-25 14:00-16:00 (1-hour overlap), 40 seats)
        // Expected Output: false, Trip T300 seats = 40
        
        LocalDateTime bookingTime = LocalDateTime.of(2023, 12, 23, 14, 0);
        int seatsToBook = 4;
        
        boolean result = ORSService.validateBookingEligibility(customerC10, tripT300, seatsToBook, bookingTime);
        
        assertFalse("Booking should be denied due to overlapping trips", result);
        assertEquals("Trip T300 available seats should remain 40", 40, tripT300.getAvailableSeats());
    }
}