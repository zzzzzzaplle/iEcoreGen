import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Clear the global rental store before each test to ensure isolation
        RentalStore.getAllRentals().clear();
    }
    
    @Test
    public void testCase1_ReturnedOneDayLate() {
        // Test Case 1: "Returned 1 day late"
        // Setup
        Customer c1 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V001");
        
        LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
        LocalDate dueDate = rentalDate.plusDays(7); // 2025-01-08
        LocalDate returnDate = LocalDate.parse("2025-01-09", formatter);
        
        rental.setTape(tape);
        rental.setRentalDate(rentalDate);
        rental.setDueDate(dueDate);
        rental.setReturnDate(returnDate);
        
        c1.getRentals().add(rental);
        RentalStore.addRental(rental);
        
        // Input: return_date="2025-01-02" (Note: This appears to be a typo in the specification)
        // According to setup, return date should be "2025-01-09" which is 1 day after due date
        LocalDate currentDate = LocalDate.parse("2025-01-02", formatter);
        
        // Calculate overdue amount
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected Output: 0.50 (1 day * $0.5)
        assertEquals("Returned 1 day late should result in $0.50 overdue fee", 
                     0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() {
        // Test Case 2: "Unreturned and 3 days overdue"
        // Setup
        Customer c2 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V002");
        
        LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
        LocalDate dueDate = rentalDate.plusDays(7); // 2025-01-08
        
        rental.setTape(tape);
        rental.setRentalDate(rentalDate);
        rental.setDueDate(dueDate);
        rental.setReturnDate(null); // Unreturned tape
        
        c2.getRentals().add(rental);
        RentalStore.addRental(rental);
        
        // Input: return_date=null, current_date="2025-01-12"
        LocalDate currentDate = LocalDate.parse("2025-01-12", formatter);
        
        // Calculate overdue amount
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected Output: 1.50 (4 days * $0.5) - Note: Due date is 2025-01-08, current date is 2025-01-12 = 4 days overdue
        assertEquals("Unreturned and 4 days overdue should result in $2.00 overdue fee", 
                     2.00, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() {
        // Test Case 3: "Returned on due date"
        // Setup
        Customer c3 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V003");
        
        LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
        LocalDate dueDate = rentalDate.plusDays(5); // 2025-01-06
        LocalDate returnDate = LocalDate.parse("2025-01-06", formatter);
        
        rental.setTape(tape);
        rental.setRentalDate(rentalDate);
        rental.setDueDate(dueDate);
        rental.setReturnDate(returnDate);
        
        c3.getRentals().add(rental);
        RentalStore.addRental(rental);
        
        // Input: return_date="2025-01-06", current_date="2025-01-10"
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        
        // Calculate overdue amount
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected Output: 0.00 (returned on due date)
        assertEquals("Returned on due date should result in $0.00 overdue fee", 
                     0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() {
        // Test Case 4: "Unreturned but not overdue"
        // Setup
        Customer c4 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V004");
        
        LocalDate rentalDate = LocalDate.parse("2025-01-05", formatter);
        LocalDate dueDate = rentalDate.plusDays(5); // 2025-01-10
        
        rental.setTape(tape);
        rental.setRentalDate(rentalDate);
        rental.setDueDate(dueDate);
        rental.setReturnDate(null); // Unreturned tape
        
        c4.getRentals().add(rental);
        RentalStore.addRental(rental);
        
        // Input: return_date=null, due_date="2025-01-10", current_date="2025-01-06"
        LocalDate currentDate = LocalDate.parse("2025-01-06", formatter);
        
        // Calculate overdue amount
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected Output: 0.00 (not overdue yet)
        assertEquals("Unreturned but not overdue should result in $0.00 overdue fee", 
                     0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_ReturnedFiveDaysLate() {
        // Test Case 5: "Returned 5 days late"
        // Setup
        Customer c5 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V005");
        
        LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
        LocalDate dueDate = rentalDate.plusDays(7); // 2025-01-08
        
        rental.setTape(tape);
        rental.setRentalDate(rentalDate);
        rental.setDueDate(dueDate);
        rental.setReturnDate(null); // Unreturned tape
        
        c5.getRentals().add(rental);
        RentalStore.addRental(rental);
        
        // Input: return_date=null, due_date="2025-01-08", current_date="2025-01-10"
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        
        // Calculate overdue amount
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected Output: 1.00 (2 days * $0.5) - Note: Due date is 2025-01-08, current date is 2025-01-10 = 2 days overdue
        assertEquals("Unreturned and 2 days overdue should result in $1.00 overdue fee", 
                     1.00, result, 0.001);
    }
}