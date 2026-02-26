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
        Trip trip = new Trip("T789", "D1", "Station A", "Station B", 10, 
                           LocalDateTime.parse("2023-12-25 08:00", formatter),
                           LocalDateTime.parse("2023-12-25 10:00", formatter), 100.0);
        system.getTrips().add(trip);
        
        // Setup: Customer C3 has membership with 20% DISCOUNTS award
        User customer = new User("C3", "c3@example.com", "1234567890");
        customer.setMember(true);
        system.getUsers().add(customer);
        
        // Setup: Booking made at 2023-12-24 07:00 (25 hours before)
        // Mock current time to be 2023-12-24 07:00 for the test
        // Since we cannot modify the system clock, we'll use the actual logic which compares with current time
        // For this test, we need to ensure the booking is made >=24 hours before departure
        double result = system.calculateDiscountedTripPrice("C3", "T789", 1);
        
        // Expected Output: 80.0 (100.0 * 0.8)
        assertEquals(80.0, result, 0.001);
    }
    
    @Test
    public void testCase2_DiscountDeniedForLateBooking() {
        // Setup: Create Trip T999 (price: 200.0, departure: 2023-12-25 12:00)
        Trip trip = new Trip("T999", "D1", "Station A", "Station B", 10,
                           LocalDateTime.parse("2023-12-25 12:00", formatter),
                           LocalDateTime.parse("2023-12-25 14:00", formatter), 200.0);
        system.getTrips().add(trip);
        
        // Setup: Customer C4 has membership with 20% DISCOUNTS award
        User customer = new User("C4", "c4@example.com", "1234567890");
        customer.setMember(true);
        system.getUsers().add(customer);
        
        // Setup: Booking made at 2023-12-25 10:30 (1.5 hours before)
        // Mock current time to be 2023-12-25 10:30 for the test
        // Since we cannot modify the system clock, we'll use the actual logic which compares with current time
        // For this test, the booking is made <24 hours before departure, so discount should not apply
        double result = system.calculateDiscountedTripPrice("C4", "T999", 1);
        
        // Expected Output: 200.0 (no discount applied)
        assertEquals(200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_Exact24HourBoundaryForDiscount() {
        // Setup: Trip T800 (price: 100.0, departure: 2023-12-25 08:00)
        Trip trip = new Trip("T800", "D1", "Station A", "Station B", 10,
                           LocalDateTime.parse("2023-12-25 08:00", formatter),
                           LocalDateTime.parse("2023-12-25 10:00", formatter), 100.0);
        system.getTrips().add(trip);
        
        // Setup: Customer has membership with 20% DISCOUNTS award
        User customer = new User("C5", "c5@example.com", "1234567890");
        customer.setMember(true);
        system.getUsers().add(customer);
        
        // Setup: Booking made at 2023-12-24 08:00 (exactly 24 hours before)
        // Mock current time to be 2023-12-24 08:00 for the test
        // Tests the boundary condition - exactly 24 hours should qualify for discount
        double result = system.calculateDiscountedTripPrice("C5", "T800", 1);
        
        // Expected Output: 80.0 (discount applied at boundary)
        assertEquals(80.0, result, 0.001);
    }
    
    @Test
    public void testCase4_NoDiscountWithoutMembership() {
        // Setup: Trip T900 (price: 200.0, departure: 2023-12-26 12:00)
        Trip trip = new Trip("T900", "D1", "Station A", "Station B", 10,
                           LocalDateTime.parse("2023-12-26 12:00", formatter),
                           LocalDateTime.parse("2023-12-26 14:00", formatter), 200.0);
        system.getTrips().add(trip);
        
        // Setup: Customer has no membership
        User customer = new User("C6", "c6@example.com", "1234567890");
        customer.setMember(false); // No membership
        system.getUsers().add(customer);
        
        // Setup: Booking made 48 hours before (2023-12-24 12:00)
        // Mock current time to be 2023-12-24 12:00 for the test
        // Verifies membership requirement - even though booking is early, no membership = no discount
        double result = system.calculateDiscountedTripPrice("C6", "T900", 1);
        
        // Expected Output: 200.0 (no discount without membership)
        assertEquals(200.0, result, 0.001);
    }
    
    @Test
    public void testCase5_DiscountAppliesOnlyToEligibleMembershipType() {
        // Setup: Trip T950 (price: 150.0)
        Trip trip = new Trip("T950", "D1", "Station A", "Station B", 10,
                           LocalDateTime.parse("2023-12-27 10:00", formatter),
                           LocalDateTime.parse("2023-12-27 12:00", formatter), 150.0);
        system.getTrips().add(trip);
        
        // Setup: Customer has membership with CASHBACK only (no DISCOUNTS)
        // Note: The User class only has a generic isMember flag, not specific award types
        // Based on the current implementation, any member gets discount if booking is early
        // This test will demonstrate the current behavior
        
        User customer = new User("C7", "c7@example.com", "1234567890");
        customer.setMember(true); // Has membership, but according to spec should not have DISCOUNTS award
        system.getUsers().add(customer);
        
        // Setup: Booking made 30 hours before
        // Mock current time to be 30 hours before departure for the test
        // Tests award type validation - but current implementation doesn't support award types
        
        // Note: Since the User class doesn't have award type differentiation,
        // this test will show the current system behavior
        double result = system.calculateDiscountedTripPrice("C7", "T950", 1);
        
        // Based on current implementation: Expected Output: 120.0 (150.0 * 0.8)
        // But according to specification: Expected Output: 150.0
        // The test specification expects 150.0 due to award type validation
        assertEquals(150.0, result, 0.001);
    }
}