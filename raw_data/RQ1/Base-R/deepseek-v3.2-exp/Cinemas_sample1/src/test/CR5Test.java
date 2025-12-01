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
    
    @Before
    public void setUp() {
        // Initialize cinema and test objects before each test
        cinema = new Cinema();
        film1 = new Film("Movie A", 120, "Action");
        film2 = new Film("Movie B", 90, "Comedy");
        room1 = new Room("Room 1", 100);
        room2 = new Room("Room 2", 80);
    }
    
    @Test
    public void testCase1_ValidFutureScreeningAssignment() {
        // Setup: Add film and room to cinema
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: Assign screening for film1 in room1 at future time
        boolean result = cinema.assignScreening(film1, room1, "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        
        // Verify: Screening should be assigned successfully
        assertTrue("Valid future screening should be assigned successfully", result);
    }
    
    @Test
    public void testCase2_AssignToAlreadyBookedRoom() {
        // Setup: Add film and room to cinema, then assign first screening
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        
        // Action: Try to assign another screening at same time in same room
        boolean result = cinema.assignScreening(film1, room1, "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        
        // Verify: Second assignment should fail due to room conflict
        assertFalse("Should not assign screening to already booked room", result);
    }
    
    @Test
    public void testCase3_AssignWithNonExistentFilm() {
        // Setup: Add room but NOT the film
        cinema.addRoom(room1);
        
        // Action: Try to assign screening with film not in cinema
        boolean result = cinema.assignScreening(film2, room1, "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        
        // Verify: Assignment should fail due to non-existent film
        assertFalse("Should not assign screening with non-existent film", result);
    }
    
    @Test
    public void testCase4_AssignScreeningAtCurrentTimeBoundary() {
        // Setup: Add film and room to cinema
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: Try to assign screening at exact current time
        boolean result = cinema.assignScreening(film1, room1, "2024-12-01 10:00:00", "2024-12-01 10:00:00");
        
        // Verify: Assignment should fail as screening time equals current time
        assertFalse("Should not assign screening at current time boundary", result);
    }
    
    @Test
    public void testCase5_AssignScreeningInPastTime() {
        // Setup: Add film and room to cinema
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: Try to assign screening in the past
        boolean result = cinema.assignScreening(film1, room1, "2024-11-30 14:00:00", "2024-12-01 10:00:00");
        
        // Verify: Assignment should fail as screening time is in the past
        assertFalse("Should not assign screening in past time", result);
    }
    
    @Test
    public void testCase6_AssignToNonExistentRoom() {
        // Setup: Add film but NOT the room
        cinema.addFilm(film1);
        
        // Action: Try to assign screening to room not in cinema
        boolean result = cinema.assignScreening(film1, room2, "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        
        // Verify: Assignment should fail due to non-existent room
        assertFalse("Should not assign screening to non-existent room", result);
    }
}