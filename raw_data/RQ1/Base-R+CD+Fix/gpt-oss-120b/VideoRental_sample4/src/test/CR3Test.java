import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Customer customer;
    private Tape tape;
    private LocalDate currentDate;
    
    @Before
    public void setUp() {
        // Clear static rentals list before each test to avoid interference
        // This is a workaround since the ALL_RENTALS list is static and private
        // In a real scenario, we would need a reset method or use reflection
    }
    
    @Test
    public void testCase1_SuccessfulRental() {
        // Setup: Create Customer C001 with 5 active rentals
        customer = new Customer("C001");
        currentDate = LocalDate.parse("2025-01-01");
        
        // Create 5 active rentals for customer
        for (int i = 1; i <= 5; i++) {
            Tape tempTape = new Tape("TEMP" + i, "Temp Video " + i);
            LocalDate rentalDate = LocalDate.parse("2025-01-0" + i);
            LocalDate dueDate = rentalDate.plusDays(7);
            VideoRental rental = new VideoRental(rentalDate, dueDate, tempTape);
            customer.getRentals().add(rental);
            Tape.registerRental(rental);
        }
        
        // Create available Tape T001
        tape = new Tape("T001", "Test Video 1");
        
        // Execute: Attempt to rent tape T001
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Rental should be successful
        assertTrue("Rental should be successful when customer has <20 rentals, no overdue, and tape is available", result);
    }
    
    @Test
    public void testCase2_CustomerHas20RentalsMaxLimit() {
        // Setup: Create Customer C002 with 20 active rentals
        customer = new Customer("C002");
        currentDate = LocalDate.parse("2025-01-01");
        
        // Create 20 active rentals for customer
        for (int i = 1; i <= 20; i++) {
            Tape tempTape = new Tape("TEMP" + i, "Temp Video " + i);
            LocalDate rentalDate = currentDate.minusDays(i);
            LocalDate dueDate = rentalDate.plusDays(7);
            VideoRental rental = new VideoRental(rentalDate, dueDate, tempTape);
            customer.getRentals().add(rental);
            Tape.registerRental(rental);
        }
        
        // Create available Tape T002
        tape = new Tape("T002", "Test Video 2");
        
        // Execute: Attempt to rent tape T002
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Rental should fail due to max rental limit
        assertFalse("Rental should fail when customer has 20 active rentals", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup: Create Customer C003 with 1 overdue rental
        customer = new Customer("C003");
        currentDate = LocalDate.parse("2025-01-05");
        
        // Create 1 overdue rental (due date was 2025-01-02, 3 days overdue)
        Tape overdueTape = new Tape("OVERDUE", "Overdue Video");
        LocalDate rentalDate = LocalDate.parse("2024-12-26");
        LocalDate dueDate = LocalDate.parse("2025-01-02"); // 3 days overdue on 2025-01-05
        VideoRental overdueRental = new VideoRental(rentalDate, dueDate, overdueTape);
        customer.getRentals().add(overdueRental);
        Tape.registerRental(overdueRental);
        
        // Create available Tape T003
        tape = new Tape("T003", "Test Video 3");
        
        // Execute: Attempt to rent tape T003
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Rental should fail due to overdue fees
        assertFalse("Rental should fail when customer has overdue fees", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup: Create Customer C004 with 0 rentals
        customer = new Customer("C004");
        currentDate = LocalDate.parse("2025-01-01");
        
        // Create Tape T004 that is already rented by another customer
        tape = new Tape("T004", "Test Video 4");
        
        // Create another customer who rents the tape
        Customer otherCustomer = new Customer("C010");
        LocalDate rentalDate = currentDate.minusDays(1);
        LocalDate dueDate = LocalDate.parse("2025-01-05");
        VideoRental existingRental = new VideoRental(rentalDate, dueDate, tape);
        otherCustomer.getRentals().add(existingRental);
        Tape.registerRental(existingRental);
        
        // Execute: Attempt to rent the unavailable tape T004
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Rental should fail because tape is unavailable
        assertFalse("Rental should fail when tape is already rented", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup: Create Customer C005 with 20 rentals and overdue fees, and tape is unavailable
        customer = new Customer("C005");
        currentDate = LocalDate.parse("2025-01-01");
        
        // Create 19 active rentals
        for (int i = 1; i <= 19; i++) {
            Tape tempTape = new Tape("TEMP" + i, "Temp Video " + i);
            LocalDate rentalDate = currentDate.minusDays(i);
            LocalDate dueDate = rentalDate.plusDays(7);
            VideoRental rental = new VideoRental(rentalDate, dueDate, tempTape);
            customer.getRentals().add(rental);
            Tape.registerRental(rental);
        }
        
        // Create 1 overdue rental (due date was 2024-12-31, 1 day overdue)
        Tape overdueTape = new Tape("OVERDUE", "Overdue Video");
        LocalDate overdueRentalDate = LocalDate.parse("2024-12-24");
        LocalDate overdueDueDate = LocalDate.parse("2024-12-31"); // 1 day overdue on 2025-01-01
        VideoRental overdueRental = new VideoRental(overdueRentalDate, overdueDueDate, overdueTape);
        customer.getRentals().add(overdueRental);
        Tape.registerRental(overdueRental);
        
        // Create Tape T005 that is already rented by another customer
        tape = new Tape("T005", "Test Video 5");
        Customer otherCustomer = new Customer("C011");
        LocalDate otherRentalDate = currentDate.minusDays(1);
        LocalDate otherDueDate = LocalDate.parse("2025-01-05");
        VideoRental otherRental = new VideoRental(otherRentalDate, otherDueDate, tape);
        otherCustomer.getRentals().add(otherRental);
        Tape.registerRental(otherRental);
        
        // Execute: Attempt to rent tape T005
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Rental should fail due to all conditions failing
        assertFalse("Rental should fail when all conditions (max rentals, overdue fees, tape unavailable) are violated", result);
    }
}