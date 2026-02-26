import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR1Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ReturnedOneDayLate() throws Exception {
        // Test Case 1: "Returned 1 day late"
        // Setup: Create a customer c1
        Customer c1 = new Customer();
        
        // Create video rental V001
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        rental.setTape(tape);
        
        // Set rental date "2025-01-01" - due date would be 3 days later: "2025-01-04"
        Date rentalDate = dateFormat.parse("2025-01-01 00:00:00");
        Date dueDate = dateFormat.parse("2025-01-04 00:00:00");
        rental.setDueDate(dueDate);
        
        // Set return date "2025-01-09" (5 days overdue from due date)
        Date returnDate = dateFormat.parse("2025-01-09 00:00:00");
        rental.setReturnDate(returnDate);
        
        c1.getRentals().add(rental);
        
        // Calculate past due amount with current date (not used since return date is set)
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected Output: 0.50 (1 day * $0.5) - but wait, there's a discrepancy
        // Due date: 2025-01-04, Return date: 2025-01-09 = 5 days overdue
        // 5 days * $0.5 = $2.50, but expected output says 0.50
        // Following the test specification strictly as requested
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() throws Exception {
        // Test Case 2: "Unreturned and 3 days overdue"
        // Setup: Create a customer c2
        Customer c2 = new Customer();
        
        // Create video rental V002
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        rental.setTape(tape);
        
        // Set rental date "2025-01-01" - due date would be 3 days later: "2025-01-04"
        Date rentalDate = dateFormat.parse("2025-01-01 00:00:00");
        Date dueDate = dateFormat.parse("2025-01-04 00:00:00");
        rental.setDueDate(dueDate);
        
        // Return date is null (unreturned)
        rental.setReturnDate(null);
        
        c2.getRentals().add(rental);
        
        // Current date "2025-01-12" - 8 days overdue from due date (2025-01-04)
        Date currentDate = dateFormat.parse("2025-01-12 00:00:00");
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected Output: 1.50 (3 days * $0.5) - but wait, there's a discrepancy
        // Due date: 2025-01-04, Current date: 2025-01-12 = 8 days overdue
        // 8 days * $0.5 = $4.00, but expected output says 1.50
        // Following the test specification strictly as requested
        assertEquals(1.50, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() throws Exception {
        // Test Case 3: "Returned on due date"
        // Setup: Create a customer c3
        Customer c3 = new Customer();
        
        // Create video rental V003
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        rental.setTape(tape);
        
        // Set rental date "2025-01-01" - due date would be 3 days later: "2025-01-04"
        Date rentalDate = dateFormat.parse("2025-01-01 00:00:00");
        Date dueDate = dateFormat.parse("2025-01-04 00:00:00");
        rental.setDueDate(dueDate);
        
        // Set return date "2025-01-06" (2 days overdue from due date)
        Date returnDate = dateFormat.parse("2025-01-06 00:00:00");
        rental.setReturnDate(returnDate);
        
        c3.getRentals().add(rental);
        
        // Calculate past due amount with current date (not used since return date is set)
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected Output: 0.00 - but wait, there's a discrepancy
        // Due date: 2025-01-04, Return date: 2025-01-06 = 2 days overdue
        // 2 days * $0.5 = $1.00, but expected output says 0.00
        // Following the test specification strictly as requested
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() throws Exception {
        // Test Case 4: "Unreturned but not overdue"
        // Setup: Create a customer c4
        Customer c4 = new Customer();
        
        // Create video rental V004
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        rental.setTape(tape);
        
        // Set rental date "2025-01-05" - due date would be 3 days later: "2025-01-08"
        Date rentalDate = dateFormat.parse("2025-01-05 00:00:00");
        Date dueDate = dateFormat.parse("2025-01-08 00:00:00");
        rental.setDueDate(dueDate);
        
        // Return date is null (unreturned)
        rental.setReturnDate(null);
        
        c4.getRentals().add(rental);
        
        // Current date "2025-01-06" - not overdue (2 days before due date)
        Date currentDate = dateFormat.parse("2025-01-06 00:00:00");
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_ReturnedFiveDaysLate() throws Exception {
        // Test Case 5: "Returned 5 days late"
        // Setup: Create a customer c5
        Customer c5 = new Customer();
        
        // Create video rental V005
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        rental.setTape(tape);
        
        // Set rental date "2025-01-01" - due date would be 3 days later: "2025-01-04"
        Date rentalDate = dateFormat.parse("2025-01-01 00:00:00");
        Date dueDate = dateFormat.parse("2025-01-04 00:00:00");
        rental.setDueDate(dueDate);
        
        // Return date is null (unreturned)
        rental.setReturnDate(null);
        
        c5.getRentals().add(rental);
        
        // Current date "2025-01-10" - 6 days overdue from due date (2025-01-04)
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected Output: 3.00 (6 days * $0.5)
        assertEquals(3.00, result, 0.001);
    }
}