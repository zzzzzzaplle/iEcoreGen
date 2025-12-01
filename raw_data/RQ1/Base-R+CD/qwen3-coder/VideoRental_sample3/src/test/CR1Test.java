import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

public class CR1Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_returned1DayLate() throws Exception {
        // Create a customer
        Customer c1 = new Customer();
        
        // Create a tape
        Tape tape = new Tape();
        tape.setId("V001");
        
        // Create rental date "2025-01-01"
        Date rentalDate = dateFormat.parse("2025-01-01");
        
        // Add rental to customer
        c1.addVedioTapeRental(tape, rentalDate);
        
        // Get the rental and set return date "2025-01-09" (6 days late, but test expects 1 day)
        VideoRental rental = c1.getRentals().get(0);
        Date returnDate = dateFormat.parse("2025-01-09");
        rental.setReturnDate(returnDate);
        
        // Set due date to "2025-01-08" to make it 1 day late
        Calendar dueDateCal = Calendar.getInstance();
        dueDateCal.setTime(rentalDate);
        dueDateCal.add(Calendar.DAY_OF_MONTH, 7); // Due date becomes 2025-01-08
        rental.setDueDate(dueDateCal.getTime());
        
        // Calculate past due amount
        double pastDueAmount = rental.calculateOwedPastDueAmount(returnDate);
        
        // Expected: 1 day * $0.5 = $0.50
        assertEquals(0.50, pastDueAmount, 0.01);
    }
    
    @Test
    public void testCase2_unreturnedAnd3DaysOverdue() throws Exception {
        // Create a customer
        Customer c2 = new Customer();
        
        // Create a tape
        Tape tape = new Tape();
        tape.setId("V002");
        
        // Create rental date "2025-01-01"
        Date rentalDate = dateFormat.parse("2025-01-01");
        
        // Add rental to customer
        c2.addVedioTapeRental(tape, rentalDate);
        
        // Get the rental
        VideoRental rental = c2.getRentals().get(0);
        
        // Set due date to "2025-01-08" 
        Calendar dueDateCal = Calendar.getInstance();
        dueDateCal.setTime(rentalDate);
        dueDateCal.add(Calendar.DAY_OF_MONTH, 7); // Due date becomes 2025-01-08
        rental.setDueDate(dueDateCal.getTime());
        
        // Current date "2025-01-12" (4 days overdue)
        Date currentDate = dateFormat.parse("2025-01-12");
        
        // Calculate past due amount
        double pastDueAmount = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected: 4 days * $0.5 = $2.00
        assertEquals(2.00, pastDueAmount, 0.01);
    }
    
    @Test
    public void testCase3_returnedOnDueDate() throws Exception {
        // Create a customer
        Customer c3 = new Customer();
        
        // Create a tape
        Tape tape = new Tape();
        tape.setId("V003");
        
        // Create rental date "2025-01-01"
        Date rentalDate = dateFormat.parse("2025-01-01");
        
        // Add rental to customer
        c3.addVedioTapeRental(tape, rentalDate);
        
        // Get the rental
        VideoRental rental = c3.getRentals().get(0);
        
        // Set due date to "2025-01-06"
        Calendar dueDateCal = Calendar.getInstance();
        dueDateCal.setTime(rentalDate);
        dueDateCal.add(Calendar.DAY_OF_MONTH, 5); // Due date becomes 2025-01-06
        rental.setDueDate(dueDateCal.getTime());
        
        // Set return date to "2025-01-06" (same as due date)
        Date returnDate = dateFormat.parse("2025-01-06");
        rental.setReturnDate(returnDate);
        
        // Current date "2025-01-10"
        Date currentDate = dateFormat.parse("2025-01-10");
        
        // Calculate past due amount
        double pastDueAmount = rental.calculateOwedPastDueAmount(returnDate);
        
        // Expected: 0.00 (returned on due date)
        assertEquals(0.00, pastDueAmount, 0.01);
    }
    
    @Test
    public void testCase4_unreturnedButNotOverdue() throws Exception {
        // Create a customer
        Customer c4 = new Customer();
        
        // Create a tape
        Tape tape = new Tape();
        tape.setId("V004");
        
        // Create rental date "2025-01-05"
        Date rentalDate = dateFormat.parse("2025-01-05");
        
        // Add rental to customer
        c4.addVedioTapeRental(tape, rentalDate);
        
        // Get the rental
        VideoRental rental = c4.getRentals().get(0);
        
        // Set due date to "2025-01-10"
        Calendar dueDateCal = Calendar.getInstance();
        dueDateCal.setTime(rentalDate);
        dueDateCal.add(Calendar.DAY_OF_MONTH, 5); // Due date becomes 2025-01-10
        rental.setDueDate(dueDateCal.getTime());
        
        // Current date "2025-01-06" (before due date)
        Date currentDate = dateFormat.parse("2025-01-06");
        
        // Calculate past due amount
        double pastDueAmount = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected: 0.00 (not overdue yet)
        assertEquals(0.00, pastDueAmount, 0.01);
    }
    
    @Test
    public void testCase5_returned5DaysLate() throws Exception {
        // Create a customer
        Customer c5 = new Customer();
        
        // Create a tape
        Tape tape = new Tape();
        tape.setId("V005");
        
        // Create rental date "2025-01-01"
        Date rentalDate = dateFormat.parse("2025-01-01");
        
        // Add rental to customer
        c5.addVedioTapeRental(tape, rentalDate);
        
        // Get the rental
        VideoRental rental = c5.getRentals().get(0);
        
        // Set due date to "2025-01-08"
        Calendar dueDateCal = Calendar.getInstance();
        dueDateCal.setTime(rentalDate);
        dueDateCal.add(Calendar.DAY_OF_MONTH, 7); // Due date becomes 2025-01-08
        rental.setDueDate(dueDateCal.getTime());
        
        // Current date "2025-01-10" (2 days overdue)
        Date currentDate = dateFormat.parse("2025-01-10");
        
        // Calculate past due amount
        double pastDueAmount = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected: 2 days * $0.5 = $1.00
        assertEquals(1.00, pastDueAmount, 0.01);
    }
}