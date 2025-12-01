import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CR1Test {
    
    private VideoRental rental;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    @Before
    public void setUp() {
        // Clear static rentals list before each test to ensure isolation
        // Note: This requires access to the static ALL_RENTALS field which isn't exposed
        // We'll create fresh rentals in each test instead
    }
    
    @Test
    public void testCase1_ReturnedOneDayLate() {
        // Setup
        Tape tape = new Tape("T001", "Test Movie");
        LocalDate dueDate = LocalDate.parse("2025-01-01", DATE_FORMATTER);
        LocalDate returnDate = LocalDate.parse("2025-01-02", DATE_FORMATTER);
        LocalDate currentDate = LocalDate.parse("2025-01-10", DATE_FORMATTER); // Arbitrary current date
        
        rental = new VideoRental(dueDate, returnDate, 0.0, tape);
        
        // Execute
        double result = rental.calculateOwnedPastDueAmount(currentDate);
        
        // Verify
        assertEquals("Returned 1 day late should result in $0.50 fee", 0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() {
        // Setup
        Tape tape = new Tape("T002", "Test Movie 2");
        LocalDate dueDate = LocalDate.parse("2025-01-01", DATE_FORMATTER);
        LocalDate returnDate = null; // Not returned
        LocalDate currentDate = LocalDate.parse("2025-01-04", DATE_FORMATTER); // 3 days overdue
        
        rental = new VideoRental(dueDate, returnDate, 0.0, tape);
        
        // Execute
        double result = rental.calculateOwnedPastDueAmount(currentDate);
        
        // Verify
        assertEquals("Unreturned and 3 days overdue should result in $1.50 fee", 1.50, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() {
        // Setup
        Tape tape = new Tape("T003", "Test Movie 3");
        LocalDate dueDate = LocalDate.parse("2025-01-01", DATE_FORMATTER);
        LocalDate returnDate = LocalDate.parse("2025-01-01", DATE_FORMATTER); // Returned on due date
        LocalDate currentDate = LocalDate.parse("2025-01-10", DATE_FORMATTER); // Arbitrary current date
        
        rental = new VideoRental(dueDate, returnDate, 0.0, tape);
        
        // Execute
        double result = rental.calculateOwnedPastDueAmount(currentDate);
        
        // Verify
        assertEquals("Returned on due date should result in $0.00 fee", 0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() {
        // Setup
        Tape tape = new Tape("T004", "Test Movie 4");
        LocalDate dueDate = LocalDate.parse("2025-01-10", DATE_FORMATTER);
        LocalDate returnDate = null; // Not returned
        LocalDate currentDate = LocalDate.parse("2025-01-05", DATE_FORMATTER); // 5 days before due date
        
        rental = new VideoRental(dueDate, returnDate, 0.0, tape);
        
        // Execute
        double result = rental.calculateOwnedPastDueAmount(currentDate);
        
        // Verify
        assertEquals("Unreturned but not overdue should result in $0.00 fee", 0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_ReturnedFivePointFiveDaysLateRoundsToSix() {
        // Setup
        Tape tape = new Tape("T005", "Test Movie 5");
        LocalDate dueDate = LocalDate.parse("2025-01-01", DATE_FORMATTER);
        LocalDate returnDate = LocalDate.parse("2025-01-07", DATE_FORMATTER); // 6 days late (Jan 1 to Jan 7 = 6 days)
        LocalDate currentDate = LocalDate.parse("2025-01-10", DATE_FORMATTER); // Arbitrary current date
        
        rental = new VideoRental(dueDate, returnDate, 0.0, tape);
        
        // Execute
        double result = rental.calculateOwnedPastDueAmount(currentDate);
        
        // Verify
        assertEquals("Returned 6 days late should result in $3.00 fee", 3.00, result, 0.001);
    }
}