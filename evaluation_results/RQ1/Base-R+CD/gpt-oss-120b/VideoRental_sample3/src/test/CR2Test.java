import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;

public class CR2Test {
    
    private Tape tapeT001;
    private Tape tapeT002;
    private Tape tapeT003;
    private Tape tapeT004;
    private Tape tapeT005;
    
    @Before
    public void setUp() {
        // Clear the rental repository before each test to ensure isolation
        List<VideoRental> allRentals = RentalRepository.getAllRentals();
        if (allRentals instanceof java.util.ArrayList) {
            ((java.util.ArrayList<VideoRental>) allRentals).clear();
        }
        
        // Create test tapes
        tapeT001 = new Tape();
        tapeT001.setId("T001");
        
        tapeT002 = new Tape();
        tapeT002.setId("T002");
        
        tapeT003 = new Tape();
        tapeT003.setId("T003");
        
        tapeT004 = new Tape();
        tapeT004.setId("T004");
        
        tapeT005 = new Tape();
        tapeT005.setId("T005");
    }
    
    @After
    public void tearDown() {
        // Clean up after each test
        List<VideoRental> allRentals = RentalRepository.getAllRentals();
        if (allRentals instanceof java.util.ArrayList) {
            ((java.util.ArrayList<VideoRental>) allRentals).clear();
        }
    }
    
    @Test
    public void testCase1_TapeIsAvailable() {
        // Test Case 1: "Tape is available"
        // Input: tape_id="T001", current_date="2025-01-01"
        // Setup: Create Tape with id="T001", No active rentals for T001
        // Expected Output: True
        
        LocalDate currentDate = LocalDate.of(2025, 1, 1);
        boolean result = tapeT001.isAvailable(currentDate);
        
        assertTrue("Tape T001 should be available when no active rentals exist", result);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() {
        // Test Case 2: "Tape is rented out"
        // Input: tape_id="T002", current_date="2025-01-01"
        // Setup: Create Tape with id="T002", Create active VideoRental for T002 with due_date="2025-01-05" (unreturned) by a customer C001
        // Expected Output: False
        
        // Create customer C001
        Customer customerC001 = new Customer();
        customerC001.setId("C001");
        
        // Create active rental for T002
        VideoRental rental = new VideoRental();
        rental.setTape(tapeT002);
        rental.setDueDate(LocalDate.of(2025, 1, 5));
        rental.setReturnDate(null); // unreturned
        
        // Add rental to repository through customer
        customerC001.getRentals().add(rental);
        RentalRepository.addRental(rental);
        
        LocalDate currentDate = LocalDate.of(2025, 1, 1);
        boolean result = tapeT002.isAvailable(currentDate);
        
        assertFalse("Tape T002 should not be available when it has an active rental", result);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Test Case 3: "Tape was rented but returned"
        // Input: tape_id="T003", current_date="2025-01-01"
        // Setup: Create Tape with id="T003", Create VideoRental for T003 with return_date="2024-12-31" by a customer C002
        // Expected Output: True
        
        // Create customer C002
        Customer customerC002 = new Customer();
        customerC002.setId("C002");
        
        // Create returned rental for T003
        VideoRental rental = new VideoRental();
        rental.setTape(tapeT003);
        rental.setReturnDate(LocalDate.of(2024, 12, 31)); // returned
        
        // Add rental to repository through customer
        customerC002.getRentals().add(rental);
        RentalRepository.addRental(rental);
        
        LocalDate currentDate = LocalDate.of(2025, 1, 1);
        boolean result = tapeT003.isAvailable(currentDate);
        
        assertTrue("Tape T003 should be available when it was returned before current date", result);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Test Case 4: "Tape exists but has overdue rental"
        // Input: tape_id="T004", current_date="2025-01-10"
        // Setup: Create Tape with id="T004", Create VideoRental for T004 with due_date="2025-01-01" (unreturned) by customer C003
        // Expected Output: False
        
        // Create customer C003
        Customer customerC003 = new Customer();
        customerC003.setId("C003");
        
        // Create overdue rental for T004
        VideoRental rental = new VideoRental();
        rental.setTape(tapeT004);
        rental.setDueDate(LocalDate.of(2025, 1, 1));
        rental.setReturnDate(null); // unreturned (overdue)
        
        // Add rental to repository through customer
        customerC003.getRentals().add(rental);
        RentalRepository.addRental(rental);
        
        LocalDate currentDate = LocalDate.of(2025, 1, 10);
        boolean result = tapeT004.isAvailable(currentDate);
        
        assertFalse("Tape T004 should not be available when it has an overdue rental", result);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Test Case 5: "Tape was rented but returned by multiple customers"
        // Input: tape_id="T005", current_date="2025-01-10"
        // Setup: 
        // 1. Create Tape with id="T005"
        // 2. The first creation: Create VideoRental for T005 with due_date="2025-01-05" and return date = "2025-01-01" by customer C004
        // 3. The second creation: Create VideoRental for T005 with due_date="2025-01-15" by customer C005 (unreturned)
        // Expected Output: The first creation: True. The second creation: False.
        
        // Create customers
        Customer customerC004 = new Customer();
        customerC004.setId("C004");
        
        Customer customerC005 = new Customer();
        customerC005.setId("C005");
        
        // First rental: returned before current date
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tapeT005);
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 1)); // returned
        
        // Second rental: unreturned
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tapeT005);
        rental2.setDueDate(LocalDate.of(2025, 1, 15));
        rental2.setReturnDate(null); // unreturned
        
        // Add rentals to repository through customers
        customerC004.getRentals().add(rental1);
        customerC005.getRentals().add(rental2);
        RentalRepository.addRental(rental1);
        RentalRepository.addRental(rental2);
        
        LocalDate currentDate = LocalDate.of(2025, 1, 10);
        boolean result = tapeT005.isAvailable(currentDate);
        
        // The tape should be unavailable because there's an active unreturned rental
        assertFalse("Tape T005 should not be available when it has at least one active unreturned rental", result);
    }
}