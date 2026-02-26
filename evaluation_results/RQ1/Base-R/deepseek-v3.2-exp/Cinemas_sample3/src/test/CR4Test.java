import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CR4Test {
    private Cinema cinema;
    private Film film1;
    private Room room1;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film("Test Film", 120, "Drama");
        room1 = new Room("Room A", 100);
    }
    
    @Test
    public void testCase1_removeFilmWithNoScreenings() {
        // Setup: Create Cinema C1 and add Film F1 to C1
        cinema.addFilm(film1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(film1, "2024-12-01 10:00:00");
        
        // Expected Output: true
        assertTrue("Film should be removed successfully when no screenings exist", result);
        assertFalse("Film should no longer be in cinema", cinema.getFilms().contains(film1));
    }
    
    @Test
    public void testCase2_removeNonExistentFilm() {
        // Setup: Create Cinema C1 and do not add Film F1
        // Film F1 is not added to cinema
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(film1, "2024-12-01 10:00:00");
        
        // Expected Output: false (Film F1 does not exist in cinema)
        assertFalse("Should return false when removing non-existent film", result);
    }
    
    @Test
    public void testCase3_removeFilmWithFutureScreening() {
        // Setup: Create Cinema C1, add Film F1 to C1, and assign screening for F1 at "2024-12-02 15:00:00"
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Assign a screening in the future
        cinema.assignScreening(film1, room1, "2024-12-02 15:00:00", "2024-12-01 10:00:00");
        
        // Verify setup: should have one screening
        assertEquals("Should have one screening before removal", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(film1, "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer be in cinema", cinema.getFilms().contains(film1));
        assertEquals("All future screenings should be removed", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_removeFilmWithPastScreening() {
        // Setup: Create Cinema C1, add Film F1 to C1, and schedule screening for F1 at "2024-11-30 18:00:00"
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Assign a screening in the past relative to removal time
        cinema.assignScreening(film1, room1, "2024-11-30 18:00:00", "2024-11-29 10:00:00");
        
        // Verify setup: should have one screening
        assertEquals("Should have one screening before removal", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(film1, "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is 1 screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer be in cinema", cinema.getFilms().contains(film1));
        assertEquals("Past screenings should remain", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_removeFilmWithCurrentTimeScreening() {
        // Setup: Create Cinema C1, add Film F1 to C1, and schedule screening for F1 at "2024-12-01 10:00:00"
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Assign a screening at the exact same time as removal time
        cinema.assignScreening(film1, room1, "2024-12-01 10:00:00", "2024-11-30 10:00:00");
        
        // Verify setup: should have one screening
        assertEquals("Should have one screening before removal", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(film1, "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer be in cinema", cinema.getFilms().contains(film1));
        assertEquals("Screenings at current time should be removed", 0, cinema.getScreenings().size());
    }
}