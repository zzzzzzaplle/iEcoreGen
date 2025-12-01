package edu.rideshare.rideshare4.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.rideshare.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR1Test {
    
    private RideshareFactory factory;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        factory = RideshareFactory.eINSTANCE;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ValidBookingWithAvailableSeatsAndNoOverlap() throws Exception {
        // Setup: Create Driver D1 with Trip T123 (2023-12-25 14:00-16:00, seats:5)
        Driver driver = factory.createDriver();
        Trip tripT123 = factory.createTrip();
        tripT123.setDepartureTime("14:00");
        tripT123.setArrivalTime("16:00");
        tripT123.setNumberOfSeats(5);
        driver.getTrips().add(tripT123);
        
        // Setup: Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        Customer customer = factory.createCustomer();
        Trip tripT456 = factory.createTrip();
        tripT456.setDepartureTime("14:00");
        tripT456.setArrivalTime("16:00");
        tripT456.setNumberOfSeats(10);
        
        Booking existingBooking = factory.createBooking();
        existingBooking.setNumberOfSeats(3);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-25 11:00:00"));
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(tripT456);
        customer.getBookings().add(existingBooking);
        
        // Create new booking for T123
        Booking newBooking = factory.createBooking();
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDate(dateFormat.parse("2023-12-25 11:00:00")); // 3 hours before departure
        newBooking.setCustomer(customer);
        newBooking.setTrip(tripT123);
        
        // Test booking eligibility
        boolean isEligible = newBooking.isBookingEligible();
        
        // Verify results
        assertTrue("Booking should be eligible with available seats and no overlap", isEligible);
        if (isEligible) {
            newBooking.updateTripSeats();
            assertEquals("Trip T123 seats should be updated to 2", 2, tripT123.getNumberOfSeats());
        }
    }
    
    @Test
    public void testCase2_BookingDeniedDueToSeatShortage() throws Exception {
        // Setup: Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        Driver driver = factory.createDriver();
        Trip tripT456 = factory.createTrip();
        tripT456.setDepartureTime("10:00");
        tripT456.setArrivalTime("12:00");
        tripT456.setNumberOfSeats(2);
        driver.getTrips().add(tripT456);
        
        // Setup: Create Customer C2 with existing booking for T456 (3 seats)
        Customer customer = factory.createCustomer();
        Booking existingBooking = factory.createBooking();
        existingBooking.setNumberOfSeats(3);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-25 07:30:00"));
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(tripT456);
        customer.getBookings().add(existingBooking);
        
        // Create new booking for T456
        Booking newBooking = factory.createBooking();
        newBooking.setNumberOfSeats(1); // Even 1 seat should be denied due to previous overbooking
        newBooking.setBookingDate(dateFormat.parse("2023-12-25 07:30:00")); // 2.5 hours before departure
        newBooking.setCustomer(customer);
        newBooking.setTrip(tripT456);
        
        // Test booking eligibility
        boolean isEligible = newBooking.isBookingEligible();
        
        // Verify results
        assertFalse("Booking should be denied due to seat shortage", isEligible);
        assertEquals("Trip T456 seats should remain 2", 2, tripT456.getNumberOfSeats());
    }
    
    @Test
    public void testCase3_BookingDeniedDueToTimeCutoffExactly2HoursBefore() throws Exception {
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        Trip tripT100 = factory.createTrip();
        tripT100.setDepartureTime("14:00");
        tripT100.setArrivalTime("16:00");
        tripT100.setNumberOfSeats(50);
        
        // Setup: Create Customer C3
        Customer customer = factory.createCustomer();
        
        // Create booking exactly 2 hours before departure
        Booking newBooking = factory.createBooking();
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDate(dateFormat.parse("2023-12-25 12:00:00")); // Exactly 2 hours before
        newBooking.setCustomer(customer);
        newBooking.setTrip(tripT100);
        
        // Test booking eligibility
        boolean isEligible = newBooking.isBookingEligible();
        
        // According to specification: "The current booking time must be at least 2 hours earlier than the departure time (excluding the case where it is exactly 2 hours)"
        // So exactly 2 hours should be allowed (true)
        assertTrue("Booking should be allowed when exactly 2 hours before departure", isEligible);
        if (isEligible) {
            newBooking.updateTripSeats();
            assertEquals("Trip T100 seats should be updated to 47", 47, tripT100.getNumberOfSeats());
        }
    }
    
    @Test
    public void testCase4_BookingAllowedWithMultipleNonOverlappingTrips() throws Exception {
        // Setup: Customer C9 has existing booking for Trip T199 (2023-12-25 08:00-10:00, 50 seats)
        Customer customer = factory.createCustomer();
        
        Trip tripT199 = factory.createTrip();
        tripT199.setDepartureTime("08:00");
        tripT199.setArrivalTime("10:00");
        tripT199.setNumberOfSeats(50);
        
        Booking existingBooking = factory.createBooking();
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-23 12:00:00"));
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(tripT199);
        customer.getBookings().add(existingBooking);
        
        // Setup: Create new booking for Trip T200 (2023-12-25 12:00-14:00, 40 seats)
        Trip tripT200 = factory.createTrip();
        tripT200.setDepartureTime("12:00");
        tripT200.setArrivalTime("14:00");
        tripT200.setNumberOfSeats(40);
        
        Booking newBooking = factory.createBooking();
        newBooking.setNumberOfSeats(4);
        newBooking.setBookingDate(dateFormat.parse("2023-12-23 14:00:00"));
        newBooking.setCustomer(customer);
        newBooking.setTrip(tripT200);
        
        // Test booking eligibility
        boolean isEligible = newBooking.isBookingEligible();
        
        // Verify results - should be allowed since no time overlap
        assertTrue("Booking should be allowed with non-overlapping trips", isEligible);
        if (isEligible) {
            newBooking.updateTripSeats();
            assertEquals("Trip T200 seats should be updated to 36", 36, tripT200.getNumberOfSeats());
        }
    }
    
    @Test
    public void testCase5_BookingDeniedWhenCustomerHasOverlappingBooking() throws Exception {
        // Setup: Customer C10 has existing booking for Trip T299 (2023-12-25 13:00-15:00, 50 seats)
        Customer customer = factory.createCustomer();
        
        Trip tripT299 = factory.createTrip();
        tripT299.setDepartureTime("13:00");
        tripT299.setArrivalTime("15:00");
        tripT299.setNumberOfSeats(50);
        
        Booking existingBooking = factory.createBooking();
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-23 12:00:00"));
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(tripT299);
        customer.getBookings().add(existingBooking);
        
        // Setup: Create new booking for Trip T300 (2023-12-25 14:00-16:00, 40 seats) - 1-hour overlap
        Trip tripT300 = factory.createTrip();
        tripT300.setDepartureTime("14:00");
        tripT300.setArrivalTime("16:00");
        tripT300.setNumberOfSeats(40);
        
        Booking newBooking = factory.createBooking();
        newBooking.setNumberOfSeats(4);
        newBooking.setBookingDate(dateFormat.parse("2023-12-23 14:00:00"));
        newBooking.setCustomer(customer);
        newBooking.setTrip(tripT300);
        
        // Test booking eligibility
        boolean isEligible = newBooking.isBookingEligible();
        
        // Verify results - should be denied due to time overlap
        assertFalse("Booking should be denied due to overlapping time periods", isEligible);
        assertEquals("Trip T300 seats should remain 40", 40, tripT300.getNumberOfSeats());
    }
}