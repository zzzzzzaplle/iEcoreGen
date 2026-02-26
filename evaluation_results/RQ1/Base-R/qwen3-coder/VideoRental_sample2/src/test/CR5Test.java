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
        rentalSystem.getCustomers().add(customer);
        
        // Execute: Call listUnreturnedTapes for customer C001
        List<String> result = rentalSystem.listUnreturnedTapes("C001");
        
        // Verify: Expected output is empty list, no active rentals
        assertTrue("List should be empty when customer has no rentals", result.isEmpty());
    }
    
    @Test
    public void testCase2_allTapesReturned() {
        // Setup: Create Customer C002
        Customer customer = new Customer();
        customer.setAccountId("C002");
        rentalSystem.getCustomers().add(customer);
        
        // Setup: Create Tape T001
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T001");
        rentalSystem.getTapes().add(tape);
        
        // Setup: Create VideoRental with return date (returned)
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.of(2025, 1, 1));
        rental.setDueDate(LocalDate.of(2025, 1, 5));
        rental.setReturnDate(LocalDate.of(2025, 1, 1)); // Returned on same day
        
        customer.getRentals().add(rental);
        rentalSystem.getRentals().add(rental);
        
        // Execute: Call listUnreturnedTapes for customer C002
        List<String> result = rentalSystem.listUnreturnedTapes("C002");
        
        // Verify: Expected output is empty list, all rentals returned
        assertTrue("List should be empty when all tapes are returned", result.isEmpty());
    }
    
    @Test
    public void testCase3_oneUnreturnedTape() {
        // Setup: Create Customer C003
        Customer customer = new Customer();
        customer.setAccountId("C003");
        rentalSystem.getCustomers().add(customer);
        
        // Setup: Create Tape T001
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T001");
        rentalSystem.getTapes().add(tape);
        
        // Setup: Create VideoRental with return_date=null (unreturned)
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.of(2025, 1, 1));
        rental.setDueDate(LocalDate.of(2025, 1, 5));
        rental.setReturnDate(null); // Not returned
        
        customer.getRentals().add(rental);
        rentalSystem.getRentals().add(rental);
        
        // Execute: Call listUnreturnedTapes for customer C003
        List<String> result = rentalSystem.listUnreturnedTapes("C003");
        
        // Verify: Expected output is list containing T001
        assertEquals("List should contain exactly one element", 1, result.size());
        assertTrue("List should contain T001", result.contains("T001"));
    }
    
    @Test
    public void testCase4_mixedReturnedUnreturned() {
        // Setup: Create Customer C004
        Customer customer = new Customer();
        customer.setAccountId("C004");
        rentalSystem.getCustomers().add(customer);
        
        // Setup: Create Tapes T001 and T002
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T002");
        rentalSystem.getTapes().add(tape1);
        rentalSystem.getTapes().add(tape2);
        
        // Setup: Create VideoRental for T001 with return date (returned)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setTape(tape1);
        rental1.setRentalDate(LocalDate.of(2025, 1, 1));
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 1)); // Returned
        
        // Setup: Create VideoRental for T002 with return_date=null (unreturned)
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setTape(tape2);
        rental2.setRentalDate(LocalDate.of(2025, 1, 2));
        rental2.setDueDate(LocalDate.of(2025, 1, 6));
        rental2.setReturnDate(null); // Not returned
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        rentalSystem.getRentals().add(rental1);
        rentalSystem.getRentals().add(rental2);
        
        // Execute: Call listUnreturnedTapes for customer C004
        List<String> result = rentalSystem.listUnreturnedTapes("C004");
        
        // Verify: Expected output is list containing T002
        assertEquals("List should contain exactly one element", 1, result.size());
        assertTrue("List should contain T002", result.contains("T002"));
        assertFalse("List should not contain T001", result.contains("T001"));
    }
    
    @Test
    public void testCase5_multipleUnreturnedTapes() {
        // Setup: Create Customer C005
        Customer customer = new Customer();
        customer.setAccountId("C005");
        rentalSystem.getCustomers().add(customer);
        
        // Setup: Create Tapes T001 and T002
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T002");
        rentalSystem.getTapes().add(tape1);
        rentalSystem.getTapes().add(tape2);
        
        // Setup: Create VideoRental for T001 with return_date=null (unreturned)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setTape(tape1);
        rental1.setRentalDate(LocalDate.of(2025, 1, 1));
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(null); // Not returned
        
        // Setup: Create VideoRental for T002 with return_date=null (unreturned)
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setTape(tape2);
        rental2.setRentalDate(LocalDate.of(2025, 1, 2));
        rental2.setDueDate(LocalDate.of(2025, 1, 6));
        rental2.setReturnDate(null); // Not returned
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        rentalSystem.getRentals().add(rental1);
        rentalSystem.getRentals().add(rental2);
        
        // Execute: Call listUnreturnedTapes for customer C005
        List<String> result = rentalSystem.listUnreturnedTapes("C005");
        
        // Verify: Expected output is list containing T001 and T002
        assertEquals("List should contain exactly two elements", 2, result.size());
        assertTrue("List should contain T001", result.contains("T001"));
        assertTrue("List should contain T002", result.contains("T002"));
    }
}