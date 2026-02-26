import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CR5Test {
    private Cinema cinema;
    private Film filmF1;
    private Film filmF2;
    private Room roomR1;
    private Room roomR2;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        filmF1 = new Film("F1");
        filmF2 = new Film("F2");
        roomR1 = new Room("R1");
        roomR2 = new Room("R2");
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ValidFutureScreeningAssignment() {
        // Setup
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        // Action
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-02 14:00:00", formatter);
        boolean result = cinema.assignScreening(filmF1, roomR1, screeningTime, currentTime);
        
        // Expected Output: true
        assertTrue("Screening should be assigned successfully", result);
    }
    
    @Test
    public void testCase2_AssignToAlreadyBookedRoom() {
        // Setup
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        // First assignment
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-02 14:00:00", formatter);
        cinema.assignScreening(filmF1, roomR1, screeningTime, currentTime);
        
        // Action: Try to assign again at same time
        boolean result = cinema.assignScreening(filmF1, roomR1, screeningTime, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening to already booked room", result);
    }
    
    @Test
    public void testCase3_AssignWithNonExistentFilm() {
        // Setup
        cinema.addRoom(roomR1);
        // Film F2 is NOT added to cinema
        
        // Action
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-02 14:00:00", formatter);
        boolean result = cinema.assignScreening(filmF2, roomR1, screeningTime, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening with non-existent film", result);
    }
    
    @Test
    public void testCase4_AssignScreeningAtCurrentTimeBoundary() {
        // Setup
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        // Action
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        boolean result = cinema.assignScreening(filmF1, roomR1, screeningTime, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening at current time boundary", result);
    }
    
    @Test
    public void testCase5_AssignScreeningInPastTime() {
        // Setup
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        // Action
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        LocalDateTime screeningTime = LocalDateTime.parse("2024-11-30 14:00:00", formatter);
        boolean result = cinema.assignScreening(filmF1, roomR1, screeningTime, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening in past time", result);
    }
    
    @Test
    public void testCase6_AssignToNonExistentRoom() {
        // Setup
        cinema.addFilm(filmF1);
        // Room R2 is NOT added to cinema
        
        // Action
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-02 14:00:00", formatter);
        boolean result = cinema.assignScreening(filmF1, roomR2, screeningTime, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening to non-existent room", result);
    }
}