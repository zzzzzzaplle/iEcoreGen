import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CR1Test {
    private Cinema cinema;
    private Room room1;
    private Room room2;
    private Room room3;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        room1 = new Room("Room1", 100);
        room2 = new Room("Room2", 150);
        room3 = new Room("Room3", 200);
    }
    
    @Test
    public void testCase1_AddNewRoomToEmptyCinema() {
        // Test Case 1: "Add new room to empty cinema"
        // Setup: Create Cinema C1 with empty rooms, Create Room R1
        // Add Room R1 to empty cinema
        boolean result = cinema.addRoom(room1);
        
        // Expected Output: true
        assertTrue("Adding room to empty cinema should return true", result);
        assertTrue("Cinema should contain the added room", cinema.getRooms().contains(room1));
    }
    
    @Test
    public void testCase2_AddDuplicateRoom() {
        // Test Case 2: "Add duplicate room"
        // Setup: Create Cinema C1, Room R1
        cinema.addRoom(room1);
        
        // Add Room R1 to C1 again
        boolean secondAddResult = cinema.addRoom(room1);
        
        // Expected Output: true, false
        assertFalse("Adding duplicate room should return false", secondAddResult);
        assertEquals("Cinema should only contain one instance of the room", 1, cinema.getRooms().size());
    }
    
    @Test
    public void testCase3_AddMultipleRooms() {
        // Test Case 3: "Add multiple rooms"
        // Setup: Create Cinema C1, Room R1, Room R2
        // Add Room R1 to C1 (true)
        boolean result1 = cinema.addRoom(room1);
        // Add Room R2 to C1 (true)
        boolean result2 = cinema.addRoom(room2);
        // Add Room R1 to C1 again (false)
        boolean result3 = cinema.addRoom(room1);
        
        // Expected Output: true, true, false
        assertTrue("First room addition should return true", result1);
        assertTrue("Second room addition should return true", result2);
        assertFalse("Duplicate room addition should return false", result3);
        assertEquals("Cinema should contain exactly 2 rooms", 2, cinema.getRooms().size());
    }
    
    @Test
    public void testCase4_AddMultipleUniqueRooms() {
        // Test Case 4: "Add multiple unique rooms"
        // Setup: Create Cinema C1
        // Add Room1 to C1 (true)
        boolean result1 = cinema.addRoom(room1);
        // Add Room2 to C1 (true)
        boolean result2 = cinema.addRoom(room2);
        
        // Expected Output: true, true
        assertTrue("First room addition should return true", result1);
        assertTrue("Second room addition should return true", result2);
        assertEquals("Cinema should contain exactly 2 rooms", 2, cinema.getRooms().size());
    }
    
    @Test
    public void testCase5_AddDuplicateRoomAcrossMultipleCinemas() {
        // Test Case 5: "Add duplicate room"
        // Setup: Create Cinema C1, Cinema C2, Room R1, Room R2, Room R3
        Cinema cinema2 = new Cinema();
        
        // Add Room R1 to C1 (true)
        boolean result1 = cinema.addRoom(room1);
        // Add Room R2 to C1 (true)
        boolean result2 = cinema.addRoom(room2);
        // Add Room R3 to C2 (true)
        boolean result3 = cinema2.addRoom(room3);
        // Add Room R1 to C1 (false)
        boolean result4 = cinema.addRoom(room1);
        
        // Expected Output: true, true, true, false
        assertTrue("First room addition to cinema1 should return true", result1);
        assertTrue("Second room addition to cinema1 should return true", result2);
        assertTrue("Room addition to cinema2 should return true", result3);
        assertFalse("Duplicate room addition to cinema1 should return false", result4);
        assertEquals("Cinema1 should contain exactly 2 rooms", 2, cinema.getRooms().size());
        assertEquals("Cinema2 should contain exactly 1 room", 1, cinema2.getRooms().size());
    }
}