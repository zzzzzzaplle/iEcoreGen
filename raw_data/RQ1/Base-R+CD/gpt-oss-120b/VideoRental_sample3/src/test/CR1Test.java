import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CR1Test {
    
    @Test
    public void testCase1_ReturnedOneDayLate() {
        // Test Case 1: "Returned 1 day late"
        // Setup: Create VideoRental with due_date="2025-01-01"
        VideoRental rental = new VideoRental();
        rental.setDueDate(LocalDate.of(2025, 1, 1));
        
        // Set return_date="2025-01-02"
        rental.setReturnDate(LocalDate.of(2025, 1, 2));
        
        // Calculate overdue amount with current_date="2025-01-10" (not relevant since returned)
        double result = rental.calculateOwnedPastDueAmount(LocalDate.of(2025, 1, 10));
        
        // Expected Output: 0.50 (1 day * $0.5)
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() {
        // Test Case 2: "Unreturned and 3 days overdue"
        // Setup: Create VideoRental with due_date="2025-01-01"
        VideoRental rental = new VideoRental();
        rental.setDueDate(LocalDate.of(2025, 1, 1));
        
        // Keep return_date unset (null)
        rental.setReturnDate(null);
        
        // Calculate with current_date="2025-01-04"
        double result = rental.calculateOwnedPastDueAmount(LocalDate.of(2025, 1, 4));
        
        // Expected Output: 1.50 (3 days * $0.5)
        assertEquals(1.50, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() {
        // Test Case 3: "Returned on due date"
        // Setup: Create VideoRental with due_date="2025-01-01"
        VideoRental rental = new VideoRental();
        rental.setDueDate(LocalDate.of(2025, 1, 1));
        
        // Set return_date="2025-01-01"
        rental.setReturnDate(LocalDate.of(2025, 1, 1));
        
        // Calculate with current_date="2025-01-10" (not relevant since returned)
        double result = rental.calculateOwnedPastDueAmount(LocalDate.of(2025, 1, 10));
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() {
        // Test Case 4: "Unreturned but not overdue"
        // Setup: Create VideoRental with due_date="2025-01-10"
        VideoRental rental = new VideoRental();
        rental.setDueDate(LocalDate.of(2025, 1, 10));
        
        // Keep return_date unset (null)
        rental.setReturnDate(null);
        
        // Calculate with current_date="2025-01-05" (before due date)
        double result = rental.calculateOwnedPastDueAmount(LocalDate.of(2025, 1, 5));
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_ReturnedFivePointFiveDaysLateRoundsToSix() {
        // Test Case 5: "Returned 5.5 days late (rounds to 6)"
        // Setup: Create VideoRental with due_date="2025-01-01"
        VideoRental rental = new VideoRental();
        rental.setDueDate(LocalDate.of(2025, 1, 1));
        
        // Set return_date="2025-01-07" (6 days difference: Jan 1 to Jan 7 = 6 days)
        rental.setReturnDate(LocalDate.of(2025, 1, 7));
        
        // Calculate with current_date="2025-01-10" (not relevant since returned)
        double result = rental.calculateOwnedPastDueAmount(LocalDate.of(2025, 1, 10));
        
        // Expected Output: 3.00 (6 days * $0.5)
        assertEquals(3.00, result, 0.001);
    }
}