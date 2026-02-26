import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR3Test {
    
    private Cinema cinema;
    private Film film1;
    private Film film2;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() throws Exception {
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
        assertTrue("Adding a new film should return true", result);
        assertTrue("Film should be added to cinema's film list", C1.getFilms().contains(F1));
    }
    
    @Test
    public void testCase2_AddDuplicateFilm() {
        // Setup: Create Cinema C1 and add Film F1 to Cinema C1 first
        Cinema C1 = cinema;
        Film F1 = film1;
        C1.addFilm(F1);
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = C1.addFilm(F1);
        
        // Expected Output: false
        assertFalse("Adding duplicate film should return false", result);
        assertEquals("Film list should contain only one instance of the film", 1, C1.getFilms().size());
    }
    
    @Test
    public void testCase3_AddMultipleUniqueFilms() {
        // Setup: Create Cinema C1, Film F1, Film F2. Add Film F1 to Cinema C1 (true)
        Cinema C1 = cinema;
        Film F1 = film1;
        Film F2 = film2;
        C1.addFilm(F1);
        
        // Action: add Film F2 to Cinema C1 (true)
        boolean result = C1.addFilm(F2);
        
        // Expected Output: true
        assertTrue("Adding a second unique film should return true", result);
        assertTrue("Film list should contain F1", C1.getFilms().contains(F1));
        assertTrue("Film list should contain F2", C1.getFilms().contains(F2));
        assertEquals("Film list should contain exactly 2 films", 2, C1.getFilms().size());
    }
    
    @Test
    public void testCase4_AddMultipleUniqueFilms() {
        // Setup: Create Cinema C1. Add Film F1 to Cinema C1 (true)
        Cinema C1 = cinema;
        Film F1 = film1;
        Film F2 = film2;
        C1.addFilm(F1);
        
        // Action: add Film F2 to Cinema C1
        boolean result = C1.addFilm(F2);
        
        // Expected Output: true
        assertTrue("Adding a second unique film should return true", result);
        assertTrue("Film list should contain F1", C1.getFilms().contains(F1));
        assertTrue("Film list should contain F2", C1.getFilms().contains(F2));
        assertEquals("Film list should contain exactly 2 films", 2, C1.getFilms().size());
    }
    
    @Test
    public void testCase5_AddMultipleUniqueFilms() throws Exception {
        // Setup: Create Cinema C1. Add Film F1 to Cinema C1 (true). Remove Film F1 from Cinema C1 (true)
        Cinema C1 = cinema;
        Film F1 = film1;
        C1.addFilm(F1);
        
        // Remove film with current time (any date works since we're removing regardless of screening time)
        Date currentTime = dateFormat.parse("2024-01-01 12:00:00");
        C1.removeFilm(F1, currentTime);
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = C1.addFilm(F1);
        
        // Expected Output: true
        assertTrue("Re-adding a previously removed film should return true", result);
        assertTrue("Film should be added back to cinema's film list", C1.getFilms().contains(F1));
        assertEquals("Film list should contain exactly 1 film", 1, C1.getFilms().size());
    }
}