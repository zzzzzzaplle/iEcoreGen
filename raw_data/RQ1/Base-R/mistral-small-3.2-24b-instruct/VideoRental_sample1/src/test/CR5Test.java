import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

public class CR5Test {
    
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_noRentalsExist() {
        // Test Case 1: "No rentals exist"
        // Input: customer_id="C001"
        // Setup: Create Customer C001 with empty rentals list
        Customer customer = new Customer();
        customer.setAccountNumber("C001");
        
        // Execute method under test
        List<String> result = customer.listUnreturnedTapes();
        
        // Expected Output: Empty list, no active rentals
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase2_allTapesReturned() {
        // Test Case 2: "All tapes returned"
        // Input: customer_id="C002"
        // Setup: Create Customer C002
        Customer customer = new Customer();
        customer.setAccountNumber("C002");
        
        // Create Tape T001
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        
        // Create VideoRental with rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01"
        Rental rental = new Rental();
        rental.setVideoTape(tape1);
        rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(LocalDate.parse("2025-01-01", formatter));
        
        // Associate with C002 and T001
        customer.addRental(rental);
        
        // Execute method under test
        List<String> result = customer.listUnreturnedTapes();
        
        // Expected Output: Empty list, all rentals returned
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase3_oneUnreturnedTape() {
        // Test Case 3: "One unreturned tape"
        // Input: customer_id="C003"
        // Setup: Create Customer C003
        Customer customer = new Customer();
        customer.setAccountNumber("C003");
        
        // Create Tape T001
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        
        // Create VideoRental with rental_date="2025-01-01", due_date="2025-01-05", return_date=null
        Rental rental = new Rental();
        rental.setVideoTape(tape1);
        rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(null);
        
        // Associate with C003 and T001
        customer.addRental(rental);
        
        // Execute method under test
        List<String> result = customer.listUnreturnedTapes();
        
        // Expected Output: List containing T001
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly one item", 1, result.size());
        assertEquals("Result should contain T001", "T001", result.get(0));
    }
    
    @Test
    public void testCase4_mixedReturnedUnreturned() {
        // Test Case 4: "Mixed returned/unreturned"
        // Input: customer_id="C004"
        // Setup: Create Customer C004
        Customer customer = new Customer();
        customer.setAccountNumber("C004");
        
        // Create Tape T001 and T002
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T002");
        
        // Create VideoRental for T001 with rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01"
        Rental rental1 = new Rental();
        rental1.setVideoTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-01", formatter));
        
        // Create VideoRental for T002 with rental_date="2025-01-02", due_date="2025-01-06", return_date=null
        Rental rental2 = new Rental();
        rental2.setVideoTape(tape2);
        rental2.setRentalDate(LocalDate.parse("2025-01-02", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-06", formatter));
        rental2.setReturnDate(null);
        
        // Associate with C004
        customer.addRental(rental1);
        customer.addRental(rental2);
        
        // Execute method under test
        List<String> result = customer.listUnreturnedTapes();
        
        // Expected Output: List containing T002
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly one item", 1, result.size());
        assertEquals("Result should contain T002", "T002", result.get(0));
    }
    
    @Test
    public void testCase5_multipleUnreturnedTapes() {
        // Test Case 5: "Multiple unreturned tapes"
        // Input: customer_id="C005"
        // Setup: Create Customer C005
        Customer customer = new Customer();
        customer.setAccountNumber("C005");
        
        // Create Tapes T001 and T002
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T002");
        
        // Create VideoRental for T001 with rental_date="2025-01-01", due_date="2025-01-05", return_date=null
        Rental rental1 = new Rental();
        rental1.setVideoTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(null);
        
        // Create VideoRental for T002 with rental_date="2025-01-02", due_date="2025-01-06", return_date=null
        Rental rental2 = new Rental();
        rental2.setVideoTape(tape2);
        rental2.setRentalDate(LocalDate.parse("2025-01-02", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-06", formatter));
        rental2.setReturnDate(null);
        
        // Associate with C005
        customer.addRental(rental1);
        customer.addRental(rental2);
        
        // Execute method under test
        List<String> result = customer.listUnreturnedTapes();
        
        // Expected Output: List containing T001 and T002
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly two items", 2, result.size());
        assertTrue("Result should contain T001", result.contains("T001"));
        assertTrue("Result should contain T002", result.contains("T002"));
    }
}