import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.ParseException;

public class CR4Test {
    private Cinema cinema;
    private Film film1;
    private Film film2;
    private Room room1;
    private DateHelper dateHelper;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film();
        film2 = new Film();
        room1 = new Room();
        dateHelper = new DateHelper();
    }
    
    @Test
    public void testCase1_removeFilmWithNoScreenings() throws ParseException {
        // Setup: Create Cinema C1 and add Film F1 to C1
        cinema.addFilm(film1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = DateHelper.parseDate("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(film1, currentTime);
        
        // Expected Output: true
        assertTrue("Film should be removed successfully when it has no screenings", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(film1));
    }
    
    @Test
    public void testCase2_removeNonExistentFilm() throws ParseException {
        // Setup: Create Cinema C1 (do not add Film F1)
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = DateHelper.parseDate("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(film1, currentTime);
        
        // Expected Output: false (Film F1 does not exist in cinema)
        assertFalse("Should return false when trying to remove non-existent film", result);
    }
    
    @Test
    public void testCase3_removeFilmWithFutureScreening() throws ParseException {
        // Setup: Create Cinema C1, add Film F1 to C1, and assign screening for F1 at "2024-12-02 15:00:00"
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = DateHelper.parseDate("2024-12-02 15:00:00");
        Date setupCurrentTime = DateHelper.parseDate("2024-12-01 09:00:00");
        cinema.assignScreening(film1, room1, screeningTime, setupCurrentTime);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = DateHelper.parseDate("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(film1, currentTime);
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully when it has future screenings", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(film1));
        assertEquals("All future screenings should be removed", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_removeFilmWithPastScreening() throws ParseException {
        // Setup: Create Cinema C1, add Film F1 to C1, and schedule screening for F1 at "2024-11-30 18:00:00"
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = DateHelper.parseDate("2024-11-30 18:00:00");
        Date setupCurrentTime = DateHelper.parseDate("2024-11-30 17:00:00");
        cinema.assignScreening(film1, room1, screeningTime, setupCurrentTime);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = DateHelper.parseDate("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(film1, currentTime);
        
        // Expected Output: true, and there is 1 screening in the cinema
        assertTrue("Film should be removed successfully when it has past screenings", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(film1));
        assertEquals("Past screenings should remain in the cinema", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_removeFilmWithCurrentTimeScreening() throws ParseException {
        // Setup: Create Cinema C1, add Film F1 to C1, and schedule screening for F1 at "2024-12-01 10:00:00"
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = DateHelper.parseDate("2024-12-01 10:00:00");
        Date setupCurrentTime = DateHelper.parseDate("2024-12-01 09:00:00");
        cinema.assignScreening(film1, room1, screeningTime, setupCurrentTime);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = DateHelper.parseDate("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(film1, currentTime);
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully when it has current-time screening", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(film1));
        assertEquals("Current-time screening should be removed", 0, cinema.getScreenings().size());
    }
}