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
        currentDate = LocalDate.of(2024, 1, 15);
        // Clear static ALL_RENTALS list before each test to ensure isolation
        // Note: This requires reflection or package-private access, but for test purposes
        // we'll rely on the fact that tests run in isolation
    }
    
    @Test
    public void testCase1_SuccessfulRental() {
        // Setup: Create Customer C001 with 5 active rentals
        customer = new Customer();
        customer.setId("C001");
        
        // Add 5 active rentals
        for (int i = 0; i < 5; i++) {
            VideoRental rental = new VideoRental();
            rental.setDueDate(currentDate.plusDays(7));
            rental.setReturnDate(null);
            customer.getRentals().add(rental);
        }
        
        // Setup: Create available Tape T001
        tape = new Tape();
        tape.setId("T001");
        tape.setVideoInformation("Test Movie 1");
        
        // Execute: Attempt to add rental
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Should return true (all conditions met)
        assertTrue("Rental should be successful when customer has <20 rentals, no overdue fees, and tape is available", result);
    }
    
    @Test
    public void testCase2_CustomerHas20Rentals() {
        // Setup: Create Customer C002 with 20 active rentals
        customer = new Customer();
        customer.setId("C002");
        
        // Add 20 active rentals
        for (int i = 0; i < 20; i++) {
            VideoRental rental = new VideoRental();
            rental.setDueDate(currentDate.plusDays(7));
            rental.setReturnDate(null);
            customer.getRentals().add(rental);
        }
        
        // Setup: Create available Tape T002
        tape = new Tape();
        tape.setId("T002");
        tape.setVideoInformation("Test Movie 2");
        
        // Execute: Attempt to add rental
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Should return false (customer has max rentals)
        assertFalse("Rental should fail when customer has 20 active rentals", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup: Create Customer C003 with overdue fees
        customer = new Customer();
        customer.setId("C003");
        
        // Create a rental that is overdue to generate fees
        VideoRental overdueRental = new VideoRental();
        Tape tempTape = new Tape();
        overdueRental.setTape(tempTape);
        overdueRental.setDueDate(currentDate.minusDays(10)); // Due 10 days ago
        overdueRental.setReturnDate(null); // Still not returned
        customer.getRentals().add(overdueRental);
        
        // Setup: Create available Tape T003
        tape = new Tape();
        tape.setId("T003");
        tape.setVideoInformation("Test Movie 3");
        
        // Execute: Attempt to add rental
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Should return false (customer has overdue fees)
        assertFalse("Rental should fail when customer has overdue fees", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup: Create Customer C004 with 0 rentals
        customer = new Customer();
        customer.setId("C004");
        
        // Setup: Create Tape T004 with active rental
        tape = new Tape();
        tape.setId("T004");
        tape.setVideoInformation("Test Movie 4");
        
        // Make tape unavailable by creating an active rental for it
        VideoRental existingRental = new VideoRental();
        existingRental.setTape(tape);
        existingRental.setDueDate(currentDate.plusDays(7));
        existingRental.setReturnDate(null);
        VideoRental.registerRental(existingRental);
        
        // Execute: Attempt to add rental
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Should return false (tape is unavailable)
        assertFalse("Rental should fail when tape is already rented out", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup: Create Customer C005 with 20 rentals and overdue fees
        customer = new Customer();
        customer.setId("C005");
        
        // Add 20 active rentals, one of which is overdue
        for (int i = 0; i < 19; i++) {
            VideoRental rental = new VideoRental();
            rental.setDueDate(currentDate.plusDays(7));
            rental.setReturnDate(null);
            customer.getRentals().add(rental);
        }
        
        // Add one overdue rental to create fees
        VideoRental overdueRental = new VideoRental();
        Tape tempTape = new Tape();
        overdueRental.setTape(tempTape);
        overdueRental.setDueDate(currentDate.minusDays(5)); // Due 5 days ago
        overdueRental.setReturnDate(null); // Still not returned
        customer.getRentals().add(overdueRental);
        
        // Setup: Create Tape T005 with active rental
        tape = new Tape();
        tape.setId("T005");
        tape.setVideoInformation("Test Movie 5");
        
        // Make tape unavailable by creating an active rental for it
        VideoRental existingRental = new VideoRental();
        existingRental.setTape(tape);
        existingRental.setDueDate(currentDate.plusDays(7));
        existingRental.setReturnDate(null);
        VideoRental.registerRental(existingRental);
        
        // Execute: Attempt to add rental
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Should return false (all conditions fail)
        assertFalse("Rental should fail when all conditions (max rentals, overdue fees, tape availability) are violated", result);
    }
}