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
        // Test Case 1: Add new room to empty cinema
        // Setup: Create Cinema C1 with empty rooms, Create Room R1
        // Expected Output: true
        boolean result = cinema.addRoom(room1);
        assertTrue("Adding room to empty cinema should return true", result);
    }
    
    @Test
    public void testCase2_AddDuplicateRoom() {
        // Test Case 2: Add duplicate room
        // Setup: Create Cinema C1, Room R1, Add Room R1 to C1 (true), Add Room R1 to C1 again (false)
        // Expected Output: true, false
        boolean firstAdd = cinema.addRoom(room1);
        boolean secondAdd = cinema.addRoom(room1);
        
        assertTrue("First addition should return true", firstAdd);
        assertFalse("Second addition should return false", secondAdd);
    }
    
    @Test
    public void testCase3_AddMultipleRooms() {
        // Test Case 3: Add multiple rooms
        // Setup: Create Cinema C1, Room R1, Room R2
        // Add Room R1 to C1 (true), Add Room R2 to C1 (true), Add Room R1 to C1 again (false)
        // Expected Output: true, true, false
        boolean firstAdd = cinema.addRoom(room1);
        boolean secondAdd = cinema.addRoom(room2);
        boolean thirdAdd = cinema.addRoom(room1);
        
        assertTrue("First addition (R1) should return true", firstAdd);
        assertTrue("Second addition (R2) should return true", secondAdd);
        assertFalse("Third addition (R1 again) should return false", thirdAdd);
    }
    
    @Test
    public void testCase4_AddMultipleUniqueRooms() {
        // Test Case 4: Add multiple unique rooms
        // Setup: Create Cinema C1, Add Room1 to C1 (true), Add Room2 to C1 (true)
        // Expected Output: true, true
        boolean firstAdd = cinema.addRoom(room1);
        boolean secondAdd = cinema.addRoom(room2);
        
        assertTrue("First addition should return true", firstAdd);
        assertTrue("Second addition should return true", secondAdd);
    }
    
    @Test
    public void testCase5_AddDuplicateRoomToDifferentCinemas() {
        // Test Case 5: Add duplicate room
        // Setup: Create Cinema C1, Cinema C2, Room R1, Room R2, Room R3
        // Add Room R1 to C1 (true), Add Room R2 to C1 (true), Add Room R3 to C2 (true), Add Room R1 to C1 (false)
        // Expected Output: true, true, true, false
        Cinema cinema2 = new Cinema();
        
        boolean addR1toC1 = cinema.addRoom(room1);
        boolean addR2toC1 = cinema.addRoom(room2);
        boolean addR3toC2 = cinema2.addRoom(room3);
        boolean addR1toC1Again = cinema.addRoom(room1);
        
        assertTrue("Adding R1 to C1 should return true", addR1toC1);
        assertTrue("Adding R2 to C1 should return true", addR2toC1);
        assertTrue("Adding R3 to C2 should return true", addR3toC2);
        assertFalse("Adding R1 to C1 again should return false", addR1toC1Again);
    }
}