import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CR6Test {
    private Cinema cinema;
    private Film film1;
    private Room room1;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film();
        film1.setTitle("Film1");
        room1 = new Room();
        room1.setRoomId("Room1");
    }

    @Test
    public void testCase1_CancelFutureScreening() {
        // Setup: Create Cinema C1, Add Film1, Add Room1, Assign Screening1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        boolean assigned = cinema.assignScreening("Film1", "Room1", "2024-10-03 12:00:00", "2024-10-02 11:00:00");
        assertTrue("Screening should be assigned successfully", assigned);
        
        // Action: cancel Screening1 at "2024-10-02 12:00:00"
        boolean result = cinema.cancelScreening("Film1", "Room1", "2024-10-03 12:00:00", "2024-10-02 12:00:00");
        
        // Expected Output: true, there is no screening in the cinema C1
        assertTrue("Cancellation should succeed", result);
        assertEquals("Cinema should have 0 screenings", 0, cinema.getScreenings().size());
        assertEquals("Film should have 0 screenings", 0, film1.getScreenings().size());
        assertEquals("Room should have 0 screenings", 0, room1.getScreenings().size());
    }

    @Test
    public void testCase2_CancelNonExistentScreening() {
        // Setup: Create Cinema C1, Add Film1, Add Room1 (no screening assigned)
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: cancel Screening2 at current time "2024-10-03 12:05:00"
        boolean result = cinema.cancelScreening("Film1", "Room1", "2024-10-02 12:05:00", "2024-10-03 12:05:00");
        
        // Expected Output: false
        assertFalse("Cancellation should fail for non-existent screening", result);
    }

    @Test
    public void testCase3_CancelAtExactScreeningTimeBoundary() {
        // Setup: Create Cinema C1, Add Film F1, Add Room R1, Assign screening S1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        boolean assigned = cinema.assignScreening("Film1", "Room1", "2024-12-03 10:00:00", "2024-12-02 10:00:00");
        assertTrue("Screening should be assigned successfully", assigned);
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        boolean result = cinema.cancelScreening("Film1", "Room1", "2024-12-03 10:00:00", "2024-12-03 10:00:00");
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Cancellation should fail at exact time boundary", result);
        assertEquals("Cinema should have 1 screening", 1, cinema.getScreenings().size());
    }

    @Test
    public void testCase4_CancelAlreadyCancelledScreening() {
        // Setup: Create Cinema C1, Add Film1, Add Room1, Assign Screening1 then cancel it
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        boolean assigned = cinema.assignScreening("Film1", "Room1", "2024-10-03 12:00:00", "2024-10-02 11:00:00");
        assertTrue("Screening should be assigned successfully", assigned);
        boolean firstCancel = cinema.cancelScreening("Film1", "Room1", "2024-10-03 12:00:00", "2024-10-02 12:00:00");
        assertTrue("First cancellation should succeed", firstCancel);
        
        // Action: cancel Screening1 again at current time "2024-10-02 12:05:00"
        boolean result = cinema.cancelScreening("Film1", "Room1", "2024-10-03 12:00:00", "2024-10-02 12:05:00");
        
        // Expected Output: false, there is no screening in the cinema
        assertFalse("Second cancellation should fail", result);
        assertEquals("Cinema should have 0 screenings", 0, cinema.getScreenings().size());
    }

    @Test
    public void testCase5_CancelPastScreeningAfterShowtime() {
        // Setup: Create Cinema C1, Add Film F1, Add Room R1, Assign screening S1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        boolean assigned = cinema.assignScreening("Film1", "Room1", "2024-12-01 14:00:00", "2024-11-30 10:00:00");
        assertTrue("Screening should be assigned successfully", assigned);
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        boolean result = cinema.cancelScreening("Film1", "Room1", "2024-12-01 14:00:00", "2024-12-03 10:00:00");
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Cancellation should fail for past screening", result);
        assertEquals("Cinema should have 1 screening", 1, cinema.getScreenings().size());
    }
}