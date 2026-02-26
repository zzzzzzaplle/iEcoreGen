package edu.cinemas.cinemas4.test;

import edu.cinemas.*;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR4Test {
    private CinemasFactory factory;
    private Cinema cinema;
    private Film film;
    private Room room;
    private SimpleDateFormat dateFormat;

    @Before
    public void setUp() {
        factory = CinemasFactory.eINSTANCE;
        cinema = factory.createCinema();
        film = factory.createFilm();
        room = factory.createRoom();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    public void testCase1_RemoveFilmWithNoScreenings() throws Exception {
        // Setup: Add Film F1 to cinema
        cinema.addFilm(film);
        
        // Action: Remove film at current time
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(film, currentTime);
        
        // Verify
        assertTrue("Film should be successfully removed", result);
        assertFalse("Film list should be empty", cinema.getFilm().contains(film));
    }

    @Test
    public void testCase2_RemoveNonExistentFilm() throws Exception {
        // Setup: Cinema has no films
        
        // Action: Remove non-existent film
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(film, currentTime);
        
        // Verify
        assertFalse("Should return false for non-existent film", result);
    }

    @Test
    public void testCase3_RemoveFilmWithFutureScreening() throws Exception {
        // Setup
        cinema.addFilm(film);
        cinema.addRoom(room);
        Screening screening = factory.createScreening();
        screening.setTime(dateFormat.parse("2024-12-02 15:00:00"));
        cinema.assignScreening(film, dateFormat.parse("2024-12-01 10:00:00"), screening, room);
        
        // Action
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(film, currentTime);
        
        // Verify
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film list should be empty", cinema.getFilm().contains(film));
        assertTrue("Future screening should be removed", cinema.getScreenings().isEmpty());
    }

    @Test
    public void testCase4_RemoveFilmWithPastScreening() throws Exception {
        // Setup
        cinema.addFilm(film);
        cinema.addRoom(room);
        Screening screening = factory.createScreening();
        screening.setTime(dateFormat.parse("2024-11-30 18:00:00"));
        cinema.assignScreening(film, dateFormat.parse("2024-11-29 10:00:00"), screening, room);
        
        // Action
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(film, currentTime);
        
        // Verify
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film list should be empty", cinema.getFilm().contains(film));
        assertEquals("Past screening should remain", 1, cinema.getScreenings().size());
    }

    @Test
    public void testCase5_RemoveFilmWithCurrentTimeScreening() throws Exception {
        // Setup
        cinema.addFilm(film);
        cinema.addRoom(room);
        Screening screening = factory.createScreening();
        screening.setTime(dateFormat.parse("2024-12-01 10:00:00"));
        cinema.assignScreening(film, dateFormat.parse("2024-12-01 09:00:00"), screening, room);
        
        // Action
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(film, currentTime);
        
        // Verify
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film list should be empty", cinema.getFilm().contains(film));
        assertTrue("Current time screening should be removed", cinema.getScreenings().isEmpty());
    }
}