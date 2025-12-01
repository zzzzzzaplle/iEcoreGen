import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CR5Test {
    private Cinema cinema;
    private Film film1;
    private Film film2;
    private Room room1;
    private Room room2;
    private DateTimeFormatter formatter;

    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film("The Matrix", 136);
        film2 = new Film("Inception", 148);
        room1 = new Room("Room A", 100);
        room2 = new Room("Room B", 150);
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    public void testCase1_ValidFutureScreeningAssignment() {
        // Setup: Add Film F1 and Room R1 to cinema
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: Assign screening for Film F1 in Room R1 at future time
        boolean result = cinema.assignScreening(film1, room1, "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        
        // Verify: Screening should be assigned successfully
        assertTrue("Valid future screening should be assigned successfully", result);
        assertEquals("Cinema should have 1 screening", 1, cinema.getScreenings().size());
    }

    @Test
    public void testCase2_AssignToAlreadyBookedRoom() {
        // Setup: Add Film F1 and Room R1 to cinema
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // First assignment should succeed
        boolean firstAssignment = cinema.assignScreening(film1, room1, "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        assertTrue("First screening assignment should succeed", firstAssignment);
        
        // Action: Try to assign another screening at same time in same room
        boolean secondAssignment = cinema.assignScreening(film1, room1, "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        
        // Verify: Second assignment should fail due to room conflict
        assertFalse("Second screening assignment to same room at same time should fail", secondAssignment);
        assertEquals("Cinema should still have only 1 screening", 1, cinema.getScreenings().size());
    }

    @Test
    public void testCase3_AssignWithNonExistentFilm() {
        // Setup: Add only Room R1 to cinema (Film F2 not added)
        cinema.addRoom(room1);
        
        // Action: Try to assign screening with film not in cinema
        boolean result = cinema.assignScreening(film2, room1, "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        
        // Verify: Assignment should fail due to non-existent film
        assertFalse("Screening assignment with non-existent film should fail", result);
        assertEquals("Cinema should have no screenings", 0, cinema.getScreenings().size());
    }

    @Test
    public void testCase4_AssignScreeningAtCurrentTimeBoundary() {
        // Setup: Add Film F1 and Room R1 to cinema
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: Try to assign screening at exactly current time
        boolean result = cinema.assignScreening(film1, room1, "2024-12-01 10:00:00", "2024-12-01 10:00:00");
        
        // Verify: Assignment should fail due to screening time not being after current time
        assertFalse("Screening assignment at current time boundary should fail", result);
        assertEquals("Cinema should have no screenings", 0, cinema.getScreenings().size());
    }

    @Test
    public void testCase5_AssignScreeningInPastTime() {
        // Setup: Add Film F1 and Room R1 to cinema
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: Try to assign screening in the past
        boolean result = cinema.assignScreening(film1, room1, "2024-11-30 14:00:00", "2024-12-01 10:00:00");
        
        // Verify: Assignment should fail due to past screening time
        assertFalse("Screening assignment in past time should fail", result);
        assertEquals("Cinema should have no screenings", 0, cinema.getScreenings().size());
    }

    @Test
    public void testCase6_AssignToNonExistentRoom() {
        // Setup: Add only Film F1 to cinema (Room R2 not added)
        cinema.addFilm(film1);
        
        // Action: Try to assign screening to room not in cinema
        boolean result = cinema.assignScreening(film1, room2, "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        
        // Verify: Assignment should fail due to non-existent room
        assertFalse("Screening assignment to non-existent room should fail", result);
        assertEquals("Cinema should have no screenings", 0, cinema.getScreenings().size());
    }
}