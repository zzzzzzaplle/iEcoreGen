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
        // Setup common test objects before each test
        cinema = new Cinema();
        room1 = new Room();
        room2 = new Room();
        room3 = new Room();
    }
    
    @Test
    public void testCase1_AddNewRoomToEmptyCinema() {
        // Test Case 1: Add new room to empty cinema
        // Input: Room R1
        // Setup: Create Cinema C1 with empty rooms, Create Room R1
        boolean result = cinema.addRoom(room1);
        
        // Expected Output: true
        assertTrue("Adding room to empty cinema should return true", result);
        assertTrue("Cinema should contain the added room", cinema.getRooms().contains(room1));
    }
    
    @Test
    public void testCase2_AddDuplicateRoom() {
        // Test Case 2: Add duplicate room
        // Input: Room R1
        // Setup: Create Cinema C1, Room R1
        boolean firstAdd = cinema.addRoom(room1);
        boolean secondAdd = cinema.addRoom(room1);
        
        // Expected Output: true, false
        assertTrue("First addition of room should return true", firstAdd);
        assertFalse("Second addition of same room should return false", secondAdd);
        assertEquals("Cinema should contain only one room", 1, cinema.getRooms().size());
    }
    
    @Test
    public void testCase3_AddMultipleRooms() {
        // Test Case 3: Add multiple rooms
        // Input: Room R1
        // Setup: Create Cinema C1, Room R1, Room R2
        boolean addRoom1 = cinema.addRoom(room1);
        boolean addRoom2 = cinema.addRoom(room2);
        boolean addRoom1Again = cinema.addRoom(room1);
        
        // Expected Output: true, true, false
        assertTrue("First room addition should return true", addRoom1);
        assertTrue("Second room addition should return true", addRoom2);
        assertFalse("Duplicate room addition should return false", addRoom1Again);
        assertEquals("Cinema should contain exactly 2 rooms", 2, cinema.getRooms().size());
    }
    
    @Test
    public void testCase4_AddMultipleUniqueRooms() {
        // Test Case 4: Add multiple unique rooms
        // Input: Room R2
        // Setup: Create Cinema C1, Add Room1 to C1, Add Room2 to C1
        boolean addRoom1 = cinema.addRoom(room1);
        boolean addRoom2 = cinema.addRoom(room2);
        
        // Expected Output: true, true
        assertTrue("First unique room addition should return true", addRoom1);
        assertTrue("Second unique room addition should return true", addRoom2);
        assertEquals("Cinema should contain exactly 2 rooms", 2, cinema.getRooms().size());
        assertTrue("Cinema should contain room1", cinema.getRooms().contains(room1));
        assertTrue("Cinema should contain room2", cinema.getRooms().contains(room2));
    }
    
    @Test
    public void testCase5_AddDuplicateRoomAcrossMultipleCinemas() {
        // Test Case 5: Add duplicate room
        // Input: Room R1, R2
        // Setup: Create Cinema C1, Cinema C2, Room R1, Room R2, Room R3
        Cinema cinema2 = new Cinema();
        
        boolean addRoom1ToC1 = cinema.addRoom(room1);
        boolean addRoom2ToC1 = cinema.addRoom(room2);
        boolean addRoom3ToC2 = cinema2.addRoom(room3);
        boolean addRoom1ToC1Again = cinema.addRoom(room1);
        
        // Expected Output: true, true, true, false
        assertTrue("Adding room1 to cinema1 should return true", addRoom1ToC1);
        assertTrue("Adding room2 to cinema1 should return true", addRoom2ToC1);
        assertTrue("Adding room3 to cinema2 should return true", addRoom3ToC2);
        assertFalse("Adding duplicate room1 to cinema1 should return false", addRoom1ToC1Again);
        
        assertEquals("Cinema1 should contain exactly 2 rooms", 2, cinema.getRooms().size());
        assertEquals("Cinema2 should contain exactly 1 room", 1, cinema2.getRooms().size());
    }
}