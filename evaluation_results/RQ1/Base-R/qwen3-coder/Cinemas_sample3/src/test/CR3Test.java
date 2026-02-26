import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CR3Test {
    
    private Cinema cinema;
    private Film film1;
    private Film film2;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        // Initialize cinema and films before each test
        cinema = new Cinema();
        film1 = new Film("Film 1");
        film2 = new Film("Film 2");
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_AddNewFilm() {
        // Setup: Create Cinema C1, Film F1
        Cinema C1 = cinema;
        Film F1 = film1;
        
        // Action: add Film F1 to Cinema C1
        boolean result = C1.addFilm(F1);
        
        // Expected Output: true
        assertTrue("Adding new film should return true", result);
        assertTrue("Film should be added to cinema films list", C1.getFilms().contains(F1));
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
        assertEquals("Cinema should still contain only one film", 1, C1.getFilms().size());
    }
    
    @Test
    public void testCase3_AddMultipleUniqueFilms() {
        // Setup: Create Cinema C1, Film F1, Film F2 and add Film F1 to Cinema C1 (true)
        Cinema C1 = cinema;
        Film F1 = film1;
        Film F2 = film2;
        boolean firstAddResult = C1.addFilm(F1);
        
        // Verify first addition was successful
        assertTrue("First film addition should return true", firstAddResult);
        
        // Action: add Film F2 to Cinema C1 (true)
        boolean result = C1.addFilm(F2);
        
        // Expected Output: true
        assertTrue("Adding second unique film should return true", result);
        assertEquals("Cinema should contain both films", 2, C1.getFilms().size());
        assertTrue("Cinema should contain F1", C1.getFilms().contains(F1));
        assertTrue("Cinema should contain F2", C1.getFilms().contains(F2));
    }
    
    @Test
    public void testCase4_AddMultipleUniqueFilms() {
        // Setup: Create Cinema C1 and add Film F1 to Cinema C1 (true)
        Cinema C1 = cinema;
        Film F1 = film1;
        Film F2 = film2;
        boolean firstAddResult = C1.addFilm(F1);
        
        // Verify first addition was successful
        assertTrue("First film addition should return true", firstAddResult);
        
        // Action: add Film F2 to Cinema C1
        boolean result = C1.addFilm(F2);
        
        // Expected Output: true
        assertTrue("Adding second unique film should return true", result);
        assertEquals("Cinema should contain both films", 2, C1.getFilms().size());
        assertTrue("Cinema should contain F1", C1.getFilms().contains(F1));
        assertTrue("Cinema should contain F2", C1.getFilms().contains(F2));
    }
    
    @Test
    public void testCase5_AddMultipleUniqueFilms() {
        // Setup: Create Cinema C1, add Film F1 to Cinema C1 (true), then remove Film F1 from Cinema C1 (true)
        Cinema C1 = cinema;
        Film F1 = film1;
        
        // Add film first
        boolean addResult = C1.addFilm(F1);
        assertTrue("First film addition should return true", addResult);
        
        // Remove film using current time
        LocalDateTime currentTime = LocalDateTime.parse("2024-01-01 10:00:00", formatter);
        boolean removeResult = C1.removeFilm(F1, currentTime);
        assertTrue("Film removal should return true", removeResult);
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = C1.addFilm(F1);
        
        // Expected Output: true
        assertTrue("Adding film after removal should return true", result);
        assertTrue("Film should be added back to cinema", C1.getFilms().contains(F1));
        assertEquals("Cinema should contain one film", 1, C1.getFilms().size());
    }
}