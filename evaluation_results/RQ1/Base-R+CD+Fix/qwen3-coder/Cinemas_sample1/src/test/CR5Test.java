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
        film1 = new Film();
        film2 = new Film();
        room1 = new Room();
        room2 = new Room();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ValidFutureScreeningAssignment() throws Exception {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Create screening time and current time
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        // Create screening object
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        screening.setFilm(film1);
        screening.setRoom(room1);
        
        // Action: Assign screening for Film F1 in Room R1
        boolean result = cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Expected Output: true
        assertTrue("Screening should be assigned successfully", result);
    }
    
    @Test
    public void testCase2_AssignToAlreadyBookedRoom() throws Exception {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Create screening time and current time
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        // Create first screening object
        Screening screening1 = new Screening();
        screening1.setTime(screeningTime);
        screening1.setFilm(film1);
        screening1.setRoom(room1);
        
        // Assign first screening
        cinema.assignScreening(film1, room1, screening1, currentTime);
        
        // Create second screening object for same time and room
        Screening screening2 = new Screening();
        screening2.setTime(screeningTime);
        screening2.setFilm(film1);
        screening2.setRoom(room1);
        
        // Action: Assign screening for Film F1 in Room R1 at same time again
        boolean result = cinema.assignScreening(film1, room1, screening2, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening to already booked room", result);
    }
    
    @Test
    public void testCase3_AssignWithNonExistentFilm() throws Exception {
        // Setup: Create Cinema C1, Add Room R1 to C1 (Film F2 not added)
        cinema.addRoom(room1);
        
        // Create screening time and current time
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        // Create screening object
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        screening.setFilm(film2); // film2 not added to cinema
        screening.setRoom(room1);
        
        // Action: Assign screening for Film F2 (not in cinema) in Room R1
        boolean result = cinema.assignScreening(film2, room1, screening, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening with non-existent film", result);
    }
    
    @Test
    public void testCase4_AssignScreeningAtCurrentTimeBoundary() throws Exception {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Create screening time and current time (same time)
        Date screeningTime = dateFormat.parse("2024-12-01 10:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        // Create screening object
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        screening.setFilm(film1);
        screening.setRoom(room1);
        
        // Action: Assign screening for Film F1 in Room R1 at current time
        boolean result = cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening at current time boundary", result);
    }
    
    @Test
    public void testCase5_AssignScreeningInPastTime() throws Exception {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Create screening time (past) and current time
        Date screeningTime = dateFormat.parse("2024-11-30 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        // Create screening object
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        screening.setFilm(film1);
        screening.setRoom(room1);
        
        // Action: Assign screening for Film F1 in Room R1 at past time
        boolean result = cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening in past time", result);
    }
    
    @Test
    public void testCase6_AssignToNonExistentRoom() throws Exception {
        // Setup: Create Cinema C1, Add Film F1 to C1 (Room R2 not added)
        cinema.addFilm(film1);
        
        // Create screening time and current time
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        // Create screening object
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        screening.setFilm(film1);
        screening.setRoom(room2); // room2 not added to cinema
        
        // Action: Assign screening for Film F1 in Room R2 (not in cinema)
        boolean result = cinema.assignScreening(film1, room2, screening, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening to non-existent room", result);
    }
}