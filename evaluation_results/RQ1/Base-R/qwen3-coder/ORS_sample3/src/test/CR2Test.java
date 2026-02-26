import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CR2Test {
    
    private OnlineRideshareSystem system;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        system = new OnlineRideshareSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }
    
    @Test
    public void testCase1_20PercentDiscountAppliedForEarlyBooking() {
        // Test Case 1: "20% discount applied for early booking"
        
        // Setup: Create Trip T789 (price: 100.0, departure: 2023-12-25 08:00)
        Trip trip = new Trip();
        trip.setPrice(100.0);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-25 08:00", formatter));
        
        // Setup: Customer C3 has membership with 20% DISCOUNTS award
        Customer customer = new Customer();
        Membership membership = new Membership();
        membership.setType("discount");
        membership.setActive(true);
        customer.setMembership(membership);
        
        // Setup: Booking made at 2023-12-24 07:00 (25 hours before)
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-24 07:00", formatter);
        
        // Execute: Calculate discounted price
        double result = system.calculateDiscountedTripPrice(customer, trip, bookingTime);
        
        // Verify: Expected Output: 80.0
        assertEquals(80.0, result, 0.001);
    }
    
    @Test
    public void testCase2_DiscountDeniedForLateBooking() {
        // Test Case 2: "Discount denied for late booking"
        
        // Setup: Create Trip T999 (price: 200.0, departure: 2023-12-25 12:00)
        Trip trip = new Trip();
        trip.setPrice(200.0);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00", formatter));
        
        // Setup: Customer C4 has membership with 20% DISCOUNTS award
        Customer customer = new Customer();
        Membership membership = new Membership();
        membership.setType("discount");
        membership.setActive(true);
        customer.setMembership(membership);
        
        // Setup: Booking made at 2023-12-25 10:30 (1.5 hours before)
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 10:30", formatter);
        
        // Execute: Calculate discounted price
        double result = system.calculateDiscountedTripPrice(customer, trip, bookingTime);
        
        // Verify: Expected Output: 200.0
        assertEquals(200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_Exact24HourBoundaryForDiscount() {
        // Test Case 3: "Exact 24-hour boundary for discount"
        
        // Setup: Trip T800 (price: 100.0, departure: 2023-12-25 08:00)
        Trip trip = new Trip();
        trip.setPrice(100.0);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-25 08:00", formatter));
        
        // Setup: Customer has membership with 20% DISCOUNTS award
        Customer customer = new Customer();
        Membership membership = new Membership();
        membership.setType("discount");
        membership.setActive(true);
        customer.setMembership(membership);
        
        // Setup: Booking made at 2023-12-24 08:00 (exactly 24 hours before)
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-24 08:00", formatter);
        
        // Execute: Calculate discounted price
        double result = system.calculateDiscountedTripPrice(customer, trip, bookingTime);
        
        // Verify: Expected Output: 80.0
        assertEquals(80.0, result, 0.001);
    }
    
    @Test
    public void testCase4_NoDiscountWithoutMembership() {
        // Test Case 4: "No discount without membership"
        
        // Setup: Trip T900 (price: 200.0, departure: 2023-12-26 12:00)
        Trip trip = new Trip();
        trip.setPrice(200.0);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-26 12:00", formatter));
        
        // Setup: Customer has no membership
        Customer customer = new Customer();
        // No membership set (null by default)
        
        // Setup: Booking made 48 hours before (2023-12-24 12:00)
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-24 12:00", formatter);
        
        // Execute: Calculate discounted price
        double result = system.calculateDiscountedTripPrice(customer, trip, bookingTime);
        
        // Verify: Expected Output: 200.0
        assertEquals(200.0, result, 0.001);
    }
    
    @Test
    public void testCase5_DiscountAppliesOnlyToEligibleMembershipType() {
        // Test Case 5: "Discount applies only to eligible membership type"
        
        // Setup: Trip T950 (price: 150.0)
        Trip trip = new Trip();
        trip.setPrice(150.0);
        // Departure time not specified, but we need it for time comparison
        trip.setDepartureTime(LocalDateTime.parse("2023-12-26 12:00", formatter));
        
        // Setup: Customer has membership with CASHBACK only (no DISCOUNTS)
        Customer customer = new Customer();
        Membership membership = new Membership();
        membership.setType("cashback");
        membership.setActive(true);
        customer.setMembership(membership);
        
        // Setup: Booking made 30 hours before
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 06:00", formatter);
        
        // Execute: Calculate discounted price
        double result = system.calculateDiscountedTripPrice(customer, trip, bookingTime);
        
        // Verify: Expected Output: 150.0
        assertEquals(150.0, result, 0.001);
    }
}