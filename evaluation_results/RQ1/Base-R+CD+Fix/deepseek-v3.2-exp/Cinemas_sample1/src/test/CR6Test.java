import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
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
    public void testCase1_CancelFutureScreening() throws ParseException {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        Date screeningTime = dateFormat.parse("2024-10-03 12:00:00");
        Date currentTimeForAssign = dateFormat.parse("2024-10-01 12:00:00");
        cinema.assignScreening(film1, room1, screeningTime, currentTimeForAssign);
        
        // Action
        Date cancelTime = dateFormat.parse("2024-10-02 12:00:00");
        boolean result = cinema.cancelScreening(screening1, cancelTime);
        
        // Verify
        assertTrue("Screening should be canceled successfully", result);
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_CancelNonExistentScreening() throws ParseException {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action - try to cancel a screening that was never added
        Date cancelTime = dateFormat.parse("2024-10-03 12:05:00");
        boolean result = cinema.cancelScreening(screening1, cancelTime);
        
        // Verify
        assertFalse("Should return false for non-existent screening", result);
    }
    
    @Test
    public void testCase3_CancelAtExactScreeningTimeBoundary() throws ParseException {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        Date screeningTime = dateFormat.parse("2024-12-03 10:00:00");
        Date currentTimeForAssign = dateFormat.parse("2024-12-01 10:00:00");
        cinema.assignScreening(film1, room1, screeningTime, currentTimeForAssign);
        
        // Action - try to cancel at exact screening time
        Date cancelTime = dateFormat.parse("2024-12-03 10:00:00");
        boolean result = cinema.cancelScreening(screening1, cancelTime);
        
        // Verify
        assertFalse("Should return false when canceling at exact screening time", result);
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_CancelAlreadyCancelledScreening() throws ParseException {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        Date screeningTime = dateFormat.parse("2024-10-03 12:00:00");
        Date currentTimeForAssign = dateFormat.parse("2024-10-01 12:00:00");
        cinema.assignScreening(film1, room1, screeningTime, currentTimeForAssign);
        
        // First cancellation
        Date firstCancelTime = dateFormat.parse("2024-10-02 12:00:00");
        boolean firstResult = cinema.cancelScreening(screening1, firstCancelTime);
        assertTrue("First cancellation should succeed", firstResult);
        
        // Action - try to cancel the same screening again
        Date secondCancelTime = dateFormat.parse("2024-10-02 12:05:00");
        boolean secondResult = cinema.cancelScreening(screening1, secondCancelTime);
        
        // Verify
        assertFalse("Should return false for already canceled screening", secondResult);
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_CancelPastScreeningAfterShowtime() throws ParseException {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        Date screeningTime = dateFormat.parse("2024-12-01 14:00:00");
        Date currentTimeForAssign = dateFormat.parse("2024-11-30 14:00:00");
        cinema.assignScreening(film1, room1, screeningTime, currentTimeForAssign);
        
        // Action - try to cancel after screening time has passed
        Date cancelTime = dateFormat.parse("2024-12-03 10:00:00");
        boolean result = cinema.cancelScreening(screening1, cancelTime);
        
        // Verify
        assertFalse("Should return false for past screening", result);
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().size());
    }
}