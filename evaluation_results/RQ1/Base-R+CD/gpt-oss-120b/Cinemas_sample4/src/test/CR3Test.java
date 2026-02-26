import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR3Test {
    
    private Cinema cinema;
    private Film film1;
    private Film film2;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() throws ParseException {
        cinema = new Cinema();
        film1 = new Film();
        film1.setTitle("Film One");
        film2 = new Film();
        film2.setTitle("Film Two");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_AddNewFilm() {
        // Setup: Create Cinema C1, Film F1
        // Action: add Film F1 to Cinema C1
        boolean result = cinema.addFilm(film1);
        
        // Expected Output: true
        assertTrue("Adding new film should return true", result);
        assertTrue("Film should be in cinema's film list", cinema.getFilms().contains(film1));
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
        assertEquals("Film list should contain both films", 2, cinema.getFilms().size());
        assertTrue("Film list should contain film1", cinema.getFilms().contains(film1));
        assertTrue("Film list should contain film2", cinema.getFilms().contains(film2));
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
        assertEquals("Film list should contain both films", 2, cinema.getFilms().size());
    }
    
    @Test
    public void testCase5_AddMultipleUniqueFilms() throws ParseException {
        // Setup:
        // 1. Create Cinema C1
        // 2. Add Film F1 to Cinema C1 (true)
        cinema.addFilm(film1);
        
        // 3. Remove Film F1 from Cinema C1 (true)
        Date currentTime = dateFormat.parse("2024-01-01 10:00:00");
        cinema.removeFilm(film1, currentTime);
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = cinema.addFilm(film1);
        
        // Expected Output: true
        assertTrue("Adding previously removed film should return true", result);
        assertTrue("Film should be in cinema's film list", cinema.getFilms().contains(film1));
    }
}