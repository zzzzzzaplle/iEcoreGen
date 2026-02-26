import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CR6Test {
    private Cinema cinema;
    private Film film1;
    private Room room1;
    private LocalDateTime screeningTime1;
    private LocalDateTime screeningTime2;
    private LocalDateTime currentTime1;
    private LocalDateTime currentTime2;
    private LocalDateTime currentTime3;
    private LocalDateTime currentTime4;
    private LocalDateTime currentTime5;
    private DateTimeFormatter formatter;

    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film("Film1");
        room1 = new Room("Room1");
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        // Initialize date times for test cases
        screeningTime1 = LocalDateTime.parse("2024-10-03 12:00:00", formatter);
        screeningTime2 = LocalDateTime.parse("2024-10-02 12:05:00", formatter);
        currentTime1 = LocalDateTime.parse("2024-10-02 12:00:00", formatter);
        currentTime2 = LocalDateTime.parse("2024-10-03 12:05:00", formatter);
        currentTime3 = LocalDateTime.parse("2024-12-03 10:00:00", formatter);
        currentTime4 = LocalDateTime.parse("2024-10-02 12:05:00", formatter);
        currentTime5 = LocalDateTime.parse("2024-12-03 10:00:00", formatter);
    }

    @Test
    public void testCase1_CancelFutureScreening() {
        // Setup: Create Cinema C1, Add Film1, Add Room1, Assign Screening1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, screeningTime1, currentTime1);
        
        // Action: cancel Screening1 at "2024-10-02 12:00:00"
        boolean result = cinema.cancelScreening(film1, screeningTime1, currentTime1);
        
        // Verify cancellation was successful
        assertTrue("Screening should be canceled successfully", result);
        // Verify there are no screenings left in the cinema
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().get(film1).size());
    }

    @Test
    public void testCase2_CancelNonExistentScreening() {
        // Setup: Create Cinema C1, Add Film1, Add Room1 (but no screening assigned)
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: cancel non-existent Screening2 at current time "2024-10-03 12:05:00"
        boolean result = cinema.cancelScreening(film1, screeningTime2, currentTime2);
        
        // Verify cancellation failed
        assertFalse("Cancellation should fail for non-existent screening", result);
    }

    @Test
    public void testCase3_CancelAtExactScreeningTimeBoundary() {
        // Setup: Create Cinema C1, Add Film F1, Add Room R1, Assign screening S1
        Film f1 = new Film("F1");
        Room r1 = new Room("R1");
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-03 10:00:00", formatter);
        
        cinema.addFilm(f1);
        cinema.addRoom(r1);
        cinema.assignScreening(f1, r1, screeningTime, currentTime3.minusDays(1));
        
        // Action: Cancel S1 at current time exactly equal to screening time
        boolean result = cinema.cancelScreening(f1, screeningTime, currentTime3);
        
        // Verify cancellation failed (screening time is not after current time)
        assertFalse("Cancellation should fail when current time equals screening time", result);
        // Verify screening still exists in cinema
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().get(f1).size());
    }

    @Test
    public void testCase4_CancelAlreadyCancelledScreening() {
        // Setup: Create Cinema C1, Add Film1, Add Room1, Assign and then cancel Screening1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, screeningTime1, currentTime1);
        cinema.cancelScreening(film1, screeningTime1, currentTime1);
        
        // Action: try to cancel already cancelled Screening1 again
        boolean result = cinema.cancelScreening(film1, screeningTime1, currentTime4);
        
        // Verify cancellation failed
        assertFalse("Cancellation should fail for already cancelled screening", result);
        // Verify there are no screenings in cinema
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().get(film1).size());
    }

    @Test
    public void testCase5_CancelPastScreeningAfterShowtime() {
        // Setup: Create Cinema C1, Add Film F1, Add Room R1, Assign screening S1
        Film f1 = new Film("F1");
        Room r1 = new Room("R1");
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-01 14:00:00", formatter);
        
        cinema.addFilm(f1);
        cinema.addRoom(r1);
        cinema.assignScreening(f1, r1, screeningTime, currentTime5.minusDays(10));
        
        // Action: Cancel S1 at current time after screening time
        boolean result = cinema.cancelScreening(f1, screeningTime, currentTime5);
        
        // Verify cancellation failed (screening time is in the past)
        assertFalse("Cancellation should fail for past screening", result);
        // Verify screening still exists in cinema
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().get(f1).size());
    }
}