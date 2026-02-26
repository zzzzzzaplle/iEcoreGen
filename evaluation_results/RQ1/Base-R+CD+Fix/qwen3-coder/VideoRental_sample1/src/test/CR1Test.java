import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR1Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_ReturnedOneDayLate() throws ParseException {
        // Setup: Create a customer c1
        Customer c1 = new Customer();
        
        // Create VideoRental V001 with rental date "2025-01-01"
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V001");
        
        Date rentalDate = dateFormat.parse("2025-01-01");
        Date dueDate = new Date(rentalDate.getTime() + 3 * 24 * 60 * 60 * 1000L); // 3 days later
        
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        
        // Set return_date="2025-01-09"
        Date returnDate = dateFormat.parse("2025-01-09");
        rental.setReturnDate(returnDate);
        
        // Calculate past-due amount
        double result = rental.calculateOwedPastDueAmount(null);
        
        // Expected Output: 0.50 (1 day * $0.5)
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() throws ParseException {
        // Setup: Create a customer c2
        Customer c2 = new Customer();
        
        // Create VideoRental V002 with rental date "2025-01-01"
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V002");
        
        Date rentalDate = dateFormat.parse("2025-01-01");
        Date dueDate = new Date(rentalDate.getTime() + 3 * 24 * 60 * 60 * 1000L); // 3 days later
        
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        rental.setReturnDate(null); // Unreturned
        
        // Current date is "2025-01-12"
        Date currentDate = dateFormat.parse("2025-01-12");
        
        // Calculate past-due amount
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected Output: 1.50 (3 days * $0.5)
        assertEquals(1.50, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() throws ParseException {
        // Setup: Create a customer c3
        Customer c3 = new Customer();
        
        // Create VideoRental V003 with rental date "2025-01-01"
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V003");
        
        Date rentalDate = dateFormat.parse("2025-01-01");
        Date dueDate = new Date(rentalDate.getTime() + 3 * 24 * 60 * 60 * 1000L); // 3 days later
        
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        
        // Set return_date="2025-01-06" (which should be the due date based on rental date + 3 days)
        Date returnDate = dateFormat.parse("2025-01-06");
        rental.setReturnDate(returnDate);
        
        // Current date is "2025-01-10" (not used since tape is returned)
        Date currentDate = dateFormat.parse("2025-01-10");
        
        // Calculate past-due amount
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected Output: 0.00 (returned on due date)
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() throws ParseException {
        // Setup: Create a customer c4
        Customer c4 = new Customer();
        
        // Create VideoRental V004 with rental date "2025-01-05"
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V004");
        
        Date rentalDate = dateFormat.parse("2025-01-05");
        Date dueDate = new Date(rentalDate.getTime() + 3 * 24 * 60 * 60 * 1000L); // 3 days later
        
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        rental.setReturnDate(null); // Unreturned
        
        // Current date is "2025-01-06" (before due date)
        Date currentDate = dateFormat.parse("2025-01-06");
        
        // Calculate past-due amount
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected Output: 0.00 (not overdue yet)
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_ReturnedFiveDaysLate() throws ParseException {
        // Setup: Create a customer c5
        Customer c5 = new Customer();
        
        // Create VideoRental V005 with rental date "2025-01-01"
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V005");
        
        Date rentalDate = dateFormat.parse("2025-01-01");
        Date dueDate = new Date(rentalDate.getTime() + 3 * 24 * 60 * 60 * 1000L); // 3 days later
        
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        rental.setReturnDate(null); // Unreturned
        
        // Current date is "2025-01-10"
        Date currentDate = dateFormat.parse("2025-01-10");
        
        // Calculate past-due amount
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected Output: 3.00 (6 days * $0.5)
        assertEquals(3.00, result, 0.001);
    }
}