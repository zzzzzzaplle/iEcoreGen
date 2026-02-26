package edu.cinemas.cinemas2.test;

import edu.cinemas.Cinema;
import edu.cinemas.CinemasFactory;
import edu.cinemas.Film;
import edu.cinemas.Room;
import edu.cinemas.Screening;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR5Test {
    private Cinema cinema;
    private Film film1;
    private Room room1;
    private SimpleDateFormat dateFormat;

    @Before
    public void setUp() throws Exception {
        // Create cinema instance using Ecore factory pattern
        cinema = CinemasFactory.eINSTANCE.createCinema();
        
        // Create film and room instances
        film1 = CinemasFactory.eINSTANCE.createFilm();
        room1 = CinemasFactory.eINSTANCE.createRoom();
        
        // Initialize date formatter
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    public void testCase1_ValidFutureScreeningAssignment() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        Screening screening = CinemasFactory.eINSTANCE.createScreening();
        screening.setFilm(film1);
        screening.setRoom(room1);
        screening.setTime(dateFormat.parse("2024-12-02 14:00:00"));
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        // Execute
        boolean result = cinema.assignScreening(film1, currentTime, screening, room1);
        
        // Verify
        assertTrue("Valid screening should be assigned", result);
    }

    @Test
    public void testCase2_AssignToAlreadyBookedRoom() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        Screening screening1 = CinemasFactory.eINSTANCE.createScreening();
        screening1.setFilm(film1);
        screening1.setRoom(room1);
        screening1.setTime(dateFormat.parse("2024-12-02 14:00:00"));
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        cinema.assignScreening(film1, currentTime, screening1, room1);
        
        // Create second screening at same time
        Screening screening2 = CinemasFactory.eINSTANCE.createScreening();
        screening2.setFilm(film1);
        screening2.setRoom(room1);
        screening2.setTime(dateFormat.parse("2024-12-02 14:00:00"));
        
        // Execute
        boolean result = cinema.assignScreening(film1, currentTime, screening2, room1);
        
        // Verify
        assertFalse("Room already booked should fail", result);
    }

    @Test
    public void testCase3_AssignWithNonExistentFilm() throws Exception {
        // Setup
        cinema.addRoom(room1);
        Film film2 = CinemasFactory.eINSTANCE.createFilm();
        Screening screening = CinemasFactory.eINSTANCE.createScreening();
        screening.setFilm(film2);
        screening.setRoom(room1);
        screening.setTime(dateFormat.parse("2024-12-02 14:00:00"));
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        // Execute
        boolean result = cinema.assignScreening(film2, currentTime, screening, room1);
        
        // Verify
        assertFalse("Non-existent film should fail", result);
    }

    @Test
    public void testCase4_AssignScreeningAtCurrentTimeBoundary() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        Screening screening = CinemasFactory.eINSTANCE.createScreening();
        screening.setFilm(film1);
        screening.setRoom(room1);
        Date screeningTime = dateFormat.parse("2024-12-01 10:00:00");
        screening.setTime(screeningTime);
        
        // Execute
        boolean result = cinema.assignScreening(film1, screeningTime, screening, room1);
        
        // Verify
        assertFalse("Current time boundary should fail", result);
    }

    @Test
    public void testCase5_AssignScreeningInPastTime() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        Screening screening = CinemasFactory.eINSTANCE.createScreening();
        screening.setFilm(film1);
        screening.setRoom(room1);
        screening.setTime(dateFormat.parse("2024-11-30 14:00:00"));
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        // Execute
        boolean result = cinema.assignScreening(film1, currentTime, screening, room1);
        
        // Verify
        assertFalse("Past time screening should fail", result);
    }

    @Test
    public void testCase6_AssignToNonExistentRoom() throws Exception {
        // Setup
        cinema.addFilm(film1);
        Room room2 = CinemasFactory.eINSTANCE.createRoom();
        Screening screening = CinemasFactory.eINSTANCE.createScreening();
        screening.setFilm(film1);
        screening.setRoom(room2);
        screening.setTime(dateFormat.parse("2024-12-02 14:00:00"));
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        // Execute
        boolean result = cinema.assignScreening(film1, currentTime, screening, room2);
        
        // Verify
        assertFalse("Non-existent room should fail", result);
    }
}