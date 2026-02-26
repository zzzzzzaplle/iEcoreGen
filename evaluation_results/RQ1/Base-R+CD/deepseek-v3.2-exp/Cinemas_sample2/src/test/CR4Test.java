import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR4Test {
    
    private Cinema cinema;
    private Film film1;
    private Room room1;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        // Initialize objects before each test
        cinema = new Cinema();
        film1 = new Film();
        room1 = new Room();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_removeFilmWithNoScreenings() throws Exception {
        // Setup: Create Cinema C1 and add Film F1 to C1
        cinema.addFilm(film1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(film1, "2024-12-01 10:00:00");
        
        // Expected Output: true
        assertTrue("Film should be removed successfully when there are no screenings", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(film1));
    }
    
    @Test
    public void testCase2_removeNonExistentFilm() throws Exception {
        // Setup: Create Cinema C1 (do not add Film F1)
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(film1, "2024-12-01 10:00:00");
        
        // Expected Output: false (Film F1 do not exist in cinema)
        assertFalse("Should return false when trying to remove non-existent film", result);
    }
    
    @Test
    public void testCase3_removeFilmWithFutureScreening() throws Exception {
        // Setup: Create Cinema C1, add Film F1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Assign screening for F1 at "2024-12-02 15:00:00" (the screening time)
        Screening screening = new Screening();
        screening.setTime(dateFormat.parse("2024-12-02 15:00:00"));
        cinema.assignScreening(film1, room1, screening, "2024-12-01 10:00:00");
        
        // Verify screening was added
        assertEquals("Should have 1 screening before removal", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(film1, "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(film1));
        assertEquals("Future screening should be removed", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_removeFilmWithPastScreening() throws Exception {
        // Setup: Create Cinema C1, add Film F1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Schedule screening for F1 at "2024-11-30 18:00:00"
        Screening screening = new Screening();
        screening.setTime(dateFormat.parse("2024-11-30 18:00:00"));
        cinema.assignScreening(film1, room1, screening, "2024-11-30 10:00:00");
        
        // Verify screening was added
        assertEquals("Should have 1 screening before removal", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(film1, "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is 1 screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(film1));
        assertEquals("Past screening should remain in cinema", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_removeFilmWithCurrentTimeScreening() throws Exception {
        // Setup: Create Cinema C1, add Film F1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Schedule screening for F1 at "2024-12-01 10:00:00"
        Screening screening = new Screening();
        screening.setTime(dateFormat.parse("2024-12-01 10:00:00"));
        cinema.assignScreening(film1, room1, screening, "2024-12-01 09:00:00");
        
        // Verify screening was added
        assertEquals("Should have 1 screening before removal", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(film1, "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(film1));
        assertEquals("Current-time screening should be removed", 0, cinema.getScreenings().size());
    }
}