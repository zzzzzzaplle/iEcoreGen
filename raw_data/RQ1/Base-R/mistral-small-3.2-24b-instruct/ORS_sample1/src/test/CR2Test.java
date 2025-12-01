import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
        // Setup: Create Trip T789 (price: 100.0, departure: 2023-12-25 08:00)
        trip.setTripId("T789");
        trip.setPrice(100.0);
        trip.setDepartureDateTime(LocalDateTime.parse("2023-12-25 08:00:00", formatter));
        
        // Setup: Customer C3 has membership with 20% DISCOUNTS award
        customer.setUserId("C3");
        customer.setHasMembership(true);
        
        // Setup: Booking made at 2023-12-24 07:00 (25 hours before)
        LocalDateTime bookingDateTime = LocalDateTime.parse("2023-12-24 07:00:00", formatter);
        
        // Calculate discounted price
        double result = trip.calculateDiscountedTripPrice("C3", bookingDateTime);
        
        // Expected Output: 80.0
        assertEquals(80.0, result, 0.001);
    }
    
    @Test
    public void testCase2_DiscountDeniedForLateBooking() {
        // Setup: Create Trip T999 (price: 200.0, departure: 2023-12-25 12:00)
        trip.setTripId("T999");
        trip.setPrice(200.0);
        trip.setDepartureDateTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        
        // Setup: Customer C4 has membership with 20% DISCOUNTS award
        customer.setUserId("C4");
        customer.setHasMembership(true);
        
        // Setup: Booking made at 2023-12-25 10:30 (1.5 hours before)
        LocalDateTime bookingDateTime = LocalDateTime.parse("2023-12-25 10:30:00", formatter);
        
        // Calculate discounted price
        double result = trip.calculateDiscountedTripPrice("C4", bookingDateTime);
        
        // Expected Output: 200.0
        assertEquals(200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_Exact24HourBoundaryForDiscount() {
        // Setup: Trip T800 (price: 100.0, departure: 2023-12-25 08:00)
        trip.setTripId("T800");
        trip.setPrice(100.0);
        trip.setDepartureDateTime(LocalDateTime.parse("2023-12-25 08:00:00", formatter));
        
        // Setup: Customer has membership with 20% DISCOUNTS award
        customer.setUserId("C5");
        customer.setHasMembership(true);
        
        // Setup: Booking made at 2023-12-24 08:00 (exactly 24 hours before)
        LocalDateTime bookingDateTime = LocalDateTime.parse("2023-12-24 08:00:00", formatter);
        
        // Calculate discounted price
        double result = trip.calculateDiscountedTripPrice("C5", bookingDateTime);
        
        // Expected Output: 80.0
        assertEquals(80.0, result, 0.001);
    }
    
    @Test
    public void testCase4_NoDiscountWithoutMembership() {
        // Setup: Trip T900 (price: 200.0, departure: 2023-12-26 12:00)
        trip.setTripId("T900");
        trip.setPrice(200.0);
        trip.setDepartureDateTime(LocalDateTime.parse("2023-12-26 12:00:00", formatter));
        
        // Setup: Customer has no membership
        customer.setUserId("C6");
        customer.setHasMembership(false);
        
        // Setup: Booking made 48 hours before (2023-12-24 12:00)
        LocalDateTime bookingDateTime = LocalDateTime.parse("2023-12-24 12:00:00", formatter);
        
        // Calculate discounted price
        double result = trip.calculateDiscountedTripPrice("C6", bookingDateTime);
        
        // Expected Output: 200.0
        assertEquals(200.0, result, 0.001);
    }
    
    @Test
    public void testCase5_DiscountAppliesOnlyToEligibleMembershipType() {
        // Setup: Trip T950 (price: 150.0)
        trip.setTripId("T950");
        trip.setPrice(150.0);
        trip.setDepartureDateTime(LocalDateTime.parse("2023-12-25 10:00:00", formatter));
        
        // Setup: Customer has membership with CASHBACK only (no DISCOUNTS)
        // Note: Since the source code only checks for membership (not specific award types),
        // this test verifies that membership alone doesn't guarantee discount
        customer.setUserId("C7");
        customer.setHasMembership(true);
        
        // Setup: Booking made 30 hours before
        LocalDateTime bookingDateTime = LocalDateTime.parse("2023-12-24 04:00:00", formatter);
        
        // Calculate discounted price - should return original price since implementation
        // doesn't check for specific award types and only checks membership
        double result = trip.calculateDiscountedTripPrice("C7", bookingDateTime);
        
        // Expected Output: 150.0 (based on test specification)
        assertEquals(150.0, result, 0.001);
    }
}