package edu.cinemas.cinemas3.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.cinemas.Cinema;
import edu.cinemas.Room;
import edu.cinemas.CinemasFactory;
import edu.cinemas.CinemasPackage;

public class CR1Test {
    
    private Cinema cinema;
    private CinemasFactory factory;
    
    @Before
    public void setUp() {
        // Initialize the factory and create cinema instance
        factory = CinemasFactory.eINSTANCE;
        cinema = factory.createCinema();
    }
    
    @Test
    public void testCase1_AddNewRoomToEmptyCinema() {
        // Test Case 1: "Add new room to empty cinema"
        // Input: Room R1
        // Setup: 
        // 1. Create Cinema C1 with empty rooms
        // 2. Create Room R1
        // 3. Add Room R1 to empty cinema
        // Expected Output: true
        
        Room room1 = factory.createRoom();
        boolean result = cinema.addRoom(room1);
        
        assertTrue("Adding new room to empty cinema should return true", result);
        assertTrue("Cinema should contain the added room", cinema.getRooms().contains(room1));
        assertEquals("Cinema should have exactly one room", 1, cinema.getRooms().size());
    }
    
    @Test
    public void testCase2_AddDuplicateRoom() {
        // Test Case 2: "Add duplicate room"
        // Input: Room R1
        // Setup:
        // 1. Create Cinema C1, Room R1
        // 2. Add Room R1 to C1 (true)
        // 3. Add Room R1 to C1 again (false)
        // Expected Output: true, false
        
        Room room1 = factory.createRoom();
        
        // First addition - should succeed
        boolean firstResult = cinema.addRoom(room1);
        assertTrue("First addition of room should return true", firstResult);
        assertEquals("Cinema should have exactly one room after first addition", 1, cinema.getRooms().size());
        
        // Second addition - should fail (duplicate)
        boolean secondResult = cinema.addRoom(room1);
        assertFalse("Adding duplicate room should return false", secondResult);
        assertEquals("Cinema should still have exactly one room after duplicate addition", 1, cinema.getRooms().size());
    }
    
    @Test
    public void testCase3_AddMultipleRooms() {
        // Test Case 3: "Add multiple rooms"
        // Input:  Room R1
        // Setup:
        // 1. Create Cinema C1, Room R1, Room R2
        // 2. Add Room R1 to C1 (true)
        // 3. Add Room R2 to C1 (true)
        // 4. Add Room R1 to C1 again (false)
        // Expected Output: true, true, false
        
        Room room1 = factory.createRoom();
        Room room2 = factory.createRoom();
        
        // Add Room R1 - should succeed
        boolean result1 = cinema.addRoom(room1);
        assertTrue("Adding room1 should return true", result1);
        assertEquals("Cinema should have one room after adding room1", 1, cinema.getRooms().size());
        
        // Add Room R2 - should succeed
        boolean result2 = cinema.addRoom(room2);
        assertTrue("Adding room2 should return true", result2);
        assertEquals("Cinema should have two rooms after adding room2", 2, cinema.getRooms().size());
        
        // Try to add Room R1 again - should fail
        boolean result3 = cinema.addRoom(room1);
        assertFalse("Adding duplicate room1 should return false", result3);
        assertEquals("Cinema should still have two rooms after duplicate addition", 2, cinema.getRooms().size());
    }
    
    @Test
    public void testCase4_AddMultipleUniqueRooms() {
        // Test Case 4: "Add multiple unique rooms"
        // Input: Room R2
        // Setup:
        // 1. Create Cinema C1
        // 2. Add Room1 to C1 (true)
        // 3. Add Room2 to C1 (true)
        // Expected Output: true, true
        
        Room room1 = factory.createRoom();
        Room room2 = factory.createRoom();
        
        // Add Room1 - should succeed
        boolean result1 = cinema.addRoom(room1);
        assertTrue("Adding room1 should return true", result1);
        assertTrue("Cinema should contain room1", cinema.getRooms().contains(room1));
        assertEquals("Cinema should have one room after adding room1", 1, cinema.getRooms().size());
        
        // Add Room2 - should succeed
        boolean result2 = cinema.addRoom(room2);
        assertTrue("Adding room2 should return true", result2);
        assertTrue("Cinema should contain room2", cinema.getRooms().contains(room2));
        assertEquals("Cinema should have two rooms after adding room2", 2, cinema.getRooms().size());
    }
    
    @Test
    public void testCase5_AddDuplicateRoomAcrossMultipleCinemas() {
        // Test Case 5: "Add duplicate room"
        // Input: Room R1, R2
        // Setup:
        // 1. Create Cinema C1, Cinema C2, Room R1, Room R2, Room R3
        // 2. Add Room R1 to C1 (true)
        // 3. Add Room R2 to C1 (true)
        // 4. Add Room R3 to C2 (true) 
        // 5. Add Room R1 to C1 (false)  
        // Expected Output: true, true, true, false.
        
        Cinema cinema2 = factory.createCinema();
        Room room1 = factory.createRoom();
        Room room2 = factory.createRoom();
        Room room3 = factory.createRoom();
        
        // Add Room R1 to C1 - should succeed
        boolean result1 = cinema.addRoom(room1);
        assertTrue("Adding room1 to cinema1 should return true", result1);
        assertEquals("Cinema1 should have one room after adding room1", 1, cinema.getRooms().size());
        
        // Add Room R2 to C1 - should succeed
        boolean result2 = cinema.addRoom(room2);
        assertTrue("Adding room2 to cinema1 should return true", result2);
        assertEquals("Cinema1 should have two rooms after adding room2", 2, cinema.getRooms().size());
        
        // Add Room R3 to C2 - should succeed
        boolean result3 = cinema2.addRoom(room3);
        assertTrue("Adding room3 to cinema2 should return true", result3);
        assertEquals("Cinema2 should have one room after adding room3", 1, cinema2.getRooms().size());
        
        // Try to add Room R1 to C1 again - should fail
        boolean result4 = cinema.addRoom(room1);
        assertFalse("Adding duplicate room1 to cinema1 should return false", result4);
        assertEquals("Cinema1 should still have two rooms after duplicate addition", 2, cinema.getRooms().size());
    }
}