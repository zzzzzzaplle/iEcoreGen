package edu.cinemas.cinemas2.test;

import edu.cinemas.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CR2Test {
    private CinemasFactory factory;
    private Cinema cinema;
    private SimpleDateFormat sdf;
    
    @Before
    public void setUp() {
        factory = CinemasFactory.eINSTANCE;
        cinema = factory.createCinema();
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    // Helper method to check room availability
    private boolean isRoomAvailable(Cinema cinema, Room room, Date time) {
        // Check if room exists in cinema
        if (!cinema.getRooms().contains(room)) {
            return false;
        }
        
        // Check if room has screening at given time
        for (Screening s : cinema.getScreenings()) {
            if (room.equals(s.getRoom()) && time.equals(s.getTime())) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void testCase1_availableRoomNoScreenings() throws ParseException {
        // Create room and add to cinema
        Room room1 = factory.createRoom();
        cinema.getRooms().add(room1);
        
        // Check availability
        Date time = sdf.parse("2024-10-05 13:00:00");
        assertTrue(isRoomAvailable(cinema, room1, time));
    }

    @Test
    public void testCase2_assignedRoom() throws ParseException {
        // Create film, room and add to cinema
        Film film1 = factory.createFilm();
        Room room1 = factory.createRoom();
        cinema.getFilm().add(film1);
        cinema.getRooms().add(room1);
        
        // Create and assign screening
        Screening screening = factory.createScreening();
        screening.setFilm(film1);
        screening.setRoom(room1);
        screening.setTime(sdf.parse("2024-10-05 13:00:00"));
        cinema.getScreenings().add(screening);
        
        // Check availability at screening time
        Date time = sdf.parse("2024-10-05 13:00:00");
        assertFalse(isRoomAvailable(cinema, room1, time));
    }

    @Test
    public void testCase3_differentTimeSlot() throws ParseException {
        // Create film, room and add to cinema
        Film film1 = factory.createFilm();
        Room room1 = factory.createRoom();
        cinema.getFilm().add(film1);
        cinema.getRooms().add(room1);
        
        // Create screening at different time
        Screening screening = factory.createScreening();
        screening.setFilm(film1);
        screening.setRoom(room1);
        screening.setTime(sdf.parse("2024-12-01 14:00:00"));
        cinema.getScreenings().add(screening);
        
        // Check availability at different time
        Date time = sdf.parse("2024-12-02 14:00:00");
        assertTrue(isRoomAvailable(cinema, room1, time));
    }

    @Test
    public void testCase4_multipleRooms() throws ParseException {
        // Create films and rooms
        Film film1 = factory.createFilm();
        Film film2 = factory.createFilm();
        Room room1 = factory.createRoom();
        Room room2 = factory.createRoom();
        
        // Add to cinema
        cinema.getFilm().addAll(java.util.Arrays.asList(film1, film2));
        cinema.getRooms().addAll(java.util.Arrays.asList(room1, room2));
        
        // Create screenings at same time
        Screening s1 = factory.createScreening();
        s1.setFilm(film1);
        s1.setRoom(room1);
        s1.setTime(sdf.parse("2024-10-05 13:00:00"));
        
        Screening s2 = factory.createScreening();
        s2.setFilm(film2);
        s2.setRoom(room2);
        s2.setTime(sdf.parse("2024-10-05 13:00:00"));
        
        cinema.getScreenings().addAll(java.util.Arrays.asList(s1, s2));
        
        // Check availability at screening time
        Date time = sdf.parse("2024-10-05 13:00:00");
        assertFalse(isRoomAvailable(cinema, room1, time));
        assertFalse(isRoomAvailable(cinema, room2, time));
    }

    @Test
    public void testCase5_differentTimeSameDay() throws ParseException {
        // Create film and room
        Film film1 = factory.createFilm();
        Room room1 = factory.createRoom();
        cinema.getFilm().add(film1);
        cinema.getRooms().add(room1);
        
        // Create screening
        Screening screening = factory.createScreening();
        screening.setFilm(film1);
        screening.setRoom(room1);
        screening.setTime(sdf.parse("2024-10-05 13:00:00"));
        cinema.getScreenings().add(screening);
        
        // Check availability at different time slot same day
        Date time = sdf.parse("2024-10-05 14:00:00");
        assertTrue(isRoomAvailable(cinema, room1, time));
    }
}