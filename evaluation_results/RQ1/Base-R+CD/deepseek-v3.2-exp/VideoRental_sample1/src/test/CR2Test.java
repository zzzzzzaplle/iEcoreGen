import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR2Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_TapeIsAvailable() throws ParseException {
        // Setup: Create Tape with id="T001", no active rentals
        Tape tape = new Tape();
        tape.setId("T001");
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Expected Output: True
        assertTrue("Tape T001 should be available when no active rentals exist", 
                   tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() throws ParseException {
        // Setup: Create Tape with id="T002", Create Customer C001
        Tape tape = new Tape();
        tape.setId("T002");
        
        Customer customer = new Customer();
        customer.setId("C001");
        
        // C001 rents T002 with rental date="2024-12-28", due_date="2025-01-05", return_date=null
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental.setReturnDate(null);
        
        // Add rental to tape's rentals list to simulate active rental
        tape.getRentals().add(rental);
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Expected Output: False
        assertFalse("Tape T002 should be unavailable when it has an active rental with null return date", 
                    tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() throws ParseException {
        // Setup: Create Tape with id="T003", Create Customer C002
        Tape tape = new Tape();
        tape.setId("T003");
        
        Customer customer = new Customer();
        customer.setId("C002");
        
        // C002 rents T003 with rental date="2024-12-25", due_date="2024-12-30", return_date="2024-12-31"
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2024-12-30 00:00:00"));
        rental.setReturnDate(dateFormat.parse("2024-12-31 00:00:00"));
        
        // Add rental to tape's rentals list
        tape.getRentals().add(rental);
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Expected Output: True
        assertTrue("Tape T003 should be available when all rentals have return dates", 
                   tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() throws ParseException {
        // Setup: Create Tape with id="T004", Create Customer C003
        Tape tape = new Tape();
        tape.setId("T004");
        
        Customer customer = new Customer();
        customer.setId("C003");
        
        // C003 rents T004 with rental date="2024-12-28", due_date="2025-01-01", return_date=null
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2025-01-01 00:00:00"));
        rental.setReturnDate(null);
        
        // Add rental to tape's rentals list
        tape.getRentals().add(rental);
        
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        
        // Expected Output: False
        assertFalse("Tape T004 should be unavailable when it has an overdue rental with null return date", 
                    tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() throws ParseException {
        // Setup: Create Tape with id="T005", Create Customer C004, C005
        Tape tape = new Tape();
        tape.setId("T005");
        
        Customer customer1 = new Customer();
        customer1.setId("C004");
        
        Customer customer2 = new Customer();
        customer2.setId("C005");
        
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        
        // First rental: C004 rents T005 with rental date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01"
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-01 00:00:00"));
        
        // Add first rental to tape's rentals list
        tape.getRentals().add(rental1);
        
        // Check availability after first rental - should be True (tape was returned)
        assertTrue("Tape T005 should be available after first customer returned it", 
                   tape.isAvailable(currentDate));
        
        // Second rental: C005 rents T005 with rental date="2025-01-06", due_date="2025-01-15", return_date=null
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape);
        rental2.setDueDate(dateFormat.parse("2025-01-15 00:00:00"));
        rental2.setReturnDate(null);
        
        // Add second rental to tape's rentals list
        tape.getRentals().add(rental2);
        
        // Check availability after second rental - should be False (tape is currently rented)
        assertFalse("Tape T005 should be unavailable when second customer has active rental", 
                    tape.isAvailable(currentDate));
    }
}