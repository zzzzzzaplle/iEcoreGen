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
        // Input: customer_id="C001"
        // Setup: Create Customer C001 with empty rentals list
        customer = new Customer();
        customer.setAccountNumber("C001");
        
        // Expected Output: Empty list, no active rentals
        List<String> result = customer.getUnreturnedTapes();
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty list", result.isEmpty());
    }
    
    @Test
    public void testCase2_AllTapesReturned() {
        // Test Case 2: "All tapes returned"
        // Input: customer_id="C002"
        // Setup: Create Customer C002, Tape T001, and VideoRental with return_date
        customer = new Customer();
        customer.setAccountNumber("C002");
        
        tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        tape1.setTitle("Test Tape 1");
        
        rental1 = new Rental();
        rental1.setTape(tape1);
        rental1.setReturnDate("2025-01-01"); // Tape has been returned
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        customer.setRentals(rentals);
        
        // Expected Output: Empty list, all rentals returned
        List<String> result = customer.getUnreturnedTapes();
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty list", result.isEmpty());
    }
    
    @Test
    public void testCase3_OneUnreturnedTape() {
        // Test Case 3: "One unreturned tape"
        // Input: customer_id="C003"
        // Setup: Create Customer C003, Tape T001, and VideoRental with return_date=null
        customer = new Customer();
        customer.setAccountNumber("C003");
        
        tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        tape1.setTitle("Test Tape 1");
        
        rental1 = new Rental();
        rental1.setTape(tape1);
        rental1.setReturnDate(null); // Tape has NOT been returned
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        customer.setRentals(rentals);
        
        // Expected Output: List containing T001
        List<String> result = customer.getUnreturnedTapes();
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly one element", 1, result.size());
        assertTrue("Result should contain T001", result.contains("T001"));
    }
    
    @Test
    public void testCase4_MixedReturnedUnreturned() {
        // Test Case 4: "Mixed returned/unreturned"
        // Input: customer_id="C004"
        // Setup: Create Customer C004, Tapes T001 and T002
        // One rental returned, one rental not returned
        customer = new Customer();
        customer.setAccountNumber("C004");
        
        tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        tape1.setTitle("Test Tape 1");
        
        tape2 = new VideoTape();
        tape2.setBarcodeId("T002");
        tape2.setTitle("Test Tape 2");
        
        rental1 = new Rental();
        rental1.setTape(tape1);
        rental1.setReturnDate("2025-01-01"); // Tape has been returned
        
        rental2 = new Rental();
        rental2.setTape(tape2);
        rental2.setReturnDate(null); // Tape has NOT been returned
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Expected Output: List containing T002
        List<String> result = customer.getUnreturnedTapes();
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly one element", 1, result.size());
        assertTrue("Result should contain T002", result.contains("T002"));
        assertFalse("Result should not contain T001", result.contains("T001"));
    }
    
    @Test
    public void testCase5_MultipleUnreturnedTapes() {
        // Test Case 5: "Multiple unreturned tapes"
        // Input: customer_id="C005"
        // Setup: Create Customer C005, Tapes T001 and T002
        // Both rentals not returned
        customer = new Customer();
        customer.setAccountNumber("C005");
        
        tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        tape1.setTitle("Test Tape 1");
        
        tape2 = new VideoTape();
        tape2.setBarcodeId("T002");
        tape2.setTitle("Test Tape 2");
        
        rental1 = new Rental();
        rental1.setTape(tape1);
        rental1.setReturnDate(null); // Tape has NOT been returned
        
        rental2 = new Rental();
        rental2.setTape(tape2);
        rental2.setReturnDate(null); // Tape has NOT been returned
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Expected Output: List containing T001 and T002
        List<String> result = customer.getUnreturnedTapes();
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly two elements", 2, result.size());
        assertTrue("Result should contain T001", result.contains("T001"));
        assertTrue("Result should contain T002", result.contains("T002"));
    }
}