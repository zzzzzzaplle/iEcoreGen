import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;

public class CR5Test {
    
    private RentalSystem rentalSystem;
    
    @Before
    public void setUp() {
        rentalSystem = new RentalSystem();
    }
    
    @Test
    public void testCase1_noRentalsExist() {
        // Test Case 1: "No rentals exist"
        // Input: customer_id="C001"
        // Setup: Create Customer C001 with empty rentals list
        Customer customer = new Customer();
        customer.setAccountId("C001");
        
        // Execute the method under test
        List<String> result = rentalSystem.listUnreturnedTapes(customer);
        
        // Expected Output: Empty list, no active rentals.
        assertTrue("List should be empty when customer has no rentals", result.isEmpty());
    }
    
    @Test
    public void testCase2_allTapesReturned() {
        // Test Case 2: "All tapes returned"
        // Input: customer_id="C002"
        // Setup:
        // 1. Create Customer C002
        Customer customer = new Customer();
        customer.setAccountId("C002");
        
        // 2. Create Tape T001
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T001");
        tape.setTitle("Test Movie");
        
        // 3. Create VideoRental with rental_date="2025-01-01", due_date="2025-01-05", 
        //    return_date="2025-01-01", associated with C002 and T001
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.of(2025, 1, 1));
        rental.setDueDate(LocalDate.of(2025, 1, 5));
        rental.setReturnDate(LocalDate.of(2025, 1, 1));
        
        // Add rental to customer's rental list
        customer.getRentals().add(rental);
        
        // Execute the method under test
        List<String> result = rentalSystem.listUnreturnedTapes(customer);
        
        // Expected Output: Empty list, all rentals returned.
        assertTrue("List should be empty when all tapes are returned", result.isEmpty());
    }
    
    @Test
    public void testCase3_oneUnreturnedTape() {
        // Test Case 3: "One unreturned tape"
        // Input: customer_id="C003"
        // Setup:
        // 1. Create Customer C003
        Customer customer = new Customer();
        customer.setAccountId("C003");
        
        // 2. Create Tape T001
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T001");
        tape.setTitle("Test Movie");
        
        // 3. Create VideoRental with rental_date="2025-01-01", due_date="2025-01-05", 
        //    return_date=null, associated with C003 and T001
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.of(2025, 1, 1));
        rental.setDueDate(LocalDate.of(2025, 1, 5));
        rental.setReturnDate(null); // Not returned
        
        // Add rental to customer's rental list
        customer.getRentals().add(rental);
        
        // Execute the method under test
        List<String> result = rentalSystem.listUnreturnedTapes(customer);
        
        // Expected Output: List containing T001.
        assertEquals("List should contain exactly one item", 1, result.size());
        assertTrue("List should contain T001", result.contains("T001"));
    }
    
    @Test
    public void testCase4_mixedReturnedUnreturned() {
        // Test Case 4: "Mixed returned/unreturned"
        // Input: customer_id="C004"
        // Setup:
        // 1. Create Customer C004
        Customer customer = new Customer();
        customer.setAccountId("C004");
        
        // 2. Create Tape T001 and T002
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        tape1.setTitle("Movie 1");
        
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T002");
        tape2.setTitle("Movie 2");
        
        // 3. Create VideoRental for T001 with rental_date="2025-01-01", due_date="2025-01-05", 
        //    return_date="2025-01-01", associated with C004
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setTape(tape1);
        rental1.setRentalDate(LocalDate.of(2025, 1, 1));
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 1)); // Returned
        
        // 4. Create VideoRental for T002 with rental_date="2025-01-02", due_date="2025-01-06", 
        //    return_date=null, associated with C004
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setTape(tape2);
        rental2.setRentalDate(LocalDate.of(2025, 1, 2));
        rental2.setDueDate(LocalDate.of(2025, 1, 6));
        rental2.setReturnDate(null); // Not returned
        
        // Add rentals to customer's rental list
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute the method under test
        List<String> result = rentalSystem.listUnreturnedTapes(customer);
        
        // Expected Output: List containing T002.
        assertEquals("List should contain exactly one item", 1, result.size());
        assertTrue("List should contain T002", result.contains("T002"));
        assertFalse("List should not contain T001", result.contains("T001"));
    }
    
    @Test
    public void testCase5_multipleUnreturnedTapes() {
        // Test Case 5: "Multiple unreturned tapes"
        // Input: customer_id="C005"
        // Setup:
        // 1. Create Customer C005
        Customer customer = new Customer();
        customer.setAccountId("C005");
        
        // 2. Create Tapes T001 and T002
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        tape1.setTitle("Movie 1");
        
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T002");
        tape2.setTitle("Movie 2");
        
        // 3. Create VideoRental for T001 with rental_date="2025-01-01", due_date="2025-01-05", 
        //    return_date=null, associated with C005
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setTape(tape1);
        rental1.setRentalDate(LocalDate.of(2025, 1, 1));
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(null); // Not returned
        
        // 4. Create VideoRental for T002 with rental_date="2025-01-02", due_date="2025-01-06", 
        //    return_date=null, associated with C005
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setTape(tape2);
        rental2.setRentalDate(LocalDate.of(2025, 1, 2));
        rental2.setDueDate(LocalDate.of(2025, 1, 6));
        rental2.setReturnDate(null); // Not returned
        
        // Add rentals to customer's rental list
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute the method under test
        List<String> result = rentalSystem.listUnreturnedTapes(customer);
        
        // Expected Output: List containing T001 and T002.
        assertEquals("List should contain exactly two items", 2, result.size());
        assertTrue("List should contain T001", result.contains("T001"));
        assertTrue("List should contain T002", result.contains("T002"));
    }
}