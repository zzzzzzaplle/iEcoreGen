import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    private Cinema cinema;
    private Room room1;
    private Room room2;
    private Film film1;
    private Film film2;

    @Before
    public void setUp() {
        cinema = new Cinema();
        room1 = new Room("Room1");
        room2 = new Room("Room2");
        film1 = new Film("F1", "Film One");
        film2 = new Film("F2", "Film Two");
    }

    @Test
    public void testCase1_CheckAvailableRoomWithNoScreenings() {
        // Setup: Create Cinema C1 and add Room1 to C1
        cinema.addRoom(room1);
        
        // Action: check Room1 availability at "2024-10-05 13:00:00"
        boolean result = cinema.checkRoomAvailability(room1, "2024-10-05 13:00:00");
        
        // Expected Output: true
        assertTrue("Room should be available when no screenings exist", result);
    }

    @Test
    public void testCase2_CheckAssignedRoom() {
        // Setup: Create Cinema C1, add Room1 and film F1 to C1
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        // Assign film F1 screening at "2024-10-05 13:00:00", room Room1 (current time "2024-10-04 13:00:00")
        cinema.assignScreening(film1, room1, "2024-10-05 13:00:00", "2024-10-04 13:00:00");
        
        // Action: check Room1 availability at "2024-10-05 13:00:00"
        boolean result = cinema.checkRoomAvailability(room1, "2024-10-05 13:00:00");
        
        // Expected Output: false
        assertFalse("Room should not be available when already assigned", result);
    }

    @Test
    public void testCase3_CheckRoomAtExactScreeningTime() {
        // Setup: Create Cinema C1, add Film F1 and Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Assign screening for F1 in R1 at "2024-12-01 14:00:00"
        cinema.assignScreening(film1, room1, "2024-12-01 14:00:00", "2024-11-01 14:00:00");
        
        // Action: Check R1 availability at "2024-12-02 14:00:00"
        boolean result = cinema.checkRoomAvailability(room1, "2024-12-02 14:00:00");
        
        // Expected Output: true
        assertTrue("Room should be available at different time than screening", result);
    }

    @Test
    public void testCase4_CheckMultipleRooms() {
        // Setup: Create Cinema C1, film F1, film F2
        cinema.addFilm(film1);
        cinema.addFilm(film2);
        cinema.addRoom(room1);
        cinema.addRoom(room2);
        
        // Assign film F1 screening S1 at "2024-10-05 13:00:00", room Room1 (current time: "2024-10-01 13:00:00")
        cinema.assignScreening(film1, room1, "2024-10-05 13:00:00", "2024-10-01 13:00:00");
        
        // Assign film F2 screening S2 at "2024-10-05 13:00:00", room Room2 (current time: "2024-10-03 13:00:00")
        cinema.assignScreening(film2, room2, "2024-10-05 13:00:00", "2024-10-03 13:00:00");
        
        // Action: check Room1, Room2 availability at "2024-10-05 13:00:00"
        boolean resultRoom1 = cinema.checkRoomAvailability(room1, "2024-10-05 13:00:00");
        boolean resultRoom2 = cinema.checkRoomAvailability(room2, "2024-10-05 13:00:00");
        
        // Expected Output: S1: false, S2: false
        assertFalse("Room1 should not be available at screening time", resultRoom1);
        assertFalse("Room2 should not be available at screening time", resultRoom2);
    }

    @Test
    public void testCase5_CheckDifferentTimeSlot() {
        // Setup: Create Cinema C1, Film F1, add Room1 and Film F1 to C1
        cinema.addRoom(room1);
        cinema.addFilm(film1);
        
        // Assign Film F1 screening at "2024-10-05 13:00:00", room Room1 (current time: "2024-09-03 13:00:00")
        cinema.assignScreening(film1, room1, "2024-10-05 13:00:00", "2024-09-03 13:00:00");
        
        // Action: check Room1 availability at "2024-10-05 14:00:00"
        boolean result = cinema.checkRoomAvailability(room1, "2024-10-05 14:00:00");
        
        // Expected Output: true
        assertTrue("Room should be available at different time slot", result);
    }
}