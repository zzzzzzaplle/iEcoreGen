import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class CR4Test {
    
    private Cinema cinema;
    private Film filmF1;
    private Room room;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        filmF1 = new Film();
        filmF1.setTitle("F1");
        
        room = new Room();
        room.setName("Room1");
        cinema.addRoom(room);
    }
    
    @Test
    public void testCase1_removeFilmWithNoScreenings() {
        // Setup: Create Cinema C1 and add Film F1 to C1
        cinema.addFilm(filmF1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm("F1", "2024-12-01 10:00:00");
        
        // Expected Output: true
        assertTrue("Film should be removed successfully", result);
        
        // Verify film is removed from cinema
        Map<String, Film> films = cinema.getFilms();
        assertFalse("Film F1 should not exist in cinema after removal", films.containsKey("F1"));
    }
    
    @Test
    public void testCase2_removeNonExistentFilm() {
        // Setup: Create Cinema C1, do not add Film F1
        // Film F1 is not added to cinema
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm("F1", "2024-12-01 10:00:00");
        
        // Expected Output: false (Film F1 does not exist in cinema)
        assertFalse("Non-existent film should not be removable", result);
    }
    
    @Test
    public void testCase3_removeFilmWithFutureScreening() {
        // Setup: Create Cinema C1, add Film F1 to C1
        cinema.addFilm(filmF1);
        
        // Assign screening for F1 at "2024-12-02 15:00:00" (the screening time)
        boolean screeningAssigned = cinema.assignScreening("F1", "Room1", "2024-12-02 15:00:00", "2024-12-01 10:00:00");
        assertTrue("Screening should be assigned successfully", screeningAssigned);
        
        // Verify screening exists before removal
        assertEquals("There should be 1 screening before removal", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm("F1", "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully", result);
        
        // Verify film is removed
        Map<String, Film> films = cinema.getFilms();
        assertFalse("Film F1 should not exist in cinema after removal", films.containsKey("F1"));
        
        // Verify future screening is removed
        assertEquals("There should be no screenings after removal", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_removeFilmWithPastScreening() {
        // Setup: Create Cinema C1, add Film F1 to C1
        cinema.addFilm(filmF1);
        
        // Schedule screening for F1 at "2024-11-30 18:00:00"
        boolean screeningAssigned = cinema.assignScreening("F1", "Room1", "2024-11-30 18:00:00", "2024-11-30 10:00:00");
        assertTrue("Screening should be assigned successfully", screeningAssigned);
        
        // Verify screening exists before removal
        assertEquals("There should be 1 screening before removal", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm("F1", "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is 1 screening in the cinema
        assertTrue("Film should be removed successfully", result);
        
        // Verify film is removed
        Map<String, Film> films = cinema.getFilms();
        assertFalse("Film F1 should not exist in cinema after removal", films.containsKey("F1"));
        
        // Verify past screening is NOT removed (screening time < current time)
        assertEquals("There should be 1 screening remaining (past screening)", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_removeFilmWithCurrentTimeScreening() {
        // Setup: Create Cinema C1, add Film F1 to C1
        cinema.addFilm(filmF1);
        
        // Schedule screening for F1 at "2024-12-01 10:00:00"
        boolean screeningAssigned = cinema.assignScreening("F1", "Room1", "2024-12-01 10:00:00", "2024-12-01 09:00:00");
        assertTrue("Screening should be assigned successfully", screeningAssigned);
        
        // Verify screening exists before removal
        assertEquals("There should be 1 screening before removal", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm("F1", "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully", result);
        
        // Verify film is removed
        Map<String, Film> films = cinema.getFilms();
        assertFalse("Film F1 should not exist in cinema after removal", films.containsKey("F1"));
        
        // Verify current-time screening is removed (screening time >= current time)
        assertEquals("There should be no screenings after removal", 0, cinema.getScreenings().size());
    }
}