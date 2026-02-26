// IMPORTANT: Do not include package declaration
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Date;

public class CR6Test {
    
    private Cinema cinema;
    private Film film1;
    private Room room1;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film("Film1");
        room1 = new Room("Room1");
    }
    
    @Test
    public void testCase1_CancelFutureScreening() throws ParseException {
        // Setup:
        // 1. Create Cinema C1
        // 2. Add Film1
        cinema.addFilm(film1);
        // 3. Add Room1
        cinema.addRoom(room1);
        // 4. Assign Screening1(Film1, Room1, "2024-10-03 12:00:00")
        Date screeningTime = DateUtil.parse("2024-10-03 12:00:00");
        Screening screening1 = new Screening(screeningTime);
        Date currentTime = DateUtil.parse("2024-10-02 12:00:00");
        
        boolean assignResult = cinema.assignScreening(film1, room1, screening1, currentTime);
        assertTrue(assignResult);
        assertEquals(1, cinema.getScreenings().size());
        
        // Action: cancel Screening1 at "2024-10-02 12:00:00"
        boolean result = cinema.cancelScreening(screening1, currentTime);
        
        // Expected Output: true, there is no screening in the cinema C1.
        assertTrue(result);
        assertEquals(0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_CancelNonExistentScreening() throws ParseException {
        // Setup:
        // 1. Create Cinema C1, Screening2 (the screening time: "2024-10-02 12:05:00")
        Screening screening2 = new Screening(DateUtil.parse("2024-10-02 12:05:00"));
        // 2. Add Film1
        cinema.addFilm(film1);
        // 3. Add Room1
        cinema.addRoom(room1);
        // Action: cancel Screening2 at current time "2024-10-03 12:05:00"
        Date currentTime = DateUtil.parse("2024-10-03 12:05:00");
        boolean result = cinema.cancelScreening(screening2, currentTime);
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase3_CancelAtExactScreeningTimeBoundary() throws ParseException {
        // Setup:
        // 1. Create Cinema C1
        // 2. Add Film F1 to C1
        cinema.addFilm(film1);
        // 3. Add Room R1 to C1
        cinema.addRoom(room1);
        // 4. Assign screening S1 for F1 in R1 at "2024-12-03 10:00:00"
        Date screeningTime = DateUtil.parse("2024-12-03 10:00:00");
        Screening screening1 = new Screening(screeningTime);
        Date currentTime = DateUtil.parse("2024-12-02 10:00:00"); // Some time before screening
        
        boolean assignResult = cinema.assignScreening(film1, room1, screening1, currentTime);
        assertTrue(assignResult);
        assertEquals(1, cinema.getScreenings().size());
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        Date cancelTime = DateUtil.parse("2024-12-03 10:00:00");
        boolean result = cinema.cancelScreening(screening1, cancelTime);
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse(result);
        assertEquals(1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_CancelAlreadyCancelledScreening() throws ParseException {
        // Setup:
        // 1. Create Cinema C1
        // 2. Add Film1
        cinema.addFilm(film1);
        // 3. Add Room1
        cinema.addRoom(room1);
        // 4. Assign Screening1(Film1, Room1, "2024-10-03 12:00:00") then cancel Screening1 at "2024-10-02 12:00:00" (true)
        Date screeningTime = DateUtil.parse("2024-10-03 12:00:00");
        Screening screening1 = new Screening(screeningTime);
        Date currentTime1 = DateUtil.parse("2024-10-02 12:00:00");
        
        boolean assignResult = cinema.assignScreening(film1, room1, screening1, currentTime1);
        assertTrue(assignResult);
        assertEquals(1, cinema.getScreenings().size());
        
        boolean cancelResult = cinema.cancelScreening(screening1, currentTime1);
        assertTrue(cancelResult);
        assertEquals(0, cinema.getScreenings().size());
        
        // Action: cancel Screening1 at current time "2024-10-02 12:05:00"
        Date currentTime2 = DateUtil.parse("2024-10-02 12:05:00");
        boolean result = cinema.cancelScreening(screening1, currentTime2);
        
        // Expected Output: false, there is no screening in the cinema
        assertFalse(result);
        assertEquals(0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_CancelPastScreening() throws ParseException {
        // Setup:
        // 1. Create Cinema C1
        // 2. Add Film F1 to C1
        cinema.addFilm(film1);
        // 3. Add Room R1 to C1
        cinema.addRoom(room1);
        // 4. Assign screening S1 for F1 in R1 at "2024-12-01 14:00:00"
        Date screeningTime = DateUtil.parse("2024-12-01 14:00:00");
        Screening screening1 = new Screening(screeningTime);
        Date currentTime1 = DateUtil.parse("2024-11-30 14:00:00"); // Before screening time
        
        boolean assignResult = cinema.assignScreening(film1, room1, screening1, currentTime1);
        assertTrue(assignResult);
        assertEquals(1, cinema.getScreenings().size());
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        Date currentTime2 = DateUtil.parse("2024-12-03 10:00:00");
        boolean result = cinema.cancelScreening(screening1, currentTime2);
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse(result);
        assertEquals(1, cinema.getScreenings().size());
    }
}