import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class CR2Test {
    
    private Trip trip;
    private Customer customer;
    private Booking booking;
    private Membership membership;
    
    @Before
    public void setUp() {
        // Common setup for test objects
        trip = new Trip();
        customer = new Customer();
        booking = new Booking();
        membership = new Membership();
    }
    
    @Test
    public void testCase1_20PercentDiscountAppliedForEarlyBooking() {
        // Setup: Create Trip T789 (price: 100.0, departure: 2023-12-25 08:00)
        trip.setId("T789");
        trip.setPrice(100.0);
        trip.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 8, 0));
        
        // Setup: Customer C3 has membership with 20% DISCOUNTS award
        membership.setRewardType(Membership.RewardType.DISCOUNT);
        customer.setMembership(membership);
        
        // Setup: Booking made at 2023-12-24 07:00 (25 hours before)
        booking.setCustomer(customer);
        booking.setTrip(trip);
        booking.setBookingDateTime(LocalDateTime.of(2023, 12, 24, 7, 0));
        
        // Execute: Calculate discounted price
        double result = ORSService.calculateDiscountedTripPrice(booking);
        
        // Verify: Expected output is 80.0
        assertEquals(80.0, result, 0.001);
    }
    
    @Test
    public void testCase2_DiscountDeniedForLateBooking() {
        // Setup: Create Trip T999 (price: 200.0, departure: 2023-12-25 12:00)
        trip.setId("T999");
        trip.setPrice(200.0);
        trip.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 12, 0));
        
        // Setup: Customer C4 has membership with 20% DISCOUNTS award
        membership.setRewardType(Membership.RewardType.DISCOUNT);
        customer.setMembership(membership);
        
        // Setup: Booking made at 2023-12-25 10:30 (1.5 hours before)
        booking.setCustomer(customer);
        booking.setTrip(trip);
        booking.setBookingDateTime(LocalDateTime.of(2023, 12, 25, 10, 30));
        
        // Execute: Calculate discounted price
        double result = ORSService.calculateDiscountedTripPrice(booking);
        
        // Verify: Expected output is 200.0
        assertEquals(200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_Exact24HourBoundaryForDiscount() {
        // Setup: Trip T800 (price: 100.0, departure: 2023-12-25 08:00)
        trip.setId("T800");
        trip.setPrice(100.0);
        trip.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 8, 0));
        
        // Setup: Customer has membership with 20% DISCOUNTS award
        membership.setRewardType(Membership.RewardType.DISCOUNT);
        customer.setMembership(membership);
        
        // Setup: Booking made at 2023-12-24 08:00 (exactly 24 hours before)
        booking.setCustomer(customer);
        booking.setTrip(trip);
        booking.setBookingDateTime(LocalDateTime.of(2023, 12, 24, 8, 0));
        
        // Execute: Calculate discounted price
        double result = ORSService.calculateDiscountedTripPrice(booking);
        
        // Verify: Expected output is 80.0 (tests boundary condition)
        assertEquals(80.0, result, 0.001);
    }
    
    @Test
    public void testCase4_NoDiscountWithoutMembership() {
        // Setup: Trip T900 (price: 200.0, departure: 2023-12-26 12:00)
        trip.setId("T900");
        trip.setPrice(200.0);
        trip.setDepartureDateTime(LocalDateTime.of(2023, 12, 26, 12, 0));
        
        // Setup: Customer has no membership
        customer.setMembership(null);
        
        // Setup: Booking made 48 hours before (2023-12-24 12:00)
        booking.setCustomer(customer);
        booking.setTrip(trip);
        booking.setBookingDateTime(LocalDateTime.of(2023, 12, 24, 12, 0));
        
        // Execute: Calculate discounted price
        double result = ORSService.calculateDiscountedTripPrice(booking);
        
        // Verify: Expected output is 200.0 (verifies membership requirement)
        assertEquals(200.0, result, 0.001);
    }
    
    @Test
    public void testCase5_DiscountAppliesOnlyToEligibleMembershipType() {
        // Setup: Trip T950 (price: 150.0)
        trip.setId("T950");
        trip.setPrice(150.0);
        trip.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 8, 0));
        
        // Setup: Customer has membership with CASHBACK only (no DISCOUNTS)
        membership.setRewardType(Membership.RewardType.CASHBACK);
        customer.setMembership(membership);
        
        // Setup: Booking made 30 hours before
        booking.setCustomer(customer);
        booking.setTrip(trip);
        booking.setBookingDateTime(LocalDateTime.of(2023, 12, 23, 22, 0));
        
        // Execute: Calculate discounted price
        double result = ORSService.calculateDiscountedTripPrice(booking);
        
        // Verify: Expected output is 150.0 (tests award type validation)
        assertEquals(150.0, result, 0.001);
    }
}