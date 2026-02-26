import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR1Test {
    
    private Rental rental;
    
    @Before
    public void setUp() {
        // Create a fresh rental object before each test
        rental = new Rental();
    }
    
    @Test
    public void testCase1_ReturnedOneDayLate() {
        // Setup: Create rental with return date 1 day after due date
        rental.setDueDate(LocalDate.of(2025, 1, 1));
        rental.setReturnDate(LocalDate.of(2025, 1, 2));
        
        // Execute: Calculate past due amount
        double result = rental.calculatePastDueAmount();
        
        // Verify: 1 day overdue * $0.5 = $0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() {
        // Setup: Create rental that is 3 days overdue (current date is 2025-01-12, due date is 2025-01-09)
        rental.setDueDate(LocalDate.of(2025, 1, 9));
        rental.setReturnDate(null); // Not returned
        
        // Mock current date for testing
        LocalDate originalNow = LocalDate.now();
        try {
            // Set fixed current date for testing
            java.lang.reflect.Field field = LocalDate.class.getDeclaredField("NOW");
            field.setAccessible(true);
            field.set(null, LocalDate.of(2025, 1, 12));
            
            // Execute: Calculate past due amount
            double result = rental.calculatePastDueAmount();
            
            // Verify: 3 days overdue * $0.5 = $1.50
            assertEquals(1.50, result, 0.001);
        } catch (Exception e) {
            fail("Exception occurred during test: " + e.getMessage());
        } finally {
            // Reset current date
            try {
                java.lang.reflect.Field field = LocalDate.class.getDeclaredField("NOW");
                field.setAccessible(true);
                field.set(null, originalNow);
            } catch (Exception e) {
                // Ignore reset failure
            }
        }
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() {
        // Setup: Create rental returned exactly on due date
        rental.setDueDate(LocalDate.of(2025, 1, 6));
        rental.setReturnDate(LocalDate.of(2025, 1, 6));
        
        // Execute: Calculate past due amount
        double result = rental.calculatePastDueAmount();
        
        // Verify: Returned on due date = $0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() {
        // Setup: Create rental that is not returned but not yet overdue
        rental.setDueDate(LocalDate.of(2025, 1, 10));
        rental.setReturnDate(null); // Not returned
        
        // Mock current date for testing (current date before due date)
        LocalDate originalNow = LocalDate.now();
        try {
            // Set fixed current date for testing
            java.lang.reflect.Field field = LocalDate.class.getDeclaredField("NOW");
            field.setAccessible(true);
            field.set(null, LocalDate.of(2025, 1, 6));
            
            // Execute: Calculate past due amount
            double result = rental.calculatePastDueAmount();
            
            // Verify: Not overdue = $0.00
            assertEquals(0.00, result, 0.001);
        } catch (Exception e) {
            fail("Exception occurred during test: " + e.getMessage());
        } finally {
            // Reset current date
            try {
                java.lang.reflect.Field field = LocalDate.class.getDeclaredField("NOW");
                field.setAccessible(true);
                field.set(null, originalNow);
            } catch (Exception e) {
                // Ignore reset failure
            }
        }
    }
    
    @Test
    public void testCase5_ReturnedFiveDaysLate() {
        // Setup: Create rental that is 6 days overdue (current date is 2025-01-10, due date is 2025-01-04)
        rental.setDueDate(LocalDate.of(2025, 1, 4));
        rental.setReturnDate(null); // Not returned
        
        // Mock current date for testing
        LocalDate originalNow = LocalDate.now();
        try {
            // Set fixed current date for testing
            java.lang.reflect.Field field = LocalDate.class.getDeclaredField("NOW");
            field.setAccessible(true);
            field.set(null, LocalDate.of(2025, 1, 10));
            
            // Execute: Calculate past due amount
            double result = rental.calculatePastDueAmount();
            
            // Verify: 6 days overdue * $0.5 = $3.00
            assertEquals(3.00, result, 0.001);
        } catch (Exception e) {
            fail("Exception occurred during test: " + e.getMessage());
        } finally {
            // Reset current date
            try {
                java.lang.reflect.Field field = LocalDate.class.getDeclaredField("NOW");
                field.setAccessible(true);
                field.set(null, originalNow);
            } catch (Exception e) {
                // Ignore reset failure
            }
        }
    }
}