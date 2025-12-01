import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CR4Test {
    
    private OnlineRideshareSystem system;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        system = new OnlineRideshareSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_pointsCalculationWithMultipleBookings() {
        // Setup: Customer C5 has membership with POINTS award
        Customer customer = new Customer();
        Membership membership = new Membership();
        membership.setPackageType("points");
        customer.setMembership(membership);
        
        // Create Booking1 (seats:2) for trip (price: 200.0, departure: 2023-12-25 12:00)
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(2);
        booking1.setBookingTime(LocalDateTime.parse("2023-12-01 10:00:00", formatter));
        customer.addBooking(booking1);
        
        // Create Booking2 (seats:3) for trip (price: 100.0, departure: 2023-12-26 12:00)
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(3);
        booking2.setBookingTime(LocalDateTime.parse("2023-12-15 10:00:00", formatter));
        customer.addBooking(booking2);
        
        // Execute: Compute reward points for Customer C5 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints(customer, 12, 2023);
        
        // Verify: Expected Output: 25. (2+3)*5=25
        assertEquals(25, result);
    }
    
    @Test
    public void testCase2_zeroPointsWithExpiredBookings() {
        // Setup: Customer C6 has membership with POINTS award
        Customer customer = new Customer();
        Membership membership = new Membership();
        membership.setPackageType("points");
        customer.setMembership(membership);
        
        // C6 create Booking3 (seats:4) for trip (price: 100.0, departure: 2024-12-26 12:00)
        Booking booking = new Booking();
        booking.setNumberOfSeats(4);
        booking.setBookingTime(LocalDateTime.parse("2023-11-01 10:00:00", formatter)); // Not in target month
        customer.addBooking(booking);
        
        // Execute: Compute reward points for Customer C6 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints(customer, 12, 2023);
        
        // Verify: Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_partialMonthInclusion() {
        // Setup: Customer C7 has membership with POINTS award
        Customer customer = new Customer();
        Membership membership = new Membership();
        membership.setPackageType("points");
        customer.setMembership(membership);
        
        // C7 create Booking1: 2023-11-30 10:00 (seats:2) - not in target month
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(2);
        booking1.setBookingTime(LocalDateTime.parse("2023-11-30 10:00:00", formatter));
        customer.addBooking(booking1);
        
        // C7 create Booking2: 2023-12-01 10:00 (seats:3) - in target month
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(3);
        booking2.setBookingTime(LocalDateTime.parse("2023-12-01 10:00:00", formatter));
        customer.addBooking(booking2);
        
        // Execute: Compute reward points for Customer C7 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints(customer, 12, 2023);
        
        // Verify: Expected Output: 15. 3*5=15
        assertEquals(15, result);
    }
    
    @Test
    public void testCase4_multipleSeatsEdgeCase() {
        // Setup: Customer C8 has membership with POINTS award
        Customer customer = new Customer();
        Membership membership = new Membership();
        membership.setPackageType("points");
        customer.setMembership(membership);
        
        // C8 create Booking: 2023-12-10 10:00 (seats:2) for trip (price: 200.0, departure: 2024-03-25 12:00)
        Booking booking = new Booking();
        booking.setNumberOfSeats(2);
        booking.setBookingTime(LocalDateTime.parse("2023-12-10 10:00:00", formatter));
        customer.addBooking(booking);
        
        // Execute: Compute reward points for Customer C8 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints(customer, 12, 2023);
        
        // Verify: Expected Output: 10. 2*5=10
        assertEquals(10, result);
    }
    
    @Test
    public void testCase5_largeSeatQuantity() {
        // Setup for Customer C8
        Customer customer8 = new Customer();
        Membership membership8 = new Membership();
        membership8.setPackageType("points");
        customer8.setMembership(membership8);
        
        // C8 create Booking: 2024-01-10 10:00 (seats:50)
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(50);
        booking1.setBookingTime(LocalDateTime.parse("2024-01-10 10:00:00", formatter));
        customer8.addBooking(booking1);
        
        // C8 create Booking: 2024-01-15 10:00 (seats:50)
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(50);
        booking2.setBookingTime(LocalDateTime.parse("2024-01-15 10:00:00", formatter));
        customer8.addBooking(booking2);
        
        // Setup for Customer C9
        Customer customer9 = new Customer();
        Membership membership9 = new Membership();
        membership9.setPackageType("points");
        customer9.setMembership(membership9);
        
        // C9 create Booking: 2024-01-10 10:00 (seats:50)
        Booking booking3 = new Booking();
        booking3.setNumberOfSeats(50);
        booking3.setBookingTime(LocalDateTime.parse("2024-01-10 10:00:00", formatter));
        customer9.addBooking(booking3);
        
        // Execute: Compute reward points for both customers (current Month: 2024-01)
        int result8 = system.computeMonthlyRewardPoints(customer8, 1, 2024);
        int result9 = system.computeMonthlyRewardPoints(customer9, 1, 2024);
        
        // Verify: Expected Output: C8 reward points: 500. C9 reward points: 250.
        assertEquals(500, result8);
        assertEquals(250, result9);
    }
}