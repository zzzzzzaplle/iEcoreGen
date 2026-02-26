package edu.cinemas.cinemas2.test;

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
    
    private Cinema cinema;
    private CinemasFactory factory;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        factory = CinemasFactory.eINSTANCE;
        cinema = factory.createCinema();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CancelFutureScreening() throws Exception {
        // Setup
        Film film1 = factory.createFilm();
        cinema.addFilm(film1);
        
        Room room1 = factory.createRoom();
        cinema.addRoom(room1);
        
        Screening screening1 = factory.createScreening();
        Date screeningTime = dateFormat.parse("2024-10-03 12:00:00");
        screening1.setTime(screeningTime);
        
        Date currentTimeForAssign = dateFormat.parse("2024-10-02 11:00:00");
        cinema.assignScreening(film1, currentTimeForAssign, screening1, room1);
        
        // Action
        Date currentTimeForCancel = dateFormat.parse("2024-10-02 12:00:00");
        boolean result = cinema.cancelScreening(currentTimeForCancel, screening1);
        
        // Assert
        assertTrue("Screening should be canceled successfully", result);
        assertFalse("Screening should be removed from cinema", cinema.getScreenings().contains(screening1));
        assertEquals("Cinema should have no screenings", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_CancelNonExistentScreening() throws Exception {
        // Setup
        Film film1 = factory.createFilm();
        cinema.addFilm(film1);
        
        Room room1 = factory.createRoom();
        cinema.addRoom(room1);
        
        Screening screening2 = factory.createScreening();
        Date screeningTime = dateFormat.parse("2024-10-02 12:05:00");
        screening2.setTime(screeningTime);
        
        // Action - cancel screening that was never assigned to cinema
        Date currentTime = dateFormat.parse("2024-10-03 12:05:00");
        boolean result = cinema.cancelScreening(currentTime, screening2);
        
        // Assert
        assertFalse("Non-existent screening should not be canceled", result);
        assertEquals("Cinema should have no screenings", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase3_CancelAtExactScreeningTimeBoundary() throws Exception {
        // Setup
        Film film1 = factory.createFilm();
        cinema.addFilm(film1);
        
        Room room1 = factory.createRoom();
        cinema.addRoom(room1);
        
        Screening screening1 = factory.createScreening();
        Date screeningTime = dateFormat.parse("2024-12-03 10:00:00");
        screening1.setTime(screeningTime);
        
        Date currentTimeForAssign = dateFormat.parse("2024-12-02 10:00:00");
        cinema.assignScreening(film1, currentTimeForAssign, screening1, room1);
        
        // Action - cancel at exact screening time
        Date currentTimeForCancel = dateFormat.parse("2024-12-03 10:00:00");
        boolean result = cinema.cancelScreening(currentTimeForCancel, screening1);
        
        // Assert
        assertFalse("Screening at exact time boundary should not be canceled", result);
        assertTrue("Screening should remain in cinema", cinema.getScreenings().contains(screening1));
        assertEquals("Cinema should have 1 screening", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_CancelAlreadyCancelledScreening() throws Exception {
        // Setup
        Film film1 = factory.createFilm();
        cinema.addFilm(film1);
        
        Room room1 = factory.createRoom();
        cinema.addRoom(room1);
        
        Screening screening1 = factory.createScreening();
        Date screeningTime = dateFormat.parse("2024-10-03 12:00:00");
        screening1.setTime(screeningTime);
        
        Date currentTimeForAssign = dateFormat.parse("2024-10-02 11:00:00");
        cinema.assignScreening(film1, currentTimeForAssign, screening1, room1);
        
        // First cancellation (successful)
        Date firstCancelTime = dateFormat.parse("2024-10-02 12:00:00");
        boolean firstResult = cinema.cancelScreening(firstCancelTime, screening1);
        assertTrue("First cancellation should succeed", firstResult);
        
        // Action - try to cancel already cancelled screening
        Date secondCancelTime = dateFormat.parse("2024-10-02 12:05:00");
        boolean secondResult = cinema.cancelScreening(secondCancelTime, screening1);
        
        // Assert
        assertFalse("Already cancelled screening should not be canceled again", secondResult);
        assertFalse("Screening should not be in cinema", cinema.getScreenings().contains(screening1));
        assertEquals("Cinema should have no screenings", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_CancelPastScreeningAfterShowtime() throws Exception {
        // Setup
        Film film1 = factory.createFilm();
        cinema.addFilm(film1);
        
        Room room1 = factory.createRoom();
        cinema.addRoom(room1);
        
        Screening screening1 = factory.createScreening();
        Date screeningTime = dateFormat.parse("2024-12-01 14:00:00");
        screening1.setTime(screeningTime);
        
        Date currentTimeForAssign = dateFormat.parse("2024-11-30 10:00:00");
        cinema.assignScreening(film1, currentTimeForAssign, screening1, room1);
        
        // Action - cancel after showtime has passed
        Date currentTimeForCancel = dateFormat.parse("2024-12-03 10:00:00");
        boolean result = cinema.cancelScreening(currentTimeForCancel, screening1);
        
        // Assert
        assertFalse("Past screening should not be canceled", result);
        assertTrue("Screening should remain in cinema", cinema.getScreenings().contains(screening1));
        assertEquals("Cinema should have 1 screening", 1, cinema.getScreenings().size());
    }
}