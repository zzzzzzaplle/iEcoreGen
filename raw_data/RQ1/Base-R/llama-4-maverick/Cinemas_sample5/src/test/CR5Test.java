import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CR5Test {
    private Cinema cinema;
    private Film film1;
    private Film film2;
    private Room room1;
    private Room room2;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        cinema = new Cinema();
        film1 = new Film("F1");
        film2 = new Film("F2");
        room1 = new Room("R1");
        room2 = new Room("R2");
    }
    
    @Test
    public void testCase1_validFutureScreeningAssignment() {
        // Setup: Create Cinema C1, add Film F1 to C1, add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" (current time: "2024-12-01 10:00:00")
        boolean result = cinema.assignScreening(film1, room1, "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        
        // Expected Output: true
        assertTrue("Valid future screening should be assigned successfully", result);
        
        // Verify screening was actually added
        List<Screening> screenings = room1.getScreenings();
        assertEquals("Room should have one screening", 1, screenings.size());
        Screening screening = screenings.get(0);
        assertEquals("Screening film should match", film1, screening.getFilm());
        assertEquals("Screening time should match", 
                     LocalDateTime.parse("2024-12-02 14:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                     screening.getScreeningTime());
    }
    
    @Test
    public void testCase2_assignToAlreadyBookedRoom() {
        // Setup: Create Cinema C1, add Film F1 to C1, add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // First assignment should succeed
        boolean firstAssignment = cinema.assignScreening(film1, room1, "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        assertTrue("First screening assignment should succeed", firstAssignment);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" again
        boolean secondAssignment = cinema.assignScreening(film1, room1, "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        
        // Expected Output: false
        assertFalse("Assigning to already booked room should fail", secondAssignment);
        
        // Verify only one screening exists
        assertEquals("Room should have only one screening", 1, room1.getScreenings().size());
    }
    
    @Test
    public void testCase3_assignWithNonExistentFilm() {
        // Setup: Create Cinema C1, add Room R1 to C1 (but NOT add Film F2)
        cinema.addRoom(room1);
        
        // Action: Assign screening for Film F2 (not in cinema) in Room R1 at "2024-12-02 14:00:00"
        boolean result = cinema.assignScreening(film2, room1, "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        
        // Expected Output: false
        assertFalse("Assigning with non-existent film should fail", result);
        
        // Verify no screenings were added
        assertEquals("Room should have no screenings", 0, room1.getScreenings().size());
    }
    
    @Test
    public void testCase4_assignScreeningAtCurrentTimeBoundary() {
        // Setup: Create Cinema C1, add Film F1 to C1, add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-12-01 10:00:00" (current time: "2024-12-01 10:00:00")
        boolean result = cinema.assignScreening(film1, room1, "2024-12-01 10:00:00", "2024-12-01 10:00:00");
        
        // Expected Output: false
        assertFalse("Assigning screening at current time boundary should fail", result);
        
        // Verify no screenings were added
        assertEquals("Room should have no screenings", 0, room1.getScreenings().size());
    }
    
    @Test
    public void testCase5_assignScreeningInPastTime() {
        // Setup: Create Cinema C1, add Film F1 to C1, add Room R1 to C1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: Assign screening for Film F1 in Room R1 at "2024-11-30 14:00:00" (current time: "2024-12-01 10:00:00")
        boolean result = cinema.assignScreening(film1, room1, "2024-11-30 14:00:00", "2024-12-01 10:00:00");
        
        // Expected Output: false
        assertFalse("Assigning screening in past time should fail", result);
        
        // Verify no screenings were added
        assertEquals("Room should have no screenings", 0, room1.getScreenings().size());
    }
    
    @Test
    public void testCase6_assignToNonExistentRoom() {
        // Setup: Create Cinema C1, add Film F1 to C1 (but NOT add Room R2)
        cinema.addFilm(film1);
        
        // Action: Assign screening for Film F1 in Room R2 (not in cinema) at "2024-12-02 14:00:00"
        boolean result = cinema.assignScreening(film1, room2, "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        
        // Expected Output: false
        assertFalse("Assigning to non-existent room should fail", result);
    }
}