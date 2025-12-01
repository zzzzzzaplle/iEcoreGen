import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR4Test {
    private Cinema cinema;
    private Film film1;
    private Film film2;
    private Room room1;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film("The Matrix", 136);
        film2 = new Film("Inception", 148);
        room1 = new Room("Room A", 100);
        cinema.addRoom(room1);
    }
    
    @Test
    public void testCase1_RemoveFilmWithNoScreenings() {
        // Setup: Create Cinema C1 and add Film F1 to C1
        cinema.addFilm(film1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(film1, "2024-12-01 10:00:00");
        
        // Expected Output: true
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(film1));
    }
    
    @Test
    public void testCase2_RemoveNonExistentFilm() {
        // Setup: Create Cinema C1 and do not add Film F1
        // Film F1 is created but not added to cinema
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(film1, "2024-12-01 10:00:00");
        
        // Expected Output: false (Film F1 does not exist in cinema)
        assertFalse("Non-existent film should not be removed", result);
    }
    
    @Test
    public void testCase3_RemoveFilmWithFutureScreening() {
        // Setup: Create Cinema C1, add Film F1 to C1, and assign screening for F1 at "2024-12-02 15:00:00"
        cinema.addFilm(film1);
        cinema.addFilm(film2); // Add another film to verify only F1's screenings are removed
        cinema.assignScreening(film1, room1, "2024-12-02 15:00:00", "2024-12-01 09:00:00");
        cinema.assignScreening(film2, room1, "2024-12-02 18:00:00", "2024-12-01 09:00:00");
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(film1, "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is no screening in the cinema for F1
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(film1));
        
        // Verify that F1's future screening was removed
        List<Screening> screenings = cinema.getScreenings();
        boolean foundF1Screening = false;
        for (Screening screening : screenings) {
            if (screening.getFilm().equals(film1)) {
                foundF1Screening = true;
                break;
            }
        }
        assertFalse("Film F1's future screening should be removed", foundF1Screening);
        
        // Verify that F2's screening still exists
        boolean foundF2Screening = false;
        for (Screening screening : screenings) {
            if (screening.getFilm().equals(film2)) {
                foundF2Screening = true;
                break;
            }
        }
        assertTrue("Film F2's screening should still exist", foundF2Screening);
    }
    
    @Test
    public void testCase4_RemoveFilmWithPastScreening() {
        // Setup: Create Cinema C1, add Film F1 to C1, and schedule screening for F1 at "2024-11-30 18:00:00"
        cinema.addFilm(film1);
        cinema.assignScreening(film1, room1, "2024-11-30 18:00:00", "2024-11-30 17:00:00");
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(film1, "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is 1 screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(film1));
        
        // Verify that past screening still exists
        List<Screening> screenings = cinema.getScreenings();
        assertEquals("Past screening should still exist", 1, screenings.size());
        assertEquals("Screening should be for Film F1", film1, screenings.get(0).getFilm());
    }
    
    @Test
    public void testCase5_RemoveFilmWithCurrentTimeScreening() {
        // Setup: Create Cinema C1, add Film F1 to C1, and schedule screening for F1 at "2024-12-01 10:00:00"
        cinema.addFilm(film1);
        cinema.assignScreening(film1, room1, "2024-12-01 10:00:00", "2024-12-01 09:00:00");
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(film1, "2024-12-01 10:00:00");
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilms().contains(film1));
        
        // Verify that current-time screening was removed
        List<Screening> screenings = cinema.getScreenings();
        assertEquals("Current-time screening should be removed", 0, screenings.size());
    }
}