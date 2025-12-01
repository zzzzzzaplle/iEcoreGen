import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.YearMonth;
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
        trip.setDepartureTime(LocalDateTime.parse("2023-12-25 08:00", formatter));
        
        // Setup: Customer C3 has membership with 20% DISCOUNTS award
        User customer = new User();
        customer.setUserId("C3");
        
        Membership membership = new Membership();
        membership.setRewardType(RewardType.DISCOUNT);
        customer.setMembership(membership);
        
        // Setup: Booking made at 2023-12-24 07:00 (25 hours before)
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setTrip(trip);
        booking.setBookingTime(LocalDateTime.parse("2023-12-24 07:00", formatter));
        
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
        trip.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00", formatter));
        
        // Setup: Customer C4 has membership with 20% DISCOUNTS award
        User customer = new User();
        customer.setUserId("C4");
        
        Membership membership = new Membership();
        membership.setRewardType(RewardType.DISCOUNT);
        customer.setMembership(membership);
        
        // Setup: Booking made at 2023-12-25 10:30 (1.5 hours before)
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setTrip(trip);
        booking.setBookingTime(LocalDateTime.parse("2023-12-25 10:30", formatter));
        
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
        trip.setDepartureTime(LocalDateTime.parse("2023-12-25 08:00", formatter));
        
        // Setup: Customer has membership with 20% DISCOUNTS award
        User customer = new User();
        
        Membership membership = new Membership();
        membership.setRewardType(RewardType.DISCOUNT);
        customer.setMembership(membership);
        
        // Setup: Booking made at 2023-12-24 08:00 (exactly 24 hours before)
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setTrip(trip);
        booking.setBookingTime(LocalDateTime.parse("2023-12-24 08:00", formatter));
        
        // Calculate discounted price
        double result = system.calculateDiscountedTripPrice(booking);
        
        // Expected Output: 80.0
        assertEquals(80.0, result, 0.001);
    }
    
    @Test
    public void testCase4_NoDiscountWithoutMembership() {
        // Setup: Trip T900 (price: 200.0, departure: 2023-12-26 12:00)
        Trip trip = new Trip();
        trip.setTripId("T900");
        trip.setPrice(200.0);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-26 12:00", formatter));
        
        // Setup: Customer has no membership
        User customer = new User();
        
        // Setup: Booking made 48 hours before (2023-12-24 12:00)
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setTrip(trip);
        booking.setBookingTime(LocalDateTime.parse("2023-12-24 12:00", formatter));
        
        // Calculate discounted price
        double result = system.calculateDiscountedTripPrice(booking);
        
        // Expected Output: 200.0
        assertEquals(200.0, result, 0.001);
    }
    
    @Test
    public void testCase5_DiscountAppliesOnlyToEligibleMembershipType() {
        // Setup: Trip T950 (price: 150.0)
        Trip trip = new Trip();
        trip.setTripId("T950");
        trip.setPrice(150.0);
        trip.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00", formatter));
        
        // Setup: Customer has membership with CASHBACK only (no DISCOUNTS)
        User customer = new User();
        
        Membership membership = new Membership();
        membership.setRewardType(RewardType.CASHBACK);
        customer.setMembership(membership);
        
        // Setup: Booking made 30 hours before
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setTrip(trip);
        booking.setBookingTime(LocalDateTime.parse("2023-12-24 06:00", formatter));
        
        // Calculate discounted price
        double result = system.calculateDiscountedTripPrice(booking);
        
        // Expected Output: 150.0
        assertEquals(150.0, result, 0.001);
    }
}