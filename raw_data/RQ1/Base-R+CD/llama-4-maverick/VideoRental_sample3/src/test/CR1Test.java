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
    public void testCase1_ReturnedOneDayLate() {
        // Test Case 1: "Returned 1 day late"
        // Setup
        Customer c1 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V001");
        
        LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
        LocalDate dueDate = rentalDate.plusDays(5); // Assuming 5-day rental period
        LocalDate returnDate = LocalDate.parse("2025-01-02", formatter);
        
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        rental.setReturnDate(returnDate);
        
        c1.getRentals().add(rental);
        
        // Calculate past-due amount with current date (should use return date since it's not null)
        LocalDate currentDate = LocalDate.parse("2025-01-09", formatter);
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected: 0.50 (1 day * $0.5)
        assertEquals("Past due amount should be 0.50 for 1 day late return", 0.50, result, 0.001);
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
        LocalDate dueDate = rentalDate.plusDays(5); // Assuming 5-day rental period
        
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        // returnDate remains null (unreturned)
        
        c2.getRentals().add(rental);
        
        // Calculate past-due amount with current date 3 days after due date
        LocalDate currentDate = LocalDate.parse("2025-01-12", formatter);
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected: 1.50 (3 days * $0.5)
        assertEquals("Past due amount should be 1.50 for 3 days overdue unreturned tape", 1.50, result, 0.001);
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
        LocalDate dueDate = rentalDate.plusDays(5); // Assuming 5-day rental period
        LocalDate returnDate = dueDate; // Returned on due date
        
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        rental.setReturnDate(returnDate);
        
        c3.getRentals().add(rental);
        
        // Calculate past-due amount
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected: 0.00 (returned on or before due date)
        assertEquals("Past due amount should be 0.00 when returned on due date", 0.00, result, 0.001);
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
        LocalDate dueDate = LocalDate.parse("2025-01-10", formatter);
        
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        // returnDate remains null (unreturned)
        
        c4.getRentals().add(rental);
        
        // Calculate past-due amount with current date before due date
        LocalDate currentDate = LocalDate.parse("2025-01-06", formatter);
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected: 0.00 (current date is before due date)
        assertEquals("Past due amount should be 0.00 when not yet overdue", 0.00, result, 0.001);
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
        LocalDate dueDate = LocalDate.parse("2025-01-08", formatter);
        
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        // returnDate remains null (unreturned)
        
        c5.getRentals().add(rental);
        
        // Calculate past-due amount with current date 2 days after due date
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected: 3.00 (6 days * $0.5)
        assertEquals("Past due amount should be 3.00 for 6 days overdue", 3.00, result, 0.001);
    }
}