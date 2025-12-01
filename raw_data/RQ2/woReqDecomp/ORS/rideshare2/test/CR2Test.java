package edu.rideshare.rideshare2.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.rideshare.RideshareFactory;
import edu.rideshare.Trip;
import edu.rideshare.Customer;
import edu.rideshare.MembershipPackage;
import edu.rideshare.Award;
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
        // Create Trip T789 (price: 100.0, departure: 2023-12-25 08:00)
        Trip trip = factory.createTrip();
        trip.setPrice(100.0);
        Date departureDate = dateFormat.parse("2023-12-25 08:00:00");
        trip.setDepartureDate(departureDate);
        
        // Create Customer C3 with membership with 20% DISCOUNTS award
        Customer customer = factory.createCustomer();
        MembershipPackage membership = factory.createMembershipPackage();
        membership.getAwards().add(Award.DISCOUNTS);
        customer.setMembershipPackage(membership);
        
        // Booking made at 2023-12-24 07:00 (25 hours before)
        String bookingTime = "2023-12-24 07:00:00";
        
        // Calculate discounted price
        double discountedPrice = trip.calculateDiscountedPrice(customer, bookingTime);
        
        // Expected Output: 80.0
        assertEquals(80.0, discountedPrice, 0.001);
    }
    
    @Test
    public void testCase2_DiscountDeniedForLateBooking() throws Exception {
        // Create Trip T999 (price: 200.0, departure: 2023-12-25 12:00)
        Trip trip = factory.createTrip();
        trip.setPrice(200.0);
        Date departureDate = dateFormat.parse("2023-12-25 12:00:00");
        trip.setDepartureDate(departureDate);
        
        // Create Customer C4 with membership with 20% DISCOUNTS award
        Customer customer = factory.createCustomer();
        MembershipPackage membership = factory.createMembershipPackage();
        membership.getAwards().add(Award.DISCOUNTS);
        customer.setMembershipPackage(membership);
        
        // Booking made at 2023-12-25 10:30 (1.5 hours before)
        String bookingTime = "2023-12-25 10:30:00";
        
        // Calculate discounted price
        double discountedPrice = trip.calculateDiscountedPrice(customer, bookingTime);
        
        // Expected Output: 200.0
        assertEquals(200.0, discountedPrice, 0.001);
    }
    
    @Test
    public void testCase3_Exact24HourBoundaryForDiscount() throws Exception {
        // Create Trip T800 (price: 100.0, departure: 2023-12-25 08:00)
        Trip trip = factory.createTrip();
        trip.setPrice(100.0);
        Date departureDate = dateFormat.parse("2023-12-25 08:00:00");
        trip.setDepartureDate(departureDate);
        
        // Customer has membership with 20% DISCOUNTS award
        Customer customer = factory.createCustomer();
        MembershipPackage membership = factory.createMembershipPackage();
        membership.getAwards().add(Award.DISCOUNTS);
        customer.setMembershipPackage(membership);
        
        // Booking made at 2023-12-24 08:00 (exactly 24 hours before)
        String bookingTime = "2023-12-24 08:00:00";
        
        // Calculate discounted price
        double discountedPrice = trip.calculateDiscountedPrice(customer, bookingTime);
        
        // Expected Output: 80.0
        assertEquals(80.0, discountedPrice, 0.001);
    }
    
    @Test
    public void testCase4_NoDiscountWithoutMembership() throws Exception {
        // Create Trip T900 (price: 200.0, departure: 2023-12-26 12:00)
        Trip trip = factory.createTrip();
        trip.setPrice(200.0);
        Date departureDate = dateFormat.parse("2023-12-26 12:00:00");
        trip.setDepartureDate(departureDate);
        
        // Customer has no membership
        Customer customer = factory.createCustomer();
        // No membership package set
        
        // Booking made 48 hours before (2023-12-24 12:00)
        String bookingTime = "2023-12-24 12:00:00";
        
        // Calculate discounted price
        double discountedPrice = trip.calculateDiscountedPrice(customer, bookingTime);
        
        // Expected Output: 200.0
        assertEquals(200.0, discountedPrice, 0.001);
    }
    
    @Test
    public void testCase5_DiscountAppliesOnlyToEligibleMembershipType() throws Exception {
        // Create Trip T950 (price: 150.0)
        Trip trip = factory.createTrip();
        trip.setPrice(150.0);
        Date departureDate = dateFormat.parse("2023-12-26 12:00:00");
        trip.setDepartureDate(departureDate);
        
        // Customer has membership with CASHBACK only (no DISCOUNTS)
        Customer customer = factory.createCustomer();
        MembershipPackage membership = factory.createMembershipPackage();
        membership.getAwards().add(Award.CASHBACK);
        customer.setMembershipPackage(membership);
        
        // Booking made 30 hours before
        String bookingTime = "2023-12-25 06:00:00";
        
        // Calculate discounted price
        double discountedPrice = trip.calculateDiscountedPrice(customer, bookingTime);
        
        // Expected Output: 150.0
        assertEquals(150.0, discountedPrice, 0.001);
    }
}