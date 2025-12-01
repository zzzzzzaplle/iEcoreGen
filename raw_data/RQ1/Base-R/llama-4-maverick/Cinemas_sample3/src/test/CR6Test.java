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
        film1 = new Film();
        film1.setTitle("Film1");
        room1 = new Room();
        room1.setName("Room1");
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    public void testCase1_cancelFutureScreening() {
        // Setup: Create Cinema C1, Add Film1, Add Room1, Assign Screening1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-10-03 12:00:00", "2024-10-02 11:00:00");
        
        // Create screening object for cancellation
        LocalDateTime screeningTime = LocalDateTime.parse("2024-10-03 12:00:00", formatter);
        Screening screeningToCancel = new Screening(film1, screeningTime);
        
        // Action: cancel Screening1 at "2024-10-02 12:00:00"
        boolean result = cinema.cancelScreening(screeningToCancel, "2024-10-02 12:00:00");
        
        // Expected Output: true, there is no screening in the cinema C1
        assertTrue("Screening should be canceled successfully", result);
        assertEquals("Room should have no screenings after cancellation", 0, room1.getScreenings().size());
    }

    @Test
    public void testCase2_cancelNonExistentScreening() {
        // Setup: Create Cinema C1, Add Film1, Add Room1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Create a screening that was never assigned
        LocalDateTime screeningTime = LocalDateTime.parse("2024-10-02 12:05:00", formatter);
        Screening nonExistentScreening = new Screening(film1, screeningTime);
        
        // Action: cancel Screening2 at current time "2024-10-03 12:05:00"
        boolean result = cinema.cancelScreening(nonExistentScreening, "2024-10-03 12:05:00");
        
        // Expected Output: false
        assertFalse("Non-existent screening should not be canceled", result);
    }

    @Test
    public void testCase3_cancelAtExactScreeningTimeBoundary() {
        // Setup: Create Cinema C1, Add Film F1, Add Room R1, Assign screening S1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-12-03 10:00:00", "2024-12-02 10:00:00");
        
        // Create screening object for cancellation
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-03 10:00:00", formatter);
        Screening screeningToCancel = new Screening(film1, screeningTime);
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        boolean result = cinema.cancelScreening(screeningToCancel, "2024-12-03 10:00:00");
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Screening at exact time boundary should not be canceled", result);
        assertEquals("Room should still have 1 screening", 1, room1.getScreenings().size());
    }

    @Test
    public void testCase4_cancelAlreadyCancelledScreening() {
        // Setup: Create Cinema C1, Add Film1, Add Room1, Assign Screening1 then cancel it
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-10-03 12:00:00", "2024-10-02 11:00:00");
        
        // Create screening object for first cancellation
        LocalDateTime screeningTime = LocalDateTime.parse("2024-10-03 12:00:00", formatter);
        Screening screeningToCancel = new Screening(film1, screeningTime);
        
        // First cancellation (should succeed)
        boolean firstCancelResult = cinema.cancelScreening(screeningToCancel, "2024-10-02 12:00:00");
        assertTrue("First cancellation should succeed", firstCancelResult);
        
        // Action: cancel Screening1 again at current time "2024-10-02 12:05:00"
        boolean secondCancelResult = cinema.cancelScreening(screeningToCancel, "2024-10-02 12:05:00");
        
        // Expected Output: false, there is no screening in the cinema
        assertFalse("Already canceled screening should not be canceled again", secondCancelResult);
        assertEquals("Room should have no screenings", 0, room1.getScreenings().size());
    }

    @Test
    public void testCase5_cancelPastScreeningAfterShowtime() {
        // Setup: Create Cinema C1, Add Film F1, Add Room R1, Assign screening S1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-12-01 14:00:00", "2024-11-30 10:00:00");
        
        // Create screening object for cancellation
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-01 14:00:00", formatter);
        Screening screeningToCancel = new Screening(film1, screeningTime);
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        boolean result = cinema.cancelScreening(screeningToCancel, "2024-12-03 10:00:00");
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Past screening should not be canceled", result);
        assertEquals("Room should still have 1 screening", 1, room1.getScreenings().size());
    }
}