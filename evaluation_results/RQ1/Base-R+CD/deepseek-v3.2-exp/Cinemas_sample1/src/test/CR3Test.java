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
        film1 = new Film();
        film2 = new Film();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_AddNewFilm() {
        // Setup: Create Cinema C1, Film F1
        Cinema c1 = new Cinema();
        Film f1 = new Film();
        
        // Action: add Film F1 to Cinema C1
        boolean result = c1.addFilm(f1);
        
        // Expected Output: true
        assertTrue("Adding new film should return true", result);
        assertTrue("Film should be added to cinema's film list", c1.getFilms().contains(f1));
    }
    
    @Test
    public void testCase2_AddDuplicateFilm() {
        // Setup: Create Cinema C1 and add Film F1 to Cinema C1 first
        Cinema c1 = new Cinema();
        Film f1 = new Film();
        c1.addFilm(f1); // First addition
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = c1.addFilm(f1);
        
        // Expected Output: false
        assertFalse("Adding duplicate film should return false", result);
        assertEquals("Film list should contain only one instance", 1, c1.getFilms().size());
    }
    
    @Test
    public void testCase3_AddMultipleUniqueFilms() {
        // Setup: Create Cinema C1, Film F1, Film F2
        // Add Film F1 to Cinema C1 (true)
        Cinema c1 = new Cinema();
        Film f1 = new Film();
        Film f2 = new Film();
        c1.addFilm(f1); // First film added
        
        // Action: add Film F2 to Cinema C1 (true)
        boolean result = c1.addFilm(f2);
        
        // Expected Output: true
        assertTrue("Adding second unique film should return true", result);
        assertTrue("Cinema should contain first film", c1.getFilms().contains(f1));
        assertTrue("Cinema should contain second film", c1.getFilms().contains(f2));
        assertEquals("Cinema should contain exactly 2 films", 2, c1.getFilms().size());
    }
    
    @Test
    public void testCase4_AddMultipleUniqueFilms_Variant() {
        // Setup: Create Cinema C1
        // Add Film F1 to Cinema C1 (true)
        Cinema c1 = new Cinema();
        Film f1 = new Film();
        Film f2 = new Film();
        c1.addFilm(f1); // First film added
        
        // Action: add Film F2 to Cinema C1
        boolean result = c1.addFilm(f2);
        
        // Expected Output: true
        assertTrue("Adding second unique film should return true", result);
        assertEquals("Cinema should contain exactly 2 films", 2, c1.getFilms().size());
    }
    
    @Test
    public void testCase5_AddFilmAfterRemoval() throws Exception {
        // Setup: Create Cinema C1
        // Add Film F1 to Cinema C1 (true)
        // Remove Film F1 from Cinema C1 (true)
        Cinema c1 = new Cinema();
        Film f1 = new Film();
        Date currentTime = dateFormat.parse("2024-01-01 12:00:00");
        
        c1.addFilm(f1); // Add film first
        c1.removeFilm(f1, currentTime); // Remove film
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = c1.addFilm(f1);
        
        // Expected Output: true
        assertTrue("Adding film after removal should return true", result);
        assertTrue("Film should be present in cinema after re-addition", c1.getFilms().contains(f1));
        assertEquals("Cinema should contain exactly 1 film", 1, c1.getFilms().size());
    }
}