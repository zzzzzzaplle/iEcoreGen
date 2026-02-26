import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Customer customer;
    private VideoRental rental;
    private Tape tape;
    
    @Before
    public void setUp() {
        customer = new Customer();
        rental = new VideoRental();
        tape = new Tape();
    }
    
    @Test
    public void testCase1_ReturnedOneDayLate() {
        // Setup: Create a customer c1
        Customer c1 = new Customer();
        c1.setId("C001");
        
        // Setup: Create a VideoRental V001 with rental date "2025-01-01"
        VideoRental v001 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("V001");
        v001.setTape(tape1);
        v001.setDueDate(LocalDate.of(2025, 1, 2)); // Due date is rental date + 1 day
        
        // Setup: Add rental to customer
        c1.getRentals().add(v001);
        
        // Setup: Set return_date="2025-01-09"
        v001.setReturnDate(LocalDate.of(2025, 1, 9));
        
        // Calculate overdue amount
        double result = v001.calculateOwedPastDueAmount(LocalDate.of(2025, 1, 9));
        
        // Expected Output: 0.50 (1 day * $0.5)
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() {
        // Setup: Create a customer c2
        Customer c2 = new Customer();
        c2.setId("C002");
        
        // Setup: Create a VideoRental V002 with rental date "2025-01-01"
        VideoRental v002 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("V002");
        v002.setTape(tape2);
        v002.setDueDate(LocalDate.of(2025, 1, 2)); // Due date is rental date + 1 day
        
        // Setup: Add rental to customer
        c2.getRentals().add(v002);
        
        // Input: return_date=null, current_date="2025-01-12"
        // Calculate overdue amount with current date
        double result = v002.calculateOwedPastDueAmount(LocalDate.of(2025, 1, 12));
        
        // Expected Output: 1.50 (3 days * $0.5)
        // Due date: 2025-01-02, Current date: 2025-01-12 → 10 days overdue
        // Note: There seems to be a discrepancy in the test specification
        // The setup says rental date "2025-01-01" → due date "2025-01-02"
        // Current date "2025-01-12" → 10 days overdue (10 * 0.5 = 5.00)
        // But expected output says 1.50 (3 days * $0.5)
        // Following the exact expected output as specified
        assertEquals(1.50, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() {
        // Setup: Create a customer c3
        Customer c3 = new Customer();
        c3.setId("C003");
        
        // Setup: Create a VideoRental V003 with rental date "2025-01-01"
        VideoRental v003 = new VideoRental();
        Tape tape3 = new Tape();
        tape3.setId("V003");
        v003.setTape(tape3);
        v003.setDueDate(LocalDate.of(2025, 1, 6)); // Due date is rental date + 5 days
        
        // Setup: Add rental to customer
        c3.getRentals().add(v003);
        
        // Setup: Set return_date="2025-01-06"
        v003.setReturnDate(LocalDate.of(2025, 1, 6));
        
        // Input: current_date="2025-01-10"
        // Calculate overdue amount
        double result = v003.calculateOwedPastDueAmount(LocalDate.of(2025, 1, 10));
        
        // Expected Output: 0.00 (returned on due date)
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() {
        // Setup: Create a customer c4
        Customer c4 = new Customer();
        c4.setId("C004");
        
        // Setup: Create a VideoRental V004 with rental date "2025-01-05"
        VideoRental v004 = new VideoRental();
        Tape tape4 = new Tape();
        tape4.setId("V004");
        v004.setTape(tape4);
        v004.setDueDate(LocalDate.of(2025, 1, 10)); // Due date is rental date + 5 days
        
        // Setup: Add rental to customer
        c4.getRentals().add(v004);
        
        // Input: return_date=null, current_date="2025-01-06"
        // Calculate overdue amount
        double result = v004.calculateOwedPastDueAmount(LocalDate.of(2025, 1, 6));
        
        // Expected Output: 0.00 (not overdue yet)
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_ReturnedFiveDaysLate() {
        // Setup: Create a customer c5
        Customer c5 = new Customer();
        c5.setId("C005");
        
        // Setup: Create a VideoRental V005 with rental date "2025-01-01"
        VideoRental v005 = new VideoRental();
        Tape tape5 = new Tape();
        tape5.setId("V005");
        v005.setTape(tape5);
        v005.setDueDate(LocalDate.of(2025, 1, 8)); // Due date is rental date + 7 days
        
        // Setup: Add rental to customer
        c5.getRentals().add(v005);
        
        // Input: return_date=null, current_date="2025-01-10"
        // Calculate overdue amount
        double result = v005.calculateOwedPastDueAmount(LocalDate.of(2025, 1, 10));
        
        // Expected Output: 3.00 (6 days * $0.5)
        // Due date: 2025-01-08, Current date: 2025-01-10 → 2 days overdue
        // Note: There seems to be a discrepancy in the test specification
        // The setup says rental date "2025-01-01" → due date calculation
        // Expected output says 3.00 (6 days * $0.5)
        // Following the exact expected output as specified
        assertEquals(3.00, result, 0.001);
    }
}