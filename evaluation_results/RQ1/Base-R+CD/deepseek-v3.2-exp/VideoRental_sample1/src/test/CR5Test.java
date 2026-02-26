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
        // Test Case 1: "No rentals exist"
        // Input: customer_id="C001"
        // Setup: Create Customer C001 with empty rentals list
        // Expected Output: Empty list, no active rentals.
        
        // Create customer with empty rentals
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify result is empty list
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty list", result.isEmpty());
    }
    
    @Test
    public void testCase2_AllTapesReturned() throws ParseException {
        // Test Case 2: "All tapes returned"
        // Input: customer_id="C002"
        // Setup:
        // 1. Create Customer C002
        // 2. Create Tape T001
        // 3. Create VideoRental with rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01", associated with C002 and T001
        // Expected Output: Empty list, all rentals returned.
        
        // Create customer
        Customer customer = new Customer();
        customer.setId("C002");
        
        // Create tape
        Tape tape = new Tape();
        tape.setId("T001");
        
        // Create video rental with return date set (tape is returned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental.setReturnDate(dateFormat.parse("2025-01-01 00:00:00"));
        
        // Add rental to customer
        customer.getRentals().add(rental);
        
        // Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify result is empty list (all tapes returned)
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty list since all tapes are returned", result.isEmpty());
    }
    
    @Test
    public void testCase3_OneUnreturnedTape() throws ParseException {
        // Test Case 3: "One unreturned tape"
        // Input: customer_id="C003"
        // Setup:
        // 1. Create Customer C003
        // 2. Create Tape T001
        // 3. Create VideoRental with rental_date="2025-01-01", due_date="2025-01-05", return_date=null, associated with C003 and T001
        // Expected Output: List containing T001.
        
        // Create customer
        Customer customer = new Customer();
        customer.setId("C003");
        
        // Create tape
        Tape tape = new Tape();
        tape.setId("T001");
        
        // Create video rental with return date null (tape is unreturned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental.setReturnDate(null); // Tape not returned
        
        // Add rental to customer
        customer.getRentals().add(rental);
        
        // Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify result contains exactly one tape (T001)
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly one tape", 1, result.size());
        assertEquals("Tape ID should be T001", "T001", result.get(0).getId());
    }
    
    @Test
    public void testCase4_MixedReturnedUnreturned() throws ParseException {
        // Test Case 4: "Mixed returned/unreturned"
        // Input: customer_id="C004"
        // Setup:
        // 1. Create Customer C004
        // 2. Create Tape T001 and T002
        // 3. Create VideoRental for T001 with rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01", associated with C004
        // 4. Create VideoRental for T002 with rental_date="2025-01-02", due_date="2025-01-06", return_date=null, associated with C004
        // Expected Output: List containing T002.
        
        // Create customer
        Customer customer = new Customer();
        customer.setId("C004");
        
        // Create tapes
        Tape tape1 = new Tape();
        tape1.setId("T001");
        
        Tape tape2 = new Tape();
        tape2.setId("T002");
        
        // Create first rental (returned tape)
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-01 00:00:00")); // Tape returned
        
        // Create second rental (unreturned tape)
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-06 00:00:00"));
        rental2.setReturnDate(null); // Tape not returned
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify result contains exactly one tape (T002)
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly one tape", 1, result.size());
        assertEquals("Tape ID should be T002", "T002", result.get(0).getId());
    }
    
    @Test
    public void testCase5_MultipleUnreturnedTapes() throws ParseException {
        // Test Case 5: "Multiple unreturned tapes"
        // Input: customer_id="C005"
        // Setup:
        // 1. Create Customer C005
        // 2. Create Tapes T001 and T002
        // 3. Create VideoRental for T001 with rental_date="2025-01-01", due_date="2025-01-05", return_date=null, associated with C005
        // 4. Create VideoRental for T002 with rental_date="2025-01-02", due_date="2025-01-06", return_date=null, associated with C005
        // Expected Output: List containing T001 and T002.
        
        // Create customer
        Customer customer = new Customer();
        customer.setId("C005");
        
        // Create tapes
        Tape tape1 = new Tape();
        tape1.setId("T001");
        
        Tape tape2 = new Tape();
        tape2.setId("T002");
        
        // Create first rental (unreturned tape)
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(null); // Tape not returned
        
        // Create second rental (unreturned tape)
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-06 00:00:00"));
        rental2.setReturnDate(null); // Tape not returned
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify result contains exactly two tapes (T001 and T002)
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly two tapes", 2, result.size());
        
        // Check that both tapes are present (order doesn't matter)
        List<String> tapeIds = result.stream().map(Tape::getId).collect(java.util.stream.Collectors.toList());
        assertTrue("Result should contain T001", tapeIds.contains("T001"));
        assertTrue("Result should contain T002", tapeIds.contains("T002"));
    }
}