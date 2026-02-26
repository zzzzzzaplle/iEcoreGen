import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;

public class CR4Test {
    private Cinema cinema;
    private Film filmF1;
    private Room room;
    
    @Before
    public void setUp() {
        // Initialize cinema and common objects before each test
        cinema = new Cinema();
        filmF1 = new Film();
        room = new Room();
    }
    
    @Test
    public void testCase1_removeFilmWithNoScreenings() throws ParseException {
        // Setup: Create Cinema C1 and add Film F1 to C1
        cinema.addFilm(filmF1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(filmF1, "2024-12-01 10:00:00");
        
        // Expected Output: true
        assertTrue("Film removal should return true when film exists with no screenings", result);
        assertFalse("Film should be removed from cinema films list", cinema.getFilms().contains(filmF1));
    }
    
    @Test
    public void testCase2_removeNonExistentFilm() throws ParseException {
        // Setup: Create Cinema C1, do not add Film F1
        // (filmF1 is not added to cinema)
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(filmF1, "2024-12-01 10:00:00");
        
        // Expected Output: false (Film F1 do not exist in cinema)
        assertFalse("Film removal should return false when film does not exist", result);
    }
    
    @Test
    public void testCase3_removeFilmWithFutureScreening() throws ParseException {
        // Setup: Create Cinema C1, add Film F1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(room);
        
        // Assign screening for F1 at "2024-12-02 15:00:00" (the screening time)
        cinema.assignScreening(filmF1, room, "2024-12-02 15:00:00", "2024-12-01 09:00:00");
        
        // Verify screening was added
        assertEquals("Should have 1 screening before removal", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(filmF1, "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film removal should return true when film exists with future screenings", result);
        assertFalse("Film should be removed from cinema films list", cinema.getFilms().contains(filmF1));
        assertEquals("All future screenings should be removed", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_removeFilmWithPastScreening() throws ParseException {
        // Setup: Create Cinema C1, add Film F1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(room);
        
        // Schedule screening for F1 at "2024-11-30 18:00:00"
        cinema.assignScreening(filmF1, room, "2024-11-30 18:00:00", "2024-11-30 09:00:00");
        
        // Verify screening was added
        assertEquals("Should have 1 screening before removal", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(filmF1, "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is 1 screening in the cinema
        assertTrue("Film removal should return true when film exists with past screenings", result);
        assertFalse("Film should be removed from cinema films list", cinema.getFilms().contains(filmF1));
        assertEquals("Past screenings should remain in cinema", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_removeFilmWithCurrentTimeScreening() throws ParseException {
        // Setup: Create Cinema C1, add Film F1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(room);
        
        // Schedule screening for F1 at "2024-12-01 10:00:00"
        cinema.assignScreening(filmF1, room, "2024-12-01 10:00:00", "2024-12-01 09:00:00");
        
        // Verify screening was added
        assertEquals("Should have 1 screening before removal", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(filmF1, "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film removal should return true when film exists with current-time screening", result);
        assertFalse("Film should be removed from cinema films list", cinema.getFilms().contains(filmF1));
        assertEquals("Current-time screenings should be removed", 0, cinema.getScreenings().size());
    }
}