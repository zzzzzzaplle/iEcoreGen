import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class CR4Test {

    private Cinema cinema;
    private Film filmF1;
    private Room roomR1;
    private Date currentTime;

    @Before
    public void setUp() throws ParseException {
        cinema = new Cinema();
        filmF1 = new Film("F1");
        roomR1 = new Room("R1");
        currentTime = DateUtil.parse("2024-12-01 10:00:00");
    }

    @Test
    public void testCase1_RemoveFilmWithNoScreenings() throws ParseException {
        // Setup: Create Cinema C1 and add Film F1 to C1
        cinema.addFilm(filmF1);

        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(filmF1, currentTime);

        // Expected Output: true
        assertTrue(result);
        
        // Verify film is removed
        assertFalse(cinema.getFilms().contains(filmF1));
    }

    @Test
    public void testCase2_RemoveNonExistentFilm() throws ParseException {
        // Setup: Create Cinema C1, do not add Film F1
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(filmF1, currentTime);

        // Expected Output: false (Film F1 does not exist in cinema)
        assertFalse(result);
    }

    @Test
    public void testCase3_RemoveFilmWithFutureScreening() throws ParseException {
        // Setup: Create Cinema C1, add Film F1 to C1, assign screening for F1 at "2024-12-02 15:00:00"
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        Date screeningTime = DateUtil.parse("2024-12-02 15:00:00");
        Screening screening = new Screening(screeningTime);
        
        cinema.assignScreening(filmF1, roomR1, screening, currentTime);

        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(filmF1, currentTime);

        // Expected Output: true, and there is no screening in the cinema
        assertTrue(result);
        assertTrue(cinema.getScreenings().isEmpty());
        assertFalse(cinema.getFilms().contains(filmF1));
    }

    @Test
    public void testCase4_RemoveFilmWithPastScreening() throws ParseException {
        // Setup: Create Cinema C1, add Film F1 to C1, schedule screening for F1 at "2024-11-30 18:00:00"
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        Date screeningTime = DateUtil.parse("2024-11-30 18:00:00");
        Screening screening = new Screening(screeningTime);
        
        cinema.assignScreening(filmF1, roomR1, screening, currentTime);

        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(filmF1, currentTime);

        // Expected Output: true, and there is 1 screening in the cinema
        assertTrue(result);
        assertEquals(1, cinema.getScreenings().size());
        assertFalse(cinema.getFilms().contains(filmF1));
    }

    @Test
    public void testCase5_RemoveFilmWithCurrentTimeScreening() throws ParseException {
        // Setup: Create Cinema C1, add Film F1 to C1, schedule screening for F1 at "2024-12-01 10:00:00"
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        Date screeningTime = DateUtil.parse("2024-12-01 10:00:00");
        Screening screening = new Screening(screeningTime);
        
        cinema.assignScreening(filmF1, roomR1, screening, currentTime);

        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        boolean result = cinema.removeFilm(filmF1, currentTime);

        // Expected Output: true, and there is no screening in the cinema
        assertTrue(result);
        assertTrue(cinema.getScreenings().isEmpty());
        assertFalse(cinema.getFilms().contains(filmF1));
    }
}