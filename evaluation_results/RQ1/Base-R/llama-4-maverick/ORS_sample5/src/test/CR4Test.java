import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    private ORS ors;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        ors = new ORS();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_pointsCalculationWithMultipleBookings() {
        // Setup customer C5 with points membership
        User customerC5 = new User();
        customerC5.setId("C5");
        customerC5.setMember(true);
        customerC5.setMembershipType("points");
        ors.getUsers().add(customerC5);
        
        // Setup booking 1 for C5 in December 2023
        Booking booking1 = new Booking();
        booking1.setCustomerId("C5");
        booking1.setNumberOfSeats(2);
        booking1.setBookingTime(LocalDateTime.of(2023, 12, 10, 10, 0));
        ors.getBookings().add(booking1);
        
        // Setup booking 2 for C5 in December 2023
        Booking booking2 = new Booking();
        booking2.setCustomerId("C5");
        booking2.setNumberOfSeats(3);
        booking2.setBookingTime(LocalDateTime.of(2023, 12, 15, 10, 0));
        ors.getBookings().add(booking2);
        
        // Execute and verify
        int result = ors.computeMonthlyRewardPoints("C5", 2023, 12);
        assertEquals("Total points should be (2+3)*5=25", 25, result);
    }
    
    @Test
    public void testCase2_zeroPointsWithExpiredBookings() {
        // Setup customer C6 with points membership
        User customerC6 = new User();
        customerC6.setId("C6");
        customerC6.setMember(true);
        customerC6.setMembershipType("points");
        ors.getUsers().add(customerC6);
        
        // Setup booking for C6 in December 2024 (different year)
        Booking booking3 = new Booking();
        booking3.setCustomerId("C6");
        booking3.setNumberOfSeats(4);
        booking3.setBookingTime(LocalDateTime.of(2024, 12, 10, 10, 0));
        ors.getBookings().add(booking3);
        
        // Execute and verify
        int result = ors.computeMonthlyRewardPoints("C6", 2023, 12);
        assertEquals("No points should be awarded for bookings in different year", 0, result);
    }
    
    @Test
    public void testCase3_partialMonthInclusion() {
        // Setup customer C7 with points membership
        User customerC7 = new User();
        customerC7.setId("C7");
        customerC7.setMember(true);
        customerC7.setMembershipType("points");
        ors.getUsers().add(customerC7);
        
        // Setup booking 1 for C7 in November 2023 (should not count)
        Booking booking1 = new Booking();
        booking1.setCustomerId("C7");
        booking1.setNumberOfSeats(2);
        booking1.setBookingTime(LocalDateTime.of(2023, 11, 30, 10, 0));
        ors.getBookings().add(booking1);
        
        // Setup booking 2 for C7 in December 2023 (should count)
        Booking booking2 = new Booking();
        booking2.setCustomerId("C7");
        booking2.setNumberOfSeats(3);
        booking2.setBookingTime(LocalDateTime.of(2023, 12, 1, 10, 0));
        ors.getBookings().add(booking2);
        
        // Execute and verify
        int result = ors.computeMonthlyRewardPoints("C7", 2023, 12);
        assertEquals("Only December bookings should count: 3*5=15", 15, result);
    }
    
    @Test
    public void testCase4_multipleSeatsEdgeCase() {
        // Setup customer C8 with points membership
        User customerC8 = new User();
        customerC8.setId("C8");
        customerC8.setMember(true);
        customerC8.setMembershipType("points");
        ors.getUsers().add(customerC8);
        
        // Setup booking for C8 in December 2023
        Booking booking = new Booking();
        booking.setCustomerId("C8");
        booking.setNumberOfSeats(2);
        booking.setBookingTime(LocalDateTime.of(2023, 12, 10, 10, 0));
        ors.getBookings().add(booking);
        
        // Execute and verify
        int result = ors.computeMonthlyRewardPoints("C8", 2023, 12);
        assertEquals("Points should be 2*5=10", 10, result);
    }
    
    @Test
    public void testCase5_largeSeatQuantity() {
        // Setup customer C8 with points membership
        User customerC8 = new User();
        customerC8.setId("C8");
        customerC8.setMember(true);
        customerC8.setMembershipType("points");
        ors.getUsers().add(customerC8);
        
        // Setup customer C9 with points membership
        User customerC9 = new User();
        customerC9.setId("C9");
        customerC9.setMember(true);
        customerC9.setMembershipType("points");
        ors.getUsers().add(customerC9);
        
        // Setup bookings for C8 in January 2024
        Booking booking1 = new Booking();
        booking1.setCustomerId("C8");
        booking1.setNumberOfSeats(50);
        booking1.setBookingTime(LocalDateTime.of(2024, 1, 10, 10, 0));
        ors.getBookings().add(booking1);
        
        Booking booking2 = new Booking();
        booking2.setCustomerId("C8");
        booking2.setNumberOfSeats(50);
        booking2.setBookingTime(LocalDateTime.of(2024, 1, 15, 10, 0));
        ors.getBookings().add(booking2);
        
        // Setup booking for C9 in January 2024
        Booking booking3 = new Booking();
        booking3.setCustomerId("C9");
        booking3.setNumberOfSeats(50);
        booking3.setBookingTime(LocalDateTime.of(2024, 1, 10, 10, 0));
        ors.getBookings().add(booking3);
        
        // Execute and verify for C8
        int resultC8 = ors.computeMonthlyRewardPoints("C8", 2024, 1);
        assertEquals("C8 points should be (50+50)*5=500", 500, resultC8);
        
        // Execute and verify for C9
        int resultC9 = ors.computeMonthlyRewardPoints("C9", 2024, 1);
        assertEquals("C9 points should be 50*5=250", 250, resultC9);
    }
}