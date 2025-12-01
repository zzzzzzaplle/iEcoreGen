import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private List<VideoRental> allRentalsBackup;
    
    @Before
    public void setUp() {
        // Backup and clear the static ALL_RENTALS list before each test
        allRentalsBackup = new ArrayList<>(VideoRental.getAllRentals());
        VideoRental.getAllRentals().clear();
    }
    
    @Test
    public void testCase1_TapeIsAvailable() {
        // Test Case 1: "Tape is available"
        // Input: tape_id="T001", current_date="2025-01-01"
        
        // Setup step 1: Create Tape with id="T001"
        Tape tape = new Tape("T001", "Test Tape 1");
        
        // Setup step 2: No active rentals for T001
        // (No rentals created, so tape should be available)
        
        // Execute the test
        LocalDate currentDate = LocalDate.of(2025, 1, 1);
        boolean result = tape.isAvailable(currentDate);
        
        // Verify expected output: True
        assertTrue("Tape T001 should be available when no active rentals exist", result);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() {
        // Test Case 2: "Tape is rented out"
        // Input: tape_id="T002", current_date="2025-01-01"
        
        // Setup step 1: Create Tape with id="T002"
        Tape tape = new Tape("T002", "Test Tape 2");
        
        // Setup step 2: Create active VideoRental for T002 with due_date="2025-01-05" (unreturned) by a customer C001
        Customer customer = new Customer("C001");
        VideoRental rental = new VideoRental(
            LocalDate.of(2025, 1, 5),  // due_date
            null,                       // return_date (null = unreturned)
            0.0,                        // ownedPastDueAmount
            tape                        // the tape
        );
        VideoRental.registerRental(rental);
        
        // Execute the test
        LocalDate currentDate = LocalDate.of(2025, 1, 1);
        boolean result = tape.isAvailable(currentDate);
        
        // Verify expected output: False
        assertFalse("Tape T002 should not be available when it has an active rental", result);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Test Case 3: "Tape was rented but returned"
        // Input: tape_id="T003", current_date="2025-01-01"
        
        // Setup step 1: Create Tape with id="T003"
        Tape tape = new Tape("T003", "Test Tape 3");
        
        // Setup step 2: Create VideoRental for T003 with return_date="2024-12-31" by a customer C002
        Customer customer = new Customer("C002");
        VideoRental rental = new VideoRental(
            LocalDate.of(2024, 12, 25), // due_date
            LocalDate.of(2024, 12, 31), // return_date (returned)
            0.0,                        // ownedPastDueAmount
            tape                        // the tape
        );
        VideoRental.registerRental(rental);
        
        // Execute the test
        LocalDate currentDate = LocalDate.of(2025, 1, 1);
        boolean result = tape.isAvailable(currentDate);
        
        // Verify expected output: True
        assertTrue("Tape T003 should be available when previous rental was returned", result);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Test Case 4: "Tape exists but has overdue rental"
        // Input: tape_id="T004", current_date="2025-01-10"
        
        // Setup step 1: Create Tape with id="T004"
        Tape tape = new Tape("T004", "Test Tape 4");
        
        // Setup step 2: Create VideoRental for T004 with due_date="2025-01-01" (unreturned) by customer C003
        Customer customer = new Customer("C003");
        VideoRental rental = new VideoRental(
            LocalDate.of(2025, 1, 1),   // due_date (overdue)
            null,                       // return_date (null = unreturned)
            0.0,                        // ownedPastDueAmount
            tape                        // the tape
        );
        VideoRental.registerRental(rental);
        
        // Execute the test
        LocalDate currentDate = LocalDate.of(2025, 1, 10);
        boolean result = tape.isAvailable(currentDate);
        
        // Verify expected output: False
        assertFalse("Tape T004 should not be available when it has an overdue unreturned rental", result);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Test Case 5: "Tape was rented but returned by multiple customers"
        // Input: tape_id="T005", current_date="2025-01-10"
        
        // Setup step 1: Create Tape with id="T005"
        Tape tape = new Tape("T005", "Test Tape 5");
        
        // Setup step 2: The first creation: Create VideoRental for T005 with due_date="2025-01-05" and return date = "2025-01-01" by customer C004
        Customer customer1 = new Customer("C004");
        VideoRental rental1 = new VideoRental(
            LocalDate.of(2025, 1, 5),   // due_date
            LocalDate.of(2025, 1, 1),   // return_date (returned early)
            0.0,                        // ownedPastDueAmount
            tape                        // the tape
        );
        VideoRental.registerRental(rental1);
        
        // Check availability after first rental (should be True)
        LocalDate currentDate = LocalDate.of(2025, 1, 10);
        boolean resultAfterFirst = tape.isAvailable(currentDate);
        assertTrue("Tape T005 should be available after first rental was returned", resultAfterFirst);
        
        // Setup step 3: The second creation: Create VideoRental for T005 with due_date="2025-01-15" by customer C005 (unreturned)
        Customer customer2 = new Customer("C005");
        VideoRental rental2 = new VideoRental(
            LocalDate.of(2025, 1, 15),  // due_date
            null,                       // return_date (null = unreturned)
            0.0,                        // ownedPastDueAmount
            tape                        // the tape
        );
        VideoRental.registerRental(rental2);
        
        // Check availability after second rental (should be False)
        boolean resultAfterSecond = tape.isAvailable(currentDate);
        assertFalse("Tape T005 should not be available when it has an active unreturned rental", resultAfterSecond);
    }
}