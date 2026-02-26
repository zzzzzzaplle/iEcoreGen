import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR5Test {
    
    private Cinema cinema;
    private Film filmF1;
    private Film filmF2;
    private Room roomR1;
    private Room roomR2;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        filmF1 = new Film();
        filmF2 = new Film();
        roomR1 = new Room();
        roomR2 = new Room();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ValidFutureScreeningAssignment() {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" (current time: "2024-12-01 10:00:00")
        Screening screening = new Screening();
        try {
            screening.setTime(dateFormat.parse("2024-12-02 14:00:00"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        boolean result = cinema.assignScreening(filmF1, roomR1, screening, "2024-12-01 10:00:00");
        
        // Expected Output: true
        assertTrue("Valid future screening should be assigned successfully", result);
    }
    
    @Test
    public void testCase2_AssignToAlreadyBookedRoom() {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        // First assignment
        Screening screening1 = new Screening();
        try {
            screening1.setTime(dateFormat.parse("2024-12-02 14:00:00"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        cinema.assignScreening(filmF1, roomR1, screening1, "2024-12-01 10:00:00");
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" again
        Screening screening2 = new Screening();
        try {
            screening2.setTime(dateFormat.parse("2024-12-02 14:00:00"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        boolean result = cinema.assignScreening(filmF1, roomR1, screening2, "2024-12-01 10:00:00");
        
        // Expected Output: false
        assertFalse("Should not assign screening to already booked room", result);
    }
    
    @Test
    public void testCase3_AssignWithNonExistentFilm() {
        // Setup: Create Cinema C1, Add Room R1 to C1
        cinema.addRoom(roomR1);
        // Note: Film F2 is NOT added to cinema
        
        // Action: Assign screening for Film F2 (not in cinema) in Room R1 at "2024-12-02 14:00:00"
        Screening screening = new Screening();
        try {
            screening.setTime(dateFormat.parse("2024-12-02 14:00:00"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        boolean result = cinema.assignScreening(filmF2, roomR1, screening, "2024-12-01 10:00:00");
        
        // Expected Output: false
        assertFalse("Should not assign screening with non-existent film", result);
    }
    
    @Test
    public void testCase4_AssignScreeningAtCurrentTimeBoundary() {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-01 10:00:00" (current time: "2024-12-01 10:00:00")
        Screening screening = new Screening();
        try {
            screening.setTime(dateFormat.parse("2024-12-01 10:00:00"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        boolean result = cinema.assignScreening(filmF1, roomR1, screening, "2024-12-01 10:00:00");
        
        // Expected Output: false
        assertFalse("Should not assign screening at current time boundary", result);
    }
    
    @Test
    public void testCase5_AssignScreeningInPastTime() {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-11-30 14:00:00" (current time: "2024-12-01 10:00:00")
        Screening screening = new Screening();
        try {
            screening.setTime(dateFormat.parse("2024-11-30 14:00:00"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        boolean result = cinema.assignScreening(filmF1, roomR1, screening, "2024-12-01 10:00:00");
        
        // Expected Output: false
        assertFalse("Should not assign screening in past time", result);
    }
    
    @Test
    public void testCase6_AssignToNonExistentRoom() {
        // Setup: Create Cinema C1, Add Film F1 to C1
        cinema.addFilm(filmF1);
        // Note: Room R2 is NOT added to cinema
        
        // Action: Assign screening for Film F1 in Room R2 (not in cinema) at "2024-12-02 14:00:00"
        Screening screening = new Screening();
        try {
            screening.setTime(dateFormat.parse("2024-12-02 14:00:00"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        boolean result = cinema.assignScreening(filmF1, roomR2, screening, "2024-12-01 10:00:00");
        
        // Expected Output: false
        assertFalse("Should not assign screening to non-existent room", result);
    }
}