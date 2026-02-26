import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CR4Test {
    private Cinema cinema;
    private Film filmF1;
    private Room room;
    
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
    public void testCase1_RemoveFilmWithNoScreenings() {
        // Setup: Add Film F1 to C1
        cinema.addFilm(filmF1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(filmF1, "2024-12-01 10:00:00");
        
        // Expected Output: true
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(filmF1));
    }
    
    @Test
    public void testCase2_RemoveNonExistentFilm() {
        // Setup: Do not add Film F1 (film is not added to cinema)
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(filmF1, "2024-12-01 10:00:00");
        
        // Expected Output: false (Film F1 do not exist in cinema)
        assertFalse("Non-existent film should not be removed", result);
    }
    
    @Test
    public void testCase3_RemoveFilmWithFutureScreening() {
        // Setup: Add Film F1 to C1 and assign future screening
        cinema.addFilm(filmF1);
        cinema.assignScreening(filmF1, room, "2024-12-02 15:00:00", "2024-12-01 09:00:00");
        
        // Verify screening was added
        assertEquals("Screening should be added", 1, room.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(filmF1, "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(filmF1));
        assertEquals("Future screening should be removed", 0, room.getScreenings().size());
    }
    
    @Test
    public void testCase4_RemoveFilmWithPastScreening() {
        // Setup: Add Film F1 to C1 and assign past screening
        cinema.addFilm(filmF1);
        cinema.assignScreening(filmF1, room, "2024-11-30 18:00:00", "2024-11-30 10:00:00");
        
        // Verify screening was added
        assertEquals("Screening should be added", 1, room.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(filmF1, "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is 1 screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(filmF1));
        assertEquals("Past screening should remain", 1, room.getScreenings().size());
    }
    
    @Test
    public void testCase5_RemoveFilmWithCurrentTimeScreening() {
        // Setup: Add Film F1 to C1 and assign screening at current time
        cinema.addFilm(filmF1);
        cinema.assignScreening(filmF1, room, "2024-12-01 10:00:00", "2024-12-01 09:00:00");
        
        // Verify screening was added
        assertEquals("Screening should be added", 1, room.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(filmF1, "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(filmF1));
        assertEquals("Current time screening should be removed", 0, room.getScreenings().size());
    }
}