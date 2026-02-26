import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Driver driverD1;
    private Driver driverD2;
    private Customer customerC1;
    private Customer customerC2;
    private Customer customerC3;
    private Customer customerC9;
    private Customer customerC10;
    private Trip tripT123;
    private Trip tripT456;
    private Trip tripT100;
    private Trip tripT199;
    private Trip tripT200;
    private Trip tripT299;
    private Trip tripT300;
    private Booking existingBookingC1;
    private Booking existingBookingC2;
    private Booking existingBookingC9;
    private Booking existingBookingC10;
    
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    @Before
    public void setUp() {
        // Initialize all objects needed for tests
        driverD1 = new Driver();
        driverD2 = new Driver();
        customerC1 = new Customer();
        customerC2 = new Customer();
        customerC3 = new Customer();
        customerC9 = new Customer();
        customerC10 = new Customer();
        
        tripT123 = new Trip();
        tripT456 = new Trip();
        tripT100 = new Trip();
        tripT199 = new Trip();
        tripT200 = new Trip();
        tripT299 = new Trip();
        tripT300 = new Trip();
        
        existingBookingC1 = new Booking();
        existingBookingC2 = new Booking();
        existingBookingC9 = new Booking();
        existingBookingC10 = new Booking();
    }
    
    @Test
    public void testCase1_ValidBookingWithAvailableSeatsAndNoOverlap() {
        // Setup: Create Driver D1 with Trip T123 (2023-12-25 14:00-16:00, seats:5)
        tripT123.setDepartureTime("2023-12-25 14:00");
        tripT123.setArrivalTime("2023-12-25 16:00");
        tripT123.setNumberOfSeats(5);
        driverD1.getTrips().add(tripT123);
        
        // Setup: Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        Trip tripT456ForC1 = new Trip();
        tripT456ForC1.setDepartureTime("2023-12-25 14:00");
        tripT456ForC1.setArrivalTime("2023-12-25 16:00");
        tripT456ForC1.setNumberOfSeats(10);
        
        existingBookingC1.setTrip(tripT456ForC1);
        existingBookingC1.setCustomer(customerC1);
        existingBookingC1.setNumberOfSeats(3);
        existingBookingC1.setBookingDateTime(LocalDateTime.parse("2023-12-25 11:00", DATE_TIME_FORMATTER));
        customerC1.getBookings().add(existingBookingC1);
        tripT456ForC1.getBookings().add(existingBookingC1);
        
        // Create new booking for Trip T123
        Booking newBooking = new Booking();
        newBooking.setTrip(tripT123);
        newBooking.setCustomer(customerC1);
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDateTime(LocalDateTime.parse("2023-12-25 11:00", DATE_TIME_FORMATTER));
        
        // Test eligibility and update seats if eligible
        boolean isEligible = newBooking.isBookingEligible();
        if (isEligible) {
            newBooking.updateTripSeats();
        }
        
        // Verify results
        assertTrue("Booking should be eligible", isEligible);
        assertEquals("Trip T123 should have 2 seats remaining", 2, tripT123.getNumberOfSeats());
    }
    
    @Test
    public void testCase2_BookingDeniedDueToSeatShortage() {
        // Setup: Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        tripT456.setDepartureTime("2023-12-25 10:00");
        tripT456.setArrivalTime("2023-12-25 12:00");
        tripT456.setNumberOfSeats(2);
        driverD2.getTrips().add(tripT456);
        
        // Setup: Create Customer C2 with existing booking for T456 (3 seats)
        existingBookingC2.setTrip(tripT456);
        existingBookingC2.setCustomer(customerC2);
        existingBookingC2.setNumberOfSeats(3);
        existingBookingC2.setBookingDateTime(LocalDateTime.parse("2023-12-25 07:30", DATE_TIME_FORMATTER));
        customerC2.getBookings().add(existingBookingC2);
        tripT456.getBookings().add(existingBookingC2);
        
        // Create new booking for Trip T456
        Booking newBooking = new Booking();
        newBooking.setTrip(tripT456);
        newBooking.setCustomer(customerC2);
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDateTime(LocalDateTime.parse("2023-12-25 07:30", DATE_TIME_FORMATTER));
        
        // Test eligibility
        boolean isEligible = newBooking.isBookingEligible();
        
        // Verify results
        assertFalse("Booking should not be eligible due to seat shortage", isEligible);
        assertEquals("Trip T456 should still have 2 seats", 2, tripT456.getNumberOfSeats());
    }
    
    @Test
    public void testCase3_BookingDeniedDueToTimeCutoffExactly2HoursBefore() {
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        tripT100.setDepartureTime("2023-12-25 14:00");
        tripT100.setArrivalTime("2023-12-25 16:00");
        tripT100.setNumberOfSeats(50);
        
        // Create Customer C3 with booking for T100 (3 seats)
        // Current time: 2023-12-25 12:00 (exactly 2 hours before)
        Booking newBooking = new Booking();
        newBooking.setTrip(tripT100);
        newBooking.setCustomer(customerC3);
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDateTime(LocalDateTime.parse("2023-12-25 12:00", DATE_TIME_FORMATTER));
        
        // Test eligibility
        boolean isEligible = newBooking.isBookingEligible();
        
        // Verify results - According to specification, exactly 2 hours should return true
        assertTrue("Booking should be eligible even at exactly 2 hours before", isEligible);
        assertEquals("Trip T100 should still have 50 seats", 50, tripT100.getNumberOfSeats());
    }
    
    @Test
    public void testCase4_BookingAllowedWithMultipleNonOverlappingTrips() {
        // Setup: Customer C9 has booked an existing booking(2023-12-23 12:00, 2 seats) for Trip T199 (2023-12-25 08:00-10:00, 50 seats)
        tripT199.setDepartureTime("2023-12-25 08:00");
        tripT199.setArrivalTime("2023-12-25 10:00");
        tripT199.setNumberOfSeats(50);
        
        existingBookingC9.setTrip(tripT199);
        existingBookingC9.setCustomer(customerC9);
        existingBookingC9.setNumberOfSeats(2);
        existingBookingC9.setBookingDateTime(LocalDateTime.parse("2023-12-23 12:00", DATE_TIME_FORMATTER));
        customerC9.getBookings().add(existingBookingC9);
        tripT199.getBookings().add(existingBookingC9);
        
        // Setup: Customer C9 create a new booking(2023-12-23 14:00, 4 seats) for Trip T200 (2023-12-25 12:00-14:00, 40 seats)
        tripT200.setDepartureTime("2023-12-25 12:00");
        tripT200.setArrivalTime("2023-12-25 14:00");
        tripT200.setNumberOfSeats(40);
        
        Booking newBooking = new Booking();
        newBooking.setTrip(tripT200);
        newBooking.setCustomer(customerC9);
        newBooking.setNumberOfSeats(4);
        newBooking.setBookingDateTime(LocalDateTime.parse("2023-12-23 14:00", DATE_TIME_FORMATTER));
        
        // Test eligibility and update seats if eligible
        boolean isEligible = newBooking.isBookingEligible();
        if (isEligible) {
            newBooking.updateTripSeats();
        }
        
        // Verify results
        assertTrue("Booking should be eligible with non-overlapping trips", isEligible);
        assertEquals("Trip T200 should have 36 seats remaining", 36, tripT200.getNumberOfSeats());
    }
    
    @Test
    public void testCase5_BookingDeniedWhenCustomerHasOverlappingBooking() {
        // Setup: Customer C10 has existing booking(2023-12-23 12:00, 2 seats) for Trip T299 (departure: 2023-12-25 13:00-15:00, 50 seats)
        tripT299.setDepartureTime("2023-12-25 13:00");
        tripT299.setArrivalTime("2023-12-25 15:00");
        tripT299.setNumberOfSeats(50);
        
        existingBookingC10.setTrip(tripT299);
        existingBookingC10.setCustomer(customerC10);
        existingBookingC10.setNumberOfSeats(2);
        existingBookingC10.setBookingDateTime(LocalDateTime.parse("2023-12-23 12:00", DATE_TIME_FORMATTER));
        customerC10.getBookings().add(existingBookingC10);
        tripT299.getBookings().add(existingBookingC10);
        
        // Setup: Customer C10 create a new booking(2023-12-23 14:00, 4 seats) for Trip T300 (2023-12-25 14:00-16:00, 40 seats)
        tripT300.setDepartureTime("2023-12-25 14:00");
        tripT300.setArrivalTime("2023-12-25 16:00");
        tripT300.setNumberOfSeats(40);
        
        Booking newBooking = new Booking();
        newBooking.setTrip(tripT300);
        newBooking.setCustomer(customerC10);
        newBooking.setNumberOfSeats(4);
        newBooking.setBookingDateTime(LocalDateTime.parse("2023-12-23 14:00", DATE_TIME_FORMATTER));
        
        // Test eligibility
        boolean isEligible = newBooking.isBookingEligible();
        
        // Verify results
        assertFalse("Booking should not be eligible due to overlapping trips", isEligible);
        assertEquals("Trip T300 should still have 40 seats", 40, tripT300.getNumberOfSeats());
    }
}