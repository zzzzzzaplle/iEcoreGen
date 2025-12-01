import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
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
    public void setUp() throws ParseException {
        cinema = new Cinema();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Create test data
        film1 = new Film("F1");
        film2 = new Film("F2");
        room1 = new Room("R1");
        room2 = new Room("R2");
    }
    
    @Test
    public void testCase1_ValidFutureScreeningAssignment() throws ParseException {
        // Setup: Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" (current time: "2024-12-01 10:00:00")
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Screening screening = new Screening(screeningTime);
        
        boolean result = cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Expected Output: true
        assertTrue("Valid future screening should be assigned successfully", result);
        assertTrue("Screening should be added to cinema's screenings", cinema.getScreenings().contains(screening));
    }
    
    @Test
    public void testCase2_AssignToAlreadyBookedRoom() throws ParseException {
        // Setup: Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // First assignment
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Screening screening1 = new Screening(screeningTime);
        cinema.assignScreening(film1, room1, screening1, currentTime);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" again
        Screening screening2 = new Screening(screeningTime);
        boolean result = cinema.assignScreening(film1, room1, screening2, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign to already booked room", result);
        assertFalse("Second screening should not be added", cinema.getScreenings().contains(screening2));
    }
    
    @Test
    public void testCase3_AssignWithNonExistentFilm() throws ParseException {
        // Setup: Add Room R1 to C1 (but NOT Film F2)
        cinema.addRoom(room1);
        
        // Action: Assign screening for Film F2 (not in cinema) in Room R1 at "2024-12-02 14:00:00"
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Screening screening = new Screening(screeningTime);
        
        boolean result = cinema.assignScreening(film2, room1, screening, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign with non-existent film", result);
        assertFalse("Screening should not be added", cinema.getScreenings().contains(screening));
    }
    
    @Test
    public void testCase4_AssignScreeningAtCurrentTimeBoundary() throws ParseException {
        // Setup: Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-01 10:00:00" (current time: "2024-12-01 10:00:00")
        Date screeningTime = dateFormat.parse("2024-12-01 10:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Screening screening = new Screening(screeningTime);
        
        boolean result = cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening at current time boundary", result);
        assertFalse("Screening should not be added", cinema.getScreenings().contains(screening));
    }
    
    @Test
    public void testCase5_AssignScreeningInPastTime() throws ParseException {
        // Setup: Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-11-30 14:00:00" (current time: "2024-12-01 10:00:00")
        Date screeningTime = dateFormat.parse("2024-11-30 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Screening screening = new Screening(screeningTime);
        
        boolean result = cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening in past time", result);
        assertFalse("Screening should not be added", cinema.getScreenings().contains(screening));
    }
    
    @Test
    public void testCase6_AssignToNonExistentRoom() throws ParseException {
        // Setup: Add Film F1 to C1 (but NOT Room R2)
        cinema.addFilm(film1);
        
        // Action: Assign screening for Film F1 in Room R2 (not in cinema) at "2024-12-02 14:00:00"
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Screening screening = new Screening(screeningTime);
        
        boolean result = cinema.assignScreening(film1, room2, screening, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign to non-existent room", result);
        assertFalse("Screening should not be added", cinema.getScreenings().contains(screening));
    }
}