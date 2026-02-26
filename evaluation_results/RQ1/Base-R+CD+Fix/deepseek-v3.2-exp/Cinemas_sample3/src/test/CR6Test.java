import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;

public class CR6Test {
    
    private Cinema cinema;
    private Film film1;
    private Room room1;
    private Screening screening1;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film();
        room1 = new Room();
        screening1 = new Screening();
    }
    
    @Test
    public void testCase1_CancelFutureScreening() throws ParseException {
        // Setup: Create Cinema C1, Add Film1, Add Room1, Assign Screening1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-10-03 12:00:00", "2024-10-01 12:00:00");
        
        // Action: cancel Screening1 at "2024-10-02 12:00:00"
        boolean result = cinema.cancelScreening(screening1, "2024-10-02 12:00:00");
        
        // Expected Output: true, there is no screening in the cinema C1
        assertTrue(result);
        assertEquals(0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_CancelNonExistentScreening() throws ParseException {
        // Setup: Create Cinema C1, Add Film1, Add Room1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Create Screening2 (not added to cinema)
        Screening screening2 = new Screening();
        
        // Action: cancel Screening2 at current time "2024-10-03 12:05:00"
        boolean result = cinema.cancelScreening(screening2, "2024-10-03 12:05:00");
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase3_CancelAtExactScreeningTimeBoundary() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1, Add Room R1, Assign screening S1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-12-03 10:00:00", "2024-12-01 10:00:00");
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        boolean result = cinema.cancelScreening(screening1, "2024-12-03 10:00:00");
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse(result);
        assertEquals(1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_CancelAlreadyCancelledScreening() throws ParseException {
        // Setup: Create Cinema C1, Add Film1, Add Room1, Assign Screening1 then cancel it
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-10-03 12:00:00", "2024-10-01 12:00:00");
        cinema.cancelScreening(screening1, "2024-10-02 12:00:00"); // First cancellation
        
        // Action: cancel Screening1 at current time "2024-10-02 12:05:00"
        boolean result = cinema.cancelScreening(screening1, "2024-10-02 12:05:00");
        
        // Expected Output: false, there is no screening in the cinema
        assertFalse(result);
        assertEquals(0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_CancelPastScreeningAfterShowtime() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1, Add Room R1, Assign screening S1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-12-01 14:00:00", "2024-11-30 10:00:00");
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        boolean result = cinema.cancelScreening(screening1, "2024-12-03 10:00:00");
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse(result);
        assertEquals(1, cinema.getScreenings().size());
    }
}