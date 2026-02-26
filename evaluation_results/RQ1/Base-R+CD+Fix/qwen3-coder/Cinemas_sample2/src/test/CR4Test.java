import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CR4Test {
    private Cinema cinema;
    private Film filmF1;
    private Room room;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        filmF1 = new Film();
        room = new Room();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cinema.addRoom(room);
    }
    
    @Test
    public void testCase1_RemoveFilmWithNoScreenings() throws Exception {
        // Setup: Create Cinema C1 and add Film F1 to C1
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
        // Setup: Create Cinema C1 and do not add Film F1
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: false (Film F1 do not exist in cinema)
        assertFalse("Non-existent film should not be removed", result);
    }
    
    @Test
    public void testCase3_RemoveFilmWithFutureScreening() throws Exception {
        // Setup: Create Cinema C1, add Film F1 to C1, and assign screening for F1 at "2024-12-02 15:00:00"
        cinema.addFilm(filmF1);
        
        Screening screening = new Screening();
        screening.setFilm(filmF1);
        screening.setRoom(room);
        screening.setTime(dateFormat.parse("2024-12-02 15:00:00"));
        
        Date setupTime = dateFormat.parse("2024-12-01 09:00:00");
        cinema.assignScreening(filmF1, room, screening, setupTime);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film with future screening should be removed", result);
        assertFalse("Film should no longer be in cinema", cinema.getFilms().contains(filmF1));
        assertEquals("All future screenings should be removed", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_RemoveFilmWithPastScreening() throws Exception {
        // Setup: Create Cinema C1, add Film F1 to C1, and schedule screening for F1 at "2024-11-30 18:00:00"
        cinema.addFilm(filmF1);
        
        Screening screening = new Screening();
        screening.setFilm(filmF1);
        screening.setRoom(room);
        screening.setTime(dateFormat.parse("2024-11-30 18:00:00"));
        
        Date setupTime = dateFormat.parse("2024-11-30 17:00:00");
        cinema.assignScreening(filmF1, room, screening, setupTime);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: true, and there is 1 screening in the cinema
        assertTrue("Film with past screening should be removed", result);
        assertFalse("Film should no longer be in cinema", cinema.getFilms().contains(filmF1));
        assertEquals("Past screenings should remain", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_RemoveFilmWithCurrentTimeScreening() throws Exception {
        // Setup: Create Cinema C1, add Film F1 to C1, and schedule screening for F1 at "2024-12-01 10:00:00"
        cinema.addFilm(filmF1);
        
        Screening screening = new Screening();
        screening.setFilm(filmF1);
        screening.setRoom(room);
        screening.setTime(dateFormat.parse("2024-12-01 10:00:00"));
        
        Date setupTime = dateFormat.parse("2024-12-01 09:00:00");
        cinema.assignScreening(filmF1, room, screening, setupTime);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film with current-time screening should be removed", result);
        assertFalse("Film should no longer be in cinema", cinema.getFilms().contains(filmF1));
        assertEquals("Current-time screenings should be removed", 0, cinema.getScreenings().size());
    }
}