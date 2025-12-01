import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Before
    public void setUp() {
        // Clear the static ALL_RENTALS list between tests to ensure isolation
        // This uses reflection to access and clear the private static field
        try {
            java.lang.reflect.Field field = Tape.class.getDeclaredField("ALL_RENTALS");
            field.setAccessible(true);
            List<?> allRentals = (List<?>) field.get(null);
            allRentals.clear();
        } catch (Exception e) {
            fail("Failed to clear ALL_RENTALS: " + e.getMessage());
        }
    }
    
    @Test
    public void testCase1_tapeIsAvailable() {
        // Setup
        Tape tape = new Tape("T001", "Test Video 1");
        LocalDate currentDate = LocalDate.parse("2025-01-01");
        
        // Execute
        boolean result = tape.isAvailable(currentDate);
        
        // Verify
        assertTrue("Tape T001 should be available when no rentals exist", result);
    }
    
    @Test
    public void testCase2_tapeIsRentedOut() {
        // Setup
        Tape tape = new Tape("T002", "Test Video 2");
        Customer customer = new Customer("C001");
        LocalDate currentDate = LocalDate.parse("2025-01-01");
        
        // Create an active rental for T002
        VideoRental rental = new VideoRental(
            LocalDate.parse("2024-12-28"), 
            LocalDate.parse("2025-01-05"), 
            tape
        );
        // rental has return_date = null (unreturned)
        Tape.registerRental(rental);
        
        // Execute
        boolean result = tape.isAvailable(currentDate);
        
        // Verify
        assertFalse("Tape T002 should not be available when actively rented out", result);
    }
    
    @Test
    public void testCase3_tapeWasRentedButReturned() {
        // Setup
        Tape tape = new Tape("T003", "Test Video 3");
        Customer customer = new Customer("C002");
        LocalDate currentDate = LocalDate.parse("2025-01-01");
        
        // Create a rental that has been returned
        VideoRental rental = new VideoRental(
            LocalDate.parse("2024-12-25"), 
            LocalDate.parse("2024-12-30"), 
            tape
        );
        rental.setReturnDate(LocalDate.parse("2024-12-31"));
        Tape.registerRental(rental);
        
        // Execute
        boolean result = tape.isAvailable(currentDate);
        
        // Verify
        assertTrue("Tape T003 should be available when previously rented but returned", result);
    }
    
    @Test
    public void testCase4_tapeExistsButHasOverdueRental() {
        // Setup
        Tape tape = new Tape("T004", "Test Video 4");
        Customer customer = new Customer("C003");
        LocalDate currentDate = LocalDate.parse("2025-01-10");
        
        // Create an overdue rental (due date has passed, not returned)
        VideoRental rental = new VideoRental(
            LocalDate.parse("2024-12-28"), 
            LocalDate.parse("2025-01-01"), 
            tape
        );
        // rental has return_date = null (unreturned) and is overdue
        Tape.registerRental(rental);
        
        // Execute
        boolean result = tape.isAvailable(currentDate);
        
        // Verify
        assertFalse("Tape T004 should not be available when it has an overdue rental", result);
    }
    
    @Test
    public void testCase5_tapeWasRentedButReturnedByMultipleCustomers() {
        // Setup
        Tape tape = new Tape("T005", "Test Video 5");
        Customer customer1 = new Customer("C004");
        Customer customer2 = new Customer("C005");
        LocalDate currentDate = LocalDate.parse("2025-01-10");
        
        // First rental: returned on same day
        VideoRental rental1 = new VideoRental(
            LocalDate.parse("2025-01-01"), 
            LocalDate.parse("2025-01-05"), 
            tape
        );
        rental1.setReturnDate(LocalDate.parse("2025-01-01"));
        Tape.registerRental(rental1);
        
        // Verify tape is available after first rental is returned
        boolean resultAfterFirstRental = tape.isAvailable(currentDate);
        assertTrue("Tape T005 should be available after first rental is returned", resultAfterFirstRental);
        
        // Second rental: not returned
        VideoRental rental2 = new VideoRental(
            LocalDate.parse("2025-01-06"), 
            LocalDate.parse("2025-01-15"), 
            tape
        );
        // rental2 has return_date = null (unreturned)
        Tape.registerRental(rental2);
        
        // Execute
        boolean resultAfterSecondRental = tape.isAvailable(currentDate);
        
        // Verify
        assertFalse("Tape T005 should not be available when second rental is active", resultAfterSecondRental);
    }
}