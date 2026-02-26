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
        room1 = new Room();
        room2 = new Room();
        room3 = new Room();
    }
    
    @Test
    public void testCase1_addNewRoomToEmptyCinema() {
        // Setup: Create Cinema C1 with empty rooms and Room R1
        room1.setName("Room1");
        
        // Add Room R1 to empty cinema
        boolean result = cinema.addRoom(room1);
        
        // Expected Output: true
        assertTrue(result);
    }
    
    @Test
    public void testCase2_addDuplicateRoom() {
        // Setup: Create Cinema C1, Room R1
        room1.setName("Room1");
        
        // Add Room R1 to C1 (first time)
        boolean firstAdd = cinema.addRoom(room1);
        
        // Add Room R1 to C1 again (second time)
        boolean secondAdd = cinema.addRoom(room1);
        
        // Expected Output: true, false
        assertTrue(firstAdd);
        assertFalse(secondAdd);
    }
    
    @Test
    public void testCase3_addMultipleRooms() {
        // Setup: Create Cinema C1, Room R1, Room R2
        room1.setName("Room1");
        room2.setName("Room2");
        
        // Add Room R1 to C1
        boolean result1 = cinema.addRoom(room1);
        
        // Add Room R2 to C1
        boolean result2 = cinema.addRoom(room2);
        
        // Add Room R1 to C1 again
        boolean result3 = cinema.addRoom(room1);
        
        // Expected Output: true, true, false
        assertTrue(result1);
        assertTrue(result2);
        assertFalse(result3);
    }
    
    @Test
    public void testCase4_addMultipleUniqueRooms() {
        // Setup: Create Cinema C1
        Room room1 = new Room();
        room1.setName("Room1");
        Room room2 = new Room();
        room2.setName("Room2");
        
        // Add Room1 to C1
        boolean result1 = cinema.addRoom(room1);
        
        // Add Room2 to C1
        boolean result2 = cinema.addRoom(room2);
        
        // Expected Output: true, true
        assertTrue(result1);
        assertTrue(result2);
    }
    
    @Test
    public void testCase5_addDuplicateRoomAcrossCinemas() {
        // Setup: Create Cinema C1, Cinema C2, Room R1, Room R2, Room R3
        Cinema cinema2 = new Cinema();
        room1.setName("Room1");
        room2.setName("Room2");
        room3.setName("Room3");
        
        // Add Room R1 to C1
        boolean result1 = cinema.addRoom(room1);
        
        // Add Room R2 to C1
        boolean result2 = cinema.addRoom(room2);
        
        // Add Room R3 to C2
        boolean result3 = cinema2.addRoom(room3);
        
        // Add Room R1 to C1 again
        boolean result4 = cinema.addRoom(room1);
        
        // Expected Output: true, true, true, false
        assertTrue(result1);
        assertTrue(result2);
        assertTrue(result3);
        assertFalse(result4);
    }
}