import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR1Test {
    
    private Trip tripT123;
    private Trip tripT456;
    private Trip tripT100;
    private Trip tripT199;
    private Trip tripT200;
    private Trip tripT299;
    private Trip tripT300;
    private Customer customerC1;
    private Customer customerC2;
    private Customer customerC3;
    private Customer customerC9;
    private Customer customerC10;
    private Driver driverD1;
    private Driver driverD2;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() throws Exception {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        // Initialize drivers
        driverD1 = new Driver();
        driverD1.setID("D1");
        
        driverD2 = new Driver();
        driverD2.setID("D2");
        
        // Initialize trips
        tripT123 = new Trip();
        tripT123.setDepartureStation("Station A");
        tripT123.setArrivalStation("Station B");
        tripT123.setDepartureTime("2023-12-25 14:00");
        tripT123.setArrivalTime("2023-12-25 16:00");
        tripT123.setNumberOfSeats(5);
        tripT123.setDepartureDate(dateFormat.parse("2023-12-25 14:00"));
        
        tripT456 = new Trip();
        tripT456.setDepartureStation("Station C");
        tripT456.setArrivalStation("Station D");
        tripT456.setDepartureTime("2023-12-25 10:00");
        tripT456.setArrivalTime("2023-12-25 12:00");
        tripT456.setNumberOfSeats(2);
        tripT456.setDepartureDate(dateFormat.parse("2023-12-25 10:00"));
        
        tripT100 = new Trip();
        tripT100.setDepartureStation("Station E");
        tripT100.setArrivalStation("Station F");
        tripT100.setDepartureTime("2023-12-25 14:00");
        tripT100.setArrivalTime("2023-12-25 16:00");
        tripT100.setNumberOfSeats(50);
        tripT100.setDepartureDate(dateFormat.parse("2023-12-25 14:00"));
        
        tripT199 = new Trip();
        tripT199.setDepartureStation("Station G");
        tripT199.setArrivalStation("Station H");
        tripT199.setDepartureTime("2023-12-25 08:00");
        tripT199.setArrivalTime("2023-12-25 10:00");
        tripT199.setNumberOfSeats(50);
        tripT199.setDepartureDate(dateFormat.parse("2023-12-25 08:00"));
        
        tripT200 = new Trip();
        tripT200.setDepartureStation("Station I");
        tripT200.setArrivalStation("Station J");
        tripT200.setDepartureTime("2023-12-25 12:00");
        tripT200.setArrivalTime("2023-12-25 14:00");
        tripT200.setNumberOfSeats(40);
        tripT200.setDepartureDate(dateFormat.parse("2023-12-25 12:00"));
        
        tripT299 = new Trip();
        tripT299.setDepartureStation("Station K");
        tripT299.setArrivalStation("Station L");
        tripT299.setDepartureTime("2023-12-25 13:00");
        tripT299.setArrivalTime("2023-12-25 15:00");
        tripT299.setNumberOfSeats(50);
        tripT299.setDepartureDate(dateFormat.parse("2023-12-25 13:00"));
        
        tripT300 = new Trip();
        tripT300.setDepartureStation("Station M");
        tripT300.setArrivalStation("Station N");
        tripT300.setDepartureTime("2023-12-25 14:00");
        tripT300.setArrivalTime("2023-12-25 16:00");
        tripT300.setNumberOfSeats(40);
        tripT300.setDepartureDate(dateFormat.parse("2023-12-25 14:00"));
        
        // Initialize customers
        customerC1 = new Customer();
        customerC1.setID("C1");
        
        customerC2 = new Customer();
        customerC2.setID("C2");
        
        customerC3 = new Customer();
        customerC3.setID("C3");
        
        customerC9 = new Customer();
        customerC9.setID("C9");
        
        customerC10 = new Customer();
        customerC10.setID("C10");
        
        // Add trips to drivers
        driverD1.addTrip(tripT123);
        driverD2.addTrip(tripT456);
    }
    
    @Test
    public void testCase1_ValidBookingWithAvailableSeatsAndNoOverlap() throws Exception {
        // Setup: Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customerC1);
        existingBooking.setTrip(tripT456);
        existingBooking.setNumberOfSeats(3);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-25 11:00"));
        tripT456.getBookings().add(existingBooking);
        
        // Set current time to 2023-12-25 11:00 (3 hours before departure)
        Date currentTime = dateFormat.parse("2023-12-25 11:00");
        
        // Create booking for Trip T123
        Booking newBooking = new Booking();
        newBooking.setCustomer(customerC1);
        newBooking.setTrip(tripT123);
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDate(currentTime);
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify result and seat update
        assertTrue("Booking should be eligible", result);
        if (result) {
            newBooking.updateTripSeats();
        }
        assertEquals("Trip T123 seats should be updated to 2", 2, tripT123.getNumberOfSeats());
    }
    
    @Test
    public void testCase2_BookingDeniedDueToSeatShortage() throws Exception {
        // Setup: Create Customer C2 with existing booking for T456 (3 seats)
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customerC2);
        existingBooking.setTrip(tripT456);
        existingBooking.setNumberOfSeats(3);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-25 07:30"));
        tripT456.getBookings().add(existingBooking);
        
        // Set current time to 2023-12-25 07:30 (2.5 hours before departure)
        Date currentTime = dateFormat.parse("2023-12-25 07:30");
        
        // Create booking for Trip T456
        Booking newBooking = new Booking();
        newBooking.setCustomer(customerC2);
        newBooking.setTrip(tripT456);
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDate(currentTime);
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify result and seat remains unchanged
        assertFalse("Booking should be denied due to seat shortage", result);
        assertEquals("Trip T456 seats should remain 2", 2, tripT456.getNumberOfSeats());
    }
    
    @Test
    public void testCase3_BookingDeniedDueToTimeCutoffExactly2HoursBefore() throws Exception {
        // Setup: Current time: 2023-12-25 12:00 (exactly 2 hours before)
        Date currentTime = dateFormat.parse("2023-12-25 12:00");
        
        // Create booking for Trip T100
        Booking newBooking = new Booking();
        newBooking.setCustomer(customerC3);
        newBooking.setTrip(tripT100);
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDate(currentTime);
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify result and seat remains unchanged
        assertFalse("Booking should be denied due to time cutoff", result);
        assertEquals("Trip T100 seats should remain 50", 50, tripT100.getNumberOfSeats());
    }
    
    @Test
    public void testCase4_BookingAllowedWithMultipleNonOverlappingTrips() throws Exception {
        // Setup: Customer C9 has booked an existing booking(2023-12-23 12:00, 2 seats) for Trip T199
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customerC9);
        existingBooking.setTrip(tripT199);
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-23 12:00"));
        tripT199.getBookings().add(existingBooking);
        
        // Customer C9 create a new booking(2023-12-23 14:00, 4 seats) for Trip T200
        Date currentTime = dateFormat.parse("2023-12-23 14:00");
        
        Booking newBooking = new Booking();
        newBooking.setCustomer(customerC9);
        newBooking.setTrip(tripT200);
        newBooking.setNumberOfSeats(4);
        newBooking.setBookingDate(currentTime);
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify result and seat update
        assertTrue("Booking should be eligible", result);
        if (result) {
            newBooking.updateTripSeats();
        }
        assertEquals("Trip T200 seats should be updated to 36", 36, tripT200.getNumberOfSeats());
    }
    
    @Test
    public void testCase5_BookingDeniedWhenCustomerHasOverlappingBooking() throws Exception {
        // Setup: Customer C10 has existing booking(2023-12-23 12:00, 2 seats) for Trip T299
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customerC10);
        existingBooking.setTrip(tripT299);
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-23 12:00"));
        tripT299.getBookings().add(existingBooking);
        
        // Customer C10 create a new booking(2023-12-23 14:00, 4 seats) for Trip T300
        Date currentTime = dateFormat.parse("2023-12-23 14:00");
        
        Booking newBooking = new Booking();
        newBooking.setCustomer(customerC10);
        newBooking.setTrip(tripT300);
        newBooking.setNumberOfSeats(4);
        newBooking.setBookingDate(currentTime);
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify result and seat remains unchanged
        assertFalse("Booking should be denied due to overlapping booking", result);
        assertEquals("Trip T300 seats should remain 40", 40, tripT300.getNumberOfSeats());
    }
}