import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR2Test {
    
    private SimpleDateFormat dateFormat;
    private Map<String, Tape> tapes;
    private Map<String, Customer> customers;
    private Map<String, VideoRental> rentals;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        tapes = new HashMap<>();
        customers = new HashMap<>();
        rentals = new HashMap<>();
    }
    
    @Test
    public void testCase1_TapeIsAvailable() throws ParseException {
        // Input: tape_id="T001", current_date="2025-01-01"
        String tapeId = "T001";
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Setup: Create Tape with id="T001"
        Tape tape = new Tape();
        tape.setId(tapeId);
        tapes.put(tapeId, tape);
        
        // Setup: No active rentals for T001
        // Expected Output: True
        assertTrue("Tape should be available when no active rentals exist", 
                   tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() throws ParseException {
        // Input: tape_id="T002", current_date="2025-01-01"
        String tapeId = "T002";
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Setup: Create Tape with id="T002"
        Tape tape = new Tape();
        tape.setId(tapeId);
        tapes.put(tapeId, tape);
        
        // Setup: Create Customer C001
        Customer customer = new Customer();
        customer.setId("C001");
        customers.put("C001", customer);
        
        // Setup: C001 rents T002 with rental date="2024-12-28", due_date="2025-01-05", return_date=null (unreturned)
        Date rentalDate = dateFormat.parse("2024-12-28 00:00:00");
        Date dueDate = dateFormat.parse("2025-01-05 00:00:00");
        
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        rental.setReturnDate(null);
        
        customer.getRentals().add(rental);
        
        // Expected Output: False
        assertFalse("Tape should be unavailable when rented out and not returned", 
                    tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() throws ParseException {
        // Input: tape_id="T003", current_date="2025-01-01"
        String tapeId = "T003";
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Setup: Create Tape with id="T003"
        Tape tape = new Tape();
        tape.setId(tapeId);
        tapes.put(tapeId, tape);
        
        // Setup: Create Customer C002
        Customer customer = new Customer();
        customer.setId("C002");
        customers.put("C002", customer);
        
        // Setup: C002 rents T003 with rental date="2024-12-25", due_date="2024-12-30", return_date="2024-12-31"
        Date rentalDate = dateFormat.parse("2024-12-25 00:00:00");
        Date dueDate = dateFormat.parse("2024-12-30 00:00:00");
        Date returnDate = dateFormat.parse("2024-12-31 00:00:00");
        
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        rental.setReturnDate(returnDate);
        
        customer.getRentals().add(rental);
        
        // Expected Output: True
        assertTrue("Tape should be available when previously rented but returned", 
                   tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() throws ParseException {
        // Input: tape_id="T004", current_date="2025-01-10"
        String tapeId = "T004";
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        
        // Setup: Create Tape with id="T004"
        Tape tape = new Tape();
        tape.setId(tapeId);
        tapes.put(tapeId, tape);
        
        // Setup: Create Customer C003
        Customer customer = new Customer();
        customer.setId("C003");
        customers.put("C003", customer);
        
        // Setup: C003 rents T004 with rental date="2024-12-28", due_date="2025-01-01", return_date=null (unreturned)
        Date rentalDate = dateFormat.parse("2024-12-28 00:00:00");
        Date dueDate = dateFormat.parse("2025-01-01 00:00:00");
        
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        rental.setReturnDate(null);
        
        customer.getRentals().add(rental);
        
        // Expected Output: False
        assertFalse("Tape should be unavailable when overdue and not returned", 
                    tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() throws ParseException {
        // Input: tape_id="T005", current_date="2025-01-10"
        String tapeId = "T005";
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        
        // Setup: Create Tape with id="T005"
        Tape tape = new Tape();
        tape.setId(tapeId);
        tapes.put(tapeId, tape);
        
        // Setup: Create Customer C004, C005
        Customer customer1 = new Customer();
        customer1.setId("C004");
        customers.put("C004", customer1);
        
        Customer customer2 = new Customer();
        customer2.setId("C005");
        customers.put("C005", customer2);
        
        // Setup: C004 rents T005 with rental date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01"
        Date rentalDate1 = dateFormat.parse("2025-01-01 00:00:00");
        Date dueDate1 = dateFormat.parse("2025-01-05 00:00:00");
        Date returnDate1 = dateFormat.parse("2025-01-01 00:00:00");
        
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape);
        rental1.setDueDate(dueDate1);
        rental1.setReturnDate(returnDate1);
        customer1.getRentals().add(rental1);
        
        // First creation: True
        assertTrue("Tape should be available after first rental is returned", 
                   tape.isAvailable(currentDate));
        
        // Setup: C005 rents T005 with rental date="2025-01-06", due_date="2025-01-15", return_date=null
        Date rentalDate2 = dateFormat.parse("2025-01-06 00:00:00");
        Date dueDate2 = dateFormat.parse("2025-01-15 00:00:00");
        
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape);
        rental2.setDueDate(dueDate2);
        rental2.setReturnDate(null);
        customer2.getRentals().add(rental2);
        
        // Second creation: False
        assertFalse("Tape should be unavailable when second rental is active", 
                    tape.isAvailable(currentDate));
    }
}