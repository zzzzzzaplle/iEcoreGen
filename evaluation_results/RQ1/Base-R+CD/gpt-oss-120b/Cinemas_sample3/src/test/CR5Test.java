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
        film1.setTitle("Film 1");
        film2 = new Film();
        film2.setTitle("Film 2");
        room1 = new Room();
        room1.setName("Room 1");
        room2 = new Room();
        room2.setName("Room 2");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_validFutureScreeningAssignment() throws ParseException {
        // Setup
        // 1. Create Cinema C1
        // 2. Add Film F1 to C1
        cinema.addFilm(film1);
        // 3. Add Room R1 to C1
        cinema.addRoom(room1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" (current time: "2024-12-01 10:00:00")
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        
        boolean result = cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Expected Output: true
        assertTrue(result);
    }
    
    @Test
    public void testCase2_assignToAlreadyBookedRoom() throws ParseException {
        // Setup
        // 1. Create Cinema C1
        // 2. Add Film F1 to C1
        cinema.addFilm(film1);
        // 3. Add Room R1 to C1
        cinema.addRoom(room1);
        
        // Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00"
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        Screening screening1 = new Screening();
        screening1.setTime(screeningTime);
        cinema.assignScreening(film1, room1, screening1, currentTime);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" again
        Screening screening2 = new Screening();
        screening2.setTime(screeningTime);
        
        boolean result = cinema.assignScreening(film1, room1, screening2, currentTime);
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase3_assignWithNonExistentFilm() throws ParseException {
        // Setup
        // 1. Create Cinema C1
        // 2. Add Room R1 to C1
        cinema.addRoom(room1);
        
        // Action: Assign screening for Film F2 (not in cinema) in Room R1 at "2024-12-02 14:00:00"
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        
        boolean result = cinema.assignScreening(film2, room1, screening, currentTime);
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase4_assignScreeningAtCurrentTimeBoundary() throws ParseException {
        // Setup
        // 1. Create Cinema C1
        // 2. Add Film F1 to C1
        cinema.addFilm(film1);
        // 3. Add Room R1 to C1
        cinema.addRoom(room1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-01 10:00:00" (current time: "2024-12-01 10:00:00")
        Date screeningTime = dateFormat.parse("2024-12-01 10:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        
        boolean result = cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase5_assignScreeningInPastTime() throws ParseException {
        // Setup
        // 1. Create Cinema C1
        // 2. Add Film F1 to C1
        cinema.addFilm(film1);
        // 3. Add Room R1 to C1
        cinema.addRoom(room1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-11-30 14:00:00" (current time: "2024-12-01 10:00:00")
        Date screeningTime = dateFormat.parse("2024-11-30 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        
        boolean result = cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase6_assignToNonExistentRoom() throws ParseException {
        // Setup
        // 1. Create Cinema C1
        // 2. Add Film F1 to C1
        cinema.addFilm(film1);
        
        // Action: Assign screening for Film F1 in Room R2 (not in cinema) at "2024-12-02 14:00:00"
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        
        boolean result = cinema.assignScreening(film1, room2, screening, currentTime);
        
        // Expected Output: false
        assertFalse(result);
    }
}