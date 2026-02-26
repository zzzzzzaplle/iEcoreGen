import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private RentalSystem rentalSystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        rentalSystem = new RentalSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_TapeIsAvailable() {
        // Setup: Create Tape with id="T001", No active rentals for T001
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T001");
        tape.setTitle("Test Tape 1");
        
        List<VideoTape> inventory = new ArrayList<>();
        inventory.add(tape);
        rentalSystem.setInventory(inventory);
        
        // Input
        String tapeId = "T001";
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Execute and Verify
        boolean result = rentalSystem.isTapeAvailable(tapeId, currentDate);
        assertTrue("Tape T001 should be available", result);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() {
        // Setup: Create Tape with id="T002", Create Customer C001
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T002");
        tape.setTitle("Test Tape 2");
        
        Customer customer = new Customer();
        customer.setAccountNumber(1);
        customer.setName("Customer C001");
        
        List<VideoTape> inventory = new ArrayList<>();
        inventory.add(tape);
        rentalSystem.setInventory(inventory);
        
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        rentalSystem.setCustomers(customers);
        
        // C001 rents T002 with rental date="2024-12-28", due_date="2025-01-05", return_date=null
        RentalTransaction rental = new RentalTransaction();
        rental.setTransactionId(1);
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.parse("2024-12-28", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(null);
        rental.setBaseFee(8.0); // 8 days rental
        
        List<RentalTransaction> transactions = new ArrayList<>();
        transactions.add(rental);
        rentalSystem.setAllTransactions(transactions);
        
        customer.setRentals(new ArrayList<>());
        customer.getRentals().add(rental);
        
        // Input
        String tapeId = "T002";
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Execute and Verify
        boolean result = rentalSystem.isTapeAvailable(tapeId, currentDate);
        assertFalse("Tape T002 should not be available (rented out)", result);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Setup: Create Tape with id="T003", Create Customer C002
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T003");
        tape.setTitle("Test Tape 3");
        
        Customer customer = new Customer();
        customer.setAccountNumber(2);
        customer.setName("Customer C002");
        
        List<VideoTape> inventory = new ArrayList<>();
        inventory.add(tape);
        rentalSystem.setInventory(inventory);
        
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        rentalSystem.setCustomers(customers);
        
        // C002 rents T003 with rental date="2024-12-25", due_date="2024-12-30", return_date="2024-12-31"
        RentalTransaction rental = new RentalTransaction();
        rental.setTransactionId(2);
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.parse("2024-12-25", formatter));
        rental.setDueDate(LocalDate.parse("2024-12-30", formatter));
        rental.setReturnDate(LocalDate.parse("2024-12-31", formatter));
        rental.setBaseFee(5.0); // 5 days rental
        
        List<RentalTransaction> transactions = new ArrayList<>();
        transactions.add(rental);
        rentalSystem.setAllTransactions(transactions);
        
        customer.setRentals(new ArrayList<>());
        customer.getRentals().add(rental);
        
        // Input
        String tapeId = "T003";
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Execute and Verify
        boolean result = rentalSystem.isTapeAvailable(tapeId, currentDate);
        assertTrue("Tape T003 should be available (was returned)", result);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Setup: Create Tape with id="T004", Create Customer C003
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T004");
        tape.setTitle("Test Tape 4");
        
        Customer customer = new Customer();
        customer.setAccountNumber(3);
        customer.setName("Customer C003");
        
        List<VideoTape> inventory = new ArrayList<>();
        inventory.add(tape);
        rentalSystem.setInventory(inventory);
        
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        rentalSystem.setCustomers(customers);
        
        // C003 rents T004 with rental date="2024-12-28", due_date="2025-01-01", return_date=null
        RentalTransaction rental = new RentalTransaction();
        rental.setTransactionId(3);
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.parse("2024-12-28", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-01", formatter));
        rental.setReturnDate(null);
        rental.setBaseFee(4.0); // 4 days rental
        
        List<RentalTransaction> transactions = new ArrayList<>();
        transactions.add(rental);
        rentalSystem.setAllTransactions(transactions);
        
        customer.setRentals(new ArrayList<>());
        customer.getRentals().add(rental);
        
        // Input
        String tapeId = "T004";
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        
        // Execute and Verify
        boolean result = rentalSystem.isTapeAvailable(tapeId, currentDate);
        assertFalse("Tape T004 should not be available (overdue rental)", result);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Setup: Create Tape with id="T005", Create Customer C004, C005
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T005");
        tape.setTitle("Test Tape 5");
        
        Customer customer1 = new Customer();
        customer1.setAccountNumber(4);
        customer1.setName("Customer C004");
        
        Customer customer2 = new Customer();
        customer2.setAccountNumber(5);
        customer2.setName("Customer C005");
        
        List<VideoTape> inventory = new ArrayList<>();
        inventory.add(tape);
        rentalSystem.setInventory(inventory);
        
        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);
        rentalSystem.setCustomers(customers);
        
        // First rental: C004 rents T005 with rental date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01"
        RentalTransaction rental1 = new RentalTransaction();
        rental1.setTransactionId(4);
        rental1.setCustomer(customer1);
        rental1.setTape(tape);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setBaseFee(4.0); // 4 days rental
        
        // Second rental: C005 rents T005 with rental date="2025-01-06", due_date="2025-01-15", return_date=null
        RentalTransaction rental2 = new RentalTransaction();
        rental2.setTransactionId(5);
        rental2.setCustomer(customer2);
        rental2.setTape(tape);
        rental2.setRentalDate(LocalDate.parse("2025-01-06", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(null);
        rental2.setBaseFee(9.0); // 9 days rental
        
        List<RentalTransaction> transactions = new ArrayList<>();
        transactions.add(rental1);
        transactions.add(rental2);
        rentalSystem.setAllTransactions(transactions);
        
        customer1.setRentals(new ArrayList<>());
        customer1.getRentals().add(rental1);
        
        customer2.setRentals(new ArrayList<>());
        customer2.getRentals().add(rental2);
        
        // Input
        String tapeId = "T005";
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        
        // Execute and Verify - Second rental should make tape unavailable
        boolean result = rentalSystem.isTapeAvailable(tapeId, currentDate);
        assertFalse("Tape T005 should not be available (currently rented by C005)", result);
    }
}