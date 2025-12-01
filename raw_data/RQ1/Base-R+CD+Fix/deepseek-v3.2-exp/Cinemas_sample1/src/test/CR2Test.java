import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
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
    public void testCase1_checkAvailableRoomWithNoScreenings() throws ParseException {
        // Setup: Create Cinema C1 and add Room1 to C1
        Room room1 = new Room();
        cinema.addRoom(room1);
        
        // Action: Check Room1 availability at "2024-10-05 13:00:00"
        Date checkTime = dateFormat.parse("2024-10-05 13:00:00");
        boolean result = cinema.checkAvailability(room1, checkTime);
        
        // Expected Output: true
        assertTrue("Room should be available when no screenings exist", result);
    }
    
    @Test
    public void testCase2_checkAssignedRoom() throws ParseException {
        // Setup: Create Cinema C1, film F1
        Cinema c1 = new Cinema();
        Film f1 = new Film();
        Room room1 = new Room();
        
        // Add Room1, film F1 to C1
        c1.addRoom(room1);
        c1.addFilm(f1);
        
        // Assign film f1 screening at "2024-10-05 13:00:00", room Room1 (current time "2024-10-04 13:00:00")
        Date screeningTime = dateFormat.parse("2024-10-05 13:00:00");
        Date currentTime = dateFormat.parse("2024-10-04 13:00:00");
        c1.assignScreening(f1, room1, screeningTime, currentTime);
        
        // Action: Check Room1 availability at "2024-10-05 13:00:00"
        boolean result = c1.checkAvailability(room1, screeningTime);
        
        // Expected Output: false
        assertFalse("Room should not be available when assigned to screening", result);
    }
    
    @Test
    public void testCase3_checkRoomAtExactScreeningTime() throws ParseException {
        // Setup: Create Cinema C1
        Cinema c1 = new Cinema();
        Film f1 = new Film();
        Room r1 = new Room();
        
        // Add Film F1 and Room R1 to C1
        c1.addFilm(f1);
        c1.addRoom(r1);
        
        // Assign screening for F1 in R1 at "2024-12-01 14:00:00"
        Date screeningTime = dateFormat.parse("2024-12-01 14:00:00");
        Date currentTime = dateFormat.parse("2024-11-01 14:00:00"); // Must be before screening time
        c1.assignScreening(f1, r1, screeningTime, currentTime);
        
        // Action: Check R1 availability at "2024-12-02 14:00:00" (different time)
        Date checkTime = dateFormat.parse("2024-12-02 14:00:00");
        boolean result = c1.checkAvailability(r1, checkTime);
        
        // Expected Output: true
        assertTrue("Room should be available at different time slot", result);
    }
    
    @Test
    public void testCase4_checkMultipleRooms() throws ParseException {
        // Setup: Create Cinema C1, film F1, film F2
        Cinema c1 = new Cinema();
        Film f1 = new Film();
        Film f2 = new Film();
        Room room1 = new Room();
        Room room2 = new Room();
        
        // Add Room1, Room2, film F1, film F2 to C1
        c1.addRoom(room1);
        c1.addRoom(room2);
        c1.addFilm(f1);
        c1.addFilm(f2);
        
        // Assign film F1 screening S1 at "2024-10-05 13:00:00", room Room1 (current time: "2024-10-01 13:00:00")
        Date screeningTime = dateFormat.parse("2024-10-05 13:00:00");
        Date currentTime1 = dateFormat.parse("2024-10-01 13:00:00");
        c1.assignScreening(f1, room1, screeningTime, currentTime1);
        
        // Assign film F2 screening S2 at "2024-10-05 13:00:00", room Room2 (current time: "2024-10-03 13:00:00")
        Date currentTime2 = dateFormat.parse("2024-10-03 13:00:00");
        c1.assignScreening(f2, room2, screeningTime, currentTime2);
        
        // Action: Check Room1, Room2 availability at "2024-10-05 13:00:00"
        boolean room1Result = c1.checkAvailability(room1, screeningTime);
        boolean room2Result = c1.checkAvailability(room2, screeningTime);
        
        // Expected Output: S1: false, S2: false
        assertFalse("Room1 should not be available at assigned time", room1Result);
        assertFalse("Room2 should not be available at assigned time", room2Result);
    }
    
    @Test
    public void testCase5_checkDifferentTimeSlot() throws ParseException {
        // Setup: Create Cinema C1, Film F1
        Cinema c1 = new Cinema();
        Film f1 = new Film();
        Room room1 = new Room();
        
        // Add Room1, Film F1 to C1
        c1.addRoom(room1);
        c1.addFilm(f1);
        
        // Assign Film F1 screening at "2024-10-05 13:00:00", room Room1 (current time: "2024-09-03 13:00:00")
        Date screeningTime = dateFormat.parse("2024-10-05 13:00:00");
        Date currentTime = dateFormat.parse("2024-09-03 13:00:00");
        c1.assignScreening(f1, room1, screeningTime, currentTime);
        
        // Action: Check Room1 availability at "2024-10-05 14:00:00"
        Date checkTime = dateFormat.parse("2024-10-05 14:00:00");
        boolean result = c1.checkAvailability(room1, checkTime);
        
        // Expected Output: true
        assertTrue("Room should be available at different time slot", result);
    }
}