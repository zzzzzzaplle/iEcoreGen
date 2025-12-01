package edu.cinemas.cinemas2.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.text.SimpleDateFormat;
import java.util.Date;
import edu.cinemas.CinemasFactory;
import edu.cinemas.Cinema;
import edu.cinemas.Film;
import edu.cinemas.Room;
import edu.cinemas.Screening;

public class CR4Test {
    private CinemasFactory factory;
    private Cinema cinema;
    private Film film1;
    private Room room1;
    private SimpleDateFormat dateFormat;

    @Before
    public void setUp() {
        factory = CinemasFactory.eINSTANCE;
        cinema = factory.createCinema();
        film1 = factory.createFilm();
        room1 = factory.createRoom();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    public void testCase1_RemoveFilmWithNoScreenings() throws Exception {
        // Setup
        cinema.addFilm(film1);
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        // Action
        boolean result = cinema.removeFilm(film1, currentTime);
        
        // Assert
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should be removed from cinema", cinema.getFilm().contains(film1));
        assertTrue("Screenings list should be empty", cinema.getScreenings().isEmpty());
    }

    @Test
    public void testCase2_RemoveNonExistentFilm() throws Exception {
        // Setup
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        // Action
        boolean result = cinema.removeFilm(film1, currentTime);
        
        // Assert
        assertFalse("Non-existent film should not be removed", result);
        assertTrue("Film list should remain empty", cinema.getFilm().isEmpty());
    }

    @Test
    public void testCase3_RemoveFilmWithFutureScreening() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Screening screening = factory.createScreening();
        screening.setFilm(film1);
        screening.setRoom(room1);
        screening.setTime(dateFormat.parse("2024-12-02 15:00:00"));
        cinema.getScreenings().add(screening);
        
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        // Action
        boolean result = cinema.removeFilm(film1, currentTime);
        
        // Assert
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should be removed from cinema", cinema.getFilm().contains(film1));
        assertTrue("Future screening should be removed", cinema.getScreenings().isEmpty());
    }

    @Test
    public void testCase4_RemoveFilmWithPastScreening() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Screening screening = factory.createScreening();
        screening.setFilm(film1);
        screening.setRoom(room1);
        screening.setTime(dateFormat.parse("2024-11-30 18:00:00"));
        cinema.getScreenings().add(screening);
        
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        // Action
        boolean result = cinema.removeFilm(film1, currentTime);
        
        // Assert
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should be removed from cinema", cinema.getFilm().contains(film1));
        assertEquals("Past screening should remain", 1, cinema.getScreenings().size());
    }

    @Test
    public void testCase5_RemoveFilmWithCurrentTimeScreening() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-12-01 10:00:00");
        Screening screening = factory.createScreening();
        screening.setFilm(film1);
        screening.setRoom(room1);
        screening.setTime(screeningTime);
        cinema.getScreenings().add(screening);
        
        // Action
        boolean result = cinema.removeFilm(film1, screeningTime);
        
        // Assert
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should be removed from cinema", cinema.getFilm().contains(film1));
        assertTrue("Current-time screening should be removed", cinema.getScreenings().isEmpty());
    }
}