import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.HashSet;
import java.util.Set;

public class CR1Test {
    
    private Cinema cinema;
    private Room room1;
    private Room room2;
    private Room room3;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        room1 = new Room("R1");
        room2 = new Room("R2");
        room3 = new Room("R3");
    }
    
    @Test
    public void testCase1_addNewRoomToEmptyCinema() {
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
    public void testCase2_addDuplicateRoom() {
        // Test Case 2: "Add duplicate room"
        // Input: Room R1
        // Setup:
        // 1. Create Cinema C1, Room R1
        // 2. Add Room R1 to C1 (true)
        // 3. Add Room R1 to C1 again (false)
        // Expected Output: true, false
        
        boolean firstAdd = cinema.addRoom(room1);
        boolean secondAdd = cinema.addRoom(room1);
        
        assertTrue("First addition of room should return true", firstAdd);
        assertFalse("Second addition of same room should return false", secondAdd);
    }
    
    @Test
    public void testCase3_addMultipleRooms() {
        // Test Case 3: "Add multiple rooms"
        // Input:  Room R1
        // Setup:
        // 1. Create Cinema C1, Room R1, Room R2
        // 2. Add Room R1 to C1 (true)
        // 3. Add Room R2 to C1 (true)
        // 4. Add Room R1 to C1 again (false)
        // Expected Output: true, true, false
        
        boolean addRoom1First = cinema.addRoom(room1);
        boolean addRoom2 = cinema.addRoom(room2);
        boolean addRoom1Second = cinema.addRoom(room1);
        
        assertTrue("First addition of R1 should return true", addRoom1First);
        assertTrue("Addition of R2 should return true", addRoom2);
        assertFalse("Second addition of R1 should return false", addRoom1Second);
    }
    
    @Test
    public void testCase4_addMultipleUniqueRooms() {
        // Test Case 4: "Add multiple unique rooms"
        // Input: Room R2
        // Setup:
        // 1. Create Cinema C1
        // 2. Add Room1 to C1 (true)
        // 3. Add Room2 to C1 (true)
        // Expected Output: true, true
        
        boolean addRoom1 = cinema.addRoom(room1);
        boolean addRoom2 = cinema.addRoom(room2);
        
        assertTrue("Addition of R1 should return true", addRoom1);
        assertTrue("Addition of R2 should return true", addRoom2);
        assertEquals("Cinema should have exactly 2 rooms", 2, cinema.getRooms().size());
        assertTrue("Cinema should contain R1", cinema.getRooms().contains(room1));
        assertTrue("Cinema should contain R2", cinema.getRooms().contains(room2));
    }
    
    @Test
    public void testCase5_addDuplicateRoomAcrossCinemas() {
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
        
        boolean addR1ToC1 = cinema.addRoom(room1);
        boolean addR2ToC1 = cinema.addRoom(room2);
        boolean addR3ToC2 = cinema2.addRoom(room3);
        boolean addR1ToC1Again = cinema.addRoom(room1);
        
        assertTrue("Adding R1 to C1 should return true", addR1ToC1);
        assertTrue("Adding R2 to C1 should return true", addR2ToC1);
        assertTrue("Adding R3 to C2 should return true", addR3ToC2);
        assertFalse("Adding R1 to C1 again should return false", addR1ToC1Again);
    }
}