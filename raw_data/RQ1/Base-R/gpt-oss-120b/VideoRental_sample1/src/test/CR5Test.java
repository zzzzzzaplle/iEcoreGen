import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;

public class CR5Test {
    
    private RentalSystem rentalSystem;
    
    @Before
    public void setUp() {
        rentalSystem = new RentalSystem();
    }
    
    @Test
    public void testCase1_noRentalsExist() {
        // Setup: Create Customer C001 with empty rentals list
        Customer customer = new Customer("C001", "Customer One");
        rentalSystem.addCustomer(customer);
        
        // Input: customer_id="C001"
        List<String> result = rentalSystem.listUnreturnedTapes("C001");
        
        // Expected Output: Empty list, no active rentals.
        assertTrue("List should be empty when customer has no rentals", result.isEmpty());
    }
    
    @Test
    public void testCase2_allTapesReturned() {
        // Setup: Create Customer C002
        Customer customer = new Customer("C002", "Customer Two");
        rentalSystem.addCustomer(customer);
        
        // Setup: Create Tape T001
        VideoTape tape = new VideoTape("T001", "Movie One");
        rentalSystem.addVideoTape(tape);
        
        // Setup: Create VideoRental with rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01", associated with C002 and T001
        LocalDate rentalDate = LocalDate.of(2025, 1, 1);
        LocalDate dueDate = LocalDate.of(2025, 1, 5);
        LocalDate returnDate = LocalDate.of(2025, 1, 1);
        
        RentalTransaction rental = new RentalTransaction("TRX001", "C002", "T001", rentalDate, dueDate);
        rental.setReturnDate(returnDate);
        customer.addRental(rental);
        rentalSystem.getRentals().add(rental);
        
        // Input: customer_id="C002"
        List<String> result = rentalSystem.listUnreturnedTapes("C002");
        
        // Expected Output: Empty list, all rentals returned.
        assertTrue("List should be empty when all tapes are returned", result.isEmpty());
    }
    
    @Test
    public void testCase3_oneUnreturnedTape() {
        // Setup: Create Customer C003
        Customer customer = new Customer("C003", "Customer Three");
        rentalSystem.addCustomer(customer);
        
        // Setup: Create Tape T001
        VideoTape tape = new VideoTape("T001", "Movie One");
        rentalSystem.addVideoTape(tape);
        
        // Setup: Create VideoRental with rental_date="2025-01-01", due_date="2025-01-05", return_date=null, associated with C003 and T001
        LocalDate rentalDate = LocalDate.of(2025, 1, 1);
        LocalDate dueDate = LocalDate.of(2025, 1, 5);
        
        RentalTransaction rental = new RentalTransaction("TRX002", "C003", "T001", rentalDate, dueDate);
        // return_date remains null
        customer.addRental(rental);
        rentalSystem.getRentals().add(rental);
        
        // Input: customer_id="C003"
        List<String> result = rentalSystem.listUnreturnedTapes("C003");
        
        // Expected Output: List containing T001.
        assertEquals("List should contain exactly one tape", 1, result.size());
        assertTrue("List should contain T001", result.contains("T001"));
    }
    
    @Test
    public void testCase4_mixedReturnedUnreturned() {
        // Setup: Create Customer C004
        Customer customer = new Customer("C004", "Customer Four");
        rentalSystem.addCustomer(customer);
        
        // Setup: Create Tape T001 and T002
        VideoTape tape1 = new VideoTape("T001", "Movie One");
        VideoTape tape2 = new VideoTape("T002", "Movie Two");
        rentalSystem.addVideoTape(tape1);
        rentalSystem.addVideoTape(tape2);
        
        // Setup: Create VideoRental for T001 with rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01", associated with C004
        LocalDate rentalDate1 = LocalDate.of(2025, 1, 1);
        LocalDate dueDate1 = LocalDate.of(2025, 1, 5);
        LocalDate returnDate1 = LocalDate.of(2025, 1, 1);
        
        RentalTransaction rental1 = new RentalTransaction("TRX003", "C004", "T001", rentalDate1, dueDate1);
        rental1.setReturnDate(returnDate1);
        customer.addRental(rental1);
        rentalSystem.getRentals().add(rental1);
        
        // Setup: Create VideoRental for T002 with rental_date="2025-01-02", due_date="2025-01-06", return_date=null, associated with C004
        LocalDate rentalDate2 = LocalDate.of(2025, 1, 2);
        LocalDate dueDate2 = LocalDate.of(2025, 1, 6);
        
        RentalTransaction rental2 = new RentalTransaction("TRX004", "C004", "T002", rentalDate2, dueDate2);
        // return_date remains null
        customer.addRental(rental2);
        rentalSystem.getRentals().add(rental2);
        
        // Input: customer_id="C004"
        List<String> result = rentalSystem.listUnreturnedTapes("C004");
        
        // Expected Output: List containing T002.
        assertEquals("List should contain exactly one tape", 1, result.size());
        assertTrue("List should contain T002", result.contains("T002"));
    }
    
    @Test
    public void testCase5_multipleUnreturnedTapes() {
        // Setup: Create Customer C005
        Customer customer = new Customer("C005", "Customer Five");
        rentalSystem.addCustomer(customer);
        
        // Setup: Create Tapes T001 and T002
        VideoTape tape1 = new VideoTape("T001", "Movie One");
        VideoTape tape2 = new VideoTape("T002", "Movie Two");
        rentalSystem.addVideoTape(tape1);
        rentalSystem.addVideoTape(tape2);
        
        // Setup: Create VideoRental for T001 with rental_date="2025-01-01", due_date="2025-01-05", return_date=null, associated with C005
        LocalDate rentalDate1 = LocalDate.of(2025, 1, 1);
        LocalDate dueDate1 = LocalDate.of(2025, 1, 5);
        
        RentalTransaction rental1 = new RentalTransaction("TRX005", "C005", "T001", rentalDate1, dueDate1);
        // return_date remains null
        customer.addRental(rental1);
        rentalSystem.getRentals().add(rental1);
        
        // Setup: Create VideoRental for T002 with rental_date="2025-01-02", due_date="2025-01-06", return_date=null, associated with C005
        LocalDate rentalDate2 = LocalDate.of(2025, 1, 2);
        LocalDate dueDate2 = LocalDate.of(2025, 1, 6);
        
        RentalTransaction rental2 = new RentalTransaction("TRX006", "C005", "T002", rentalDate2, dueDate2);
        // return_date remains null
        customer.addRental(rental2);
        rentalSystem.getRentals().add(rental2);
        
        // Input: customer_id="C005"
        List<String> result = rentalSystem.listUnreturnedTapes("C005");
        
        // Expected Output: List containing T001 and T002.
        assertEquals("List should contain exactly two tapes", 2, result.size());
        assertTrue("List should contain T001", result.contains("T001"));
        assertTrue("List should contain T002", result.contains("T002"));
    }
}