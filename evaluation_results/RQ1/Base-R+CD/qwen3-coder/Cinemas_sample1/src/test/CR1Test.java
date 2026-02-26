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
    
    /**
     * Test Case 1: Add new room to empty cinema
     * Input: Room R1
     * Setup: 
     * 1. Create Cinema C1 with empty rooms
     * 2. Create Room R1
     * 3. Add Room R1 to empty cinema
     * Expected Output: true
     */
    @Test
    public void testCase1_addNewRoomToEmptyCinema() {
        // Create Cinema C1 with empty rooms (done in setUp)
        // Create Room R1 (done in setUp)
        // Add Room R1 to empty cinema
        boolean result = cinema.addRoom(room1);
        
        // Expected Output: true
        assertTrue(result);
    }
    
    /**
     * Test Case 2: Add duplicate room
     * Input: Room R1
     * Setup:
     * 1. Create Cinema C1, Room R1
     * 2. Add Room R1 to C1 (true)
     * 3. Add Room R1 to C1 again (false)
     * Expected Output: true, false
     */
    @Test
    public void testCase2_addDuplicateRoom() {
        // Create Cinema C1, Room R1 (done in setUp)
        // Add Room R1 to C1 (true)
        boolean firstAdd = cinema.addRoom(room1);
        assertTrue(firstAdd);
        
        // Add Room R1 to C1 again (false)
        boolean secondAdd = cinema.addRoom(room1);
        
        // Expected Output: true, false
        assertFalse(secondAdd);
    }
    
    /**
     * Test Case 3: Add multiple rooms
     * Input: Room R1
     * Setup:
     * 1. Create Cinema C1, Room R1, Room R2
     * 2. Add Room R1 to C1 (true)
     * 3. Add Room R2 to C1 (true)
     * 4. Add Room R1 to C1 again (false)
     * Expected Output: true, true, false
     */
    @Test
    public void testCase3_addMultipleRooms() {
        // Create Cinema C1, Room R1, Room R2 (done in setUp)
        // Add Room R1 to C1 (true)
        boolean result1 = cinema.addRoom(room1);
        assertTrue(result1);
        
        // Add Room R2 to C1 (true)
        boolean result2 = cinema.addRoom(room2);
        assertTrue(result2);
        
        // Add Room R1 to C1 again (false)
        boolean result3 = cinema.addRoom(room1);
        
        // Expected Output: true, true, false
        assertTrue(result1);
        assertTrue(result2);
        assertFalse(result3);
    }
    
    /**
     * Test Case 4: Add multiple unique rooms
     * Input: Room R2
     * Setup:
     * 1. Create Cinema C1
     * 2. Add Room1 to C1 (true)
     * 3. Add Room2 to C1 (true)
     * Expected Output: true, true
     */
    @Test
    public void testCase4_addMultipleUniqueRooms() {
        // Create Cinema C1 (done in setUp)
        // Add Room1 to C1 (true)
        boolean result1 = cinema.addRoom(room1);
        
        // Add Room2 to C1 (true)
        boolean result2 = cinema.addRoom(room2);
        
        // Expected Output: true, true
        assertTrue(result1);
        assertTrue(result2);
    }
    
    /**
     * Test Case 5: Add duplicate room
     * Input: Room R1, R2
     * Setup:
     * 1. Create Cinema C1, Cinema C2, Room R1, Room R2, Room R3
     * 2. Add Room R1 to C1 (true)
     * 3. Add Room R2 to C1 (true)
     * 4. Add Room R3 to C2 (true) 
     * 5. Add Room R1 to C1 (false)  
     * Expected Output: true, true, true, false.
     */
    @Test
    public void testCase5_addDuplicateRoom() {
        Cinema cinema2 = new Cinema();
        
        // Add Room R1 to C1 (true)
        boolean result1 = cinema.addRoom(room1);
        
        // Add Room R2 to C1 (true)
        boolean result2 = cinema.addRoom(room2);
        
        // Add Room R3 to C2 (true)
        boolean result3 = cinema2.addRoom(room3);
        
        // Add Room R1 to C1 (false)
        boolean result4 = cinema.addRoom(room1);
        
        // Expected Output: true, true, true, false
        assertTrue(result1);
        assertTrue(result2);
        assertTrue(result3);
        assertFalse(result4);
    }
}