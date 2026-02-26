import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CR4Test {
    
    private Cinema cinema;
    private Film filmF1;
    private Room roomA;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        filmF1 = new Film("Film F1");
        roomA = new Room("Room A");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_RemoveFilmWithNoScreenings() throws Exception {
        // Setup: Add Film F1 to C1
        cinema.addFilm(filmF1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: true
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer be in cinema", cinema.getFilms().contains(filmF1));
    }
    
    @Test
    public void testCase2_RemoveNonExistentFilm() throws Exception {
        // Setup: Do not add Film F1 (filmF1 is not added to cinema)
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: false (Film F1 do not exist in cinema)
        assertFalse("Non-existent film should not be removed", result);
    }
    
    @Test
    public void testCase3_RemoveFilmWithFutureScreening() throws Exception {
        // Setup: Add Film F1 to C1 and assign screening for F1 at "2024-12-02 15:00:00"
        cinema.addFilm(filmF1);
        cinema.addRoom(roomA);
        
        Date screeningTime = dateFormat.parse("2024-12-02 15:00:00");
        Date currentTimeForAssignment = dateFormat.parse("2024-12-01 10:00:00");
        Screening screening = new Screening(screeningTime, filmF1, roomA);
        
        cinema.assignScreening(filmF1, roomA, screening, currentTimeForAssignment);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTimeForRemoval = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, currentTimeForRemoval);
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film with future screening should be removed successfully", result);
        assertFalse("Film should no longer be in cinema", cinema.getFilms().contains(filmF1));
        assertEquals("All future screenings should be removed", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_RemoveFilmWithPastScreening() throws Exception {
        // Setup: Add Film F1 to C1 and schedule screening for F1 at "2024-11-30 18:00:00"
        cinema.addFilm(filmF1);
        cinema.addRoom(roomA);
        
        Date screeningTime = dateFormat.parse("2024-11-30 18:00:00");
        Date currentTimeForAssignment = dateFormat.parse("2024-11-29 10:00:00");
        Screening screening = new Screening(screeningTime, filmF1, roomA);
        
        cinema.assignScreening(filmF1, roomA, screening, currentTimeForAssignment);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTimeForRemoval = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, currentTimeForRemoval);
        
        // Expected Output: true, and there is 1 screening in the cinema
        assertTrue("Film with past screening should be removed successfully", result);
        assertFalse("Film should no longer be in cinema", cinema.getFilms().contains(filmF1));
        assertEquals("Past screening should remain", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_RemoveFilmWithCurrentTimeScreening() throws Exception {
        // Setup: Add Film F1 to C1 and schedule screening for F1 at "2024-12-01 10:00:00"
        cinema.addFilm(filmF1);
        cinema.addRoom(roomA);
        
        Date screeningTime = dateFormat.parse("2024-12-01 10:00:00");
        Date currentTimeForAssignment = dateFormat.parse("2024-11-30 10:00:00");
        Screening screening = new Screening(screeningTime, filmF1, roomA);
        
        cinema.assignScreening(filmF1, roomA, screening, currentTimeForAssignment);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTimeForRemoval = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, currentTimeForRemoval);
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film with current-time screening should be removed successfully", result);
        assertFalse("Film should no longer be in cinema", cinema.getFilms().contains(filmF1));
        assertEquals("Current-time screening should be removed", 0, cinema.getScreenings().size());
    }
}