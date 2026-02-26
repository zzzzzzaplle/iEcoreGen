import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.Date;
import java.text.ParseException;

public class CR5Test {
    
    private Cinema cinema;
    private Film film1;
    private Film film2;
    private Room room1;
    private Room room2;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film("Film 1");
        film2 = new Film("Film 2");
        room1 = new Room("Room 1");
        room2 = new Room("Room 2");
    }
    
    @Test
    public void testCase1_validFutureScreeningAssignment() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Parse dates
        Date currentTime = DateUtil.parse("2024-12-01 10:00:00");
        Date screeningTime = DateUtil.parse("2024-12-02 14:00:00");
        
        // Create screening
        Screening screening = new Screening(screeningTime);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" (current time: "2024-12-01 10:00:00")
        boolean result = cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Expected Output: true
        assertTrue(result);
    }
    
    @Test
    public void testCase2_assignToAlreadyBookedRoom() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Parse dates
        Date currentTime = DateUtil.parse("2024-12-01 10:00:00");
        Date screeningTime = DateUtil.parse("2024-12-02 14:00:00");
        
        // Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00"
        Screening firstScreening = new Screening(screeningTime);
        cinema.assignScreening(film1, room1, firstScreening, currentTime);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" again
        Screening secondScreening = new Screening(screeningTime);
        boolean result = cinema.assignScreening(film1, room1, secondScreening, currentTime);
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase3_assignWithNonExistentFilm() throws ParseException {
        // Setup: Create Cinema C1, Add Room R1 to C1
        cinema.addRoom(room1);
        
        // Parse dates
        Date currentTime = DateUtil.parse("2024-12-01 10:00:00");
        Date screeningTime = DateUtil.parse("2024-12-02 14:00:00");
        
        // Action: Assign screening for Film F2 (not in cinema) in Room R1 at "2024-12-02 14:00:00"
        Screening screening = new Screening(screeningTime);
        boolean result = cinema.assignScreening(film2, room1, screening, currentTime);
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase4_assignScreeningAtCurrentTimeBoundary() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Parse dates
        Date currentTime = DateUtil.parse("2024-12-01 10:00:00");
        Date screeningTime = DateUtil.parse("2024-12-01 10:00:00");
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-01 10:00:00" (current time: "2024-12-01 10:00:00")
        Screening screening = new Screening(screeningTime);
        boolean result = cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase5_assignScreeningInPastTime() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Parse dates
        Date currentTime = DateUtil.parse("2024-12-01 10:00:00");
        Date screeningTime = DateUtil.parse("2024-11-30 14:00:00");
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-11-30 14:00:00" (current time: "2024-12-01 10:00:00")
        Screening screening = new Screening(screeningTime);
        boolean result = cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase6_assignToNonExistentRoom() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1 to C1
        cinema.addFilm(film1);
        
        // Parse dates
        Date currentTime = DateUtil.parse("2024-12-01 10:00:00");
        Date screeningTime = DateUtil.parse("2024-12-02 14:00:00");
        
        // Action: Assign screening for Film F1 in Room R2 (not in cinema) at "2024-12-02 14:00:00"
        Screening screening = new Screening(screeningTime);
        boolean result = cinema.assignScreening(film1, room2, screening, currentTime);
        
        // Expected Output: false
        assertFalse(result);
    }
}