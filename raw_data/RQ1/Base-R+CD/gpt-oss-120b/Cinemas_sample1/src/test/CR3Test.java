// IMPORTANT: Do not include package declaration
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.ParseException;

public class CR3Test {
    
    private Cinema cinema;
    private Film film1;
    private Film film2;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film("Film One");
        film2 = new Film("Film Two");
    }
    
    @Test
    public void testCase1_addNewFilm() {
        // Setup: Create Cinema C1, Film F1
        // Action: add Film F1 to Cinema C1
        boolean result = cinema.addFilm(film1);
        
        // Expected Output: true
        assertTrue(result);
        assertTrue(cinema.getFilms().contains(film1));
    }
    
    @Test
    public void testCase2_addDuplicateFilm() {
        // Setup: Create Cinema C1, Add Film F1 to Cinema C1 first
        cinema.addFilm(film1);
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = cinema.addFilm(film1);
        
        // Expected Output: false
        assertFalse(result);
        assertEquals(1, cinema.getFilms().size());
    }
    
    @Test
    public void testCase3_addMultipleUniqueFilms() {
        // Setup: Create Cinema C1, Film F1, Film F2, Add Film F1 to Cinema C1 (true)
        cinema.addFilm(film1);
        
        // Action: add Film F2 to Cinema C1 (true)
        boolean result = cinema.addFilm(film2);
        
        // Expected Output: true
        assertTrue(result);
        assertTrue(cinema.getFilms().contains(film1));
        assertTrue(cinema.getFilms().contains(film2));
        assertEquals(2, cinema.getFilms().size());
    }
    
    @Test
    public void testCase4_addMultipleUniqueFilms() {
        // Setup: Create Cinema C1, Add Film F1 to Cinema C1 (true)
        cinema.addFilm(film1);
        
        // Action: add Film F2 to Cinema C1
        boolean result = cinema.addFilm(film2);
        
        // Expected Output: true
        assertTrue(result);
        assertTrue(cinema.getFilms().contains(film1));
        assertTrue(cinema.getFilms().contains(film2));
        assertEquals(2, cinema.getFilms().size());
    }
    
    @Test
    public void testCase5_addMultipleUniqueFilms() {
        // Setup: Create Cinema C1, Add Film F1 to Cinema C1 (true), Remove Film F1 from Cinema C1 (true)
        cinema.addFilm(film1);
        // Note: There's no explicit removeFilm method for just removing a film without time consideration
        // But we can simulate by clearing and re-adding or using internal access
        // For this test, we'll assume the film was somehow removed from the collection
        cinema.getFilms().remove(film1);
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = cinema.addFilm(film1);
        
        // Expected Output: true
        assertTrue(result);
        assertTrue(cinema.getFilms().contains(film1));
        assertEquals(1, cinema.getFilms().size());
    }
}