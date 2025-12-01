package edu.cinemas.cinemas3.test;

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
    private CinemasFactory factory;
    private Cinema cinema;
    private Film film1;
    private Room room1;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        // Initialize factory and create cinema instance
        factory = CinemasFactory.eINSTANCE;
        cinema = factory.createCinema();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Create reusable film and room
        film1 = factory.createFilm();
        room1 = factory.createRoom();
        
        // Add film and room to cinema
        cinema.addFilm(film1);
        cinema.addRoom(room1);
    }
    
    // Helper method to parse date strings
    private Date parseDate(String dateStr) {
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format", e);
        }
    }

    // Test Case 1: Cancel future screening
    @Test
    public void testCancelFutureScreening() throws Exception {
        // Setup screening
        Screening screening1 = factory.createScreening();
        screening1.setTime(parseDate("2024-10-03 12:00:00"));
        
        // Assign screening to cinema
        cinema.assignScreening(film1, parseDate("2024-10-01 12:00:00"), screening1, room1);
        
        // Cancel screening
        boolean result = cinema.cancelScreening(parseDate("2024-10-02 12:00:00"), screening1);
        
        // Verify results
        assertTrue("Cancellation should succeed", result);
        assertEquals("Screenings should be empty", 0, cinema.getScreenings().size());
    }

    // Test Case 2: Cancel non-existent screening
    @Test
    public void testCancelNonExistentScreening() throws Exception {
        // Create but don't add screening to cinema
        Screening screening2 = factory.createScreening();
        screening2.setTime(parseDate("2024-10-02 12:05:00"));
        
        // Attempt cancellation
        boolean result = cinema.cancelScreening(parseDate("2024-10-03 12:05:00"), screening2);
        
        // Verify results
        assertFalse("Cancellation should fail", result);
    }

    // Test Case 3: Cancel at exact screening time boundary
    @Test
    public void testCancelAtExactScreeningTime() throws Exception {
        // Setup screening
        Screening s1 = factory.createScreening();
        s1.setTime(parseDate("2024-12-03 10:00:00"));
        
        // Assign screening to cinema
        cinema.assignScreening(film1, parseDate("2024-12-01 10:00:00"), s1, room1);
        
        // Attempt cancellation at exact screening time
        boolean result = cinema.cancelScreening(parseDate("2024-12-03 10:00:00"), s1);
        
        // Verify results
        assertFalse("Cancellation should fail", result);
        assertEquals("Screening should remain", 1, cinema.getScreenings().size());
    }

    // Test Case 4: Cancel already cancelled screening
    @Test
    public void testCancelAlreadyCancelledScreening() throws Exception {
        // Setup screening
        Screening screening1 = factory.createScreening();
        screening1.setTime(parseDate("2024-10-03 12:00:00"));
        
        // Assign and then cancel screening
        cinema.assignScreening(film1, parseDate("2024-10-01 12:00:00"), screening1, room1);
        cinema.cancelScreening(parseDate("2024-10-02 12:00:00"), screening1);
        
        // Attempt to cancel again
        boolean result = cinema.cancelScreening(parseDate("2024-10-02 12:05:00"), screening1);
        
        // Verify results
        assertFalse("Cancellation should fail", result);
        assertEquals("Screenings should remain empty", 0, cinema.getScreenings().size());
    }

    // Test Case 5: Cancel past screening
    @Test
    public void testCancelPastScreening() throws Exception {
        // Setup screening
        Screening s1 = factory.createScreening();
        s1.setTime(parseDate("2024-12-01 14:00:00"));
        
        // Assign screening to cinema
        cinema.assignScreening(film1, parseDate("2024-11-01 14:00:00"), s1, room1);
        
        // Attempt cancellation after showtime
        boolean result = cinema.cancelScreening(parseDate("2024-12-03 10:00:00"), s1);
        
        // Verify results
        assertFalse("Cancellation should fail", result);
        assertEquals("Screening should remain", 1, cinema.getScreenings().size());
    }
}