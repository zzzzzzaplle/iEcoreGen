package edu.cinemas.cinemas2.test;

import edu.cinemas.Cinema;
import edu.cinemas.CinemasFactory;
import edu.cinemas.Room;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CR1Test {

    private Cinema cinema;
    private CinemasFactory factory;
    private Room room1;
    private Room room2;
    private Room room3;

    @Before
    public void setUp() {
        factory = CinemasFactory.eINSTANCE;
        cinema = factory.createCinema();
        room1 = factory.createRoom();
        room2 = factory.createRoom();
        room3 = factory.createRoom();
    }

    // Test Case 1: Add new room to empty cinema
    @Test
    public void testCase1_AddNewRoomToEmptyCinema() {
        // Add Room R1 to empty cinema
        boolean result = cinema.addRoom(room1);
        // Verify room was added successfully
        assertTrue(result);
        // Verify cinema contains the added room
        assertTrue(cinema.getRooms().contains(room1));
    }

    // Test Case 2: Add duplicate room
    @Test
    public void testCase2_AddDuplicateRoom() {
        // Add Room R1 to cinema (should succeed)
        boolean firstAdd = cinema.addRoom(room1);
        assertTrue(firstAdd);
        
        // Add same room R1 again (should fail)
        boolean secondAdd = cinema.addRoom(room1);
        assertFalse(secondAdd);
    }

    // Test Case 3: Add multiple rooms
    @Test
    public void testCase3_AddMultipleRooms() {
        // Add Room R1 (should succeed)
        boolean addR1 = cinema.addRoom(room1);
        assertTrue(addR1);
        
        // Add Room R2 (should succeed)
        boolean addR2 = cinema.addRoom(room2);
        assertTrue(addR2);
        
        // Try to add Room R1 again (should fail)
        boolean addR1Again = cinema.addRoom(room1);
        assertFalse(addR1Again);
    }

    // Test Case 4: Add multiple unique rooms
    @Test
    public void testCase4_AddMultipleUniqueRooms() {
        // Add Room1 (should succeed)
        boolean addRoom1 = cinema.addRoom(room1);
        assertTrue(addRoom1);
        
        // Add Room2 (should succeed)
        boolean addRoom2 = cinema.addRoom(room2);
        assertTrue(addRoom2);
    }

    // Test Case 5: Add duplicate room across multiple cinemas
    @Test
    public void testCase5_AddDuplicateRoomAcrossCinemas() {
        // Create second cinema
        Cinema cinema2 = factory.createCinema();
        
        // Add Room R1 to cinema1 (should succeed)
        boolean addR1ToC1 = cinema.addRoom(room1);
        assertTrue(addR1ToC1);
        
        // Add Room R2 to cinema1 (should succeed)
        boolean addR2ToC1 = cinema.addRoom(room2);
        assertTrue(addR2ToC1);
        
        // Add Room R3 to cinema2 (should succeed)
        boolean addR3ToC2 = cinema2.addRoom(room3);
        assertTrue(addR3ToC2);
        
        // Try to add Room R1 to cinema1 again (should fail)
        boolean addR1ToC1Again = cinema.addRoom(room1);
        assertFalse(addR1ToC1Again);
    }
}