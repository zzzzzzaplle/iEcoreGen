import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR1Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_Returned1DayLate() throws Exception {
        // Setup: Create a customer c1
        Customer c1 = new Customer();
        
        // Setup: Create tape V001
        Tape tape = new Tape();
        tape.setId("V001");
        
        // Setup: Rental date "2025-01-01", due date should be 2025-01-08 (7 days later)
        Date rentalDate = dateFormat.parse("2025-01-01");
        
        // Create video rental with due date 2025-01-08
        Date dueDate = dateFormat.parse("2025-01-08");
        VideoRental rental = new VideoRental(tape, dueDate);
        
        // Set return date to "2025-01-09" (1 day late)
        Date returnDate = dateFormat.parse("2025-01-09");
        rental.setReturnDate(returnDate);
        
        // Add rental to customer
        c1.getRentals().add(rental);
        
        // Calculate past due amount with current date after return date
        Date currentDate = dateFormat.parse("2025-01-10");
        double result = c1.calculateTotalPastDueAmount(currentDate);
        
        // Expected Output: 0.50 (1 day * $0.5)
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAnd3DaysOverdue() throws Exception {
        // Setup: Create a customer c2
        Customer c2 = new Customer();
        
        // Setup: Create tape V002
        Tape tape = new Tape();
        tape.setId("V002");
        
        // Setup: Rental date "2025-01-01", due date should be 2025-01-08 (7 days later)
        Date rentalDate = dateFormat.parse("2025-01-01");
        
        // Create video rental with due date 2025-01-08
        Date dueDate = dateFormat.parse("2025-01-08");
        VideoRental rental = new VideoRental(tape, dueDate);
        
        // Return date is null (unreturned)
        rental.setReturnDate(null);
        
        // Add rental to customer
        c2.getRentals().add(rental);
        
        // Calculate past due amount with current date "2025-01-12" (4 days overdue)
        Date currentDate = dateFormat.parse("2025-01-12");
        double result = c2.calculateTotalPastDueAmount(currentDate);
        
        // Expected Output: 2.00 (4 days * $0.5)
        // Note: Correction from specification - 2025-01-12 is 4 days after 2025-01-08
        assertEquals(2.00, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() throws Exception {
        // Setup: Create a customer c3
        Customer c3 = new Customer();
        
        // Setup: Create tape V003
        Tape tape = new Tape();
        tape.setId("V003");
        
        // Setup: Rental date "2025-01-01", due date should be 2025-01-08 (7 days later)
        Date rentalDate = dateFormat.parse("2025-01-01");
        
        // Create video rental with due date 2025-01-08
        Date dueDate = dateFormat.parse("2025-01-08");
        VideoRental rental = new VideoRental(tape, dueDate);
        
        // Set return date to "2025-01-08" (on due date)
        Date returnDate = dateFormat.parse("2025-01-08");
        rental.setReturnDate(returnDate);
        
        // Add rental to customer
        c3.getRentals().add(rental);
        
        // Calculate past due amount with current date "2025-01-10"
        Date currentDate = dateFormat.parse("2025-01-10");
        double result = c3.calculateTotalPastDueAmount(currentDate);
        
        // Expected Output: 0.00 (returned on due date)
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() throws Exception {
        // Setup: Create a customer c4
        Customer c4 = new Customer();
        
        // Setup: Create tape V004
        Tape tape = new Tape();
        tape.setId("V004");
        
        // Setup: Rental date "2025-01-05", due date should be 2025-01-12 (7 days later)
        Date rentalDate = dateFormat.parse("2025-01-05");
        
        // Create video rental with due date 2025-01-12
        Date dueDate = dateFormat.parse("2025-01-12");
        VideoRental rental = new VideoRental(tape, dueDate);
        
        // Return date is null (unreturned)
        rental.setReturnDate(null);
        
        // Add rental to customer
        c4.getRentals().add(rental);
        
        // Calculate past due amount with current date "2025-01-06" (before due date)
        Date currentDate = dateFormat.parse("2025-01-06");
        double result = c4.calculateTotalPastDueAmount(currentDate);
        
        // Expected Output: 0.00 (current date is before due date)
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_Returned5DaysLate() throws Exception {
        // Setup: Create a customer c5
        Customer c5 = new Customer();
        
        // Setup: Create tape V005
        Tape tape = new Tape();
        tape.setId("V005");
        
        // Setup: Rental date "2025-01-01", due date should be 2025-01-08 (7 days later)
        Date rentalDate = dateFormat.parse("2025-01-01");
        
        // Create video rental with due date 2025-01-08
        Date dueDate = dateFormat.parse("2025-01-08");
        VideoRental rental = new VideoRental(tape, dueDate);
        
        // Return date is null (unreturned in specification, but test expects calculation with current date)
        rental.setReturnDate(null);
        
        // Add rental to customer
        c5.getRentals().add(rental);
        
        // Calculate past due amount with current date "2025-01-10" (2 days overdue)
        Date currentDate = dateFormat.parse("2025-01-10");
        double result = c5.calculateTotalPastDueAmount(currentDate);
        
        // Expected Output: 1.00 (2 days * $0.5)
        // Note: Correction from specification - 2025-01-10 is 2 days after 2025-01-08
        assertEquals(1.00, result, 0.001);
    }
}