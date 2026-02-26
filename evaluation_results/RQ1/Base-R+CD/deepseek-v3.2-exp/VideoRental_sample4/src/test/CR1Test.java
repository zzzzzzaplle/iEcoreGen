import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private VideoRental videoRental;
    private Date rentalDate;
    private Date dueDate;
    private Date returnDate;
    private Date currentDate;
    
    @Before
    public void setUp() {
        videoRental = new VideoRental();
        Tape tape = new Tape();
        videoRental.setTape(tape);
    }
    
    @Test
    public void testCase1_Returned1DayLate() {
        // Setup: Create a customer c1, add VideoRental V001 with rental date "2025-01-01"
        Customer c1 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V001");
        rental.setTape(tape);
        
        // Set rental date "2025-01-01" and due date (7 days later)
        Date rentalDate = new Date("2025-01-01 00:00:00");
        Date dueDate = new Date("2025-01-08 00:00:00");
        rental.setDueDate(dueDate);
        
        // Set return date "2025-01-09" (1 day late)
        Date returnDate = new Date("2025-01-09 00:00:00");
        rental.setReturnDate(returnDate);
        
        // Calculate past due amount with current date after return
        Date currentDate = new Date("2025-01-10 00:00:00");
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected: 1 day * $0.5 = 0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAnd3DaysOverdue() {
        // Setup: Create a customer c2, add VideoRental V002 with rental date "2025-01-01"
        Customer c2 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V002");
        rental.setTape(tape);
        
        // Set rental date "2025-01-01" and due date (7 days later)
        Date rentalDate = new Date("2025-01-01 00:00:00");
        Date dueDate = new Date("2025-01-08 00:00:00");
        rental.setDueDate(dueDate);
        
        // Return date is null (unreturned)
        rental.setReturnDate(null);
        
        // Current date is "2025-01-12" (4 days overdue)
        Date currentDate = new Date("2025-01-12 00:00:00");
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected: 4 days * $0.5 = 2.00
        assertEquals(2.00, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() {
        // Setup: Create a customer c3, add VideoRental V003 with rental date "2025-01-01"
        Customer c3 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V003");
        rental.setTape(tape);
        
        // Set rental date "2025-01-01" and due date (7 days later)
        Date rentalDate = new Date("2025-01-01 00:00:00");
        Date dueDate = new Date("2025-01-08 00:00:00");
        rental.setDueDate(dueDate);
        
        // Set return date "2025-01-08" (on due date)
        Date returnDate = new Date("2025-01-08 00:00:00");
        rental.setReturnDate(returnDate);
        
        // Calculate with current date after return
        Date currentDate = new Date("2025-01-10 00:00:00");
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected: 0 days overdue = 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() {
        // Setup: Create a customer c4, add VideoRental V004 with rental date "2025-01-05"
        Customer c4 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V004");
        rental.setTape(tape);
        
        // Set rental date "2025-01-05" and due date (7 days later = "2025-01-12")
        Date rentalDate = new Date("2025-01-05 00:00:00");
        Date dueDate = new Date("2025-01-12 00:00:00");
        rental.setDueDate(dueDate);
        
        // Return date is null (unreturned)
        rental.setReturnDate(null);
        
        // Current date is "2025-01-06" (before due date)
        Date currentDate = new Date("2025-01-06 00:00:00");
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected: 0 days overdue = 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_Returned5DaysLate() {
        // Setup: Create a customer c5, add VideoRental V005 with rental date "2025-01-01"
        Customer c5 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V005");
        rental.setTape(tape);
        
        // Set rental date "2025-01-01" and due date (7 days later = "2025-01-08")
        Date rentalDate = new Date("2025-01-01 00:00:00");
        Date dueDate = new Date("2025-01-08 00:00:00");
        rental.setDueDate(dueDate);
        
        // Return date is null (unreturned)
        rental.setReturnDate(null);
        
        // Current date is "2025-01-10" (2 days overdue)
        Date currentDate = new Date("2025-01-10 00:00:00");
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected: 2 days * $0.5 = 1.00
        assertEquals(1.00, result, 0.001);
    }
}