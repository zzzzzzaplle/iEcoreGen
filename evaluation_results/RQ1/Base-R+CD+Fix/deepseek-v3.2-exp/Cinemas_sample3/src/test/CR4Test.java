import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;

public class CR4Test {
    
    private Cinema cinema;
    private Film film1;
    
    @Before
    public void setUp() {
        // Initialize cinema and film before each test
        cinema = new Cinema();
        film1 = new Film();
    }
    
    @Test
    public void testCase1_removeFilmWithNoScreenings() throws ParseException {
        // Setup: Create Cinema C1 and add Film F1 to C1
        cinema.addFilm(film1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(film1, "2024-12-01 10:00:00");
        
        // Expected Output: true
        assertTrue("Film should be removed successfully when no screenings exist", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(film1));
    }
    
    @Test
    public void testCase2_removeNonExistentFilm() throws ParseException {
        // Setup: Create Cinema C1 (do not add Film F1)
        // Film F1 is not added to cinema
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(film1, "2024-12-01 10:00:00");
        
        // Expected Output: false (Film F1 do not exist in cinema)
        assertFalse("Should return false when film does not exist", result);
    }
    
    @Test
    public void testCase3_removeFilmWithFutureScreening() throws ParseException {
        // Setup: Create Cinema C1, add Film F1 to C1
        cinema.addFilm(film1);
        Room room1 = new Room();
        cinema.addRoom(room1);
        
        // Assign screening for F1 at "2024-12-02 15:00:00" (the screening time)
        cinema.assignScreening(film1, room1, "2024-12-02 15:00:00", "2024-12-01 09:00:00");
        
        // Verify screening was added
        assertEquals("Should have 1 screening before removal", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(film1, "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully with future screening", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(film1));
        assertEquals("All future screenings should be removed", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_removeFilmWithPastScreening() throws ParseException {
        // Setup: Create Cinema C1, add Film F1 to C1
        cinema.addFilm(film1);
        Room room1 = new Room();
        cinema.addRoom(room1);
        
        // Schedule screening for F1 at "2024-11-30 18:00:00"
        cinema.assignScreening(film1, room1, "2024-11-30 18:00:00", "2024-11-30 09:00:00");
        
        // Verify screening was added
        assertEquals("Should have 1 screening before removal", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(film1, "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is 1 screening in the cinema
        assertTrue("Film should be removed successfully with past screening", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(film1));
        assertEquals("Past screenings should remain in cinema", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_removeFilmWithCurrentTimeScreening() throws ParseException {
        // Setup: Create Cinema C1, add Film F1 to C1
        cinema.addFilm(film1);
        Room room1 = new Room();
        cinema.addRoom(room1);
        
        // Schedule screening for F1 at "2024-12-01 10:00:00"
        cinema.assignScreening(film1, room1, "2024-12-01 10:00:00", "2024-12-01 09:00:00");
        
        // Verify screening was added
        assertEquals("Should have 1 screening before removal", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(film1, "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully with current-time screening", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(film1));
        assertEquals("Current-time screening should be removed", 0, cinema.getScreenings().size());
    }
}