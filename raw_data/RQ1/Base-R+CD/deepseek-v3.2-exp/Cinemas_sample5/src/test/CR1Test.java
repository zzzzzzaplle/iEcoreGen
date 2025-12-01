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
        // Setup: Create Cinema C1 with empty rooms, Create Room R1, Add Room R1 to empty cinema
        // Expected Output: true
        
        // Act: Add room1 to empty cinema
        boolean result = cinema.addRoom(room1);
        
        // Assert: Should return true and cinema should contain the room
        assertTrue("Adding new room to empty cinema should return true", result);
        assertTrue("Cinema should contain the added room", cinema.getRooms().contains(room1));
        assertEquals("Cinema should have exactly 1 room", 1, cinema.getRooms().size());
    }
    
    @Test
    public void testCase2_AddDuplicateRoom() {
        // Test Case 2: "Add duplicate room"
        // Input: Room R1
        // Setup: Create Cinema C1, Room R1, Add Room R1 to C1 (true), Add Room R1 to C1 again (false)
        // Expected Output: true, false
        
        // Act: Add room1 twice
        boolean firstAdd = cinema.addRoom(room1);
        boolean secondAdd = cinema.addRoom(room1);
        
        // Assert: First add should succeed, second should fail
        assertTrue("First addition of room should return true", firstAdd);
        assertFalse("Adding duplicate room should return false", secondAdd);
        assertEquals("Cinema should have exactly 1 room", 1, cinema.getRooms().size());
    }
    
    @Test
    public void testCase3_AddMultipleRooms() {
        // Test Case 3: "Add multiple rooms"
        // Input: Room R1
        // Setup: Create Cinema C1, Room R1, Room R2, Add Room R1 to C1 (true), Add Room R2 to C1 (true), Add Room R1 to C1 again (false)
        // Expected Output: true, true, false
        
        // Act: Add multiple rooms including duplicate
        boolean addRoom1 = cinema.addRoom(room1);
        boolean addRoom2 = cinema.addRoom(room2);
        boolean addRoom1Again = cinema.addRoom(room1);
        
        // Assert: First two adds should succeed, third should fail
        assertTrue("First room addition should return true", addRoom1);
        assertTrue("Second room addition should return true", addRoom2);
        assertFalse("Adding duplicate room should return false", addRoom1Again);
        assertEquals("Cinema should have exactly 2 rooms", 2, cinema.getRooms().size());
        assertTrue("Cinema should contain room1", cinema.getRooms().contains(room1));
        assertTrue("Cinema should contain room2", cinema.getRooms().contains(room2));
    }
    
    @Test
    public void testCase4_AddMultipleUniqueRooms() {
        // Test Case 4: "Add multiple unique rooms"
        // Input: Room R2
        // Setup: Create Cinema C1, Add Room1 to C1 (true), Add Room2 to C1 (true)
        // Expected Output: true, true
        
        // Act: Add two unique rooms
        boolean addRoom1 = cinema.addRoom(room1);
        boolean addRoom2 = cinema.addRoom(room2);
        
        // Assert: Both additions should succeed
        assertTrue("First room addition should return true", addRoom1);
        assertTrue("Second room addition should return true", addRoom2);
        assertEquals("Cinema should have exactly 2 rooms", 2, cinema.getRooms().size());
        assertTrue("Cinema should contain room1", cinema.getRooms().contains(room1));
        assertTrue("Cinema should contain room2", cinema.getRooms().contains(room2));
    }
    
    @Test
    public void testCase5_AddDuplicateRoomAcrossMultipleCinemas() {
        // Test Case 5: "Add duplicate room"
        // Input: Room R1, R2
        // Setup: Create Cinema C1, Cinema C2, Room R1, Room R2, Room R3
        // Add Room R1 to C1 (true), Add Room R2 to C1 (true), Add Room R3 to C2 (true), Add Room R1 to C1 (false)
        // Expected Output: true, true, true, false
        
        // Act: Add rooms to different cinemas
        boolean addR1ToC1 = cinema.addRoom(room1);
        boolean addR2ToC1 = cinema.addRoom(room2);
        boolean addR3ToC2 = cinema2.addRoom(room3);
        boolean addR1ToC1Again = cinema.addRoom(room1);
        
        // Assert: First three adds should succeed, fourth should fail
        assertTrue("Adding room1 to cinema1 should return true", addR1ToC1);
        assertTrue("Adding room2 to cinema1 should return true", addR2ToC1);
        assertTrue("Adding room3 to cinema2 should return true", addR3ToC2);
        assertFalse("Adding duplicate room1 to cinema1 should return false", addR1ToC1Again);
        
        assertEquals("Cinema1 should have exactly 2 rooms", 2, cinema.getRooms().size());
        assertEquals("Cinema2 should have exactly 1 room", 1, cinema2.getRooms().size());
        assertTrue("Cinema1 should contain room1", cinema.getRooms().contains(room1));
        assertTrue("Cinema1 should contain room2", cinema.getRooms().contains(room2));
        assertTrue("Cinema2 should contain room3", cinema2.getRooms().contains(room3));
    }
}