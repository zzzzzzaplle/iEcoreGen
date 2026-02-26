import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR5Test {
    
    @Test
    public void testCase1_noRentalsExist() {
        // Test Case 1: "No rentals exist"
        // Input: customer_id="C001"
        // Setup: Create Customer C001 with empty rentals list
        Customer customer = new Customer();
        customer.setIdCard("C001");
        
        // Expected Output: Empty list, no active rentals.
        List<String> result = customer.listUnreturnedTapes();
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase2_allTapesReturned() {
        // Test Case 2: "All tapes returned"
        // Input: customer_id="C002"
        // Setup:
        // 1. Create Customer C002
        Customer customer = new Customer();
        customer.setIdCard("C002");
        
        // 2. Create Tape T001
        // 3. Create VideoRental with rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01", associated with C002 and T001
        Rental rental = new Rental();
        rental.setTapeId("T001");
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-05");
        rental.setReturnDate("2025-01-01");
        
        RentalTransaction transaction = new RentalTransaction();
        transaction.addRental(rental);
        customer.addRentalTransaction(transaction);
        
        // Expected Output: Empty list, all rentals returned.
        List<String> result = customer.listUnreturnedTapes();
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase3_oneUnreturnedTape() {
        // Test Case 3: "One unreturned tape"
        // Input: customer_id="C003"
        // Setup:
        // 1. Create Customer C003
        Customer customer = new Customer();
        customer.setIdCard("C003");
        
        // 2. Create Tape T001
        // 3. Create VideoRental with rental_date="2025-01-01", due_date="2025-01-05", return_date=null, associated with C003 and T001
        Rental rental = new Rental();
        rental.setTapeId("T001");
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-05");
        rental.setReturnDate(null);
        
        RentalTransaction transaction = new RentalTransaction();
        transaction.addRental(rental);
        customer.addRentalTransaction(transaction);
        
        // Expected Output: List containing T001.
        List<String> result = customer.listUnreturnedTapes();
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly 1 item", 1, result.size());
        assertTrue("Result should contain T001", result.contains("T001"));
    }
    
    @Test
    public void testCase4_mixedReturnedUnreturned() {
        // Test Case 4: "Mixed returned/unreturned"
        // Input: customer_id="C004"
        // Setup:
        // 1. Create Customer C004
        Customer customer = new Customer();
        customer.setIdCard("C004");
        
        // 2. Create Tape T001 and T002
        // 3. Create VideoRental for T001 with rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01", associated with C004
        Rental rental1 = new Rental();
        rental1.setTapeId("T001");
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-01");
        
        // 4. Create VideoRental for T002 with rental_date="2025-01-02", due_date="2025-01-06", return_date=null, associated with C004
        Rental rental2 = new Rental();
        rental2.setTapeId("T002");
        rental2.setRentalDate("2025-01-02");
        rental2.setDueDate("2025-01-06");
        rental2.setReturnDate(null);
        
        RentalTransaction transaction = new RentalTransaction();
        transaction.addRental(rental1);
        transaction.addRental(rental2);
        customer.addRentalTransaction(transaction);
        
        // Expected Output: List containing T002.
        List<String> result = customer.listUnreturnedTapes();
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly 1 item", 1, result.size());
        assertTrue("Result should contain T002", result.contains("T002"));
    }
    
    @Test
    public void testCase5_multipleUnreturnedTapes() {
        // Test Case 5: "Multiple unreturned tapes"
        // Input: customer_id="C005"
        // Setup:
        // 1. Create Customer C005
        Customer customer = new Customer();
        customer.setIdCard("C005");
        
        // 2. Create Tapes T001 and T002
        // 3. Create VideoRental for T001 with rental_date="2025-01-01", due_date="2025-01-05", return_date=null, associated with C005
        Rental rental1 = new Rental();
        rental1.setTapeId("T001");
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate(null);
        
        // 4. Create VideoRental for T002 with rental_date="2025-01-02", due_date="2025-01-06", return_date=null, associated with C005
        Rental rental2 = new Rental();
        rental2.setTapeId("T002");
        rental2.setRentalDate("2025-01-02");
        rental2.setDueDate("2025-01-06");
        rental2.setReturnDate(null);
        
        RentalTransaction transaction = new RentalTransaction();
        transaction.addRental(rental1);
        transaction.addRental(rental2);
        customer.addRentalTransaction(transaction);
        
        // Expected Output: List containing T001 and T002.
        List<String> result = customer.listUnreturnedTapes();
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly 2 items", 2, result.size());
        assertTrue("Result should contain T001", result.contains("T001"));
        assertTrue("Result should contain T002", result.contains("T002"));
    }
}