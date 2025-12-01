import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR6Test {
    private Cinema cinema;
    private Film film1;
    private Room room1;
    private Screening screening1;
    private Screening screening2;
    private SimpleDateFormat dateFormat;

    @Before
    public void setUp() throws Exception {
        cinema = new Cinema();
        film1 = new Film();
        room1 = new Room();
        screening1 = new Screening();
        screening2 = new Screening();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Configure screening1 with film1, room1 and a future time
        screening1.setFilm(film1);
        screening1.setRoom(room1);
        screening1.setTime(dateFormat.parse("2024-10-03 12:00:00"));
        
        // Configure screening2 with a different time
        screening2.setFilm(film1);
        screening2.setRoom(room1);
        screening2.setTime(dateFormat.parse("2024-10-02 12:05:00"));
    }

    @Test
    public void testCase1_cancelFutureScreening() throws Exception {
        // Setup: Create Cinema C1, Add Film1, Add Room1, Assign Screening1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, screening1, dateFormat.parse("2024-10-01 12:00:00"));
        
        // Action: cancel Screening1 at "2024-10-02 12:00:00"
        Date currentTime = dateFormat.parse("2024-10-02 12:00:00");
        boolean result = cinema.cancelScreening(screening1, currentTime);
        
        // Expected Output: true, there is no screening in the cinema C1
        assertTrue("Screening should be canceled successfully", result);
        assertEquals("Cinema should have no screenings after cancellation", 0, cinema.getScreenings().size());
    }

    @Test
    public void testCase2_cancelNonExistentScreening() throws Exception {
        // Setup: Create Cinema C1, Screening2 (the screening time: "2024-10-02 12:05:00")
        // Add Film1, Add Room1 (but don't assign screening2 to cinema)
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: cancel Screening2 at current time "2024-10-03 12:05:00"
        Date currentTime = dateFormat.parse("2024-10-03 12:05:00");
        boolean result = cinema.cancelScreening(screening2, currentTime);
        
        // Expected Output: false
        assertFalse("Should return false when canceling non-existent screening", result);
    }

    @Test
    public void testCase3_cancelAtExactScreeningTimeBoundary() throws Exception {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        // Assign screening S1 for F1 in R1 at "2024-12-03 10:00:00"
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        screening1.setTime(dateFormat.parse("2024-12-03 10:00:00"));
        cinema.assignScreening(film1, room1, screening1, dateFormat.parse("2024-12-01 10:00:00"));
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-03 10:00:00");
        boolean result = cinema.cancelScreening(screening1, currentTime);
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Should return false when canceling at exact screening time", result);
        assertEquals("Cinema should still have 1 screening", 1, cinema.getScreenings().size());
    }

    @Test
    public void testCase4_cancelAlreadyCancelledScreening() throws Exception {
        // Setup: Create Cinema C1, Add Film1, Add Room1
        // Assign Screening1(Film1, Room1, "2024-10-03 12:00:00") then cancel Screening1 at "2024-10-02 12:00:00" (true)
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, screening1, dateFormat.parse("2024-10-01 12:00:00"));
        
        // First cancellation
        Date firstCancelTime = dateFormat.parse("2024-10-02 12:00:00");
        boolean firstResult = cinema.cancelScreening(screening1, firstCancelTime);
        assertTrue("First cancellation should succeed", firstResult);
        
        // Action: cancel Screening1 at current time "2024-10-02 12:05:00"
        Date currentTime = dateFormat.parse("2024-10-02 12:05:00");
        boolean result = cinema.cancelScreening(screening1, currentTime);
        
        // Expected Output: false, there is no screening in the cinema
        assertFalse("Should return false when canceling already canceled screening", result);
        assertEquals("Cinema should have no screenings", 0, cinema.getScreenings().size());
    }

    @Test
    public void testCase5_cancelPastScreeningAfterShowtime() throws Exception {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        // Assign screening S1 for F1 in R1 at "2024-12-01 14:00:00"
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        screening1.setTime(dateFormat.parse("2024-12-01 14:00:00"));
        cinema.assignScreening(film1, room1, screening1, dateFormat.parse("2024-11-01 10:00:00"));
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-03 10:00:00");
        boolean result = cinema.cancelScreening(screening1, currentTime);
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Should return false when canceling past screening", result);
        assertEquals("Cinema should still have 1 screening", 1, cinema.getScreenings().size());
    }
}