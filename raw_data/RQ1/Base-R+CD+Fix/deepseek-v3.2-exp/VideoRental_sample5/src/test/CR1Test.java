import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private Customer customer;
    private VideoRental rental;
    
    @Before
    public void setUp() {
        customer = new Customer();
        rental = new VideoRental();
    }
    
    @Test
    public void testCase1_ReturnedOneDayLate() {
        // Setup: Create a VideoRental V001 with rental date "2025-01-01"
        Tape tape = new Tape();
        tape.setId("V001");
        rental.setTape(tape);
        rental.setDueDate("2025-01-08"); // Due date is 7 days after rental
        
        // Set return_date="2025-01-09" (1 day late)
        rental.setReturnDate("2025-01-09");
        
        // Calculate overdue amount
        double result = rental.calculateOwedPastDueAmount("2025-01-10"); // current_date not relevant since returned
        
        // Expected Output: 0.50 (1 day * $0.5)
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() {
        // Setup: Create a VideoRental V002 with rental date "2025-01-01"
        Tape tape = new Tape();
        tape.setId("V002");
        rental.setTape(tape);
        rental.setDueDate("2025-01-08"); // Due date is 7 days after rental
        
        // return_date=null, current_date="2025-01-12"
        // Overdue days: 2025-01-12 - 2025-01-08 = 4 days
        double result = rental.calculateOwedPastDueAmount("2025-01-12");
        
        // Expected Output: 2.00 (4 days * $0.5)
        assertEquals(2.00, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() {
        // Setup: Create a VideoRental V003 with rental date "2025-01-01"
        Tape tape = new Tape();
        tape.setId("V003");
        rental.setTape(tape);
        rental.setDueDate("2025-01-08"); // Due date is 7 days after rental
        
        // Set return_date="2025-01-08" (on due date)
        rental.setReturnDate("2025-01-08");
        
        // Calculate overdue amount with current_date="2025-01-10"
        double result = rental.calculateOwedPastDueAmount("2025-01-10");
        
        // Expected Output: 0.00 (returned on due date)
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() {
        // Setup: Create a VideoRental V004 with rental date "2025-01-05"
        Tape tape = new Tape();
        tape.setId("V004");
        rental.setTape(tape);
        rental.setDueDate("2025-01-12"); // Due date is 7 days after rental
        
        // return_date=null, current_date="2025-01-06" (before due date)
        double result = rental.calculateOwedPastDueAmount("2025-01-06");
        
        // Expected Output: 0.00 (not overdue yet)
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_ReturnedFiveDaysLate() {
        // Setup: Create a VideoRental V005 with rental date "2025-01-01"
        Tape tape = new Tape();
        tape.setId("V005");
        rental.setTape(tape);
        rental.setDueDate("2025-01-08"); // Due date is 7 days after rental
        
        // Set return_date="2025-01-13" (5 days late)
        rental.setReturnDate("2025-01-13");
        
        // Calculate overdue amount with current_date="2025-01-10"
        double result = rental.calculateOwedPastDueAmount("2025-01-10");
        
        // Expected Output: 2.50 (5 days * $0.5)
        assertEquals(2.50, result, 0.001);
    }
}