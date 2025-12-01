package edu.cinemas.cinemas3.test;

import edu.cinemas.CinemasFactory;
import edu.cinemas.Cinema;
import edu.cinemas.Room;
import edu.cinemas.Film;
import edu.cinemas.Screening;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CR2Test {
    private CinemasFactory factory;
    private SimpleDateFormat sdf;
    private Cinema cinema;
    
    @Before
    public void setUp() {
        factory = CinemasFactory.eINSTANCE;
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cinema = factory.createCinema();
    }
    
    // Helper method to check room availability
    private boolean isRoomAvailableAtTime(Cinema cinema, Room room, Date time) {
        // Room must be registered in cinema
        if (!cinema.getRooms().contains(room)) {
            return false;
        }
        
        // Check if room is already assigned at given time
        for (Screening s : cinema.getScreenings()) {
            if (s.getRoom().equals(room) && s.getTime().equals(time)) {
                return false;
            }
        }
        return true;
    }
    
    // Test Case 1: Check available room with no screenings
    @Test
    public void testCase1_availableRoomWithNoScreenings() throws ParseException {
        // Setup
        Room room1 = factory.createRoom();
        cinema.addRoom(room1);
        
        Date checkTime = sdf.parse("2024-10-05 13:00:00");
        
        // Action and Verification
        assertTrue(isRoomAvailableAtTime(cinema, room1, checkTime));
    }
    
    // Test Case 2: Check assigned room
    @Test
    public void testCase2_checkAssignedRoom() throws ParseException {
        // Setup
        Film film1 = factory.createFilm();
        Room room1 = factory.createRoom();
        
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = sdf.parse("2024-10-05 13:00:00");
        Date currentTime = sdf.parse("2024-10-04 13:00:00");
        
        Screening screening = factory.createScreening();
        screening.setTime(screeningTime);
        cinema.assignScreening(film1, currentTime, screening, room1);
        
        // Action and Verification
        assertFalse(isRoomAvailableAtTime(cinema, room1, screeningTime));
    }
    
    // Test Case 3: Check room at exact screening time
    @Test
    public void testCase3_checkRoomAtExactScreeningTime() throws ParseException {
        // Setup
        Film film1 = factory.createFilm();
        Room room1 = factory.createRoom();
        
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = sdf.parse("2024-12-01 14:00:00");
        Date currentTime = sdf.parse("2024-11-30 14:00:00");
        
        Screening screening = factory.createScreening();
        screening.setTime(screeningTime);
        cinema.assignScreening(film1, currentTime, screening, room1);
        
        // Different time to check
        Date checkTime = sdf.parse("2024-12-02 14:00:00");
        
        // Action and Verification
        assertTrue(isRoomAvailableAtTime(cinema, room1, checkTime));
    }
    
    // Test Case 4: Check multiple rooms
    @Test
    public void testCase4_checkMultipleRooms() throws ParseException {
        // Setup
        Film film1 = factory.createFilm();
        Film film2 = factory.createFilm();
        Room room1 = factory.createRoom();
        Room room2 = factory.createRoom();
        
        cinema.addFilm(film1);
        cinema.addFilm(film2);
        cinema.addRoom(room1);
        cinema.addRoom(room2);
        
        Date screeningTime = sdf.parse("2024-10-05 13:00:00");
        Date currentTime1 = sdf.parse("2024-10-01 13:00:00");
        Date currentTime2 = sdf.parse("2024-10-03 13:00:00");
        
        Screening screening1 = factory.createScreening();
        screening1.setTime(screeningTime);
        cinema.assignScreening(film1, currentTime1, screening1, room1);
        
        Screening screening2 = factory.createScreening();
        screening2.setTime(screeningTime);
        cinema.assignScreening(film2, currentTime2, screening2, room2);
        
        // Action and Verification
        assertFalse(isRoomAvailableAtTime(cinema, room1, screeningTime));
        assertFalse(isRoomAvailableAtTime(cinema, room2, screeningTime));
    }
    
    // Test Case 5: Check different time slot
    @Test
    public void testCase5_checkDifferentTimeSlot() throws ParseException {
        // Setup
        Film film1 = factory.createFilm();
        Room room1 = factory.createRoom();
        
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = sdf.parse("2024-10-05 13:00:00");
        Date currentTime = sdf.parse("2024-09-03 13:00:00");
        
        Screening screening = factory.createScreening();
        screening.setTime(screeningTime);
        cinema.assignScreening(film1, currentTime, screening, room1);
        
        // Check different time slot
        Date checkTime = sdf.parse("2024-10-05 14:00:00");
        
        // Action and Verification
        assertTrue(isRoomAvailableAtTime(cinema, room1, checkTime));
    }
}