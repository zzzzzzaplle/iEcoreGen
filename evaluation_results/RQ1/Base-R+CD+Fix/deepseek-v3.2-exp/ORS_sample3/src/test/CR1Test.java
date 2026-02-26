import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
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
    
    @Before
    public void setUp() throws Exception {
        // Initialize drivers
        driver1 = new Driver();
        driver2 = new Driver();
        
        // Initialize customers
        customer1 = new Customer();
        customer2 = new Customer();
        customer3 = new Customer();
        customer9 = new Customer();
        customer10 = new Customer();
        
        // Initialize trips
        tripT123 = new Trip();
        tripT456 = new Trip();
        tripT100 = new Trip();
        tripT199 = new Trip();
        tripT200 = new Trip();
        tripT299 = new Trip();
        tripT300 = new Trip();
    }
    
    @Test
    public void testCase1_validBookingWithAvailableSeatsAndNoOverlap() throws Exception {
        // Setup: Create Driver D1 with Trip T123 (2023-12-25 14:00-16:00, seats:5)
        driver1.setID("D1");
        tripT123.setDepartureStation("Station A");
        tripT123.setArrivalStation("Station B");
        tripT123.setNumberOfSeats(5);
        tripT123.setDepartureDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-12-25"));
        tripT123.setDepartureTime("14:00");
        tripT123.setArrivalTime("16:00");
        driver1.getTrips().add(tripT123);
        
        // Setup: Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        customer1.setID("C1");
        Booking existingBooking = new Booking();
        existingBooking.setNumberOfSeats(3);
        existingBooking.setCustomer(customer1);
        
        Trip tripT456 = new Trip();
        tripT456.setDepartureDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-12-25"));
        tripT456.setDepartureTime("14:00");
        tripT456.setArrivalTime("16:00");
        existingBooking.setTrip(tripT456);
        existingBooking.setBookingDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2023-12-25 11:00"));
        customer1.getBookings().add(existingBooking);
        
        // Create new booking for Trip T123
        Booking newBooking = new Booking();
        newBooking.setNumberOfSeats(3);
        newBooking.setCustomer(customer1);
        newBooking.setTrip(tripT123);
        newBooking.setBookingDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2023-12-25 12:00"));
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify result and update seats if eligible
        if (result) {
            newBooking.updateTripSeats();
        }
        
        // Assert expected output
        assertTrue("Booking should be eligible", result);
        assertEquals("Trip T123 seats should be updated to 2", 2, tripT123.getNumberOfSeats());
    }
    
    @Test
    public void testCase2_bookingDeniedDueToSeatShortage() throws Exception {
        // Setup: Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        driver2.setID("D2");
        tripT456.setDepartureStation("Station C");
        tripT456.setArrivalStation("Station D");
        tripT456.setNumberOfSeats(2);
        tripT456.setDepartureDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-12-25"));
        tripT456.setDepartureTime("10:00");
        tripT456.setArrivalTime("12:00");
        driver2.getTrips().add(tripT456);
        
        // Setup: Create Customer C2 with existing booking for T456 (3 seats)
        customer2.setID("C2");
        Booking existingBooking = new Booking();
        existingBooking.setNumberOfSeats(3);
        existingBooking.setCustomer(customer2);
        existingBooking.setTrip(tripT456);
        existingBooking.setBookingDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2023-12-25 07:30"));
        customer2.getBookings().add(existingBooking);
        
        // Create new booking for Trip T456
        Booking newBooking = new Booking();
        newBooking.setNumberOfSeats(3);
        newBooking.setCustomer(customer2);
        newBooking.setTrip(tripT456);
        newBooking.setBookingDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2023-12-25 07:30"));
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify result and update seats if eligible
        if (result) {
            newBooking.updateTripSeats();
        }
        
        // Assert expected output
        assertFalse("Booking should be denied due to seat shortage", result);
        assertEquals("Trip T456 seats should remain 2", 2, tripT456.getNumberOfSeats());
    }
    
    @Test
    public void testCase3_bookingDeniedDueToTimeCutoffExactly2HoursBefore() throws Exception {
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        tripT100.setDepartureStation("Station E");
        tripT100.setArrivalStation("Station F");
        tripT100.setNumberOfSeats(50);
        tripT100.setDepartureDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-12-25"));
        tripT100.setDepartureTime("14:00");
        tripT100.setArrivalTime("16:00");
        
        // Setup: Current time: 2023-12-25 12:00 (exactly 2 hours before)
        customer3.setID("C3");
        
        // Create new booking for Trip T100
        Booking newBooking = new Booking();
        newBooking.setNumberOfSeats(3);
        newBooking.setCustomer(customer3);
        newBooking.setTrip(tripT100);
        newBooking.setBookingDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2023-12-25 12:00"));
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify result and update seats if eligible
        if (result) {
            newBooking.updateTripSeats();
        }
        
        // Assert expected output
        assertFalse("Booking should be denied due to exactly 2 hours cutoff", result);
        assertEquals("Trip T100 seats should remain 50", 50, tripT100.getNumberOfSeats());
    }
    
    @Test
    public void testCase4_bookingAllowedWithMultipleNonOverlappingTrips() throws Exception {
        // Setup: Customer C9 has booked an existing booking(2023-12-23 12:00, 2 seats) for Trip T199 (2023-12-25 08:00-10:00, 50 seats)
        customer9.setID("C9");
        
        tripT199.setDepartureStation("Station G");
        tripT199.setArrivalStation("Station H");
        tripT199.setNumberOfSeats(50);
        tripT199.setDepartureDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-12-25"));
        tripT199.setDepartureTime("08:00");
        tripT199.setArrivalTime("10:00");
        
        Booking existingBooking = new Booking();
        existingBooking.setNumberOfSeats(2);
        existingBooking.setCustomer(customer9);
        existingBooking.setTrip(tripT199);
        existingBooking.setBookingDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2023-12-23 12:00"));
        customer9.getBookings().add(existingBooking);
        
        // Setup: Customer C9 create a new booking(2023-12-23 14:00, 4 seats) for Trip T200 (2023-12-25 12:00-14:00 (no overlap), 40 seats)
        tripT200.setDepartureStation("Station I");
        tripT200.setArrivalStation("Station J");
        tripT200.setNumberOfSeats(40);
        tripT200.setDepartureDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-12-25"));
        tripT200.setDepartureTime("12:00");
        tripT200.setArrivalTime("14:00");
        
        Booking newBooking = new Booking();
        newBooking.setNumberOfSeats(4);
        newBooking.setCustomer(customer9);
        newBooking.setTrip(tripT200);
        newBooking.setBookingDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2023-12-23 14:00"));
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify result and update seats if eligible
        if (result) {
            newBooking.updateTripSeats();
        }
        
        // Assert expected output
        assertTrue("Booking should be allowed with non-overlapping trips", result);
        assertEquals("Trip T200 seats should be updated to 36", 36, tripT200.getNumberOfSeats());
    }
    
    @Test
    public void testCase5_bookingDeniedWhenCustomerHasOverlappingBooking() throws Exception {
        // Setup: Customer C10 has existing booking(2023-12-23 12:00, 2 seats) for Trip T299 (departure: 2023-12-25 13:00-15:00, 50 seats)
        customer10.setID("C10");
        
        tripT299.setDepartureStation("Station K");
        tripT299.setArrivalStation("Station L");
        tripT299.setNumberOfSeats(50);
        tripT299.setDepartureDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-12-25"));
        tripT299.setDepartureTime("13:00");
        tripT299.setArrivalTime("15:00");
        
        Booking existingBooking = new Booking();
        existingBooking.setNumberOfSeats(2);
        existingBooking.setCustomer(customer10);
        existingBooking.setTrip(tripT299);
        existingBooking.setBookingDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2023-12-23 12:00"));
        customer10.getBookings().add(existingBooking);
        
        // Setup: Customer C10 create a new booking(2023-12-23 14:00, 4 seats) for Trip T300 (2023-12-25 14:00-16:00 (1-hour overlap), 40 seats)
        tripT300.setDepartureStation("Station M");
        tripT300.setArrivalStation("Station N");
        tripT300.setNumberOfSeats(40);
        tripT300.setDepartureDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-12-25"));
        tripT300.setDepartureTime("14:00");
        tripT300.setArrivalTime("16:00");
        
        Booking newBooking = new Booking();
        newBooking.setNumberOfSeats(4);
        newBooking.setCustomer(customer10);
        newBooking.setTrip(tripT300);
        newBooking.setBookingDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2023-12-23 14:00"));
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify result and update seats if eligible
        if (result) {
            newBooking.updateTripSeats();
        }
        
        // Assert expected output
        assertFalse("Booking should be denied due to overlapping booking", result);
        assertEquals("Trip T300 seats should remain 40", 40, tripT300.getNumberOfSeats());
    }
}