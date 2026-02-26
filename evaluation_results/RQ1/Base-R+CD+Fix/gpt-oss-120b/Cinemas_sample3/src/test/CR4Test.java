import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CR4Test {
    
    private Cinema cinema;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_RemoveFilmWithNoScreenings() throws Exception {
        // Setup: Create Cinema C1 and add Film F1
        Film f1 = new Film("F1");
        cinema.addFilm(f1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(f1, currentTime);
        
        // Expected Output: true
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film F1 should no longer be in cinema", cinema.getFilms().contains(f1));
        assertEquals("Screenings list should be empty", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_RemoveNonExistentFilm() throws Exception {
        // Setup: Create Cinema C1 (do not add Film F1)
        Film f1 = new Film("F1");
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(f1, currentTime);
        
        // Expected Output: false (Film F1 does not exist in cinema)
        assertFalse("Non-existent film should not be removed", result);
        assertEquals("Films list should remain empty", 0, cinema.getFilms().size());
    }
    
    @Test
    public void testCase3_RemoveFilmWithFutureScreening() throws Exception {
        // Setup: Create Cinema C1, add Film F1, and assign future screening
        Film f1 = new Film("F1");
        Room room = new Room("Room 1");
        cinema.addFilm(f1);
        cinema.addRoom(room);
        
        Date screeningTime = dateFormat.parse("2024-12-02 15:00:00");
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        Date currentTimeForAssignment = dateFormat.parse("2024-11-01 10:00:00");
        cinema.assignScreening(f1, room, screening, currentTimeForAssignment);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(f1, currentTime);
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film with future screening should be removed", result);
        assertFalse("Film F1 should no longer be in cinema", cinema.getFilms().contains(f1));
        assertEquals("Future screening should be removed", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_RemoveFilmWithPastScreening() throws Exception {
        // Setup: Create Cinema C1, add Film F1, and schedule past screening
        Film f1 = new Film("F1");
        Room room = new Room("Room 1");
        cinema.addFilm(f1);
        cinema.addRoom(room);
        
        Date screeningTime = dateFormat.parse("2024-11-30 18:00:00");
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        Date currentTimeForAssignment = dateFormat.parse("2024-11-01 10:00:00");
        cinema.assignScreening(f1, room, screening, currentTimeForAssignment);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(f1, currentTime);
        
        // Expected Output: true, and there is 1 screening in the cinema
        assertTrue("Film with past screening should be removed", result);
        assertFalse("Film F1 should no longer be in cinema", cinema.getFilms().contains(f1));
        assertEquals("Past screening should remain", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_RemoveFilmWithCurrentTimeScreening() throws Exception {
        // Setup: Create Cinema C1, add Film F1, and schedule screening at current time
        Film f1 = new Film("F1");
        Room room = new Room("Room 1");
        cinema.addFilm(f1);
        cinema.addRoom(room);
        
        Date screeningTime = dateFormat.parse("2024-12-01 10:00:00");
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        Date currentTimeForAssignment = dateFormat.parse("2024-11-01 10:00:00");
        cinema.assignScreening(f1, room, screening, currentTimeForAssignment);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(f1, currentTime);
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film with current-time screening should be removed", result);
        assertFalse("Film F1 should no longer be in cinema", cinema.getFilms().contains(f1));
        assertEquals("Current-time screening should be removed", 0, cinema.getScreenings().size());
    }
}