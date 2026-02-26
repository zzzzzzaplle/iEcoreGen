import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;

public class CR3Test {
    private Cinema cinema;
    private Film film1;
    private Film film2;
    
    @Before
    public void setUp() {
        // Initialize common test objects before each test
        cinema = new Cinema();
        film1 = new Film();
        film2 = new Film();
    }
    
    @Test
    public void testCase1_addNewFilm() {
        // Test Case 1: "Add new film"
        // Setup: Create Cinema C1, Film F1
        // Action: add Film F1 to Cinema C1
        boolean result = cinema.addFilm(film1);
        
        // Expected Output: true
        assertTrue("Adding new film should return true", result);
        assertTrue("Film should be in cinema's film list", cinema.getFilms().contains(film1));
    }
    
    @Test
    public void testCase2_addDuplicateFilm() {
        // Test Case 2: "Add duplicate film"
        // Setup: 
        // 1. Create Cinema C1
        // 2. Add Film F1 to Cinema C1 first
        cinema.addFilm(film1);
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = cinema.addFilm(film1);
        
        // Expected Output: false
        assertFalse("Adding duplicate film should return false", result);
        assertEquals("Film list should contain only one instance", 1, cinema.getFilms().size());
    }
    
    @Test
    public void testCase3_addMultipleUniqueFilms() {
        // Test Case 3: "Add multiple unique films"
        // Setup:
        // 1. Create Cinema C1, Film F1, Film F2
        // 2. Add Film F1 to Cinema C1 (true)
        cinema.addFilm(film1);
        
        // Action: add Film F2 to Cinema C1 (true)
        boolean result = cinema.addFilm(film2);
        
        // Expected Output: true
        assertTrue("Adding second unique film should return true", result);
        assertTrue("Film F1 should be in cinema's film list", cinema.getFilms().contains(film1));
        assertTrue("Film F2 should be in cinema's film list", cinema.getFilms().contains(film2));
        assertEquals("Film list should contain both films", 2, cinema.getFilms().size());
    }
    
    @Test
    public void testCase4_addMultipleUniqueFilms_variant() {
        // Test Case 4: "Add multiple unique films"
        // Setup:
        // 1. Create Cinema C1
        // 2. Add Film F1 to Cinema C1 (true)
        cinema.addFilm(film1);
        
        // Action: add Film F2 to Cinema C1
        boolean result = cinema.addFilm(film2);
        
        // Expected Output: true
        assertTrue("Adding second unique film should return true", result);
        assertTrue("Film F1 should be in cinema's film list", cinema.getFilms().contains(film1));
        assertTrue("Film F2 should be in cinema's film list", cinema.getFilms().contains(film2));
        assertEquals("Film list should contain both films", 2, cinema.getFilms().size());
    }
    
    @Test
    public void testCase5_addFilmAfterRemoval() {
        // Test Case 5: "Add multiple unique films"
        // Setup:
        // 1. Create Cinema C1
        // 2. Add Film F1 to Cinema C1 (true)
        cinema.addFilm(film1);
        
        // 3. Remove Film F1 from Cinema C1 (true)
        Date currentTime = new Date();
        boolean removeResult = cinema.removeFilm(film1, currentTime);
        assertTrue("Removing film should return true", removeResult);
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = cinema.addFilm(film1);
        
        // Expected Output: true
        assertTrue("Adding film after removal should return true", result);
        assertTrue("Film should be in cinema's film list after re-adding", cinema.getFilms().contains(film1));
        assertEquals("Film list should contain the re-added film", 1, cinema.getFilms().size());
    }
}