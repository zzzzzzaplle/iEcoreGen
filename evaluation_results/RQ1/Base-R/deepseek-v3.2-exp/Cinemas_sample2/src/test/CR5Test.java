import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

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
        film1 = new Film("F1", "Test Film 1");
        film2 = new Film("F2", "Test Film 2");
        room1 = new Room("R1");
        room2 = new Room("R2");
    }
    
    @Test
    public void testCase1_ValidFutureScreeningAssignment() {
        // Setup: Add Film F1 to C1 and Room R1 to C1
        cinema.addFilm