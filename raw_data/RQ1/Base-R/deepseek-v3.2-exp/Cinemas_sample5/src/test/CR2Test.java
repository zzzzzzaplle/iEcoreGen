import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    private Cinema cinema;
    private Room room1;
    private Room room2;
    private Film film1;
    private Film film2;

    @Before
    public void setUp() {
        cinema = new Cinema();
        room1 = new Room("Room1", 100);
        room2 = new Room("Room2", 150);
        film1 = new Film("Film1", 120);
        film2 = new Film("Film2", 90);
    }

    @Test
    public void testCase1_checkAvailableRoomWithNoScreenings() {
        // Setup: Create Cinema C1 and add Room1 to C1
        cinema.addRoom(room1);
        
        // Action: check Room1 availability at the current time "2024-10-05 13:00:00"
        boolean result = cinema.checkRoomAvailability(room1, "2024-10-05 13:00:00");
        
        // Expected Output: true
        assertTrue("Room should be available when no screenings exist", result);
    }

    @Test
    public void testCase2_checkAssignedRoom() {
        // Setup: Create Cinema C1, film F1
        // Add Room1, film F1 to C1
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        // Assign film f1 screening at "2024-10-05 13:00:00", room Room1 (the current time "2024-10-04 13:00:00")
        cinema.assignScreening(film1, room1, "2024-10-05 13:00:00", "2024-10-04 13:00:00");
        
        // Action: check Room1 availability at the current time "2024-10-05 13:00:00"
        boolean result = cinema.checkRoomAvailability(room1, "2024-10-05 13:00:00");
        
        // Expected Output: false
        assertFalse("Room should not be available when already assigned for a screening", result);
    }

    @Test
    public void testCase3_checkRoomAtExactScreeningTime() {
        // Setup: Create Cinema C1
        // Add Film F1 and Room R1 to C1
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        // Assign screening for F1 in R1 at "2024-12-01 14:00:00"
        cinema.assignScreening(film1, room1, "2024-12-01 14:00:00", "2024-11-01 14:00:00");
        
        // Action: Check R1 availability at the current time "2024-12-02 14:00:00"
        boolean result = cinema.checkRoomAvailability(room1, "2024-12-02 14:00:00");
        
        // Expected Output: true
        assertTrue("Room should be available at a different time slot", result);
    }

    @Test
    public void testCase4_checkMultipleRooms() {
        // Setup: Create Cinema C1, film F1, film F2
        // Add Room1, Room2, film F1, film F2 to C1
        cinema.addRoom(room1);
        cinema.addRoom(room2);
        cinema.addFilm(film1);
        cinema.addFilm(film2);
        
        // Assign film F1 screening S1 at "2024-10-05 13:00:00", room Room1 (the current time : "2024-10-01 13:00:00")
        cinema.assignScreening(film1, room1, "2024-10-05 13:00:00", "2024-10-01 13:00:00");
        
        // Assign film F2 screening S2 at "2024-10-05 13:00:00", room Room2 (the current time : "2024-10-03 13:00:00")
        cinema.assignScreening(film2, room2, "2024-10-05 13:00:00", "2024-10-03 13:00:00");
        
        // Action: check Room1, Room2 availability at the current time "2024-10-05 13:00:00"
        boolean resultRoom1 = cinema.checkRoomAvailability(room1, "2024-10-05 13:00:00");
        boolean resultRoom2 = cinema.checkRoomAvailability(room2, "2024-10-05 13:00:00");
        
        // Expected Output: S1: false, S2: false
        assertFalse("Room1 should not be available as it has a screening", resultRoom1);
        assertFalse("Room2 should not be available as it has a screening", resultRoom2);
    }

    @Test
    public void testCase5_checkDifferentTimeSlot() {
        // Setup: Create Cinema C1, Film F1
        // Add Room1, Film F1 to C1
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        // Assign Film F1 screening at "2024-10-05 13:00:00", room Room1 (the current time : "2024-09-03 13:00:00")
        cinema.assignScreening(film1, room1, "2024-10-05 13:00:00", "2024-09-03 13:00:00");
        
        // Action: check Room1 availability at the current time "2024-10-05 14:00:00"
        boolean result = cinema.checkRoomAvailability(room1, "2024-10-05 14:00:00");
        
        // Expected Output: true
        assertTrue("Room should be available at a different time slot", result);
    }
}