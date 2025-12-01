import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR2Test {
    
    private Cinema cinema;
    private Room room1;
    private Room room2;
    private Film film1;
    private Film film2;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        room1 = new Room();
        room2 = new Room();
        film1 = new Film();
        film2 = new Film();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CheckAvailableRoomWithNoScreenings() throws ParseException {
        // Setup: Create Cinema C1 and add Room1 to C1
        cinema.addRoom(room1);
        
        // Action: check Room1 availability at the current time "2024-10-05 13:00:00"
        Date timeToCheck = dateFormat.parse("2024-10-05 13:00:00");
        boolean result = cinema.checkAvailability(room1, timeToCheck);
        
        // Expected Output: true
        assertTrue(result);
    }
    
    @Test
    public void testCase2_CheckAssignedRoom() throws ParseException {
        // Setup: Create Cinema C1, film F1 and add Room1, film F1 to C1
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        // Assign film f1 screening at "2024-10-05 13:00:00", room Room1 
        Date screeningTime = dateFormat.parse("2024-10-05 13:00:00");
        Date currentTime = dateFormat.parse("2024-10-04 13:00:00");
        
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        screening.setFilm(film1);
        screening.setRoom(room1);
        
        cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Action: check Room1 availability at the current time "2024-10-05 13:00:00"
        boolean result = cinema.checkAvailability(room1, screeningTime);
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase3_CheckRoomAtExactScreeningTime() throws ParseException {
        // Setup: Create Cinema C1 and add Film F1 and Room R1 to C1
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        // Assign screening for F1 in R1 at "2024-12-01 14:00:00"
        Date screeningTime = dateFormat.parse("2024-12-01 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-02 14:00:00"); // Current time after screening
        
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        screening.setFilm(film1);
        screening.setRoom(room1);
        
        cinema.assignScreening(film1, room1, screening, new Date(currentTime.getTime() - 1000000));
        
        // Action: Check R1 availability at the current time "2024-12-02 14:00:00"
        boolean result = cinema.checkAvailability(room1, currentTime);
        
        // Expected Output: true (because the screening time has passed)
        assertTrue(result);
    }
    
    @Test
    public void testCase4_CheckMultipleRooms() throws ParseException {
        // Setup: Create Cinema C1, film F1, film F2 and add Room1, Room2, film F1, film F2 to C1
        cinema.addRoom(room1);
        cinema.addRoom(room2);
        cinema.addFilm(film1);
        cinema.addFilm(film2);
        
        // Assign film F1 screening S1 at "2024-10-05 13:00:00", room Room1
        Date screeningTime = dateFormat.parse("2024-10-05 13:00:00");
        Date currentTime1 = dateFormat.parse("2024-10-01 13:00:00");
        
        Screening screening1 = new Screening();
        screening1.setTime(screeningTime);
        screening1.setFilm(film1);
        screening1.setRoom(room1);
        
        cinema.assignScreening(film1, room1, screening1, currentTime1);
        
        // Assign film F2 screening S2 at "2024-10-05 13:00:00", room Room2
        Date currentTime2 = dateFormat.parse("2024-10-03 13:00:00");
        
        Screening screening2 = new Screening();
        screening2.setTime(screeningTime);
        screening2.setFilm(film2);
        screening2.setRoom(room2);
        
        cinema.assignScreening(film2, room2, screening2, currentTime2);
        
        // Action: check Room1, Room2 availability at the current time "2024-10-05 13:00:00"
        boolean resultRoom1 = cinema.checkAvailability(room1, screeningTime);
        boolean resultRoom2 = cinema.checkAvailability(room2, screeningTime);
        
        // Expected Output: S1: false, S2: false
        assertFalse(resultRoom1);
        assertFalse(resultRoom2);
    }
    
    @Test
    public void testCase5_CheckDifferentTimeSlot() throws ParseException {
        // Setup: Create Cinema C1, Film F1 and add Room1, Film F1 to C1
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        // Assign Film F1 screening at "2024-10-05 13:00:00", room Room1
        Date screeningTime = dateFormat.parse("2024-10-05 13:00:00");
        Date currentTime = dateFormat.parse("2024-09-03 13:00:00");
        
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        screening.setFilm(film1);
        screening.setRoom(room1);
        
        cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Action: check Room1 availability at the current time "2024-10-05 14:00:00"
        Date checkTime = dateFormat.parse("2024-10-05 14:00:00");
        boolean result = cinema.checkAvailability(room1, checkTime);
        
        // Expected Output: true
        assertTrue(result);
    }
}