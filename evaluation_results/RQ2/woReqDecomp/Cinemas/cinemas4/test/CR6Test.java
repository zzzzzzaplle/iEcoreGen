package edu.cinemas.cinemas4.test;

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

public class CR6Test {
    private CinemasFactory factory;
    private SimpleDateFormat sdf;
    
    @Before
    public void setUp() {
        factory = CinemasFactory.eINSTANCE;
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    // Test Case 1: Cancel future screening
    @Test
    public void testCase1_CancelFutureScreening() throws Exception {
        Cinema cinema = factory.createCinema();
        Film film = factory.createFilm();
        Room room = factory.createRoom();
        
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        Screening screening = factory.createScreening();
        screening.setTime(sdf.parse("2024-10-03 12:00:00"));
        cinema.assignScreening(film, sdf.parse("2024-10-02 12:00:00"), screening, room);
        
        boolean result = cinema.cancelScreening(sdf.parse("2024-10-02 12:00:00"), screening);
        
        assertTrue("Cancellation should succeed", result);
        assertFalse("Screening should be removed", cinema.getScreenings().contains(screening));
    }

    // Test Case 2: Cancel non-existent screening
    @Test
    public void testCase2_CancelNonExistentScreening() throws Exception {
        Cinema cinema = factory.createCinema();
        Screening screening = factory.createScreening();
        screening.setTime(sdf.parse("2024-10-02 12:05:00"));
        
        boolean result = cinema.cancelScreening(sdf.parse("2024-10-03 12:05:00"), screening);
        
        assertFalse("Cancellation should fail", result);
    }

    // Test Case 3: Cancel at exact screening time boundary
    @Test
    public void testCase3_CancelAtExactScreeningTimeBoundary() throws Exception {
        Cinema cinema = factory.createCinema();
        Film film = factory.createFilm();
        Room room = factory.createRoom();
        
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        Screening screening = factory.createScreening();
        screening.setTime(sdf.parse("2024-12-03 10:00:00"));
        cinema.assignScreening(film, sdf.parse("2024-12-02 10:00:00"), screening, room);
        
        boolean result = cinema.cancelScreening(sdf.parse("2024-12-03 10:00:00"), screening);
        
        assertFalse("Cancellation should fail", result);
        assertTrue("Screening should remain", cinema.getScreenings().contains(screening));
    }

    // Test Case 4: Cancel already cancelled screening
    @Test
    public void testCase4_CancelAlreadyCancelledScreening() throws Exception {
        Cinema cinema = factory.createCinema();
        Film film = factory.createFilm();
        Room room = factory.createRoom();
        
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        Screening screening = factory.createScreening();
        screening.setTime(sdf.parse("2024-10-03 12:00:00"));
        cinema.assignScreening(film, sdf.parse("2024-10-02 12:00:00"), screening, room);
        
        // First cancellation succeeds
        cinema.cancelScreening(sdf.parse("2024-10-02 12:00:00"), screening);
        
        // Second cancellation attempt
        boolean result = cinema.cancelScreening(sdf.parse("2024-10-02 12:05:00"), screening);
        
        assertFalse("Second cancellation should fail", result);
        assertFalse("Screening should remain removed", cinema.getScreenings().contains(screening));
    }

    // Test Case 5: Cancel past screening (after showtime)
    @Test
    public void testCase5_CancelPastScreening() throws Exception {
        Cinema cinema = factory.createCinema();
        Film film = factory.createFilm();
        Room room = factory.createRoom();
        
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        Screening screening = factory.createScreening();
        screening.setTime(sdf.parse("2024-12-01 14:00:00"));
        cinema.assignScreening(film, sdf.parse("2024-11-30 14:00:00"), screening, room);
        
        boolean result = cinema.cancelScreening(sdf.parse("2024-12-03 10:00:00"), screening);
        
        assertFalse("Cancellation should fail", result);
        assertTrue("Screening should remain", cinema.getScreenings().contains(screening));
    }
}