import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CR6Test {
    private Cinema cinema;
    private Film film1;
    private Room room1;
    private Screening screening1;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film("Film1");
        room1 = new Room();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_cancelFutureScreening() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-10-03 12:00:00", "2024-10-02 12:00:00");
        
        // Get the created screening from the room
        screening1 = room1.getScreenings().get(0);
        
        // Action: cancel Screening1 at "2024-10-02 12:00:00"
        boolean result = cinema.cancelScreening(screening1, "2024-10-02 12:00:00");
        
        // Verify result and state
        assertTrue("Screening should be canceled successfully", result);
        assertEquals("There should be no screenings in the cinema", 0, room1.getScreenings().size());
    }
    
    @Test
    public void testCase2_cancelNonExistentScreening() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Create a screening that is not assigned to the cinema
        Screening screening2 = new Screening(film1, room1, LocalDateTime.parse("2024-10-02 12:05:00", formatter));
        
        // Action: cancel Screening2 at current time "2024-10-03 12:05:00"
        boolean result = cinema.cancelScreening(screening2, "2024-10-03 12:05:00");
        
        // Verify result
        assertFalse("Non-existent screening should not be canceled", result);
    }
    
    @Test
    public void testCase3_cancelAtExactScreeningTimeBoundary() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-12-03 10:00:00", "2024-12-02 10:00:00");
        
        // Get the created screening from the room
        screening1 = room1.getScreenings().get(0);
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        boolean result = cinema.cancelScreening(screening1, "2024-12-03 10:00:00");
        
        // Verify result and state
        assertFalse("Screening at exact time boundary should not be canceled", result);
        assertEquals("There should be 1 screening in the cinema", 1, room1.getScreenings().size());
    }
    
    @Test
    public void testCase4_cancelAlreadyCancelledScreening() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-10-03 12:00:00", "2024-10-02 12:00:00");
        
        // Get the created screening from the room
        screening1 = room1.getScreenings().get(0);
        
        // First cancellation (should succeed)
        boolean firstResult = cinema.cancelScreening(screening1, "2024-10-02 12:00:00");
        assertTrue("First cancellation should succeed", firstResult);
        
        // Action: cancel Screening1 at current time "2024-10-02 12:05:00"
        boolean secondResult = cinema.cancelScreening(screening1, "2024-10-02 12:05:00");
        
        // Verify result and state
        assertFalse("Already cancelled screening should not be canceled again", secondResult);
        assertEquals("There should be no screening in the cinema", 0, room1.getScreenings().size());
    }
    
    @Test
    public void testCase5_cancelPastScreeningAfterShowtime() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-12-01 14:00:00", "2024-11-30 10:00:00");
        
        // Get the created screening from the room
        screening1 = room1.getScreenings().get(0);
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        boolean result = cinema.cancelScreening(screening1, "2024-12-03 10:00:00");
        
        // Verify result and state
        assertFalse("Past screening should not be canceled", result);
        assertEquals("There should be 1 screening in the cinema", 1, room1.getScreenings().size());
    }
}