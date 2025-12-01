import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR1Test {
    
    private Cinema cinema;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_addNewRoomToEmptyCinema() throws Exception {
        // Test Case 1: "Add new room to empty cinema"
        // Setup: Create Cinema C1 with empty rooms, Create Room R1
        Room roomR1 = new Room("R1");
        
        // Add Room R1 to empty cinema
        boolean result = cinema.addRoom(roomR1);
        
        // Expected Output: true
        assertTrue("Should return true when adding new room to empty cinema", result);
        assertTrue("Cinema should contain the added room", cinema.getRooms().contains(roomR1));
    }
    
    @Test
    public void testCase2_addDuplicateRoom() throws Exception {
        // Test Case 2: "Add duplicate room"
        // Setup: Create Cinema C1, Room R1
        Cinema cinemaC1 = new Cinema();
        Room roomR1 = new Room("R1");
        
        // Add Room R1 to C1 (true)
        boolean firstAdd = cinemaC1.addRoom(roomR1);
        
        // Add Room R1 to C1 again (false)
        boolean secondAdd = cinemaC1.addRoom(roomR1);
        
        // Expected Output: true, false
        assertTrue("First addition should return true", firstAdd);
        assertFalse("Second addition should return false", secondAdd);
        assertEquals("Cinema should contain exactly one room", 1, cinemaC1.getRooms().size());
    }
    
    @Test
    public void testCase3_addMultipleRooms() throws Exception {
        // Test Case 3: "Add multiple rooms"
        // Setup: Create Cinema C1, Room R1, Room R2
        Cinema cinemaC1 = new Cinema();
        Room roomR1 = new Room("R1");
        Room roomR2 = new Room("R2");
        
        // Add Room R1 to C1 (true)
        boolean addR1 = cinemaC1.addRoom(roomR1);
        
        // Add Room R2 to C1 (true)
        boolean addR2 = cinemaC1.addRoom(roomR2);
        
        // Add Room R1 to C1 again (false)
        boolean addR1Again = cinemaC1.addRoom(roomR1);
        
        // Expected Output: true, true, false
        assertTrue("First addition of R1 should return true", addR1);
        assertTrue("Addition of R2 should return true", addR2);
        assertFalse("Second addition of R1 should return false", addR1Again);
        assertEquals("Cinema should contain exactly two rooms", 2, cinemaC1.getRooms().size());
    }
    
    @Test
    public void testCase4_addMultipleUniqueRooms() throws Exception {
        // Test Case 4: "Add multiple unique rooms"
        // Setup: Create Cinema C1
        Cinema cinemaC1 = new Cinema();
        Room room1 = new Room("Room1");
        Room room2 = new Room("Room2");
        
        // Add Room1 to C1 (true)
        boolean addRoom1 = cinemaC1.addRoom(room1);
        
        // Add Room2 to C1 (true)
        boolean addRoom2 = cinemaC1.addRoom(room2);
        
        // Expected Output: true, true
        assertTrue("Addition of Room1 should return true", addRoom1);
        assertTrue("Addition of Room2 should return true", addRoom2);
        assertEquals("Cinema should contain exactly two rooms", 2, cinemaC1.getRooms().size());
        assertTrue("Cinema should contain Room1", cinemaC1.getRooms().contains(room1));
        assertTrue("Cinema should contain Room2", cinemaC1.getRooms().contains(room2));
    }
    
    @Test
    public void testCase5_addDuplicateRoomAcrossCinemas() throws Exception {
        // Test Case 5: "Add duplicate room"
        // Setup: Create Cinema C1, Cinema C2, Room R1, Room R2, Room R3
        Cinema cinemaC1 = new Cinema();
        Cinema cinemaC2 = new Cinema();
        Room roomR1 = new Room("R1");
        Room roomR2 = new Room("R2");
        Room roomR3 = new Room("R3");
        
        // Add Room R1 to C1 (true)
        boolean addR1ToC1 = cinemaC1.addRoom(roomR1);
        
        // Add Room R2 to C1 (true)
        boolean addR2ToC1 = cinemaC1.addRoom(roomR2);
        
        // Add Room R3 to C2 (true)
        boolean addR3ToC2 = cinemaC2.addRoom(roomR3);
        
        // Add Room R1 to C1 (false)
        boolean addR1ToC1Again = cinemaC1.addRoom(roomR1);
        
        // Expected Output: true, true, true, false
        assertTrue("Addition of R1 to C1 should return true", addR1ToC1);
        assertTrue("Addition of R2 to C1 should return true", addR2ToC1);
        assertTrue("Addition of R3 to C2 should return true", addR3ToC2);
        assertFalse("Second addition of R1 to C1 should return false", addR1ToC1Again);
        assertEquals("Cinema C1 should contain exactly two rooms", 2, cinemaC1.getRooms().size());
        assertEquals("Cinema C2 should contain exactly one room", 1, cinemaC2.getRooms().size());
    }
}