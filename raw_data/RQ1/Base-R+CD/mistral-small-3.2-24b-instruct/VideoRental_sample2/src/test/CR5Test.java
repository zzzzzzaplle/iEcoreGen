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
        // Initialize date format for parsing dates
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_noRentalsExist() throws Exception {
        // Test Case 1: "No rentals exist"
        // Input: customer_id="C001"
        // Setup: Create Customer C001 with empty rentals list
        
        // Create customer with empty rentals
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Get unreturned tapes
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Expected Output: Empty list, no active rentals
        assertNotNull("Unreturned tapes list should not be null", unreturnedTapes);
        assertEquals("Unreturned tapes list should be empty", 0, unreturnedTapes.size());
    }
    
    @Test
    public void testCase2_allTapesReturned() throws Exception {
        // Test Case 2: "All tapes returned"
        // Input: customer_id="C002"
        // Setup: Create Customer C002, Tape T001, VideoRental with return_date set
        
        // Create customer
        Customer customer = new Customer();
        customer.setId("C002");
        
        // Create tape
        Tape tape = new Tape();
        tape.setId("T001");
        tape.setVideoInformation("Movie Title");
        
        // Create video rental with return date set (tape is returned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2025-01-05 23:59:59"));
        rental.setReturnDate(dateFormat.parse("2025-01-01 23:59:59")); // Returned before due date
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Get unreturned tapes
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Expected Output: Empty list, all rentals returned
        assertNotNull("Unreturned tapes list should not be null", unreturnedTapes);
        assertEquals("Unreturned tapes list should be empty when all tapes are returned", 0, unreturnedTapes.size());
    }
    
    @Test
    public void testCase3_oneUnreturnedTape() throws Exception {
        // Test Case 3: "One unreturned tape"
        // Input: customer_id="C003"
        // Setup: Create Customer C003, Tape T001, VideoRental with return_date=null
        
        // Create customer
        Customer customer = new Customer();
        customer.setId("C003");
        
        // Create tape
        Tape tape = new Tape();
        tape.setId("T001");
        tape.setVideoInformation("Movie Title");
        
        // Create video rental with no return date (tape is unreturned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2025-01-05 23:59:59"));
        rental.setReturnDate(null); // Not returned
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Get unreturned tapes
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Expected Output: List containing T001
        assertNotNull("Unreturned tapes list should not be null", unreturnedTapes);
        assertEquals("Unreturned tapes list should contain 1 tape", 1, unreturnedTapes.size());
        assertEquals("Unreturned tape should be T001", "T001", unreturnedTapes.get(0).getId());
    }
    
    @Test
    public void testCase4_mixedReturnedUnreturned() throws Exception {
        // Test Case 4: "Mixed returned/unreturned"
        // Input: customer_id="C004"
        // Setup: Create Customer C004, Tapes T001 and T002, 
        // VideoRental for T001 with return_date set, VideoRental for T002 with return_date=null
        
        // Create customer
        Customer customer = new Customer();
        customer.setId("C004");
        
        // Create tapes
        Tape tape1 = new Tape();
        tape1.setId("T001");
        tape1.setVideoInformation("Movie Title 1");
        
        Tape tape2 = new Tape();
        tape2.setId("T002");
        tape2.setVideoInformation("Movie Title 2");
        
        // Create first rental (returned)
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 23:59:59"));
        rental1.setReturnDate(dateFormat.parse("2025-01-01 23:59:59")); // Returned
        
        // Create second rental (unreturned)
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-06 23:59:59"));
        rental2.setReturnDate(null); // Not returned
        
        // Add rentals to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Get unreturned tapes
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Expected Output: List containing T002
        assertNotNull("Unreturned tapes list should not be null", unreturnedTapes);
        assertEquals("Unreturned tapes list should contain 1 tape", 1, unreturnedTapes.size());
        assertEquals("Unreturned tape should be T002", "T002", unreturnedTapes.get(0).getId());
    }
    
    @Test
    public void testCase5_multipleUnreturnedTapes() throws Exception {
        // Test Case 5: "Multiple unreturned tapes"
        // Input: customer_id="C005"
        // Setup: Create Customer C005, Tapes T001 and T002,
        // VideoRentals for both tapes with return_date=null
        
        // Create customer
        Customer customer = new Customer();
        customer.setId("C005");
        
        // Create tapes
        Tape tape1 = new Tape();
        tape1.setId("T001");
        tape1.setVideoInformation("Movie Title 1");
        
        Tape tape2 = new Tape();
        tape2.setId("T002");
        tape2.setVideoInformation("Movie Title 2");
        
        // Create first rental (unreturned)
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 23:59:59"));
        rental1.setReturnDate(null); // Not returned
        
        // Create second rental (unreturned)
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-06 23:59:59"));
        rental2.setReturnDate(null); // Not returned
        
        // Add rentals to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Get unreturned tapes
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Expected Output: List containing T001 and T002
        assertNotNull("Unreturned tapes list should not be null", unreturnedTapes);
        assertEquals("Unreturned tapes list should contain 2 tapes", 2, unreturnedTapes.size());
        
        // Check that both tapes are in the list
        List<String> tapeIds = new ArrayList<>();
        for (Tape tape : unreturnedTapes) {
            tapeIds.add(tape.getId());
        }
        
        assertTrue("Unreturned tapes should contain T001", tapeIds.contains("T001"));
        assertTrue("Unreturned tapes should contain T002", tapeIds.contains("T002"));
    }
}