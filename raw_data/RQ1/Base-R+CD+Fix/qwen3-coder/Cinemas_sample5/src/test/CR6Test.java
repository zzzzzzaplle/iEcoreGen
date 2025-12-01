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
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film();
        room1 = new Room();
        screening1 = new Screening();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CancelFutureScreening() throws Exception {
        // Setup: Create Cinema C1, Add Film1, Add Room1, Assign Screening1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-10-03 12:00:00");
        screening1.setTime(screeningTime);
        screening1.setFilm(film1);
        screening1.setRoom(room1);
        
        // Assign the screening with a current time before screening time
        Date currentTimeForAssignment = dateFormat.parse("2024-10-01 12:00:00");
        boolean assignmentResult = cinema.assignScreening(film1, room1, screening1, currentTimeForAssignment);
        assertTrue("Screening should be assigned successfully", assignmentResult);
        assertEquals("Cinema should have 1 screening", 1, cinema.getScreenings().size());
        
        // Action: cancel Screening1 at "2024-10-02 12:00:00"
        Date cancelTime = dateFormat.parse("2024-10-02 12:00:00");
        boolean result = cinema.cancelScreening(screening1, cancelTime);
        
        // Expected Output: true, there is no screening in the cinema C1
        assertTrue("Screening should be canceled successfully", result);
        assertEquals("Cinema should have 0 screenings after cancellation", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_CancelNonExistentScreening() throws Exception {
        // Setup: Create Cinema C1, Screening2, Add Film1, Add Room1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Screening screening2 = new Screening();
        Date screeningTime = dateFormat.parse("2024-10-02 12:05:00");
        screening2.setTime(screeningTime);
        
        // Note: screening2 is NOT assigned to the cinema
        
        // Action: cancel Screening2 at current time "2024-10-03 12:05:00"
        Date currentTime = dateFormat.parse("2024-10-03 12:05:00");
        boolean result = cinema.cancelScreening(screening2, currentTime);
        
        // Expected Output: false
        assertFalse("Canceling non-existent screening should return false", result);
    }
    
    @Test
    public void testCase3_CancelAtExactScreeningTimeBoundary() throws Exception {
        // Setup: Create Cinema C1, Add Film F1, Add Room R1, Assign screening S1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-12-03 10:00:00");
        screening1.setTime(screeningTime);
        screening1.setFilm(film1);
        screening1.setRoom(room1);
        
        Date assignmentTime = dateFormat.parse("2024-12-02 10:00:00");
        boolean assignmentResult = cinema.assignScreening(film1, room1, screening1, assignmentTime);
        assertTrue("Screening should be assigned successfully", assignmentResult);
        assertEquals("Cinema should have 1 screening", 1, cinema.getScreenings().size());
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-03 10:00:00");
        boolean result = cinema.cancelScreening(screening1, currentTime);
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Canceling at exact screening time should return false", result);
        assertEquals("Cinema should still have 1 screening", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_CancelAlreadyCancelledScreening() throws Exception {
        // Setup: Create Cinema C1, Add Film1, Add Room1, Assign Screening1 then cancel it
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-10-03 12:00:00");
        screening1.setTime(screeningTime);
        screening1.setFilm(film1);
        screening1.setRoom(room1);
        
        Date assignmentTime = dateFormat.parse("2024-10-01 12:00:00");
        boolean assignmentResult = cinema.assignScreening(film1, room1, screening1, assignmentTime);
        assertTrue("Screening should be assigned successfully", assignmentResult);
        assertEquals("Cinema should have 1 screening", 1, cinema.getScreenings().size());
        
        // First cancellation (should succeed)
        Date firstCancelTime = dateFormat.parse("2024-10-02 12:00:00");
        boolean firstResult = cinema.cancelScreening(screening1, firstCancelTime);
        assertTrue("First cancellation should succeed", firstResult);
        assertEquals("Cinema should have 0 screenings after first cancellation", 0, cinema.getScreenings().size());
        
        // Action: cancel Screening1 at current time "2024-10-02 12:05:00"
        Date secondCancelTime = dateFormat.parse("2024-10-02 12:05:00");
        boolean result = cinema.cancelScreening(screening1, secondCancelTime);
        
        // Expected Output: false, there is no screening in the cinema
        assertFalse("Canceling already cancelled screening should return false", result);
        assertEquals("Cinema should still have 0 screenings", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_CancelPastScreeningAfterShowtime() throws Exception {
        // Setup: Create Cinema C1, Add Film F1, Add Room R1, Assign screening S1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-12-01 14:00:00");
        screening1.setTime(screeningTime);
        screening1.setFilm(film1);
        screening1.setRoom(room1);
        
        Date assignmentTime = dateFormat.parse("2024-11-30 10:00:00");
        boolean assignmentResult = cinema.assignScreening(film1, room1, screening1, assignmentTime);
        assertTrue("Screening should be assigned successfully", assignmentResult);
        assertEquals("Cinema should have 1 screening", 1, cinema.getScreenings().size());
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-03 10:00:00");
        boolean result = cinema.cancelScreening(screening1, currentTime);
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Canceling past screening should return false", result);
        assertEquals("Cinema should still have 1 screening", 1, cinema.getScreenings().size());
    }
}