package edu.cinemas.cinemas2.test;

import static org.junit.Assert.*;
import org.junit.Test;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import edu.cinemas.Cinema;
import edu.cinemas.CinemasFactory;
import edu.cinemas.Film;
import edu.cinemas.Room;
import edu.cinemas.Screening;

public class CR5Test {

    // Helper method to parse date strings
    private Date parseDate(String dateString) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString);
    }

    @Test
    public void testCase1_ValidFutureScreeningAssignment() throws ParseException {
        // Setup
        CinemasFactory factory = CinemasFactory.eINSTANCE;
        Cinema cinema = factory.createCinema();
        Film film = factory.createFilm();
        Room room = factory.createRoom();
        
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        // Create screening and set attributes
        Screening screening = factory.createScreening();
        screening.setTime(parseDate("2024-12-02 14:00:00"));
        screening.setFilm(film);
        screening.setRoom(room);
        
        // Execute and Verify
        assertTrue(cinema.assignScreening(
            film, 
            parseDate("2024-12-01 10:00:00"), 
            screening, 
            room
        ));
    }

    @Test
    public void testCase2_AssignToAlreadyBookedRoom() throws ParseException {
        // Setup
        CinemasFactory factory = CinemasFactory.eINSTANCE;
        Cinema cinema = factory.createCinema();
        Film film = factory.createFilm();
        Room room = factory.createRoom();
        
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        // First booking (should succeed)
        Screening firstScreening = factory.createScreening();
        firstScreening.setTime(parseDate("2024-12-02 14:00:00"));
        firstScreening.setFilm(film);
        firstScreening.setRoom(room);
        cinema.assignScreening(film, parseDate("2024-12-01 10:00:00"), firstScreening, room);
        
        // Second booking attempt
        Screening secondScreening = factory.createScreening();
        secondScreening.setTime(parseDate("2024-12-02 14:00:00"));
        secondScreening.setFilm(film);
        secondScreening.setRoom(room);
        
        // Execute and Verify
        assertFalse(cinema.assignScreening(
            film, 
            parseDate("2024-12-01 10:00:00"), 
            secondScreening, 
            room
        ));
    }

    @Test
    public void testCase3_AssignWithNonExistentFilm() throws ParseException {
        // Setup
        CinemasFactory factory = CinemasFactory.eINSTANCE;
        Cinema cinema = factory.createCinema();
        Room room = factory.createRoom();
        cinema.addRoom(room);
        
        Film nonExistentFilm = factory.createFilm();
        Screening screening = factory.createScreening();
        screening.setTime(parseDate("2024-12-02 14:00:00"));
        screening.setFilm(nonExistentFilm);
        screening.setRoom(room);
        
        // Execute and Verify
        assertFalse(cinema.assignScreening(
            nonExistentFilm, 
            parseDate("2024-12-01 10:00:00"), 
            screening, 
            room
        ));
    }

    @Test
    public void testCase4_AssignScreeningAtCurrentTimeBoundary() throws ParseException {
        // Setup
        CinemasFactory factory = CinemasFactory.eINSTANCE;
        Cinema cinema = factory.createCinema();
        Film film = factory.createFilm();
        Room room = factory.createRoom();
        
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        Screening screening = factory.createScreening();
        screening.setTime(parseDate("2024-12-01 10:00:00"));
        screening.setFilm(film);
        screening.setRoom(room);
        
        // Execute and Verify
        assertFalse(cinema.assignScreening(
            film, 
            parseDate("2024-12-01 10:00:00"), 
            screening, 
            room
        ));
    }

    @Test
    public void testCase5_AssignScreeningInPastTime() throws ParseException {
        // Setup
        CinemasFactory factory = CinemasFactory.eINSTANCE;
        Cinema cinema = factory.createCinema();
        Film film = factory.createFilm();
        Room room = factory.createRoom();
        
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        Screening screening = factory.createScreening();
        screening.setTime(parseDate("2024-11-30 14:00:00"));
        screening.setFilm(film);
        screening.setRoom(room);
        
        // Execute and Verify
        assertFalse(cinema.assignScreening(
            film, 
            parseDate("2024-12-01 10:00:00"), 
            screening, 
            room
        ));
    }

    @Test
    public void testCase6_AssignToNonExistentRoom() throws ParseException {
        // Setup
        CinemasFactory factory = CinemasFactory.eINSTANCE;
        Cinema cinema = factory.createCinema();
        Film film = factory.createFilm();
        cinema.addFilm(film);
        
        Room nonExistentRoom = factory.createRoom();
        Screening screening = factory.createScreening();
        screening.setTime(parseDate("2024-12-02 14:00:00"));
        screening.setFilm(film);
        screening.setRoom(nonExistentRoom);
        
        // Execute and Verify
        assertFalse(cinema.assignScreening(
            film, 
            parseDate("2024-12-01 10:00:00"), 
            screening, 
            nonExistentRoom
        ));
    }
}