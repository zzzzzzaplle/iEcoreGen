import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Arrays;

public class CR5Test {
    
    private RentalStore rentalStore;
    
    @Before
    public void setUp() {
        rentalStore = new RentalStore();
    }
    
    @Test
    public void testCase1_noRentalsExist() {
        // Setup: Create Customer C001 with empty rentals list
        Customer customer = new Customer();
        customer.setAccountNumber("C001");
        customer.setName("Customer One");
        customer.setIdCardBarcode("ID001");
        
        // Execute: Call listUnreturnedTapes method
        List<String> result = rentalStore.listUnreturnedTapes(customer);
        
        // Verify: Expected Output: Empty list, no active rentals
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase2_allTapesReturned() {
        // Setup:
        // 1. Create Customer C002
        Customer customer = new Customer();
        customer.setAccountNumber("C002");
        customer.setName("Customer Two");
        customer.setIdCardBarcode("ID002");
        
        // 2. Create Tape T001
        VideoTape tape = new VideoTape();
        tape.setTapeId("T001");
        tape.setTitle("Movie One");
        
        // 3. Create VideoRental with rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01"
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.parse("2025-01-01"));
        rental.setDueDate(LocalDate.parse("2025-01-05"));
        rental.setReturnDate(LocalDate.parse("2025-01-01"));
        
        // Add rental to customer and store
        customer.getRentals().add(rental);
        rentalStore.getRentals().add(rental);
        
        // Execute: Call listUnreturnedTapes method
        List<String> result = rentalStore.listUnreturnedTapes(customer);
        
        // Verify: Expected Output: Empty list, all rentals returned
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase3_oneUnreturnedTape() {
        // Setup:
        // 1. Create Customer C003
        Customer customer = new Customer();
        customer.setAccountNumber("C003");
        customer.setName("Customer Three");
        customer.setIdCardBarcode("ID003");
        
        // 2. Create Tape T001
        VideoTape tape = new VideoTape();
        tape.setTapeId("T001");
        tape.setTitle("Movie One");
        
        // 3. Create VideoRental with rental_date="2025-01-01", due_date="2025-01-05", return_date=null
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.parse("2025-01-01"));
        rental.setDueDate(LocalDate.parse("2025-01-05"));
        rental.setReturnDate(null);
        
        // Add rental to customer and store
        customer.getRentals().add(rental);
        rentalStore.getRentals().add(rental);
        
        // Execute: Call listUnreturnedTapes method
        List<String> result = rentalStore.listUnreturnedTapes(customer);
        
        // Verify: Expected Output: List containing T001
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly one element", 1, result.size());
        assertEquals("Result should contain T001", "T001", result.get(0));
    }
    
    @Test
    public void testCase4_mixedReturnedUnreturned() {
        // Setup:
        // 1. Create Customer C004
        Customer customer = new Customer();
        customer.setAccountNumber("C004");
        customer.setName("Customer Four");
        customer.setIdCardBarcode("ID004");
        
        // 2. Create Tape T001 and T002
        VideoTape tape1 = new VideoTape();
        tape1.setTapeId("T001");
        tape1.setTitle("Movie One");
        
        VideoTape tape2 = new VideoTape();
        tape2.setTapeId("T002");
        tape2.setTitle("Movie Two");
        
        // 3. Create VideoRental for T001 with rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01"
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01"));
        rental1.setDueDate(LocalDate.parse("2025-01-05"));
        rental1.setReturnDate(LocalDate.parse("2025-01-01"));
        
        // 4. Create VideoRental for T002 with rental_date="2025-01-02", due_date="2025-01-06", return_date=null
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setTape(tape2);
        rental2.setRentalDate(LocalDate.parse("2025-01-02"));
        rental2.setDueDate(LocalDate.parse("2025-01-06"));
        rental2.setReturnDate(null);
        
        // Add rentals to customer and store
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        rentalStore.getRentals().add(rental1);
        rentalStore.getRentals().add(rental2);
        
        // Execute: Call listUnreturnedTapes method
        List<String> result = rentalStore.listUnreturnedTapes(customer);
        
        // Verify: Expected Output: List containing T002
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly one element", 1, result.size());
        assertEquals("Result should contain T002", "T002", result.get(0));
    }
    
    @Test
    public void testCase5_multipleUnreturnedTapes() {
        // Setup:
        // 1. Create Customer C005
        Customer customer = new Customer();
        customer.setAccountNumber("C005");
        customer.setName("Customer Five");
        customer.setIdCardBarcode("ID005");
        
        // 2. Create Tapes T001 and T002
        VideoTape tape1 = new VideoTape();
        tape1.setTapeId("T001");
        tape1.setTitle("Movie One");
        
        VideoTape tape2 = new VideoTape();
        tape2.setTapeId("T002");
        tape2.setTitle("Movie Two");
        
        // 3. Create VideoRental for T001 with rental_date="2025-01-01", due_date="2025-01-05", return_date=null
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01"));
        rental1.setDueDate(LocalDate.parse("2025-01-05"));
        rental1.setReturnDate(null);
        
        // 4. Create VideoRental for T002 with rental_date="2025-01-02", due_date="2025-01-06", return_date=null
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setTape(tape2);
        rental2.setRentalDate(LocalDate.parse("2025-01-02"));
        rental2.setDueDate(LocalDate.parse("2025-01-06"));
        rental2.setReturnDate(null);
        
        // Add rentals to customer and store
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        rentalStore.getRentals().add(rental1);
        rentalStore.getRentals().add(rental2);
        
        // Execute: Call listUnreturnedTapes method
        List<String> result = rentalStore.listUnreturnedTapes(customer);
        
        // Verify: Expected Output: List containing T001 and T002
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly two elements", 2, result.size());
        assertTrue("Result should contain T001", result.contains("T001"));
        assertTrue("Result should contain T002", result.contains("T002"));
    }
}