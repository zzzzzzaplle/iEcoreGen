import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CR4Test {
    private Cinema cinema;
    private Film filmF1;
    private Room room;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        filmF1 = new Film("F1");
        room = new Room("Room1");
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_RemoveFilmWithNoScreenings() {
        // Setup: Create Cinema C1 and add Film F1 to C1
        cinema.addFilm(filmF1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: true
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(filmF1));
    }
    
    @Test
    public void testCase2_RemoveNonExistentFilm() {
        // Setup: Create Cinema C1, do not add Film F1
        // (filmF1 is not added to cinema)
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: false (Film F1 do not exist in cinema)
        assertFalse("Non-existent film should not be removed", result);
    }
    
    @Test
    public void testCase3_RemoveFilmWithFutureScreening() {
        // Setup: Create Cinema C1, add Film F1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(room);
        
        // Assign screening for F1 at "2024-12-02 15:00:00"
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-02 15:00:00", formatter);
        LocalDateTime setupTime = LocalDateTime.parse("2024-11-30 10:00:00", formatter);
        cinema.assignScreening(filmF1, room, screeningTime, setupTime);
        
        // Verify setup: should have 1 screening
        assertEquals("Should have 1 screening before removal", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(filmF1));
        assertEquals("All future screenings should be removed", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_RemoveFilmWithPastScreening() {
        // Setup: Create Cinema C1, add Film F1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(room);
        
        // Schedule screening for F1 at "2024-11-30 18:00:00"
        LocalDateTime screeningTime = LocalDateTime.parse("2024-11-30 18:00:00", formatter);
        LocalDateTime setupTime = LocalDateTime.parse("2024-11-29 10:00:00", formatter);
        cinema.assignScreening(filmF1, room, screeningTime, setupTime);
        
        // Verify setup: should have 1 screening
        assertEquals("Should have 1 screening before removal", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: true, and there is 1 screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(filmF1));
        assertEquals("Past screening should remain", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_RemoveFilmWithCurrentTimeScreening() {
        // Setup: Create Cinema C1, add Film F1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(room);
        
        // Schedule screening for F1 at "2024-12-01 10:00:00"
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        LocalDateTime setupTime = LocalDateTime.parse("2024-11-30 10:00:00", formatter);
        cinema.assignScreening(filmF1, room, screeningTime, setupTime);
        
        // Verify setup: should have 1 screening
        assertEquals("Should have 1 screening before removal", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(filmF1));
        assertEquals("Current-time screening should be removed", 0, cinema.getScreenings().size());
    }
}