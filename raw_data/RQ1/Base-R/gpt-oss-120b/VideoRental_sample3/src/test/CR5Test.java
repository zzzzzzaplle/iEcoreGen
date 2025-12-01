import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CR5Test {
    
    private RentalService rentalService;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        rentalService = new RentalService();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_noRentalsExist() {
        // Setup: Create Customer C001 with empty rentals list
        Customer customer = new Customer("C001", "Customer One");
        rentalService.registerCustomer(customer);
        
        // Execute: Call listUnreturnedTapes for customer C001
        List<String> result = rentalService.listUnreturnedTapes("C001");
        
        // Verify: Expected Output: Empty list, no active rentals
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty when no rentals exist", result.isEmpty());
    }
    
    @Test
    public void testCase2_allTapesReturned() {
        // Setup: Create Customer C002
        Customer customer = new Customer("C002", "Customer Two");
        rentalService.registerCustomer(customer);
        
        // Setup: Create Tape T001
        VideoTape tape = new VideoTape("T001", "Test Movie");
        rentalService.addVideoTape(tape);
        
        // Setup: Create VideoRental with rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01"
        LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
        LocalDate dueDate = LocalDate.parse("2025-01-05", formatter);
        LocalDate returnDate = LocalDate.parse("2025-01-01", formatter);
        
        Rental rental = new Rental(tape, customer, rentalDate, dueDate);
        rental.setReturnDate(returnDate);
        
        RentalTransaction transaction = new RentalTransaction("TX002", customer, rentalDate);
        transaction.getRentals().add(rental);
        
        customer.getTransactions().add(transaction);
        rentalService.getTransactions().put(transaction.getTransactionId(), transaction);
        
        // Execute: Call listUnreturnedTapes for customer C002
        List<String> result = rentalService.listUnreturnedTapes("C002");
        
        // Verify: Expected Output: Empty list, all rentals returned
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty when all tapes are returned", result.isEmpty());
    }
    
    @Test
    public void testCase3_oneUnreturnedTape() {
        // Setup: Create Customer C003
        Customer customer = new Customer("C003", "Customer Three");
        rentalService.registerCustomer(customer);
        
        // Setup: Create Tape T001
        VideoTape tape = new VideoTape("T001", "Test Movie");
        rentalService.addVideoTape(tape);
        
        // Setup: Create VideoRental with rental_date="2025-01-01", due_date="2025-01-05", return_date=null
        LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
        LocalDate dueDate = LocalDate.parse("2025-01-05", formatter);
        
        Rental rental = new Rental(tape, customer, rentalDate, dueDate);
        // return_date remains null (unreturned)
        
        RentalTransaction transaction = new RentalTransaction("TX003", customer, rentalDate);
        transaction.getRentals().add(rental);
        
        customer.getTransactions().add(transaction);
        rentalService.getTransactions().put(transaction.getTransactionId(), transaction);
        
        // Execute: Call listUnreturnedTapes for customer C003
        List<String> result = rentalService.listUnreturnedTapes("C003");
        
        // Verify: Expected Output: List containing T001
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly one tape", 1, result.size());
        assertTrue("Result should contain T001", result.contains("T001"));
    }
    
    @Test
    public void testCase4_mixedReturnedUnreturned() {
        // Setup: Create Customer C004
        Customer customer = new Customer("C004", "Customer Four");
        rentalService.registerCustomer(customer);
        
        // Setup: Create Tape T001 and T002
        VideoTape tape1 = new VideoTape("T001", "Test Movie 1");
        VideoTape tape2 = new VideoTape("T002", "Test Movie 2");
        rentalService.addVideoTape(tape1);
        rentalService.addVideoTape(tape2);
        
        // Setup: Create VideoRental for T001 with rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01"
        LocalDate rentalDate1 = LocalDate.parse("2025-01-01", formatter);
        LocalDate dueDate1 = LocalDate.parse("2025-01-05", formatter);
        LocalDate returnDate1 = LocalDate.parse("2025-01-01", formatter);
        
        Rental rental1 = new Rental(tape1, customer, rentalDate1, dueDate1);
        rental1.setReturnDate(returnDate1);
        
        // Setup: Create VideoRental for T002 with rental_date="2025-01-02", due_date="2025-01-06", return_date=null
        LocalDate rentalDate2 = LocalDate.parse("2025-01-02", formatter);
        LocalDate dueDate2 = LocalDate.parse("2025-01-06", formatter);
        
        Rental rental2 = new Rental(tape2, customer, rentalDate2, dueDate2);
        // return_date remains null (unreturned)
        
        RentalTransaction transaction1 = new RentalTransaction("TX004-1", customer, rentalDate1);
        transaction1.getRentals().add(rental1);
        
        RentalTransaction transaction2 = new RentalTransaction("TX004-2", customer, rentalDate2);
        transaction2.getRentals().add(rental2);
        
        customer.getTransactions().add(transaction1);
        customer.getTransactions().add(transaction2);
        rentalService.getTransactions().put(transaction1.getTransactionId(), transaction1);
        rentalService.getTransactions().put(transaction2.getTransactionId(), transaction2);
        
        // Execute: Call listUnreturnedTapes for customer C004
        List<String> result = rentalService.listUnreturnedTapes("C004");
        
        // Verify: Expected Output: List containing T002
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly one tape", 1, result.size());
        assertTrue("Result should contain T002", result.contains("T002"));
        assertFalse("Result should not contain T001", result.contains("T001"));
    }
    
    @Test
    public void testCase5_multipleUnreturnedTapes() {
        // Setup: Create Customer C005
        Customer customer = new Customer("C005", "Customer Five");
        rentalService.registerCustomer(customer);
        
        // Setup: Create Tapes T001 and T002
        VideoTape tape1 = new VideoTape("T001", "Test Movie 1");
        VideoTape tape2 = new VideoTape("T002", "Test Movie 2");
        rentalService.addVideoTape(tape1);
        rentalService.addVideoTape(tape2);
        
        // Setup: Create VideoRental for T001 with rental_date="2025-01-01", due_date="2025-01-05", return_date=null
        LocalDate rentalDate1 = LocalDate.parse("2025-01-01", formatter);
        LocalDate dueDate1 = LocalDate.parse("2025-01-05", formatter);
        
        Rental rental1 = new Rental(tape1, customer, rentalDate1, dueDate1);
        // return_date remains null (unreturned)
        
        // Setup: Create VideoRental for T002 with rental_date="2025-01-02", due_date="2025-01-06", return_date=null
        LocalDate rentalDate2 = LocalDate.parse("2025-01-02", formatter);
        LocalDate dueDate2 = LocalDate.parse("2025-01-06", formatter);
        
        Rental rental2 = new Rental(tape2, customer, rentalDate2, dueDate2);
        // return_date remains null (unreturned)
        
        RentalTransaction transaction1 = new RentalTransaction("TX005-1", customer, rentalDate1);
        transaction1.getRentals().add(rental1);
        
        RentalTransaction transaction2 = new RentalTransaction("TX005-2", customer, rentalDate2);
        transaction2.getRentals().add(rental2);
        
        customer.getTransactions().add(transaction1);
        customer.getTransactions().add(transaction2);
        rentalService.getTransactions().put(transaction1.getTransactionId(), transaction1);
        rentalService.getTransactions().put(transaction2.getTransactionId(), transaction2);
        
        // Execute: Call listUnreturnedTapes for customer C005
        List<String> result = rentalService.listUnreturnedTapes("C005");
        
        // Verify: Expected Output: List containing T001 and T002
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly two tapes", 2, result.size());
        assertTrue("Result should contain T001", result.contains("T001"));
        assertTrue("Result should contain T002", result.contains("T002"));
    }
}