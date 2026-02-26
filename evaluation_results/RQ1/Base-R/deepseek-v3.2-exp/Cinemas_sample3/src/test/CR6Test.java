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
    
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film("Film Title", 120, "Action");
        room1 = new Room("Room 1", 100);
    }
    
    @Test
    public void testCase1_CancelFutureScreening() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-10-03 12:00:00", "2024-10-01 12:00:00");
        
        // Action: cancel Screening1 at "2024-10-02 12:00:00"
        // Find the created screening
        Screening screeningToCancel = null;
        for (Screening s : cinema.getScreenings()) {
            if (s.getFilm().equals(film1) && s.getRoom().equals(room1) && 
                s.getScreeningTime().equals(LocalDateTime.parse("2024-10-03 12:00:00", DATE_TIME_FORMATTER))) {
                screeningToCancel = s;
                break;
            }
        }
        
        boolean result = cinema.cancelScreening(screeningToCancel, "2024-10-02 12:00:00");
        
        // Expected Output: true, there is no screening in the cinema C1
        assertTrue("Screening should be canceled successfully", result);
        assertEquals("Cinema should have no screenings after cancellation", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_CancelNonExistentScreening() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        // Create a screening but don't add it to cinema (non-existent screening)
        Screening screening2 = new Screening(film1, room1, LocalDateTime.parse("2024-10-02 12:05:00", DATE_TIME_FORMATTER));
        
        // Action: cancel Screening2 at current time "2024-10-03 12:05:00"
        boolean result = cinema.cancelScreening(screening2, "2024-10-03 12:05:00");
        
        // Expected Output: false
        assertFalse("Non-existent screening should not be canceled", result);
    }
    
    @Test
    public void testCase3_CancelAtExactScreeningTimeBoundary() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-12-03 10:00:00", "2024-12-01 10:00:00");
        
        // Find the created screening
        Screening screeningToCancel = null;
        for (Screening s : cinema.getScreenings()) {
            if (s.getFilm().equals(film1) && s.getRoom().equals(room1) && 
                s.getScreeningTime().equals(LocalDateTime.parse("2024-12-03 10:00:00", DATE_TIME_FORMATTER))) {
                screeningToCancel = s;
                break;
            }
        }
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        boolean result = cinema.cancelScreening(screeningToCancel, "2024-12-03 10:00:00");
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Screening at exact time boundary should not be canceled", result);
        assertEquals("Cinema should still have 1 screening", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_CancelAlreadyCancelledScreening() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-10-03 12:00:00", "2024-10-01 12:00:00");
        
        // Find the created screening
        Screening screeningToCancel = null;
        for (Screening s : cinema.getScreenings()) {
            if (s.getFilm().equals(film1) && s.getRoom().equals(room1) && 
                s.getScreeningTime().equals(LocalDateTime.parse("2024-10-03 12:00:00", DATE_TIME_FORMATTER))) {
                screeningToCancel = s;
                break;
            }
        }
        
        // First cancellation (should succeed)
        boolean firstResult = cinema.cancelScreening(screeningToCancel, "2024-10-02 12:00:00");
        assertTrue("First cancellation should succeed", firstResult);
        
        // Action: cancel Screening1 at current time "2024-10-02 12:05:00"
        boolean secondResult = cinema.cancelScreening(screeningToCancel, "2024-10-02 12:05:00");
        
        // Expected Output: false, there is no screening in the cinema
        assertFalse("Already cancelled screening should not be canceled again", secondResult);
        assertEquals("Cinema should have no screenings", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_CancelPastScreeningAfterShowtime() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-12-01 14:00:00", "2024-11-01 10:00:00");
        
        // Find the created screening
        Screening screeningToCancel = null;
        for (Screening s : cinema.getScreenings()) {
            if (s.getFilm().equals(film1) && s.getRoom().equals(room1) && 
                s.getScreeningTime().equals(LocalDateTime.parse("2024-12-01 14:00:00", DATE_TIME_FORMATTER))) {
                screeningToCancel = s;
                break;
            }
        }
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        boolean result = cinema.cancelScreening(screeningToCancel, "2024-12-03 10:00:00");
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Past screening should not be canceled", result);
        assertEquals("Cinema should still have 1 screening", 1, cinema.getScreenings().size());
    }
}