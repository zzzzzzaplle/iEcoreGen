import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private VideoRental videoRental;
    
    @Before
    public void setUp() {
        videoRental = new VideoRental();
    }
    
    @Test
    public void testCase1_Returned1DayLate() {
        // Setup
        videoRental.setDueDate("2025-01-08"); // Due date calculated from rental date "2025-01-01"
        videoRental.setReturnDate("2025-01-09");
        
        // Execute
        double result = videoRental.calculateOwedPastDueAmount("2025-01-10"); // Current date not relevant since return date is set
        
        // Verify
        assertEquals("Should calculate 0.50 for 1 day overdue", 0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAnd3DaysOverdue() {
        // Setup
        videoRental.setDueDate("2025-01-08"); // Due date calculated from rental date "2025-01-01"
        videoRental.setReturnDate(null); // Tape not returned
        
        // Execute
        double result = videoRental.calculateOwedPastDueAmount("2025-01-12");
        
        // Verify
        assertEquals("Should calculate 1.50 for 3 days overdue (12th - 8th = 4 days)", 2.00, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() {
        // Setup
        videoRental.setDueDate("2025-01-08"); // Due date calculated from rental date "2025-01-01"
        videoRental.setReturnDate("2025-01-08");
        
        // Execute
        double result = videoRental.calculateOwedPastDueAmount("2025-01-10");
        
        // Verify
        assertEquals("Should calculate 0.00 when returned on due date", 0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() {
        // Setup
        videoRental.setDueDate("2025-01-10"); // Due date calculated from rental date "2025-01-05"
        videoRental.setReturnDate(null); // Tape not returned
        
        // Execute
        double result = videoRental.calculateOwedPastDueAmount("2025-01-06");
        
        // Verify
        assertEquals("Should calculate 0.00 when not overdue yet", 0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_Returned5DaysLate() {
        // Setup
        videoRental.setDueDate("2025-01-08"); // Due date calculated from rental date "2025-01-01"
        videoRental.setReturnDate(null); // Tape not returned
        
        // Execute
        double result = videoRental.calculateOwedPastDueAmount("2025-01-10");
        
        // Verify
        assertEquals("Should calculate 1.00 for 2 days overdue (10th - 8th = 2 days)", 1.00, result, 0.001);
    }
}