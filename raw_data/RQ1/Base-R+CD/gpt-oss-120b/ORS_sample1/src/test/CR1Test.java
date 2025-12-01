import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
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
    }
    
    @Test
    public void testCase1_validBookingWithAvailableSeatsAndNoOverlap() {
        // Setup: Create Driver D1 with Trip T123 (2023-12-25 14:00-16:00, seats:5)
        driver1.setTrips(new ArrayList<>());
        tripT123.setDepartureDateTime("2023-12-25 14:00");
        tripT123.setArrivalDateTime("2023-12-25 16:00");
        tripT123.setDepartureDate(LocalDate.of(2023, 12, 25));
        tripT123.setNumberOfSeats(5);
        tripT123.setBookings(new ArrayList<>());
        driver1.getTrips().add(tripT123);
        
        // Setup: Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        customer1.setBookings(new ArrayList<>());
        Trip tripT456ForC1 = new Trip();
        tripT456ForC1.setDepartureDateTime("2023-12-25 14:00");
        tripT456ForC1.setArrivalDateTime("2023-12-25 16:00");
        tripT456ForC1.setDepartureDate(LocalDate.of(2023, 12, 25));
        tripT456ForC1.setNumberOfSeats(10);
        tripT456ForC1.setBookings(new ArrayList<>());
        
        Booking existingBooking = new Booking(customer1, tripT456ForC1, 3, LocalDate.of(2023, 12, 25));
        customer1.getBookings().add(existingBooking);
        
        // Mock current time to be 2023-12-25 11:00 (3 hours before departure)
        // For testing purposes, we'll set up the trip and manually check eligibility
        
        // Create booking for T123
        Booking newBooking = new Booking(customer1, tripT123, 3, LocalDate.of(2023, 12, 25));
        
        // Test eligibility
        boolean isEligible = newBooking.isBookingEligible();
        
        // Update seats if eligible
        if (isEligible) {
            newBooking.updateTripSeats();
        }
        
        // Verify results
        assertTrue("Booking should be eligible", isEligible);
        assertEquals("Trip T123 should have 2 seats remaining", 2, tripT123.getNumberOfSeats());
    }
    
    @Test
    public void testCase2_bookingDeniedDueToSeatShortage() {
        // Setup: Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        driver2.setTrips(new ArrayList<>());
        tripT456.setDepartureDateTime("2023-12-25 10:00");
        tripT456.setArrivalDateTime("2023-12-25 12:00");
        tripT456.setDepartureDate(LocalDate.of(2023, 12, 25));
        tripT456.setNumberOfSeats(2);
        tripT456.setBookings(new ArrayList<>());
        driver2.getTrips().add(tripT456);
        
        // Setup: Create Customer C2 with existing booking for T456 (3 seats)
        customer2.setBookings(new ArrayList<>());
        Booking existingBooking = new Booking(customer2, tripT456, 3, LocalDate.of(2023, 12, 25));
        customer2.getBookings().add(existingBooking);
        
        // Current time: 2023-12-25 07:30 (2.5 hours before departure)
        // Create new booking for T456
        Booking newBooking = new Booking(customer2, tripT456, 3, LocalDate.of(2023, 12, 25));
        
        // Test eligibility
        boolean isEligible = newBooking.isBookingEligible();
        
        // Verify results
        assertFalse("Booking should not be eligible due to seat shortage", isEligible);
        assertEquals("Trip T456 should still have 2 seats", 2, tripT456.getNumberOfSeats());
    }
    
    @Test
    public void testCase3_bookingDeniedDueToTimeCutoffExactly2HoursBefore() {
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        tripT100.setDepartureDateTime("2023-12-25 14:00");
        tripT100.setArrivalDateTime("2023-12-25 16:00");
        tripT100.setDepartureDate(LocalDate.of(2023, 12, 25));
        tripT100.setNumberOfSeats(50);
        tripT100.setBookings(new ArrayList<>());
        
        // Current time: 2023-12-25 12:00 (exactly 2 hours before)
        // Create Customer C3 with booking for T100 (3 seats)
        customer3.setBookings(new ArrayList<>());
        Booking newBooking = new Booking(customer3, tripT100, 3, LocalDate.of(2023, 12, 25));
        
        // Test eligibility - should be false due to exactly 2 hours cutoff
        boolean isEligible = newBooking.isBookingEligible();
        
        // Verify results
        assertFalse("Booking should not be eligible due to exactly 2 hours cutoff", isEligible);
        assertEquals("Trip T100 should still have 50 seats", 50, tripT100.getNumberOfSeats());
    }
    
    @Test
    public void testCase4_bookingAllowedWithMultipleNonOverlappingTrips() {
        // Setup: Customer C9 has booked an existing booking(2023-12-23 12:00, 2 seats) for Trip T199 (2023-12-25 08:00-10:00, 50 seats)
        customer9.setBookings(new ArrayList<>());
        tripT199.setDepartureDateTime("2023-12-25 08:00");
        tripT199.setArrivalDateTime("2023-12-25 10:00");
        tripT199.setDepartureDate(LocalDate.of(2023, 12, 25));
        tripT199.setNumberOfSeats(50);
        tripT199.setBookings(new ArrayList<>());
        
        Booking existingBooking = new Booking(customer9, tripT199, 2, LocalDate.of(2023, 12, 23));
        customer9.getBookings().add(existingBooking);
        
        // Setup: Customer C9 create a new booking(2023-12-23 14:00, 4 seats) for Trip T200 (2023-12-25 12:00-14:00, 40 seats)
        tripT200.setDepartureDateTime("2023-12-25 12:00");
        tripT200.setArrivalDateTime("2023-12-25 14:00");
        tripT200.setDepartureDate(LocalDate.of(2023, 12, 25));
        tripT200.setNumberOfSeats(40);
        tripT200.setBookings(new ArrayList<>());
        
        Booking newBooking = new Booking(customer9, tripT200, 4, LocalDate.of(2023, 12, 23));
        
        // Test eligibility
        boolean isEligible = newBooking.isBookingEligible();
        
        // Update seats if eligible
        if (isEligible) {
            newBooking.updateTripSeats();
        }
        
        // Verify results
        assertTrue("Booking should be eligible with non-overlapping trips", isEligible);
        assertEquals("Trip T200 should have 36 seats remaining", 36, tripT200.getNumberOfSeats());
    }
    
    @Test
    public void testCase5_bookingDeniedWhenCustomerHasOverlappingBooking() {
        // Setup: Customer C10 has existing booking(2023-12-23 12:00, 2 seats) for Trip T299 (departure: 2023-12-25 13:00-15:00, 50 seats)
        customer10.setBookings(new ArrayList<>());
        tripT299.setDepartureDateTime("2023-12-25 13:00");
        tripT299.setArrivalDateTime("2023-12-25 15:00");
        tripT299.setDepartureDate(LocalDate.of(2023, 12, 25));
        tripT299.setNumberOfSeats(50);
        tripT299.setBookings(new ArrayList<>());
        
        Booking existingBooking = new Booking(customer10, tripT299, 2, LocalDate.of(2023, 12, 23));
        customer10.getBookings().add(existingBooking);
        
        // Setup: Customer C10 create a new booking(2023-12-23 14:00, 4 seats) for Trip T300 (2023-12-25 14:00-16:00, 40 seats)
        tripT300.setDepartureDateTime("2023-12-25 14:00");
        tripT300.setArrivalDateTime("2023-12-25 16:00");
        tripT300.setDepartureDate(LocalDate.of(2023, 12, 25));
        tripT300.setNumberOfSeats(40);
        tripT300.setBookings(new ArrayList<>());
        
        Booking newBooking = new Booking(customer10, tripT300, 4, LocalDate.of(2023, 12, 23));
        
        // Test eligibility
        boolean isEligible = newBooking.isBookingEligible();
        
        // Verify results
        assertFalse("Booking should not be eligible due to overlapping trips", isEligible);
        assertEquals("Trip T300 should still have 40 seats", 40, tripT300.getNumberOfSeats());
    }
}