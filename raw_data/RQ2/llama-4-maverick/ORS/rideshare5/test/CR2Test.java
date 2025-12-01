package edu.rideshare.rideshare5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.rideshare.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR2Test {
    
    private RideshareFactory factory;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        factory = RideshareFactory.eINSTANCE;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_20PercentDiscountAppliedForEarlyBooking() throws Exception {
        // Setup: Create Trip T789 (price: 100.0, departure: 2023-12-25 08:00)
        Trip trip = factory.createTrip();
        trip.setPrice(100.0);
        Date departureDate = dateFormat.parse("2023-12-25 08:00:00");
        trip.setDepartureDate(departureDate);
        
        // Setup: Customer C3 has membership with 20% DISCOUNTS award
        Customer customer = factory.createCustomer();
        MembershipPackage membership = factory.createMembershipPackage();
        membership.getAwards().add(Award.DISCOUNTS);
        customer.setMembershipPackage(membership);
        
        // Setup: Booking made at 2023-12-24 07:00 (25 hours before)
        String bookingTime = "2023-12-24 07:00:00";
        
        // Execute: Calculate discounted price
        double result = trip.calculateDiscountedPrice(customer, bookingTime);
        
        // Verify: Expected Output: 80.0
        assertEquals(80.0, result, 0.001);
    }
    
    @Test
    public void testCase2_DiscountDeniedForLateBooking() throws Exception {
        // Setup: Create Trip T999 (price: 200.0, departure: 2023-12-25 12:00)
        Trip trip = factory.createTrip();
        trip.setPrice(200.0);
        Date departureDate = dateFormat.parse("2023-12-25 12:00:00");
        trip.setDepartureDate(departureDate);
        
        // Setup: Customer C4 has membership with 20% DISCOUNTS award
        Customer customer = factory.createCustomer();
        MembershipPackage membership = factory.createMembershipPackage();
        membership.getAwards().add(Award.DISCOUNTS);
        customer.setMembershipPackage(membership);
        
        // Setup: Booking made at 2023-12-25 10:30 (1.5 hours before)
        String bookingTime = "2023-12-25 10:30:00";
        
        // Execute: Calculate discounted price
        double result = trip.calculateDiscountedPrice(customer, bookingTime);
        
        // Verify: Expected Output: 200.0
        assertEquals(200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_Exact24HourBoundaryForDiscount() throws Exception {
        // Setup: Trip T800 (price: 100.0, departure: 2023-12-25 08:00)
        Trip trip = factory.createTrip();
        trip.setPrice(100.0);
        Date departureDate = dateFormat.parse("2023-12-25 08:00:00");
        trip.setDepartureDate(departureDate);
        
        // Setup: Customer has membership with 20% DISCOUNTS award
        Customer customer = factory.createCustomer();
        MembershipPackage membership = factory.createMembershipPackage();
        membership.getAwards().add(Award.DISCOUNTS);
        customer.setMembershipPackage(membership);
        
        // Setup: Booking made at 2023-12-24 08:00 (exactly 24 hours before)
        String bookingTime = "2023-12-24 08:00:00";
        
        // Execute: Calculate discounted price
        double result = trip.calculateDiscountedPrice(customer, bookingTime);
        
        // Verify: Expected Output: 80.0
        assertEquals(80.0, result, 0.001);
    }
    
    @Test
    public void testCase4_NoDiscountWithoutMembership() throws Exception {
        // Setup: Trip T900 (price: 200.0, departure: 2023-12-26 12:00)
        Trip trip = factory.createTrip();
        trip.setPrice(200.0);
        Date departureDate = dateFormat.parse("2023-12-26 12:00:00");
        trip.setDepartureDate(departureDate);
        
        // Setup: Customer has no membership
        Customer customer = factory.createCustomer();
        // No membership package set
        
        // Setup: Booking made 48 hours before (2023-12-24 12:00)
        String bookingTime = "2023-12-24 12:00:00";
        
        // Execute: Calculate discounted price
        double result = trip.calculateDiscountedPrice(customer, bookingTime);
        
        // Verify: Expected Output: 200.0
        assertEquals(200.0, result, 0.001);
    }
    
    @Test
    public void testCase5_DiscountAppliesOnlyToEligibleMembershipType() throws Exception {
        // Setup: Trip T950 (price: 150.0)
        Trip trip = factory.createTrip();
        trip.setPrice(150.0);
        Date departureDate = dateFormat.parse("2023-12-26 10:00:00");
        trip.setDepartureDate(departureDate);
        
        // Setup: Customer has membership with CASHBACK only (no DISCOUNTS)
        Customer customer = factory.createCustomer();
        MembershipPackage membership = factory.createMembershipPackage();
        membership.getAwards().add(Award.CASHBACK);
        customer.setMembershipPackage(membership);
        
        // Setup: Booking made 30 hours before
        String bookingTime = "2023-12-25 04:00:00";
        
        // Execute: Calculate discounted price
        double result = trip.calculateDiscountedPrice(customer, bookingTime);
        
        // Verify: Expected Output: 150.0
        assertEquals(150.0, result, 0.001);
    }
}