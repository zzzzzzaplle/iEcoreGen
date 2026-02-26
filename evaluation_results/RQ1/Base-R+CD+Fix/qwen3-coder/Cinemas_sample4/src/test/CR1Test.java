import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Cinema cinema;
    private Room room1;
    private Room room2;
    private Room room3;
    private Cinema cinema2;
    
    @Before
    public void setUp() {
        // Initialize test objects before each test
        cinema = new Cinema();
        cinema2 = new Cinema();
        room1 = new Room();
        room2 = new Room();
        room3 = new Room();
    }
    
    @Test
    public void testCase1_AddNewRoomToEmptyCinema() {
        // Test Case 1: "Add new room to empty cinema"
        // Input: Room R1
        // Setup: 
        // 1. Create Cinema C1 with empty rooms
        // 2. Create Room R1
        // 3. Add Room R1 to empty cinema
        // Expected Output: true
        
        // Add room to empty cinema
        boolean result = cinema.addRoom(room1);
        
        // Verify room was added successfully
        assertTrue("Room should be added to empty cinema", result);
        assertEquals("Cinema should contain exactly 1 room", 1, cinema.getRooms().size());
        assertTrue("Cinema should contain the added room", cinema.getRooms().contains(room1));
    }
    
    @Test
    public void testCase2_AddDuplicateRoom() {
        // Test Case 2: "Add duplicate room"
        // Input: Room R1
        // Setup:
        // 1. Create Cinema C1, Room R1
        // 2. Add Room R1 to C1 (true)
        // 3. Add Room R1 to C1 again (false)
        // Expected Output: true, false
        
        // First addition should succeed
        boolean firstResult = cinema.addRoom(room1);
        assertTrue("First room addition should succeed", firstResult);
        assertEquals("Cinema should contain exactly 1 room after first addition", 1, cinema.getRooms().size());
        
        // Second addition of same room should fail
        boolean secondResult = cinema.addRoom(room1);
        assertFalse("Duplicate room addition should fail", secondResult);
        assertEquals("Cinema should still contain exactly 1 room", 1, cinema.getRooms().size());
    }
    
    @Test
    public void testCase3_AddMultipleRooms() {
        // Test Case 3: "Add multiple rooms"
        // Input:  Room R1
        // Setup:
        // 1. Create Cinema C1, Room R1, Room R2
        // 2. Add Room R1 to C1 (true)
        // 3. Add Room R2 to C1 (true)
        // 4. Add Room R1 to C1 again (false)
        // Expected Output: true, true, false
        
        // Add first room
        boolean result1 = cinema.addRoom(room1);
        assertTrue("First room addition should succeed", result1);
        assertEquals("Cinema should contain 1 room", 1, cinema.getRooms().size());
        
        // Add second room
        boolean result2 = cinema.addRoom(room2);
        assertTrue("Second room addition should succeed", result2);
        assertEquals("Cinema should contain 2 rooms", 2, cinema.getRooms().size());
        
        // Try to add first room again
        boolean result3 = cinema.addRoom(room1);
        assertFalse("Duplicate room addition should fail", result3);
        assertEquals("Cinema should still contain 2 rooms", 2, cinema.getRooms().size());
    }
    
    @Test
    public void testCase4_AddMultipleUniqueRooms() {
        // Test Case 4: "Add multiple unique rooms"
        // Input: Room R2
        // Setup:
        // 1. Create Cinema C1
        // 2. Add Room1 to C1 (true)
        // 3. Add Room2 to C1 (true)
        // Expected Output: true, true
        
        // Add first room
        boolean result1 = cinema.addRoom(room1);
        assertTrue("First room addition should succeed", result1);
        assertEquals("Cinema should contain 1 room", 1, cinema.getRooms().size());
        
        // Add second room
        boolean result2 = cinema.addRoom(room2);
        assertTrue("Second room addition should succeed", result2);
        assertEquals("Cinema should contain 2 rooms", 2, cinema.getRooms().size());
        
        // Verify both rooms are in the cinema
        assertTrue("Cinema should contain room1", cinema.getRooms().contains(room1));
        assertTrue("Cinema should contain room2", cinema.getRooms().contains(room2));
    }
    
    @Test
    public void testCase5_AddDuplicateRoomToDifferentCinemas() {
        // Test Case 5: "Add duplicate room"
        // Input: Room R1, R2
        // Setup:
        // 1. Create Cinema C1, Cinema C2, Room R1, Room R2, Room R3
        // 2. Add Room R1 to C1 (true)
        // 3. Add Room R2 to C1 (true)
        // 4. Add Room R3 to C2 (true) 
        // 5. Add Room R1 to C1 (false)  
        // Expected Output: true, true, true, false
        
        // Add room1 to cinema1
        boolean result1 = cinema.addRoom(room1);
        assertTrue("Room1 should be added to cinema1", result1);
        assertEquals("Cinema1 should contain 1 room", 1, cinema.getRooms().size());
        
        // Add room2 to cinema1
        boolean result2 = cinema.addRoom(room2);
        assertTrue("Room2 should be added to cinema1", result2);
        assertEquals("Cinema1 should contain 2 rooms", 2, cinema.getRooms().size());
        
        // Add room3 to cinema2
        boolean result3 = cinema2.addRoom(room3);
        assertTrue("Room3 should be added to cinema2", result3);
        assertEquals("Cinema2 should contain 1 room", 1, cinema2.getRooms().size());
        
        // Try to add room1 to cinema1 again (should fail)
        boolean result4 = cinema.addRoom(room1);
        assertFalse("Duplicate room1 addition to cinema1 should fail", result4);
        assertEquals("Cinema1 should still contain 2 rooms", 2, cinema.getRooms().size());
    }
}