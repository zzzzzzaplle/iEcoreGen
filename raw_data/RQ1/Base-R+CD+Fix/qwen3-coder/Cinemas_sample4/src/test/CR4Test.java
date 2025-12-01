import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR4Test {
    private Cinema cinema;
    private Film filmF1;
    private Room room1;
    private Screening screening;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        filmF1 = new Film();
        room1 = new Room();
        screening = new Screening();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_removeFilmWithNoScreenings() throws ParseException {
        // Setup: Create Cinema C1 and add Film F1 to C1
        cinema.addFilm(filmF1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: true
        assertTrue("Film should be removed successfully when there are no screenings", result);
        assertFalse("Film should no longer be in cinema", cinema.getFilms().contains(filmF1));
    }
    
    @Test
    public void testCase2_removeNonExistentFilm() throws ParseException {
        // Setup: Create Cinema C1, do not add Film F1
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: false (Film F1 does not exist in cinema)
        assertFalse("Should return false when trying to remove non-existent film", result);
    }
    
    @Test
    public void testCase3_removeFilmWithFutureScreening() throws ParseException {
        // Setup: Create Cinema C1, add Film F1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(room1);
        
        // Assign screening for F1 at "2024-12-02 15:00:00" (the screening time)
        Screening futureScreening = new Screening();
        Date screeningTime = dateFormat.parse("2024-12-02 15:00:00");
        futureScreening.setTime(screeningTime);
        
        Date setupTime = dateFormat.parse("2024-11-30 10:00:00");
        cinema.assignScreening(filmF1, room1, futureScreening, setupTime);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully when there are future screenings", result);
        assertFalse("Film should no longer be in cinema", cinema.getFilms().contains(filmF1));
        assertEquals("All future screenings should be removed", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_removeFilmWithPastScreening() throws ParseException {
        // Setup: Create Cinema C1, add Film F1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(room1);
        
        // Schedule screening for F1 at "2024-11-30 18:00:00"
        Screening pastScreening = new Screening();
        Date screeningTime = dateFormat.parse("2024-11-30 18:00:00");
        pastScreening.setTime(screeningTime);
        
        Date setupTime = dateFormat.parse("2024-11-29 10:00:00");
        cinema.assignScreening(filmF1, room1, pastScreening, setupTime);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: true, and there is 1 screening in the cinema
        assertTrue("Film should be removed successfully when there are past screenings", result);
        assertFalse("Film should no longer be in cinema", cinema.getFilms().contains(filmF1));
        assertEquals("Past screenings should remain in cinema", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_removeFilmWithCurrentTimeScreening() throws ParseException {
        // Setup: Create Cinema C1, add Film F1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(room1);
        
        // Schedule screening for F1 at "2024-12-01 10:00:00"
        Screening currentScreening = new Screening();
        Date screeningTime = dateFormat.parse("2024-12-01 10:00:00");
        currentScreening.setTime(screeningTime);
        
        Date setupTime = dateFormat.parse("2024-11-30 10:00:00");
        cinema.assignScreening(filmF1, room1, currentScreening, setupTime);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully when there is screening at current time", result);
        assertFalse("Film should no longer be in cinema", cinema.getFilms().contains(filmF1));
        assertEquals("Screenings at current time should be removed", 0, cinema.getScreenings().size());
    }
}