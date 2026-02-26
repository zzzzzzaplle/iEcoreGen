import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.HashMap;

public class CR2Test {
    
    private Cinema cinema;
    private Room room1;
    private Room room2;
    private Film film1;
    private Film film2;
    private LocalDateTime currentTime;
    private LocalDateTime screeningTime;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        // Create rooms
        room1 = new Room();
        room1.setId("R1");
        room1.setName("Room 1");
        
        room2 = new Room();
        room2.setId("R2");
        room2.setName("Room 2");
        
        // Create films
        film1 = new Film();
        film1.setId("F1");
        film1.setTitle("Film 1");
        
        film2 = new Film();
        film2.setId("F2");
        film2.setTitle("Film 2");
    }
    
    @Test
    public void testCase1_CheckAvailableRoomWithNoScreenings() {
        // Setup: Create Cinema C1 and add Room1 to C1
        cinema.addRoom(room1);
        
        // Action: check Room1 availability at the current time "2024-10-05 13:00:00"
        LocalDateTime checkTime = LocalDateTime.parse("2024-10-05 13:00:00", formatter);
        boolean result = cinema.isRoomAvailable("R1", checkTime);
        
        // Expected Output: true
        assertTrue("Room should be available when no screenings are scheduled", result);
    }
    
    @Test
    public void testCase2_CheckAssignedRoom() {
        // Setup: Create Cinema C1, film F1, add Room1 and film F1 to C1
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        // Assign film f1 screening at "2024-10-05 13:00:00", room Room1 (the current time "2024-10-04 13:00:00")
        LocalDateTime currentTime = LocalDateTime.parse("2024-10-04 13:00:00", formatter);
        LocalDateTime screeningTime = LocalDateTime.parse("2024-10-05 13:00:00", formatter);
        cinema.assignScreening("F1", "R1", screeningTime, currentTime);
        
        // Action: check Room1 availability at the current time "2024-10-05 13:00:00"
        LocalDateTime checkTime = LocalDateTime.parse("2024-10-05 13:00:00", formatter);
        boolean result = cinema.isRoomAvailable("R1", checkTime);
        
        // Expected Output: false
        assertFalse("Room should not be available when already assigned for screening", result);
    }
    
    @Test
    public void testCase3_CheckRoomAtExactScreeningTime() {
        // Setup: Create Cinema C1, add Film F1 and Room R1 to C1
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        // Assign screening for F1 in R1 at "2024-12-01 14:00:00"
        LocalDateTime currentTime = LocalDateTime.parse("2024-11-01 14:00:00", formatter);
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-01 14:00:00", formatter);
        cinema.assignScreening("F1", "R1", screeningTime, currentTime);
        
        // Action: Check R1 availability at the current time "2024-12-02 14:00:00"
        LocalDateTime checkTime = LocalDateTime.parse("2024-12-02 14:00:00", formatter);
        boolean result = cinema.isRoomAvailable("R1", checkTime);
        
        // Expected Output: true
        assertTrue("Room should be available at different time from screening", result);
    }
    
    @Test
    public void testCase4_CheckMultipleRooms() {
        // Setup: Create Cinema C1, film F1, film F2
        // Add Room1, Room2, film F1, film F2 to C1
        cinema.addRoom(room1);
        cinema.addRoom(room2);
        cinema.addFilm(film1);
        cinema.addFilm(film2);
        
        // Assign film F1 screening S1 at "2024-10-05 13:00:00", room Room1 (the current time : "2024-10-01 13:00:00")
        LocalDateTime currentTime1 = LocalDateTime.parse("2024-10-01 13:00:00", formatter);
        LocalDateTime screeningTime1 = LocalDateTime.parse("2024-10-05 13:00:00", formatter);
        cinema.assignScreening("F1", "R1", screeningTime1, currentTime1);
        
        // Assign film F2 screening S2 at "2024-10-05 13:00:00", room Room2 (the current time : "2024-10-03 13:00:00")
        LocalDateTime currentTime2 = LocalDateTime.parse("2024-10-03 13:00:00", formatter);
        LocalDateTime screeningTime2 = LocalDateTime.parse("2024-10-05 13:00:00", formatter);
        cinema.assignScreening("F2", "R2", screeningTime2, currentTime2);
        
        // Action: check Room1, Room2 availability at the current time "2024-10-05 13:00:00"
        LocalDateTime checkTime = LocalDateTime.parse("2024-10-05 13:00:00", formatter);
        boolean resultR1 = cinema.isRoomAvailable("R1", checkTime);
        boolean resultR2 = cinema.isRoomAvailable("R2", checkTime);
        
        // Expected Output: S1: false, S2: false
        assertFalse("Room1 should not be available when assigned for screening", resultR1);
        assertFalse("Room2 should not be available when assigned for screening", resultR2);
    }
    
    @Test
    public void testCase5_CheckDifferentTimeSlot() {
        // Setup: Create Cinema C1, Film F1
        // Add Room1, Film F1 to C1
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        // Assign Film F1 screening at "2024-10-05 13:00:00", room Room1 (the current time : "2024-09-03 13:00:00")
        LocalDateTime currentTime = LocalDateTime.parse("2024-09-03 13:00:00", formatter);
        LocalDateTime screeningTime = LocalDateTime.parse("2024-10-05 13:00:00", formatter);
        cinema.assignScreening("F1", "R1", screeningTime, currentTime);
        
        // Action: check Room1 availability at the current time "2024-10-05 14:00:00"
        LocalDateTime checkTime = LocalDateTime.parse("2024-10-05 14:00:00", formatter);
        boolean result = cinema.isRoomAvailable("R1", checkTime);
        
        // Expected Output: true
        assertTrue("Room should be available at different time slot", result);
    }
}