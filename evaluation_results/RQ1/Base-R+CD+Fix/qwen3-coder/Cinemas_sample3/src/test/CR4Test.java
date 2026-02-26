import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR4Test {
    private Cinema cinema;
    private Film filmF1;
    private Room room;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        filmF1 = new Film("F1");
        room = new Room("Room1");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_removeFilmWithNoScreenings() throws ParseException {
        // Setup
        cinema.addFilm(filmF1);
        
        // Action
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: true
        assertTrue("Film should be removed successfully when no screenings exist", result);
        assertFalse("Film should no longer exist in cinema", cinema.films.contains(filmF1));
    }
    
    @Test
    public void testCase2_removeNonExistentFilm() throws ParseException {
        // Setup: Do not add Film F1 (already not added by default)
        
        // Action
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: false (Film F1 do not exist in cinema)
        assertFalse("Should return false when removing non-existent film", result);
    }
    
    @Test
    public void testCase3_removeFilmWithFutureScreening() throws ParseException {
        // Setup
        cinema.addFilm(filmF1);
        cinema.addRoom(room);
        
        Date screeningTime = dateFormat.parse("2024-12-02 15:00:00");
        Date currentTimeForSetup = dateFormat.parse("2024-12-01 09:00:00");
        cinema.assignScreening(filmF1, room, screeningTime, currentTimeForSetup);
        
        // Action
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: true, and there is no screening in the cinema.
        assertTrue("Film should be removed successfully when future screening exists", result);
        assertFalse("Film should no longer exist in cinema", cinema.films.contains(filmF1));
        assertEquals("All screenings should be removed", 0, cinema.screenings.size());
    }
    
    @Test
    public void testCase4_removeFilmWithPastScreening() throws ParseException {
        // Setup
        cinema.addFilm(filmF1);
        cinema.addRoom(room);
        
        Date screeningTime = dateFormat.parse("2024-11-30 18:00:00");
        Date currentTimeForSetup = dateFormat.parse("2024-11-30 17:00:00");
        cinema.assignScreening(filmF1, room, screeningTime, currentTimeForSetup);
        
        // Action
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: true, and there is 1 screening in the cinema.
        assertTrue("Film should be removed successfully when past screening exists", result);
        assertFalse("Film should no longer exist in cinema", cinema.films.contains(filmF1));
        assertEquals("Past screening should remain", 1, cinema.screenings.size());
    }
    
    @Test
    public void testCase5_removeFilmWithCurrentTimeScreening() throws ParseException {
        // Setup
        cinema.addFilm(filmF1);
        cinema.addRoom(room);
        
        Date screeningTime = dateFormat.parse("2024-12-01 10:00:00");
        Date currentTimeForSetup = dateFormat.parse("2024-12-01 09:00:00");
        cinema.assignScreening(filmF1, room, screeningTime, currentTimeForSetup);
        
        // Action
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: true, and there is no screening in the cinema.
        assertTrue("Film should be removed successfully when current-time screening exists", result);
        assertFalse("Film should no longer exist in cinema", cinema.films.contains(filmF1));
        assertEquals("Current-time screening should be removed", 0, cinema.screenings.size());
    }
}