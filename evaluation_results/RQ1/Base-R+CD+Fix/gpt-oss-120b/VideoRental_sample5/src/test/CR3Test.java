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
        // Clear the rental repository before each test to ensure isolation
        // Since ALL_RENTALS is private and static, we need to clear it by removing all elements
        List<VideoRental> allRentals = RentalRepository.getAllRentals();
        // Create a new list to clear the rentals since getAllRentals returns unmodifiable list
        // We'll use reflection or direct access isn't possible, so we'll rely on test isolation
        // For this test, we'll create fresh objects each time
    }
    
    @Test
    public void testCase1_SuccessfulRental() {
        // Setup: Create Customer C001 with 5 active rentals
        customer = new Customer("C001");
        currentDate = LocalDate.of(2025, 1, 1);
        
        // Create 5 active rentals for customer
        for (int i = 1; i <= 5; i++) {
            Tape tempTape = new Tape("TEMP" + i, "Video " + i);
            VideoRental rental = new VideoRental();
            rental.setTape(tempTape);
            rental.setRentalStartDate(currentDate.minusDays(i));
            rental.setDueDate(currentDate.minusDays(i).plusDays(7));
            rental.setReturnDate(null);
            customer.getRentals().add(rental);
            RentalRepository.addRental(rental);
        }
        
        // Create available Tape T001
        tape = new Tape("T001", "Test Video 1");
        
        // Execute: Customer rents tape T001
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Rental should be successful
        assertTrue("Rental should be successful when customer has <20 rentals, no past due, and tape is available", result);
    }
    
    @Test
    public void testCase2_CustomerHas20RentalsMaxLimit() {
        // Setup: Create Customer C002 with 20 active rentals
        customer = new Customer("C002");
        currentDate = LocalDate.of(2025, 1, 1);
        
        // Create 20 active rentals for customer
        for (int i = 1; i <= 20; i++) {
            Tape tempTape = new Tape("TEMP" + i, "Video " + i);
            VideoRental rental = new VideoRental();
            rental.setTape(tempTape);
            rental.setRentalStartDate(currentDate.minusDays(i));
            rental.setDueDate(currentDate.minusDays(i).plusDays(7));
            rental.setReturnDate(null);
            customer.getRentals().add(rental);
            RentalRepository.addRental(rental);
        }
        
        // Create available Tape T002
        tape = new Tape("T002", "Test Video 2");
        
        // Execute: Customer rents tape T002
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Rental should fail due to max rental limit
        assertFalse("Rental should fail when customer has 20 active rentals", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup: Create Customer C003 with 1 overdue rental
        customer = new Customer("C003");
        currentDate = LocalDate.of(2025, 1, 5);
        
        // Create 1 active rental that is 3 days overdue
        Tape overdueTape = new Tape("OVERDUE", "Overdue Video");
        VideoRental overdueRental = new VideoRental();
        overdueRental.setTape(overdueTape);
        overdueRental.setRentalStartDate(LocalDate.of(2024, 12, 20));
        overdueRental.setDueDate(LocalDate.of(2025, 1, 2)); // Due date was 3 days ago
        overdueRental.setReturnDate(null);
        customer.getRentals().add(overdueRental);
        RentalRepository.addRental(overdueRental);
        
        // Create available Tape T003
        tape = new Tape("T003", "Test Video 3");
        
        // Execute: Customer rents tape T003
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Rental should fail due to overdue fees
        assertFalse("Rental should fail when customer has overdue fees", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup: Create Customer C004 with 0 rentals
        customer = new Customer("C004");
        currentDate = LocalDate.of(2025, 1, 1);
        
        // Create Tape T004 with active rental by another customer
        tape = new Tape("T004", "Test Video 4");
        
        // Create another customer C010 who has rented T004
        Customer anotherCustomer = new Customer("C010");
        VideoRental existingRental = new VideoRental();
        existingRental.setTape(tape);
        existingRental.setRentalStartDate(currentDate.minusDays(1));
        existingRental.setDueDate(currentDate.plusDays(6));
        existingRental.setReturnDate(null);
        anotherCustomer.getRentals().add(existingRental);
        RentalRepository.addRental(existingRental);
        
        // Execute: Customer rents tape T004
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Rental should fail due to tape being unavailable
        assertFalse("Rental should fail when tape is unavailable", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup: Create Customer C005 with 20 active rentals and one overdue rental
        customer = new Customer("C005");
        currentDate = LocalDate.of(2025, 1, 1);
        
        // Create 20 active rentals for customer
        for (int i = 1; i <= 20; i++) {
            Tape tempTape = new Tape("TEMP" + i, "Video " + i);
            VideoRental rental = new VideoRental();
            rental.setTape(tempTape);
            rental.setRentalStartDate(currentDate.minusDays(i));
            rental.setDueDate(currentDate.minusDays(i).plusDays(7));
            rental.setReturnDate(null);
            customer.getRentals().add(rental);
            RentalRepository.addRental(rental);
        }
        
        // Add one overdue rental (due date was yesterday)
        Tape overdueTape = new Tape("OVERDUE", "Overdue Video");
        VideoRental overdueRental = new VideoRental();
        overdueRental.setTape(overdueTape);
        overdueRental.setRentalStartDate(currentDate.minusDays(8));
        overdueRental.setDueDate(LocalDate.of(2024, 12, 31)); // Due date was yesterday
        overdueRental.setReturnDate(null);
        customer.getRentals().add(overdueRental);
        RentalRepository.addRental(overdueRental);
        
        // Create Tape T005 with active rental by another customer
        tape = new Tape("T005", "Test Video 5");
        
        // Create another customer C011 who has rented T005
        Customer anotherCustomer = new Customer("C011");
        VideoRental existingRental = new VideoRental();
        existingRental.setTape(tape);
        existingRental.setRentalStartDate(currentDate.minusDays(1));
        existingRental.setDueDate(currentDate.plusDays(6));
        existingRental.setReturnDate(null);
        anotherCustomer.getRentals().add(existingRental);
        RentalRepository.addRental(existingRental);
        
        // Execute: Customer rents tape T005
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Rental should fail due to all conditions failing
        assertFalse("Rental should fail when all conditions (max rentals, overdue fees, tape unavailable) are violated", result);
    }
}