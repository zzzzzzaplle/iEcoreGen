import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    private Cinema cinema;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
    }
    
    @Test
    public void testCase1_CheckAvailableRoomWithNoScreenings() {
        // Setup: Create Cinema C1 and add Room1 to C1
        Room room1 = new Room("Room1");
        cinema.addRoom(room1);
        
        // Action: check Room1 availability at "2024-10-05 13:00:00"
        boolean result = cinema.checkRoomAvailability("Room1", "2024-10-05 13:00:00");
        
        // Expected Output: true
        assertTrue("Room should be available when no screenings are assigned", result);
    }
    
    @Test
    public void testCase2_CheckAssignedRoom() {
        // Setup: Create Cinema C1, film F1, add Room1 and film F1 to C1
        Room room1 = new Room("Room1");
        Film film1 = new Film("F1");
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        // Assign film F1 screening at "2024-10-05 13:00:00" in room Room1
        cinema.assignScreening("F1", "Room1", "2024-10-05 13:00:00", "2024-10-04 13:00:00");
        
        // Action: check Room1 availability at "2024-10-05 13:00:00"
        boolean result = cinema.checkRoomAvailability("Room1", "2024-10-05 13:00:00");
        
        // Expected Output: false
        assertFalse("Room should not be available when already assigned for a screening", result);
    }
    
    @Test
    public void testCase3_CheckRoomAtExactScreeningTime() {
        // Setup: Create Cinema C1, add Film F1 and Room R1 to C1
        Room room1 = new Room("R1");
        Film film1 = new Film("F1");
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        // Assign screening for F1 in R1 at "2024-12-01 14:00:00"
        cinema.assignScreening("F1", "R1", "2024-12-01 14:00:00", "2024-11-01 14:00:00");
        
        // Action: Check R1 availability at "2024-12-02 14:00:00"
        boolean result = cinema.checkRoomAvailability("R1", "2024-12-02 14:00:00");
        
        // Expected Output: true
        assertTrue("Room should be available at a different time than the scheduled screening", result);
    }
    
    @Test
    public void testCase4_CheckMultipleRooms() {
        // Setup: Create Cinema C1, film F1, film F2
        Room room1 = new Room("Room1");
        Room room2 = new Room("Room2");
        Film film1 = new Film("F1");
        Film film2 = new Film("F2");
        
        // Add Room1, Room2, film F1, film F2 to C1
        cinema.addRoom(room1);
        cinema.addRoom(room2);
        cinema.addFilm(film1);
        cinema.addFilm(film2);
        
        // Assign film F1 screening S1 at "2024-10-05 13:00:00" in room Room1
        cinema.assignScreening("F1", "Room1", "2024-10-05 13:00:00", "2024-10-01 13:00:00");
        
        // Assign film F2 screening S2 at "2024-10-05 13:00:00" in room Room2
        cinema.assignScreening("F2", "Room2", "2024-10-05 13:00:00", "2024-10-03 13:00:00");
        
        // Action: check Room1 and Room2 availability at "2024-10-05 13:00:00"
        boolean resultRoom1 = cinema.checkRoomAvailability("Room1", "2024-10-05 13:00:00");
        boolean resultRoom2 = cinema.checkRoomAvailability("Room2", "2024-10-05 13:00:00");
        
        // Expected Output: S1: false, S2: false
        assertFalse("Room1 should not be available as it has a screening at this time", resultRoom1);
        assertFalse("Room2 should not be available as it has a screening at this time", resultRoom2);
    }
    
    @Test
    public void testCase5_CheckDifferentTimeSlot() {
        // Setup: Create Cinema C1, Film F1, add Room1 and Film F1 to C1
        Room room1 = new Room("Room1");
        Film film1 = new Film("F1");
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        // Assign Film F1 screening at "2024-10-05 13:00:00" in room Room1
        cinema.assignScreening("F1", "Room1", "2024-10-05 13:00:00", "2024-09-03 13:00:00");
        
        // Action: check Room1 availability at "2024-10-05 14:00:00"
        boolean result = cinema.checkRoomAvailability("Room1", "2024-10-05 14:00:00");
        
        // Expected Output: true
        assertTrue("Room should be available at a different time slot", result);
    }
}