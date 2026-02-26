import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR6Test {
    
    private Cinema cinema;
    private Film film1;
    private Room room1;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film("Film1", 120);
        room1 = new Room("Room1", 100);
    }
    
    @Test
    public void testCase1_CancelFutureScreening() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening("Film1", "Room1", "2024-10-03 12:00:00", "2024-10-01 12:00:00");
        
        // Action: cancel Screening1 at "2024-10-02 12:00:00"
        boolean result = cinema.cancelScreening("Film1", "Room1", "2024-10-03 12:00:00", "2024-10-02 12:00:00");
        
        // Expected Output: true, there is no screening in the cinema C1
        assertTrue("Screening should be canceled successfully", result);
        assertEquals("There should be no screenings left", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_CancelNonExistentScreening() {
        // Setup: Create Cinema C1, but no screening assigned
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        // No screening assigned (Screening2 doesn't exist)
        
        // Action: cancel Screening2 at current time "2024-10-03 12:05:00"
        boolean result = cinema.cancelScreening("Film1", "Room1", "2024-10-02 12:05:00", "2024-10-03 12:05:00");
        
        // Expected Output: false
        assertFalse("Non-existent screening should not be canceled", result);
    }
    
    @Test
    public void testCase3_CancelAtExactScreeningTimeBoundary() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening("Film1", "Room1", "2024-12-03 10:00:00", "2024-12-01 10:00:00");
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        boolean result = cinema.cancelScreening("Film1", "Room1", "2024-12-03 10:00:00", "2024-12-03 10:00:00");
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Screening at exact current time should not be canceled", result);
        assertEquals("There should still be 1 screening", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_CancelAlreadyCancelledScreening() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening("Film1", "Room1", "2024-10-03 12:00:00", "2024-10-01 12:00:00");
        
        // First cancellation (should succeed)
        boolean firstCancel = cinema.cancelScreening("Film1", "Room1", "2024-10-03 12:00:00", "2024-10-02 12:00:00");
        assertTrue("First cancellation should succeed", firstCancel);
        
        // Action: cancel Screening1 at current time "2024-10-02 12:05:00"
        boolean result = cinema.cancelScreening("Film1", "Room1", "2024-10-03 12:00:00", "2024-10-02 12:05:00");
        
        // Expected Output: false, there is no screening in the cinema
        assertFalse("Already canceled screening should not be canceled again", result);
        assertEquals("There should be no screenings", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_CancelPastScreeningAfterShowtime() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening("Film1", "Room1", "2024-12-01 14:00:00", "2024-11-01 10:00:00");
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        boolean result = cinema.cancelScreening("Film1", "Room1", "2024-12-01 14:00:00", "2024-12-03 10:00:00");
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Past screening should not be canceled", result);
        assertEquals("There should still be 1 screening", 1, cinema.getScreenings().size());
    }
}