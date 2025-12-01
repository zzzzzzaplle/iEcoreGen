import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    private Cinema cinema;
    private Film filmF1;
    private Room room;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        filmF1 = new Film("F1", 120);
        room = new Room("Room1", 100);
    }
    
    @Test
    public void testCase1_RemoveFilmWithNoScreenings() {
        // Setup: Create Cinema C1 and add Film F1
        cinema.addFilm(filmF1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm("F1", "2024-12-01 10:00:00");
        
        // Expected Output: true
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().containsKey("F1"));
    }
    
    @Test
    public void testCase2_RemoveNonExistentFilm() {
        // Setup: Create Cinema C1, do not add Film F1
        // (Film F1 is not added to cinema)
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm("F1", "2024-12-01 10:00:00");
        
        // Expected Output: false (Film F1 does not exist in cinema)
        assertFalse("Non-existent film should not be removed", result);
    }
    
    @Test
    public void testCase3_RemoveFilmWithFutureScreening() {
        // Setup: Create Cinema C1, add Film F1, and assign screening for F1
        cinema.addFilm(filmF1);
        cinema.addRoom(room);
        cinema.assignScreening("F1", "Room1", "2024-12-02 15:00:00", "2024-12-01 09:00:00");
        
        // Verify setup: there should be 1 screening
        assertEquals("Should have 1 screening before removal", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm("F1", "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().containsKey("F1"));
        assertEquals("All future screenings should be removed", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_RemoveFilmWithPastScreening() {
        // Setup: Create Cinema C1, add Film F1, and schedule screening for F1
        cinema.addFilm(filmF1);
        cinema.addRoom(room);
        
        // Create a past screening (before current time)
        Film film = new Film("F1", 120);
        Room roomObj = new Room("Room1", 100);
        LocalDateTime pastTime = LocalDateTime.parse("2024-11-30 18:00:00", 
            java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Screening pastScreening = new Screening(film, roomObj, pastTime);
        cinema.getScreenings().add(pastScreening);
        
        // Verify setup: there should be 1 screening
        assertEquals("Should have 1 screening before removal", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm("F1", "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is 1 screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().containsKey("F1"));
        assertEquals("Past screening should remain", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_RemoveFilmWithCurrentTimeScreening() {
        // Setup: Create Cinema C1, add Film F1, and schedule screening for F1
        cinema.addFilm(filmF1);
        cinema.addRoom(room);
        
        // Create a screening at exactly current time
        Film film = new Film("F1", 120);
        Room roomObj = new Room("Room1", 100);
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", 
            java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Screening currentScreening = new Screening(film, roomObj, currentTime);
        cinema.getScreenings().add(currentScreening);
        
        // Verify setup: there should be 1 screening
        assertEquals("Should have 1 screening before removal", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm("F1", "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().containsKey("F1"));
        assertEquals("Current time screening should be removed", 0, cinema.getScreenings().size());
    }
}