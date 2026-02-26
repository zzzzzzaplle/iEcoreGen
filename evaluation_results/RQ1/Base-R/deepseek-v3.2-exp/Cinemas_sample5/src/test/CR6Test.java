import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR6Test {
    private Cinema cinema;
    private Film film1;
    private Room room1;
    private Screening screening1;

    @Before
    public void setUp() {
        cinema = new Cinema();
        film1 = new Film("Film1", 120);
        room1 = new Room("Room1", 100);
    }

    @Test
    public void testCase1_cancelFutureScreening() {
        // Setup: Create Cinema C1, add Film1, add Room1, assign Screening1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        String screeningTime = "2024-10-03 12:00:00";
        String currentTimeForAssignment = "2024-10-02 11:00:00";
        cinema.assignScreening(film1, room1, screeningTime, currentTimeForAssignment);
        
        // Verify screening was added
        assertEquals(1, cinema.getScreenings().size());
        screening1 = cinema.getScreenings().get(0);
        
        // Action: cancel Screening1 at "2024-10-02 12:00:00"
        String currentTimeForCancellation = "2024-10-02 12:00:00";
        boolean result = cinema.cancelScreening(screening1, currentTimeForCancellation);
        
        // Expected Output: true, there is no screening in the cinema C1
        assertTrue(result);
        assertEquals(0, cinema.getScreenings().size());
    }

    @Test
    public void testCase2_cancelNonExistentScreening() {
        // Setup: Create Cinema C1, add Film1, add Room1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        // Create a screening that is NOT added to the cinema
        LocalDateTime screeningTime = LocalDateTime.parse("2024-10-02 12:05:00", 
            java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Screening screening2 = new Screening(film1, room1, screeningTime);
        
        // Action: cancel Screening2 at current time "2024-10-03 12:05:00"
        String currentTime = "2024-10-03 12:05:00";
        boolean result = cinema.cancelScreening(screening2, currentTime);
        
        // Expected Output: false
        assertFalse(result);
        assertEquals(0, cinema.getScreenings().size());
    }

    @Test
    public void testCase3_cancelAtExactScreeningTimeBoundary() {
        // Setup: Create Cinema C1, add Film F1, add Room R1, assign screening S1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        String screeningTime = "2024-12-03 10:00:00";
        String currentTimeForAssignment = "2024-12-02 10:00:00";
        cinema.assignScreening(film1, room1, screeningTime, currentTimeForAssignment);
        
        // Verify screening was added
        assertEquals(1, cinema.getScreenings().size());
        screening1 = cinema.getScreenings().get(0);
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        String currentTimeForCancellation = "2024-12-03 10:00:00";
        boolean result = cinema.cancelScreening(screening1, currentTimeForCancellation);
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse(result);
        assertEquals(1, cinema.getScreenings().size());
    }

    @Test
    public void testCase4_cancelAlreadyCancelledScreening() {
        // Setup: Create Cinema C1, add Film1, add Room1, assign Screening1 then cancel it
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        String screeningTime = "2024-10-03 12:00:00";
        String currentTimeForAssignment = "2024-10-02 11:00:00";
        cinema.assignScreening(film1, room1, screeningTime, currentTimeForAssignment);
        
        // Verify screening was added
        assertEquals(1, cinema.getScreenings().size());
        screening1 = cinema.getScreenings().get(0);
        
        // First cancellation - should succeed
        String firstCancellationTime = "2024-10-02 12:00:00";
        boolean firstResult = cinema.cancelScreening(screening1, firstCancellationTime);
        assertTrue(firstResult);
        assertEquals(0, cinema.getScreenings().size());
        
        // Action: cancel Screening1 again at current time "2024-10-02 12:05:00"
        String secondCancellationTime = "2024-10-02 12:05:00";
        boolean secondResult = cinema.cancelScreening(screening1, secondCancellationTime);
        
        // Expected Output: false, there is no screening in the cinema
        assertFalse(secondResult);
        assertEquals(0, cinema.getScreenings().size());
    }

    @Test
    public void testCase5_cancelPastScreening() {
        // Setup: Create Cinema C1, add Film F1, add Room R1, assign screening S1
        cinema.addFilm(film1);
        cinema.addRoom(room1);
        
        String screeningTime = "2024-12-01 14:00:00";
        String currentTimeForAssignment = "2024-11-30 10:00:00";
        cinema.assignScreening(film1, room1, screeningTime, currentTimeForAssignment);
        
        // Verify screening was added
        assertEquals(1, cinema.getScreenings().size());
        screening1 = cinema.getScreenings().get(0);
        
        // Action: Cancel S1 at current time "2024-12-03 10:00:00"
        String currentTimeForCancellation = "2024-12-03 10:00:00";
        boolean result = cinema.cancelScreening(screening1, currentTimeForCancellation);
        
        // Expected Output: false, there is 1 screening in the cinema
        assertFalse(result);
        assertEquals(1, cinema.getScreenings().size());
    }
}