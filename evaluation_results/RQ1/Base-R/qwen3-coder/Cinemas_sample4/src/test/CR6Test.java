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
        
        // Action
        Screening screening1 = new Screening(film1, room1, screeningTime);
        boolean result = cinema.cancelScreening(screening1, LocalDateTime.parse("2024-10-02 12:00:00", formatter));
        
        // Assert
        assertTrue("Screening should be canceled successfully", result);
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_CancelNonExistentScreening() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action - Screening2 was never added to cinema, only created locally
        Screening screening2 = new Screening(film1, room1, LocalDateTime.parse("2024-10-02 12:05:00", formatter));
        boolean result = cinema.cancelScreening(screening2, LocalDateTime.parse("2024-10-03 12:05:00", formatter));
        
        // Assert
        assertFalse("Should return false for non-existent screening", result);
    }
    
    @Test
    public void testCase3_CancelAtExactScreeningTimeBoundary() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-03 10:00:00", formatter);
        cinema.assignScreening(film1, room1, screeningTime, LocalDateTime.parse("2024-12-01 10:00:00", formatter));
        
        // Action
        Screening screening1 = new Screening(film1, room1, screeningTime);
        boolean result = cinema.cancelScreening(screening1, LocalDateTime.parse("2024-12-03 10:00:00", formatter));
        
        // Assert
        assertFalse("Should return false when current time equals screening time", result);
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_CancelAlreadyCancelledScreening() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        LocalDateTime screeningTime = LocalDateTime.parse("2024-10-03 12:00:00", formatter);
        cinema.assignScreening(film1, room1, screeningTime, LocalDateTime.parse("2024-10-01 12:00:00", formatter));
        
        // First cancellation (successful)
        Screening screening1 = new Screening(film1, room1, screeningTime);
        boolean firstResult = cinema.cancelScreening(screening1, LocalDateTime.parse("2024-10-02 12:00:00", formatter));
        assertTrue("First cancellation should succeed", firstResult);
        
        // Action - Try to cancel again
        boolean result = cinema.cancelScreening(screening1, LocalDateTime.parse("2024-10-02 12:05:00", formatter));
        
        // Assert
        assertFalse("Should return false for already cancelled screening", result);
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_CancelPastScreeningAfterShowtime() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-01 14:00:00", formatter);
        cinema.assignScreening(film1, room1, screeningTime, LocalDateTime.parse("2024-11-30 10:00:00", formatter));
        
        // Action
        Screening screening1 = new Screening(film1, room1, screeningTime);
        boolean result = cinema.cancelScreening(screening1, LocalDateTime.parse("2024-12-03 10:00:00", formatter));
        
        // Assert
        assertFalse("Should return false for past screening", result);
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().size());
    }
}