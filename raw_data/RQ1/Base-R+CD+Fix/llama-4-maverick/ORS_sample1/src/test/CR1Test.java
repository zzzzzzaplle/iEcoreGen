import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR1Test {
    
    private SimpleDateFormat sdf;
    private Driver driver1, driver2;
    private Customer customer1, customer2, customer3, customer9, customer10;
    private Trip tripT123, tripT456, tripT100, tripT199, tripT200, tripT299, tripT300;
    private Booking bookingT456_C1, bookingT456_C2, bookingT100_C3, bookingT199_C9, bookingT299_C10;
    
    @Before
    public void setUp() throws ParseException {
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        // Initialize drivers
        driver1 = new Driver();
        driver2 = new Driver();
        
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
        tripT123.setDepartureDate(sdf.parse("2023-12-25 14:00"));
        tripT123.setDepartureTime("14:00");
        tripT123.setArrivalTime("16:00");
        tripT123.setNumberOfSeats(5);
        
        tripT456 = new Trip();
        tripT456.setDepartureDate(sdf.parse("2023-12-25 10:00"));
        tripT456.setDepartureTime("10:00");
        tripT456.setArrivalTime("12:00");
        tripT456.setNumberOfSeats(2);
        
        tripT100 = new Trip();
        tripT100.setDepartureDate(sdf.parse("2023-12-25 14:00"));
        tripT100.setDepartureTime("14:00");
        tripT100.setArrivalTime("16:00");
        tripT100.setNumberOfSeats(50);
        
        tripT199 = new Trip();
        tripT199.setDepartureDate(sdf.parse("2023-12-25 08:00"));
        tripT199.setDepartureTime("08:00");
        tripT199.setArrivalTime("10:00");
        tripT199.setNumberOfSeats(50);
        
        tripT200 = new Trip();
        tripT200.setDepartureDate(sdf.parse("2023-12-25 12:00"));
        tripT200.setDepartureTime("12:00");
        tripT200.setArrivalTime("14:00");
        tripT200.setNumberOfSeats(40);
        
        tripT299 = new Trip();
        tripT299.setDepartureDate(sdf.parse("2023-12-25 13:00"));
        tripT299.setDepartureTime("13:00");
        tripT299.setArrivalTime("15:00");
        tripT299.setNumberOfSeats(50);
        
        tripT300 = new Trip();
        tripT300.setDepartureDate(sdf.parse("2023-12-25 14:00"));
        tripT300.setDepartureTime("14:00");
        tripT300.setArrivalTime("16:00");
        tripT300.setNumberOfSeats(40);
        
        // Initialize bookings
        bookingT456_C1 = new Booking();
        bookingT456_C1.setCustomer(customer1);
        bookingT456_C1.setTrip(tripT456);
        bookingT456_C1.setNumberOfSeats(3);
        bookingT456_C1.setBookingDate(sdf.parse("2023-12-25 11:00"));
        
        bookingT456_C2 = new Booking();
        bookingT456_C2.setCustomer(customer2);
        bookingT456_C2.setTrip(tripT456);
        bookingT456_C2.setNumberOfSeats(3);
        bookingT456_C2.setBookingDate(sdf.parse("2023-12-25 07:30"));
        
        bookingT100_C3 = new Booking();
        bookingT100_C3.setCustomer(customer3);
        bookingT100_C3.setTrip(tripT100);
        bookingT100_C3.setNumberOfSeats(3);
        bookingT100_C3.setBookingDate(sdf.parse("2023-12-25 12:00"));
        
        bookingT199_C9 = new Booking();
        bookingT199_C9.setCustomer(customer9);
        bookingT199_C9.setTrip(tripT199);
        bookingT199_C9.setNumberOfSeats(2);
        bookingT199_C9.setBookingDate(sdf.parse("2023-12-23 12:00"));
        
        bookingT299_C10 = new Booking();
        bookingT299_C10.setCustomer(customer10);
        bookingT299_C10.setTrip(tripT299);
        bookingT299_C10.setNumberOfSeats(2);
        bookingT299_C10.setBookingDate(sdf.parse("2023-12-23 12:00"));
    }
    
    @Test
    public void testCase1_ValidBookingWithAvailableSeatsAndNoOverlap() throws ParseException {
        // Create test data: Trip T123 with 5 seats, Customer C1 with booking for T456
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer1);
        newBooking.setTrip(tripT123);
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDate(sdf.parse("2023-12-25 11:00"));
        
        // Check eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify result and seat update
        assertTrue("Booking should be eligible", result);
        if (result) {
            newBooking.updateTripSeats();
            assertEquals("Trip seats should be updated to 2", 2, tripT123.getNumberOfSeats());
        }
    }
    
    @Test
    public void testCase2_BookingDeniedDueToSeatShortage() throws ParseException {
        // Create test data: Trip T456 with 2 seats, Customer C2 with booking for T456 (3 seats)
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer2);
        newBooking.setTrip(tripT456);
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDate(sdf.parse("2023-12-25 07:30"));
        
        // Check eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify result and seat remains unchanged
        assertFalse("Booking should be denied due to seat shortage", result);
        assertEquals("Trip seats should remain 2", 2, tripT456.getNumberOfSeats());
    }
    
    @Test
    public void testCase3_BookingDeniedDueToTimeCutoffExactly2HoursBefore() throws ParseException {
        // Create test data: Trip T100 with 50 seats, Customer C3 with booking exactly 2 hours before
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer3);
        newBooking.setTrip(tripT100);
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDate(sdf.parse("2023-12-25 12:00"));
        
        // Check eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify result and seat remains unchanged
        assertFalse("Booking should be denied due to exactly 2 hours cutoff", result);
        assertEquals("Trip seats should remain 50", 50, tripT100.getNumberOfSeats());
    }
    
    @Test
    public void testCase4_BookingAllowedWithMultipleNonOverlappingTrips() throws ParseException {
        // Create test data: Customer C9 has existing booking for T199, new booking for T200 (no overlap)
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer9);
        newBooking.setTrip(tripT200);
        newBooking.setNumberOfSeats(4);
        newBooking.setBookingDate(sdf.parse("2023-12-23 14:00"));
        
        // Check eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify result and seat update
        assertTrue("Booking should be allowed for non-overlapping trips", result);
        if (result) {
            newBooking.updateTripSeats();
            assertEquals("Trip seats should be updated to 36", 36, tripT200.getNumberOfSeats());
        }
    }
    
    @Test
    public void testCase5_BookingDeniedWhenCustomerHasOverlappingBooking() throws ParseException {
        // Create test data: Customer C10 has existing booking for T299, new booking for T300 (1-hour overlap)
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer10);
        newBooking.setTrip(tripT300);
        newBooking.setNumberOfSeats(4);
        newBooking.setBookingDate(sdf.parse("2023-12-23 14:00"));
        
        // Check eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify result and seat remains unchanged
        assertFalse("Booking should be denied due to overlapping trips", result);
        assertEquals("Trip seats should remain 40", 40, tripT300.getNumberOfSeats());
    }
}