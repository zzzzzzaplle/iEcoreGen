import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    private Cinema cinema;
    private Film filmF1;
    private Room room1;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Before
    public void setUp() {
        cinema = new Cinema();
        filmF1 = new Film();
        filmF1.setTitle("F1");
        
        room1 = new Room();
        room1.setRoomId("R1");
        cinema.addRoom(room1);
    }

    @Test
    public void testCase1_removeFilmWithNoScreenings() {
        // Setup: Add Film F1 to C1
        cinema.addFilm(filmF1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm("F1", "2024-12-01 10:00:00");
        
        // Expected Output: true
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film F1 should no longer exist in cinema", cinema.getFilms().contains(filmF1));
    }

    @Test
    public void testCase2_removeNonExistentFilm() {
        // Setup: Do not add Film F1 (already not added in setup)
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm("F1", "2024-12-01 10:00:00");
        
        // Expected Output: false (Film F1 do not exist in cinema)
        assertFalse("Non-existent film should not be removed", result);
    }

    @Test
    public void testCase3_removeFilmWithFutureScreening() {
        // Setup: Add Film F1 to C1
        cinema.addFilm(filmF1);
        
        // Assign screening for F1 at "2024-12-02 15:00:00" (the screening time)
        cinema.assignScreening("F1", "R1", "2024-12-02 15:00:00", "2024-12-01 10:00:00");
        
        // Verify setup: should have one screening
        assertEquals("Should have 1 screening before removal", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm("F1", "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film F1 should no longer exist in cinema", cinema.getFilms().contains(filmF1));
        assertEquals("All screenings should be removed", 0, cinema.getScreenings().size());
        assertEquals("Room should have no screenings", 0, room1.getScreenings().size());
    }

    @Test
    public void testCase4_removeFilmWithPastScreening() {
        // Setup: Add Film F1 to C1
        cinema.addFilm(filmF1);
        
        // Schedule screening for F1 at "2024-11-30 18:00:00"
        cinema.assignScreening("F1", "R1", "2024-11-30 18:00:00", "2024-11-30 10:00:00");
        
        // Verify setup: should have one screening
        assertEquals("Should have 1 screening before removal", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm("F1", "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is 1 screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film F1 should no longer exist in cinema", cinema.getFilms().contains(filmF1));
        assertEquals("Past screening should remain", 1, cinema.getScreenings().size());
        assertEquals("Room should still have past screening", 1, room1.getScreenings().size());
    }

    @Test
    public void testCase5_removeFilmWithCurrentTimeScreening() {
        // Setup: Add Film F1 to C1
        cinema.addFilm(filmF1);
        
        // Schedule screening for F1 at "2024-12-01 10:00:00"
        cinema.assignScreening("F1", "R1", "2024-12-01 10:00:00", "2024-12-01 09:00:00");
        
        // Verify setup: should have one screening
        assertEquals("Should have 1 screening before removal", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm("F1", "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film F1 should no longer exist in cinema", cinema.getFilms().contains(filmF1));
        assertEquals("Current time screening should be removed", 0, cinema.getScreenings().size());
        assertEquals("Room should have no screenings", 0, room1.getScreenings().size());
    }
}