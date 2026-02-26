import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Tape tapeT001;
    private Tape tapeT002;
    private Tape tapeT003;
    private Tape tapeT004;
    private Tape tapeT005;
    private Customer customerC001;
    private Customer customerC002;
    private Customer customerC003;
    private Customer customerC004;
    private Customer customerC005;
    
    @Before
    public void setUp() {
        // Clear the static rental registry before each test
        // This is necessary because Tape.RENTAL_REGISTRY is static and shared across tests
        List<VideoRental> registry = new ArrayList<>();
        try {
            java.lang.reflect.Field field = Tape.class.getDeclaredField("RENTAL_REGISTRY");
            field.setAccessible(true);
            registry = (List<VideoRental>) field.get(null);
            registry.clear();
        } catch (Exception e) {
            fail("Failed to clear rental registry: " + e.getMessage());
        }
        
        // Initialize tapes
        tapeT001 = new Tape("T001", "Video T001");
        tapeT002 = new Tape("T002", "Video T002");
        tapeT003 = new Tape("T003", "Video T003");
        tapeT004 = new Tape("T004", "Video T004");
        tapeT005 = new Tape("T005", "Video T005");
        
        // Initialize customers
        customerC001 = new Customer("C001");
        customerC002 = new Customer("C002");
        customerC003 = new Customer("C003");
        customerC004 = new Customer("C004");
        customerC005 = new Customer("C005");
    }
    
    @Test
    public void testCase1_TapeIsAvailable() {
        // Test Case 1: "Tape is available"
        // Input: tape_id="T001", current_date="2025-01-01"
        // Setup: Create Tape with id="T001", No active rentals for T001
        // Expected Output: True
        
        LocalDate currentDate = LocalDate.parse("2025-01-01");
        boolean result = tapeT001.isAvailable(currentDate);
        
        assertTrue("Tape T001 should be available when no active rentals exist", result);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() {
        // Test Case 2: "Tape is rented out"
        // Input: tape_id="T002", current_date="2025-01-01"
        // Setup: Create Tape with id="T002", Create active VideoRental for T002 with due_date="2025-01-05" (unreturned)
        // Expected Output: False
        
        LocalDate currentDate = LocalDate.parse("2025-01-01");
        LocalDate dueDate = LocalDate.parse("2025-01-05");
        
        // Create an active rental (returnDate = null)
        VideoRental rental = new VideoRental(dueDate, null, tapeT002);
        customerC001.getRentals().add(rental);
        
        boolean result = tapeT002.isAvailable(currentDate);
        
        assertFalse("Tape T002 should not be available when it has an active rental", result);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Test Case 3: "Tape was rented but returned"
        // Input: tape_id="T003", current_date="2025-01-01"
        // Setup: Create Tape with id="T003", Create VideoRental for T003 with return_date="2024-12-31"
        // Expected Output: True
        
        LocalDate currentDate = LocalDate.parse("2025-01-01");
        LocalDate returnDate = LocalDate.parse("2024-12-31");
        LocalDate dueDate = LocalDate.parse("2024-12-25"); // Some due date before return
        
        // Create a completed rental (returnDate is set)
        VideoRental rental = new VideoRental(dueDate, returnDate, tapeT003);
        customerC002.getRentals().add(rental);
        
        boolean result = tapeT003.isAvailable(currentDate);
        
        assertTrue("Tape T003 should be available when all rentals are returned", result);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Test Case 4: "Tape exists but has overdue rental"
        // Input: tape_id="T004", current_date="2025-01-10"
        // Setup: Create Tape with id="T004", Create VideoRental for T004 with due_date="2025-01-01" (unreturned)
        // Expected Output: False
        
        LocalDate currentDate = LocalDate.parse("2025-01-10");
        LocalDate dueDate = LocalDate.parse("2025-01-01");
        
        // Create an overdue rental (due date is before current date, returnDate = null)
        VideoRental rental = new VideoRental(dueDate, null, tapeT004);
        customerC003.getRentals().add(rental);
        
        boolean result = tapeT004.isAvailable(currentDate);
        
        assertFalse("Tape T004 should not be available when it has an overdue rental", result);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Test Case 5: "Tape was rented but returned by multiple customers"
        // Input: tape_id="T005", current_date="2025-01-10"
        // Setup: 
        // 1. Create Tape with id="T005"
        // 2. First creation: Create VideoRental for T005 with due_date="2025-01-05" and return date = "2025-01-01"
        // 3. Second creation: Create VideoRental for T005 with due_date="2025-01-15" (unreturned)
        // Expected Output: The first creation: True. The second creation: False.
        
        LocalDate currentDate = LocalDate.parse("2025-01-10");
        
        // First rental: returned before current date
        LocalDate firstDueDate = LocalDate.parse("2025-01-05");
        LocalDate firstReturnDate = LocalDate.parse("2025-01-01");
        VideoRental firstRental = new VideoRental(firstDueDate, firstReturnDate, tapeT005);
        customerC004.getRentals().add(firstRental);
        
        // Check availability after first rental (should be true since it's returned)
        boolean resultAfterFirst = tapeT005.isAvailable(currentDate);
        assertTrue("Tape T005 should be available after first rental is returned", resultAfterFirst);
        
        // Second rental: active (unreturned)
        LocalDate secondDueDate = LocalDate.parse("2025-01-15");
        VideoRental secondRental = new VideoRental(secondDueDate, null, tapeT005);
        customerC005.getRentals().add(secondRental);
        
        // Check availability after second rental (should be false since it's currently rented)
        boolean resultAfterSecond = tapeT005.isAvailable(currentDate);
        assertFalse("Tape T005 should not be available when it has an active rental", resultAfterSecond);
    }
}