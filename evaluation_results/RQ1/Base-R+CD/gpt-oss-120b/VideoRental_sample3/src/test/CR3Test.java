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
        // Clear the rental repository before each test
        // Access the private field via reflection or use a reset method if available
        // For this test, we'll create fresh objects for each test
        currentDate = LocalDate.of(2024, 1, 15);
    }
    
    @Test
    public void testCase1_SuccessfulRental() {
        // Setup: Create Customer C001 with 5 active rentals
        customer = new Customer();
        customer.setId("C001");
        
        // Create 5 active rentals for the customer
        for (int i = 0; i < 5; i++) {
            VideoRental rental = new VideoRental();
            rental.setDueDate(currentDate.plusDays(7));
            rental.setReturnDate(null); // Active rental
            customer.getRentals().add(rental);
        }
        
        // Setup: Create available Tape T001
        tape = new Tape();
        tape.setId("T001");
        tape.setVideoInformation("Test Movie");
        
        // Execute the rental operation
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify the result is true (successful rental)
        assertTrue("Rental should be successful when customer has <20 rentals, no overdue fees, and tape is available", result);
    }
    
    @Test
    public void testCase2_CustomerHas20Rentals() {
        // Setup: Create Customer C002 with 20 active rentals
        customer = new Customer();
        customer.setId("C002");
        
        // Create 20 active rentals for the customer
        for (int i = 0; i < 20; i++) {
            VideoRental rental = new VideoRental();
            rental.setDueDate(currentDate.plusDays(7));
            rental.setReturnDate(null); // Active rental
            customer.getRentals().add(rental);
        }
        
        // Setup: Create available Tape T002
        tape = new Tape();
        tape.setId("T002");
        tape.setVideoInformation("Test Movie 2");
        
        // Execute the rental operation
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify the result is false (customer has maximum rentals)
        assertFalse("Rental should fail when customer has 20 active rentals", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup: Create Customer C003 with overdue fees
        customer = new Customer();
        customer.setId("C003");
        
        // Create a rental with overdue fees
        VideoRental overdueRental = new VideoRental();
        overdueRental.setDueDate(currentDate.minusDays(10)); // Due 10 days ago
        overdueRental.setReturnDate(null); // Not returned yet
        // The overdue amount will be calculated as 10 * 0.50 = 5.00
        customer.getRentals().add(overdueRental);
        
        // Setup: Create available Tape T003
        tape = new Tape();
        tape.setId("T003");
        tape.setVideoInformation("Test Movie 3");
        
        // Execute the rental operation
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify the result is false (customer has overdue fees)
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
        
        // Create an active rental for this tape in the repository
        VideoRental existingRental = new VideoRental();
        existingRental.setTape(tape);
        existingRental.setDueDate(currentDate.plusDays(7));
        existingRental.setReturnDate(null); // Active rental
        RentalRepository.addRental(existingRental);
        
        // Execute the rental operation
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify the result is false (tape is unavailable)
        assertFalse("Rental should fail when tape is already rented", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup: Create Customer C005 with 20 rentals and overdue fees
        customer = new Customer();
        customer.setId("C005");
        
        // Create 20 active rentals for the customer, one of which is overdue
        for (int i = 0; i < 19; i++) {
            VideoRental rental = new VideoRental();
            rental.setDueDate(currentDate.plusDays(7));
            rental.setReturnDate(null); // Active rental
            customer.getRentals().add(rental);
        }
        
        // Add one overdue rental
        VideoRental overdueRental = new VideoRental();
        overdueRental.setDueDate(currentDate.minusDays(10)); // Due 10 days ago
        overdueRental.setReturnDate(null); // Not returned yet
        // The overdue amount will be calculated as 10 * 0.50 = 5.00
        customer.getRentals().add(overdueRental);
        
        // Setup: Create Tape T005 with active rental
        tape = new Tape();
        tape.setId("T005");
        tape.setVideoInformation("Test Movie 5");
        
        // Create an active rental for this tape in the repository
        VideoRental existingRental = new VideoRental();
        existingRental.setTape(tape);
        existingRental.setDueDate(currentDate.plusDays(7));
        existingRental.setReturnDate(null); // Active rental
        RentalRepository.addRental(existingRental);
        
        // Execute the rental operation
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify the result is false (all conditions fail)
        assertFalse("Rental should fail when all conditions (max rentals, overdue fees, tape unavailable) are violated", result);
    }
}