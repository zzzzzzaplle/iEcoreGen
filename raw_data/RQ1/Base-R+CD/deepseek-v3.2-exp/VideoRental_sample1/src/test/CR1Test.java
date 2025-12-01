import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.util.Date;

public class CR1Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_Returned1DayLate() throws ParseException {
        // Setup: Create a customer c1
        Customer c1 = new Customer();
        c1.setId("c1");
        
        // Setup: Create VideoRental V001 with rental date "2025-01-01"
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V001");
        rental.setTape(tape);
        
        // Set rental date to "2025-01-01" and due date to "2025-01-08" (7 days later)
        Date rentalDate = dateFormat.parse("2025-01-01 00:00:00");
        Calendar cal = Calendar.getInstance();
        cal.setTime(rentalDate);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        Date dueDate = cal.getTime();
        rental.setDueDate(dueDate);
        
        // Set return date to "2025-01-09" (1 day late)
        Date returnDate = dateFormat.parse("2025-01-09 00:00:00");
        rental.setReturnDate(returnDate);
        
        // Add rental to customer
        c1.getRentals().add(rental);
        
        // Calculate past due amount with current date (not used since return date is set)
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected Output: 0.50 (1 day * $0.5)
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAnd3DaysOverdue() throws ParseException {
        // Setup: Create a customer c2
        Customer c2 = new Customer();
        c2.setId("c2");
        
        // Setup: Create VideoRental V002 with rental date "2025-01-01"
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V002");
        rental.setTape(tape);
        
        // Set rental date to "2025-01-01" and due date to "2025-01-08" (7 days later)
        Date rentalDate = dateFormat.parse("2025-01-01 00:00:00");
        Calendar cal = Calendar.getInstance();
        cal.setTime(rentalDate);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        Date dueDate = cal.getTime();
        rental.setDueDate(dueDate);
        
        // Return date is null (unreturned)
        rental.setReturnDate(null);
        
        // Add rental to customer
        c2.getRentals().add(rental);
        
        // Calculate past due amount with current date "2025-01-12"
        Date currentDate = dateFormat.parse("2025-01-12 00:00:00");
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected Output: 2.00 (4 days * $0.5) - 2025-01-12 minus 2025-01-08 = 4 days
        assertEquals(2.00, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() throws ParseException {
        // Setup: Create a customer c3
        Customer c3 = new Customer();
        c3.setId("c3");
        
        // Setup: Create VideoRental V003 with rental date "2025-01-01"
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V003");
        rental.setTape(tape);
        
        // Set rental date to "2025-01-01" and due date to "2025-01-06" (5 days later - different from standard 7)
        Date rentalDate = dateFormat.parse("2025-01-01 00:00:00");
        Calendar cal = Calendar.getInstance();
        cal.setTime(rentalDate);
        cal.add(Calendar.DAY_OF_MONTH, 5);
        Date dueDate = cal.getTime();
        rental.setDueDate(dueDate);
        
        // Set return date to "2025-01-06" (on due date)
        Date returnDate = dateFormat.parse("2025-01-06 00:00:00");
        rental.setReturnDate(returnDate);
        
        // Add rental to customer
        c3.getRentals().add(rental);
        
        // Calculate past due amount with current date "2025-01-10"
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected Output: 0.00 (returned on due date)
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() throws ParseException {
        // Setup: Create a customer c4
        Customer c4 = new Customer();
        c4.setId("c4");
        
        // Setup: Create VideoRental V004 with rental date "2025-01-05"
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V004");
        rental.setTape(tape);
        
        // Set rental date to "2025-01-05" and due date to "2025-01-10" (5 days later)
        Date rentalDate = dateFormat.parse("2025-01-05 00:00:00");
        Calendar cal = Calendar.getInstance();
        cal.setTime(rentalDate);
        cal.add(Calendar.DAY_OF_MONTH, 5);
        Date dueDate = cal.getTime();
        rental.setDueDate(dueDate);
        
        // Return date is null (unreturned)
        rental.setReturnDate(null);
        
        // Add rental to customer
        c4.getRentals().add(rental);
        
        // Calculate past due amount with current date "2025-01-06"
        Date currentDate = dateFormat.parse("2025-01-06 00:00:00");
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected Output: 0.00 (current date before due date)
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_Returned5DaysLate() throws ParseException {
        // Setup: Create a customer c5
        Customer c5 = new Customer();
        c5.setId("c5");
        
        // Setup: Create VideoRental V005 with rental date "2025-01-01"
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V005");
        rental.setTape(tape);
        
        // Set rental date to "2025-01-01" and due date to "2025-01-08" (7 days later)
        Date rentalDate = dateFormat.parse("2025-01-01 00:00:00");
        Calendar cal = Calendar.getInstance();
        cal.setTime(rentalDate);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        Date dueDate = cal.getTime();
        rental.setDueDate(dueDate);
        
        // Return date is null (unreturned)
        rental.setReturnDate(null);
        
        // Add rental to customer
        c5.getRentals().add(rental);
        
        // Calculate past due amount with current date "2025-01-10"
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected Output: 1.00 (2 days * $0.5) - 2025-01-10 minus 2025-01-08 = 2 days
        assertEquals(1.00, result, 0.001);
    }
}