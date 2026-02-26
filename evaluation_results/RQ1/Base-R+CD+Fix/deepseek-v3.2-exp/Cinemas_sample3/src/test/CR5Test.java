import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;

public class CR5Test {
    
    private Cinema cinema;
    private Film filmF1;
    private Film filmF2;
    private Room roomR1;
    private Room roomR2;
    
    @Before
    public void setUp() {
        // Initialize cinema and test objects before each test
        cinema = new Cinema();
        filmF1 = new Film();
        filmF2 = new Film();
        roomR1 = new Room();
        roomR2 = new Room();
    }
    
    @Test
    public void testCase1_ValidFutureScreeningAssignment() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" (current time: "2024-12-01 10:00:00")
        boolean result = cinema.assignScreening(filmF1, roomR1, "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        
        // Expected Output: true
        assertTrue("Screening should be assigned successfully for valid future time", result);
    }
    
    @Test
    public void testCase2_AssignToAlreadyBookedRoom() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        // First assignment
        cinema.assignScreening(filmF1, roomR1, "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" again
        boolean result = cinema.assignScreening(filmF1, roomR1, "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        
        // Expected Output: false
        assertFalse("Should not assign screening to already booked room", result);
    }
    
    @Test
    public void testCase3_AssignWithNonExistentFilm() throws ParseException {
        // Setup: Create Cinema C1, Add Room R1 to C1
        cinema.addRoom(roomR1);
        
        // Action: Assign screening for Film F2 (not in cinema) in Room R1 at "2024-12-02 14:00:00"
        boolean result = cinema.assignScreening(filmF2, roomR1, "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        
        // Expected Output: false
        assertFalse("Should not assign screening with non-existent film", result);
    }
    
    @Test
    public void testCase4_AssignScreeningAtCurrentTimeBoundary() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-01 10:00:00" (current time: "2024-12-01 10:00:00")
        boolean result = cinema.assignScreening(filmF1, roomR1, "2024-12-01 10:00:00", "2024-12-01 10:00:00");
        
        // Expected Output: false
        assertFalse("Should not assign screening at current time boundary", result);
    }
    
    @Test
    public void testCase5_AssignScreeningInPastTime() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-11-30 14:00:00" (current time: "2024-12-01 10:00:00")
        boolean result = cinema.assignScreening(filmF1, roomR1, "2024-11-30 14:00:00", "2024-12-01 10:00:00");
        
        // Expected Output: false
        assertFalse("Should not assign screening in past time", result);
    }
    
    @Test
    public void testCase6_AssignToNonExistentRoom() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1 to C1
        cinema.addFilm(filmF1);
        
        // Action: Assign screening for Film F1 in Room R2 (not in cinema) at "2024-12-02 14:00:00"
        boolean result = cinema.assignScreening(filmF1, roomR2, "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        
        // Expected Output: false
        assertFalse("Should not assign screening to non-existent room", result);
    }
}