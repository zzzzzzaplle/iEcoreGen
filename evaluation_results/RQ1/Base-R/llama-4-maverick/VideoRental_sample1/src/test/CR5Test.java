import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR5Test {
    
    private VideoRentalSystem system;
    private Customer customer;
    private VideoTape tape1;
    private VideoTape tape2;
    
    @Before
    public void setUp() {
        system = new VideoRentalSystem();
    }
    
    @Test
    public void testCase1_NoRentalsExist() {
        // Setup: Create Customer C001 with empty rentals list
        customer = new Customer();
        customer.setId("C001");
        customer.setRentalTransactions(new ArrayList<>());
        
        // Execute: Get unreturned tapes for customer C001
        List<String> result = new ArrayList<>();
        // Since the customer has no rental transactions, we need to check each transaction
        for (RentalTransaction transaction : customer.getRentalTransactions()) {
            result.addAll(transaction.listUnreturnedTapes());
        }
        
        // Verify: Expected empty list, no active rentals
        assertTrue("List should be empty when customer has no rentals", result.isEmpty());
    }
    
    @Test
    public void testCase2_AllTapesReturned() {
        // Setup: Create Customer C002
        customer = new Customer();
        customer.setId("C002");
        customer.setRentalTransactions(new ArrayList<>());
        
        // Create Tape T001
        tape1 = new VideoTape();
        tape1.setBarCodeId("T001");
        
        // Create VideoRental with return date set (returned)
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentalDate("2025-01-01");
        
        Rental rental = new Rental();
        rental.setVideoTape(tape1);
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-05");
        rental.setReturnDate("2025-01-01"); // Tape is returned
        
        transaction.getRentals().add(rental);
        customer.getRentalTransactions().add(transaction);
        
        // Execute: Get unreturned tapes for customer C002
        List<String> result = transaction.listUnreturnedTapes();
        
        // Verify: Expected empty list, all rentals returned
        assertTrue("List should be empty when all tapes are returned", result.isEmpty());
    }
    
    @Test
    public void testCase3_OneUnreturnedTape() {
        // Setup: Create Customer C003
        customer = new Customer();
        customer.setId("C003");
        customer.setRentalTransactions(new ArrayList<>());
        
        // Create Tape T001
        tape1 = new VideoTape();
        tape1.setBarCodeId("T001");
        
        // Create VideoRental with return_date=null (unreturned)
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentalDate("2025-01-01");
        
        Rental rental = new Rental();
        rental.setVideoTape(tape1);
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-05");
        rental.setReturnDate(null); // Tape is not returned
        
        transaction.getRentals().add(rental);
        customer.getRentalTransactions().add(transaction);
        
        // Execute: Get unreturned tapes for customer C003
        List<String> result = transaction.listUnreturnedTapes();
        
        // Verify: Expected list containing T001
        assertEquals("List should contain exactly one tape", 1, result.size());
        assertTrue("List should contain T001", result.contains("T001"));
    }
    
    @Test
    public void testCase4_MixedReturnedUnreturned() {
        // Setup: Create Customer C004
        customer = new Customer();
        customer.setId("C004");
        customer.setRentalTransactions(new ArrayList<>());
        
        // Create Tapes T001 and T002
        tape1 = new VideoTape();
        tape1.setBarCodeId("T001");
        tape2 = new VideoTape();
        tape2.setBarCodeId("T002");
        
        // Create RentalTransaction for customer
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        
        // Create VideoRental for T001 (returned)
        Rental rental1 = new Rental();
        rental1.setVideoTape(tape1);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-01"); // Tape is returned
        
        // Create VideoRental for T002 (unreturned)
        Rental rental2 = new Rental();
        rental2.setVideoTape(tape2);
        rental2.setRentalDate("2025-01-02");
        rental2.setDueDate("2025-01-06");
        rental2.setReturnDate(null); // Tape is not returned
        
        transaction.getRentals().add(rental1);
        transaction.getRentals().add(rental2);
        customer.getRentalTransactions().add(transaction);
        
        // Execute: Get unreturned tapes for customer C004
        List<String> result = transaction.listUnreturnedTapes();
        
        // Verify: Expected list containing T002 only
        assertEquals("List should contain exactly one tape", 1, result.size());
        assertTrue("List should contain T002", result.contains("T002"));
        assertFalse("List should not contain T001", result.contains("T001"));
    }
    
    @Test
    public void testCase5_MultipleUnreturnedTapes() {
        // Setup: Create Customer C005
        customer = new Customer();
        customer.setId("C005");
        customer.setRentalTransactions(new ArrayList<>());
        
        // Create Tapes T001 and T002
        tape1 = new VideoTape();
        tape1.setBarCodeId("T001");
        tape2 = new VideoTape();
        tape2.setBarCodeId("T002");
        
        // Create RentalTransaction for customer
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        
        // Create VideoRental for T001 (unreturned)
        Rental rental1 = new Rental();
        rental1.setVideoTape(tape1);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate(null); // Tape is not returned
        
        // Create VideoRental for T002 (unreturned)
        Rental rental2 = new Rental();
        rental2.setVideoTape(tape2);
        rental2.setRentalDate("2025-01-02");
        rental2.setDueDate("2025-01-06");
        rental2.setReturnDate(null); // Tape is not returned
        
        transaction.getRentals().add(rental1);
        transaction.getRentals().add(rental2);
        customer.getRentalTransactions().add(transaction);
        
        // Execute: Get unreturned tapes for customer C005
        List<String> result = transaction.listUnreturnedTapes();
        
        // Verify: Expected list containing T001 and T002
        assertEquals("List should contain exactly two tapes", 2, result.size());
        assertTrue("List should contain T001", result.contains("T001"));
        assertTrue("List should contain T002", result.contains("T002"));
    }
}