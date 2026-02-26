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
    public void testCase1_validFutureScreeningAssignment() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        
        // Action
        boolean result = cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Expected Output: true
        assertTrue("Valid future screening assignment should return true", result);
    }

    @Test
    public void testCase2_assignToAlreadyBookedRoom() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        Screening firstScreening = new Screening();
        firstScreening.setTime(screeningTime);
        cinema.assignScreening(film1, room1, firstScreening, currentTime);
        
        Screening secondScreening = new Screening();
        secondScreening.setTime(screeningTime);
        
        // Action
        boolean result = cinema.assignScreening(film1, room1, secondScreening, currentTime);
        
        // Expected Output: false
        assertFalse("Assigning to already booked room should return false", result);
    }

    @Test
    public void testCase3_assignWithNonExistentFilm() throws Exception {
        // Setup
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        
        // Action
        boolean result = cinema.assignScreening(film2, room1, screening, currentTime);
        
        // Expected Output: false
        assertFalse("Assigning with non-existent film should return false", result);
    }

    @Test
    public void testCase4_assignScreeningAtCurrentTimeBoundary() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-12-01 10:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        
        // Action
        boolean result = cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Expected Output: false
        assertFalse("Assigning screening at current time boundary should return false", result);
    }

    @Test
    public void testCase5_assignScreeningInPastTime() throws Exception {
        // Setup
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        Date screeningTime = dateFormat.parse("2024-11-30 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        
        // Action
        boolean result = cinema.assignScreening(film1, room1, screening, currentTime);
        
        // Expected Output: false
        assertFalse("Assigning screening in past time should return false", result);
    }

    @Test
    public void testCase6_assignToNonExistentRoom() throws Exception {
        // Setup
        cinema.addFilm(film1);
        
        Date screeningTime = dateFormat.parse("2024-12-02 14:00:00");
        Date currentTime = dateFormat.parse("2024-12-01 10:00:00");
        
        Screening screening = new Screening();
        screening.setTime(screeningTime);
        
        // Action
        boolean result = cinema.assignScreening(film1, room2, screening, currentTime);
        
        // Expected Output: false
        assertFalse("Assigning to non-existent room should return false", result);
    }
}