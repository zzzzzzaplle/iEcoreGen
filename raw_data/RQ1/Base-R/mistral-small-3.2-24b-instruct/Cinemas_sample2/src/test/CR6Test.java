import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class CR6Test {
    private Cinema cinema;
    private Film film1;
    private Room room1;
    private LocalDateTime screeningTime1;
    private LocalDateTime screeningTime2;
    private LocalDateTime screeningTime3;
    private LocalDateTime currentTime1;
    private LocalDateTime currentTime2;
    private LocalDateTime currentTime3;
    private LocalDateTime currentTime4;
    private DateTimeFormatter formatter;

    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film("Film1", "Action", 120);
        room1 = new Room("Room1", 100);
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        // Parse all datetime strings once for reuse
        screeningTime1 = LocalDateTime.parse("2024-10-03 12:00:00", formatter);
        screeningTime2 = LocalDateTime.parse("2024-10-02 12:05:00", formatter);
        screeningTime3 = LocalDateTime.parse("2024-12-01 14:00:00", formatter);
        currentTime1 = LocalDateTime.parse("2024-10-02 12:00:00", formatter);
        currentTime2 = LocalDateTime.parse("2024-10-03 12:05:00", formatter);
        currentTime3 = LocalDateTime.parse("2024-12-03 10:00:00", formatter);
        currentTime4 = LocalDateTime.parse("2024-10-02 12:05:00", formatter);
    }

    @Test
    public void testCase1_CancelFutureScreening() {
        // Setup: Create Cinema C1, Add Film1, Add Room1, Assign Screening1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, screeningTime1, currentTime1);
        
        // Action: cancel Screening1 at "2024-10-02 12:00:00"
        boolean result = cinema.cancelScreening(screeningTime1, currentTime1);
        
        // Verify cancellation was successful
        assertTrue("Screening should be canceled successfully", result);
        
        // Verify there is no screening in the cinema
        Map<LocalDateTime, Screening> screenings = cinema.getScreenings();
        assertEquals("There should be no screenings after cancellation", 0, screenings.size());
    }

    @Test
    public void testCase2_CancelNonExistentScreening() {
        // Setup: Create Cinema C1, Add Film1, Add Room1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: cancel Screening2 (non-existent) at current time "2024-10-03 12:05:00"
        boolean result = cinema.cancelScreening(screeningTime2, currentTime2);
        
        // Verify cancellation failed
        assertFalse("Cancellation should fail for non-existent screening", result);
    }

    @Test
    public void testCase3_CancelAtExactScreeningTimeBoundary() {
        // Setup: Create Cinema C1, Add Film F1, Add Room R1, Assign screening S1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        LocalDateTime exactScreeningTime = LocalDateTime.parse("2024-12-03 10:00:00", formatter);
        cinema.assignScreening(film1, room1, exactScreeningTime, currentTime3);
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00" (exact same time)
        boolean result = cinema.cancelScreening(exactScreeningTime, exactScreeningTime);
        
        // Verify cancellation failed
        assertFalse("Cancellation should fail when current time equals screening time", result);
        
        // Verify there is still 1 screening in the cinema
        Map<LocalDateTime, Screening> screenings = cinema.getScreenings();
        assertEquals("There should be 1 screening remaining", 1, screenings.size());
    }

    @Test
    public void testCase4_CancelAlreadyCancelledScreening() {
        // Setup: Create Cinema C1, Add Film1, Add Room1, Assign Screening1 then cancel it
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, screeningTime1, currentTime1);
        cinema.cancelScreening(screeningTime1, currentTime1); // First cancellation (true)
        
        // Action: cancel Screening1 again at current time "2024-10-02 12:05:00"
        boolean result = cinema.cancelScreening(screeningTime1, currentTime4);
        
        // Verify second cancellation failed
        assertFalse("Cancellation should fail for already canceled screening", result);
        
        // Verify there is no screening in the cinema
        Map<LocalDateTime, Screening> screenings = cinema.getScreenings();
        assertEquals("There should be no screenings", 0, screenings.size());
    }

    @Test
    public void testCase5_CancelPastScreeningAfterShowtime() {
        // Setup: Create Cinema C1, Add Film F1, Add Room R1, Assign screening S1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, screeningTime3, currentTime3);
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00" (after showtime)
        boolean result = cinema.cancelScreening(screeningTime3, currentTime3);
        
        // Verify cancellation failed
        assertFalse("Cancellation should fail for past screening", result);
        
        // Verify there is still 1 screening in the cinema
        Map<LocalDateTime, Screening> screenings = cinema.getScreenings();
        assertEquals("There should be 1 screening remaining", 1, screenings.size());
    }
}