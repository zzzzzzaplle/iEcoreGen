import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CR5Test {
    
    private Customer customer;
    private Tape tape1;
    private Tape tape2;
    
    @Before
    public void setUp() {
        // Clear the static rental registry before each test
        // This is necessary because Tape.RENTAL_REGISTRY is static and shared across tests
        // We need to reset it to ensure test isolation
        // Note: This uses reflection to access the private static field
        try {
            java.lang.reflect.Field field = Tape.class.getDeclaredField("RENTAL_REGISTRY");
            field.setAccessible(true);
            java.util.List<VideoRental> registry = (java.util.List<VideoRental>) field.get(null);
            registry.clear();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clear rental registry", e);
        }
    }
    
    @Test
    public void testCase1_NoRentalsExist() {
        // Setup: Create Customer C001 with empty rentals list
        customer = new Customer("C001");
        
        // Execute: Get unreturned tapes
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Verify: No active rentals expected
        assertTrue("Expected empty list when no rentals exist", unreturnedTapes.isEmpty());
    }
    
    @Test
    public void testCase2_AllTapesReturned() {
        // Setup: Create Customer C002 with returned rental
        customer = new Customer("C002");
        tape1 = new Tape("T001", "Test Video 1");
        
        // Create a rental that has been returned
        LocalDate dueDate = LocalDate.of(2025, 1, 1);
        LocalDate returnDate = LocalDate.of(2025, 1, 1);
        VideoRental rental = new VideoRental(dueDate, returnDate, tape1);
        customer.getRentals().add(rental);
        
        // Execute: Get unreturned tapes
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Verify: No unreturned rentals expected
        assertTrue("Expected empty list when all tapes are returned", unreturnedTapes.isEmpty());
    }
    
    @Test
    public void testCase3_OneUnreturnedTape() {
        // Setup: Create Customer C003 with one unreturned rental
        customer = new Customer("C003");
        tape1 = new Tape("T001", "Test Video 1");
        
        // Create a rental that has not been returned
        LocalDate dueDate = LocalDate.now().plusDays(7);
        VideoRental rental = new VideoRental(dueDate, null, tape1);
        customer.getRentals().add(rental);
        
        // Execute: Get unreturned tapes
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Verify: List containing T001 expected
        assertEquals("Expected exactly one unreturned tape", 1, unreturnedTapes.size());
        assertEquals("Expected tape T001 in the list", "T001", unreturnedTapes.get(0).getId());
    }
    
    @Test
    public void testCase4_MixedReturnedUnreturned() {
        // Setup: Create Customer C004 with mixed returned and unreturned rentals
        customer = new Customer("C004");
        tape1 = new Tape("T001", "Test Video 1");
        tape2 = new Tape("T002", "Test Video 2");
        
        // Create first rental (returned)
        LocalDate dueDate1 = LocalDate.of(2025, 1, 1);
        LocalDate returnDate1 = LocalDate.of(2025, 1, 1);
        VideoRental rental1 = new VideoRental(dueDate1, returnDate1, tape1);
        
        // Create second rental (unreturned)
        LocalDate dueDate2 = LocalDate.now().plusDays(7);
        VideoRental rental2 = new VideoRental(dueDate2, null, tape2);
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute: Get unreturned tapes
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Verify: List containing only T002 expected
        assertEquals("Expected exactly one unreturned tape", 1, unreturnedTapes.size());
        assertEquals("Expected tape T002 in the list", "T002", unreturnedTapes.get(0).getId());
    }
    
    @Test
    public void testCase5_MultipleUnreturnedTapes() {
        // Setup: Create Customer C005 with multiple unreturned rentals
        customer = new Customer("C005");
        tape1 = new Tape("T001", "Test Video 1");
        tape2 = new Tape("T002", "Test Video 2");
        
        // Create first rental (unreturned)
        LocalDate dueDate1 = LocalDate.now().plusDays(7);
        VideoRental rental1 = new VideoRental(dueDate1, null, tape1);
        
        // Create second rental (unreturned)
        LocalDate dueDate2 = LocalDate.now().plusDays(7);
        VideoRental rental2 = new VideoRental(dueDate2, null, tape2);
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute: Get unreturned tapes
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Verify: List containing both T001 and T002 expected
        assertEquals("Expected exactly two unreturned tapes", 2, unreturnedTapes.size());
        
        // Check that both tapes are in the list (order doesn't matter)
        boolean foundT001 = false;
        boolean foundT002 = false;
        for (Tape tape : unreturnedTapes) {
            if ("T001".equals(tape.getId())) {
                foundT001 = true;
            }
            if ("T002".equals(tape.getId())) {
                foundT002 = true;
            }
        }
        assertTrue("Expected to find tape T001 in the list", foundT001);
        assertTrue("Expected to find tape T002 in the list", foundT002);
    }
}