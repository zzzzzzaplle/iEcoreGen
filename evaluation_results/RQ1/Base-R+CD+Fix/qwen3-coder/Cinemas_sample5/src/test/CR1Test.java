import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR1Test {
    private Cinema cinema;
    private Room room1;
    private Room room2;
    private Room room3;

    @Before
    public void setUp() {
        // Initialize test objects before each test
        cinema = new Cinema();
        room1 = new Room();
        room2 = new Room();
        room3 = new Room();
    }

    @Test
    public void testCase1_AddNewRoomToEmptyCinema() {
        // Test Case 1: "Add new room to empty cinema"
        // Input: Room R1
        // Setup: Create Cinema C1 with empty rooms, Create Room R1, Add Room R1 to empty cinema
        // Expected Output: true
        
        boolean result = cinema.addRoom(room1);
        assertTrue("Should return true when adding new room to empty cinema", result);
        assertTrue("Room should be in cinema's room list", cinema.getRooms().contains(room1));
    }

    @Test
    public void testCase2_AddDuplicateRoom() {
        // Test Case 2: "Add duplicate room"
        // Input: Room R1
        // Setup: Create Cinema C1, Room R1, Add Room R1 to C1 (true), Add Room R1 to C1 again (false)
        // Expected Output: true, false
        
        boolean firstAdd = cinema.addRoom(room1);
        boolean secondAdd = cinema.addRoom(room1);
        
        assertTrue("First add should return true", firstAdd);
        assertFalse("Second add should return false for duplicate room", secondAdd);
        assertEquals("Cinema should contain only one room", 1, cinema.getRooms().size());
    }

    @Test
    public void testCase3_AddMultipleRooms() {
        // Test Case 3: "Add multiple rooms"
        // Input: Room R1
        // Setup: Create Cinema C1, Room R1, Room R2, Add Room R1 to C1 (true), Add Room R2 to C1 (true), Add Room R1 to C1 again (false)
        // Expected Output: true, true, false
        
        boolean addR1 = cinema.addRoom(room1);
        boolean addR2 = cinema.addRoom(room2);
        boolean addR1Again = cinema.addRoom(room1);
        
        assertTrue("First add of R1 should return true", addR1);
        assertTrue("Add of R2 should return true", addR2);
        assertFalse("Second add of R1 should return false", addR1Again);
        assertEquals("Cinema should contain exactly 2 rooms", 2, cinema.getRooms().size());
    }

    @Test
    public void testCase4_AddMultipleUniqueRooms() {
        // Test Case 4: "Add multiple unique rooms"
        // Input: Room R2
        // Setup: Create Cinema C1, Add Room1 to C1 (true), Add Room2 to C1 (true)
        // Expected Output: true, true
        
        boolean addRoom1 = cinema.addRoom(room1);
        boolean addRoom2 = cinema.addRoom(room2);
        
        assertTrue("Add Room1 should return true", addRoom1);
        assertTrue("Add Room2 should return true", addRoom2);
        assertEquals("Cinema should contain exactly 2 rooms", 2, cinema.getRooms().size());
        assertTrue("Cinema should contain Room1", cinema.getRooms().contains(room1));
        assertTrue("Cinema should contain Room2", cinema.getRooms().contains(room2));
    }

    @Test
    public void testCase5_AddDuplicateRoomAcrossMultipleCinemas() {
        // Test Case 5: "Add duplicate room"
        // Input: Room R1, R2
        // Setup: Create Cinema C1, Cinema C2, Room R1, Room R2, Room R3
        // Add Room R1 to C1 (true), Add Room R2 to C1 (true), Add Room R3 to C2 (true), Add Room R1 to C1 (false)
        // Expected Output: true, true, true, false
        
        Cinema cinema2 = new Cinema();
        
        boolean addR1ToC1 = cinema.addRoom(room1);
        boolean addR2ToC1 = cinema.addRoom(room2);
        boolean addR3ToC2 = cinema2.addRoom(room3);
        boolean addR1ToC1Again = cinema.addRoom(room1);
        
        assertTrue("Add R1 to C1 should return true", addR1ToC1);
        assertTrue("Add R2 to C1 should return true", addR2ToC1);
        assertTrue("Add R3 to C2 should return true", addR3ToC2);
        assertFalse("Add R1 to C1 again should return false", addR1ToC1Again);
        
        assertEquals("Cinema1 should contain exactly 2 rooms", 2, cinema.getRooms().size());
        assertEquals("Cinema2 should contain exactly 1 room", 1, cinema2.getRooms().size());
    }
}