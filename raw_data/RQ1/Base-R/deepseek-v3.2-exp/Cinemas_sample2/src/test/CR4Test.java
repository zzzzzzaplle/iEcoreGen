import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CR4Test {
    
    private Cinema cinema;
    private Film filmF1;
    private Room room1;
    private static final String CURRENT_TIME = "2024-12-01 10:00:00";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Before
    public void setUp() {
        // Initialize cinema before each test
        cinema = new Cinema();
        filmF1 = new Film("F1", "Test Film");
        room1 = new Room("R1");
    }
    
    @Test
    public void testCase1_removeFilmWithNoScreenings() {
        // Setup: Add Film F1 to C1
        cinema.addFilm(filmF1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(filmF1, CURRENT_TIME);
        
        // Expected Output: true
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(filmF1));
    }
    
    @Test
    public void testCase2_removeNonExistentFilm() {
        // Setup: Do not add Film F1 (cinema is empty)
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(filmF1, CURRENT_TIME);
        
        // Expected Output: false (Film F1 do not exist in cinema)
        assertFalse("Non-existent film should not be removed", result);
    }
    
    @Test
    public void testCase3_removeFilmWithFutureScreening() {
        // Setup: Add Film F1 to C1 and assign screening for F1 at "2024-12-02 15:00:00"
        cinema.addFilm(filmF1);
        cinema.addRoom(room1);
        
        String screeningTime = "2024-12-02 15:00:00";
        cinema.assignScreening(filmF1, room1, screeningTime, CURRENT_TIME);
        
        // Verify screening was added
        assertEquals("Should have 1 screening before removal", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(filmF1, CURRENT_TIME);
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(filmF1));
        assertEquals("All future screenings should be removed", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_removeFilmWithPastScreening() {
        // Setup: Add Film F1 to C1 and schedule screening for F1 at "2024-11-30 18:00:00"
        cinema.addFilm(filmF1);
        cinema.addRoom(room1);
        
        String pastScreeningTime = "2024-11-30 18:00:00";
        cinema.assignScreening(filmF1, room1, pastScreeningTime, "2024-11-30 10:00:00");
        
        // Verify screening was added
        assertEquals("Should have 1 screening before removal", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(filmF1, CURRENT_TIME);
        
        // Expected Output: true, and there is 1 screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(filmF1));
        assertEquals("Past screenings should remain", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_removeFilmWithCurrentTimeScreening() {
        // Setup: Add Film F1 to C1 and schedule screening for F1 at "2024-12-01 10:00:00"
        cinema.addFilm(filmF1);
        cinema.addRoom(room1);
        
        String currentScreeningTime = "2024-12-01 10:00:00";
        cinema.assignScreening(filmF1, room1, currentScreeningTime, "2024-12-01 09:00:00");
        
        // Verify screening was added
        assertEquals("Should have 1 screening before removal", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(filmF1, CURRENT_TIME);
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(filmF1));
        assertEquals("Current time screenings should be removed", 0, cinema.getScreenings().size());
    }
}