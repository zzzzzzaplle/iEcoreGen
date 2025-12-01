import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR4Test {
    
    private List<Booking> originalBookings;
    
    @Before
    public void setUp() {
        // Save original bookings to restore after each test
        originalBookings = new ArrayList<>(Trip.getBookings());
    }
    
    @Before
    public void tearDown() {
        // Restore original bookings after each test
        Trip.setBookings(originalBookings);
    }
    
    @Test
    public void testCase1_PointsCalculationWithMultipleBookings() throws Exception {
        // Setup: Customer C5 has membership with POINTS award
        Customer c5 = new Customer();
        MembershipPackage mp1 = new MembershipPackage();
        mp1.setAwards(new Award[]{Award.POINTS});
        c5.setMembershipPackage(mp1);
        
        // Create bookings for C5 in December 2023
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        // Booking1: seats:2 for trip with departure 2023-12-25
        Booking booking1 = new Booking();
        booking1.setCustomer(c5);
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(sdf.parse("2023-12-01"));
        
        // Booking2: seats:3 for trip with departure 2023-12-26
        Booking booking2 = new Booking();
        booking2.setCustomer(c5);
        booking2.setNumberOfSeats(3);
        booking2.setBookingDate(sdf.parse("2023-12-02"));
        
        // Add bookings to Trip's static list
        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking1);
        bookings.add(booking2);
        Trip.setBookings(bookings);
        
        // Test: Compute reward points for Customer C5 (current Month: 2023-12)
        int result = c5.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 25 (2+3)*5=25
        assertEquals(25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() throws Exception {
        // Setup: Customer C6 has membership with POINTS award
        Customer c6 = new Customer();
        MembershipPackage mp2 = new MembershipPackage();
        mp2.setAwards(new Award[]{Award.POINTS});
        c6.setMembershipPackage(mp2);
        
        // Create booking for C6 in December 2024 (different year)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        // Booking3: seats:4 for trip with departure 2024-12-26
        Booking booking3 = new Booking();
        booking3.setCustomer(c6);
        booking3.setNumberOfSeats(4);
        booking3.setBookingDate(sdf.parse("2024-12-01"));
        
        // Add booking to Trip's static list
        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking3);
        Trip.setBookings(bookings);
        
        // Test: Compute reward points for Customer C6 (current Month: 2023-12)
        int result = c6.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() throws Exception {
        // Setup: Customer C7 has membership with POINTS award
        Customer c7 = new Customer();
        MembershipPackage mp3 = new MembershipPackage();
        mp3.setAwards(new Award[]{Award.POINTS});
        c7.setMembershipPackage(mp3);
        
        // Create bookings for C7 - one in November, one in December
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        // Booking1: 2023-11-30 (should not count for December)
        Booking booking1 = new Booking();
        booking1.setCustomer(c7);
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(sdf.parse("2023-11-30"));
        
        // Booking2: 2023-12-01 (should count for December)
        Booking booking2 = new Booking();
        booking2.setCustomer(c7);
        booking2.setNumberOfSeats(3);
        booking2.setBookingDate(sdf.parse("2023-12-01"));
        
        // Add bookings to Trip's static list
        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking1);
        bookings.add(booking2);
        Trip.setBookings(bookings);
        
        // Test: Compute reward points for Customer C7 (current Month: 2023-12)
        int result = c7.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 15 (3*5=15)
        assertEquals(15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() throws Exception {
        // Setup: Customer C8 has membership with POINTS award
        Customer c8 = new Customer();
        MembershipPackage mp4 = new MembershipPackage();
        mp4.setAwards(new Award[]{Award.POINTS});
        c8.setMembershipPackage(mp4);
        
        // Create booking for C8 in December 2023
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        // Booking: 2023-12-10, seats:2
        Booking booking = new Booking();
        booking.setCustomer(c8);
        booking.setNumberOfSeats(2);
        booking.setBookingDate(sdf.parse("2023-12-10"));
        
        // Add booking to Trip's static list
        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);
        Trip.setBookings(bookings);
        
        // Test: Compute reward points for Customer C8 (current Month: 2023-12)
        int result = c8.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 10 (2*5=10)
        assertEquals(10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() throws Exception {
        // Setup: Customers C8 and C9 have membership with POINTS award
        Customer c8 = new Customer();
        Customer c9 = new Customer();
        
        MembershipPackage mp5 = new MembershipPackage();
        mp5.setAwards(new Award[]{Award.POINTS});
        c8.setMembershipPackage(mp5);
        c9.setMembershipPackage(mp5);
        
        // Create bookings for January 2024
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        // C8 Booking1: 2024-01-10, seats:50
        Booking booking1 = new Booking();
        booking1.setCustomer(c8);
        booking1.setNumberOfSeats(50);
        booking1.setBookingDate(sdf.parse("2024-01-10"));
        
        // C8 Booking2: 2024-01-15, seats:50
        Booking booking2 = new Booking();
        booking2.setCustomer(c8);
        booking2.setNumberOfSeats(50);
        booking2.setBookingDate(sdf.parse("2024-01-15"));
        
        // C9 Booking: 2024-01-10, seats:50
        Booking booking3 = new Booking();
        booking3.setCustomer(c9);
        booking3.setNumberOfSeats(50);
        booking3.setBookingDate(sdf.parse("2024-01-10"));
        
        // Add bookings to Trip's static list
        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking1);
        bookings.add(booking2);
        bookings.add(booking3);
        Trip.setBookings(bookings);
        
        // Test: Compute reward points for Customer C8 (current Month: 2024-01)
        int resultC8 = c8.computeMonthlyRewardPoints("2024-01");
        
        // Test: Compute reward points for Customer C9 (current Month: 2024-01)
        int resultC9 = c9.computeMonthlyRewardPoints("2024-01");
        
        // Expected Output: C8 reward points: 500, C9 reward points: 250
        assertEquals("C8 should have 500 points", 500, resultC8);
        assertEquals("C9 should have 250 points", 250, resultC9);
    }
}