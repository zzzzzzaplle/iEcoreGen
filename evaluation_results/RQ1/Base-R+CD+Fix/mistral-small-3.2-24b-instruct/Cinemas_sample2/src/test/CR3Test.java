import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

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
        // Test Case 1: "Add new film"
        // Setup: Create Cinema C1, Film F1
        // Action: add Film F1 to Cinema C1
        // Expected Output: true
        
        boolean result = cinema.addFilm(film1);
        assertTrue("Adding new film should return true", result);
        assertTrue("Film should be in cinema's film list", cinema.getFilms().contains(film1));
    }

    @Test
    public void testCase2_AddDuplicateFilm() {
        // Test Case 2: "Add duplicate film"
        // Setup:
        // 1. Create Cinema C1
        // 2. Add Film F1 to Cinema C1 first
        // Action: add Film F1 to Cinema C1 again
        // Expected Output: false
        
        // Add film1 first time
        cinema.addFilm(film1);
        
        // Try to add film1 again
        boolean result = cinema.addFilm(film1);
        assertFalse("Adding duplicate film should return false", result);
        assertEquals("Cinema should contain only one film", 1, cinema.getFilms().size());
    }

    @Test
    public void testCase3_AddMultipleUniqueFilms() {
        // Test Case 3: "Add multiple unique films"
        // Setup:
        // 1. Create Cinema C1, Film F1, Film F2
        // 2. Add Film F1 to Cinema C1 (true)
        // Action: add Film F2 to Cinema C1 (true)
        // Expected Output: true
        
        // Add film1 first
        boolean result1 = cinema.addFilm(film1);
        assertTrue("First film addition should return true", result1);
        
        // Add film2 second
        boolean result2 = cinema.addFilm(film2);
        assertTrue("Second unique film addition should return true", result2);
        assertEquals("Cinema should contain both films", 2, cinema.getFilms().size());
        assertTrue("Cinema should contain film1", cinema.getFilms().contains(film1));
        assertTrue("Cinema should contain film2", cinema.getFilms().contains(film2));
    }

    @Test
    public void testCase4_AddMultipleUniqueFilms() {
        // Test Case 4: "Add multiple unique films"
        // Setup:
        // 1. Create Cinema C1
        // 2. Add Film F1 to Cinema C1 (true)
        // Action: add Film F2 to Cinema C1
        // Expected Output: true
        
        // Add film1 first
        cinema.addFilm(film1);
        
        // Add film2
        boolean result = cinema.addFilm(film2);
        assertTrue("Adding second unique film should return true", result);
        assertEquals("Cinema should contain two films", 2, cinema.getFilms().size());
    }

    @Test
    public void testCase5_AddMultipleUniqueFilms() throws Exception {
        // Test Case 5: "Add multiple unique films"
        // Setup:
        // 1. Create Cinema C1
        // 2. Add Film F1 to Cinema C1 (true)
        // 3. Remove Film F1 from Cinema C1 (true)
        // Action: add Film F1 to Cinema C1 again
        // Expected Output: true
        
        // Add film1 first
        cinema.addFilm(film1);
        
        // Remove film1
        Date currentTime = dateFormat.parse("2024-01-01 10:00:00");
        boolean removeResult = cinema.removeFilm(film1, currentTime);
        assertTrue("Removing film should return true", removeResult);
        assertFalse("Film should not be in cinema after removal", cinema.getFilms().contains(film1));
        
        // Add film1 again
        boolean addResult = cinema.addFilm(film1);
        assertTrue("Adding previously removed film should return true", addResult);
        assertTrue("Film should be back in cinema", cinema.getFilms().contains(film1));
    }
}