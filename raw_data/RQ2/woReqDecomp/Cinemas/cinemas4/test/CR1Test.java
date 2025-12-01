package edu.cinemas.cinemas4.test;

import static org.junit.Assert.*;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import edu.cinemas.Cinema;
import edu.cinemas.CinemasFactory;
import edu.cinemas.Film;
import edu.cinemas.Room;
import edu.cinemas.Screening;

public class CR1Test {
    // Factory instance for creating Ecore objects
    private CinemasFactory factory = CinemasFactory.eINSTANCE;
    private Cinema cinema;
    
    @Before
    public void setUp() {
        // Initialize cinema before each test
        cinema = factory.createCinema();
    }

    @Test
    public void testCase1_AddNewRoomToEmptyCinema() {
        // Create Room R1
        Room room = factory.createRoom();
        
        // Add Room R1 to empty cinema
        boolean result = cinema.addRoom(room);
        
        // Verify room was added
        assertTrue(result);
    }

    @Test
    public void testCase2_AddDuplicateRoom() {
        // Create Room R1
        Room room1 = factory.createRoom();
        
        // Add Room R1 first time (should succeed)
        boolean firstResult = cinema.addRoom(room1);
        assertTrue(firstResult);
        
        // Add Room R1 again (should fail)
        boolean secondResult = cinema.addRoom(room1);
        assertFalse(secondResult);
    }

    @Test
    public void testCase3_AddMultipleRooms() {
        // Create rooms
        Room room1 = factory.createRoom();
        Room room2 = factory.createRoom();
        
        // Add Room1 (should succeed)
        boolean result1 = cinema.addRoom(room1);
        assertTrue(result1);
        
        // Add Room2 (should succeed)
        boolean result2 = cinema.addRoom(room2);
        assertTrue(result2);
        
        // Add Room1 again (should fail)
        boolean result3 = cinema.addRoom(room1);
        assertFalse(result3);
    }

    @Test
    public void testCase4_AddMultipleUniqueRooms() {
        // Create unique rooms
        Room room1 = factory.createRoom();
        Room room2 = factory.createRoom();
        
        // Add Room1 (should succeed)
        boolean result1 = cinema.addRoom(room1);
        assertTrue(result1);
        
        // Add Room2 (should succeed)
        boolean result2 = cinema.addRoom(room2);
        assertTrue(result2);
    }

    @Test
    public void testCase5_AddDuplicateRoomAcrossCinemas() {
        // Create cinemas and rooms
        Cinema cinema1 = factory.createCinema();
        Cinema cinema2 = factory.createCinema();
        Room room1 = factory.createRoom();
        Room room2 = factory.createRoom();
        Room room3 = factory.createRoom();
        
        // Add Room1 to Cinema1 (success)
        boolean result1 = cinema1.addRoom(room1);
        assertTrue(result1);
        
        // Add Room2 to Cinema1 (success)
        boolean result2 = cinema1.addRoom(room2);
        assertTrue(result2);
        
        // Add Room3 to Cinema2 (success)
        boolean result3 = cinema2.addRoom(room3);
        assertTrue(result3);
        
        // Add Room1 to Cinema1 again (fail)
        boolean result4 = cinema1.addRoom(room1);
        assertFalse(result4);
    }
}