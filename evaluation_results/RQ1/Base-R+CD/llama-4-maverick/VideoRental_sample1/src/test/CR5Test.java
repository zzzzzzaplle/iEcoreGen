import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;

public class CR5Test {
    
    @Before
    public void setUp() {
        // Clear the static list of all rentals before each test
        VideoRental.getAllRentals().clear();
    }
    
    @Test
    public void testCase1_noRentalsExist() {
        // Setup: Create Customer C001 with empty rentals list
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Execute: Call getUnreturnedTapes()
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify: Expected Output: Empty list, no active rentals.
        assertTrue("List should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase2_allTapesReturned() {
        // Setup: Create Customer C002
        Customer customer = new Customer();
        customer.setId("C002");
        
        // Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        
        // Create VideoRental with return_date set (returned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDate.of(2025, 1, 5));
        rental.setReturnDate(LocalDate.of(2025, 1, 1)); // Returned early
        
        // Add rental to customer
        customer.getRentals().add(rental);
        
        // Execute: Call getUnreturnedTapes()
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify: Expected Output: Empty list, all rentals returned.
        assertTrue("List should be empty as all tapes are returned", result.isEmpty());
    }
    
    @Test
    public void testCase3_oneUnreturnedTape() {
        // Setup: Create Customer C003
        Customer customer = new Customer();
        customer.setId("C003");
        
        // Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        
        // Create VideoRental with return_date=null (unreturned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDate.of(2025, 1, 5));
        rental.setReturnDate(null); // Not returned
        
        // Add rental to customer
        customer.getRentals().add(rental);
        
        // Execute: Call getUnreturnedTapes()
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify: Expected Output: List containing T001.
        assertEquals("List should contain exactly 1 item", 1, result.size());
        assertEquals("List should contain T001", "T001", result.get(0));
    }
    
    @Test
    public void testCase4_mixedReturnedUnreturned() {
        // Setup: Create Customer C004
        Customer customer = new Customer();
        customer.setId("C004");
        
        // Create Tape T001 and T002
        Tape tape1 = new Tape();
        tape1.setId("T001");
        Tape tape2 = new Tape();
        tape2.setId("T002");
        
        // Create VideoRental for T001 with return_date set (returned)
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 1)); // Returned
        
        // Create VideoRental for T002 with return_date=null (unreturned)
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDate.of(2025, 1, 6));
        rental2.setReturnDate(null); // Not returned
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute: Call getUnreturnedTapes()
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify: Expected Output: List containing T002.
        assertEquals("List should contain exactly 1 item", 1, result.size());
        assertEquals("List should contain T002", "T002", result.get(0));
    }
    
    @Test
    public void testCase5_multipleUnreturnedTapes() {
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
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(null); // Not returned
        
        // Create VideoRental for T002 with return_date=null (unreturned)
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDate.of(2025, 1, 6));
        rental2.setReturnDate(null); // Not returned
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute: Call getUnreturnedTapes()
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify: Expected Output: List containing T001 and T002.
        assertEquals("List should contain exactly 2 items", 2, result.size());
        assertTrue("List should contain T001", result.contains("T001"));
        assertTrue("List should contain T002", result.contains("T002"));
    }
}