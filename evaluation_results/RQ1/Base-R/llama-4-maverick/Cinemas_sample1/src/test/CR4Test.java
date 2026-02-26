import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CR4Test {
    private Cinema cinema;
    
    @Before
    public void setUp() {
        // Initialize cinema before each test
        cinema = new Cinema();
    }
    
    @Test
    public void testCase1_removeFilmWithNoScreenings() {
        // Setup: Create Cinema C1 and add Film F1
        Film filmF1 = new Film("F1");
        cinema.addFilm(filmF1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm("F1", "2024-12-01 10:00:00");
        
        // Expected Output: true
        assertTrue("Film F1 should be removed successfully", result);
        
        // Verify film was removed from cinema
        assertFalse("Film F1 should no longer be in cinema", cinema.addFilm(filmF1));
    }
    
    @Test
    public void testCase2_removeNonExistentFilm() {
        // Setup: Create Cinema C1 (Film F1 is not added)
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm("F1", "2024-12-01 10:00:00");
        
        // Expected Output: false (Film F1 does not exist in cinema)
        assertFalse("Non-existent film should return false", result);
    }
    
    @Test
    public void testCase3_removeFilmWithFutureScreening() {
        // Setup: Create Cinema C1, add Film F1, and assign future screening
        Film filmF1 = new Film("F1");
        Room room = new Room("Room1");
        cinema.addFilm(filmF1);
        cinema.addRoom(room);
        
        // Assign screening for F1 at "2024-12-02 15:00:00" (future time)
        cinema.assignScreening("F1", "Room1", "2024-12-02 15:00:00", "2024-12-01 10:00:00");
        
        // Verify screening was added
        assertEquals("Room should have 1 screening", 1, room.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm("F1", "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film F1 should be removed successfully", result);
        assertEquals("All future screenings should be removed", 0, room.getScreenings().size());
    }
    
    @Test
    public void testCase4_removeFilmWithPastScreening() {
        // Setup: Create Cinema C1, add Film F1, and schedule past screening
        Film filmF1 = new Film("F1");
        Room room = new Room("Room1");
        cinema.addFilm(filmF1);
        cinema.addRoom(room);
        
        // Schedule screening for F1 at "2024-11-30 18:00:00" (past time)
        // Use assignScreening with appropriate current time
        cinema.assignScreening("F1", "Room1", "2024-11-30 18:00:00", "2024-11-30 10:00:00");
        
        // Verify screening was added
        assertEquals("Room should have 1 screening", 1, room.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm("F1", "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is 1 screening in the cinema (past screening remains)
        assertTrue("Film F1 should be removed successfully", result);
        assertEquals("Past screening should remain", 1, room.getScreenings().size());
    }
    
    @Test
    public void testCase5_removeFilmWithCurrentTimeScreening() {
        // Setup: Create Cinema C1, add Film F1, and schedule screening at current time
        Film filmF1 = new Film("F1");
        Room room = new Room("Room1");
        cinema.addFilm(filmF1);
        cinema.addRoom(room);
        
        // Schedule screening for F1 at "2024-12-01 10:00:00" (same as current time)
        // Use assignScreening with appropriate current time (before the screening time)
        cinema.assignScreening("F1", "Room1", "2024-12-01 10:00:00", "2024-12-01 09:00:00");
        
        // Verify screening was added
        assertEquals("Room should have 1 screening", 1, room.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm("F1", "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film F1 should be removed successfully", result);
        assertEquals("Current time screening should be removed", 0, room.getScreenings().size());
    }
}