import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Driver driver1;
    private Driver driver2;
    private Customer customer1;
    private Customer customer2;
    private Customer customer3;
    private Customer customer9;
    private Customer customer10;
    private Trip trip123;
    private Trip trip456;
    private Trip trip100;
    private Trip trip199;
    private Trip trip200;
    private Trip trip299;
    private Trip trip300;
    private Booking existingBooking1;
    private Booking existingBooking2;
    private Booking existingBooking3;
    private Booking existingBooking4;
    
    @Before
    public void setUp() throws Exception {
        // Initialize all test objects
        driver1 = new Driver();
        driver1.setID("D1");
        
        driver2 = new Driver();
        driver2.setID("D2");
        
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
        
        // Trip T123: 2023-12-25 14:00-16:00, seats:5
        trip123 = new Trip();
        trip123.setDepartureTime("2023-12-25 14:00");
        trip123.setArrivalTime("2023-12-25 16:00");
        trip123.setNumberOfSeats(5);
        
        // Trip T456: 2023-12-25 10:00-12:00, seats:2
        trip456 = new Trip();
        trip456.setDepartureTime("2023-12-25 10:00");
        trip456.setArrivalTime("2023-12-25 12:00");
        trip456.setNumberOfSeats(2);
        
        // Trip T100: 2023-12-25 14:00-16:00, seats:50
        trip100 = new Trip();
        trip100.setDepartureTime("2023-12-25 14:00");
        trip100.setArrivalTime("2023-12-25 16:00");
        trip100.setNumberOfSeats(50);
        
        // Trip T199: 2023-12-25 08:00-10:00, seats:50
        trip199 = new Trip();
        trip199.setDepartureTime("2023-12-25 08:00");
        trip199.setArrivalTime("2023-12-25 10:00");
        trip199.setNumberOfSeats(50);
        
        // Trip T200: 2023-12-25 12:00-14:00, seats:40
        trip200 = new Trip();
        trip200.setDepartureTime("2023-12-25 12:00");
        trip200.setArrivalTime("2023-12-25 14:00");
        trip200.setNumberOfSeats(40);
        
        // Trip T299: 2023-12-25 13:00-15:00, seats:50
        trip299 = new Trip();
        trip299.setDepartureTime("2023-12-25 13:00");
        trip299.setArrivalTime("2023-12-25 15:00");
        trip299.setNumberOfSeats(50);
        
        // Trip T300: 2023-12-25 14:00-16:00, seats:40
        trip300 = new Trip();
        trip300.setDepartureTime("2023-12-25 14:00");
        trip300.setArrivalTime("2023-12-25 16:00");
        trip300.setNumberOfSeats(40);
        
        // Set up existing bookings
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Customer C1 existing booking for T456 (3 seats), booking time: 2023-12-25 11:00
        existingBooking1 = new Booking();
        existingBooking1.setCustomer(customer1);
        existingBooking1.setTrip(trip456);
        existingBooking1.setNumberOfSeats(3);
        existingBooking1.setBookingDate(sdf.parse("2023-12-25 11:00:00"));
        
        // Customer C2 existing booking for T456 (3 seats)
        existingBooking2 = new Booking();
        existingBooking2.setCustomer(customer2);
        existingBooking2.setTrip(trip456);
        existingBooking2.setNumberOfSeats(3);
        existingBooking2.setBookingDate(sdf.parse("2023-12-25 07:30:00"));
        
        // Customer C9 existing booking for T199 (2 seats), booking time: 2023-12-23 12:00
        existingBooking3 = new Booking();
        existingBooking3.setCustomer(customer9);
        existingBooking3.setTrip(trip199);
        existingBooking3.setNumberOfSeats(2);
        existingBooking3.setBookingDate(sdf.parse("2023-12-23 12:00:00"));
        
        // Customer C10 existing booking for T299 (2 seats), booking time: 2023-12-23 12:00
        existingBooking4 = new Booking();
        existingBooking4.setCustomer(customer10);
        existingBooking4.setTrip(trip299);
        existingBooking4.setNumberOfSeats(2);
        existingBooking4.setBookingDate(sdf.parse("2023-12-23 12:00:00"));
        
        // Set up customer bookings lists
        List<Booking> customer1Bookings = new ArrayList<>();
        customer1Bookings.add(existingBooking1);
        customer1.setBookings(customer1Bookings);
        
        List<Booking> customer2Bookings = new ArrayList<>();
        customer2Bookings.add(existingBooking2);
        customer2.setBookings(customer2Bookings);
        
        List<Booking> customer9Bookings = new ArrayList<>();
        customer9Bookings.add(existingBooking3);
        customer9.setBookings(customer9Bookings);
        
        List<Booking> customer10Bookings = new ArrayList<>();
        customer10Bookings.add(existingBooking4);
        customer10.setBookings(customer10Bookings);
        
        // Set up trip bookings lists
        List<Booking> trip456Bookings = new ArrayList<>();
        trip456Bookings.add(existingBooking1);
        trip456Bookings.add(existingBooking2);
        trip456.setBookings(trip456Bookings);
        
        List<Booking> trip199Bookings = new ArrayList<>();
        trip199Bookings.add(existingBooking3);
        trip199.setBookings(trip199Bookings);
        
        List<Booking> trip299Bookings = new ArrayList<>();
        trip299Bookings.add(existingBooking4);
        trip299.setBookings(trip299Bookings);
    }
    
    @Test
    public void testCase1_ValidBookingWithAvailableSeatsAndNoOverlap() throws Exception {
        // Setup: Set current time to 2023-12-25 11:00 (3 hours before departure)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentTime = sdf.parse("2023-12-25 11:00:00");
        
        // Create booking for Trip T123 with 3 seats
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer1);
        newBooking.setTrip(trip123);
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDate(currentTime);
        
        // Test booking eligibility
        boolean isEligible = newBooking.isBookingEligible();
        
        // Verify eligibility and seat update
        assertTrue("Booking should be eligible", isEligible);
        
        // Update seats if eligible
        if (isEligible) {
            newBooking.updateTripSeats();
            assertEquals("Trip T123 seats should be updated to 2", 2, trip123.getNumberOfSeats());
        }
    }
    
    @Test
    public void testCase2_BookingDeniedDueToSeatShortage() throws Exception {
        // Setup: Set current time to 2023-12-25 07:30 (2.5 hours before departure)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentTime = sdf.parse("2023-12-25 07:30:00");
        
        // Create booking for Trip T456 with 3 seats
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer2);
        newBooking.setTrip(trip456);
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDate(currentTime);
        
        // Test booking eligibility
        boolean isEligible = newBooking.isBookingEligible();
        
        // Verify eligibility and no seat update
        assertFalse("Booking should not be eligible due to seat shortage", isEligible);
        assertEquals("Trip T456 seats should remain 2", 2, trip456.getNumberOfSeats());
    }
    
    @Test
    public void testCase3_BookingDeniedDueToTimeCutoffExactly2HoursBefore() throws Exception {
        // Setup: Set current time to 2023-12-25 12:00 (exactly 2 hours before departure)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentTime = sdf.parse("2023-12-25 12:00:00");
        
        // Create booking for Trip T100 with 3 seats
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer3);
        newBooking.setTrip(trip100);
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDate(currentTime);
        
        // Test booking eligibility
        boolean isEligible = newBooking.isBookingEligible();
        
        // Verify eligibility and no seat update
        assertFalse("Booking should not be eligible due to exactly 2 hours cutoff", isEligible);
        assertEquals("Trip T100 seats should remain 50", 50, trip100.getNumberOfSeats());
    }
    
    @Test
    public void testCase4_BookingAllowedWithMultipleNonOverlappingTrips() throws Exception {
        // Setup: Set current time to 2023-12-23 14:00
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentTime = sdf.parse("2023-12-23 14:00:00");
        
        // Create booking for Trip T200 with 4 seats
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer9);
        newBooking.setTrip(trip200);
        newBooking.setNumberOfSeats(4);
        newBooking.setBookingDate(currentTime);
        
        // Test booking eligibility
        boolean isEligible = newBooking.isBookingEligible();
        
        // Verify eligibility and seat update
        assertTrue("Booking should be eligible with non-overlapping trips", isEligible);
        
        // Update seats if eligible
        if (isEligible) {
            newBooking.updateTripSeats();
            assertEquals("Trip T200 seats should be updated to 36", 36, trip200.getNumberOfSeats());
        }
    }
    
    @Test
    public void testCase5_BookingDeniedWhenCustomerHasOverlappingBooking() throws Exception {
        // Setup: Set current time to 2023-12-23 14:00
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentTime = sdf.parse("2023-12-23 14:00:00");
        
        // Create booking for Trip T300 with 4 seats
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer10);
        newBooking.setTrip(trip300);
        newBooking.setNumberOfSeats(4);
        newBooking.setBookingDate(currentTime);
        
        // Test booking eligibility
        boolean isEligible = newBooking.isBookingEligible();
        
        // Verify eligibility and no seat update
        assertFalse("Booking should not be eligible due to overlapping booking", isEligible);
        assertEquals("Trip T300 seats should remain 40", 40, trip300.getNumberOfSeats());
    }
}