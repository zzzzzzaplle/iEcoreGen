package edu.cinemas.cinemas1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.cinemas.CinemasFactory;
import edu.cinemas.Cinema;
import edu.cinemas.Room;

public class CR1Test {
    
    private CinemasFactory factory;
    private Cinema cinema;
    
    @Before
    public void setUp() {
        // Initialize factory and create cinema instance before each test
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
        
        // Create Room R1 using factory
        Room room1 = factory.createRoom();
        
        // Add room to empty cinema and verify result
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
        
        // Create Room R1 using factory
        Room room1 = factory.createRoom();
        
        // First addition should succeed
        boolean firstResult = cinema.addRoom(room1);
        assertTrue("First room addition should return true", firstResult);
        assertTrue("Cinema should contain the first room", cinema.getRooms().contains(room1));
        
        // Second addition of same room should fail
        boolean secondResult = cinema.addRoom(room1);
        assertFalse("Adding duplicate room should return false", secondResult);
        assertEquals("Cinema should still have only one room", 1, cinema.getRooms().size());
    }
    
    @Test
    public void testCase3_AddMultipleRooms() {
        // Test Case 3: "Add multiple rooms"
        // Input: Room R1
        // Setup:
        // 1. Create Cinema C1, Room R1, Room R2
        // 2. Add Room R1 to C1 (true)
        // 3. Add Room R2 to C1 (true)
        // 4. Add Room R1 to C1 again (false)
        // Expected Output: true, true, false
        
        // Create rooms using factory
        Room room1 = factory.createRoom();
        Room room2 = factory.createRoom();
        
        // Add first room - should succeed
        boolean result1 = cinema.addRoom(room1);
        assertTrue("Adding first room should return true", result1);
        
        // Add second room - should succeed
        boolean result2 = cinema.addRoom(room2);
        assertTrue("Adding second room should return true", result2);
        
        // Try to add first room again - should fail
        boolean result3 = cinema.addRoom(room1);
        assertFalse("Adding duplicate room should return false", result3);
        
        // Verify final state
        assertTrue("Cinema should contain room1", cinema.getRooms().contains(room1));
        assertTrue("Cinema should contain room2", cinema.getRooms().contains(room2));
        assertEquals("Cinema should have exactly two rooms", 2, cinema.getRooms().size());
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
        
        // Create unique rooms using factory
        Room room1 = factory.createRoom();
        Room room2 = factory.createRoom();
        
        // Add first room - should succeed
        boolean result1 = cinema.addRoom(room1);
        assertTrue("Adding first unique room should return true", result1);
        
        // Add second room - should succeed
        boolean result2 = cinema.addRoom(room2);
        assertTrue("Adding second unique room should return true", result2);
        
        // Verify final state
        assertTrue("Cinema should contain room1", cinema.getRooms().contains(room1));
        assertTrue("Cinema should contain room2", cinema.getRooms().contains(room2));
        assertEquals("Cinema should have exactly two rooms", 2, cinema.getRooms().size());
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
        
        // Create cinemas and rooms using factory
        Cinema cinema1 = factory.createCinema();
        Cinema cinema2 = factory.createCinema();
        Room room1 = factory.createRoom();
        Room room2 = factory.createRoom();
        Room room3 = factory.createRoom();
        
        // Add Room R1 to C1 - should succeed
        boolean result1 = cinema1.addRoom(room1);
        assertTrue("Adding room1 to cinema1 should return true", result1);
        
        // Add Room R2 to C1 - should succeed
        boolean result2 = cinema1.addRoom(room2);
        assertTrue("Adding room2 to cinema1 should return true", result2);
        
        // Add Room R3 to C2 - should succeed
        boolean result3 = cinema2.addRoom(room3);
        assertTrue("Adding room3 to cinema2 should return true", result3);
        
        // Try to add Room R1 to C1 again - should fail
        boolean result4 = cinema1.addRoom(room1);
        assertFalse("Adding duplicate room1 to cinema1 should return false", result4);
        
        // Verify final states
        assertTrue("Cinema1 should contain room1", cinema1.getRooms().contains(room1));
        assertTrue("Cinema1 should contain room2", cinema1.getRooms().contains(room2));
        assertFalse("Cinema1 should not contain room3", cinema1.getRooms().contains(room3));
        assertEquals("Cinema1 should have exactly two rooms", 2, cinema1.getRooms().size());
        
        assertTrue("Cinema2 should contain room3", cinema2.getRooms().contains(room3));
        assertFalse("Cinema2 should not contain room1", cinema2.getRooms().contains(room1));
        assertFalse("Cinema2 should not contain room2", cinema2.getRooms().contains(room2));
        assertEquals("Cinema2 should have exactly one room", 1, cinema2.getRooms().size());
    }
}