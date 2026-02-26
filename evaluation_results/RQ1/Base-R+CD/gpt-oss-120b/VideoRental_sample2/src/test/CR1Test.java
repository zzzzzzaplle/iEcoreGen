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
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_ReturnedOneDayLate() {
        // Setup: Create VideoRental with due_date="2025-01-01"
        LocalDate dueDate = LocalDate.parse("2025-01-01", formatter);
        Tape tape = new Tape("V001", "Test Tape");
        videoRental = new VideoRental(dueDate, null, tape);
        
        // Set return_date="2025-01-02"
        LocalDate returnDate = LocalDate.parse("2025-01-02", formatter);
        videoRental.setReturnDate(returnDate);
        
        // Calculate past-due amount with current_date="2025-01-10" (not relevant since return date is set)
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        double result = videoRental.calculateOwnedPastDueAmount(currentDate);
        
        // Expected Output: 0.50 (1 day * $0.5)
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() {
        // Setup: Create VideoRental with due_date="2025-01-01"
        LocalDate dueDate = LocalDate.parse("2025-01-01", formatter);
        Tape tape = new Tape("V001", "Test Tape");
        videoRental = new VideoRental(dueDate, null, tape);
        
        // Keep return_date unset (null)
        // Calculate with current_date="2025-01-04"
        LocalDate currentDate = LocalDate.parse("2025-01-04", formatter);
        double result = videoRental.calculateOwnedPastDueAmount(currentDate);
        
        // Expected Output: 1.50 (3 days * $0.5)
        assertEquals(1.50, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() {
        // Setup: Create VideoRental with due_date="2025-01-01"
        LocalDate dueDate = LocalDate.parse("2025-01-01", formatter);
        Tape tape = new Tape("V001", "Test Tape");
        videoRental = new VideoRental(dueDate, null, tape);
        
        // Set return_date="2025-01-01"
        LocalDate returnDate = LocalDate.parse("2025-01-01", formatter);
        videoRental.setReturnDate(returnDate);
        
        // Calculate with current_date="2025-01-10" (not relevant since return date is set)
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        double result = videoRental.calculateOwnedPastDueAmount(currentDate);
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() {
        // Setup: Create VideoRental with due_date="2025-01-10"
        LocalDate dueDate = LocalDate.parse("2025-01-10", formatter);
        Tape tape = new Tape("V001", "Test Tape");
        videoRental = new VideoRental(dueDate, null, tape);
        
        // Keep return_date unset (null)
        // Calculate with current_date="2025-01-05"
        LocalDate currentDate = LocalDate.parse("2025-01-05", formatter);
        double result = videoRental.calculateOwnedPastDueAmount(currentDate);
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_ReturnedFivePointFiveDaysLateRoundsToSix() {
        // Setup: Create VideoRental with due_date="2025-01-01"
        LocalDate dueDate = LocalDate.parse("2025-01-01", formatter);
        Tape tape = new Tape("V001", "Test Tape");
        videoRental = new VideoRental(dueDate, null, tape);
        
        // Set return_date="2025-01-07" (partial day counts as full day - 6 days total)
        LocalDate returnDate = LocalDate.parse("2025-01-07", formatter);
        videoRental.setReturnDate(returnDate);
        
        // Calculate with current_date="2025-01-10" (not relevant since return date is set)
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        double result = videoRental.calculateOwnedPastDueAmount(currentDate);
        
        // Expected Output: 3.00 (6 days * $0.5)
        assertEquals(3.00, result, 0.001);
    }
}