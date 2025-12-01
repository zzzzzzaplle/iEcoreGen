import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR4Test {
    private Cinema cinema;
    private Film film;
    private Room room;
    private SimpleDateFormat dateFormat;

    @Before
    public void setUp() {
        cinema = new Cinema();
        film = new Film();
        room = new Room();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    public void testCase1_removeFilmWithNoScreenings() throws ParseException {
        // Setup: Create Cinema C1 and Add Film F1 to C1
        cinema.addFilm(film);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(film, currentTime);
        
        // Expected Output: true
        assertTrue(result);
    }

    @Test
    public void testCase2_removeNonExistentFilm() throws ParseException {
        // Setup: Create Cinema C1 and Do not add Film F1
        // (film is created but not added to cinema)
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(film, currentTime);
        
        // Expected Output: false (Film F1 do not exist in cinema)
        assertFalse(result);
    }

    @Test
    public void testCase3_removeFilmWithFutureScreening() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1 to C1, Assign screening for F1 at "2024-12-02 15:00:00"
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        Screening screening = new Screening();
        screening.setFilm(film);
        screening.setRoom(room);
        Date screeningTime = dateFormat.parse("2024-12-02 15:00:00");
        screening.setTime(screeningTime);
        
        cinema.assignScreening(film, room, screening, dateFormat.parse("2024-12-01 09:00:00"));
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(film, currentTime);
        
        // Expected Output: true, and there is no screening in the cinema.
        assertTrue(result);
        assertEquals(0, cinema.getScreenings().size());
    }

    @Test
    public void testCase4_removeFilmWithPastScreening() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1 to C1, Schedule screening for F1 at "2024-11-30 18:00:00"
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        Screening screening = new Screening();
        screening.setFilm(film);
        screening.setRoom(room);
        Date screeningTime = dateFormat.parse("2024-11-30 18:00:00");
        screening.setTime(screeningTime);
        
        cinema.assignScreening(film, room, screening, dateFormat.parse("2024-11-29 09:00:00"));
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(film, currentTime);
        
        // Expected Output: true, and there is 1 screening in the cinema.
        assertTrue(result);
        assertEquals(1, cinema.getScreenings().size());
    }

    @Test
    public void testCase5_removeFilmWithCurrentTimeScreening() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1 to C1, Schedule screening for F1 at "2024-12-01 10:00:00"
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        Screening screening = new Screening();
        screening.setFilm(film);
        screening.setRoom(room);
        Date screeningTime = dateFormat.parse("2024-12-01 10:00:00");
        screening.setTime(screeningTime);
        
        cinema.assignScreening(film, room, screening, dateFormat.parse("2024-12-01 09:00:00"));
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(film, currentTime);
        
        // Expected Output: true, and there is no screening in the cinema.
        assertTrue(result);
        assertEquals(0, cinema.getScreenings().size());
    }
}