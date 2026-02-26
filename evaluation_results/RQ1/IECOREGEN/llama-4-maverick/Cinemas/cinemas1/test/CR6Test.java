package edu.cinemas.cinemas1.test;

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

public class CR6Test {
    
    private CinemasFactory factory;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() throws Exception {
        factory = CinemasFactory.eINSTANCE;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CancelFutureScreening() throws Exception {
        // Setup
        Cinema cinema = factory.createCinema();
        Film film = factory.createFilm();
        Room room = factory.createRoom();
        Screening screening = factory.createScreening();
        
        // Add film and room to cinema
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        // Set screening time and assign screening
        Date screeningTime = dateFormat.parse("2024-10-03 12:00:00");
        screening.setTime(screeningTime);
        Date currentTimeForAssignment = dateFormat.parse("2024-10-01 12:00:00");
        cinema.assignScreening(film, currentTimeForAssignment, screening, room);
        
        // Action: cancel screening at future time
        Date cancelTime = dateFormat.parse("2024-10-02 12:00:00");
        boolean result = cinema.cancelScreening(cancelTime, screening);
        
        // Assertions
        assertTrue("Screening should be canceled successfully", result);
        assertFalse("Screening should be removed from cinema", cinema.getScreenings().contains(screening));
        assertEquals("Cinema should have no screenings", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_CancelNonExistentScreening() throws Exception {
        // Setup
        Cinema cinema = factory.createCinema();
        Film film = factory.createFilm();
        Room room = factory.createRoom();
        Screening screening = factory.createScreening();
        
        // Add film and room to cinema
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        // Set screening time but don't assign it to cinema
        Date screeningTime = dateFormat.parse("2024-10-02 12:05:00");
        screening.setTime(screeningTime);
        
        // Action: try to cancel screening that was never assigned
        Date cancelTime = dateFormat.parse("2024-10-03 12:05:00");
        boolean result = cinema.cancelScreening(cancelTime, screening);
        
        // Assertions
        assertFalse("Should return false for non-existent screening", result);
        assertEquals("Cinema should have no screenings", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase3_CancelAtExactScreeningTimeBoundary() throws Exception {
        // Setup
        Cinema cinema = factory.createCinema();
        Film film = factory.createFilm();
        Room room = factory.createRoom();
        Screening screening = factory.createScreening();
        
        // Add film and room to cinema
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        // Set screening time and assign screening
        Date screeningTime = dateFormat.parse("2024-12-03 10:00:00");
        screening.setTime(screeningTime);
        Date currentTimeForAssignment = dateFormat.parse("2024-12-01 10:00:00");
        cinema.assignScreening(film, currentTimeForAssignment, screening, room);
        
        // Action: try to cancel at exact screening time
        Date cancelTime = dateFormat.parse("2024-12-03 10:00:00");
        boolean result = cinema.cancelScreening(cancelTime, screening);
        
        // Assertions
        assertFalse("Should return false when canceling at exact screening time", result);
        assertTrue("Screening should still be in cinema", cinema.getScreenings().contains(screening));
        assertEquals("Cinema should have 1 screening", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_CancelAlreadyCancelledScreening() throws Exception {
        // Setup
        Cinema cinema = factory.createCinema();
        Film film = factory.createFilm();
        Room room = factory.createRoom();
        Screening screening = factory.createScreening();
        
        // Add film and room to cinema
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        // Set screening time and assign screening
        Date screeningTime = dateFormat.parse("2024-10-03 12:00:00");
        screening.setTime(screeningTime);
        Date currentTimeForAssignment = dateFormat.parse("2024-10-01 12:00:00");
        cinema.assignScreening(film, currentTimeForAssignment, screening, room);
        
        // First cancellation (should succeed)
        Date firstCancelTime = dateFormat.parse("2024-10-02 12:00:00");
        boolean firstResult = cinema.cancelScreening(firstCancelTime, screening);
        assertTrue("First cancellation should succeed", firstResult);
        
        // Action: try to cancel already cancelled screening
        Date secondCancelTime = dateFormat.parse("2024-10-02 12:05:00");
        boolean secondResult = cinema.cancelScreening(secondCancelTime, screening);
        
        // Assertions
        assertFalse("Should return false for already cancelled screening", secondResult);
        assertFalse("Screening should not be in cinema", cinema.getScreenings().contains(screening));
        assertEquals("Cinema should have no screenings", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_CancelPastScreeningAfterShowtime() throws Exception {
        // Setup
        Cinema cinema = factory.createCinema();
        Film film = factory.createFilm();
        Room room = factory.createRoom();
        Screening screening = factory.createScreening();
        
        // Add film and room to cinema
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        // Set screening time and assign screening
        Date screeningTime = dateFormat.parse("2024-12-01 14:00:00");
        screening.setTime(screeningTime);
        Date currentTimeForAssignment = dateFormat.parse("2024-11-01 14:00:00");
        cinema.assignScreening(film, currentTimeForAssignment, screening, room);
        
        // Action: try to cancel past screening
        Date cancelTime = dateFormat.parse("2024-12-03 10:00:00");
        boolean result = cinema.cancelScreening(cancelTime, screening);
        
        // Assertions
        assertFalse("Should return false for past screening", result);
        assertTrue("Screening should still be in cinema", cinema.getScreenings().contains(screening));
        assertEquals("Cinema should have 1 screening", 1, cinema.getScreenings().size());
    }
}