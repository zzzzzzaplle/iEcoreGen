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

public class CR6Test {
    
    private Cinema cinema;
    private CinemasFactory factory;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        // Initialize factory and cinema using Ecore factory pattern
        factory = CinemasFactory.eINSTANCE;
        cinema = factory.createCinema();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CancelFutureScreening() throws Exception {
        // Setup
        Film film1 = factory.createFilm();
        Room room1 = factory.createRoom();
        Screening screening1 = factory.createScreening();
        
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-10-03 12:00:00");
        screening1.setTime(screeningTime);
        screening1.setFilm(film1);
        screening1.setRoom(room1);
        
        // Assign screening
        Date currentTimeForAssign = dateFormat.parse("2024-10-02 11:00:00");
        cinema.assignScreening(film1, currentTimeForAssign, screening1, room1);
        
        // Verify screening was assigned
        assertEquals(1, cinema.getScreenings().size());
        
        // Action: cancel screening at future time
        Date cancelTime = dateFormat.parse("2024-10-02 12:00:00");
        boolean result = cinema.cancelScreening(cancelTime, screening1);
        
        // Expected Output: true, there is no screening in the cinema C1
        assertTrue(result);
        assertEquals(0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_CancelNonExistentScreening() throws Exception {
        // Setup
        Film film1 = factory.createFilm();
        Room room1 = factory.createRoom();
        Screening screening2 = factory.createScreening();
        
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Note: screening2 is NOT assigned to cinema, so it doesn't exist in cinema's screenings
        
        // Action: cancel non-existent screening
        Date currentTime = dateFormat.parse("2024-10-03 12:05:00");
        boolean result = cinema.cancelScreening(currentTime, screening2);
        
        // Expected Output: false
        assertFalse(result);
        assertEquals(0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase3_CancelAtExactScreeningTimeBoundary() throws Exception {
        // Setup
        Film film1 = factory.createFilm();
        Room room1 = factory.createRoom();
        Screening screening1 = factory.createScreening();
        
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-12-03 10:00:00");
        screening1.setTime(screeningTime);
        screening1.setFilm(film1);
        screening1.setRoom(room1);
        
        // Assign screening
        Date assignTime = dateFormat.parse("2024-12-02 10:00:00");
        cinema.assignScreening(film1, assignTime, screening1, room1);
        
        // Verify screening was assigned
        assertEquals(1, cinema.getScreenings().size());
        
        // Action: cancel at exact screening time boundary
        Date cancelTime = dateFormat.parse("2024-12-03 10:00:00");
        boolean result = cinema.cancelScreening(cancelTime, screening1);
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse(result);
        assertEquals(1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_CancelAlreadyCancelledScreening() throws Exception {
        // Setup
        Film film1 = factory.createFilm();
        Room room1 = factory.createRoom();
        Screening screening1 = factory.createScreening();
        
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-10-03 12:00:00");
        screening1.setTime(screeningTime);
        screening1.setFilm(film1);
        screening1.setRoom(room1);
        
        // Assign screening
        Date assignTime = dateFormat.parse("2024-10-02 11:00:00");
        cinema.assignScreening(film1, assignTime, screening1, room1);
        
        // First cancellation (successful)
        Date firstCancelTime = dateFormat.parse("2024-10-02 12:00:00");
        boolean firstResult = cinema.cancelScreening(firstCancelTime, screening1);
        assertTrue(firstResult);
        assertEquals(0, cinema.getScreenings().size());
        
        // Action: cancel already cancelled screening
        Date secondCancelTime = dateFormat.parse("2024-10-02 12:05:00");
        boolean secondResult = cinema.cancelScreening(secondCancelTime, screening1);
        
        // Expected Output: false, there is no screening in the cinema
        assertFalse(secondResult);
        assertEquals(0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_CancelPastScreeningAfterShowtime() throws Exception {
        // Setup
        Film film1 = factory.createFilm();
        Room room1 = factory.createRoom();
        Screening screening1 = factory.createScreening();
        
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-12-01 14:00:00");
        screening1.setTime(screeningTime);
        screening1.setFilm(film1);
        screening1.setRoom(room1);
        
        // Assign screening
        Date assignTime = dateFormat.parse("2024-11-30 10:00:00");
        cinema.assignScreening(film1, assignTime, screening1, room1);
        
        // Verify screening was assigned
        assertEquals(1, cinema.getScreenings().size());
        
        // Action: cancel past screening (after showtime)
        Date cancelTime = dateFormat.parse("2024-12-03 10:00:00");
        boolean result = cinema.cancelScreening(cancelTime, screening1);
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse(result);
        assertEquals(1, cinema.getScreenings().size());
    }
}