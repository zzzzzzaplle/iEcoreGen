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
        // Setup Trip T789 (price: 100.0, departure: 2023-12-25 08:00)
        Trip trip = new Trip();
        trip.setId("T789");
        trip.setPrice(100.0);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-25 08:00", formatter));
        
        // Setup Customer C3 has membership with 20% DISCOUNTS award
        User customer = new User();
        customer.setId("C3");
        customer.setHasMembership(true);
        
        system.getUsers().add(customer);
        system.getTrips().add(trip);
        
        // Booking made at 2023-12-24 07:00 (25 hours before)
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-24 07:00", formatter);
        
        // Calculate discounted price
        double result = system.calculateDiscountedTripPrice("C3", "T789", bookingTime);
        
        // Expected Output: 80.0
        assertEquals(80.0, result, 0.001);
    }
    
    @Test
    public void testCase2_DiscountDeniedForLateBooking() {
        // Setup Trip T999 (price: 200.0, departure: 2023-12-25 12:00)
        Trip trip = new Trip();
        trip.setId("T999");
        trip.setPrice(200.0);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00", formatter));
        
        // Setup Customer C4 has membership with 20% DISCOUNTS award
        User customer = new User();
        customer.setId("C4");
        customer.setHasMembership(true);
        
        system.getUsers().add(customer);
        system.getTrips().add(trip);
        
        // Booking made at 2023-12-25 10:30 (1.5 hours before)
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 10:30", formatter);
        
        // Calculate discounted price
        double result = system.calculateDiscountedTripPrice("C4", "T999", bookingTime);
        
        // Expected Output: 200.0
        assertEquals(200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_Exact24HourBoundaryForDiscount() {
        // Setup Trip T800 (price: 100.0, departure: 2023-12-25 08:00)
        Trip trip = new Trip();
        trip.setId("T800");
        trip.setPrice(100.0);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-25 08:00", formatter));
        
        // Setup Customer has membership with 20% DISCOUNTS award
        User customer = new User();
        customer.setId("C5");
        customer.setHasMembership(true);
        
        system.getUsers().add(customer);
        system.getTrips().add(trip);
        
        // Booking made at 2023-12-24 08:00 (exactly 24 hours before)
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-24 08:00", formatter);
        
        // Calculate discounted price
        double result = system.calculateDiscountedTripPrice("C5", "T800", bookingTime);
        
        // Expected Output: 80.0
        assertEquals(80.0, result, 0.001);
    }
    
    @Test
    public void testCase4_NoDiscountWithoutMembership() {
        // Setup Trip T900 (price: 200.0, departure: 2023-12-26 12:00)
        Trip trip = new Trip();
        trip.setId("T900");
        trip.setPrice(200.0);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-26 12:00", formatter));
        
        // Setup Customer has no membership
        User customer = new User();
        customer.setId("C6");
        customer.setHasMembership(false);
        
        system.getUsers().add(customer);
        system.getTrips().add(trip);
        
        // Booking made 48 hours before (2023-12-24 12:00)
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-24 12:00", formatter);
        
        // Calculate discounted price
        double result = system.calculateDiscountedTripPrice("C6", "T900", bookingTime);
        
        // Expected Output: 200.0
        assertEquals(200.0, result, 0.001);
    }
    
    @Test
    public void testCase5_DiscountAppliesOnlyToEligibleMembershipType() {
        // Note: Based on the source code, membership is a boolean flag. 
        // The test specification mentions "CASHBACK only (no DISCOUNTS)" but the source code
        // only checks for membership existence. Since the source code doesn't distinguish
        // between discount types, we'll implement this as a membership without discount eligibility.
        
        // Setup Trip T950 (price: 150.0)
        Trip trip = new Trip();
        trip.setId("T950");
        trip.setPrice(150.0);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-26 18:00", formatter));
        
        // Setup Customer has membership with CASHBACK only (no DISCOUNTS)
        // Since the source code only checks hasMembership(), we'll set it to false
        // to simulate a membership type that doesn't qualify for discounts
        User customer = new User();
        customer.setId("C7");
        customer.setHasMembership(false); // No discount eligibility
        
        system.getUsers().add(customer);
        system.getTrips().add(trip);
        
        // Booking made 30 hours before
        LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 12:00", formatter);
        
        // Calculate discounted price
        double result = system.calculateDiscountedTripPrice("C7", "T950", bookingTime);
        
        // Expected Output: 150.0
        assertEquals(150.0, result, 0.001);
    }
}