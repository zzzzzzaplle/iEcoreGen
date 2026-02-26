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
        // Setup: Create Trip T789 (price: 100.0, departure: 2023-12-25 08:00)
        Trip trip = new Trip();
        trip.setId("T789");
        trip.setPrice(100.0);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-25 08:00:00", formatter));
        
        // Setup: Customer C3 has membership with 20% DISCOUNTS award
        Customer customer = new Customer();
        Membership membership = new Membership();
        Reward reward = new Reward();
        reward.setRewardType("DISCOUNTS");
        membership.setReward(reward);
        customer.setMembership(membership);
        
        // Setup: Booking made at 2023-12-24 07:00 (25 hours before)
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-24 07:00:00", formatter);
        
        // Calculate discounted price
        double result = system.calculateDiscountedTripPrice(customer, trip, bookingTime);
        
        // Expected Output: 80.0
        assertEquals(80.0, result, 0.001);
    }
    
    @Test
    public void testCase2_DiscountDeniedForLateBooking() {
        // Setup: Create Trip T999 (price: 200.0, departure: 2023-12-25 12:00)
        Trip trip = new Trip();
        trip.setId("T999");
        trip.setPrice(200.0);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        
        // Setup: Customer C4 has membership with 20% DISCOUNTS award
        Customer customer = new Customer();
        Membership membership = new Membership();
        Reward reward = new Reward();
        reward.setRewardType("DISCOUNTS");
        membership.setReward(reward);
        customer.setMembership(membership);
        
        // Setup: Booking made at 2023-12-25 10:30 (1.5 hours before)
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 10:30:00", formatter);
        
        // Calculate discounted price
        double result = system.calculateDiscountedTripPrice(customer, trip, bookingTime);
        
        // Expected Output: 200.0
        assertEquals(200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_Exact24HourBoundaryForDiscount() {
        // Setup: Trip T800 (price: 100.0, departure: 2023-12-25 08:00)
        Trip trip = new Trip();
        trip.setId("T800");
        trip.setPrice(100.0);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-25 08:00:00", formatter));
        
        // Setup: Customer has membership with 20% DISCOUNTS award
        Customer customer = new Customer();
        Membership membership = new Membership();
        Reward reward = new Reward();
        reward.setRewardType("DISCOUNTS");
        membership.setReward(reward);
        customer.setMembership(membership);
        
        // Setup: Booking made at 2023-12-24 08:00 (exactly 24 hours before)
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-24 08:00:00", formatter);
        
        // Calculate discounted price
        double result = system.calculateDiscountedTripPrice(customer, trip, bookingTime);
        
        // Expected Output: 80.0
        assertEquals(80.0, result, 0.001);
    }
    
    @Test
    public void testCase4_NoDiscountWithoutMembership() {
        // Setup: Trip T900 (price: 200.0, departure: 2023-12-26 12:00)
        Trip trip = new Trip();
        trip.setId("T900");
        trip.setPrice(200.0);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-26 12:00:00", formatter));
        
        // Setup: Customer has no membership
        Customer customer = new Customer();
        
        // Setup: Booking made 48 hours before (2023-12-24 12:00)
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-24 12:00:00", formatter);
        
        // Calculate discounted price
        double result = system.calculateDiscountedTripPrice(customer, trip, bookingTime);
        
        // Expected Output: 200.0
        assertEquals(200.0, result, 0.001);
    }
    
    @Test
    public void testCase5_DiscountAppliesOnlyToEligibleMembershipType() {
        // Setup: Trip T950 (price: 150.0)
        Trip trip = new Trip();
        trip.setId("T950");
        trip.setPrice(150.0);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-26 12:00:00", formatter));
        
        // Setup: Customer has membership with CASHBACK only (no DISCOUNTS)
        Customer customer = new Customer();
        Membership membership = new Membership();
        Reward reward = new Reward();
        reward.setRewardType("CASHBACK");
        membership.setReward(reward);
        customer.setMembership(membership);
        
        // Setup: Booking made 30 hours before
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 06:00:00", formatter);
        
        // Calculate discounted price
        double result = system.calculateDiscountedTripPrice(customer, trip, bookingTime);
        
        // Expected Output: 150.0
        assertEquals(150.0, result, 0.001);
    }
}