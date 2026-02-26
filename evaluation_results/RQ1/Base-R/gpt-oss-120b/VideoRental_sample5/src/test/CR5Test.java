import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

public class CR5Test {
    
    private RentalSystem rentalSystem;
    private DateTimeFormatter dateFormatter;
    
    @Before
    public void setUp() {
        rentalSystem = new RentalSystem();
        dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_noRentalsExist() {
        // Setup: Create Customer C001 with empty rentals list
        Customer customer = new Customer();
        customer.setAccountNumber(1);
        customer.setName("C001");
        
        rentalSystem.getCustomers().add(customer);
        
        // Execute: Call listUnreturnedTapes for customer_id="C001"
        List<String> result = rentalSystem.listUnreturnedTapes(1);
        
        // Verify: Expected Output: Empty list, no active rentals.
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase2_allTapesReturned() {
        // Setup: Create Customer C002
        Customer customer = new Customer();
        customer.setAccountNumber(2);
        customer.setName("C002");
        
        // Create Tape T001
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T001");
        tape.setTitle("Test Tape 1");
        
        // Create VideoRental with rental_date="2025-01-01", due_date="2025-01-05", 
        // return_date="2025-01-01", associated with C002 and T001
        RentalTransaction rental = new RentalTransaction();
        rental.setTransactionId(1);
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.parse("2025-01-01", dateFormatter));
        rental.setDueDate(LocalDate.parse("2025-01-05", dateFormatter));
        rental.setReturnDate(LocalDate.parse("2025-01-01", dateFormatter));
        rental.setBaseFee(4.0); // 4 days * $1
        
        customer.getRentals().add(rental);
        rentalSystem.getCustomers().add(customer);
        rentalSystem.getAllTransactions().add(rental);
        
        // Execute: Call listUnreturnedTapes for customer_id="C002"
        List<String> result = rentalSystem.listUnreturnedTapes(2);
        
        // Verify: Expected Output: Empty list, all rentals returned.
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase3_oneUnreturnedTape() {
        // Setup: Create Customer C003
        Customer customer = new Customer();
        customer.setAccountNumber(3);
        customer.setName("C003");
        
        // Create Tape T001
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T001");
        tape.setTitle("Test Tape 1");
        
        // Create VideoRental with rental_date="2025-01-01", due_date="2025-01-05", 
        // return_date=null, associated with C003 and T001
        RentalTransaction rental = new RentalTransaction();
        rental.setTransactionId(1);
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.parse("2025-01-01", dateFormatter));
        rental.setDueDate(LocalDate.parse("2025-01-05", dateFormatter));
        rental.setReturnDate(null); // Not returned
        rental.setBaseFee(4.0); // 4 days * $1
        
        customer.getRentals().add(rental);
        rentalSystem.getCustomers().add(customer);
        rentalSystem.getAllTransactions().add(rental);
        
        // Execute: Call listUnreturnedTapes for customer_id="C003"
        List<String> result = rentalSystem.listUnreturnedTapes(3);
        
        // Verify: Expected Output: List containing T001.
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly 1 item", 1, result.size());
        assertEquals("Result should contain T001", "T001", result.get(0));
    }
    
    @Test
    public void testCase4_mixedReturnedUnreturned() {
        // Setup: Create Customer C004
        Customer customer = new Customer();
        customer.setAccountNumber(4);
        customer.setName("C004");
        
        // Create Tape T001 and T002
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        tape1.setTitle("Test Tape 1");
        
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T002");
        tape2.setTitle("Test Tape 2");
        
        // Create VideoRental for T001 with rental_date="2025-01-01", due_date="2025-01-05", 
        // return_date="2025-01-01", associated with C004
        RentalTransaction rental1 = new RentalTransaction();
        rental1.setTransactionId(1);
        rental1.setCustomer(customer);
        rental1.setTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", dateFormatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", dateFormatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-01", dateFormatter));
        rental1.setBaseFee(4.0); // 4 days * $1
        
        // Create VideoRental for T002 with rental_date="2025-01-02", due_date="2025-01-06", 
        // return_date=null, associated with C004
        RentalTransaction rental2 = new RentalTransaction();
        rental2.setTransactionId(2);
        rental2.setCustomer(customer);
        rental2.setTape(tape2);
        rental2.setRentalDate(LocalDate.parse("2025-01-02", dateFormatter));
        rental2.setDueDate(LocalDate.parse("2025-01-06", dateFormatter));
        rental2.setReturnDate(null); // Not returned
        rental2.setBaseFee(4.0); // 4 days * $1
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        rentalSystem.getCustomers().add(customer);
        rentalSystem.getAllTransactions().add(rental1);
        rentalSystem.getAllTransactions().add(rental2);
        
        // Execute: Call listUnreturnedTapes for customer_id="C004"
        List<String> result = rentalSystem.listUnreturnedTapes(4);
        
        // Verify: Expected Output: List containing T002.
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly 1 item", 1, result.size());
        assertEquals("Result should contain T002", "T002", result.get(0));
    }
    
    @Test
    public void testCase5_multipleUnreturnedTapes() {
        // Setup: Create Customer C005
        Customer customer = new Customer();
        customer.setAccountNumber(5);
        customer.setName("C005");
        
        // Create Tapes T001 and T002
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        tape1.setTitle("Test Tape 1");
        
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T002");
        tape2.setTitle("Test Tape 2");
        
        // Create VideoRental for T001 with rental_date="2025-01-01", due_date="2025-01-05", 
        // return_date=null, associated with C005
        RentalTransaction rental1 = new RentalTransaction();
        rental1.setTransactionId(1);
        rental1.setCustomer(customer);
        rental1.setTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", dateFormatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", dateFormatter));
        rental1.setReturnDate(null); // Not returned
        rental1.setBaseFee(4.0); // 4 days * $1
        
        // Create VideoRental for T002 with rental_date="2025-01-02", due_date="2025-01-06", 
        // return_date=null, associated with C005
        RentalTransaction rental2 = new RentalTransaction();
        rental2.setTransactionId(2);
        rental2.setCustomer(customer);
        rental2.setTape(tape2);
        rental2.setRentalDate(LocalDate.parse("2025-01-02", dateFormatter));
        rental2.setDueDate(LocalDate.parse("2025-01-06", dateFormatter));
        rental2.setReturnDate(null); // Not returned
        rental2.setBaseFee(4.0); // 4 days * $1
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        rentalSystem.getCustomers().add(customer);
        rentalSystem.getAllTransactions().add(rental1);
        rentalSystem.getAllTransactions().add(rental2);
        
        // Execute: Call listUnreturnedTapes for customer_id="C005"
        List<String> result = rentalSystem.listUnreturnedTapes(5);
        
        // Verify: Expected Output: List containing T001 and T002.
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly 2 items", 2, result.size());
        assertTrue("Result should contain T001", result.contains("T001"));
        assertTrue("Result should contain T002", result.contains("T002"));
    }
}