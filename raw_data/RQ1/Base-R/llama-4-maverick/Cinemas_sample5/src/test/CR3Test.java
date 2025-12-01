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
        film1 = new Film("Film F1");
        film2 = new Film("Film F2");
    }
    
    @Test
    public void testCase1_AddNewFilm() {
        // Test Case 1: Add new film
        // Setup: Create Cinema C1, Film F1
        boolean result = cinema.addFilm(film1);
        
        // Verify film was added successfully
        assertTrue("Adding new film should return true", result);
        assertTrue("Cinema should contain the added film", cinema.getFilms().contains(film1));
    }
    
    @Test
    public void testCase2_AddDuplicateFilm() {
        // Test Case 2: Add duplicate film
        // Setup: Create Cinema C1 and add Film F1 first
        cinema.addFilm(film1);
        
        // Action: Add Film F1 to Cinema C1 again
        boolean result = cinema.addFilm(film1);
        
        // Verify duplicate film addition returns false
        assertFalse("Adding duplicate film should return false", result);
        assertEquals("Cinema should contain only one instance of the film", 1, cinema.getFilms().size());
    }
    
    @Test
    public void testCase3_AddMultipleUniqueFilms() {
        // Test Case 3: Add multiple unique films
        // Setup: Create Cinema C1, Film F1, Film F2
        // Add Film F1 to Cinema C1 (true)
        boolean result1 = cinema.addFilm(film1);
        assertTrue("First film addition should return true", result1);
        
        // Action: add Film F2 to Cinema C1 (true)
        boolean result2 = cinema.addFilm(film2);
        
        // Verify second film addition returns true
        assertTrue("Adding second unique film should return true", result2);
        assertEquals("Cinema should contain both films", 2, cinema.getFilms().size());
        assertTrue("Cinema should contain film1", cinema.getFilms().contains(film1));
        assertTrue("Cinema should contain film2", cinema.getFilms().contains(film2));
    }
    
    @Test
    public void testCase4_AddMultipleUniqueFilmsAlternative() {
        // Test Case 4: Add multiple unique films (alternative scenario)
        // Setup: Create Cinema C1
        // Add Film F1 to Cinema C1 (true)
        boolean result1 = cinema.addFilm(film1);
        assertTrue("First film addition should return true", result1);
        
        // Action: add Film F2 to Cinema C1
        boolean result2 = cinema.addFilm(film2);
        
        // Verify second film addition returns true
        assertTrue("Adding second unique film should return true", result2);
        assertEquals("Cinema should contain both films", 2, cinema.getFilms().size());
    }
    
    @Test
    public void testCase5_AddFilmAfterRemoval() {
        // Test Case 5: Add film after removal
        // Setup: Create Cinema C1
        // Add Film F1 to Cinema C1 (true)
        boolean addResult1 = cinema.addFilm(film1);
        assertTrue("First film addition should return true", addResult1);
        
        // Remove Film F1 from Cinema C1 (true)
        boolean removeResult = cinema.removeFilm(film1, "2024-01-01 10:00:00");
        assertTrue("Film removal should return true", removeResult);
        
        // Action: add Film F1 to Cinema C1 again
        boolean addResult2 = cinema.addFilm(film1);
        
        // Verify film can be added again after removal
        assertTrue("Adding film after removal should return true", addResult2);
        assertTrue("Cinema should contain the re-added film", cinema.getFilms().contains(film1));
    }
}