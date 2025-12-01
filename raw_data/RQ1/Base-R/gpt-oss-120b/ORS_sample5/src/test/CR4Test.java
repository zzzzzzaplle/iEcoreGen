import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private RideShareSystem system;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        system = new RideShareSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_pointsCalculationWithMultipleBookings() {
        // Setup
        Customer customer = new Customer();
        customer.setId("C5");
        
        Membership membership = new Membership();
        membership.setActive(true);
        membership.setRewardType(RewardType.POINTS);
        customer.setMembership(membership);
        
        // Create trips
        Trip trip1 = new Trip();
        trip1.setId("T1");
        trip1.setPricePerSeat(200.0);
        trip1.setDepartureDateTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        
        Trip trip2 = new Trip();
        trip2.setId("T2");
        trip2.setPricePerSeat(100.0);
        trip2.setDepartureDateTime(LocalDateTime.parse("2023-12-26 12:00:00", formatter));
        
        // Create bookings
        Booking booking1 = new Booking();
        booking1.setId("B1");
        booking1.setCustomer(customer);
        booking1.setTrip(trip1);
        booking1.setSeatsBooked(2);
        booking1.setBookingDateTime(LocalDateTime.parse("2023-12-01 10:00:00", formatter));
        
        Booking booking2 = new Booking();
        booking2.setId("B2");
        booking2.setCustomer(customer);
        booking2.setTrip(trip2);
        booking2.setSeatsBooked(3);
        booking2.setBookingDateTime(LocalDateTime.parse("2023-12-02 10:00:00", formatter));
        
        // Add to system
        system.addUser(customer);
        system.addTrip(trip1);
        system.addTrip(trip2);
        system.addBooking(booking1);
        system.addBooking(booking2);
        
        // Execute
        YearMonth targetMonth = YearMonth.of(2023, 12);
        int result = system.computeMonthlyRewardPoints(customer, targetMonth);
        
        // Verify
        assertEquals("Points should be (2+3)*5=25", 25, result);
    }
    
    @Test
    public void testCase2_zeroPointsWithExpiredBookings() {
        // Setup
        Customer customer = new Customer();
        customer.setId("C6");
        
        Membership membership = new Membership();
        membership.setActive(true);
        membership.setRewardType(RewardType.POINTS);
        customer.setMembership(membership);
        
        // Create trip with departure in future year
        Trip trip = new Trip();
        trip.setId("T3");
        trip.setPricePerSeat(100.0);
        trip.setDepartureDateTime(LocalDateTime.parse("2024-12-26 12:00:00", formatter));
        
        // Create booking with booking date in target month but departure in different year
        Booking booking = new Booking();
        booking.setId("B3");
        booking.setCustomer(customer);
        booking.setTrip(trip);
        booking.setSeatsBooked(4);
        booking.setBookingDateTime(LocalDateTime.parse("2023-12-01 10:00:00", formatter));
        
        // Add to system
        system.addUser(customer);
        system.addTrip(trip);
        system.addBooking(booking);
        
        // Execute
        YearMonth targetMonth = YearMonth.of(2023, 12);
        int result = system.computeMonthlyRewardPoints(customer, targetMonth);
        
        // Verify
        assertEquals("Points should be 0 since booking date is in target month", 0, result);
    }
    
    @Test
    public void testCase3_partialMonthInclusion() {
        // Setup
        Customer customer = new Customer();
        customer.setId("C7");
        
        Membership membership = new Membership();
        membership.setActive(true);
        membership.setRewardType(RewardType.POINTS);
        customer.setMembership(membership);
        
        // Create trips
        Trip trip1 = new Trip();
        trip1.setId("T4");
        trip1.setPricePerSeat(200.0);
        trip1.setDepartureDateTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        
        Trip trip2 = new Trip();
        trip2.setId("T5");
        trip2.setPricePerSeat(200.0);
        trip2.setDepartureDateTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        
        // Create bookings - one in previous month, one in target month
        Booking booking1 = new Booking();
        booking1.setId("B4");
        booking1.setCustomer(customer);
        booking1.setTrip(trip1);
        booking1.setSeatsBooked(2);
        booking1.setBookingDateTime(LocalDateTime.parse("2023-11-30 10:00:00", formatter));
        
        Booking booking2 = new Booking();
        booking2.setId("B5");
        booking2.setCustomer(customer);
        booking2.setTrip(trip2);
        booking2.setSeatsBooked(3);
        booking2.setBookingDateTime(LocalDateTime.parse("2023-12-01 10:00:00", formatter));
        
        // Add to system
        system.addUser(customer);
        system.addTrip(trip1);
        system.addTrip(trip2);
        system.addBooking(booking1);
        system.addBooking(booking2);
        
        // Execute
        YearMonth targetMonth = YearMonth.of(2023, 12);
        int result = system.computeMonthlyRewardPoints(customer, targetMonth);
        
        // Verify
        assertEquals("Points should be 3*5=15 (only December booking counts)", 15, result);
    }
    
    @Test
    public void testCase4_multipleSeatsEdgeCase() {
        // Setup
        Customer customer = new Customer();
        customer.setId("C8");
        
        Membership membership = new Membership();
        membership.setActive(true);
        membership.setRewardType(RewardType.POINTS);
        customer.setMembership(membership);
        
        // Create trip with departure in future
        Trip trip = new Trip();
        trip.setId("T6");
        trip.setPricePerSeat(200.0);
        trip.setDepartureDateTime(LocalDateTime.parse("2024-03-25 12:00:00", formatter));
        
        // Create booking in target month
        Booking booking = new Booking();
        booking.setId("B6");
        booking.setCustomer(customer);
        booking.setTrip(trip);
        booking.setSeatsBooked(2);
        booking.setBookingDateTime(LocalDateTime.parse("2023-12-10 10:00:00", formatter));
        
        // Add to system
        system.addUser(customer);
        system.addTrip(trip);
        system.addBooking(booking);
        
        // Execute
        YearMonth targetMonth = YearMonth.of(2023, 12);
        int result = system.computeMonthlyRewardPoints(customer, targetMonth);
        
        // Verify
        assertEquals("Points should be 2*5=10", 10, result);
    }
    
    @Test
    public void testCase5_largeSeatQuantity() {
        // Setup
        Customer customer8 = new Customer();
        customer8.setId("C8");
        
        Customer customer9 = new Customer();
        customer9.setId("C9");
        
        Membership membership8 = new Membership();
        membership8.setActive(true);
        membership8.setRewardType(RewardType.POINTS);
        customer8.setMembership(membership8);
        
        Membership membership9 = new Membership();
        membership9.setActive(true);
        membership9.setRewardType(RewardType.POINTS);
        customer9.setMembership(membership9);
        
        // Create trips for customer8
        Trip trip1 = new Trip();
        trip1.setId("T7");
        trip1.setPricePerSeat(150.0);
        trip1.setDepartureDateTime(LocalDateTime.parse("2024-05-25 12:00:00", formatter));
        
        Trip trip2 = new Trip();
        trip2.setId("T8");
        trip2.setPricePerSeat(200.0);
        trip2.setDepartureDateTime(LocalDateTime.parse("2024-06-25 12:00:00", formatter));
        
        // Create trip for customer9
        Trip trip3 = new Trip();
        trip3.setId("T9");
        trip3.setPricePerSeat(200.0);
        trip3.setDepartureDateTime(LocalDateTime.parse("2024-07-25 12:00:00", formatter));
        
        // Create bookings for customer8
        Booking booking1 = new Booking();
        booking1.setId("B7");
        booking1.setCustomer(customer8);
        booking1.setTrip(trip1);
        booking1.setSeatsBooked(50);
        booking1.setBookingDateTime(LocalDateTime.parse("2024-01-10 10:00:00", formatter));
        
        Booking booking2 = new Booking();
        booking2.setId("B8");
        booking2.setCustomer(customer8);
        booking2.setTrip(trip2);
        booking2.setSeatsBooked(50);
        booking2.setBookingDateTime(LocalDateTime.parse("2024-01-15 10:00:00", formatter));
        
        // Create booking for customer9
        Booking booking3 = new Booking();
        booking3.setId("B9");
        booking3.setCustomer(customer9);
        booking3.setTrip(trip3);
        booking3.setSeatsBooked(50);
        booking3.setBookingDateTime(LocalDateTime.parse("2024-01-10 10:00:00", formatter));
        
        // Add to system
        system.addUser(customer8);
        system.addUser(customer9);
        system.addTrip(trip1);
        system.addTrip(trip2);
        system.addTrip(trip3);
        system.addBooking(booking1);
        system.addBooking(booking2);
        system.addBooking(booking3);
        
        // Execute for customer8
        YearMonth targetMonth = YearMonth.of(2024, 1);
        int result8 = system.computeMonthlyRewardPoints(customer8, targetMonth);
        int result9 = system.computeMonthlyRewardPoints(customer9, targetMonth);
        
        // Verify
        assertEquals("Customer8 points should be (50+50)*5=500", 500, result8);
        assertEquals("Customer9 points should be 50*5=250", 250, result9);
    }
}