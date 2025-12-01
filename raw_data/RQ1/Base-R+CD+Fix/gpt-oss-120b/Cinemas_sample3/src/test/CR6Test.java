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
        film1 = new Film("Film1");
        room1 = new Room("Room1");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Add film and room to cinema for tests that need them
        cinema.addFilm(film1);
        cinema.addRoom(room1);
    }
    
    @Test
    public void testCase1_CancelFutureScreening() throws Exception {
        // Setup: Create Cinema C1, Add Film1, Add Room1, Assign Screening1
        Date screeningTime = dateFormat.parse("2024-10-03 12:00:00");
        Screening screening1 = new Screening();
        screening1.setTime(screeningTime);
        
        Date currentTimeForAssignment = dateFormat.parse("2024-10-01 12:00:00");
        boolean assigned = cinema.assignScreening(film1, room1, screening1, currentTimeForAssignment);
        assertTrue("Screening should be assigned successfully", assigned);
        
        // Action: cancel Screening1 at "2024-10-02 12:00:00"
        Date cancelTime = dateFormat.parse("2024-10-02 12:00:00");
        boolean result = cinema.cancelScreening(screening1, cancelTime);
        
        // Expected Output: true, there is no screening in the cinema C1
        assertTrue("Screening should be cancelled successfully", result);
        assertEquals("Cinema should have no screenings after cancellation", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_CancelNonExistentScreening() throws Exception {
        // Setup: Create Cinema C1, Screening2 (the screening time: "2024-10-02 12:05:00")
        // Add Film1, Add Room1 (already done in setUp)
        Date screeningTime = dateFormat.parse("2024-10-02 12:05:00");
        Screening screening2 = new Screening(screeningTime, film1, room1);
        
        // Note: screening2 is NOT assigned to cinema, so it doesn't exist in cinema's screenings list
        
        // Action: cancel Screening2 at current time "2024-10-03 12:05:00"
        Date cancelTime = dateFormat.parse("2024-10-03 12:05:00");
        boolean result = cinema.cancelScreening(screening2, cancelTime);
        
        // Expected Output: false
        assertFalse("Cancelling non-existent screening should return false", result);
    }
    
    @Test
    public void testCase3_CancelAtExactScreeningTimeBoundary() throws Exception {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        // Assign screening S1 for F1 in R1 at "2024-12-03 10:00:00"
        Date screeningTime = dateFormat.parse("2024-12-03 10:00:00");
        Screening screening1 = new Screening();
        screening1.setTime(screeningTime);
        
        Date currentTimeForAssignment = dateFormat.parse("2024-12-01 10:00:00");
        boolean assigned = cinema.assignScreening(film1, room1, screening1, currentTimeForAssignment);
        assertTrue("Screening should be assigned successfully", assigned);
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        Date cancelTime = dateFormat.parse("2024-12-03 10:00:00");
        boolean result = cinema.cancelScreening(screening1, cancelTime);
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Cancelling at exact screening time should return false", result);
        assertEquals("Cinema should still have 1 screening", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_CancelAlreadyCancelledScreening() throws Exception {
        // Setup: Create Cinema C1, Add Film1, Add Room1
        // Assign Screening1(Film1, Room1, "2024-10-03 12:00:00") then cancel Screening1 at "2024-10-02 12:00:00" (true)
        Date screeningTime = dateFormat.parse("2024-10-03 12:00:00");
        Screening screening1 = new Screening();
        screening1.setTime(screeningTime);
        
        Date currentTimeForAssignment = dateFormat.parse("2024-10-01 12:00:00");
        boolean assigned = cinema.assignScreening(film1, room1, screening1, currentTimeForAssignment);
        assertTrue("Screening should be assigned successfully", assigned);
        
        // First cancellation (should succeed)
        Date firstCancelTime = dateFormat.parse("2024-10-02 12:00:00");
        boolean firstResult = cinema.cancelScreening(screening1, firstCancelTime);
        assertTrue("First cancellation should succeed", firstResult);
        
        // Action: cancel Screening1 at current time "2024-10-02 12:05:00"
        Date secondCancelTime = dateFormat.parse("2024-10-02 12:05:00");
        boolean result = cinema.cancelScreening(screening1, secondCancelTime);
        
        // Expected Output: false, there is no screening in the cinema
        assertFalse("Cancelling already cancelled screening should return false", result);
        assertEquals("Cinema should have no screenings", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_CancelPastScreeningAfterShowtime() throws Exception {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        // Assign screening S1 for F1 in R1 at "2024-12-01 14:00:00"
        Date screeningTime = dateFormat.parse("2024-12-01 14:00:00");
        Screening screening1 = new Screening();
        screening1.setTime(screeningTime);
        
        Date currentTimeForAssignment = dateFormat.parse("2024-11-30 14:00:00");
        boolean assigned = cinema.assignScreening(film1, room1, screening1, currentTimeForAssignment);
        assertTrue("Screening should be assigned successfully", assigned);
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        Date cancelTime = dateFormat.parse("2024-12-03 10:00:00");
        boolean result = cinema.cancelScreening(screening1, cancelTime);
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Cancelling past screening should return false", result);
        assertEquals("Cinema should still have 1 screening", 1, cinema.getScreenings().size());
    }
}