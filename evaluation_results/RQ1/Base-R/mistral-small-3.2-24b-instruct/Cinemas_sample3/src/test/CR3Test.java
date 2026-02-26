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
        film1 = new Film("F1");
        film2 = new Film("F2");
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_AddNewFilm() {
        // Setup: Create Cinema C1, Film F1
        // Action: add Film F1 to Cinema C1
        boolean result = cinema.addFilm(film1);
        
        // Expected Output: true
        assertTrue("Adding new film should return true", result);
        assertTrue("Film should be in cinema's film list", cinema.getFilms().contains(film1));
        assertTrue("Film should have empty screenings list", cinema.getScreenings().containsKey(film1));
    }
    
    @Test
    public void testCase2_AddDuplicateFilm() {
        // Setup: Create Cinema C1 and add Film F1 to Cinema C1 first
        cinema.addFilm(film1);
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = cinema.addFilm(film1);
        
        // Expected Output: false
        assertFalse("Adding duplicate film should return false", result);
        assertEquals("Film list should contain only one film", 1, cinema.getFilms().size());
    }
    
    @Test
    public void testCase3_AddMultipleUniqueFilms() {
        // Setup: Create Cinema C1, Film F1, Film F2
        // Add Film F1 to Cinema C1 (true)
        cinema.addFilm(film1);
        
        // Action: add Film F2 to Cinema C1 (true)
        boolean result = cinema.addFilm(film2);
        
        // Expected Output: true
        assertTrue("Adding second unique film should return true", result);
        assertEquals("Film list should contain two films", 2, cinema.getFilms().size());
        assertTrue("Film F1 should be in cinema", cinema.getFilms().contains(film1));
        assertTrue("Film F2 should be in cinema", cinema.getFilms().contains(film2));
    }
    
    @Test
    public void testCase4_AddMultipleUniqueFilmsAlternative() {
        // Setup: Create Cinema C1 and add Film F1 to Cinema C1 (true)
        cinema.addFilm(film1);
        
        // Action: add Film F2 to Cinema C1
        boolean result = cinema.addFilm(film2);
        
        // Expected Output: true
        assertTrue("Adding second unique film should return true", result);
        assertEquals("Film list should contain two films", 2, cinema.getFilms().size());
    }
    
    @Test
    public void testCase5_AddFilmAfterRemoval() {
        // Setup: Create Cinema C1
        // Add Film F1 to Cinema C1 (true)
        cinema.addFilm(film1);
        
        // Remove Film F1 from Cinema C1 (true)
        LocalDateTime currentTime = LocalDateTime.parse("2024-01-01 10:00:00", formatter);
        cinema.removeFilm(film1, currentTime);
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = cinema.addFilm(film1);
        
        // Expected Output: true
        assertTrue("Adding film after removal should return true", result);
        assertTrue("Film should be back in cinema's film list", cinema.getFilms().contains(film1));
        assertTrue("Film should have screenings map entry", cinema.getScreenings().containsKey(film1));
    }
}