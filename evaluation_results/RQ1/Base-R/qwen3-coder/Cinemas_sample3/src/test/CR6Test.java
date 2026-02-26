import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    public void testCase1_CancelFutureScreening() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        LocalDateTime screeningTime = LocalDateTime.parse("2024-10-03 12:00:00", formatter);
        cinema.assignScreening(film1, room1, screeningTime, LocalDateTime.parse("2024-10-01 12:00:00", formatter));
        
        // Action: cancel Screening1 at "2024-10-02 12:00:00"
        Screening screening1 = new Screening(film1, room1, screeningTime);
        LocalDateTime currentTime = LocalDateTime.parse("2024-10-02 12:00:00", formatter);
        boolean result = cinema.cancelScreening(screening1, currentTime);
        
        // Verify result and screening list
        assertTrue("Screening should be canceled successfully", result);
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_CancelNonExistentScreening() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        // Note: Screening2 is created but NOT assigned to cinema
        
        // Action: cancel Screening2 at current time "2024-10-03 12:05:00"
        Screening screening2 = new Screening(film1, room1, LocalDateTime.parse("2024-10-02 12:05:00", formatter));
        LocalDateTime currentTime = LocalDateTime.parse("2024-10-03 12:05:00", formatter);
        boolean result = cinema.cancelScreening(screening2, currentTime);
        
        // Verify result
        assertFalse("Non-existent screening should not be canceled", result);
    }
    
    @Test
    public void testCase3_CancelAtExactScreeningTimeBoundary() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-03 10:00:00", formatter);
        cinema.assignScreening(film1, room1, screeningTime, LocalDateTime.parse("2024-12-01 10:00:00", formatter));
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        Screening screening1 = new Screening(film1, room1, screeningTime);
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-03 10:00:00", formatter);
        boolean result = cinema.cancelScreening(screening1, currentTime);
        
        // Verify result and screening list
        assertFalse("Screening at exact time boundary should not be canceled", result);
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_CancelAlreadyCancelledScreening() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        LocalDateTime screeningTime = LocalDateTime.parse("2024-10-03 12:00:00", formatter);
        cinema.assignScreening(film1, room1, screeningTime, LocalDateTime.parse("2024-10-01 12:00:00", formatter));
        
        // First cancellation (should succeed)
        Screening screening1 = new Screening(film1, room1, screeningTime);
        LocalDateTime firstCancelTime = LocalDateTime.parse("2024-10-02 12:00:00", formatter);
        boolean firstResult = cinema.cancelScreening(screening1, firstCancelTime);
        assertTrue("First cancellation should succeed", firstResult);
        
        // Action: cancel Screening1 again at current time "2024-10-02 12:05:00"
        LocalDateTime secondCancelTime = LocalDateTime.parse("2024-10-02 12:05:00", formatter);
        boolean secondResult = cinema.cancelScreening(screening1, secondCancelTime);
        
        // Verify result and screening list
        assertFalse("Already canceled screening should not be canceled again", secondResult);
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_CancelPastScreeningAfterShowtime() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-01 14:00:00", formatter);
        cinema.assignScreening(film1, room1, screeningTime, LocalDateTime.parse("2024-11-01 10:00:00", formatter));
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        Screening screening1 = new Screening(film1, room1, screeningTime);
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-03 10:00:00", formatter);
        boolean result = cinema.cancelScreening(screening1, currentTime);
        
        // Verify result and screening list
        assertFalse("Past screening should not be canceled", result);
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().size());
    }
}