import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private Cinema cinema;
    private Room room1;
    private Room room2;
    private Film film1;
    private Film film2;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        room1 = new Room("Room1", 100);
        room2 = new Room("Room2", 150);
        film1 = new Film("F1", 120);
        film2 = new Film("F2", 90);
    }
    
    @Test
    public void testCase1_CheckAvailableRoomWithNoScreenings() {
        // Setup: Add Room1 to cinema
        cinema.addRoom(room1);
        
        // Action: Check Room1 availability at "2024-10-05 13:00:00"
        boolean result = cinema.isRoomAvailable("Room1", "2024-10-05 13:00:00");
        
        // Expected Output: true
        assertTrue("Room1 should be available when no screenings are scheduled", result);
    }
    
    @Test
    public void testCase2_CheckAssignedRoom() {
        // Setup: Add Room1 and film F1 to cinema
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        // Assign screening for F1 in Room1 at "2024-10-05 13:00:00" (current time "2024-10-04 13:00:00")
        cinema.assignScreening("F1", "Room1", "2024-10-05 13:00:00", "2024-10-04 13:00:00");
        
        // Action: Check Room1 availability at "2024-10-05 13:00:00"
        boolean result = cinema.isRoomAvailable("Room1", "2024-10-05 13:00:00");
        
        // Expected Output: false
        assertFalse("Room1 should not be available when already assigned for a screening", result);
    }
    
    @Test
    public void testCase3_CheckRoomAtDifferentTimeThanScreening() {
        // Setup: Add Film F1 and Room R1 to cinema
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Assign screening for F1 in R1 at "2024-12-01 14:00:00"
        cinema.assignScreening("F1", "Room1", "2024-12-01 14:00:00", "2024-11-01 14:00:00");
        
        // Action: Check R1 availability at "2024-12-02 14:00:00"
        boolean result = cinema.isRoomAvailable("Room1", "2024-12-02 14:00:00");
        
        // Expected Output: true
        assertTrue("Room1 should be available at different time than scheduled screening", result);
    }
    
    @Test
    public void testCase4_CheckMultipleRooms() {
        // Setup: Add Room1, Room2, film F1, film F2 to cinema
        cinema.addRoom(room1);
        cinema.addRoom(room2);
        cinema.addFilm(film1);
        cinema.addFilm(film2);
        
        // Assign film F1 screening S1 at "2024-10-05 13:00:00", room Room1
        cinema.assignScreening("F1", "Room1", "2024-10-05 13:00:00", "2024-10-01 13:00:00");
        
        // Assign film F2 screening S2 at "2024-10-05 13:00:00", room Room2
        cinema.assignScreening("F2", "Room2", "2024-10-05 13:00:00", "2024-10-03 13:00:00");
        
        // Action: Check Room1 and Room2 availability at "2024-10-05 13:00:00"
        boolean resultRoom1 = cinema.isRoomAvailable("Room1", "2024-10-05 13:00:00");
        boolean resultRoom2 = cinema.isRoomAvailable("Room2", "2024-10-05 13:00:00");
        
        // Expected Output: Room1 - false, Room2 - false
        assertFalse("Room1 should not be available when assigned for screening", resultRoom1);
        assertFalse("Room2 should not be available when assigned for screening", resultRoom2);
    }
    
    @Test
    public void testCase5_CheckDifferentTimeSlot() {
        // Setup: Add Room1 and Film F1 to cinema
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        // Assign Film F1 screening at "2024-10-05 13:00:00", room Room1
        cinema.assignScreening("F1", "Room1", "2024-10-05 13:00:00", "2024-09-03 13:00:00");
        
        // Action: Check Room1 availability at "2024-10-05 14:00:00"
        boolean result = cinema.isRoomAvailable("Room1", "2024-10-05 14:00:00");
        
        // Expected Output: true
        assertTrue("Room1 should be available at different time slot", result);
    }
}