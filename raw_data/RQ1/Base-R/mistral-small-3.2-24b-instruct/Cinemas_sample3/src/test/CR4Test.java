import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class CR4Test {
    private Cinema cinema;
    private Film film1;
    private Room room1;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film("F1");
        room1 = new Room("R1");
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_removeFilmWithNoScreenings() {
        // Setup: Create Cinema C1 and add Film F1 to C1
        cinema.addFilm(film1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        boolean result = cinema.removeFilm(film1, currentTime);
        
        // Expected Output: true
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film F1 should no longer be in cinema", cinema.getFilms().contains(film1));
        assertFalse("Screenings map should not contain film F1", cinema.getScreenings().containsKey(film1));
    }
    
    @Test
    public void testCase2_removeNonExistentFilm() {
        // Setup: Create Cinema C1, do not add Film F1
        // (cinema is empty, film1 not added)
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        boolean result = cinema.removeFilm(film1, currentTime);
        
        // Expected Output: false (Film F1 do not exist in cinema)
        assertFalse("Non-existent film should not be removed", result);
    }
    
    @Test
    public void testCase3_removeFilmWithFutureScreening() {
        // Setup: Create Cinema C1, add Film F1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Assign screening for F1 at "2024-12-02 15:00:00" (the screening time)
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-02 15:00:00", formatter);
        LocalDateTime setupCurrentTime = LocalDateTime.parse("2024-11-30 10:00:00", formatter);
        cinema.assignScreening(film1, room1, screeningTime, setupCurrentTime);
        
        // Verify setup: should have 1 screening
        assertEquals("Should have 1 screening before removal", 1, cinema.getScreenings().get(film1).size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        boolean result = cinema.removeFilm(film1, currentTime);
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film F1 should no longer be in cinema", cinema.getFilms().contains(film1));
        assertFalse("Screenings map should not contain film F1", cinema.getScreenings().containsKey(film1));
    }
    
    @Test
    public void testCase4_removeFilmWithPastScreening() {
        // Setup: Create Cinema C1, add Film F1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Schedule screening for F1 at "2024-11-30 18:00:00"
        LocalDateTime screeningTime = LocalDateTime.parse("2024-11-30 18:00:00", formatter);
        LocalDateTime setupCurrentTime = LocalDateTime.parse("2024-11-29 10:00:00", formatter);
        cinema.assignScreening(film1, room1, screeningTime, setupCurrentTime);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        boolean result = cinema.removeFilm(film1, currentTime);
        
        // Expected Output: true, and there is 1 screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film F1 should no longer be in cinema", cinema.getFilms().contains(film1));
        assertFalse("Screenings map should not contain film F1", cinema.getScreenings().containsKey(film1));
    }
    
    @Test
    public void testCase5_removeFilmWithCurrentTimeScreening() {
        // Setup: Create Cinema C1, add Film F1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Schedule screening for F1 at "2024-12-01 10:00:00"
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        LocalDateTime setupCurrentTime = LocalDateTime.parse("2024-11-30 10:00:00", formatter);
        cinema.assignScreening(film1, room1, screeningTime, setupCurrentTime);
        
        // Verify setup: should have 1 screening
        assertEquals("Should have 1 screening before removal", 1, cinema.getScreenings().get(film1).size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        boolean result = cinema.removeFilm(film1, currentTime);
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film F1 should no longer be in cinema", cinema.getFilms().contains(film1));
        assertFalse("Screenings map should not contain film F1", cinema.getScreenings().containsKey(film1));
    }
}