import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;

public class CR5Test {
    
    private Customer customer;
    
    @Before
    public void setUp() {
        // Clear static rentals list before each test to ensure isolation
        List<VideoRental> allRentals = VideoRental.getAllRentals();
        if (allRentals instanceof java.util.ArrayList) {
            ((java.util.ArrayList<VideoRental>) allRentals).clear();
        }
    }
    
    @Test
    public void testCase1_NoRentalsExist() {
        // Input: customer_id="C001"
        // Setup: Create Customer C001 with empty rentals list
        customer = new Customer("C001");
        customer.setRentals(new java.util.ArrayList<VideoRental>());
        
        // Expected Output: No active rentals (empty list)
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        assertNotNull("Returned list should not be null", unreturnedTapes);
        assertTrue("List should be empty when no rentals exist", unreturnedTapes.isEmpty());
    }
    
    @Test
    public void testCase2_AllTapesReturned() {
        // Input: customer_id="C002"
        // Setup: Create Customer C002 with Rental 1: tape_id="T001", return_date="2025-01-01"
        customer = new Customer("C002");
        
        Tape tape1 = new Tape("T001", "Video 1");
        VideoRental rental1 = new VideoRental(
            LocalDate.of(2025, 1, 1), 
            LocalDate.of(2025, 1, 1), 
            0.0, 
            tape1
        );
        
        List<VideoRental> rentals = new java.util.ArrayList<>();
        rentals.add(rental1);
        customer.setRentals(rentals);
        
        // Expected Output: No unreturned rentals (empty list)
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        assertNotNull("Returned list should not be null", unreturnedTapes);
        assertTrue("List should be empty when all tapes are returned", unreturnedTapes.isEmpty());
    }
    
    @Test
    public void testCase3_OneUnreturnedTape() {
        // Input: customer_id="C003"
        // Setup: Create Customer C003 with Rental 1: tape_id="T001", return_date=null
        customer = new Customer("C003");
        
        Tape tape1 = new Tape("T001", "Video 1");
        VideoRental rental1 = new VideoRental(
            LocalDate.of(2025, 1, 1), 
            null, 
            0.0, 
            tape1
        );
        
        List<VideoRental> rentals = new java.util.ArrayList<>();
        rentals.add(rental1);
        customer.setRentals(rentals);
        
        // Expected Output: List containing T001
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        assertNotNull("Returned list should not be null", unreturnedTapes);
        assertEquals("List should contain exactly 1 tape", 1, unreturnedTapes.size());
        assertEquals("Tape ID should be T001", "T001", unreturnedTapes.get(0).getId());
    }
    
    @Test
    public void testCase4_MixedReturnedUnreturned() {
        // Input: customer_id="C004"
        // Setup: Create Customer C004 with:
        //   - Rental 1: tape_id="T001", return_date="2025-01-01"
        //   - Rental 2: tape_id="T002", return_date=null
        customer = new Customer("C004");
        
        Tape tape1 = new Tape("T001", "Video 1");
        Tape tape2 = new Tape("T002", "Video 2");
        
        VideoRental rental1 = new VideoRental(
            LocalDate.of(2025, 1, 1), 
            LocalDate.of(2025, 1, 1), 
            0.0, 
            tape1
        );
        
        VideoRental rental2 = new VideoRental(
            LocalDate.of(2025, 1, 1), 
            null, 
            0.0, 
            tape2
        );
        
        List<VideoRental> rentals = new java.util.ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Expected Output: List containing T002
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        assertNotNull("Returned list should not be null", unreturnedTapes);
        assertEquals("List should contain exactly 1 tape", 1, unreturnedTapes.size());
        assertEquals("Tape ID should be T002", "T002", unreturnedTapes.get(0).getId());
    }
    
    @Test
    public void testCase5_MultipleUnreturnedTapes() {
        // Input: customer_id="C005"
        // Setup: Create Customer C005 with:
        //   - Rental 1: tape_id="T001", return_date=null
        //   - Rental 2: tape_id="T002", return_date=null
        customer = new Customer("C005");
        
        Tape tape1 = new Tape("T001", "Video 1");
        Tape tape2 = new Tape("T002", "Video 2");
        
        VideoRental rental1 = new VideoRental(
            LocalDate.of(2025, 1, 1), 
            null, 
            0.0, 
            tape1
        );
        
        VideoRental rental2 = new VideoRental(
            LocalDate.of(2025, 1, 1), 
            null, 
            0.0, 
            tape2
        );
        
        List<VideoRental> rentals = new java.util.ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Expected Output: List containing T001 and T002
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        assertNotNull("Returned list should not be null", unreturnedTapes);
        assertEquals("List should contain exactly 2 tapes", 2, unreturnedTapes.size());
        
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
        assertTrue("List should contain T001", foundT001);
        assertTrue("List should contain T002", foundT002);
    }
}