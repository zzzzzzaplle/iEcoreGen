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
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film();
        room1 = new Room();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CancelFutureScreening() throws ParseException {
        // Setup
        // 1. Create Cinema C1
        // 2. Add Film1
        cinema.addFilm(film1);
        // 3. Add Room1
        cinema.addRoom(room1);
        
        // Create Screening1 at "2024-10-03 12:00:00"
        Screening screening1 = new Screening();
        Date screeningTime = dateFormat.parse("2024-10-03 12:00:00");
        screening1.setTime(screeningTime);
        screening1.setFilm(film1);
        screening1.setRoom(room1);
        
        // Assign Screening1
        Date currentTime = dateFormat.parse("2024-10-02 12:00:00");
        cinema.assignScreening(film1, room1, screening1, currentTime);
        
        // Action: cancel Screening1 at "2024-10-02 12:00:00"
        boolean result = cinema.cancelScreening(screening1, currentTime);
        
        // Expected Output: true, there is no screening in the cinema C1
        assertTrue(result);
        assertEquals(0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_CancelNonExistentScreening() throws ParseException {
        // Setup
        // 1. Create Cinema C1
        // 2. Add Film1
        cinema.addFilm(film1);
        // 3. Add Room1
        cinema.addRoom(room1);
        
        // Create Screening2 (the screening time: "2024-10-02 12:05:00")
        Screening screening2 = new Screening();
        Date screeningTime = dateFormat.parse("2024-10-02 12:05:00");
        screening2.setTime(screeningTime);
        screening2.setFilm(film1);
        screening2.setRoom(room1);
        
        // Action: cancel Screening2 at current time "2024-10-03 12:05:00"
        Date currentTime = dateFormat.parse("2024-10-03 12:05:00");
        boolean result = cinema.cancelScreening(screening2, currentTime);
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase3_CancelAtExactScreeningTimeBoundary() throws ParseException {
        // Setup
        // 1. Create Cinema C1
        // 2. Add Film F1 to C1
        cinema.addFilm(film1);
        // 3. Add Room R1 to C1
        cinema.addRoom(room1);
        
        // Create screening S1 for F1 in R1 at "2024-12-03 10:00:00"
        Screening s1 = new Screening();
        Date screeningTime = dateFormat.parse("2024-12-03 10:00:00");
        s1.setTime(screeningTime);
        s1.setFilm(film1);
        s1.setRoom(room1);
        
        // Assign screening S1
        Date assignTime = dateFormat.parse("2024-12-03 09:00:00");
        cinema.assignScreening(film1, room1, s1, assignTime);
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-03 10:00:00");
        boolean result = cinema.cancelScreening(s1, currentTime);
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse(result);
        assertEquals(1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_CancelAlreadyCancelledScreening() throws ParseException {
        // Setup
        // 1. Create Cinema C1
        // 2. Add Film1
        cinema.addFilm(film1);
        // 3. Add Room1
        cinema.addRoom(room1);
        
        // Create Screening1 at "2024-10-03 12:00:00"
        Screening screening1 = new Screening();
        Date screeningTime = dateFormat.parse("2024-10-03 12:00:00");
        screening1.setTime(screeningTime);
        screening1.setFilm(film1);
        screening1.setRoom(room1);
        
        // Assign Screening1
        Date currentTime1 = dateFormat.parse("2024-10-02 12:00:00");
        cinema.assignScreening(film1, room1, screening1, currentTime1);
        
        // Cancel Screening1 at "2024-10-02 12:00:00"
        boolean firstCancelResult = cinema.cancelScreening(screening1, currentTime1);
        assertTrue(firstCancelResult);
        
        // Action: cancel Screening1 at current time "2024-10-02 12:05:00"
        Date currentTime2 = dateFormat.parse("2024-10-02 12:05:00");
        boolean result = cinema.cancelScreening(screening1, currentTime2);
        
        // Expected Output: false, there is no screening in the cinema
        assertFalse(result);
        assertEquals(0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_CancelPastScreeningAfterShowtime() throws ParseException {
        // Setup
        // 1. Create Cinema C1
        // 2. Add Film F1 to C1
        cinema.addFilm(film1);
        // 3. Add Room R1 to C1
        cinema.addRoom(room1);
        
        // Create screening S1 for F1 in R1 at "2024-12-01 14:00:00"
        Screening s1 = new Screening();
        Date screeningTime = dateFormat.parse("2024-12-01 14:00:00");
        s1.setTime(screeningTime);
        s1.setFilm(film1);
        s1.setRoom(room1);
        
        // Assign screening S1
        Date assignTime = dateFormat.parse("2024-12-01 13:00:00");
        cinema.assignScreening(film1, room1, s1, assignTime);
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-03 10:00:00");
        boolean result = cinema.cancelScreening(s1, currentTime);
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse(result);
        assertEquals(1, cinema.getScreenings().size());
    }
}