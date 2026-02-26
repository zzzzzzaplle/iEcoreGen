import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CR4Test {
    private Cinema cinema;
    private Film film1;
    private Room room1;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film();
        room1 = new Room();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_removeFilmWithNoScreenings() throws Exception {
        // Setup: Create Cinema C1 and add Film F1 to C1
        cinema.addFilm(film1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(film1, currentTime);
        
        // Expected Output: true
        assertTrue("Film should be removed successfully when no screenings exist", result);
        assertFalse("Film should no longer be in cinema films list", cinema.getFilms().contains(film1));
    }
    
    @Test
    public void testCase2_removeNonExistentFilm() throws Exception {
        // Setup: Create Cinema C1 (do not add Film F1)
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(film1, currentTime);
        
        // Expected Output: false (Film F1 does not exist in cinema)
        assertFalse("Should return false when trying to remove non-existent film", result);
    }
    
    @Test
    public void testCase3_removeFilmWithFutureScreening() throws Exception {
        // Setup: Create Cinema C1, add Film F1 to C1, and assign screening for F1 at "2024-12-02 15:00:00"
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Screening screening = new Screening();
        screening.setFilm(film1);
        screening.setRoom(room1);
        screening.setTime(dateFormat.parse("2024-12-02 15:00:00"));
        
        Date setupTime = dateFormat.parse("2024-12-01 09:00:00");
        cinema.assignScreening(film1, room1, screening, setupTime);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(film1, currentTime);
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully when future screening exists", result);
        assertFalse("Film should no longer be in cinema films list", cinema.getFilms().contains(film1));
        assertEquals("All future screenings should be removed", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_removeFilmWithPastScreening() throws Exception {
        // Setup: Create Cinema C1, add Film F1 to C1, and schedule screening for F1 at "2024-11-30 18:00:00"
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Screening screening = new Screening();
        screening.setFilm(film1);
        screening.setRoom(room1);
        screening.setTime(dateFormat.parse("2024-11-30 18:00:00"));
        
        Date setupTime = dateFormat.parse("2024-11-30 17:00:00");
        cinema.assignScreening(film1, room1, screening, setupTime);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(film1, currentTime);
        
        // Expected Output: true, and there is 1 screening in the cinema
        assertTrue("Film should be removed successfully when past screening exists", result);
        assertFalse("Film should no longer be in cinema films list", cinema.getFilms().contains(film1));
        assertEquals("Past screenings should remain in cinema", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_removeFilmWithCurrentTimeScreening() throws Exception {
        // Setup: Create Cinema C1, add Film F1 to C1, and schedule screening for F1 at "2024-12-01 10:00:00"
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Screening screening = new Screening();
        screening.setFilm(film1);
        screening.setRoom(room1);
        screening.setTime(dateFormat.parse("2024-12-01 10:00:00"));
        
        Date setupTime = dateFormat.parse("2024-12-01 09:00:00");
        cinema.assignScreening(film1, room1, screening, setupTime);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(film1, currentTime);
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully when screening time equals current time", result);
        assertFalse("Film should no longer be in cinema films list", cinema.getFilms().contains(film1));
        assertEquals("Screenings at current time should be removed", 0, cinema.getScreenings().size());
    }
}