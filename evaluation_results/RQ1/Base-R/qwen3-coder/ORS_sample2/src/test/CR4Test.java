import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private OnlineRideshareSystem system;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        system = new OnlineRideshareSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_PointsCalculationWithMultipleBookings() {
        // Setup: Customer C5 has membership with POINTS award
        Customer customer = new Customer();
        Membership membership = new Membership();
        membership.setRewardType(RewardType.POINTS);
        customer.setMembership(membership);
        
        // Create bookings for December 2023
        List<Booking> bookings = new ArrayList<>();
        
        // Booking1: seats:2, departure: 2023-12-25 12:00
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(2);
        booking1.setBookingTime(LocalDateTime.parse("2023-12-01 10:00:00", formatter));
        bookings.add(booking1);
        
        // Booking2: seats:3, departure: 2023-12-26 12:00
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(3);
        booking2.setBookingTime(LocalDateTime.parse("2023-12-02 10:00:00", formatter));
        bookings.add(booking2);
        
        customer.setBookings(bookings);
        
        // Execute: Compute reward points for December 2023
        int result = system.computeMonthlyRewardPoints(customer, 2023, 12);
        
        // Verify: Expected output: 25 (2+3)*5=25
        assertEquals(25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() {
        // Setup: Customer C6 has membership with POINTS award
        Customer customer = new Customer();
        Membership membership = new Membership();
        membership.setRewardType(RewardType.POINTS);
        customer.setMembership(membership);
        
        // Create booking for December 2024 (future month)
        List<Booking> bookings = new ArrayList<>();
        
        // Booking3: seats:4, departure: 2024-12-26 12:00
        Booking booking = new Booking();
        booking.setNumberOfSeats(4);
        booking.setBookingTime(LocalDateTime.parse("2024-12-01 10:00:00", formatter));
        bookings.add(booking);
        
        customer.setBookings(bookings);
        
        // Execute: Compute reward points for December 2023
        int result = system.computeMonthlyRewardPoints(customer, 2023, 12);
        
        // Verify: Expected output: 0 (booking is in different year)
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() {
        // Setup: Customer C7 has membership with POINTS award
        Customer customer = new Customer();
        Membership membership = new Membership();
        membership.setRewardType(RewardType.POINTS);
        customer.setMembership(membership);
        
        // Create bookings - one in November, one in December
        List<Booking> bookings = new ArrayList<>();
        
        // Booking1: 2023-11-30 10:00 (November - should not count)
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(2);
        booking1.setBookingTime(LocalDateTime.parse("2023-11-30 10:00:00", formatter));
        bookings.add(booking1);
        
        // Booking2: 2023-12-01 10:00 (December - should count)
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(3);
        booking2.setBookingTime(LocalDateTime.parse("2023-12-01 10:00:00", formatter));
        bookings.add(booking2);
        
        customer.setBookings(bookings);
        
        // Execute: Compute reward points for December 2023
        int result = system.computeMonthlyRewardPoints(customer, 2023, 12);
        
        // Verify: Expected output: 15 (3*5=15)
        assertEquals(15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() {
        // Setup: Customer C8 has membership with POINTS award
        Customer customer = new Customer();
        Membership membership = new Membership();
        membership.setRewardType(RewardType.POINTS);
        customer.setMembership(membership);
        
        // Create booking for December 2023
        List<Booking> bookings = new ArrayList<>();
        
        // Booking: 2023-12-10 10:00, seats:2
        Booking booking = new Booking();
        booking.setNumberOfSeats(2);
        booking.setBookingTime(LocalDateTime.parse("2023-12-10 10:00:00", formatter));
        bookings.add(booking);
        
        customer.setBookings(bookings);
        
        // Execute: Compute reward points for December 2023
        int result = system.computeMonthlyRewardPoints(customer, 2023, 12);
        
        // Verify: Expected output: 10 (2*5=10)
        assertEquals(10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() {
        // Setup Customer C8
        Customer customer8 = new Customer();
        Membership membership8 = new Membership();
        membership8.setRewardType(RewardType.POINTS);
        customer8.setMembership(membership8);
        
        // Create bookings for C8 in January 2024
        List<Booking> bookings8 = new ArrayList<>();
        
        // Booking1: 2024-01-10 10:00, seats:50
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(50);
        booking1.setBookingTime(LocalDateTime.parse("2024-01-10 10:00:00", formatter));
        bookings8.add(booking1);
        
        // Booking2: 2024-01-15 10:00, seats:50
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(50);
        booking2.setBookingTime(LocalDateTime.parse("2024-01-15 10:00:00", formatter));
        bookings8.add(booking2);
        
        customer8.setBookings(bookings8);
        
        // Setup Customer C9
        Customer customer9 = new Customer();
        Membership membership9 = new Membership();
        membership9.setRewardType(RewardType.POINTS);
        customer9.setMembership(membership9);
        
        // Create booking for C9 in January 2024
        List<Booking> bookings9 = new ArrayList<>();
        
        // Booking: 2024-01-10 10:00, seats:50
        Booking booking3 = new Booking();
        booking3.setNumberOfSeats(50);
        booking3.setBookingTime(LocalDateTime.parse("2024-01-10 10:00:00", formatter));
        bookings9.add(booking3);
        
        customer9.setBookings(bookings9);
        
        // Execute: Compute reward points for January 2024
        int resultC8 = system.computeMonthlyRewardPoints(customer8, 2024, 1);
        int resultC9 = system.computeMonthlyRewardPoints(customer9, 2024, 1);
        
        // Verify: Expected output: C8 reward points: 500, C9 reward points: 250
        assertEquals(500, resultC8);
        assertEquals(250, resultC9);
    }
}