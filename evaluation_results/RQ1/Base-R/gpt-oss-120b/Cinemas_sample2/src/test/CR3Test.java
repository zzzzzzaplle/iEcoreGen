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
        film1 = new Film();
        film1.setTitle("The Matrix");
        film1.setDurationMinutes(136);
        
        film2 = new Film();
        film2.setTitle("Inception");
        film2.setDurationMinutes(148);
    }
    
    @Test
    public void testCase1_AddNewFilm() {
        // Setup: Create Cinema C1, Film F1
        // Action: add Film F1 to Cinema C1
        boolean result = cinema.addFilm(film1);
        
        // Expected Output: true
        assertTrue("Adding new film should return true", result);
        assertTrue("Film should be added to cinema", cinema.getFilms().containsKey("The Matrix"));
    }
    
    @Test
    public void testCase2_AddDuplicateFilm() {
        // Setup: Create Cinema C1, Add Film F1 to Cinema C1 first
        cinema.addFilm(film1);
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = cinema.addFilm(film1);
        
        // Expected Output: false
        assertFalse("Adding duplicate film should return false", result);
        assertEquals("Cinema should still contain only one film", 1, cinema.getFilms().size());
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
        assertEquals("Cinema should contain both films", 2, cinema.getFilms().size());
        assertTrue("Cinema should contain film1", cinema.getFilms().containsKey("The Matrix"));
        assertTrue("Cinema should contain film2", cinema.getFilms().containsKey("Inception"));
    }
    
    @Test
    public void testCase4_AddMultipleUniqueFilmsAlternative() {
        // Setup: Create Cinema C1
        // Add Film F1 to Cinema C1 (true)
        cinema.addFilm(film1);
        
        // Action: add Film F2 to Cinema C1
        boolean result = cinema.addFilm(film2);
        
        // Expected Output: true
        assertTrue("Adding second unique film should return true", result);
        assertEquals("Cinema should contain both films", 2, cinema.getFilms().size());
    }
    
    @Test
    public void testCase5_AddMultipleUniqueFilmsAfterRemoval() {
        // Setup: Create Cinema C1
        // Add Film F1 to Cinema C1 (true)
        cinema.addFilm(film1);
        
        // Remove Film F1 from Cinema C1 (true)
        String currentTime = "2024-01-01 10:00:00";
        boolean removeResult = cinema.removeFilm("The Matrix", currentTime);
        assertTrue("Removing film should return true", removeResult);
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = cinema.addFilm(film1);
        
        // Expected Output: true
        assertTrue("Adding film after removal should return true", result);
        assertTrue("Film should be re-added to cinema", cinema.getFilms().containsKey("The Matrix"));
    }
}