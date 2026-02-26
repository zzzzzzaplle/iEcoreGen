import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class CR5Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_NoRentalsExist() throws Exception {
        // Setup: Create Customer C001 with empty rentals list
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: Empty list, no active rentals
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase2_AllTapesReturned() throws Exception {
        // Setup: Create Customer C002
        Customer customer = new Customer();
        customer.setId("C002");
        
        // Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        
        // Create VideoRental with return_date set
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental.setReturnDate(dateFormat.parse("2025-01-01 00:00:00"));
        
        // Add rental to customer
        customer.getRentals().add(rental);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: Empty list, all rentals returned
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_OneUnreturnedTape() throws Exception {
        // Setup: Create Customer C003
        Customer customer = new Customer();
        customer.setId("C003");
        
        // Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        
        // Create VideoRental with return_date=null
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental.setReturnDate(null);
        
        // Add rental to customer
        customer.getRentals().add(rental);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: List containing T001
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("T001", result.get(0).getId());
    }
    
    @Test
    public void testCase4_MixedReturnedUnreturned() throws Exception {
        // Setup: Create Customer C004
        Customer customer = new Customer();
        customer.setId("C004");
        
        // Create Tape T001 and T002
        Tape tape1 = new Tape();
        tape1.setId("T001");
        Tape tape2 = new Tape();
        tape2.setId("T002");
        
        // Create VideoRental for T001 with return_date set
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-01 00:00:00"));
        
        // Create VideoRental for T002 with return_date=null
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-06 00:00:00"));
        rental2.setReturnDate(null);
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: List containing T002
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("T002", result.get(0).getId());
    }
    
    @Test
    public void testCase5_MultipleUnreturnedTapes() throws Exception {
        // Setup: Create Customer C005
        Customer customer = new Customer();
        customer.setId("C005");
        
        // Create Tapes T001 and T002
        Tape tape1 = new Tape();
        tape1.setId("T001");
        Tape tape2 = new Tape();
        tape2.setId("T002");
        
        // Create VideoRental for T001 with return_date=null
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(null);
        
        // Create VideoRental for T002 with return_date=null
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-06 00:00:00"));
        rental2.setReturnDate(null);
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: List containing T001 and T002
        assertNotNull(result);
        assertEquals(2, result.size());
        
        // Check that both tapes are in the result
        List<String> tapeIds = new ArrayList<>();
        for (Tape tape : result) {
            tapeIds.add(tape.getId());
        }
        
        assertTrue(tapeIds.contains("T001"));
        assertTrue(tapeIds.contains("T002"));
    }
}