import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CR3Test {
    
    private Cinema cinema;
    private Film film1;
    private Film film2;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film();
        film1.setTitle("Inception");
        film2 = new Film();
        film2.setTitle("Interstellar");
    }
    
    /**
     * Test Case 1: "Add new film"
     * Setup:
     * 1. Create Cinema C1, Film F1
     * Action: add Film F1 to Cinema C1
     * Expected Output: true
     */
    @Test
    public void testCase1_addNewFilm() {
        // Setup: Create Cinema C1, Film F1
        // Action: add Film F1 to Cinema C1
        boolean result = cinema.addFilm(film1);
        
        // Expected Output: true
        assertTrue(result);
        assertTrue(cinema.getFilms().contains(film1));
    }
    
    /**
     * Test Case 2: "Add duplicate film"
     * Setup:
     * 1. Create Cinema C1
     * 2. Add Film F1 to Cinema C1 first
     * Action: add Film F1 to Cinema C1 again
     * Expected Output: false
     */
    @Test
    public void testCase2_addDuplicateFilm() {
        // Setup: Create Cinema C1
        // Add Film F1 to Cinema C1 first
        cinema.addFilm(film1);
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = cinema.addFilm(film1);
        
        // Expected Output: false
        assertFalse(result);
        assertEquals(1, cinema.getFilms().size());
    }
    
    /**
     * Test Case 3: "Add multiple unique films"
     * Setup:
     * 1. Create Cinema C1, Film F1, Film F2
     * 2. Add Film F1 to Cinema C1 (true)
     * Action: add Film F2 to Cinema C1 (true)
     * Expected Output: true
     */
    @Test
    public void testCase3_addMultipleUniqueFilms() {
        // Setup: Create Cinema C1, Film F1, Film F2
        // Add Film F1 to Cinema C1 (true)
        cinema.addFilm(film1);
        
        // Action: add Film F2 to Cinema C1 (true)
        boolean result = cinema.addFilm(film2);
        
        // Expected Output: true
        assertTrue(result);
        assertTrue(cinema.getFilms().contains(film1));
        assertTrue(cinema.getFilms().contains(film2));
        assertEquals(2, cinema.getFilms().size());
    }
    
    /**
     * Test Case 4: "Add multiple unique films"
     * Setup:
     * 1. Create Cinema C1
     * 2. Add Film F1 to Cinema C1 (true)
     * Action: add Film F2 to Cinema C1
     * Expected Output: true
     */
    @Test
    public void testCase4_addMultipleUniqueFilms() {
        // Setup: Create Cinema C1
        // Add Film F1 to Cinema C1 (true)
        cinema.addFilm(film1);
        
        // Action: add Film F2 to Cinema C1
        boolean result = cinema.addFilm(film2);
        
        // Expected Output: true
        assertTrue(result);
        assertTrue(cinema.getFilms().contains(film1));
        assertTrue(cinema.getFilms().contains(film2));
        assertEquals(2, cinema.getFilms().size());
    }
    
    /**
     * Test Case 5: "Add multiple unique films"
     * Setup:
     * 1. Create Cinema C1
     * 2. Add Film F1 to Cinema C1 (true)
     * 3. Remove Film F1 from Cinema C1 (true)
     * Action: add Film F1 to Cinema C1 again
     * Expected Output: true
     */
    @Test
    public void testCase5_addMultipleUniqueFilms() throws ParseException {
        // Setup: Create Cinema C1
        // Add Film F1 to Cinema C1 (true)
        cinema.addFilm(film1);
        
        // Remove Film F1 from Cinema C1 (true)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentTime = sdf.parse("2023-01-01 12:00:00");
        cinema.removeFilm(film1, currentTime);
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = cinema.addFilm(film1);
        
        // Expected Output: true
        assertTrue(result);
        assertTrue(cinema.getFilms().contains(film1));
    }
}