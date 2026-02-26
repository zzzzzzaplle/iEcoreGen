import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR5Test {
    private Cinema cinema;
    private Film film1;
    private Film film2;
    private Room room1;
    private Room room2;
    private SimpleDateFormat dateFormat;

    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film();
        film2 = new Film();
        room1 = new Room();
        room2 = new Room();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    public void testCase1_validFutureScreeningAssignment() {
        try {
            // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
            cinema.addFilm(film1);
            cinema.addRoom(room1);
            
            // Create screening
            Screening screening = new Screening();
            Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
            screening.setTime(screeningTime);
            screening.setFilm(film1);
            screening.setRoom(room1);
            
            // Current time
            Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
            
            // Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" (current time: "2024-12-01 10:00:00")
            boolean result = cinema.assignScreening(film1, room1, screening, currentTime);
            
            // Expected Output: true
            assertTrue(result);
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testCase2_assignToAlreadyBookedRoom() {
        try {
            // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
            cinema.addFilm(film1);
            cinema.addRoom(room1);
            
            // Create first screening
            Screening screening1 = new Screening();
            Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
            screening1.setTime(screeningTime);
            screening1.setFilm(film1);
            screening1.setRoom(room1);
            
            // Current time
            Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
            
            // Assign first screening
            cinema.assignScreening(film1, room1, screening1, currentTime);
            
            // Create second screening with same time and room
            Screening screening2 = new Screening();
            screening2.setTime(screeningTime);
            screening2.setFilm(film1);
            screening2.setRoom(room1);
            
            // Action: Assign screening for Film F1 in Room R1 at "2024-12-02 14:00:00" again
            boolean result = cinema.assignScreening(film1, room1, screening2, currentTime);
            
            // Expected Output: false
            assertFalse(result);
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testCase3_assignWithNonExistentFilm() {
        try {
            // Setup: Create Cinema C1, Add Room R1 to C1
            cinema.addRoom(room1);
            
            // Create screening with non-existent film
            Screening screening = new Screening();
            Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
            screening.setTime(screeningTime);
            screening.setFilm(film2); // F2 not added to cinema
            screening.setRoom(room1);
            
            // Current time
            Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
            
            // Action: Assign screening for Film F2 (not in cinema) in Room R1 at "2024-12-02 14:00:00"
            boolean result = cinema.assignScreening(film2, room1, screening, currentTime);
            
            // Expected Output: false
            assertFalse(result);
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testCase4_assignScreeningAtCurrentTimeBoundary() {
        try {
            // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
            cinema.addFilm(film1);
            cinema.addRoom(room1);
            
            // Create screening
            Screening screening = new Screening();
            Date screeningTime = dateFormat.parse("2024-12-01 10:00:00");
            screening.setTime(screeningTime);
            screening.setFilm(film1);
            screening.setRoom(room1);
            
            // Current time same as screening time
            Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
            
            // Action: Assign screening for Film F1 in Room R1 at "2024-12-01 10:00:00" (current time: "2024-12-01 10:00:00")
            boolean result = cinema.assignScreening(film1, room1, screening, currentTime);
            
            // Expected Output: false
            assertFalse(result);
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testCase5_assignScreeningInPastTime() {
        try {
            // Setup: Create Cinema C1, Add Film F1 to C1, Add Room R1 to C1
            cinema.addFilm(film1);
            cinema.addRoom(room1);
            
            // Create screening
            Screening screening = new Screening();
            Date screeningTime = dateFormat.parse("2024-11-30 14:00:00");
            screening.setTime(screeningTime);
            screening.setFilm(film1);
            screening.setRoom(room1);
            
            // Current time after screening time
            Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
            
            // Action: Assign screening for Film F1 in Room R1 at "2024-11-30 14:00:00" (current time: "2024-12-01 10:00:00")
            boolean result = cinema.assignScreening(film1, room1, screening, currentTime);
            
            // Expected Output: false
            assertFalse(result);
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testCase6_assignToNonExistentRoom() {
        try {
            // Setup: Create Cinema C1, Add Film F1 to C1
            cinema.addFilm(film1);
            // Room R2 not added to cinema
            
            // Create screening
            Screening screening = new Screening();
            Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
            screening.setTime(screeningTime);
            screening.setFilm(film1);
            screening.setRoom(room2); // R2 not in cinema
            
            // Current time
            Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
            
            // Action: Assign screening for Film F1 in Room R2 (not in cinema) at "2024-12-02 14:00:00"
            boolean result = cinema.assignScreening(film1, room2, screening, currentTime);
            
            // Expected Output: false
            assertFalse(result);
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }
}