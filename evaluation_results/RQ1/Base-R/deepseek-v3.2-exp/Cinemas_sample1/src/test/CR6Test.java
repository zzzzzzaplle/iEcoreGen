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
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film("Inception", 148, "Sci-Fi");
        room1 = new Room("Room A", 100);
    }
    
    @Test
    public void testCase1_cancelFutureScreening() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-10-03 12:00:00", "2024-10-01 12:00:00");
        
        // Action: cancel Screening1 at "2024-10-02 12:00:00"
        Screening screeningToCancel = new Screening(film1, room1, 
            LocalDateTime.parse("2024-10-03 12:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        boolean result = cinema.cancelScreening(screeningToCancel, "2024-10-02 12:00:00");
        
        // Verify
        assertTrue("Screening should be canceled successfully", result);
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_cancelNonExistentScreening() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        // Note: Screening2 is not added to cinema, only created as object
        
        // Action: cancel Screening2 at current time "2024-10-03 12:05:00"
        Screening screening2 = new Screening(film1, room1, 
            LocalDateTime.parse("2024-10-02 12:05:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        boolean result = cinema.cancelScreening(screening2, "2024-10-03 12:05:00");
        
        // Verify
        assertFalse("Should return false for non-existent screening", result);
    }
    
    @Test
    public void testCase3_cancelAtExactScreeningTimeBoundary() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-12-03 10:00:00", "2024-12-01 10:00:00");
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        Screening screeningToCancel = new Screening(film1, room1, 
            LocalDateTime.parse("2024-12-03 10:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        boolean result = cinema.cancelScreening(screeningToCancel, "2024-12-03 10:00:00");
        
        // Verify
        assertFalse("Should return false when current time equals screening time", result);
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_cancelAlreadyCancelledScreening() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-10-03 12:00:00", "2024-10-01 12:00:00");
        
        // First cancellation (should succeed)
        Screening screeningToCancel = new Screening(film1, room1, 
            LocalDateTime.parse("2024-10-03 12:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        boolean firstResult = cinema.cancelScreening(screeningToCancel, "2024-10-02 12:00:00");
        assertTrue("First cancellation should succeed", firstResult);
        
        // Action: cancel Screening1 again at current time "2024-10-02 12:05:00"
        boolean secondResult = cinema.cancelScreening(screeningToCancel, "2024-10-02 12:05:00");
        
        // Verify
        assertFalse("Should return false for already cancelled screening", secondResult);
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_cancelPastScreening() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-12-01 14:00:00", "2024-11-30 10:00:00");
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        Screening screeningToCancel = new Screening(film1, room1, 
            LocalDateTime.parse("2024-12-01 14:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        boolean result = cinema.cancelScreening(screeningToCancel, "2024-12-03 10:00:00");
        
        // Verify
        assertFalse("Should return false for past screening", result);
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().size());
    }
}