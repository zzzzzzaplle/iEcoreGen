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
        boolean assigned = cinema.assignScreening(film1, room1, "2024-10-03 12:00:00", "2024-10-01 12:00:00");
        assertTrue("Screening should be assigned successfully", assigned);
        
        // Action: cancel Screening1 at "2024-10-02 12:00:00"
        boolean result = cinema.cancelScreening(screening1, "2024-10-02 12:00:00");
        
        // Verify: Expected Output: true, there is no screening in the cinema C1
        assertTrue("Screening should be canceled successfully", result);
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_CancelNonExistentScreening() throws ParseException {
        // Setup: Create Cinema C1, Screening2 (the screening time: "2024-10-02 12:05:00")
        // Add Film1, Add Room1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Create a different screening that is not added to cinema
        Screening screening2 = new Screening();
        
        // Action: cancel Screening2 at current time "2024-10-03 12:05:00"
        boolean result = cinema.cancelScreening(screening2, "2024-10-03 12:05:00");
        
        // Verify: Expected Output: false
        assertFalse("Non-existent screening should not be canceled", result);
    }
    
    @Test
    public void testCase3_CancelAtExactScreeningTimeBoundary() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Assign screening S1 for F1 in R1 at "2024-12-03 10:00:00"
        boolean assigned = cinema.assignScreening(film1, room1, "2024-12-03 10:00:00", "2024-12-01 10:00:00");
        assertTrue("Screening should be assigned successfully", assigned);
        
        // Get the actual screening object that was added
        Screening s1 = cinema.getScreenings().get(0);
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        boolean result = cinema.cancelScreening(s1, "2024-12-03 10:00:00");
        
        // Verify: Expected Output: false, there is 1 screening in the cinema
        assertFalse("Screening at exact time boundary should not be canceled", result);
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_CancelAlreadyCancelledScreening() throws ParseException {
        // Setup: Create Cinema C1, Add Film1, Add Room1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Assign Screening1(Film1, Room1, "2024-10-03 12:00:00") 
        boolean assigned = cinema.assignScreening(film1, room1, "2024-10-03 12:00:00", "2024-10-01 12:00:00");
        assertTrue("Screening should be assigned successfully", assigned);
        
        // then cancel Screening1 at "2024-10-02 12:00:00" (true)
        boolean firstCancel = cinema.cancelScreening(screening1, "2024-10-02 12:00:00");
        assertTrue("First cancellation should succeed", firstCancel);
        
        // Action: cancel Screening1 at current time "2024-10-02 12:05:00"
        boolean result = cinema.cancelScreening(screening1, "2024-10-02 12:05:00");
        
        // Verify: Expected Output: false, there is no screening in the cinema
        assertFalse("Already canceled screening should not be canceled again", result);
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_CancelPastScreeningAfterShowtime() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Assign screening S1 for F1 in R1 at "2024-12-01 14:00:00"
        boolean assigned = cinema.assignScreening(film1, room1, "2024-12-01 14:00:00", "2024-11-30 10:00:00");
        assertTrue("Screening should be assigned successfully", assigned);
        
        // Get the actual screening object that was added
        Screening s1 = cinema.getScreenings().get(0);
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        boolean result = cinema.cancelScreening(s1, "2024-12-03 10:00:00");
        
        // Verify: Expected Output: false, there is 1 screening in the cinema
        assertFalse("Past screening should not be canceled", result);
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().size());
    }
}