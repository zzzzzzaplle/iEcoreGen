import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR5Test {
    
    private Customer customer;
    private VideoTape tape1;
    private VideoTape tape2;
    private Rental rental1;
    private Rental rental2;
    
    @Before
    public void setUp() {
        // Initialize common objects for tests
        customer = new Customer();
        tape1 = new VideoTape();
        tape2 = new VideoTape();
        rental1 = new Rental();
        rental2 = new Rental();
        
        tape1.setId("T001");
        tape2.setId("T002");
    }
    
    @Test
    public void testCase1_noRentalsExist() {
        // Setup: Create Customer C001 with empty rentals list
        customer.setId("C001");
        customer.setRentals(new ArrayList<Rental>());
        
        // Execute: Call listUnreturnedTapes method
        List<String> result = customer.listUnreturnedTapes();
        
        // Verify: Empty list, no active rentals
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase2_allTapesReturned() {
        // Setup: Create Customer C002
        customer.setId("C002");
        customer.setRentals(new ArrayList<Rental>());
        
        // Setup: Create VideoRental with return date
        rental1.setTapeId("T001");
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-01");
        
        customer.addRental(rental1);
        
        // Execute: Call listUnreturnedTapes method
        List<String> result = customer.listUnreturnedTapes();
        
        // Verify: Empty list, all rentals returned
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase3_oneUnreturnedTape() {
        // Setup: Create Customer C003
        customer.setId("C003");
        customer.setRentals(new ArrayList<Rental>());
        
        // Setup: Create VideoRental with null return date
        rental1.setTapeId("T001");
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate(null);
        
        customer.addRental(rental1);
        
        // Execute: Call listUnreturnedTapes method
        List<String> result = customer.listUnreturnedTapes();
        
        // Verify: List containing T001
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly one element", 1, result.size());
        assertTrue("Result should contain T001", result.contains("T001"));
    }
    
    @Test
    public void testCase4_mixedReturnedUnreturned() {
        // Setup: Create Customer C004
        customer.setId("C004");
        customer.setRentals(new ArrayList<Rental>());
        
        // Setup: Create VideoRental for T001 with return date (returned)
        rental1.setTapeId("T001");
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-01");
        
        // Setup: Create VideoRental for T002 with null return date (unreturned)
        rental2.setTapeId("T002");
        rental2.setRentalDate("2025-01-02");
        rental2.setDueDate("2025-01-06");
        rental2.setReturnDate(null);
        
        customer.addRental(rental1);
        customer.addRental(rental2);
        
        // Execute: Call listUnreturnedTapes method
        List<String> result = customer.listUnreturnedTapes();
        
        // Verify: List containing T002
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly one element", 1, result.size());
        assertTrue("Result should contain T002", result.contains("T002"));
        assertFalse("Result should not contain T001", result.contains("T001"));
    }
    
    @Test
    public void testCase5_multipleUnreturnedTapes() {
        // Setup: Create Customer C005
        customer.setId("C005");
        customer.setRentals(new ArrayList<Rental>());
        
        // Setup: Create VideoRental for T001 with null return date
        rental1.setTapeId("T001");
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate(null);
        
        // Setup: Create VideoRental for T002 with null return date
        rental2.setTapeId("T002");
        rental2.setRentalDate("2025-01-02");
        rental2.setDueDate("2025-01-06");
        rental2.setReturnDate(null);
        
        customer.addRental(rental1);
        customer.addRental(rental2);
        
        // Execute: Call listUnreturnedTapes method
        List<String> result = customer.listUnreturnedTapes();
        
        // Verify: List containing T001 and T002
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly two elements", 2, result.size());
        assertTrue("Result should contain T001", result.contains("T001"));
        assertTrue("Result should contain T002", result.contains("T002"));
    }
}