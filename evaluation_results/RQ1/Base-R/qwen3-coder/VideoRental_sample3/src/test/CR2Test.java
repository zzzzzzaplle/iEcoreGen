import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private RentalSystem rentalSystem;
    
    @Before
    public void setUp() {
        rentalSystem = new RentalSystem();
    }
    
    @Test
    public void testCase1_TapeIsAvailable() {
        // Test Case 1: "Tape is available"
        // Input: tape_id="T001", current_date="2025-01-01"
        // Setup: 1. Create Tape with id="T001", 2. No active rentals for T001
        // Expected Output: True
        
        // Create tape T001 and add to inventory
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T001");
        tape.setTitle("Test Tape 1");
        tape.setAvailable(true);
        
        List<VideoTape> inventory = new ArrayList<>();
        inventory.add(tape);
        rentalSystem.setInventory(inventory);
        
        // Set empty customers list (no rentals)
        rentalSystem.setCustomers(new ArrayList<>());
        
        // Test tape availability
        boolean result = rentalSystem.checkTapeAvailability("T001");
        assertTrue("Tape T001 should be available", result);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() {
        // Test Case 2: "Tape is rented out"
        // Input: tape_id="T002", current_date="2025-01-01"
        // Setup: 1. Create Tape with id="T002". Create Customer C001.
        //        2. C001 rents T002 with rental date="2024-12-28", due_date="2025-01-05", return_date=null (unreturned)
        // Expected Output: False
        
        // Create tape T002 and add to inventory
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T002");
        tape.setTitle("Test Tape 2");
        tape.setAvailable(true);
        
        List<VideoTape> inventory = new ArrayList<>();
        inventory.add(tape);
        rentalSystem.setInventory(inventory);
        
        // Create customer C001 with active rental for T002
        Customer customer = new Customer();
        customer.setAccountId("C001");
        customer.setIdCardBarcode("ID001");
        
        Rental rental = new Rental();
        rental.setTapeId("T002");
        rental.setRentalDate(LocalDate.of(2024, 12, 28));
        rental.setDueDate(LocalDate.of(2025, 1, 5));
        rental.setReturnDate(null); // Unreturned
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        rentalSystem.setCustomers(customers);
        
        // Test tape availability - should be false since it's rented out
        boolean result = rentalSystem.checkTapeAvailability("T002");
        assertFalse("Tape T002 should not be available (rented out)", result);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Test Case 3: "Tape was rented but returned"
        // Input: tape_id="T003", current_date="2025-01-01"
        // Setup: 1. Create Tape with id="T003". Create Customer C002.
        //        2. C002 rents T003 with rental date="2024-12-25", due_date="2024-12-30", return_date="2024-12-31"
        // Expected Output: True
        
        // Create tape T003 and add to inventory
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T003");
        tape.setTitle("Test Tape 3");
        tape.setAvailable(true);
        
        List<VideoTape> inventory = new ArrayList<>();
        inventory.add(tape);
        rentalSystem.setInventory(inventory);
        
        // Create customer C002 with returned rental for T003
        Customer customer = new Customer();
        customer.setAccountId("C002");
        customer.setIdCardBarcode("ID002");
        
        Rental rental = new Rental();
        rental.setTapeId("T003");
        rental.setRentalDate(LocalDate.of(2024, 12, 25));
        rental.setDueDate(LocalDate.of(2024, 12, 30));
        rental.setReturnDate(LocalDate.of(2024, 12, 31)); // Returned
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        rentalSystem.setCustomers(customers);
        
        // Test tape availability - should be true since it was returned
        boolean result = rentalSystem.checkTapeAvailability("T003");
        assertTrue("Tape T003 should be available (was returned)", result);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Test Case 4: "Tape exists but has overdue rental"
        // Input: tape_id="T004", current_date="2025-01-10"
        // Setup: 1. Create Tape with id="T004". Create Customer C003.
        //        2. C003 rents T004 with rental date="2024-12-28", due_date="2025-01-01", return_date=null (unreturned)
        // Expected Output: False
        
        // Create tape T004 and add to inventory
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T004");
        tape.setTitle("Test Tape 4");
        tape.setAvailable(true);
        
        List<VideoTape> inventory = new ArrayList<>();
        inventory.add(tape);
        rentalSystem.setInventory(inventory);
        
        // Create customer C003 with overdue rental for T004
        Customer customer = new Customer();
        customer.setAccountId("C003");
        customer.setIdCardBarcode("ID003");
        
        Rental rental = new Rental();
        rental.setTapeId("T004");
        rental.setRentalDate(LocalDate.of(2024, 12, 28));
        rental.setDueDate(LocalDate.of(2025, 1, 1));
        rental.setReturnDate(null); // Unreturned and overdue
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        rentalSystem.setCustomers(customers);
        
        // Test tape availability - should be false since it's overdue and unreturned
        boolean result = rentalSystem.checkTapeAvailability("T004");
        assertFalse("Tape T004 should not be available (overdue rental)", result);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Test Case 5: "Tape was rented but returned by multiple customers"
        // Input: tape_id="T005", current_date="2025-01-10"
        // Setup: 
        // 1. Create Tape with id="T005". Create Customer C004, C005.
        // 2. C004 rents T005 with rental date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01" → first rental
        // 3. C005 rents T005 with rental date="2025-01-06", due_date="2025-01-15", return_date=null → second rental
        // Expected Output: The first creation: True. The second creation: False.
        
        // Create tape T005 and add to inventory
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T005");
        tape.setTitle("Test Tape 5");
        tape.setAvailable(true);
        
        List<VideoTape> inventory = new ArrayList<>();
        inventory.add(tape);
        rentalSystem.setInventory(inventory);
        
        // Create customers C004 and C005
        Customer customer1 = new Customer();
        customer1.setAccountId("C004");
        customer1.setIdCardBarcode("ID004");
        
        Customer customer2 = new Customer();
        customer2.setAccountId("C005");
        customer2.setIdCardBarcode("ID005");
        
        // First rental: C004 rents and returns T005
        Rental rental1 = new Rental();
        rental1.setTapeId("T005");
        rental1.setRentalDate(LocalDate.of(2025, 1, 1));
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 1)); // Returned same day
        
        List<Rental> rentals1 = new ArrayList<>();
        rentals1.add(rental1);
        customer1.setRentals(rentals1);
        
        // Second rental: C005 rents T005 (currently active)
        Rental rental2 = new Rental();
        rental2.setTapeId("T005");
        rental2.setRentalDate(LocalDate.of(2025, 1, 6));
        rental2.setDueDate(LocalDate.of(2025, 1, 15));
        rental2.setReturnDate(null); // Currently rented out
        
        List<Rental> rentals2 = new ArrayList<>();
        rentals2.add(rental2);
        customer2.setRentals(rentals2);
        
        // Add both customers to system
        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);
        rentalSystem.setCustomers(customers);
        
        // Test tape availability - should be false since C005 currently has it rented
        boolean result = rentalSystem.checkTapeAvailability("T005");
        assertFalse("Tape T005 should not be available (currently rented by C005)", result);
    }
}