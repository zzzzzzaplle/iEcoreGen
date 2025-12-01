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
    public void testCase1_removeFilmWithNoScreenings() throws Exception {
        // Setup: Create Cinema C1 and add Film F1 to C1
        Film filmF1 = new Film("F1");
        cinema.addFilm(filmF1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: true
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film F1 should no longer exist in cinema", cinema.getFilms().contains(filmF1));
    }
    
    @Test
    public void testCase2_removeNonExistentFilm() throws Exception {
        // Setup: Create Cinema C1 (do not add Film F1)
        Film filmF1 = new Film("F1");
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: false (Film F1 does not exist in cinema)
        assertFalse("Non-existent film should not be removed", result);
    }
    
    @Test
    public void testCase3_removeFilmWithFutureScreening() throws Exception {
        // Setup: Create Cinema C1, add Film F1 to C1
        Film filmF1 = new Film("F1");
        cinema.addFilm(filmF1);
        
        // Assign screening for F1 at "2024-12-02 15:00:00"
        Room room = new Room("Room1");
        cinema.addRoom(room);
        Date screeningTime = dateFormat.parse("2024-12-02 15:00:00");
        Screening screening = new Screening(screeningTime);
        Date currentTimeForAssignment = dateFormat.parse("2024-12-01 10:00:00");
        cinema.assignScreening(filmF1, room, screening, currentTimeForAssignment);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date removalTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, removalTime);
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film F1 should no longer exist in cinema", cinema.getFilms().contains(filmF1));
        assertEquals("All future screenings should be removed", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_removeFilmWithPastScreening() throws Exception {
        // Setup: Create Cinema C1, add Film F1 to C1
        Film filmF1 = new Film("F1");
        cinema.addFilm(filmF1);
        
        // Schedule screening for F1 at "2024-11-30 18:00:00"
        Room room = new Room("Room1");
        cinema.addRoom(room);
        Date screeningTime = dateFormat.parse("2024-11-30 18:00:00");
        Screening screening = new Screening(screeningTime);
        Date currentTimeForAssignment = dateFormat.parse("2024-11-30 10:00:00");
        cinema.assignScreening(filmF1, room, screening, currentTimeForAssignment);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date removalTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, removalTime);
        
        // Expected Output: true, and there is 1 screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film F1 should no longer exist in cinema", cinema.getFilms().contains(filmF1));
        assertEquals("Past screenings should remain", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_removeFilmWithCurrentTimeScreening() throws Exception {
        // Setup: Create Cinema C1, add Film F1 to C1
        Film filmF1 = new Film("F1");
        cinema.addFilm(filmF1);
        
        // Schedule screening for F1 at "2024-12-01 10:00:00"
        Room room = new Room("Room1");
        cinema.addRoom(room);
        Date screeningTime = dateFormat.parse("2024-12-01 10:00:00");
        Screening screening = new Screening(screeningTime);
        Date currentTimeForAssignment = dateFormat.parse("2024-12-01 09:00:00");
        cinema.assignScreening(filmF1, room, screening, currentTimeForAssignment);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date removalTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, removalTime);
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film F1 should no longer exist in cinema", cinema.getFilms().contains(filmF1));
        assertEquals("Current time screening should be removed", 0, cinema.getScreenings().size());
    }
}