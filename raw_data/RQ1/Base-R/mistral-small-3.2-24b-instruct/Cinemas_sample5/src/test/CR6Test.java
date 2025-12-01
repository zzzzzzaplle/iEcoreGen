import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;

public class CR6Test {
    private Cinema cinema;
    private Film film1;
    private Room room1;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film();
        film1.setFilmId("F1");
        room1 = new Room();
        room1.setRoomId("R1");
    }
    
    @Test
    public void testCase1_cancelFutureScreening() throws ParseException {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-10-03 12:00:00", "2024-10-01 12:00:00");
        
        // Action: cancel Screening1 at "2024-10-02 12:00:00"
        boolean result = cinema.cancelScreening(film1, "2024-10-03 12:00:00", "2024-10-02 12:00:00");
        
        // Expected Output: true, there is no screening in the cinema C1
        assertTrue("Screening should be canceled successfully", result);
        assertTrue("There should be no screenings for film1", cinema.getScreenings().get(film1).isEmpty());
    }
    
    @Test
    public void testCase2_cancelNonExistentScreening() throws ParseException {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        // Note: Screening2 is not assigned
        
        // Action: cancel Screening2 at current time "2024-10-03 12:05:00"
        boolean result = cinema.cancelScreening(film1, "2024-10-02 12:05:00", "2024-10-03 12:05:00");
        
        // Expected Output: false
        assertFalse("Non-existent screening should not be canceled", result);
    }
    
    @Test
    public void testCase3_cancelAtExactScreeningTimeBoundary() throws ParseException {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-12-03 10:00:00", "2024-12-01 10:00:00");
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        boolean result = cinema.cancelScreening(film1, "2024-12-03 10:00:00", "2024-12-03 10:00:00");
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Screening at exact time boundary should not be canceled", result);
        assertEquals("There should be 1 screening remaining", 1, cinema.getScreenings().get(film1).size());
    }
    
    @Test
    public void testCase4_cancelAlreadyCancelledScreening() throws ParseException {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-10-03 12:00:00", "2024-10-01 12:00:00");
        cinema.cancelScreening(film1, "2024-10-03 12:00:00", "2024-10-02 12:00:00"); // First cancellation
        
        // Action: cancel Screening1 at current time "2024-10-02 12:05:00"
        boolean result = cinema.cancelScreening(film1, "2024-10-03 12:00:00", "2024-10-02 12:05:00");
        
        // Expected Output: false, there is no screening in the cinema
        assertFalse("Already canceled screening should not be canceled again", result);
        assertTrue("There should be no screenings remaining", cinema.getScreenings().get(film1).isEmpty());
    }
    
    @Test
    public void testCase5_cancelPastScreeningAfterShowtime() throws ParseException {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-12-01 14:00:00", "2024-11-01 10:00:00");
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        boolean result = cinema.cancelScreening(film1, "2024-12-01 14:00:00", "2024-12-03 10:00:00");
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Past screening should not be canceled", result);
        assertEquals("There should be 1 screening remaining", 1, cinema.getScreenings().get(film1).size());
    }
}