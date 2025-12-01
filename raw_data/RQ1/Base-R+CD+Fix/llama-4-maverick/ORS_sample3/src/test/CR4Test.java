import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR4Test {
    
    private Customer customer;
    private MembershipPackage membershipPackage;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        membershipPackage = new MembershipPackage();
        customer = new Customer();
    }
    
    @Test
    public void testCase1_PointsCalculationWithMultipleBookings() throws Exception {
        // Setup
        membershipPackage.setAwards(new Award[]{Award.POINTS});
        customer.setMembershipPackage(membershipPackage);
        
        // Create bookings for customer C5
        List<Booking> bookings = new ArrayList<>();
        
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(dateFormat.parse("2023-12-25 10:00"));
        bookings.add(booking1);
        
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(3);
        booking2.setBookingDate(dateFormat.parse("2023-12-26 10:00"));
        bookings.add(booking2);
        
        // Use reflection to set bookings since getBookings() is private
        setPrivateBookings(customer, bookings);
        
        // Execute
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("Total points should be 25 for 5 seats across 2 bookings", 25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() throws Exception {
        // Setup
        membershipPackage.setAwards(new Award[]{Award.POINTS});
        customer.setMembershipPackage(membershipPackage);
        
        // Create booking for customer C6 with date outside target month
        List<Booking> bookings = new ArrayList<>();
        
        Booking booking3 = new Booking();
        booking3.setNumberOfSeats(4);
        booking3.setBookingDate(dateFormat.parse("2024-12-26 10:00")); // Different year
        bookings.add(booking3);
        
        setPrivateBookings(customer, bookings);
        
        // Execute
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("No points should be awarded for bookings outside target month", 0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() throws Exception {
        // Setup
        membershipPackage.setAwards(new Award[]{Award.POINTS});
        customer.setMembershipPackage(membershipPackage);
        
        // Create bookings for customer C7 with one booking in previous month
        List<Booking> bookings = new ArrayList<>();
        
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(dateFormat.parse("2023-11-30 10:00")); // Previous month
        bookings.add(booking1);
        
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(3);
        booking2.setBookingDate(dateFormat.parse("2023-12-01 10:00")); // Target month
        bookings.add(booking2);
        
        setPrivateBookings(customer, bookings);
        
        // Execute
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("Only bookings from target month should be counted", 15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() throws Exception {
        // Setup
        membershipPackage.setAwards(new Award[]{Award.POINTS});
        customer.setMembershipPackage(membershipPackage);
        
        // Create booking for customer C8
        List<Booking> bookings = new ArrayList<>();
        
        Booking booking = new Booking();
        booking.setNumberOfSeats(2);
        booking.setBookingDate(dateFormat.parse("2023-12-10 10:00"));
        bookings.add(booking);
        
        setPrivateBookings(customer, bookings);
        
        // Execute
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Verify
        assertEquals("Points should be calculated correctly for 2 seats", 10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() throws Exception {
        // Setup customer C8
        membershipPackage.setAwards(new Award[]{Award.POINTS});
        customer.setMembershipPackage(membershipPackage);
        
        List<Booking> bookingsC8 = new ArrayList<>();
        
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(50);
        booking1.setBookingDate(dateFormat.parse("2024-01-10 10:00"));
        bookingsC8.add(booking1);
        
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(50);
        booking2.setBookingDate(dateFormat.parse("2024-01-15 10:00"));
        bookingsC8.add(booking2);
        
        setPrivateBookings(customer, bookingsC8);
        
        // Execute for C8
        int resultC8 = customer.computeMonthlyRewardPoints("2024-01");
        
        // Setup customer C9
        Customer customer9 = new Customer();
        customer9.setMembershipPackage(membershipPackage);
        
        List<Booking> bookingsC9 = new ArrayList<>();
        
        Booking booking3 = new Booking();
        booking3.setNumberOfSeats(50);
        booking3.setBookingDate(dateFormat.parse("2024-01-10 10:00"));
        bookingsC9.add(booking3);
        
        setPrivateBookings(customer9, bookingsC9);
        
        // Execute for C9
        int resultC9 = customer9.computeMonthlyRewardPoints("2024-01");
        
        // Verify
        assertEquals("C8 should have 500 points for 100 seats", 500, resultC8);
        assertEquals("C9 should have 250 points for 50 seats", 250, resultC9);
    }
    
    // Helper method to set private bookings using reflection
    private void setPrivateBookings(Customer customer, List<Booking> bookings) {
        try {
            java.lang.reflect.Method getBookingsMethod = Customer.class.getDeclaredMethod("getBookings");
            getBookingsMethod.setAccessible(true);
            
            @SuppressWarnings("unchecked")
            List<Booking> existingBookings = (List<Booking>) getBookingsMethod.invoke(customer);
            existingBookings.clear();
            existingBookings.addAll(bookings);
            
            getBookingsMethod.setAccessible(false);
        } catch (Exception e) {
            fail("Failed to set bookings using reflection: " + e.getMessage());
        }
    }
}