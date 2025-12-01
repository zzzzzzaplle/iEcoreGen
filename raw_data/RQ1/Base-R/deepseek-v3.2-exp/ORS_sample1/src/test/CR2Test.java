import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CR2Test {
    private Trip trip;
    private User customer;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        trip = new Trip();
        customer = new User();
    }
    
    @Test
    public void testCase1_20PercentDiscountAppliedForEarlyBooking() {
        // Setup: Trip T789 (price: 100.0, departure: 2023-12-25 08:00)
        trip.setId("T789");
        trip.setPrice(100.0);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-25 08:00:00", formatter));
        
        // Customer C3 has membership with 20% DISCOUNTS award
        Membership membership = new Membership();
        Reward reward = new Reward();
        reward.setRewardType("DISCOUNTS");
        membership.setReward(reward);
        customer.setMembership(membership);
        
        // Booking made at 2023-12-24 07:00 (25 hours before)
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-24 07:00:00", formatter);
        
        // Execute and verify
        double result = trip.calculateDiscountedTripPrice(customer, bookingTime);
        assertEquals(80.0, result, 0.001);
    }
    
    @Test
    public void testCase2_DiscountDeniedForLateBooking() {
        // Setup: Trip T999 (price: 200.0, departure: 2023-12-25 12:00)
        trip.setId("T999");
        trip.setPrice(200.0);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        
        // Customer C4 has membership with 20% DISCOUNTS award
        Membership membership = new Membership();
        Reward reward = new Reward();
        reward.setRewardType("DISCOUNTS");
        membership.setReward(reward);
        customer.setMembership(membership);
        
        // Booking made at 2023-12-25 10:30 (1.5 hours before)
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 10:30:00", formatter);
        
        // Execute and verify
        double result = trip.calculateDiscountedTripPrice(customer, bookingTime);
        assertEquals(200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_Exact24HourBoundaryForDiscount() {
        // Setup: Trip T800 (price: 100.0, departure: 2023-12-25 08:00)
        trip.setId("T800");
        trip.setPrice(100.0);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-25 08:00:00", formatter));
        
        // Customer has membership with 20% DISCOUNTS award
        Membership membership = new Membership();
        Reward reward = new Reward();
        reward.setRewardType("DISCOUNTS");
        membership.setReward(reward);
        customer.setMembership(membership);
        
        // Booking made at 2023-12-24 08:00 (exactly 24 hours before)
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-24 08:00:00", formatter);
        
        // Execute and verify
        double result = trip.calculateDiscountedTripPrice(customer, bookingTime);
        assertEquals(80.0, result, 0.001);
    }
    
    @Test
    public void testCase4_NoDiscountWithoutMembership() {
        // Setup: Trip T900 (price: 200.0, departure: 2023-12-26 12:00)
        trip.setId("T900");
        trip.setPrice(200.0);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-26 12:00:00", formatter));
        
        // Customer has no membership
        customer.setMembership(null);
        
        // Booking made 48 hours before (2023-12-24 12:00)
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-24 12:00:00", formatter);
        
        // Execute and verify
        double result = trip.calculateDiscountedTripPrice(customer, bookingTime);
        assertEquals(200.0, result, 0.001);
    }
    
    @Test
    public void testCase5_DiscountAppliesOnlyToEligibleMembershipType() {
        // Setup: Trip T950 (price: 150.0)
        trip.setId("T950");
        trip.setPrice(150.0);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-26 12:00:00", formatter));
        
        // Customer has membership with CASHBACK only (no DISCOUNTS)
        Membership membership = new Membership();
        Reward reward = new Reward();
        reward.setRewardType("CASHBACK");
        membership.setReward(reward);
        customer.setMembership(membership);
        
        // Booking made 30 hours before
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 06:00:00", formatter);
        
        // Execute and verify
        double result = trip.calculateDiscountedTripPrice(customer, bookingTime);
        assertEquals(150.0, result, 0.001);
    }
}