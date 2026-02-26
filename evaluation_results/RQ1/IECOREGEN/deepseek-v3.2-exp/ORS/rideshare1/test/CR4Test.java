package edu.rideshare.rideshare1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.rideshare.RideshareFactory;
import edu.rideshare.Customer;
import edu.rideshare.Booking;
import edu.rideshare.Trip;
import edu.rideshare.MembershipPackage;
import edu.rideshare.Award;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR4Test {
    
    private RideshareFactory factory;
    
    @Before
    public void setUp() {
        factory = RideshareFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_PointsCalculationWithMultipleBookings() throws Exception {
        // Setup
        Customer customer = factory.createCustomer();
        MembershipPackage membership = factory.createMembershipPackage();
        membership.getAwards().add(Award.POINTS);
        customer.setMembershipPackage(membership);
        
        // Create trips
        Trip trip1 = factory.createTrip();
        trip1.setPrice(200.0);
        
        Trip trip2 = factory.createTrip();
        trip2.setPrice(100.0);
        
        // Create bookings for December 2023
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        Booking booking1 = factory.createBooking();
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(sdf.parse("2023-12-25 12:00:00"));
        booking1.setCustomer(customer);
        booking1.setTrip(trip1);
        customer.getBookings().add(booking1);
        
        Booking booking2 = factory.createBooking();
        booking2.setNumberOfSeats(3);
        booking2.setBookingDate(sdf.parse("2023-12-26 12:00:00"));
        booking2.setCustomer(customer);
        booking2.setTrip(trip2);
        customer.getBookings().add(booking2);
        
        // Test
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("Total points should be 25 for 5 seats (2+3) * 5 points per seat", 25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() throws Exception {
        // Setup
        Customer customer = factory.createCustomer();
        MembershipPackage membership = factory.createMembershipPackage();
        membership.getAwards().add(Award.POINTS);
        customer.setMembershipPackage(membership);
        
        // Create trip
        Trip trip = factory.createTrip();
        trip.setPrice(100.0);
        
        // Create booking for December 2024 (outside target month 2023-12)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        Booking booking = factory.createBooking();
        booking.setNumberOfSeats(4);
        booking.setBookingDate(sdf.parse("2024-12-26 12:00:00"));
        booking.setCustomer(customer);
        booking.setTrip(trip);
        customer.getBookings().add(booking);
        
        // Test
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("No points should be awarded for bookings outside target month", 0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() throws Exception {
        // Setup
        Customer customer = factory.createCustomer();
        MembershipPackage membership = factory.createMembershipPackage();
        membership.getAwards().add(Award.POINTS);
        customer.setMembershipPackage(membership);
        
        // Create trips
        Trip trip1 = factory.createTrip();
        trip1.setPrice(200.0);
        
        Trip trip2 = factory.createTrip();
        trip2.setPrice(200.0);
        
        // Create bookings - one in November, one in December
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Booking in November (should be excluded)
        Booking booking1 = factory.createBooking();
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(sdf.parse("2023-11-30 10:00:00"));
        booking1.setCustomer(customer);
        booking1.setTrip(trip1);
        customer.getBookings().add(booking1);
        
        // Booking in December (should be included)
        Booking booking2 = factory.createBooking();
        booking2.setNumberOfSeats(3);
        booking2.setBookingDate(sdf.parse("2023-12-01 10:00:00"));
        booking2.setCustomer(customer);
        booking2.setTrip(trip2);
        customer.getBookings().add(booking2);
        
        // Test
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("Only December booking should count: 3 seats * 5 points = 15", 15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() throws Exception {
        // Setup
        Customer customer = factory.createCustomer();
        MembershipPackage membership = factory.createMembershipPackage();
        membership.getAwards().add(Award.POINTS);
        customer.setMembershipPackage(membership);
        
        // Create trip
        Trip trip = factory.createTrip();
        trip.setPrice(200.0);
        
        // Create booking in December 2023
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        Booking booking = factory.createBooking();
        booking.setNumberOfSeats(2);
        booking.setBookingDate(sdf.parse("2023-12-10 10:00:00"));
        booking.setCustomer(customer);
        booking.setTrip(trip);
        customer.getBookings().add(booking);
        
        // Test
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("2 seats * 5 points = 10", 10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() throws Exception {
        // Setup
        Customer customerC8 = factory.createCustomer();
        Customer customerC9 = factory.createCustomer();
        
        MembershipPackage membershipC8 = factory.createMembershipPackage();
        membershipC8.getAwards().add(Award.POINTS);
        customerC8.setMembershipPackage(membershipC8);
        
        MembershipPackage membershipC9 = factory.createMembershipPackage();
        membershipC9.getAwards().add(Award.POINTS);
        customerC9.setMembershipPackage(membershipC9);
        
        // Create trips
        Trip trip1 = factory.createTrip();
        trip1.setPrice(150.0);
        
        Trip trip2 = factory.createTrip();
        trip2.setPrice(200.0);
        
        Trip trip3 = factory.createTrip();
        trip3.setPrice(200.0);
        
        // Create bookings for January 2024
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Customer C8 bookings
        Booking bookingC8_1 = factory.createBooking();
        bookingC8_1.setNumberOfSeats(50);
        bookingC8_1.setBookingDate(sdf.parse("2024-01-10 10:00:00"));
        bookingC8_1.setCustomer(customerC8);
        bookingC8_1.setTrip(trip1);
        customerC8.getBookings().add(bookingC8_1);
        
        Booking bookingC8_2 = factory.createBooking();
        bookingC8_2.setNumberOfSeats(50);
        bookingC8_2.setBookingDate(sdf.parse("2024-01-15 10:00:00"));
        bookingC8_2.setCustomer(customerC8);
        bookingC8_2.setTrip(trip2);
        customerC8.getBookings().add(bookingC8_2);
        
        // Customer C9 booking
        Booking bookingC9 = factory.createBooking();
        bookingC9.setNumberOfSeats(50);
        bookingC9.setBookingDate(sdf.parse("2024-01-10 10:00:00"));
        bookingC9.setCustomer(customerC9);
        bookingC9.setTrip(trip3);
        customerC9.getBookings().add(bookingC9);
        
        // Test
        int resultC8 = customerC8.computeMonthlyRewardPoints("2024-01");
        int resultC9 = customerC9.computeMonthlyRewardPoints("2024-01");
        
        // Verify
        assertEquals("Customer C8 should have 500 points (100 seats * 5 points)", 500, resultC8);
        assertEquals("Customer C9 should have 250 points (50 seats * 5 points)", 250, resultC9);
    }
}