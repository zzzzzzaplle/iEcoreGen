import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR5Test {
    private Cinema cinema;
    private Film film1;
    private Film film2;
    private Room room1;
    private Room room2;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film();
        film2 = new Film();
        room1 = new Room();
        room2 = new Room();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ValidFutureScreeningAssignment() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" (current time: "2024-12-01 10:00:00")
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.assignScreening(film1, room1, screeningTime, currentTime);
        
        // Expected Output: true
        assertTrue("Valid future screening should be assigned successfully", result);
    }
    
    @Test
    public void testCase2_AssignToAlreadyBookedRoom() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
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
    public void testCase3_AssignWithNonExistentFilm() throws ParseException {
        // Setup: Create Cinema C1, Add Room R1 to C1
        cinema.addRoom(room1);
        
        // Action: Assign screening for Film F2 (not in cinema) in Room R1 at "2024-12-02 14:00:00"
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.assignScreening(film2, room1, screeningTime, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening with non-existent film", result);
    }
    
    @Test
    public void testCase4_AssignScreeningAtCurrentTimeBoundary() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-01 10:00:00" (current time: "2024-12-01 10:00:00")
        Date screeningTime = dateFormat.parse("2024-12-01 10:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.assignScreening(film1, room1, screeningTime, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening at current time boundary", result);
    }
    
    @Test
    public void testCase5_AssignScreeningInPastTime() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-11-30 14:00:00" (current time: "2024-12-01 10:00:00")
        Date screeningTime = dateFormat.parse("2024-11-30 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.assignScreening(film1, room1, screeningTime, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening in past time", result);
    }
    
    @Test
    public void testCase6_AssignToNonExistentRoom() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1 to C1
        cinema.addFilm(film1);
        
        // Action: Assign screening for Film F1 in Room R2 (not in cinema) at "2024-12-02 14:00:00"
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        boolean result = cinema.assignScreening(film1, room2, screeningTime, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening to non-existent room", result);
    }
}