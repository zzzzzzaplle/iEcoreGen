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
        // Reset the static customers list before each test
        RentalSystem.customers = new ArrayList<>();
    }
    
    @Test
    public void testCase1_SuccessfulRental() {
        // Setup
        customer = new Customer();
        customer.setId("C001");
        
        // Create 5 active rentals for customer
        for (int i = 1; i <= 5; i++) {
            VideoRental rental = new VideoRental();
            Tape tempTape = new Tape();
            tempTape.setId("T00" + (i + 100)); // Unique IDs T101-T105
            rental.setTape(tempTape);
            rental.setDueDate(LocalDate.parse("2025-01-0" + i).plusDays(7));
            rental.setReturnDate(null);
            customer.getRentals().add(rental);
        }
        
        // Create available tape T001
        tape = new Tape();
        tape.setId("T001");
        
        currentDate = LocalDate.parse("2025-01-01");
        
        // Execute
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify
        assertTrue("Rental should be successful", result);
    }
    
    @Test
    public void testCase2_CustomerHas20Rentals() {
        // Setup
        customer = new Customer();
        customer.setId("C002");
        
        // Create 20 active rentals for customer
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = new VideoRental();
            Tape tempTape = new Tape();
            tempTape.setId("T20" + i); // Unique IDs T201-T220
            rental.setTape(tempTape);
            rental.setDueDate(LocalDate.parse("2025-01-01").plusDays(7));
            rental.setReturnDate(null);
            customer.getRentals().add(rental);
        }
        
        // Create available tape T002
        tape = new Tape();
        tape.setId("T002");
        
        currentDate = LocalDate.parse("2025-01-01");
        
        // Execute
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify
        assertFalse("Rental should fail due to 20 rental limit", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup
        customer = new Customer();
        customer.setId("C003");
        
        // Create 1 active rental that is 3 days overdue
        VideoRental overdueRental = new VideoRental();
        Tape tempTape = new Tape();
        tempTape.setId("T300");
        overdueRental.setTape(tempTape);
        overdueRental.setDueDate(LocalDate.parse("2025-01-02")); // Due 3 days ago from current date
        overdueRental.setReturnDate(null);
        customer.getRentals().add(overdueRental);
        
        // Create available tape T003
        tape = new Tape();
        tape.setId("T003");
        
        currentDate = LocalDate.parse("2025-01-05");
        
        // Execute
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify
        assertFalse("Rental should fail due to overdue fees", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup
        customer = new Customer();
        customer.setId("C004");
        
        // Create another customer who has T004 rented
        Customer otherCustomer = new Customer();
        otherCustomer.setId("C010");
        
        Tape rentedTape = new Tape();
        rentedTape.setId("T004");
        
        VideoRental activeRental = new VideoRental();
        activeRental.setTape(rentedTape);
        activeRental.setDueDate(LocalDate.parse("2025-01-05"));
        activeRental.setReturnDate(null);
        otherCustomer.getRentals().add(activeRental);
        
        // Add both customers to the system
        RentalSystem.customers.add(customer);
        RentalSystem.customers.add(otherCustomer);
        
        // Create tape T004 (same as the rented one)
        tape = new Tape();
        tape.setId("T004");
        
        currentDate = LocalDate.parse("2025-01-01");
        
        // Execute
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify
        assertFalse("Rental should fail due to tape unavailability", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup
        customer = new Customer();
        customer.setId("C005");
        
        // Create 20 active rentals for customer
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = new VideoRental();
            Tape tempTape = new Tape();
            tempTape.setId("T50" + i); // Unique IDs T501-T520
            rental.setTape(tempTape);
            rental.setDueDate(LocalDate.parse("2025-01-01").plusDays(7));
            rental.setReturnDate(null);
            customer.getRentals().add(rental);
        }
        
        // Add one overdue rental
        VideoRental overdueRental = new VideoRental();
        Tape overdueTape = new Tape();
        overdueTape.setId("T521");
        overdueRental.setTape(overdueTape);
        overdueRental.setDueDate(LocalDate.parse("2024-12-31")); // Overdue
        overdueRental.setReturnDate(null);
        customer.getRentals().add(overdueRental);
        
        // Create another customer who has T005 rented
        Customer otherCustomer = new Customer();
        otherCustomer.setId("C011");
        
        Tape rentedTape = new Tape();
        rentedTape.setId("T005");
        
        VideoRental activeRental = new VideoRental();
        activeRental.setTape(rentedTape);
        activeRental.setDueDate(LocalDate.parse("2025-01-05"));
        activeRental.setReturnDate(null);
        otherCustomer.getRentals().add(activeRental);
        
        // Add both customers to the system
        RentalSystem.customers.add(customer);
        RentalSystem.customers.add(otherCustomer);
        
        // Create tape T005 (same as the rented one)
        tape = new Tape();
        tape.setId("T005");
        
        currentDate = LocalDate.parse("2025-01-01");
        
        // Execute
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify
        assertFalse("Rental should fail due to all conditions failing", result);
    }
}