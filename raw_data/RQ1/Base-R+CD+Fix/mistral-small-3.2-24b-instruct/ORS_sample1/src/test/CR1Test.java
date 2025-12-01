import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR1Test {
    
    private Driver driver;
    private Customer customer;
    private Trip trip;
    private Booking booking;
    
    @Before
    public void setUp() {
        // Initialize common objects before each test
        driver = new Driver();
        customer = new Customer();
        trip = new Trip();
        booking = new Booking();
    }
    
    @Test
    public void testCase1_validBookingWithAvailableSeatsAndNoOverlap() throws Exception {
        // Setup: Create Driver D1 with Trip T123 (2023-12-25 14:00-16:00, seats:5)
        Trip t123 = new Trip();
        t123.setDepartureTime("2023-12-25 14:00:00");
        t123.setArrivalTime("2023-12-25 16:00:00");
        t123.setNumberOfSeats(5);
        
        Driver d1 = new Driver();
        List<Trip> d1Trips = new ArrayList<>();
        d1Trips.add(t123);
        d1.setTrips(d1Trips);
        
        // Setup: Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        Customer c1 = new Customer();
        List<Trip> c1Trips = new ArrayList<>();
        
        Trip t456 = new Trip();
        t456.setDepartureTime("2023-12-25 14:00:00");
        t456.setArrivalTime("2023-12-25 16:00:00");
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(c1);
        existingBooking.setTrip(t456);
        existingBooking.setNumberOfSeats(3);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        existingBooking.setBookingDate(sdf.parse("2023-12-25 11:00:00"));
        
        List<Booking> t456Bookings = new ArrayList<>();
        t456Bookings.add(existingBooking);
        t456.setBookings(t456Bookings);
        c1Trips.add(t456);
        c1.setTrips(c1Trips);
        
        // Create booking for T123
        Booking newBooking = new Booking();
        newBooking.setCustomer(c1);
        newBooking.setTrip(t123);
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDate(sdf.parse("2023-12-25 11:00:00"));
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify results
        assertTrue("Booking should be eligible", result);
        assertEquals("Trip T123 seats should be updated to 2", 2, t123.getNumberOfSeats());
    }
    
    @Test
    public void testCase2_bookingDeniedDueToSeatShortage() throws Exception {
        // Setup: Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        Trip t456 = new Trip();
        t456.setDepartureTime("2023-12-25 10:00:00");
        t456.setArrivalTime("2023-12-25 12:00:00");
        t456.setNumberOfSeats(2);
        
        Driver d2 = new Driver();
        List<Trip> d2Trips = new ArrayList<>();
        d2Trips.add(t456);
        d2.setTrips(d2Trips);
        
        // Setup: Create Customer C2 with existing booking for T456 (3 seats)
        Customer c2 = new Customer();
        List<Trip> c2Trips = new ArrayList<>();
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(c2);
        existingBooking.setTrip(t456);
        existingBooking.setNumberOfSeats(3);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        existingBooking.setBookingDate(sdf.parse("2023-12-25 07:30:00"));
        
        List<Booking> t456Bookings = new ArrayList<>();
        t456Bookings.add(existingBooking);
        t456.setBookings(t456Bookings);
        c2Trips.add(t456);
        c2.setTrips(c2Trips);
        
        // Create new booking for T456
        Booking newBooking = new Booking();
        newBooking.setCustomer(c2);
        newBooking.setTrip(t456);
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDate(sdf.parse("2023-12-25 07:30:00"));
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify results
        assertFalse("Booking should be denied due to seat shortage", result);
        assertEquals("Trip T456 seats should remain 2", 2, t456.getNumberOfSeats());
    }
    
    @Test
    public void testCase3_bookingDeniedDueToTimeCutoffExactly2HoursBefore() throws Exception {
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        Trip t100 = new Trip();
        t100.setDepartureTime("2023-12-25 14:00:00");
        t100.setArrivalTime("2023-12-25 16:00:00");
        t100.setNumberOfSeats(50);
        
        // Setup: Current time: 2023-12-25 12:00 (exactly 2 hours before)
        Customer c3 = new Customer();
        List<Trip> c3Trips = new ArrayList<>();
        c3.setTrips(c3Trips);
        
        Booking newBooking = new Booking();
        newBooking.setCustomer(c3);
        newBooking.setTrip(t100);
        newBooking.setNumberOfSeats(3);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        newBooking.setBookingDate(sdf.parse("2023-12-25 12:00:00"));
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify results
        assertFalse("Booking should be denied due to exactly 2 hours cutoff", result);
        assertEquals("Trip T100 seats should remain 50", 50, t100.getNumberOfSeats());
    }
    
    @Test
    public void testCase4_bookingAllowedWithMultipleNonOverlappingTrips() throws Exception {
        // Setup: Customer C9 has booked an existing booking(2023-12-23 12:00, 2 seats) for Trip T199 (2023-12-25 08:00-10:00, 50 seats)
        Customer c9 = new Customer();
        List<Trip> c9Trips = new ArrayList<>();
        
        Trip t199 = new Trip();
        t199.setDepartureTime("2023-12-25 08:00:00");
        t199.setArrivalTime("2023-12-25 10:00:00");
        t199.setNumberOfSeats(50);
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(c9);
        existingBooking.setTrip(t199);
        existingBooking.setNumberOfSeats(2);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        existingBooking.setBookingDate(sdf.parse("2023-12-23 12:00:00"));
        
        List<Booking> t199Bookings = new ArrayList<>();
        t199Bookings.add(existingBooking);
        t199.setBookings(t199Bookings);
        c9Trips.add(t199);
        c9.setTrips(c9Trips);
        
        // Setup: Customer C9 create a new booking(2023-12-23 14:00, 4 seats) for Trip T200 (2023-12-25 12:00-14:00, 40 seats)
        Trip t200 = new Trip();
        t200.setDepartureTime("2023-12-25 12:00:00");
        t200.setArrivalTime("2023-12-25 14:00:00");
        t200.setNumberOfSeats(40);
        
        Booking newBooking = new Booking();
        newBooking.setCustomer(c9);
        newBooking.setTrip(t200);
        newBooking.setNumberOfSeats(4);
        newBooking.setBookingDate(sdf.parse("2023-12-23 14:00:00"));
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify results
        assertTrue("Booking should be allowed with non-overlapping trips", result);
        assertEquals("Trip T200 seats should be updated to 36", 36, t200.getNumberOfSeats());
    }
    
    @Test
    public void testCase5_bookingDeniedWhenCustomerHasOverlappingBooking() throws Exception {
        // Setup: Customer C10 has existing booking(2023-12-23 12:00, 2 seats) for Trip T299 (departure: 2023-12-25 13:00-15:00, 50 seats)
        Customer c10 = new Customer();
        List<Trip> c10Trips = new ArrayList<>();
        
        Trip t299 = new Trip();
        t299.setDepartureTime("2023-12-25 13:00:00");
        t299.setArrivalTime("2023-12-25 15:00:00");
        t299.setNumberOfSeats(50);
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(c10);
        existingBooking.setTrip(t299);
        existingBooking.setNumberOfSeats(2);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        existingBooking.setBookingDate(sdf.parse("2023-12-23 12:00:00"));
        
        List<Booking> t299Bookings = new ArrayList<>();
        t299Bookings.add(existingBooking);
        t299.setBookings(t299Bookings);
        c10Trips.add(t299);
        c10.setTrips(c10Trips);
        
        // Setup: Customer C10 create a new booking(2023-12-23 14:00, 4 seats) for Trip T300 (2023-12-25 14:00-16:00, 40 seats)
        Trip t300 = new Trip();
        t300.setDepartureTime("2023-12-25 14:00:00");
        t300.setArrivalTime("2023-12-25 16:00:00");
        t300.setNumberOfSeats(40);
        
        Booking newBooking = new Booking();
        newBooking.setCustomer(c10);
        newBooking.setTrip(t300);
        newBooking.setNumberOfSeats(4);
        newBooking.setBookingDate(sdf.parse("2023-12-23 14:00:00"));
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify results
        assertFalse("Booking should be denied due to overlapping trips", result);
        assertEquals("Trip T300 seats should remain 40", 40, t300.getNumberOfSeats());
    }
}