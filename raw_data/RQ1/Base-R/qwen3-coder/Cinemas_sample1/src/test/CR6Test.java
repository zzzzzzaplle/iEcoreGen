import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CR6Test {
    private Cinema cinema;
    private Film film1;
    private Room room1;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film("Film1");
        room1 = new Room("Room1");
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_cancelFutureScreening() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        LocalDateTime screeningTime = LocalDateTime.parse("2024-10-03 12:00:00", formatter);
        cinema.assignScreening(film1, room1, screeningTime, LocalDateTime.parse("2024-10-02 12:00:00", formatter));
        
        // Action
        Screening screening1 = new Screening(film1, room1, screeningTime);
        boolean result = cinema.cancelScreening(screening1, LocalDateTime.parse("2024-10-02 12:00:00", formatter));
        
        // Verify
        assertTrue("Screening should be canceled successfully", result);
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_cancelNonExistentScreening() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action
        Screening screening2 = new Screening(film1, room1, LocalDateTime.parse("2024-10-02 12:05:00", formatter));
        boolean result = cinema.cancelScreening(screening2, LocalDateTime.parse("2024-10-03 12:05:00", formatter));
        
        // Verify
        assertFalse("Non-existent screening should not be canceled", result);
    }
    
    @Test
    public void testCase3_cancelAtExactScreeningTimeBoundary() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-03 10:00:00", formatter);
        cinema.assignScreening(film1, room1, screeningTime, LocalDateTime.parse("2024-12-02 10:00:00", formatter));
        
        // Action
        Screening screening1 = new Screening(film1, room1, screeningTime);
        boolean result = cinema.cancelScreening(screening1, LocalDateTime.parse("2024-12-03 10:00:00", formatter));
        
        // Verify
        assertFalse("Screening at exact time boundary should not be canceled", result);
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_cancelAlreadyCancelledScreening() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        LocalDateTime screeningTime = LocalDateTime.parse("2024-10-03 12:00:00", formatter);
        cinema.assignScreening(film1, room1, screeningTime, LocalDateTime.parse("2024-10-02 12:00:00", formatter));
        
        // First cancellation (successful)
        Screening screening1 = new Screening(film1, room1, screeningTime);
        boolean firstResult = cinema.cancelScreening(screening1, LocalDateTime.parse("2024-10-02 12:00:00", formatter));
        assertTrue("First cancellation should succeed", firstResult);
        
        // Action - Try to cancel already cancelled screening
        boolean secondResult = cinema.cancelScreening(screening1, LocalDateTime.parse("2024-10-02 12:05:00", formatter));
        
        // Verify
        assertFalse("Already cancelled screening should not be canceled again", secondResult);
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_cancelPastScreeningAfterShowtime() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-01 14:00:00", formatter);
        cinema.assignScreening(film1, room1, screeningTime, LocalDateTime.parse("2024-11-30 10:00:00", formatter));
        
        // Action
        Screening screening1 = new Screening(film1, room1, screeningTime);
        boolean result = cinema.cancelScreening(screening1, LocalDateTime.parse("2024-12-03 10:00:00", formatter));
        
        // Verify
        assertFalse("Past screening should not be canceled", result);
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().size());
    }
}