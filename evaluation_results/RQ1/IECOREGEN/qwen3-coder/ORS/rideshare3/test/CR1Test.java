package edu.rideshare.rideshare3.test;

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
        tripT123.setDepartureStation("Station A");
        tripT123.setArrivalStation("Station B");
        tripT123.setDepartureTime("14:00");
        tripT123.setArrivalTime("16:00");
        tripT123.setDepartureDate(dateFormat.parse("2023-12-25 14:00:00"));
        tripT123.setNumberOfSeats(5);
        tripT123.setPrice(50.0);
        driver.getTrips().add(tripT123);
        
        // Setup: Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        Customer customer = factory.createCustomer();
        MembershipPackage membership = factory.createMembershipPackage();
        customer.setMembershipPackage(membership);
        
        Trip tripT456 = factory.createTrip();
        tripT456.setDepartureStation("Station C");
        tripT456.setArrivalStation("Station D");
        tripT456.setDepartureTime("14:00");
        tripT456.setArrivalTime("16:00");
        tripT456.setDepartureDate(dateFormat.parse("2023-12-25 14:00:00"));
        tripT456.setNumberOfSeats(10);
        tripT456.setPrice(40.0);
        
        Booking existingBooking = factory.createBooking();
        existingBooking.setNumberOfSeats(3);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-25 11:00:00"));
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(tripT456);
        customer.getBookings().add(existingBooking);
        tripT456.getBookings().add(existingBooking);
        
        // Test: Create new booking for Trip T123
        Booking newBooking = factory.createBooking();
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDate(dateFormat.parse("2023-12-25 11:00:00")); // 3 hours before departure
        newBooking.setCustomer(customer);
        newBooking.setTrip(tripT123);
        
        // Verify eligibility and update seats
        boolean isEligible = newBooking.isBookingEligible();
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
        tripT456.setDepartureStation("Station E");
        tripT456.setArrivalStation("Station F");
        tripT456.setDepartureTime("10:00");
        tripT456.setArrivalTime("12:00");
        tripT456.setDepartureDate(dateFormat.parse("2023-12-25 10:00:00"));
        tripT456.setNumberOfSeats(2);
        tripT456.setPrice(30.0);
        driver.getTrips().add(tripT456);
        
        // Setup: Create Customer C2 with existing booking for T456 (3 seats)
        Customer customer = factory.createCustomer();
        MembershipPackage membership = factory.createMembershipPackage();
        customer.setMembershipPackage(membership);
        
        // Current time: 2023-12-25 07:30 (2.5 hours before departure)
        Booking newBooking = factory.createBooking();
        newBooking.setNumberOfSeats(3); // Trying to book 3 seats when only 2 available
        newBooking.setBookingDate(dateFormat.parse("2023-12-25 07:30:00"));
        newBooking.setCustomer(customer);
        newBooking.setTrip(tripT456);
        
        // Verify eligibility
        boolean isEligible = newBooking.isBookingEligible();
        assertFalse("Booking should be denied due to seat shortage", isEligible);
        assertEquals("Trip T456 seats should remain 2", 2, tripT456.getNumberOfSeats());
    }
    
    @Test
    public void testCase3_BookingDeniedDueToTimeCutoffExactly2HoursBefore() throws Exception {
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        Driver driver = factory.createDriver();
        Trip tripT100 = factory.createTrip();
        tripT100.setDepartureStation("Station G");
        tripT100.setArrivalStation("Station H");
        tripT100.setDepartureTime("14:00");
        tripT100.setArrivalTime("16:00");
        tripT100.setDepartureDate(dateFormat.parse("2023-12-25 14:00:00"));
        tripT100.setNumberOfSeats(50);
        tripT100.setPrice(25.0);
        driver.getTrips().add(tripT100);
        
        // Current time: 2023-12-25 12:00 (exactly 2 hours before)
        Customer customer = factory.createCustomer();
        MembershipPackage membership = factory.createMembershipPackage();
        customer.setMembershipPackage(membership);
        
        Booking newBooking = factory.createBooking();
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDate(dateFormat.parse("2023-12-25 12:00:00")); // Exactly 2 hours before
        newBooking.setCustomer(customer);
        newBooking.setTrip(tripT100);
        
        // Verify eligibility - should be allowed (spec says exactly 2 hours should return true)
        boolean isEligible = newBooking.isBookingEligible();
        assertFalse("Booking should be not allowed when exactly 2 hours before departure", isEligible);
        
        if (isEligible) {
            newBooking.updateTripSeats();
            assertEquals("Trip T100 seats should remain 50", 50, tripT100.getNumberOfSeats());
        }
    }
    
    @Test
    public void testCase4_BookingAllowedWithMultipleNonOverlappingTrips() throws Exception {
        // Setup: Customer C9 has booked an existing booking for Trip T199 (2023-12-25 08:00-10:00, 50 seats)
        Customer customer = factory.createCustomer();
        MembershipPackage membership = factory.createMembershipPackage();
        customer.setMembershipPackage(membership);
        
        Driver driver1 = factory.createDriver();
        Trip tripT199 = factory.createTrip();
        tripT199.setDepartureStation("Station I");
        tripT199.setArrivalStation("Station J");
        tripT199.setDepartureTime("08:00");
        tripT199.setArrivalTime("10:00");
        tripT199.setDepartureDate(dateFormat.parse("2023-12-25 08:00:00"));
        tripT199.setNumberOfSeats(50);
        tripT199.setPrice(35.0);
        driver1.getTrips().add(tripT199);
        
        Booking existingBooking = factory.createBooking();
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-23 12:00:00"));
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(tripT199);
        customer.getBookings().add(existingBooking);
        tripT199.getBookings().add(existingBooking);
        
        // Setup: Customer C9 create a new booking for Trip T200 (2023-12-25 12:00-14:00, 40 seats)
        Driver driver2 = factory.createDriver();
        Trip tripT200 = factory.createTrip();
        tripT200.setDepartureStation("Station K");
        tripT200.setArrivalStation("Station L");
        tripT200.setDepartureTime("12:00");
        tripT200.setArrivalTime("14:00");
        tripT200.setDepartureDate(dateFormat.parse("2023-12-25 12:00:00"));
        tripT200.setNumberOfSeats(40);
        tripT200.setPrice(45.0);
        driver2.getTrips().add(tripT200);
        
        Booking newBooking = factory.createBooking();
        newBooking.setNumberOfSeats(4);
        newBooking.setBookingDate(dateFormat.parse("2023-12-23 14:00:00"));
        newBooking.setCustomer(customer);
        newBooking.setTrip(tripT200);
        
        // Verify eligibility
        boolean isEligible = newBooking.isBookingEligible();
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
        MembershipPackage membership = factory.createMembershipPackage();
        customer.setMembershipPackage(membership);
        
        Driver driver1 = factory.createDriver();
        Trip tripT299 = factory.createTrip();
        tripT299.setDepartureStation("Station M");
        tripT299.setArrivalStation("Station N");
        tripT299.setDepartureTime("13:00");
        tripT299.setArrivalTime("15:00");
        tripT299.setDepartureDate(dateFormat.parse("2023-12-25 13:00:00"));
        tripT299.setNumberOfSeats(50);
        tripT299.setPrice(40.0);
        driver1.getTrips().add(tripT299);
        
        Booking existingBooking = factory.createBooking();
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-23 12:00:00"));
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(tripT299);
        customer.getBookings().add(existingBooking);
        tripT299.getBookings().add(existingBooking);
        
        // Setup: Customer C10 create a new booking for Trip T300 (2023-12-25 14:00-16:00, 40 seats)
        Driver driver2 = factory.createDriver();
        Trip tripT300 = factory.createTrip();
        tripT300.setDepartureStation("Station O");
        tripT300.setArrivalStation("Station P");
        tripT300.setDepartureTime("14:00");
        tripT300.setArrivalTime("16:00");
        tripT300.setDepartureDate(dateFormat.parse("2023-12-25 14:00:00"));
        tripT300.setNumberOfSeats(40);
        tripT300.setPrice(50.0);
        driver2.getTrips().add(tripT300);
        
        Booking newBooking = factory.createBooking();
        newBooking.setNumberOfSeats(4);
        newBooking.setBookingDate(dateFormat.parse("2023-12-23 14:00:00"));
        newBooking.setCustomer(customer);
        newBooking.setTrip(tripT300);
        
        // Verify eligibility - should be denied due to 1-hour overlap
        boolean isEligible = newBooking.isBookingEligible();
        assertFalse("Booking should be denied due to overlapping time periods", isEligible);
        assertEquals("Trip T300 seats should remain 40", 40, tripT300.getNumberOfSeats());
    }
}