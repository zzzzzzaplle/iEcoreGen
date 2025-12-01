import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR6Test {
    private Cinema cinema;
    private Film film1;
    private Room room1;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() throws Exception {
        cinema = new Cinema();
        film1 = new Film();
        room1 = new Room();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Common setup steps for most test cases
        cinema.addFilm(film1);
        cinema.addRoom(room1);
    }
    
    @Test
    public void testCase1_CancelFutureScreening() throws Exception {
        // Setup: Assign Screening1(Film1, Room1, "2024-10-03 12:00:00")
        Date screeningTime = dateFormat.parse("2024-10-03 12:00:00");
        Date currentTimeForAssignment = dateFormat.parse("2024-10-01 12:00:00");
        
        boolean assigned = cinema.assignScreening(film1, room1, screeningTime, currentTimeForAssignment);
        assertTrue("Screening should be assigned successfully", assigned);
        assertEquals("Cinema should have 1 screening before cancellation", 1, cinema.getScreenings().size());
        
        // Action: cancel Screening1 at "2024-10-02 12:00:00"
        Screening screening1 = cinema.getScreenings().get(0);
        Date cancelTime = dateFormat.parse("2024-10-02 12:00:00");
        
        boolean result = cinema.cancelScreening(screening1, cancelTime);
        
        // Expected Output: true, there is no screening in the cinema C1
        assertTrue("Cancellation should be successful", result);
        assertEquals("Cinema should have 0 screenings after cancellation", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_CancelNonExistentScreening() throws Exception {
        // Setup: Create Cinema C1, Screening2 (the screening time: "2024-10-02 12:05:00")
        // Note: Screening2 is created but NOT added to cinema screenings list
        Screening screening2 = new Screening();
        screening2.setFilm(film1);
        screening2.setRoom(room1);
        screening2.setTime(dateFormat.parse("2024-10-02 12:05:00"));
        
        // Action: cancel Screening2 at current time "2024-10-03 12:05:00"
        Date currentTime = dateFormat.parse("2024-10-03 12:05:00");
        boolean result = cinema.cancelScreening(screening2, currentTime);
        
        // Expected Output: false
        assertFalse("Cancellation should fail for non-existent screening", result);
        assertEquals("Cinema should still have 0 screenings", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase3_CancelAtExactScreeningTimeBoundary() throws Exception {
        // Setup: Assign screening S1 for F1 in R1 at "2024-12-03 10:00:00"
        Date screeningTime = dateFormat.parse("2024-12-03 10:00:00");
        Date currentTimeForAssignment = dateFormat.parse("2024-12-01 10:00:00");
        
        boolean assigned = cinema.assignScreening(film1, room1, screeningTime, currentTimeForAssignment);
        assertTrue("Screening should be assigned successfully", assigned);
        assertEquals("Cinema should have 1 screening before cancellation attempt", 1, cinema.getScreenings().size());
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        Screening screening1 = cinema.getScreenings().get(0);
        Date cancelTime = dateFormat.parse("2024-12-03 10:00:00");
        
        boolean result = cinema.cancelScreening(screening1, cancelTime);
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Cancellation should fail at exact screening time boundary", result);
        assertEquals("Cinema should still have 1 screening", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_CancelAlreadyCancelledScreening() throws Exception {
        // Setup: Assign Screening1(Film1, Room1, "2024-10-03 12:00:00") then cancel Screening1 at "2024-10-02 12:00:00" (true)
        Date screeningTime = dateFormat.parse("2024-10-03 12:00:00");
        Date currentTimeForAssignment = dateFormat.parse("2024-10-01 12:00:00");
        
        boolean assigned = cinema.assignScreening(film1, room1, screeningTime, currentTimeForAssignment);
        assertTrue("Screening should be assigned successfully", assigned);
        
        Screening screening1 = cinema.getScreenings().get(0);
        Date firstCancelTime = dateFormat.parse("2024-10-02 12:00:00");
        
        boolean firstCancellation = cinema.cancelScreening(screening1, firstCancelTime);
        assertTrue("First cancellation should be successful", firstCancellation);
        assertEquals("Cinema should have 0 screenings after first cancellation", 0, cinema.getScreenings().size());
        
        // Action: cancel Screening1 at current time "2024-10-02 12:05:00"
        Date secondCancelTime = dateFormat.parse("2024-10-02 12:05:00");
        boolean result = cinema.cancelScreening(screening1, secondCancelTime);
        
        // Expected Output: false, there is no screening in the cinema
        assertFalse("Second cancellation should fail for already cancelled screening", result);
        assertEquals("Cinema should still have 0 screenings", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_CancelPastScreeningAfterShowtime() throws Exception {
        // Setup: Assign screening S1 for F1 in R1 at "2024-12-01 14:00:00"
        Date screeningTime = dateFormat.parse("2024-12-01 14:00:00");
        Date currentTimeForAssignment = dateFormat.parse("2024-11-30 10:00:00");
        
        boolean assigned = cinema.assignScreening(film1, room1, screeningTime, currentTimeForAssignment);
        assertTrue("Screening should be assigned successfully", assigned);
        assertEquals("Cinema should have 1 screening before cancellation attempt", 1, cinema.getScreenings().size());
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        Screening screening1 = cinema.getScreenings().get(0);
        Date cancelTime = dateFormat.parse("2024-12-03 10:00:00");
        
        boolean result = cinema.cancelScreening(screening1, cancelTime);
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Cancellation should fail for past screening", result);
        assertEquals("Cinema should still have 1 screening", 1, cinema.getScreenings().size());
    }
}