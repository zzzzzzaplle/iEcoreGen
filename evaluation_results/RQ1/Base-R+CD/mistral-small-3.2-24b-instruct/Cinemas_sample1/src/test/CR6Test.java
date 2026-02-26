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
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-10-03 12:00:00");
        screening1.setTime(screeningTime);
        cinema.assignScreening(film1, room1, screening1, dateFormat.parse("2024-10-01 12:00:00"));
        
        // Action
        boolean result = cinema.cancelScreening(screening1, dateFormat.parse("2024-10-02 12:00:00"));
        
        // Verify
        assertTrue("Screening should be canceled successfully", result);
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_CancelNonExistentScreening() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Screening screening2 = new Screening();
        screening2.setTime(dateFormat.parse("2024-10-02 12:05:00"));
        
        // Action - screening2 was never assigned to cinema
        boolean result = cinema.cancelScreening(screening2, dateFormat.parse("2024-10-03 12:05:00"));
        
        // Verify
        assertFalse("Non-existent screening should not be canceled", result);
    }
    
    @Test
    public void testCase3_CancelAtExactScreeningTimeBoundary() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-12-03 10:00:00");
        screening1.setTime(screeningTime);
        cinema.assignScreening(film1, room1, screening1, dateFormat.parse("2024-12-01 10:00:00"));
        
        // Action - current time equals screening time
        boolean result = cinema.cancelScreening(screening1, screeningTime);
        
        // Verify
        assertFalse("Screening at exact time boundary should not be canceled", result);
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_CancelAlreadyCancelledScreening() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-10-03 12:00:00");
        screening1.setTime(screeningTime);
        cinema.assignScreening(film1, room1, screening1, dateFormat.parse("2024-10-01 12:00:00"));
        
        // First cancellation (should succeed)
        boolean firstResult = cinema.cancelScreening(screening1, dateFormat.parse("2024-10-02 12:00:00"));
        assertTrue("First cancellation should succeed", firstResult);
        
        // Action - try to cancel already canceled screening
        boolean secondResult = cinema.cancelScreening(screening1, dateFormat.parse("2024-10-02 12:05:00"));
        
        // Verify
        assertFalse("Already canceled screening should not be canceled again", secondResult);
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_CancelPastScreeningAfterShowtime() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-12-01 14:00:00");
        screening1.setTime(screeningTime);
        cinema.assignScreening(film1, room1, screening1, dateFormat.parse("2024-11-01 14:00:00"));
        
        // Action - current time is after screening time
        boolean result = cinema.cancelScreening(screening1, dateFormat.parse("2024-12-03 10:00:00"));
        
        // Verify
        assertFalse("Past screening should not be canceled", result);
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().size());
    }
}