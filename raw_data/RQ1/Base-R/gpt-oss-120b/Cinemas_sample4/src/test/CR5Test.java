import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private Cinema cinema;
    private Film filmF1;
    private Room roomR1;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        filmF1 = new Film("F1", 120);
        roomR1 = new Room("R1", 100);
    }
    
    @Test
    public void testCase1_validFutureScreeningAssignment() {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" (current time: "2024-12-01 10:00:00")
        boolean result = cinema.assignScreening("F1", "R1", "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        
        // Expected Output: true
        assertTrue("Screening should be assigned successfully", result);
        assertEquals("Should have exactly one screening", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_assignToAlreadyBookedRoom() {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        // Assign first screening for Film F1 in Room R1 at "2024-12-02 14:00:00"
        cinema.assignScreening("F1", "R1", "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" again
        boolean result = cinema.assignScreening("F1", "R1", "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        
        // Expected Output: false
        assertFalse("Should not assign screening to already booked room", result);
        assertEquals("Should still have only one screening", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase3_assignWithNonExistentFilm() {
        // Setup: Create Cinema C1, Add Room R1 to C1
        cinema.addRoom(roomR1);
        
        // Action: Assign screening for Film F2 (not in cinema) in Room R1 at "2024-12-02 14:00:00"
        boolean result = cinema.assignScreening("F2", "R1", "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        
        // Expected Output: false
        assertFalse("Should not assign screening with non-existent film", result);
        assertEquals("Should have no screenings", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_assignScreeningAtCurrentTimeBoundary() {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-01 10:00:00" (current time: "2024-12-01 10:00:00")
        boolean result = cinema.assignScreening("F1", "R1", "2024-12-01 10:00:00", "2024-12-01 10:00:00");
        
        // Expected Output: false
        assertFalse("Should not assign screening at current time boundary", result);
        assertEquals("Should have no screenings", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_assignScreeningInPastTime() {
        // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-11-30 14:00:00" (current time: "2024-12-01 10:00:00")
        boolean result = cinema.assignScreening("F1", "R1", "2024-11-30 14:00:00", "2024-12-01 10:00:00");
        
        // Expected Output: false
        assertFalse("Should not assign screening in past time", result);
        assertEquals("Should have no screenings", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase6_assignToNonExistentRoom() {
        // Setup: Create Cinema C1, Add Film F1 to C1
        cinema.addFilm(filmF1);
        
        // Action: Assign screening for Film F1 in Room R2 (not in cinema) at "2024-12-02 14:00:00"
        boolean result = cinema.assignScreening("F1", "R2", "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        
        // Expected Output: false
        assertFalse("Should not assign screening to non-existent room", result);
        assertEquals("Should have no screenings", 0, cinema.getScreenings().size());
    }
}