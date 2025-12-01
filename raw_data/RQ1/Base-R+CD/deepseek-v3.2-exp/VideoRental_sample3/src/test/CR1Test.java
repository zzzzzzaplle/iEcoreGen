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
    public void testCase1_ReturnedOneDayLate() {
        // Test Case 1: "Returned 1 day late"
        // Setup
        Customer c1 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V001");
        rental.setTape(tape);
        
        // Set rental date to "2025-01-01" and due date 7 days later (2025-01-08)
        LocalDateTime rentalDate = LocalDateTime.parse("2025-01-01 00:00:00", formatter);
        LocalDateTime dueDate = rentalDate.plusDays(7); // 2025-01-08
        rental.setDueDate(dueDate);
        
        // Set return date to "2025-01-09" (1 day late)
        LocalDateTime returnDate = LocalDateTime.parse("2025-01-09 00:00:00", formatter);
        rental.setReturnDate(returnDate);
        
        c1.getRentals().add(rental);
        
        // Input: current_date="2025-01-10" (not used since return date is set)
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-10 00:00:00", formatter);
        
        // Expected Output: 0.50 (1 day * $0.5)
        double expected = 0.50;
        double actual = c1.calculateTotalPastDueAmount(currentDate);
        
        assertEquals("Returned 1 day late should result in $0.50 overdue fee", 
                     expected, actual, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() {
        // Test Case 2: "Unreturned and 3 days overdue"
        // Setup
        Customer c2 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V002");
        rental.setTape(tape);
        
        // Set rental date to "2025-01-01" and due date 7 days later (2025-01-08)
        LocalDateTime rentalDate = LocalDateTime.parse("2025-01-01 00:00:00", formatter);
        LocalDateTime dueDate = rentalDate.plusDays(7); // 2025-01-08
        rental.setDueDate(dueDate);
        
        // Return date is null (unreturned)
        rental.setReturnDate(null);
        
        c2.getRentals().add(rental);
        
        // Input: return_date=null, current_date="2025-01-12"
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-12 00:00:00", formatter);
        
        // Expected Output: 2.00 (4 days * $0.5) - from Jan 8 to Jan 12 is 4 days
        double expected = 2.00;
        double actual = c2.calculateTotalPastDueAmount(currentDate);
        
        assertEquals("Unreturned and 4 days overdue should result in $2.00 overdue fee", 
                     expected, actual, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() {
        // Test Case 3: "Returned on due date"
        // Setup
        Customer c3 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V003");
        rental.setTape(tape);
        
        // Set rental date to "2025-01-01" and due date 7 days later (2025-01-08)
        LocalDateTime rentalDate = LocalDateTime.parse("2025-01-01 00:00:00", formatter);
        LocalDateTime dueDate = rentalDate.plusDays(7); // 2025-01-08
        rental.setDueDate(dueDate);
        
        // Set return date to "2025-01-08" (on due date)
        LocalDateTime returnDate = LocalDateTime.parse("2025-01-08 00:00:00", formatter);
        rental.setReturnDate(returnDate);
        
        c3.getRentals().add(rental);
        
        // Input: current_date="2025-01-10" (not used since return date is set)
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-10 00:00:00", formatter);
        
        // Expected Output: 0.00
        double expected = 0.00;
        double actual = c3.calculateTotalPastDueAmount(currentDate);
        
        assertEquals("Returned on due date should result in $0.00 overdue fee", 
                     expected, actual, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() {
        // Test Case 4: "Unreturned but not overdue"
        // Setup
        Customer c4 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V004");
        rental.setTape(tape);
        
        // Set rental date to "2025-01-05" and due date 7 days later (2025-01-12)
        LocalDateTime rentalDate = LocalDateTime.parse("2025-01-05 00:00:00", formatter);
        LocalDateTime dueDate = rentalDate.plusDays(7); // 2025-01-12
        rental.setDueDate(dueDate);
        
        // Return date is null (unreturned)
        rental.setReturnDate(null);
        
        c4.getRentals().add(rental);
        
        // Input: return_date=null, current_date="2025-01-06" (before due date)
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-06 00:00:00", formatter);
        
        // Expected Output: 0.00
        double expected = 0.00;
        double actual = c4.calculateTotalPastDueAmount(currentDate);
        
        assertEquals("Unreturned but not overdue should result in $0.00 overdue fee", 
                     expected, actual, 0.001);
    }
    
    @Test
    public void testCase5_ReturnedFiveDaysLate() {
        // Test Case 5: "Returned 5 days late"
        // Note: There seems to be a discrepancy in the test specification
        // The description says "Returned 5 days late" but the expected output shows 2 days
        // Following the exact specification as requested
        
        // Setup
        Customer c5 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V005");
        rental.setTape(tape);
        
        // Set rental date to "2025-01-01" and due date 7 days later (2025-01-08)
        LocalDateTime rentalDate = LocalDateTime.parse("2025-01-01 00:00:00", formatter);
        LocalDateTime dueDate = rentalDate.plusDays(7); // 2025-01-08
        rental.setDueDate(dueDate);
        
        // Return date is null (unreturned) according to input specification
        rental.setReturnDate(null);
        
        c5.getRentals().add(rental);
        
        // Input: return_date=null, current_date="2025-01-10"
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-10 00:00:00", formatter);
        
        // Expected Output: 1.00 (2 days * $0.5) - from Jan 8 to Jan 10 is 2 days
        double expected = 1.00;
        double actual = c5.calculateTotalPastDueAmount(currentDate);
        
        assertEquals("Unreturned and 2 days overdue should result in $1.00 overdue fee", 
                     expected, actual, 0.001);
    }
}