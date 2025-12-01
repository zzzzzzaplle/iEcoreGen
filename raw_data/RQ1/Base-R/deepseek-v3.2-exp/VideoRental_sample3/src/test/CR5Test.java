import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private VideoRentalSystem system;
    private Customer customer;
    private VideoTape tape1;
    private VideoTape tape2;
    private Rental rental1;
    private Rental rental2;
    
    @Before
    public void setUp() {
        system = new VideoRentalSystem();
    }
    
    @Test
    public void testCase1_noRentalsExist() {
        // Setup: Create Customer C001 with empty rentals list
        Customer customer = new Customer();
        customer.setAccountNumber("C001");
        customer.setRentals(new ArrayList<>());
        
        // Execute: List unreturned tapes for customer C001
        List<String> result = system.listUnreturnedTapes(customer);
        
        // Verify: Empty list, no active rentals
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase2_allTapesReturned() {
        // Setup: Create Customer C002
        Customer customer = new Customer();
        customer.setAccountNumber("C002");
        customer.setRentals(new ArrayList<>());
        
        // Setup: Create Tape T001
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("T001");
        
        // Setup: Create VideoRental with return_date="2025-01-01"
        Rental rental = new Rental();
        rental.setTape(tape);
        rental.setCustomer(customer);
        rental.setRentalDate(LocalDate.parse("2025-01-01"));
        rental.setDueDate(LocalDate.parse("2025-01-05"));
        rental.setReturnDate(LocalDate.parse("2025-01-01"));
        
        // Add rental to customer's rentals
        customer.getRentals().add(rental);
        
        // Execute: List unreturned tapes for customer C002
        List<String> result = system.listUnreturnedTapes(customer);
        
        // Verify: Empty list, all rentals returned
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_oneUnreturnedTape() {
        // Setup: Create Customer C003
        Customer customer = new Customer();
        customer.setAccountNumber("C003");
        customer.setRentals(new ArrayList<>());
        
        // Setup: Create Tape T001
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("T001");
        
        // Setup: Create VideoRental with return_date=null
        Rental rental = new Rental();
        rental.setTape(tape);
        rental.setCustomer(customer);
        rental.setRentalDate(LocalDate.parse("2025-01-01"));
        rental.setDueDate(LocalDate.parse("2025-01-05"));
        rental.setReturnDate(null); // Not returned
        
        // Add rental to customer's rentals
        customer.getRentals().add(rental);
        
        // Execute: List unreturned tapes for customer C003
        List<String> result = system.listUnreturnedTapes(customer);
        
        // Verify: List containing T001
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("T001", result.get(0));
    }
    
    @Test
    public void testCase4_mixedReturnedUnreturned() {
        // Setup: Create Customer C004
        Customer customer = new Customer();
        customer.setAccountNumber("C004");
        customer.setRentals(new ArrayList<>());
        
        // Setup: Create Tape T001 and T002
        VideoTape tape1 = new VideoTape();
        tape1.setBarCodeId("T001");
        VideoTape tape2 = new VideoTape();
        tape2.setBarCodeId("T002");
        
        // Setup: Create VideoRental for T001 with return_date="2025-01-01"
        Rental rental1 = new Rental();
        rental1.setTape(tape1);
        rental1.setCustomer(customer);
        rental1.setRentalDate(LocalDate.parse("2025-01-01"));
        rental1.setDueDate(LocalDate.parse("2025-01-05"));
        rental1.setReturnDate(LocalDate.parse("2025-01-01")); // Returned
        
        // Setup: Create VideoRental for T002 with return_date=null
        Rental rental2 = new Rental();
        rental2.setTape(tape2);
        rental2.setCustomer(customer);
        rental2.setRentalDate(LocalDate.parse("2025-01-02"));
        rental2.setDueDate(LocalDate.parse("2025-01-06"));
        rental2.setReturnDate(null); // Not returned
        
        // Add rentals to customer's rentals
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute: List unreturned tapes for customer C004
        List<String> result = system.listUnreturnedTapes(customer);
        
        // Verify: List containing T002
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("T002", result.get(0));
    }
    
    @Test
    public void testCase5_multipleUnreturnedTapes() {
        // Setup: Create Customer C005
        Customer customer = new Customer();
        customer.setAccountNumber("C005");
        customer.setRentals(new ArrayList<>());
        
        // Setup: Create Tapes T001 and T002
        VideoTape tape1 = new VideoTape();
        tape1.setBarCodeId("T001");
        VideoTape tape2 = new VideoTape();
        tape2.setBarCodeId("T002");
        
        // Setup: Create VideoRental for T001 with return_date=null
        Rental rental1 = new Rental();
        rental1.setTape(tape1);
        rental1.setCustomer(customer);
        rental1.setRentalDate(LocalDate.parse("2025-01-01"));
        rental1.setDueDate(LocalDate.parse("2025-01-05"));
        rental1.setReturnDate(null); // Not returned
        
        // Setup: Create VideoRental for T002 with return_date=null
        Rental rental2 = new Rental();
        rental2.setTape(tape2);
        rental2.setCustomer(customer);
        rental2.setRentalDate(LocalDate.parse("2025-01-02"));
        rental2.setDueDate(LocalDate.parse("2025-01-06"));
        rental2.setReturnDate(null); // Not returned
        
        // Add rentals to customer's rentals
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute: List unreturned tapes for customer C005
        List<String> result = system.listUnreturnedTapes(customer);
        
        // Verify: List containing T001 and T002
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("T001"));
        assertTrue(result.contains("T002"));
    }
}