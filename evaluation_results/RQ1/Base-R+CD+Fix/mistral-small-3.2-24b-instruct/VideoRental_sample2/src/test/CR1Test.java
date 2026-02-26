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
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V001");
        
        Date rentalDate = dateFormat.parse("2025-01-01");
        Date returnDate = dateFormat.parse("2025-01-09");
        Date dueDate = dateFormat.parse("2025-01-08"); // 7 days after rental (2025-01-01 + 7 days)
        
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        rental.setReturnDate(returnDate);
        
        c1.getRentals().add(rental);
        
        // Test calculation with current date (not used since return date is set)
        Date currentDate = dateFormat.parse("2025-01-10");
        double result = c1.calculateTotalPastDueAmount(currentDate);
        
        // Expected: 1 day * $0.5 = 0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() throws Exception {
        // Setup
        Customer c2 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V002");
        
        Date rentalDate = dateFormat.parse("2025-01-01");
        Date dueDate = dateFormat.parse("2025-01-08"); // 7 days after rental (2025-01-01 + 7 days)
        
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        // returnDate remains null (unreturned)
        
        c2.getRentals().add(rental);
        
        // Test calculation with current date 3 days after due date
        Date currentDate = dateFormat.parse("2025-01-12");
        double result = c2.calculateTotalPastDueAmount(currentDate);
        
        // Expected: 4 days * $0.5 = 2.00 (2025-01-08 to 2025-01-12 is 4 days)
        assertEquals(2.00, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() throws Exception {
        // Setup
        Customer c3 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V003");
        
        Date rentalDate = dateFormat.parse("2025-01-01");
        Date dueDate = dateFormat.parse("2025-01-08"); // 7 days after rental (2025-01-01 + 7 days)
        Date returnDate = dateFormat.parse("2025-01-08"); // Returned on due date
        
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        rental.setReturnDate(returnDate);
        
        c3.getRentals().add(rental);
        
        // Test calculation with current date
        Date currentDate = dateFormat.parse("2025-01-10");
        double result = c3.calculateTotalPastDueAmount(currentDate);
        
        // Expected: 0.00 (returned on due date)
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() throws Exception {
        // Setup
        Customer c4 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V004");
        
        Date rentalDate = dateFormat.parse("2025-01-05");
        Date dueDate = dateFormat.parse("2025-01-12"); // 7 days after rental (2025-01-05 + 7 days)
        
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        // returnDate remains null (unreturned)
        
        c4.getRentals().add(rental);
        
        // Test calculation with current date before due date
        Date currentDate = dateFormat.parse("2025-01-06");
        double result = c4.calculateTotalPastDueAmount(currentDate);
        
        // Expected: 0.00 (not overdue yet)
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_ReturnedFiveDaysLate() throws Exception {
        // Setup
        Customer c5 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V005");
        
        Date rentalDate = dateFormat.parse("2025-01-01");
        Date dueDate = dateFormat.parse("2025-01-08"); // 7 days after rental (2025-01-01 + 7 days)
        
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        // returnDate remains null (unreturned)
        
        c5.getRentals().add(rental);
        
        // Test calculation with current date 2 days after due date
        Date currentDate = dateFormat.parse("2025-01-10");
        double result = c5.calculateTotalPastDueAmount(currentDate);
        
        // Expected: 2 days * $0.5 = 1.00 (2025-01-08 to 2025-01-10 is 2 days)
        assertEquals(1.00, result, 0.001);
    }
}