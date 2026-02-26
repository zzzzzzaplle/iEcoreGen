import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR5Test {
    private Cinema cinema;
    private Film film1;
    private Film film2;
    private Room room1;
    private Room room2;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() throws ParseException {
        cinema = new Cinema();
        film1 = new Film();
        film2 = new Film();
        room1 = new Room();
        room2 = new Room();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Add film1 and room1 to cinema for test setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
    }
    
    @Test
    public void testCase1_validFutureScreeningAssignment() throws ParseException {
        // Setup: Cinema C1, Film F1 added, Room R1 added (already done in setUp)
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" (current time: "2024-12-01 10:00:00")
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.assignScreening(film1, room1, screeningTime, currentTime);
        
        // Expected Output: true
        assertTrue("Valid future screening should be assigned successfully", result);
    }
    
    @Test
    public void testCase2_assignToAlreadyBookedRoom() throws ParseException {
        // Setup: Cinema C1, Film F1 added, Room R1 added (already done in setUp)
        
        // First assignment
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        cinema.assignScreening(film1, room1, screeningTime, currentTime);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" again
        boolean result = cinema.assignScreening(film1, room1, screeningTime, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening to already booked room", result);
    }
    
    @Test
    public void testCase3_assignWithNonExistentFilm() throws ParseException {
        // Setup: Cinema C1, Room R1 added (already done in setUp)
        // Note: film2 is not added to cinema
        
        // Action: Assign screening for Film F2 (not in cinema) in Room R1 at "2024-12-02 14:00:00"
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.assignScreening(film2, room1, screeningTime, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening with non-existent film", result);
    }
    
    @Test
    public void testCase4_assignScreeningAtCurrentTimeBoundary() throws ParseException {
        // Setup: Cinema C1, Film F1 added, Room R1 added (already done in setUp)
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-01 10:00:00" (current time: "2024-12-01 10:00:00")
        Date screeningTime = dateFormat.parse("2024-12-01 10:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.assignScreening(film1, room1, screeningTime, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening at current time boundary", result);
    }
    
    @Test
    public void testCase5_assignScreeningInPastTime() throws ParseException {
        // Setup: Cinema C1, Film F1 added, Room R1 added (already done in setUp)
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-11-30 14:00:00" (current time: "2024-12-01 10:00:00")
        Date screeningTime = dateFormat.parse("2024-11-30 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.assignScreening(film1, room1, screeningTime, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening in past time", result);
    }
    
    @Test
    public void testCase6_assignToNonExistentRoom() throws ParseException {
        // Setup: Cinema C1, Film F1 added (already done in setUp)
        // Note: room2 is not added to cinema
        
        // Action: Assign screening for Film F1 in Room R2 (not in cinema) at "2024-12-02 14:00:00"
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.assignScreening(film1, room2, screeningTime, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening to non-existent room", result);
    }
}