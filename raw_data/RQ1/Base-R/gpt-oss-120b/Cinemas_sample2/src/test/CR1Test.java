import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private Cinema cinema;
    private Room room1;
    private Room room2;
    private Room room3;
    
    @Before
    public void setUp() {
        // Initialize cinema and rooms before each test
        cinema = new Cinema();
        room1 = new Room();
        room1.setName("R1");
        
        room2 = new Room();
        room2.setName("R2");
        
        room3 = new Room();
        room3.setName("R3");
    }
    
    @Test
    public void testCase1_AddNewRoomToEmptyCinema() {
        // Test Case 1: "Add new room to empty cinema"
        // Input: Room R1
        // Setup: Create Cinema C1 with empty rooms, Create Room R1, Add Room R1 to empty cinema
        // Expected Output: true
        
        boolean result = cinema.addRoom(room1);
        assertTrue("Adding room to empty cinema should return true", result);
        assertTrue("Cinema should contain the added room", cinema.getRooms().containsKey("R1"));
    }
    
    @Test
    public void testCase2_AddDuplicateRoom() {
        // Test Case 2: "Add duplicate room"
        // Input: Room R1
        // Setup: Create Cinema C1, Room R1, Add Room R1 to C1 (true), Add Room R1 to C1 again (false)
        // Expected Output: true, false
        
        // First addition should succeed
        boolean firstResult = cinema.addRoom(room1);
        assertTrue("First room addition should return true", firstResult);
        
        // Second addition should fail (duplicate)
        boolean secondResult = cinema.addRoom(room1);
        assertFalse("Duplicate room addition should return false", secondResult);
    }
    
    @Test
    public void testCase3_AddMultipleRooms() {
        // Test Case 3: "Add multiple rooms"
        // Input: Room R1
        // Setup: Create Cinema C1, Room R1, Room R2, Add Room R1 to C1 (true), Add Room R2 to C1 (true), Add Room R1 to C1 again (false)
        // Expected Output: true, true, false
        
        // Add first room
        boolean result1 = cinema.addRoom(room1);
        assertTrue("First room addition should return true", result1);
        
        // Add second room
        boolean result2 = cinema.addRoom(room2);
        assertTrue("Second room addition should return true", result2);
        
        // Try to add first room again (duplicate)
        boolean result3 = cinema.addRoom(room1);
        assertFalse("Duplicate room addition should return false", result3);
    }
    
    @Test
    public void testCase4_AddMultipleUniqueRooms() {
        // Test Case 4: "Add multiple unique rooms"
        // Input: Room R2
        // Setup: Create Cinema C1, Add Room1 to C1 (true), Add Room2 to C1 (true)
        // Expected Output: true, true
        
        // Create additional rooms for this test
        Room room1 = new Room();
        room1.setName("Room1");
        
        Room room2 = new Room();
        room2.setName("Room2");
        
        // Add first room
        boolean result1 = cinema.addRoom(room1);
        assertTrue("First room addition should return true", result1);
        
        // Add second room
        boolean result2 = cinema.addRoom(room2);
        assertTrue("Second room addition should return true", result2);
        
        // Verify both rooms are in the cinema
        assertTrue("Cinema should contain Room1", cinema.getRooms().containsKey("Room1"));
        assertTrue("Cinema should contain Room2", cinema.getRooms().containsKey("Room2"));
    }
    
    @Test
    public void testCase5_AddDuplicateRoomAcrossCinemas() {
        // Test Case 5: "Add duplicate room"
        // Input: Room R1, R2
        // Setup: Create Cinema C1, Cinema C2, Room R1, Room R2, Room R3
        // Add Room R1 to C1 (true), Add Room R2 to C1 (true), Add Room R3 to C2 (true), Add Room R1 to C1 (false)
        // Expected Output: true, true, true, false
        
        Cinema cinema2 = new Cinema();
        
        // Add R1 to C1
        boolean result1 = cinema.addRoom(room1);
        assertTrue("Adding R1 to C1 should return true", result1);
        
        // Add R2 to C1
        boolean result2 = cinema.addRoom(room2);
        assertTrue("Adding R2 to C1 should return true", result2);
        
        // Add R3 to C2
        boolean result3 = cinema2.addRoom(room3);
        assertTrue("Adding R3 to C2 should return true", result3);
        
        // Try to add R1 to C1 again (duplicate)
        boolean result4 = cinema.addRoom(room1);
        assertFalse("Duplicate R1 addition to C1 should return false", result4);
    }
}