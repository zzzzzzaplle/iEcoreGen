import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
    public void testCase1_addNewRoomToEmptyCinema() {
        // Create Cinema C1 with empty rooms
        // Create Room R1
        // Add Room R1 to empty cinema
        boolean result = cinema.addRoom(room1);
        
        // Expected Output: true
        assertTrue(result);
        assertTrue(cinema.getRooms().contains(room1));
    }
    
    @Test
    public void testCase2_addDuplicateRoom() {
        // Create Cinema C1, Room R1
        // Add Room R1 to C1 (true)
        boolean firstAdd = cinema.addRoom(room1);
        assertTrue(firstAdd);
        
        // Add Room R1 to C1 again (false)
        boolean secondAdd = cinema.addRoom(room1);
        
        // Expected Output: true, false
        assertFalse(secondAdd);
        assertEquals(1, cinema.getRooms().size());
    }
    
    @Test
    public void testCase3_addMultipleRooms() {
        // Create Cinema C1, Room R1, Room R2
        // Add Room R1 to C1 (true)
        boolean result1 = cinema.addRoom(room1);
        assertTrue(result1);
        
        // Add Room R2 to C1 (true)
        boolean result2 = cinema.addRoom(room2);
        assertTrue(result2);
        
        // Add Room R1 to C1 again (false)
        boolean result3 = cinema.addRoom(room1);
        
        // Expected Output: true, true, false
        assertFalse(result3);
        assertEquals(2, cinema.getRooms().size());
        assertTrue(cinema.getRooms().contains(room1));
        assertTrue(cinema.getRooms().contains(room2));
    }
    
    @Test
    public void testCase4_addMultipleUniqueRooms() {
        // Create Cinema C1
        // Add Room1 to C1 (true)
        boolean result1 = cinema.addRoom(room1);
        assertTrue(result1);
        
        // Add Room2 to C1 (true)
        boolean result2 = cinema.addRoom(room2);
        assertTrue(result2);
        
        // Expected Output: true, true
        assertEquals(2, cinema.getRooms().size());
    }
    
    @Test
    public void testCase5_addDuplicateRoom() {
        // Create Cinema C1, Cinema C2, Room R1, Room R2, Room R3
        Cinema cinema2 = new Cinema();
        
        // Add Room R1 to C1 (true)
        boolean result1 = cinema.addRoom(room1);
        assertTrue(result1);
        
        // Add Room R2 to C1 (true)
        boolean result2 = cinema.addRoom(room2);
        assertTrue(result2);
        
        // Add Room R3 to C2 (true)
        boolean result3 = cinema2.addRoom(room3);
        assertTrue(result3);
        
        // Add Room R1 to C1 (false)
        boolean result4 = cinema.addRoom(room1);
        
        // Expected Output: true, true, true, false
        assertFalse(result4);
        assertEquals(2, cinema.getRooms().size());
        assertEquals(1, cinema2.getRooms().size());
    }
}