import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR4Test {
    
    private Customer customer;
    private Trip trip1;
    private Trip trip2;
    private Trip trip3;
    private Booking booking1;
    private Booking booking2;
    private Booking booking3;
    private MembershipPackage membershipPackage;
    
    @Before
    public void setUp() {
        customer = new Customer();
        membershipPackage = new MembershipPackage();
        membershipPackage.setAwards(new Award[]{Award.POINTS});
        customer.setMembershipPackage(membershipPackage);
        customer.setBookings(new ArrayList<>());
        
        trip1 = new Trip();
        trip1.setDepartureDate("2023-12-25");
        trip1.setDepartureTime("12:00");
        trip1.setPrice(200.0);
        
        trip2 = new Trip();
        trip2.setDepartureDate("2023-12-26");
        trip2.setDepartureTime("12:00");
        trip2.setPrice(100.0);
        
        trip3 = new Trip();
        trip3.setDepartureDate("2024-12-26");
        trip3.setDepartureTime("12:00");
        trip3.setPrice(100.0);
    }
    
    @Test
    public void testCase1_PointsCalculationWithMultipleBookings() throws Exception {
        // Setup: Customer C5 has membership with POINTS award
        Customer c5 = new Customer();
        c5.setMembershipPackage(membershipPackage);
        c5.setBookings(new ArrayList<>());
        
        // Create Booking1 (seats:2) for trip (price: 200.0, departure: 2023-12-25 12:00)
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(2);
        booking1.setCustomer(c5);
        booking1.setTrip(trip1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        booking1.setBookingDate(sdf.parse("2023-12-01"));
        
        // Create Booking2 (seats:3) for trip (price: 100.0, departure: 2023-12-26 12:00)
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(3);
        booking2.setCustomer(c5);
        booking2.setTrip(trip2);
        booking2.setBookingDate(sdf.parse("2023-12-02"));
        
        c5.getBookings().add(booking1);
        c5.getBookings().add(booking2);
        
        // Input: Compute reward points for Customer C5 (current Month: 2023-12)
        int result = c5.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 25. (2+3)*5=25
        assertEquals(25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() throws Exception {
        // Setup: Customer C6 has membership with POINTS award
        Customer c6 = new Customer();
        c6.setMembershipPackage(membershipPackage);
        c6.setBookings(new ArrayList<>());
        
        // C6 create Booking3 (seats:4) for trip (price: 100.0, departure: 2024-12-26 12:00)
        Booking booking3 = new Booking();
        booking3.setNumberOfSeats(4);
        booking3.setCustomer(c6);
        booking3.setTrip(trip3);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        booking3.setBookingDate(sdf.parse("2023-12-01"));
        
        c6.getBookings().add(booking3);
        
        // Input: Compute reward points for Customer C6 (current Month: 2023-12)
        int result = c6.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() throws Exception {
        // Setup: Customer C7 has membership with POINTS award
        Customer c7 = new Customer();
        c7.setMembershipPackage(membershipPackage);
        c7.setBookings(new ArrayList<>());
        
        // C7 create Booking1: 2023-11-30 10:00 (seats:2) for trip (price: 200.0, departure: 2023-12-25 12:00)
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(2);
        booking1.setCustomer(c7);
        booking1.setTrip(trip1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        booking1.setBookingDate(sdf.parse("2023-11-30"));
        
        // C7 create Booking2: 2023-12-01 10:00 (seats:3) for trip (price: 200.0, departure: 2023-12-25 12:00)
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(3);
        booking2.setCustomer(c7);
        booking2.setTrip(trip1);
        booking2.setBookingDate(sdf.parse("2023-12-01"));
        
        c7.getBookings().add(booking1);
        c7.getBookings().add(booking2);
        
        // Input: Compute reward points for Customer C7 (current Month: 2023-12)
        int result = c7.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 15. 3*5=15
        assertEquals(15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() throws Exception {
        // Setup: Customer C8 has membership with POINTS award
        Customer c8 = new Customer();
        c8.setMembershipPackage(membershipPackage);
        c8.setBookings(new ArrayList<>());
        
        // C8 create Booking: 2023-12-10 10:00 (seats:2) for trip (price: 200.0, departure: 2024-03-25 12:00)
        Trip trip4 = new Trip();
        trip4.setDepartureDate("2024-03-25");
        trip4.setDepartureTime("12:00");
        trip4.setPrice(200.0);
        
        Booking booking = new Booking();
        booking.setNumberOfSeats(2);
        booking.setCustomer(c8);
        booking.setTrip(trip4);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        booking.setBookingDate(sdf.parse("2023-12-10"));
        
        c8.getBookings().add(booking);
        
        // Input: Compute reward points for Customer C8 (current Month: 2023-12)
        int result = c8.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 10. 2*5=10
        assertEquals(10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() throws Exception {
        // Setup: Customer C8 has membership with POINTS award
        Customer c8 = new Customer();
        c8.setMembershipPackage(membershipPackage);
        c8.setBookings(new ArrayList<>());
        
        // C8 create Booking: 2024-01-10 10:00 (seats:50) for trip (price: 150.0, departure: 2024-05-25 12:00)
        Trip trip5 = new Trip();
        trip5.setDepartureDate("2024-05-25");
        trip5.setDepartureTime("12:00");
        trip5.setPrice(150.0);
        
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(50);
        booking1.setCustomer(c8);
        booking1.setTrip(trip5);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        booking1.setBookingDate(sdf.parse("2024-01-10"));
        
        // C8 create Booking: 2024-01-15 10:00 (seats:50) for trip (price: 200.0, departure: 2024-06-25 12:00)
        Trip trip6 = new Trip();
        trip6.setDepartureDate("2024-06-25");
        trip6.setDepartureTime("12:00");
        trip6.setPrice(200.0);
        
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(50);
        booking2.setCustomer(c8);
        booking2.setTrip(trip6);
        booking2.setBookingDate(sdf.parse("2024-01-15"));
        
        c8.getBookings().add(booking1);
        c8.getBookings().add(booking2);
        
        // Setup: Customer C9 has membership with POINTS award
        Customer c9 = new Customer();
        MembershipPackage mp9 = new MembershipPackage();
        mp9.setAwards(new Award[]{Award.POINTS});
        c9.setMembershipPackage(mp9);
        c9.setBookings(new ArrayList<>());
        
        // C9 create Booking: 2024-01-10 10:00 (seats:50) for trip (price: 200.0, departure: 2024-07-25 12:00)
        Trip trip7 = new Trip();
        trip7.setDepartureDate("2024-07-25");
        trip7.setDepartureTime("12:00");
        trip7.setPrice(200.0);
        
        Booking booking3 = new Booking();
        booking3.setNumberOfSeats(50);
        booking3.setCustomer(c9);
        booking3.setTrip(trip7);
        booking3.setBookingDate(sdf.parse("2024-01-10"));
        
        c9.getBookings().add(booking3);
        
        // Input: Compute reward points for Customer C8 (current Month: 2024-01)
        int resultC8 = c8.computeMonthlyRewardPoints("2024-01");
        
        // Input: Compute reward points for Customer C9 (current Month: 2024-01)
        int resultC9 = c9.computeMonthlyRewardPoints("2024-01");
        
        // Expected Output: C8 reward points: 500. C9 reward points: 250.
        assertEquals(500, resultC8);
        assertEquals(250, resultC9);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMonthFormat() {
        // Test that invalid month format throws IllegalArgumentException
        customer.computeMonthlyRewardPoints("2023-13"); // Invalid month
    }
}