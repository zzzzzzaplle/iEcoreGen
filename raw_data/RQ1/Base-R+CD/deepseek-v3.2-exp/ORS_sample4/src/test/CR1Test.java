import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    
    @Before
    public void setUp() {
        // Initialize all objects needed for tests
        driver1 = new Driver();
        driver2 = new Driver();
        customer1 = new Customer();
        customer2 = new Customer();
        customer3 = new Customer();
        customer9 = new Customer();
        customer10 = new Customer();
        
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
        driver1.setTrips(new ArrayList<>());
        tripT123.setDepartureTime("2023-12-25 14:00");
        tripT123.setArrivalTime("2023-12-25 16:00");
        tripT123.setNumberOfSeats(5);
        driver1.getTrips().add(tripT123);
        
        // Setup: Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        customer1.setBookings(new ArrayList<>());
        Trip tripT456ForC1 = new Trip();
        tripT456ForC1.setDepartureTime("2023-12-25 14:00"); // Same day but different trip
        tripT456ForC1.setArrivalTime("2023-12-25 16:00");
        existingBookingC1.setTrip(tripT456ForC1);
        existingBookingC1.setCustomer(customer1);
        existingBookingC1.setNumberOfSeats(3);
        existingBookingC1.setBookingDate(LocalDateTime.parse("2023-12-25 11:00", 
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        customer1.getBookings().add(existingBookingC1);
        
        // Create new booking for T123
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer1);
        newBooking.setTrip(tripT123);
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDate(LocalDateTime.parse("2023-12-25 11:00", 
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))); // 3 hours before departure
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify results
        assertTrue("Booking should be eligible", result);
        if (result) {
            newBooking.updateTripSeats();
            assertEquals("Trip T123 seats should be updated to 2", 2, tripT123.getNumberOfSeats());
        }
    }
    
    @Test
    public void testCase2_BookingDeniedDueToSeatShortage() {
        // Setup: Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        driver2.setTrips(new ArrayList<>());
        tripT456.setDepartureTime("2023-12-25 10:00");
        tripT456.setArrivalTime("2023-12-25 12:00");
        tripT456.setNumberOfSeats(2);
        driver2.getTrips().add(tripT456);
        
        // Setup: Create Customer C2 with existing booking for T456 (3 seats)
        customer2.setBookings(new ArrayList<>());
        existingBookingC2.setTrip(tripT456);
        existingBookingC2.setCustomer(customer2);
        existingBookingC2.setNumberOfSeats(3);
        existingBookingC2.setBookingDate(LocalDateTime.parse("2023-12-25 07:30", 
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        customer2.getBookings().add(existingBookingC2);
        
        // Create new booking for T456
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer2);
        newBooking.setTrip(tripT456);
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDate(LocalDateTime.parse("2023-12-25 07:30", 
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))); // 2.5 hours before departure
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify results
        assertFalse("Booking should be denied due to seat shortage", result);
        assertEquals("Trip T456 seats should remain 2", 2, tripT456.getNumberOfSeats());
    }
    
    @Test
    public void testCase3_BookingDeniedDueToTimeCutoffExactly2HoursBefore() {
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        tripT100.setDepartureTime("2023-12-25 14:00");
        tripT100.setArrivalTime("2023-12-25 16:00");
        tripT100.setNumberOfSeats(50);
        
        // Create Customer C3 with booking for T100 (3 seats)
        customer3.setBookings(new ArrayList<>());
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer3);
        newBooking.setTrip(tripT100);
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDate(LocalDateTime.parse("2023-12-25 12:00", 
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))); // Exactly 2 hours before departure
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify results - According to specification, this should return true
        // but the description says "Booking denied due to time cutoff (exactly 2 hours before)"
        // However, the expected output says "true, Trip T123 seats = 50"
        // Following the strict expected output
        assertTrue("Booking should be allowed", result);
        assertEquals("Trip T100 seats should remain 50", 50, tripT100.getNumberOfSeats());
    }
    
    @Test
    public void testCase4_BookingAllowedWithMultipleNonOverlappingTrips() {
        // Setup: Customer C9 has booked an existing booking(2023-12-23 12:00, 2 seats) for Trip T199 (2023-12-25 08:00-10:00, 50 seats)
        customer9.setBookings(new ArrayList<>());
        tripT199.setDepartureTime("2023-12-25 08:00");
        tripT199.setArrivalTime("2023-12-25 10:00");
        tripT199.setNumberOfSeats(50);
        
        existingBookingC9.setTrip(tripT199);
        existingBookingC9.setCustomer(customer9);
        existingBookingC9.setNumberOfSeats(2);
        existingBookingC9.setBookingDate(LocalDateTime.parse("2023-12-23 12:00", 
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        customer9.getBookings().add(existingBookingC9);
        
        // Setup: Customer C9 create a new booking(2023-12-23 14:00, 4 seats) for Trip T200 (2023-12-25 12:00-14:00, 40 seats)
        tripT200.setDepartureTime("2023-12-25 12:00");
        tripT200.setArrivalTime("2023-12-25 14:00");
        tripT200.setNumberOfSeats(40);
        
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer9);
        newBooking.setTrip(tripT200);
        newBooking.setNumberOfSeats(4);
        newBooking.setBookingDate(LocalDateTime.parse("2023-12-23 14:00", 
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify results
        assertTrue("Booking should be allowed with non-overlapping trips", result);
        if (result) {
            newBooking.updateTripSeats();
            assertEquals("Trip T200 seats should be updated to 36", 36, tripT200.getNumberOfSeats());
        }
    }
    
    @Test
    public void testCase5_BookingDeniedWhenCustomerHasOverlappingBooking() {
        // Setup: Customer C10 has existing booking(2023-12-23 12:00, 2 seats) for Trip T299 (departure: 2023-12-25 13:00-15:00, 50 seats)
        customer10.setBookings(new ArrayList<>());
        tripT299.setDepartureTime("2023-12-25 13:00");
        tripT299.setArrivalTime("2023-12-25 15:00");
        tripT299.setNumberOfSeats(50);
        
        existingBookingC10.setTrip(tripT299);
        existingBookingC10.setCustomer(customer10);
        existingBookingC10.setNumberOfSeats(2);
        existingBookingC10.setBookingDate(LocalDateTime.parse("2023-12-23 12:00", 
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        customer10.getBookings().add(existingBookingC10);
        
        // Setup: Customer C10 create a new booking(2023-12-23 14:00, 4 seats) for Trip T300 (2023-12-25 14:00-16:00, 40 seats)
        tripT300.setDepartureTime("2023-12-25 14:00");
        tripT300.setArrivalTime("2023-12-25 16:00");
        tripT300.setNumberOfSeats(40);
        
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer10);
        newBooking.setTrip(tripT300);
        newBooking.setNumberOfSeats(4);
        newBooking.setBookingDate(LocalDateTime.parse("2023-12-23 14:00", 
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Verify results
        assertFalse("Booking should be denied due to overlapping time periods", result);
        assertEquals("Trip T300 seats should remain 40", 40, tripT300.getNumberOfSeats());
    }
}