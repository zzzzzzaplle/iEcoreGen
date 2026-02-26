package edu.rideshare.rideshare4.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.rideshare.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import org.eclipse.emf.common.util.EList;

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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        trip1.setDepartureDate(dateFormat.parse("2023-12-25 12:00"));
        
        Trip trip2 = factory.createTrip();
        trip2.setPrice(100.0);
        trip2.setDepartureDate(dateFormat.parse("2023-12-26 12:00"));
        
        // Create bookings
        Booking booking1 = factory.createBooking();
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(dateFormat.parse("2023-12-01 10:00"));
        booking1.setTrip(trip1);
        booking1.setCustomer(customer);
        customer.getBookings().add(booking1);
        
        Booking booking2 = factory.createBooking();
        booking2.setNumberOfSeats(3);
        booking2.setBookingDate(dateFormat.parse("2023-12-01 10:00"));
        booking2.setTrip(trip2);
        booking2.setCustomer(customer);
        customer.getBookings().add(booking2);
        
        // Test
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("Total points should be 25 for 5 seats across two bookings", 25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() throws Exception {
        // Setup
        Customer customer = factory.createCustomer();
        MembershipPackage membership = factory.createMembershipPackage();
        membership.getAwards().add(Award.POINTS);
        customer.setMembershipPackage(membership);
        
        // Create trip with future departure date
        Trip trip = factory.createTrip();
        trip.setPrice(100.0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        trip.setDepartureDate(dateFormat.parse("2024-12-26 12:00"));
        
        // Create booking
        Booking booking = factory.createBooking();
        booking.setNumberOfSeats(4);
        booking.setBookingDate(dateFormat.parse("2023-12-01 10:00"));
        booking.setTrip(trip);
        booking.setCustomer(customer);
        customer.getBookings().add(booking);
        
        // Test
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("Should return 0 points for booking with departure date outside current month", 0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() throws Exception {
        // Setup
        Customer customer = factory.createCustomer();
        MembershipPackage membership = factory.createMembershipPackage();
        membership.getAwards().add(Award.POINTS);
        customer.setMembershipPackage(membership);
        
        // Create trip
        Trip trip = factory.createTrip();
        trip.setPrice(200.0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        trip.setDepartureDate(dateFormat.parse("2023-12-25 12:00"));
        
        // Create booking from previous month (should not count)
        Booking booking1 = factory.createBooking();
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(dateFormat.parse("2023-11-30 10:00"));
        booking1.setTrip(trip);
        booking1.setCustomer(customer);
        customer.getBookings().add(booking1);
        
        // Create booking from current month (should count)
        Booking booking2 = factory.createBooking();
        booking2.setNumberOfSeats(3);
        booking2.setBookingDate(dateFormat.parse("2023-12-01 10:00"));
        booking2.setTrip(trip);
        booking2.setCustomer(customer);
        customer.getBookings().add(booking2);
        
        // Test
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("Should only count bookings from current month", 15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() throws Exception {
        // Setup
        Customer customer = factory.createCustomer();
        MembershipPackage membership = factory.createMembershipPackage();
        membership.getAwards().add(Award.POINTS);
        customer.setMembershipPackage(membership);
        
        // Create trip with future departure date
        Trip trip = factory.createTrip();
        trip.setPrice(200.0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        trip.setDepartureDate(dateFormat.parse("2024-03-25 12:00"));
        
        // Create booking
        Booking booking = factory.createBooking();
        booking.setNumberOfSeats(2);
        booking.setBookingDate(dateFormat.parse("2023-12-10 10:00"));
        booking.setTrip(trip);
        booking.setCustomer(customer);
        customer.getBookings().add(booking);
        
        // Test
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("Should calculate points based on seats regardless of trip departure date", 10, result);
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        trip1.setDepartureDate(dateFormat.parse("2024-05-25 12:00"));
        
        Trip trip2 = factory.createTrip();
        trip2.setPrice(200.0);
        trip2.setDepartureDate(dateFormat.parse("2024-06-25 12:00"));
        
        Trip trip3 = factory.createTrip();
        trip3.setPrice(200.0);
        trip3.setDepartureDate(dateFormat.parse("2024-07-25 12:00"));
        
        // Create bookings for C8
        Booking booking1C8 = factory.createBooking();
        booking1C8.setNumberOfSeats(50);
        booking1C8.setBookingDate(dateFormat.parse("2024-01-10 10:00"));
        booking1C8.setTrip(trip1);
        booking1C8.setCustomer(customerC8);
        customerC8.getBookings().add(booking1C8);
        
        Booking booking2C8 = factory.createBooking();
        booking2C8.setNumberOfSeats(50);
        booking2C8.setBookingDate(dateFormat.parse("2024-01-15 10:00"));
        booking2C8.setTrip(trip2);
        booking2C8.setCustomer(customerC8);
        customerC8.getBookings().add(booking2C8);
        
        // Create booking for C9
        Booking bookingC9 = factory.createBooking();
        bookingC9.setNumberOfSeats(50);
        bookingC9.setBookingDate(dateFormat.parse("2024-01-10 10:00"));
        bookingC9.setTrip(trip3);
        bookingC9.setCustomer(customerC9);
        customerC9.getBookings().add(bookingC9);
        
        // Test
        int resultC8 = customerC8.computeMonthlyRewardPoints("2024-01");
        int resultC9 = customerC9.computeMonthlyRewardPoints("2024-01");
        
        // Verify
        assertEquals("C8 should have 500 points for 100 seats", 500, resultC8);
        assertEquals("C9 should have 250 points for 50 seats", 250, resultC9);
    }
}