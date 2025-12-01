import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR4Test {
    
    private Customer customer;
    private Trip trip1, trip2, trip3, trip4;
    private Booking booking1, booking2, booking3, booking4, booking5, booking6;
    private MembershipPackage membershipPackage;
    
    @Before
    public void setUp() {
        // Create membership package with POINTS award
        membershipPackage = new MembershipPackage();
        membershipPackage.setAwards(new Award[]{Award.POINTS});
        
        // Create trips
        trip1 = new Trip();
        trip1.setDepartureTime("2023-12-25 12:00");
        trip1.setPrice(200.0);
        
        trip2 = new Trip();
        trip2.setDepartureTime("2023-12-26 12:00");
        trip2.setPrice(100.0);
        
        trip3 = new Trip();
        trip3.setDepartureTime("2024-12-26 12:00");
        trip3.setPrice(100.0);
        
        trip4 = new Trip();
        trip4.setDepartureTime("2024-03-25 12:00");
        trip4.setPrice(200.0);
    }
    
    @Test
    public void testCase1_pointsCalculationWithMultipleBookings() throws Exception {
        // Setup for Customer C5
        customer = new Customer();
        customer.setMembershipPackage(membershipPackage);
        
        // Create booking1 for trip1 with 2 seats
        booking1 = new Booking();
        booking1.setCustomer(customer);
        booking1.setTrip(trip1);
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2023-12-24 10:00"));
        
        // Create booking2 for trip2 with 3 seats
        booking2 = new Booking();
        booking2.setCustomer(customer);
        booking2.setTrip(trip2);
        booking2.setNumberOfSeats(3);
        booking2.setBookingDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2023-12-25 10:00"));
        
        // Add bookings to customer and trips
        customer.getBookings().add(booking1);
        customer.getBookings().add(booking2);
        trip1.getBookings().add(booking1);
        trip2.getBookings().add(booking2);
        
        // Test points calculation for December 2023
        int result = customer.computeMonthlyRewardPoints("2023-12");
        assertEquals("Total points should be (2+3)*5=25", 25, result);
    }
    
    @Test
    public void testCase2_zeroPointsWithExpiredBookings() throws Exception {
        // Setup for Customer C6
        customer = new Customer();
        customer.setMembershipPackage(membershipPackage);
        
        // Create booking3 for trip3 with 4 seats (departure in 2024)
        booking3 = new Booking();
        booking3.setCustomer(customer);
        booking3.setTrip(trip3);
        booking3.setNumberOfSeats(4);
        booking3.setBookingDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2023-12-25 10:00"));
        
        // Add booking to customer and trip
        customer.getBookings().add(booking3);
        trip3.getBookings().add(booking3);
        
        // Test points calculation for December 2023 - should be 0 since booking date is in 2023 but trip is in 2024
        int result = customer.computeMonthlyRewardPoints("2023-12");
        assertEquals("Points should be 0 for bookings with future departure dates", 0, result);
    }
    
    @Test
    public void testCase3_partialMonthInclusion() throws Exception {
        // Setup for Customer C7
        customer = new Customer();
        customer.setMembershipPackage(membershipPackage);
        
        // Create booking1 for November 30th (should not be counted)
        booking1 = new Booking();
        booking1.setCustomer(customer);
        booking1.setTrip(trip1);
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2023-11-30 10:00"));
        
        // Create booking2 for December 1st (should be counted)
        booking2 = new Booking();
        booking2.setCustomer(customer);
        booking2.setTrip(trip1);
        booking2.setNumberOfSeats(3);
        booking2.setBookingDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2023-12-01 10:00"));
        
        // Add bookings to customer and trip
        customer.getBookings().add(booking1);
        customer.getBookings().add(booking2);
        trip1.getBookings().add(booking1);
        trip1.getBookings().add(booking2);
        
        // Test points calculation for December 2023
        int result = customer.computeMonthlyRewardPoints("2023-12");
        assertEquals("Only December bookings should be counted: 3*5=15", 15, result);
    }
    
    @Test
    public void testCase4_multipleSeatsEdgeCase() throws Exception {
        // Setup for Customer C8
        customer = new Customer();
        customer.setMembershipPackage(membershipPackage);
        
        // Create booking for December 10th with 2 seats
        booking4 = new Booking();
        booking4.setCustomer(customer);
        booking4.setTrip(trip4);
        booking4.setNumberOfSeats(2);
        booking4.setBookingDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2023-12-10 10:00"));
        
        // Add booking to customer and trip
        customer.getBookings().add(booking4);
        trip4.getBookings().add(booking4);
        
        // Test points calculation for December 2023
        int result = customer.computeMonthlyRewardPoints("2023-12");
        assertEquals("Points should be 2*5=10", 10, result);
    }
    
    @Test
    public void testCase5_largeSeatQuantity() throws Exception {
        // Setup for Customer C8
        Customer customerC8 = new Customer();
        customerC8.setMembershipPackage(membershipPackage);
        
        // Setup for Customer C9
        Customer customerC9 = new Customer();
        customerC9.setMembershipPackage(membershipPackage);
        
        // Create trips for large quantity test
        Trip trip5 = new Trip();
        trip5.setDepartureTime("2024-05-25 12:00");
        trip5.setPrice(150.0);
        
        Trip trip6 = new Trip();
        trip6.setDepartureTime("2024-06-25 12:00");
        trip6.setPrice(200.0);
        
        Trip trip7 = new Trip();
        trip7.setDepartureTime("2024-07-25 12:00");
        trip7.setPrice(200.0);
        
        // Create bookings for C8
        booking5 = new Booking();
        booking5.setCustomer(customerC8);
        booking5.setTrip(trip5);
        booking5.setNumberOfSeats(50);
        booking5.setBookingDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2024-01-10 10:00"));
        
        booking6 = new Booking();
        booking6.setCustomer(customerC8);
        booking6.setTrip(trip6);
        booking6.setNumberOfSeats(50);
        booking6.setBookingDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2024-01-15 10:00"));
        
        // Create booking for C9
        Booking booking7 = new Booking();
        booking7.setCustomer(customerC9);
        booking7.setTrip(trip7);
        booking7.setNumberOfSeats(50);
        booking7.setBookingDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2024-01-10 10:00"));
        
        // Add bookings to customers and trips
        customerC8.getBookings().add(booking5);
        customerC8.getBookings().add(booking6);
        customerC9.getBookings().add(booking7);
        
        trip5.getBookings().add(booking5);
        trip6.getBookings().add(booking6);
        trip7.getBookings().add(booking7);
        
        // Test points calculation for January 2024
        int resultC8 = customerC8.computeMonthlyRewardPoints("2024-01");
        int resultC9 = customerC9.computeMonthlyRewardPoints("2024-01");
        
        assertEquals("C8 reward points should be (50+50)*5=500", 500, resultC8);
        assertEquals("C9 reward points should be 50*5=250", 250, resultC9);
    }
}