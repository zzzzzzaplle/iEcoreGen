package edu.cinemas.cinemas2.test;

import edu.cinemas.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CR6Test {
    private CinemasFactory factory;
    private SimpleDateFormat sdf;
    private Cinema cinema;
    private Film film;
    private Room room;
    private Screening screening;

    @Before
    public void setUp() {
        factory = CinemasFactory.eINSTANCE;
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cinema = factory.createCinema();
        film = factory.createFilm();
        room = factory.createRoom();
        screening = factory.createScreening();
    }

    // Test Case 1: Cancel future screening
    @Test
    public void testCase1_CancelFutureScreening() throws Exception {
        // Setup: Add film and room to cinema
        assertTrue(cinema.addFilm(film));
        assertTrue(cinema.addRoom(room));
        
        // Set screening attributes
        screening.setFilm(film);
        screening.setRoom(room);
        screening.setTime(sdf.parse("2024-10-03 12:00:00"));
        
        // Assign screening
        Date assignTime = sdf.parse("2024-10-02 12:00:00");
        assertTrue(cinema.assignScreening(film, assignTime, screening, room));
        
        // Action: Cancel screening
        Date cancelTime = sdf.parse("2024-10-02 12:00:00");
        assertTrue(cinema.cancelScreening(cancelTime, screening));
        
        // Verify screening removed
        assertTrue(cinema.getScreenings().isEmpty());
    }

    // Test Case 2: Cancel non-existent screening
    @Test
    public void testCase2_CancelNonExistentScreening() throws Exception {
        // Create screening that's not added to cinema
        Screening nonExistentScreening = factory.createScreening();
        nonExistentScreening.setTime(sdf.parse("2024-10-02 12:05:00"));
        
        // Action: Cancel non-existent screening
        Date cancelTime = sdf.parse("2024-10-03 12:05:00");
        assertFalse(cinema.cancelScreening(cancelTime, nonExistentScreening));
        
        // Verify cinema remains unchanged
        assertTrue(cinema.getScreenings().isEmpty());
    }

    // Test Case 3: Cancel at exact screening time boundary
    @Test
    public void testCase3_CancelAtExactScreeningTime() throws Exception {
        // Setup: Add film and room to cinema
        assertTrue(cinema.addFilm(film));
        assertTrue(cinema.addRoom(room));
        
        // Set screening attributes
        screening.setFilm(film);
        screening.setRoom(room);
        screening.setTime(sdf.parse("2024-12-03 10:00:00"));
        
        // Assign screening
        Date assignTime = sdf.parse("2024-12-02 10:00:00");
        assertTrue(cinema.assignScreening(film, assignTime, screening, room));
        
        // Action: Cancel at exact screening time
        Date cancelTime = sdf.parse("2024-12-03 10:00:00");
        assertFalse(cinema.cancelScreening(cancelTime, screening));
        
        // Verify screening still present
        assertEquals(1, cinema.getScreenings().size());
    }

    // Test Case 4: Cancel already cancelled screening
    @Test
    public void testCase4_CancelAlreadyCancelledScreening() throws Exception {
        // Setup: Add film and room to cinema
        assertTrue(cinema.addFilm(film));
        assertTrue(cinema.addRoom(room));
        
        // Set screening attributes
        screening.setFilm(film);
        screening.setRoom(room);
        screening.setTime(sdf.parse("2024-10-03 12:00:00"));
        
        // Assign screening
        Date assignTime = sdf.parse("2024-10-02 12:00:00");
        assertTrue(cinema.assignScreening(film, assignTime, screening, room));
        
        // First cancellation (success)
        Date firstCancelTime = sdf.parse("2024-10-02 12:00:00");
        assertTrue(cinema.cancelScreening(firstCancelTime, screening));
        
        // Second cancellation attempt
        Date secondCancelTime = sdf.parse("2024-10-02 12:05:00");
        assertFalse(cinema.cancelScreening(secondCancelTime, screening));
        
        // Verify still no screenings
        assertTrue(cinema.getScreenings().isEmpty());
    }

    // Test Case 5: Cancel past screening (after showtime)
    @Test
    public void testCase5_CancelPastScreening() throws Exception {
        // Setup: Add film and room to cinema
        assertTrue(cinema.addFilm(film));
        assertTrue(cinema.addRoom(room));
        
        // Set screening attributes
        screening.setFilm(film);
        screening.setRoom(room);
        screening.setTime(sdf.parse("2024-12-01 14:00:00"));
        
        // Assign screening
        Date assignTime = sdf.parse("2024-11-01 14:00:00");
        assertTrue(cinema.assignScreening(film, assignTime, screening, room));
        
        // Action: Cancel after showtime
        Date cancelTime = sdf.parse("2024-12-03 10:00:00");
        assertFalse(cinema.cancelScreening(cancelTime, screening));
        
        // Verify screening still present
        assertEquals(1, cinema.getScreenings().size());
    }
}