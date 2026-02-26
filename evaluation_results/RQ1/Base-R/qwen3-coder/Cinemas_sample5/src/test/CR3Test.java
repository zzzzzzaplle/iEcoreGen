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
        // Initialize cinema and films before each test
        cinema = new Cinema();
        film1 = new Film("Film F1");
        film2 = new Film("Film F2");
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_AddNewFilm() {
        // Test Case 1: "Add new film"
        // Setup: Create Cinema C1, Film F1
        
        // Action: add Film F1 to Cinema C1
        boolean result = cinema.addFilm(film1);
        
        // Expected Output: true
        assertTrue("Adding new film should return true", result);
        assertTrue("Film should be in cinema's film list", cinema.getFilms().contains(film1));
    }
    
    @Test
    public void testCase2_AddDuplicateFilm() {
        // Test Case 2: "Add duplicate film"
        // Setup: 
        // 1. Create Cinema C1
        // 2. Add Film F1 to Cinema C1 first
        cinema.addFilm(film1);
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = cinema.addFilm(film1);
        
        // Expected Output: false
        assertFalse("Adding duplicate film should return false", result);
        assertEquals("Cinema should still contain only one film", 1, cinema.getFilms().size());
    }
    
    @Test
    public void testCase3_AddMultipleUniqueFilms() {
        // Test Case 3: "Add multiple unique films"
        // Setup:
        // 1. Create Cinema C1, Film F1, Film F2
        // 2. Add Film F1 to Cinema C1 (true)
        cinema.addFilm(film1);
        
        // Action: add Film F2 to Cinema C1 (true)
        boolean result = cinema.addFilm(film2);
        
        // Expected Output: true
        assertTrue("Adding second unique film should return true", result);
        assertEquals("Cinema should contain both films", 2, cinema.getFilms().size());
        assertTrue("Cinema should contain film1", cinema.getFilms().contains(film1));
        assertTrue("Cinema should contain film2", cinema.getFilms().contains(film2));
    }
    
    @Test
    public void testCase4_AddMultipleUniqueFilmsAlternative() {
        // Test Case 4: "Add multiple unique films"
        // Setup:
        // 1. Create Cinema C1
        // 2. Add Film F1 to Cinema C1 (true)
        cinema.addFilm(film1);
        
        // Action: add Film F2 to Cinema C1
        boolean result = cinema.addFilm(film2);
        
        // Expected Output: true
        assertTrue("Adding second unique film should return true", result);
        assertEquals("Cinema should contain both films", 2, cinema.getFilms().size());
    }
    
    @Test
    public void testCase5_AddFilmAfterRemoval() {
        // Test Case 5: "Add multiple unique films"
        // Setup:
        // 1. Create Cinema C1
        // 2. Add Film F1 to Cinema C1 (true)
        cinema.addFilm(film1);
        // 3. Remove Film F1 from Cinema C1 (true)
        LocalDateTime currentTime = LocalDateTime.now();
        cinema.removeFilm(film1, currentTime);
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = cinema.addFilm(film1);
        
        // Expected Output: true
        assertTrue("Adding film after removal should return true", result);
        assertTrue("Film should be in cinema's film list", cinema.getFilms().contains(film1));
        assertEquals("Cinema should contain one film", 1, cinema.getFilms().size());
    }
}