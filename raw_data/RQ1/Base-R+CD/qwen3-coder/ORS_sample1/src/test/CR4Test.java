import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR4Test {
    
    private Customer customer;
    private Trip trip1, trip2, trip3;
    private Booking booking1, booking2, booking3, booking4, booking5, booking6;
    private MembershipPackage membership;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        customer = new Customer();
        membership = new MembershipPackage();
        membership.setAwards(new Award[]{Award.POINTS});
        customer.setMembershipPackage(membership);
        
        trip1 = new Trip();
        trip1.setDepartureDate(createDate("2023-12-25 12:00:00"));
        
        trip2 = new Trip();
        trip2.setDepartureDate(createDate("2023-12-26 12:00:00"));
        
        trip3 = new Trip();
        trip3.setDepartureDate(createDate("2024-03-25 12:00:00"));
        
        booking1 = new Booking();
        booking2 = new Booking();
        booking3 = new Booking();
        booking4 = new Booking();
        booking5 = new Booking();
        booking6 = new Booking();
    }
    
    private Date createDate(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.parse(dateString);
        } catch (Exception e) {
            return new Date();
        }
    }
    
    @Test
    public void testCase1_PointsCalculationWithMultipleBookings() {
        // Setup: Customer C5 has membership with POINTS award
        Customer c5 = new Customer();
        c5.setMembershipPackage(membership);
        
        // Create Booking1 (seats:2) for trip (price: 200.0, departure: 2023-12-25 12:00)
        Booking b1 = new Booking();
        b1.setCustomer(c5);
        b1.setNumberOfSeats(2);
        b1.setBookingDate(createDate("2023-12-01 10:00:00"));
        
        // Create Booking2 (seats:3) for trip (price: 100.0, departure: 2023-12-26 12:00)
        Booking b2 = new Booking();
        b2.setCustomer(c5);
        b2.setNumberOfSeats(3);
        b2.setBookingDate(createDate("2023-12-02 10:00:00"));
        
        // Add bookings to trips
        List<Booking> trip1Bookings = new ArrayList<>();
        trip1Bookings.add(b1);
        trip1.setBookings(trip1Bookings);
        
        List<Booking> trip2Bookings = new ArrayList<>();
        trip2Bookings.add(b2);
        trip2.setBookings(trip2Bookings);
        
        // Mock getAllBookedTrips to return the test trips
        // Since getAllBookedTrips is private, we'll use reflection to test the public method
        // For this test, we'll create a helper method to simulate the behavior
        
        // Expected output: 25 (2+3)*5=25
        int result = c5.computeMonthlyRewardPoints("2023-12");
        assertEquals(25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() {
        // Setup: Customer C6 has membership with POINTS award
        Customer c6 = new Customer();
        c6.setMembershipPackage(membership);
        
        // C6 create Booking3 (seats:4) for trip (price: 100.0, departure: 2024-12-26 12:00)
        Booking b3 = new Booking();
        b3.setCustomer(c6);
        b3.setNumberOfSeats(4);
        b3.setBookingDate(createDate("2023-12-01 10:00:00"));
        
        Trip futureTrip = new Trip();
        futureTrip.setDepartureDate(createDate("2024-12-26 12:00:00"));
        
        List<Booking> futureTripBookings = new ArrayList<>();
        futureTripBookings.add(b3);
        futureTrip.setBookings(futureTripBookings);
        
        // Expected output: 0 (booking is for future month)
        int result = c6.computeMonthlyRewardPoints("2023-12");
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() {
        // Setup: Customer C7 has membership with POINTS award
        Customer c7 = new Customer();
        c7.setMembershipPackage(membership);
        
        // C7 create Booking1: 2023-11-30 10:00 (seats:2) for trip (price: 200.0, departure: 2023-12-25 12:00)
        Booking b1 = new Booking();
        b1.setCustomer(c7);
        b1.setNumberOfSeats(2);
        b1.setBookingDate(createDate("2023-11-30 10:00:00"));
        
        // C7 create Booking2: 2023-12-01 10:00 (seats:3) for trip (price: 200.0, departure: 2023-12-25 12:00)
        Booking b2 = new Booking();
        b2.setCustomer(c7);
        b2.setNumberOfSeats(3);
        b2.setBookingDate(createDate("2023-12-01 10:00:00"));
        
        List<Booking> tripBookings = new ArrayList<>();
        tripBookings.add(b1);
        tripBookings.add(b2);
        trip1.setBookings(tripBookings);
        
        // Expected output: 15 (3*5=15, only December booking counts)
        int result = c7.computeMonthlyRewardPoints("2023-12");
        assertEquals(15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() {
        // Setup: Customer C8 has membership with POINTS award
        Customer c8 = new Customer();
        c8.setMembershipPackage(membership);
        
        // C8 create Booking: 2023-12-10 10:00 (seats:2) for trip (price: 200.0, departure: 2024-03-25 12:00)
        Booking booking = new Booking();
        booking.setCustomer(c8);
        booking.setNumberOfSeats(2);
        booking.setBookingDate(createDate("2023-12-10 10:00:00"));
        
        List<Booking> tripBookings = new ArrayList<>();
        tripBookings.add(booking);
        trip3.setBookings(tripBookings);
        
        // Expected output: 10 (2*5=10)
        int result = c8.computeMonthlyRewardPoints("2023-12");
        assertEquals(10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() {
        // Setup: Customer C8 and C9 have membership with POINTS award
        Customer c8 = new Customer();
        c8.setMembershipPackage(membership);
        
        Customer c9 = new Customer();
        c9.setMembershipPackage(membership);
        
        // C8 create Booking: 2024-01-10 10:00 (seats:50) for trip (price: 150.0, departure: 2024-05-25 12:00)
        Booking b1 = new Booking();
        b1.setCustomer(c8);
        b1.setNumberOfSeats(50);
        b1.setBookingDate(createDate("2024-01-10 10:00:00"));
        
        // C8 create Booking: 2024-01-15 10:00 (seats:50) for trip (price: 200.0, departure: 2024-06-25 12:00)
        Booking b2 = new Booking();
        b2.setCustomer(c8);
        b2.setNumberOfSeats(50);
        b2.setBookingDate(createDate("2024-01-15 10:00:00"));
        
        // C9 create Booking: 2024-01-10 10:00 (seats:50) for trip (price: 200.0, departure: 2024-07-25 12:00)
        Booking b3 = new Booking();
        b3.setCustomer(c9);
        b3.setNumberOfSeats(50);
        b3.setBookingDate(createDate("2024-01-10 10:00:00"));
        
        Trip trip4 = new Trip();
        trip4.setDepartureDate(createDate("2024-05-25 12:00:00"));
        
        Trip trip5 = new Trip();
        trip5.setDepartureDate(createDate("2024-06-25 12:00:00"));
        
        Trip trip6 = new Trip();
        trip6.setDepartureDate(createDate("2024-07-25 12:00:00"));
        
        List<Booking> trip4Bookings = new ArrayList<>();
        trip4Bookings.add(b1);
        trip4.setBookings(trip4Bookings);
        
        List<Booking> trip5Bookings = new ArrayList<>();
        trip5Bookings.add(b2);
        trip5.setBookings(trip5Bookings);
        
        List<Booking> trip6Bookings = new ArrayList<>();
        trip6Bookings.add(b3);
        trip6.setBookings(trip6Bookings);
        
        // Expected output: C8 reward points: 500, C9 reward points: 250
        int c8Result = c8.computeMonthlyRewardPoints("2024-01");
        int c9Result = c9.computeMonthlyRewardPoints("2024-01");
        
        assertEquals(500, c8Result);
        assertEquals(250, c9Result);
    }
}