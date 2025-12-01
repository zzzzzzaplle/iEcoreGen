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
        Room room1 = new Room();
        cinema.addRoom(room1);
        
        Date checkTime = dateFormat.parse("2024-10-05 13:00:00");
        
        // Action
        boolean result = cinema.checkAvailability(room1, checkTime);
        
        // Expected Output: true
        assertTrue("Room should be available when no screenings are scheduled", result);
    }
    
    @Test
    public void testCase2_checkAssignedRoom() throws Exception {
        // Setup
        Room room1 = new Room();
        Film film1 = new Film();
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        Screening screening = new Screening();
        screening.setFilm(film1);
        screening.setRoom(room1);
        screening.setTime(dateFormat.parse("2024-10-05 13:00:00"));
        
        Date currentTime = dateFormat.parse("2024-10-04 13:00:00");
        cinema.assignScreening(film1, room1, screening, currentTime);
        
        Date checkTime = dateFormat.parse("2024-10-05 13:00:00");
        
        // Action
        boolean result = cinema.checkAvailability(room1, checkTime);
        
        // Expected Output: false
        assertFalse("Room should not be available when assigned for screening", result);
    }
    
    @Test
    public void testCase3_checkRoomAtExactScreeningTime() throws Exception {
        // Setup
        Room room1 = new Room();
        Film film1 = new Film();
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        Screening screening = new Screening();
        screening.setFilm(film1);
        screening.setRoom(room1);
        screening.setTime(dateFormat.parse("2024-12-01 14:00:00"));
        
        Date currentTime = dateFormat.parse("2024-10-01 13:00:00");
        cinema.assignScreening(film1, room1, screening, currentTime);
        
        Date checkTime = dateFormat.parse("2024-12-02 14:00:00");
        
        // Action
        boolean result = cinema.checkAvailability(room1, checkTime);
        
        // Expected Output: true
        assertTrue("Room should be available at different time than screening", result);
    }
    
    @Test
    public void testCase4_checkMultipleRooms() throws Exception {
        // Setup
        Room room1 = new Room();
        Room room2 = new Room();
        Film film1 = new Film();
        Film film2 = new Film();
        
        cinema.addRoom(room1);
        cinema.addRoom(room2);
        cinema.addFilm(film1);
        cinema.addFilm(film2);
        
        // Assign screening for F1 in Room1
        Screening screening1 = new Screening();
        screening1.setFilm(film1);
        screening1.setRoom(room1);
        screening1.setTime(dateFormat.parse("2024-10-05 13:00:00"));
        
        Date currentTime1 = dateFormat.parse("2024-10-01 13:00:00");
        cinema.assignScreening(film1, room1, screening1, currentTime1);
        
        // Assign screening for F2 in Room2
        Screening screening2 = new Screening();
        screening2.setFilm(film2);
        screening2.setRoom(room2);
        screening2.setTime(dateFormat.parse("2024-10-05 13:00:00"));
        
        Date currentTime2 = dateFormat.parse("2024-10-03 13:00:00");
        cinema.assignScreening(film2, room2, screening2, currentTime2);
        
        Date checkTime = dateFormat.parse("2024-10-05 13:00:00");
        
        // Action
        boolean result1 = cinema.checkAvailability(room1, checkTime);
        boolean result2 = cinema.checkAvailability(room2, checkTime);
        
        // Expected Output: S1: false, S2: false
        assertFalse("Room1 should not be available at screening time", result1);
        assertFalse("Room2 should not be available at screening time", result2);
    }
    
    @Test
    public void testCase5_checkDifferentTimeSlot() throws Exception {
        // Setup
        Room room1 = new Room();
        Film film1 = new Film();
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        Screening screening = new Screening();
        screening.setFilm(film1);
        screening.setRoom(room1);
        screening.setTime(dateFormat.parse("2024-10-05 13:00:00"));
        
        Date currentTime = dateFormat.parse("2024-09-03 13:00:00");
        cinema.assignScreening(film1, room1, screening, currentTime);
        
        Date checkTime = dateFormat.parse("2024-10-05 14:00:00");
        
        // Action
        boolean result = cinema.checkAvailability(room1, checkTime);
        
        // Expected Output: true
        assertTrue("Room should be available at different time slot", result);
    }
}