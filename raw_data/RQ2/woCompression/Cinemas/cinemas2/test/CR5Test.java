package edu.cinemas.cinemas2.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import edu.cinemas.Cinema;
import edu.cinemas.CinemasFactory;
import edu.cinemas.Film;
import edu.cinemas.Room;
import edu.cinemas.Screening;

public class CR5Test {
    
    private Cinema cinema;
    private CinemasFactory factory;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() throws Exception {
        factory = CinemasFactory.eINSTANCE;
        cinema = factory.createCinema();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ValidFutureScreeningAssignment() throws Exception {
        // Setup
        Film film = factory.createFilm();
        Room room = factory.createRoom();
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Screening screening = factory.createScreening();
        screening.setTime(screeningTime);
        
        // Action
        boolean result = cinema.assignScreening(film, currentTime, screening, room);
        
        // Assert
        assertTrue("Valid future screening should be assigned successfully", result);
        assertTrue("Screening should be added to cinema screenings", cinema.getScreenings().contains(screening));
        assertEquals("Screening should reference the correct film", film, screening.getFilm());
        assertEquals("Screening should reference the correct room", room, screening.getRoom());
        assertEquals("Screening should be associated with cinema", cinema, screening.getCinemas());
    }
    
    @Test
    public void testCase2_AssignToAlreadyBookedRoom() throws Exception {
        // Setup
        Film film = factory.createFilm();
        Room room = factory.createRoom();
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        
        // First assignment
        Screening screening1 = factory.createScreening();
        screening1.setTime(screeningTime);
        boolean firstResult = cinema.assignScreening(film, currentTime, screening1, room);
        assertTrue("First screening assignment should succeed", firstResult);
        
        // Action - Second assignment at same time
        Screening screening2 = factory.createScreening();
        screening2.setTime(screeningTime);
        boolean result = cinema.assignScreening(film, currentTime, screening2, room);
        
        // Assert
        assertFalse("Should not assign screening to already booked room", result);
        assertFalse("Second screening should not be added to cinema", cinema.getScreenings().contains(screening2));
    }
    
    @Test
    public void testCase3_AssignWithNonExistentFilm() throws Exception {
        // Setup
        Room room = factory.createRoom();
        cinema.addRoom(room);
        
        Film nonExistentFilm = factory.createFilm(); // Not added to cinema
        
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Screening screening = factory.createScreening();
        screening.setTime(screeningTime);
        
        // Action
        boolean result = cinema.assignScreening(nonExistentFilm, currentTime, screening, room);
        
        // Assert
        assertFalse("Should not assign screening with non-existent film", result);
        assertFalse("Screening should not be added to cinema", cinema.getScreenings().contains(screening));
    }
    
    @Test
    public void testCase4_AssignScreeningAtCurrentTimeBoundary() throws Exception {
        // Setup
        Film film = factory.createFilm();
        Room room = factory.createRoom();
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Date screeningTime = dateFormat.parse("2024-12-01 10:00:00"); // Same as current time
        Screening screening = factory.createScreening();
        screening.setTime(screeningTime);
        
        // Action
        boolean result = cinema.assignScreening(film, currentTime, screening, room);
        
        // Assert
        assertFalse("Should not assign screening at current time boundary", result);
        assertFalse("Screening should not be added to cinema", cinema.getScreenings().contains(screening));
    }
    
    @Test
    public void testCase5_AssignScreeningInPastTime() throws Exception {
        // Setup
        Film film = factory.createFilm();
        Room room = factory.createRoom();
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Date screeningTime = dateFormat.parse("2024-11-30 14:00:00"); // Past time
        Screening screening = factory.createScreening();
        screening.setTime(screeningTime);
        
        // Action
        boolean result = cinema.assignScreening(film, currentTime, screening, room);
        
        // Assert
        assertFalse("Should not assign screening in past time", result);
        assertFalse("Screening should not be added to cinema", cinema.getScreenings().contains(screening));
    }
    
    @Test
    public void testCase6_AssignToNonExistentRoom() throws Exception {
        // Setup
        Film film = factory.createFilm();
        cinema.addFilm(film);
        
        Room nonExistentRoom = factory.createRoom(); // Not added to cinema
        
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Screening screening = factory.createScreening();
        screening.setTime(screeningTime);
        
        // Action
        boolean result = cinema.assignScreening(film, currentTime, screening, nonExistentRoom);
        
        // Assert
        assertFalse("Should not assign screening to non-existent room", result);
        assertFalse("Screening should not be added to cinema", cinema.getScreenings().contains(screening));
    }
}