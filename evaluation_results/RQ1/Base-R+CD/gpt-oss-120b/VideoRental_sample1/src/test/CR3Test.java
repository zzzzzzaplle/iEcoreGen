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
        // Clear all rentals before each test to ensure isolation
        List<VideoRental> allRentals = VideoRental.getAllRentals();
        // Since ALL_RENTALS is private and unmodifiable, we need to clear it through reflection or reset state
        // For testing purposes, we'll create fresh objects in each test
        currentDate = LocalDate.of(2024, 1, 15);
    }
    
    @Test
    public void testCase1_SuccessfulRental() {
        // Setup: Create Customer C001 with 5 active rentals
        customer = new Customer();
        customer.setId("C001");
        
        // Create 5 active rentals for the customer
        for (int i = 0; i < 5; i++) {
            Tape dummyTape = new Tape();
            dummyTape.setId("D" + i);
            VideoRental rental = new VideoRental(currentDate.plusDays(7), null, 0.0, dummyTape);
            customer.getRentals().add(rental);
            VideoRental.registerRental(rental);
        }
        
        // Create available Tape T001
        tape = new Tape();
        tape.setId("T001");
        
        // Execute: Attempt to add rental
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Should return true as all conditions are met
        assertTrue("Rental should be successful when customer has <20 rentals, no overdue fees, and tape is available", result);
    }
    
    @Test
    public void testCase2_CustomerHas20Rentals() {
        // Setup: Create Customer C002 with 20 active rentals
        customer = new Customer();
        customer.setId("C002");
        
        // Create 20 active rentals for the customer
        for (int i = 0; i < 20; i++) {
            Tape dummyTape = new Tape();
            dummyTape.setId("D" + i);
            VideoRental rental = new VideoRental(currentDate.plusDays(7), null, 0.0, dummyTape);
            customer.getRentals().add(rental);
            VideoRental.registerRental(rental);
        }
        
        // Create available Tape T002
        tape = new Tape();
        tape.setId("T002");
        
        // Execute: Attempt to add rental
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Should return false due to maximum rental limit
        assertFalse("Rental should fail when customer already has 20 rentals", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup: Create Customer C003 with overdue fees
        customer = new Customer();
        customer.setId("C003");
        
        // Create a rental with overdue fees
        Tape overdueTape = new Tape();
        overdueTape.setId("OVERDUE_TAPE");
        // Create a rental that is 3 days overdue (3 * 0.5 = 1.50 overdue fee)
        VideoRental overdueRental = new VideoRental(currentDate.minusDays(10), null, 1.50, overdueTape);
        customer.getRentals().add(overdueRental);
        VideoRental.registerRental(overdueRental);
        
        // Create available Tape T003
        tape = new Tape();
        tape.setId("T003");
        
        // Execute: Attempt to add rental
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Should return false due to overdue fees
        assertFalse("Rental should fail when customer has overdue fees", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup: Create Customer C004 with 0 rentals
        customer = new Customer();
        customer.setId("C004");
        
        // Create Tape T004 with active rental (make it unavailable)
        tape = new Tape();
        tape.setId("T004");
        
        // Create an active rental for this tape with another customer
        Customer otherCustomer = new Customer();
        otherCustomer.setId("OTHER_CUSTOMER");
        VideoRental activeRental = new VideoRental(currentDate.plusDays(7), null, 0.0, tape);
        otherCustomer.getRentals().add(activeRental);
        VideoRental.registerRental(activeRental);
        
        // Execute: Attempt to add rental
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Should return false due to tape being unavailable
        assertFalse("Rental should fail when tape is already rented out", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup: Create Customer C005 with 20 rentals and overdue fees
        customer = new Customer();
        customer.setId("C005");
        
        // Create 20 active rentals with overdue fees for the customer
        for (int i = 0; i < 20; i++) {
            Tape dummyTape = new Tape();
            dummyTape.setId("D" + i);
            // Create overdue rentals (5 days overdue = 2.50 fee each)
            VideoRental rental = new VideoRental(currentDate.minusDays(12), null, 2.50, dummyTape);
            customer.getRentals().add(rental);
            VideoRental.registerRental(rental);
        }
        
        // Create Tape T005 with active rental (make it unavailable)
        tape = new Tape();
        tape.setId("T005");
        
        // Create an active rental for this tape with another customer
        Customer otherCustomer = new Customer();
        otherCustomer.setId("OTHER_CUSTOMER2");
        VideoRental activeRental = new VideoRental(currentDate.plusDays(7), null, 0.0, tape);
        otherCustomer.getRentals().add(activeRental);
        VideoRental.registerRental(activeRental);
        
        // Execute: Attempt to add rental
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Should return false as all conditions fail
        assertFalse("Rental should fail when all conditions (max rentals, overdue fees, tape availability) are violated", result);
    }
}