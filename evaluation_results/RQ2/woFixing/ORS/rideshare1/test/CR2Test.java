package edu.rideshare.rideshare1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.rideshare.RideshareFactory;
import edu.rideshare.Trip;
import edu.rideshare.Customer;
import edu.rideshare.MembershipPackage;
import edu.rideshare.Award;
import java.util.Date;
import java.text.SimpleDateFormat;

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
        
        // Customer C3 has membership with 20% DISCOUNTS award
        Customer customer = factory.createCustomer();
        MembershipPackage membership = factory.createMembershipPackage();
        membership.getAwards().add(Award.DISCOUNTS);
        customer.setMembershipPackage(membership);
        
        // Booking made at 2023-12-24 07:00 (25 hours before)
        Date bookingDate = dateFormat.parse("2023-12-24 07:00:00");
        
        // Calculate discounted price
        double discountedPrice = calculateDiscountedPrice(trip, customer, bookingDate);
        
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
        
        // Customer C4 has membership with 20% DISCOUNTS award
        Customer customer = factory.createCustomer();
        MembershipPackage membership = factory.createMembershipPackage();
        membership.getAwards().add(Award.DISCOUNTS);
        customer.setMembershipPackage(membership);
        
        // Booking made at 2023-12-25 10:30 (1.5 hours before)
        Date bookingDate = dateFormat.parse("2023-12-25 10:30:00");
        
        // Calculate discounted price
        double discountedPrice = calculateDiscountedPrice(trip, customer, bookingDate);
        
        // Expected Output: 200.0
        assertEquals(200.0, discountedPrice, 0.001);
    }
    
    @Test
    public void testCase3_Exact24HourBoundaryForDiscount() throws Exception {
        // Trip T800 (price: 100.0, departure: 2023-12-25 08:00)
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
        Date bookingDate = dateFormat.parse("2023-12-24 08:00:00");
        
        // Calculate discounted price
        double discountedPrice = calculateDiscountedPrice(trip, customer, bookingDate);
        
        // Expected Output: 80.0
        assertEquals(80.0, discountedPrice, 0.001);
    }
    
    @Test
    public void testCase4_NoDiscountWithoutMembership() throws Exception {
        // Trip T900 (price: 200.0, departure: 2023-12-26 12:00)
        Trip trip = factory.createTrip();
        trip.setPrice(200.0);
        Date departureDate = dateFormat.parse("2023-12-26 12:00:00");
        trip.setDepartureDate(departureDate);
        
        // Customer has no membership
        Customer customer = factory.createCustomer();
        // No membership package set
        
        // Booking made 48 hours before (2023-12-24 12:00)
        Date bookingDate = dateFormat.parse("2023-12-24 12:00:00");
        
        // Calculate discounted price
        double discountedPrice = calculateDiscountedPrice(trip, customer, bookingDate);
        
        // Expected Output: 200.0
        assertEquals(200.0, discountedPrice, 0.001);
    }
    
    @Test
    public void testCase5_DiscountAppliesOnlyToEligibleMembershipType() throws Exception {
        // Trip T950 (price: 150.0)
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
        Date bookingDate = dateFormat.parse("2023-12-25 06:00:00");
        
        // Calculate discounted price
        double discountedPrice = calculateDiscountedPrice(trip, customer, bookingDate);
        
        // Expected Output: 150.0
        assertEquals(150.0, discountedPrice, 0.001);
    }
    
    /**
     * Helper method to calculate discounted price based on CR2 requirements
     * Discount is 20% off if:
     * 1. Customer has membership with DISCOUNTS award
     * 2. Booking is made ≥24 hours before departure
     */
    private double calculateDiscountedPrice(Trip trip, Customer customer, Date bookingDate) {
        double originalPrice = trip.getPrice();
        
        // Check if customer has membership with DISCOUNTS award
        boolean hasDiscountAward = false;
        if (customer.getMembershipPackage() != null) {
            hasDiscountAward = customer.getMembershipPackage().hasAward(Award.DISCOUNTS);
        }
        
        // Check if booking is made ≥24 hours before departure
        boolean isEarlyBooking = false;
        if (trip.getDepartureDate() != null && bookingDate != null) {
            long timeDifference = trip.getDepartureDate().getTime() - bookingDate.getTime();
            long hoursDifference = timeDifference / (1000 * 60 * 60);
            isEarlyBooking = hoursDifference >= 24;
        }
        
        // Apply 20% discount only if both conditions are met
        if (hasDiscountAward && isEarlyBooking) {
            return originalPrice * 0.8;
        } else {
            return originalPrice;
        }
    }
}