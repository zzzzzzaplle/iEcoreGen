import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CR1Test {
    
    private VideoRental videoRental;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        videoRental = new VideoRental();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_ReturnedOneDayLate() {
        // Setup: Create video rental with due date 2025-01-01 and return date 2025-01-02
        LocalDate dueDate = LocalDate.parse("2025-01-01", formatter);
        LocalDate returnDate = LocalDate.parse("2025-01-02", formatter);
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter); // Not used since return date is provided
        
        videoRental.setDueDate(dueDate);
        videoRental.setReturnDate(returnDate);
        
        // Calculate past-due amount
        double result = videoRental.calculateOwedPastDueAmount(currentDate);
        
        // Expected: 1 day * $0.5 = $0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() {
        // Setup: Create video rental with due date 2025-01-09, no return date, current date 2025-01-12
        LocalDate dueDate = LocalDate.parse("2025-01-09", formatter);
        LocalDate currentDate = LocalDate.parse("2025-01-12", formatter);
        
        videoRental.setDueDate(dueDate);
        videoRental.setReturnDate(null); // Not returned
        
        // Calculate past-due amount
        double result = videoRental.calculateOwedPastDueAmount(currentDate);
        
        // Expected: 3 days * $0.5 = $1.50
        assertEquals(1.50, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() {
        // Setup: Create video rental with due date 2025-01-06 and return date 2025-01-06
        LocalDate dueDate = LocalDate.parse("2025-01-06", formatter);
        LocalDate returnDate = LocalDate.parse("2025-01-06", formatter);
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter); // Not used since return date is provided
        
        videoRental.setDueDate(dueDate);
        videoRental.setReturnDate(returnDate);
        
        // Calculate past-due amount
        double result = videoRental.calculateOwedPastDueAmount(currentDate);
        
        // Expected: Returned on due date = $0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() {
        // Setup: Create video rental with due date 2025-01-10, no return date, current date 2025-01-06
        LocalDate dueDate = LocalDate.parse("2025-01-10", formatter);
        LocalDate currentDate = LocalDate.parse("2025-01-06", formatter);
        
        videoRental.setDueDate(dueDate);
        videoRental.setReturnDate(null); // Not returned
        
        // Calculate past-due amount
        double result = videoRental.calculateOwedPastDueAmount(currentDate);
        
        // Expected: Current date before due date = $0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_ReturnedSixDaysLate() {
        // Setup: Create video rental with due date 2025-01-04, no return date, current date 2025-01-10
        LocalDate dueDate = LocalDate.parse("2025-01-04", formatter);
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        
        videoRental.setDueDate(dueDate);
        videoRental.setReturnDate(null); // Not returned
        
        // Calculate past-due amount
        double result = videoRental.calculateOwedPastDueAmount(currentDate);
        
        // Expected: 6 days * $0.5 = $3.00
        assertEquals(3.00, result, 0.001);
    }
}