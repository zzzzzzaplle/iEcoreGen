package edu.cinemas.cinemas3.test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import edu.cinemas.CinemasFactory;
import edu.cinemas.Cinema;
import edu.cinemas.Room;

public class CR1Test {

    private CinemasFactory factory;
    private Cinema cinema;
    
    @Before
    public void setUp() {
        factory = CinemasFactory.eINSTANCE;
        cinema = factory.createCinema();
    }

    // Test Case 1: Add new room to empty cinema
    @Test
    public void testAddNewRoomToEmptyCinema() {
        // Create room
        Room room = factory.createRoom();
        
        // Add room to cinema
        boolean result = cinema.addRoom(room);
        
        // Verify result
        assertTrue("Adding new room should return true", result);
        assertTrue("Cinema should contain added room", cinema.getRooms().contains(room));
    }

    // Test Case 2: Add duplicate room
    @Test
    public void testAddDuplicateRoom() {
        // Create room
        Room room = factory.createRoom();
        
        // First add (should succeed)
        boolean firstAdd = cinema.addRoom(room);
        assertTrue("First room add should succeed", firstAdd);
        assertEquals("Cinema should have 1 room", 1, cinema.getRooms().size());
        
        // Second add (should fail)
        boolean secondAdd = cinema.addRoom(room);
        assertFalse("Adding duplicate room should return false", secondAdd);
        assertEquals("Cinema should still have 1 room", 1, cinema.getRooms().size());
    }

    // Test Case 3: Add multiple rooms
    @Test
    public void testAddMultipleRooms() {
        // Create rooms
        Room room1 = factory.createRoom();
        Room room2 = factory.createRoom();
        
        // Add room1
        boolean result1 = cinema.addRoom(room1);
        assertTrue("Adding room1 should succeed", result1);
        assertEquals("Cinema should have 1 room", 1, cinema.getRooms().size());
        
        // Add room2
        boolean result2 = cinema.addRoom(room2);
        assertTrue("Adding room2 should succeed", result2);
        assertEquals("Cinema should have 2 rooms", 2, cinema.getRooms().size());
        
        // Add duplicate room1
        boolean result3 = cinema.addRoom(room1);
        assertFalse("Adding duplicate room1 should fail", result3);
        assertEquals("Cinema should still have 2 rooms", 2, cinema.getRooms().size());
    }

    // Test Case 4: Add multiple unique rooms
    @Test
    public void testAddMultipleUniqueRooms() {
        // Create rooms
        Room room1 = factory.createRoom();
        Room room2 = factory.createRoom();
        
        // Add room1
        boolean result1 = cinema.addRoom(room1);
        assertTrue("Adding room1 should succeed", result1);
        
        // Add room2
        boolean result2 = cinema.addRoom(room2);
        assertTrue("Adding room2 should succeed", result2);
        
        // Verify both rooms added
        assertEquals("Cinema should have 2 rooms", 2, cinema.getRooms().size());
        assertTrue("Cinema should contain room1", cinema.getRooms().contains(room1));
        assertTrue("Cinema should contain room2", cinema.getRooms().contains(room2));
    }

    // Test Case 5: Add duplicate room across cinemas
    @Test
    public void testAddDuplicateRoomAcrossCinemas() {
        // Create cinemas and rooms
        Cinema cinema1 = factory.createCinema();
        Cinema cinema2 = factory.createCinema();
        Room room1 = factory.createRoom();
        Room room2 = factory.createRoom();
        Room room3 = factory.createRoom();
        
        // Add room1 to cinema1
        boolean result1 = cinema1.addRoom(room1);
        assertTrue("Adding room1 to cinema1 should succeed", result1);
        
        // Add room2 to cinema1
        boolean result2 = cinema1.addRoom(room2);
        assertTrue("Adding room2 to cinema1 should succeed", result2);
        
        // Add room3 to cinema2
        boolean result3 = cinema2.addRoom(room3);
        assertTrue("Adding room3 to cinema2 should succeed", result3);
        
        // Add duplicate room1 to cinema1
        boolean result4 = cinema1.addRoom(room1);
        assertFalse("Adding duplicate room1 to cinema1 should fail", result4);
        
        // Verify counts
        assertEquals("cinema1 should have 2 rooms", 2, cinema1.getRooms().size());
        assertEquals("cinema2 should have 1 room", 1, cinema2.getRooms().size());
    }
}