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
        room1.setName("Room1");
        room2 = new Room();
        room2.setName("Room2");
        film1 = new Film();
        film1.setTitle("F1");
        film2 = new Film();
        film2.setTitle("F2");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    public void testCase1_checkAvailableRoomWithNoScreenings() throws ParseException {
        // Setup: Create Cinema C1 and add Room1 to C1
        cinema.addRoom(room1);
        
        // Action: check Room1 availability at the current time "2024-10-05 13:00:00"
        Date currentTime = dateFormat.parse("2024-10-05 13:00:00");
        boolean result = cinema.checkAvailability(room1, currentTime);
        
        // Expected Output: true
        assertTrue(result);
    }

    @Test
    public void testCase2_checkAssignedRoom() throws ParseException {
        // Setup: Create Cinema C1, film F1 and add Room1, film F1 to C1
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        // Assign film f1 screening at "2024-10-05 13:00:00", room Room1 (the current time "2024-10-04 13:00:00")
        Date screeningTime = dateFormat.parse("2024-10-05 13:00:00");
        Date currentTime = dateFormat.parse("2024-10-04 13:00:00");
        
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Action: check Room1 availability at the current time "2024-10-05 13:00:00"
        boolean result = cinema.checkAvailability(room1, screeningTime);
        
        // Expected Output: false
        assertFalse(result);
    }

    @Test
    public void testCase3_checkRoomAtExactScreeningTime() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1 and Room R1 to C1
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        // Assign screening for F1 in R1 at "2024-12-01 14:00:00"
        Date screeningTime = dateFormat.parse("2024-12-01 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-02 14:00:00");
        
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        cinema.assignScreening(film1, room1, screening, dateFormat.parse("2024-11-01 14:00:00"));
        
        // Action: Check R1 availability at the current time "2024-12-02 14:00:00"
        boolean result = cinema.checkAvailability(room1, currentTime);
        
        // Expected Output: true
        assertTrue(result);
    }

    @Test
    public void testCase4_checkMultipleRooms() throws ParseException {
        // Setup: Create Cinema C1, film F1, film F2 and add Room1, Room2, film F1, film F2 to C1
        cinema.addRoom(room1);
        cinema.addRoom(room2);
        cinema.addFilm(film1);
        cinema.addFilm(film2);
        
        // Assign film F1 screening S1 at "2024-10-05 13:00:00", room Room1 (the current time: "2024-10-01 13:00:00")
        Date screeningTime = dateFormat.parse("2024-10-05 13:00:00");
        Date currentTime1 = dateFormat.parse("2024-10-01 13:00:00");
        
        Screening screening1 = new Screening();
        screening1.setTime(screeningTime);
        cinema.assignScreening(film1, room1, screening1, currentTime1);
        
        // Assign film F2 screening S2 at "2024-10-05 13:00:00", room Room2 (the current time: "2024-10-03 13:00:00")
        Date currentTime2 = dateFormat.parse("2024-10-03 13:00:00");
        
        Screening screening2 = new Screening();
        screening2.setTime(screeningTime);
        cinema.assignScreening(film2, room2, screening2, currentTime2);
        
        // Action: check Room1, Room2 availability at the current time "2024-10-05 13:00:00"
        boolean result1 = cinema.checkAvailability(room1, screeningTime);
        boolean result2 = cinema.checkAvailability(room2, screeningTime);
        
        // Expected Output: S1: false, S2: false
        assertFalse(result1);
        assertFalse(result2);
    }

    @Test
    public void testCase5_checkDifferentTimeSlot() throws ParseException {
        // Setup: Create Cinema C1, Film F1 and add Room1, Film F1 to C1
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        // Assign Film F1 screening at "2024-10-05 13:00:00", room Room1 (the current time: "2024-09-03 13:00:00")
        Date screeningTime = dateFormat.parse("2024-10-05 13:00:00");
        Date currentTime = dateFormat.parse("2024-09-03 13:00:00");
        
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Action: check Room1 availability at the current time "2024-10-05 14:00:00"
        Date checkTime = dateFormat.parse("2024-10-05 14:00:00");
        boolean result = cinema.checkAvailability(room1, checkTime);
        
        // Expected Output: true
        assertTrue(result);
    }
}