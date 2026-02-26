import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Customer customer;
    private Tape tape;
    
    @Before
    public void setUp() {
        // Clear static rentals list before each test to ensure isolation
        VideoRental.getAllRentals().clear();
    }
    
    @Test
    public void testCase1_SuccessfulRental() {
        // Setup: Create Customer C001 with 5 active rentals
        customer = new Customer();
        customer.setId("C001");
        
        LocalDate currentDate = LocalDate.parse("2025-01-01");
        
        // Create 5 active rentals for customer
        for (int i = 0; i < 5; i++) {
            Tape tempTape = new Tape();
            tempTape.setId("TEMP" + i);
            tempTape.setVideoInformation("Temporary Tape " + i);
            
            VideoRental rental = new VideoRental();
            rental.setTape(tempTape);
            rental.setDueDate(currentDate.plusDays(7)); // Due date 7 days after rental
            customer.getRentals().add(rental);
        }
        
        // Create available Tape T001
        tape = new Tape();
        tape.setId("T001");
        tape.setVideoInformation("Test Tape 1");
        
        // Test: Attempt to rent tape T001
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Should return true since customer has <20 rentals, no past-due, and tape is available
        assertTrue("Rental should be successful", result);
        assertEquals("Customer should now have 6 rentals", 6, customer.getRentals().size());
        assertEquals("Last rental should be for tape T001", "T001", 
                     customer.getRentals().get(5).getTape().getId());
    }
    
    @Test
    public void testCase2_CustomerHasMaxRentals() {
        // Setup: Create Customer C002 with 20 active rentals
        customer = new Customer();
        customer.setId("C002");
        
        LocalDate currentDate = LocalDate.parse("2025-01-01");
        
        // Create 20 active rentals for customer
        for (int i = 0; i < 20; i++) {
            Tape tempTape = new Tape();
            tempTape.setId("TEMP" + i);
            tempTape.setVideoInformation("Temporary Tape " + i);
            
            VideoRental rental = new VideoRental();
            rental.setTape(tempTape);
            rental.setDueDate(currentDate.plusDays(7));
            customer.getRentals().add(rental);
        }
        
        // Create available Tape T002
        tape = new Tape();
        tape.setId("T002");
        tape.setVideoInformation("Test Tape 2");
        
        // Test: Attempt to rent tape T002
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Should return false since customer has maximum 20 rentals
        assertFalse("Rental should fail due to maximum rentals limit", result);
        assertEquals("Customer should still have 20 rentals", 20, customer.getRentals().size());
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup: Create Customer C003 with 1 overdue rental
        customer = new Customer();
        customer.setId("C003");
        
        LocalDate currentDate = LocalDate.parse("2025-01-05");
        
        // Create overdue rental (due date was 3 days ago)
        Tape overdueTape = new Tape();
        overdueTape.setId("OVERDUE");
        overdueTape.setVideoInformation("Overdue Tape");
        
        VideoRental overdueRental = new VideoRental();
        overdueRental.setTape(overdueTape);
        overdueRental.setDueDate(LocalDate.parse("2025-01-02")); // 3 days overdue
        customer.getRentals().add(overdueRental);
        
        // Create available Tape T003
        tape = new Tape();
        tape.setId("T003");
        tape.setVideoInformation("Test Tape 3");
        
        // Test: Attempt to rent tape T003
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Should return false since customer has overdue fees
        assertFalse("Rental should fail due to overdue fees", result);
        assertEquals("Customer should still have only 1 rental", 1, customer.getRentals().size());
        assertTrue("Customer should have past-due amount > 0", 
                  customer.calculateTotalPastDueAmount(currentDate) > 0);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup: Create Customer C004 with 0 rentals
        customer = new Customer();
        customer.setId("C004");
        
        LocalDate currentDate = LocalDate.parse("2025-01-01");
        
        // Create Tape T004 that is currently rented by another customer
        tape = new Tape();
        tape.setId("T004");
        tape.setVideoInformation("Test Tape 4");
        
        // Create another customer C010 who has rented T004
        Customer otherCustomer = new Customer();
        otherCustomer.setId("C010");
        
        VideoRental otherRental = new VideoRental();
        otherRental.setTape(tape);
        otherRental.setDueDate(LocalDate.parse("2025-01-05"));
        otherCustomer.getRentals().add(otherRental);
        
        // Test: Attempt to rent tape T004 (which is currently rented by C010)
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Should return false since tape is unavailable
        assertFalse("Rental should fail due to tape being unavailable", result);
        assertEquals("Customer C004 should have 0 rentals", 0, customer.getRentals().size());
        assertFalse("Tape T004 should not be available", tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup: Create Customer C005 with 20 rentals and overdue fees, and tape is unavailable
        customer = new Customer();
        customer.setId("C005");
        
        LocalDate currentDate = LocalDate.parse("2025-01-01");
        
        // Create 19 active rentals
        for (int i = 0; i < 19; i++) {
            Tape tempTape = new Tape();
            tempTape.setId("TEMP" + i);
            tempTape.setVideoInformation("Temporary Tape " + i);
            
            VideoRental rental = new VideoRental();
            rental.setTape(tempTape);
            rental.setDueDate(currentDate.plusDays(7));
            customer.getRentals().add(rental);
        }
        
        // Create 1 overdue rental
        Tape overdueTape = new Tape();
        overdueTape.setId("OVERDUE");
        overdueTape.setVideoInformation("Overdue Tape");
        
        VideoRental overdueRental = new VideoRental();
        overdueRental.setTape(overdueTape);
        overdueRental.setDueDate(LocalDate.parse("2024-12-31")); // 1 day overdue
        customer.getRentals().add(overdueRental);
        
        // Create Tape T005 that is currently rented by another customer
        tape = new Tape();
        tape.setId("T005");
        tape.setVideoInformation("Test Tape 5");
        
        // Create another customer C011 who has rented T005
        Customer otherCustomer = new Customer();
        otherCustomer.setId("C011");
        
        VideoRental otherRental = new VideoRental();
        otherRental.setTape(tape);
        otherRental.setDueDate(LocalDate.parse("2025-01-05"));
        otherCustomer.getRentals().add(otherRental);
        
        // Test: Attempt to rent tape T005
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Should return false since all conditions fail
        assertFalse("Rental should fail due to all conditions failing", result);
        assertEquals("Customer should still have 20 rentals", 20, customer.getRentals().size());
        assertTrue("Customer should have past-due amount > 0", 
                  customer.calculateTotalPastDueAmount(currentDate) > 0);
        assertFalse("Tape T005 should not be available", tape.isAvailable(currentDate));
    }
}