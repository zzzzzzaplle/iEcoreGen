import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR1Test {
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_ReturnedOneDayLate() throws Exception {
        // Setup
        Customer c1 = new Customer();
        Tape tape = new Tape();
        tape.setId("V001");
        
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        Date rentalDate = dateFormat.parse("2025-01-01");
        rental.setDueDate(rentalDate);
        
        Date returnDate = dateFormat.parse("2025-01-02");
        rental.setReturnDate(returnDate);
        
        // Calculate past due amount with current date (not used when return date is set)
        double result = rental.calculateOwedPastDueAmount(dateFormat.parse("2025-01-10"));
        
        // Verify
        assertEquals("Returned 1 day late should result in $0.50 fee", 0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() throws Exception {
        // Setup
        Customer c2 = new Customer();
        Tape tape = new Tape();
        tape.setId("V002");
        
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        Date rentalDate = dateFormat.parse("2025-01-01");
        rental.setDueDate(rentalDate);
        // Return date remains null (unreturned)
        
        Date currentDate = dateFormat.parse("2025-01-12");
        
        // Calculate past due amount
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Verify
        assertEquals("Unreturned and 3 days overdue should result in $1.50 fee", 1.50, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() throws Exception {
        // Setup
        Customer c3 = new Customer();
        Tape tape = new Tape();
        tape.setId("V003");
        
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        Date rentalDate = dateFormat.parse("2025-01-01");
        rental.setDueDate(rentalDate);
        
        Date returnDate = dateFormat.parse("2025-01-06");
        rental.setReturnDate(returnDate);
        
        // Calculate past due amount with current date (not used when return date is set)
        double result = rental.calculateOwedPastDueAmount(dateFormat.parse("2025-01-10"));
        
        // Verify
        assertEquals("Returned on due date should result in $0.00 fee", 0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() throws Exception {
        // Setup
        Customer c4 = new Customer();
        Tape tape = new Tape();
        tape.setId("V004");
        
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        Date rentalDate = dateFormat.parse("2025-01-05");
        rental.setDueDate(rentalDate);
        // Return date remains null (unreturned)
        
        Date currentDate = dateFormat.parse("2025-01-06");
        
        // Calculate past due amount
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Verify
        assertEquals("Unreturned but not overdue should result in $0.00 fee", 0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_ReturnedFiveDaysLate() throws Exception {
        // Setup
        Customer c5 = new Customer();
        Tape tape = new Tape();
        tape.setId("V005");
        
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        Date rentalDate = dateFormat.parse("2025-01-01");
        rental.setDueDate(rentalDate);
        // Return date remains null (unreturned)
        
        Date currentDate = dateFormat.parse("2025-01-10");
        
        // Calculate past due amount
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Verify
        assertEquals("Unreturned and 6 days overdue should result in $3.00 fee", 3.00, result, 0.001);
    }
}