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
    public void testCase1_checkAvailableRoomWithNoScreenings() throws Exception {
        // Setup: Create Cinema C1 and add Room1 to C1
        Room room1 = new Room();
        cinema.addRoom(room1);
        
        // Action: Check Room1 availability at "2024-10-05 13:00:00"
        Date checkTime = dateFormat.parse("2024-10-05 13:00:00");
        boolean result = cinema.checkAvailability(room1, checkTime);
        
        // Expected Output: true
        assertTrue("Room should be available when no screenings are scheduled", result);
    }
    
    @Test
    public void testCase2_checkAssignedRoom() throws Exception {
        // Setup: Create Cinema C1, film F1
        Film film1 = new Film();
        Room room1 = new Room();
        
        // Add Room1, film F1 to C1
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        // Assign film f1 screening at "2024-10-05 13:00:00", room Room1 (current time "2024-10-04 13:00:00")
        Screening screening = new Screening();
        screening.setTime(dateFormat.parse("2024-10-05 13:00:00"));
        Date currentTime = dateFormat.parse("2024-10-04 13:00:00");
        cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Action: Check Room1 availability at "2024-10-05 13:00:00"
        Date checkTime = dateFormat.parse("2024-10-05 13:00:00");
        boolean result = cinema.checkAvailability(room1, checkTime);
        
        // Expected Output: false
        assertFalse("Room should not be available when already assigned for screening", result);
    }
    
    @Test
    public void testCase3_checkRoomAtExactScreeningTime() throws Exception {
        // Setup: Create Cinema C1, add Film F1 and Room R1 to C1
        Film film1 = new Film();
        Room room1 = new Room();
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Assign screening for F1 in R1 at "2024-12-01 14:00:00"
        Screening screening = new Screening();
        screening.setTime(dateFormat.parse("2024-12-01 14:00:00"));
        // Using a current time before the screening time for assignment
        Date currentTime = dateFormat.parse("2024-11-01 14:00:00");
        cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Action: Check R1 availability at "2024-12-02 14:00:00" (different time)
        Date checkTime = dateFormat.parse("2024-12-02 14:00:00");
        boolean result = cinema.checkAvailability(room1, checkTime);
        
        // Expected Output: true
        assertTrue("Room should be available at different time slot", result);
    }
    
    @Test
    public void testCase4_checkMultipleRooms() throws Exception {
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
        screening1.setTime(dateFormat.parse("2024-10-05 13:00:00"));
        Date currentTime1 = dateFormat.parse("2024-10-01 13:00:00");
        cinema.assignScreening(film1, room1, screening1, currentTime1);
        
        // Assign film F2 screening S2 at "2024-10-05 13:00:00", room Room2 (current time: "2024-10-03 13:00:00")
        Screening screening2 = new Screening();
        screening2.setTime(dateFormat.parse("2024-10-05 13:00:00"));
        Date currentTime2 = dateFormat.parse("2024-10-03 13:00:00");
        cinema.assignScreening(film2, room2, screening2, currentTime2);
        
        // Action: Check Room1, Room2 availability at "2024-10-05 13:00:00"
        Date checkTime = dateFormat.parse("2024-10-05 13:00:00");
        boolean resultRoom1 = cinema.checkAvailability(room1, checkTime);
        boolean resultRoom2 = cinema.checkAvailability(room2, checkTime);
        
        // Expected Output: S1: false, S2: false
        assertFalse("Room1 should not be available when assigned for screening", resultRoom1);
        assertFalse("Room2 should not be available when assigned for screening", resultRoom2);
    }
    
    @Test
    public void testCase5_checkDifferentTimeSlot() throws Exception {
        // Setup: Create Cinema C1, Film F1
        Film film1 = new Film();
        Room room1 = new Room();
        
        // Add Room1, Film F1 to C1
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        // Assign Film F1 screening at "2024-10-05 13:00:00", room Room1 (current time: "2024-09-03 13:00:00")
        Screening screening = new Screening();
        screening.setTime(dateFormat.parse("2024-10-05 13:00:00"));
        Date currentTime = dateFormat.parse("2024-09-03 13:00:00");
        cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Action: Check Room1 availability at "2024-10-05 14:00:00" (different time slot)
        Date checkTime = dateFormat.parse("2024-10-05 14:00:00");
        boolean result = cinema.checkAvailability(room1, checkTime);
        
        // Expected Output: true
        assertTrue("Room should be available at different time slot", result);
    }
}