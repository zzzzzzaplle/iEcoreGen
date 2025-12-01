import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR1Test {
    
    @Test
    public void testCase1_ReturnedOneDayLate() {
        // Setup
        Customer c1 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V001");
        rental.setTape(tape);
        rental.setDueDate(LocalDate.parse("2025-01-01").plusDays(1)); // Due date is rental date + 1 day
        rental.setReturnDate(LocalDate.parse("2025-01-09"));
        
        // Calculate past-due amount with current date as return date
        double result = rental.calculateOwedPastDueAmount(LocalDate.parse("2025-01-09"));
        
        // Verify
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() {
        // Setup
        Customer c2 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V002");
        rental.setTape(tape);
        rental.setDueDate(LocalDate.parse("2025-01-01").plusDays(1)); // Due date is rental date + 1 day
        // Return date remains null (unreturned)
        
        // Calculate past-due amount with current date 3 days after due date
        double result = rental.calculateOwedPastDueAmount(LocalDate.parse("2025-01-12"));
        
        // Verify
        assertEquals(1.50, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() {
        // Setup
        Customer c3 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V003");
        rental.setTape(tape);
        rental.setDueDate(LocalDate.parse("2025-01-01").plusDays(1)); // Due date is rental date + 1 day
        rental.setReturnDate(LocalDate.parse("2025-01-06"));
        
        // Calculate past-due amount with current date as return date
        double result = rental.calculateOwedPastDueAmount(LocalDate.parse("2025-01-10"));
        
        // Verify
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() {
        // Setup
        Customer c4 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V004");
        rental.setTape(tape);
        rental.setDueDate(LocalDate.parse("2025-01-05").plusDays(1)); // Due date is rental date + 1 day
        // Return date remains null (unreturned)
        
        // Calculate past-due amount with current date before due date
        double result = rental.calculateOwedPastDueAmount(LocalDate.parse("2025-01-06"));
        
        // Verify
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_ReturnedFiveDaysLate() {
        // Setup
        Customer c5 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V005");
        rental.setTape(tape);
        rental.setDueDate(LocalDate.parse("2025-01-01").plusDays(1)); // Due date is rental date + 1 day
        // Return date remains null (unreturned)
        
        // Calculate past-due amount with current date 6 days after due date
        double result = rental.calculateOwedPastDueAmount(LocalDate.parse("2025-01-10"));
        
        // Verify
        assertEquals(3.00, result, 0.001);
    }
}