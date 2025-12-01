import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import com.cinema.Cinema;
import com.cinema.Room;

public class CR1Test {
    
    private Cinema cinema;
    private Room room1;
    private Room room2;
    private Room room3;
    
    @Before
    public void setUp() {
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
        // Setup: 
        // 1. Create Cinema C1 with empty rooms
        // 2. Create Room R1
        // 3. Add Room R1 to empty cinema
        // Expected Output: true
        
        boolean result = cinema.addRoom(room1);
        assertTrue("Adding room to empty cinema should return true", result);
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
        
        assertEquals("Cinema should contain only one room", 1, cinema.getRooms().size());
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
        
        boolean addRoom1 = cinema.addRoom(room1);
        assertTrue("First addition of room R1 should return true", addRoom1);
        
        boolean addRoom2 = cinema.addRoom(room2);
        assertTrue("Addition of room R2 should return true", addRoom2);
        
        boolean addRoom1Again = cinema.addRoom(room1);
        assertFalse("Adding duplicate room R1 should return false", addRoom1Again);
        
        assertEquals("Cinema should contain exactly 2 rooms", 2, cinema.getRooms().size());
        assertTrue("Cinema should contain room R1", cinema.getRooms().contains(room1));
        assertTrue("Cinema should contain room R2", cinema.getRooms().contains(room2));
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
        assertTrue("Addition of room R1 should return true", addRoom1);
        
        boolean addRoom2 = cinema.addRoom(room2);
        assertTrue("Addition of room R2 should return true", addRoom2);
        
        assertEquals("Cinema should contain exactly 2 rooms", 2, cinema.getRooms().size());
        assertTrue("Cinema should contain room R1", cinema.getRooms().contains(room1));
        assertTrue("Cinema should contain room R2", cinema.getRooms().contains(room2));
    }
    
    @Test
    public void testCase5_AddDuplicateRoomAcrossMultipleCinemas() {
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
        
        boolean addRoom1ToC1 = cinema.addRoom(room1);
        assertTrue("Adding room R1 to cinema C1 should return true", addRoom1ToC1);
        
        boolean addRoom2ToC1 = cinema.addRoom(room2);
        assertTrue("Adding room R2 to cinema C1 should return true", addRoom2ToC1);
        
        boolean addRoom3ToC2 = cinema2.addRoom(room3);
        assertTrue("Adding room R3 to cinema C2 should return true", addRoom3ToC2);
        
        boolean addRoom1ToC1Again = cinema.addRoom(room1);
        assertFalse("Adding duplicate room R1 to cinema C1 should return false", addRoom1ToC1Again);
        
        assertEquals("Cinema C1 should contain exactly 2 rooms", 2, cinema.getRooms().size());
        assertEquals("Cinema C2 should contain exactly 1 room", 1, cinema2.getRooms().size());
    }
}