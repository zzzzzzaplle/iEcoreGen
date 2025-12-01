import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR1Test {
    
    private Driver driver1;
    private Driver driver2;
    private Customer customer1;
    private Customer customer2;
    private Customer customer3;
    private Customer customer9;
    private Customer customer10;
    private Trip tripT123;
    private Trip tripT456;
    private Trip tripT100;
    private Trip tripT199;
    private Trip tripT200;
    private Trip tripT299;
    private Trip tripT300;
    private SimpleDateFormat sdf;
    
    @Before
    public void setUp() {
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        // Initialize drivers
        driver1 = new Driver();
        driver2 = new Driver();
        
        // Initialize customers
        customer1 = new Customer();
        customer2 = new Customer();
        customer3 = new Customer();
        customer9 = new Customer();
        customer10 = new Customer();
        
        // Initialize trips
        tripT123 = new Trip();
        tripT456 = new Trip();
        tripT100 = new Trip();
        tripT199 = new Trip();
        tripT200 = new Trip();
        tripT299 = new Trip();
        tripT300 = new Trip();
    }
    
    @Test
    public void testCase1_validBookingWithAvailableSeatsAndNoOverlap() {
        // Setup: Create Driver D1 with Trip T123 (2023-12-25 14:00-16:00, seats:5)
        tripT123.setDepartureTime("2023-12-25 14:00");
        tripT123.setArrivalTime("2023-12-25 16:00");
        tripT123.setNumberOfSeats(5);
        driver1.addTrip(tripT123);
        
        // Setup: Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        Trip tripT456Temp = new Trip();
        tripT456Temp.setDepartureTime("2023-12-25 14:00");
        tripT456Temp.setArrivalTime("2023-12-25 16:00");
        tripT456Temp.setNumberOfSeats(10);
        
        Booking existingBooking = new Booking();
        existingBooking.setTrip(tripT456Temp);
        existingBooking.setNumberOfSeats(3);
        existingBooking.setCustomer(customer1);
        try {
            existingBooking.setBookingDate(sdf.parse("2023-12-25 11:00"));
        } catch (Exception e) {}
        
        // Set current time to 2023-12-25 11:00 (3 hours before departure)
        Date currentTime = null;
        try {
            currentTime = sdf.parse("2023-12-25 11:00");
        } catch (Exception e) {}
        
        // Test booking eligibility for Trip T123 with 3 seats
        boolean result = tripT123.bookSeats(3);
        
        // Verify results
        assertTrue("Booking should be successful", result);
        assertEquals("Trip seats should be updated to 2", 2, tripT123.getNumberOfSeats());
    }
    
    @Test
    public void testCase2_bookingDeniedDueToSeatShortage() {
        // Setup: Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        tripT456.setDepartureTime("2023-12-25 10:00");
        tripT456.setArrivalTime("2023-12-25 12:00");
        tripT456.setNumberOfSeats(2);
        driver2.addTrip(tripT456);
        
        // Setup: Create Customer C2 with existing booking for T456 (3 seats)
        Booking existingBooking = new Booking();
        existingBooking.setTrip(tripT456);
        existingBooking.setNumberOfSeats(3);
        existingBooking.setCustomer(customer2);
        
        // Set current time to 2023-12-25 07:30 (2.5 hours before departure)
        Date currentTime = null;
        try {
            currentTime = sdf.parse("2023-12-25 07:30");
        } catch (Exception e) {}
        
        // Test booking eligibility for Trip T456 with 3 seats
        boolean result = tripT456.bookSeats(3);
        
        // Verify results
        assertFalse("Booking should be denied due to seat shortage", result);
        assertEquals("Trip seats should remain 2", 2, tripT456.getNumberOfSeats());
    }
    
    @Test
    public void testCase3_bookingDeniedDueToTimeCutoffExactly2HoursBefore() {
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        tripT100.setDepartureTime("2023-12-25 14:00");
        tripT100.setArrivalTime("2023-12-25 16:00");
        tripT100.setNumberOfSeats(50);
        
        // Set current time to 2023-12-25 12:00 (exactly 2 hours before)
        Date currentTime = null;
        try {
            currentTime = sdf.parse("2023-12-25 12:00");
        } catch (Exception e) {}
        
        // Test booking eligibility for Trip T100 with 3 seats
        boolean result = tripT100.bookSeats(3);
        
        // Verify results
        assertFalse("Booking should be denied due to exactly 2 hours cutoff", result);
        assertEquals("Trip seats should remain 50", 50, tripT100.getNumberOfSeats());
    }
    
    @Test
    public void testCase4_bookingAllowedWithMultipleNonOverlappingTrips() {
        // Setup: Customer C9 has booked an existing booking(2023-12-23 12:00, 2 seats) for Trip T199 (2023-12-25 08:00-10:00, 50 seats)
        tripT199.setDepartureTime("2023-12-25 08:00");
        tripT199.setArrivalTime("2023-12-25 10:00");
        tripT199.setNumberOfSeats(50);
        
        Booking existingBooking = new Booking();
        existingBooking.setTrip(tripT199);
        existingBooking.setNumberOfSeats(2);
        existingBooking.setCustomer(customer9);
        try {
            existingBooking.setBookingDate(sdf.parse("2023-12-23 12:00"));
        } catch (Exception e) {}
        
        // Setup: Customer C9 create a new booking(2023-12-23 14:00, 4 seats) for Trip T200 (2023-12-25 12:00-14:00, 40 seats)
        tripT200.setDepartureTime("2023-12-25 12:00");
        tripT200.setArrivalTime("2023-12-25 14:00");
        tripT200.setNumberOfSeats(40);
        
        // Set current time to 2023-12-23 14:00 (well before departure)
        Date currentTime = null;
        try {
            currentTime = sdf.parse("2023-12-23 14:00");
        } catch (Exception e) {}
        
        // Test booking eligibility for Trip T200 with 4 seats
        boolean result = tripT200.bookSeats(4);
        
        // Verify results
        assertTrue("Booking should be allowed with non-overlapping trips", result);
        assertEquals("Trip seats should be updated to 36", 36, tripT200.getNumberOfSeats());
    }
    
    @Test
    public void testCase5_bookingDeniedWhenCustomerHasOverlappingBooking() {
        // Setup: Customer C10 has existing booking(2023-12-23 12:00, 2 seats) for Trip T299 (departure: 2023-12-25 13:00-15:00, 50 seats)
        tripT299.setDepartureTime("2023-12-25 13:00");
        tripT299.setArrivalTime("2023-12-25 15:00");
        tripT299.setNumberOfSeats(50);
        
        Booking existingBooking = new Booking();
        existingBooking.setTrip(tripT299);
        existingBooking.setNumberOfSeats(2);
        existingBooking.setCustomer(customer10);
        try {
            existingBooking.setBookingDate(sdf.parse("2023-12-23 12:00"));
        } catch (Exception e) {}
        
        // Setup: Customer C10 create a new booking(2023-12-23 14:00, 4 seats) for Trip T300 (2023-12-25 14:00-16:00, 40 seats)
        tripT300.setDepartureTime("2023-12-25 14:00");
        tripT300.setArrivalTime("2023-12-25 16:00");
        tripT300.setNumberOfSeats(40);
        
        // Set current time to 2023-12-23 14:00 (well before departure)
        Date currentTime = null;
        try {
            currentTime = sdf.parse("2023-12-23 14:00");
        } catch (Exception e) {}
        
        // Test booking eligibility for Trip T300 with 4 seats
        boolean result = tripT300.bookSeats(4);
        
        // Verify results
        assertFalse("Booking should be denied due to overlapping time periods", result);
        assertEquals("Trip seats should remain 40", 40, tripT300.getNumberOfSeats());
    }
}