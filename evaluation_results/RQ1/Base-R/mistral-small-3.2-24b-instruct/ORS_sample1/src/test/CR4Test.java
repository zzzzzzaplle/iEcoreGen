import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    private ORS ors;
    private DateTimeFormatter formatter;

    @Before
    public void setUp() {
        ors = new ORS();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    public void testCase1_PointsCalculationWithMultipleBookings() {
        // Setup
        User customerC5 = new User();
        customerC5.setUserId("C5");
        customerC5.setHasMembership(true);
        ors.getUsers().add(customerC5);

        Booking booking1 = new Booking();
        booking1.setCustomerId("C5");
        booking1.setNumberOfSeats(2);
        booking1.setBookingDateTime(LocalDateTime.parse("2023-12-01 10:00:00", formatter));
        ors.getBookings().add(booking1);

        Booking booking2 = new Booking();
        booking2.setCustomerId("C5");
        booking2.setNumberOfSeats(3);
        booking2.setBookingDateTime(LocalDateTime.parse("2023-12-02 10:00:00", formatter));
        ors.getBookings().add(booking2);

        // Execute
        int result = ors.computeMonthlyRewardPoints("C5", 12);

        // Verify
        assertEquals("Total points should be (2+3)*5=25", 25, result);
    }

    @Test
    public void testCase2_ZeroPointsWithExpiredBookings() {
        // Setup
        User customerC6 = new User();
        customerC6.setUserId("C6");
        customerC6.setHasMembership(true);
        ors.getUsers().add(customerC6);

        Booking booking3 = new Booking();
        booking3.setCustomerId("C6");
        booking3.setNumberOfSeats(4);
        booking3.setBookingDateTime(LocalDateTime.parse("2024-12-26 10:00:00", formatter));
        ors.getBookings().add(booking3);

        // Execute
        int result = ors.computeMonthlyRewardPoints("C6", 12);

        // Verify
        assertEquals("Points should be 0 for booking in different year", 0, result);
    }

    @Test
    public void testCase3_PartialMonthInclusion() {
        // Setup
        User customerC7 = new User();
        customerC7.setUserId("C7");
        customerC7.setHasMembership(true);
        ors.getUsers().add(customerC7);

        Booking booking1 = new Booking();
        booking1.setCustomerId("C7");
        booking1.setNumberOfSeats(2);
        booking1.setBookingDateTime(LocalDateTime.parse("2023-11-30 10:00:00", formatter));
        ors.getBookings().add(booking1);

        Booking booking2 = new Booking();
        booking2.setCustomerId("C7");
        booking2.setNumberOfSeats(3);
        booking2.setBookingDateTime(LocalDateTime.parse("2023-12-01 10:00:00", formatter));
        ors.getBookings().add(booking2);

        // Execute
        int result = ors.computeMonthlyRewardPoints("C7", 12);

        // Verify
        assertEquals("Only December booking should count: 3*5=15", 15, result);
    }

    @Test
    public void testCase4_MultipleSeatsEdgeCase() {
        // Setup
        User customerC8 = new User();
        customerC8.setUserId("C8");
        customerC8.setHasMembership(true);
        ors.getUsers().add(customerC8);

        Booking booking = new Booking();
        booking.setCustomerId("C8");
        booking.setNumberOfSeats(2);
        booking.setBookingDateTime(LocalDateTime.parse("2023-12-10 10:00:00", formatter));
        ors.getBookings().add(booking);

        // Execute
        int result = ors.computeMonthlyRewardPoints("C8", 12);

        // Verify
        assertEquals("Points should be 2*5=10", 10, result);
    }

    @Test
    public void testCase5_LargeSeatQuantity() {
        // Setup
        User customerC8 = new User();
        customerC8.setUserId("C8");
        customerC8.setHasMembership(true);
        ors.getUsers().add(customerC8);

        User customerC9 = new User();
        customerC9.setUserId("C9");
        customerC9.setHasMembership(true);
        ors.getUsers().add(customerC9);

        Booking booking1 = new Booking();
        booking1.setCustomerId("C8");
        booking1.setNumberOfSeats(50);
        booking1.setBookingDateTime(LocalDateTime.parse("2024-01-10 10:00:00", formatter));
        ors.getBookings().add(booking1);

        Booking booking2 = new Booking();
        booking2.setCustomerId("C8");
        booking2.setNumberOfSeats(50);
        booking2.setBookingDateTime(LocalDateTime.parse("2024-01-15 10:00:00", formatter));
        ors.getBookings().add(booking2);

        Booking booking3 = new Booking();
        booking3.setCustomerId("C9");
        booking3.setNumberOfSeats(50);
        booking3.setBookingDateTime(LocalDateTime.parse("2024-01-10 10:00:00", formatter));
        ors.getBookings().add(booking3);

        // Execute
        int resultC8 = ors.computeMonthlyRewardPoints("C8", 1);
        int resultC9 = ors.computeMonthlyRewardPoints("C9", 1);

        // Verify
        assertEquals("C8 points should be (50+50)*5=500", 500, resultC8);
        assertEquals("C9 points should be 50*5=250", 250, resultC9);
    }
}