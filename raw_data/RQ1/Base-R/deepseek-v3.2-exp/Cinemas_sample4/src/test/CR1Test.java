import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    private Cinema cinema;
    private Room room1;
    private Room room2;
    private Room room3;
    private Cinema cinema2;

    @Before
    public void setUp() {
        // Initialize test objects before each test
        cinema = new Cinema();
        cinema2 = new Cinema();
        room1 = new Room();
        room2 = new Room();
        room3 = new Room();
        
        // Set room IDs
        room1.setRoomId("R1");
        room2.setRoomId("R2");
        room3.setRoomId("R3");
    }

    @Test
    public void testCase1_AddNewRoomToEmptyCinema() {
        // Test adding a new room to an empty cinema
        boolean result = cinema.addRoom(room1);
        assertTrue("Room R1 should be added successfully to empty cinema", result);
        assertEquals("Cinema should have exactly 1 room", 1, cinema.getRooms().size());
        assertTrue("Cinema should contain room R1", cinema.getRooms().contains(room1));
    }

    @Test
    public void testCase2_AddDuplicateRoom() {
        // Test adding a duplicate room to the same cinema
        boolean firstAdd = cinema.addRoom(room1);
        boolean secondAdd = cinema.addRoom(room1);
        
        assertTrue("First addition of room R1 should succeed", firstAdd);
        assertFalse("Second addition of room R1 should fail", secondAdd);
        assertEquals("Cinema should have exactly 1 room", 1, cinema.getRooms().size());
    }

    @Test
    public void testCase3_AddMultipleRooms() {
        // Test adding multiple rooms including duplicates
        boolean addR1First = cinema.addRoom(room1);
        boolean addR2 = cinema.addRoom(room2);
        boolean addR1Second = cinema.addRoom(room1);
        
        assertTrue("First addition of room R1 should succeed", addR1First);
        assertTrue("Addition of room R2 should succeed", addR2);
        assertFalse("Second addition of room R1 should fail", addR1Second);
        assertEquals("Cinema should have exactly 2 rooms", 2, cinema.getRooms().size());
    }

    @Test
    public void testCase4_AddMultipleUniqueRooms() {
        // Test adding multiple unique rooms to cinema
        boolean addR1 = cinema.addRoom(room1);
        boolean addR2 = cinema.addRoom(room2);
        
        assertTrue("Addition of room R1 should succeed", addR1);
        assertTrue("Addition of room R2 should succeed", addR2);
        assertEquals("Cinema should have exactly 2 rooms", 2, cinema.getRooms().size());
        assertTrue("Cinema should contain room R1", cinema.getRooms().contains(room1));
        assertTrue("Cinema should contain room R2", cinema.getRooms().contains(room2));
    }

    @Test
    public void testCase5_AddDuplicateRoomAcrossMultipleCinemas() {
        // Test adding rooms to multiple cinemas with duplicate handling
        boolean addR1ToC1 = cinema.addRoom(room1);
        boolean addR2ToC1 = cinema.addRoom(room2);
        boolean addR3ToC2 = cinema2.addRoom(room3);
        boolean addR1AgainToC1 = cinema.addRoom(room1);
        
        assertTrue("Addition of room R1 to cinema C1 should succeed", addR1ToC1);
        assertTrue("Addition of room R2 to cinema C1 should succeed", addR2ToC1);
        assertTrue("Addition of room R3 to cinema C2 should succeed", addR3ToC2);
        assertFalse("Second addition of room R1 to cinema C1 should fail", addR1AgainToC1);
        
        assertEquals("Cinema C1 should have exactly 2 rooms", 2, cinema.getRooms().size());
        assertEquals("Cinema C2 should have exactly 1 room", 1, cinema2.getRooms().size());
    }
}