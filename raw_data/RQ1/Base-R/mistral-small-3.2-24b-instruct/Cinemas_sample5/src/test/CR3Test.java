import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;

public class CR3Test {
    
    private Cinema cinema;
    private Film film1;
    private Film film2;
    
    @Before
    public void setUp() {
        // Initialize cinema and films before each test
        cinema = new Cinema();
        film1 = new Film();
        film1.setFilmId("F1");
        film2 = new Film();
        film2.setFilmId("F2");
    }
    
    @Test
    public void testCase1_AddNewFilm() {
        // Test Case 1: Add new film
        // Setup: Create Cinema C1, Film F1
        // Action: add Film F1 to Cinema C1
        // Expected Output: true
        
        boolean result = cinema.addFilm(film1);
        assertTrue("Adding new film should return true", result);
        assertTrue("Cinema should contain the added film", cinema.getFilms().contains(film1));
        assertTrue("Screenings map should contain the film", cinema.getScreenings().containsKey(film1));
    }
    
    @Test
    public void testCase2_AddDuplicateFilm() {
        // Test Case 2: Add duplicate film
        // Setup: 
        // 1. Create Cinema C1
        // 2. Add Film F1 to Cinema C1 first
        // Action: add Film F1 to Cinema C1 again
        // Expected Output: false
        
        cinema.addFilm(film1);
        boolean result = cinema.addFilm(film1);
        assertFalse("Adding duplicate film should return false", result);
        assertEquals("Cinema should contain only one film", 1, cinema.getFilms().size());
        assertEquals("Screenings map should contain only one film", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase3_AddMultipleUniqueFilms() {
        // Test Case 3: Add multiple unique films
        // Setup:
        // 1. Create Cinema C1, Film F1, Film F2
        // 2. Add Film F1 to Cinema C1 (true)
        // Action: add Film F2 to Cinema C1 (true)
        // Expected Output: true
        
        cinema.addFilm(film1);
        boolean result = cinema.addFilm(film2);
        assertTrue("Adding second unique film should return true", result);
        assertEquals("Cinema should contain 2 films", 2, cinema.getFilms().size());
        assertTrue("Cinema should contain film1", cinema.getFilms().contains(film1));
        assertTrue("Cinema should contain film2", cinema.getFilms().contains(film2));
        assertEquals("Screenings map should contain 2 films", 2, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_AddMultipleUniqueFilms() {
        // Test Case 4: Add multiple unique films
        // Setup:
        // 1. Create Cinema C1
        // 2. Add Film F1 to Cinema C1 (true)
        // Action: add Film F2 to Cinema C1
        // Expected Output: true
        
        cinema.addFilm(film1);
        boolean result = cinema.addFilm(film2);
        assertTrue("Adding second unique film should return true", result);
        assertEquals("Cinema should contain 2 films", 2, cinema.getFilms().size());
        assertTrue("Cinema should contain film1", cinema.getFilms().contains(film1));
        assertTrue("Cinema should contain film2", cinema.getFilms().contains(film2));
        assertEquals("Screenings map should contain 2 films", 2, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_AddMultipleUniqueFilms() throws ParseException {
        // Test Case 5: Add multiple unique films
        // Setup:
        // 1. Create Cinema C1
        // 2. Add Film F1 to Cinema C1 (true)
        // 3. Remove Film F1 from Cinema C1 (true)
        // Action: add Film F1 to Cinema C1 again
        // Expected Output: true
        
        cinema.addFilm(film1);
        boolean removeResult = cinema.removeFilm(film1, "2024-01-01 10:00:00");
        assertTrue("Removing film should return true", removeResult);
        
        boolean addResult = cinema.addFilm(film1);
        assertTrue("Re-adding previously removed film should return true", addResult);
        assertTrue("Cinema should contain the re-added film", cinema.getFilms().contains(film1));
        assertTrue("Screenings map should contain the re-added film", cinema.getScreenings().containsKey(film1));
    }
}