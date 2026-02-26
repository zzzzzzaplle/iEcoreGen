import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR3Test {
    
    private RentalService rentalService;
    private LocalDate currentDate;
    
    @Before
    public void setUp() {
        rentalService = new RentalService();
        currentDate = LocalDate.of(2025, 1, 1);
    }
    
    @Test
    public void testCase1_SuccessfulRental() {
        // Setup: Create Customer C001 with 5 active rentals
        Customer customer = new Customer("C001", "Customer One");
        rentalService.registerCustomer(customer);
        
        // Create 5 active rentals for C001
        for (int i = 1; i <= 5; i++) {
            VideoTape tape = new VideoTape("T00" + i, "Movie " + i);
            rentalService.addVideoTape(tape);
            
            LocalDate rentalDate = LocalDate.of(2025, 1, i);
            LocalDate dueDate = rentalDate.plusDays(7);
            
            // Manually create rental transaction since we can't use addVideoTapeRental for setup
            Rental rental = new Rental(tape, customer, rentalDate, dueDate);
            RentalTransaction tx = new RentalTransaction(
                "TX00" + i, customer, rentalDate);
            tx.getRentals().add(rental);
            customer.getTransactions().add(tx);
            rentalService.getTransactions().put(tx.getTransactionId(), tx);
        }
        
        // Create available Tape T001 for rental
        VideoTape tapeT001 = new VideoTape("T001", "Available Movie");
        rentalService.addVideoTape(tapeT001);
        
        // Test: C001 rents tape T001
        boolean result = rentalService.addVideoTapeRental("C001", "T001", currentDate, currentDate.plusDays(7));
        
        // Verify: Should return true (all conditions pass)
        assertTrue("Rental should be successful when customer has <20 rentals and no overdue fees", result);
    }
    
    @Test
    public void testCase2_CustomerHas20RentalsMaxLimit() {
        // Setup: Create Customer C002 with 20 active rentals
        Customer customer = new Customer("C002", "Customer Two");
        rentalService.registerCustomer(customer);
        
        // Create 20 active rentals for C002
        for (int i = 1; i <= 20; i++) {
            VideoTape tape = new VideoTape("T1" + String.format("%02d", i), "Movie " + i);
            rentalService.addVideoTape(tape);
            
            LocalDate rentalDate = LocalDate.of(2025, 1, 1);
            LocalDate dueDate = rentalDate.plusDays(7);
            
            Rental rental = new Rental(tape, customer, rentalDate, dueDate);
            RentalTransaction tx = new RentalTransaction(
                "TX1" + String.format("%02d", i), customer, rentalDate);
            tx.getRentals().add(rental);
            customer.getTransactions().add(tx);
            rentalService.getTransactions().put(tx.getTransactionId(), tx);
        }
        
        // Create available Tape T002
        VideoTape tapeT002 = new VideoTape("T002", "Available Movie");
        rentalService.addVideoTape(tapeT002);
        
        // Test: C002 tries to rent tape T002
        boolean result = rentalService.addVideoTapeRental("C002", "T002", currentDate, currentDate.plusDays(7));
        
        // Verify: Should return false (customer has 20 active rentals)
        assertFalse("Rental should fail when customer has 20 active rentals", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup: Create Customer C003 with 1 overdue rental
        Customer customer = new Customer("C003", "Customer Three");
        rentalService.registerCustomer(customer);
        
        // Create one overdue rental for C003 (due date is same as current date, but not returned)
        VideoTape overdueTape = new VideoTape("T003_OVERDUE", "Overdue Movie");
        rentalService.addVideoTape(overdueTape);
        
        LocalDate rentalDate = LocalDate.of(2025, 1, 1);
        LocalDate dueDate = LocalDate.of(2025, 1, 5); // Due date is today, but not returned = overdue
        
        Rental rental = new Rental(overdueTape, customer, rentalDate, dueDate);
        RentalTransaction tx = new RentalTransaction("TX_OVERDUE", customer, rentalDate);
        tx.getRentals().add(rental);
        customer.getTransactions().add(tx);
        rentalService.getTransactions().put(tx.getTransactionId(), tx);
        
        // Create available Tape T003 for rental attempt
        VideoTape tapeT003 = new VideoTape("T003", "Available Movie");
        rentalService.addVideoTape(tapeT003);
        
        // Test: C003 tries to rent tape T003 with current_date="2025-01-05"
        boolean result = rentalService.addVideoTapeRental("C003", "T003", 
            LocalDate.of(2025, 1, 5), LocalDate.of(2025, 1, 12));
        
        // Verify: Should return false (customer has overdue fees)
        assertFalse("Rental should fail when customer has overdue fees", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup: Create Customer C004 with 0 rentals
        Customer customer = new Customer("C004", "Customer Four");
        rentalService.registerCustomer(customer);
        
        // Create Tape T004 with active rental by another customer C010
        VideoTape tapeT004 = new VideoTape("T004", "Unavailable Movie");
        rentalService.addVideoTape(tapeT004);
        
        Customer otherCustomer = new Customer("C010", "Other Customer");
        rentalService.registerCustomer(otherCustomer);
        
        LocalDate rentalDate = LocalDate.of(2025, 1, 1);
        LocalDate dueDate = LocalDate.of(2025, 1, 5);
        
        Rental rental = new Rental(tapeT004, otherCustomer, rentalDate, dueDate);
        RentalTransaction tx = new RentalTransaction("TX_UNAVAILABLE", otherCustomer, rentalDate);
        tx.getRentals().add(rental);
        otherCustomer.getTransactions().add(tx);
        rentalService.getTransactions().put(tx.getTransactionId(), tx);
        
        // Test: C004 tries to rent tape T004
        boolean result = rentalService.addVideoTapeRental("C004", "T004", currentDate, currentDate.plusDays(7));
        
        // Verify: Should return false (tape is unavailable)
        assertFalse("Rental should fail when tape is unavailable", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup: Create Customer C005 with 20 active rentals and one overdue rental
        Customer customer = new Customer("C005", "Customer Five");
        rentalService.registerCustomer(customer);
        
        // Create 20 active rentals for C005
        for (int i = 1; i <= 20; i++) {
            VideoTape tape = new VideoTape("T2" + String.format("%02d", i), "Movie " + i);
            rentalService.addVideoTape(tape);
            
            LocalDate rentalDate = LocalDate.of(2025, 1, 1);
            LocalDate dueDate = rentalDate.plusDays(7);
            
            Rental rental = new Rental(tape, customer, rentalDate, dueDate);
            RentalTransaction tx = new RentalTransaction(
                "TX2" + String.format("%02d", i), customer, rentalDate);
            tx.getRentals().add(rental);
            customer.getTransactions().add(tx);
            rentalService.getTransactions().put(tx.getTransactionId(), tx);
        }
        
        // Add one overdue rental (due date before current date, not returned)
        VideoTape overdueTape = new VideoTape("T_OVERDUE", "Overdue Movie");
        rentalService.addVideoTape(overdueTape);
        
        LocalDate overdueRentalDate = LocalDate.of(2024, 12, 25);
        LocalDate overdueDueDate = LocalDate.of(2024, 12, 31); // Overdue by 1 day on 2025-01-01
        
        Rental overdueRental = new Rental(overdueTape, customer, overdueRentalDate, overdueDueDate);
        RentalTransaction overdueTx = new RentalTransaction("TX_OVERDUE_ALL", customer, overdueRentalDate);
        overdueTx.getRentals().add(overdueRental);
        customer.getTransactions().add(overdueTx);
        rentalService.getTransactions().put(overdueTx.getTransactionId(), overdueTx);
        
        // Create Tape T005 with active rental by another customer C011
        VideoTape tapeT005 = new VideoTape("T005", "Unavailable Movie");
        rentalService.addVideoTape(tapeT005);
        
        Customer otherCustomer = new Customer("C011", "Other Customer Two");
        rentalService.registerCustomer(otherCustomer);
        
        LocalDate otherRentalDate = LocalDate.of(2025, 1, 1);
        LocalDate otherDueDate = LocalDate.of(2025, 1, 5);
        
        Rental otherRental = new Rental(tapeT005, otherCustomer, otherRentalDate, otherDueDate);
        RentalTransaction otherTx = new RentalTransaction("TX_OTHER", otherCustomer, otherRentalDate);
        otherTx.getRentals().add(otherRental);
        otherCustomer.getTransactions().add(otherTx);
        rentalService.getTransactions().put(otherTx.getTransactionId(), otherTx);
        
        // Test: C005 tries to rent tape T005
        boolean result = rentalService.addVideoTapeRental("C005", "T005", currentDate, currentDate.plusDays(7));
        
        // Verify: Should return false (all conditions fail: 20 rentals + overdue fees + tape unavailable)
        assertFalse("Rental should fail when all conditions fail", result);
    }
}