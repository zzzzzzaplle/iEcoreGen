package edu.rideshare.rideshare2.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.rideshare.*;
import org.eclipse.emf.common.util.EList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR4Test {
    
    private RideshareFactory factory;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        factory = RideshareFactory.eINSTANCE;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_PointsCalculationWithMultipleBookings() throws Exception {
        // Setup: Customer C5 has membership with POINTS award
        Customer customerC5 = factory.createCustomer();
        MembershipPackage membershipC5 = factory.createMembershipPackage();
        membershipC5.getAwards().add(Award.POINTS);
        customerC5.setMembershipPackage(membershipC5);
        
        // Create trips
        Trip trip1 = factory.createTrip();
        trip1.setPrice(200.0);
        trip1.setDepartureDate(dateFormat.parse("2023-12-25 12:00:00"));
        
        Trip trip2 = factory.createTrip();
        trip2.setPrice(100.0);
        trip2.setDepartureDate(dateFormat.parse("2023-12-26 12:00:00"));
        
        // Create bookings for C5
        Booking booking1 = factory.createBooking();
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(dateFormat.parse("2023-12-01 10:00:00"));
        booking1.setCustomer(customerC5);
        booking1.setTrip(trip1);
        
        Booking booking2 = factory.createBooking();
        booking2.setNumberOfSeats(3);
        booking2.setBookingDate(dateFormat.parse("2023-12-01 10:00:00"));
        booking2.setCustomer(customerC5);
        booking2.setTrip(trip2);
        
        customerC5.getBookings().add(booking1);
        customerC5.getBookings().add(booking2);
        
        // Test: Compute reward points for Customer C5 (current Month: 2023-12)
        int result = customerC5.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 25. (2+3)*5=25
        assertEquals(25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() throws Exception {
        // Setup: Customer C6 has membership with POINTS award
        Customer customerC6 = factory.createCustomer();
        MembershipPackage membershipC6 = factory.createMembershipPackage();
        membershipC6.getAwards().add(Award.POINTS);
        customerC6.setMembershipPackage(membershipC6);
        
        // Create trip with future departure date
        Trip trip = factory.createTrip();
        trip.setPrice(100.0);
        trip.setDepartureDate(dateFormat.parse("2024-12-26 12:00:00"));
        
        // Create booking for C6
        Booking booking = factory.createBooking();
        booking.setNumberOfSeats(4);
        booking.setBookingDate(dateFormat.parse("2023-12-01 10:00:00"));
        booking.setCustomer(customerC6);
        booking.setTrip(trip);
        
        customerC6.getBookings().add(booking);
        
        // Test: Compute reward points for Customer C6 (current Month: 2023-12)
        int result = customerC6.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() throws Exception {
        // Setup: Customer C7 has membership with POINTS award
        Customer customerC7 = factory.createCustomer();
        MembershipPackage membershipC7 = factory.createMembershipPackage();
        membershipC7.getAwards().add(Award.POINTS);
        customerC7.setMembershipPackage(membershipC7);
        
        // Create trip
        Trip trip = factory.createTrip();
        trip.setPrice(200.0);
        trip.setDepartureDate(dateFormat.parse("2023-12-25 12:00:00"));
        
        // Create bookings for C7 - one in November, one in December
        Booking booking1 = factory.createBooking();
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(dateFormat.parse("2023-11-30 10:00:00")); // November booking
        booking1.setCustomer(customerC7);
        booking1.setTrip(trip);
        
        Booking booking2 = factory.createBooking();
        booking2.setNumberOfSeats(3);
        booking2.setBookingDate(dateFormat.parse("2023-12-01 10:00:00")); // December booking
        booking2.setCustomer(customerC7);
        booking2.setTrip(trip);
        
        customerC7.getBookings().add(booking1);
        customerC7.getBookings().add(booking2);
        
        // Test: Compute reward points for Customer C7 (current Month: 2023-12)
        int result = customerC7.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 15. 3*5=15 (only December booking counts)
        assertEquals(15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() throws Exception {
        // Setup: Customer C8 has membership with POINTS award
        Customer customerC8 = factory.createCustomer();
        MembershipPackage membershipC8 = factory.createMembershipPackage();
        membershipC8.getAwards().add(Award.POINTS);
        customerC8.setMembershipPackage(membershipC8);
        
        // Create trip with future departure date
        Trip trip = factory.createTrip();
        trip.setPrice(200.0);
        trip.setDepartureDate(dateFormat.parse("2024-03-25 12:00:00"));
        
        // Create booking for C8
        Booking booking = factory.createBooking();
        booking.setNumberOfSeats(2);
        booking.setBookingDate(dateFormat.parse("2023-12-10 10:00:00"));
        booking.setCustomer(customerC8);
        booking.setTrip(trip);
        
        customerC8.getBookings().add(booking);
        
        // Test: Compute reward points for Customer C8 (current Month: 2023-12)
        int result = customerC8.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 10. 2*5=10
        assertEquals(10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() throws Exception {
        // Setup: Customer C8 has membership with POINTS award
        Customer customerC8 = factory.createCustomer();
        MembershipPackage membershipC8 = factory.createMembershipPackage();
        membershipC8.getAwards().add(Award.POINTS);
        customerC8.setMembershipPackage(membershipC8);
        
        // Setup: Customer C9 has membership with POINTS award
        Customer customerC9 = factory.createCustomer();
        MembershipPackage membershipC9 = factory.createMembershipPackage();
        membershipC9.getAwards().add(Award.POINTS);
        customerC9.setMembershipPackage(membershipC9);
        
        // Create trips for C8
        Trip trip1 = factory.createTrip();
        trip1.setPrice(150.0);
        trip1.setDepartureDate(dateFormat.parse("2024-05-25 12:00:00"));
        
        Trip trip2 = factory.createTrip();
        trip2.setPrice(200.0);
        trip2.setDepartureDate(dateFormat.parse("2024-06-25 12:00:00"));
        
        // Create trips for C9
        Trip trip3 = factory.createTrip();
        trip3.setPrice(200.0);
        trip3.setDepartureDate(dateFormat.parse("2024-07-25 12:00:00"));
        
        // Create bookings for C8
        Booking booking1 = factory.createBooking();
        booking1.setNumberOfSeats(50);
        booking1.setBookingDate(dateFormat.parse("2024-01-10 10:00:00"));
        booking1.setCustomer(customerC8);
        booking1.setTrip(trip1);
        
        Booking booking2 = factory.createBooking();
        booking2.setNumberOfSeats(50);
        booking2.setBookingDate(dateFormat.parse("2024-01-15 10:00:00"));
        booking2.setCustomer(customerC8);
        booking2.setTrip(trip2);
        
        // Create booking for C9
        Booking booking3 = factory.createBooking();
        booking3.setNumberOfSeats(50);
        booking3.setBookingDate(dateFormat.parse("2024-01-10 10:00:00"));
        booking3.setCustomer(customerC9);
        booking3.setTrip(trip3);
        
        customerC8.getBookings().add(booking1);
        customerC8.getBookings().add(booking2);
        customerC9.getBookings().add(booking3);
        
        // Test: Compute reward points for Customer C8 (current Month: 2024-01)
        int resultC8 = customerC8.computeMonthlyRewardPoints("2024-01");
        
        // Test: Compute reward points for Customer C9 (current Month: 2024-01)
        int resultC9 = customerC9.computeMonthlyRewardPoints("2024-01");
        
        // Expected Output: C8 reward points: 500. C9 reward points: 250.
        assertEquals(500, resultC8); // (50 + 50) * 5 = 500
        assertEquals(250, resultC9); // 50 * 5 = 250
    }
}