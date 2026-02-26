package edu.rideshare.rideshare3.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.rideshare.*;
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
        
        Trip trip1 = factory.createTrip();
        trip1.setPrice(200.0);
        
        Trip trip2 = factory.createTrip();
        trip2.setPrice(100.0);
        
        Booking booking1 = factory.createBooking();
        booking1.setNumberOfSeats(2);
        booking1.setCustomer(customer);
        booking1.setTrip(trip1);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date bookingDate1 = sdf.parse("2023-12-25 12:00");
        booking1.setBookingDate(bookingDate1);
        
        Booking booking2 = factory.createBooking();
        booking2.setNumberOfSeats(3);
        booking2.setCustomer(customer);
        booking2.setTrip(trip2);
        
        Date bookingDate2 = sdf.parse("2023-12-26 12:00");
        booking2.setBookingDate(bookingDate2);
        
        customer.getBookings().add(booking1);
        customer.getBookings().add(booking2);
        
        // Execute
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("Total points should be 25 for 5 seats across 2 bookings", 25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() throws Exception {
        // Setup
        Customer customer = factory.createCustomer();
        MembershipPackage membership = factory.createMembershipPackage();
        membership.getAwards().add(Award.POINTS);
        customer.setMembershipPackage(membership);
        
        Trip trip = factory.createTrip();
        trip.setPrice(100.0);
        
        Booking booking = factory.createBooking();
        booking.setNumberOfSeats(4);
        booking.setCustomer(customer);
        booking.setTrip(trip);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date bookingDate = sdf.parse("2024-12-26 12:00");
        booking.setBookingDate(bookingDate);
        
        customer.getBookings().add(booking);
        
        // Execute
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("No points should be awarded for bookings outside current month", 0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() throws Exception {
        // Setup
        Customer customer = factory.createCustomer();
        MembershipPackage membership = factory.createMembershipPackage();
        membership.getAwards().add(Award.POINTS);
        customer.setMembershipPackage(membership);
        
        Trip trip1 = factory.createTrip();
        trip1.setPrice(200.0);
        
        Trip trip2 = factory.createTrip();
        trip2.setPrice(200.0);
        
        Booking booking1 = factory.createBooking();
        booking1.setNumberOfSeats(2);
        booking1.setCustomer(customer);
        booking1.setTrip(trip1);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date bookingDate1 = sdf.parse("2023-11-30 10:00");
        booking1.setBookingDate(bookingDate1);
        
        Booking booking2 = factory.createBooking();
        booking2.setNumberOfSeats(3);
        booking2.setCustomer(customer);
        booking2.setTrip(trip2);
        
        Date bookingDate2 = sdf.parse("2023-12-01 10:00");
        booking2.setBookingDate(bookingDate2);
        
        customer.getBookings().add(booking1);
        customer.getBookings().add(booking2);
        
        // Execute
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("Only December bookings should be counted (3 seats = 15 points)", 15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() throws Exception {
        // Setup
        Customer customer = factory.createCustomer();
        MembershipPackage membership = factory.createMembershipPackage();
        membership.getAwards().add(Award.POINTS);
        customer.setMembershipPackage(membership);
        
        Trip trip = factory.createTrip();
        trip.setPrice(200.0);
        
        Booking booking = factory.createBooking();
        booking.setNumberOfSeats(2);
        booking.setCustomer(customer);
        booking.setTrip(trip);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date bookingDate = sdf.parse("2023-12-10 10:00");
        booking.setBookingDate(bookingDate);
        
        customer.getBookings().add(booking);
        
        // Execute
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("2 seats should yield 10 points", 10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() throws Exception {
        // Setup Customer C8
        Customer customerC8 = factory.createCustomer();
        MembershipPackage membershipC8 = factory.createMembershipPackage();
        membershipC8.getAwards().add(Award.POINTS);
        customerC8.setMembershipPackage(membershipC8);
        
        Trip trip1 = factory.createTrip();
        trip1.setPrice(150.0);
        
        Trip trip2 = factory.createTrip();
        trip2.setPrice(200.0);
        
        Booking booking1 = factory.createBooking();
        booking1.setNumberOfSeats(50);
        booking1.setCustomer(customerC8);
        booking1.setTrip(trip1);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date bookingDate1 = sdf.parse("2024-01-10 10:00");
        booking1.setBookingDate(bookingDate1);
        
        Booking booking2 = factory.createBooking();
        booking2.setNumberOfSeats(50);
        booking2.setCustomer(customerC8);
        booking2.setTrip(trip2);
        
        Date bookingDate2 = sdf.parse("2024-01-15 10:00");
        booking2.setBookingDate(bookingDate2);
        
        customerC8.getBookings().add(booking1);
        customerC8.getBookings().add(booking2);
        
        // Setup Customer C9
        Customer customerC9 = factory.createCustomer();
        MembershipPackage membershipC9 = factory.createMembershipPackage();
        membershipC9.getAwards().add(Award.POINTS);
        customerC9.setMembershipPackage(membershipC9);
        
        Trip trip3 = factory.createTrip();
        trip3.setPrice(200.0);
        
        Booking booking3 = factory.createBooking();
        booking3.setNumberOfSeats(50);
        booking3.setCustomer(customerC9);
        booking3.setTrip(trip3);
        
        Date bookingDate3 = sdf.parse("2024-01-10 10:00");
        booking3.setBookingDate(bookingDate3);
        
        customerC9.getBookings().add(booking3);
        
        // Execute
        int resultC8 = customerC8.computeMonthlyRewardPoints("2024-01");
        int resultC9 = customerC9.computeMonthlyRewardPoints("2024-01");
        
        // Verify
        assertEquals("Customer C8 should have 500 points for 100 seats", 500, resultC8);
        assertEquals("Customer C9 should have 250 points for 50 seats", 250, resultC9);
    }
}