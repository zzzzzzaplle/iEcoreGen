import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
        // Setup
        Room room1 = new Room("Room1");
        cinema.addRoom(room1);
        
        LocalDateTime checkTime = LocalDateTime.parse("2024-10-05 13:00:00", formatter);
        
        // Action
        boolean result = cinema.isRoomAvailable(room1, checkTime);
        
        // Expected Output: true
        assertTrue("Room should be available when no screenings exist", result);
    }
    
    @Test
    public void testCase2_checkAssignedRoom() {
        // Setup
        Room room1 = new Room("Room1");
        Film film1 = new Film("F1");
        
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        LocalDateTime currentTime = LocalDateTime.parse("2024-10-04 13:00:00", formatter);
        LocalDateTime screeningTime = LocalDateTime.parse("2024-10-05 13:00:00", formatter);
        
        cinema.assignScreening(film1, room1, screeningTime, currentTime);
        
        // Action
        boolean result = cinema.isRoomAvailable(room1, screeningTime);
        
        // Expected Output: false
        assertFalse("Room should not be available when already assigned for screening", result);
    }
    
    @Test
    public void testCase3_checkRoomAtExactScreeningTime() {
        // Setup
        Room room1 = new Room("R1");
        Film film1 = new Film("F1");
        
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        LocalDateTime screeningTime = LocalDateTime.parse("2024-12-01 14:00:00", formatter);
        LocalDateTime currentTime = LocalDateTime.parse("2024-10-01 13:00:00", formatter);
        
        cinema.assignScreening(film1, room1, screeningTime, currentTime);
        
        LocalDateTime checkTime = LocalDateTime.parse("2024-12-02 14:00:00", formatter);
        
        // Action
        boolean result = cinema.isRoomAvailable(room1, checkTime);
        
        // Expected Output: true
        assertTrue("Room should be available at different time than screening", result);
    }
    
    @Test
    public void testCase4_checkMultipleRooms() {
        // Setup
        Room room1 = new Room("Room1");
        Room room2 = new Room("Room2");
        Film film1 = new Film("F1");
        Film film2 = new Film("F2");
        
        cinema.addRoom(room1);
        cinema.addRoom(room2);
        cinema.addFilm(film1);
        cinema.addFilm(film2);
        
        LocalDateTime screeningTime = LocalDateTime.parse("2024-10-05 13:00:00", formatter);
        
        LocalDateTime currentTime1 = LocalDateTime.parse("2024-10-01 13:00:00", formatter);
        cinema.assignScreening(film1, room1, screeningTime, currentTime1);
        
        LocalDateTime currentTime2 = LocalDateTime.parse("2024-10-03 13:00:00", formatter);
        cinema.assignScreening(film2, room2, screeningTime, currentTime2);
        
        // Action
        boolean resultRoom1 = cinema.isRoomAvailable(room1, screeningTime);
        boolean resultRoom2 = cinema.isRoomAvailable(room2, screeningTime);
        
        // Expected Output: S1: false, S2: false
        assertFalse("Room1 should not be available at screening time", resultRoom1);
        assertFalse("Room2 should not be available at screening time", resultRoom2);
    }
    
    @Test
    public void testCase5_checkDifferentTimeSlot() {
        // Setup
        Room room1 = new Room("Room1");
        Film film1 = new Film("F1");
        
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        LocalDateTime screeningTime = LocalDateTime.parse("2024-10-05 13:00:00", formatter);
        LocalDateTime currentTime = LocalDateTime.parse("2024-09-03 13:00:00", formatter);
        
        cinema.assignScreening(film1, room1, screeningTime, currentTime);
        
        LocalDateTime checkTime = LocalDateTime.parse("2024-10-05 14:00:00", formatter);
        
        // Action
        boolean result = cinema.isRoomAvailable(room1, checkTime);
        
        // Expected Output: true
        assertTrue("Room should be available at different time slot", result);
    }
}