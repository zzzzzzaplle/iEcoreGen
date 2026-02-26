import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CR5Test {
    private Cinema cinema;
    private Film film1;
    private Film film2;
    private Room room1;
    private Room room2;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        // Create test films
        film1 = new Film("Film F1", "Action", 120);
        film2 = new Film("Film F2", "Drama", 90);
        
        // Create test rooms
        room1 = new Room("Room R1", 100);
        room2 = new Room("Room R2", 150);
    }
    
    @Test
    public void testCase1_validFutureScreeningAssignment() {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" (current time: "2024-12-01 10:00:00")
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-02 14:00:00", formatter);
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        boolean result = cinema.assignScreening(film1, room1, screeningTime, currentTime);
        
        // Expected Output: true
        assertTrue("Valid future screening assignment should return true", result);
    }
    
    @Test
    public void testCase2_assignToAlreadyBookedRoom() {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Assign first screening for Film F1 in Room R1 at "2024-12-02 14:00:00"
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-02 14:00:00", formatter);
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        cinema.assignScreening(film1, room1, screeningTime, currentTime);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" again
        boolean result = cinema.assignScreening(film1, room1, screeningTime, currentTime);
        
        // Expected Output: false
        assertFalse("Assigning to already booked room should return false", result);
    }
    
    @Test
    public void testCase3_assignWithNonExistentFilm() {
        // Setup: Create Cinema C1, Add Room R1 to C1
        cinema.addRoom(room1);
        
        // Action: Assign screening for Film F2 (not in cinema) in Room R1 at "2024-12-02 14:00:00"
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-02 14:00:00", formatter);
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        boolean result = cinema.assignScreening(film2, room1, screeningTime, currentTime);
        
        // Expected Output: false
        assertFalse("Assigning with non-existent film should return false", result);
    }
    
    @Test
    public void testCase4_assignScreeningAtCurrentTimeBoundary() {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-01 10:00:00" (current time: "2024-12-01 10:00:00")
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        boolean result = cinema.assignScreening(film1, room1, screeningTime, currentTime);
        
        // Expected Output: false
        assertFalse("Assigning screening at current time boundary should return false", result);
    }
    
    @Test
    public void testCase5_assignScreeningInPastTime() {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-11-30 14:00:00" (current time: "2024-12-01 10:00:00")
        LocalDateTime screeningTime = LocalDateTime.parse("2024-11-30 14:00:00", formatter);
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        boolean result = cinema.assignScreening(film1, room1, screeningTime, currentTime);
        
        // Expected Output: false
        assertFalse("Assigning screening in past time should return false", result);
    }
    
    @Test
    public void testCase6_assignToNonExistentRoom() {
        // Setup: Create Cinema C1, Add Film F1 to C1
        cinema.addFilm(film1);
        
        // Action: Assign screening for Film F1 in Room R2 (not in cinema) at "2024-12-02 14:00:00"
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-02 14:00:00", formatter);
        LocalDateTime currentTime = LocalDateTime.parse("2024-12-01 10:00:00", formatter);
        boolean result = cinema.assignScreening(film1, room2, screeningTime, currentTime);
        
        // Expected Output: false
        assertFalse("Assigning to non-existent room should return false", result);
    }
}