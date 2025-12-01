package edu.cinemas.cinemas5.test;

import edu.cinemas.CinemasFactory;
import edu.cinemas.Cinema;
import edu.cinemas.Room;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CR1Test {
    private CinemasFactory factory;
    private Cinema cinema;

    @Before
    public void setUp() {
        factory = CinemasFactory.eINSTANCE;
        cinema = factory.createCinema();
    }

    @Test
    public void testCase1_AddNewRoomToEmptyCinema() {
        // Test Case 1: Add new room to empty cinema
        Room room = factory.createRoom();
        assertTrue("Adding new room to empty cinema should return true", cinema.addRoom(room));
    }

    @Test
    public void testCase2_AddDuplicateRoom() {
        // Test Case 2: Add duplicate room
        Room room = factory.createRoom();
        assertTrue("First addition of room should succeed", cinema.addRoom(room));
        assertFalse("Adding duplicate room should return false", cinema.addRoom(room));
    }

    @Test
    public void testCase3_AddMultipleRoomsThenDuplicate() {
        // Test Case 3: Add multiple rooms then duplicate
        Room room1 = factory.createRoom();
        Room room2 = factory.createRoom();
        
        assertTrue("First room addition should succeed", cinema.addRoom(room1));
        assertTrue("Second room addition should succeed", cinema.addRoom(room2));
        assertFalse("Adding duplicate room should fail", cinema.addRoom(room1));
    }

    @Test
    public void testCase4_AddMultipleUniqueRooms() {
        // Test Case 4: Add multiple unique rooms
        Room room1 = factory.createRoom();
        Room room2 = factory.createRoom();
        
        assertTrue("First room addition should succeed", cinema.addRoom(room1));
        assertTrue("Second room addition should succeed", cinema.addRoom(room2));
    }

    @Test
    public void testCase5_AddRoomsToDifferentCinemas() {
        // Test Case 5: Add rooms to different cinemas
        Cinema cinema2 = factory.createCinema();
        Room room1 = factory.createRoom();
        Room room2 = factory.createRoom();
        Room room3 = factory.createRoom();
        
        assertTrue("Adding room1 to cinema1 should succeed", cinema.addRoom(room1));
        assertTrue("Adding room2 to cinema1 should succeed", cinema.addRoom(room2));
        assertTrue("Adding room3 to cinema2 should succeed", cinema2.addRoom(room3));
        assertFalse("Adding duplicate room1 to cinema1 should fail", cinema.addRoom(room1));
    }
}