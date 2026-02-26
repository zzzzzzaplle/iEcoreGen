import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private VideoRentalSystem videoRentalSystem;
    
    @Before
    public void setUp() {
        videoRentalSystem = new VideoRentalSystem();
    }
    
    @Test
    public void testCase1_tapeIsAvailable() {
        // Test Case 1: "Tape is available"
        // Input: tape_id="T001", current_date="2025-01-01"
        // Setup: Create Tape with id="T001", No active rentals for T001
        // Expected Output: True
        
        // Create tape T001
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("T001");
        videoRentalSystem.getVideoTapes().add(tape);
        
        // Check availability - should be true since no rentals exist
        boolean result = videoRentalSystem.checkTapeAvailability("T001", "2025-01-01");
        assertTrue("Tape T001 should be available when no rentals exist", result);
    }
    
    @Test
    public void testCase2_tapeIsRentedOut() {
        // Test Case 2: "Tape is rented out"
        // Input: tape_id="T002", current_date="2025-01-01"
        // Setup: Create Tape with id="T002". Create Customer C001.
        //        C001 rents T002 with rental date="2024-12-28", due_date="2025-01-05", return_date=null (unreturned)
        // Expected Output: False
        
        // Create tape T002
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("T002");
        videoRentalSystem.getVideoTapes().add(tape);
        
        // Create customer C001
        Customer customer = new Customer();
        customer.setId("C001");
        videoRentalSystem.getCustomers().add(customer);
        
        // Create rental transaction with unreturned tape
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentalDate("2024-12-28");
        
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate("2024-12-28");
        rental.setDueDate("2025-01-05");
        rental.setReturnDate(null); // Unreturned
        
        transaction.getRentals().add(rental);
        customer.getRentalTransactions().add(transaction);
        videoRentalSystem.getRentalTransactions().add(transaction);
        
        // Check availability - should be false since tape is currently rented
        boolean result = videoRentalSystem.checkTapeAvailability("T002", "2025-01-01");
        assertFalse("Tape T002 should not be available when rented out with null return date", result);
    }
    
    @Test
    public void testCase3_tapeWasRentedButReturned() {
        // Test Case 3: "Tape was rented but returned"
        // Input: tape_id="T003", current_date="2025-01-01"
        // Setup: Create Tape with id="T003". Create Customer C002.
        //        C002 rents T003 with rental date="2024-12-25", due_date="2024-12-30", return_date="2024-12-31"
        // Expected Output: True
        
        // Create tape T003
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("T003");
        videoRentalSystem.getVideoTapes().add(tape);
        
        // Create customer C002
        Customer customer = new Customer();
        customer.setId("C002");
        videoRentalSystem.getCustomers().add(customer);
        
        // Create rental transaction with returned tape
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentalDate("2024-12-25");
        
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate("2024-12-25");
        rental.setDueDate("2024-12-30");
        rental.setReturnDate("2024-12-31"); // Returned before current date
        
        transaction.getRentals().add(rental);
        customer.getRentalTransactions().add(transaction);
        videoRentalSystem.getRentalTransactions().add(transaction);
        
        // Check availability - should be true since tape was returned before current date
        boolean result = videoRentalSystem.checkTapeAvailability("T003", "2025-01-01");
        assertTrue("Tape T003 should be available when returned before current date", result);
    }
    
    @Test
    public void testCase4_tapeExistsButHasOverdueRental() {
        // Test Case 4: "Tape exists but has overdue rental"
        // Input: tape_id="T004", current_date="2025-01-10"
        // Setup: Create Tape with id="T004". Create Customer C003.
        //        C003 rents T004 with rental date="2024-12-28", due_date="2025-01-01", return_date=null (unreturned)
        // Expected Output: False
        
        // Create tape T004
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("T004");
        videoRentalSystem.getVideoTapes().add(tape);
        
        // Create customer C003
        Customer customer = new Customer();
        customer.setId("C003");
        videoRentalSystem.getCustomers().add(customer);
        
        // Create rental transaction with overdue unreturned tape
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentalDate("2024-12-28");
        
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate("2024-12-28");
        rental.setDueDate("2025-01-01");
        rental.setReturnDate(null); // Overdue and unreturned
        
        transaction.getRentals().add(rental);
        customer.getRentalTransactions().add(transaction);
        videoRentalSystem.getRentalTransactions().add(transaction);
        
        // Check availability - should be false since tape is overdue and unreturned
        boolean result = videoRentalSystem.checkTapeAvailability("T004", "2025-01-10");
        assertFalse("Tape T004 should not be available when overdue and unreturned", result);
    }
    
    @Test
    public void testCase5_tapeWasRentedButReturnedByMultipleCustomers() {
        // Test Case 5: "Tape was rented but returned by multiple customers"
        // Input: tape_id="T005", current_date="2025-01-10"
        // Setup: 
        // 1. Create Tape with id="T005". Create Customer C004, C005.
        // 2. C004 rents T005 with rental date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01" → first rental
        // 3. C005 rents T005 with rental date="2025-01-06", due_date="2025-01-15", return_date=null → second rental
        // Expected Output: The first creation: True. The second creation: False.
        
        // Create tape T005
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("T005");
        videoRentalSystem.getVideoTapes().add(tape);
        
        // Create customer C004
        Customer customer1 = new Customer();
        customer1.setId("C004");
        videoRentalSystem.getCustomers().add(customer1);
        
        // Create customer C005
        Customer customer2 = new Customer();
        customer2.setId("C005");
        videoRentalSystem.getCustomers().add(customer2);
        
        // First rental: C004 rents and returns T005
        RentalTransaction transaction1 = new RentalTransaction();
        transaction1.setCustomer(customer1);
        transaction1.setRentalDate("2025-01-01");
        
        Rental rental1 = new Rental();
        rental1.setVideoTape(tape);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-01"); // Returned immediately
        
        transaction1.getRentals().add(rental1);
        customer1.getRentalTransactions().add(transaction1);
        videoRentalSystem.getRentalTransactions().add(transaction1);
        
        // Check availability after first rental - should be true since tape was returned
        boolean result1 = videoRentalSystem.checkTapeAvailability("T005", "2025-01-10");
        assertTrue("Tape T005 should be available after first customer returned it", result1);
        
        // Second rental: C005 rents T005 (unreturned)
        RentalTransaction transaction2 = new RentalTransaction();
        transaction2.setCustomer(customer2);
        transaction2.setRentalDate("2025-01-06");
        
        Rental rental2 = new Rental();
        rental2.setVideoTape(tape);
        rental2.setRentalDate("2025-01-06");
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate(null); // Currently rented by C005
        
        transaction2.getRentals().add(rental2);
        customer2.getRentalTransactions().add(transaction2);
        videoRentalSystem.getRentalTransactions().add(transaction2);
        
        // Check availability after second rental - should be false since tape is currently rented
        boolean result2 = videoRentalSystem.checkTapeAvailability("T005", "2025-01-10");
        assertFalse("Tape T005 should not be available when currently rented by second customer", result2);
    }
}