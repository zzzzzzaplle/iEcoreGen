import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CR6Test {
    
    private Cinema cinema;
    private Film film1;
    private Room room1;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film();
        film1.setTitle("Film1");
        room1 = new Room();
        room1.setName("Room1");
    }
    
    @Test
    public void testCase1_cancelFutureScreening() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Assign screening for future time
        boolean assigned = cinema.assignScreening("Film1", "Room1", "2024-10-03 12:00:00", "2024-10-02 11:00:00");
        assertTrue("Screening should be assigned successfully", assigned);
        assertEquals("There should be 1 screening before cancellation", 1, cinema.getScreenings().size());
        
        // Action: cancel screening at current time before screening time
        boolean result = cinema.cancelScreening("Film1", "Room1", "2024-10-03 12:00:00", "2024-10-02 12:00:00");
        
        // Expected Output: true, there is no screening in the cinema C1
        assertTrue("Cancellation should be successful", result);
        assertEquals("There should be no screenings after cancellation", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_cancelNonExistentScreening() {
        // Setup: cinema with film and room but no screening assigned
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Verify no screenings exist initially
        assertEquals("There should be no screenings initially", 0, cinema.getScreenings().size());
        
        // Action: try to cancel non-existent screening
        boolean result = cinema.cancelScreening("Film1", "Room1", "2024-10-02 12:05:00", "2024-10-03 12:05:00");
        
        // Expected Output: false
        assertFalse("Cancellation should fail for non-existent screening", result);
        assertEquals("Number of screenings should remain 0", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase3_cancelAtExactScreeningTimeBoundary() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Assign screening
        boolean assigned = cinema.assignScreening("Film1", "Room1", "2024-12-03 10:00:00", "2024-12-02 10:00:00");
        assertTrue("Screening should be assigned successfully", assigned);
        assertEquals("There should be 1 screening before cancellation attempt", 1, cinema.getScreenings().size());
        
        // Action: cancel at exact screening time (current time equals screening time)
        boolean result = cinema.cancelScreening("Film1", "Room1", "2024-12-03 10:00:00", "2024-12-03 10:00:00");
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Cancellation should fail when current time equals screening time", result);
        assertEquals("Number of screenings should remain 1", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_cancelAlreadyCancelledScreening() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Assign screening
        boolean assigned = cinema.assignScreening("Film1", "Room1", "2024-10-03 12:00:00", "2024-10-02 11:00:00");
        assertTrue("Screening should be assigned successfully", assigned);
        assertEquals("There should be 1 screening before first cancellation", 1, cinema.getScreenings().size());
        
        // First cancellation (should succeed)
        boolean firstCancel = cinema.cancelScreening("Film1", "Room1", "2024-10-03 12:00:00", "2024-10-02 12:00:00");
        assertTrue("First cancellation should be successful", firstCancel);
        assertEquals("There should be no screenings after first cancellation", 0, cinema.getScreenings().size());
        
        // Action: try to cancel the same screening again
        boolean result = cinema.cancelScreening("Film1", "Room1", "2024-10-03 12:00:00", "2024-10-02 12:05:00");
        
        // Expected Output: false, there is no screening in the cinema
        assertFalse("Second cancellation should fail for already cancelled screening", result);
        assertEquals("Number of screenings should remain 0", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_cancelPastScreeningAfterShowtime() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Assign screening in the past relative to cancellation time
        boolean assigned = cinema.assignScreening("Film1", "Room1", "2024-12-01 14:00:00", "2024-11-30 10:00:00");
        assertTrue("Screening should be assigned successfully", assigned);
        assertEquals("There should be 1 screening before cancellation attempt", 1, cinema.getScreenings().size());
        
        // Action: cancel screening after it has already occurred (current time after screening time)
        boolean result = cinema.cancelScreening("Film1", "Room1", "2024-12-01 14:00:00", "2024-12-03 10:00:00");
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Cancellation should fail for past screening", result);
        assertEquals("Number of screenings should remain 1", 1, cinema.getScreenings().size());
    }
}