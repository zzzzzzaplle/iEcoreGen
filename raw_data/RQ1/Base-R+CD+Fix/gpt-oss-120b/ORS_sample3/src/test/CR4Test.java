import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CR4Test {
    
    private Customer customer;
    private Trip trip1, trip2, trip3, trip4, trip5, trip6;
    private MembershipPackage membershipWithPoints;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        
        // Create membership package with POINTS award
        membershipWithPoints = new MembershipPackage();
        membershipWithPoints.setAwards(new Award[]{Award.POINTS});
        
        // Initialize trips for various test cases
        trip1 = new Trip();
        trip1.setDepartureTime("2023-12-25 12:00");
        trip1.setArrivalTime("2023-12-25 14:00");
        trip1.setPrice(200.0);
        
        trip2 = new Trip();
        trip2.setDepartureTime("2023-12-26 12:00");
        trip2.setArrivalTime("2023-12-26 14:00");
        trip2.setPrice(100.0);
        
        trip3 = new Trip();
        trip3.setDepartureTime("2024-12-26 12:00");
        trip3.setArrivalTime("2024-12-26 14:00");
        trip3.setPrice(100.0);
        
        trip4 = new Trip();
        trip4.setDepartureTime("2023-12-25 12:00");
        trip4.setArrivalTime("2023-12-25 14:00");
        trip4.setPrice(200.0);
        
        trip5 = new Trip();
        trip5.setDepartureTime("2024-03-25 12:00");
        trip5.setArrivalTime("2024-03-25 14:00");
        trip5.setPrice(200.0);
        
        trip6 = new Trip();
        trip6.setDepartureTime("2024-05-25 12:00");
        trip6.setArrivalTime("2024-05-25 14:00");
        trip6.setPrice(150.0);
    }
    
    @Test
    public void testCase1_PointsCalculationWithMultipleBookings() {
        // Setup: Customer C5 has membership with POINTS award
        Customer c5 = new Customer();
        c5.setMembershipPackage(membershipWithPoints);
        
        // Create booking1: 2 seats for trip1
        Booking booking1 = new Booking(c5, trip1, 2);
        booking1.setBookingDate(LocalDateTime.parse("2023-12-10 10:00", formatter));
        c5.getBookings().add(booking1);
        trip1.getBookings().add(booking1);
        
        // Create booking2: 3 seats for trip2  
        Booking booking2 = new Booking(c5, trip2, 3);
        booking2.setBookingDate(LocalDateTime.parse("2023-12-11 10:00", formatter));
        c5.getBookings().add(booking2);
        trip2.getBookings().add(booking2);
        
        // Compute points for current month: 2023-12
        int result = c5.computeMonthlyRewardPoints("2023-12");
        
        // Expected: (2+3)*5 = 25
        assertEquals(25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() {
        // Setup: Customer C6 has membership with POINTS award
        Customer c6 = new Customer();
        c6.setMembershipPackage(membershipWithPoints);
        
        // Create booking3: 4 seats for trip3 (departure in 2024-12)
        Booking booking3 = new Booking(c6, trip3, 4);
        booking3.setBookingDate(LocalDateTime.parse("2023-12-10 10:00", formatter));
        c6.getBookings().add(booking3);
        trip3.getBookings().add(booking3);
        
        // Compute points for current month: 2023-12
        int result = c6.computeMonthlyRewardPoints("2023-12");
        
        // Expected: 0 (booking date is in 2023-12, but test expects 0 per specification)
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() {
        // Setup: Customer C7 has membership with POINTS award
        Customer c7 = new Customer();
        c7.setMembershipPackage(membershipWithPoints);
        
        // Create booking1: 2 seats, booking date 2023-11-30 (NOT in target month)
        Booking booking1 = new Booking(c7, trip4, 2);
        booking1.setBookingDate(LocalDateTime.parse("2023-11-30 10:00", formatter));
        c7.getBookings().add(booking1);
        trip4.getBookings().add(booking1);
        
        // Create booking2: 3 seats, booking date 2023-12-01 (IN target month)
        Booking booking2 = new Booking(c7, trip4, 3);
        booking2.setBookingDate(LocalDateTime.parse("2023-12-01 10:00", formatter));
        c7.getBookings().add(booking2);
        trip4.getBookings().add(booking2);
        
        // Compute points for current month: 2023-12
        int result = c7.computeMonthlyRewardPoints("2023-12");
        
        // Expected: 3*5 = 15 (only booking2 counts)
        assertEquals(15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() {
        // Setup: Customer C8 has membership with POINTS award
        Customer c8 = new Customer();
        c8.setMembershipPackage(membershipWithPoints);
        
        // Create booking: 2 seats, booking date 2023-12-10
        Booking booking = new Booking(c8, trip5, 2);
        booking.setBookingDate(LocalDateTime.parse("2023-12-10 10:00", formatter));
        c8.getBookings().add(booking);
        trip5.getBookings().add(booking);
        
        // Compute points for current month: 2023-12
        int result = c8.computeMonthlyRewardPoints("2023-12");
        
        // Expected: 2*5 = 10
        assertEquals(10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() {
        // Setup: Customer C8 has membership with POINTS award
        Customer c8 = new Customer();
        c8.setMembershipPackage(membershipWithPoints);
        
        // Create booking1 for C8: 50 seats, booking date 2024-01-10
        Trip trip7 = new Trip();
        trip7.setDepartureTime("2024-05-25 12:00");
        trip7.setArrivalTime("2024-05-25 14:00");
        trip7.setPrice(150.0);
        
        Booking booking1 = new Booking(c8, trip7, 50);
        booking1.setBookingDate(LocalDateTime.parse("2024-01-10 10:00", formatter));
        c8.getBookings().add(booking1);
        trip7.getBookings().add(booking1);
        
        // Create booking2 for C8: 50 seats, booking date 2024-01-15
        Trip trip8 = new Trip();
        trip8.setDepartureTime("2024-06-25 12:00");
        trip8.setArrivalTime("2024-06-25 14:00");
        trip8.setPrice(200.0);
        
        Booking booking2 = new Booking(c8, trip8, 50);
        booking2.setBookingDate(LocalDateTime.parse("2024-01-15 10:00", formatter));
        c8.getBookings().add(booking2);
        trip8.getBookings().add(booking2);
        
        // Compute points for C8 for current month: 2024-01
        int resultC8 = c8.computeMonthlyRewardPoints("2024-01");
        
        // Setup: Customer C9 has membership with POINTS award
        Customer c9 = new Customer();
        c9.setMembershipPackage(membershipWithPoints);
        
        // Create booking for C9: 50 seats, booking date 2024-01-10
        Trip trip9 = new Trip();
        trip9.setDepartureTime("2024-07-25 12:00");
        trip9.setArrivalTime("2024-07-25 14:00");
        trip9.setPrice(200.0);
        
        Booking booking3 = new Booking(c9, trip9, 50);
        booking3.setBookingDate(LocalDateTime.parse("2024-01-10 10:00", formatter));
        c9.getBookings().add(booking3);
        trip9.getBookings().add(booking3);
        
        // Compute points for C9 for current month: 2024-01
        int resultC9 = c9.computeMonthlyRewardPoints("2024-01");
        
        // Expected: C8: 500 (100 seats * 5), C9: 250 (50 seats * 5)
        assertEquals(500, resultC8);
        assertEquals(250, resultC9);
    }
}