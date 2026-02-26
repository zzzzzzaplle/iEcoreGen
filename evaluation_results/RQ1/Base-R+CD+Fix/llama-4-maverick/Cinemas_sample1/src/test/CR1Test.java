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
    
    @Before
    public void setUp() {
        // Initialize cinema and rooms before each test
        cinema = new Cinema();
        room1 = new Room();
        room2 = new Room();
        room3 = new Room();
    }
    
    @Test
    public void testCase1_AddNewRoomToEmptyCinema() {
        // Test Case 1: Add new room to empty cinema
        // Setup: Cinema C1 with empty rooms, Room R1
        boolean result = cinema.addRoom(room1);
        
        // Verify room was added successfully
        assertTrue("Should return true when adding room to empty cinema", result);
        assertTrue("Cinema should contain the added room", cinema.getRooms().contains(room1));
    }
    
    @Test
    public void testCase2_AddDuplicateRoom() {
        // Test Case 2: Add duplicate room
        // Setup: Create Cinema C1, Room R1
        // Add Room R1 to C1 (true)
        boolean firstAdd = cinema.addRoom(room1);
        assertTrue("First addition should return true", firstAdd);
        
        // Add Room R1 to C1 again (false)
        boolean secondAdd = cinema.addRoom(room1);
        assertFalse("Second addition of same room should return false", secondAdd);
        
        // Verify room list size is still 1
        assertEquals("Cinema should still have only one room", 1, cinema.getRooms().size());
    }
    
    @Test
    public void testCase3_AddMultipleRooms() {
        // Test Case 3: Add multiple rooms
        // Setup: Create Cinema C1, Room R1, Room R2
        // Add Room R1 to C1 (true)
        boolean addR1 = cinema.addRoom(room1);
        assertTrue("First room addition should return true", addR1);
        
        // Add Room R2 to C1 (true)
        boolean addR2 = cinema.addRoom(room2);
        assertTrue("Second room addition should return true", addR2);
        
        // Add Room R1 to C1 again (false)
        boolean addR1Again = cinema.addRoom(room1);
        assertFalse("Duplicate room addition should return false", addR1Again);
        
        // Verify room list contains both rooms and has correct size
        assertTrue("Cinema should contain room1", cinema.getRooms().contains(room1));
        assertTrue("Cinema should contain room2", cinema.getRooms().contains(room2));
        assertEquals("Cinema should have exactly 2 rooms", 2, cinema.getRooms().size());
    }
    
    @Test
    public void testCase4_AddMultipleUniqueRooms() {
        // Test Case 4: Add multiple unique rooms
        // Setup: Create Cinema C1
        // Add Room1 to C1 (true)
        boolean addRoom1 = cinema.addRoom(room1);
        assertTrue("First room addition should return true", addRoom1);
        
        // Add Room2 to C1 (true)
        boolean addRoom2 = cinema.addRoom(room2);
        assertTrue("Second room addition should return true", addRoom2);
        
        // Verify both rooms are in cinema
        assertTrue("Cinema should contain room1", cinema.getRooms().contains(room1));
        assertTrue("Cinema should contain room2", cinema.getRooms().contains(room2));
        assertEquals("Cinema should have exactly 2 rooms", 2, cinema.getRooms().size());
    }
    
    @Test
    public void testCase5_AddDuplicateRoomMultipleCinemas() {
        // Test Case 5: Add duplicate room across multiple cinemas
        // Setup: Create Cinema C1, Cinema C2, Room R1, Room R2, Room R3
        Cinema cinema2 = new Cinema();
        
        // Add Room R1 to C1 (true)
        boolean addR1C1 = cinema.addRoom(room1);
        assertTrue("Adding room1 to cinema1 should return true", addR1C1);
        
        // Add Room R2 to C1 (true)
        boolean addR2C1 = cinema.addRoom(room2);
        assertTrue("Adding room2 to cinema1 should return true", addR2C1);
        
        // Add Room R3 to C2 (true)
        boolean addR3C2 = cinema2.addRoom(room3);
        assertTrue("Adding room3 to cinema2 should return true", addR3C2);
        
        // Add Room R1 to C1 (false)
        boolean addR1C1Again = cinema.addRoom(room1);
        assertFalse("Adding duplicate room1 to cinema1 should return false", addR1C1Again);
        
        // Verify cinema1 has correct rooms
        assertTrue("Cinema1 should contain room1", cinema.getRooms().contains(room1));
        assertTrue("Cinema1 should contain room2", cinema.getRooms().contains(room2));
        assertFalse("Cinema1 should not contain room3", cinema.getRooms().contains(room3));
        assertEquals("Cinema1 should have exactly 2 rooms", 2, cinema.getRooms().size());
        
        // Verify cinema2 has correct rooms
        assertTrue("Cinema2 should contain room3", cinema2.getRooms().contains(room3));
        assertFalse("Cinema2 should not contain room1", cinema2.getRooms().contains(room1));
        assertFalse("Cinema2 should not contain room2", cinema2.getRooms().contains(room2));
        assertEquals("Cinema2 should have exactly 1 room", 1, cinema2.getRooms().size());
    }
}