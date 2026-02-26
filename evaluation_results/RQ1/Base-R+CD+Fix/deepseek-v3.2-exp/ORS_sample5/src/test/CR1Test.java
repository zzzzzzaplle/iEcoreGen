import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

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
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Initialize drivers
        driver1 = new Driver();
        driver1.setID("D1");
        
        driver2 = new Driver();
        driver2.setID("D2");
        
        // Initialize customers
        customer1 = new Customer();
        customer1.setID("C1");
        
        customer2 = new Customer();
        customer2.setID("C2");
        
        customer3 = new Customer();
        customer3.setID("C3");
        
        customer9 = new Customer();
        customer9.setID("C9");
        
        customer10 = new Customer();
        customer10.setID("C10");
        
        // Initialize trips
        tripT123 = new Trip();
        tripT123.setDepartureTime("2023-12-25 14:00");
        tripT123.setArrivalTime("2023-12-25 16:00");
        tripT123.setNumberOfSeats(5);
        
        tripT456 = new Trip();
        tripT456.setDepartureTime("2023-12-25 10:00");
        tripT456.setArrivalTime("2023-12-25 12:00");
        tripT456.setNumberOfSeats(2);
        
        tripT100 = new Trip();
        tripT100.setDepartureTime("2023-12-25 14:00");
        tripT100.setArrivalTime("2023-12-25 16:00");
        tripT100.setNumberOfSeats(50);
        
        tripT199 = new Trip();
        tripT199.setDepartureTime("2023-12-25 08:00");
        tripT199.setArrivalTime("2023-12-25 10:00");
        tripT199.setNumberOfSeats(50);
        
        tripT200 = new Trip();
        tripT200.setDepartureTime("2023-12-25 12:00");
        tripT200.setArrivalTime("2023-12-25 14:00");
        tripT200.setNumberOfSeats(40);
        
        tripT299 = new Trip();
        tripT299.setDepartureTime("2023-12-25 13:00");
        tripT299.setArrivalTime("2023-12-25 15:00");
        tripT299.setNumberOfSeats(50);
        
        tripT300 = new Trip();
        tripT300.setDepartureTime("2023-12-25 14:00");
        tripT300.setArrivalTime("2023-12-25 16:00");
        tripT300.setNumberOfSeats(40);
    }
    
    @Test
    public void testCase1_ValidBookingWithAvailableSeatsAndNoOverlap() throws Exception {
        // Setup: Create Driver D1 with Trip T123
        driver1.getTrips().add(tripT123);
        
        // Setup: Create Customer C1 with booking for T456
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer1);
        existingBooking.setTrip(tripT456);
        existingBooking.setNumberOfSeats(3);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-25 11:00:00"));
        
        // Create new booking for T123
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer1);
        newBooking.setTrip(tripT123);
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDate(dateFormat.parse("2023-12-25 11:00:00"));
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Update trip seats if eligible
        if (result) {
            newBooking.updateTripSeats();
        }
        
        // Verify results
        assertTrue("Booking should be eligible", result);
        assertEquals("Trip T123 seats should be updated to 2", 2, tripT123.getNumberOfSeats());
    }
    
    @Test
    public void testCase2_BookingDeniedDueToSeatShortage() throws Exception {
        // Setup: Create Driver D2 with Trip T456
        driver2.getTrips().add(tripT456);
        
        // Setup: Create Customer C2 with existing booking for T456 (3 seats)
        // This would normally exceed available seats (2), but we'll simulate it
        tripT456.setNumberOfSeats(2); // Reset to original state
        
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer2);
        newBooking.setTrip(tripT456);
        newBooking.setNumberOfSeats(3); // Trying to book 3 seats when only 2 available
        newBooking.setBookingDate(dateFormat.parse("2023-12-25 07:30:00"));
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Update trip seats if eligible
        if (result) {
            newBooking.updateTripSeats();
        }
        
        // Verify results
        assertFalse("Booking should be denied due to seat shortage", result);
        assertEquals("Trip T123 seats should remain 2", 2, tripT456.getNumberOfSeats());
    }
    
    @Test
    public void testCase3_BookingDeniedDueToTimeCutoffExactly2HoursBefore() throws Exception {
        // Setup: Create Trip T100
        // Setup: Current time exactly 2 hours before departure
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer3);
        newBooking.setTrip(tripT100);
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDate(dateFormat.parse("2023-12-25 12:00:00"));
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Update trip seats if eligible
        if (result) {
            newBooking.updateTripSeats();
        }
        
        // Verify results
        assertFalse("Booking should be denied due to exactly 2 hours cutoff", result);
        assertEquals("Trip T100 seats should remain 50", 50, tripT100.getNumberOfSeats());
    }
    
    @Test
    public void testCase4_BookingAllowedWithMultipleNonOverlappingTrips() throws Exception {
        // Setup: Customer C9 has existing booking for Trip T199
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer9);
        existingBooking.setTrip(tripT199);
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-23 12:00:00"));
        
        // Setup: Customer C9 create new booking for Trip T200 (no overlap)
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer9);
        newBooking.setTrip(tripT200);
        newBooking.setNumberOfSeats(4);
        newBooking.setBookingDate(dateFormat.parse("2023-12-23 14:00:00"));
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Update trip seats if eligible
        if (result) {
            newBooking.updateTripSeats();
        }
        
        // Verify results
        assertTrue("Booking should be allowed with non-overlapping trips", result);
        assertEquals("Trip T200 seats should be updated to 36", 36, tripT200.getNumberOfSeats());
    }
    
    @Test
    public void testCase5_BookingDeniedWhenCustomerHasOverlappingBooking() throws Exception {
        // Setup: Customer C10 has existing booking for Trip T299
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer10);
        existingBooking.setTrip(tripT299);
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-23 12:00:00"));
        
        // Setup: Customer C10 create new booking for Trip T300 (1-hour overlap)
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer10);
        newBooking.setTrip(tripT300);
        newBooking.setNumberOfSeats(4);
        newBooking.setBookingDate(dateFormat.parse("2023-12-23 14:00:00"));
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Update trip seats if eligible
        if (result) {
            newBooking.updateTripSeats();
        }
        
        // Verify results
        assertFalse("Booking should be denied due to overlapping booking", result);
        assertEquals("Trip T300 seats should remain 40", 40, tripT300.getNumberOfSeats());
    }
}