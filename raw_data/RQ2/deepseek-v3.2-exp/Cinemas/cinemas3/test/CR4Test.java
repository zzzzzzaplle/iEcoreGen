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

public class CR4Test {
    private CinemasFactory factory;
    private SimpleDateFormat dateFormat;

    @Before
    public void setUp() {
        factory = CinemasFactory.eINSTANCE;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    // Test Case 1: Remove film with no screenings
    @Test
    public void testRemoveFilm_NoScreenings() throws Exception {
        // Setup
        Cinema cinema = factory.createCinema();
        Film film = factory.createFilm();
        cinema.addFilm(film);
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        // Action
        boolean result = cinema.removeFilm(film, currentTime);
        
        // Assertions
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer be in cinema", cinema.getFilm().contains(film));
    }

    // Test Case 2: Remove non-existent film
    @Test
    public void testRemoveFilm_NonExistent() throws Exception {
        // Setup
        Cinema cinema = factory.createCinema();
        Film film = factory.createFilm();
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        // Action
        boolean result = cinema.removeFilm(film, currentTime);
        
        // Assertions
        assertFalse("Should return false for non-existent film", result);
        assertTrue("Film list should be empty", cinema.getFilm().isEmpty());
    }

    // Test Case 3: Remove film with future screening
    @Test
    public void testRemoveFilm_FutureScreening() throws Exception {
        // Setup
        Cinema cinema = factory.createCinema();
        Film film = factory.createFilm();
        Room room = factory.createRoom();
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        Screening screening = factory.createScreening();
        screening.setTime(dateFormat.parse("2024-12-02 15:00:00"));
        screening.setFilm(film);
        screening.setRoom(room);
        cinema.getScreenings().add(screening);
        
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        // Action
        boolean result = cinema.removeFilm(film, currentTime);
        
        // Assertions
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer be in cinema", cinema.getFilm().contains(film));
        assertTrue("Screening should be removed", cinema.getScreenings().isEmpty());
    }

    // Test Case 4: Remove film with past screening
    @Test
    public void testRemoveFilm_PastScreening() throws Exception {
        // Setup
        Cinema cinema = factory.createCinema();
        Film film = factory.createFilm();
        Room room = factory.createRoom();
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        Screening screening = factory.createScreening();
        screening.setTime(dateFormat.parse("2024-11-30 18:00:00"));
        screening.setFilm(film);
        screening.setRoom(room);
        cinema.getScreenings().add(screening);
        
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        // Action
        boolean result = cinema.removeFilm(film, currentTime);
        
        // Assertions
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer be in cinema", cinema.getFilm().contains(film));
        assertEquals("Past screening should remain", 1, cinema.getScreenings().size());
    }

    // Test Case 5: Remove film with current-time screening
    @Test
    public void testRemoveFilm_CurrentTimeScreening() throws Exception {
        // Setup
        Cinema cinema = factory.createCinema();
        Film film = factory.createFilm();
        Room room = factory.createRoom();
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        Screening screening = factory.createScreening();
        screening.setTime(currentTime);
        screening.setFilm(film);
        screening.setRoom(room);
        cinema.getScreenings().add(screening);
        
        // Action
        boolean result = cinema.removeFilm(film, currentTime);
        
        // Assertions
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer be in cinema", cinema.getFilm().contains(film));
        assertTrue("Screening at current time should be removed", cinema.getScreenings().isEmpty());
    }
}