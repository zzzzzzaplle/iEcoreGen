import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;

public class CR5Test {
    
    private Cinema cinema;
    private Film filmF1;
    private Room roomR1;
    
    @Before
    public void setUp() {
        cinema = new Cinema();
        filmF1 = new Film();
        filmF1.setTitle("F1");
        roomR1 = new Room();
        roomR1.setName("R1");
    }
    
    @Test
    public void testCase1_validFutureScreeningAssignment() {
        // Setup
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        // Action
        boolean result = cinema.assignScreening("F1", "R1", "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        
        // Verify
        assertTrue("Screening should be assigned successfully", result);
        assertEquals("Should have exactly 1 screening", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase2_assignToAlreadyBookedRoom() {
        // Setup
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        cinema.assignScreening("F1", "R1", "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        
        // Action
        boolean result = cinema.assignScreening("F1", "R1", "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        
        // Verify
        assertFalse("Should not assign screening to already booked room", result);
        assertEquals("Should still have only 1 screening", 1, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase3_assignWithNonExistentFilm() {
        // Setup
        cinema.addRoom(roomR1);
        
        // Action
        boolean result = cinema.assignScreening("F2", "R1", "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        
        // Verify
        assertFalse("Should not assign screening for non-existent film", result);
        assertEquals("Should have no screenings", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase4_assignScreeningAtCurrentTimeBoundary() {
        // Setup
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        // Action
        boolean result = cinema.assignScreening("F1", "R1", "2024-12-01 10:00:00", "2024-12-01 10:00:00");
        
        // Verify
        assertFalse("Should not assign screening at current time boundary", result);
        assertEquals("Should have no screenings", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase5_assignScreeningInPastTime() {
        // Setup
        cinema.addFilm(filmF1);
        cinema.addRoom(roomR1);
        
        // Action
        boolean result = cinema.assignScreening("F1", "R1", "2024-11-30 14:00:00", "2024-12-01 10:00:00");
        
        // Verify
        assertFalse("Should not assign screening in past time", result);
        assertEquals("Should have no screenings", 0, cinema.getScreenings().size());
    }
    
    @Test
    public void testCase6_assignToNonExistentRoom() {
        // Setup
        cinema.addFilm(filmF1);
        
        // Action
        boolean result = cinema.assignScreening("F1", "R2", "2024-12-02 14:00:00", "2024-12-01 10:00:00");
        
        // Verify
        assertFalse("Should not assign screening to non-existent room", result);
        assertEquals("Should have no screenings", 0, cinema.getScreenings().size());
    }
}