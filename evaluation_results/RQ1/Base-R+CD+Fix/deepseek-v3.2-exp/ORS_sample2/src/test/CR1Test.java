import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CR1Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    }
    
    @Test
    public void testCase1_ValidBookingWithAvailableSeatsAndNoOverlap() throws Exception {
        // Setup: Create Driver D1 with Trip T123 (2023-12-25 14:00-16:00, seats:5)
        Driver driver = new Driver();
        driver.setID("D1");
        
        Trip tripT123 = new Trip();
        tripT123.setDepartureStation("Station A");
        tripT123.setArrivalStation("Station B");
        tripT123.setNumberOfSeats(5);
        tripT123.setDepartureTime("2023-12-25 14:00");
        tripT123.setArrivalTime("2023-12-25 16:00");
        tripT123.setPrice(100.0);
        
        // Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        Customer customer = new Customer();
        customer.setID("C1");
        
        // Setup membership package for customer
        MembershipPackage membership = new MembershipPackage();
        membership.setAwards(new Award[]{Award.POINTS});
        customer.setMembershipPackage(membership);
        
        // Create existing booking T456 for customer (non-overlapping trip)
        Trip tripT456 = new Trip();
        tripT456.setDepartureTime("2023-12-25 08:00");
        tripT456.setArrivalTime("2023-12-25 10:00");
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(tripT456);
        existingBooking.setNumberOfSeats(3);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-25 05:00"));
        
        // Set current booking time for new booking: 2023-12-25 11:00 (3 hours before departure)
        Date currentBookingTime = dateFormat.parse("2023-12-25 11:00");
        
        // Create booking for T123
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setTrip(tripT123);
        booking.setNumberOfSeats(3);
        booking.setBookingDate(currentBookingTime);
        
        // Test eligibility
        boolean isEligible = booking.isBookingEligible();
        
        // Verify results
        assertTrue("Booking should be eligible", isEligible);
        assertEquals("Trip seats should be updated to 2", 2, tripT123.getNumberOfSeats());
    }
    
    @Test
    public void testCase2_BookingDeniedDueToSeatShortage() throws Exception {
        // Setup: Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        Driver driver = new Driver();
        driver.setID("D2");
        
        Trip tripT456 = new Trip();
        tripT456.setDepartureStation("Station C");
        tripT456.setArrivalStation("Station D");
        tripT456.setNumberOfSeats(2);
        tripT456.setDepartureTime("2023-12-25 10:00");
        tripT456.setArrivalTime("2023-12-25 12:00");
        tripT456.setPrice(80.0);
        
        // Create Customer C2 with existing booking for T456 (3 seats)
        Customer customer = new Customer();
        customer.setID("C2");
        
        // Current time: 2023-12-25 07:30 (2.5 hours before departure)
        Date currentBookingTime = dateFormat.parse("2023-12-25 07:30");
        
        // Create booking attempting to book 3 seats when only 2 available
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setTrip(tripT456);
        booking.setNumberOfSeats(3);
        booking.setBookingDate(currentBookingTime);
        
        // Test eligibility
        boolean isEligible = booking.isBookingEligible();
        
        // Verify results
        assertFalse("Booking should not be eligible due to seat shortage", isEligible);
        assertEquals("Trip seats should remain 2", 2, tripT456.getNumberOfSeats());
    }
    
    @Test
    public void testCase3_BookingDeniedDueToTimeCutoffExactly2HoursBefore() throws Exception {
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        Trip tripT100 = new Trip();
        tripT100.setDepartureStation("Station E");
        tripT100.setArrivalStation("Station F");
        tripT100.setNumberOfSeats(50);
        tripT100.setDepartureTime("2023-12-25 14:00");
        tripT100.setArrivalTime("2023-12-25 16:00");
        tripT100.setPrice(120.0);
        
        // Create Customer C3
        Customer customer = new Customer();
        customer.setID("C3");
        
        // Current time: 2023-12-25 12:00 (exactly 2 hours before)
        Date currentBookingTime = dateFormat.parse("2023-12-25 12:00");
        
        // Create booking for T100 (3 seats)
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setTrip(tripT100);
        booking.setNumberOfSeats(3);
        booking.setBookingDate(currentBookingTime);
        
        // Test eligibility
        boolean isEligible = booking.isBookingEligible();
        
        // Verify results
        assertFalse("Booking should not be eligible due to exactly 2 hours cutoff", isEligible);
        assertEquals("Trip seats should remain 50", 50, tripT100.getNumberOfSeats());
    }
    
    @Test
    public void testCase4_BookingAllowedWithMultipleNonOverlappingTrips() throws Exception {
        // Setup: Customer C9 has booked an existing booking(2023-12-23 12:00, 2 seats) for Trip T199 (2023-12-25 08:00-10:00, 50 seats)
        Customer customer = new Customer();
        customer.setID("C9");
        
        // Setup membership package for customer
        MembershipPackage membership = new MembershipPackage();
        membership.setAwards(new Award[]{Award.POINTS});
        customer.setMembershipPackage(membership);
        
        Trip tripT199 = new Trip();
        tripT199.setDepartureTime("2023-12-25 08:00");
        tripT199.setArrivalTime("2023-12-25 10:00");
        tripT199.setNumberOfSeats(50);
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(tripT199);
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-23 12:00"));
        
        // Customer C9 create a new booking(2023-12-23 14:00, 4 seats) for Trip T200 (2023-12-25 12:00-14:00, 40 seats)
        Trip tripT200 = new Trip();
        tripT200.setDepartureStation("Station G");
        tripT200.setArrivalStation("Station H");
        tripT200.setNumberOfSeats(40);
        tripT200.setDepartureTime("2023-12-25 12:00");
        tripT200.setArrivalTime("2023-12-25 14:00");
        tripT200.setPrice(90.0);
        
        Date currentBookingTime = dateFormat.parse("2023-12-23 14:00");
        
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer);
        newBooking.setTrip(tripT200);
        newBooking.setNumberOfSeats(4);
        newBooking.setBookingDate(currentBookingTime);
        
        // Test eligibility
        boolean isEligible = newBooking.isBookingEligible();
        
        // Verify results
        assertTrue("Booking should be eligible with non-overlapping trips", isEligible);
        assertEquals("Trip seats should be updated to 36", 36, tripT200.getNumberOfSeats());
    }
    
    @Test
    public void testCase5_BookingDeniedWhenCustomerHasOverlappingBooking() throws Exception {
        // Setup: Customer C10 has existing booking(2023-12-23 12:00, 2 seats) for Trip T299 (departure: 2023-12-25 13:00-15:00, 50 seats)
        Customer customer = new Customer();
        customer.setID("C10");
        
        // Setup membership package for customer
        MembershipPackage membership = new MembershipPackage();
        membership.setAwards(new Award[]{Award.POINTS});
        customer.setMembershipPackage(membership);
        
        Trip tripT299 = new Trip();
        tripT299.setDepartureTime("2023-12-25 13:00");
        tripT299.setArrivalTime("2023-12-25 15:00");
        tripT299.setNumberOfSeats(50);
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        existingBooking.setTrip(tripT299);
        existingBooking.setNumberOfSeats(2);
        existingBooking.setBookingDate(dateFormat.parse("2023-12-23 12:00"));
        
        // Customer C10 create a new booking(2023-12-23 14:00, 4 seats) for Trip T300 (2023-12-25 14:00-16:00, 40 seats)
        Trip tripT300 = new Trip();
        tripT300.setDepartureStation("Station I");
        tripT300.setArrivalStation("Station J");
        tripT300.setNumberOfSeats(40);
        tripT300.setDepartureTime("2023-12-25 14:00");
        tripT300.setArrivalTime("2023-12-25 16:00");
        tripT300.setPrice(110.0);
        
        Date currentBookingTime = dateFormat.parse("2023-12-23 14:00");
        
        Booking newBooking = new Booking();
        newBooking.setCustomer(customer);
        newBooking.setTrip(tripT300);
        newBooking.setNumberOfSeats(4);
        newBooking.setBookingDate(currentBookingTime);
        
        // Test eligibility
        boolean isEligible = newBooking.isBookingEligible();
        
        // Verify results
        assertFalse("Booking should not be eligible due to overlapping trips", isEligible);
        assertEquals("Trip seats should remain 40", 40, tripT300.getNumberOfSeats());
    }
}