import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;

public class CR1Test {
    
    private Cinema cinema;
    private Room room1;
    private Room room2;
    private Room room3;
    
    @Before
    public void setUp() {
        // Initialize fresh cinema and rooms before each test
        cinema = new Cinema();
        room1 = new Room();
        room1.setId("R1");
        room1.setName("Room 1");
        
        room2 = new Room();
        room2.setId("R2");
        room2.setName("Room 2");
        
        room3 = new Room();
        room3.setId("R3");
        room3.setName("Room 3");
    }
    
    @Test
    public void testCase1_AddNewRoomToEmptyCinema() {
        // Test Case 1: "Add new room to empty cinema"
        // Input: Room R1
        // Setup: Create Cinema C1 with empty rooms, Create Room R1, Add Room R1 to empty cinema
        // Expected Output: true
        
        boolean result = cinema.addRoom(room1);
        assertTrue("Adding room to empty cinema should return true", result);
        assertTrue("Room should be present in cinema after addition", cinema.getRooms().containsKey("R1"));
    }
    
    @Test
    public void testCase2_AddDuplicateRoom() {
        // Test Case 2: "Add duplicate room"
        // Input: Room R1
        // Setup: Create Cinema C1, Room R1, Add Room R1 to C1 (true), Add Room R1 to C1 again (false)
        // Expected Output: true, false
        
        boolean firstAdd = cinema.addRoom(room1);
        assertTrue("First addition of room should return true", firstAdd);
        
        boolean secondAdd = cinema.addRoom(room1);
        assertFalse("Adding duplicate room should return false", secondAdd);
    }
    
    @Test
    public void testCase3_AddMultipleRooms() {
        // Test Case 3: "Add multiple rooms"
        // Input: Room R1
        // Setup: Create Cinema C1, Room R1, Room R2, Add Room R1 to C1 (true), Add Room R2 to C1 (true), Add Room R1 to C1 again (false)
        // Expected Output: true, true, false
        
        boolean addRoom1First = cinema.addRoom(room1);
        assertTrue("First addition of R1 should return true", addRoom1First);
        
        boolean addRoom2 = cinema.addRoom(room2);
        assertTrue("Addition of R2 should return true", addRoom2);
        
        boolean addRoom1Second = cinema.addRoom(room1);
        assertFalse("Second addition of R1 should return false", addRoom1Second);
    }
    
    @Test
    public void testCase4_AddMultipleUniqueRooms() {
        // Test Case 4: "Add multiple unique rooms"
        // Input: Room R2
        // Setup: Create Cinema C1, Add Room1 to C1 (true), Add Room2 to C1 (true)
        // Expected Output: true, true
        
        boolean addRoom1 = cinema.addRoom(room1);
        assertTrue("Addition of R1 should return true", addRoom1);
        
        boolean addRoom2 = cinema.addRoom(room2);
        assertTrue("Addition of R2 should return true", addRoom2);
        
        assertEquals("Cinema should have 2 rooms after adding two unique rooms", 2, cinema.getRooms().size());
    }
    
    @Test
    public void testCase5_AddDuplicateRoomMultipleCinemas() {
        // Test Case 5: "Add duplicate room"
        // Input: Room R1, R2
        // Setup: Create Cinema C1, Cinema C2, Room R1, Room R2, Room R3
        //         Add Room R1 to C1 (true), Add Room R2 to C1 (true), Add Room R3 to C2 (true), Add Room R1 to C1 (false)
        // Expected Output: true, true, true, false
        
        Cinema cinema2 = new Cinema();
        
        boolean addR1ToC1 = cinema.addRoom(room1);
        assertTrue("Adding R1 to C1 should return true", addR1ToC1);
        
        boolean addR2ToC1 = cinema.addRoom(room2);
        assertTrue("Adding R2 to C1 should return true", addR2ToC1);
        
        boolean addR3ToC2 = cinema2.addRoom(room3);
        assertTrue("Adding R3 to C2 should return true", addR3ToC2);
        
        boolean addR1ToC1Again = cinema.addRoom(room1);
        assertFalse("Adding R1 to C1 again should return false", addR1ToC1Again);
    }
}