import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private VideoRentalSystem system;
    
    @Before
    public void setUp() {
        system = new VideoRentalSystem();
    }
    
    @Test
    public void testCase1_tapeIsAvailable() {
        // Setup: Create Tape with id="T001", no active rentals
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T001");
        tape.setAvailable(true);
        
        List<VideoTape> inventory = new ArrayList<>();
        inventory.add(tape);
        system.setInventory(inventory);
        
        // Input: tape_id="T001", current_date="2025-01-01"
        boolean result = system.isTapeAvailable("T001", "2025-01-01");
        
        // Expected Output: True
        assertTrue("Tape T001 should be available when no active rentals exist", result);
    }
    
    @Test
    public void testCase2_tapeIsRentedOut() {
        // Setup: Create Tape with id="T002", Customer C001, and active rental
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T002");
        tape.setAvailable(true);
        
        Customer customer = new Customer();
        customer.setAccountNumber("C001");
        
        Rental rental = new Rental();
        rental.setTape(tape);
        rental.setReturnDate(null); // unreturned
        rental.setRentalDate("2024-12-28");
        rental.setDueDate("2025-01-05");
        
        List<Rental> customerRentals = new ArrayList<>();
        customerRentals.add(rental);
        customer.setRentals(customerRentals);
        
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        List<Rental> transactionRentals = new ArrayList<>();
        transactionRentals.add(rental);
        transaction.setRentals(transactionRentals);
        
        List<VideoTape> inventory = new ArrayList<>();
        inventory.add(tape);
        system.setInventory(inventory);
        
        List<RentalTransaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        system.setTransactions(transactions);
        
        // Input: tape_id="T002", current_date="2025-01-01"
        boolean result = system.isTapeAvailable("T002", "2025-01-01");
        
        // Expected Output: False
        assertFalse("Tape T002 should be unavailable when it has an active rental", result);
    }
    
    @Test
    public void testCase3_tapeWasRentedButReturned() {
        // Setup: Create Tape with id="T003", Customer C002, and returned rental
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T003");
        tape.setAvailable(true);
        
        Customer customer = new Customer();
        customer.setAccountNumber("C002");
        
        Rental rental = new Rental();
        rental.setTape(tape);
        rental.setReturnDate("2024-12-31"); // returned
        rental.setRentalDate("2024-12-25");
        rental.setDueDate("2024-12-30");
        
        List<Rental> customerRentals = new ArrayList<>();
        customerRentals.add(rental);
        customer.setRentals(customerRentals);
        
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        List<Rental> transactionRentals = new ArrayList<>();
        transactionRentals.add(rental);
        transaction.setRentals(transactionRentals);
        
        List<VideoTape> inventory = new ArrayList<>();
        inventory.add(tape);
        system.setInventory(inventory);
        
        List<RentalTransaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        system.setTransactions(transactions);
        
        // Input: tape_id="T003", current_date="2025-01-01"
        boolean result = system.isTapeAvailable("T003", "2025-01-01");
        
        // Expected Output: True
        assertTrue("Tape T003 should be available when it was returned before current date", result);
    }
    
    @Test
    public void testCase4_tapeExistsButHasOverdueRental() {
        // Setup: Create Tape with id="T004", Customer C003, and overdue rental
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T004");
        tape.setAvailable(true);
        
        Customer customer = new Customer();
        customer.setAccountNumber("C003");
        
        Rental rental = new Rental();
        rental.setTape(tape);
        rental.setReturnDate(null); // unreturned (overdue)
        rental.setRentalDate("2024-12-28");
        rental.setDueDate("2025-01-01");
        
        List<Rental> customerRentals = new ArrayList<>();
        customerRentals.add(rental);
        customer.setRentals(customerRentals);
        
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        List<Rental> transactionRentals = new ArrayList<>();
        transactionRentals.add(rental);
        transaction.setRentals(transactionRentals);
        
        List<VideoTape> inventory = new ArrayList<>();
        inventory.add(tape);
        system.setInventory(inventory);
        
        List<RentalTransaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        system.setTransactions(transactions);
        
        // Input: tape_id="T004", current_date="2025-01-10"
        boolean result = system.isTapeAvailable("T004", "2025-01-10");
        
        // Expected Output: False
        assertFalse("Tape T004 should be unavailable when it has an overdue rental", result);
    }
    
    @Test
    public void testCase5_tapeWasRentedButReturnedByMultipleCustomers() {
        // Setup: Create Tape with id="T005", Customers C004 and C005, with two rentals
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T005");
        tape.setAvailable(true);
        
        // First customer (C004) - returned rental
        Customer customer1 = new Customer();
        customer1.setAccountNumber("C004");
        
        Rental rental1 = new Rental();
        rental1.setTape(tape);
        rental1.setReturnDate("2025-01-01"); // returned
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        
        List<Rental> customer1Rentals = new ArrayList<>();
        customer1Rentals.add(rental1);
        customer1.setRentals(customer1Rentals);
        
        RentalTransaction transaction1 = new RentalTransaction();
        transaction1.setCustomer(customer1);
        List<Rental> transaction1Rentals = new ArrayList<>();
        transaction1Rentals.add(rental1);
        transaction1.setRentals(transaction1Rentals);
        
        // Second customer (C005) - active rental
        Customer customer2 = new Customer();
        customer2.setAccountNumber("C005");
        
        Rental rental2 = new Rental();
        rental2.setTape(tape);
        rental2.setReturnDate(null); // unreturned
        rental2.setRentalDate("2025-01-06");
        rental2.setDueDate("2025-01-15");
        
        List<Rental> customer2Rentals = new ArrayList<>();
        customer2Rentals.add(rental2);
        customer2.setRentals(customer2Rentals);
        
        RentalTransaction transaction2 = new RentalTransaction();
        transaction2.setCustomer(customer2);
        List<Rental> transaction2Rentals = new ArrayList<>();
        transaction2Rentals.add(rental2);
        transaction2.setRentals(transaction2Rentals);
        
        List<VideoTape> inventory = new ArrayList<>();
        inventory.add(tape);
        system.setInventory(inventory);
        
        List<RentalTransaction> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);
        system.setTransactions(transactions);
        
        // Input: tape_id="T005", current_date="2025-01-10"
        boolean result = system.isTapeAvailable("T005", "2025-01-10");
        
        // Expected Output: False (because second rental is still active)
        assertFalse("Tape T005 should be unavailable when it has an active rental from second customer", result);
    }
}