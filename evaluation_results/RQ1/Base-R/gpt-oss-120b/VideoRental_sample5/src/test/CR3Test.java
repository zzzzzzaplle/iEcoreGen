import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private RentalSystem rentalSystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        rentalSystem = new RentalSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_successfulRental() {
        // Setup: Create Customer C001 with 5 active rentals
        Customer customer = new Customer();
        customer.setAccountNumber(1);
        customer.setName("C001");
        
        // Create 5 active rentals for customer
        List<RentalTransaction> rentals = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            RentalTransaction rental = new RentalTransaction();
            rental.setTransactionId(i);
            rental.setCustomer(customer);
            
            VideoTape tape = new VideoTape();
            tape.setBarcodeId("T00" + i);
            tape.setTitle("Tape " + i);
            rental.setTape(tape);
            
            LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
            rental.setRentalDate(rentalDate);
            rental.setDueDate(rentalDate.plusDays(7));
            rental.setReturnDate(null); // unreturned
            rental.setBaseFee(7.0); // 7 days * $1
            
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        rentalSystem.getCustomers().add(customer);
        
        // Create available Tape T001
        VideoTape tapeT001 = new VideoTape();
        tapeT001.setBarcodeId("T001");
        tapeT001.setTitle("Available Tape");
        rentalSystem.getInventory().add(tapeT001);
        
        // Input: C001 rents tape "T001" with current_date="2025-01-01"
        boolean result = rentalSystem.addVideoTapeRental(1, "T001", 
            LocalDate.parse("2025-01-01", formatter), 7);
        
        // Expected Output: True
        assertTrue("Rental should be successful when all conditions are met", result);
    }
    
    @Test
    public void testCase2_customerHasMaxRentals() {
        // Setup: Create Customer C002 with 20 active rentals
        Customer customer = new Customer();
        customer.setAccountNumber(2);
        customer.setName("C002");
        
        // Create 20 active rentals for customer
        List<RentalTransaction> rentals = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            RentalTransaction rental = new RentalTransaction();
            rental.setTransactionId(i);
            rental.setCustomer(customer);
            
            VideoTape tape = new VideoTape();
            tape.setBarcodeId("T00" + i);
            tape.setTitle("Tape " + i);
            rental.setTape(tape);
            
            LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
            rental.setRentalDate(rentalDate);
            rental.setDueDate(rentalDate.plusDays(7));
            rental.setReturnDate(null); // unreturned
            rental.setBaseFee(7.0); // 7 days * $1
            
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        rentalSystem.getCustomers().add(customer);
        
        // Create available Tape T002
        VideoTape tapeT002 = new VideoTape();
        tapeT002.setBarcodeId("T002");
        tapeT002.setTitle("Available Tape");
        rentalSystem.getInventory().add(tapeT002);
        
        // Input: C002 rents tape "T002" with current_date="2025-01-01"
        boolean result = rentalSystem.addVideoTapeRental(2, "T002", 
            LocalDate.parse("2025-01-01", formatter), 7);
        
        // Expected Output: False
        assertFalse("Rental should fail when customer has 20 active rentals", result);
    }
    
    @Test
    public void testCase3_customerHasOverdueFees() {
        // Setup: Create Customer C003 with 1 active overdue rental
        Customer customer = new Customer();
        customer.setAccountNumber(3);
        customer.setName("C003");
        
        List<RentalTransaction> rentals = new ArrayList<>();
        RentalTransaction overdueRental = new RentalTransaction();
        overdueRental.setTransactionId(1);
        overdueRental.setCustomer(customer);
        
        VideoTape overdueTape = new VideoTape();
        overdueTape.setBarcodeId("T999");
        overdueTape.setTitle("Overdue Tape");
        overdueRental.setTape(overdueTape);
        
        LocalDate rentalDate = LocalDate.parse("2024-12-28", formatter);
        overdueRental.setRentalDate(rentalDate);
        overdueRental.setDueDate(LocalDate.parse("2025-01-02", formatter)); // 3 days overdue on 2025-01-05
        overdueRental.setReturnDate(null); // unreturned
        overdueRental.setBaseFee(5.0); // 5 days * $1
        
        rentals.add(overdueRental);
        customer.setRentals(rentals);
        rentalSystem.getCustomers().add(customer);
        
        // Create available Tape T003
        VideoTape tapeT003 = new VideoTape();
        tapeT003.setBarcodeId("T003");
        tapeT003.setTitle("Available Tape");
        rentalSystem.getInventory().add(tapeT003);
        
        // Input: C003 rents tape "T003" with current_date="2025-01-05"
        boolean result = rentalSystem.addVideoTapeRental(3, "T003", 
            LocalDate.parse("2025-01-05", formatter), 7);
        
        // Expected Output: False
        assertFalse("Rental should fail when customer has overdue fees", result);
    }
    
    @Test
    public void testCase4_tapeIsUnavailable() {
        // Setup: Create Customer C004 with 0 rentals
        Customer customer = new Customer();
        customer.setAccountNumber(4);
        customer.setName("C004");
        customer.setRentals(new ArrayList<>());
        rentalSystem.getCustomers().add(customer);
        
        // Create another customer C010 who has Tape T004 rented
        Customer customerC010 = new Customer();
        customerC010.setAccountNumber(10);
        customerC010.setName("C010");
        
        List<RentalTransaction> c010Rentals = new ArrayList<>();
        RentalTransaction activeRental = new RentalTransaction();
        activeRental.setTransactionId(1);
        activeRental.setCustomer(customerC010);
        
        VideoTape tapeT004 = new VideoTape();
        tapeT004.setBarcodeId("T004");
        tapeT004.setTitle("Unavailable Tape");
        activeRental.setTape(tapeT004);
        
        LocalDate rentalDate = LocalDate.parse("2024-12-29", formatter);
        activeRental.setRentalDate(rentalDate);
        activeRental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        activeRental.setReturnDate(null); // unreturned
        activeRental.setBaseFee(7.0); // 7 days * $1
        
        c010Rentals.add(activeRental);
        customerC010.setRentals(c010Rentals);
        rentalSystem.getCustomers().add(customerC010);
        
        // Add tape to inventory and transaction to system
        rentalSystem.getInventory().add(tapeT004);
        rentalSystem.getAllTransactions().add(activeRental);
        
        // Input: C004 rents tape "T004" with current_date="2025-01-01"
        boolean result = rentalSystem.addVideoTapeRental(4, "T004", 
            LocalDate.parse("2025-01-01", formatter), 7);
        
        // Expected Output: False
        assertFalse("Rental should fail when tape is unavailable", result);
    }
    
    @Test
    public void testCase5_allConditionsFail() {
        // Setup: Create Customer C005 with 20 active rentals and one overdue rental
        Customer customer = new Customer();
        customer.setAccountNumber(5);
        customer.setName("C005");
        
        // Create 20 active rentals for customer
        List<RentalTransaction> rentals = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            RentalTransaction rental = new RentalTransaction();
            rental.setTransactionId(i);
            rental.setCustomer(customer);
            
            VideoTape tape = new VideoTape();
            tape.setBarcodeId("T00" + i);
            tape.setTitle("Tape " + i);
            rental.setTape(tape);
            
            LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
            rental.setRentalDate(rentalDate);
            rental.setDueDate(rentalDate.plusDays(7));
            rental.setReturnDate(null); // unreturned
            rental.setBaseFee(7.0); // 7 days * $1
            
            rentals.add(rental);
        }
        
        // Add one overdue rental (due_date="2024-12-31", return_date=null)
        RentalTransaction overdueRental = new RentalTransaction();
        overdueRental.setTransactionId(21);
        overdueRental.setCustomer(customer);
        
        VideoTape overdueTape = new VideoTape();
        overdueTape.setBarcodeId("T999");
        overdueTape.setTitle("Overdue Tape");
        overdueRental.setTape(overdueTape);
        
        LocalDate overdueRentalDate = LocalDate.parse("2024-12-24", formatter);
        overdueRental.setRentalDate(overdueRentalDate);
        overdueRental.setDueDate(LocalDate.parse("2024-12-31", formatter)); // 1 day overdue on 2025-01-01
        overdueRental.setReturnDate(null); // unreturned
        overdueRental.setBaseFee(7.0); // 7 days * $1
        
        rentals.add(overdueRental);
        customer.setRentals(rentals);
        rentalSystem.getCustomers().add(customer);
        
        // Create Tape T005 with active rental by another customer C011
        VideoTape tapeT005 = new VideoTape();
        tapeT005.setBarcodeId("T005");
        tapeT005.setTitle("Unavailable Tape");
        rentalSystem.getInventory().add(tapeT005);
        
        Customer customerC011 = new Customer();
        customerC011.setAccountNumber(11);
        customerC011.setName("C011");
        
        List<RentalTransaction> c011Rentals = new ArrayList<>();
        RentalTransaction activeRental = new RentalTransaction();
        activeRental.setTransactionId(22);
        activeRental.setCustomer(customerC011);
        activeRental.setTape(tapeT005);
        
        LocalDate rentalDate = LocalDate.parse("2024-12-29", formatter);
        activeRental.setRentalDate(rentalDate);
        activeRental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        activeRental.setReturnDate(null); // unreturned
        activeRental.setBaseFee(7.0); // 7 days * $1
        
        c011Rentals.add(activeRental);
        customerC011.setRentals(c011Rentals);
        rentalSystem.getCustomers().add(customerC011);
        rentalSystem.getAllTransactions().add(activeRental);
        
        // Input: C005 rents tape "T005" with current_date="2025-01-01"
        boolean result = rentalSystem.addVideoTapeRental(5, "T005", 
            LocalDate.parse("2025-01-01", formatter), 7);
        
        // Expected Output: False
        assertFalse("Rental should fail when all conditions are violated", result);
    }
}