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
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        
        // Action
        boolean result = cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Expected Output: true
        assertTrue("Valid future screening should be assigned successfully", result);
    }
    
    @Test
    public void testCase2_AssignToAlreadyBookedRoom() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        Screening screening1 = new Screening();
        screening1.setTime(screeningTime);
        cinema.assignScreening(film1, room1, screening1, currentTime);
        
        Screening screening2 = new Screening();
        screening2.setTime(screeningTime);
        
        // Action: Assign screening for same room at same time again
        boolean result = cinema.assignScreening(film1, room1, screening2, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening to already booked room", result);
    }
    
    @Test
    public void testCase3_AssignWithNonExistentFilm() throws Exception {
        // Setup
        cinema.addRoom(room1);
        // Note: film1 is NOT added to cinema
        
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        
        // Action: Assign screening for film1 which is not in cinema
        boolean result = cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening with non-existent film", result);
    }
    
    @Test
    public void testCase4_AssignScreeningAtCurrentTimeBoundary() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-12-01 10:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        
        // Action: Assign screening at same time as current time
        boolean result = cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening at current time boundary", result);
    }
    
    @Test
    public void testCase5_AssignScreeningInPastTime() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-11-30 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        
        // Action: Assign screening in past time
        boolean result = cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening in past time", result);
    }
    
    @Test
    public void testCase6_AssignToNonExistentRoom() throws Exception {
        // Setup
        cinema.addFilm(film1);
        // Note: room1 is NOT added to cinema
        
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        
        // Action: Assign screening to room1 which is not in cinema
        boolean result = cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Expected Output: false
        assertFalse("Should not assign screening to non-existent room", result);
    }
}