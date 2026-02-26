import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;

public class CR5Test {
    private Cinema cinema;
    private Film film1;
    private Film film2;
    private Room room1;
    private Room room2;
    
    @Before
    public void setUp() {
        // Initialize fresh objects before each test
        cinema = new Cinema();
        film1 = new Film("Inception", 148, "Sci-Fi");
        film2 = new Film("The Godfather", 175, "Crime");
        room1 = new Room("Room A", 100);
        room2 = new Room("Room B", 150);
    }
    
    @Test
    public void testCase1_validFutureScreeningAssignment() {
        // Setup: Add film and room to cinema
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: Assign screening for future time
        boolean result = cinema.assignScreening(
            film1, 
            room1, 
            "2024-12-02 14:00:00", 
            "2024-12-01 10:00:00"
        );
        
        // Verify screening was assigned successfully
        assertTrue("Valid future screening should be assigned successfully", result);
        assertEquals("Should have exactly one screening", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_assignToAlreadyBookedRoom() {
        // Setup: Add film and room, then assign first screening
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        cinema.assignScreening(film1, room1, "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        
        // Action: Try to assign another screening at same time in same room
        boolean result = cinema.assignScreening(
            film1, 
            room1, 
            "2024-12-02 14:00:00", 
            "2024-12-01 10:00:00"
        );
        
        // Verify second assignment fails due to room conflict
        assertFalse("Should not assign screening to already booked room", result);
        assertEquals("Should still have only one screening", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase3_assignWithNonExistentFilm() {
        // Setup: Add room but not film
        cinema.addRoom(room1);
        
        // Action: Try to assign screening with film not in cinema
        boolean result = cinema.assignScreening(
            film2, // film2 not added to cinema
            room1, 
            "2024-12-02 14:00:00", 
            "2024-12-01 10:00:00"
        );
        
        // Verify assignment fails due to non-existent film
        assertFalse("Should not assign screening with non-existent film", result);
        assertTrue("Should have no screenings", cinema.getScreenings().isEmpty());
    }
    
    @Test
    public void testCase4_assignScreeningAtCurrentTimeBoundary() {
        // Setup: Add film and room to cinema
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: Try to assign screening at exactly current time
        boolean result = cinema.assignScreening(
            film1, 
            room1, 
            "2024-12-01 10:00:00", 
            "2024-12-01 10:00:00"
        );
        
        // Verify assignment fails when screening time equals current time
        assertFalse("Should not assign screening at current time boundary", result);
        assertTrue("Should have no screenings", cinema.getScreenings().isEmpty());
    }
    
    @Test
    public void testCase5_assignScreeningInPastTime() {
        // Setup: Add film and room to cinema
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Action: Try to assign screening in the past
        boolean result = cinema.assignScreening(
            film1, 
            room1, 
            "2024-11-30 14:00:00", 
            "2024-12-01 10:00:00"
        );
        
        // Verify assignment fails for past screening time
        assertFalse("Should not assign screening in past time", result);
        assertTrue("Should have no screenings", cinema.getScreenings().isEmpty());
    }
    
    @Test
    public void testCase6_assignToNonExistentRoom() {
        // Setup: Add film but not room
        cinema.addFilm(film1);
        
        // Action: Try to assign screening to room not in cinema
        boolean result = cinema.assignScreening(
            film1, 
            room2, // room2 not added to cinema
            "2024-12-02 14:00:00", 
            "2024-12-01 10:00:00"
        );
        
        // Verify assignment fails due to non-existent room
        assertFalse("Should not assign screening to non-existent room", result);
        assertTrue("Should have no screenings", cinema.getScreenings().isEmpty());
    }
}