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
        // 4. Assign Screening1(Film1, Room1, "2024-10-03 12:00:00")
        Date screeningTime = dateFormat.parse("2024-10-03 12:00:00");
        Screening screening1 = new Screening();
        screening1.setTime(screeningTime);
        cinema.assignScreening(film1, room1, screening1, dateFormat.parse("2024-10-02 12:00:00"));
        
        // Action: cancel Screening1 at "2024-10-02 12:00:00"
        Date currentTime = dateFormat.parse("2024-10-02 12:00:00");
        boolean result = cinema.cancelScreening(screening1, currentTime);
        
        // Expected Output: true, there is no screening in the cinema C1.
        assertTrue(result);
        assertEquals(0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_CancelNonExistentScreening() throws ParseException {
        // Setup
        // 1. Create Cinema C1, Screening2 (the screening time: "2024-10-02 12:05:00")
        Screening screening2 = new Screening();
        Date screeningTime = dateFormat.parse("2024-10-02 12:05:00");
        screening2.setTime(screeningTime);
        // 2. Add Film1
        cinema.addFilm(film1);
        // 3. Add Room1
        cinema.addRoom(room1);
        
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
        // 4. Assign screening S1 for F1 in R1 at "2024-12-03 10:00:00"
        Date screeningTime = dateFormat.parse("2024-12-03 10:00:00");
        Screening screening1 = new Screening();
        screening1.setTime(screeningTime);
        cinema.assignScreening(film1, room1, screening1, new Date()); // Using current time for assignment
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-03 10:00:00");
        boolean result = cinema.cancelScreening(screening1, currentTime);
        
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
        // 4. Assign Screening1(Film1, Room1, "2024-10-03 12:00:00") then cancel Screening1 at "2024-10-02 12:00:00" (true)
        Date screeningTime = dateFormat.parse("2024-10-03 12:00:00");
        Screening screening1 = new Screening();
        screening1.setTime(screeningTime);
        cinema.assignScreening(film1, room1, screening1, dateFormat.parse("2024-10-02 11:00:00"));
        
        Date currentTime1 = dateFormat.parse("2024-10-02 12:00:00");
        boolean firstCancelResult = cinema.cancelScreening(screening1, currentTime1);
        assertTrue(firstCancelResult); // First cancellation should succeed
        
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
        // 4. Assign screening S1 for F1 in R1 at "2024-12-01 14:00:00"
        Date screeningTime = dateFormat.parse("2024-12-01 14:00:00");
        Screening screening1 = new Screening();
        screening1.setTime(screeningTime);
        cinema.assignScreening(film1, room1, screening1, dateFormat.parse("2024-12-01 10:00:00"));
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-03 10:00:00");
        boolean result = cinema.cancelScreening(screening1, currentTime);
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse(result);
        assertEquals(1, cinema.getScreenings().size());
    }
}