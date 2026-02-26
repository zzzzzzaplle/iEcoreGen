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
        
        Date rentalDate = dateFormat.parse("2025-01-01");
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        
        // Set due date (7 days after rental)
        Date dueDate = dateFormat.parse("2025-01-08");
        rental.setDueDate(dueDate);
        
        // Set return date (1 day late)
        Date returnDate = dateFormat.parse("2025-01-09");
        rental.setReturnDate(returnDate);
        
        c1.getRentals().add(rental);
        
        // Test calculation with current date (not used since return date is set)
        Date currentDate = dateFormat.parse("2025-01-10");
        double result = c1.calculateTotalPastDueAmount(currentDate);
        
        // Verify
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() throws Exception {
        // Setup
        Customer c2 = new Customer();
        Tape tape = new Tape();
        tape.setId("V002");
        
        Date rentalDate = dateFormat.parse("2025-01-01");
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        
        // Set due date (7 days after rental)
        Date dueDate = dateFormat.parse("2025-01-08");
        rental.setDueDate(dueDate);
        
        // Return date remains null (unreturned)
        c2.getRentals().add(rental);
        
        // Test calculation with current date 3 days overdue
        Date currentDate = dateFormat.parse("2025-01-11");
        double result = c1.calculateTotalPastDueAmount(currentDate);
        
        // Verify
        assertEquals(1.50, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() throws Exception {
        // Setup
        Customer c3 = new Customer();
        Tape tape = new Tape();
        tape.setId("V003");
        
        Date rentalDate = dateFormat.parse("2025-01-01");
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        
        // Set due date
        Date dueDate = dateFormat.parse("2025-01-06");
        rental.setDueDate(dueDate);
        
        // Set return date (on due date)
        Date returnDate = dateFormat.parse("2025-01-06");
        rental.setReturnDate(returnDate);
        
        c3.getRentals().add(rental);
        
        // Test calculation with current date
        Date currentDate = dateFormat.parse("2025-01-10");
        double result = c3.calculateTotalPastDueAmount(currentDate);
        
        // Verify
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() throws Exception {
        // Setup
        Customer c4 = new Customer();
        Tape tape = new Tape();
        tape.setId("V004");
        
        Date rentalDate = dateFormat.parse("2025-01-05");
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        
        // Set due date (7 days after rental)
        Date dueDate = dateFormat.parse("2025-01-12");
        rental.setDueDate(dueDate);
        
        // Return date remains null (unreturned)
        c4.getRentals().add(rental);
        
        // Test calculation with current date before due date
        Date currentDate = dateFormat.parse("2025-01-06");
        double result = c4.calculateTotalPastDueAmount(currentDate);
        
        // Verify
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_ReturnedFiveDaysLate() throws Exception {
        // Setup
        Customer c5 = new Customer();
        Tape tape = new Tape();
        tape.setId("V005");
        
        Date rentalDate = dateFormat.parse("2025-01-01");
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        
        // Set due date (7 days after rental)
        Date dueDate = dateFormat.parse("2025-01-08");
        rental.setDueDate(dueDate);
        
        // Return date remains null (unreturned)
        c5.getRentals().add(rental);
        
        // Test calculation with current date 5 days overdue
        Date currentDate = dateFormat.parse("2025-01-13");
        double result = c5.calculateTotalPastDueAmount(currentDate);
        
        // Verify - 5 days overdue: 5 * $0.5 = $2.50
        assertEquals(2.50, result, 0.001);
    }
}