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
        film1 = new Film("Film1");
        room1 = new Room("Room1");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    public void testCase1_CancelFutureScreening() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-10-03 12:00:00");
        Screening screening1 = new Screening(screeningTime);
        Date currentTimeForAssignment = dateFormat.parse("2024-10-01 12:00:00");
        cinema.assignScreening(film1, room1, screening1, currentTimeForAssignment);
        
        // Action: cancel Screening1 at "2024-10-02 12:00:00"
        Date cancelTime = dateFormat.parse("2024-10-02 12:00:00");
        boolean result = cinema.cancelScreening(screening1, cancelTime);
        
        // Expected Output: true, there is no screening in the cinema C1.
        assertTrue("Screening should be canceled successfully", result);
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().size());
    }

    @Test
    public void testCase2_CancelNonExistentScreening() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Screening2 is created but not assigned to cinema
        Date screeningTime = dateFormat.parse("2024-10-02 12:05:00");
        Screening screening2 = new Screening(screeningTime);
        
        // Action: cancel Screening2 at current time "2024-10-03 12:05:00"
        Date cancelTime = dateFormat.parse("2024-10-03 12:05:00");
        boolean result = cinema.cancelScreening(screening2, cancelTime);
        
        // Expected Output: false
        assertFalse("Non-existent screening should not be canceled", result);
    }

    @Test
    public void testCase3_CancelAtExactScreeningTimeBoundary() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-12-03 10:00:00");
        Screening screening1 = new Screening(screeningTime);
        Date currentTimeForAssignment = dateFormat.parse("2024-12-01 10:00:00");
        cinema.assignScreening(film1, room1, screening1, currentTimeForAssignment);
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        Date cancelTime = dateFormat.parse("2024-12-03 10:00:00");
        boolean result = cinema.cancelScreening(screening1, cancelTime);
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Screening at exact time boundary should not be canceled", result);
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().size());
    }

    @Test
    public void testCase4_CancelAlreadyCancelledScreening() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-10-03 12:00:00");
        Screening screening1 = new Screening(screeningTime);
        Date currentTimeForAssignment = dateFormat.parse("2024-10-01 12:00:00");
        cinema.assignScreening(film1, room1, screening1, currentTimeForAssignment);
        
        // First cancel: cancel Screening1 at "2024-10-02 12:00:00" (true)
        Date firstCancelTime = dateFormat.parse("2024-10-02 12:00:00");
        boolean firstResult = cinema.cancelScreening(screening1, firstCancelTime);
        assertTrue("First cancellation should succeed", firstResult);
        
        // Action: cancel Screening1 at current time "2024-10-02 12:05:00"
        Date secondCancelTime = dateFormat.parse("2024-10-02 12:05:00");
        boolean secondResult = cinema.cancelScreening(screening1, secondCancelTime);
        
        // Expected Output: false, there is no screening in the cinema
        assertFalse("Already cancelled screening should not be canceled again", secondResult);
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().size());
    }

    @Test
    public void testCase5_CancelPastScreeningAfterShowtime() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-12-01 14:00:00");
        Screening screening1 = new Screening(screeningTime);
        Date currentTimeForAssignment = dateFormat.parse("2024-11-30 14:00:00");
        cinema.assignScreening(film1, room1, screening1, currentTimeForAssignment);
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        Date cancelTime = dateFormat.parse("2024-12-03 10:00:00");
        boolean result = cinema.cancelScreening(screening1, cancelTime);
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Past screening should not be canceled", result);
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().size());
    }
}