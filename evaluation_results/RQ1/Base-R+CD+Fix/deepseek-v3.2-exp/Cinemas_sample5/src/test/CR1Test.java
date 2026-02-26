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
        // Setup: Create Cinema C1 with empty rooms and Room R1
        Cinema c1 = new Cinema();
        Room r1 = new Room();
        
        // Add Room R1 to empty cinema
        boolean result = c1.addRoom(r1);
        
        // Expected Output: true
        assertTrue("Adding new room to empty cinema should return true", result);
        assertTrue("Cinema should contain the added room", c1.getRooms().contains(r1));
    }
    
    @Test
    public void testCase2_AddDuplicateRoom() {
        // Setup: Create Cinema C1, Room R1
        Cinema c1 = new Cinema();
        Room r1 = new Room();
        
        // Add Room R1 to C1 (true)
        boolean firstAdd = c1.addRoom(r1);
        
        // Add Room R1 to C1 again (false)
        boolean secondAdd = c1.addRoom(r1);
        
        // Expected Output: true, false
        assertTrue("First addition of room should return true", firstAdd);
        assertFalse("Second addition of same room should return false", secondAdd);
        assertEquals("Cinema should contain only one room", 1, c1.getRooms().size());
    }
    
    @Test
    public void testCase3_AddMultipleRooms() {
        // Setup: Create Cinema C1, Room R1, Room R2
        Cinema c1 = new Cinema();
        Room r1 = new Room();
        Room r2 = new Room();
        
        // Add Room R1 to C1 (true)
        boolean addR1 = c1.addRoom(r1);
        
        // Add Room R2 to C1 (true)
        boolean addR2 = c1.addRoom(r2);
        
        // Add Room R1 to C1 again (false)
        boolean addR1Again = c1.addRoom(r1);
        
        // Expected Output: true, true, false
        assertTrue("First room addition should return true", addR1);
        assertTrue("Second room addition should return true", addR2);
        assertFalse("Duplicate room addition should return false", addR1Again);
        assertEquals("Cinema should contain exactly 2 rooms", 2, c1.getRooms().size());
    }
    
    @Test
    public void testCase4_AddMultipleUniqueRooms() {
        // Setup: Create Cinema C1
        Cinema c1 = new Cinema();
        Room room1 = new Room();
        Room room2 = new Room();
        
        // Add Room1 to C1 (true)
        boolean addRoom1 = c1.addRoom(room1);
        
        // Add Room2 to C1 (true)
        boolean addRoom2 = c1.addRoom(room2);
        
        // Expected Output: true, true
        assertTrue("First room addition should return true", addRoom1);
        assertTrue("Second room addition should return true", addRoom2);
        assertEquals("Cinema should contain exactly 2 rooms", 2, c1.getRooms().size());
        assertTrue("Cinema should contain room1", c1.getRooms().contains(room1));
        assertTrue("Cinema should contain room2", c1.getRooms().contains(room2));
    }
    
    @Test
    public void testCase5_AddDuplicateRoomAcrossMultipleCinemas() {
        // Setup: Create Cinema C1, Cinema C2, Room R1, Room R2, Room R3
        Cinema c1 = new Cinema();
        Cinema c2 = new Cinema();
        Room r1 = new Room();
        Room r2 = new Room();
        Room r3 = new Room();
        
        // Add Room R1 to C1 (true)
        boolean addR1ToC1 = c1.addRoom(r1);
        
        // Add Room R2 to C1 (true)
        boolean addR2ToC1 = c1.addRoom(r2);
        
        // Add Room R3 to C2 (true)
        boolean addR3ToC2 = c2.addRoom(r3);
        
        // Add Room R1 to C1 (false)
        boolean addR1ToC1Again = c1.addRoom(r1);
        
        // Expected Output: true, true, true, false
        assertTrue("Adding R1 to C1 should return true", addR1ToC1);
        assertTrue("Adding R2 to C1 should return true", addR2ToC1);
        assertTrue("Adding R3 to C2 should return true", addR3ToC2);
        assertFalse("Adding duplicate R1 to C1 should return false", addR1ToC1Again);
        
        assertEquals("C1 should contain exactly 2 rooms", 2, c1.getRooms().size());
        assertEquals("C2 should contain exactly 1 room", 1, c2.getRooms().size());
    }
}