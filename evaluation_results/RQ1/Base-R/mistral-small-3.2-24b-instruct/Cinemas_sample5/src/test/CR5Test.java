import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;

public class CR5Test {
    private Cinema cinema;
    private Film film1;
    private Film film2;
    private Room room1;
    private Room room2;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        
        film1 = new Film();
        film1.setFilmId("F1");
        
        film2 = new Film();
        film2.setFilmId("F2");
        
        room1 = new Room();
        room1.setRoomId("R1");
        
        room2 = new Room();
        room2.setRoomId("R2");
    }
    
    @Test
    public void testCase1_validFutureScreeningAssignment() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" (current time: "2024-12-01 10:00:00")
        boolean result = cinema.assignScreening(film1, room1, "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        
        // Expected Output: true
        assertTrue("Screening should be assigned successfully", result);
    }
    
    @Test
    public void testCase2_assignToAlreadyBookedRoom() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // First assignment
        cinema.assignScreening(film1, room1, "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" again
        boolean result = cinema.assignScreening(film1, room1, "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        
        // Expected Output: false
        assertFalse("Should not assign screening to already booked room", result);
    }
    
    @Test
    public void testCase3_assignWithNonExistentFilm() throws ParseException {
        // Setup: Create Cinema C1, Add Room R1 to C1
        cinema.addRoom(room1);
        
        // Action: Assign screening for Film F2 (not in cinema) in Room R1 at "2024-12-02 14:00:00"
        boolean result = cinema.assignScreening(film2, room1, "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        
        // Expected Output: false
        assertFalse("Should not assign screening for non-existent film", result);
    }
    
    @Test
    public void testCase4_assignScreeningAtCurrentTimeBoundary() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-01 10:00:00" (current time: "2024-12-01 10:00:00")
        boolean result = cinema.assignScreening(film1, room1, "2024-12-01 10:00:00", "2024-12-01 10:00:00");
        
        // Expected Output: false
        assertFalse("Should not assign screening when current time equals screening time", result);
    }
    
    @Test
    public void testCase5_assignScreeningInPastTime() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-11-30 14:00:00" (current time: "2024-12-01 10:00:00")
        boolean result = cinema.assignScreening(film1, room1, "2024-11-30 14:00:00", "2024-12-01 10:00:00");
        
        // Expected Output: false
        assertFalse("Should not assign screening for past time", result);
    }
    
    @Test
    public void testCase6_assignToNonExistentRoom() throws ParseException {
        // Setup: Create Cinema C1, Add Film F1 to C1
        cinema.addFilm(film1);
        
        // Action: Assign screening for Film F1 in Room R2 (not in cinema) at "2024-12-02 14:00:00"
        boolean result = cinema.assignScreening(film1, room2, "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        
        // Expected Output: false
        assertFalse("Should not assign screening to non-existent room", result);
    }
}