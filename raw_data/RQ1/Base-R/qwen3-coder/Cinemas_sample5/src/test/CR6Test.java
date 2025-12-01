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
        
        // Add film and room to cinema for all tests
        cinema.addFilm(film1);
        cinema.addRoom(room1);
    }
    
    @Test
    public void testCase1_CancelFutureScreening() {
        // Setup
        LocalDateTime screeningTime = LocalDateTime.parse("2024-10-03 12:00:00", formatter);
        LocalDateTime currentTime = LocalDateTime.parse("2024-10-02 12:00:00", formatter);
        
        // Assign screening
        cinema.assignScreening(film1, room1, screeningTime, currentTime);
        
        // Action: cancel Screening1 at "2024-10-02 12:00:00"
        Screening screening1 = new Screening(film1, room1, screeningTime);
        boolean result = cinema.cancelScreening(screening1, currentTime);
        
        // Expected Output: true, there is no screening in the cinema C1.
        assertTrue("Screening should be canceled successfully", result);
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_CancelNonExistentScreening() {
        // Setup
        LocalDateTime currentTime = LocalDateTime.parse("2024-10-03 12:05:00", formatter);
        
        // Create a screening that is not assigned to the cinema
        LocalDateTime screeningTime = LocalDateTime.parse("2024-10-02 12:05:00", formatter);
        Screening screening2 = new Screening(film1, room1, screeningTime);
        
        // Action: cancel Screening2 at current time "2024-10-03 12:05:00"
        boolean result = cinema.cancelScreening(screening2, currentTime);
        
        // Expected Output: false
        assertFalse("Should return false for non-existent screening", result);
    }
    
    @Test
    public void testCase3_CancelAtExactScreeningTimeBoundary() {
        // Setup
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-03 10:00:00", formatter);
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-03 10:00:00", formatter);
        
        // Assign screening using a time before the screening time for setup
        LocalDateTime setupTime = LocalDateTime.parse("2024-12-02 10:00:00", formatter);
        cinema.assignScreening(film1, room1, screeningTime, setupTime);
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        Screening screening1 = new Screening(film1, room1, screeningTime);
        boolean result = cinema.cancelScreening(screening1, currentTime);
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Should return false when current time equals screening time", result);
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_CancelAlreadyCancelledScreening() {
        // Setup
        LocalDateTime screeningTime = LocalDateTime.parse("2024-10-03 12:00:00", formatter);
        LocalDateTime firstCancelTime = LocalDateTime.parse("2024-10-02 12:00:00", formatter);
        LocalDateTime secondCancelTime = LocalDateTime.parse("2024-10-02 12:05:00", formatter);
        
        // Assign and then cancel screening first time
        cinema.assignScreening(film1, room1, screeningTime, firstCancelTime);
        Screening screening1 = new Screening(film1, room1, screeningTime);
        cinema.cancelScreening(screening1, firstCancelTime);
        
        // Action: cancel Screening1 at current time "2024-10-02 12:05:00"
        boolean result = cinema.cancelScreening(screening1, secondCancelTime);
        
        // Expected Output: false, there is no screening in the cinema
        assertFalse("Should return false for already cancelled screening", result);
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_CancelPastScreeningAfterShowtime() {
        // Setup
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-01 14:00:00", formatter);
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-03 10:00:00", formatter);
        
        // Assign screening using a time before the screening time for setup
        LocalDateTime setupTime = LocalDateTime.parse("2024-11-30 10:00:00", formatter);
        cinema.assignScreening(film1, room1, screeningTime, setupTime);
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        Screening screening1 = new Screening(film1, room1, screeningTime);
        boolean result = cinema.cancelScreening(screening1, currentTime);
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Should return false for past screening", result);
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().size());
    }
}