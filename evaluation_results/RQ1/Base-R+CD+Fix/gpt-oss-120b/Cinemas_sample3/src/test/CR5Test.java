import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
        film1 = new Film("F1");
        film2 = new Film("F2");
        room1 = new Room("R1");
        room2 = new Room("R2");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_validFutureScreeningAssignment() throws Exception {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" (current time: "2024-12-01 10:00:00")
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        
        boolean result = cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Expected Output: true
        assertTrue("Valid future screening should be assigned successfully", result);
        assertTrue("Screening should be added to cinema's screenings list", cinema.getScreenings().contains(screening));
    }
    
    @Test
    public void testCase2_assignToAlreadyBookedRoom() throws Exception {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Assign first screening for Film F1 in Room R1 at "2024-12-02 14:00:00"
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Screening firstScreening = new Screening();
        firstScreening.setTime(screeningTime);
        cinema.assignScreening(film1, room1, firstScreening, currentTime);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" again
        Screening secondScreening = new Screening();
        secondScreening.setTime(screeningTime);
        boolean result = cinema.assignScreening(film1, room1, secondScreening, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening to already booked room", result);
        assertEquals("Only one screening should exist", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase3_assignWithNonExistentFilm() throws Exception {
        // Setup: Create Cinema C1, Add Room R1 to C1
        cinema.addRoom(room1);
        
        // Action: Assign screening for Film F2 (not in cinema) in Room R1 at "2024-12-02 14:00:00"
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        
        boolean result = cinema.assignScreening(film2, room1, screening, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening with non-existent film", result);
        assertTrue("No screenings should be added", cinema.getScreenings().isEmpty());
    }
    
    @Test
    public void testCase4_assignScreeningAtCurrentTimeBoundary() throws Exception {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-01 10:00:00" (current time: "2024-12-01 10:00:00")
        Date screeningTime = dateFormat.parse("2024-12-01 10:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        
        boolean result = cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening at current time boundary", result);
        assertTrue("No screenings should be added", cinema.getScreenings().isEmpty());
    }
    
    @Test
    public void testCase5_assignScreeningInPastTime() throws Exception {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-11-30 14:00:00" (current time: "2024-12-01 10:00:00")
        Date screeningTime = dateFormat.parse("2024-11-30 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        
        boolean result = cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening in past time", result);
        assertTrue("No screenings should be added", cinema.getScreenings().isEmpty());
    }
    
    @Test
    public void testCase6_assignToNonExistentRoom() throws Exception {
        // Setup: Create Cinema C1, Add Film F1 to C1
        cinema.addFilm(film1);
        
        // Action: Assign screening for Film F1 in Room R2 (not in cinema) at "2024-12-02 14:00:00"
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        
        boolean result = cinema.assignScreening(film1, room2, screening, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening to non-existent room", result);
        assertTrue("No screenings should be added", cinema.getScreenings().isEmpty());
    }
}