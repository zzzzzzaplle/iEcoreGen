import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    public void testCase1_AddNewRoomToEmptyCinema() {
        // Test Case 1: "Add new room to empty cinema"
        // Setup: Create Cinema C1 with empty rooms, Create Room R1
        // Add Room R1 to empty cinema
        boolean result = cinema.addRoom(room1);
        
        // Expected Output: true
        assertTrue("Adding new room to empty cinema should return true", result);
        assertTrue("Room should be added to cinema", cinema.getRooms().contains(room1));
    }
    
    @Test
    public void testCase2_AddDuplicateRoom() {
        // Test Case 2: "Add duplicate room"
        // Setup: Create Cinema C1, Room R1
        // Add Room R1 to C1 (true)
        boolean firstAdd = cinema.addRoom(room1);
        assertTrue("First add should succeed", firstAdd);
        
        // Add Room R1 to C1 again (false)
        boolean secondAdd = cinema.addRoom(room1);
        
        // Expected Output: true, false
        assertFalse("Adding duplicate room should return false", secondAdd);
        assertEquals("Cinema should contain only one room", 1, cinema.getRooms().size());
    }
    
    @Test
    public void testCase3_AddMultipleRooms() {
        // Test Case 3: "Add multiple rooms"
        // Setup: Create Cinema C1, Room R1, Room R2
        // Add Room R1 to C1 (true)
        boolean addR1 = cinema.addRoom(room1);
        assertTrue("Adding R1 should succeed", addR1);
        
        // Add Room R2 to C1 (true)
        boolean addR2 = cinema.addRoom(room2);
        assertTrue("Adding R2 should succeed", addR2);
        
        // Add Room R1 to C1 again (false)
        boolean addR1Again = cinema.addRoom(room1);
        
        // Expected Output: true, true, false
        assertFalse("Adding R1 again should fail", addR1Again);
        assertEquals("Cinema should contain exactly 2 rooms", 2, cinema.getRooms().size());
        assertTrue("Cinema should contain R1", cinema.getRooms().contains(room1));
        assertTrue("Cinema should contain R2", cinema.getRooms().contains(room2));
    }
    
    @Test
    public void testCase4_AddMultipleUniqueRooms() {
        // Test Case 4: "Add multiple unique rooms"
        // Setup: Create Cinema C1
        Room room1 = new Room("Room1");
        Room room2 = new Room("Room2");
        
        // Add Room1 to C1 (true)
        boolean addRoom1 = cinema.addRoom(room1);
        
        // Add Room2 to C1 (true)
        boolean addRoom2 = cinema.addRoom(room2);
        
        // Expected Output: true, true
        assertTrue("Adding Room1 should succeed", addRoom1);
        assertTrue("Adding Room2 should succeed", addRoom2);
        assertEquals("Cinema should contain exactly 2 rooms", 2, cinema.getRooms().size());
        assertTrue("Cinema should contain Room1", cinema.getRooms().contains(room1));
        assertTrue("Cinema should contain Room2", cinema.getRooms().contains(room2));
    }
    
    @Test
    public void testCase5_AddDuplicateRoomAcrossCinemas() {
        // Test Case 5: "Add duplicate room"
        // Setup: Create Cinema C1, Cinema C2, Room R1, Room R2, Room R3
        Cinema cinema2 = new Cinema();
        
        // Add Room R1 to C1 (true)
        boolean addR1ToC1 = cinema.addRoom(room1);
        assertTrue("Adding R1 to C1 should succeed", addR1ToC1);
        
        // Add Room R2 to C1 (true)
        boolean addR2ToC1 = cinema.addRoom(room2);
        assertTrue("Adding R2 to C1 should succeed", addR2ToC1);
        
        // Add Room R3 to C2 (true)
        boolean addR3ToC2 = cinema2.addRoom(room3);
        assertTrue("Adding R3 to C2 should succeed", addR3ToC2);
        
        // Add Room R1 to C1 (false)
        boolean addR1ToC1Again = cinema.addRoom(room1);
        
        // Expected Output: true, true, true, false
        assertFalse("Adding R1 to C1 again should fail", addR1ToC1Again);
        assertEquals("C1 should contain exactly 2 rooms", 2, cinema.getRooms().size());
        assertEquals("C2 should contain exactly 1 room", 1, cinema2.getRooms().size());
    }
}