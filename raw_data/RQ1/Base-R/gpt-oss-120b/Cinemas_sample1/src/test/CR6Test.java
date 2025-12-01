import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class CR6Test {
    
    private Cinema cinema;
    private Film film1;
    private Room room1;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film("Film1");
        room1 = new Room("Room1");
    }
    
    @Test
    public void testCase1_CancelFutureScreening() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening("Film1", "Room1", "2024-10-03 12:00:00", "2024-10-01 12:00:00");
        
        // Action: cancel Screening1 at "2024-10-02 12:00:00"
        boolean result = cinema.cancelScreening("Film1", "Room1", "2024-10-03 12:00:00", "2024-10-02 12:00:00");
        
        // Verify result
        assertTrue("Screening should be cancelled successfully", result);
        
        // Verify screening was removed
        List<Screening> screenings = cinema.getScreenings();
        assertEquals("There should be no screenings left", 0, screenings.size());
    }
    
    @Test
    public void testCase2_CancelNonExistentScreening() {
        // Setup: Create Cinema C1, Film1, Room1 (but no screening assigned)
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: cancel non-existent Screening2 at current time "2024-10-03 12:05:00"
        boolean result = cinema.cancelScreening("Film1", "Room1", "2024-10-02 12:05:00", "2024-10-03 12:05:00");
        
        // Verify result
        assertFalse("Should return false for non-existent screening", result);
    }
    
    @Test
    public void testCase3_CancelAtExactScreeningTimeBoundary() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening("Film1", "Room1", "2024-12-03 10:00:00", "2024-12-02 10:00:00");
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00" (exact screening time)
        boolean result = cinema.cancelScreening("Film1", "Room1", "2024-12-03 10:00:00", "2024-12-03 10:00:00");
        
        // Verify result
        assertFalse("Should return false when current time equals screening time", result);
        
        // Verify screening still exists
        List<Screening> screenings = cinema.getScreenings();
        assertEquals("There should be 1 screening remaining", 1, screenings.size());
    }
    
    @Test
    public void testCase4_CancelAlreadyCancelledScreening() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening("Film1", "Room1", "2024-10-03 12:00:00", "2024-10-01 12:00:00");
        
        // First cancellation - should succeed
        boolean firstResult = cinema.cancelScreening("Film1", "Room1", "2024-10-03 12:00:00", "2024-10-02 12:00:00");
        assertTrue("First cancellation should succeed", firstResult);
        
        // Action: try to cancel the same screening again at "2024-10-02 12:05:00"
        boolean result = cinema.cancelScreening("Film1", "Room1", "2024-10-03 12:00:00", "2024-10-02 12:05:00");
        
        // Verify result
        assertFalse("Should return false for already cancelled screening", result);
        
        // Verify no screenings exist
        List<Screening> screenings = cinema.getScreenings();
        assertEquals("There should be no screenings", 0, screenings.size());
    }
    
    @Test
    public void testCase5_CancelPastScreeningAfterShowtime() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening("Film1", "Room1", "2024-12-01 14:00:00", "2024-11-30 10:00:00");
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00" (after screening time)
        boolean result = cinema.cancelScreening("Film1", "Room1", "2024-12-01 14:00:00", "2024-12-03 10:00:00");
        
        // Verify result
        assertFalse("Should return false for past screening", result);
        
        // Verify screening still exists
        List<Screening> screenings = cinema.getScreenings();
        assertEquals("There should be 1 screening remaining", 1, screenings.size());
    }
}