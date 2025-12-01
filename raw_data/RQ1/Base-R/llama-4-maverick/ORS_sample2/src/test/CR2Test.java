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
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_20PercentDiscountAppliedForEarlyBooking() {
        // Test Case 1: "20% discount applied for early booking"
        // Setup: Create Trip T789 (price: 100.0, departure: 2023-12-25 08:00)
        Trip trip = new Trip();
        trip.setId("T789");
        trip.setPrice(100.0);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-25 08:00:00", formatter));
        
        // Setup: Customer C3 has membership with 20% DISCOUNTS award
        User customer = new User();
        customer.setId("C3");
        Membership membership = new Membership();
        membership.setHasRewards(true);
        membership.setDiscountPercentage(20.0);
        customer.setMembership(membership);
        
        // Setup: Booking made at 2023-12-24 07:00 (25 hours before)
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-24 07:00:00", formatter);
        
        // Execute: Calculate discounted price
        double result = system.calculateDiscountedTripPrice(trip, customer, bookingTime);
        
        // Verify: Expected output is 80.0
        assertEquals(80.0, result, 0.001);
    }
    
    @Test
    public void testCase2_DiscountDeniedForLateBooking() {
        // Test Case 2: "Discount denied for late booking"
        // Setup: Create Trip T999 (price: 200.0, departure: 2023-12-25 12:00)
        Trip trip = new Trip();
        trip.setId("T999");
        trip.setPrice(200.0);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        
        // Setup: Customer C4 has membership with 20% DISCOUNTS award
        User customer = new User();
        customer.setId("C4");
        Membership membership = new Membership();
        membership.setHasRewards(true);
        membership.setDiscountPercentage(20.0);
        customer.setMembership(membership);
        
        // Setup: Booking made at 2023-12-25 10:30 (1.5 hours before)
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 10:30:00", formatter);
        
        // Execute: Calculate discounted price
        double result = system.calculateDiscountedTripPrice(trip, customer, bookingTime);
        
        // Verify: Expected output is 200.0
        assertEquals(200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_Exact24HourBoundaryForDiscount() {
        // Test Case 3: "Exact 24-hour boundary for discount"
        // Setup: Trip T800 (price: 100.0, departure: 2023-12-25 08:00)
        Trip trip = new Trip();
        trip.setId("T800");
        trip.setPrice(100.0);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-25 08:00:00", formatter));
        
        // Setup: Customer has membership with 20% DISCOUNTS award
        User customer = new User();
        Membership membership = new Membership();
        membership.setHasRewards(true);
        membership.setDiscountPercentage(20.0);
        customer.setMembership(membership);
        
        // Setup: Booking made at 2023-12-24 08:00 (exactly 24 hours before)
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-24 08:00:00", formatter);
        
        // Execute: Calculate discounted price
        double result = system.calculateDiscountedTripPrice(trip, customer, bookingTime);
        
        // Verify: Expected output is 80.0 (tests the boundary condition)
        assertEquals(80.0, result, 0.001);
    }
    
    @Test
    public void testCase4_NoDiscountWithoutMembership() {
        // Test Case 4: "No discount without membership"
        // Setup: Trip T900 (price: 200.0, departure: 2023-12-26 12:00)
        Trip trip = new Trip();
        trip.setId("T900");
        trip.setPrice(200.0);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-26 12:00:00", formatter));
        
        // Setup: Customer has no membership
        User customer = new User();
        customer.setMembership(null); // Explicitly set to null for clarity
        
        // Setup: Booking made 48 hours before (2023-12-24 12:00)
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-24 12:00:00", formatter);
        
        // Execute: Calculate discounted price
        double result = system.calculateDiscountedTripPrice(trip, customer, bookingTime);
        
        // Verify: Expected output is 200.0 (verifies membership requirement)
        assertEquals(200.0, result, 0.001);
    }
    
    @Test
    public void testCase5_DiscountAppliesOnlyToEligibleMembershipType() {
        // Test Case 5: "Discount applies only to eligible membership type"
        // Setup: Trip T950 (price: 150.0)
        Trip trip = new Trip();
        trip.setId("T950");
        trip.setPrice(150.0);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-26 12:00:00", formatter));
        
        // Setup: Customer has membership with CASHBACK only (no DISCOUNTS)
        // Interpretation: hasRewards = false means no discount eligibility
        User customer = new User();
        Membership membership = new Membership();
        membership.setHasRewards(false); // No discounts, only cashback
        membership.setDiscountPercentage(20.0); // Still has discount percentage but not eligible
        customer.setMembership(membership);
        
        // Setup: Booking made 30 hours before
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 06:00:00", formatter);
        
        // Execute: Calculate discounted price
        double result = system.calculateDiscountedTripPrice(trip, customer, bookingTime);
        
        // Verify: Expected output is 150.0 (tests award type validation)
        assertEquals(150.0, result, 0.001);
    }
}