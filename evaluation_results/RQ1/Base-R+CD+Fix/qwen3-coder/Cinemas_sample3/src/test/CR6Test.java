import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR6Test {
    private Cinema cinema;
    private Film film1;
    private Room room1;
    private Screening screening1;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() throws Exception {
        cinema = new Cinema();
        film1 = new Film("Film1");
        room1 = new Room("Room1");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CancelFutureScreening() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        Date screeningTime = dateFormat.parse("2024-10-03 12:00:00");
        cinema.assignScreening(film1, room1, screeningTime, dateFormat.parse("2024-10-02 11:00:00"));
        
        // Create the screening object for cancellation
        Screening screeningToCancel = new Screening(film1, room1, screeningTime);
        
        // Action: cancel Screening1 at "2024-10-02 12:00:00"
        Date currentTime = dateFormat.parse("2024-10-02 12:00:00");
        boolean result = cinema.cancelScreening(screeningToCancel, currentTime);
        
        // Assertions
        assertTrue("Screening should be canceled successfully", result);
        // Verify there are no screenings left in the cinema
        assertEquals("There should be no screenings in cinema", 0, getScreeningsCount(cinema));
    }
    
    @Test
    public void testCase2_CancelNonExistentScreening() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Create a screening that was never assigned to the cinema
        Screening screening2 = new Screening(film1, room1, dateFormat.parse("2024-10-02 12:05:00"));
        
        // Action: cancel Screening2 at current time "2024-10-03 12:05:00"
        Date currentTime = dateFormat.parse("2024-10-03 12:05:00");
        boolean result = cinema.cancelScreening(screening2, currentTime);
        
        // Assertions
        assertFalse("Non-existent screening should not be canceled", result);
    }
    
    @Test
    public void testCase3_CancelAtExactScreeningTimeBoundary() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        Date screeningTime = dateFormat.parse("2024-12-03 10:00:00");
        cinema.assignScreening(film1, room1, screeningTime, dateFormat.parse("2024-12-02 10:00:00"));
        
        // Create the screening object for cancellation
        Screening screeningToCancel = new Screening(film1, room1, screeningTime);
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-03 10:00:00");
        boolean result = cinema.cancelScreening(screeningToCancel, currentTime);
        
        // Assertions
        assertFalse("Screening at exact time boundary should not be canceled", result);
        // Verify there is still 1 screening in the cinema
        assertEquals("There should be 1 screening in cinema", 1, getScreeningsCount(cinema));
    }
    
    @Test
    public void testCase4_CancelAlreadyCancelledScreening() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        Date screeningTime = dateFormat.parse("2024-10-03 12:00:00");
        cinema.assignScreening(film1, room1, screeningTime, dateFormat.parse("2024-10-02 11:00:00"));
        
        // Create the screening object for cancellation
        Screening screeningToCancel = new Screening(film1, room1, screeningTime);
        
        // First cancellation (should succeed)
        Date firstCurrentTime = dateFormat.parse("2024-10-02 12:00:00");
        boolean firstResult = cinema.cancelScreening(screeningToCancel, firstCurrentTime);
        assertTrue("First cancellation should succeed", firstResult);
        
        // Action: cancel Screening1 at current time "2024-10-02 12:05:00"
        Date secondCurrentTime = dateFormat.parse("2024-10-02 12:05:00");
        boolean result = cinema.cancelScreening(screeningToCancel, secondCurrentTime);
        
        // Assertions
        assertFalse("Already canceled screening should not be canceled again", result);
        // Verify there are no screenings in the cinema
        assertEquals("There should be no screenings in cinema", 0, getScreeningsCount(cinema));
    }
    
    @Test
    public void testCase5_CancelPastScreeningAfterShowtime() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        Date screeningTime = dateFormat.parse("2024-12-01 14:00:00");
        cinema.assignScreening(film1, room1, screeningTime, dateFormat.parse("2024-11-30 10:00:00"));
        
        // Create the screening object for cancellation
        Screening screeningToCancel = new Screening(film1, room1, screeningTime);
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        Date currentTime = dateFormat.parse("2024-12-03 10:00:00");
        boolean result = cinema.cancelScreening(screeningToCancel, currentTime);
        
        // Assertions
        assertFalse("Past screening should not be canceled", result);
        // Verify there is still 1 screening in the cinema
        assertEquals("There should be 1 screening in cinema", 1, getScreeningsCount(cinema));
    }
    
    // Helper method to get screenings count (using reflection since screenings is private)
    private int getScreeningsCount(Cinema cinema) {
        try {
            java.lang.reflect.Field screeningsField = Cinema.class.getDeclaredField("screenings");
            screeningsField.setAccessible(true);
            java.util.List<?> screenings = (java.util.List<?>) screeningsField.get(cinema);
            return screenings.size();
        } catch (Exception e) {
            fail("Failed to access screenings field: " + e.getMessage());
            return -1;
        }
    }
}