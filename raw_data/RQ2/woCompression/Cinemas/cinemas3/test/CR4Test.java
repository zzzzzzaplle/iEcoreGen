package edu.cinemas.cinemas3.test;

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
    
    private Cinema cinema;
    private CinemasFactory factory;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        // Initialize cinema and factory using Ecore factory pattern
        factory = CinemasFactory.eINSTANCE;
        cinema = factory.createCinema();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_RemoveFilmWithNoScreenings() throws Exception {
        // Setup: Create Cinema C1 and add Film F1
        Film filmF1 = factory.createFilm();
        cinema.addFilm(filmF1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: true
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilm().contains(filmF1));
    }
    
    @Test
    public void testCase2_RemoveNonExistentFilm() throws Exception {
        // Setup: Create Cinema C1 (do not add Film F1)
        Film filmF1 = factory.createFilm();
        // Film F1 is not added to cinema
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: false (Film F1 do not exist in cinema)
        assertFalse("Non-existent film should not be removed", result);
        assertFalse("Film should not exist in cinema", cinema.getFilm().contains(filmF1));
    }
    
    @Test
    public void testCase3_RemoveFilmWithFutureScreening() throws Exception {
        // Setup: Create Cinema C1, add Film F1, and assign screening for F1
        Film filmF1 = factory.createFilm();
        Room room = factory.createRoom();
        Screening screening = factory.createScreening();
        
        cinema.addFilm(filmF1);
        cinema.addRoom(room);
        
        // Set screening time to future date
        Date screeningTime = dateFormat.parse("2024-12-02 15:00:00");
        screening.setTime(screeningTime);
        
        // Assign screening using assignScreening method
        Date setupCurrentTime = dateFormat.parse("2024-12-01 09:00:00");
        cinema.assignScreening(filmF1, setupCurrentTime, screening, room);
        
        // Verify setup: film and screening exist
        assertTrue("Film should exist in cinema", cinema.getFilm().contains(filmF1));
        assertEquals("Should have 1 screening", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilm().contains(filmF1));
        assertEquals("All future screenings should be removed", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_RemoveFilmWithPastScreening() throws Exception {
        // Setup: Create Cinema C1, add Film F1, and schedule screening for F1
        Film filmF1 = factory.createFilm();
        Room room = factory.createRoom();
        Screening screening = factory.createScreening();
        
        cinema.addFilm(filmF1);
        cinema.addRoom(room);
        
        // Set screening time to past date
        Date screeningTime = dateFormat.parse("2024-11-30 18:00:00");
        screening.setTime(screeningTime);
        
        // Manually create and add screening since it's in the past
        screening.setFilm(filmF1);
        screening.setRoom(room);
        screening.setCinemas(cinema);
        cinema.getScreenings().add(screening);
        
        // Verify setup: film and screening exist
        assertTrue("Film should exist in cinema", cinema.getFilm().contains(filmF1));
        assertEquals("Should have 1 screening", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: true, and there is 1 screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilm().contains(filmF1));
        assertEquals("Past screenings should remain", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_RemoveFilmWithCurrentTimeScreening() throws Exception {
        // Setup: Create Cinema C1, add Film F1, and schedule screening for F1
        Film filmF1 = factory.createFilm();
        Room room = factory.createRoom();
        Screening screening = factory.createScreening();
        
        cinema.addFilm(filmF1);
        cinema.addRoom(room);
        
        // Set screening time to current time
        Date screeningTime = dateFormat.parse("2024-12-01 10:00:00");
        screening.setTime(screeningTime);
        
        // Manually create and add screening since it's at current time
        screening.setFilm(filmF1);
        screening.setRoom(room);
        screening.setCinemas(cinema);
        cinema.getScreenings().add(screening);
        
        // Verify setup: film and screening exist
        assertTrue("Film should exist in cinema", cinema.getFilm().contains(filmF1));
        assertEquals("Should have 1 screening", 1, cinema.getScreenings().size());
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(filmF1, currentTime);
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilm().contains(filmF1));
        assertEquals("Current time screenings should be removed", 0, cinema.getScreenings().size());
    }
}