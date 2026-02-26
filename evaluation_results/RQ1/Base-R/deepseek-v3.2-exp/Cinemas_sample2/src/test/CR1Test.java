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
        cinema = new Cinema();
        cinema2 = new Cinema();
        room1 = new Room("R1");
        room2 = new Room("R2");
        room3 = new Room("R3");
    }

    @Test
    public void testCase1_AddNewRoomToEmptyCinema() {
        // Test adding a new room to empty cinema
        boolean result = cinema.addRoom(room1);
        assertTrue("Should return true when adding room to empty cinema", result);
        assertEquals("Cinema should contain 1 room", 1, cinema.getRooms().size());
        assertTrue("Cinema should contain the added room", cinema.getRooms().contains(room1));
    }

    @Test
    public void testCase2_AddDuplicateRoom() {
        // Test adding duplicate room to cinema
        boolean firstAdd = cinema.addRoom(room1);
        boolean secondAdd = cinema.addRoom(room1);
        
        assertTrue("First addition should return true", firstAdd);
        assertFalse("Second addition should return false", secondAdd);
        assertEquals("Cinema should contain only 1 room", 1, cinema.getRooms().size());
    }

    @Test
    public void testCase3_AddMultipleRooms() {
        // Test adding multiple rooms including duplicates
        boolean addRoom1First = cinema.addRoom(room1);
        boolean addRoom2 = cinema.addRoom(room2);
        boolean addRoom1Second = cinema.addRoom(room1);
        
        assertTrue("First room addition should return true", addRoom1First);
        assertTrue("Second room addition should return true", addRoom2);
        assertFalse("Duplicate room addition should return false", addRoom1Second);
        assertEquals("Cinema should contain 2 rooms", 2, cinema.getRooms().size());
    }

    @Test
    public void testCase4_AddMultipleUniqueRooms() {
        // Test adding multiple unique rooms
        boolean addRoom1 = cinema.addRoom(room1);
        boolean addRoom2 = cinema.addRoom(room2);
        
        assertTrue("First room addition should return true", addRoom1);
        assertTrue("Second room addition should return true", addRoom2);
        assertEquals("Cinema should contain 2 rooms", 2, cinema.getRooms().size());
        assertTrue("Cinema should contain room1", cinema.getRooms().contains(room1));
        assertTrue("Cinema should contain room2", cinema.getRooms().contains(room2));
    }

    @Test
    public void testCase5_AddDuplicateRoomAcrossMultipleCinemas() {
        // Test adding duplicate room across multiple cinemas
        boolean addRoom1ToC1 = cinema.addRoom(room1);
        boolean addRoom2ToC1 = cinema.addRoom(room2);
        boolean addRoom3ToC2 = cinema2.addRoom(room3);
        boolean addRoom1AgainToC1 = cinema.addRoom(room1);
        
        assertTrue("Room1 addition to C1 should return true", addRoom1ToC1);
        assertTrue("Room2 addition to C1 should return true", addRoom2ToC1);
        assertTrue("Room3 addition to C2 should return true", addRoom3ToC2);
        assertFalse("Duplicate Room1 addition to C1 should return false", addRoom1AgainToC1);
        
        assertEquals("C1 should contain 2 rooms", 2, cinema.getRooms().size());
        assertEquals("C2 should contain 1 room", 1, cinema2.getRooms().size());
    }
}