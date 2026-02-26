import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class CR4Test {
    
    private Cinema cinema;
    private Film filmF1;
    private Room room;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        filmF1 = new Film("F1");
        room = new Room("Room1");
    }
    
    @Test
    public void testCase1_removeFilmWithNoScreenings() {
        // Setup: Create Cinema C1 and add Film F1 to C1
        cinema.addFilm(filmF1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm("F1", "2024-12-01 10:00:00");
        
        // Expected Output: true
        assertTrue("Film should be removed successfully when there are no screenings", result);
        assertFalse("Film F1 should no longer exist in cinema", cinema.getFilms().contains(filmF1));
    }
    
    @Test
    public void testCase2_removeNonExistentFilm() {
        // Setup: Create Cinema C1 (do not add Film F1)
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm("F1", "2024-12-01 10:00:00");
        
        // Expected Output: false (Film F1 does not exist in cinema)
        assertFalse("Should return false when trying to remove non-existent film", result);
    }
    
    @Test
    public void testCase3_removeFilmWithFutureScreening() {
        // Setup: Create Cinema C1, add Film F1 to C1, and assign screening for F1
        cinema.addFilm(filmF1);
        cinema.addRoom(room);
        
        // Assign screening for F1 at "2024-12-02 15:00:00"
        boolean assignmentResult = cinema.assignScreening("F1", "Room1", "2024-12-02 15:00:00", "2024-12-01 10:00:00");
        assertTrue("Screening assignment should succeed", assignmentResult);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm("F1", "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully with future screening", result);
        assertFalse("Film F1 should no longer exist in cinema", cinema.getFilms().contains(filmF1));
        assertEquals("All future screenings should be removed", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_removeFilmWithPastScreening() {
        // Setup: Create Cinema C1, add Film F1 to C1, and schedule screening for F1 at "2024-11-30 18:00:00"
        cinema.addFilm(filmF1);
        cinema.addRoom(room);
        
        // Create and manually add a past screening
        Screening pastScreening = new Screening();
        pastScreening.setFilm(filmF1);
        pastScreening.setRoom(room);
        pastScreening.setScreeningTime(java.time.LocalDateTime.parse("2024-11-30 18:00:00", 
            java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        
        List<Screening> screenings = new ArrayList<>();
        screenings.add(pastScreening);
        cinema.setScreenings(screenings);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm("F1", "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is 1 screening in the cinema
        assertTrue("Film should be removed successfully with past screening", result);
        assertFalse("Film F1 should no longer exist in cinema", cinema.getFilms().contains(filmF1));
        assertEquals("Past screening should remain", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_removeFilmWithCurrentTimeScreening() {
        // Setup: Create Cinema C1, add Film F1 to C1, and schedule screening for F1 at "2024-12-01 10:00:00"
        cinema.addFilm(filmF1);
        cinema.addRoom(room);
        
        // Create and manually add a screening at current time
        Screening currentScreening = new Screening();
        currentScreening.setFilm(filmF1);
        currentScreening.setRoom(room);
        currentScreening.setScreeningTime(java.time.LocalDateTime.parse("2024-12-01 10:00:00", 
            java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        
        List<Screening> screenings = new ArrayList<>();
        screenings.add(currentScreening);
        cinema.setScreenings(screenings);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm("F1", "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully with current-time screening", result);
        assertFalse("Film F1 should no longer exist in cinema", cinema.getFilms().contains(filmF1));
        assertEquals("Current-time screening should be removed", 0, cinema.getScreenings().size());
    }
}