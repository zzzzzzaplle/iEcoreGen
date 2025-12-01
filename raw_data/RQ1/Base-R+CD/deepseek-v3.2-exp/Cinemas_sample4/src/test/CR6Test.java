import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR6Test {
    
    private Cinema cinema;
    private Film film1;
    private Room room1;
    private Screening screening1;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() throws ParseException {
        cinema = new Cinema();
        film1 = new Film();
        room1 = new Room();
        screening1 = new Screening();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Common setup for tests that need film and room
        cinema.addFilm(film1);
        cinema.addRoom(room1);
    }
    
    @Test
    public void testCase1_cancelFutureScreening() throws ParseException {
        // Setup: Create Cinema C1, Add Film1, Add Room1, Assign Screening1
        Date screeningTime = dateFormat.parse("2024-10-03 12:00:00");
        Date currentTime = dateFormat.parse("2024-10-02 12:00:00");
        
        // Assign screening
        boolean assigned = cinema.assignScreening(film1, room1, screeningTime, currentTime);
        assertTrue("Screening should be assigned successfully", assigned);
        
        // Action: cancel Screening1 at "2024-10-02 12:00:00"
        Screening screeningToCancel = cinema.getScreenings().get(0);
        boolean result = cinema.cancelScreening(screeningToCancel, currentTime);
        
        // Expected Output: true, there is no screening in the cinema C1
        assertTrue("Screening should be canceled successfully", result);
        assertEquals("Cinema should have no screenings after cancellation", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_cancelNonExistentScreening() throws ParseException {
        // Setup: Create Cinema C1, Screening2 (the screening time: "2024-10-02 12:05:00")
        // Add Film1, Add Room1
        Date currentTime = dateFormat.parse("2024-10-03 12:05:00");
        
        // Create a screening that is not added to cinema
        Screening screening2 = new Screening();
        screening2.setFilm(film1);
        screening2.setRoom(room1);
        screening2.setTime(dateFormat.parse("2024-10-02 12:05:00"));
        
        // Action: cancel Screening2 at current time "2024-10-03 12:05:00"
        boolean result = cinema.cancelScreening(screening2, currentTime);
        
        // Expected Output: false
        assertFalse("Non-existent screening should not be canceled", result);
    }
    
    @Test
    public void testCase3_cancelAtExactScreeningTimeBoundary() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        // Assign screening S1 for F1 in R1 at "2024-12-03 10:00:00"
        Date screeningTime = dateFormat.parse("2024-12-03 10:00:00");
        Date assignmentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        boolean assigned = cinema.assignScreening(film1, room1, screeningTime, assignmentTime);
        assertTrue("Screening should be assigned successfully", assigned);
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-03 10:00:00");
        Screening screeningToCancel = cinema.getScreenings().get(0);
        boolean result = cinema.cancelScreening(screeningToCancel, currentTime);
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Screening at exact time boundary should not be canceled", result);
        assertEquals("Cinema should still have 1 screening", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_cancelAlreadyCancelledScreening() throws ParseException {
        // Setup: Create Cinema C1, Add Film1, Add Room1
        // Assign Screening1(Film1, Room1, "2024-10-03 12:00:00") then cancel Screening1 at "2024-10-02 12:00:00" (true)
        Date screeningTime = dateFormat.parse("2024-10-03 12:00:00");
        Date firstCancelTime = dateFormat.parse("2024-10-02 12:00:00");
        
        // Assign and then cancel first time
        boolean assigned = cinema.assignScreening(film1, room1, screeningTime, firstCancelTime);
        assertTrue("Screening should be assigned successfully", assigned);
        
        Screening screeningToCancel = cinema.getScreenings().get(0);
        boolean firstCancelResult = cinema.cancelScreening(screeningToCancel, firstCancelTime);
        assertTrue("First cancellation should be successful", firstCancelResult);
        
        // Action: cancel Screening1 at current time "2024-10-02 12:05:00"
        Date secondCancelTime = dateFormat.parse("2024-10-02 12:05:00");
        boolean result = cinema.cancelScreening(screeningToCancel, secondCancelTime);
        
        // Expected Output: false, there is no screening in the cinema
        assertFalse("Already canceled screening should not be canceled again", result);
        assertEquals("Cinema should have no screenings", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_cancelPastScreeningAfterShowtime() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        // Assign screening S1 for F1 in R1 at "2024-12-01 14:00:00"
        Date screeningTime = dateFormat.parse("2024-12-01 14:00:00");
        Date assignmentTime = dateFormat.parse("2024-11-30 10:00:00");
        
        boolean assigned = cinema.assignScreening(film1, room1, screeningTime, assignmentTime);
        assertTrue("Screening should be assigned successfully", assigned);
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-03 10:00:00");
        Screening screeningToCancel = cinema.getScreenings().get(0);
        boolean result = cinema.cancelScreening(screeningToCancel, currentTime);
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Past screening should not be canceled", result);
        assertEquals("Cinema should still have 1 screening", 1, cinema.getScreenings().size());
    }
}