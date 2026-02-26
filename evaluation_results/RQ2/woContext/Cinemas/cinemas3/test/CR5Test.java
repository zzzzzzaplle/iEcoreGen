package edu.cinemas.cinemas3.test;

import edu.cinemas.CinemasFactory;
import edu.cinemas.Cinema;
import edu.cinemas.Film;
import edu.cinemas.Room;
import edu.cinemas.Screening;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR5Test {
    private Cinema cinema;
    private Film film;
    private Room room;
    private CinemasFactory factory;
    private SimpleDateFormat dateFormat;

    @Before
    public void setUp() {
        // Initialize factory and date format
        factory = CinemasFactory.eINSTANCE;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Create new cinema for each test
        cinema = factory.createCinema();
    }

    // Helper method to parse dates
    private Date parseDate(String dateStr) throws Exception {
        return dateFormat.parse(dateStr);
    }

    @Test
    public void testCase1_ValidFutureScreeningAssignment() throws Exception {
        // Setup: Create and add film/room
        film = factory.createFilm();
        room = factory.createRoom();
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        // Create screening and set time
        Screening screening = factory.createScreening();
        screening.setTime(parseDate("2024-12-02 14:00:00"));
        
        // Execute assignment
        boolean result = cinema.assignScreening(
            film, 
            parseDate("2024-12-01 10:00:00"), 
            screening, 
            room
        );
        
        // Verify successful assignment
        assertTrue(result);
    }

    @Test
    public void testCase2_AssignToAlreadyBookedRoom() throws Exception {
        // Setup: Create and add film/room
        film = factory.createFilm();
        room = factory.createRoom();
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        // Create first screening and assign
        Screening firstScreening = factory.createScreening();
        firstScreening.setTime(parseDate("2024-12-02 14:00:00"));
        cinema.assignScreening(
            film, 
            parseDate("2024-12-01 10:00:00"), 
            firstScreening, 
            room
        );
        
        // Create second screening for same time/room
        Screening secondScreening = factory.createScreening();
        secondScreening.setTime(parseDate("2024-12-02 14:00:00"));
        
        // Execute assignment
        boolean result = cinema.assignScreening(
            film, 
            parseDate("2024-12-01 10:00:00"), 
            secondScreening, 
            room
        );
        
        // Verify assignment fails
        assertFalse(result);
    }

    @Test
    public void testCase3_AssignWithNonExistentFilm() throws Exception {
        // Setup: Create and add room only
        room = factory.createRoom();
        cinema.addRoom(room);
        
        // Create film not added to cinema
        film = factory.createFilm();
        
        // Create screening
        Screening screening = factory.createScreening();
        screening.setTime(parseDate("2024-12-02 14:00:00"));
        
        // Execute assignment
        boolean result = cinema.assignScreening(
            film, 
            parseDate("2024-12-01 10:00:00"), 
            screening, 
            room
        );
        
        // Verify assignment fails
        assertFalse(result);
    }

    @Test
    public void testCase4_AssignScreeningAtCurrentTimeBoundary() throws Exception {
        // Setup: Create and add film/room
        film = factory.createFilm();
        room = factory.createRoom();
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        // Create screening with same time as current
        Screening screening = factory.createScreening();
        Date currentTime = parseDate("2024-12-01 10:00:00");
        screening.setTime(currentTime);
        
        // Execute assignment
        boolean result = cinema.assignScreening(
            film, 
            currentTime, 
            screening, 
            room
        );
        
        // Verify assignment fails
        assertFalse(result);
    }

    @Test
    public void testCase5_AssignScreeningInPastTime() throws Exception {
        // Setup: Create and add film/room
        film = factory.createFilm();
        room = factory.createRoom();
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        // Create screening with past time
        Screening screening = factory.createScreening();
        screening.setTime(parseDate("2024-11-30 14:00:00"));
        
        // Execute assignment
        boolean result = cinema.assignScreening(
            film, 
            parseDate("2024-12-01 10:00:00"), 
            screening, 
            room
        );
        
        // Verify assignment fails
        assertFalse(result);
    }

    @Test
    public void testCase6_AssignToNonExistentRoom() throws Exception {
        // Setup: Create and add film only
        film = factory.createFilm();
        cinema.addFilm(film);
        
        // Create room not added to cinema
        room = factory.createRoom();
        
        // Create screening
        Screening screening = factory.createScreening();
        screening.setTime(parseDate("2024-12-02 14:00:00"));
        
        // Execute assignment
        boolean result = cinema.assignScreening(
            film, 
            parseDate("2024-12-01 10:00:00"), 
            screening, 
            room
        );
        
        // Verify assignment fails
        assertFalse(result);
    }
}