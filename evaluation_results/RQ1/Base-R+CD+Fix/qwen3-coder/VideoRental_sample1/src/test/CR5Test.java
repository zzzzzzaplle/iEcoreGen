import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CR5Test {
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_NoRentalsExist() throws ParseException {
        // Setup: Create Customer C001 with empty rentals list
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: Empty list, no active rentals
        assertTrue("Expected empty list when no rentals exist", result.isEmpty());
    }
    
    @Test
    public void testCase2_AllTapesReturned() throws ParseException {
        // Setup
        Customer customer = new Customer();
        customer.setId("C002");
        
        Tape tape = new Tape();
        tape.setId("T001");
        
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental.setReturnDate(dateFormat.parse("2025-01-01 00:00:00"));
        
        customer.getRentals().add(rental);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: Empty list, all rentals returned
        assertTrue("Expected empty list when all tapes are returned", result.isEmpty());
    }
    
    @Test
    public void testCase3_OneUnreturnedTape() throws ParseException {
        // Setup
        Customer customer = new Customer();
        customer.setId("C003");
        
        Tape tape = new Tape();
        tape.setId("T001");
        
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental.setReturnDate(null); // Tape not returned
        
        customer.getRentals().add(rental);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: List containing T001
        assertEquals("Expected 1 unreturned tape", 1, result.size());
        assertEquals("Expected tape T001 in result", "T001", result.get(0).getId());
    }
    
    @Test
    public void testCase4_MixedReturnedUnreturned() throws ParseException {
        // Setup
        Customer customer = new Customer();
        customer.setId("C004");
        
        // Create Tape T001 (returned)
        Tape tape1 = new Tape();
        tape1.setId("T001");
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-01 00:00:00"));
        
        // Create Tape T002 (unreturned)
        Tape tape2 = new Tape();
        tape2.setId("T002");
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-06 00:00:00"));
        rental2.setReturnDate(null); // Tape not returned
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: List containing T002 only
        assertEquals("Expected 1 unreturned tape", 1, result.size());
        assertEquals("Expected tape T002 in result", "T002", result.get(0).getId());
    }
    
    @Test
    public void testCase5_MultipleUnreturnedTapes() throws ParseException {
        // Setup
        Customer customer = new Customer();
        customer.setId("C005");
        
        // Create Tape T001 (unreturned)
        Tape tape1 = new Tape();
        tape1.setId("T001");
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(null); // Tape not returned
        
        // Create Tape T002 (unreturned)
        Tape tape2 = new Tape();
        tape2.setId("T002");
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-06 00:00:00"));
        rental2.setReturnDate(null); // Tape not returned
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: List containing T001 and T002
        assertEquals("Expected 2 unreturned tapes", 2, result.size());
        
        // Check that both tapes are in the result
        boolean foundT001 = false;
        boolean foundT002 = false;
        for (Tape tape : result) {
            if ("T001".equals(tape.getId())) {
                foundT001 = true;
            }
            if ("T002".equals(tape.getId())) {
                foundT002 = true;
            }
        }
        
        assertTrue("Expected tape T001 in result", foundT001);
        assertTrue("Expected tape T002 in result", foundT002);
    }
}