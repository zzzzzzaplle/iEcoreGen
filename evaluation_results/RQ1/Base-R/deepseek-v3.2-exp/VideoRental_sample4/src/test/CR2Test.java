import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private VideoRentalSystem rentalSystem;
    
    @Before
    public void setUp() {
        rentalSystem = new VideoRentalSystem();
    }
    
    @Test
    public void testCase1_TapeIsAvailable() {
        // Setup: Create Tape with id="T001", no active rentals for T001
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("T001");
        tape.setTitle("Test Movie 1");
        tape.setAvailable(true);
        
        List<VideoTape> inventory = new ArrayList<>();
        inventory.add(tape);
        rentalSystem.setVideoInventory(inventory);
        rentalSystem.setAllRentalTransactions(new ArrayList<>());
        
        // Test: Check availability for tape T001 on current_date="2025-01-01"
        boolean result = rentalSystem.checkTapeAvailability("T001", "2025-01-01");
        
        // Verify: Tape should be available
        assertTrue("Tape T001 should be available when no active rentals exist", result);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() {
        // Setup: Create Tape with id="T002", Customer C001, and active rental
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("T002");
        tape.setTitle("Test Movie 2");
        
        Customer customer = new Customer();
        customer.setAccountNumber("C001");
        customer.setName("Customer One");
        
        RentalTransaction rental = new RentalTransaction();
        rental.setTransactionId("TXN001");
        rental.setCustomer(customer);
        rental.setVideoTape(tape);
        rental.setRentalDate("2024-12-28");
        rental.setDueDate("2025-01-05");
        rental.setReturnDate(null); // Unreturned
        
        List<RentalTransaction> transactions = new ArrayList<>();
        transactions.add(rental);
        rentalSystem.setAllRentalTransactions(transactions);
        
        List<VideoTape> inventory = new ArrayList<>();
        inventory.add(tape);
        rentalSystem.setVideoInventory(inventory);
        
        // Test: Check availability for tape T002 on current_date="2025-01-01"
        boolean result = rentalSystem.checkTapeAvailability("T002", "2025-01-01");
        
        // Verify: Tape should be unavailable (rented out)
        assertFalse("Tape T002 should be unavailable when actively rented", result);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Setup: Create Tape with id="T003", Customer C002, with returned rental
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("T003");
        tape.setTitle("Test Movie 3");
        
        Customer customer = new Customer();
        customer.setAccountNumber("C002");
        customer.setName("Customer Two");
        
        RentalTransaction rental = new RentalTransaction();
        rental.setTransactionId("TXN002");
        rental.setCustomer(customer);
        rental.setVideoTape(tape);
        rental.setRentalDate("2024-12-25");
        rental.setDueDate("2024-12-30");
        rental.setReturnDate("2024-12-31"); // Returned
        
        List<RentalTransaction> transactions = new ArrayList<>();
        transactions.add(rental);
        rentalSystem.setAllRentalTransactions(transactions);
        
        List<VideoTape> inventory = new ArrayList<>();
        inventory.add(tape);
        rentalSystem.setVideoInventory(inventory);
        
        // Test: Check availability for tape T003 on current_date="2025-01-01"
        boolean result = rentalSystem.checkTapeAvailability("T003", "2025-01-01");
        
        // Verify: Tape should be available (previously returned)
        assertTrue("Tape T003 should be available when previously returned", result);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Setup: Create Tape with id="T004", Customer C003, with overdue unreturned rental
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("T004");
        tape.setTitle("Test Movie 4");
        
        Customer customer = new Customer();
        customer.setAccountNumber("C003");
        customer.setName("Customer Three");
        
        RentalTransaction rental = new RentalTransaction();
        rental.setTransactionId("TXN003");
        rental.setCustomer(customer);
        rental.setVideoTape(tape);
        rental.setRentalDate("2024-12-28");
        rental.setDueDate("2025-01-01");
        rental.setReturnDate(null); // Unreturned and overdue
        
        List<RentalTransaction> transactions = new ArrayList<>();
        transactions.add(rental);
        rentalSystem.setAllRentalTransactions(transactions);
        
        List<VideoTape> inventory = new ArrayList<>();
        inventory.add(tape);
        rentalSystem.setVideoInventory(inventory);
        
        // Test: Check availability for tape T004 on current_date="2025-01-10"
        boolean result = rentalSystem.checkTapeAvailability("T004", "2025-01-10");
        
        // Verify: Tape should be unavailable (overdue and unreturned)
        assertFalse("Tape T004 should be unavailable when overdue and unreturned", result);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Setup: Create Tape with id="T005", Customers C004 and C005 with multiple rentals
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("T005");
        tape.setTitle("Test Movie 5");
        
        Customer customer1 = new Customer();
        customer1.setAccountNumber("C004");
        customer1.setName("Customer Four");
        
        Customer customer2 = new Customer();
        customer2.setAccountNumber("C005");
        customer2.setName("Customer Five");
        
        // First rental: returned early
        RentalTransaction rental1 = new RentalTransaction();
        rental1.setTransactionId("TXN004");
        rental1.setCustomer(customer1);
        rental1.setVideoTape(tape);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-01"); // Returned same day
        
        // Second rental: currently active
        RentalTransaction rental2 = new RentalTransaction();
        rental2.setTransactionId("TXN005");
        rental2.setCustomer(customer2);
        rental2.setVideoTape(tape);
        rental2.setRentalDate("2025-01-06");
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate(null); // Currently rented
        
        List<RentalTransaction> transactions = new ArrayList<>();
        transactions.add(rental1);
        transactions.add(rental2);
        rentalSystem.setAllRentalTransactions(transactions);
        
        List<VideoTape> inventory = new ArrayList<>();
        inventory.add(tape);
        rentalSystem.setVideoInventory(inventory);
        
        // Test: Check availability for tape T005 on current_date="2025-01-10"
        boolean result = rentalSystem.checkTapeAvailability("T005", "2025-01-10");
        
        // Verify: Tape should be unavailable due to second active rental
        assertFalse("Tape T005 should be unavailable due to active second rental", result);
    }
}