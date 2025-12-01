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
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film("Film Title 1");
        film2 = new Film("Film Title 2");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
    public void testCase5_addMultipleUniqueFilms() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1 to Cinema C1 (true), 
        // Remove Film F1 from Cinema C1 (true) - simulating removal by adding screening and then removing film
        Date currentTime = dateFormat.parse("2023-01-01 10:00:00");
        Date futureTime = dateFormat.parse("2023-01-01 12:00:00");
        
        cinema.addFilm(film1);
        
        // Add a room to enable film removal
        Room room = new Room("Room 1");
        cinema.addRoom(room);
        
        // Schedule a screening for the film
        Screening screening = new Screening(futureTime, film1, room);
        cinema.assignScreening(film1, room, screening, currentTime);
        
        // Remove the film
        boolean removed = cinema.removeFilm(film1, currentTime);
        assertTrue(removed);
        
        // Action: add Film F1 to Cinema C1 again
        boolean result = cinema.addFilm(film1);
        
        // Expected Output: true
        assertTrue(result);
        assertTrue(cinema.getFilms().contains(film1));
    }
}