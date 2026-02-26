import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR1Test {
    
    private Driver driver;
    private Customer customer;
    private Trip trip;
    private Booking booking;
    
    @Before
    public void setUp() {
        driver = new Driver();
        customer = new Customer();
        trip = new Trip();
        booking = new Booking();
    }
    
    @Test
    public void testCase1_ValidBookingWithAvailableSeatsAndNoOverlap() {
        // Setup: Create Driver D1 with Trip T123 (2023-12-25 14:00-16:00, seats:5)
        driver.setID("D1");
        trip.setDepartureDate("2023-12-25");
        trip.setDepartureTime("14:00");
        trip.setArrivalTime("16:00");
        trip.setNumberOfSeats(5);
        driver.addTrip(trip);
        
        // Setup: Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        customer.setID("C1");
        Trip existingTrip = new Trip();
        existingTrip.setDepartureDate("2023-12-25");
        existingTrip.setDepartureTime("14:00");
        existingTrip.setArrivalTime("16:00");
        existingTrip.setNumberOfSeats(10);
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(existingTrip);
        existingBooking.setNumberOfSeats(3);
        
        // Set current time to 2023-12-25 11:00 (3 hours before departure)
        try {
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date bookingTime = dateTimeFormat.parse("2023-12-25 11:00");
            existingBooking.setBookingDate(bookingTime);
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Create new booking for Trip T123
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer);
        newBooking.setTrip(trip);
        newBooking.setNumberOfSeats(3);
        
        // Set booking time to 2023-12-25 11:00
        try {
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date bookingTime = dateTimeFormat.parse("2023-12-25 11:00");
            newBooking.setBookingDate(bookingTime);
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify results
        assertTrue("Booking should be eligible", result);
        assertEquals("Trip seats should be updated", 2, trip.getNumberOfSeats());
    }
    
    @Test
    public void testCase2_BookingDeniedDueToSeatShortage() {
        // Setup: Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        driver.setID("D2");
        trip.setDepartureDate("2023-12-25");
        trip.setDepartureTime("10:00");
        trip.setArrivalTime("12:00");
        trip.setNumberOfSeats(2);
        driver.addTrip(trip);
        
        // Setup: Create Customer C2 with existing booking for T456 (3 seats)
        customer.setID("C2");
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(trip);
        existingBooking.setNumberOfSeats(3);
        
        // Set current time to 2023-12-25 07:30 (2.5 hours before departure)
        try {
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date bookingTime = dateTimeFormat.parse("2023-12-25 07:30");
            existingBooking.setBookingDate(bookingTime);
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Create new booking for Trip T456
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer);
        newBooking.setTrip(trip);
        newBooking.setNumberOfSeats(3);
        
        // Set booking time to 2023-12-25 07:30
        try {
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date bookingTime = dateTimeFormat.parse("2023-12-25 07:30");
            newBooking.setBookingDate(bookingTime);
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify results
        assertFalse("Booking should be denied due to seat shortage", result);
        assertEquals("Trip seats should remain unchanged", 2, trip.getNumberOfSeats());
    }
    
    @Test
    public void testCase3_BookingDeniedDueToTimeCutoffExactly2HoursBefore() {
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        trip.setDepartureDate("2023-12-25");
        trip.setDepartureTime("14:00");
        trip.setArrivalTime("16:00");
        trip.setNumberOfSeats(50);
        
        // Setup: Create Customer C3 with booking for T100 (3 seats)
        customer.setID("C3");
        
        // Create booking for Trip T100
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer);
        newBooking.setTrip(trip);
        newBooking.setNumberOfSeats(3);
        
        // Set current time to 2023-12-25 12:00 (exactly 2 hours before)
        try {
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date bookingTime = dateTimeFormat.parse("2023-12-25 12:00");
            newBooking.setBookingDate(bookingTime);
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify results
        assertFalse("Booking should be denied when exactly 2 hours before departure", result);
        assertEquals("Trip seats should remain unchanged", 50, trip.getNumberOfSeats());
    }
    
    @Test
    public void testCase4_BookingAllowedWithMultipleNonOverlappingTrips() {
        // Setup: Customer C9 has booked an existing booking(2023-12-23 12:00, 2 seats) for Trip T199 (2023-12-25 08:00-10:00, 50 seats)
        customer.setID("C9");
        
        Trip tripT199 = new Trip();
        tripT199.setDepartureDate("2023-12-25");
        tripT199.setDepartureTime("08:00");
        tripT199.setArrivalTime("10:00");
        tripT199.setNumberOfSeats(50);
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(tripT199);
        existingBooking.setNumberOfSeats(2);
        
        // Set existing booking time to 2023-12-23 12:00
        try {
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date bookingTime = dateTimeFormat.parse("2023-12-23 12:00");
            existingBooking.setBookingDate(bookingTime);
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Setup: Customer C9 create a new booking(2023-12-23 14:00, 4 seats) for Trip T200 (2023-12-25 12:00-14:00, 40 seats)
        Trip tripT200 = new Trip();
        tripT200.setDepartureDate("2023-12-25");
        tripT200.setDepartureTime("12:00");
        tripT200.setArrivalTime("14:00");
        tripT200.setNumberOfSeats(40);
        
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer);
        newBooking.setTrip(tripT200);
        newBooking.setNumberOfSeats(4);
        
        // Set new booking time to 2023-12-23 14:00
        try {
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date bookingTime = dateTimeFormat.parse("2023-12-23 14:00");
            newBooking.setBookingDate(bookingTime);
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify results
        assertTrue("Booking should be allowed with non-overlapping trips", result);
        assertEquals("Trip seats should be updated", 36, tripT200.getNumberOfSeats());
    }
    
    @Test
    public void testCase5_BookingDeniedWhenCustomerHasOverlappingBooking() {
        // Setup: Customer C10 has existing booking(2023-12-23 12:00, 2 seats) for Trip T299 (departure: 2023-12-25 13:00-15:00, 50 seats)
        customer.setID("C10");
        
        Trip tripT299 = new Trip();
        tripT299.setDepartureDate("2023-12-25");
        tripT299.setDepartureTime("13:00");
        tripT299.setArrivalTime("15:00");
        tripT299.setNumberOfSeats(50);
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(tripT299);
        existingBooking.setNumberOfSeats(2);
        
        // Set existing booking time to 2023-12-23 12:00
        try {
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date bookingTime = dateTimeFormat.parse("2023-12-23 12:00");
            existingBooking.setBookingDate(bookingTime);
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Setup: Customer C10 create a new booking(2023-12-23 14:00, 4 seats) for Trip T300 (2023-12-25 14:00-16:00, 40 seats)
        Trip tripT300 = new Trip();
        tripT300.setDepartureDate("2023-12-25");
        tripT300.setDepartureTime("14:00");
        tripT300.setArrivalTime("16:00");
        tripT300.setNumberOfSeats(40);
        
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer);
        newBooking.setTrip(tripT300);
        newBooking.setNumberOfSeats(4);
        
        // Set new booking time to 2023-12-23 14:00
        try {
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date bookingTime = dateTimeFormat.parse("2023-12-23 14:00");
            newBooking.setBookingDate(bookingTime);
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify results
        assertFalse("Booking should be denied when customer has overlapping booking", result);
        assertEquals("Trip seats should remain unchanged", 40, tripT300.getNumberOfSeats());
    }
}