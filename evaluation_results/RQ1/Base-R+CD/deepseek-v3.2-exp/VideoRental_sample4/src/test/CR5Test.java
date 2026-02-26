import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

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
        // Setup: Create Customer C001 with empty rentals list
        customer = new Customer();
        customer.setId("C001");
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: Empty list, no active rentals
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty list", result.isEmpty());
        assertEquals("List size should be 0", 0, result.size());
    }
    
    @Test
    public void testCase2_AllTapesReturned() {
        // Setup: Create Customer C002
        customer = new Customer();
        customer.setId("C002");
        
        // Create Tape T001
        tape1 = new Tape();
        tape1.setId("T001");
        
        // Create VideoRental with return_date set (tape returned)
        rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setReturnDate(new Date("2025-01-01 12:00:00"));
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        customer.setRentals(rentals);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: Empty list, all rentals returned
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty list", result.isEmpty());
        assertEquals("List size should be 0", 0, result.size());
    }
    
    @Test
    public void testCase3_OneUnreturnedTape() {
        // Setup: Create Customer C003
        customer = new Customer();
        customer.setId("C003");
        
        // Create Tape T001
        tape1 = new Tape();
        tape1.setId("T001");
        
        // Create VideoRental with return_date=null (tape not returned)
        rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setReturnDate(null); // Tape not returned
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        customer.setRentals(rentals);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: List containing T001
        assertNotNull("Result should not be null", result);
        assertFalse("Result should not be empty", result.isEmpty());
        assertEquals("List size should be 1", 1, result.size());
        assertEquals("Tape ID should be T001", "T001", result.get(0).getId());
    }
    
    @Test
    public void testCase4_MixedReturnedUnreturned() {
        // Setup: Create Customer C004
        customer = new Customer();
        customer.setId("C004");
        
        // Create Tape T001 and T002
        tape1 = new Tape();
        tape1.setId("T001");
        tape2 = new Tape();
        tape2.setId("T002");
        
        // Create VideoRental for T001 with return_date set (returned)
        rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setReturnDate(new Date("2025-01-01 12:00:00")); // Tape returned
        
        // Create VideoRental for T002 with return_date=null (not returned)
        rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setReturnDate(null); // Tape not returned
        
        // Add rentals to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: List containing T002 only
        assertNotNull("Result should not be null", result);
        assertFalse("Result should not be empty", result.isEmpty());
        assertEquals("List size should be 1", 1, result.size());
        assertEquals("Tape ID should be T002", "T002", result.get(0).getId());
    }
    
    @Test
    public void testCase5_MultipleUnreturnedTapes() {
        // Setup: Create Customer C005
        customer = new Customer();
        customer.setId("C005");
        
        // Create Tapes T001 and T002
        tape1 = new Tape();
        tape1.setId("T001");
        tape2 = new Tape();
        tape2.setId("T002");
        
        // Create VideoRental for T001 with return_date=null (not returned)
        rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setReturnDate(null); // Tape not returned
        
        // Create VideoRental for T002 with return_date=null (not returned)
        rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setReturnDate(null); // Tape not returned
        
        // Add rentals to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: List containing T001 and T002
        assertNotNull("Result should not be null", result);
        assertFalse("Result should not be empty", result.isEmpty());
        assertEquals("List size should be 2", 2, result.size());
        
        // Verify both tapes are in the result (order doesn't matter)
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
        assertTrue("Should contain T001", foundT001);
        assertTrue("Should contain T002", foundT002);
    }
}