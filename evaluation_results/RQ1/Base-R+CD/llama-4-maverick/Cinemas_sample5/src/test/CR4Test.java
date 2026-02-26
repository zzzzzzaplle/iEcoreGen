import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR4Test {
    
    private Cinema cinema;
    private Film filmF1;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        filmF1 = new Film();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_removeFilmWithNoScreenings() throws ParseException {
        // Setup:
        // 1. Create Cinema C1 (done in setUp)
        // 2. Add Film F1 to C1
        cinema.addFilm(filmF1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: true
        assertTrue(result);
    }
    
    @Test
    public void testCase2_removeNonExistentFilm() throws ParseException {
        // Setup:
        // 1. Create Cinema C1 (done in setUp)
        // 2. Do not add Film F1
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: false (Film F1 do not exist in cinema)
        assertFalse(result);
    }
    
    @Test
    public void testCase3_removeFilmWithFutureScreening() throws ParseException {
        // Setup:
        // 1. Create Cinema C1 (done in setUp)
        // 2. Add Film F1 to C1
        cinema.addFilm(filmF1);
        
        // 3. Assign screening for F1 at "2024-12-02 15:00:00" (the screening time)
        Room room = new Room();
        cinema.addRoom(room);
        
        Screening screening = new Screening();
        Date screeningTime = dateFormat.parse("2024-12-02 15:00:00");
        screening.setTime(screeningTime);
        
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        cinema.assignScreening(filmF1, room, screening, currentTime);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: true, and there is no screening in the cinema.
        assertTrue(result);
        assertEquals(0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_removeFilmWithPastScreening() throws ParseException {
        // Setup:
        // 1. Create Cinema C1 (done in setUp)
        // 2. Add Film F1 to C1
        cinema.addFilm(filmF1);
        
        // 3. Schedule screening for F1 at "2024-11-30 18:00:00"
        Room room = new Room();
        cinema.addRoom(room);
        
        Screening screening = new Screening();
        Date screeningTime = dateFormat.parse("2024-11-30 18:00:00");
        screening.setTime(screeningTime);
        
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        cinema.assignScreening(filmF1, room, screening, new Date(currentTime.getTime() - 24*60*60*1000)); // Assign before current time
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: true, and there is 1 screening in the cinema.
        assertTrue(result);
        assertEquals(1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_removeFilmWithCurrentTimeScreening() throws ParseException {
        // Setup:
        // 1. Create Cinema C1 (done in setUp)
        // 2. Add Film F1 to C1
        cinema.addFilm(filmF1);
        
        // 3. Schedule screening for F1 at "2024-12-01 10:00:00"
        Room room = new Room();
        cinema.addRoom(room);
        
        Screening screening = new Screening();
        Date screeningTime = dateFormat.parse("2024-12-01 10:00:00");
        screening.setTime(screeningTime);
        
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        cinema.assignScreening(filmF1, room, screening, new Date(currentTime.getTime() - 60*1000)); // Assign before current time
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: true, and there is no screening in the cinema.
        assertTrue(result);
        assertEquals(0, cinema.getScreenings().size());
    }
}