import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.util.Date;

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
        Date currentTimeForAssignment = DateUtils.parseDate("2024-10-01 12:00:00");
        
        cinema.assignScreening(film1, room1, screeningTime, currentTimeForAssignment);
        
        // Action: cancel Screening1 at "2024-10-02 12:00:00"
        Date cancelTime = DateUtils.parseDate("2024-10-02 12:00:00");
        boolean result = cinema.cancelScreening(screening1, cancelTime);
        
        // Expected Output: true, there is no screening in the cinema C1
        assertTrue("Screening should be canceled successfully", result);
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_CancelNonExistentScreening() throws ParseException {
        // Setup: Create Cinema C1, Screening2 (the screening time: "2024-10-02 12:05:00")
        // Add Film1, Add Room1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Create a different screening that is not added to cinema
        Screening screening2 = new Screening();
        screening2.setTime(DateUtils.parseDate("2024-10-02 12:05:00"));
        screening2.setFilm(film1);
        screening2.setRoom(room1);
        
        // Action: cancel Screening2 at current time "2024-10-03 12:05:00"
        Date cancelTime = DateUtils.parseDate("2024-10-03 12:05:00");
        boolean result = cinema.cancelScreening(screening2, cancelTime);
        
        // Expected Output: false
        assertFalse("Non-existent screening should not be canceled", result);
    }
    
    @Test
    public void testCase3_CancelAtExactScreeningTimeBoundary() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Assign screening S1 for F1 in R1 at "2024-12-03 10:00:00"
        Date screeningTime = DateUtils.parseDate("2024-12-03 10:00:00");
        Date currentTimeForAssignment = DateUtils.parseDate("2024-12-01 10:00:00");
        
        cinema.assignScreening(film1, room1, screeningTime, currentTimeForAssignment);
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        Date cancelTime = DateUtils.parseDate("2024-12-03 10:00:00");
        boolean result = cinema.cancelScreening(screening1, cancelTime);
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Screening at exact time boundary should not be canceled", result);
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_CancelAlreadyCancelledScreening() throws ParseException {
        // Setup: Create Cinema C1, Add Film1, Add Room1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Assign Screening1(Film1, Room1, "2024-10-03 12:00:00") 
        Date screeningTime = DateUtils.parseDate("2024-10-03 12:00:00");
        Date currentTimeForAssignment = DateUtils.parseDate("2024-10-01 12:00:00");
        
        cinema.assignScreening(film1, room1, screeningTime, currentTimeForAssignment);
        
        // then cancel Screening1 at "2024-10-02 12:00:00" (true)
        Date firstCancelTime = DateUtils.parseDate("2024-10-02 12:00:00");
        boolean firstCancelResult = cinema.cancelScreening(screening1, firstCancelTime);
        assertTrue("First cancellation should succeed", firstCancelResult);
        
        // Action: cancel Screening1 at current time "2024-10-02 12:05:00"
        Date secondCancelTime = DateUtils.parseDate("2024-10-02 12:05:00");
        boolean result = cinema.cancelScreening(screening1, secondCancelTime);
        
        // Expected Output: false, there is no screening in the cinema
        assertFalse("Already cancelled screening should not be canceled again", result);
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_CancelPastScreeningAfterShowtime() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Assign screening S1 for F1 in R1 at "2024-12-01 14:00:00"
        Date screeningTime = DateUtils.parseDate("2024-12-01 14:00:00");
        Date currentTimeForAssignment = DateUtils.parseDate("2024-11-01 14:00:00");
        
        cinema.assignScreening(film1, room1, screeningTime, currentTimeForAssignment);
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        Date cancelTime = DateUtils.parseDate("2024-12-03 10:00:00");
        boolean result = cinema.cancelScreening(screening1, cancelTime);
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Past screening should not be canceled", result);
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().size());
    }
}