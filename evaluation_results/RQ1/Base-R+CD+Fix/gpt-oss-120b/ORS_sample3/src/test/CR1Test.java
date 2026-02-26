import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CR1Test {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private Driver driver1, driver2;
    private Customer customer1, customer2, customer3, customer9, customer10;
    private Trip trip123, trip456, trip100, trip199, trip200, trip299, trip300;
    
    @Before
    public void setUp() {
        // Initialize test objects
        driver1 = new Driver();
        driver2 = new Driver();
        
        customer1 = new Customer();
        customer2 = new Customer();
        customer3 = new Customer();
        customer9 = new Customer();
        customer10 = new Customer();
        
        // Create trips with initial configurations
        trip123 = new Trip();
        trip123.setDepartureTime("2023-12-25 14:00");
        trip123.setArrivalTime("2023-12-25 16:00");
        trip123.setNumberOfSeats(5);
        
        trip456 = new Trip();
        trip456.setDepartureTime("2023-12-25 10:00");
        trip456.setArrivalTime("2023-12-25 12:00");
        trip456.setNumberOfSeats(2);
        
        trip100 = new Trip();
        trip100.setDepartureTime("2023-12-25 14:00");
        trip100.setArrivalTime("2023-12-25 16:00");
        trip100.setNumberOfSeats(50);
        
        trip199 = new Trip();
        trip199.setDepartureTime("2023-12-25 08:00");
        trip199.setArrivalTime("2023-12-25 10:00");
        trip199.setNumberOfSeats(50);
        
        trip200 = new Trip();
        trip200.setDepartureTime("2023-12-25 12:00");
        trip200.setArrivalTime("2023-12-25 14:00");
        trip200.setNumberOfSeats(40);
        
        trip299 = new Trip();
        trip299.setDepartureTime("2023-12-25 13:00");
        trip299.setArrivalTime("2023-12-25 15:00");
        trip299.setNumberOfSeats(50);
        
        trip300 = new Trip();
        trip300.setDepartureTime("2023-12-25 14:00");
        trip300.setArrivalTime("2023-12-25 16:00");
        trip300.setNumberOfSeats(40);
    }
    
    @Test
    public void testCase1_validBookingWithAvailableSeatsAndNoOverlap() {
        // Setup: Create Driver D1 with Trip T123
        driver1.getTrips().add(trip123);
        
        // Setup: Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer1);
        existingBooking.setTrip(trip456);
        existingBooking.setNumberOfSeats(3);
        existingBooking.setBookingDate(LocalDateTime.parse("2023-12-25 08:00", FORMATTER)); // 2 hours before T456 departure
        
        customer1.getBookings().add(existingBooking);
        
        // Test booking eligibility for Trip T123
        try {
            // Set booking time to 2023-12-25 11:00 (3 hours before T123 departure)
            LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 11:00", FORMATTER);
            
            // Create a custom booking with specific booking time for testing
            Booking testBooking = new Booking(customer1, trip123, 3);
            testBooking.setBookingDate(bookingTime);
            
            boolean isEligible = testBooking.isBookingEligible();
            
            assertTrue("Booking should be eligible", isEligible);
            
            // Update trip seats if eligible
            if (isEligible) {
                testBooking.updateTripSeats();
            }
            
            assertEquals("Trip T123 seats should be updated to 2", 2, trip123.getNumberOfSeats());
            
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testCase2_bookingDeniedDueToSeatShortage() {
        // Setup: Create Driver D2 with Trip T456 (seats:2)
        driver2.getTrips().add(trip456);
        
        // Setup: Create Customer C2 with existing booking for T456 (3 seats)
        // This should not be possible since trip only has 2 seats, so we'll create a valid booking first
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer2);
        existingBooking.setTrip(trip456);
        existingBooking.setNumberOfSeats(2); // Book all available seats
        existingBooking.setBookingDate(LocalDateTime.parse("2023-12-25 07:30", FORMATTER));
        
        customer2.getBookings().add(existingBooking);
        trip456.setNumberOfSeats(0); // Update trip seats after booking
        
        // Test booking eligibility for Trip T456
        try {
            // Current time: 2023-12-25 07:30 (2.5 hours before departure)
            LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 07:30", FORMATTER);
            
            Booking testBooking = new Booking(customer2, trip456, 3);
            testBooking.setBookingDate(bookingTime);
            
            boolean isEligible = testBooking.isBookingEligible();
            
            assertFalse("Booking should be denied due to seat shortage", isEligible);
            assertEquals("Trip T456 seats should remain at 0", 0, trip456.getNumberOfSeats());
            
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testCase3_bookingDeniedDueToTimeCutoffExactly2HoursBefore() {
        // Setup: Create Trip T100 (seats:50)
        // Test booking eligibility for Trip T100
        try {
            // Current time: 2023-12-25 12:00 (exactly 2 hours before departure)
            LocalDateTime bookingTime = LocalDateTime.parse("2023-12-25 12:00", FORMATTER);
            
            Booking testBooking = new Booking(customer3, trip100, 3);
            testBooking.setBookingDate(bookingTime);
            
            boolean isEligible = testBooking.isBookingEligible();
            
            assertFalse("Booking should be denied due to exactly 2 hours cutoff", isEligible);
            assertEquals("Trip T100 seats should remain at 50", 50, trip100.getNumberOfSeats());
            
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testCase4_bookingAllowedWithMultipleNonOverlappingTrips() {
        // Setup: Customer C9 has existing booking for Trip T199 (2023-12-25 08:00-10:00)
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer9);
        existingBooking.setTrip(trip199);
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingDate(LocalDateTime.parse("2023-12-23 12:00", FORMATTER));
        
        customer9.getBookings().add(existingBooking);
        
        // Test new booking for Trip T200 (2023-12-25 12:00-14:00) - no overlap
        try {
            // Booking time: 2023-12-23 14:00
            LocalDateTime bookingTime = LocalDateTime.parse("2023-12-23 14:00", FORMATTER);
            
            Booking testBooking = new Booking(customer9, trip200, 4);
            testBooking.setBookingDate(bookingTime);
            
            boolean isEligible = testBooking.isBookingEligible();
            
            assertTrue("Booking should be allowed with non-overlapping trips", isEligible);
            
            // Update trip seats if eligible
            if (isEligible) {
                testBooking.updateTripSeats();
            }
            
            assertEquals("Trip T200 seats should be updated to 36", 36, trip200.getNumberOfSeats());
            
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testCase5_bookingDeniedWhenCustomerHasOverlappingBooking() {
        // Setup: Customer C10 has existing booking for Trip T299 (2023-12-25 13:00-15:00)
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer10);
        existingBooking.setTrip(trip299);
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingDate(LocalDateTime.parse("2023-12-23 12:00", FORMATTER));
        
        customer10.getBookings().add(existingBooking);
        
        // Test new booking for Trip T300 (2023-12-25 14:00-16:00) - 1-hour overlap
        try {
            // Booking time: 2023-12-23 14:00
            LocalDateTime bookingTime = LocalDateTime.parse("2023-12-23 14:00", FORMATTER);
            
            Booking testBooking = new Booking(customer10, trip300, 4);
            testBooking.setBookingDate(bookingTime);
            
            boolean isEligible = testBooking.isBookingEligible();
            
            assertFalse("Booking should be denied due to overlapping trips", isEligible);
            assertEquals("Trip T300 seats should remain at 40", 40, trip300.getNumberOfSeats());
            
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
}