import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CR4Test {
    private Cinema cinema;
    private Film film1;
    private Room room1;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film("F1");
        room1 = new Room("Room1");
        cinema.addRoom(room1);
    }
    
    @Test
    public void testCase1_removeFilmWithNoScreenings() {
        // Setup: Create Cinema C1 and add Film F1 to C1
        cinema.addFilm(film1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm("F1", "2024-12-01 10:00:00");
        
        // Expected Output: true
        assertTrue("Film should be removed successfully when it has no screenings", result);
        assertFalse("Film F1 should no longer exist in cinema", cinema.addFilm(film1));
    }
    
    @Test
    public void testCase2_removeNonExistentFilm() {
        // Setup: Create Cinema C1 (Film F1 is not added)
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm("F1", "2024-12-01 10:00:00");
        
        // Expected Output: false (Film F1 do not exist in cinema)
        assertFalse("Should return false when trying to remove non-existent film", result);
    }
    
    @Test
    public void testCase3_removeFilmWithFutureScreening() {
        // Setup: Create Cinema C1, add Film F1 to C1, and assign screening for F1 at "2024-12-02 15:00:00"
        cinema.addFilm(film1);
        cinema.assignScreening("F1", "Room1", "2024-12-02 15:00:00", "2024-12-01 09:00:00");
        
        // Verify screening was added
        assertEquals("Screening should be added initially", 1, room1.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm("F1", "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertEquals("All future screenings should be removed", 0, room1.getScreenings().size());
        assertFalse("Film F1 should no longer exist in cinema", cinema.addFilm(film1));
    }
    
    @Test
    public void testCase4_removeFilmWithPastScreening() {
        // Setup: Create Cinema C1, add Film F1 to C1, and schedule screening for F1 at "2024-11-30 18:00:00"
        cinema.addFilm(film1);
        cinema.assignScreening("F1", "Room1", "2024-11-30 18:00:00", "2024-11-30 10:00:00");
        
        // Verify screening was added
        assertEquals("Screening should be added initially", 1, room1.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm("F1", "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is 1 screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertEquals("Past screenings should remain in the cinema", 1, room1.getScreenings().size());
        assertFalse("Film F1 should no longer exist in cinema", cinema.addFilm(film1));
    }
    
    @Test
    public void testCase5_removeFilmWithCurrentTimeScreening() {
        // Setup: Create Cinema C1, add Film F1 to C1, and schedule screening for F1 at "2024-12-01 10:00:00"
        cinema.addFilm(film1);
        cinema.assignScreening("F1", "Room1", "2024-12-01 10:00:00", "2024-12-01 09:00:00");
        
        // Verify screening was added
        assertEquals("Screening should be added initially", 1, room1.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm("F1", "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertEquals("Current time screening should be removed", 0, room1.getScreenings().size());
        assertFalse("Film F1 should no longer exist in cinema", cinema.addFilm(film1));
    }
}