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
        // Initialize Cinema and Film objects before each test
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
    public void testCase5_AddFilmAfterRemoval() {
        // Setup: Create Cinema C1, add Film F1 to Cinema C1 (true)
        cinema.addFilm(film1);
        
        // Remove Film F1 from Cinema C1 (true)
        // Using current time that ensures all screenings would be considered future if any existed
        Date currentTime = new Date(System.currentTimeMillis() - 1000);
        boolean removeResult = cinema.removeFilm(film1, currentTime);
        assertTrue(removeResult);
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = cinema.addFilm(film1);
        
        // Expected Output: true
        assertTrue(result);
    }
}