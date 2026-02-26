import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR2Test {
    
    private Cinema cinema;
    private Room room1;
    private Room room2;
    private Film film1;
    private Film film2;
    private Screening screening1;
    private Screening screening2;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        room1 = new Room();
        room2 = new Room();
        film1 = new Film();
        film2 = new Film();
        screening1 = new Screening();
        screening2 = new Screening();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_checkAvailableRoomWithNoScreenings() {
        // Setup: Create Cinema C1 and add Room1 to C1
        cinema.addRoom(room1);
        
        // Action: check Room1 availability at "2024-10-05 13:00:00"
        boolean result = cinema.checkAvailability(room1, "2024-10-05 13:00:00");
        
        // Expected Output: true
        assertTrue("Room1 should be available when no screenings are assigned", result);
    }
    
    @Test
    public void testCase2_checkAssignedRoom() throws Exception {
        // Setup: Create Cinema C1, film F1
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        // Assign film f1 screening at "2024-10-05 13:00:00", room Room1
        Date screeningTime = dateFormat.parse("2024-10-05 13:00:00");
        screening1.setTime(screeningTime);
        screening1.setFilm(film1);
        screening1.setRoom(room1);
        
        cinema.assignScreening(film1, room1, screening1, "2024-10-04 13:00:00");
        
        // Action: check Room1 availability at "2024-10-05 13:00:00"
        boolean result = cinema.checkAvailability(room1, "2024-10-05 13:00:00");
        
        // Expected Output: false
        assertFalse("Room1 should not be available when assigned to a screening at the same time", result);
    }
    
    @Test
    public void testCase3_checkRoomAtExactScreeningTime() throws Exception {
        // Setup: Create Cinema C1, add Film F1 and Room R1 to C1
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        // Assign screening for F1 in R1 at "2024-12-01 14:00:00"
        Date screeningTime = dateFormat.parse("2024-12-01 14:00:00");
        screening1.setTime(screeningTime);
        screening1.setFilm(film1);
        screening1.setRoom(room1);
        
        cinema.assignScreening(film1, room1, screening1, "2024-11-01 14:00:00");
        
        // Action: Check R1 availability at "2024-12-02 14:00:00"
        boolean result = cinema.checkAvailability(room1, "2024-12-02 14:00:00");
        
        // Expected Output: true
        assertTrue("Room1 should be available at different time than the scheduled screening", result);
    }
    
    @Test
    public void testCase4_checkMultipleRooms() throws Exception {
        // Setup: Create Cinema C1, film F1, film F2
        cinema.addRoom(room1);
        cinema.addRoom(room2);
        cinema.addFilm(film1);
        cinema.addFilm(film2);
        
        // Assign film F1 screening S1 at "2024-10-05 13:00:00", room Room1
        Date screeningTime = dateFormat.parse("2024-10-05 13:00:00");
        screening1.setTime(screeningTime);
        screening1.setFilm(film1);
        screening1.setRoom(room1);
        
        cinema.assignScreening(film1, room1, screening1, "2024-10-01 13:00:00");
        
        // Assign film F2 screening S2 at "2024-10-05 13:00:00", room Room2
        screening2.setTime(screeningTime);
        screening2.setFilm(film2);
        screening2.setRoom(room2);
        
        cinema.assignScreening(film2, room2, screening2, "2024-10-03 13:00:00");
        
        // Action: check Room1, Room2 availability at "2024-10-05 13:00:00"
        boolean resultRoom1 = cinema.checkAvailability(room1, "2024-10-05 13:00:00");
        boolean resultRoom2 = cinema.checkAvailability(room2, "2024-10-05 13:00:00");
        
        // Expected Output: S1: false, S2: false
        assertFalse("Room1 should not be available at the scheduled screening time", resultRoom1);
        assertFalse("Room2 should not be available at the scheduled screening time", resultRoom2);
    }
    
    @Test
    public void testCase5_checkDifferentTimeSlot() throws Exception {
        // Setup: Create Cinema C1, Film F1, add Room1, Film F1 to C1
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        // Assign Film F1 screening at "2024-10-05 13:00:00", room Room1
        Date screeningTime = dateFormat.parse("2024-10-05 13:00:00");
        screening1.setTime(screeningTime);
        screening1.setFilm(film1);
        screening1.setRoom(room1);
        
        cinema.assignScreening(film1, room1, screening1, "2024-09-03 13:00:00");
        
        // Action: check Room1 availability at "2024-10-05 14:00:00"
        boolean result = cinema.checkAvailability(room1, "2024-10-05 14:00:00");
        
        // Expected Output: true
        assertTrue("Room1 should be available at different time slot than the scheduled screening", result);
    }
}