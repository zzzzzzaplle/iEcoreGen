import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private RentalSystem rentalSystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        rentalSystem = new RentalSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Setup
        Customer customer = new Customer();
        customer.setAccountNumber(1); // C001 represented as account number 1
        customer.setName("Customer C001");
        
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        tape1.setTitle("Tape 1");
        
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T002");
        tape2.setTitle("Tape 2");
        
        // Create rental transactions
        RentalTransaction rental1 = new RentalTransaction();
        rental1.setTransactionId(1);
        rental1.setCustomer(customer);
        rental1.setTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-03", formatter));
        rental1.setBaseFee(4.0); // 4 days rental (Jan 1-3 inclusive) × $1 = $4
        
        RentalTransaction rental2 = new RentalTransaction();
        rental2.setTransactionId(2);
        rental2.setCustomer(customer);
        rental2.setTape(tape2);
        rental2.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(LocalDate.parse("2025-01-12", formatter));
        rental2.setBaseFee(11.0); // 11 days rental (Jan 1-12 inclusive) × $1 = $11
        
        // Add rentals to customer
        List<RentalTransaction> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Add customer and tapes to system
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        rentalSystem.setCustomers(customers);
        
        List<VideoTape> inventory = new ArrayList<>();
        inventory.add(tape1);
        inventory.add(tape2);
        rentalSystem.setInventory(inventory);
        
        // Test
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        double result = rentalSystem.calculateTotalPriceForCustomer(1);
        
        // Verify
        assertEquals(13.0, result, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Setup
        Customer customer = new Customer();
        customer.setAccountNumber(2); // C002 represented as account number 2
        customer.setName("Customer C002");
        
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T003");
        tape.setTitle("Tape 3");
        
        // Create rental transaction
        RentalTransaction rental = new RentalTransaction();
        rental.setTransactionId(1);
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(LocalDate.parse("2025-01-12", formatter));
        rental.setBaseFee(11.0); // 11 days rental (Jan 1-12 inclusive) × $1 = $11
        
        // Add rental to customer
        List<RentalTransaction> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Add customer and tape to system
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        rentalSystem.setCustomers(customers);
        
        List<VideoTape> inventory = new ArrayList<>();
        inventory.add(tape);
        rentalSystem.setInventory(inventory);
        
        // Test
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        double result = rentalSystem.calculateTotalPriceForCustomer(2);
        
        // Verify
        assertEquals(14.50, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Setup
        Customer customer = new Customer();
        customer.setAccountNumber(3); // C003 represented as account number 3
        customer.setName("Customer C003");
        
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T004");
        tape1.setTitle("Tape 4");
        
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T005");
        tape2.setTitle("Tape 5");
        
        // Create rental transactions
        RentalTransaction rental1 = new RentalTransaction();
        rental1.setTransactionId(1);
        rental1.setCustomer(customer);
        rental1.setTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-09", formatter));
        rental1.setBaseFee(8.0); // 8 days rental (Jan 1-9 inclusive) × $1 = $8
        
        RentalTransaction rental2 = new RentalTransaction();
        rental2.setTransactionId(2);
        rental2.setCustomer(customer);
        rental2.setTape(tape2);
        rental2.setRentalDate(LocalDate.parse("2025-01-10", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(LocalDate.parse("2025-01-18", formatter));
        rental2.setBaseFee(8.0); // 8 days rental (Jan 10-18 inclusive) × $1 = $8
        
        // Add rentals to customer
        List<RentalTransaction> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Add customer and tapes to system
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        rentalSystem.setCustomers(customers);
        
        List<VideoTape> inventory = new ArrayList<>();
        inventory.add(tape1);
        inventory.add(tape2);
        rentalSystem.setInventory(inventory);
        
        // Test
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        double result = rentalSystem.calculateTotalPriceForCustomer(3);
        
        // Verify
        assertEquals(19.50, result, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Setup
        Customer customer = new Customer();
        customer.setAccountNumber(4); // C004 represented as account number 4
        customer.setName("Customer C004");
        
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T006");
        tape1.setTitle("Tape 6");
        
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T007");
        tape2.setTitle("Tape 7");
        
        // Create rental transactions
        RentalTransaction rental1 = new RentalTransaction();
        rental1.setTransactionId(1);
        rental1.setCustomer(customer);
        rental1.setTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-07", formatter));
        rental1.setBaseFee(6.0); // 6 days rental (Jan 1-7 inclusive) × $1 = $6
        
        RentalTransaction rental2 = new RentalTransaction();
        rental2.setTransactionId(2);
        rental2.setCustomer(customer);
        rental2.setTape(tape2);
        rental2.setRentalDate(LocalDate.parse("2025-01-10", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(LocalDate.parse("2025-01-14", formatter));
        rental2.setBaseFee(4.0); // 4 days rental (Jan 10-14 inclusive) × $1 = $4
        
        // Add rentals to customer
        List<RentalTransaction> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Add customer and tapes to system
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        rentalSystem.setCustomers(customers);
        
        List<VideoTape> inventory = new ArrayList<>();
        inventory.add(tape1);
        inventory.add(tape2);
        rentalSystem.setInventory(inventory);
        
        // Test
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        double result = rentalSystem.calculateTotalPriceForCustomer(4);
        
        // Verify
        assertEquals(11.00, result, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Setup
        Customer customer = new Customer();
        customer.setAccountNumber(6); // C006 represented as account number 6
        customer.setName("Customer C006");
        
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T008");
        tape.setTitle("Tape 8");
        
        // Create rental transaction (unreturned)
        RentalTransaction rental = new RentalTransaction();
        rental.setTransactionId(1);
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(null); // Not returned
        rental.setBaseFee(9.0); // 9 days rental (Jan 1-10 inclusive) × $1 = $9
        
        // Add rental to customer
        List<RentalTransaction> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Add customer and tape to system
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        rentalSystem.setCustomers(customers);
        
        List<VideoTape> inventory = new ArrayList<>();
        inventory.add(tape);
        rentalSystem.setInventory(inventory);
        
        // Test - Note: System uses LocalDate.now() internally, so we need to set up the rental transaction list directly
        // Since calculateTotalPriceForCustomer uses LocalDate.now(), we'll test the RentalTransaction directly
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        double totalPrice = rental.calculateTotalPrice(currentDate);
        
        // Verify
        assertEquals(11.50, totalPrice, 0.001);
    }
}