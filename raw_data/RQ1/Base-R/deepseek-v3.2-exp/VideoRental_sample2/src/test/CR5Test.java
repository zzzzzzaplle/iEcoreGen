import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    private VideoRentalSystem system;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        system = new VideoRentalSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_noRentalsExist() {
        // Setup: Create Customer C001 with empty rentals list
        Customer customer = new Customer();
        customer.setAccountNumber("C001");
        
        // Execute: Call listUnreturnedTapes
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
        
        // Create Tape T001
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T001");
        tape.setTitle("Test Movie");
        
        // Create VideoRental with return_date="2025-01-01"
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(LocalDate.parse("2025-01-01", formatter));
        
        // Add rental to customer
        customer.getRentals().add(rental);
        
        // Execute: Call listUnreturnedTapes
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
        
        // Create Tape T001
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T001");
        tape.setTitle("Test Movie");
        
        // Create VideoRental with return_date=null
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(null);
        
        // Add rental to customer
        customer.getRentals().add(rental);
        
        // Execute: Call listUnreturnedTapes
        List<String> result = system.listUnreturnedTapes(customer);
        
        // Verify: List containing T001
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains("T001"));
    }
    
    @Test
    public void testCase4_mixedReturnedUnreturned() {
        // Setup: Create Customer C004
        Customer customer = new Customer();
        customer.setAccountNumber("C004");
        
        // Create Tape T001 and T002
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        tape1.setTitle("Movie 1");
        
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T002");
        tape2.setTitle("Movie 2");
        
        // Create VideoRental for T001 with return_date="2025-01-01"
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-01", formatter));
        
        // Create VideoRental for T002 with return_date=null
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setTape(tape2);
        rental2.setRentalDate(LocalDate.parse("2025-01-02", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-06", formatter));
        rental2.setReturnDate(null);
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute: Call listUnreturnedTapes
        List<String> result = system.listUnreturnedTapes(customer);
        
        // Verify: List containing T002
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains("T002"));
        assertFalse(result.contains("T001"));
    }
    
    @Test
    public void testCase5_multipleUnreturnedTapes() {
        // Setup: Create Customer C005
        Customer customer = new Customer();
        customer.setAccountNumber("C005");
        
        // Create Tapes T001 and T002
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        tape1.setTitle("Movie 1");
        
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T002");
        tape2.setTitle("Movie 2");
        
        // Create VideoRental for T001 with return_date=null
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(null);
        
        // Create VideoRental for T002 with return_date=null
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setTape(tape2);
        rental2.setRentalDate(LocalDate.parse("2025-01-02", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-06", formatter));
        rental2.setReturnDate(null);
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute: Call listUnreturnedTapes
        List<String> result = system.listUnreturnedTapes(customer);
        
        // Verify: List containing T001 and T002
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("T001"));
        assertTrue(result.contains("T002"));
    }
}