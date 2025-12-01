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
    public void testCase1_checkAvailableRoomWithNoScreenings() throws Exception {
        // Setup: Create Cinema C1 and add Room1 to C1
        cinema.addRoom(room1);
        
        // Action: check Room1 availability at "2024-10-05 13:00:00"
        Date checkTime = dateFormat.parse("2024-10-05 13:00:00");
        boolean result = cinema.checkAvailability(room1, checkTime);
        
        // Expected Output: true
        assertTrue(result);
    }

    @Test
    public void testCase2_checkAssignedRoom() throws Exception {
        // Setup: Create Cinema C1, film F1, add Room1 and film F1 to C1
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        // Assign film F1 screening at "2024-10-05 13:00:00", room Room1
        Screening screening = new Screening();
        Date screeningTime = dateFormat.parse("2024-10-05 13:00:00");
        screening.setTime(screeningTime);
        Date currentTime = dateFormat.parse("2024-10-04 13:00:00");
        cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Action: check Room1 availability at "2024-10-05 13:00:00"
        Date checkTime = dateFormat.parse("2024-10-05 13:00:00");
        boolean result = cinema.checkAvailability(room1, checkTime);
        
        // Expected Output: false
        assertFalse(result);
    }

    @Test
    public void testCase3_checkRoomAtDifferentTime() throws Exception {
        // Setup: Create Cinema C1, add Film F1 and Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Assign screening for F1 in R1 at "2024-12-01 14:00:00"
        Screening screening = new Screening();
        Date screeningTime = dateFormat.parse("2024-12-01 14:00:00");
        screening.setTime(screeningTime);
        Date currentTime = dateFormat.parse("2024-11-01 14:00:00");
        cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Action: Check R1 availability at "2024-12-02 14:00:00"
        Date checkTime = dateFormat.parse("2024-12-02 14:00:00");
        boolean result = cinema.checkAvailability(room1, checkTime);
        
        // Expected Output: true
        assertTrue(result);
    }

    @Test
    public void testCase4_checkMultipleRooms() throws Exception {
        // Setup: Create Cinema C1, film F1, film F2
        cinema.addFilm(film1);
        cinema.addFilm(film2);
        cinema.addRoom(room1);
        cinema.addRoom(room2);
        
        // Assign film F1 screening S1 at "2024-10-05 13:00:00", room Room1
        Screening screening1 = new Screening();
        Date screeningTime1 = dateFormat.parse("2024-10-05 13:00:00");
        screening1.setTime(screeningTime1);
        Date currentTime1 = dateFormat.parse("2024-10-01 13:00:00");
        cinema.assignScreening(film1, room1, screening1, currentTime1);
        
        // Assign film F2 screening S2 at "2024-10-05 13:00:00", room Room2
        Screening screening2 = new Screening();
        Date screeningTime2 = dateFormat.parse("2024-10-05 13:00:00");
        screening2.setTime(screeningTime2);
        Date currentTime2 = dateFormat.parse("2024-10-03 13:00:00");
        cinema.assignScreening(film2, room2, screening2, currentTime2);
        
        // Action: check Room1 and Room2 availability at "2024-10-05 13:00:00"
        Date checkTime = dateFormat.parse("2024-10-05 13:00:00");
        boolean resultRoom1 = cinema.checkAvailability(room1, checkTime);
        boolean resultRoom2 = cinema.checkAvailability(room2, checkTime);
        
        // Expected Output: S1: false, S2: false
        assertFalse(resultRoom1);
        assertFalse(resultRoom2);
    }

    @Test
    public void testCase5_checkDifferentTimeSlot() throws Exception {
        // Setup: Create Cinema C1, Film F1, add Room1 and Film F1 to C1
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        // Assign Film F1 screening at "2024-10-05 13:00:00", room Room1
        Screening screening = new Screening();
        Date screeningTime = dateFormat.parse("2024-10-05 13:00:00");
        screening.setTime(screeningTime);
        Date currentTime = dateFormat.parse("2024-09-03 13:00:00");
        cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Action: check Room1 availability at "2024-10-05 14:00:00"
        Date checkTime = dateFormat.parse("2024-10-05 14:00:00");
        boolean result = cinema.checkAvailability(room1, checkTime);
        
        // Expected Output: true
        assertTrue(result);
    }
}