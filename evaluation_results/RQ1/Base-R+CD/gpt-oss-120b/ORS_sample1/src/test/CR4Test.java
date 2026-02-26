import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Customer customer;
    private MembershipPackage membershipPackage;
    
    @Before
    public void setUp() {
        // Common setup for tests
        membershipPackage = new MembershipPackage();
        membershipPackage.setAwards(new Award[]{Award.POINTS});
    }
    
    @Test
    public void testCase1_PointsCalculationWithMultipleBookings() {
        // Setup
        customer = new Customer();
        customer.setMembershipPackage(membershipPackage);
        
        // Create bookings for December 2023
        Booking booking1 = createBooking(2, LocalDate.of(2023, 12, 25));
        Booking booking2 = createBooking(3, LocalDate.of(2023, 12, 26));
        
        customer.getBookings().add(booking1);
        customer.getBookings().add(booking2);
        
        // Execute
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("Total points should be (2+3)*5=25", 25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() {
        // Setup
        customer = new Customer();
        customer.setMembershipPackage(membershipPackage);
        
        // Create booking for December 2024 (different year)
        Booking booking3 = createBooking(4, LocalDate.of(2024, 12, 26));
        customer.getBookings().add(booking3);
        
        // Execute
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("No points should be earned for bookings in different month", 0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() {
        // Setup
        customer = new Customer();
        customer.setMembershipPackage(membershipPackage);
        
        // Create booking from November 2023 (should not be counted)
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(LocalDate.of(2023, 11, 30));
        
        // Create booking from December 2023 (should be counted)
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(3);
        booking2.setBookingDate(LocalDate.of(2023, 12, 1));
        
        customer.getBookings().add(booking1);
        customer.getBookings().add(booking2);
        
        // Execute
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("Only December bookings should be counted: 3*5=15", 15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() {
        // Setup
        customer = new Customer();
        customer.setMembershipPackage(membershipPackage);
        
        // Create booking with 2 seats in December 2023
        Booking booking = createBooking(2, LocalDate.of(2023, 12, 10));
        customer.getBookings().add(booking);
        
        // Execute
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("Points should be 2*5=10", 10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() {
        // Setup for Customer C8
        Customer customerC8 = new Customer();
        customerC8.setMembershipPackage(membershipPackage);
        
        // Create bookings for C8 in January 2024
        Booking bookingC8_1 = createBooking(50, LocalDate.of(2024, 1, 10));
        Booking bookingC8_2 = createBooking(50, LocalDate.of(2024, 1, 15));
        
        customerC8.getBookings().add(bookingC8_1);
        customerC8.getBookings().add(bookingC8_2);
        
        // Setup for Customer C9
        Customer customerC9 = new Customer();
        customerC9.setMembershipPackage(membershipPackage);
        
        // Create booking for C9 in January 2024
        Booking bookingC9 = createBooking(50, LocalDate.of(2024, 1, 10));
        customerC9.getBookings().add(bookingC9);
        
        // Execute
        int resultC8 = customerC8.computeMonthlyRewardPoints("2024-01");
        int resultC9 = customerC9.computeMonthlyRewardPoints("2024-01");
        
        // Verify
        assertEquals("C8 should have (50+50)*5=500 points", 500, resultC8);
        assertEquals("C9 should have 50*5=250 points", 250, resultC9);
    }
    
    @Test
    public void testCase_NoMembershipPackage() {
        // Setup customer without membership package
        customer = new Customer();
        customer.setMembershipPackage(null);
        
        // Create some bookings
        Booking booking = createBooking(2, LocalDate.of(2023, 12, 10));
        customer.getBookings().add(booking);
        
        // Execute
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("No points should be earned without membership package", 0, result);
    }
    
    @Test
    public void testCase_MembershipWithoutPointsAward() {
        // Setup customer with membership but no POINTS award
        customer = new Customer();
        MembershipPackage noPointsMembership = new MembershipPackage();
        noPointsMembership.setAwards(new Award[]{Award.CASHBACK, Award.DISCOUNTS});
        customer.setMembershipPackage(noPointsMembership);
        
        // Create some bookings
        Booking booking = createBooking(2, LocalDate.of(2023, 12, 10));
        customer.getBookings().add(booking);
        
        // Execute
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("No points should be earned without POINTS award", 0, result);
    }
    
    @Test
    public void testCase_EmptyBookingsList() {
        // Setup customer with points membership but no bookings
        customer = new Customer();
        customer.setMembershipPackage(membershipPackage);
        
        // Execute
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("No points should be earned with empty bookings list", 0, result);
    }
    
    // Helper method to create bookings with specified seats and booking date
    private Booking createBooking(int seats, LocalDate bookingDate) {
        Booking booking = new Booking();
        booking.setNumberOfSeats(seats);
        booking.setBookingDate(bookingDate);
        return booking;
    }
}