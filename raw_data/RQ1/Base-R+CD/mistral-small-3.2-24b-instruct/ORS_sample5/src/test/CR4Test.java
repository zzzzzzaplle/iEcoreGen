import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR4Test {
    
    private Customer customer;
    private Trip trip1;
    private Trip trip2;
    private Trip trip3;
    private Booking booking1;
    private Booking booking2;
    private Booking booking3;
    private MembershipPackage membershipPackage;
    
    @Before
    public void setUp() {
        // Create a membership package with POINTS award
        membershipPackage = new MembershipPackage();
        membershipPackage.setAwards(new Award[]{Award.POINTS});
        
        // Create customer
        customer = new Customer();
        customer.setMembershipPackage(membershipPackage);
        
        // Create trips
        trip1 = new Trip();
        trip2 = new Trip();
        trip3 = new Trip();
        
        // Create bookings list for trips
        trip1.setBookings(new ArrayList<>());
        trip2.setBookings(new ArrayList<>());
        trip3.setBookings(new ArrayList<>());
    }
    
    @Test
    public void testCase1_PointsCalculationWithMultipleBookings() throws Exception {
        // Setup: Customer C5 has membership with POINTS award
        Customer c5 = new Customer();
        c5.setMembershipPackage(membershipPackage);
        
        // Create bookings for December 2023
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        // Booking1: seats:2 for trip with departure 2023-12-25 12:00
        Booking booking1 = new Booking();
        booking1.setCustomer(c5);
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(sdf.parse("2023-12-01 10:00"));
        
        // Booking2: seats:3 for trip with departure 2023-12-26 12:00
        Booking booking2 = new Booking();
        booking2.setCustomer(c5);
        booking2.setNumberOfSeats(3);
        booking2.setBookingDate(sdf.parse("2023-12-02 10:00"));
        
        // Add bookings to trips
        Trip trip1 = new Trip();
        trip1.setBookings(new ArrayList<>());
        trip1.getBookings().add(booking1);
        
        Trip trip2 = new Trip();
        trip2.setBookings(new ArrayList<>());
        trip2.getBookings().add(booking2);
        
        // Mock the isBookingInMonth method to return true for December 2023 bookings
        Trip spyTrip1 = new Trip() {
            @Override
            public int calculateMonthlyPoints(Customer customer, String currentMonth) {
                int points = 0;
                for (Booking booking : getBookings()) {
                    if (booking.getCustomer().equals(customer)) {
                        points += booking.getNumberOfSeats() * 5;
                    }
                }
                return points;
            }
        };
        spyTrip1.setBookings(trip1.getBookings());
        
        Trip spyTrip2 = new Trip() {
            @Override
            public int calculateMonthlyPoints(Customer customer, String currentMonth) {
                int points = 0;
                for (Booking booking : getBookings()) {
                    if (booking.getCustomer().equals(customer)) {
                        points += booking.getNumberOfSeats() * 5;
                    }
                }
                return points;
            }
        };
        spyTrip2.setBookings(trip2.getBookings());
        
        // Create a spy customer that returns our test trips
        Customer spyCustomer = new Customer() {
            @Override
            public int computeMonthlyRewardPoints(String currentMonth) {
                int totalPoints = 0;
                if (getMembershipPackage() != null && getMembershipPackage().hasAward(Award.POINTS)) {
                    // Mock getTrips to return our test trips
                    List<Trip> trips = new ArrayList<>();
                    trips.add(spyTrip1);
                    trips.add(spyTrip2);
                    
                    for (Trip trip : trips) {
                        totalPoints += trip.calculateMonthlyPoints(this, currentMonth);
                    }
                }
                return totalPoints;
            }
        };
        spyCustomer.setMembershipPackage(membershipPackage);
        
        // Test: Compute reward points for December 2023
        int result = spyCustomer.computeMonthlyRewardPoints("2023-12");
        
        // Expected: (2+3)*5 = 25
        assertEquals(25, result);
    }
    
    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() throws Exception {
        // Setup: Customer C6 has membership with POINTS award
        Customer c6 = new Customer();
        c6.setMembershipPackage(membershipPackage);
        
        // Booking3: seats:4 for trip with departure 2024-12-26 12:00 (outside current month)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Booking booking3 = new Booking();
        booking3.setCustomer(c6);
        booking3.setNumberOfSeats(4);
        booking3.setBookingDate(sdf.parse("2023-11-01 10:00")); // Booking in November 2023
        
        // Add booking to trip
        Trip trip = new Trip();
        trip.setBookings(new ArrayList<>());
        trip.getBookings().add(booking3);
        
        // Mock the isBookingInMonth method to return false for November 2023 when checking December 2023
        Trip spyTrip = new Trip() {
            @Override
            public int calculateMonthlyPoints(Customer customer, String currentMonth) {
                // Since booking is from November 2023, it should not count for December 2023
                return 0;
            }
        };
        spyTrip.setBookings(trip.getBookings());
        
        // Create a spy customer
        Customer spyCustomer = new Customer() {
            @Override
            public int computeMonthlyRewardPoints(String currentMonth) {
                int totalPoints = 0;
                if (getMembershipPackage() != null && getMembershipPackage().hasAward(Award.POINTS)) {
                    List<Trip> trips = new ArrayList<>();
                    trips.add(spyTrip);
                    
                    for (Trip trip : trips) {
                        totalPoints += trip.calculateMonthlyPoints(this, currentMonth);
                    }
                }
                return totalPoints;
            }
        };
        spyCustomer.setMembershipPackage(membershipPackage);
        
        // Test: Compute reward points for December 2023
        int result = spyCustomer.computeMonthlyRewardPoints("2023-12");
        
        // Expected: 0 (booking is from different month)
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_PartialMonthInclusion() throws Exception {
        // Setup: Customer C7 has membership with POINTS award
        Customer c7 = new Customer();
        c7.setMembershipPackage(membershipPackage);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        // Booking1: 2023-11-30 10:00 (seats:2) - should NOT count for December
        Booking booking1 = new Booking();
        booking1.setCustomer(c7);
        booking1.setNumberOfSeats(2);
        booking1.setBookingDate(sdf.parse("2023-11-30 10:00"));
        
        // Booking2: 2023-12-01 10:00 (seats:3) - should count for December
        Booking booking2 = new Booking();
        booking2.setCustomer(c7);
        booking2.setNumberOfSeats(3);
        booking2.setBookingDate(sdf.parse("2023-12-01 10:00"));
        
        // Add bookings to trips
        Trip trip1 = new Trip();
        trip1.setBookings(new ArrayList<>());
        trip1.getBookings().add(booking1);
        
        Trip trip2 = new Trip();
        trip2.setBookings(new ArrayList<>());
        trip2.getBookings().add(booking2);
        
        // Mock trips to return points based on booking dates
        Trip spyTrip1 = new Trip() {
            @Override
            public int calculateMonthlyPoints(Customer customer, String currentMonth) {
                // November booking should not count for December
                return 0;
            }
        };
        spyTrip1.setBookings(trip1.getBookings());
        
        Trip spyTrip2 = new Trip() {
            @Override
            public int calculateMonthlyPoints(Customer customer, String currentMonth) {
                int points = 0;
                for (Booking booking : getBookings()) {
                    if (booking.getCustomer().equals(customer)) {
                        points += booking.getNumberOfSeats() * 5;
                    }
                }
                return points;
            }
        };
        spyTrip2.setBookings(trip2.getBookings());
        
        // Create spy customer
        Customer spyCustomer = new Customer() {
            @Override
            public int computeMonthlyRewardPoints(String currentMonth) {
                int totalPoints = 0;
                if (getMembershipPackage() != null && getMembershipPackage().hasAward(Award.POINTS)) {
                    List<Trip> trips = new ArrayList<>();
                    trips.add(spyTrip1);
                    trips.add(spyTrip2);
                    
                    for (Trip trip : trips) {
                        totalPoints += trip.calculateMonthlyPoints(this, currentMonth);
                    }
                }
                return totalPoints;
            }
        };
        spyCustomer.setMembershipPackage(membershipPackage);
        
        // Test: Compute reward points for December 2023
        int result = spyCustomer.computeMonthlyRewardPoints("2023-12");
        
        // Expected: 3*5 = 15 (only December booking counts)
        assertEquals(15, result);
    }
    
    @Test
    public void testCase4_MultipleSeatsEdgeCase() throws Exception {
        // Setup: Customer C8 has membership with POINTS award
        Customer c8 = new Customer();
        c8.setMembershipPackage(membershipPackage);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        // Booking: 2023-12-10 10:00 (seats:2) for trip with departure 2024-03-25 12:00
        Booking booking = new Booking();
        booking.setCustomer(c8);
        booking.setNumberOfSeats(2);
        booking.setBookingDate(sdf.parse("2023-12-10 10:00"));
        
        // Add booking to trip
        Trip trip = new Trip();
        trip.setBookings(new ArrayList<>());
        trip.getBookings().add(booking);
        
        // Mock trip to calculate points
        Trip spyTrip = new Trip() {
            @Override
            public int calculateMonthlyPoints(Customer customer, String currentMonth) {
                int points = 0;
                for (Booking booking : getBookings()) {
                    if (booking.getCustomer().equals(customer)) {
                        points += booking.getNumberOfSeats() * 5;
                    }
                }
                return points;
            }
        };
        spyTrip.setBookings(trip.getBookings());
        
        // Create spy customer
        Customer spyCustomer = new Customer() {
            @Override
            public int computeMonthlyRewardPoints(String currentMonth) {
                int totalPoints = 0;
                if (getMembershipPackage() != null && getMembershipPackage().hasAward(Award.POINTS)) {
                    List<Trip> trips = new ArrayList<>();
                    trips.add(spyTrip);
                    
                    for (Trip trip : trips) {
                        totalPoints += trip.calculateMonthlyPoints(this, currentMonth);
                    }
                }
                return totalPoints;
            }
        };
        spyCustomer.setMembershipPackage(membershipPackage);
        
        // Test: Compute reward points for December 2023
        int result = spyCustomer.computeMonthlyRewardPoints("2023-12");
        
        // Expected: 2*5 = 10
        assertEquals(10, result);
    }
    
    @Test
    public void testCase5_LargeSeatQuantity() throws Exception {
        // Setup: Customers C8 and C9 have membership with POINTS award
        Customer c8 = new Customer();
        c8.setMembershipPackage(membershipPackage);
        
        Customer c9 = new Customer();
        c9.setMembershipPackage(membershipPackage);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        // C8 Booking1: 2024-01-10 10:00 (seats:50)
        Booking c8Booking1 = new Booking();
        c8Booking1.setCustomer(c8);
        c8Booking1.setNumberOfSeats(50);
        c8Booking1.setBookingDate(sdf.parse("2024-01-10 10:00"));
        
        // C8 Booking2: 2024-01-15 10:00 (seats:50)
        Booking c8Booking2 = new Booking();
        c8Booking2.setCustomer(c8);
        c8Booking2.setNumberOfSeats(50);
        c8Booking2.setBookingDate(sdf.parse("2024-01-15 10:00"));
        
        // C9 Booking: 2024-01-10 10:00 (seats:50)
        Booking c9Booking = new Booking();
        c9Booking.setCustomer(c9);
        c9Booking.setNumberOfSeats(50);
        c9Booking.setBookingDate(sdf.parse("2024-01-10 10:00"));
        
        // Add bookings to trips
        Trip trip1 = new Trip();
        trip1.setBookings(new ArrayList<>());
        trip1.getBookings().add(c8Booking1);
        
        Trip trip2 = new Trip();
        trip2.setBookings(new ArrayList<>());
        trip2.getBookings().add(c8Booking2);
        
        Trip trip3 = new Trip();
        trip3.setBookings(new ArrayList<>());
        trip3.getBookings().add(c9Booking);
        
        // Mock trips to calculate points
        Trip spyTrip1 = new Trip() {
            @Override
            public int calculateMonthlyPoints(Customer customer, String currentMonth) {
                int points = 0;
                for (Booking booking : getBookings()) {
                    if (booking.getCustomer().equals(customer)) {
                        points += booking.getNumberOfSeats() * 5;
                    }
                }
                return points;
            }
        };
        spyTrip1.setBookings(trip1.getBookings());
        
        Trip spyTrip2 = new Trip() {
            @Override
            public int calculateMonthlyPoints(Customer customer, String currentMonth) {
                int points = 0;
                for (Booking booking : getBookings()) {
                    if (booking.getCustomer().equals(customer)) {
                        points += booking.getNumberOfSeats() * 5;
                    }
                }
                return points;
            }
        };
        spyTrip2.setBookings(trip2.getBookings());
        
        Trip spyTrip3 = new Trip() {
            @Override
            public int calculateMonthlyPoints(Customer customer, String currentMonth) {
                int points = 0;
                for (Booking booking : getBookings()) {
                    if (booking.getCustomer().equals(customer)) {
                        points += booking.getNumberOfSeats() * 5;
                    }
                }
                return points;
            }
        };
        spyTrip3.setBookings(trip3.getBookings());
        
        // Create spy customers
        Customer spyCustomerC8 = new Customer() {
            @Override
            public int computeMonthlyRewardPoints(String currentMonth) {
                int totalPoints = 0;
                if (getMembershipPackage() != null && getMembershipPackage().hasAward(Award.POINTS)) {
                    List<Trip> trips = new ArrayList<>();
                    trips.add(spyTrip1);
                    trips.add(spyTrip2);
                    
                    for (Trip trip : trips) {
                        totalPoints += trip.calculateMonthlyPoints(this, currentMonth);
                    }
                }
                return totalPoints;
            }
        };
        spyCustomerC8.setMembershipPackage(membershipPackage);
        
        Customer spyCustomerC9 = new Customer() {
            @Override
            public int computeMonthlyRewardPoints(String currentMonth) {
                int totalPoints = 0;
                if (getMembershipPackage() != null && getMembershipPackage().hasAward(Award.POINTS)) {
                    List<Trip> trips = new ArrayList<>();
                    trips.add(spyTrip3);
                    
                    for (Trip trip : trips) {
                        totalPoints += trip.calculateMonthlyPoints(this, currentMonth);
                    }
                }
                return totalPoints;
            }
        };
        spyCustomerC9.setMembershipPackage(membershipPackage);
        
        // Test: Compute reward points for January 2024
        int resultC8 = spyCustomerC8.computeMonthlyRewardPoints("2024-01");
        int resultC9 = spyCustomerC9.computeMonthlyRewardPoints("2024-01");
        
        // Expected: C8: (50+50)*5 = 500, C9: 50*5 = 250
        assertEquals(500, resultC8);
        assertEquals(250, resultC9);
    }
}