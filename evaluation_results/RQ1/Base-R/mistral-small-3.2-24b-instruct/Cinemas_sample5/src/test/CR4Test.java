import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;

public class CR4Test {
    private Cinema cinema;
    private Film film1;
    private Room room1;
    
    @Before
    public void setUp() {
        // Initialize cinema and test objects before each test
        cinema = new Cinema();
        film1 = new Film();
        film1.setFilmId("F1");
        room1 = new Room();
        room1.setRoomId("R1");
    }
    
    @Test
    public void testCase1_RemoveFilmWithNoScreenings() throws ParseException {
        // Setup: Create Cinema C1 and add Film F1 to C1
        cinema.addFilm(film1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(film1, "2024-12-01 10:00:00");
        
        // Expected Output: true
        assertTrue("Film should be removed successfully when no screenings exist", result);
        assertFalse("Film should no longer exist in cinema after removal", cinema.getFilms().contains(film1));
        assertFalse("Screenings map should not contain film after removal", cinema.getScreenings().containsKey(film1));
    }
    
    @Test
    public void testCase2_RemoveNonExistentFilm() throws ParseException {
        // Setup: Create Cinema C1 (Film F1 is not added)
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(film1, "2024-12-01 10:00:00");
        
        // Expected Output: false (Film F1 does not exist in cinema)
        assertFalse("Should return false when trying to remove non-existent film", result);
        assertFalse("Film should not exist in cinema", cinema.getFilms().contains(film1));
    }
    
    @Test
    public void testCase3_RemoveFilmWithFutureScreening() throws ParseException {
        // Setup: Create Cinema C1, add Film F1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Assign screening for F1 at "2024-12-02 15:00:00" (future screening)
        cinema.assignScreening(film1, room1, "2024-12-02 15:00:00", "2024-12-01 10:00:00");
        
        // Verify screening was added
        assertTrue("Screening should exist before removal", cinema.getScreenings().get(film1).size() == 1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(film1, "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully with future screening", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(film1));
        assertFalse("Screenings map should not contain film after removal", cinema.getScreenings().containsKey(film1));
    }
    
    @Test
    public void testCase4_RemoveFilmWithPastScreening() throws ParseException {
        // Setup: Create Cinema C1, add Film F1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Schedule screening for F1 at "2024-11-30 18:00:00" (past screening)
        cinema.assignScreening(film1, room1, "2024-11-30 18:00:00", "2024-11-30 10:00:00");
        
        // Verify screening was added
        assertTrue("Screening should exist before removal", cinema.getScreenings().get(film1).size() == 1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(film1, "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is 1 screening in the cinema
        assertTrue("Film should be removed successfully with past screening", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(film1));
        assertFalse("Screenings map should not contain film after removal", cinema.getScreenings().containsKey(film1));
    }
    
    @Test
    public void testCase5_RemoveFilmWithCurrentTimeScreening() throws ParseException {
        // Setup: Create Cinema C1, add Film F1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Schedule screening for F1 at "2024-12-01 10:00:00" (current time screening)
        cinema.assignScreening(film1, room1, "2024-12-01 10:00:00", "2024-12-01 09:00:00");
        
        // Verify screening was added
        assertTrue("Screening should exist before removal", cinema.getScreenings().get(film1).size() == 1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(film1, "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully with current-time screening", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(film1));
        assertFalse("Screenings map should not contain film after removal", cinema.getScreenings().containsKey(film1));
    }
}