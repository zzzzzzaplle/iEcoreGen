import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CR6Test {
    
    private Cinema cinema;
    private Film film1;
    private Room room1;
    private Screening screening1;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film("F1", "Film One");
        room1 = new Room("R1", 100, "Standard");
    }
    
    @Test
    public void testCase1_CancelFutureScreening() {
        // Setup: Create Cinema C1, Add Film1, Add Room1, Assign Screening1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-10-03 12:00:00", "2024-10-01 12:00:00");
        
        // Create the screening object for cancellation
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime screeningTime = LocalDateTime.parse("2024-10-03 12:00:00", formatter);
        screening1 = new Screening(film1, room1, screeningTime);
        
        // Action: cancel Screening1 at "2024-10-02 12:00:00"
        boolean result = cinema.cancelScreening(screening1, "2024-10-02 12:00:00");
        
        // Expected Output: true, there is no screening in the cinema C1
        assertTrue("Screening should be canceled successfully", result);
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_CancelNonExistentScreening() {
        // Setup: Create Cinema C1, Add Film1, Add Room1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Create a screening that was never added to cinema
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime screeningTime = LocalDateTime.parse("2024-10-02 12:05:00", formatter);
        Screening screening2 = new Screening(film1, room1, screeningTime);
        
        // Action: cancel Screening2 at current time "2024-10-03 12:05:00"
        boolean result = cinema.cancelScreening(screening2, "2024-10-03 12:05:00");
        
        // Expected Output: false
        assertFalse("Non-existent screening should not be canceled", result);
    }
    
    @Test
    public void testCase3_CancelAtExactScreeningTimeBoundary() {
        // Setup: Create Cinema C1, Add Film F1, Add Room R1, Assign screening S1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-12-03 10:00:00", "2024-12-01 10:00:00");
        
        // Create the screening object for cancellation
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-03 10:00:00", formatter);
        screening1 = new Screening(film1, room1, screeningTime);
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        boolean result = cinema.cancelScreening(screening1, "2024-12-03 10:00:00");
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Screening at exact time boundary should not be canceled", result);
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_CancelAlreadyCancelledScreening() {
        // Setup: Create Cinema C1, Add Film1, Add Room1, Assign Screening1 then cancel it
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-10-03 12:00:00", "2024-10-01 12:00:00");
        
        // Create the screening object for cancellation
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime screeningTime = LocalDateTime.parse("2024-10-03 12:00:00", formatter);
        screening1 = new Screening(film1, room1, screeningTime);
        
        // First cancellation (should succeed)
        boolean firstResult = cinema.cancelScreening(screening1, "2024-10-02 12:00:00");
        assertTrue("First cancellation should succeed", firstResult);
        
        // Action: cancel Screening1 again at current time "2024-10-02 12:05:00"
        boolean secondResult = cinema.cancelScreening(screening1, "2024-10-02 12:05:00");
        
        // Expected Output: false, there is no screening in the cinema
        assertFalse("Already cancelled screening should not be canceled again", secondResult);
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_CancelPastScreeningAfterShowtime() {
        // Setup: Create Cinema C1, Add Film F1, Add Room R1, Assign screening S1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-12-01 14:00:00", "2024-11-30 10:00:00");
        
        // Create the screening object for cancellation
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-01 14:00:00", formatter);
        screening1 = new Screening(film1, room1, screeningTime);
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        boolean result = cinema.cancelScreening(screening1, "2024-12-03 10:00:00");
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Past screening should not be canceled", result);
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().size());
    }
}