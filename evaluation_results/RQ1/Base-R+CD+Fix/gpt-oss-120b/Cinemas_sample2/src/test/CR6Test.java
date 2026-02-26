import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CR6Test {
    
    private Cinema cinema;
    private Film film1;
    private Room room1;
    private Screening screening1;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() throws Exception {
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
        screening1 = new Screening(screeningTime);
        
        Date currentTimeForAssignment = dateFormat.parse("2024-10-01 12:00:00");
        cinema.assignScreening(film1, room1, screening1, currentTimeForAssignment);
        
        // Action
        Date currentTimeForCancellation = dateFormat.parse("2024-10-02 12:00:00");
        boolean result = cinema.cancelScreening(screening1, currentTimeForCancellation);
        
        // Verify
        assertTrue("Screening should be cancelled successfully", result);
        assertFalse("Screening should be removed from cinema", cinema.getScreenings().contains(screening1));
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_CancelNonExistentScreening() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-10-02 12:05:00");
        Screening screening2 = new Screening(screeningTime);
        
        // Action
        Date currentTime = dateFormat.parse("2024-10-03 12:05:00");
        boolean result = cinema.cancelScreening(screening2, currentTime);
        
        // Verify
        assertFalse("Non-existent screening should not be cancelled", result);
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase3_CancelAtExactScreeningTimeBoundary() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-12-03 10:00:00");
        screening1 = new Screening(screeningTime);
        
        Date currentTimeForAssignment = dateFormat.parse("2024-12-01 10:00:00");
        cinema.assignScreening(film1, room1, screening1, currentTimeForAssignment);
        
        // Action
        Date currentTimeForCancellation = dateFormat.parse("2024-12-03 10:00:00");
        boolean result = cinema.cancelScreening(screening1, currentTimeForCancellation);
        
        // Verify
        assertFalse("Screening at exact time boundary should not be cancelled", result);
        assertTrue("Screening should still be in cinema", cinema.getScreenings().contains(screening1));
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_CancelAlreadyCancelledScreening() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-10-03 12:00:00");
        screening1 = new Screening(screeningTime);
        
        Date currentTimeForAssignment = dateFormat.parse("2024-10-01 12:00:00");
        cinema.assignScreening(film1, room1, screening1, currentTimeForAssignment);
        
        // First cancellation (successful)
        Date firstCancellationTime = dateFormat.parse("2024-10-02 12:00:00");
        boolean firstResult = cinema.cancelScreening(screening1, firstCancellationTime);
        assertTrue("First cancellation should be successful", firstResult);
        
        // Action - Second cancellation attempt
        Date secondCancellationTime = dateFormat.parse("2024-10-02 12:05:00");
        boolean result = cinema.cancelScreening(screening1, secondCancellationTime);
        
        // Verify
        assertFalse("Already cancelled screening should not be cancelled again", result);
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_CancelPastScreening() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-12-01 14:00:00");
        screening1 = new Screening(screeningTime);
        
        Date currentTimeForAssignment = dateFormat.parse("2024-11-01 10:00:00");
        cinema.assignScreening(film1, room1, screening1, currentTimeForAssignment);
        
        // Action
        Date currentTimeForCancellation = dateFormat.parse("2024-12-03 10:00:00");
        boolean result = cinema.cancelScreening(screening1, currentTimeForCancellation);
        
        // Verify
        assertFalse("Past screening should not be cancelled", result);
        assertTrue("Screening should still be in cinema", cinema.getScreenings().contains(screening1));
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().size());
    }
}