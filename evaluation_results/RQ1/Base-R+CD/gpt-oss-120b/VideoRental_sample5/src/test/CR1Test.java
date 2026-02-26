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
        videoRental = new VideoRental();
    }
    
    @Test
    public void testCase1_ReturnedOneDayLate() {
        // Setup: Create VideoRental with due_date="2025-01-01"
        LocalDate dueDate = LocalDate.parse("2025-01-01", DATE_FORMATTER);
        videoRental.setDueDate(dueDate);
        
        // Set return_date="2025-01-02"
        LocalDate returnDate = LocalDate.parse("2025-01-02", DATE_FORMATTER);
        videoRental.setReturnDate(returnDate);
        
        // Calculate overdue amount with current date (not used when return date is set)
        LocalDate currentDate = LocalDate.parse("2025-01-10", DATE_FORMATTER);
        double result = videoRental.calculateOwnedPastDueAmount(currentDate);
        
        // Expected Output: 0.50 (1 day * $0.5)
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() {
        // Setup: Create VideoRental with due_date="2025-01-01"
        LocalDate dueDate = LocalDate.parse("2025-01-01", DATE_FORMATTER);
        videoRental.setDueDate(dueDate);
        
        // Keep return_date unset (null)
        videoRental.setReturnDate(null);
        
        // Calculate overdue amount with current_date="2025-01-04"
        LocalDate currentDate = LocalDate.parse("2025-01-04", DATE_FORMATTER);
        double result = videoRental.calculateOwnedPastDueAmount(currentDate);
        
        // Expected Output: 1.50 (3 days * $0.5)
        assertEquals(1.50, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() {
        // Setup: Create VideoRental with due_date="2025-01-01"
        LocalDate dueDate = LocalDate.parse("2025-01-01", DATE_FORMATTER);
        videoRental.setDueDate(dueDate);
        
        // Set return_date="2025-01-01"
        LocalDate returnDate = LocalDate.parse("2025-01-01", DATE_FORMATTER);
        videoRental.setReturnDate(returnDate);
        
        // Calculate overdue amount with current_date="2025-01-10"
        LocalDate currentDate = LocalDate.parse("2025-01-10", DATE_FORMATTER);
        double result = videoRental.calculateOwnedPastDueAmount(currentDate);
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() {
        // Setup: Create VideoRental with due_date="2025-01-10"
        LocalDate dueDate = LocalDate.parse("2025-01-10", DATE_FORMATTER);
        videoRental.setDueDate(dueDate);
        
        // Keep return_date unset (null)
        videoRental.setReturnDate(null);
        
        // Calculate overdue amount with current_date="2025-01-05"
        LocalDate currentDate = LocalDate.parse("2025-01-05", DATE_FORMATTER);
        double result = videoRental.calculateOwnedPastDueAmount(currentDate);
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_ReturnedFivePointFiveDaysLateRoundsToSix() {
        // Setup: Create VideoRental with due_date="2025-01-01"
        LocalDate dueDate = LocalDate.parse("2025-01-01", DATE_FORMATTER);
        videoRental.setDueDate(dueDate);
        
        // Set return_date="2025-01-07" (partial day counts as full day)
        LocalDate returnDate = LocalDate.parse("2025-01-07", DATE_FORMATTER);
        videoRental.setReturnDate(returnDate);
        
        // Calculate overdue amount with current_date="2025-01-10"
        LocalDate currentDate = LocalDate.parse("2025-01-10", DATE_FORMATTER);
        double result = videoRental.calculateOwnedPastDueAmount(currentDate);
        
        // Expected Output: 3.00 (6 days * $0.5)
        assertEquals(3.00, result, 0.001);
    }
}