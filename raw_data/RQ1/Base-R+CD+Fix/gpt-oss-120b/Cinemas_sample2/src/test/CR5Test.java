import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
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
        filmF1 = new Film("Film F1");
        filmF2 = new Film("Film F2");
        roomR1 = new Room("Room R1");
        roomR2 = new Room("Room R2");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_validFutureScreeningAssignment() throws ParseException {
        // Setup: Create Cinema C1, add Film F1 to C1, add Room R1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" (current time: "2024-12-01 10:00:00")
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Screening screening = new Screening(screeningTime);
        
        boolean result = cinema.assignScreening(filmF1, roomR1, screening, currentTime);
        
        // Expected Output: true
        assertTrue("Valid future screening should be assigned successfully", result);
    }
    
    @Test
    public void testCase2_assignToAlreadyBookedRoom() throws ParseException {
        // Setup: Create Cinema C1, add Film F1 to C1, add Room R1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        // Assign first screening for Film F1 in Room R1 at "2024-12-02 14:00:00"
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Screening firstScreening = new Screening(screeningTime);
        cinema.assignScreening(filmF1, roomR1, firstScreening, currentTime);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" again
        Screening secondScreening = new Screening(screeningTime);
        boolean result = cinema.assignScreening(filmF1, roomR1, secondScreening, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening to already booked room", result);
    }
    
    @Test
    public void testCase3_assignWithNonExistentFilm() throws ParseException {
        // Setup: Create Cinema C1, add Room R1 to C1
        cinema.addRoom(roomR1);
        
        // Action: Assign screening for Film F2 (not in cinema) in Room R1 at "2024-12-02 14:00:00"
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Screening screening = new Screening(screeningTime);
        
        boolean result = cinema.assignScreening(filmF2, roomR1, screening, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening with non-existent film", result);
    }
    
    @Test
    public void testCase4_assignScreeningAtCurrentTimeBoundary() throws ParseException {
        // Setup: Create Cinema C1, add Film F1 to C1, add Room R1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-01 10:00:00" (current time: "2024-12-01 10:00:00")
        Date screeningTime = dateFormat.parse("2024-12-01 10:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Screening screening = new Screening(screeningTime);
        
        boolean result = cinema.assignScreening(filmF1, roomR1, screening, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening at current time boundary", result);
    }
    
    @Test
    public void testCase5_assignScreeningInPastTime() throws ParseException {
        // Setup: Create Cinema C1, add Film F1 to C1, add Room R1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-11-30 14:00:00" (current time: "2024-12-01 10:00:00")
        Date screeningTime = dateFormat.parse("2024-11-30 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Screening screening = new Screening(screeningTime);
        
        boolean result = cinema.assignScreening(filmF1, roomR1, screening, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening in past time", result);
    }
    
    @Test
    public void testCase6_assignToNonExistentRoom() throws ParseException {
        // Setup: Create Cinema C1, add Film F1 to C1
        cinema.addFilm(filmF1);
        
        // Action: Assign screening for Film F1 in Room R2 (not in cinema) at "2024-12-02 14:00:00"
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Screening screening = new Screening(screeningTime);
        
        boolean result = cinema.assignScreening(filmF1, roomR2, screening, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening to non-existent room", result);
    }
}