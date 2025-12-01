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
        cinema = new Cinema();
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
        
        boolean result = cinema.addRoom(room1);
        assertTrue("Adding new room to empty cinema should return true", result);
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
        
        boolean firstAdd = cinema.addRoom(room1);
        assertTrue("First addition of room should return true", firstAdd);
        
        boolean secondAdd = cinema.addRoom(room1);
        assertFalse("Adding duplicate room should return false", secondAdd);
        
        assertEquals("Cinema should contain exactly 1 room after duplicate addition", 
                     1, cinema.getRooms().size());
    }
    
    @Test
    public void testCase3_AddMultipleRooms() {
        // Test Case 3: "Add multiple rooms"
        // Input: Room R1
        // Setup:
        // 1. Create Cinema C1, Room R1, Room R2
        // 2. Add Room R1 to C1 (true)
        // 3. Add Room R2 to C1 (true)
        // 4. Add Room R1 to C1 again (false)
        // Expected Output: true, true, false
        
        boolean addRoom1 = cinema.addRoom(room1);
        assertTrue("First addition of room1 should return true", addRoom1);
        
        boolean addRoom2 = cinema.addRoom(room2);
        assertTrue("Addition of room2 should return true", addRoom2);
        
        boolean addRoom1Again = cinema.addRoom(room1);
        assertFalse("Adding duplicate room1 should return false", addRoom1Again);
        
        assertEquals("Cinema should contain exactly 2 rooms", 2, cinema.getRooms().size());
        assertTrue("Cinema should contain room1", cinema.getRooms().contains(room1));
        assertTrue("Cinema should contain room2", cinema.getRooms().contains(room2));
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
        
        boolean addRoom1 = cinema.addRoom(room1);
        assertTrue("Addition of room1 should return true", addRoom1);
        
        boolean addRoom2 = cinema.addRoom(room2);
        assertTrue("Addition of room2 should return true", addRoom2);
        
        assertEquals("Cinema should contain exactly 2 rooms", 2, cinema.getRooms().size());
        assertTrue("Cinema should contain room1", cinema.getRooms().contains(room1));
        assertTrue("Cinema should contain room2", cinema.getRooms().contains(room2));
    }
    
    @Test
    public void testCase5_AddDuplicateRoomAcrossCinemas() {
        // Test Case 5: "Add duplicate room"
        // Input: Room R1, R2
        // Setup:
        // 1. Create Cinema C1, Cinema C2, Room R1, Room R2, Room R3
        // 2. Add Room R1 to C1 (true)
        // 3. Add Room R2 to C1 (true)
        // 4. Add Room R3 to C2 (true) 
        // 5. Add Room R1 to C1 (false)  
        // Expected Output: true, true, true, false.
        
        Cinema cinema2 = new Cinema();
        
        boolean addRoom1ToCinema1 = cinema.addRoom(room1);
        assertTrue("Adding room1 to cinema1 should return true", addRoom1ToCinema1);
        
        boolean addRoom2ToCinema1 = cinema.addRoom(room2);
        assertTrue("Adding room2 to cinema1 should return true", addRoom2ToCinema1);
        
        boolean addRoom3ToCinema2 = cinema2.addRoom(room3);
        assertTrue("Adding room3 to cinema2 should return true", addRoom3ToCinema2);
        
        boolean addRoom1ToCinema1Again = cinema.addRoom(room1);
        assertFalse("Adding duplicate room1 to cinema1 should return false", addRoom1ToCinema1Again);
        
        assertEquals("Cinema1 should contain exactly 2 rooms", 2, cinema.getRooms().size());
        assertEquals("Cinema2 should contain exactly 1 room", 1, cinema2.getRooms().size());
        assertTrue("Cinema1 should contain room1", cinema.getRooms().contains(room1));
        assertTrue("Cinema1 should contain room2", cinema.getRooms().contains(room2));
        assertTrue("Cinema2 should contain room3", cinema2.getRooms().contains(room3));
    }
}