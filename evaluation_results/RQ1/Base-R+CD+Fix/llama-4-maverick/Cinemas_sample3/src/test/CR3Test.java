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
        cinema = new Cinema();
        film1 = new Film();
        film2 = new Film();
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
        assertTrue("Film should be in cinema's film list", C1.getFilms().contains(F1));
    }
    
    @Test
    public void testCase2_AddDuplicateFilm() {
        // Setup: Create Cinema C1 and add Film F1 first
        Cinema C1 = cinema;
        Film F1 = film1;
        C1.addFilm(F1); // First addition
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = C1.addFilm(F1);
        
        // Expected Output: false
        assertFalse("Adding duplicate film should return false", result);
        assertEquals("Film list should contain only one instance", 1, C1.getFilms().size());
    }
    
    @Test
    public void testCase3_AddMultipleUniqueFilms() {
        // Setup: Create Cinema C1, Film F1, Film F2
        Cinema C1 = cinema;
        Film F1 = film1;
        Film F2 = film2;
        
        // Add Film F1 to Cinema C1 (true)
        boolean result1 = C1.addFilm(F1);
        assertTrue("First film addition should succeed", result1);
        
        // Action: add Film F2 to Cinema C1 (true)
        boolean result2 = C1.addFilm(F2);
        
        // Expected Output: true
        assertTrue("Second unique film addition should return true", result2);
        assertEquals("Cinema should contain 2 films", 2, C1.getFilms().size());
        assertTrue("Cinema should contain F1", C1.getFilms().contains(F1));
        assertTrue("Cinema should contain F2", C1.getFilms().contains(F2));
    }
    
    @Test
    public void testCase4_AddMultipleUniqueFilms() {
        // Setup: Create Cinema C1 and add Film F1 (true)
        Cinema C1 = cinema;
        Film F1 = film1;
        Film F2 = film2;
        
        C1.addFilm(F1); // First addition
        
        // Action: add Film F2 to Cinema C1
        boolean result = C1.addFilm(F2);
        
        // Expected Output: true
        assertTrue("Adding second unique film should return true", result);
        assertEquals("Cinema should contain 2 films", 2, C1.getFilms().size());
    }
    
    @Test
    public void testCase5_AddMultipleUniqueFilms() {
        // Setup: Create Cinema C1, add Film F1 (true), then remove Film F1 (true)
        Cinema C1 = cinema;
        Film F1 = film1;
        Date currentTime = new Date();
        
        C1.addFilm(F1); // Add film first
        boolean removeResult = C1.removeFilm(F1, currentTime); // Remove film
        
        assertTrue("Removing film should return true", removeResult);
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = C1.addFilm(F1);
        
        // Expected Output: true
        assertTrue("Adding previously removed film should return true", result);
        assertTrue("Film should be in cinema's film list", C1.getFilms().contains(F1));
    }
}