import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Customer customer;
    private MembershipPackage membershipPackage;
    
    @Before
    public void setUp() {
        // Initialize membership package with POINTS award for all tests
        membershipPackage = new MembershipPackage();
        membershipPackage.setAwards(new Award[]{Award.POINTS});
    }
    
    @Test
    public void testCase1_PointsCalculationWithMultipleBookings() {
        // Setup: Customer C5 has membership with POINTS award
        customer = new Customer();
        customer.setID("C5");
        customer.setMembershipPackage(membershipPackage);
        
        // Create bookings for the customer
        List<Booking> bookings = new ArrayList<>();
        
        // Booking1: seats:2, departure: 2023-12-25 12:00
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(2);
        booking1.setCustomer(customer);
        Trip trip1 = new Trip();
        trip1.setDepartureTime("2023-12-25 12:00");
        booking1.setTrip(trip1);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            booking1.setBookingDate(sdf.parse("2023-12-01")); // Booking date in December
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        bookings.add(booking1);
        
        // Booking2: seats:3, departure: 2023-12-26 12:00
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(3);
        booking2.setCustomer(customer);
        Trip trip2 = new Trip();
        trip2.setDepartureTime("2023-12-26 12:00");
        booking2.setTrip(trip2);
        try {
            booking2.setBookingDate(sdf.parse("2023-12-02")); // Booking date in December
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        bookings.add(booking2);
        
        // Mock the static method to return our test bookings
        // This requires reflection or mocking framework, but we'll simulate it by setting up the test data
        // For simplicity, we'll override the method behavior in test context
        Trip tripSpy = new Trip() {
            @Override
            public static List<Booking> getBookingsForCustomer(Customer cust) {
                if (cust.getID().equals("C5")) {
                    return bookings;
                }
                return new ArrayList<>();
            }
        };
        
        // Test computation for current month: 2023-12
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Expected: (2+3)*5 = 25
        assertEquals(25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() {
        // Setup: Customer C6 has membership with POINTS award
        customer = new Customer();
        customer.setID("C6");
        customer.setMembershipPackage(membershipPackage);
        
        // Create booking with departure in different year (2024)
        List<Booking> bookings = new ArrayList<>();
        
        Booking booking3 = new Booking();
        booking3.setNumberOfSeats(4);
        booking3.setCustomer(customer);
        Trip trip3 = new Trip();
        trip3.setDepartureTime("2024-12-26 12:00");
        booking3.setTrip(trip3);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            booking3.setBookingDate(sdf.parse("2023-12-01")); // Booking date in December 2023
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        bookings.add(booking3);
        
        // Mock the static method
        Trip tripSpy = new Trip() {
            @Override
            public static List<Booking> getBookingsForCustomer(Customer cust) {
                if (cust.getID().equals("C6")) {
                    return bookings;
                }
                return new ArrayList<>();
            }
        };
        
        // Test computation for current month: 2023-12
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Expected: 0 (booking is for 2024 departure, but booking date is in 2023-12)
        // According to spec: "Only calculate the bookings whose booking dates are within the target month"
        // Booking date is 2023-12-01 which IS within target month 2023-12, so should be included
        // But test case says "expired bookings" - clarification needed
        // Following the test spec exactly: Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() {
        // Setup: Customer C7 has membership with POINTS award
        customer = new Customer();
        customer.setID("C7");
        customer.setMembershipPackage(membershipPackage);
        
        List<Booking> bookings = new ArrayList<>();
        
        // Booking1: 2023-11-30 10:00 (seats:2) - NOT in target month
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(2);
        booking1.setCustomer(customer);
        Trip trip1 = new Trip();
        trip1.setDepartureTime("2023-12-25 12:00");
        booking1.setTrip(trip1);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            booking1.setBookingDate(sdf.parse("2023-11-30")); // Booking date in November
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        bookings.add(booking1);
        
        // Booking2: 2023-12-01 10:00 (seats:3) - IN target month
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(3);
        booking2.setCustomer(customer);
        Trip trip2 = new Trip();
        trip2.setDepartureTime("2023-12-25 12:00");
        booking2.setTrip(trip2);
        try {
            booking2.setBookingDate(sdf.parse("2023-12-01")); // Booking date in December
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        bookings.add(booking2);
        
        // Mock the static method
        Trip tripSpy = new Trip() {
            @Override
            public static List<Booking> getBookingsForCustomer(Customer cust) {
                if (cust.getID().equals("C7")) {
                    return bookings;
                }
                return new ArrayList<>();
            }
        };
        
        // Test computation for current month: 2023-12
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Expected: 15 (3*5=15) - only booking2 should be counted
        assertEquals(15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() {
        // Setup: Customer C8 has membership with POINTS award
        customer = new Customer();
        customer.setID("C8");
        customer.setMembershipPackage(membershipPackage);
        
        List<Booking> bookings = new ArrayList<>();
        
        // Booking: 2023-12-10 10:00 (seats:2) for trip with departure in 2024
        Booking booking = new Booking();
        booking.setNumberOfSeats(2);
        booking.setCustomer(customer);
        Trip trip = new Trip();
        trip.setDepartureTime("2024-03-25 12:00");
        booking.setTrip(trip);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            booking.setBookingDate(sdf.parse("2023-12-10")); // Booking date in December 2023
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        bookings.add(booking);
        
        // Mock the static method
        Trip tripSpy = new Trip() {
            @Override
            public static List<Booking> getBookingsForCustomer(Customer cust) {
                if (cust.getID().equals("C8")) {
                    return bookings;
                }
                return new ArrayList<>();
            }
        };
        
        // Test computation for current month: 2023-12
        int result = customer.computeMonthlyRewardPoints("2023-12");
        
        // Expected: 10 (2*5=10)
        assertEquals(10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() {
        // Test Customer C8
        Customer customer8 = new Customer();
        customer8.setID("C8");
        customer8.setMembershipPackage(membershipPackage);
        
        List<Booking> bookings8 = new ArrayList<>();
        
        // C8 Booking1: 2024-01-10 10:00 (seats:50)
        Booking booking1 = new Booking();
        booking1.setNumberOfSeats(50);
        booking1.setCustomer(customer8);
        Trip trip1 = new Trip();
        trip1.setDepartureTime("2024-05-25 12:00");
        booking1.setTrip(trip1);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            booking1.setBookingDate(sdf.parse("2024-01-10")); // Booking date in January 2024
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        bookings8.add(booking1);
        
        // C8 Booking2: 2024-01-15 10:00 (seats:50)
        Booking booking2 = new Booking();
        booking2.setNumberOfSeats(50);
        booking2.setCustomer(customer8);
        Trip trip2 = new Trip();
        trip2.setDepartureTime("2024-06-25 12:00");
        booking2.setTrip(trip2);
        try {
            booking2.setBookingDate(sdf.parse("2024-01-15")); // Booking date in January 2024
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        bookings8.add(booking2);
        
        // Test Customer C9
        Customer customer9 = new Customer();
        customer9.setID("C9");
        customer9.setMembershipPackage(membershipPackage);
        
        List<Booking> bookings9 = new ArrayList<>();
        
        // C9 Booking: 2024-01-10 10:00 (seats:50)
        Booking booking3 = new Booking();
        booking3.setNumberOfSeats(50);
        booking3.setCustomer(customer9);
        Trip trip3 = new Trip();
        trip3.setDepartureTime("2024-07-25 12:00");
        booking3.setTrip(trip3);
        try {
            booking3.setBookingDate(sdf.parse("2024-01-10")); // Booking date in January 2024
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        bookings9.add(booking3);
        
        // Mock the static method for both customers
        Trip tripSpy = new Trip() {
            @Override
            public static List<Booking> getBookingsForCustomer(Customer cust) {
                if (cust.getID().equals("C8")) {
                    return bookings8;
                } else if (cust.getID().equals("C9")) {
                    return bookings9;
                }
                return new ArrayList<>();
            }
        };
        
        // Test computation for current month: 2024-01
        int result8 = customer8.computeMonthlyRewardPoints("2024-01");
        int result9 = customer9.computeMonthlyRewardPoints("2024-01");
        
        // Expected: C8: 500 (50+50)*5=500, C9: 250 (50*5=250)
        assertEquals(500, result8);
        assertEquals(250, result9);
    }
}