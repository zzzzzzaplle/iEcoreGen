import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Trip tripT789;
    private Trip tripT999;
    private Trip tripT800;
    private Trip tripT900;
    private Trip tripT950;
    private Customer customerC3;
    private Customer customerC4;
    private Customer customerNoMembership;
    private Customer customerCashbackOnly;
    private Booking booking1;
    private Booking booking2;
    private Booking booking3;
    private Booking booking4;
    private Booking booking5;
    
    @Before
    public void setUp() {
        // Create trips
        Driver driver1 = new Driver("D1", "driver1@test.com", "1234567890");
        tripT789 = new DirectTrip("T789", driver1, "Station A", "Station B", 
            4, LocalDateTime.of(2023, 12, 25, 8, 0), 
            LocalDateTime.of(2023, 12, 25, 10, 0), 100.0);
        
        tripT999 = new DirectTrip("T999", driver1, "Station C", "Station D", 
            4, LocalDateTime.of(2023, 12, 25, 12, 0), 
            LocalDateTime.of(2023, 12, 25, 14, 0), 200.0);
        
        tripT800 = new DirectTrip("T800", driver1, "Station E", "Station F", 
            4, LocalDateTime.of(2023, 12, 25, 8, 0), 
            LocalDateTime.of(2023, 12, 25, 10, 0), 100.0);
        
        tripT900 = new DirectTrip("T900", driver1, "Station G", "Station H", 
            4, LocalDateTime.of(2023, 12, 26, 12, 0), 
            LocalDateTime.of(2023, 12, 26, 14, 0), 200.0);
        
        tripT950 = new DirectTrip("T950", driver1, "Station I", "Station J", 
            4, LocalDateTime.of(2023, 12, 26, 12, 0), 
            LocalDateTime.of(2023, 12, 26, 14, 0), 150.0);
        
        // Create customers with different membership configurations
        customerC3 = new Customer("C3", "customer3@test.com", "1111111111");
        Membership discountMembership = new Membership(true, RewardType.DISCOUNT);
        customerC3.setMembership(discountMembership);
        
        customerC4 = new Customer("C4", "customer4@test.com", "2222222222");
        Membership discountMembership2 = new Membership(true, RewardType.DISCOUNT);
        customerC4.setMembership(discountMembership2);
        
        customerNoMembership = new Customer("C5", "customer5@test.com", "3333333333");
        
        customerCashbackOnly = new Customer("C6", "customer6@test.com", "4444444444");
        Membership cashbackMembership = new Membership(true, RewardType.CASHBACK);
        customerCashbackOnly.setMembership(cashbackMembership);
    }
    
    @Test
    public void testCase1_20PercentDiscountAppliedForEarlyBooking() {
        // Setup: Booking made 25 hours before departure
        LocalDateTime bookingTime = LocalDateTime.of(2023, 12, 24, 7, 0); // 25 hours before
        booking1 = new Booking("B1", customerC3, tripT789, 1, bookingTime);
        
        // Calculate discounted price
        double result = ORSService.calculateDiscountedTripPrice(booking1);
        
        // Verify 20% discount is applied (100.0 * 0.8 = 80.0)
        assertEquals(80.0, result, 0.001);
    }
    
    @Test
    public void testCase2_DiscountDeniedForLateBooking() {
        // Setup: Booking made 1.5 hours before departure
        LocalDateTime bookingTime = LocalDateTime.of(2023, 12, 25, 10, 30); // 1.5 hours before
        booking2 = new Booking("B2", customerC4, tripT999, 1, bookingTime);
        
        // Calculate discounted price
        double result = ORSService.calculateDiscountedTripPrice(booking2);
        
        // Verify no discount applied (original price 200.0)
        assertEquals(200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_Exact24HourBoundaryForDiscount() {
        // Setup: Booking made exactly 24 hours before departure
        LocalDateTime bookingTime = LocalDateTime.of(2023, 12, 24, 8, 0); // exactly 24 hours before
        booking3 = new Booking("B3", customerC3, tripT800, 1, bookingTime);
        
        // Calculate discounted price
        double result = ORSService.calculateDiscountedTripPrice(booking3);
        
        // Verify 20% discount is applied at boundary (100.0 * 0.8 = 80.0)
        assertEquals(80.0, result, 0.001);
    }
    
    @Test
    public void testCase4_NoDiscountWithoutMembership() {
        // Setup: Customer has no membership, booking made 48 hours before
        LocalDateTime bookingTime = LocalDateTime.of(2023, 12, 24, 12, 0); // 48 hours before
        booking4 = new Booking("B4", customerNoMembership, tripT900, 1, bookingTime);
        
        // Calculate discounted price
        double result = ORSService.calculateDiscountedTripPrice(booking4);
        
        // Verify no discount applied (original price 200.0)
        assertEquals(200.0, result, 0.001);
    }
    
    @Test
    public void testCase5_DiscountAppliesOnlyToEligibleMembershipType() {
        // Setup: Customer has CASHBACK membership only, booking made 30 hours before
        LocalDateTime bookingTime = LocalDateTime.of(2023, 12, 25, 6, 0); // 30 hours before
        booking5 = new Booking("B5", customerCashbackOnly, tripT950, 1, bookingTime);
        
        // Calculate discounted price
        double result = ORSService.calculateDiscountedTripPrice(booking5);
        
        // Verify no discount applied for CASHBACK membership (original price 150.0)
        assertEquals(150.0, result, 0.001);
    }
}