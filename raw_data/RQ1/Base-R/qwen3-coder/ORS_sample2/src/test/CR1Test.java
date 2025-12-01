import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        Driver driver1 = new Driver();
        driver1.setUserID("D1");
        
        Trip trip123 = new Trip();
        trip123.setDepartureStation("Station A");
        trip123.setArrivalStation("Station B");
        trip123.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        trip123.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        trip123.setNumberOfSeats(5);
        trip123.setDriver(driver1);
        driver1.addTrip(trip123);
        
        // Setup: Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        Customer customer1 = new Customer();
        customer1.setUserID("C1");
        
        // Create existing booking T456 on different day to avoid overlap
        Trip trip456 = new Trip();
        trip456.setDepartureTime(LocalDateTime.parse("2023-12-26 14:00:00", formatter));
        trip456.setArrivalTime(LocalDateTime.parse("2023-12-26 16:00:00", formatter));
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer1);
        existingBooking.setTrip(trip456);
        existingBooking.setNumberOfSeats(3);
        existingBooking.setBookingTime(LocalDateTime.parse("2023-12-25 11:00:00", formatter));
        customer1.addBooking(existingBooking);
        
        // Test booking for Trip T123
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 11:00:00", formatter);
        boolean result = system.validateBookingEligibility(customer1, trip123, 3, bookingTime);
        
        // Verify result and updated seats
        assertTrue("Booking should be eligible", result);
        assertEquals("Trip T123 seats should be updated to 2", 2, trip123.getNumberOfSeats());
    }
    
    @Test
    public void testCase2_bookingDeniedDueToSeatShortage() {
        // Setup: Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        Driver driver2 = new Driver();
        driver2.setUserID("D2");
        
        Trip trip456 = new Trip();
        trip456.setDepartureStation("Station C");
        trip456.setArrivalStation("Station D");
        trip456.setDepartureTime(LocalDateTime.parse("2023-12-25 10:00:00", formatter));
        trip456.setArrivalTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        trip456.setNumberOfSeats(2);
        trip456.setDriver(driver2);
        driver2.addTrip(trip456);
        
        // Setup: Create Customer C2 with existing booking for T456 (3 seats)
        Customer customer2 = new Customer();
        customer2.setUserID("C2");
        
        // Current time: 2023-12-25 07:30 (2.5 hours before departure)
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 07:30:00", formatter);
        
        // Test booking for Trip T456 - should fail due to seat shortage
        boolean result = system.validateBookingEligibility(customer2, trip456, 3, bookingTime);
        
        // Verify result and unchanged seats
        assertFalse("Booking should be denied due to seat shortage", result);
        assertEquals("Trip T456 seats should remain 2", 2, trip456.getNumberOfSeats());
    }
    
    @Test
    public void testCase3_bookingDeniedDueToTimeCutoffExactly2HoursBefore() {
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        Trip trip100 = new Trip();
        trip100.setDepartureStation("Station E");
        trip100.setArrivalStation("Station F");
        trip100.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        trip100.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        trip100.setNumberOfSeats(50);
        
        // Setup: Create Customer C3
        Customer customer3 = new Customer();
        customer3.setUserID("C3");
        
        // Current time: 2023-12-25 12:00 (exactly 2 hours before)
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 12:00:00", formatter);
        
        // Test booking for Trip T100 - should fail due to exact 2-hour cutoff
        boolean result = system.validateBookingEligibility(customer3, trip100, 3, bookingTime);
        
        // Verify result and unchanged seats
        assertFalse("Booking should be denied due to exact 2-hour cutoff", result);
        assertEquals("Trip T100 seats should remain 50", 50, trip100.getNumberOfSeats());
    }
    
    @Test
    public void testCase4_bookingAllowedWithMultipleNonOverlappingTrips() {
        // Setup: Customer C9 has booked an existing booking(2023-12-23 12:00, 2 seats) for Trip T199 (2023-12-25 08:00-10:00, 50 seats)
        Customer customer9 = new Customer();
        customer9.setUserID("C9");
        
        Trip trip199 = new Trip();
        trip199.setDepartureTime(LocalDateTime.parse("2023-12-25 08:00:00", formatter));
        trip199.setArrivalTime(LocalDateTime.parse("2023-12-25 10:00:00", formatter));
        trip199.setNumberOfSeats(50);
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer9);
        existingBooking.setTrip(trip199);
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingTime(LocalDateTime.parse("2023-12-23 12:00:00", formatter));
        customer9.addBooking(existingBooking);
        
        // Setup: Customer C9 create a new booking(2023-12-23 14:00, 4 seats) for Trip T200 (2023-12-25 12:00-14:00, 40 seats)
        Trip trip200 = new Trip();
        trip200.setDepartureStation("Station G");
        trip200.setArrivalStation("Station H");
        trip200.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        trip200.setArrivalTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        trip200.setNumberOfSeats(40);
        
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-23 14:00:00", formatter);
        
        // Test booking for Trip T200 - should succeed (no overlap)
        boolean result = system.validateBookingEligibility(customer9, trip200, 4, bookingTime);
        
        // Verify result and updated seats
        assertTrue("Booking should be allowed with non-overlapping trips", result);
        assertEquals("Trip T200 seats should be updated to 36", 36, trip200.getNumberOfSeats());
    }
    
    @Test
    public void testCase5_bookingDeniedWhenCustomerHasOverlappingBooking() {
        // Setup: Customer C10 has existing booking(2023-12-23 12:00, 2 seats) for Trip T299 (departure: 2023-12-25 13:00-15:00, 50 seats)
        Customer customer10 = new Customer();
        customer10.setUserID("C10");
        
        Trip trip299 = new Trip();
        trip299.setDepartureTime(LocalDateTime.parse("2023-12-25 13:00:00", formatter));
        trip299.setArrivalTime(LocalDateTime.parse("2023-12-25 15:00:00", formatter));
        trip299.setNumberOfSeats(50);
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer10);
        existingBooking.setTrip(trip299);
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingTime(LocalDateTime.parse("2023-12-23 12:00:00", formatter));
        customer10.addBooking(existingBooking);
        
        // Setup: Customer C10 create a new booking(2023-12-23 14:00, 4 seats) for Trip T300 (2023-12-25 14:00-16:00, 40 seats)
        Trip trip300 = new Trip();
        trip300.setDepartureStation("Station I");
        trip300.setArrivalStation("Station J");
        trip300.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        trip300.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        trip300.setNumberOfSeats(40);
        
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-23 14:00:00", formatter);
        
        // Test booking for Trip T300 - should fail due to 1-hour overlap
        boolean result = system.validateBookingEligibility(customer10, trip300, 4, bookingTime);
        
        // Verify result and unchanged seats
        assertFalse("Booking should be denied due to overlapping time periods", result);
        assertEquals("Trip T300 seats should remain 40", 40, trip300.getNumberOfSeats());
    }
}