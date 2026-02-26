package edu.rideshare.rideshare2.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.rideshare.*;
import org.eclipse.emf.common.util.EList;
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
        tripT123.setDepartureStation("Station A");
        tripT123.setArrivalStation("Station B");
        tripT123.setDepartureTime("14:00");
        tripT123.setArrivalTime("16:00");
        tripT123.setNumberOfSeats(5);
        tripT123.setDepartureDate(dateFormat.parse("2023-12-25 14:00:00"));
        tripT123.setPrice(50.0);
        driver.getTrips().add(tripT123);
        
        // Setup: Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        Customer customer = factory.createCustomer();
        Trip tripT456 = factory.createTrip();
        tripT456.setDepartureStation("Station C");
        tripT456.setArrivalStation("Station D");
        tripT456.setDepartureTime("14:00");
        tripT456.setArrivalTime("16:00");
        tripT456.setNumberOfSeats(10);
        tripT456.setDepartureDate(dateFormat.parse("2023-12-25 14:00:00"));
        tripT456.setPrice(60.0);
        
        Booking existingBooking = factory.createBooking();
        existingBooking.setTrip(tripT456);
        existingBooking.setNumberOfSeats(3);
        existingBooking.setCustomer(customer);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-25 11:00:00"));
        customer.getBookings().add(existingBooking);
        
        // Create new booking for T123
        Booking newBooking = factory.createBooking();
        newBooking.setTrip(tripT123);
        newBooking.setNumberOfSeats(3);
        newBooking.setCustomer(customer);
        newBooking.setBookingDate(dateFormat.parse("2023-12-25 11:00:00")); // 3 hours before departure
        
        // Test booking eligibility
        boolean isEligible = newBooking.isBookingEligible();
        
        // Verify results
        assertTrue("Booking should be eligible", isEligible);
        assertEquals("Trip seats should be updated", 2, tripT123.getNumberOfSeats());
    }
    
    @Test
    public void testCase2_BookingDeniedDueToSeatShortage() throws Exception {
        // Setup: Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        Driver driver = factory.createDriver();
        Trip tripT456 = factory.createTrip();
        tripT456.setDepartureStation("Station E");
        tripT456.setArrivalStation("Station F");
        tripT456.setDepartureTime("10:00");
        tripT456.setArrivalTime("12:00");
        tripT456.setNumberOfSeats(2);
        tripT456.setDepartureDate(dateFormat.parse("2023-12-25 10:00:00"));
        tripT456.setPrice(40.0);
        driver.getTrips().add(tripT456);
        
        // Setup: Create Customer C2 with existing booking for T456 (3 seats)
        Customer customer = factory.createCustomer();
        Booking existingBooking = factory.createBooking();
        existingBooking.setTrip(tripT456);
        existingBooking.setNumberOfSeats(3);
        existingBooking.setCustomer(customer);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-25 07:30:00"));
        customer.getBookings().add(existingBooking);
        
        // Create new booking for T456
        Booking newBooking = factory.createBooking();
        newBooking.setTrip(tripT456);
        newBooking.setNumberOfSeats(3);
        newBooking.setCustomer(customer);
        newBooking.setBookingDate(dateFormat.parse("2023-12-25 07:30:00")); // 2.5 hours before departure
        
        // Test booking eligibility
        boolean isEligible = newBooking.isBookingEligible();
        
        // Verify results
        assertFalse("Booking should be denied due to seat shortage", isEligible);
        assertEquals("Trip seats should remain unchanged", 2, tripT456.getNumberOfSeats());
    }
    
    @Test
    public void testCase3_BookingDeniedDueToTimeCutoffExactly2HoursBefore() throws Exception {
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        Trip tripT100 = factory.createTrip();
        tripT100.setDepartureStation("Station G");
        tripT100.setArrivalStation("Station H");
        tripT100.setDepartureTime("14:00");
        tripT100.setArrivalTime("16:00");
        tripT100.setNumberOfSeats(50);
        tripT100.setDepartureDate(dateFormat.parse("2023-12-25 14:00:00"));
        tripT100.setPrice(30.0);
        
        // Create Customer C3 with booking for T100 (3 seats)
        Customer customer = factory.createCustomer();
        Booking newBooking = factory.createBooking();
        newBooking.setTrip(tripT100);
        newBooking.setNumberOfSeats(3);
        newBooking.setCustomer(customer);
        newBooking.setBookingDate(dateFormat.parse("2023-12-25 12:00:00")); // Exactly 2 hours before departure
        
        // Test booking eligibility
        boolean isEligible = newBooking.isBookingEligible();
        
        // Verify results - According to specification, should be true when exactly 2 hours
        assertTrue("Booking should be allowed when exactly 2 hours before", isEligible);
        assertEquals("Trip seats should be updated", 47, tripT100.getNumberOfSeats());
    }
    
    @Test
    public void testCase4_BookingAllowedWithMultipleNonOverlappingTrips() throws Exception {
        // Setup: Customer C9 has booked an existing booking for Trip T199 (2023-12-25 08:00-10:00, 50 seats)
        Customer customer = factory.createCustomer();
        
        Trip tripT199 = factory.createTrip();
        tripT199.setDepartureStation("Station I");
        tripT199.setArrivalStation("Station J");
        tripT199.setDepartureTime("08:00");
        tripT199.setArrivalTime("10:00");
        tripT199.setNumberOfSeats(50);
        tripT199.setDepartureDate(dateFormat.parse("2023-12-25 08:00:00"));
        tripT199.setPrice(35.0);
        
        Booking existingBooking = factory.createBooking();
        existingBooking.setTrip(tripT199);
        existingBooking.setNumberOfSeats(2);
        existingBooking.setCustomer(customer);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-23 12:00:00"));
        customer.getBookings().add(existingBooking);
        
        // Setup: Trip T200 (2023-12-25 12:00-14:00, 40 seats) - no overlap with T199
        Trip tripT200 = factory.createTrip();
        tripT200.setDepartureStation("Station K");
        tripT200.setArrivalStation("Station L");
        tripT200.setDepartureTime("12:00");
        tripT200.setArrivalTime("14:00");
        tripT200.setNumberOfSeats(40);
        tripT200.setDepartureDate(dateFormat.parse("2023-12-25 12:00:00"));
        tripT200.setPrice(45.0);
        
        // Create new booking for T200
        Booking newBooking = factory.createBooking();
        newBooking.setTrip(tripT200);
        newBooking.setNumberOfSeats(4);
        newBooking.setCustomer(customer);
        newBooking.setBookingDate(dateFormat.parse("2023-12-23 14:00:00"));
        
        // Test booking eligibility
        boolean isEligible = newBooking.isBookingEligible();
        
        // Verify results
        assertTrue("Booking should be allowed for non-overlapping trips", isEligible);
        assertEquals("Trip T200 seats should be updated", 36, tripT200.getNumberOfSeats());
    }
    
    @Test
    public void testCase5_BookingDeniedWhenCustomerHasOverlappingBooking() throws Exception {
        // Setup: Customer C10 has existing booking for Trip T299 (2023-12-25 13:00-15:00, 50 seats)
        Customer customer = factory.createCustomer();
        
        Trip tripT299 = factory.createTrip();
        tripT299.setDepartureStation("Station M");
        tripT299.setArrivalStation("Station N");
        tripT299.setDepartureTime("13:00");
        tripT299.setArrivalTime("15:00");
        tripT299.setNumberOfSeats(50);
        tripT299.setDepartureDate(dateFormat.parse("2023-12-25 13:00:00"));
        tripT299.setPrice(55.0);
        
        Booking existingBooking = factory.createBooking();
        existingBooking.setTrip(tripT299);
        existingBooking.setNumberOfSeats(2);
        existingBooking.setCustomer(customer);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-23 12:00:00"));
        customer.getBookings().add(existingBooking);
        
        // Setup: Trip T300 (2023-12-25 14:00-16:00, 40 seats) - 1-hour overlap with T299
        Trip tripT300 = factory.createTrip();
        tripT300.setDepartureStation("Station O");
        tripT300.setArrivalStation("Station P");
        tripT300.setDepartureTime("14:00");
        tripT300.setArrivalTime("16:00");
        tripT300.setNumberOfSeats(40);
        tripT300.setDepartureDate(dateFormat.parse("2023-12-25 14:00:00"));
        tripT300.setPrice(60.0);
        
        // Create new booking for T300
        Booking newBooking = factory.createBooking();
        newBooking.setTrip(tripT300);
        newBooking.setNumberOfSeats(4);
        newBooking.setCustomer(customer);
        newBooking.setBookingDate(dateFormat.parse("2023-12-23 14:00:00"));
        
        // Test booking eligibility
        boolean isEligible = newBooking.isBookingEligible();
        
        // Verify results
        assertFalse("Booking should be denied due to overlapping trips", isEligible);
        assertEquals("Trip T300 seats should remain unchanged", 40, tripT300.getNumberOfSeats());
    }
}