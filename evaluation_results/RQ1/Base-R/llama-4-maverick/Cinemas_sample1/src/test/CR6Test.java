import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CR6Test {
    private Cinema cinema;
    private Film film1;
    private Room room1;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film("Film1");
        room1 = new Room("Room1");
    }

    @Test
    public void testCase1_cancelFutureScreening() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening("Film1", "Room1", "2024-10-03 12:00:00", "2024-10-01 12:00:00");
        
        // Action: cancel Screening1 at "2024-10-02 12:00:00"
        boolean result = cinema.cancelScreening("Film1", "Room1", "2024-10-03 12:00:00", "2024-10-02 12:00:00");
        
        // Expected Output: true, there is no screening in the cinema C1
        assertTrue("Screening should be canceled successfully", result);
        assertEquals("Room should have no screenings after cancellation", 0, room1.getScreenings().size());
    }

    @Test
    public void testCase2_cancelNonExistentScreening() {
        // Setup: Create Cinema C1, but Screening2 (the screening time: "2024-10-02 12:05:00") is not assigned
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        // Note: Screening is not actually assigned as per specification
        
        // Action: cancel Screening2 at current time "2024-10-03 12:05:00"
        boolean result = cinema.cancelScreening("Film1", "Room1", "2024-10-02 12:05:00", "2024-10-03 12:05:00");
        
        // Expected Output: false
        assertFalse("Should return false for non-existent screening", result);
    }

    @Test
    public void testCase3_cancelAtExactScreeningTimeBoundary() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening("Film1", "Room1", "2024-12-03 10:00:00", "2024-12-01 10:00:00");
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        boolean result = cinema.cancelScreening("Film1", "Room1", "2024-12-03 10:00:00", "2024-12-03 10:00:00");
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Should return false when current time equals screening time", result);
        assertEquals("Room should still have 1 screening", 1, room1.getScreenings().size());
    }

    @Test
    public void testCase4_cancelAlreadyCancelledScreening() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening("Film1", "Room1", "2024-10-03 12:00:00", "2024-10-01 12:00:00");
        
        // First cancellation (should succeed)
        boolean firstCancel = cinema.cancelScreening("Film1", "Room1", "2024-10-03 12:00:00", "2024-10-02 12:00:00");
        assertTrue("First cancellation should succeed", firstCancel);
        
        // Action: cancel Screening1 at current time "2024-10-02 12:05:00"
        boolean result = cinema.cancelScreening("Film1", "Room1", "2024-10-03 12:00:00", "2024-10-02 12:05:00");
        
        // Expected Output: false, there is no screening in the cinema
        assertFalse("Should return false for already canceled screening", result);
        assertEquals("Room should have no screenings", 0, room1.getScreenings().size());
    }

    @Test
    public void testCase5_cancelPastScreeningAfterShowtime() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening("Film1", "Room1", "2024-12-01 14:00:00", "2024-11-30 10:00:00");
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        boolean result = cinema.cancelScreening("Film1", "Room1", "2024-12-01 14:00:00", "2024-12-03 10:00:00");
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Should return false for past screening", result);
        assertEquals("Room should still have 1 screening", 1, room1.getScreenings().size());
    }
}