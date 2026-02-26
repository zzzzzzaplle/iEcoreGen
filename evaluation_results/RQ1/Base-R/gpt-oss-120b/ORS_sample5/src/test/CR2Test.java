import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.YearMonth;

public class CR2Test {
    
    private RideShareSystem system;
    private Customer customerWithDiscount;
    private Customer customerWithCashback;
    private Customer customerNoMembership;
    private Membership discountMembership;
    private Membership cashbackMembership;
    
    @Before
    public void setUp() {
        system = new RideShareSystem();
        
        // Setup membership types
        discountMembership = new Membership();
        discountMembership.setActive(true);
        discountMembership.setRewardType(RewardType.DISCOUNT);
        
        cashbackMembership = new Membership();
        cashbackMembership.setActive(true);
        cashbackMembership.setRewardType(RewardType.CASHBACK);
        
        // Setup customers
        customerWithDiscount = new Customer();
        customerWithDiscount.setId("C3");
        customerWithDiscount.setMembership(discountMembership);
        
        customerWithCashback = new Customer();
        customerWithCashback.setId("C5");
        customerWithCashback.setMembership(cashbackMembership);
        
        customerNoMembership = new Customer();
        customerNoMembership.setId("C6");
        customerNoMembership.setMembership(null);
        
        system.addUser(customerWithDiscount);
        system.addUser(customerWithCashback);
        system.addUser(customerNoMembership);
    }
    
    @Test
    public void testCase1_20PercentDiscountAppliedForEarlyBooking() {
        // Setup: Trip T789 (price: 100.0, departure: 2023-12-25 08:00)
        Trip trip = new Trip();
        trip.setId("T789");
        trip.setPricePerSeat(100.0);
        trip.setDepartureDateTime(RideShareSystem.parseDateTime("2023-12-25 08:00"));
        
        // Booking made at 2023-12-24 07:00 (25 hours before)
        Booking booking = new Booking();
        booking.setCustomer(customerWithDiscount);
        booking.setTrip(trip);
        booking.setSeatsBooked(1);
        booking.setBookingDateTime(RideShareSystem.parseDateTime("2023-12-24 07:00"));
        
        // Calculate discounted price
        double result = system.calculateDiscountedTripPrice(booking);
        
        // Expected Output: 80.0
        assertEquals(80.0, result, 0.001);
    }
    
    @Test
    public void testCase2_DiscountDeniedForLateBooking() {
        // Setup: Trip T999 (price: 200.0, departure: 2023-12-25 12:00)
        Trip trip = new Trip();
        trip.setId("T999");
        trip.setPricePerSeat(200.0);
        trip.setDepartureDateTime(RideShareSystem.parseDateTime("2023-12-25 12:00"));
        
        // Booking made at 2023-12-25 10:30 (1.5 hours before)
        Booking booking = new Booking();
        booking.setCustomer(customerWithDiscount);
        booking.setTrip(trip);
        booking.setSeatsBooked(1);
        booking.setBookingDateTime(RideShareSystem.parseDateTime("2023-12-25 10:30"));
        
        // Calculate discounted price
        double result = system.calculateDiscountedTripPrice(booking);
        
        // Expected Output: 200.0
        assertEquals(200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_Exact24HourBoundaryForDiscount() {
        // Setup: Trip T800 (price: 100.0, departure: 2023-12-25 08:00)
        Trip trip = new Trip();
        trip.setId("T800");
        trip.setPricePerSeat(100.0);
        trip.setDepartureDateTime(RideShareSystem.parseDateTime("2023-12-25 08:00"));
        
        // Booking made at 2023-12-24 08:00 (exactly 24 hours before)
        Booking booking = new Booking();
        booking.setCustomer(customerWithDiscount);
        booking.setTrip(trip);
        booking.setSeatsBooked(1);
        booking.setBookingDateTime(RideShareSystem.parseDateTime("2023-12-24 08:00"));
        
        // Calculate discounted price
        double result = system.calculateDiscountedTripPrice(booking);
        
        // Expected Output: 80.0
        assertEquals(80.0, result, 0.001);
    }
    
    @Test
    public void testCase4_NoDiscountWithoutMembership() {
        // Setup: Trip T900 (price: 200.0, departure: 2023-12-26 12:00)
        Trip trip = new Trip();
        trip.setId("T900");
        trip.setPricePerSeat(200.0);
        trip.setDepartureDateTime(RideShareSystem.parseDateTime("2023-12-26 12:00"));
        
        // Customer has no membership, booking made 48 hours before
        Booking booking = new Booking();
        booking.setCustomer(customerNoMembership);
        booking.setTrip(trip);
        booking.setSeatsBooked(1);
        booking.setBookingDateTime(RideShareSystem.parseDateTime("2023-12-24 12:00"));
        
        // Calculate discounted price
        double result = system.calculateDiscountedTripPrice(booking);
        
        // Expected Output: 200.0
        assertEquals(200.0, result, 0.001);
    }
    
    @Test
    public void testCase5_DiscountAppliesOnlyToEligibleMembershipType() {
        // Setup: Trip T950 (price: 150.0)
        Trip trip = new Trip();
        trip.setId("T950");
        trip.setPricePerSeat(150.0);
        trip.setDepartureDateTime(RideShareSystem.parseDateTime("2023-12-26 12:00"));
        
        // Customer has membership with CASHBACK only (no DISCOUNTS)
        // Booking made 30 hours before
        Booking booking = new Booking();
        booking.setCustomer(customerWithCashback);
        booking.setTrip(trip);
        booking.setSeatsBooked(1);
        booking.setBookingDateTime(RideShareSystem.parseDateTime("2023-12-25 06:00"));
        
        // Calculate discounted price
        double result = system.calculateDiscountedTripPrice(booking);
        
        // Expected Output: 150.0
        assertEquals(150.0, result, 0.001);
    }
}