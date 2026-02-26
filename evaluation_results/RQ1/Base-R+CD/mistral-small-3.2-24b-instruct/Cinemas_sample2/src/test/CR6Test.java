import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR6Test {
    private Cinema cinema;
    private Film film1;
    private Room room1;
    private Screening screening1;
    private Screening screening2;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() throws ParseException {
        cinema = new Cinema();
        film1 = new Film();
        room1 = new Room();
        screening1 = new Screening();
        screening2 = new Screening();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Setup common test data
        cinema.addFilm(film1);
        cinema.addRoom(room1);
    }
    
    @Test
    public void testCase1_cancelFutureScreening() throws ParseException {
        // Setup: Assign Screening1(Film1, Room1, "2024-10-03 12:00:00")
        screening1.setTime(dateFormat.parse("2024-10-03 12:00:00"));
        cinema.assignScreening(film1, room1, screening1, dateFormat.parse("2024-10-01 00:00:00"));
        
        // Action: cancel Screening1 at "2024-10-02 12:00:00"
        boolean result = cinema.cancelScreening(screening1, dateFormat.parse("2024-10-02 12:00:00"));
        
        // Verify: true, there is no screening in the cinema C1
        assertTrue(result);
        assertFalse(cinema.getScreenings().contains(screening1));
        assertEquals(0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_cancelNonExistentScreening() throws ParseException {
        // Setup: Create Cinema C1, Screening2 (the screening time: "2024-10-02 12:05:00")
        // Screening2 is created but not assigned to the cinema
        screening2.setTime(dateFormat.parse("2024-10-02 12:05:00"));
        
        // Action: cancel Screening2 at current time "2024-10-03 12:05:00"
        boolean result = cinema.cancelScreening(screening2, dateFormat.parse("2024-10-03 12:05:00"));
        
        // Verify: false
        assertFalse(result);
        assertEquals(0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase3_cancelAtExactScreeningTimeBoundary() throws ParseException {
        // Setup: Assign screening S1 for F1 in R1 at "2024-12-03 10:00:00"
        screening1.setTime(dateFormat.parse("2024-12-03 10:00:00"));
        cinema.assignScreening(film1, room1, screening1, dateFormat.parse("2024-12-01 00:00:00"));
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        boolean result = cinema.cancelScreening(screening1, dateFormat.parse("2024-12-03 10:00:00"));
        
        // Verify: false, there is 1 screening in the cinema
        assertFalse(result);
        assertTrue(cinema.getScreenings().contains(screening1));
        assertEquals(1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_cancelAlreadyCancelledScreening() throws ParseException {
        // Setup: Assign Screening1(Film1, Room1, "2024-10-03 12:00:00") then cancel Screening1 at "2024-10-02 12:00:00" (true)
        screening1.setTime(dateFormat.parse("2024-10-03 12:00:00"));
        cinema.assignScreening(film1, room1, screening1, dateFormat.parse("2024-10-01 00:00:00"));
        cinema.cancelScreening(screening1, dateFormat.parse("2024-10-02 12:00:00"));
        
        // Action: cancel Screening1 at current time "2024-10-02 12:05:00"
        boolean result = cinema.cancelScreening(screening1, dateFormat.parse("2024-10-02 12:05:00"));
        
        // Verify: false, there is no screening in the cinema
        assertFalse(result);
        assertFalse(cinema.getScreenings().contains(screening1));
        assertEquals(0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_cancelPastScreeningAfterShowtime() throws ParseException {
        // Setup: Assign screening S1 for F1 in R1 at "2024-12-01 14:00:00"
        screening1.setTime(dateFormat.parse("2024-12-01 14:00:00"));
        cinema.assignScreening(film1, room1, screening1, dateFormat.parse("2024-11-01 00:00:00"));
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        boolean result = cinema.cancelScreening(screening1, dateFormat.parse("2024-12-03 10:00:00"));
        
        // Verify: false, there is 1 screening in the cinema
        assertFalse(result);
        assertTrue(cinema.getScreenings().contains(screening1));
        assertEquals(1, cinema.getScreenings().size());
    }
}