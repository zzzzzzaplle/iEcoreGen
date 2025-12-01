import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CR1Test {
    
    private Trip tripT123;
    private Trip tripT456;
    private Trip tripT100;
    private Trip tripT200;
    private Trip tripT300;
    private Trip tripT199;
    private Trip tripT299;
    private Customer customerC1;
    private Customer customerC2;
    private Customer customerC3;
    private Customer customerC9;
    private Customer customerC10;
    private Driver driverD1;
    private Driver driverD2;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Initialize drivers
        driverD1 = new Driver();
        driverD1.setID("D1");
        
        driverD2 = new Driver();
        driverD2.setID("D2");
        
        // Initialize trips
        tripT123 = new Trip();
        tripT123.setDepartureStation("StationA");
        tripT123.setArrivalStation("StationB");
        tripT123.setNumberOfSeats(5);
        tripT123.setDepartureDate("2023-12-25");
        tripT123.setDepartureTime("14:00");
        tripT123.setArrivalTime("16:00");
        tripT123.setPrice(50.0);
        driverD1.getTrips().add(tripT123);
        
        tripT456 = new Trip();
        tripT456.setDepartureStation("StationC");
        tripT456.setArrivalStation("StationD");
        tripT456.setNumberOfSeats(2);
        tripT456.setDepartureDate("2023-12-25");
        tripT456.setDepartureTime("10:00");
        tripT456.setArrivalTime("12:00");
        tripT456.setPrice(40.0);
        driverD2.getTrips().add(tripT456);
        
        tripT100 = new Trip();
        tripT100.setDepartureStation("StationE");
        tripT100.setArrivalStation("StationF");
        tripT100.setNumberOfSeats(50);
        tripT100.setDepartureDate("2023-12-25");
        tripT100.setDepartureTime("14:00");
        tripT100.setArrivalTime("16:00");
        tripT100.setPrice(60.0);
        
        tripT200 = new Trip();
        tripT200.setDepartureStation("StationG");
        tripT200.setArrivalStation("StationH");
        tripT200.setNumberOfSeats(40);
        tripT200.setDepartureDate("2023-12-25");
        tripT200.setDepartureTime("12:00");
        tripT200.setArrivalTime("14:00");
        tripT200.setPrice(55.0);
        
        tripT300 = new Trip();
        tripT300.setDepartureStation("StationI");
        tripT300.setArrivalStation("StationJ");
        tripT300.setNumberOfSeats(40);
        tripT300.setDepartureDate("2023-12-25");
        tripT300.setDepartureTime("14:00");
        tripT300.setArrivalTime("16:00");
        tripT300.setPrice(65.0);
        
        tripT199 = new Trip();
        tripT199.setDepartureStation("StationK");
        tripT199.setArrivalStation("StationL");
        tripT199.setNumberOfSeats(50);
        tripT199.setDepartureDate("2023-12-25");
        tripT199.setDepartureTime("08:00");
        tripT199.setArrivalTime("10:00");
        tripT199.setPrice(45.0);
        
        tripT299 = new Trip();
        tripT299.setDepartureStation("StationM");
        tripT299.setArrivalStation("StationN");
        tripT299.setNumberOfSeats(50);
        tripT299.setDepartureDate("2023-12-25");
        tripT299.setDepartureTime("13:00");
        tripT299.setArrivalTime("15:00");
        tripT299.setPrice(70.0);
        
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
    }
    
    @Test
    public void testCase1_ValidBookingWithAvailableSeatsAndNoOverlap() throws Exception {
        // Setup: Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customerC1);
        existingBooking.setTrip(tripT456);
        existingBooking.setNumberOfSeats(3);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-25 11:00:00"));
        tripT456.getBookings().add(existingBooking);
        
        // Create new booking for T123
        Booking newBooking = new Booking();
        newBooking.setCustomer(customerC1);
        newBooking.setTrip(tripT123);
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDate(dateFormat.parse("2023-12-25 11:00:00")); // 3 hours before departure
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify results
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
        existingBooking.setNumberOfSeats(3); // This exceeds the available 2 seats
        existingBooking.setBookingDate(dateFormat.parse("2023-12-25 07:30:00"));
        tripT456.getBookings().add(existingBooking);
        
        // Create new booking attempt for T456
        Booking newBooking = new Booking();
        newBooking.setCustomer(customerC2);
        newBooking.setTrip(tripT456);
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDate(dateFormat.parse("2023-12-25 07:30:00")); // 2.5 hours before departure
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify results
        assertFalse("Booking should be denied due to seat shortage", result);
        assertEquals("Trip T456 seats should remain 2", 2, tripT456.getNumberOfSeats());
    }
    
    @Test
    public void testCase3_BookingDeniedDueToTimeCutoffExactly2HoursBefore() throws Exception {
        // Setup: Create Trip T100 and booking attempt exactly 2 hours before departure
        Booking newBooking = new Booking();
        newBooking.setCustomer(customerC3);
        newBooking.setTrip(tripT100);
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDate(dateFormat.parse("2023-12-25 12:00:00")); // Exactly 2 hours before departure
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify results
        assertFalse("Booking should be denied when exactly 2 hours before departure", result);
        assertEquals("Trip T100 seats should remain 50", 50, tripT100.getNumberOfSeats());
    }
    
    @Test
    public void testCase4_BookingAllowedWithMultipleNonOverlappingTrips() throws Exception {
        // Setup: Customer C9 has existing booking for Trip T199
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customerC9);
        existingBooking.setTrip(tripT199);
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-23 12:00:00"));
        tripT199.getBookings().add(existingBooking);
        
        // Create new booking for Trip T200 (no time overlap)
        Booking newBooking = new Booking();
        newBooking.setCustomer(customerC9);
        newBooking.setTrip(tripT200);
        newBooking.setNumberOfSeats(4);
        newBooking.setBookingDate(dateFormat.parse("2023-12-23 14:00:00"));
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify results
        assertTrue("Booking should be allowed for non-overlapping trips", result);
        if (result) {
            newBooking.updateTripSeats();
        }
        assertEquals("Trip T200 seats should be updated to 36", 36, tripT200.getNumberOfSeats());
    }
    
    @Test
    public void testCase5_BookingDeniedWhenCustomerHasOverlappingBooking() throws Exception {
        // Setup: Customer C10 has existing booking for Trip T299
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customerC10);
        existingBooking.setTrip(tripT299);
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-23 12:00:00"));
        tripT299.getBookings().add(existingBooking);
        
        // Create new booking for Trip T300 (1-hour overlap)
        Booking newBooking = new Booking();
        newBooking.setCustomer(customerC10);
        newBooking.setTrip(tripT300);
        newBooking.setNumberOfSeats(4);
        newBooking.setBookingDate(dateFormat.parse("2023-12-23 14:00:00"));
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify results
        assertFalse("Booking should be denied due to time overlap", result);
        assertEquals("Trip T300 seats should remain 40", 40, tripT300.getNumberOfSeats());
    }
}