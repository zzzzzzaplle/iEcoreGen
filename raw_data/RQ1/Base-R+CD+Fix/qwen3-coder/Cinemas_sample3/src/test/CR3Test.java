import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR3Test {
    private Cinema cinema;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_AddNewFilm() throws Exception {
        // Setup: Create Cinema C1, Film F1
        Film film = new Film("F1");
        
        // Action: add Film F1 to Cinema C1
        boolean result = cinema.addFilm(film);
        
        // Expected Output: true
        assertTrue("Adding new film should return true", result);
    }
    
    @Test
    public void testCase2_AddDuplicateFilm() throws Exception {
        // Setup: Create Cinema C1 and add Film F1 first
        Film film = new Film("F1");
        cinema.addFilm(film);
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = cinema.addFilm(film);
        
        // Expected Output: false
        assertFalse("Adding duplicate film should return false", result);
    }
    
    @Test
    public void testCase3_AddMultipleUniqueFilms() throws Exception {
        // Setup: Create Cinema C1, Film F1, Film F2
        Film film1 = new Film("F1");
        Film film2 = new Film("F2");
        
        // Add Film F1 to Cinema C1 (true)
        boolean result1 = cinema.addFilm(film1);
        assertTrue("First film addition should succeed", result1);
        
        // Action: add Film F2 to Cinema C1 (true)
        boolean result2 = cinema.addFilm(film2);
        
        // Expected Output: true
        assertTrue("Adding second unique film should return true", result2);
    }
    
    @Test
    public void testCase4_AddMultipleUniqueFilms() throws Exception {
        // Setup: Create Cinema C1 and add Film F1 to Cinema C1 (true)
        Film film1 = new Film("F1");
        Film film2 = new Film("F2");
        
        boolean result1 = cinema.addFilm(film1);
        assertTrue("First film addition should succeed", result1);
        
        // Action: add Film F2 to Cinema C1
        boolean result2 = cinema.addFilm(film2);
        
        // Expected Output: true
        assertTrue("Adding second unique film should return true", result2);
    }
    
    @Test
    public void testCase5_AddMultipleUniqueFilms() throws Exception {
        // Setup: Create Cinema C1 and add Film F1 to Cinema C1 (true)
        Film film = new Film("F1");
        boolean addResult = cinema.addFilm(film);
        assertTrue("Initial film addition should succeed", addResult);
        
        // Remove Film F1 from Cinema C1 (true)
        Date currentTime = dateFormat.parse("2024-01-01 12:00:00");
        boolean removeResult = cinema.removeFilm(film, currentTime);
        assertTrue("Film removal should succeed", removeResult);
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = cinema.addFilm(film);
        
        // Expected Output: true
        assertTrue("Adding previously removed film should return true", result);
    }
}