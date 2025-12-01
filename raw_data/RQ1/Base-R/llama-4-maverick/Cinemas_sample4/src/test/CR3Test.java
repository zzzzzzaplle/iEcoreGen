import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    private Cinema cinema;
    private Film film1;
    private Film film2;
    
    @Before
    public void setUp() {
        // Initialize cinema and films before each test
        cinema = new Cinema();
        film1 = new Film("F1");
        film2 = new Film("F2");
    }
    
    @Test
    public void testCase1_AddNewFilm() {
        // Test Case 1: "Add new film"
        // Setup: Create Cinema C1, Film F1
        // Action: add Film F1 to Cinema C1
        boolean result = cinema.addFilm(film1);
        
        // Expected Output: true
        assertTrue("Adding new film should return true", result);
        assertTrue("Cinema should contain the added film", cinema.addFilm(film1));
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
    }
    
    @Test
    public void testCase3_AddMultipleUniqueFilms() {
        // Test Case 3: "Add multiple unique films"
        // Setup:
        // 1. Create Cinema C1, Film F1, Film F2
        // 2. Add Film F1 to Cinema C1 (true)
        boolean firstResult = cinema.addFilm(film1);
        assertTrue("First film addition should succeed", firstResult);
        
        // Action: add Film F2 to Cinema C1 (true)
        boolean secondResult = cinema.addFilm(film2);
        
        // Expected Output: true
        assertTrue("Second unique film addition should return true", secondResult);
    }
    
    @Test
    public void testCase4_AddMultipleUniqueFilms() {
        // Test Case 4: "Add multiple unique films"
        // Setup:
        // 1. Create Cinema C1
        // 2. Add Film F1 to Cinema C1 (true)
        boolean firstResult = cinema.addFilm(film1);
        assertTrue("First film addition should succeed", firstResult);
        
        // Action: add Film F2 to Cinema C1
        boolean secondResult = cinema.addFilm(film2);
        
        // Expected Output: true
        assertTrue("Adding second unique film should return true", secondResult);
    }
    
    @Test
    public void testCase5_AddMultipleUniqueFilms() {
        // Test Case 5: "Add multiple unique films"
        // Setup:
        // 1. Create Cinema C1
        // 2. Add Film F1 to Cinema C1 (true)
        boolean addResult = cinema.addFilm(film1);
        assertTrue("Initial film addition should succeed", addResult);
        
        // 3. Remove Film F1 from Cinema C1 (true)
        boolean removeResult = cinema.removeFilm("F1", "2024-01-01 10:00:00");
        assertTrue("Film removal should succeed", removeResult);
        
        // Action: add Film F1 to Cinema C1 again
        boolean readdResult = cinema.addFilm(film1);
        
        // Expected Output: true
        assertTrue("Re-adding previously removed film should return true", readdResult);
    }
}