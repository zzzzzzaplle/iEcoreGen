import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;

public class CR2Test {
    
    private Tape tape;
    
    @Before
    public void setUp() {
        // Clear all rentals before each test to ensure isolation
        List<VideoRental> allRentals = VideoRental.getAllRentals();
        // Since ALL_RENTALS is private and unmodifiable, we need to clear it through the class
        // However, the provided code doesn't have a clear method, so we'll work around this
        // by using reflection or by ensuring each test creates fresh objects
        // For this test, we'll rely on fresh object creation in each test method
    }
    
    @After
    public void tearDown() {
        // Clear the static ALL_RENTALS list between tests
        // Since there's no public clear method, we'll use reflection
        try {
            java.lang.reflect.Field field = VideoRental.class.getDeclaredField("ALL_RENTALS");
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            List<VideoRental> allRentals = (List<VideoRental>) field.get(null);
            allRentals.clear();
        } catch (Exception e) {
            // If reflection fails, tests might interfere with each other
            System.err.println("Warning: Could not clear ALL_RENTALS between tests");
        }
    }
    
    @Test
    public void testCase1_TapeIsAvailable() {
        // Test Case 1: "Tape is available"
        // Input: tape_id="T001", current_date="2025-01-01"
        
        // Setup:
        // 1. Create Tape with id="T001"
        tape = new Tape();
        tape.setId("T001");
        
        // 2. No active rentals for T001 (ensured by setUp/tearDown)
        
        // Execute test
        LocalDate currentDate = LocalDate.of(2025, 1, 1);
        boolean result = tape.isAvailable(currentDate);
        
        // Verify expected output
        assertTrue("Tape T001 should be available when no active rentals exist", result);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() {
        // Test Case 2: "Tape is rented out"
        // Input: tape_id="T002", current_date="2025-01-01"
        
        // Setup:
        // 1. Create Tape with id="T002"
        tape = new Tape();
        tape.setId("T002");
        
        // 2. Create active VideoRental for T002 with due_date="2025-01-05" (unreturned) by a customer C001
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDate.of(2025, 1, 5));
        rental.setReturnDate(null); // unreturned
        
        // Register the rental (simulating what happens in addVedioTapeRental)
        VideoRental.registerRental(rental);
        
        // Execute test
        LocalDate currentDate = LocalDate.of(2025, 1, 1);
        boolean result = tape.isAvailable(currentDate);
        
        // Verify expected output
        assertFalse("Tape T002 should not be available when it has an active rental", result);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Test Case 3: "Tape was rented but returned"
        // Input: tape_id="T003", current_date="2025-01-01"
        
        // Setup:
        // 1. Create Tape with id="T003"
        tape = new Tape();
        tape.setId("T003");
        
        // 2. Create VideoRental for T003 with return_date="2024-12-31" by a customer C002
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDate.of(2024, 12, 25)); // arbitrary due date
        rental.setReturnDate(LocalDate.of(2024, 12, 31)); // returned before current date
        
        // Register the rental
        VideoRental.registerRental(rental);
        
        // Execute test
        LocalDate currentDate = LocalDate.of(2025, 1, 1);
        boolean result = tape.isAvailable(currentDate);
        
        // Verify expected output
        assertTrue("Tape T003 should be available when it was returned before current date", result);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Test Case 4: "Tape exists but has overdue rental"
        // Input: tape_id="T004", current_date="2025-01-10"
        
        // Setup:
        // 1. Create Tape with id="T004"
        tape = new Tape();
        tape.setId("T004");
        
        // 2. Create VideoRental for T004 with due_date="2025-01-01" (unreturned) by customer C003
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDate.of(2025, 1, 1));
        rental.setReturnDate(null); // unreturned and overdue
        
        // Register the rental
        VideoRental.registerRental(rental);
        
        // Execute test
        LocalDate currentDate = LocalDate.of(2025, 1, 10);
        boolean result = tape.isAvailable(currentDate);
        
        // Verify expected output
        assertFalse("Tape T004 should not be available when it has an overdue unreturned rental", result);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Test Case 5: "Tape was rented but returned by multiple customers"
        // Input: tape_id="T005", current_date="2025-01-10"
        
        // Setup:
        // 1. Create Tape with id="T005"
        tape = new Tape();
        tape.setId("T005");
        
        // 2. The first creation: Create VideoRental for T005 with due_date="2025-01-05" and return date = "2025-01-01" by customer C004
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape);
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 1)); // returned before current date
        
        // 3. The second creation: Create VideoRental for T005 with due_date="2025-01-15" by customer C005 (unreturned)
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape);
        rental2.setDueDate(LocalDate.of(2025, 1, 15));
        rental2.setReturnDate(null); // unreturned
        
        // Register both rentals
        VideoRental.registerRental(rental1);
        VideoRental.registerRental(rental2);
        
        // Execute test
        LocalDate currentDate = LocalDate.of(2025, 1, 10);
        boolean result = tape.isAvailable(currentDate);
        
        // Verify expected output
        // The tape should be unavailable because there's an active unreturned rental (rental2)
        assertFalse("Tape T005 should not be available when it has any active unreturned rental", result);
    }
}