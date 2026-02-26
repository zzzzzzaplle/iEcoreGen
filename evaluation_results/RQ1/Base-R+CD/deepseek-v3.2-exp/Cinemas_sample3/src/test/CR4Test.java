import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.util.Date;

public class CR4Test {
    
    private Cinema cinema;
    private Film film1;
    private Film film2;
    private Room room1;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film();
        film2 = new Film();
        room1 = new Room();
    }
    
    @Test
    public void testCase1_removeFilmWithNoScreenings() throws ParseException {
        // Setup: Create Cinema C1 and add Film F1 to C1
        cinema.addFilm(film1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = DateUtils.parseDate("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(film1, currentTime);
        
        // Expected Output: true
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(film1));
    }
    
    @Test
    public void testCase2_removeNonExistentFilm() throws ParseException {
        // Setup: Create Cinema C1, do not add Film F1
        // (film1 is not added to cinema)
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = DateUtils.parseDate("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(film1, currentTime);
        
        // Expected Output: false (Film F1 does not exist in cinema)
        assertFalse("Non-existent film should not be removed", result);
    }
    
    @Test
    public void testCase3_removeFilmWithFutureScreening() throws ParseException {
        // Setup: Create Cinema C1, add Film F1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Assign screening for F1 at "2024-12-02 15:00:00"
        Date screeningTime = DateUtils.parseDate("2024-12-02 15:00:00");
        Date currentTimeSetup = DateUtils.parseDate("2024-12-01 09:00:00");
        cinema.assignScreening(film1, room1, screeningTime, currentTimeSetup);
        
        // Verify setup: there should be 1 screening
        assertEquals("Setup verification: should have 1 screening", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = DateUtils.parseDate("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(film1, currentTime);
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(film1));
        assertEquals("All future screenings should be removed", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_removeFilmWithPastScreening() throws ParseException {
        // Setup: Create Cinema C1, add Film F1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Schedule screening for F1 at "2024-11-30 18:00:00"
        Date screeningTime = DateUtils.parseDate("2024-11-30 18:00:00");
        Date currentTimeSetup = DateUtils.parseDate("2024-11-30 10:00:00");
        cinema.assignScreening(film1, room1, screeningTime, currentTimeSetup);
        
        // Verify setup: there should be 1 screening
        assertEquals("Setup verification: should have 1 screening", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = DateUtils.parseDate("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(film1, currentTime);
        
        // Expected Output: true, and there is 1 screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(film1));
        assertEquals("Past screenings should remain", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_removeFilmWithCurrentTimeScreening() throws ParseException {
        // Setup: Create Cinema C1, add Film F1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Schedule screening for F1 at "2024-12-01 10:00:00"
        Date screeningTime = DateUtils.parseDate("2024-12-01 10:00:00");
        Date currentTimeSetup = DateUtils.parseDate("2024-12-01 09:00:00");
        cinema.assignScreening(film1, room1, screeningTime, currentTimeSetup);
        
        // Verify setup: there should be 1 screening
        assertEquals("Setup verification: should have 1 screening", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = DateUtils.parseDate("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(film1, currentTime);
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(film1));
        assertEquals("Current-time screenings should be removed", 0, cinema.getScreenings().size());
    }
}