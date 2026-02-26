import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR1Test {
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_ReturnedOneDayLate() throws Exception {
        // Setup: Create a customer c1
        Customer c1 = new Customer();
        
        // Create video rental with rental date "2025-01-01"
        VideoRental rental = new VideoRental();
        Date rentalDate = dateFormat.parse("2025-01-01");
        
        // Set due date 3 days from rental date
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(rentalDate);
        calendar.add(java.util.Calendar.DAY_OF_MONTH, 3);
        Date dueDate = calendar.getTime();
        rental.setDueDate(dueDate);
        
        // Set return date to "2025-01-09" (1 day after due date of 2025-01-04)
        Date returnDate = dateFormat.parse("2025-01-09");
        rental.setReturnDate(returnDate);
        
        // Calculate overdue amount
        double result = rental.calculateOwedPastDueAmount(dateFormat.parse("2025-01-10"));
        
        // Expected Output: 0.50 (1 day * $0.5)
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() throws Exception {
        // Setup: Create a customer c2
        Customer c2 = new Customer();
        
        // Create video rental with rental date "2025-01-01"
        VideoRental rental = new VideoRental();
        Date rentalDate = dateFormat.parse("2025-01-01");
        
        // Set due date 3 days from rental date (due date = 2025-01-04)
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(rentalDate);
        calendar.add(java.util.Calendar.DAY_OF_MONTH, 3);
        Date dueDate = calendar.getTime();
        rental.setDueDate(dueDate);
        
        // Return date is null (unreturned)
        rental.setReturnDate(null);
        
        // Calculate overdue amount with current date "2025-01-12"
        Date currentDate = dateFormat.parse("2025-01-12");
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected Output: 1.50 (3 days * $0.5)
        assertEquals(1.50, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() throws Exception {
        // Setup: Create a customer c3
        Customer c3 = new Customer();
        
        // Create video rental with rental date "2025-01-01"
        VideoRental rental = new VideoRental();
        Date rentalDate = dateFormat.parse("2025-01-01");
        
        // Set due date 5 days from rental date (due date = 2025-01-06)
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(rentalDate);
        calendar.add(java.util.Calendar.DAY_OF_MONTH, 5);
        Date dueDate = calendar.getTime();
        rental.setDueDate(dueDate);
        
        // Set return date to "2025-01-06" (same as due date)
        Date returnDate = dateFormat.parse("2025-01-06");
        rental.setReturnDate(returnDate);
        
        // Calculate overdue amount with current date "2025-01-10"
        double result = rental.calculateOwedPastDueAmount(dateFormat.parse("2025-01-10"));
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() throws Exception {
        // Setup: Create a customer c4
        Customer c4 = new Customer();
        
        // Create video rental with rental date "2025-01-05"
        VideoRental rental = new VideoRental();
        Date rentalDate = dateFormat.parse("2025-01-05");
        
        // Set due date 5 days from rental date (due date = 2025-01-10)
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(rentalDate);
        calendar.add(java.util.Calendar.DAY_OF_MONTH, 5);
        Date dueDate = calendar.getTime();
        rental.setDueDate(dueDate);
        
        // Return date is null (unreturned)
        rental.setReturnDate(null);
        
        // Calculate overdue amount with current date "2025-01-06" (before due date)
        Date currentDate = dateFormat.parse("2025-01-06");
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_ReturnedFiveDaysLate() throws Exception {
        // Setup: Create a customer c5
        Customer c5 = new Customer();
        
        // Create video rental with rental date "2025-01-01"
        VideoRental rental = new VideoRental();
        Date rentalDate = dateFormat.parse("2025-01-01");
        
        // Set due date 7 days from rental date (due date = 2025-01-08)
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(rentalDate);
        calendar.add(java.util.Calendar.DAY_OF_MONTH, 7);
        Date dueDate = calendar.getTime();
        rental.setDueDate(dueDate);
        
        // Return date is null (unreturned)
        rental.setReturnDate(null);
        
        // Calculate overdue amount with current date "2025-01-10"
        Date currentDate = dateFormat.parse("2025-01-10");
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected Output: 3.00 (6 days * $0.5)
        assertEquals(3.00, result, 0.001);
    }
}