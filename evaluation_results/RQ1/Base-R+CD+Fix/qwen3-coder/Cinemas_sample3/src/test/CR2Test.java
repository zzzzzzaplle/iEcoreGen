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
        // Setup
        Room room1 = new Room("Room1");
        cinema.addRoom(room1);
        Date checkTime = dateFormat.parse("2024-10-05 13:00:00");
        
        // Action
        boolean result = cinema.checkAvailability(room1, checkTime);
        
        // Expected Output: true
        assertTrue("Room1 should be available when no screenings are assigned", result);
    }
    
    @Test
    public void testCase2_checkAssignedRoom() throws Exception {
        // Setup
        Room room1 = new Room("Room1");
        Film film1 = new Film("F1");
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        Date screeningTime = dateFormat.parse("2024-10-05 13:00:00");
        Date currentTime = dateFormat.parse("2024-10-04 13:00:00");
        cinema.assignScreening(film1, room1, screeningTime, currentTime);
        
        // Action
        boolean result = cinema.checkAvailability(room1, screeningTime);
        
        // Expected Output: false
        assertFalse("Room1 should not be available when already assigned for screening", result);
    }
    
    @Test
    public void testCase3_checkRoomAtExactScreeningTime() throws Exception {
        // Setup
        Room room1 = new Room("R1");
        Film film1 = new Film("F1");
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        Date screeningTime = dateFormat.parse("2024-12-01 14:00:00");
        Date currentTime = dateFormat.parse("2024-10-01 14:00:00"); // Current time before screening
        cinema.assignScreening(film1, room1, screeningTime, currentTime);
        
        // Action: Check availability at different time (2024-12-02 14:00:00)
        Date checkTime = dateFormat.parse("2024-12-02 14:00:00");
        boolean result = cinema.checkAvailability(room1, checkTime);
        
        // Expected Output: true
        assertTrue("Room1 should be available at different time from screening", result);
    }
    
    @Test
    public void testCase4_checkMultipleRooms() throws Exception {
        // Setup
        Room room1 = new Room("Room1");
        Room room2 = new Room("Room2");
        Film film1 = new Film("F1");
        Film film2 = new Film("F2");
        
        cinema.addRoom(room1);
        cinema.addRoom(room2);
        cinema.addFilm(film1);
        cinema.addFilm(film2);
        
        Date screeningTime = dateFormat.parse("2024-10-05 13:00:00");
        Date currentTime1 = dateFormat.parse("2024-10-01 13:00:00");
        Date currentTime2 = dateFormat.parse("2024-10-03 13:00:00");
        
        cinema.assignScreening(film1, room1, screeningTime, currentTime1);
        cinema.assignScreening(film2, room2, screeningTime, currentTime2);
        
        // Action
        boolean resultRoom1 = cinema.checkAvailability(room1, screeningTime);
        boolean resultRoom2 = cinema.checkAvailability(room2, screeningTime);
        
        // Expected Output: S1: false, S2: false
        assertFalse("Room1 should not be available as it has screening at this time", resultRoom1);
        assertFalse("Room2 should not be available as it has screening at this time", resultRoom2);
    }
    
    @Test
    public void testCase5_checkDifferentTimeSlot() throws Exception {
        // Setup
        Room room1 = new Room("Room1");
        Film film1 = new Film("F1");
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        Date screeningTime = dateFormat.parse("2024-10-05 13:00:00");
        Date currentTime = dateFormat.parse("2024-09-03 13:00:00");
        cinema.assignScreening(film1, room1, screeningTime, currentTime);
        
        // Action: Check availability at different time slot (14:00:00 instead of 13:00:00)
        Date checkTime = dateFormat.parse("2024-10-05 14:00:00");
        boolean result = cinema.checkAvailability(room1, checkTime);
        
        // Expected Output: true
        assertTrue("Room1 should be available at different time slot", result);
    }
}