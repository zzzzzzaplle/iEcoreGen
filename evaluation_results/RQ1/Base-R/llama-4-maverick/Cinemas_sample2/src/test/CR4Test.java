import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CR4Test {
    private Cinema cinema;
    private Film film1;
    private Film film2;
    private Room room1;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film("F1");
        film2 = new Film("F2");
        room1 = new Room();
    }
    
    @Test
    public void testCase1_RemoveFilmWithNoScreenings() {
        // Setup: Create Cinema C1 and add Film F1 to C1
        cinema.addFilm(film1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(film1, "2024-12-01 10:00:00");
        
        // Expected Output: true
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer be in cinema", cinema.getFilms().contains(film1));
    }
    
    @Test
    public void testCase2_RemoveNonExistentFilm() {
        // Setup: Create Cinema C1 and do not add Film F1
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(film1, "2024-12-01 10:00:00");
        
        // Expected Output: false (Film F1 do not exist in cinema)
        assertFalse("Non-existent film should not be removed", result);
    }
    
    @Test
    public void testCase3_RemoveFilmWithFutureScreening() {
        // Setup: Create Cinema C1, add Film F1 to C1, and assign screening for F1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Assign screening for F1 at "2024-12-02 15:00:00" (future time)
        cinema.assignScreening(film1, room1, "2024-12-02 15:00:00", "2024-12-01 10:00:00");
        
        // Verify screening was added
        assertEquals("Screening should be added initially", 1, room1.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(film1, "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film with future screening should be removed successfully", result);
        assertFalse("Film should no longer be in cinema", cinema.getFilms().contains(film1));
        assertEquals("Future screening should be removed", 0, room1.getScreenings().size());
    }
    
    @Test
    public void testCase4_RemoveFilmWithPastScreening() {
        // Setup: Create Cinema C1, add Film F1 to C1, and schedule screening for F1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Schedule screening for F1 at "2024-11-30 18:00:00" (past time)
        cinema.assignScreening(film1, room1, "2024-11-30 18:00:00", "2024-11-29 10:00:00");
        
        // Verify screening was added
        assertEquals("Screening should be added initially", 1, room1.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(film1, "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is 1 screening in the cinema
        assertTrue("Film with past screening should be removed successfully", result);
        assertFalse("Film should no longer be in cinema", cinema.getFilms().contains(film1));
        assertEquals("Past screening should remain", 1, room1.getScreenings().size());
    }
    
    @Test
    public void testCase5_RemoveFilmWithCurrentTimeScreening() {
        // Setup: Create Cinema C1, add Film F1 to C1, and schedule screening for F1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Schedule screening for F1 at "2024-12-01 10:00:00" (current time)
        cinema.assignScreening(film1, room1, "2024-12-01 10:00:00", "2024-11-30 10:00:00");
        
        // Verify screening was added
        assertEquals("Screening should be added initially", 1, room1.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(film1, "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film with current-time screening should be removed successfully", result);
        assertFalse("Film should no longer be in cinema", cinema.getFilms().contains(film1));
        assertEquals("Current-time screening should be removed", 0, room1.getScreenings().size());
    }
}