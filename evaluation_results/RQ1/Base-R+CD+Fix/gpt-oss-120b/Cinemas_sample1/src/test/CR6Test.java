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
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film("Film1");
        room1 = new Room("Room1");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CancelFutureScreening() throws ParseException {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-10-03 12:00:00");
        Screening screening1 = new Screening(screeningTime, film1, room1);
        Date currentTimeForAssignment = dateFormat.parse("2024-10-01 12:00:00");
        cinema.assignScreening(film1, room1, screening1, currentTimeForAssignment);
        
        // Action
        Date cancelTime = dateFormat.parse("2024-10-02 12:00:00");
        boolean result = cinema.cancelScreening(screening1, cancelTime);
        
        // Verify
        assertTrue("Screening should be cancelled successfully", result);
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_CancelNonExistentScreening() throws ParseException {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Create a screening but don't assign it to cinema
        Date screeningTime = dateFormat.parse("2024-10-02 12:05:00");
        Screening screening2 = new Screening(screeningTime, film1, room1);
        
        // Action
        Date currentTime = dateFormat.parse("2024-10-03 12:05:00");
        boolean result = cinema.cancelScreening(screening2, currentTime);
        
        // Verify
        assertFalse("Non-existent screening should not be cancelled", result);
    }
    
    @Test
    public void testCase3_CancelAtExactScreeningTimeBoundary() throws ParseException {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-12-03 10:00:00");
        Screening screening1 = new Screening(screeningTime, film1, room1);
        Date currentTimeForAssignment = dateFormat.parse("2024-12-01 10:00:00");
        cinema.assignScreening(film1, room1, screening1, currentTimeForAssignment);
        
        // Action
        Date cancelTime = dateFormat.parse("2024-12-03 10:00:00");
        boolean result = cinema.cancelScreening(screening1, cancelTime);
        
        // Verify
        assertFalse("Screening at exact time boundary should not be cancelled", result);
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_CancelAlreadyCancelledScreening() throws ParseException {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-10-03 12:00:00");
        Screening screening1 = new Screening(screeningTime, film1, room1);
        Date currentTimeForAssignment = dateFormat.parse("2024-10-01 12:00:00");
        cinema.assignScreening(film1, room1, screening1, currentTimeForAssignment);
        
        // First cancellation (should succeed)
        Date firstCancelTime = dateFormat.parse("2024-10-02 12:00:00");
        boolean firstResult = cinema.cancelScreening(screening1, firstCancelTime);
        assertTrue("First cancellation should succeed", firstResult);
        
        // Action: Second cancellation attempt
        Date secondCancelTime = dateFormat.parse("2024-10-02 12:05:00");
        boolean secondResult = cinema.cancelScreening(screening1, secondCancelTime);
        
        // Verify
        assertFalse("Already cancelled screening should not be cancelled again", secondResult);
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_CancelPastScreeningAfterShowtime() throws ParseException {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-12-01 14:00:00");
        Screening screening1 = new Screening(screeningTime, film1, room1);
        Date currentTimeForAssignment = dateFormat.parse("2024-11-01 14:00:00");
        cinema.assignScreening(film1, room1, screening1, currentTimeForAssignment);
        
        // Action
        Date cancelTime = dateFormat.parse("2024-12-03 10:00:00");
        boolean result = cinema.cancelScreening(screening1, cancelTime);
        
        // Verify
        assertFalse("Past screening should not be cancelled", result);
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().size());
    }
}