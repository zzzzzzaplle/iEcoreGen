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
        film1 = new Film("F1");
        film2 = new Film("F2");
        room1 = new Room("R1");
        room2 = new Room("R2");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ValidFutureScreeningAssignment() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.assignScreening(film1, room1, screeningTime, currentTime);
        
        // Expected Output: true
        assertTrue("Valid future screening should be assigned successfully", result);
    }
    
    @Test
    public void testCase2_AssignToAlreadyBookedRoom() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        cinema.assignScreening(film1, room1, screeningTime, currentTime);
        
        // Action: Assign screening for same film in same room at same time again
        boolean result = cinema.assignScreening(film1, room1, screeningTime, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening to already booked room", result);
    }
    
    @Test
    public void testCase3_AssignWithNonExistentFilm() throws Exception {
        // Setup
        cinema.addRoom(room1);
        // Note: film2 is not added to cinema
        
        // Action
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.assignScreening(film2, room1, screeningTime, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening for non-existent film", result);
    }
    
    @Test
    public void testCase4_AssignScreeningAtCurrentTimeBoundary() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action
        Date screeningTime = dateFormat.parse("2024-12-01 10:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.assignScreening(film1, room1, screeningTime, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening when current time equals screening time", result);
    }
    
    @Test
    public void testCase5_AssignScreeningInPastTime() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action
        Date screeningTime = dateFormat.parse("2024-11-30 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.assignScreening(film1, room1, screeningTime, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening in past time", result);
    }
    
    @Test
    public void testCase6_AssignToNonExistentRoom() throws Exception {
        // Setup
        cinema.addFilm(film1);
        // Note: room2 is not added to cinema
        
        // Action
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.assignScreening(film1, room2, screeningTime, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening to non-existent room", result);
    }
}