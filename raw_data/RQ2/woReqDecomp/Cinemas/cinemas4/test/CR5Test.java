package edu.cinemas.cinemas4.test;

import edu.cinemas.Cinema;
import edu.cinemas.Film;
import edu.cinemas.Room;
import edu.cinemas.Screening;
import edu.cinemas.CinemasFactory;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CR5Test {

    private Cinema cinema;
    private Film film;
    private Room room;
    private Screening screening;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Before
    public void setUp() {
        cinema = CinemasFactory.eINSTANCE.createCinema();
        film = CinemasFactory.eINSTANCE.createFilm();
        room = CinemasFactory.eINSTANCE.createRoom();
        screening = CinemasFactory.eINSTANCE.createScreening();
    }

    // Test Case 1: Valid future screening assignment
    @Test
    public void testCase1_validFutureScreeningAssignment() throws Exception {
        // Setup
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        // Create dates
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        screening.setTime(screeningTime);
        
        // Action & Assertion
        assertTrue(cinema.assignScreening(film, currentTime, screening, room));
    }

    // Test Case 2: Assign to already booked room
    @Test
    public void testCase2_assignToAlreadyBookedRoom() throws Exception {
        // Setup
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        // Create dates
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        screening.setTime(screeningTime);
        
        // First assignment (should succeed)
        cinema.assignScreening(film, currentTime, screening, room);
        
        // Create new screening for same time
        Screening newScreening = CinemasFactory.eINSTANCE.createScreening();
        newScreening.setTime(screeningTime);
        
        // Action & Assertion
        assertFalse(cinema.assignScreening(film, currentTime, newScreening, room));
    }

    // Test Case 3: Assign with non-existent film
    @Test
    public void testCase3_assignWithNonExistentFilm() throws Exception {
        // Setup
        cinema.addRoom(room);
        
        // Create dates
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        screening.setTime(screeningTime);
        
        // Create new film not added to cinema
        Film otherFilm = CinemasFactory.eINSTANCE.createFilm();
        
        // Action & Assertion
        assertFalse(cinema.assignScreening(otherFilm, currentTime, screening, room));
    }

    // Test Case 4: Assign screening at current time boundary
    @Test
    public void testCase4_assignScreeningAtCurrentTimeBoundary() throws Exception {
        // Setup
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        // Create dates (same time)
        Date boundaryTime = dateFormat.parse("2024-12-01 10:00:00");
        screening.setTime(boundaryTime);
        
        // Action & Assertion
        assertFalse(cinema.assignScreening(film, boundaryTime, screening, room));
    }

    // Test Case 5: Assign screening in past time
    @Test
    public void testCase5_assignScreeningInPastTime() throws Exception {
        // Setup
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        // Create dates
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Date pastTime = dateFormat.parse("2024-11-30 14:00:00");
        screening.setTime(pastTime);
        
        // Action & Assertion
        assertFalse(cinema.assignScreening(film, currentTime, screening, room));
    }

    // Test Case 6: Assign to non-existent room
    @Test
    public void testCase6_assignToNonExistentRoom() throws Exception {
        // Setup
        cinema.addFilm(film);
        
        // Create dates
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        screening.setTime(screeningTime);
        
        // Create room not added to cinema
        Room otherRoom = CinemasFactory.eINSTANCE.createRoom();
        
        // Action & Assertion
        assertFalse(cinema.assignScreening(film, currentTime, screening, otherRoom));
    }
}