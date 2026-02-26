import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Customer customer;
    private Tape tape;
    private LocalDate currentDate;
    
    @Before
    public void setUp() {
        currentDate = LocalDate.now();
        // Clear the static rental registry before each test
        // This is necessary because Tape.RENTAL_REGISTRY is static and shared across tests
        clearRentalRegistry();
    }
    
    // Helper method to clear the static rental registry
    private void clearRentalRegistry() {
        try {
            java.lang.reflect.Field field = Tape.class.getDeclaredField("RENTAL_REGISTRY");
            field.setAccessible(true);
            List<VideoRental> registry = (List<VideoRental>) field.get(null);
            registry.clear();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clear rental registry", e);
        }
    }
    
    @Test
    public void testCase1_SuccessfulRental() {
        // Setup: Create Customer C001 with 5 active rentals
        customer = new Customer("C001");
        for (int i = 0; i < 5; i++) {
            Tape tempTape = new Tape("TEMP" + i, "Temporary Tape " + i);
            VideoRental rental = new VideoRental(currentDate.plusDays(7), null, tempTape);
            customer.getRentals().add(rental);
        }
        
        // Setup: Create available Tape T001
        tape = new Tape("T001", "Test Tape 1");
        
        // Execute: Attempt to add rental
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Should return true since all conditions are met
        assertTrue("Rental should be successful when customer has <20 rentals, no overdue fees, and tape is available", result);
    }
    
    @Test
    public void testCase2_CustomerHas20Rentals() {
        // Setup: Create Customer C002 with 20 active rentals
        customer = new Customer("C002");
        for (int i = 0; i < 20; i++) {
            Tape tempTape = new Tape("TEMP" + i, "Temporary Tape " + i);
            VideoRental rental = new VideoRental(currentDate.plusDays(7), null, tempTape);
            customer.getRentals().add(rental);
        }
        
        // Setup: Create available Tape T002
        tape = new Tape("T002", "Test Tape 2");
        
        // Execute: Attempt to add rental
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Should return false due to maximum rental limit
        assertFalse("Rental should fail when customer has 20 active rentals", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup: Create Customer C003 with overdue fees
        customer = new Customer("C003");
        
        // Create a tape and rental that is overdue
        Tape overdueTape = new Tape("OVERDUE", "Overdue Tape");
        LocalDate pastDueDate = currentDate.minusDays(10); // Due date was 10 days ago
        VideoRental overdueRental = new VideoRental(pastDueDate, null, overdueTape);
        customer.getRentals().add(overdueRental);
        
        // Setup: Create available Tape T003
        tape = new Tape("T003", "Test Tape 3");
        
        // Execute: Attempt to add rental
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Should return false due to overdue fees
        assertFalse("Rental should fail when customer has overdue fees", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup: Create Customer C004 with 0 rentals
        customer = new Customer("C004");
        
        // Setup: Create Tape T004 with active rental (make it unavailable)
        tape = new Tape("T004", "Test Tape 4");
        
        // Create another customer to rent the tape first
        Customer otherCustomer = new Customer("OTHER");
        VideoRental existingRental = new VideoRental(currentDate.plusDays(7), null, tape);
        otherCustomer.getRentals().add(existingRental);
        
        // Execute: Attempt to add rental
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Should return false due to tape being unavailable
        assertFalse("Rental should fail when tape is already rented out", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup: Create Customer C005 with 20 rentals and overdue fees
        customer = new Customer("C005");
        
        // Add 20 active rentals
        for (int i = 0; i < 20; i++) {
            Tape tempTape = new Tape("TEMP" + i, "Temporary Tape " + i);
            VideoRental rental = new VideoRental(currentDate.plusDays(7), null, tempTape);
            customer.getRentals().add(rental);
        }
        
        // Add an overdue rental to create overdue fees
        Tape overdueTape = new Tape("OVERDUE", "Overdue Tape");
        LocalDate pastDueDate = currentDate.minusDays(5); // Due date was 5 days ago
        VideoRental overdueRental = new VideoRental(pastDueDate, null, overdueTape);
        customer.getRentals().add(overdueRental);
        
        // Setup: Create Tape T005 with active rental (make it unavailable)
        tape = new Tape("T005", "Test Tape 5");
        
        // Create another customer to rent the tape first
        Customer otherCustomer = new Customer("OTHER2");
        VideoRental existingRental = new VideoRental(currentDate.plusDays(7), null, tape);
        otherCustomer.getRentals().add(existingRental);
        
        // Execute: Attempt to add rental
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Should return false since all conditions fail
        assertFalse("Rental should fail when all conditions (max rentals, overdue fees, tape availability) are violated", result);
    }
}