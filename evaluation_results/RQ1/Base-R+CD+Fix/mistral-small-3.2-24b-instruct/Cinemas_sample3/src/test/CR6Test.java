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
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film();
        room1 = new Room();
        screening1 = new Screening();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_cancelFutureScreening() throws Exception {
        // Setup: Create Cinema C1, Add Film1, Add Room1, Assign Screening1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        screening1.setFilm(film1);
        screening1.setRoom(room1);
        screening1.setTime(dateFormat.parse("2024-10-03 12:00:00"));
        
        Date currentTime = dateFormat.parse("2024-10-02 12:00:00");
        cinema.assignScreening(film1, room1, screening1, currentTime);
        
        // Action: cancel Screening1 at "2024-10-02 12:00:00"
        boolean result = cinema.cancelScreening(screening1, currentTime);
        
        // Expected Output: true, there is no screening in the cinema C1
        assertTrue("Screening should be canceled successfully", result);
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_cancelNonExistentScreening() throws Exception {
        // Setup: Create Cinema C1, Add Film1, Add Room1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Create a screening that is NOT added to cinema
        Screening screening2 = new Screening();
        screening2.setTime(dateFormat.parse("2024-10-02 12:05:00"));
        
        // Action: cancel Screening2 at current time "2024-10-03 12:05:00"
        Date currentTime = dateFormat.parse("2024-10-03 12:05:00");
        boolean result = cinema.cancelScreening(screening2, currentTime);
        
        // Expected Output: false
        assertFalse("Should return false for non-existent screening", result);
    }
    
    @Test
    public void testCase3_cancelAtExactScreeningTimeBoundary() throws Exception {
        // Setup: Create Cinema C1, Add Film F1, Add Room R1, Assign screening S1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        screening1.setFilm(film1);
        screening1.setRoom(room1);
        Date screeningTime = dateFormat.parse("2024-12-03 10:00:00");
        screening1.setTime(screeningTime);
        
        Date currentTime = dateFormat.parse("2024-12-03 10:00:00");
        cinema.assignScreening(film1, room1, screening1, currentTime);
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        boolean result = cinema.cancelScreening(screening1, currentTime);
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Should return false when current time equals screening time", result);
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_cancelAlreadyCancelledScreening() throws Exception {
        // Setup: Create Cinema C1, Add Film1, Add Room1, Assign and then cancel Screening1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        screening1.setFilm(film1);
        screening1.setRoom(room1);
        screening1.setTime(dateFormat.parse("2024-10-03 12:00:00"));
        
        Date firstCancelTime = dateFormat.parse("2024-10-02 12:00:00");
        cinema.assignScreening(film1, room1, screening1, firstCancelTime);
        cinema.cancelScreening(screening1, firstCancelTime);
        
        // Action: cancel Screening1 at current time "2024-10-02 12:05:00"
        Date secondCancelTime = dateFormat.parse("2024-10-02 12:05:00");
        boolean result = cinema.cancelScreening(screening1, secondCancelTime);
        
        // Expected Output: false, there is no screening in the cinema
        assertFalse("Should return false for already cancelled screening", result);
        assertEquals("There should be no screenings in cinema", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_cancelPastScreening() throws Exception {
        // Setup: Create Cinema C1, Add Film F1, Add Room R1, Assign screening S1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        screening1.setFilm(film1);
        screening1.setRoom(room1);
        screening1.setTime(dateFormat.parse("2024-12-01 14:00:00"));
        
        Date currentTime = dateFormat.parse("2024-12-03 10:00:00");
        cinema.assignScreening(film1, room1, screening1, currentTime);
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        boolean result = cinema.cancelScreening(screening1, currentTime);
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse("Should return false for past screening", result);
        assertEquals("There should be 1 screening in cinema", 1, cinema.getScreenings().size());
    }
}