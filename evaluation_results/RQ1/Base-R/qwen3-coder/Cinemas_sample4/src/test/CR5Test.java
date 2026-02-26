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
        filmF1 = new Film("Film F1");
        filmF2 = new Film("Film F2");
        roomR1 = new Room("Room R1");
        roomR2 = new Room("Room R2");
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ValidFutureScreeningAssignment() {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" (current time: "2024-12-01 10:00:00")
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-02 14:00:00", formatter);
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        boolean result = cinema.assignScreening(filmF1, roomR1, screeningTime, currentTime);
        
        // Expected Output: true
        assertTrue("Screening should be assigned successfully for valid future time", result);
    }
    
    @Test
    public void testCase2_AssignToAlreadyBookedRoom() {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        // Assign initial screening for Film F1 in Room R1 at "2024-12-02 14:00:00"
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-02 14:00:00", formatter);
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        cinema.assignScreening(filmF1, roomR1, screeningTime, currentTime);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" again
        boolean result = cinema.assignScreening(filmF1, roomR1, screeningTime, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening to already booked room", result);
    }
    
    @Test
    public void testCase3_AssignWithNonExistentFilm() {
        // Setup: Create Cinema C1, Add Room R1 to C1
        cinema.addRoom(roomR1);
        
        // Action: Assign screening for Film F2 (not in cinema) in Room R1 at "2024-12-02 14:00:00"
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-02 14:00:00", formatter);
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        boolean result = cinema.assignScreening(filmF2, roomR1, screeningTime, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening for film not in cinema", result);
    }
    
    @Test
    public void testCase4_AssignScreeningAtCurrentTimeBoundary() {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-01 10:00:00" (current time: "2024-12-01 10:00:00")
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        boolean result = cinema.assignScreening(filmF1, roomR1, screeningTime, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening at current time boundary", result);
    }
    
    @Test
    public void testCase5_AssignScreeningInPastTime() {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-11-30 14:00:00" (current time: "2024-12-01 10:00:00")
        LocalDateTime screeningTime = LocalDateTime.parse("2024-11-30 14:00:00", formatter);
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        boolean result = cinema.assignScreening(filmF1, roomR1, screeningTime, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening in past time", result);
    }
    
    @Test
    public void testCase6_AssignToNonExistentRoom() {
        // Setup: Create Cinema C1, Add Film F1 to C1
        cinema.addFilm(filmF1);
        
        // Action: Assign screening for Film F1 in Room R2 (not in cinema) at "2024-12-02 14:00:00"
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-02 14:00:00", formatter);
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        boolean result = cinema.assignScreening(filmF1, roomR2, screeningTime, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening to non-existent room", result);
    }
}