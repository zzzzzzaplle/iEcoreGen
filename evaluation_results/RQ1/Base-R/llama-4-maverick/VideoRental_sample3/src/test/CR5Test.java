import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR5Test {
    
    private VideoRentalSystem system;
    
    @Before
    public void setUp() {
        system = new VideoRentalSystem();
    }
    
    @Test
    public void testCase1_noRentalsExist() {
        // Test Case 1: "No rentals exist"
        // Input: customer_id="C001"
        // Setup: Create Customer C001 with empty rentals list
        // Expected Output: Empty list, no active rentals.
        
        Customer customer = new Customer();
        customer.setIdCard("C001");
        
        List<String> result = system.listUnreturnedTapes(customer);
        
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty when no rentals exist", result.isEmpty());
    }
    
    @Test
    public void testCase2_allTapesReturned() {
        // Test Case 2: "All tapes returned"
        // Input: customer_id="C002"
        // Setup:
        // 1. Create Customer C002
        // 2. Create Tape T001
        // 3. Create VideoRental with rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01", associated with C002 and T001
        // Expected Output: Empty list, all rentals returned.
        
        Customer customer = new Customer();
        customer.setIdCard("C002");
        
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("T001");
        
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-05");
        rental.setReturnDate("2025-01-01");
        
        RentalTransaction transaction = new RentalTransaction();
        transaction.getRentals().add(rental);
        customer.getRentalTransactions().add(transaction);
        
        List<String> result = system.listUnreturnedTapes(customer);
        
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty when all tapes are returned", result.isEmpty());
    }
    
    @Test
    public void testCase3_oneUnreturnedTape() {
        // Test Case 3: "One unreturned tape"
        // Input: customer_id="C003"
        // Setup:
        // 1. Create Customer C003
        // 2. Create Tape T001
        // 3. Create VideoRental with rental_date="2025-01-01", due_date="2025-01-05", return_date=null, associated with C003 and T001
        // Expected Output: List containing T001.
        
        Customer customer = new Customer();
        customer.setIdCard("C003");
        
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("T001");
        
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-05");
        rental.setReturnDate(null);
        
        RentalTransaction transaction = new RentalTransaction();
        transaction.getRentals().add(rental);
        customer.getRentalTransactions().add(transaction);
        
        List<String> result = system.listUnreturnedTapes(customer);
        
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly one tape", 1, result.size());
        assertTrue("Result should contain T001", result.contains("T001"));
    }
    
    @Test
    public void testCase4_mixedReturnedUnreturned() {
        // Test Case 4: "Mixed returned/unreturned"
        // Input: customer_id="C004"
        // Setup:
        // 1. Create Customer C004
        // 2. Create Tape T001 and T002
        // 3. Create VideoRental for T001 with rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01", associated with C004
        // 4. Create VideoRental for T002 with rental_date="2025-01-02", due_date="2025-01-06", return_date=null, associated with C004
        // Expected Output: List containing T002.
        
        Customer customer = new Customer();
        customer.setIdCard("C004");
        
        // Create first tape (returned)
        VideoTape tape1 = new VideoTape();
        tape1.setBarCodeId("T001");
        
        Rental rental1 = new Rental();
        rental1.setVideoTape(tape1);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-01");
        
        RentalTransaction transaction1 = new RentalTransaction();
        transaction1.getRentals().add(rental1);
        
        // Create second tape (unreturned)
        VideoTape tape2 = new VideoTape();
        tape2.setBarCodeId("T002");
        
        Rental rental2 = new Rental();
        rental2.setVideoTape(tape2);
        rental2.setRentalDate("2025-01-02");
        rental2.setDueDate("2025-01-06");
        rental2.setReturnDate(null);
        
        RentalTransaction transaction2 = new RentalTransaction();
        transaction2.getRentals().add(rental2);
        
        // Add both transactions to customer
        customer.getRentalTransactions().add(transaction1);
        customer.getRentalTransactions().add(transaction2);
        
        List<String> result = system.listUnreturnedTapes(customer);
        
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly one tape", 1, result.size());
        assertTrue("Result should contain T002", result.contains("T002"));
        assertFalse("Result should not contain T001 (returned tape)", result.contains("T001"));
    }
    
    @Test
    public void testCase5_multipleUnreturnedTapes() {
        // Test Case 5: "Multiple unreturned tapes"
        // Input: customer_id="C005"
        // Setup:
        // 1. Create Customer C005
        // 2. Create Tapes T001 and T002
        // 3. Create VideoRental for T001 with rental_date="2025-01-01", due_date="2025-01-05", return_date=null, associated with C005
        // 4. Create VideoRental for T002 with rental_date="2025-01-02", due_date="2025-01-06", return_date=null, associated with C005
        // Expected Output: List containing T001 and T002.
        
        Customer customer = new Customer();
        customer.setIdCard("C005");
        
        // Create first tape (unreturned)
        VideoTape tape1 = new VideoTape();
        tape1.setBarCodeId("T001");
        
        Rental rental1 = new Rental();
        rental1.setVideoTape(tape1);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate(null);
        
        RentalTransaction transaction1 = new RentalTransaction();
        transaction1.getRentals().add(rental1);
        
        // Create second tape (unreturned)
        VideoTape tape2 = new VideoTape();
        tape2.setBarCodeId("T002");
        
        Rental rental2 = new Rental();
        rental2.setVideoTape(tape2);
        rental2.setRentalDate("2025-01-02");
        rental2.setDueDate("2025-01-06");
        rental2.setReturnDate(null);
        
        RentalTransaction transaction2 = new RentalTransaction();
        transaction2.getRentals().add(rental2);
        
        // Add both transactions to customer
        customer.getRentalTransactions().add(transaction1);
        customer.getRentalTransactions().add(transaction2);
        
        List<String> result = system.listUnreturnedTapes(customer);
        
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly two tapes", 2, result.size());
        assertTrue("Result should contain T001", result.contains("T001"));
        assertTrue("Result should contain T002", result.contains("T002"));
    }
}