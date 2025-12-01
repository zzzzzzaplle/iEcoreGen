import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CR2Test {
    private Cinema cinema;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_checkAvailableRoomWithNoScreenings() {
        // Setup: Create Cinema C1 and add Room1 to C1
        Room room1 = new Room("Room1", 100);
        cinema.addRoom(room1);
        
        // Action: Check Room1 availability at "2024-10-05 13:00:00"
        LocalDateTime checkTime = LocalDateTime.parse("2024-10-05 13:00:00", formatter);
        boolean result = cinema.checkRoomAvailability(room1, checkTime);
        
        // Expected Output: true
        assertTrue("Room should be available when no screenings are scheduled", result);
    }
    
    @Test
    public void testCase2_checkAssignedRoom() {
        // Setup: Create Cinema C1, film F1
        Film film1 = new Film("Film1", "Action", 120);
        Room room1 = new Room("Room1", 100);
        
        // Add Room1, film F1 to C1
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        // Assign film f1 screening at "2024-10-05 13:00:00", room Room1 (current time "2024-10-04 13:00:00")
        LocalDateTime screeningTime = LocalDateTime.parse("2024-10-05 13:00:00", formatter);
        LocalDateTime currentTime = LocalDateTime.parse("2024-10-04 13:00:00", formatter);
        cinema.assignScreening(film1, room1, screeningTime, currentTime);
        
        // Action: Check Room1 availability at "2024-10-05 13:00:00"
        boolean result = cinema.checkRoomAvailability(room1, screeningTime);
        
        // Expected Output: false
        assertFalse("Room should not be available when already assigned for screening", result);
    }
    
    @Test
    public void testCase3_checkRoomAtExactScreeningTime() {
        // Setup: Create Cinema C1
        Film film1 = new Film("Film1", "Drama", 90);
        Room room1 = new Room("Room1", 80);
        
        // Add Film F1 and Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Assign screening for F1 in R1 at "2024-12-01 14:00:00"
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-01 14:00:00", formatter);
        LocalDateTime currentTime = LocalDateTime.parse("2024-11-30 14:00:00", formatter);
        cinema.assignScreening(film1, room1, screeningTime, currentTime);
        
        // Action: Check R1 availability at "2024-12-02 14:00:00" (different time)
        LocalDateTime checkTime = LocalDateTime.parse("2024-12-02 14:00:00", formatter);
        boolean result = cinema.checkRoomAvailability(room1, checkTime);
        
        // Expected Output: true
        assertTrue("Room should be available at different time slot", result);
    }
    
    @Test
    public void testCase4_checkMultipleRooms() {
        // Setup: Create Cinema C1, film F1, film F2
        Film film1 = new Film("Film1", "Action", 120);
        Film film2 = new Film("Film2", "Comedy", 90);
        Room room1 = new Room("Room1", 100);
        Room room2 = new Room("Room2", 80);
        
        // Add Room1, Room2, film F1, film F2 to C1
        cinema.addRoom(room1);
        cinema.addRoom(room2);
        cinema.addFilm(film1);
        cinema.addFilm(film2);
        
        // Assign film F1 screening S1 at "2024-10-05 13:00:00", room Room1 (current time: "2024-10-01 13:00:00")
        LocalDateTime screeningTime1 = LocalDateTime.parse("2024-10-05 13:00:00", formatter);
        LocalDateTime currentTime1 = LocalDateTime.parse("2024-10-01 13:00:00", formatter);
        cinema.assignScreening(film1, room1, screeningTime1, currentTime1);
        
        // Assign film F2 screening S2 at "2024-10-05 13:00:00", room Room2 (current time: "2024-10-03 13:00:00")
        LocalDateTime screeningTime2 = LocalDateTime.parse("2024-10-05 13:00:00", formatter);
        LocalDateTime currentTime2 = LocalDateTime.parse("2024-10-03 13:00:00", formatter);
        cinema.assignScreening(film2, room2, screeningTime2, currentTime2);
        
        // Action: Check Room1, Room2 availability at "2024-10-05 13:00:00"
        LocalDateTime checkTime = LocalDateTime.parse("2024-10-05 13:00:00", formatter);
        boolean resultRoom1 = cinema.checkRoomAvailability(room1, checkTime);
        boolean resultRoom2 = cinema.checkRoomAvailability(room2, checkTime);
        
        // Expected Output: S1: false, S2: false
        assertFalse("Room1 should not be available at scheduled screening time", resultRoom1);
        assertFalse("Room2 should not be available at scheduled screening time", resultRoom2);
    }
    
    @Test
    public void testCase5_checkDifferentTimeSlot() {
        // Setup: Create Cinema C1, Film F1
        Film film1 = new Film("Film1", "Thriller", 110);
        Room room1 = new Room("Room1", 120);
        
        // Add Room1, Film F1 to C1
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        // Assign Film F1 screening at "2024-10-05 13:00:00", room Room1 (current time: "2024-09-03 13:00:00")
        LocalDateTime screeningTime = LocalDateTime.parse("2024-10-05 13:00:00", formatter);
        LocalDateTime currentTime = LocalDateTime.parse("2024-09-03 13:00:00", formatter);
        cinema.assignScreening(film1, room1, screeningTime, currentTime);
        
        // Action: Check Room1 availability at "2024-10-05 14:00:00"
        LocalDateTime checkTime = LocalDateTime.parse("2024-10-05 14:00:00", formatter);
        boolean result = cinema.checkRoomAvailability(room1, checkTime);
        
        // Expected Output: true
        assertTrue("Room should be available at different time slot", result);
    }
}