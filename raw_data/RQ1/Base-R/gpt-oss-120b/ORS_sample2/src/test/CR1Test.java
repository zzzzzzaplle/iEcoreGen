import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }
    
    @Test
    public void testCase1_validBookingWithAvailableSeatsAndNoOverlap() {
        // Setup: Create Driver D1 with Trip T123 (2023-12-25 14:00-16:00, seats:5)
        Driver driverD1 = new Driver();
        driverD1.setId("D1");
        
        Trip tripT123 = new Trip();
        tripT123.setId("T123");
        tripT123.setDriver(driverD1);
        tripT123.setDepartureDateTime(LocalDateTime.parse("2023-12-25 14:00", formatter));
        tripT123.setArrivalDateTime(LocalDateTime.parse("2023-12-25 16:00", formatter));
        tripT123.setSeats(5);
        tripT123.setPrice(50.0);
        
        driverD1.getTrips().add(tripT123);
        
        // Setup: Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        Customer customerC1 = new Customer();
        customerC1.setId("C1");
        
        // Create a different trip T456 for the existing booking
        Trip tripT456 = new Trip();
        tripT456.setId("T456");
        tripT456.setDepartureDateTime(LocalDateTime.parse("2023-12-25 14:00", formatter));
        tripT456.setArrivalDateTime(LocalDateTime.parse("2023-12-25 15:00", formatter));
        tripT456.setSeats(10);
        tripT456.setPrice(30.0);
        
        Booking existingBooking = new Booking();
        existingBooking.setId("BKG-EXISTING");
        existingBooking.setCustomer(customerC1);
        existingBooking.setTrip(tripT456);
        existingBooking.setSeatsBooked(3);
        existingBooking.setBookingDateTime(LocalDateTime.parse("2023-12-25 11:00", formatter));
        
        customerC1.getBookings().add(existingBooking);
        tripT456.getBookings().add(existingBooking);
        
        // Test input: Check booking eligibility for Trip T123
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 11:00", formatter);
        int seatsRequested = 3;
        
        // Execute the method
        boolean result = ORSService.validateBookingEligibility(customerC1, tripT123, seatsRequested, bookingTime);
        
        // Verify results
        assertTrue("Booking should be allowed", result);
        assertEquals("Trip T123 seats should be updated to 2", 2, tripT123.getSeats());
        
        // Verify booking was created
        assertEquals("Customer should have 2 bookings", 2, customerC1.getBookings().size());
        assertEquals("Trip T123 should have 1 booking", 1, tripT123.getBookings().size());
    }
    
    @Test
    public void testCase2_bookingDeniedDueToSeatShortage() {
        // Setup: Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        Driver driverD2 = new Driver();
        driverD2.setId("D2");
        
        Trip tripT456 = new Trip();
        tripT456.setId("T456");
        tripT456.setDriver(driverD2);
        tripT456.setDepartureDateTime(LocalDateTime.parse("2023-12-25 10:00", formatter));
        tripT456.setArrivalDateTime(LocalDateTime.parse("2023-12-25 12:00", formatter));
        tripT456.setSeats(2);
        tripT456.setPrice(40.0);
        
        driverD2.getTrips().add(tripT456);
        
        // Setup: Create Customer C2 with existing booking for T456 (3 seats)
        Customer customerC2 = new Customer();
        customerC2.setId("C2");
        
        Booking existingBooking = new Booking();
        existingBooking.setId("BKG-EXISTING");
        existingBooking.setCustomer(customerC2);
        existingBooking.setTrip(tripT456);
        existingBooking.setSeatsBooked(3);
        existingBooking.setBookingDateTime(LocalDateTime.parse("2023-12-25 07:00", formatter));
        
        customerC2.getBookings().add(existingBooking);
        tripT456.getBookings().add(existingBooking);
        
        // Current time: 2023-12-25 07:30 (2.5 hours before departure)
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 07:30", formatter);
        int seatsRequested = 3;
        
        // Execute the method
        boolean result = ORSService.validateBookingEligibility(customerC2, tripT456, seatsRequested, bookingTime);
        
        // Verify results
        assertFalse("Booking should be denied due to seat shortage", result);
        assertEquals("Trip T456 seats should remain 2", 2, tripT456.getSeats());
        
        // Verify no new booking was created
        assertEquals("Customer should have only 1 booking", 1, customerC2.getBookings().size());
        assertEquals("Trip T456 should have only 1 booking", 1, tripT456.getBookings().size());
    }
    
    @Test
    public void testCase3_bookingDeniedDueToTimeCutoffExactly2HoursBefore() {
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        Trip tripT100 = new Trip();
        tripT100.setId("T100");
        tripT100.setDepartureDateTime(LocalDateTime.parse("2023-12-25 14:00", formatter));
        tripT100.setArrivalDateTime(LocalDateTime.parse("2023-12-25 16:00", formatter));
        tripT100.setSeats(50);
        tripT100.setPrice(60.0);
        
        // Create Customer C3
        Customer customerC3 = new Customer();
        customerC3.setId("C3");
        
        // Current time: 2023-12-25 12:00 (exactly 2 hours before)
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 12:00", formatter);
        int seatsRequested = 3;
        
        // Execute the method
        boolean result = ORSService.validateBookingEligibility(customerC3, tripT100, seatsRequested, bookingTime);
        
        // Verify results
        assertFalse("Booking should be denied due to exactly 2 hours cutoff", result);
        assertEquals("Trip T100 seats should remain 50", 50, tripT100.getSeats());
        
        // Verify no booking was created
        assertEquals("Customer should have no bookings", 0, customerC3.getBookings().size());
        assertEquals("Trip T100 should have no bookings", 0, tripT100.getBookings().size());
    }
    
    @Test
    public void testCase4_bookingAllowedWithMultipleNonOverlappingTrips() {
        // Setup: Customer C9 has booked an existing booking(2023-12-23 12:00, 2 seats) for Trip T199 (2023-12-25 08:00-10:00, 50 seats)
        Customer customerC9 = new Customer();
        customerC9.setId("C9");
        
        Trip tripT199 = new Trip();
        tripT199.setId("T199");
        tripT199.setDepartureDateTime(LocalDateTime.parse("2023-12-25 08:00", formatter));
        tripT199.setArrivalDateTime(LocalDateTime.parse("2023-12-25 10:00", formatter));
        tripT199.setSeats(50);
        tripT199.setPrice(35.0);
        
        Booking existingBooking = new Booking();
        existingBooking.setId("BKG-EXISTING");
        existingBooking.setCustomer(customerC9);
        existingBooking.setTrip(tripT199);
        existingBooking.setSeatsBooked(2);
        existingBooking.setBookingDateTime(LocalDateTime.parse("2023-12-23 12:00", formatter));
        
        customerC9.getBookings().add(existingBooking);
        tripT199.getBookings().add(existingBooking);
        
        // Setup: Trip T200 (2023-12-25 12:00-14:00, 40 seats)
        Trip tripT200 = new Trip();
        tripT200.setId("T200");
        tripT200.setDepartureDateTime(LocalDateTime.parse("2023-12-25 12:00", formatter));
        tripT200.setArrivalDateTime(LocalDateTime.parse("2023-12-25 14:00", formatter));
        tripT200.setSeats(40);
        tripT200.setPrice(45.0);
        
        // Customer C9 create a new booking(2023-12-23 14:00, 4 seats) for Trip T200
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-23 14:00", formatter);
        int seatsRequested = 4;
        
        // Execute the method
        boolean result = ORSService.validateBookingEligibility(customerC9, tripT200, seatsRequested, bookingTime);
        
        // Verify results
        assertTrue("Booking should be allowed with non-overlapping trips", result);
        assertEquals("Trip T200 seats should be updated to 36", 36, tripT200.getSeats());
        
        // Verify new booking was created
        assertEquals("Customer should have 2 bookings", 2, customerC9.getBookings().size());
        assertEquals("Trip T200 should have 1 booking", 1, tripT200.getBookings().size());
    }
    
    @Test
    public void testCase5_bookingDeniedWhenCustomerHasOverlappingBooking() {
        // Setup: Customer C10 has existing booking(2023-12-23 12:00, 2 seats) for Trip T299 (departure: 2023-12-25 13:00-15:00, 50 seats)
        Customer customerC10 = new Customer();
        customerC10.setId("C10");
        
        Trip tripT299 = new Trip();
        tripT299.setId("T299");
        tripT299.setDepartureDateTime(LocalDateTime.parse("2023-12-25 13:00", formatter));
        tripT299.setArrivalDateTime(LocalDateTime.parse("2023-12-25 15:00", formatter));
        tripT299.setSeats(50);
        tripT299.setPrice(55.0);
        
        Booking existingBooking = new Booking();
        existingBooking.setId("BKG-EXISTING");
        existingBooking.setCustomer(customerC10);
        existingBooking.setTrip(tripT299);
        existingBooking.setSeatsBooked(2);
        existingBooking.setBookingDateTime(LocalDateTime.parse("2023-12-23 12:00", formatter));
        
        customerC10.getBookings().add(existingBooking);
        tripT299.getBookings().add(existingBooking);
        
        // Setup: Trip T300 (2023-12-25 14:00-16:00, 40 seats) - 1-hour overlap with T299
        Trip tripT300 = new Trip();
        tripT300.setId("T300");
        tripT300.setDepartureDateTime(LocalDateTime.parse("2023-12-25 14:00", formatter));
        tripT300.setArrivalDateTime(LocalDateTime.parse("2023-12-25 16:00", formatter));
        tripT300.setSeats(40);
        tripT300.setPrice(60.0);
        
        // Customer C10 create a new booking(2023-12-23 14:00, 4 seats) for Trip T300
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-23 14:00", formatter);
        int seatsRequested = 4;
        
        // Execute the method
        boolean result = ORSService.validateBookingEligibility(customerC10, tripT300, seatsRequested, bookingTime);
        
        // Verify results
        assertFalse("Booking should be denied due to overlap", result);
        assertEquals("Trip T300 seats should remain 40", 40, tripT300.getSeats());
        
        // Verify no new booking was created
        assertEquals("Customer should have only 1 booking", 1, customerC10.getBookings().size());
        assertEquals("Trip T300 should have no bookings", 0, tripT300.getBookings().size());
    }
}