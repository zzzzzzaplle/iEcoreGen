import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CR3Test {
    private Cinema cinema;
    private Film film1;
    private Film film2;
    private DateTimeFormatter formatter;

    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film("Film1");
        film2 = new Film("Film2");
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    public void testCase1_AddNewFilm() {
        // Setup: Create Cinema C1, Film F1
        Cinema C1 = cinema;
        Film F1 = film1;
        
        // Action: add Film F1 to Cinema C1
        boolean result = C1.addFilm(F1);
        
        // Expected Output: true
        assertTrue("Adding new film should return true", result);
        assertTrue("Film should be added to cinema's film list", C1.getFilms().contains(F1));
    }

    @Test
    public void testCase2_AddDuplicateFilm() {
        // Setup: Create Cinema C1 and add Film F1 first
        Cinema C1 = cinema;
        Film F1 = film1;
        C1.addFilm(F1);
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = C1.addFilm(F1);
        
        // Expected Output: false
        assertFalse("Adding duplicate film should return false", result);
        assertEquals("Film list should contain only one film", 1, C1.getFilms().size());
    }

    @Test
    public void testCase3_AddMultipleUniqueFilms() {
        // Setup: Create Cinema C1, Film F1, Film F2 and add Film F1 first
        Cinema C1 = cinema;
        Film F1 = film1;
        Film F2 = film2;
        C1.addFilm(F1);
        
        // Action: add Film F2 to Cinema C1
        boolean result = C1.addFilm(F2);
        
        // Expected Output: true
        assertTrue("Adding second unique film should return true", result);
        assertTrue("Film F1 should be in cinema's film list", C1.getFilms().contains(F1));
        assertTrue("Film F2 should be in cinema's film list", C1.getFilms().contains(F2));
        assertEquals("Film list should contain 2 films", 2, C1.getFilms().size());
    }

    @Test
    public void testCase4_AddMultipleUniqueFilmsAlternative() {
        // Setup: Create Cinema C1 and add Film F1 first
        Cinema C1 = cinema;
        Film F1 = film1;
        Film F2 = film2;
        C1.addFilm(F1);
        
        // Action: add Film F2 to Cinema C1
        boolean result = C1.addFilm(F2);
        
        // Expected Output: true
        assertTrue("Adding second unique film should return true", result);
        assertTrue("Film F1 should be in cinema's film list", C1.getFilms().contains(F1));
        assertTrue("Film F2 should be in cinema's film list", C1.getFilms().contains(F2));
        assertEquals("Film list should contain 2 films", 2, C1.getFilms().size());
    }

    @Test
    public void testCase5_AddFilmAfterRemoval() {
        // Setup: Create Cinema C1, add Film F1, then remove it
        Cinema C1 = cinema;
        Film F1 = film1;
        C1.addFilm(F1);
        
        // Remove Film F1 from Cinema C1
        LocalDateTime currentTime = LocalDateTime.parse("2024-01-01 12:00:00", formatter);
        C1.removeFilm(F1, currentTime);
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = C1.addFilm(F1);
        
        // Expected Output: true
        assertTrue("Adding film after removal should return true", result);
        assertTrue("Film should be added back to cinema's film list", C1.getFilms().contains(F1));
        assertEquals("Film list should contain 1 film", 1, C1.getFilms().size());
    }
}