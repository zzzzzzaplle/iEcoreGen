import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    private Cinema cinema;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_checkAvailableRoomWithNoScreenings() {
        // Setup: Create Cinema C1 and add Room1
        Room room1 = new Room();
        room1.setRoomId("Room1");
        cinema.addRoom(room1);
        
        // Action: Check Room1 availability at "2024-10-05 13:00:00"
        boolean result = cinema.checkRoomAvailability("Room1", "2024-10-05 13:00:00");
        
        // Expected Output: true
        assertTrue("Room should be available when no screenings are scheduled", result);
    }
    
    @Test
    public void testCase2_checkAssignedRoom() {
        // Setup: Create Cinema C1, film F1
        Film film1 = new Film();
        film1.setTitle("F1");
        
        Room room1 = new Room();
        room1.setRoomId("Room1");
        
        // Add Room1 and film F1 to C1
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        // Assign film F1 screening at "2024-10-05 13:00:00" in Room1
        cinema.assignScreening("F1", "Room1", "2024-10-05 13:00:00", "2024-10-04 13:00:00");
        
        // Action: Check Room1 availability at "2024-10-05 13:00:00"
        boolean result = cinema.checkRoomAvailability("Room1", "2024-10-05 13:00:00");
        
        // Expected Output: false
        assertFalse("Room should not be available when already assigned for screening", result);
    }
    
    @Test
    public void testCase3_checkRoomAtExactScreeningTime() {
        // Setup: Create Cinema C1
        Film film1 = new Film();
        film1.setTitle("F1");
        
        Room room1 = new Room();
        room1.setRoomId("R1");
        
        // Add Film F1 and Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Assign screening for F1 in R1 at "2024-12-01 14:00:00"
        cinema.assignScreening("F1", "R1", "2024-12-01 14:00:00", "2024-11-01 14:00:00");
        
        // Action: Check R1 availability at "2024-12-02 14:00:00" (different time)
        boolean result = cinema.checkRoomAvailability("R1", "2024-12-02 14:00:00");
        
        // Expected Output: true
        assertTrue("Room should be available at different time slot", result);
    }
    
    @Test
    public void testCase4_checkMultipleRooms() {
        // Setup: Create Cinema C1, film F1, film F2
        Film film1 = new Film();
        film1.setTitle("F1");
        
        Film film2 = new Film();
        film2.setTitle("F2");
        
        Room room1 = new Room();
        room1.setRoomId("Room1");
        
        Room room2 = new Room();
        room2.setRoomId("Room2");
        
        // Add Room1, Room2, film F1, film F2 to C1
        cinema.addRoom(room1);
        cinema.addRoom(room2);
        cinema.addFilm(film1);
        cinema.addFilm(film2);
        
        // Assign film F1 screening S1 at "2024-10-05 13:00:00" in Room1
        cinema.assignScreening("F1", "Room1", "2024-10-05 13:00:00", "2024-10-01 13:00:00");
        
        // Assign film F2 screening S2 at "2024-10-05 13:00:00" in Room2
        cinema.assignScreening("F2", "Room2", "2024-10-05 13:00:00", "2024-10-03 13:00:00");
        
        // Action: Check Room1 and Room2 availability at "2024-10-05 13:00:00"
        boolean room1Result = cinema.checkRoomAvailability("Room1", "2024-10-05 13:00:00");
        boolean room2Result = cinema.checkRoomAvailability("Room2", "2024-10-05 13:00:00");
        
        // Expected Output: S1: false, S2: false
        assertFalse("Room1 should not be available at scheduled screening time", room1Result);
        assertFalse("Room2 should not be available at scheduled screening time", room2Result);
    }
    
    @Test
    public void testCase5_checkDifferentTimeSlot() {
        // Setup: Create Cinema C1, Film F1
        Film film1 = new Film();
        film1.setTitle("F1");
        
        Room room1 = new Room();
        room1.setRoomId("Room1");
        
        // Add Room1, Film F1 to C1
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        // Assign Film F1 screening at "2024-10-05 13:00:00" in Room1
        cinema.assignScreening("F1", "Room1", "2024-10-05 13:00:00", "2024-09-03 13:00:00");
        
        // Action: Check Room1 availability at "2024-10-05 14:00:00" (different time)
        boolean result = cinema.checkRoomAvailability("Room1", "2024-10-05 14:00:00");
        
        // Expected Output: true
        assertTrue("Room should be available at different time slot", result);
    }
}