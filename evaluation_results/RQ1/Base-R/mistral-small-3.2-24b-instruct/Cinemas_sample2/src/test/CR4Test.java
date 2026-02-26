import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class CR4Test {
    private Cinema cinema;
    private Film film1;
    private Room room1;
    private DateTimeFormatter formatter;

    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film("F1", "Action", 120);
        room1 = new Room("Room1", 100);
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        cinema.addRoom(room1);
    }

    @Test
    public void testCase1_removeFilmWithNoScreenings() {
        // Setup: Create Cinema C1 and add Film F1 to C1
        cinema.addFilm(film1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        boolean result = cinema.removeFilm(film1, currentTime);
        
        // Expected Output: true
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer be in cinema", cinema.getFilms().contains(film1));
    }

    @Test
    public void testCase2_removeNonExistentFilm() {
        // Setup: Create Cinema C1, do not add Film F1
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        boolean result = cinema.removeFilm(film1, currentTime);
        
        // Expected Output: false (Film F1 does not exist in cinema)
        assertFalse("Non-existent film should not be removed", result);
    }

    @Test
    public void testCase3_removeFilmWithFutureScreening() {
        // Setup: Create Cinema C1, add Film F1 to C1, assign screening for F1 at "2024-12-02 15:00:00"
        cinema.addFilm(film1);
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-02 15:00:00", formatter);
        LocalDateTime setupTime = LocalDateTime.parse("2024-11-01 10:00:00", formatter);
        cinema.assignScreening(film1, room1, screeningTime, setupTime);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        boolean result = cinema.removeFilm(film1, currentTime);
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer be in cinema", cinema.getFilms().contains(film1));
        assertEquals("There should be no screenings", 0, cinema.getScreenings().size());
    }

    @Test
    public void testCase4_removeFilmWithPastScreening() {
        // Setup: Create Cinema C1, add Film F1 to C1, schedule screening for F1 at "2024-11-30 18:00:00"
        cinema.addFilm(film1);
        LocalDateTime screeningTime = LocalDateTime.parse("2024-11-30 18:00:00", formatter);
        LocalDateTime setupTime = LocalDateTime.parse("2024-11-01 10:00:00", formatter);
        cinema.assignScreening(film1, room1, screeningTime, setupTime);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        boolean result = cinema.removeFilm(film1, currentTime);
        
        // Expected Output: true, and there is 1 screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer be in cinema", cinema.getFilms().contains(film1));
        assertEquals("Past screening should remain", 1, cinema.getScreenings().size());
        
        // Verify the remaining screening is the past one
        Map<LocalDateTime, Screening> screenings = cinema.getScreenings();
        Screening remainingScreening = screenings.get(screeningTime);
        assertNotNull("Past screening should exist", remainingScreening);
        assertEquals("Remaining screening should be for F1", film1, remainingScreening.getFilm());
    }

    @Test
    public void testCase5_removeFilmWithCurrentTimeScreening() {
        // Setup: Create Cinema C1, add Film F1 to C1, schedule screening for F1 at "2024-12-01 10:00:00"
        cinema.addFilm(film1);
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        LocalDateTime setupTime = LocalDateTime.parse("2024-11-01 10:00:00", formatter);
        cinema.assignScreening(film1, room1, screeningTime, setupTime);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        boolean result = cinema.removeFilm(film1, currentTime);
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer be in cinema", cinema.getFilms().contains(film1));
        assertEquals("Current-time screening should be removed", 0, cinema.getScreenings().size());
    }
}