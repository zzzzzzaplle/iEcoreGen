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
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film();
        film1.setId("F1");
        film1.setTitle("Film 1");
        
        room1 = new Room();
        room1.setId("R1");
        room1.setName("Room 1");
    }
    
    @Test
    public void testCase1_CancelFutureScreening() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        LocalDateTime screeningTime = LocalDateTime.parse("2024-10-03 12:00:00", FORMATTER);
        LocalDateTime currentTimeForAssignment = LocalDateTime.parse("2024-10-01 12:00:00", FORMATTER);
        cinema.assignScreening(film1.getId(), room1.getId(), screeningTime, currentTimeForAssignment);
        
        // Action
        LocalDateTime currentTimeForCancellation = LocalDateTime.parse("2024-10-02 12:00:00", FORMATTER);
        boolean result = cinema.cancelScreening(screeningTime, currentTimeForCancellation);
        
        // Verify
        assertTrue("Screening should be cancelled successfully", result);
        List<Screening> screenings = cinema.getScreenings();
        assertEquals("There should be no screenings left in cinema", 0, screenings.size());
    }
    
    @Test
    public void testCase2_CancelNonExistentScreening() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Note: No screening is actually assigned - we're trying to cancel a non-existent screening
        
        // Action
        LocalDateTime screeningTime = LocalDateTime.parse("2024-10-02 12:05:00", FORMATTER);
        LocalDateTime currentTime = LocalDateTime.parse("2024-10-03 12:05:00", FORMATTER);
        boolean result = cinema.cancelScreening(screeningTime, currentTime);
        
        // Verify
        assertFalse("Should return false for non-existent screening", result);
    }
    
    @Test
    public void testCase3_CancelAtExactScreeningTimeBoundary() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-03 10:00:00", FORMATTER);
        LocalDateTime currentTimeForAssignment = LocalDateTime.parse("2024-12-01 10:00:00", FORMATTER);
        cinema.assignScreening(film1.getId(), room1.getId(), screeningTime, currentTimeForAssignment);
        
        // Action
        LocalDateTime currentTimeForCancellation = LocalDateTime.parse("2024-12-03 10:00:00", FORMATTER);
        boolean result = cinema.cancelScreening(screeningTime, currentTimeForCancellation);
        
        // Verify
        assertFalse("Should return false when current time equals screening time", result);
        List<Screening> screenings = cinema.getScreenings();
        assertEquals("There should still be 1 screening in cinema", 1, screenings.size());
    }
    
    @Test
    public void testCase4_CancelAlreadyCancelledScreening() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        LocalDateTime screeningTime = LocalDateTime.parse("2024-10-03 12:00:00", FORMATTER);
        LocalDateTime currentTimeForAssignment = LocalDateTime.parse("2024-10-01 12:00:00", FORMATTER);
        cinema.assignScreening(film1.getId(), room1.getId(), screeningTime, currentTimeForAssignment);
        
        // First cancellation (should succeed)
        LocalDateTime firstCancellationTime = LocalDateTime.parse("2024-10-02 12:00:00", FORMATTER);
        boolean firstResult = cinema.cancelScreening(screeningTime, firstCancellationTime);
        assertTrue("First cancellation should succeed", firstResult);
        
        // Action: Try to cancel the already cancelled screening
        LocalDateTime secondCancellationTime = LocalDateTime.parse("2024-10-02 12:05:00", FORMATTER);
        boolean result = cinema.cancelScreening(screeningTime, secondCancellationTime);
        
        // Verify
        assertFalse("Should return false for already cancelled screening", result);
        List<Screening> screenings = cinema.getScreenings();
        assertEquals("There should be no screenings in cinema", 0, screenings.size());
    }
    
    @Test
    public void testCase5_CancelPastScreeningAfterShowtime() {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-01 14:00:00", FORMATTER);
        LocalDateTime currentTimeForAssignment = LocalDateTime.parse("2024-11-01 14:00:00", FORMATTER);
        cinema.assignScreening(film1.getId(), room1.getId(), screeningTime, currentTimeForAssignment);
        
        // Action
        LocalDateTime currentTimeForCancellation = LocalDateTime.parse("2024-12-03 10:00:00", FORMATTER);
        boolean result = cinema.cancelScreening(screeningTime, currentTimeForCancellation);
        
        // Verify
        assertFalse("Should return false for past screening", result);
        List<Screening> screenings = cinema.getScreenings();
        assertEquals("There should still be 1 screening in cinema", 1, screenings.size());
    }
}