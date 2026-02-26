import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    private Cinema cinema;
    private Film film1;
    private Film film2;
    
    @Before
    public void setUp() {
        // Initialize fresh cinema and films before each test
        cinema = new Cinema();
        film1 = new Film();
        film2 = new Film();
    }
    
    @Test
    public void testCase1_AddNewFilm() {
        // Setup: Create Cinema C1, Film F1
        // Action: add Film F1 to Cinema C1
        boolean result = cinema.addFilm(film1);
        
        // Expected Output: true
        assertTrue("Adding new film should return true", result);
        assertTrue("Film should be added to cinema's film list", cinema.getFilms().contains(film1));
    }
    
    @Test
    public void testCase2_AddDuplicateFilm() {
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
    public void testCase3_AddMultipleUniqueFilms() {
        // Setup:
        // 1. Create Cinema C1, Film F1, Film F2
        // 2. Add Film F1 to Cinema C1 (true)
        cinema.addFilm(film1);
        
        // Action: add Film F2 to Cinema C1 (true)
        boolean result = cinema.addFilm(film2);
        
        // Expected Output: true
        assertTrue("Adding second unique film should return true", result);
        assertTrue("Film F1 should still be in cinema", cinema.getFilms().contains(film1));
        assertTrue("Film F2 should be added to cinema", cinema.getFilms().contains(film2));
        assertEquals("Cinema should have 2 films", 2, cinema.getFilms().size());
    }
    
    @Test
    public void testCase4_AddMultipleUniqueFilms() {
        // Setup:
        // 1. Create Cinema C1
        // 2. Add Film F1 to Cinema C1 (true)
        cinema.addFilm(film1);
        
        // Action: add Film F2 to Cinema C1
        boolean result = cinema.addFilm(film2);
        
        // Expected Output: true
        assertTrue("Adding second unique film should return true", result);
        assertTrue("Film F1 should be in cinema", cinema.getFilms().contains(film1));
        assertTrue("Film F2 should be in cinema", cinema.getFilms().contains(film2));
        assertEquals("Cinema should have 2 films", 2, cinema.getFilms().size());
    }
    
    @Test
    public void testCase5_AddMultipleUniqueFilms() throws Exception {
        // Setup:
        // 1. Create Cinema C1
        // 2. Add Film F1 to Cinema C1 (true)
        cinema.addFilm(film1);
        
        // 3. Remove Film F1 from Cinema C1 (true)
        // Note: removeFilm requires a currentTime parameter
        Date currentTime = DateHelper.parseDate("2024-01-01 12:00:00");
        boolean removeResult = cinema.removeFilm(film1, currentTime);
        assertTrue("Removing film should return true", removeResult);
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = cinema.addFilm(film1);
        
        // Expected Output: true
        assertTrue("Adding previously removed film should return true", result);
        assertTrue("Film should be added back to cinema", cinema.getFilms().contains(film1));
        assertEquals("Cinema should have 1 film", 1, cinema.getFilms().size());
    }
}