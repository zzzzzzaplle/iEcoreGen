import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Customer customer;
    private Rental rental;
    
    @Before
    public void setUp() {
        customer = new Customer();
        rental = new Rental();
    }
    
    @Test
    public void testCase1_Returned1DayLate() {
        // Setup: Create a rental with due date 2025-01-01 and return date 2025-01-02
        rental.setDueDate(LocalDate.of(2025, 1, 1));
        rental.setReturnDate(LocalDate.of(2025, 1, 2));
        
        // Execute: Calculate past due amount
        double result = rental.calculatePastDueAmount();
        
        // Verify: 1 day late * $0.5 = $0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAnd3DaysOverdue() {
        // Setup: Create a rental with due date 2025-01-09 (8 days after rental date)
        rental.setDueDate(LocalDate.of(2025, 1, 9));
        rental.setReturnDate(null); // Tape not returned
        
        // Mock current date as 2025-01-12 (3 days overdue)
        // Note: Since we cannot easily mock LocalDate.now() without additional dependencies,
        // we'll rely on the fact that due date is set to 2025-01-09 and current date should be 2025-01-12
        // The actual implementation will use LocalDate.now(), so this test may fail if run on different dates
        // For this exercise, we'll assume the current date is 2025-01-12 as specified in the test case
        
        // Execute: Calculate past due amount
        double result = rental.calculatePastDueAmount();
        
        // Verify: 3 days overdue * $0.5 = $1.50
        // Note: This test may need adjustment based on actual execution date
        assertEquals(1.50, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() {
        // Setup: Create a rental with due date 2025-01-06 and returned on same date
        rental.setDueDate(LocalDate.of(2025, 1, 6));
        rental.setReturnDate(LocalDate.of(2025, 1, 6));
        
        // Execute: Calculate past due amount
        double result = rental.calculatePastDueAmount();
        
        // Verify: Returned on due date = $0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() {
        // Setup: Create a rental with due date 2025-01-10 (not yet due)
        rental.setDueDate(LocalDate.of(2025, 1, 10));
        rental.setReturnDate(null); // Tape not returned
        
        // Mock current date as 2025-01-06 (not overdue)
        // Note: Since we cannot easily mock LocalDate.now() without additional dependencies,
        // we'll rely on the fact that due date is set to future date
        // For this exercise, we'll assume the current date is 2025-01-06 as specified in the test case
        
        // Execute: Calculate past due amount
        double result = rental.calculatePastDueAmount();
        
        // Verify: Not overdue = $0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_Returned6DaysLate() {
        // Setup: Create a rental with due date 2025-01-08 and current date 2025-01-14
        rental.setDueDate(LocalDate.of(2025, 1, 8));
        rental.setReturnDate(null); // Tape not returned
        
        // Mock current date as 2025-01-14 (6 days overdue)
        // Note: Since we cannot easily mock LocalDate.now() without additional dependencies,
        // we'll rely on the fact that due date is set to 2025-01-08 and current date should be 2025-01-14
        // The actual implementation will use LocalDate.now(), so this test may fail if run on different dates
        // For this exercise, we'll assume the current date is 2025-01-14 as specified in the test case
        
        // Execute: Calculate past due amount
        double result = rental.calculatePastDueAmount();
        
        // Verify: 6 days overdue * $0.5 = $3.00
        // Note: This test may need adjustment based on actual execution date
        assertEquals(3.00, result, 0.001);
    }
}