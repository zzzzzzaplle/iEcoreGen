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
    private Screening screening2;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() throws Exception {
        cinema = new Cinema();
        film1 = new Film();
        room1 = new Room();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CancelFutureScreening() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-10-03 12:00:00");
        screening1 = new Screening(screeningTime, film1, room1);
        cinema.assignScreening(film1, room1, screening1, dateFormat.parse("2024-10-01 12:00:00"));
        
        // Action: cancel Screening1 at "2024-10-02 12:00:00"
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
        
        // Create screening2 but don't assign it to cinema
        Date screeningTime = dateFormat.parse("2024-10-02 12:05:00");
        screening2 = new Screening(screeningTime, film1, room1);
        
        // Action: cancel Screening2 at current time "2024-10-03 12:05:00"
        boolean result = cinema.cancelScreening(screening2, dateFormat.parse("2024-10-03 12:05:00"));
        
        // Verify
        assertFalse("Should return false for non-existent screening", result);
    }
    
    @Test
    public void testCase3_CancelAtExactScreeningTimeBoundary() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-12-03 10:00:00");
        screening1 = new Screening(screeningTime, film1, room1);
        cinema.assignScreening(film1, room1, screening1, dateFormat.parse("2024-12-02 10:00:00"));
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        boolean result = cinema.cancelScreening(screening1, dateFormat.parse("2024-12-03 10:00:00"));
        
        // Verify
        assertFalse("Should return false when current time equals screening time", result);
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_CancelAlreadyCancelledScreening() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-10-03 12:00:00");
        screening1 = new Screening(screeningTime, film1, room1);
        cinema.assignScreening(film1, room1, screening1, dateFormat.parse("2024-10-01 12:00:00"));
        
        // First cancellation (should succeed)
        boolean firstResult = cinema.cancelScreening(screening1, dateFormat.parse("2024-10-02 12:00:00"));
        assertTrue("First cancellation should succeed", firstResult);
        
        // Action: cancel Screening1 at current time "2024-10-02 12:05:00"
        boolean result = cinema.cancelScreening(screening1, dateFormat.parse("2024-10-02 12:05:00"));
        
        // Verify
        assertFalse("Should return false for already cancelled screening", result);
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_CancelPastScreeningAfterShowtime() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-12-01 14:00:00");
        screening1 = new Screening(screeningTime, film1, room1);
        cinema.assignScreening(film1, room1, screening1, dateFormat.parse("2024-11-30 10:00:00"));
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        boolean result = cinema.cancelScreening(screening1, dateFormat.parse("2024-12-03 10:00:00"));
        
        // Verify
        assertFalse("Should return false for past screening", result);
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().size());
    }
}