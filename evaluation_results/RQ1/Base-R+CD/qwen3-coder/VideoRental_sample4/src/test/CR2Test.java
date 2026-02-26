import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR2Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_tapeIsAvailable() throws ParseException {
        // Input: tape_id="T001", current_date="2025-01-01"
        // Setup:
        // 1. Create Tape with id="T001"
        // 2. No active rentals for T001
        // Expected Output: True
        
        Tape tape = new Tape();
        tape.setId("T001");
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Since there are no active rentals, tape should be available
        boolean result = tape.isAvailable(currentDate);
        assertTrue(result);
    }
    
    @Test
    public void testCase2_tapeIsRentedOut() throws ParseException {
        // Input: tape_id="T002", current_date="2025-01-01"
        // Setup:
        // 1. Create Tape with id="T002". Create Customer C001. 
        // 2. C001 rents T002 with rental date="2024-12-28", due_date="2025-01-05", return_date=null (unreturned)
        // Expected Output: False
        
        Tape tape = new Tape();
        tape.setId("T002");
        
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Create rental with return_date = null (unreturned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        Date dueDate = dateFormat.parse("2025-01-05 00:00:00");
        rental.setDueDate(dueDate);
        rental.setReturnDate(null); // Not returned
        
        customer.getRentals().add(rental);
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Since tape is rented out and not returned, it should be unavailable
        boolean result = tape.isAvailable(currentDate);
        assertFalse(result);
    }
    
    @Test
    public void testCase3_tapeWasRentedButReturned() throws ParseException {
        // Input: tape_id="T003", current_date="2025-01-01"
        // Setup:
        // 1. Create Tape with id="T003". Create Customer C002.
        // 2. C002 rents T003 with rental date="2024-12-25", due_date="2024-12-30", return_date="2024-12-31"
        // Expected Output: True
        
        Tape tape = new Tape();
        tape.setId("T003");
        
        Customer customer = new Customer();
        customer.setId("C002");
        
        // Create rental with return_date set (returned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        Date dueDate = dateFormat.parse("2024-12-30 00:00:00");
        rental.setDueDate(dueDate);
        Date returnDate = dateFormat.parse("2024-12-31 00:00:00");
        rental.setReturnDate(returnDate);
        
        customer.getRentals().add(rental);
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Since tape was returned, it should be available
        boolean result = tape.isAvailable(currentDate);
        assertTrue(result);
    }
    
    @Test
    public void testCase4_tapeExistsButHasOverdueRental() throws ParseException {
        // Input: tape_id="T004", current_date="2025-01-10"
        // Setup:
        // 1. Create Tape with id="T004". Create Customer C003.
        // 2. C003 rents T004 with rental date="2024-12-28", due_date="2025-01-01", return_date=null (unreturned)
        // Expected Output: False
        
        Tape tape = new Tape();
        tape.setId("T004");
        
        Customer customer = new Customer();
        customer.setId("C003");
        
        // Create rental with return_date = null (unreturned) and past due date
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        Date dueDate = dateFormat.parse("2025-01-01 00:00:00");
        rental.setDueDate(dueDate);
        rental.setReturnDate(null); // Not returned
        
        customer.getRentals().add(rental);
        
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        
        // Since tape is rented out and not returned, it should be unavailable
        boolean result = tape.isAvailable(currentDate);
        assertFalse(result);
    }
    
    @Test
    public void testCase5_tapeWasRentedButReturnedByMultipleCustomers() throws ParseException {
        // Input: tape_id="T005", current_date="2025-01-10"
        // Setup: 
        // 1. Create Tape with id="T005". Create Customer C004, C005.
        // 2. C004 rents T005 with rental date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01" → first rental
        // 3. C005 rents T005 with rental date="2025-01-06", due_date="2025-01-15", return_date=null → second rental
        // Expected Output: The first creation: True. The second creation: False.
        
        Tape tape = new Tape();
        tape.setId("T005");
        
        // First rental - returned
        Customer customer1 = new Customer();
        customer1.setId("C004");
        
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape);
        Date dueDate1 = dateFormat.parse("2025-01-05 00:00:00");
        rental1.setDueDate(dueDate1);
        Date returnDate1 = dateFormat.parse("2025-01-01 00:00:00");
        rental1.setReturnDate(returnDate1);
        
        customer1.getRentals().add(rental1);
        
        // Second rental - not returned
        Customer customer2 = new Customer();
        customer2.setId("C005");
        
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape);
        Date dueDate2 = dateFormat.parse("2025-01-15 00:00:00");
        rental2.setDueDate(dueDate2);
        rental2.setReturnDate(null); // Not returned
        
        customer2.getRentals().add(rental2);
        
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        
        // Since tape is currently rented out (second rental not returned), it should be unavailable
        boolean result = tape.isAvailable(currentDate);
        assertFalse(result);
    }
}