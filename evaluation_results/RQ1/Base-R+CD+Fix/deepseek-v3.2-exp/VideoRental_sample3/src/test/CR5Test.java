import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR5Test {
    
    private Customer customer;
    private Tape tape1;
    private Tape tape2;
    private VideoRental rental1;
    private VideoRental rental2;
    
    @Before
    public void setUp() {
        // Reset objects before each test
        customer = null;
        tape1 = null;
        tape2 = null;
        rental1 = null;
        rental2 = null;
    }
    
    @Test
    public void testCase1_NoRentalsExist() {
        // Test Case 1: "No rentals exist"
        // Setup: Create Customer C001 with empty rentals list
        customer = new Customer();
        customer.setId("C001");
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: Expected Output: Empty list, no active rentals
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase2_AllTapesReturned() {
        // Test Case 2: "All tapes returned"
        // Setup:
        // 1. Create Customer C002
        customer = new Customer();
        customer.setId("C002");
        
        // 2. Create Tape T001
        tape1 = new Tape();
        tape1.setId("T001");
        
        // 3. Create VideoRental with rental_date="2025-01-01", due_date="2025-01-05", 
        //    return_date="2025-01-01", associated with C002 and T001
        rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-01");
        
        customer.getRentals().add(rental1);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: Expected Output: Empty list, all rentals returned
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase3_OneUnreturnedTape() {
        // Test Case 3: "One unreturned tape"
        // Setup:
        // 1. Create Customer C003
        customer = new Customer();
        customer.setId("C003");
        
        // 2. Create Tape T001
        tape1 = new Tape();
        tape1.setId("T001");
        
        // 3. Create VideoRental with rental_date="2025-01-01", due_date="2025-01-05", 
        //    return_date=null, associated with C003 and T001
        rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate(null); // Not returned
        
        customer.getRentals().add(rental1);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: Expected Output: List containing T001
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly 1 tape", 1, result.size());
        assertEquals("Tape ID should be T001", "T001", result.get(0).getId());
    }
    
    @Test
    public void testCase4_MixedReturnedUnreturned() {
        // Test Case 4: "Mixed returned/unreturned"
        // Setup:
        // 1. Create Customer C004
        customer = new Customer();
        customer.setId("C004");
        
        // 2. Create Tape T001 and T002
        tape1 = new Tape();
        tape1.setId("T001");
        tape2 = new Tape();
        tape2.setId("T002");
        
        // 3. Create VideoRental for T001 with rental_date="2025-01-01", due_date="2025-01-05", 
        //    return_date="2025-01-01", associated with C004
        rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-01"); // Returned
        
        // 4. Create VideoRental for T002 with rental_date="2025-01-02", due_date="2025-01-06", 
        //    return_date=null, associated with C004
        rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate("2025-01-06");
        rental2.setReturnDate(null); // Not returned
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: Expected Output: List containing T002
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly 1 tape", 1, result.size());
        assertEquals("Tape ID should be T002", "T002", result.get(0).getId());
    }
    
    @Test
    public void testCase5_MultipleUnreturnedTapes() {
        // Test Case 5: "Multiple unreturned tapes"
        // Setup:
        // 1. Create Customer C005
        customer = new Customer();
        customer.setId("C005");
        
        // 2. Create Tapes T001 and T002
        tape1 = new Tape();
        tape1.setId("T001");
        tape2 = new Tape();
        tape2.setId("T002");
        
        // 3. Create VideoRental for T001 with rental_date="2025-01-01", due_date="2025-01-05", 
        //    return_date=null, associated with C005
        rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate(null); // Not returned
        
        // 4. Create VideoRental for T002 with rental_date="2025-01-02", due_date="2025-01-06", 
        //    return_date=null, associated with C005
        rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate("2025-01-06");
        rental2.setReturnDate(null); // Not returned
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: Expected Output: List containing T001 and T002
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly 2 tapes", 2, result.size());
        
        // Check that both tapes are present (order doesn't matter per specification)
        boolean foundT001 = false;
        boolean foundT002 = false;
        
        for (Tape tape : result) {
            if ("T001".equals(tape.getId())) {
                foundT001 = true;
            } else if ("T002".equals(tape.getId())) {
                foundT002 = true;
            }
        }
        
        assertTrue("Should contain T001", foundT001);
        assertTrue("Should contain T002", foundT002);
    }
}