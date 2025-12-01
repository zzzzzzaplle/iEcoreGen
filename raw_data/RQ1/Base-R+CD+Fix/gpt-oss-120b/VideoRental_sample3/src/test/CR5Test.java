import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;

public class CR5Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // Clear static rentals list before each test to avoid interference
        VideoRental.getAllRentals().clear();
    }
    
    @Test
    public void testCase1_NoRentalsExist() throws Exception {
        // Test Case 1: "No rentals exist"
        // Input: customer_id="C001"
        // Setup: Create Customer C001 with empty rentals list
        
        // Create customer with empty rentals
        Customer customer = new Customer("C001");
        
        // Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Expected Output: Empty list, no active rentals
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty when no rentals exist", result.isEmpty());
    }
    
    @Test
    public void testCase2_AllTapesReturned() throws Exception {
        // Test Case 2: "All tapes returned"
        // Input: customer_id="C002"
        // Setup: Create Customer C002, Tape T001, VideoRental with return_date set
        
        // Create customer
        Customer customer = new Customer("C002");
        
        // Create tape
        Tape tape = new Tape("T001", "Movie Title");
        
        // Create video rental with return date (tape is returned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setRentalDate(dateFormat.parse("2025-01-01 10:00:00"));
        rental.setDueDate(dateFormat.parse("2025-01-05 10:00:00"));
        rental.setReturnDate(dateFormat.parse("2025-01-01 10:00:00")); // Returned on same day
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Register rental (for tape availability checks)
        VideoRental.registerRental(rental);
        
        // Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Expected Output: Empty list, all rentals returned
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty when all tapes are returned", result.isEmpty());
    }
    
    @Test
    public void testCase3_OneUnreturnedTape() throws Exception {
        // Test Case 3: "One unreturned tape"
        // Input: customer_id="C003"
        // Setup: Create Customer C003, Tape T001, VideoRental with return_date=null
        
        // Create customer
        Customer customer = new Customer("C003");
        
        // Create tape
        Tape tape = new Tape("T001", "Movie Title");
        
        // Create video rental with no return date (tape is unreturned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setRentalDate(dateFormat.parse("2025-01-01 10:00:00"));
        rental.setDueDate(dateFormat.parse("2025-01-05 10:00:00"));
        rental.setReturnDate(null); // Not returned
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Register rental (for tape availability checks)
        VideoRental.registerRental(rental);
        
        // Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Expected Output: List containing T001
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain exactly one tape", 1, result.size());
        assertEquals("Should contain T001", "T001", result.get(0).getId());
    }
    
    @Test
    public void testCase4_MixedReturnedUnreturned() throws Exception {
        // Test Case 4: "Mixed returned/unreturned"
        // Input: customer_id="C004"
        // Setup: Create Customer C004, Tapes T001 and T002, 
        //        VideoRental for T001 with return_date set, VideoRental for T002 with return_date=null
        
        // Create customer
        Customer customer = new Customer("C004");
        
        // Create tapes
        Tape tape1 = new Tape("T001", "Movie Title 1");
        Tape tape2 = new Tape("T002", "Movie Title 2");
        
        // Create first rental (returned)
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setRentalDate(dateFormat.parse("2025-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2025-01-05 10:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-01 10:00:00")); // Returned
        
        // Create second rental (unreturned)
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setRentalDate(dateFormat.parse("2025-01-02 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2025-01-06 10:00:00"));
        rental2.setReturnDate(null); // Not returned
        
        // Add rentals to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Register rentals (for tape availability checks)
        VideoRental.registerRental(rental1);
        VideoRental.registerRental(rental2);
        
        // Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Expected Output: List containing T002
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain exactly one tape", 1, result.size());
        assertEquals("Should contain T002", "T002", result.get(0).getId());
    }
    
    @Test
    public void testCase5_MultipleUnreturnedTapes() throws Exception {
        // Test Case 5: "Multiple unreturned tapes"
        // Input: customer_id="C005"
        // Setup: Create Customer C005, Tapes T001 and T002,
        //        VideoRentals for both tapes with return_date=null
        
        // Create customer
        Customer customer = new Customer("C005");
        
        // Create tapes
        Tape tape1 = new Tape("T001", "Movie Title 1");
        Tape tape2 = new Tape("T002", "Movie Title 2");
        
        // Create first rental (unreturned)
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setRentalDate(dateFormat.parse("2025-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2025-01-05 10:00:00"));
        rental1.setReturnDate(null); // Not returned
        
        // Create second rental (unreturned)
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setRentalDate(dateFormat.parse("2025-01-02 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2025-01-06 10:00:00"));
        rental2.setReturnDate(null); // Not returned
        
        // Add rentals to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Register rentals (for tape availability checks)
        VideoRental.registerRental(rental1);
        VideoRental.registerRental(rental2);
        
        // Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Expected Output: List containing T001 and T002
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain exactly two tapes", 2, result.size());
        
        // Check that both tapes are present (order doesn't matter)
        boolean hasT001 = false;
        boolean hasT002 = false;
        for (Tape tape : result) {
            if ("T001".equals(tape.getId())) {
                hasT001 = true;
            }
            if ("T002".equals(tape.getId())) {
                hasT002 = true;
            }
        }
        assertTrue("Should contain T001", hasT001);
        assertTrue("Should contain T002", hasT002);
    }
}