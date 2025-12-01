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
        // Setup: Create Trip T789 (price: 100.0, departure: 2023-12-25 08:00)
        Trip trip = new Trip();
        trip.setTripId("T789");
        trip.setPrice(100.0);
        trip.setDepartureDateTime(LocalDateTime.parse("2023-12-25 08:00", formatter));
        
        // Setup: Customer C3 has membership with 20% DISCOUNTS award
        User customer = new User("C3", "c3@test.com", "1234567890");
        Membership membership = new Membership("M001", "DISCOUNTS", 0.2);
        customer.setMembership(membership);
        
        // Setup: Booking made at 2023-12-24 07:00 (25 hours before)
        LocalDateTime bookingDateTime = LocalDateTime.parse("2023-12-24 07:00", formatter);
        Booking booking = new Booking("B001", customer, trip, 1, bookingDateTime);
        
        // Calculate discounted price
        double result = system.calculateDiscountedTripPrice(booking);
        
        // Expected Output: 80.0
        assertEquals(80.0, result, 0.001);
    }
    
    @Test
    public void testCase2_DiscountDeniedForLateBooking() {
        // Setup: Create Trip T999 (price: 200.0, departure: 2023-12-25 12:00)
        Trip trip = new Trip();
        trip.setTripId("T999");
        trip.setPrice(200.0);
        trip.setDepartureDateTime(LocalDateTime.parse("2023-12-25 12:00", formatter));
        
        // Setup: Customer C4 has membership with 20% DISCOUNTS award
        User customer = new User("C4", "c4@test.com", "1234567891");
        Membership membership = new Membership("M002", "DISCOUNTS", 0.2);
        customer.setMembership(membership);
        
        // Setup: Booking made at 2023-12-25 10:30 (1.5 hours before)
        LocalDateTime bookingDateTime = LocalDateTime.parse("2023-12-25 10:30", formatter);
        Booking booking = new Booking("B002", customer, trip, 1, bookingDateTime);
        
        // Calculate discounted price
        double result = system.calculateDiscountedTripPrice(booking);
        
        // Expected Output: 200.0
        assertEquals(200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_Exact24HourBoundaryForDiscount() {
        // Setup: Trip T800 (price: 100.0, departure: 2023-12-25 08:00)
        Trip trip = new Trip();
        trip.setTripId("T800");
        trip.setPrice(100.0);
        trip.setDepartureDateTime(LocalDateTime.parse("2023-12-25 08:00", formatter));
        
        // Setup: Customer has membership with 20% DISCOUNTS award
        User customer = new User("C5", "c5@test.com", "1234567892");
        Membership membership = new Membership("M003", "DISCOUNTS", 0.2);
        customer.setMembership(membership);
        
        // Setup: Booking made at 2023-12-24 08:00 (exactly 24 hours before)
        LocalDateTime bookingDateTime = LocalDateTime.parse("2023-12-24 08:00", formatter);
        Booking booking = new Booking("B003", customer, trip, 1, bookingDateTime);
        
        // Calculate discounted price
        double result = system.calculateDiscountedTripPrice(booking);
        
        // Expected Output: 80.0 (discount should apply at exact 24-hour boundary)
        assertEquals(80.0, result, 0.001);
    }
    
    @Test
    public void testCase4_NoDiscountWithoutMembership() {
        // Setup: Trip T900 (price: 200.0, departure: 2023-12-26 12:00)
        Trip trip = new Trip();
        trip.setTripId("T900");
        trip.setPrice(200.0);
        trip.setDepartureDateTime(LocalDateTime.parse("2023-12-26 12:00", formatter));
        
        // Setup: Customer has no membership
        User customer = new User("C6", "c6@test.com", "1234567893");
        // No membership set
        
        // Setup: Booking made 48 hours before (2023-12-24 12:00)
        LocalDateTime bookingDateTime = LocalDateTime.parse("2023-12-24 12:00", formatter);
        Booking booking = new Booking("B004", customer, trip, 1, bookingDateTime);
        
        // Calculate discounted price
        double result = system.calculateDiscountedTripPrice(booking);
        
        // Expected Output: 200.0 (no discount without membership)
        assertEquals(200.0, result, 0.001);
    }
    
    @Test
    public void testCase5_DiscountAppliesOnlyToEligibleMembershipType() {
        // Setup: Trip T950 (price: 150.0)
        Trip trip = new Trip();
        trip.setTripId("T950");
        trip.setPrice(150.0);
        trip.setDepartureDateTime(LocalDateTime.parse("2023-12-27 10:00", formatter));
        
        // Setup: Customer has membership with CASHBACK only (no DISCOUNTS)
        User customer = new User("C7", "c7@test.com", "1234567894");
        Membership membership = new Membership("M004", "CASHBACK", 0.2);
        customer.setMembership(membership);
        
        // Setup: Booking made 30 hours before
        LocalDateTime bookingDateTime = LocalDateTime.parse("2023-12-26 04:00", formatter);
        Booking booking = new Booking("B005", customer, trip, 1, bookingDateTime);
        
        // Calculate discounted price
        double result = system.calculateDiscountedTripPrice(booking);
        
        // Expected Output: 150.0 (no discount for CASHBACK reward type)
        assertEquals(150.0, result, 0.001);
    }
}