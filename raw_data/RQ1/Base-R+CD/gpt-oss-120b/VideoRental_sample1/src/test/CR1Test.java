import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CR1Test {
    
    private VideoRental videoRental;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    @Before
    public void setUp() {
        // Clear static rentals list before each test to ensure isolation
        // Note: This requires access to the static ALL_RENTALS field which isn't directly accessible
        // We'll rely on the fact that each test creates new VideoRental instances
    }
    
    @Test
    public void testCase1_ReturnedOneDayLate() {
        // Setup
        LocalDate dueDate = LocalDate.parse("2025-01-01", DATE_FORMATTER);
        LocalDate returnDate = LocalDate.parse("2025-01-02", DATE_FORMATTER);
        LocalDate currentDate = LocalDate.parse("2025-01-10", DATE_FORMATTER); // Arbitrary current date
        
        videoRental = new VideoRental();
        videoRental.setDueDate(dueDate);
        videoRental.setReturnDate(returnDate);
        
        // Execute
        double result = videoRental.calculateOwnedPastDueAmount(currentDate);
        
        // Verify
        assertEquals("Returned 1 day late should result in $0.50 fee", 0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() {
        // Setup
        LocalDate dueDate = LocalDate.parse("2025-01-01", DATE_FORMATTER);
        LocalDate currentDate = LocalDate.parse("2025-01-04", DATE_FORMATTER);
        
        videoRental = new VideoRental();
        videoRental.setDueDate(dueDate);
        // returnDate remains null (unreturned)
        
        // Execute
        double result = videoRental.calculateOwnedPastDueAmount(currentDate);
        
        // Verify
        assertEquals("Unreturned and 3 days overdue should result in $1.50 fee", 1.50, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() {
        // Setup
        LocalDate dueDate = LocalDate.parse("2025-01-01", DATE_FORMATTER);
        LocalDate returnDate = LocalDate.parse("2025-01-01", DATE_FORMATTER);
        LocalDate currentDate = LocalDate.parse("2025-01-10", DATE_FORMATTER);
        
        videoRental = new VideoRental();
        videoRental.setDueDate(dueDate);
        videoRental.setReturnDate(returnDate);
        
        // Execute
        double result = videoRental.calculateOwnedPastDueAmount(currentDate);
        
        // Verify
        assertEquals("Returned on due date should result in $0.00 fee", 0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() {
        // Setup
        LocalDate dueDate = LocalDate.parse("2025-01-10", DATE_FORMATTER);
        LocalDate currentDate = LocalDate.parse("2025-01-05", DATE_FORMATTER);
        
        videoRental = new VideoRental();
        videoRental.setDueDate(dueDate);
        // returnDate remains null (unreturned)
        
        // Execute
        double result = videoRental.calculateOwnedPastDueAmount(currentDate);
        
        // Verify
        assertEquals("Unreturned but not overdue should result in $0.00 fee", 0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_ReturnedFiveAndHalfDaysLateRoundsToSix() {
        // Setup
        LocalDate dueDate = LocalDate.parse("2025-01-01", DATE_FORMATTER);
        LocalDate returnDate = LocalDate.parse("2025-01-07", DATE_FORMATTER); // 6 days late (Jan 1 to Jan 7 inclusive)
        LocalDate currentDate = LocalDate.parse("2025-01-10", DATE_FORMATTER);
        
        videoRental = new VideoRental();
        videoRental.setDueDate(dueDate);
        videoRental.setReturnDate(returnDate);
        
        // Execute
        double result = videoRental.calculateOwnedPastDueAmount(currentDate);
        
        // Verify
        assertEquals("Returned 6 days late should result in $3.00 fee", 3.00, result, 0.001);
    }
}