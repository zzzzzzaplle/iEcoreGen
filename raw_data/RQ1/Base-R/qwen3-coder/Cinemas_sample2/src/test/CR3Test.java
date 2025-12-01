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
        film1 = new Film("Film 1", 120);
        film2 = new Film("Film 2", 90);
    }
    
    @Test
    public void testCase1_AddNewFilm() {
        // Test Case 1: "Add new film"
        // Setup: Create Cinema C1, Film F1
        Cinema c1 = new Cinema();
        Film f1 = new Film("Test Film", 120);
        
        // Action: add Film F1 to Cinema C1
        boolean result = c1.addFilm(f1);
        
        // Expected Output: true
        assertTrue("Adding new film should return true", result);
        assertTrue("Film should be in cinema's film list", c1.getFilms().contains(f1));
    }
    
    @Test
    public void testCase2_AddDuplicateFilm() {
        // Test Case 2: "Add duplicate film"
        // Setup: 
        // 1. Create Cinema C1
        Cinema c1 = new Cinema();
        Film f1 = new Film("Duplicate Film", 150);
        
        // 2. Add Film F1 to Cinema C1 first
        c1.addFilm(f1);
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = c1.addFilm(f1);
        
        // Expected Output: false
        assertFalse("Adding duplicate film should return false", result);
        assertEquals("Cinema should still have only one film", 1, c1.getFilms().size());
    }
    
    @Test
    public void testCase3_AddMultipleUniqueFilms() {
        // Test Case 3: "Add multiple unique films"
        // Setup:
        // 1. Create Cinema C1, Film F1, Film F2
        Cinema c1 = new Cinema();
        Film f1 = new Film("Film A", 100);
        Film f2 = new Film("Film B", 110);
        
        // 2. Add Film F1 to Cinema C1 (true)
        boolean result1 = c1.addFilm(f1);
        assertTrue("First film should be added successfully", result1);
        
        // Action: add Film F2 to Cinema C1 (true)
        boolean result2 = c1.addFilm(f2);
        
        // Expected Output: true
        assertTrue("Second unique film should be added successfully", result2);
        assertEquals("Cinema should have both films", 2, c1.getFilms().size());
        assertTrue("Cinema should contain first film", c1.getFilms().contains(f1));
        assertTrue("Cinema should contain second film", c1.getFilms().contains(f2));
    }
    
    @Test
    public void testCase4_AddMultipleUniqueFilms() {
        // Test Case 4: "Add multiple unique films"
        // Setup:
        // 1. Create Cinema C1
        Cinema c1 = new Cinema();
        Film f1 = new Film("Film X", 130);
        Film f2 = new Film("Film Y", 140);
        
        // 2. Add Film F1 to Cinema C1 (true)
        boolean result1 = c1.addFilm(f1);
        assertTrue("First film should be added successfully", result1);
        
        // Action: add Film F2 to Cinema C1
        boolean result2 = c1.addFilm(f2);
        
        // Expected Output: true
        assertTrue("Second unique film should be added successfully", result2);
        assertEquals("Cinema should have both films", 2, c1.getFilms().size());
    }
    
    @Test
    public void testCase5_AddMultipleUniqueFilms() {
        // Test Case 5: "Add multiple unique films"
        // Setup:
        // 1. Create Cinema C1
        Cinema c1 = new Cinema();
        Film f1 = new Film("Film Z", 160);
        
        // 2. Add Film F1 to Cinema C1 (true)
        boolean result1 = c1.addFilm(f1);
        assertTrue("First film should be added successfully", result1);
        
        // 3. Remove Film F1 from Cinema C1 (true)
        boolean removeResult = c1.removeFilm(f1, "2024-01-01 10:00:00");
        assertTrue("Film should be removed successfully", removeResult);
        
        // Action: add Film F1 to Cinema C1 again
        boolean result2 = c1.addFilm(f1);
        
        // Expected Output: true
        assertTrue("Film should be added again after removal", result2);
        assertTrue("Film should be in cinema's film list", c1.getFilms().contains(f1));
    }
}