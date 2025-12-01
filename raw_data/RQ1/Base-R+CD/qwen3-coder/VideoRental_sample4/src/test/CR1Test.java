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
        Customer customer = new Customer();
        
        // Create a tape
        Tape tape = new Tape();
        tape.setId("V001");
        
        // Set rental date to 2025-01-01
        Date rentalDate = dateFormat.parse("2025-01-01");
        
        // Add rental (due date will be 5 days later: 2025-01-06)
        boolean result = customer.addVedioTapeRental(tape, rentalDate);
        assertTrue(result);
        
        // Get the rental and set return date to 2025-01-09 (3 days late)
        VideoRental rental = customer.getRentals().get(0);
        Date returnDate = dateFormat.parse("2025-01-09");
        rental.setReturnDate(returnDate);
        
        // Set due date to 2025-01-08 (so it's 1 day late)
        Date dueDate = dateFormat.parse("2025-01-08");
        rental.setDueDate(dueDate);
        
        // Calculate past due amount
        double pastDueAmount = rental.calculateOwedPastDueAmount(returnDate);
        
        // Expected: 1 day * $0.5 = $0.50
        assertEquals(0.5, pastDueAmount, 0.01);
    }
    
    @Test
    public void testCase2_unreturnedAnd3DaysOverdue() throws Exception {
        // Create a customer
        Customer customer = new Customer();
        
        // Create a tape
        Tape tape = new Tape();
        tape.setId("V002");
        
        // Set rental date to 2025-01-01
        Date rentalDate = dateFormat.parse("2025-01-01");
        
        // Add rental (due date will be 5 days later: 2025-01-06)
        boolean result = customer.addVedioTapeRental(tape, rentalDate);
        assertTrue(result);
        
        // Get the rental
        VideoRental rental = customer.getRentals().get(0);
        
        // Set current date to 2025-01-12 (6 days after rental, 4 days after due date)
        Date currentDate = dateFormat.parse("2025-01-12");
        
        // Calculate past due amount
        double pastDueAmount = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected: 4 days * $0.5 = $2.00
        assertEquals(2.0, pastDueAmount, 0.01);
    }
    
    @Test
    public void testCase3_returnedOnDueDate() throws Exception {
        // Create a customer
        Customer customer = new Customer();
        
        // Create a tape
        Tape tape = new Tape();
        tape.setId("V003");
        
        // Set rental date to 2025-01-01
        Date rentalDate = dateFormat.parse("2025-01-01");
        
        // Add rental
        boolean result = customer.addVedioTapeRental(tape, rentalDate);
        assertTrue(result);
        
        // Get the rental and set return date to 2025-01-06 (same as due date)
        VideoRental rental = customer.getRentals().get(0);
        Date returnDate = dateFormat.parse("2025-01-06");
        rental.setReturnDate(returnDate);
        Date dueDate = dateFormat.parse("2025-01-06");
        rental.setDueDate(dueDate);
        
        // Set current date to 2025-01-10 (after return)
        Date currentDate = dateFormat.parse("2025-01-10");
        
        // Calculate past due amount
        double pastDueAmount = rental.calculateOwedPastDueAmount(returnDate);
        
        // Expected: 0.00 (returned on due date)
        assertEquals(0.0, pastDueAmount, 0.01);
    }
    
    @Test
    public void testCase4_unreturnedButNotOverdue() throws Exception {
        // Create a customer
        Customer customer = new Customer();
        
        // Create a tape
        Tape tape = new Tape();
        tape.setId("V004");
        
        // Set rental date to 2025-01-05
        Date rentalDate = dateFormat.parse("2025-01-05");
        
        // Add rental
        boolean result = customer.addVedioTapeRental(tape, rentalDate);
        assertTrue(result);
        
        // Get the rental and set due date to 2025-01-10
        VideoRental rental = customer.getRentals().get(0);
        Date dueDate = dateFormat.parse("2025-01-10");
        rental.setDueDate(dueDate);
        
        // Set current date to 2025-01-06 (before due date)
        Date currentDate = dateFormat.parse("2025-01-06");
        
        // Calculate past due amount
        double pastDueAmount = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected: 0.00 (not yet overdue)
        assertEquals(0.0, pastDueAmount, 0.01);
    }
    
    @Test
    public void testCase5_returned5DaysLate() throws Exception {
        // Create a customer
        Customer customer = new Customer();
        
        // Create a tape
        Tape tape = new Tape();
        tape.setId("V005");
        
        // Set rental date to 2025-01-01
        Date rentalDate = dateFormat.parse("2025-01-01");
        
        // Add rental
        boolean result = customer.addVedioTapeRental(tape, rentalDate);
        assertTrue(result);
        
        // Get the rental and set due date to 2025-01-08
        VideoRental rental = customer.getRentals().get(0);
        Date dueDate = dateFormat.parse("2025-01-08");
        rental.setDueDate(dueDate);
        
        // Set current date to 2025-01-10 (2 days after due date)
        Date currentDate = dateFormat.parse("2025-01-10");
        
        // Calculate past due amount
        double pastDueAmount = rental.calculateOwedPastDueAmount(currentDate);
        
        // Expected: 2 days * $0.5 = $1.00
        assertEquals(1.0, pastDueAmount, 0.01);
    }
}