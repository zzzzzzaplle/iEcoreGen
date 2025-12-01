import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR5Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_NoRentalsExist() throws ParseException {
        // Input: customer_id="C001"
        // Setup: 
        // 1. Create Customer C001 with empty rentals list
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Expected Output: Empty list, no active rentals.
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        assertNotNull("Unreturned tapes list should not be null", unreturnedTapes);
        assertEquals("Should return empty list when no rentals exist", 0, unreturnedTapes.size());
    }
    
    @Test
    public void testCase2_AllTapesReturned() throws ParseException {
        // Input: customer_id="C002"
        // Setup:
        // 1. Create Customer C002
        Customer customer = new Customer();
        customer.setId("C002");
        
        // 2. Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        
        // 3. Create VideoRental with rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01", associated with C002 and T001
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental.setReturnDate(dateFormat.parse("2025-01-01 00:00:00"));
        
        customer.getRentals().add(rental);
        
        // Expected Output: Empty list, all rentals returned.
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        assertNotNull("Unreturned tapes list should not be null", unreturnedTapes);
        assertEquals("Should return empty list when all tapes are returned", 0, unreturnedTapes.size());
    }
    
    @Test
    public void testCase3_OneUnreturnedTape() throws ParseException {
        // Input: customer_id="C003"
        // Setup:
        // 1. Create Customer C003
        Customer customer = new Customer();
        customer.setId("C003");
        
        // 2. Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        
        // 3. Create VideoRental with rental_date="2025-01-01", due_date="2025-01-05", return_date=null, associated with C003 and T001
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental.setReturnDate(null);
        
        customer.getRentals().add(rental);
        
        // Expected Output: List containing T001.
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        assertNotNull("Unreturned tapes list should not be null", unreturnedTapes);
        assertEquals("Should return list with one unreturned tape", 1, unreturnedTapes.size());
        assertEquals("Tape ID should be T001", "T001", unreturnedTapes.get(0).getId());
    }
    
    @Test
    public void testCase4_MixedReturnedUnreturned() throws ParseException {
        // Input: customer_id="C004"
        // Setup:
        // 1. Create Customer C004
        Customer customer = new Customer();
        customer.setId("C004");
        
        // 2. Create Tape T001 and T002
        Tape tape1 = new Tape();
        tape1.setId("T001");
        
        Tape tape2 = new Tape();
        tape2.setId("T002");
        
        // 3. Create VideoRental for T001 with rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01", associated with C004
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-01 00:00:00"));
        
        // 4. Create VideoRental for T002 with rental_date="2025-01-02", due_date="2025-01-06", return_date=null, associated with C004
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-06 00:00:00"));
        rental2.setReturnDate(null);
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Expected Output: List containing T002.
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        assertNotNull("Unreturned tapes list should not be null", unreturnedTapes);
        assertEquals("Should return list with one unreturned tape", 1, unreturnedTapes.size());
        assertEquals("Tape ID should be T002", "T002", unreturnedTapes.get(0).getId());
    }
    
    @Test
    public void testCase5_MultipleUnreturnedTapes() throws ParseException {
        // Input: customer_id="C005"
        // Setup:
        // 1. Create Customer C005
        Customer customer = new Customer();
        customer.setId("C005");
        
        // 2. Create Tapes T001 and T002
        Tape tape1 = new Tape();
        tape1.setId("T001");
        
        Tape tape2 = new Tape();
        tape2.setId("T002");
        
        // 3. Create VideoRental for T001 with rental_date="2025-01-01", due_date="2025-01-05", return_date=null, associated with C005
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(null);
        
        // 4. Create VideoRental for T002 with rental_date="2025-01-02", due_date="2025-01-06", return_date=null, associated with C005
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-06 00:00:00"));
        rental2.setReturnDate(null);
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Expected Output: List containing T001 and T002.
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        assertNotNull("Unreturned tapes list should not be null", unreturnedTapes);
        assertEquals("Should return list with two unreturned tapes", 2, unreturnedTapes.size());
        
        // Verify both tapes are in the list (order may vary)
        Set<String> tapeIds = new HashSet<>();
        for (Tape tape : unreturnedTapes) {
            tapeIds.add(tape.getId());
        }
        assertTrue("Should contain T001", tapeIds.contains("T001"));
        assertTrue("Should contain T002", tapeIds.contains("T002"));
    }
}