import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;

public class CR1Test {
    
    private Cinema cinema;
    private Room room1;
    private Room room2;
    private Room room3;
    private Cinema cinema2;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        cinema2 = new Cinema();
        room1 = new Room();
        room2 = new Room();
        room3 = new Room();
    }
    
    @Test
    public void testCase1_AddNewRoomToEmptyCinema() {
        // Test Case 1: "Add new room to empty cinema"
        // Setup: Create Cinema C1 with empty rooms, Create Room R1
        boolean result = cinema.addRoom(room1);
        
        // Expected Output: true
        assertTrue("Adding room to empty cinema should return true", result);
        assertTrue("Cinema should contain the added room", cinema.getRooms().contains(room1));
        assertEquals("Cinema should have exactly 1 room", 1, cinema.getRooms().size());
    }
    
    @Test
    public void testCase2_AddDuplicateRoom() {
        // Test Case 2: "Add duplicate room"
        // Setup: Create Cinema C1, Room R1
        // Add Room R1 to C1 (true)
        boolean firstAdd = cinema.addRoom(room1);
        
        // Add Room R1 to C1 again (false)
        boolean secondAdd = cinema.addRoom(room1);
        
        // Expected Output: true, false
        assertTrue("First addition should return true", firstAdd);
        assertFalse("Second addition of same room should return false", secondAdd);
        assertEquals("Cinema should have exactly 1 room", 1, cinema.getRooms().size());
    }
    
    @Test
    public void testCase3_AddMultipleRooms() {
        // Test Case 3: "Add multiple rooms"
        // Setup: Create Cinema C1, Room R1, Room R2
        // Add Room R1 to C1 (true)
        boolean addR1 = cinema.addRoom(room1);
        
        // Add Room R2 to C1 (true)
        boolean addR2 = cinema.addRoom(room2);
        
        // Add Room R1 to C1 again (false)
        boolean addR1Again = cinema.addRoom(room1);
        
        // Expected Output: true, true, false
        assertTrue("First room addition should return true", addR1);
        assertTrue("Second room addition should return true", addR2);
        assertFalse("Duplicate room addition should return false", addR1Again);
        assertEquals("Cinema should have exactly 2 rooms", 2, cinema.getRooms().size());
    }
    
    @Test
    public void testCase4_AddMultipleUniqueRooms() {
        // Test Case 4: "Add multiple unique rooms"
        // Setup: Create Cinema C1
        // Add Room1 to C1 (true)
        boolean addRoom1 = cinema.addRoom(room1);
        
        // Add Room2 to C1 (true)
        boolean addRoom2 = cinema.addRoom(room2);
        
        // Expected Output: true, true
        assertTrue("First room addition should return true", addRoom1);
        assertTrue("Second room addition should return true", addRoom2);
        assertEquals("Cinema should have exactly 2 rooms", 2, cinema.getRooms().size());
        assertTrue("Cinema should contain room1", cinema.getRooms().contains(room1));
        assertTrue("Cinema should contain room2", cinema.getRooms().contains(room2));
    }
    
    @Test
    public void testCase5_AddDuplicateRoomAcrossMultipleCinemas() {
        // Test Case 5: "Add duplicate room"
        // Setup: Create Cinema C1, Cinema C2, Room R1, Room R2, Room R3
        // Add Room R1 to C1 (true)
        boolean addR1ToC1 = cinema.addRoom(room1);
        
        // Add Room R2 to C1 (true)
        boolean addR2ToC1 = cinema.addRoom(room2);
        
        // Add Room R3 to C2 (true)
        boolean addR3ToC2 = cinema2.addRoom(room3);
        
        // Add Room R1 to C1 (false)
        boolean addR1ToC1Again = cinema.addRoom(room1);
        
        // Expected Output: true, true, true, false
        assertTrue("Adding R1 to C1 should return true", addR1ToC1);
        assertTrue("Adding R2 to C1 should return true", addR2ToC1);
        assertTrue("Adding R3 to C2 should return true", addR3ToC2);
        assertFalse("Adding R1 to C1 again should return false", addR1ToC1Again);
        
        assertEquals("C1 should have exactly 2 rooms", 2, cinema.getRooms().size());
        assertEquals("C2 should have exactly 1 room", 1, cinema2.getRooms().size());
    }
}