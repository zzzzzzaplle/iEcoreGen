import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private LocalDate currentDate;
    
    @Before
    public void setUp() {
        // Clear static rentals before each test to ensure isolation
        List<VideoRental> allRentals = VideoRental.getAllRentals();
        if (allRentals instanceof ArrayList) {
            ((ArrayList<VideoRental>) allRentals).clear();
        }
        currentDate = LocalDate.of(2024, 1, 15);
    }
    
    @Test
    public void testCase1_SuccessfulRental() {
        // Setup: Create Customer C001 with 5 active rentals
        Customer customer = new Customer("C001");
        List<VideoRental> rentals = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Tape tape = new Tape("TEMP" + i, "Temp Tape " + i);
            VideoRental rental = new VideoRental(currentDate.plusDays(7), null, 0.0, tape);
            rentals.add(rental);
            VideoRental.registerRental(rental);
        }
        customer.setRentals(rentals);
        
        // Setup: Create available Tape T001
        Tape tape = new Tape("T001", "Test Tape 1");
        
        // Execute: Attempt to add rental
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Rental should be successful
        assertTrue("Rental should be successful when customer has <20 rentals, no overdue fees, and tape is available", result);
    }
    
    @Test
    public void testCase2_CustomerHas20RentalsMaxLimit() {
        // Setup: Create Customer C002 with 20 active rentals
        Customer customer = new Customer("C002");
        List<VideoRental> rentals = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Tape tape = new Tape("TEMP" + i, "Temp Tape " + i);
            VideoRental rental = new VideoRental(currentDate.plusDays(7), null, 0.0, tape);
            rentals.add(rental);
            VideoRental.registerRental(rental);
        }
        customer.setRentals(rentals);
        
        // Setup: Create available Tape T002
        Tape tape = new Tape("T002", "Test Tape 2");
        
        // Execute: Attempt to add rental
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Rental should fail due to maximum rental limit
        assertFalse("Rental should fail when customer has 20 active rentals", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup: Create Customer C003 with overdue fees of 10.50
        Customer customer = new Customer("C003");
        List<VideoRental> rentals = new ArrayList<>();
        
        // Create an overdue rental (due date in past, not returned)
        Tape overdueTape = new Tape("OVERDUE", "Overdue Tape");
        VideoRental overdueRental = new VideoRental(currentDate.minusDays(10), null, 0.0, overdueTape);
        rentals.add(overdueRental);
        VideoRental.registerRental(overdueRental);
        
        customer.setRentals(rentals);
        
        // Setup: Create available Tape T003
        Tape tape = new Tape("T003", "Test Tape 3");
        
        // Execute: Attempt to add rental
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Rental should fail due to overdue fees
        assertFalse("Rental should fail when customer has overdue fees", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup: Create Customer C004 with 0 rentals
        Customer customer = new Customer("C004");
        
        // Setup: Create Tape T004 with active rental (by another customer)
        Tape tape = new Tape("T004", "Test Tape 4");
        
        // Create an active rental for this tape by another customer
        VideoRental existingRental = new VideoRental(currentDate.plusDays(7), null, 0.0, tape);
        VideoRental.registerRental(existingRental);
        
        // Execute: Attempt to add rental
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Rental should fail due to tape unavailability
        assertFalse("Rental should fail when tape is already rented out", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup: Create Customer C005 with 20 rentals and overdue fees of 5.00
        Customer customer = new Customer("C005");
        List<VideoRental> rentals = new ArrayList<>();
        
        // Add 20 active rentals
        for (int i = 0; i < 20; i++) {
            Tape tempTape = new Tape("TEMP" + i, "Temp Tape " + i);
            VideoRental rental = new VideoRental(currentDate.plusDays(7), null, 0.0, tempTape);
            rentals.add(rental);
            VideoRental.registerRental(rental);
        }
        
        // Add an overdue rental to create overdue fees
        Tape overdueTape = new Tape("OVERDUE", "Overdue Tape");
        VideoRental overdueRental = new VideoRental(currentDate.minusDays(10), null, 0.0, overdueTape);
        rentals.add(overdueRental);
        VideoRental.registerRental(overdueRental);
        
        customer.setRentals(rentals);
        
        // Setup: Create Tape T005 with active rental
        Tape tape = new Tape("T005", "Test Tape 5");
        
        // Create an active rental for this tape by another customer
        VideoRental existingRental = new VideoRental(currentDate.plusDays(7), null, 0.0, tape);
        VideoRental.registerRental(existingRental);
        
        // Execute: Attempt to add rental
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Rental should fail due to all conditions failing
        assertFalse("Rental should fail when all conditions (max rentals, overdue fees, tape availability) fail", result);
    }
}