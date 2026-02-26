import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR3Test {
    
    private Cinema cinema;
    private Film film1;
    private Film film2;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film("The Matrix");
        film2 = new Film("Inception");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
    public void testCase2_AddDuplicateFilm() throws Exception {
        // Setup: Create Cinema C1 and add Film F1 to Cinema C1 first
        Cinema C1 = cinema;
        Film F1 = film1;
        C1.addFilm(F1);
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = C1.addFilm(F1);
        
        // Expected Output: false
        assertFalse("Adding duplicate film should return false", result);
        assertEquals("Film list should contain only one instance", 1, C1.getFilms().size());
    }
    
    @Test
    public void testCase3_AddMultipleUniqueFilms() throws Exception {
        // Setup: Create Cinema C1, Film F1, Film F2 and add Film F1 to Cinema C1 (true)
        Cinema C1 = cinema;
        Film F1 = film1;
        Film F2 = film2;
        boolean firstAddResult = C1.addFilm(F1);
        
        // Verify first addition was successful
        assertTrue("First film addition should succeed", firstAddResult);
        
        // Action: add Film F2 to Cinema C1 (true)
        boolean result = C1.addFilm(F2);
        
        // Expected Output: true
        assertTrue("Adding second unique film should return true", result);
        assertEquals("Cinema should contain 2 films", 2, C1.getFilms().size());
        assertTrue("Cinema should contain F1", C1.getFilms().contains(F1));
        assertTrue("Cinema should contain F2", C1.getFilms().contains(F2));
    }
    
    @Test
    public void testCase4_AddMultipleUniqueFilmsAlternative() throws Exception {
        // Setup: Create Cinema C1 and add Film F1 to Cinema C1 (true)
        Cinema C1 = cinema;
        Film F1 = film1;
        Film F2 = film2;
        boolean firstAddResult = C1.addFilm(F1);
        
        // Verify first addition was successful
        assertTrue("First film addition should succeed", firstAddResult);
        
        // Action: add Film F2 to Cinema C1
        boolean result = C1.addFilm(F2);
        
        // Expected Output: true
        assertTrue("Adding second unique film should return true", result);
        assertEquals("Cinema should contain 2 films", 2, C1.getFilms().size());
    }
    
    @Test
    public void testCase5_AddFilmAfterRemoval() throws Exception {
        // Setup: Create Cinema C1, add Film F1 to Cinema C1 (true),
        // and remove Film F1 from Cinema C1 (true)
        Cinema C1 = cinema;
        Film F1 = film1;
        
        // Add film
        boolean addResult = C1.addFilm(F1);
        assertTrue("First film addition should succeed", addResult);
        
        // Remove film (using current time in the past)
        Date currentTime = dateFormat.parse("2023-01-01 10:00:00");
        boolean removeResult = C1.removeFilm(F1, currentTime);
        assertTrue("Film removal should succeed", removeResult);
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = C1.addFilm(F1);
        
        // Expected Output: true
        assertTrue("Adding film after removal should return true", result);
        assertTrue("Film should be in cinema's film list", C1.getFilms().contains(F1));
        assertEquals("Cinema should contain 1 film", 1, C1.getFilms().size());
    }
}