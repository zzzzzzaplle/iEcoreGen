import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR4Test {
    
    private Customer customerC5;
    private Customer customerC6;
    private Customer customerC7;
    private Customer customerC8;
    private Customer customerC9;
    private Driver driver;
    private MembershipPackage pointsMembership;
    
    @Before
    public void setUp() {
        // Create membership package with POINTS award
        pointsMembership = new MembershipPackage();
        pointsMembership.setAwards(new Award[]{Award.POINTS});
        
        // Initialize customers
        customerC5 = new Customer();
        customerC5.setMembershipPackage(pointsMembership);
        
        customerC6 = new Customer();
        customerC6.setMembershipPackage(pointsMembership);
        
        customerC7 = new Customer();
        customerC7.setMembershipPackage(pointsMembership);
        
        customerC8 = new Customer();
        customerC8.setMembershipPackage(pointsMembership);
        
        customerC9 = new Customer();
        customerC9.setMembershipPackage(pointsMembership);
        
        // Initialize driver for trips
        driver = new Driver();
    }
    
    @Test
    public void testCase1_PointsCalculationWithMultipleBookings() {
        // Setup: Customer C5 has membership with POINTS award
        // Create Booking1 (seats:2) for trip (price: 200.0, departure: 2023-12-25 12:00)
        // Create Booking2 (seats:3) for trip (price: 100.0, departure: 2023-12-26 12:00)
        
        Trip trip1 = createTrip("2023-12-25", "12:00", "14:00", 200.0, 10);
        Trip trip2 = createTrip("2023-12-26", "12:00", "14:00", 100.0, 10);
        
        driver.getTrips().add(trip1);
        driver.getTrips().add(trip2);
        
        // Create bookings for customer C5
        createBooking(customerC5, trip1, 2, "2023-12-01 10:00");
        createBooking(customerC5, trip2, 3, "2023-12-01 10:00");
        
        // Compute reward points for current month: 2023-12
        int result = customerC5.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 25 (2+3)*5=25
        assertEquals("Points should be calculated correctly for multiple bookings", 25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() {
        // Setup: Customer C6 has membership with POINTS award
        // Create Booking3 (seats:4) for trip (price: 100.0, departure: 2024-12-26 12:00)
        
        Trip trip = createTrip("2024-12-26", "12:00", "14:00", 100.0, 10);
        driver.getTrips().add(trip);
        
        // Create booking for customer C6
        createBooking(customerC6, trip, 4, "2023-12-01 10:00");
        
        // Compute reward points for current month: 2023-12
        int result = customerC6.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 0 (booking is for next year)
        assertEquals("Points should be 0 for bookings outside current month", 0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() {
        // Setup: Customer C7 has membership with POINTS award
        // Booking1: 2023-11-30 10:00 (seats:2) for trip (price: 200.0, departure: 2023-12-25 12:00)
        // Booking2: 2023-12-01 10:00 (seats:3) for trip (price: 200.0, departure: 2023-12-25 12:00)
        
        Trip trip = createTrip("2023-12-25", "12:00", "14:00", 200.0, 10);
        driver.getTrips().add(trip);
        
        // Create bookings for customer C7
        createBooking(customerC7, trip, 2, "2023-11-30 10:00");
        createBooking(customerC7, trip, 3, "2023-12-01 10:00");
        
        // Compute reward points for current month: 2023-12
        int result = customerC7.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 15 (3*5=15) - only December booking counts
        assertEquals("Points should only include bookings from current month", 15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() {
        // Setup: Customer C8 has membership with POINTS award
        // Booking: 2023-12-10 10:00 (seats:2) for trip (price: 200.0, departure: 2024-03-25 12:00)
        
        Trip trip = createTrip("2024-03-25", "12:00", "14:00", 200.0, 10);
        driver.getTrips().add(trip);
        
        // Create booking for customer C8
        createBooking(customerC8, trip, 2, "2023-12-10 10:00");
        
        // Compute reward points for current month: 2023-12
        int result = customerC8.computeMonthlyRewardPoints("2023-12");
        
        // Expected Output: 10 (2*5=10)
        assertEquals("Points should be calculated correctly for seat quantity", 10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() {
        // Setup: 
        // Customer C8 has membership with POINTS award
        // C8 create Booking: 2024-01-10 10:00 (seats:50) for trip (price: 150.0, departure: 2024-05-25 12:00)
        // C8 create Booking: 2024-01-15 10:00 (seats:50) for trip (price: 200.0, departure: 2024-06-25 12:00)
        // C9 create Booking: 2024-01-10 10:00 (seats:50) for trip (price: 200.0, departure: 2024-07-25 12:00)
        
        Trip trip1 = createTrip("2024-05-25", "12:00", "14:00", 150.0, 100);
        Trip trip2 = createTrip("2024-06-25", "12:00", "14:00", 200.0, 100);
        Trip trip3 = createTrip("2024-07-25", "12:00", "14:00", 200.0, 100);
        
        driver.getTrips().add(trip1);
        driver.getTrips().add(trip2);
        driver.getTrips().add(trip3);
        
        // Create bookings for customers C8 and C9
        createBooking(customerC8, trip1, 50, "2024-01-10 10:00");
        createBooking(customerC8, trip2, 50, "2024-01-15 10:00");
        createBooking(customerC9, trip3, 50, "2024-01-10 10:00");
        
        // Compute reward points for current month: 2024-01
        int resultC8 = customerC8.computeMonthlyRewardPoints("2024-01");
        int resultC9 = customerC9.computeMonthlyRewardPoints("2024-01");
        
        // Expected Output: C8 reward points: 500, C9 reward points: 250
        assertEquals("C8 should have 500 points for 100 seats", 500, resultC8);
        assertEquals("C9 should have 250 points for 50 seats", 250, resultC9);
    }
    
    // Helper method to create a trip with specified parameters
    private Trip createTrip(String departureDate, String departureTime, String arrivalTime, double price, int numberOfSeats) {
        Trip trip = new Trip();
        try {
            trip.setDepartureDate(new SimpleDateFormat("yyyy-MM-dd").parse(departureDate));
        } catch (ParseException e) {
            fail("Date parsing failed: " + e.getMessage());
        }
        trip.setDepartureTime(departureTime);
        trip.setArrivalTime(arrivalTime);
        trip.setPrice(price);
        trip.setNumberOfSeats(numberOfSeats);
        return trip;
    }
    
    // Helper method to create a booking for a customer on a trip
    private void createBooking(Customer customer, Trip trip, int numberOfSeats, String bookingTime) {
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setTrip(trip);
        booking.setNumberOfSeats(numberOfSeats);
        try {
            booking.setBookingDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(bookingTime));
        } catch (ParseException e) {
            fail("Booking time parsing failed: " + e.getMessage());
        }
        trip.getBookings().add(booking);
    }
}