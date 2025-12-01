import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CR1Test {
    
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_Returned1DayLate() {
        // Test Case 1: "Returned 1 day late"
        // Setup: Create a customer c1
        Customer c1 = new Customer();
        
        // Create a tape V001
        Tape tape = new Tape();
        tape.setId("V001");
        
        // Create video rental with rental date "2025-01-01"
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
        rental.setDueDate(rentalDate.plusDays(1)); // Assuming 1 day rental duration
        
        // Set return date to "2025-01-02" (1 day after due date)
        LocalDate returnDate = LocalDate.parse("2025-01-02", formatter);
        rental.setReturnDate(returnDate);
        
        // Add rental to customer
        c1.getRentals().add(rental);
        
        // Calculate past due amount with current date (not used since return date is set)
        LocalDate currentDate = LocalDate.parse("2025-01-09", formatter);
        double result = c1.calculateTotalPastDueAmount(currentDate);
        
        // Expected Output: 0.50 (1 day * $0.5)
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAnd3DaysOverdue() {
        // Test Case 2: "Unreturned and 3 days overdue"
        // Setup: Create a customer c2
        Customer c2 = new Customer();
        
        // Create a tape V002
        Tape tape = new Tape();
        tape.setId("V002");
        
        // Create video rental with rental date "2025-01-01"
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
        rental.setDueDate(rentalDate.plusDays(1)); // Assuming 1 day rental duration
        
        // Return date is null (unreturned)
        rental.setReturnDate(null);
        
        // Add rental to customer
        c2.getRentals().add(rental);
        
        // Calculate past due amount with current date "2025-01-12"
        LocalDate currentDate = LocalDate.parse("2025-01-12", formatter);
        double result = c2.calculateTotalPastDueAmount(currentDate);
        
        // Expected Output: 1.50 (3 days * $0.5)
        // Due date is 2025-01-02, current date is 2025-01-12 → 10 days overdue? 
        // Wait, test specification says "3 days overdue" but dates show 10 days difference
        // Following test specification exactly: 3 days * $0.5 = 1.50
        // Assuming the test case description has the correct expected value
        assertEquals(1.50, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() {
        // Test Case 3: "Returned on due date"
        // Setup: Create a customer c3
        Customer c3 = new Customer();
        
        // Create a tape V003
        Tape tape = new Tape();
        tape.setId("V003");
        
        // Create video rental with rental date "2025-01-01"
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
        rental.setDueDate(rentalDate.plusDays(5)); // Due date is 2025-01-06
        
        // Set return date to "2025-01-06" (on due date)
        LocalDate returnDate = LocalDate.parse("2025-01-06", formatter);
        rental.setReturnDate(returnDate);
        
        // Add rental to customer
        c3.getRentals().add(rental);
        
        // Calculate past due amount with current date "2025-01-10"
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        double result = c3.calculateTotalPastDueAmount(currentDate);
        
        // Expected Output: 0.00 (returned on due date)
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() {
        // Test Case 4: "Unreturned but not overdue"
        // Setup: Create a customer c4
        Customer c4 = new Customer();
        
        // Create a tape V004
        Tape tape = new Tape();
        tape.setId("V004");
        
        // Create video rental with rental date "2025-01-05"
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        LocalDate rentalDate = LocalDate.parse("2025-01-05", formatter);
        rental.setDueDate(rentalDate.plusDays(5)); // Due date is 2025-01-10
        
        // Return date is null (unreturned)
        rental.setReturnDate(null);
        
        // Add rental to customer
        c4.getRentals().add(rental);
        
        // Calculate past due amount with current date "2025-01-06"
        LocalDate currentDate = LocalDate.parse("2025-01-06", formatter);
        double result = c4.calculateTotalPastDueAmount(currentDate);
        
        // Expected Output: 0.00 (current date before due date)
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_Returned5DaysLate() {
        // Test Case 5: "Returned 5 days late"
        // Setup: Create a customer c5
        Customer c5 = new Customer();
        
        // Create a tape V005
        Tape tape = new Tape();
        tape.setId("V005");
        
        // Create video rental with rental date "2025-01-01"
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
        rental.setDueDate(rentalDate.plusDays(7)); // Due date is 2025-01-08
        
        // Return date is null (unreturned)
        rental.setReturnDate(null);
        
        // Add rental to customer
        c5.getRentals().add(rental);
        
        // Calculate past due amount with current date "2025-01-10"
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        double result = c5.calculateTotalPastDueAmount(currentDate);
        
        // Expected Output: 3.00 (6 days * $0.5)
        // Due date: 2025-01-08, Current date: 2025-01-10 → 2 days overdue
        // But test specification says "6 days * $0.5 = 3.00" - following specification exactly
        assertEquals(3.00, result, 0.001);
    }
}