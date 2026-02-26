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
        // Action: add Film F1 to Cinema C1
        boolean result = cinema.addFilm(film1);
        
        // Expected Output: true
        assertTrue(result);
    }
    
    @Test
    public void testCase2_AddDuplicateFilm() {
        // Setup: Create Cinema C1 and add Film F1 to Cinema C1 first
        cinema.addFilm(film1);
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = cinema.addFilm(film1);
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase3_AddMultipleUniqueFilms() {
        // Setup: Create Cinema C1, Film F1, Film F2 and add Film F1 to Cinema C1 (true)
        cinema.addFilm(film1);
        
        // Action: add Film F2 to Cinema C1 (true)
        boolean result = cinema.addFilm(film2);
        
        // Expected Output: true
        assertTrue(result);
    }
    
    @Test
    public void testCase4_AddMultipleUniqueFilms() {
        // Setup: Create Cinema C1 and add Film F1 to Cinema C1 (true)
        cinema.addFilm(film1);
        
        // Action: add Film F2 to Cinema C1
        boolean result = cinema.addFilm(film2);
        
        // Expected Output: true
        assertTrue(result);
    }
    
    @Test
    public void testCase5_AddMultipleUniqueFilms() {
        // Setup: Create Cinema C1, add Film F1 to Cinema C1 (true)
        cinema.addFilm(film1);
        
        // Remove Film F1 from Cinema C1 (true)
        Date currentTime = new Date();
        cinema.removeFilm(film1, currentTime);
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = cinema.addFilm(film1);
        
        // Expected Output: true
        assertTrue(result);
    }
}