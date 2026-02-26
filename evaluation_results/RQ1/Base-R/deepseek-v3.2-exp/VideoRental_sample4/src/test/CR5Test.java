import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR5Test {
    
    private VideoRentalSystem system;
    
    @Before
    public void setUp() {
        system = new VideoRentalSystem();
    }
    
    @Test
    public void testCase1_NoRentalsExist() {
        // Setup: Create Customer C001 with empty rentals list
        Customer customer = new Customer();
        customer.setAccountNumber("C001");
        
        // Execute: List unreturned tapes for customer
        List<String> result = system.listUnreturnedTapes(customer);
        
        // Verify: Empty list, no active rentals
        assertTrue("Result should be empty list", result.isEmpty());
        assertEquals("List size should be 0", 0, result.size());
    }
    
    @Test
    public void testCase2_AllTapesReturned() {
        // Setup: Create Customer C002
        Customer customer = new Customer();
        customer.setAccountNumber("C002");
        
        // Setup: Create Tape T001
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("T001");
        
        // Setup: Create VideoRental with return date set (returned tape)
        RentalTransaction rental = new RentalTransaction();
        rental.setCustomer(customer);
        rental.setVideoTape(tape);
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-05");
        rental.setReturnDate("2025-01-01"); // Tape has been returned
        
        // Add rental to customer's transactions
        customer.getRentalTransactions().add(rental);
        
        // Execute: List unreturned tapes for customer
        List<String> result = system.listUnreturnedTapes(customer);
        
        // Verify: Empty list, all rentals returned
        assertTrue("Result should be empty list", result.isEmpty());
        assertEquals("List size should be 0", 0, result.size());
    }
    
    @Test
    public void testCase3_OneUnreturnedTape() {
        // Setup: Create Customer C003
        Customer customer = new Customer();
        customer.setAccountNumber("C003");
        
        // Setup: Create Tape T001
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("T001");
        
        // Setup: Create VideoRental with return_date=null (unreturned tape)
        RentalTransaction rental = new RentalTransaction();
        rental.setCustomer(customer);
        rental.setVideoTape(tape);
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-05");
        rental.setReturnDate(null); // Tape has NOT been returned
        
        // Add rental to customer's transactions
        customer.getRentalTransactions().add(rental);
        
        // Execute: List unreturned tapes for customer
        List<String> result = system.listUnreturnedTapes(customer);
        
        // Verify: List containing T001
        assertEquals("List should contain exactly 1 item", 1, result.size());
        assertTrue("List should contain T001", result.contains("T001"));
        assertEquals("First item should be T001", "T001", result.get(0));
    }
    
    @Test
    public void testCase4_MixedReturnedUnreturned() {
        // Setup: Create Customer C004
        Customer customer = new Customer();
        customer.setAccountNumber("C004");
        
        // Setup: Create Tapes T001 and T002
        VideoTape tape1 = new VideoTape();
        tape1.setBarCodeId("T001");
        
        VideoTape tape2 = new VideoTape();
        tape2.setBarCodeId("T002");
        
        // Setup: Create VideoRental for T001 with return date set (returned)
        RentalTransaction rental1 = new RentalTransaction();
        rental1.setCustomer(customer);
        rental1.setVideoTape(tape1);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-01"); // Tape has been returned
        
        // Setup: Create VideoRental for T002 with return_date=null (unreturned)
        RentalTransaction rental2 = new RentalTransaction();
        rental2.setCustomer(customer);
        rental2.setVideoTape(tape2);
        rental2.setRentalDate("2025-01-02");
        rental2.setDueDate("2025-01-06");
        rental2.setReturnDate(null); // Tape has NOT been returned
        
        // Add rentals to customer's transactions
        customer.getRentalTransactions().add(rental1);
        customer.getRentalTransactions().add(rental2);
        
        // Execute: List unreturned tapes for customer
        List<String> result = system.listUnreturnedTapes(customer);
        
        // Verify: List containing T002 only
        assertEquals("List should contain exactly 1 item", 1, result.size());
        assertTrue("List should contain T002", result.contains("T002"));
        assertFalse("List should not contain T001", result.contains("T001"));
        assertEquals("First item should be T002", "T002", result.get(0));
    }
    
    @Test
    public void testCase5_MultipleUnreturnedTapes() {
        // Setup: Create Customer C005
        Customer customer = new Customer();
        customer.setAccountNumber("C005");
        
        // Setup: Create Tapes T001 and T002
        VideoTape tape1 = new VideoTape();
        tape1.setBarCodeId("T001");
        
        VideoTape tape2 = new VideoTape();
        tape2.setBarCodeId("T002");
        
        // Setup: Create VideoRental for T001 with return_date=null (unreturned)
        RentalTransaction rental1 = new RentalTransaction();
        rental1.setCustomer(customer);
        rental1.setVideoTape(tape1);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate(null); // Tape has NOT been returned
        
        // Setup: Create VideoRental for T002 with return_date=null (unreturned)
        RentalTransaction rental2 = new RentalTransaction();
        rental2.setCustomer(customer);
        rental2.setVideoTape(tape2);
        rental2.setRentalDate("2025-01-02");
        rental2.setDueDate("2025-01-06");
        rental2.setReturnDate(null); // Tape has NOT been returned
        
        // Add rentals to customer's transactions
        customer.getRentalTransactions().add(rental1);
        customer.getRentalTransactions().add(rental2);
        
        // Execute: List unreturned tapes for customer
        List<String> result = system.listUnreturnedTapes(customer);
        
        // Verify: List containing T001 and T002
        assertEquals("List should contain exactly 2 items", 2, result.size());
        assertTrue("List should contain T001", result.contains("T001"));
        assertTrue("List should contain T002", result.contains("T002"));
    }
}