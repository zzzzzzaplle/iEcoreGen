import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.ParseException;

public class CR6Test {
    
    private Cinema cinema;
    private Film film1;
    private Room room1;
    private Screening screening1;
    private DateUtils dateUtils;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film();
        room1 = new Room();
        screening1 = new Screening();
        dateUtils = new DateUtils();
    }
    
    @Test
    public void testCase1_CancelFutureScreening() throws ParseException {
        // Setup: Create Cinema C1, Add Film1, Add Room1, Assign Screening1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = DateUtils.parseDate("2024-10-03 12:00:00");
        Date currentTimeForAssignment = DateUtils.parseDate("2024-10-01 12:00:00"); // Before screening time for assignment
        
        // Assign screening
        boolean assignmentResult = cinema.assignScreening(film1, room1, screeningTime, currentTimeForAssignment);
        assertTrue("Screening should be assigned successfully", assignmentResult);
        
        // Verify there is 1 screening before cancellation
        assertEquals("There should be 1 screening before cancellation", 1, cinema.getScreenings().size());
        
        // Action: cancel Screening1 at "2024-10-02 12:00:00"
        Date cancelTime = DateUtils.parseDate("2024-10-02 12:00:00");
        boolean result = cinema.cancelScreening(cinema.getScreenings().get(0), cancelTime);
        
        // Expected Output: true, there is no screening in the cinema C1
        assertTrue("Cancellation should be successful", result);
        assertEquals("There should be no screenings after cancellation", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_CancelNonExistentScreening() throws ParseException {
        // Setup: Create Cinema C1, Add Film1, Add Room1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Screening2 is created but not added to cinema
        Screening screening2 = new Screening();
        screening2.setTime(DateUtils.parseDate("2024-10-02 12:05:00"));
        screening2.setFilm(film1);
        screening2.setRoom(room1);
        
        // Action: cancel Screening2 at current time "2024-10-03 12:05:00"
        Date currentTime = DateUtils.parseDate("2024-10-03 12:05:00");
        boolean result = cinema.cancelScreening(screening2, currentTime);
        
        // Expected Output: false
        assertFalse("Cancellation should fail for non-existent screening", result);
    }
    
    @Test
    public void testCase3_CancelAtExactScreeningTimeBoundary() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1, Add Room R1, Assign screening S1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = DateUtils.parseDate("2024-12-03 10:00:00");
        Date currentTimeForAssignment = DateUtils.parseDate("2024-12-01 10:00:00"); // Before screening time for assignment
        
        // Assign screening
        boolean assignmentResult = cinema.assignScreening(film1, room1, screeningTime, currentTimeForAssignment);
        assertTrue("Screening should be assigned successfully", assignmentResult);
        
        // Verify there is 1 screening before cancellation attempt
        assertEquals("There should be 1 screening before cancellation attempt", 1, cinema.getScreenings().size());
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        Date currentTime = DateUtils.parseDate("2024-12-03 10:00:00");
        boolean result = cinema.cancelScreening(cinema.getScreenings().get(0), currentTime);
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Cancellation should fail at exact screening time boundary", result);
        assertEquals("There should still be 1 screening after failed cancellation", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_CancelAlreadyCancelledScreening() throws ParseException {
        // Setup: Create Cinema C1, Add Film1, Add Room1, Assign Screening1 then cancel it
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = DateUtils.parseDate("2024-10-03 12:00:00");
        Date currentTimeForAssignment = DateUtils.parseDate("2024-10-01 12:00:00"); // Before screening time for assignment
        
        // Assign screening
        boolean assignmentResult = cinema.assignScreening(film1, room1, screeningTime, currentTimeForAssignment);
        assertTrue("Screening should be assigned successfully", assignmentResult);
        
        // First cancellation at "2024-10-02 12:00:00"
        Date firstCancelTime = DateUtils.parseDate("2024-10-02 12:00:00");
        boolean firstCancellationResult = cinema.cancelScreening(cinema.getScreenings().get(0), firstCancelTime);
        assertTrue("First cancellation should be successful", firstCancellationResult);
        
        // Verify no screenings after first cancellation
        assertEquals("There should be no screenings after first cancellation", 0, cinema.getScreenings().size());
        
        // Action: cancel Screening1 again at current time "2024-10-02 12:05:00"
        Date secondCancelTime = DateUtils.parseDate("2024-10-02 12:05:00");
        boolean result = cinema.cancelScreening(screening1, secondCancelTime);
        
        // Expected Output: false, there is no screening in the cinema
        assertFalse("Cancellation should fail for already cancelled screening", result);
        assertEquals("There should still be no screenings", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_CancelPastScreeningAfterShowtime() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1, Add Room R1, Assign screening S1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = DateUtils.parseDate("2024-12-01 14:00:00");
        Date currentTimeForAssignment = DateUtils.parseDate("2024-11-01 14:00:00"); // Before screening time for assignment
        
        // Assign screening
        boolean assignmentResult = cinema.assignScreening(film1, room1, screeningTime, currentTimeForAssignment);
        assertTrue("Screening should be assigned successfully", assignmentResult);
        
        // Verify there is 1 screening before cancellation attempt
        assertEquals("There should be 1 screening before cancellation attempt", 1, cinema.getScreenings().size());
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        Date currentTime = DateUtils.parseDate("2024-12-03 10:00:00");
        boolean result = cinema.cancelScreening(cinema.getScreenings().get(0), currentTime);
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Cancellation should fail for past screening", result);
        assertEquals("There should still be 1 screening after failed cancellation", 1, cinema.getScreenings().size());
    }
}