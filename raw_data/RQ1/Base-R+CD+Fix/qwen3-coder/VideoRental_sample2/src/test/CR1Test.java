import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

public class CR1Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        // Initialize date format for parsing test dates
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_Returned1DayLate() throws Exception {
        // Test Case 1: "Returned 1 day late"
        // Expected Output: 0.50 (1 day * $0.5)
        
        // Setup test data
        Date rentalDate = dateFormat.parse("2025-01-01");
        Date returnDate = dateFormat.parse("2025-01-02");
        Date dueDate = calculateDueDate(rentalDate);
        Date currentDate = dateFormat.parse("2025-01-09");
        
        // Create and configure video rental
        VideoRental rental = new VideoRental();
        rental.setDueDate(dueDate);
        rental.setReturnDate(returnDate);
        
        // Calculate past-due amount
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Verify result
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAnd3DaysOverdue() throws Exception {
        // Test Case 2: "Unreturned and 3 days overdue"
        // Expected Output: 1.50 (3 days * $0.5)
        
        // Setup test data
        Date rentalDate = dateFormat.parse("2025-01-01");
        Date dueDate = calculateDueDate(rentalDate);
        Date currentDate = dateFormat.parse("2025-01-12");
        
        // Create and configure video rental (not returned)
        VideoRental rental = new VideoRental();
        rental.setDueDate(dueDate);
        // returnDate remains null
        
        // Calculate past-due amount
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Verify result
        assertEquals(1.50, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() throws Exception {
        // Test Case 3: "Returned on due date"
        // Expected Output: 0.00
        
        // Setup test data
        Date rentalDate = dateFormat.parse("2025-01-01");
        Date dueDate = calculateDueDate(rentalDate);
        Date returnDate = dueDate; // Returned on due date
        Date currentDate = dateFormat.parse("2025-01-10");
        
        // Create and configure video rental
        VideoRental rental = new VideoRental();
        rental.setDueDate(dueDate);
        rental.setReturnDate(returnDate);
        
        // Calculate past-due amount
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Verify result
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() throws Exception {
        // Test Case 4: "Unreturned but not overdue"
        // Expected Output: 0.00
        
        // Setup test data
        Date rentalDate = dateFormat.parse("2025-01-05");
        Date dueDate = calculateDueDate(rentalDate);
        Date currentDate = dateFormat.parse("2025-01-06");
        
        // Create and configure video rental (not returned)
        VideoRental rental = new VideoRental();
        rental.setDueDate(dueDate);
        // returnDate remains null
        
        // Calculate past-due amount
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Verify result
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_Returned5DaysLate() throws Exception {
        // Test Case 5: "Returned 5 days late"
        // Expected Output: 3.00 (6 days * $0.5)
        
        // Setup test data
        Date rentalDate = dateFormat.parse("2025-01-01");
        Date dueDate = calculateDueDate(rentalDate);
        Date currentDate = dateFormat.parse("2025-01-10");
        
        // Create and configure video rental (not returned - using current date as comparison)
        VideoRental rental = new VideoRental();
        rental.setDueDate(dueDate);
        // returnDate remains null to simulate unreturned tape
        
        // Calculate past-due amount
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Verify result
        assertEquals(3.00, result, 0.001);
    }
    
    /**
     * Helper method to calculate due date (5 days after rental date)
     */
    private Date calculateDueDate(Date rentalDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(rentalDate);
        calendar.add(Calendar.DAY_OF_MONTH, 5);
        return calendar.getTime();
    }
}