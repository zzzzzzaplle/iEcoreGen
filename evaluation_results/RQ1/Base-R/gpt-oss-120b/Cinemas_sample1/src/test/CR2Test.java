import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.HashSet;
import java.util.Set;

public class CR2Test {
    
    private Cinema cinema;
    private Room room1;
    private Room room2;
    private Film film1;
    private Film film2;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        room1 = new Room("Room1");
        room2 = new Room("Room2");
        film1 = new Film("F1");
        film2 = new Film("F2");
    }
    
    @Test
    public void testCase1_checkAvailableRoomWithNoScreenings() {
        // Setup: Create Cinema C1 and add Room1 to C1
        cinema.addRoom(room1);
        
        // Action: check Room1 availability at "2024-10-05 13:00:00"
        boolean result = cinema.isRoomAvailable("Room1", "2024-10-05 13:00:00");
        
        // Expected Output: true
        assertTrue("Room should be available when no screenings exist", result);
    }
    
    @Test
    public void testCase2_checkAssignedRoom() {
        // Setup: Create Cinema C1, film F1, add Room1 and film F1 to C1
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        // Assign film F1 screening at "2024-10-05 13:00:00" in Room1 (current time "2024-10-04 13:00:00")
        cinema.assignScreening("F1", "Room1", "2024-10-05 13:00:00", "2024-10-04 13:00:00");
        
        // Action: check Room1 availability at "2024-10-05 13:00:00"
        boolean result = cinema.isRoomAvailable("Room1", "2024-10-05 13:00:00");
        
        // Expected Output: false
        assertFalse("Room should not be available when assigned for screening at same time", result);
    }
    
    @Test
    public void testCase3_checkRoomAtExactScreeningTime() {
        // Setup: Create Cinema C1, add Film F1 and Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Assign screening for F1 in R1 at "2024-12-01 14:00:00"
        cinema.assignScreening("F1", "Room1", "2024-12-01 14:00:00", "2024-11-01 14:00:00");
        
        // Action: Check R1 availability at "2024-12-02 14:00:00"
        boolean result = cinema.isRoomAvailable("Room1", "2024-12-02 14:00:00");
        
        // Expected Output: true
        assertTrue("Room should be available at different time than scheduled screening", result);
    }
    
    @Test
    public void testCase4_checkMultipleRooms() {
        // Setup: Create Cinema C1, film F1, film F2
        cinema.addFilm(film1);
        cinema.addFilm(film2);
        cinema.addRoom(room1);
        cinema.addRoom(room2);
        
        // Assign film F1 screening S1 at "2024-10-05 13:00:00" in Room1 (current time "2024-10-01 13:00:00")
        cinema.assignScreening("F1", "Room1", "2024-10-05 13:00:00", "2024-10-01 13:00:00");
        
        // Assign film F2 screening S2 at "2024-10-05 13:00:00" in Room2 (current time "2024-10-03 13:00:00")
        cinema.assignScreening("F2", "Room2", "2024-10-05 13:00:00", "2024-10-03 13:00:00");
        
        // Action: check Room1 and Room2 availability at "2024-10-05 13:00:00"
        boolean resultRoom1 = cinema.isRoomAvailable("Room1", "2024-10-05 13:00:00");
        boolean resultRoom2 = cinema.isRoomAvailable("Room2", "2024-10-05 13:00:00");
        
        // Expected Output: S1: false, S2: false
        assertFalse("Room1 should not be available when assigned for screening", resultRoom1);
        assertFalse("Room2 should not be available when assigned for screening", resultRoom2);
    }
    
    @Test
    public void testCase5_checkDifferentTimeSlot() {
        // Setup: Create Cinema C1, Film F1, add Room1 and Film F1 to C1
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        // Assign Film F1 screening at "2024-10-05 13:00:00" in Room1 (current time "2024-09-03 13:00:00")
        cinema.assignScreening("F1", "Room1", "2024-10-05 13:00:00", "2024-09-03 13:00:00");
        
        // Action: check Room1 availability at "2024-10-05 14:00:00"
        boolean result = cinema.isRoomAvailable("Room1", "2024-10-05 14:00:00");
        
        // Expected Output: true
        assertTrue("Room should be available at different time slot than scheduled screening", result);
    }
}