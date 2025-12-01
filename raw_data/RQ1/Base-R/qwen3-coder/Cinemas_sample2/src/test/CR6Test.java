import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR6Test {
    private Cinema cinema;
    private Film film1;
    private Room room1;
    private Screening screening1;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film("Film1", 120);
        room1 = new Room("Room1");
    }
    
    @Test
    public void testCase1_CancelFutureScreening() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-10-03 12:00:00", "2024-10-01 12:00:00");
        
        // Find the created screening
        Screening screeningToCancel = null;
        for (Screening s : cinema.getScreenings()) {
            if (s.getFilm().equals(film1) && s.getRoom().equals(room1)) {
                screeningToCancel = s;
                break;
            }
        }
        
        // Action: cancel Screening1 at "2024-10-02 12:00:00"
        boolean result = cinema.cancelScreening(screeningToCancel, "2024-10-02 12:00:00");
        
        // Expected Output: true, there is no screening in the cinema C1
        assertTrue("Screening should be canceled successfully", result);
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_CancelNonExistentScreening() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Create a screening that is not added to cinema
        Screening screening2 = new Screening(film1, room1, 
            LocalDateTime.parse("2024-10-02 12:05:00", 
                java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        
        // Action: cancel Screening2 at current time "2024-10-03 12:05:00"
        boolean result = cinema.cancelScreening(screening2, "2024-10-03 12:05:00");
        
        // Expected Output: false
        assertFalse("Should return false for non-existent screening", result);
    }
    
    @Test
    public void testCase3_CancelAtExactScreeningTimeBoundary() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-12-03 10:00:00", "2024-12-01 10:00:00");
        
        // Find the created screening
        Screening screeningS1 = null;
        for (Screening s : cinema.getScreenings()) {
            if (s.getFilm().equals(film1) && s.getRoom().equals(room1)) {
                screeningS1 = s;
                break;
            }
        }
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        boolean result = cinema.cancelScreening(screeningS1, "2024-12-03 10:00:00");
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Should return false when canceling at exact screening time", result);
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_CancelAlreadyCancelledScreening() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-10-03 12:00:00", "2024-10-01 12:00:00");
        
        // Find the created screening
        Screening screeningS1 = null;
        for (Screening s : cinema.getScreenings()) {
            if (s.getFilm().equals(film1) && s.getRoom().equals(room1)) {
                screeningS1 = s;
                break;
            }
        }
        
        // First cancellation (should succeed)
        boolean firstResult = cinema.cancelScreening(screeningS1, "2024-10-02 12:00:00");
        assertTrue("First cancellation should succeed", firstResult);
        
        // Action: cancel Screening1 at current time "2024-10-02 12:05:00"
        boolean result = cinema.cancelScreening(screeningS1, "2024-10-02 12:05:00");
        
        // Expected Output: false, there is no screening in the cinema
        assertFalse("Should return false for already cancelled screening", result);
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_CancelPastScreeningAfterShowtime() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-12-01 14:00:00", "2024-11-30 12:00:00");
        
        // Find the created screening
        Screening screeningS1 = null;
        for (Screening s : cinema.getScreenings()) {
            if (s.getFilm().equals(film1) && s.getRoom().equals(room1)) {
                screeningS1 = s;
                break;
            }
        }
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        boolean result = cinema.cancelScreening(screeningS1, "2024-12-03 10:00:00");
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Should return false for past screening", result);
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().size());
    }
}