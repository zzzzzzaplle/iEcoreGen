import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    private Cinema cinema;
    private Film film1;
    private Film film2;

    @Before
    public void setUp() {
        // Initialize common test objects before each test
        cinema = new Cinema();
        film1 = new Film();
        film2 = new Film();
    }

    @Test
    public void testCase1_AddNewFilm() {
        // Test Case 1: "Add new film"
        // Setup: Create Cinema C1, Film F1
        // Action: add Film F1 to Cinema C1
        // Expected Output: true
        
        boolean result = cinema.addFilm(film1);
        assertTrue("Adding a new film should return true", result);
        assertTrue("Film should be present in cinema's film list", cinema.getFilms().contains(film1));
    }

    @Test
    public void testCase2_AddDuplicateFilm() {
        // Test Case 2: "Add duplicate film"
        // Setup:
        // 1. Create Cinema C1
        // 2. Add Film F1 to Cinema C1 first
        // Action: add Film F1 to Cinema C1 again
        // Expected Output: false
        
        cinema.addFilm(film1);
        boolean result = cinema.addFilm(film1);
        assertFalse("Adding duplicate film should return false", result);
        assertEquals("Film list should contain only one instance", 1, cinema.getFilms().size());
    }

    @Test
    public void testCase3_AddMultipleUniqueFilms() {
        // Test Case 3: "Add multiple unique films"
        // Setup:
        // 1. Create Cinema C1, Film F1, Film F2
        // 2. Add Film F1 to Cinema C1 (true)
        // Action: add Film F2 to Cinema C1 (true)
        // Expected Output: true
        
        cinema.addFilm(film1);
        boolean result = cinema.addFilm(film2);
        assertTrue("Adding second unique film should return true", result);
        assertTrue("Both films should be present in cinema", cinema.getFilms().contains(film1));
        assertTrue("Both films should be present in cinema", cinema.getFilms().contains(film2));
        assertEquals("Film list should contain two films", 2, cinema.getFilms().size());
    }

    @Test
    public void testCase4_AddMultipleUniqueFilmsAlternative() {
        // Test Case 4: "Add multiple unique films"
        // Setup:
        // 1. Create Cinema C1
        // 2. Add Film F1 to Cinema C1 (true)
        // Action: add Film F2 to Cinema C1
        // Expected Output: true
        
        cinema.addFilm(film1);
        boolean result = cinema.addFilm(film2);
        assertTrue("Adding second unique film should return true", result);
        assertTrue("Film F1 should be present", cinema.getFilms().contains(film1));
        assertTrue("Film F2 should be present", cinema.getFilms().contains(film2));
    }

    @Test
    public void testCase5_AddFilmAfterRemoval() throws ParseException {
        // Test Case 5: "Add multiple unique films"
        // Setup:
        // 1. Create Cinema C1
        // 2. Add Film F1 to Cinema C1 (true)
        // 3. Remove Film F1 from Cinema C1 (true)
        // Action: add Film F1 to Cinema C1 again
        // Expected Output: true
        
        cinema.addFilm(film1);
        
        // Remove the film using current time (any valid date string)
        boolean removeResult = cinema.removeFilm(film1, "2024-01-01 10:00:00");
        assertTrue("Removing film should return true", removeResult);
        assertFalse("Film should not be present after removal", cinema.getFilms().contains(film1));
        
        // Add the same film again
        boolean addResult = cinema.addFilm(film1);
        assertTrue("Adding film after removal should return true", addResult);
        assertTrue("Film should be present again after re-adding", cinema.getFilms().contains(film1));
    }
}