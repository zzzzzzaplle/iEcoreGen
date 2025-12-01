import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CR2Test {
    
    private RentalService rentalService;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        rentalService = new RentalService();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_TapeIsAvailable() {
        // Setup: Create Tape with id="T001", no active rentals for T001
        VideoTape tape = new VideoTape("T001", "Test Movie 1");
        rentalService.addVideoTape(tape);
        
        // Input: tape_id="T001", current_date="2025-01-01"
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        boolean result = rentalService.isTapeAvailable("T001", currentDate);
        
        // Expected Output: True
        assertTrue("Tape T001 should be available when no active rentals exist", result);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() {
        // Setup: Create Tape with id="T002", Create Customer C001
        VideoTape tape = new VideoTape("T002", "Test Movie 2");
        Customer customer = new Customer("C001", "Customer One");
        
        rentalService.addVideoTape(tape);
        rentalService.registerCustomer(customer);
        
        // Setup: C001 rents T002 with rental date="2024-12-28", due_date="2025-01-05", return_date=null
        LocalDate rentalDate = LocalDate.parse("2024-12-28", formatter);
        LocalDate dueDate = LocalDate.parse("2025-01-05", formatter);
        
        // Create and add rental transaction manually since we're testing availability logic
        Rental rental = new Rental(tape, customer, rentalDate, dueDate);
        RentalTransaction transaction = new RentalTransaction("TX001", customer, rentalDate);
        transaction.getRentals().add(rental);
        customer.getTransactions().add(transaction);
        rentalService.getTransactions().put("TX001", transaction);
        
        // Input: tape_id="T002", current_date="2025-01-01"
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        boolean result = rentalService.isTapeAvailable("T002", currentDate);
        
        // Expected Output: False
        assertFalse("Tape T002 should not be available when rented and not returned", result);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Setup: Create Tape with id="T003", Create Customer C002
        VideoTape tape = new VideoTape("T003", "Test Movie 3");
        Customer customer = new Customer("C002", "Customer Two");
        
        rentalService.addVideoTape(tape);
        rentalService.registerCustomer(customer);
        
        // Setup: C002 rents T003 with rental date="2024-12-25", due_date="2024-12-30", return_date="2024-12-31"
        LocalDate rentalDate = LocalDate.parse("2024-12-25", formatter);
        LocalDate dueDate = LocalDate.parse("2024-12-30", formatter);
        LocalDate returnDate = LocalDate.parse("2024-12-31", formatter);
        
        Rental rental = new Rental(tape, customer, rentalDate, dueDate);
        rental.setReturnDate(returnDate);
        
        RentalTransaction transaction = new RentalTransaction("TX002", customer, rentalDate);
        transaction.getRentals().add(rental);
        customer.getTransactions().add(transaction);
        rentalService.getTransactions().put("TX002", transaction);
        
        // Input: tape_id="T003", current_date="2025-01-01"
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        boolean result = rentalService.isTapeAvailable("T003", currentDate);
        
        // Expected Output: True
        assertTrue("Tape T003 should be available when previously rented but returned", result);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Setup: Create Tape with id="T004", Create Customer C003
        VideoTape tape = new VideoTape("T004", "Test Movie 4");
        Customer customer = new Customer("C003", "Customer Three");
        
        rentalService.addVideoTape(tape);
        rentalService.registerCustomer(customer);
        
        // Setup: C003 rents T004 with rental date="2024-12-28", due_date="2025-01-01", return_date=null
        LocalDate rentalDate = LocalDate.parse("2024-12-28", formatter);
        LocalDate dueDate = LocalDate.parse("2025-01-01", formatter);
        
        Rental rental = new Rental(tape, customer, rentalDate, dueDate);
        
        RentalTransaction transaction = new RentalTransaction("TX003", customer, rentalDate);
        transaction.getRentals().add(rental);
        customer.getTransactions().add(transaction);
        rentalService.getTransactions().put("TX003", transaction);
        
        // Input: tape_id="T004", current_date="2025-01-10"
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        boolean result = rentalService.isTapeAvailable("T004", currentDate);
        
        // Expected Output: False
        assertFalse("Tape T004 should not be available when overdue and not returned", result);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Setup: Create Tape with id="T005", Create Customers C004, C005
        VideoTape tape = new VideoTape("T005", "Test Movie 5");
        Customer customer1 = new Customer("C004", "Customer Four");
        Customer customer2 = new Customer("C005", "Customer Five");
        
        rentalService.addVideoTape(tape);
        rentalService.registerCustomer(customer1);
        rentalService.registerCustomer(customer2);
        
        // Setup: C004 rents T005 with rental date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01"
        LocalDate rentalDate1 = LocalDate.parse("2025-01-01", formatter);
        LocalDate dueDate1 = LocalDate.parse("2025-01-05", formatter);
        LocalDate returnDate1 = LocalDate.parse("2025-01-01", formatter);
        
        Rental rental1 = new Rental(tape, customer1, rentalDate1, dueDate1);
        rental1.setReturnDate(returnDate1);
        
        RentalTransaction transaction1 = new RentalTransaction("TX004", customer1, rentalDate1);
        transaction1.getRentals().add(rental1);
        customer1.getTransactions().add(transaction1);
        rentalService.getTransactions().put("TX004", transaction1);
        
        // First check: Tape should be available after first customer returns it
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        boolean result1 = rentalService.isTapeAvailable("T005", currentDate);
        assertTrue("Tape T005 should be available after first customer returns it", result1);
        
        // Setup: C005 rents T005 with rental date="2025-01-06", due_date="2025-01-15", return_date=null
        LocalDate rentalDate2 = LocalDate.parse("2025-01-06", formatter);
        LocalDate dueDate2 = LocalDate.parse("2025-01-15", formatter);
        
        Rental rental2 = new Rental(tape, customer2, rentalDate2, dueDate2);
        
        RentalTransaction transaction2 = new RentalTransaction("TX005", customer2, rentalDate2);
        transaction2.getRentals().add(rental2);
        customer2.getTransactions().add(transaction2);
        rentalService.getTransactions().put("TX005", transaction2);
        
        // Second check: Tape should not be available after second customer rents it
        boolean result2 = rentalService.isTapeAvailable("T005", currentDate);
        assertFalse("Tape T005 should not be available when second customer has active rental", result2);
    }
}