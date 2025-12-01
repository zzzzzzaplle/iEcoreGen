import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;

public class CR5Test {
    
    private Customer customer;
    
    @Before
    public void setUp() {
        // Clear the rental repository before each test to ensure isolation
        // Note: Since RentalRepository uses a static list, we need to clear it
        // This is a workaround since the original code doesn't provide a clear method
        List<VideoRental> allRentals = RentalRepository.getAllRentals();
        if (!allRentals.isEmpty()) {
            // This is a limitation of the provided code - we cannot properly clear the repository
            // In a real scenario, we would need a clear() method in RentalRepository
        }
    }
    
    @After
    public void tearDown() {
        customer = null;
    }
    
    @Test
    public void testCase1_NoRentalsExist() {
        // Test Case 1: "No rentals exist"
        // Input: customer_id="C001"
        // Setup: Create Customer C001 with empty rentals list
        
        // Create customer with ID C001
        customer = new Customer();
        customer.setId("C001");
        
        // Get unreturned tapes - should be empty list
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Expected Output: No active rentals (empty list)
        assertNotNull("Unreturned tapes list should not be null", unreturnedTapes);
        assertTrue("Unreturned tapes list should be empty", unreturnedTapes.isEmpty());
    }
    
    @Test
    public void testCase2_AllTapesReturned() {
        // Test Case 2: "All tapes returned"
        // Input: customer_id="C002"
        // Setup: Create Customer C002 with Rental 1: tape_id="T001", return_date="2025-01-01"
        
        // Create customer with ID C002
        customer = new Customer();
        customer.setId("C002");
        
        // Create tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        tape.setVideoInformation("Test Video 1");
        
        // Create rental with return date (tape has been returned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDate.of(2024, 12, 25));
        rental.setReturnDate(LocalDate.of(2025, 1, 1)); // Returned
        
        // Add rental to customer
        customer.getRentals().add(rental);
        
        // Get unreturned tapes
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Expected Output: No unreturned rentals (empty list)
        assertNotNull("Unreturned tapes list should not be null", unreturnedTapes);
        assertTrue("Unreturned tapes list should be empty when all tapes are returned", unreturnedTapes.isEmpty());
    }
    
    @Test
    public void testCase3_OneUnreturnedTape() {
        // Test Case 3: "One unreturned tape"
        // Input: customer_id="C003"
        // Setup: Create Customer C003 with Rental 1: tape_id="T001", return_date=null
        
        // Create customer with ID C003
        customer = new Customer();
        customer.setId("C003");
        
        // Create tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        tape.setVideoInformation("Test Video 1");
        
        // Create rental with null return date (tape not returned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDate.now().plusDays(7));
        rental.setReturnDate(null); // Not returned
        
        // Add rental to customer
        customer.getRentals().add(rental);
        
        // Get unreturned tapes
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Expected Output: List containing T001
        assertNotNull("Unreturned tapes list should not be null", unreturnedTapes);
        assertEquals("Should have exactly 1 unreturned tape", 1, unreturnedTapes.size());
        assertEquals("Unreturned tape should be T001", "T001", unreturnedTapes.get(0).getId());
    }
    
    @Test
    public void testCase4_MixedReturnedUnreturned() {
        // Test Case 4: "Mixed returned/unreturned"
        // Input: customer_id="C004"
        // Setup: Create Customer C004 with:
        //   - Rental 1: tape_id="T001", return_date="2025-01-01"
        //   - Rental 2: tape_id="T002", return_date=null
        
        // Create customer with ID C004
        customer = new Customer();
        customer.setId("C004");
        
        // Create tape T001
        Tape tape1 = new Tape();
        tape1.setId("T001");
        tape1.setVideoInformation("Test Video 1");
        
        // Create tape T002
        Tape tape2 = new Tape();
        tape2.setId("T002");
        tape2.setVideoInformation("Test Video 2");
        
        // Create rental 1 with return date (tape has been returned)
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.of(2024, 12, 25));
        rental1.setReturnDate(LocalDate.of(2025, 1, 1)); // Returned
        
        // Create rental 2 with null return date (tape not returned)
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDate.now().plusDays(7));
        rental2.setReturnDate(null); // Not returned
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Get unreturned tapes
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Expected Output: List containing T002
        assertNotNull("Unreturned tapes list should not be null", unreturnedTapes);
        assertEquals("Should have exactly 1 unreturned tape", 1, unreturnedTapes.size());
        assertEquals("Unreturned tape should be T002", "T002", unreturnedTapes.get(0).getId());
    }
    
    @Test
    public void testCase5_MultipleUnreturnedTapes() {
        // Test Case 5: "Multiple unreturned tapes"
        // Input: customer_id="C005"
        // Setup: Create Customer C005 with:
        //   - Rental 1: tape_id="T001", return_date=null
        //   - Rental 2: tape_id="T002", return_date=null
        
        // Create customer with ID C005
        customer = new Customer();
        customer.setId("C005");
        
        // Create tape T001
        Tape tape1 = new Tape();
        tape1.setId("T001");
        tape1.setVideoInformation("Test Video 1");
        
        // Create tape T002
        Tape tape2 = new Tape();
        tape2.setId("T002");
        tape2.setVideoInformation("Test Video 2");
        
        // Create rental 1 with null return date (tape not returned)
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.now().plusDays(7));
        rental1.setReturnDate(null); // Not returned
        
        // Create rental 2 with null return date (tape not returned)
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDate.now().plusDays(7));
        rental2.setReturnDate(null); // Not returned
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Get unreturned tapes
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Expected Output: List containing T001 and T002
        assertNotNull("Unreturned tapes list should not be null", unreturnedTapes);
        assertEquals("Should have exactly 2 unreturned tapes", 2, unreturnedTapes.size());
        
        // Check that both T001 and T002 are in the list
        boolean foundT001 = false;
        boolean foundT002 = false;
        for (Tape tape : unreturnedTapes) {
            if ("T001".equals(tape.getId())) {
                foundT001 = true;
            }
            if ("T002".equals(tape.getId())) {
                foundT002 = true;
            }
        }
        assertTrue("Should contain T001", foundT001);
        assertTrue("Should contain T002", foundT002);
    }
}