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
        room1 = new Room("R1", 100);
        room2 = new Room("R2", 150);
        room3 = new Room("R3", 200);
    }
    
    @Test
    public void testCase1_addNewRoomToEmptyCinema() {
        // Test Case 1: Add new room to empty cinema
        // Input: Room R1
        // Setup: Create Cinema C1 with empty rooms, Create Room R1, Add Room R1 to empty cinema
        // Expected Output: true
        
        boolean result = cinema.addRoom(room1);
        assertTrue("Should successfully add room to empty cinema", result);
        assertEquals("Cinema should have exactly 1 room", 1, cinema.getRooms().size());
        assertTrue("Cinema should contain the added room", cinema.getRooms().contains(room1));
    }
    
    @Test
    public void testCase2_addDuplicateRoom() {
        // Test Case 2: Add duplicate room
        // Input: Room R1
        // Setup: Create Cinema C1, Room R1, Add Room R1 to C1 (true), Add Room R1 to C1 again (false)
        // Expected Output: true, false
        
        // First addition should succeed
        boolean firstResult = cinema.addRoom(room1);
        assertTrue("First addition of room should succeed", firstResult);
        
        // Second addition should fail (duplicate)
        boolean secondResult = cinema.addRoom(room1);
        assertFalse("Second addition of same room should fail", secondResult);
        
        assertEquals("Cinema should still have exactly 1 room", 1, cinema.getRooms().size());
    }
    
    @Test
    public void testCase3_addMultipleRooms() {
        // Test Case 3: Add multiple rooms
        // Input: Room R1
        // Setup: Create Cinema C1, Room R1, Room R2, Add Room R1 to C1 (true), Add Room R2 to C1 (true), Add Room R1 to C1 again (false)
        // Expected Output: true, true, false
        
        // Add first room
        boolean result1 = cinema.addRoom(room1);
        assertTrue("First room addition should succeed", result1);
        
        // Add second room
        boolean result2 = cinema.addRoom(room2);
        assertTrue("Second room addition should succeed", result2);
        
        // Try to add first room again (should fail)
        boolean result3 = cinema.addRoom(room1);
        assertFalse("Duplicate room addition should fail", result3);
        
        assertEquals("Cinema should have exactly 2 rooms", 2, cinema.getRooms().size());
    }
    
    @Test
    public void testCase4_addMultipleUniqueRooms() {
        // Test Case 4: Add multiple unique rooms
        // Input: Room R2
        // Setup: Create Cinema C1, Add Room1 to C1 (true), Add Room2 to C1 (true)
        // Expected Output: true, true
        
        // Add first room
        boolean result1 = cinema.addRoom(room1);
        assertTrue("First room addition should succeed", result1);
        
        // Add second room
        boolean result2 = cinema.addRoom(room2);
        assertTrue("Second room addition should succeed", result2);
        
        assertEquals("Cinema should have exactly 2 rooms", 2, cinema.getRooms().size());
        assertTrue("Cinema should contain room1", cinema.getRooms().contains(room1));
        assertTrue("Cinema should contain room2", cinema.getRooms().contains(room2));
    }
    
    @Test
    public void testCase5_addDuplicateRoomAcrossMultipleCinemas() {
        // Test Case 5: Add duplicate room
        // Input: Room R1, R2
        // Setup: Create Cinema C1, Cinema C2, Room R1, Room R2, Room R3
        // Add Room R1 to C1 (true), Add Room R2 to C1 (true), Add Room R3 to C2 (true), Add Room R1 to C1 (false)
        // Expected Output: true, true, true, false
        
        Cinema cinema2 = new Cinema();
        
        // Add rooms to first cinema
        boolean result1 = cinema.addRoom(room1);
        assertTrue("Room R1 should be added to cinema1", result1);
        
        boolean result2 = cinema.addRoom(room2);
        assertTrue("Room R2 should be added to cinema1", result2);
        
        // Add room to second cinema
        boolean result3 = cinema2.addRoom(room3);
        assertTrue("Room R3 should be added to cinema2", result3);
        
        // Try to add duplicate room to first cinema
        boolean result4 = cinema.addRoom(room1);
        assertFalse("Duplicate room R1 should not be added to cinema1", result4);
        
        // Verify cinema1 has 2 rooms, cinema2 has 1 room
        assertEquals("Cinema1 should have 2 rooms", 2, cinema.getRooms().size());
        assertEquals("Cinema2 should have 1 room", 1, cinema2.getRooms().size());
    }
}