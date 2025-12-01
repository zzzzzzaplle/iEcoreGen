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
        Customer customer = new Customer();
        customer.setAccountId("C001");
        
        // Execute: List unreturned tapes for customer
        List<String> result = rentalSystem.listUnreturnedTapes(customer);
        
        // Verify: Expected output is empty list, no active rentals
        assertTrue("List should be empty when customer has no rentals", result.isEmpty());
    }
    
    @Test
    public void testCase2_allTapesReturned() {
        // Setup: Create Customer C002
        Customer customer = new Customer();
        customer.setAccountId("C002");
        
        // Create Tape T001
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T001");
        
        // Create VideoRental with return date (already returned)
        Rental rental = new Rental();
        rental.setTapeId("T001");
        rental.setRentalDate(LocalDate.of(2025, 1, 1));
        rental.setDueDate(LocalDate.of(2025, 1, 5));
        rental.setReturnDate(LocalDate.of(2025, 1, 1)); // Returned on same day
        
        customer.getRentals().add(rental);
        
        // Execute: List unreturned tapes for customer
        List<String> result = rentalSystem.listUnreturnedTapes(customer);
        
        // Verify: Expected output is empty list, all rentals returned
        assertTrue("List should be empty when all tapes are returned", result.isEmpty());
    }
    
    @Test
    public void testCase3_oneUnreturnedTape() {
        // Setup: Create Customer C003
        Customer customer = new Customer();
        customer.setAccountId("C003");
        
        // Create Tape T001
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T001");
        
        // Create VideoRental with return_date=null (not returned)
        Rental rental = new Rental();
        rental.setTapeId("T001");
        rental.setRentalDate(LocalDate.of(2025, 1, 1));
        rental.setDueDate(LocalDate.of(2025, 1, 5));
        rental.setReturnDate(null); // Not returned
        
        customer.getRentals().add(rental);
        
        // Execute: List unreturned tapes for customer
        List<String> result = rentalSystem.listUnreturnedTapes(customer);
        
        // Verify: Expected output is list containing T001
        assertEquals("List should contain exactly one element", 1, result.size());
        assertTrue("List should contain T001", result.contains("T001"));
    }
    
    @Test
    public void testCase4_mixedReturnedUnreturned() {
        // Setup: Create Customer C004
        Customer customer = new Customer();
        customer.setAccountId("C004");
        
        // Create Tapes T001 and T002
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T002");
        
        // Create VideoRental for T001 with return date (returned)
        Rental rental1 = new Rental();
        rental1.setTapeId("T001");
        rental1.setRentalDate(LocalDate.of(2025, 1, 1));
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 1)); // Returned
        
        // Create VideoRental for T002 with return_date=null (not returned)
        Rental rental2 = new Rental();
        rental2.setTapeId("T002");
        rental2.setRentalDate(LocalDate.of(2025, 1, 2));
        rental2.setDueDate(LocalDate.of(2025, 1, 6));
        rental2.setReturnDate(null); // Not returned
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute: List unreturned tapes for customer
        List<String> result = rentalSystem.listUnreturnedTapes(customer);
        
        // Verify: Expected output is list containing T002 only
        assertEquals("List should contain exactly one element", 1, result.size());
        assertTrue("List should contain T002", result.contains("T002"));
        assertFalse("List should not contain T001", result.contains("T001"));
    }
    
    @Test
    public void testCase5_multipleUnreturnedTapes() {
        // Setup: Create Customer C005
        Customer customer = new Customer();
        customer.setAccountId("C005");
        
        // Create Tapes T001 and T002
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T002");
        
        // Create VideoRental for T001 with return_date=null (not returned)
        Rental rental1 = new Rental();
        rental1.setTapeId("T001");
        rental1.setRentalDate(LocalDate.of(2025, 1, 1));
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(null); // Not returned
        
        // Create VideoRental for T002 with return_date=null (not returned)
        Rental rental2 = new Rental();
        rental2.setTapeId("T002");
        rental2.setRentalDate(LocalDate.of(2025, 1, 2));
        rental2.setDueDate(LocalDate.of(2025, 1, 6));
        rental2.setReturnDate(null); // Not returned
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute: List unreturned tapes for customer
        List<String> result = rentalSystem.listUnreturnedTapes(customer);
        
        // Verify: Expected output is list containing T001 and T002
        assertEquals("List should contain exactly two elements", 2, result.size());
        assertTrue("List should contain T001", result.contains("T001"));
        assertTrue("List should contain T002", result.contains("T002"));
    }
}