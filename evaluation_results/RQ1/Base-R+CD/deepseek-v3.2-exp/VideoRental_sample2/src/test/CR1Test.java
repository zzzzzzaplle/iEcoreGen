import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CR1Test {
    
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_Returned1DayLate() {
        // Test Case 1: "Returned 1 day late"
        // Setup
        Customer c1 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V001");
        
        LocalDateTime rentalDate = LocalDateTime.parse("2025-01-01 00:00:00", formatter);
        LocalDateTime dueDate = rentalDate.plusDays(7); // 2025-01-08
        LocalDateTime returnDate = LocalDateTime.parse("2025-01-09 00:00:00", formatter);
        
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        rental.setReturnDate(returnDate);
        c1.getRentals().add(rental);
        
        // Calculate past due amount with current date after return
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-10 00:00:00", formatter);
        double result = c1.calculateTotalPastDueAmount(currentDate);
        
        // Expected Output: 0.50 (1 day * $0.5)
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAnd3DaysOverdue() {
        // Test Case 2: "Unreturned and 3 days overdue"
        // Note: There's a discrepancy in the test specification - it says "3 days overdue" but expects 4 days
        // Following the expected output of 2.00 (4 days * $0.5)
        
        // Setup
        Customer c2 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V002");
        
        LocalDateTime rentalDate = LocalDateTime.parse("2025-01-01 00:00:00", formatter);
        LocalDateTime dueDate = rentalDate.plusDays(7); // 2025-01-08
        
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        // return_date is null (unreturned)
        rental.setReturnDate(null);
        c2.getRentals().add(rental);
        
        // Input: current_date="2025-01-12"
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-12 00:00:00", formatter);
        double result = c2.calculateTotalPastDueAmount(currentDate);
        
        // Expected Output: 2.00 (4 days * $0.5)
        assertEquals(2.00, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() {
        // Test Case 3: "Returned on due date"
        // Setup
        Customer c3 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V003");
        
        LocalDateTime rentalDate = LocalDateTime.parse("2025-01-01 00:00:00", formatter);
        LocalDateTime dueDate = rentalDate.plusDays(5); // 2025-01-06 (as per test case)
        
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        rental.setReturnDate(dueDate); // Returned on due date
        c3.getRentals().add(rental);
        
        // Input: current_date="2025-01-10" (but calculation uses return date)
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-10 00:00:00", formatter);
        double result = c3.calculateTotalPastDueAmount(currentDate);
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() {
        // Test Case 4: "Unreturned but not overdue"
        // Setup
        Customer c4 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V004");
        
        LocalDateTime rentalDate = LocalDateTime.parse("2025-01-05 00:00:00", formatter);
        LocalDateTime dueDate = LocalDateTime.parse("2025-01-10 00:00:00", formatter);
        
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        rental.setReturnDate(null); // Unreturned
        c4.getRentals().add(rental);
        
        // Input: current_date="2025-01-06" (before due date)
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-06 00:00:00", formatter);
        double result = c4.calculateTotalPastDueAmount(currentDate);
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_Returned5DaysLate() {
        // Test Case 5: "Returned 5 days late"
        // Note: There's a discrepancy in the test specification - it says "5 days late" but expects 2 days
        // Following the expected output of 1.00 (2 days * $0.5)
        
        // Setup
        Customer c5 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V005");
        
        LocalDateTime rentalDate = LocalDateTime.parse("2025-01-01 00:00:00", formatter);
        LocalDateTime dueDate = LocalDateTime.parse("2025-01-08 00:00:00", formatter);
        
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        rental.setReturnDate(null); // Unreturned as per input
        c5.getRentals().add(rental);
        
        // Input: current_date="2025-01-10"
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-10 00:00:00", formatter);
        double result = c5.calculateTotalPastDueAmount(currentDate);
        
        // Expected Output: 1.00 (2 days * $0.5)
        assertEquals(1.00, result, 0.001);
    }
}