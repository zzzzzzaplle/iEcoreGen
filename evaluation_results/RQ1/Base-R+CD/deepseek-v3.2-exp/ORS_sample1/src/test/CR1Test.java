import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;

public class CR1Test {
    
    private Driver driver1;
    private Driver driver2;
    private Customer customer1;
    private Customer customer2;
    private Customer customer3;
    private Customer customer9;
    private Customer customer10;
    private Trip tripT123;
    private Trip tripT456;
    private Trip tripT100;
    private Trip tripT199;
    private Trip tripT200;
    private Trip tripT299;
    private Trip tripT300;
    private Booking booking1;
    private Booking booking2;
    private Booking booking3;
    private Booking booking4;
    private Booking booking5;
    
    @Before
    public void setUp() throws Exception {
        // Initialize drivers
        driver1 = new Driver();
        driver1.setID("D1");
        
        driver2 = new Driver();
        driver2.setID("D2");
        
        // Initialize customers
        customer1 = new Customer();
        customer1.setID("C1");
        
        customer2 = new Customer();
        customer2.setID("C2");
        
        customer3 = new Customer();
        customer3.setID("C3");
        
        customer9 = new Customer();
        customer9.setID("C9");
        
        customer10 = new Customer();
        customer10.setID("C10");
        
        // Initialize trips
        tripT123 = new Trip();
        tripT123.setDepartureStation("Station A");
        tripT123.setArrivalStation("Station B");
        tripT123.setNumberOfSeats(5);
        tripT123.setDepartureTime("2023-12-25 14:00");
        tripT123.setArrivalTime("2023-12-25 16:00");
        
        tripT456 = new Trip();
        tripT456.setDepartureStation("Station C");
        tripT456.setArrivalStation("Station D");
        tripT456.setNumberOfSeats(2);
        tripT456.setDepartureTime("2023-12-25 10:00");
        tripT456.setArrivalTime("2023-12-25 12:00");
        
        tripT100 = new Trip();
        tripT100.setDepartureStation("Station E");
        tripT100.setArrivalStation("Station F");
        tripT100.setNumberOfSeats(50);
        tripT100.setDepartureTime("2023-12-25 14:00");
        tripT100.setArrivalTime("2023-12-25 16:00");
        
        tripT199 = new Trip();
        tripT199.setDepartureStation("Station G");
        tripT199.setArrivalStation("Station H");
        tripT199.setNumberOfSeats(50);
        tripT199.setDepartureTime("2023-12-25 08:00");
        tripT199.setArrivalTime("2023-12-25 10:00");
        
        tripT200 = new Trip();
        tripT200.setDepartureStation("Station I");
        tripT200.setArrivalStation("Station J");
        tripT200.setNumberOfSeats(40);
        tripT200.setDepartureTime("2023-12-25 12:00");
        tripT200.setArrivalTime("2023-12-25 14:00");
        
        tripT299 = new Trip();
        tripT299.setDepartureStation("Station K");
        tripT299.setArrivalStation("Station L");
        tripT299.setNumberOfSeats(50);
        tripT299.setDepartureTime("2023-12-25 13:00");
        tripT299.setArrivalTime("2023-12-25 15:00");
        
        tripT300 = new Trip();
        tripT300.setDepartureStation("Station M");
        tripT300.setArrivalStation("Station N");
        tripT300.setNumberOfSeats(40);
        tripT300.setDepartureTime("2023-12-25 14:00");
        tripT300.setArrivalTime("2023-12-25 16:00");
        
        // Initialize bookings
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        booking1 = new Booking();
        booking1.setCustomer(customer1);
        booking1.setTrip(tripT456);
        booking1.setNumberOfSeats(3);
        booking1.setBookingDate(sdf.parse("2023-12-25 11:00:00"));
        
        booking2 = new Booking();
        booking2.setCustomer(customer2);
        booking2.setTrip(tripT456);
        booking2.setNumberOfSeats(3);
        booking2.setBookingDate(sdf.parse("2023-12-25 07:30:00"));
        
        booking3 = new Booking();
        booking3.setCustomer(customer3);
        booking3.setTrip(tripT100);
        booking3.setNumberOfSeats(3);
        booking3.setBookingDate(sdf.parse("2023-12-25 12:00:00"));
        
        booking4 = new Booking();
        booking4.setCustomer(customer9);
        booking4.setTrip(tripT199);
        booking4.setNumberOfSeats(2);
        booking4.setBookingDate(sdf.parse("2023-12-23 12:00:00"));
        
        booking5 = new Booking();
        booking5.setCustomer(customer10);
        booking5.setTrip(tripT299);
        booking5.setNumberOfSeats(2);
        booking5.setBookingDate(sdf.parse("2023-12-23 12:00:00"));
        
        // Set up driver trips
        List<Trip> driver1Trips = new ArrayList<>();
        driver1Trips.add(tripT123);
        driver1.setTrips(driver1Trips);
        
        List<Trip> driver2Trips = new ArrayList<>();
        driver2Trips.add(tripT456);
        driver2.setTrips(driver2Trips);
    }
    
    @Test
    public void testCase1_ValidBookingWithAvailableSeatsAndNoOverlap() throws Exception {
        // Setup: Create Driver D1 with Trip T123 (2023-12-25 14:00-16:00, seats:5)
        // Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00 (3 hours before departure)
        
        // Create new booking for Trip T123
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer1);
        newBooking.setTrip(tripT123);
        newBooking.setNumberOfSeats(3);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        newBooking.setBookingDate(sdf.parse("2023-12-25 11:00:00"));
        
        // Check eligibility and update seats
        boolean result = newBooking.isBookingEligible();
        if (result) {
            newBooking.updateTripSeats();
        }
        
        // Verify results
        assertTrue("Booking should be eligible", result);
        assertEquals("Trip T123 seats should be updated to 2", 2, tripT123.getNumberOfSeats());
    }
    
    @Test
    public void testCase2_BookingDeniedDueToSeatShortage() throws Exception {
        // Setup: Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        // Create Customer C2 with existing booking for T456 (3 seats)
        // Current time: 2023-12-25 07:30 (2.5 hours before departure)
        
        // Create new booking for Trip T456
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer2);
        newBooking.setTrip(tripT456);
        newBooking.setNumberOfSeats(3);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        newBooking.setBookingDate(sdf.parse("2023-12-25 07:30:00"));
        
        // Check eligibility and update seats
        boolean result = newBooking.isBookingEligible();
        if (result) {
            newBooking.updateTripSeats();
        }
        
        // Verify results
        assertFalse("Booking should be denied due to seat shortage", result);
        assertEquals("Trip T456 seats should remain 2", 2, tripT456.getNumberOfSeats());
    }
    
    @Test
    public void testCase3_BookingDeniedDueToTimeCutoffExactly2HoursBefore() throws Exception {
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        // Current time: 2023-12-25 12:00 (exactly 2 hours before)
        // Create Customer C3 with booking for T100 (3 seats)
        
        // Create new booking for Trip T100
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer3);
        newBooking.setTrip(tripT100);
        newBooking.setNumberOfSeats(3);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        newBooking.setBookingDate(sdf.parse("2023-12-25 12:00:00"));
        
        // Check eligibility and update seats
        boolean result = newBooking.isBookingEligible();
        if (result) {
            newBooking.updateTripSeats();
        }
        
        // Verify results
        assertFalse("Booking should be denied due to exactly 2 hours cutoff", result);
        assertEquals("Trip T100 seats should remain 50", 50, tripT100.getNumberOfSeats());
    }
    
    @Test
    public void testCase4_BookingAllowedWithMultipleNonOverlappingTrips() throws Exception {
        // Setup: Customer C9 has booked an existing booking(2023-12-23 12:00, 2 seats) for Trip T199 (2023-12-25 08:00-10:00, 50 seats)
        // Customer C9 create a new booking(2023-12-23 14:00, 4 seats) for Trip T200 (2023-12-25 12:00-14:00 (no overlap), 40 seats)
        
        // Create new booking for Trip T200
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer9);
        newBooking.setTrip(tripT200);
        newBooking.setNumberOfSeats(4);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        newBooking.setBookingDate(sdf.parse("2023-12-23 14:00:00"));
        
        // Check eligibility and update seats
        boolean result = newBooking.isBookingEligible();
        if (result) {
            newBooking.updateTripSeats();
        }
        
        // Verify results
        assertTrue("Booking should be allowed with non-overlapping trips", result);
        assertEquals("Trip T200 seats should be updated to 36", 36, tripT200.getNumberOfSeats());
    }
    
    @Test
    public void testCase5_BookingDeniedWhenCustomerHasOverlappingBooking() throws Exception {
        // Setup: Customer C10 has existing booking(2023-12-23 12:00, 2 seats) for Trip T299 (departure: 2023-12-25 13:00-15:00, 50 seats)
        // Customer C10 create a new booking(2023-12-23 14:00, 4 seats) for Trip T300 (2023-12-25 14:00-16:00 (1-hour overlap), 40 seats)
        
        // Create new booking for Trip T300
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer10);
        newBooking.setTrip(tripT300);
        newBooking.setNumberOfSeats(4);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        newBooking.setBookingDate(sdf.parse("2023-12-23 14:00:00"));
        
        // Check eligibility and update seats
        boolean result = newBooking.isBookingEligible();
        if (result) {
            newBooking.updateTripSeats();
        }
        
        // Verify results
        assertFalse("Booking should be denied due to overlapping time periods", result);
        assertEquals("Trip T300 seats should remain 40", 40, tripT300.getNumberOfSeats());
    }
}