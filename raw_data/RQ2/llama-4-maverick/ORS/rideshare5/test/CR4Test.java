package edu.rideshare.rideshare5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.rideshare.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR4Test {
    
    private Customer customer;
    private Trip trip1, trip2, trip3;
    private Booking booking1, booking2, booking3;
    private MembershipPackage membershipPackage;
    
    @Before
    public void setUp() {
        // Create factory instances using Ecore factory pattern
        RideshareFactory factory = RideshareFactory.eINSTANCE;
        
        // Create customer with membership package containing POINTS award
        customer = factory.createCustomer();
        customer.setID("C5");
        customer.setEmail("customer@example.com");
        customer.setPhoneNumber("1234567890");
        
        membershipPackage = factory.createMembershipPackage();
        membershipPackage.getAwards().add(Award.POINTS);
        customer.setMembershipPackage(membershipPackage);
        
        // Create trips
        trip1 = factory.createTrip();
        trip1.setPrice(200.0);
        
        trip2 = factory.createTrip();
        trip2.setPrice(100.0);
        
        trip3 = factory.createTrip();
        trip3.setPrice(150.0);
    }
    
    @Test
    public void testCase1_PointsCalculationWithMultipleBookings() throws Exception {
        // Setup: Customer C5 has membership with POINTS award
        Customer c5 = RideshareFactory.eINSTANCE.createCustomer();
        c5.setID("C5");
        MembershipPackage mp = RideshareFactory.eINSTANCE.createMembershipPackage();
        mp.getAwards().add(Award.POINTS);
        c5.setMembershipPackage(mp);
        
        // Create Booking1 (seats:2) for trip with December 2023 departure
        Trip trip1 = RideshareFactory.eINSTANCE.createTrip();
        trip1.setPrice(200.0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        trip1.setDepartureDate(dateFormat.parse("2023-12-25 12:00"));
        
        Booking booking1 = RideshareFactory.eINSTANCE.createBooking();
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(dateFormat.parse("2023-12-20 10:00"));
        booking1.setTrip(trip1);
        booking1.setCustomer(c5);
        c5.getBookings().add(booking1);
        
        // Create Booking2 (seats:3) for trip with December 2023 departure
        Trip trip2 = RideshareFactory.eINSTANCE.createTrip();
        trip2.setPrice(100.0);
        trip2.setDepartureDate(dateFormat.parse("2023-12-26 12:00"));
        
        Booking booking2 = RideshareFactory.eINSTANCE.createBooking();
        booking2.setNumberOfSeats(3);
        booking2.setBookingDate(dateFormat.parse("2023-12-21 10:00"));
        booking2.setTrip(trip2);
        booking2.setCustomer(c5);
        c5.getBookings().add(booking2);
        
        // Test: Compute reward points for current month 2023-12
        int result = c5.computeMonthlyRewardPoints("2023-12");
        
        // Expected: (2+3)*5=25
        assertEquals("Points calculation with multiple bookings should return 25", 25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() throws Exception {
        // Setup: Customer C6 has membership with POINTS award
        Customer c6 = RideshareFactory.eINSTANCE.createCustomer();
        c6.setID("C6");
        MembershipPackage mp = RideshareFactory.eINSTANCE.createMembershipPackage();
        mp.getAwards().add(Award.POINTS);
        c6.setMembershipPackage(mp);
        
        // Create Booking3 (seats:4) for trip with December 2024 departure (future year)
        Trip trip = RideshareFactory.eINSTANCE.createTrip();
        trip.setPrice(100.0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        trip.setDepartureDate(dateFormat.parse("2024-12-26 12:00"));
        
        Booking booking = RideshareFactory.eINSTANCE.createBooking();
        booking.setNumberOfSeats(4);
        booking.setBookingDate(dateFormat.parse("2024-12-20 10:00"));
        booking.setTrip(trip);
        booking.setCustomer(c6);
        c6.getBookings().add(booking);
        
        // Test: Compute reward points for current month 2023-12
        int result = c6.computeMonthlyRewardPoints("2023-12");
        
        // Expected: 0 (booking is in different year)
        assertEquals("Zero points should be returned for expired bookings", 0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() throws Exception {
        // Setup: Customer C7 has membership with POINTS award
        Customer c7 = RideshareFactory.eINSTANCE.createCustomer();
        c7.setID("C7");
        MembershipPackage mp = RideshareFactory.eINSTANCE.createMembershipPackage();
        mp.getAwards().add(Award.POINTS);
        c7.setMembershipPackage(mp);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        // Create Booking1: 2023-11-30 (outside target month)
        Trip trip1 = RideshareFactory.eINSTANCE.createTrip();
        trip1.setPrice(200.0);
        trip1.setDepartureDate(dateFormat.parse("2023-12-25 12:00"));
        
        Booking booking1 = RideshareFactory.eINSTANCE.createBooking();
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(dateFormat.parse("2023-11-30 10:00"));
        booking1.setTrip(trip1);
        booking1.setCustomer(c7);
        c7.getBookings().add(booking1);
        
        // Create Booking2: 2023-12-01 (within target month)
        Trip trip2 = RideshareFactory.eINSTANCE.createTrip();
        trip2.setPrice(200.0);
        trip2.setDepartureDate(dateFormat.parse("2023-12-25 12:00"));
        
        Booking booking2 = RideshareFactory.eINSTANCE.createBooking();
        booking2.setNumberOfSeats(3);
        booking2.setBookingDate(dateFormat.parse("2023-12-01 10:00"));
        booking2.setTrip(trip2);
        booking2.setCustomer(c7);
        c7.getBookings().add(booking2);
        
        // Test: Compute reward points for current month 2023-12
        int result = c7.computeMonthlyRewardPoints("2023-12");
        
        // Expected: 3*5=15 (only booking2 counts)
        assertEquals("Partial month inclusion should return points only for December bookings", 15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() throws Exception {
        // Setup: Customer C8 has membership with POINTS award
        Customer c8 = RideshareFactory.eINSTANCE.createCustomer();
        c8.setID("C8");
        MembershipPackage mp = RideshareFactory.eINSTANCE.createMembershipPackage();
        mp.getAwards().add(Award.POINTS);
        c8.setMembershipPackage(mp);
        
        // Create Booking: 2023-12-10 (seats:2) for trip with future departure
        Trip trip = RideshareFactory.eINSTANCE.createTrip();
        trip.setPrice(200.0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        trip.setDepartureDate(dateFormat.parse("2024-03-25 12:00"));
        
        Booking booking = RideshareFactory.eINSTANCE.createBooking();
        booking.setNumberOfSeats(2);
        booking.setBookingDate(dateFormat.parse("2023-12-10 10:00"));
        booking.setTrip(trip);
        booking.setCustomer(c8);
        c8.getBookings().add(booking);
        
        // Test: Compute reward points for current month 2023-12
        int result = c8.computeMonthlyRewardPoints("2023-12");
        
        // Expected: 2*5=10
        assertEquals("Multiple seats edge case should return 10 points", 10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() throws Exception {
        // Setup: Customer C8 has membership with POINTS award
        Customer c8 = RideshareFactory.eINSTANCE.createCustomer();
        c8.setID("C8");
        MembershipPackage mp8 = RideshareFactory.eINSTANCE.createMembershipPackage();
        mp8.getAwards().add(Award.POINTS);
        c8.setMembershipPackage(mp8);
        
        // Setup: Customer C9 has membership with POINTS award
        Customer c9 = RideshareFactory.eINSTANCE.createCustomer();
        c9.setID("C9");
        MembershipPackage mp9 = RideshareFactory.eINSTANCE.createMembershipPackage();
        mp9.getAwards().add(Award.POINTS);
        c9.setMembershipPackage(mp9);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        // C8 create Booking1: 2024-01-10 (seats:50)
        Trip trip1 = RideshareFactory.eINSTANCE.createTrip();
        trip1.setPrice(150.0);
        trip1.setDepartureDate(dateFormat.parse("2024-05-25 12:00"));
        
        Booking booking1 = RideshareFactory.eINSTANCE.createBooking();
        booking1.setNumberOfSeats(50);
        booking1.setBookingDate(dateFormat.parse("2024-01-10 10:00"));
        booking1.setTrip(trip1);
        booking1.setCustomer(c8);
        c8.getBookings().add(booking1);
        
        // C8 create Booking2: 2024-01-15 (seats:50)
        Trip trip2 = RideshareFactory.eINSTANCE.createTrip();
        trip2.setPrice(200.0);
        trip2.setDepartureDate(dateFormat.parse("2024-06-25 12:00"));
        
        Booking booking2 = RideshareFactory.eINSTANCE.createBooking();
        booking2.setNumberOfSeats(50);
        booking2.setBookingDate(dateFormat.parse("2024-01-15 10:00"));
        booking2.setTrip(trip2);
        booking2.setCustomer(c8);
        c8.getBookings().add(booking2);
        
        // C9 create Booking: 2024-01-10 (seats:50)
        Trip trip3 = RideshareFactory.eINSTANCE.createTrip();
        trip3.setPrice(200.0);
        trip3.setDepartureDate(dateFormat.parse("2024-07-25 12:00"));
        
        Booking booking3 = RideshareFactory.eINSTANCE.createBooking();
        booking3.setNumberOfSeats(50);
        booking3.setBookingDate(dateFormat.parse("2024-01-10 10:00"));
        booking3.setTrip(trip3);
        booking3.setCustomer(c9);
        c9.getBookings().add(booking3);
        
        // Test: Compute reward points for current month 2024-01
        int resultC8 = c8.computeMonthlyRewardPoints("2024-01");
        int resultC9 = c9.computeMonthlyRewardPoints("2024-01");
        
        // Expected: C8: (50+50)*5=500, C9: 50*5=250
        assertEquals("C8 should have 500 points for large seat quantity", 500, resultC8);
        assertEquals("C9 should have 250 points for large seat quantity", 250, resultC9);
    }
}