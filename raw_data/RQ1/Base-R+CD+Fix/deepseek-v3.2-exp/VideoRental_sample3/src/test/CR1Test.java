import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR1Test {
    
    private Customer customer;
    private Tape tape;
    private VideoRental rental;
    
    @Before
    public void setUp() {
        customer = new Customer();
        tape = new Tape();
        rental = new VideoRental();
    }
    
    @Test
    public void testCase1_ReturnedOneDayLate() {
        // Setup: Create a customer and tape
        Customer c1 = new Customer();
        Tape v001 = new Tape();
        
        // Create and configure rental
        VideoRental rental = new VideoRental();
        rental.setTape(v001);
        rental.setDueDate("2025-01-08"); // Due date is 7 days after rental date 2025-01-01
        
        // Set return date to 1 day after due date
        rental.setReturnDate("2025-01-09");
        
        // Calculate overdue amount
        double result = rental.calculateOwedPastDueAmount("2025-01-10");
        
        // Expected: 1 day * $0.5 = $0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() {
        // Setup: Create a customer and tape
        Customer c2 = new Customer();
        Tape v002 = new Tape();
        
        // Create and configure rental (no return date set)
        VideoRental rental = new VideoRental();
        rental.setTape(v002);
        rental.setDueDate("2025-01-08"); // Due date is 7 days after rental date 2025-01-01
        
        // Calculate overdue amount with current date 4 days after due date
        double result = rental.calculateOwedPastDueAmount("2025-01-12");
        
        // Expected: 4 days * $0.5 = $2.00
        assertEquals(2.00, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() {
        // Setup: Create a customer and tape
        Customer c3 = new Customer();
        Tape v003 = new Tape();
        
        // Create and configure rental
        VideoRental rental = new VideoRental();
        rental.setTape(v003);
        rental.setDueDate("2025-01-08"); // Due date is 7 days after rental date 2025-01-01
        
        // Set return date to due date
        rental.setReturnDate("2025-01-08");
        
        // Calculate overdue amount
        double result = rental.calculateOwedPastDueAmount("2025-01-10");
        
        // Expected: Returned on due date = $0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() {
        // Setup: Create a customer and tape
        Customer c4 = new Customer();
        Tape v004 = new Tape();
        
        // Create and configure rental (no return date set)
        VideoRental rental = new VideoRental();
        rental.setTape(v004);
        rental.setDueDate("2025-01-12"); // Due date is 7 days after rental date 2025-01-05
        
        // Calculate overdue amount with current date before due date
        double result = rental.calculateOwedPastDueAmount("2025-01-06");
        
        // Expected: Not overdue = $0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_ReturnedSixDaysLate() {
        // Setup: Create a customer and tape
        Customer c5 = new Customer();
        Tape v005 = new Tape();
        
        // Create and configure rental
        VideoRental rental = new VideoRental();
        rental.setTape(v005);
        rental.setDueDate("2025-01-08"); // Due date is 7 days after rental date 2025-01-01
        
        // Set return date to 6 days after due date
        rental.setReturnDate("2025-01-14");
        
        // Calculate overdue amount
        double result = rental.calculateOwedPastDueAmount("2025-01-15");
        
        // Expected: 6 days * $0.5 = $3.00
        assertEquals(3.00, result, 0.001);
    }
}