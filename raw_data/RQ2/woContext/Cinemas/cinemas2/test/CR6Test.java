package edu.cinemas.cinemas2.test;

import edu.cinemas.Cinema;
import edu.cinemas.CinemasFactory;
import edu.cinemas.Film;
import edu.cinemas.Room;
import edu.cinemas.Screening;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CR6Test {
    private Cinema cinema;
    private Film film;
    private Room room;
    private CinemasFactory factory;
    private SimpleDateFormat dateFormat;

    @Before
    public void setUp() {
        // Initialize factory and date format
        factory = CinemasFactory.eINSTANCE;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Create cinema instance
        cinema = factory.createCinema();
    }

    // Test Case 1: Cancel future screening
    @Test
    public void testCase1_CancelFutureScreening() throws ParseException {
        // Create film and room
        film = factory.createFilm();
        room = factory.createRoom();
        
        // Add film and room to cinema
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        // Create screening
        Screening screening = factory.createScreening();
        screening.setTime(dateFormat.parse("2024-10-03 12:00:00"));
        screening.setFilm(film);
        screening.setRoom(room);
        
        // Assign screening to cinema
        Date assignTime = dateFormat.parse("2024-10-01 12:00:00");
        cinema.assignScreening(film, assignTime, screening, room);
        
        // Cancel screening
        Date cancelTime = dateFormat.parse("2024-10-02 12:00:00");
        assertTrue(cinema.cancelScreening(cancelTime, screening));
        assertTrue(cinema.getScreenings().isEmpty());
    }

    // Test Case 2: Cancel non-existent screening
    @Test
    public void testCase2_CancelNonExistentScreening() throws ParseException {
        // Create film and room
        film = factory.createFilm();
        room = factory.createRoom();
        
        // Add film and room to cinema
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        // Create screening not added to cinema
        Screening screening = factory.createScreening();
        screening.setTime(dateFormat.parse("2024-10-02 12:05:00"));
        
        // Attempt cancellation
        Date cancelTime = dateFormat.parse("2024-10-03 12:05:00");
        assertFalse(cinema.cancelScreening(cancelTime, screening));
    }

    // Test Case 3: Cancel at exact screening time boundary
    @Test
    public void testCase3_CancelAtExactScreeningTime() throws ParseException {
        // Create film and room
        film = factory.createFilm();
        room = factory.createRoom();
        
        // Add film and room to cinema
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        // Create screening
        Screening screening = factory.createScreening();
        screening.setTime(dateFormat.parse("2024-12-03 10:00:00"));
        screening.setFilm(film);
        screening.setRoom(room);
        
        // Assign screening to cinema
        Date assignTime = dateFormat.parse("2024-12-01 10:00:00");
        cinema.assignScreening(film, assignTime, screening, room);
        
        // Attempt cancellation at exact screening time
        Date cancelTime = dateFormat.parse("2024-12-03 10:00:00");
        assertFalse(cinema.cancelScreening(cancelTime, screening));
        assertEquals(1, cinema.getScreenings().size());
    }

    // Test Case 4: Cancel already cancelled screening
    @Test
    public void testCase4_CancelAlreadyCancelledScreening() throws ParseException {
        // Create film and room
        film = factory.createFilm();
        room = factory.createRoom();
        
        // Add film and room to cinema
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        // Create screening
        Screening screening = factory.createScreening();
        screening.setTime(dateFormat.parse("2024-10-03 12:00:00"));
        screening.setFilm(film);
        screening.setRoom(room);
        
        // Assign and cancel screening
        Date assignTime = dateFormat.parse("2024-10-01 12:00:00");
        cinema.assignScreening(film, assignTime, screening, room);
        Date firstCancelTime = dateFormat.parse("2024-10-02 12:00:00");
        assertTrue(cinema.cancelScreening(firstCancelTime, screening));
        
        // Attempt cancellation again
        Date secondCancelTime = dateFormat.parse("2024-10-02 12:05:00");
        assertFalse(cinema.cancelScreening(secondCancelTime, screening));
        assertTrue(cinema.getScreenings().isEmpty());
    }

    // Test Case 5: Cancel past screening
    @Test
    public void testCase5_CancelPastScreening() throws ParseException {
        // Create film and room
        film = factory.createFilm();
        room = factory.createRoom();
        
        // Add film and room to cinema
        cinema.addFilm(film);
        cinema.addRoom(room);
        
        // Create screening
        Screening screening = factory.createScreening();
        screening.setTime(dateFormat.parse("2024-12-01 14:00:00"));
        screening.setFilm(film);
        screening.setRoom(room);
        
        // Assign screening to cinema
        Date assignTime = dateFormat.parse("2024-11-01 00:00:00");
        cinema.assignScreening(film, assignTime, screening, room);
        
        // Attempt cancellation after showtime
        Date cancelTime = dateFormat.parse("2024-12-03 10:00:00");
        assertFalse(cinema.cancelScreening(cancelTime, screening));
        assertEquals(1, cinema.getScreenings().size());
    }
}