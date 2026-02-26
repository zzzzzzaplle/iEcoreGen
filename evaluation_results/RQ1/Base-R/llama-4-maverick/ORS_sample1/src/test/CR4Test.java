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
    public void testCase1_pointsCalculationWithMultipleBookings() {
        // Setup: Customer C5 has membership with POINTS award
        User customerC5 = new User();
        customerC5.setId("C5");
        customerC5.setHasMembership(true);
        system.setUsers(new ArrayList<User>() {{ add(customerC5); }});
        
        // Setup bookings for C5
        Booking booking1 = new Booking();
        booking1.setCustomerId("C5");
        booking1.setNumberOfSeats(2);
        booking1.setBookingTime(LocalDateTime.parse("2023-12-01 10:00:00", formatter));
        
        Booking booking2 = new Booking();
        booking2.setCustomerId("C5");
        booking2.setNumberOfSeats(3);
        booking2.setBookingTime(LocalDateTime.parse("2023-12-15 10:00:00", formatter));
        
        system.setBookings(new ArrayList<Booking>() {{ add(booking1); add(booking2); }});
        
        // Test: Compute reward points for Customer C5 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints("C5", 2023, 12);
        
        // Verify: Expected Output: 25. (2+3)*5=25
        assertEquals(25, result);
    }
    
    @Test
    public void testCase2_zeroPointsWithExpiredBookings() {
        // Setup: Customer C6 has membership with POINTS award
        User customerC6 = new User();
        customerC6.setId("C6");
        customerC6.setHasMembership(true);
        system.setUsers(new ArrayList<User>() {{ add(customerC6); }});
        
        // Setup booking for C6 with booking date in different year
        Booking booking3 = new Booking();
        booking3.setCustomerId("C6");
        booking3.setNumberOfSeats(4);
        booking3.setBookingTime(LocalDateTime.parse("2024-01-01 10:00:00", formatter));
        
        system.setBookings(new ArrayList<Booking>() {{ add(booking3); }});
        
        // Test: Compute reward points for Customer C6 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints("C6", 2023, 12);
        
        // Verify: Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_partialMonthInclusion() {
        // Setup: Customer C7 has membership with POINTS award
        User customerC7 = new User();
        customerC7.setId("C7");
        customerC7.setHasMembership(true);
        system.setUsers(new ArrayList<User>() {{ add(customerC7); }});
        
        // Setup bookings for C7 - one in November, one in December
        Booking booking1 = new Booking();
        booking1.setCustomerId("C7");
        booking1.setNumberOfSeats(2);
        booking1.setBookingTime(LocalDateTime.parse("2023-11-30 10:00:00", formatter));
        
        Booking booking2 = new Booking();
        booking2.setCustomerId("C7");
        booking2.setNumberOfSeats(3);
        booking2.setBookingTime(LocalDateTime.parse("2023-12-01 10:00:00", formatter));
        
        system.setBookings(new ArrayList<Booking>() {{ add(booking1); add(booking2); }});
        
        // Test: Compute reward points for Customer C7 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints("C7", 2023, 12);
        
        // Verify: Expected Output: 15. 3*5=15
        assertEquals(15, result);
    }
    
    @Test
    public void testCase4_multipleSeatsEdgeCase() {
        // Setup: Customer C8 has membership with POINTS award
        User customerC8 = new User();
        customerC8.setId("C8");
        customerC8.setHasMembership(true);
        system.setUsers(new ArrayList<User>() {{ add(customerC8); }});
        
        // Setup booking for C8 in December 2023
        Booking booking = new Booking();
        booking.setCustomerId("C8");
        booking.setNumberOfSeats(2);
        booking.setBookingTime(LocalDateTime.parse("2023-12-10 10:00:00", formatter));
        
        system.setBookings(new ArrayList<Booking>() {{ add(booking); }});
        
        // Test: Compute reward points for Customer C8 (current Month: 2023-12)
        int result = system.computeMonthlyRewardPoints("C8", 2023, 12);
        
        // Verify: Expected Output: 10. 2*5=10
        assertEquals(10, result);
    }
    
    @Test
    public void testCase5_largeSeatQuantity() {
        // Setup: Customers C8 and C9 have membership with POINTS award
        User customerC8 = new User();
        customerC8.setId("C8");
        customerC8.setHasMembership(true);
        
        User customerC9 = new User();
        customerC9.setId("C9");
        customerC9.setHasMembership(true);
        
        system.setUsers(new ArrayList<User>() {{ add(customerC8); add(customerC9); }});
        
        // Setup bookings for C8 and C9 in January 2024
        Booking booking1 = new Booking();
        booking1.setCustomerId("C8");
        booking1.setNumberOfSeats(50);
        booking1.setBookingTime(LocalDateTime.parse("2024-01-10 10:00:00", formatter));
        
        Booking booking2 = new Booking();
        booking2.setCustomerId("C8");
        booking2.setNumberOfSeats(50);
        booking2.setBookingTime(LocalDateTime.parse("2024-01-15 10:00:00", formatter));
        
        Booking booking3 = new Booking();
        booking3.setCustomerId("C9");
        booking3.setNumberOfSeats(50);
        booking3.setBookingTime(LocalDateTime.parse("2024-01-10 10:00:00", formatter));
        
        system.setBookings(new ArrayList<Booking>() {{ add(booking1); add(booking2); add(booking3); }});
        
        // Test: Compute reward points for Customer C8 (current Month: 2024-01)
        int resultC8 = system.computeMonthlyRewardPoints("C8", 2024, 1);
        
        // Test: Compute reward points for Customer C9 (current Month: 2024-01)
        int resultC9 = system.computeMonthlyRewardPoints("C9", 2024, 1);
        
        // Verify: Expected Output: C8 reward points: 500. C9 reward points: 250.
        assertEquals(500, resultC8);
        assertEquals(250, resultC9);
    }
}