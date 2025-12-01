import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CR6Test {
    private Cinema cinema;
    private Film film1;
    private Room room1;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film("Film1");
        room1 = new Room("Room1");
    }
    
    @Test
    public void testCase1_CancelFutureScreening() {
        // Setup: Create Cinema C1, Add Film1, Add Room1, Assign Screening1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-10-03 12:00:00", "2024-10-02 11:00:00");
        
        // Action: cancel Screening1 at "2024-10-02 12:00:00"
        boolean result = cinema.cancelScreening(film1, room1, "2024-10-03 12:00:00", "2024-10-02 12:00:00");
        
        // Verify cancellation was successful
        assertTrue("Screening should be canceled successfully", result);
        
        // Verify there are no screenings left in the cinema
        List<Room> rooms = cinema.getRooms();
        int screeningCount = 0;
        for (Room room : rooms) {
            screeningCount += room.getScreenings().size();
        }
        assertEquals("There should be no screenings in cinema", 0, screeningCount);
    }
    
    @Test
    public void testCase2_CancelNonExistentScreening() {
        // Setup: Create Cinema C1, Add Film1, Add Room1 (no screening assigned)
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: cancel non-existent Screening2 at current time "2024-10-03 12:05:00"
        boolean result = cinema.cancelScreening(film1, room1, "2024-10-02 12:05:00", "2024-10-03 12:05:00");
        
        // Verify cancellation failed
        assertFalse("Non-existent screening should not be canceled", result);
    }
    
    @Test
    public void testCase3_CancelAtExactScreeningTimeBoundary() {
        // Setup: Create Cinema C1, Add Film F1, Add Room R1, Assign screening S1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-12-03 10:00:00", "2024-12-02 10:00:00");
        
        // Action: Cancel S1 at exact screening time "2024-12-03 10:00:00"
        boolean result = cinema.cancelScreening(film1, room1, "2024-12-03 10:00:00", "2024-12-03 10:00:00");
        
        // Verify cancellation failed (current time equals screening time)
        assertFalse("Screening at exact time boundary should not be canceled", result);
        
        // Verify screening still exists in cinema
        List<Room> rooms = cinema.getRooms();
        int screeningCount = 0;
        for (Room room : rooms) {
            screeningCount += room.getScreenings().size();
        }
        assertEquals("There should be 1 screening in cinema", 1, screeningCount);
    }
    
    @Test
    public void testCase4_CancelAlreadyCancelledScreening() {
        // Setup: Create Cinema C1, Add Film1, Add Room1, Assign Screening1 then cancel it
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-10-03 12:00:00", "2024-10-02 11:00:00");
        
        // First cancellation (should succeed)
        boolean firstCancel = cinema.cancelScreening(film1, room1, "2024-10-03 12:00:00", "2024-10-02 12:00:00");
        assertTrue("First cancellation should succeed", firstCancel);
        
        // Action: try to cancel the same screening again at "2024-10-02 12:05:00"
        boolean result = cinema.cancelScreening(film1, room1, "2024-10-03 12:00:00", "2024-10-02 12:05:00");
        
        // Verify second cancellation fails
        assertFalse("Already canceled screening should not be canceled again", result);
        
        // Verify there are no screenings in cinema
        List<Room> rooms = cinema.getRooms();
        int screeningCount = 0;
        for (Room room : rooms) {
            screeningCount += room.getScreenings().size();
        }
        assertEquals("There should be no screenings in cinema", 0, screeningCount);
    }
    
    @Test
    public void testCase5_CancelPastScreeningAfterShowtime() {
        // Setup: Create Cinema C1, Add Film F1, Add Room R1, Assign screening S1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-12-01 14:00:00", "2024-11-30 10:00:00");
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00" (after screening time)
        boolean result = cinema.cancelScreening(film1, room1, "2024-12-01 14:00:00", "2024-12-03 10:00:00");
        
        // Verify cancellation failed (current time after screening time)
        assertFalse("Past screening should not be canceled", result);
        
        // Verify screening still exists in cinema
        List<Room> rooms = cinema.getRooms();
        int screeningCount = 0;
        for (Room room : rooms) {
            screeningCount += room.getScreenings().size();
        }
        assertEquals("There should be 1 screening in cinema", 1, screeningCount);
    }
}