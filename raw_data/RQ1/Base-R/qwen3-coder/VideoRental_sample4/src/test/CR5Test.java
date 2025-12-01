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
    public void testCase1_NoRentalsExist() {
        // Test Case 1: "No rentals exist"
        // Input: customer_id="C001"
        // Setup: Create Customer C001 with empty rentals list
        
        // Create customer C001
        Customer customer = new Customer();
        customer.setAccountId("C001");
        
        // Method under test
        List<String> result = rentalSystem.listUnreturnedTapes(customer);
        
        // Expected Output: Empty list, no active rentals
        assertTrue("List should be empty when customer has no rentals", result.isEmpty());
    }
    
    @Test
    public void testCase2_AllTapesReturned() {
        // Test Case 2: "All tapes returned"
        // Input: customer_id="C002"
        // Setup: Create Customer C002, Tape T001, VideoRental with return_date
        
        // Create customer C002
        Customer customer = new Customer();
        customer.setAccountId("C002");
        
        // Create tape T001
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T001");
        rentalSystem.getVideoTapes().add(tape);
        
        // Create rental with return_date (tape has been returned)
        Rental rental = new Rental();
        rental.setTapeId("T001");
        rental.setRentalDate(LocalDate.of(2025, 1, 1));
        rental.setDueDate(LocalDate.of(2025, 1, 5));
        rental.setReturnDate(LocalDate.of(2025, 1, 1));
        
        // Add rental to customer and system
        customer.getRentals().add(rental);
        rentalSystem.getRentals().add(rental);
        
        // Method under test
        List<String> result = rentalSystem.listUnreturnedTapes(customer);
        
        // Expected Output: Empty list, all rentals returned
        assertTrue("List should be empty when all tapes have been returned", result.isEmpty());
    }
    
    @Test
    public void testCase3_OneUnreturnedTape() {
        // Test Case 3: "One unreturned tape"
        // Input: customer_id="C003"
        // Setup: Create Customer C003, Tape T001, VideoRental with return_date=null
        
        // Create customer C003
        Customer customer = new Customer();
        customer.setAccountId("C003");
        
        // Create tape T001
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T001");
        rentalSystem.getVideoTapes().add(tape);
        
        // Create rental with return_date=null (tape not returned)
        Rental rental = new Rental();
        rental.setTapeId("T001");
        rental.setRentalDate(LocalDate.of(2025, 1, 1));
        rental.setDueDate(LocalDate.of(2025, 1, 5));
        rental.setReturnDate(null); // Tape not returned
        
        // Add rental to customer and system
        customer.getRentals().add(rental);
        rentalSystem.getRentals().add(rental);
        
        // Method under test
        List<String> result = rentalSystem.listUnreturnedTapes(customer);
        
        // Expected Output: List containing T001
        assertEquals("List should contain exactly one element", 1, result.size());
        assertTrue("List should contain T001", result.contains("T001"));
    }
    
    @Test
    public void testCase4_MixedReturnedUnreturned() {
        // Test Case 4: "Mixed returned/unreturned"
        // Input: customer_id="C004"
        // Setup: Create Customer C004, Tapes T001 and T002
        // Two rentals: one returned, one unreturned
        
        // Create customer C004
        Customer customer = new Customer();
        customer.setAccountId("C004");
        
        // Create tapes T001 and T002
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T002");
        rentalSystem.getVideoTapes().add(tape1);
        rentalSystem.getVideoTapes().add(tape2);
        
        // Create rental for T001 with return_date (returned)
        Rental rental1 = new Rental();
        rental1.setTapeId("T001");
        rental1.setRentalDate(LocalDate.of(2025, 1, 1));
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 1));
        
        // Create rental for T002 with return_date=null (unreturned)
        Rental rental2 = new Rental();
        rental2.setTapeId("T002");
        rental2.setRentalDate(LocalDate.of(2025, 1, 2));
        rental2.setDueDate(LocalDate.of(2025, 1, 6));
        rental2.setReturnDate(null); // Tape not returned
        
        // Add rentals to customer and system
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        rentalSystem.getRentals().add(rental1);
        rentalSystem.getRentals().add(rental2);
        
        // Method under test
        List<String> result = rentalSystem.listUnreturnedTapes(customer);
        
        // Expected Output: List containing T002
        assertEquals("List should contain exactly one element", 1, result.size());
        assertTrue("List should contain T002", result.contains("T002"));
        assertFalse("List should not contain T001", result.contains("T001"));
    }
    
    @Test
    public void testCase5_MultipleUnreturnedTapes() {
        // Test Case 5: "Multiple unreturned tapes"
        // Input: customer_id="C005"
        // Setup: Create Customer C005, Tapes T001 and T002
        // Two rentals: both unreturned
        
        // Create customer C005
        Customer customer = new Customer();
        customer.setAccountId("C005");
        
        // Create tapes T001 and T002
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T002");
        rentalSystem.getVideoTapes().add(tape1);
        rentalSystem.getVideoTapes().add(tape2);
        
        // Create rental for T001 with return_date=null (unreturned)
        Rental rental1 = new Rental();
        rental1.setTapeId("T001");
        rental1.setRentalDate(LocalDate.of(2025, 1, 1));
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(null); // Tape not returned
        
        // Create rental for T002 with return_date=null (unreturned)
        Rental rental2 = new Rental();
        rental2.setTapeId("T002");
        rental2.setRentalDate(LocalDate.of(2025, 1, 2));
        rental2.setDueDate(LocalDate.of(2025, 1, 6));
        rental2.setReturnDate(null); // Tape not returned
        
        // Add rentals to customer and system
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        rentalSystem.getRentals().add(rental1);
        rentalSystem.getRentals().add(rental2);
        
        // Method under test
        List<String> result = rentalSystem.listUnreturnedTapes(customer);
        
        // Expected Output: List containing T001 and T002
        assertEquals("List should contain exactly two elements", 2, result.size());
        assertTrue("List should contain T001", result.contains("T001"));
        assertTrue("List should contain T002", result.contains("T002"));
    }
}