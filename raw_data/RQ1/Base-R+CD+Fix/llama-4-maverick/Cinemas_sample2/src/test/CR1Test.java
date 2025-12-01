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
        // Setup: Create Cinema C1 with empty rooms, Create Room R1
        // Add Room R1 to empty cinema
        boolean result = cinema.addRoom(room1);
        
        // Expected Output: true
        assertTrue(result);
        assertTrue(cinema.getRooms().contains(room1));
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
        assertTrue(firstAdd);
        assertFalse(secondAdd);
        assertEquals(1, cinema.getRooms().size());
    }
    
    @Test
    public void testCase3_AddMultipleRooms() {
        // Test Case 3: "Add multiple rooms"
        // Setup: Create Cinema C1, Room R1, Room R2
        // Add Room R1 to C1 (true)
        boolean addRoom1 = cinema.addRoom(room1);
        // Add Room R2 to C1 (true)
        boolean addRoom2 = cinema.addRoom(room2);
        // Add Room R1 to C1 again (false)
        boolean addRoom1Again = cinema.addRoom(room1);
        
        // Expected Output: true, true, false
        assertTrue(addRoom1);
        assertTrue(addRoom2);
        assertFalse(addRoom1Again);
        assertEquals(2, cinema.getRooms().size());
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
        assertTrue(addRoom1);
        assertTrue(addRoom2);
        assertEquals(2, cinema.getRooms().size());
        assertTrue(cinema.getRooms().contains(room1));
        assertTrue(cinema.getRooms().contains(room2));
    }
    
    @Test
    public void testCase5_AddDuplicateRoomToDifferentCinemas() {
        // Test Case 5: "Add duplicate room"
        // Setup: Create Cinema C1, Cinema C2, Room R1, Room R2, Room R3
        Cinema cinema2 = new Cinema();
        
        // Add Room R1 to C1 (true)
        boolean addR1ToC1 = cinema.addRoom(room1);
        // Add Room R2 to C1 (true)
        boolean addR2ToC1 = cinema.addRoom(room2);
        // Add Room R3 to C2 (true)
        boolean addR3ToC2 = cinema2.addRoom(room3);
        // Add Room R1 to C1 (false)
        boolean addR1ToC1Again = cinema.addRoom(room1);
        
        // Expected Output: true, true, true, false
        assertTrue(addR1ToC1);
        assertTrue(addR2ToC1);
        assertTrue(addR3ToC2);
        assertFalse(addR1ToC1Again);
        
        assertEquals(2, cinema.getRooms().size());
        assertEquals(1, cinema2.getRooms().size());
    }
}