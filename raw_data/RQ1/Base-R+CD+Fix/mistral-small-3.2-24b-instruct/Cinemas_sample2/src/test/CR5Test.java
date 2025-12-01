import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR5Test {
    private Cinema cinema;
    private Film film1;
    private Film film2;
    private Room room1;
    private Room room2;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film();
        film2 = new Film();
        room1 = new Room();
        room2 = new Room();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_validFutureScreeningAssignment() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Screening screening = new Screening(screeningTime, film1, room1);
        
        // Action: Assign screening for Film F1 in Room R1
        boolean result = cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Expected Output: true
        assertTrue("Screening should be assigned successfully", result);
    }
    
    @Test
    public void testCase2_assignToAlreadyBookedRoom() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        
        // First assignment
        Screening screening1 = new Screening(screeningTime, film1, room1);
        cinema.assignScreening(film1, room1, screening1, currentTime);
        
        // Action: Assign screening for same film in same room at same time again
        Screening screening2 = new Screening(screeningTime, film1, room1);
        boolean result = cinema.assignScreening(film1, room1, screening2, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening to already booked room", result);
    }
    
    @Test
    public void testCase3_assignWithNonExistentFilm() throws Exception {
        // Setup
        cinema.addRoom(room1);
        // film2 is NOT added to cinema
        
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Screening screening = new Screening(screeningTime, film2, room1);
        
        // Action: Assign screening for Film F2 (not in cinema) in Room R1
        boolean result = cinema.assignScreening(film2, room1, screening, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening with non-existent film", result);
    }
    
    @Test
    public void testCase4_assignScreeningAtCurrentTimeBoundary() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Date screeningTime = dateFormat.parse("2024-12-01 10:00:00"); // Same as current time
        Screening screening = new Screening(screeningTime, film1, room1);
        
        // Action: Assign screening at current time boundary
        boolean result = cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening at current time boundary", result);
    }
    
    @Test
    public void testCase5_assignScreeningInPastTime() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Date screeningTime = dateFormat.parse("2024-11-30 14:00:00"); // Past time
        Screening screening = new Screening(screeningTime, film1, room1);
        
        // Action: Assign screening in past time
        boolean result = cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening in past time", result);
    }
    
    @Test
    public void testCase6_assignToNonExistentRoom() throws Exception {
        // Setup
        cinema.addFilm(film1);
        // room2 is NOT added to cinema
        
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Screening screening = new Screening(screeningTime, film1, room2);
        
        // Action: Assign screening for Film F1 in Room R2 (not in cinema)
        boolean result = cinema.assignScreening(film1, room2, screening, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening to non-existent room", result);
    }
}