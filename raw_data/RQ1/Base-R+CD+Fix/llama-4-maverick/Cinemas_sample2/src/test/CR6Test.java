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
    public void setUp() throws Exception {
        cinema = new Cinema();
        film1 = new Film();
        room1 = new Room();
        screening1 = new Screening();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_cancelFutureScreening() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-10-03 12:00:00");
        screening1.setTime(screeningTime);
        screening1.setFilm(film1);
        screening1.setRoom(room1);
        
        // Add screening directly to cinema screenings list for test setup
        cinema.getScreenings().add(screening1);
        
        Date currentTime = dateFormat.parse("2024-10-02 12:00:00");
        
        // Action
        boolean result = cinema.cancelScreening(screening1, currentTime);
        
        // Assert
        assertTrue("Screening should be canceled successfully", result);
        assertFalse("Screening should be removed from cinema", cinema.getScreenings().contains(screening1));
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_cancelNonExistentScreening() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Create a screening but don't add it to cinema
        Screening screening2 = new Screening();
        Date screeningTime = dateFormat.parse("2024-10-02 12:05:00");
        screening2.setTime(screeningTime);
        screening2.setFilm(film1);
        screening2.setRoom(room1);
        
        Date currentTime = dateFormat.parse("2024-10-03 12:05:00");
        
        // Action
        boolean result = cinema.cancelScreening(screening2, currentTime);
        
        // Assert
        assertFalse("Non-existent screening should not be canceled", result);
    }
    
    @Test
    public void testCase3_cancelAtExactScreeningTimeBoundary() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-12-03 10:00:00");
        screening1.setTime(screeningTime);
        screening1.setFilm(film1);
        screening1.setRoom(room1);
        
        // Add screening directly to cinema screenings list for test setup
        cinema.getScreenings().add(screening1);
        
        Date currentTime = dateFormat.parse("2024-12-03 10:00:00");
        
        // Action
        boolean result = cinema.cancelScreening(screening1, currentTime);
        
        // Assert
        assertFalse("Screening at exact time boundary should not be canceled", result);
        assertTrue("Screening should still be in cinema", cinema.getScreenings().contains(screening1));
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_cancelAlreadyCancelledScreening() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-10-03 12:00:00");
        screening1.setTime(screeningTime);
        screening1.setFilm(film1);
        screening1.setRoom(room1);
        
        // Add screening directly to cinema screenings list for test setup
        cinema.getScreenings().add(screening1);
        
        Date firstCancelTime = dateFormat.parse("2024-10-02 12:00:00");
        cinema.cancelScreening(screening1, firstCancelTime); // First cancellation (should succeed)
        
        Date secondCancelTime = dateFormat.parse("2024-10-02 12:05:00");
        
        // Action
        boolean result = cinema.cancelScreening(screening1, secondCancelTime);
        
        // Assert
        assertFalse("Already canceled screening should not be canceled again", result);
        assertFalse("Screening should not be in cinema", cinema.getScreenings().contains(screening1));
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_cancelPastScreening() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-12-01 14:00:00");
        screening1.setTime(screeningTime);
        screening1.setFilm(film1);
        screening1.setRoom(room1);
        
        // Add screening directly to cinema screenings list for test setup
        cinema.getScreenings().add(screening1);
        
        Date currentTime = dateFormat.parse("2024-12-03 10:00:00");
        
        // Action
        boolean result = cinema.cancelScreening(screening1, currentTime);
        
        // Assert
        assertFalse("Past screening should not be canceled", result);
        assertTrue("Screening should still be in cinema", cinema.getScreenings().contains(screening1));
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().size());
    }
}