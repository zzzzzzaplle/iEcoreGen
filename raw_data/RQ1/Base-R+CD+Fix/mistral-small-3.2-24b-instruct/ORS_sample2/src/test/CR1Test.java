import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR1Test {
    
    private SimpleDateFormat dateFormat;
    private Driver driver1, driver2;
    private Customer customer1, customer2, customer3, customer9, customer10;
    private Trip tripT123, tripT456, tripT100, tripT199, tripT200, tripT299, tripT300;
    private Booking booking1, booking2, booking3, booking4;
    
    @Before
    public void setUp() throws Exception {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
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
        tripT123.setDepartureStation("StationA");
        tripT123.setArrivalStation("StationB");
        tripT123.setNumberOfSeats(5);
        tripT123.setDepartureDate(dateFormat.parse("2023-12-25 00:00:00"));
        tripT123.setDepartureTime("14:00");
        tripT123.setArrivalTime("16:00");
        driver1.getTrips().add(tripT123);
        
        tripT456 = new Trip();
        tripT456.setDepartureStation("StationC");
        tripT456.setArrivalStation("StationD");
        tripT456.setNumberOfSeats(2);
        tripT456.setDepartureDate(dateFormat.parse("2023-12-25 00:00:00"));
        tripT456.setDepartureTime("10:00");
        tripT456.setArrivalTime("12:00");
        driver2.getTrips().add(tripT456);
        
        tripT100 = new Trip();
        tripT100.setDepartureStation("StationE");
        tripT100.setArrivalStation("StationF");
        tripT100.setNumberOfSeats(50);
        tripT100.setDepartureDate(dateFormat.parse("2023-12-25 00:00:00"));
        tripT100.setDepartureTime("14:00");
        tripT100.setArrivalTime("16:00");
        
        tripT199 = new Trip();
        tripT199.setDepartureStation("StationG");
        tripT199.setArrivalStation("StationH");
        tripT199.setNumberOfSeats(50);
        tripT199.setDepartureDate(dateFormat.parse("2023-12-25 00:00:00"));
        tripT199.setDepartureTime("08:00");
        tripT199.setArrivalTime("10:00");
        
        tripT200 = new Trip();
        tripT200.setDepartureStation("StationI");
        tripT200.setArrivalStation("StationJ");
        tripT200.setNumberOfSeats(40);
        tripT200.setDepartureDate(dateFormat.parse("2023-12-25 00:00:00"));
        tripT200.setDepartureTime("12:00");
        tripT200.setArrivalTime("14:00");
        
        tripT299 = new Trip();
        tripT299.setDepartureStation("StationK");
        tripT299.setArrivalStation("StationL");
        tripT299.setNumberOfSeats(50);
        tripT299.setDepartureDate(dateFormat.parse("2023-12-25 00:00:00"));
        tripT299.setDepartureTime("13:00");
        tripT299.setArrivalTime("15:00");
        
        tripT300 = new Trip();
        tripT300.setDepartureStation("StationM");
        tripT300.setArrivalStation("StationN");
        tripT300.setNumberOfSeats(40);
        tripT300.setDepartureDate(dateFormat.parse("2023-12-25 00:00:00"));
        tripT300.setDepartureTime("14:00");
        tripT300.setArrivalTime("16:00");
    }
    
    @Test
    public void testCase1_ValidBookingWithAvailableSeatsAndNoOverlap() throws Exception {
        // Setup: Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer1);
        existingBooking.setTrip(tripT456);
        existingBooking.setNumberOfSeats(3);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-25 11:00:00"));
        tripT456.getBookings().add(existingBooking);
        
        // Set current time to 2023-12-25 11:00 (3 hours before departure)
        Date currentTime = dateFormat.parse("2023-12-25 11:00:00");
        
        // Create new booking for Trip T123
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer1);
        newBooking.setTrip(tripT123);
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDate(currentTime);
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify result and seat update
        assertTrue("Booking should be eligible", result);
        if (result) {
            newBooking.updateTripSeats();
            assertEquals("Trip T123 seats should be updated to 2", 2, tripT123.getNumberOfSeats());
        }
    }
    
    @Test
    public void testCase2_BookingDeniedDueToSeatShortage() throws Exception {
        // Setup: Create Customer C2 with existing booking for T456 (3 seats)
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer2);
        existingBooking.setTrip(tripT456);
        existingBooking.setNumberOfSeats(3);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-25 07:00:00"));
        tripT456.getBookings().add(existingBooking);
        
        // Set current time to 2023-12-25 07:30 (2.5 hours before departure)
        Date currentTime = dateFormat.parse("2023-12-25 07:30:00");
        
        // Create new booking for Trip T456
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer2);
        newBooking.setTrip(tripT456);
        newBooking.setNumberOfSeats(2);
        newBooking.setBookingDate(currentTime);
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify result and that seats remain unchanged
        assertFalse("Booking should be denied due to seat shortage", result);
        assertEquals("Trip T456 seats should remain 2", 2, tripT456.getNumberOfSeats());
    }
    
    @Test
    public void testCase3_BookingDeniedDueToTimeCutoffExactly2HoursBefore() throws Exception {
        // Setup: Create Trip T100 with 50 seats
        // Set current time to 2023-12-25 12:00 (exactly 2 hours before departure)
        Date currentTime = dateFormat.parse("2023-12-25 12:00:00");
        
        // Create new booking for Trip T100
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer3);
        newBooking.setTrip(tripT100);
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDate(currentTime);
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify result and that seats remain unchanged
        assertFalse("Booking should be denied due to exact 2-hour cutoff", result);
        assertEquals("Trip T100 seats should remain 50", 50, tripT100.getNumberOfSeats());
    }
    
    @Test
    public void testCase4_BookingAllowedWithMultipleNonOverlappingTrips() throws Exception {
        // Setup: Customer C9 has existing booking for Trip T199
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer9);
        existingBooking.setTrip(tripT199);
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-23 12:00:00"));
        tripT199.getBookings().add(existingBooking);
        
        // Set current time to 2023-12-23 14:00 for new booking
        Date currentTime = dateFormat.parse("2023-12-23 14:00:00");
        
        // Create new booking for Trip T200
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer9);
        newBooking.setTrip(tripT200);
        newBooking.setNumberOfSeats(4);
        newBooking.setBookingDate(currentTime);
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify result and seat update
        assertTrue("Booking should be allowed with non-overlapping trips", result);
        if (result) {
            newBooking.updateTripSeats();
            assertEquals("Trip T200 seats should be updated to 36", 36, tripT200.getNumberOfSeats());
        }
    }
    
    @Test
    public void testCase5_BookingDeniedWhenCustomerHasOverlappingBooking() throws Exception {
        // Setup: Customer C10 has existing booking for Trip T299
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer10);
        existingBooking.setTrip(tripT299);
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-23 12:00:00"));
        tripT299.getBookings().add(existingBooking);
        
        // Set current time to 2023-12-23 14:00 for new booking
        Date currentTime = dateFormat.parse("2023-12-23 14:00:00");
        
        // Create new booking for Trip T300
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer10);
        newBooking.setTrip(tripT300);
        newBooking.setNumberOfSeats(4);
        newBooking.setBookingDate(currentTime);
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify result and that seats remain unchanged
        assertFalse("Booking should be denied due to overlapping booking", result);
        assertEquals("Trip T300 seats should remain 40", 40, tripT300.getNumberOfSeats());
    }
}