package edu.cinemas.cinemas5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import edu.cinemas.Cinema;
import edu.cinemas.Film;
import edu.cinemas.Screening;
import edu.cinemas.Room;
import edu.cinemas.CinemasFactory;
import edu.cinemas.CinemasPackage;

public class CR4Test {
    
    private Cinema cinema;
    private CinemasFactory factory;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        // Initialize the cinema package and factory
        CinemasPackage.eINSTANCE.eClass();
        factory = CinemasFactory.eINSTANCE;
        cinema = factory.createCinema();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_RemoveFilmWithNoScreenings() throws Exception {
        // Setup: Create Cinema C1 and add Film F1
        Film film1 = factory.createFilm();
        cinema.addFilm(film1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(film1, currentTime);
        
        // Expected Output: true
        assertTrue("Film should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilm().contains(film1));
    }
    
    @Test
    public void testCase2_RemoveNonExistentFilm() throws Exception {
        // Setup: Create Cinema C1 without adding Film F1
        Film film1 = factory.createFilm();
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(film1, currentTime);
        
        // Expected Output: false (Film F1 do not exist in cinema)
        assertFalse("Non-existent film should not be removed", result);
    }
    
    @Test
    public void testCase3_RemoveFilmWithFutureScreening() throws Exception {
        // Setup: Create Cinema C1, add Film F1, and assign screening
        Film film1 = factory.createFilm();
        Room room1 = factory.createRoom();
        Screening screening1 = factory.createScreening();
        
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Set screening time to future date
        Date screeningTime = dateFormat.parse("2024-12-02 15:00:00");
        screening1.setTime(screeningTime);
        screening1.setFilm(film1);
        screening1.setRoom(room1);
        
        // Add screening to cinema
        cinema.getScreenings().add(screening1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(film1, currentTime);
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film with future screening should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilm().contains(film1));
        assertTrue("Future screening should be removed", cinema.getScreenings().isEmpty());
    }
    
    @Test
    public void testCase4_RemoveFilmWithPastScreening() throws Exception {
        // Setup: Create Cinema C1, add Film F1, and schedule past screening
        Film film1 = factory.createFilm();
        Room room1 = factory.createRoom();
        Screening screening1 = factory.createScreening();
        
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Set screening time to past date
        Date screeningTime = dateFormat.parse("2024-11-30 18:00:00");
        screening1.setTime(screeningTime);
        screening1.setFilm(film1);
        screening1.setRoom(room1);
        
        // Add screening to cinema
        cinema.getScreenings().add(screening1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(film1, currentTime);
        
        // Expected Output: true, and there is 1 screening in the cinema
        assertTrue("Film with past screening should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilm().contains(film1));
        assertEquals("Past screening should remain in cinema", 1, cinema.getScreenings().size());
        assertTrue("Past screening should still exist", cinema.getScreenings().contains(screening1));
    }
    
    @Test
    public void testCase5_RemoveFilmWithCurrentTimeScreening() throws Exception {
        // Setup: Create Cinema C1, add Film F1, and schedule current-time screening
        Film film1 = factory.createFilm();
        Room room1 = factory.createRoom();
        Screening screening1 = factory.createScreening();
        
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Set screening time to current time
        Date screeningTime = dateFormat.parse("2024-12-01 10:00:00");
        screening1.setTime(screeningTime);
        screening1.setFilm(film1);
        screening1.setRoom(room1);
        
        // Add screening to cinema
        cinema.getScreenings().add(screening1);
        
        // Action: Remove Film F1 at current time "2024-12-01 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.removeFilm(film1, currentTime);
        
        // Expected Output: true, and there is no screening in the cinema
        assertTrue("Film with current-time screening should be removed successfully", result);
        assertFalse("Film should no longer exist in cinema", cinema.getFilm().contains(film1));
        assertTrue("Current-time screening should be removed", cinema.getScreenings().isEmpty());
    }
}