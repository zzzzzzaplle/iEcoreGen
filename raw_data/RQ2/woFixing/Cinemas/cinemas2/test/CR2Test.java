package edu.cinemas.cinemas2.test;

import edu.cinemas.Cinema;
import edu.cinemas.CinemasFactory;
import edu.cinemas.Film;
import edu.cinemas.Room;
import edu.cinemas.Screening;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR2Test {
    private CinemasFactory factory;
    private Cinema cinema;
    private Room room1, room2;
    private Film film1, film2;
    private Screening screening1, screening2;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Before
    public void setUp() {
        factory = CinemasFactory.eINSTANCE;
        cinema = factory.createCinema();
    }

    // Helper method to check room availability
    private boolean checkRoomAvailability(Cinema cinema, Room room, String timeStr) throws ParseException {
        Date time = sdf.parse(timeStr);
        
        // Check if room exists in cinema
        if (!cinema.getRooms().contains(room)) {
            return false;
        }
        
        // Check for scheduling conflicts
        for (Screening s : cinema.getScreenings()) {
            if (s.getRoom() != null && s.getTime() != null && 
                s.getRoom().equals(room) && s.getTime().equals(time)) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void testCase1_AvailableRoomWithNoScreenings() throws ParseException {
        // Setup: Create room and add to cinema
        room1 = factory.createRoom();
        assertTrue(cinema.addRoom(room1));
        
        // Action & Assert: Check availability
        assertTrue(checkRoomAvailability(cinema, room1, "2024-10-05 13:00:00"));
    }

    @Test
    public void testCase2_AssignedRoom() throws ParseException {
        // Setup: Create room, film, and screening
        room1 = factory.createRoom();
        film1 = factory.createFilm();
        assertTrue(cinema.addRoom(room1));
        assertTrue(cinema.addFilm(film1));
        
        screening1 = factory.createScreening();
        screening1.setTime(sdf.parse("2024-10-05 13:00:00"));
        screening1.setRoom(room1);
        screening1.setFilm(film1);
        
        // Assign screening
        assertTrue(cinema.assignScreening(
            film1, 
            sdf.parse("2024-10-04 13:00:00"), 
            screening1, 
            room1
        ));
        
        // Action & Assert: Check availability
        assertFalse(checkRoomAvailability(cinema, room1, "2024-10-05 13:00:00"));
    }

    @Test
    public void testCase3_DifferentDayAvailability() throws ParseException {
        // Setup: Create room, film, and screening
        room1 = factory.createRoom();
        film1 = factory.createFilm();
        assertTrue(cinema.addRoom(room1));
        assertTrue(cinema.addFilm(film1));
        
        screening1 = factory.createScreening();
        screening1.setTime(sdf.parse("2024-12-01 14:00:00"));
        screening1.setRoom(room1);
        screening1.setFilm(film1);
        
        // Assign screening
        assertTrue(cinema.assignScreening(
            film1, 
            sdf.parse("2024-11-30 00:00:00"), 
            screening1, 
            room1
        ));
        
        // Action & Assert: Check availability on different day
        assertTrue(checkRoomAvailability(cinema, room1, "2024-12-02 14:00:00"));
    }

    @Test
    public void testCase4_MultipleRoomAssignments() throws ParseException {
        // Setup: Create rooms, films, and screenings
        room1 = factory.createRoom();
        room2 = factory.createRoom();
        film1 = factory.createFilm();
        film2 = factory.createFilm();
        assertTrue(cinema.addRoom(room1));
        assertTrue(cinema.addRoom(room2));
        assertTrue(cinema.addFilm(film1));
        assertTrue(cinema.addFilm(film2));
        
        screening1 = factory.createScreening();
        screening1.setTime(sdf.parse("2024-10-05 13:00:00"));
        screening1.setRoom(room1);
        screening1.setFilm(film1);
        
        screening2 = factory.createScreening();
        screening2.setTime(sdf.parse("2024-10-05 13:00:00"));
        screening2.setRoom(room2);
        screening2.setFilm(film2);
        
        // Assign screenings
        assertTrue(cinema.assignScreening(
            film1, 
            sdf.parse("2024-10-01 13:00:00"), 
            screening1, 
            room1
        ));
        assertTrue(cinema.assignScreening(
            film2, 
            sdf.parse("2024-10-03 13:00:00"), 
            screening2, 
            room2
        ));
        
        // Action & Assert: Check both rooms
        assertFalse(checkRoomAvailability(cinema, room1, "2024-10-05 13:00:00"));
        assertFalse(checkRoomAvailability(cinema, room2, "2024-10-05 13:00:00"));
    }

    @Test
    public void testCase5_DifferentTimeSlotAvailability() throws ParseException {
        // Setup: Create room, film, and screening
        room1 = factory.createRoom();
        film1 = factory.createFilm();
        assertTrue(cinema.addRoom(room1));
        assertTrue(cinema.addFilm(film1));
        
        screening1 = factory.createScreening();
        screening1.setTime(sdf.parse("2024-10-05 13:00:00"));
        screening1.setRoom(room1);
        screening1.setFilm(film1);
        
        // Assign screening
        assertTrue(cinema.assignScreening(
            film1, 
            sdf.parse("2024-09-03 13:00:00"), 
            screening1, 
            room1
        ));
        
        // Action & Assert: Check different time slot
        assertTrue(checkRoomAvailability(cinema, room1, "2024-10-05 14:00:00"));
    }
}