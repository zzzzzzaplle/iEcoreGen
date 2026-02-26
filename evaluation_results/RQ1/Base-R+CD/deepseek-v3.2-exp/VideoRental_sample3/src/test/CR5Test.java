import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CR5Test {
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_NoRentalsExist() {
        // Setup: Create Customer C001 with empty rentals list
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: Expected Output: Empty list, no active rentals
        assertTrue("Result should be empty when no rentals exist", result.isEmpty());
    }
    
    @Test
    public void testCase2_AllTapesReturned() {
        // Setup: Create Customer C002
        Customer customer = new Customer();
        customer.setId("C002");
        
        // Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        
        // Create VideoRental with return_date="2025-01-01"
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDateTime.parse("2025-01-05 00:00:00", formatter));
        rental.setReturnDate(LocalDateTime.parse("2025-01-01 00:00:00", formatter));
        
        // Add rental to customer
        customer.getRentals().add(rental);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: Expected Output: Empty list, all rentals returned
        assertTrue("Result should be empty when all tapes are returned", result.isEmpty());
    }
    
    @Test
    public void testCase3_OneUnreturnedTape() {
        // Setup: Create Customer C003
        Customer customer = new Customer();
        customer.setId("C003");
        
        // Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        
        // Create VideoRental with return_date=null
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDateTime.parse("2025-01-05 00:00:00", formatter));
        rental.setReturnDate(null); // Unreturned tape
        
        // Add rental to customer
        customer.getRentals().add(rental);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: Expected Output: List containing T001
        assertEquals("Should contain exactly one unreturned tape", 1, result.size());
        assertEquals("Unreturned tape should be T001", "T001", result.get(0).getId());
    }
    
    @Test
    public void testCase4_MixedReturnedUnreturned() {
        // Setup: Create Customer C004
        Customer customer = new Customer();
        customer.setId("C004");
        
        // Create Tape T001 and T002
        Tape tape1 = new Tape();
        tape1.setId("T001");
        
        Tape tape2 = new Tape();
        tape2.setId("T002");
        
        // Create VideoRental for T001 with return_date="2025-01-01" (returned)
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDateTime.parse("2025-01-05 00:00:00", formatter));
        rental1.setReturnDate(LocalDateTime.parse("2025-01-01 00:00:00", formatter));
        
        // Create VideoRental for T002 with return_date=null (unreturned)
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDateTime.parse("2025-01-06 00:00:00", formatter));
        rental2.setReturnDate(null);
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: Expected Output: List containing T002
        assertEquals("Should contain exactly one unreturned tape", 1, result.size());
        assertEquals("Unreturned tape should be T002", "T002", result.get(0).getId());
    }
    
    @Test
    public void testCase5_MultipleUnreturnedTapes() {
        // Setup: Create Customer C005
        Customer customer = new Customer();
        customer.setId("C005");
        
        // Create Tapes T001 and T002
        Tape tape1 = new Tape();
        tape1.setId("T001");
        
        Tape tape2 = new Tape();
        tape2.setId("T002");
        
        // Create VideoRental for T001 with return_date=null (unreturned)
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDateTime.parse("2025-01-05 00:00:00", formatter));
        rental1.setReturnDate(null);
        
        // Create VideoRental for T002 with return_date=null (unreturned)
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDateTime.parse("2025-01-06 00:00:00", formatter));
        rental2.setReturnDate(null);
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: Expected Output: List containing T001 and T002
        assertEquals("Should contain exactly two unreturned tapes", 2, result.size());
        
        // Check that both tapes are in the result
        boolean foundT001 = false;
        boolean foundT002 = false;
        for (Tape tape : result) {
            if ("T001".equals(tape.getId())) {
                foundT001 = true;
            } else if ("T002".equals(tape.getId())) {
                foundT002 = true;
            }
        }
        
        assertTrue("Result should contain T001", foundT001);
        assertTrue("Result should contain T002", foundT002);
    }
}