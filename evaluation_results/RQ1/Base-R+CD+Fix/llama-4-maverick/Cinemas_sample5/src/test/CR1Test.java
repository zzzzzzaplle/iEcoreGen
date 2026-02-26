import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;

public class CR1Test {
    
    private Cinema cinema;
    private Room room1;
    private Room room2;
    private Room room3;
    
    @Before
    public void setUp() {
        // Set up common test objects before each test
        cinema = new Cinema();
        room1 = new Room();
        room2 = new Room();
        room3 = new Room();
    }
    
    @Test
    public void testCase1_AddNewRoomToEmptyCinema() {
        // Test Case 1: Add new room to empty cinema
        // Input: Room R1
        // Setup: Create Cinema C1 with empty rooms, Create Room R1, Add Room R1 to empty cinema
        // Expected Output: true
        
        boolean result = cinema.addRoom(room1);
        assertTrue("Adding room to empty cinema should return true", result);
        assertEquals("Cinema should have exactly 1 room", 1, cinema.getRooms().size());
        assertTrue("Cinema should contain the added room", cinema.getRooms().contains(room1));
    }
    
    @Test
    public void testCase2_AddDuplicateRoom() {
        // Test Case 2: Add duplicate room
        // Input: Room R1
        // Setup: Create Cinema C1, Room R1, Add Room R1 to C1 (true), Add Room R1 to C1 again (false)
        // Expected Output: true, false
        
        // First addition should succeed
        boolean firstResult = cinema.addRoom(room1);
        assertTrue("First room addition should return true", firstResult);
        assertEquals("Cinema should have exactly 1 room after first addition", 1, cinema.getRooms().size());
        
        // Second addition should fail (duplicate)
        boolean secondResult = cinema.addRoom(room1);
        assertFalse("Duplicate room addition should return false", secondResult);
        assertEquals("Cinema should still have exactly 1 room after duplicate addition", 1, cinema.getRooms().size());
    }
    
    @Test
    public void testCase3_AddMultipleRooms() {
        // Test Case 3: Add multiple rooms
        // Input: Room R1
        // Setup: Create Cinema C1, Room R1, Room R2, Add Room R1 to C1 (true), Add Room R2 to C1 (true), Add Room R1 to C1 again (false)
        // Expected Output: true, true, false
        
        // Add first room
        boolean result1 = cinema.addRoom(room1);
        assertTrue("First room addition should return true", result1);
        assertEquals("Cinema should have 1 room after first addition", 1, cinema.getRooms().size());
        
        // Add second room
        boolean result2 = cinema.addRoom(room2);
        assertTrue("Second room addition should return true", result2);
        assertEquals("Cinema should have 2 rooms after second addition", 2, cinema.getRooms().size());
        assertTrue("Cinema should contain first room", cinema.getRooms().contains(room1));
        assertTrue("Cinema should contain second room", cinema.getRooms().contains(room2));
        
        // Try to add first room again (duplicate)
        boolean result3 = cinema.addRoom(room1);
        assertFalse("Duplicate room addition should return false", result3);
        assertEquals("Cinema should still have 2 rooms after duplicate addition", 2, cinema.getRooms().size());
    }
    
    @Test
    public void testCase4_AddMultipleUniqueRooms() {
        // Test Case 4: Add multiple unique rooms
        // Input: Room R2
        // Setup: Create Cinema C1, Add Room1 to C1 (true), Add Room2 to C1 (true)
        // Expected Output: true, true
        
        // Add first room
        boolean result1 = cinema.addRoom(room1);
        assertTrue("First room addition should return true", result1);
        assertEquals("Cinema should have 1 room after first addition", 1, cinema.getRooms().size());
        
        // Add second room
        boolean result2 = cinema.addRoom(room2);
        assertTrue("Second room addition should return true", result2);
        assertEquals("Cinema should have 2 rooms after second addition", 2, cinema.getRooms().size());
        assertTrue("Cinema should contain first room", cinema.getRooms().contains(room1));
        assertTrue("Cinema should contain second room", cinema.getRooms().contains(room2));
    }
    
    @Test
    public void testCase5_AddDuplicateRoomMultipleCinemas() {
        // Test Case 5: Add duplicate room
        // Input: Room R1, R2
        // Setup: Create Cinema C1, Cinema C2, Room R1, Room R2, Room R3
        // Add Room R1 to C1 (true), Add Room R2 to C1 (true), Add Room R3 to C2 (true), Add Room R1 to C1 (false)
        // Expected Output: true, true, true, false
        
        Cinema cinema2 = new Cinema();
        
        // Add Room R1 to C1
        boolean result1 = cinema.addRoom(room1);
        assertTrue("Adding room1 to cinema1 should return true", result1);
        assertEquals("Cinema1 should have 1 room", 1, cinema.getRooms().size());
        
        // Add Room R2 to C1
        boolean result2 = cinema.addRoom(room2);
        assertTrue("Adding room2 to cinema1 should return true", result2);
        assertEquals("Cinema1 should have 2 rooms", 2, cinema.getRooms().size());
        
        // Add Room R3 to C2
        boolean result3 = cinema2.addRoom(room3);
        assertTrue("Adding room3 to cinema2 should return true", result3);
        assertEquals("Cinema2 should have 1 room", 1, cinema2.getRooms().size());
        
        // Try to add Room R1 to C1 again (duplicate)
        boolean result4 = cinema.addRoom(room1);
        assertFalse("Duplicate room addition to cinema1 should return false", result4);
        assertEquals("Cinema1 should still have 2 rooms", 2, cinema.getRooms().size());
        assertEquals("Cinema2 should still have 1 room", 1, cinema2.getRooms().size());
    }
}