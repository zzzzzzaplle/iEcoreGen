import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class CR1Test {
    
    private Driver driver;
    private Customer customer;
    private Trip trip;
    private Booking booking;
    
    @Before
    public void setUp() {
        driver = new Driver();
        customer = new Customer();
        trip = new Trip();
        booking = new Booking();
    }
    
    @Test
    public void testCase1_ValidBookingWithAvailableSeatsAndNoOverlap() throws Exception {
        // Setup: Create Driver D1 with Trip T123 (2023-12-25 14:00-16:00, seats:5)
        driver.setID("D1");
        trip.setDepartureStation("T123");
        trip.setNumberOfSeats(5);
        trip.setDepartureTime("14:00");
        trip.setArrivalTime("16:00");
        driver.getTrips().add(trip);
        
        // Setup: Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        customer.setID("C1");
        Trip otherTrip = new Trip();
        otherTrip.setDepartureStation("T456");
        otherTrip.setDepartureTime("14:00");
        otherTrip.setArrivalTime("16:00");
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(otherTrip);
        existingBooking.setNumberOfSeats(3);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        existingBooking.setBookingDate(sdf.parse("2023-12-25 11:00:00"));
        otherTrip.getBookings().add(existingBooking);
        
        // Create new booking for T123
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer);
        newBooking.setTrip(trip);
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDate(sdf.parse("2023-12-25 11:00:00"));
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Expected Output: true, updated Trip T123 seats = 2
        assertTrue(result);
        assertEquals(2, trip.getNumberOfSeats());
    }
    
    @Test
    public void testCase2_BookingDeniedDueToSeatShortage() throws Exception {
        // Setup: Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        driver.setID("D2");
        trip.setDepartureStation("T456");
        trip.setNumberOfSeats(2);
        trip.setDepartureTime("10:00");
        trip.setArrivalTime("12:00");
        driver.getTrips().add(trip);
        
        // Setup: Create Customer C2 with existing booking for T456 (3 seats)
        customer.setID("C2");
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(trip);
        existingBooking.setNumberOfSeats(3);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        existingBooking.setBookingDate(sdf.parse("2023-12-25 07:30:00"));
        trip.getBookings().add(existingBooking);
        
        // Create new booking for T456
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer);
        newBooking.setTrip(trip);
        newBooking.setNumberOfSeats(3);
        newBooking.setBookingDate(sdf.parse("2023-12-25 07:30:00"));
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Expected Output: false, Trip T456 seats = 2
        assertFalse(result);
        assertEquals(2, trip.getNumberOfSeats());
    }
    
    @Test
    public void testCase3_BookingDeniedDueToTimeCutoffExactly2HoursBefore() throws Exception {
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        driver.setID("D3");
        trip.setDepartureStation("T100");
        trip.setNumberOfSeats(50);
        trip.setDepartureTime("14:00");
        trip.setArrivalTime("16:00");
        driver.getTrips().add(trip);
        
        // Setup: Current time: 2023-12-25 12:00 (exactly 2 hours before)
        customer.setID("C3");
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer);
        newBooking.setTrip(trip);
        newBooking.setNumberOfSeats(3);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        newBooking.setBookingDate(sdf.parse("2023-12-25 12:00:00"));
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Expected Output: true, Trip T100 seats = 50
        assertTrue(result);
        assertEquals(50, trip.getNumberOfSeats());
    }
    
    @Test
    public void testCase4_BookingAllowedWithMultipleNonOverlappingTrips() throws Exception {
        // Setup: Customer C9 has booked an existing booking(2023-12-23 12:00, 2 seats) for Trip T199 (2023-12-25 08:00-10:00, 50 seats)
        customer.setID("C9");
        Trip trip199 = new Trip();
        trip199.setDepartureStation("T199");
        trip199.setNumberOfSeats(50);
        trip199.setDepartureTime("08:00");
        trip199.setArrivalTime("10:00");
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(trip199);
        existingBooking.setNumberOfSeats(2);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        existingBooking.setBookingDate(sdf.parse("2023-12-23 12:00:00"));
        trip199.getBookings().add(existingBooking);
        
        // Setup: Customer C9 create a new booking(2023-12-23 14:00, 4 seats) for Trip T200 (2023-12-25 12:00-14:00, 40 seats)
        Trip trip200 = new Trip();
        trip200.setDepartureStation("T200");
        trip200.setNumberOfSeats(40);
        trip200.setDepartureTime("12:00");
        trip200.setArrivalTime("14:00");
        
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer);
        newBooking.setTrip(trip200);
        newBooking.setNumberOfSeats(4);
        newBooking.setBookingDate(sdf.parse("2023-12-23 14:00:00"));
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Expected Output: true, Trip T200 seats = 36
        assertTrue(result);
        assertEquals(36, trip200.getNumberOfSeats());
    }
    
    @Test
    public void testCase5_BookingDeniedWhenCustomerHasOverlappingBooking() throws Exception {
        // Setup: Customer C10 has existing booking(2023-12-23 12:00, 2 seats) for Trip T299 (departure: 2023-12-25 13:00-15:00, 50 seats)
        customer.setID("C10");
        Trip trip299 = new Trip();
        trip299.setDepartureStation("T299");
        trip299.setNumberOfSeats(50);
        trip299.setDepartureTime("13:00");
        trip299.setArrivalTime("15:00");
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(trip299);
        existingBooking.setNumberOfSeats(2);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        existingBooking.setBookingDate(sdf.parse("2023-12-23 12:00:00"));
        trip299.getBookings().add(existingBooking);
        
        // Setup: Customer C10 create a new booking(2023-12-23 14:00, 4 seats) for Trip T300 (2023-12-25 14:00-16:00, 40 seats)
        Trip trip300 = new Trip();
        trip300.setDepartureStation("T300");
        trip300.setNumberOfSeats(40);
        trip300.setDepartureTime("14:00");
        trip300.setArrivalTime("16:00");
        
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer);
        newBooking.setTrip(trip300);
        newBooking.setNumberOfSeats(4);
        newBooking.setBookingDate(sdf.parse("2023-12-23 14:00:00"));
        
        // Test booking eligibility
        boolean result = newBooking.isBookingEligible();
        
        // Expected Output: false, Trip T300 seats = 40
        assertFalse(result);
        assertEquals(40, trip300.getNumberOfSeats());
    }
}