import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.YearMonth;

public class CR1Test {
    
    private RideShareSystem system;
    private Driver driver1;
    private Driver driver2;
    private Customer customer1;
    private Customer customer2;
    private Customer customer3;
    private Customer customer9;
    private Customer customer10;
    
    @Before
    public void setUp() {
        system = new RideShareSystem();
        
        // Create drivers
        driver1 = new Driver();
        driver1.setId("D1");
        driver1.setEmail("d1@example.com");
        driver1.setPhoneNumber("111-1111");
        
        driver2 = new Driver();
        driver2.setId("D2");
        driver2.setEmail("d2@example.com");
        driver2.setPhoneNumber("222-2222");
        
        // Create customers
        customer1 = new Customer();
        customer1.setId("C1");
        customer1.setEmail("c1@example.com");
        customer1.setPhoneNumber("333-3333");
        
        customer2 = new Customer();
        customer2.setId("C2");
        customer2.setEmail("c2@example.com");
        customer2.setPhoneNumber("444-4444");
        
        customer3 = new Customer();
        customer3.setId("C3");
        customer3.setEmail("c3@example.com");
        customer3.setPhoneNumber("555-5555");
        
        customer9 = new Customer();
        customer9.setId("C9");
        customer9.setEmail("c9@example.com");
        customer9.setPhoneNumber("999-9999");
        
        customer10 = new Customer();
        customer10.setId("C10");
        customer10.setEmail("c10@example.com");
        customer10.setPhoneNumber("1010-1010");
        
        // Add users to system
        system.addUser(driver1);
        system.addUser(driver2);
        system.addUser(customer1);
        system.addUser(customer2);
        system.addUser(customer3);
        system.addUser(customer9);
        system.addUser(customer10);
    }
    
    @Test
    public void testCase1_validBookingWithAvailableSeatsAndNoOverlap() {
        // Setup: Create Driver D1 with Trip T123 (2023-12-25 14:00-16:00, seats:5)
        Trip tripT123 = new Trip();
        tripT123.setId("T123");
        tripT123.setDriver(driver1);
        tripT123.setDepartureStation("Station A");
        tripT123.setArrivalStation("Station B");
        tripT123.setAvailableSeats(5);
        tripT123.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 14, 0));
        tripT123.setArrivalDateTime(LocalDateTime.of(2023, 12, 25, 16, 0));
        tripT123.setPricePerSeat(25.0);
        system.addTrip(tripT123);
        
        // Setup: Create Customer C1 with booking for T456 (3 seats), booking time: 2023-12-25 11:00
        Trip tripT456 = new Trip();
        tripT456.setId("T456");
        tripT456.setDriver(driver2);
        tripT456.setDepartureStation("Station C");
        tripT456.setArrivalStation("Station D");
        tripT456.setAvailableSeats(10);
        tripT456.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 14, 0)); // Same day as T123 but different time
        tripT456.setArrivalDateTime(LocalDateTime.of(2023, 12, 25, 15, 0));
        tripT456.setPricePerSeat(30.0);
        system.addTrip(tripT456);
        
        Booking existingBooking = new Booking();
        existingBooking.setId("B456");
        existingBooking.setCustomer(customer1);
        existingBooking.setTrip(tripT456);
        existingBooking.setSeatsBooked(3);
        existingBooking.setBookingDateTime(LocalDateTime.of(2023, 12, 25, 11, 0));
        system.addBooking(existingBooking);
        
        // Test: Check booking eligibility for Trip T123 (3 seats requested)
        LocalDateTime bookingTime = LocalDateTime.of(2023, 12, 25, 11, 0); // 3 hours before departure
        boolean result = system.validateBookingEligibility(customer1, tripT123, 3, bookingTime);
        
        // Verify: Expected Output: true, updated Trip T123 seats = 2
        assertTrue("Booking should be allowed", result);
        assertEquals("Trip T123 seats should be updated to 2", 2, tripT123.getAvailableSeats());
    }
    
    @Test
    public void testCase2_bookingDeniedDueToSeatShortage() {
        // Setup: Create Driver D2 with Trip T456 (2023-12-25 10:00-12:00, seats:2)
        Trip tripT456 = new Trip();
        tripT456.setId("T456");
        tripT456.setDriver(driver2);
        tripT456.setDepartureStation("Station E");
        tripT456.setArrivalStation("Station F");
        tripT456.setAvailableSeats(2);
        tripT456.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 10, 0));
        tripT456.setArrivalDateTime(LocalDateTime.of(2023, 12, 25, 12, 0));
        tripT456.setPricePerSeat(20.0);
        system.addTrip(tripT456);
        
        // Setup: Create Customer C2 with existing booking for T456 (3 seats)
        // Note: This setup seems inconsistent - booking 3 seats when trip has only 2 seats
        // Based on expected output, we'll assume the existing booking is for a different trip
        Trip otherTrip = new Trip();
        otherTrip.setId("T999");
        otherTrip.setDriver(driver1);
        otherTrip.setDepartureStation("Station X");
        otherTrip.setArrivalStation("Station Y");
        otherTrip.setAvailableSeats(5);
        otherTrip.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 8, 0));
        otherTrip.setArrivalDateTime(LocalDateTime.of(2023, 12, 25, 10, 0));
        otherTrip.setPricePerSeat(15.0);
        system.addTrip(otherTrip);
        
        Booking existingBooking = new Booking();
        existingBooking.setId("B999");
        existingBooking.setCustomer(customer2);
        existingBooking.setTrip(otherTrip);
        existingBooking.setSeatsBooked(3);
        existingBooking.setBookingDateTime(LocalDateTime.of(2023, 12, 24, 20, 0));
        system.addBooking(existingBooking);
        
        // Test: Check booking eligibility for Trip T456 (3 seats requested)
        LocalDateTime bookingTime = LocalDateTime.of(2023, 12, 25, 7, 30); // 2.5 hours before departure
        boolean result = system.validateBookingEligibility(customer2, tripT456, 3, bookingTime);
        
        // Verify: Expected Output: false, Trip T456 seats = 2
        assertFalse("Booking should be denied due to insufficient seats", result);
        assertEquals("Trip T456 seats should remain 2", 2, tripT456.getAvailableSeats());
    }
    
    @Test
    public void testCase3_bookingDeniedDueToTimeCutoffExactly2HoursBefore() {
        // Setup: Create Trip T100 (2023-12-25 14:00-16:00, seats:50)
        Trip tripT100 = new Trip();
        tripT100.setId("T100");
        tripT100.setDriver(driver1);
        tripT100.setDepartureStation("Station G");
        tripT100.setArrivalStation("Station H");
        tripT100.setAvailableSeats(50);
        tripT100.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 14, 0));
        tripT100.setArrivalDateTime(LocalDateTime.of(2023, 12, 25, 16, 0));
        tripT100.setPricePerSeat(35.0);
        system.addTrip(tripT100);
        
        // Test: Check booking eligibility for Trip T100 (3 seats requested)
        LocalDateTime bookingTime = LocalDateTime.of(2023, 12, 25, 12, 0); // exactly 2 hours before departure
        boolean result = system.validateBookingEligibility(customer3, tripT100, 3, bookingTime);
        
        // Verify: Expected Output: False, Trip T100 seats = 50 (unchanged)
        assertFalse("Booking should be denied when exactly 2 hours before departure", result);
        assertEquals("Trip T100 seats should remain 50", 50, tripT100.getAvailableSeats());
    }
    
    @Test
    public void testCase4_bookingAllowedWithMultipleNonOverlappingTrips() {
        // Setup: Customer C9 has booked an existing booking(2023-12-23 12:00, 2 seats) for Trip T199 (2023-12-25 08:00-10:00, 50 seats)
        Trip tripT199 = new Trip();
        tripT199.setId("T199");
        tripT199.setDriver(driver1);
        tripT199.setDepartureStation("Station I");
        tripT199.setArrivalStation("Station J");
        tripT199.setAvailableSeats(50);
        tripT199.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 8, 0));
        tripT199.setArrivalDateTime(LocalDateTime.of(2023, 12, 25, 10, 0));
        tripT199.setPricePerSeat(40.0);
        system.addTrip(tripT199);
        
        Booking existingBooking = new Booking();
        existingBooking.setId("B199");
        existingBooking.setCustomer(customer9);
        existingBooking.setTrip(tripT199);
        existingBooking.setSeatsBooked(2);
        existingBooking.setBookingDateTime(LocalDateTime.of(2023, 12, 23, 12, 0));
        system.addBooking(existingBooking);
        
        // Setup: Trip T200 (2023-12-25 12:00-14:00, 40 seats)
        Trip tripT200 = new Trip();
        tripT200.setId("T200");
        tripT200.setDriver(driver2);
        tripT200.setDepartureStation("Station K");
        tripT200.setArrivalStation("Station L");
        tripT200.setAvailableSeats(40);
        tripT200.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 12, 0));
        tripT200.setArrivalDateTime(LocalDateTime.of(2023, 12, 25, 14, 0));
        tripT200.setPricePerSeat(45.0);
        system.addTrip(tripT200);
        
        // Test: Customer C9 create a new booking(2023-12-23 14:00, 4 seats) for Trip T200
        LocalDateTime bookingTime = LocalDateTime.of(2023, 12, 23, 14, 0);
        boolean result = system.validateBookingEligibility(customer9, tripT200, 4, bookingTime);
        
        // Verify: Expected Output: true, Trip T200 seats = 36
        assertTrue("Booking should be allowed for non-overlapping trip", result);
        assertEquals("Trip T200 seats should be updated to 36", 36, tripT200.getAvailableSeats());
    }
    
    @Test
    public void testCase5_bookingDeniedWhenCustomerHasOverlappingBooking() {
        // Setup: Customer C10 has existing booking(2023-12-23 12:00, 2 seats) for Trip T299 (departure: 2023-12-25 13:00-15:00, 50 seats)
        Trip tripT299 = new Trip();
        tripT299.setId("T299");
        tripT299.setDriver(driver1);
        tripT299.setDepartureStation("Station M");
        tripT299.setArrivalStation("Station N");
        tripT299.setAvailableSeats(50);
        tripT299.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 13, 0));
        tripT299.setArrivalDateTime(LocalDateTime.of(2023, 12, 25, 15, 0));
        tripT299.setPricePerSeat(50.0);
        system.addTrip(tripT299);
        
        Booking existingBooking = new Booking();
        existingBooking.setId("B299");
        existingBooking.setCustomer(customer10);
        existingBooking.setTrip(tripT299);
        existingBooking.setSeatsBooked(2);
        existingBooking.setBookingDateTime(LocalDateTime.of(2023, 12, 23, 12, 0));
        system.addBooking(existingBooking);
        
        // Setup: Trip T300 (2023-12-25 14:00-16:00, 40 seats)
        Trip tripT300 = new Trip();
        tripT300.setId("T300");
        tripT300.setDriver(driver2);
        tripT300.setDepartureStation("Station O");
        tripT300.setArrivalStation("Station P");
        tripT300.setAvailableSeats(40);
        tripT300.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 14, 0));
        tripT300.setArrivalDateTime(LocalDateTime.of(2023, 12, 25, 16, 0));
        tripT300.setPricePerSeat(55.0);
        system.addTrip(tripT300);
        
        // Test: Customer C10 create a new booking(2023-12-23 14:00, 4 seats) for Trip T300
        LocalDateTime bookingTime = LocalDateTime.of(2023, 12, 23, 14, 0);
        boolean result = system.validateBookingEligibility(customer10, tripT300, 4, bookingTime);
        
        // Verify: Expected Output: false, Trip T300 seats = 40
        assertFalse("Booking should be denied due to overlapping time periods", result);
        assertEquals("Trip T300 seats should remain 40", 40, tripT300.getAvailableSeats());
    }
}