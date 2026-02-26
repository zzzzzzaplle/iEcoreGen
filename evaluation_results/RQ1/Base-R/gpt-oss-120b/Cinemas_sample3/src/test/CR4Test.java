import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CR4Test {
    
    private Cinema cinema;
    private Film filmF1;
    private Room roomR1;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        
        // Create film F1
        filmF1 = new Film();
        filmF1.setId("F1");
        filmF1.setTitle("Test Film");
        
        // Create room R1
        roomR1 = new Room();
        roomR1.setId("R1");
        roomR1.setName("Main Room");
    }
    
    @Test
    public void testCase1_RemoveFilmWithNoScreenings() {
        // Setup: Create Cinema C1 and add Film F1
        cinema.addFilm(filmF1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", FORMATTER);
        boolean result = cinema.removeFilm("F1", currentTime);
        
        // Expected Output: true
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film F1 should no longer exist in cinema", cinema.getFilms().containsKey("F1"));
    }
    
    @Test
    public void testCase2_RemoveNonExistentFilm() {
        // Setup: Create Cinema C1 (do not add Film F1)
        // Film F1 is not added to cinema
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", FORMATTER);
        boolean result = cinema.removeFilm("F1", currentTime);
        
        // Expected Output: false (Film F1 do not exist in cinema)
        assertFalse("Non-existent film should not be removed", result);
    }
    
    @Test
    public void testCase3_RemoveFilmWithFutureScreening() {
        // Setup: Create Cinema C1, add Film F1, and assign screening for F1
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-02 15:00:00", FORMATTER);
        LocalDateTime assignmentTime = LocalDateTime.parse("2024-12-01 09:00:00", FORMATTER);
        
        cinema.assignScreening("F1", "R1", screeningTime, assignmentTime);
        
        // Verify screening was added
        assertEquals("Should have 1 screening before removal", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", FORMATTER);
        boolean result = cinema.removeFilm("F1", currentTime);
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film F1 should no longer exist in cinema", cinema.getFilms().containsKey("F1"));
        assertEquals("All future screenings should be removed", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_RemoveFilmWithPastScreening() {
        // Setup: Create Cinema C1, add Film F1, and schedule screening for F1
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        LocalDateTime screeningTime = LocalDateTime.parse("2024-11-30 18:00:00", FORMATTER);
        LocalDateTime assignmentTime = LocalDateTime.parse("2024-11-29 10:00:00", FORMATTER);
        
        cinema.assignScreening("F1", "R1", screeningTime, assignmentTime);
        
        // Verify screening was added
        assertEquals("Should have 1 screening before removal", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", FORMATTER);
        boolean result = cinema.removeFilm("F1", currentTime);
        
        // Expected Output: true, and there is 1 screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film F1 should no longer exist in cinema", cinema.getFilms().containsKey("F1"));
        assertEquals("Past screenings should remain", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_RemoveFilmWithCurrentTimeScreening() {
        // Setup: Create Cinema C1, add Film F1, and schedule screening for F1
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-01 10:00:00", FORMATTER);
        LocalDateTime assignmentTime = LocalDateTime.parse("2024-11-30 15:00:00", FORMATTER);
        
        cinema.assignScreening("F1", "R1", screeningTime, assignmentTime);
        
        // Verify screening was added
        assertEquals("Should have 1 screening before removal", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", FORMATTER);
        boolean result = cinema.removeFilm("F1", currentTime);
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film F1 should no longer exist in cinema", cinema.getFilms().containsKey("F1"));
        assertEquals("Screenings at current time should be removed", 0, cinema.getScreenings().size());
    }
}