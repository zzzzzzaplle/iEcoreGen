import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR5Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_noRentalsExist() throws Exception {
        // Test Case 1: "No rentals exist"
        // Input: customer_id="C001"
        // Setup: Create Customer C001 with empty rentals list
        Customer customer = new Customer();
        customer.setId("C001");
        customer.setRentals(new ArrayList<VideoRental>());
        
        // Expected Output: Empty list, no active rentals
        List<Tape> result = customer.getUnreturnedTapes();
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty list", result.isEmpty());
    }
    
    @Test
    public void testCase2_allTapesReturned() throws Exception {
        // Test Case 2: "All tapes returned"
        // Input: customer_id="C002"
        // Setup: Create Customer C002
        Customer customer = new Customer();
        customer.setId("C002");
        customer.setRentals(new ArrayList<VideoRental>());
        
        // Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        tape.setVideoInformation("Movie Title");
        
        // Create VideoRental with return_date set (returned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental.setReturnDate(dateFormat.parse("2025-01-01 00:00:00"));
        
        customer.getRentals().add(rental);
        
        // Expected Output: Empty list, all rentals returned
        List<Tape> result = customer.getUnreturnedTapes();
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty list since all tapes are returned", result.isEmpty());
    }
    
    @Test
    public void testCase3_oneUnreturnedTape() throws Exception {
        // Test Case 3: "One unreturned tape"
        // Input: customer_id="C003"
        // Setup: Create Customer C003
        Customer customer = new Customer();
        customer.setId("C003");
        customer.setRentals(new ArrayList<VideoRental>());
        
        // Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        tape.setVideoInformation("Movie Title");
        
        // Create VideoRental with return_date=null (unreturned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental.setReturnDate(null); // Not returned
        
        customer.getRentals().add(rental);
        
        // Expected Output: List containing T001
        List<Tape> result = customer.getUnreturnedTapes();
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly 1 tape", 1, result.size());
        assertEquals("Result should contain T001", "T001", result.get(0).getId());
    }
    
    @Test
    public void testCase4_mixedReturnedUnreturned() throws Exception {
        // Test Case 4: "Mixed returned/unreturned"
        // Input: customer_id="C004"
        // Setup: Create Customer C004
        Customer customer = new Customer();
        customer.setId("C004");
        customer.setRentals(new ArrayList<VideoRental>());
        
        // Create Tape T001 and T002
        Tape tape1 = new Tape();
        tape1.setId("T001");
        tape1.setVideoInformation("Movie 1");
        
        Tape tape2 = new Tape();
        tape2.setId("T002");
        tape2.setVideoInformation("Movie 2");
        
        // Create VideoRental for T001 with return_date set (returned)
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-01 00:00:00"));
        
        // Create VideoRental for T002 with return_date=null (unreturned)
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-06 00:00:00"));
        rental2.setReturnDate(null); // Not returned
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Expected Output: List containing T002
        List<Tape> result = customer.getUnreturnedTapes();
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly 1 tape", 1, result.size());
        assertEquals("Result should contain T002", "T002", result.get(0).getId());
    }
    
    @Test
    public void testCase5_multipleUnreturnedTapes() throws Exception {
        // Test Case 5: "Multiple unreturned tapes"
        // Input: customer_id="C005"
        // Setup: Create Customer C005
        Customer customer = new Customer();
        customer.setId("C005");
        customer.setRentals(new ArrayList<VideoRental>());
        
        // Create Tapes T001 and T002
        Tape tape1 = new Tape();
        tape1.setId("T001");
        tape1.setVideoInformation("Movie 1");
        
        Tape tape2 = new Tape();
        tape2.setId("T002");
        tape2.setVideoInformation("Movie 2");
        
        // Create VideoRental for T001 with return_date=null (unreturned)
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(null); // Not returned
        
        // Create VideoRental for T002 with return_date=null (unreturned)
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-06 00:00:00"));
        rental2.setReturnDate(null); // Not returned
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Expected Output: List containing T001 and T002
        List<Tape> result = customer.getUnreturnedTapes();
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly 2 tapes", 2, result.size());
        
        // Check that both T001 and T002 are in the result
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
        assertTrue("Result should contain T001", foundT001);
        assertTrue("Result should contain T002", foundT002);
    }
}