import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR4Test {
    private Cinema cinema;
    private Film filmF1;
    private Room roomR1;
    private SimpleDateFormat dateFormat;

    @Before
    public void setUp() {
        cinema = new Cinema();
        filmF1 = new Film();
        roomR1 = new Room();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    public void testCase1_RemoveFilmWithNoScreenings() throws ParseException {
        // Setup:
        // 1. Create Cinema C1 (already created in setUp)
        // 2. Add Film F1 to C1
        cinema.addFilm(filmF1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: true
        assertTrue(result);
    }

    @Test
    public void testCase2_RemoveNonExistentFilm() throws ParseException {
        // Setup:
        // 1. Create Cinema C1 (already created in setUp)
        // 2. Do not add Film F1
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: false (Film F1 do not exist in cinema)
        assertFalse(result);
    }

    @Test
    public void testCase3_RemoveFilmWithFutureScreening() throws ParseException {
        // Setup:
        // 1. Create Cinema C1 (already created in setUp)
        // 2. Add Film F1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        // 3. Assign screening for F1 at "2024-12-02 15:00:00" (the screening time)
        Screening screening = new Screening();
        Date screeningTime = dateFormat.parse("2024-12-02 15:00:00");
        screening.setTime(screeningTime);
        screening.setFilm(filmF1);
        screening.setRoom(roomR1);
        cinema.assignScreening(filmF1, roomR1, screening, dateFormat.parse("2024-12-01 10:00:00"));
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: true, and there is no screening in the cinema.
        assertTrue(result);
        assertEquals(0, cinema.getScreenings().size());
    }

    @Test
    public void testCase4_RemoveFilmWithPastScreening() throws ParseException {
        // Setup:
        // 1. Create Cinema C1 (already created in setUp)
        // 2. Add Film F1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        // 3. Schedule screening for F1 at "2024-11-30 18:00:00"
        Screening screening = new Screening();
        Date screeningTime = dateFormat.parse("2024-11-30 18:00:00");
        screening.setTime(screeningTime);
        screening.setFilm(filmF1);
        screening.setRoom(roomR1);
        cinema.getScreenings().add(screening);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: true, and there is 1 screening in the cinema.
        assertTrue(result);
        assertEquals(1, cinema.getScreenings().size());
    }

    @Test
    public void testCase5_RemoveFilmWithCurrentTimeScreening() throws ParseException {
        // Setup:
        // 1. Create Cinema C1 (already created in setUp)
        // 2. Add Film F1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        // 3. Schedule screening for F1 at "2024-12-01 10:00:00"
        Screening screening = new Screening();
        Date screeningTime = dateFormat.parse("2024-12-01 10:00:00");
        screening.setTime(screeningTime);
        screening.setFilm(filmF1);
        screening.setRoom(roomR1);
        cinema.getScreenings().add(screening);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: true, and there is no screening in the cinema.
        assertTrue(result);
        assertEquals(0, cinema.getScreenings().size());
    }
}