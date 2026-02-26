import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR2Test {
    private Cinema cinema;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CheckAvailableRoomWithNoScreenings() throws Exception {
        // Setup: Create Cinema C1 and add Room1 to C1
        Room room1 = new Room();
        cinema.addRoom(room1);
        
        // Action: check Room1 availability at "2024-10-05 13:00:00"
        Date checkTime = dateFormat.parse("2024-10-05 13:00:00");
        boolean result = cinema.checkAvailability(room1, checkTime);
        
        // Expected Output: true
        assertTrue("Room should be available when no screenings are scheduled", result);
    }
    
    @Test
    public void testCase2_CheckAssignedRoom() throws Exception {
        // Setup: Create Cinema C1, film F1
        Film film1 = new Film();
        Room room1 = new Room();
        
        // Add Room1, film F1 to C1
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        // Assign film f1 screening at "2024-10-05 13:00:00", room Room1 (current time "2024-10-04 13:00:00")
        Screening screening = new Screening();
        Date screeningTime = dateFormat.parse("2024-10-05 13:00:00");
        Date currentTime = dateFormat.parse("2024-10-04 13:00:00");
        screening.setTime(screeningTime);
        
        cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Action: check Room1 availability at "2024-10-05 13:00:00"
        boolean result = cinema.checkAvailability(room1, screeningTime);
        
        // Expected Output: false
        assertFalse("Room should not be available when assigned for screening", result);
    }
    
    @Test
    public void testCase3_CheckRoomAtExactScreeningTime() throws Exception {
        // Setup: Create Cinema C1
        Film film1 = new Film();
        Room room1 = new Room();
        
        // Add Film F1 and Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Assign screening for F1 in R1 at "2024-12-01 14:00:00"
        Screening screening = new Screening();
        Date screeningTime = dateFormat.parse("2024-12-01 14:00:00");
        Date currentTime = dateFormat.parse("2024-11-30 14:00:00");
        screening.setTime(screeningTime);
        
        cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Action: Check R1 availability at "2024-12-02 14:00:00"
        Date checkTime = dateFormat.parse("2024-12-02 14:00:00");
        boolean result = cinema.checkAvailability(room1, checkTime);
        
        // Expected Output: true
        assertTrue("Room should be available at different time slot", result);
    }
    
    @Test
    public void testCase4_CheckMultipleRooms() throws Exception {
        // Setup: Create Cinema C1, film F1, film F2
        Film film1 = new Film();
        Film film2 = new Film();
        Room room1 = new Room();
        Room room2 = new Room();
        
        // Add Room1, Room2, film F1, film F2 to C1
        cinema.addRoom(room1);
        cinema.addRoom(room2);
        cinema.addFilm(film1);
        cinema.addFilm(film2);
        
        // Assign film F1 screening S1 at "2024-10-05 13:00:00", room Room1 (current time: "2024-10-01 13:00:00")
        Screening screening1 = new Screening();
        Date screeningTime1 = dateFormat.parse("2024-10-05 13:00:00");
        Date currentTime1 = dateFormat.parse("2024-10-01 13:00:00");
        screening1.setTime(screeningTime1);
        cinema.assignScreening(film1, room1, screening1, currentTime1);
        
        // Assign film F2 screening S2 at "2024-10-05 13:00:00", room Room2 (current time: "2024-10-03 13:00:00")
        Screening screening2 = new Screening();
        Date screeningTime2 = dateFormat.parse("2024-10-05 13:00:00");
        Date currentTime2 = dateFormat.parse("2024-10-03 13:00:00");
        screening2.setTime(screeningTime2);
        cinema.assignScreening(film2, room2, screening2, currentTime2);
        
        // Action: check Room1, Room2 availability at "2024-10-05 13:00:00"
        Date checkTime = dateFormat.parse("2024-10-05 13:00:00");
        boolean result1 = cinema.checkAvailability(room1, checkTime);
        boolean result2 = cinema.checkAvailability(room2, checkTime);
        
        // Expected Output: S1: false, S2: false
        assertFalse("Room1 should not be available as it has screening S1", result1);
        assertFalse("Room2 should not be available as it has screening S2", result2);
    }
    
    @Test
    public void testCase5_CheckDifferentTimeSlot() throws Exception {
        // Setup: Create Cinema C1, Film F1
        Film film1 = new Film();
        Room room1 = new Room();
        
        // Add Room1, Film F1 to C1
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        // Assign Film F1 screening at "2024-10-05 13:00:00", room Room1 (current time: "2024-09-03 13:00:00")
        Screening screening = new Screening();
        Date screeningTime = dateFormat.parse("2024-10-05 13:00:00");
        Date currentTime = dateFormat.parse("2024-09-03 13:00:00");
        screening.setTime(screeningTime);
        
        cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Action: check Room1 availability at "2024-10-05 14:00:00"
        Date checkTime = dateFormat.parse("2024-10-05 14:00:00");
        boolean result = cinema.checkAvailability(room1, checkTime);
        
        // Expected Output: true
        assertTrue("Room should be available at different time slot", result);
    }
}