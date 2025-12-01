import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    private Cinema cinema;
    private Film film1;
    private Film film2;
    
    @Before
    public void setUp() {
        // Initialize fresh objects before each test
        cinema = new Cinema();
        film1 = new Film();
        film2 = new Film();
    }
    
    @Test
    public void testCase1_AddNewFilm() {
        // Test Case 1: "Add new film"
        // Setup: Create Cinema C1, Film F1
        // Action: add Film F1 to Cinema C1
        // Expected Output: true
        
        boolean result = cinema.addFilm(film1);
        
        assertTrue("Adding a new film should return true", result);
        assertTrue("Film should be added to cinema's film list", cinema.getFilms().contains(film1));
        assertEquals("Cinema should have exactly 1 film", 1, cinema.getFilms().size());
    }
    
    @Test
    public void testCase2_AddDuplicateFilm() {
        // Test Case 2: "Add duplicate film"
        // Setup: 
        // 1. Create Cinema C1
        // 2. Add Film F1 to Cinema C1 first
        // Action: add Film F1 to Cinema C1 again
        // Expected Output: false
        
        // First addition
        cinema.addFilm(film1);
        
        // Second addition attempt
        boolean result = cinema.addFilm(film1);
        
        assertFalse("Adding duplicate film should return false", result);
        assertEquals("Cinema should still have only 1 film", 1, cinema.getFilms().size());
    }
    
    @Test
    public void testCase3_AddMultipleUniqueFilms() {
        // Test Case 3: "Add multiple unique films"
        // Setup:
        // 1. Create Cinema C1, Film F1, Film F2
        // 2. Add Film F1 to Cinema C1 (true)
        // Action: add Film F2 to Cinema C1 (true)
        // Expected Output: true
        
        // Add first film
        boolean result1 = cinema.addFilm(film1);
        assertTrue("First film addition should return true", result1);
        
        // Add second film
        boolean result2 = cinema.addFilm(film2);
        
        assertTrue("Adding second unique film should return true", result2);
        assertEquals("Cinema should have 2 films", 2, cinema.getFilms().size());
        assertTrue("Cinema should contain film1", cinema.getFilms().contains(film1));
        assertTrue("Cinema should contain film2", cinema.getFilms().contains(film2));
    }
    
    @Test
    public void testCase4_AddMultipleUniqueFilmsAlternative() {
        // Test Case 4: "Add multiple unique films"
        // Setup:
        // 1. Create Cinema C1
        // 2. Add Film F1 to Cinema C1 (true)
        // Action: add Film F2 to Cinema C1
        // Expected Output: true
        
        // Add first film
        cinema.addFilm(film1);
        
        // Add second film
        boolean result = cinema.addFilm(film2);
        
        assertTrue("Adding second unique film should return true", result);
        assertEquals("Cinema should have 2 films", 2, cinema.getFilms().size());
    }
    
    @Test
    public void testCase5_AddFilmAfterRemoval() {
        // Test Case 5: "Add multiple unique films"
        // Setup:
        // 1. Create Cinema C1
        // 2. Add Film F1 to Cinema C1 (true)
        // 3. Remove Film F1 from Cinema C1 (true)
        // Action: add Film F1 to Cinema C1 again
        // Expected Output: true
        
        // Add film first
        cinema.addFilm(film1);
        
        // Remove film (using a dummy current date since we need it for the method signature)
        java.util.Date currentDate = new java.util.Date();
        boolean removeResult = cinema.removeFilm(film1, currentDate);
        assertTrue("Removing film should return true", removeResult);
        
        // Add the same film again
        boolean addResult = cinema.addFilm(film1);
        
        assertTrue("Adding film after removal should return true", addResult);
        assertTrue("Film should be back in cinema's film list", cinema.getFilms().contains(film1));
        assertEquals("Cinema should have 1 film", 1, cinema.getFilms().size());
    }
}