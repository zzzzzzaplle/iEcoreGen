import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CR5Test {
    private Cinema cinema;
    private Film filmF1;
    private Room roomR1;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        
        // Create film F1
        filmF1 = new Film();
        filmF1.setId("F1");
        filmF1.setTitle("Test Film");
        
        // Create room R1
        roomR1 = new Room();
        roomR1.setId("R1");
        roomR1.setName("Room 1");
    }
    
    @Test
    public void testCase1_ValidFutureScreeningAssignment() {
        // Setup: Add Film F1 to C1 and Add Room R1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" (current time: "2024-12-01 10:00:00")
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-02 14:00:00", FORMATTER);
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", FORMATTER);
        boolean result = cinema.assignScreening("F1", "R1", screeningTime, currentTime);
        
        // Expected Output: true
        assertTrue("Valid screening assignment should return true", result);
    }
    
    @Test
    public void testCase2_AssignToAlreadyBookedRoom() {
        // Setup: Add Film F1 to C1 and Add Room R1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        // First assignment
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-02 14:00:00", FORMATTER);
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", FORMATTER);
        cinema.assignScreening("F1", "R1", screeningTime, currentTime);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" again
        boolean result = cinema.assignScreening("F1", "R1", screeningTime, currentTime);
        
        // Expected Output: false
        assertFalse("Assignment to already booked room should return false", result);
    }
    
    @Test
    public void testCase3_AssignWithNonExistentFilm() {
        // Setup: Add Room R1 to C1 (but not Film F2)
        cinema.addRoom(roomR1);
        
        // Action: Assign screening for Film F2 (not in cinema) in Room R1 at "2024-12-02 14:00:00"
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-02 14:00:00", FORMATTER);
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", FORMATTER);
        boolean result = cinema.assignScreening("F2", "R1", screeningTime, currentTime);
        
        // Expected Output: false
        assertFalse("Assignment with non-existent film should return false", result);
    }
    
    @Test
    public void testCase4_AssignScreeningAtCurrentTimeBoundary() {
        // Setup: Add Film F1 to C1 and Add Room R1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-01 10:00:00" (current time: "2024-12-01 10:00:00")
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-01 10:00:00", FORMATTER);
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", FORMATTER);
        boolean result = cinema.assignScreening("F1", "R1", screeningTime, currentTime);
        
        // Expected Output: false
        assertFalse("Assignment at current time boundary should return false", result);
    }
    
    @Test
    public void testCase5_AssignScreeningInPastTime() {
        // Setup: Add Film F1 to C1 and Add Room R1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-11-30 14:00:00" (current time: "2024-12-01 10:00:00")
        LocalDateTime screeningTime = LocalDateTime.parse("2024-11-30 14:00:00", FORMATTER);
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", FORMATTER);
        boolean result = cinema.assignScreening("F1", "R1", screeningTime, currentTime);
        
        // Expected Output: false
        assertFalse("Assignment in past time should return false", result);
    }
    
    @Test
    public void testCase6_AssignToNonExistentRoom() {
        // Setup: Add Film F1 to C1 (but not Room R2)
        cinema.addFilm(filmF1);
        
        // Action: Assign screening for Film F1 in Room R2 (not in cinema) at "2024-12-02 14:00:00"
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-02 14:00:00", FORMATTER);
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", FORMATTER);
        boolean result = cinema.assignScreening("F1", "R2", screeningTime, currentTime);
        
        // Expected Output: false
        assertFalse("Assignment to non-existent room should return false", result);
    }
}