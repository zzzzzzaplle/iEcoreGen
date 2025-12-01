package edu.cinemas.cinemas4.test;

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

public class CR4Test {
    
    private CinemasFactory factory;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        factory = CinemasFactory.eINSTANCE;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_RemoveFilmWithNoScreenings() throws Exception {
        // Setup
        Cinema cinema = factory.createCinema();
        Film film = factory.createFilm();
        cinema.addFilm(film);
        
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        // Action
        boolean result = cinema.removeFilm(film, currentTime);
        
        // Assert
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilm().contains(film));
        assertTrue("Screenings list should be empty", cinema.getScreenings().isEmpty());
    }
    
    @Test
    public void testCase2_RemoveNonExistentFilm() throws Exception {
        // Setup
        Cinema cinema = factory.createCinema();
        Film film = factory.createFilm();
        // Film not added to cinema
        
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        // Action
        boolean result = cinema.removeFilm(film, currentTime);
        
        // Assert
        assertFalse("Should return false for non-existent film", result);
        assertFalse("Film should not exist in cinema", cinema.getFilm().contains(film));
    }
    
    @Test
    public void testCase3_RemoveFilmWithFutureScreening() throws Exception {
        // Setup
        Cinema cinema = factory.createCinema();
        Film film = factory.createFilm();
        Room room = factory.createRoom();
        
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        // Create and assign future screening
        Screening screening = factory.createScreening();
        screening.setTime(dateFormat.parse("2024-12-02 15:00:00"));
        screening.setFilm(film);
        screening.setRoom(room);
        cinema.getScreenings().add(screening);
        
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        // Action
        boolean result = cinema.removeFilm(film, currentTime);
        
        // Assert
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilm().contains(film));
        assertTrue("Future screening should be removed", cinema.getScreenings().isEmpty());
    }
    
    @Test
    public void testCase4_RemoveFilmWithPastScreening() throws Exception {
        // Setup
        Cinema cinema = factory.createCinema();
        Film film = factory.createFilm();
        Room room = factory.createRoom();
        
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        // Create and assign past screening
        Screening screening = factory.createScreening();
        screening.setTime(dateFormat.parse("2024-11-30 18:00:00"));
        screening.setFilm(film);
        screening.setRoom(room);
        cinema.getScreenings().add(screening);
        
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        // Action
        boolean result = cinema.removeFilm(film, currentTime);
        
        // Assert
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilm().contains(film));
        assertEquals("Past screening should remain in cinema", 1, cinema.getScreenings().size());
        assertEquals("Screening should still reference the removed film", film, cinema.getScreenings().get(0).getFilm());
    }
    
    @Test
    public void testCase5_RemoveFilmWithCurrentTimeScreening() throws Exception {
        // Setup
        Cinema cinema = factory.createCinema();
        Film film = factory.createFilm();
        Room room = factory.createRoom();
        
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        // Create and assign screening at current time
        Date screeningTime = dateFormat.parse("2024-12-01 10:00:00");
        Screening screening = factory.createScreening();
        screening.setTime(screeningTime);
        screening.setFilm(film);
        screening.setRoom(room);
        cinema.getScreenings().add(screening);
        
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        // Action
        boolean result = cinema.removeFilm(film, currentTime);
        
        // Assert
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilm().contains(film));
        assertTrue("Current-time screening should be removed", cinema.getScreenings().isEmpty());
    }
}