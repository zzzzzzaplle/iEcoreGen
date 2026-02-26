import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private VideoRentalSystem system;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        system = new VideoRentalSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SuccessfulRental() {
        // Setup: Create Customer C001 with 5 active rentals
        Customer customer = new Customer();
        customer.setAccountNumber("C001");
        customer.setIdCardNumber("ID001");
        customer.setName("Customer One");
        
        // Create 5 active rentals for customer
        for (int i = 1; i <= 5; i++) {
            VideoTape tape = new VideoTape();
            tape.setBarcodeId("T00" + (i + 100)); // T0101, T0102, etc.
            tape.setTitle("Movie " + i);
            tape.setAvailable(true);
            system.getInventory().add(tape);
            
            Rental rental = new Rental();
            rental.setRentalId("R00" + i);
            rental.setCustomer(customer);
            rental.setTape(tape);
            rental.setRentalDate("2025-01-0" + i);
            
            LocalDate rentalDate = LocalDate.parse("2025-01-0" + i, formatter);
            LocalDate dueDate = rentalDate.plusDays(7);
            rental.setDueDate(dueDate.format(formatter));
            rental.setReturnDate(null);
            rental.setRentalDays(7);
            rental.setBaseRentalFee(BigDecimal.valueOf(7));
            
            customer.getRentals().add(rental);
        }
        system.getCustomers().add(customer);
        
        // Create available Tape T001
        VideoTape tapeT001 = new VideoTape();
        tapeT001.setBarcodeId("T001");
        tapeT001.setTitle("Movie T001");
        tapeT001.setAvailable(true);
        system.getInventory().add(tapeT001);
        
        // Test: C001 rents tape "T001" with current_date="2025-01-01"
        boolean result = system.addVideoRental("C001", "T001", "2025-01-01", 7, "2025-01-01");
        
        // Verify: Should return true as all conditions are met
        assertTrue("Rental should be successful when customer has <20 rentals, no past-due amount, and tape is available", result);
    }
    
    @Test
    public void testCase2_CustomerHas20RentalsMaxLimit() {
        // Setup: Create Customer C002 with 20 active rentals
        Customer customer = new Customer();
        customer.setAccountNumber("C002");
        customer.setIdCardNumber("ID002");
        customer.setName("Customer Two");
        
        // Create 20 active rentals for customer
        for (int i = 1; i <= 20; i++) {
            VideoTape tape = new VideoTape();
            tape.setBarcodeId("T02" + String.format("%02d", i));
            tape.setTitle("Movie " + i);
            tape.setAvailable(true);
            system.getInventory().add(tape);
            
            Rental rental = new Rental();
            rental.setRentalId("R02" + i);
            rental.setCustomer(customer);
            rental.setTape(tape);
            rental.setRentalDate("2025-01-01");
            
            LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
            LocalDate dueDate = rentalDate.plusDays(7);
            rental.setDueDate(dueDate.format(formatter));
            rental.setReturnDate(null);
            rental.setRentalDays(7);
            rental.setBaseRentalFee(BigDecimal.valueOf(7));
            
            customer.getRentals().add(rental);
        }
        system.getCustomers().add(customer);
        
        // Create available Tape T002
        VideoTape tapeT002 = new VideoTape();
        tapeT002.setBarcodeId("T002");
        tapeT002.setTitle("Movie T002");
        tapeT002.setAvailable(true);
        system.getInventory().add(tapeT002);
        
        // Test: C002 rents tape "T002" with current_date="2025-01-01"
        boolean result = system.addVideoRental("C002", "T002", "2025-01-01", 7, "2025-01-01");
        
        // Verify: Should return false as customer has 20 rentals (max limit)
        assertFalse("Rental should fail when customer has 20 active rentals", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup: Create Customer C003 with 1 active rental that is overdue
        Customer customer = new Customer();
        customer.setAccountNumber("C003");
        customer.setIdCardNumber("ID003");
        customer.setName("Customer Three");
        
        // Create overdue rental for customer (due date is 2025-01-02, current date is 2025-01-05)
        VideoTape overdueTape = new VideoTape();
        overdueTape.setBarcodeId("T030");
        overdueTape.setTitle("Overdue Movie");
        overdueTape.setAvailable(true);
        system.getInventory().add(overdueTape);
        
        Rental overdueRental = new Rental();
        overdueRental.setRentalId("R030");
        overdueRental.setCustomer(customer);
        overdueRental.setTape(overdueTape);
        overdueRental.setRentalDate("2024-12-26"); // 7 days before due date
        overdueRental.setDueDate("2025-01-02");
        overdueRental.setReturnDate(null); // Not returned
        overdueRental.setRentalDays(7);
        overdueRental.setBaseRentalFee(BigDecimal.valueOf(7));
        
        customer.getRentals().add(overdueRental);
        system.getCustomers().add(customer);
        
        // Create available Tape T003
        VideoTape tapeT003 = new VideoTape();
        tapeT003.setBarcodeId("T003");
        tapeT003.setTitle("Movie T003");
        tapeT003.setAvailable(true);
        system.getInventory().add(tapeT003);
        
        // Test: C003 rents tape "T003" with current_date="2025-01-05"
        boolean result = system.addVideoRental("C003", "T003", "2025-01-05", 7, "2025-01-05");
        
        // Verify: Should return false as customer has overdue fees
        assertFalse("Rental should fail when customer has past-due amounts", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup: Create Customer C004 with 0 rentals
        Customer customer = new Customer();
        customer.setAccountNumber("C004");
        customer.setIdCardNumber("ID004");
        customer.setName("Customer Four");
        system.getCustomers().add(customer);
        
        // Create Customer C010 who has active rental of T004
        Customer customerC010 = new Customer();
        customerC010.setAccountNumber("C010");
        customerC010.setIdCardNumber("ID010");
        customerC010.setName("Customer Ten");
        
        // Create Tape T004 with active rental by C010
        VideoTape tapeT004 = new VideoTape();
        tapeT004.setBarcodeId("T004");
        tapeT004.setTitle("Movie T004");
        tapeT004.setAvailable(true);
        system.getInventory().add(tapeT004);
        
        Rental activeRental = new Rental();
        activeRental.setRentalId("R010");
        activeRental.setCustomer(customerC010);
        activeRental.setTape(tapeT004);
        activeRental.setRentalDate("2024-12-29");
        activeRental.setDueDate("2025-01-05");
        activeRental.setReturnDate(null); // Active rental
        activeRental.setRentalDays(7);
        activeRental.setBaseRentalFee(BigDecimal.valueOf(7));
        
        customerC010.getRentals().add(activeRental);
        system.getCustomers().add(customerC010);
        
        // Create transaction for C010 with the active rental
        RentalTransaction transaction = new RentalTransaction();
        transaction.setTransactionId("TX010");
        transaction.setCustomer(customerC010);
        transaction.setTransactionDate("2024-12-29");
        transaction.getRentals().add(activeRental);
        system.getTransactions().add(transaction);
        
        // Test: C004 rents tape "T004" with current_date="2025-01-01"
        boolean result = system.addVideoRental("C004", "T004", "2025-01-01", 7, "2025-01-01");
        
        // Verify: Should return false as tape is unavailable (rented by another customer)
        assertFalse("Rental should fail when tape is unavailable", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup: Create Customer C005 with 20 active rentals and one overdue rental
        Customer customer = new Customer();
        customer.setAccountNumber("C005");
        customer.setIdCardNumber("ID005");
        customer.setName("Customer Five");
        
        // Create 20 active rentals for customer
        for (int i = 1; i <= 20; i++) {
            VideoTape tape = new VideoTape();
            tape.setBarcodeId("T05" + String.format("%02d", i));
            tape.setTitle("Movie " + i);
            tape.setAvailable(true);
            system.getInventory().add(tape);
            
            Rental rental = new Rental();
            rental.setRentalId("R05" + i);
            rental.setCustomer(customer);
            rental.setTape(tape);
            rental.setRentalDate("2025-01-01");
            
            LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
            LocalDate dueDate = rentalDate.plusDays(7);
            rental.setDueDate(dueDate.format(formatter));
            rental.setReturnDate(null);
            rental.setRentalDays(7);
            rental.setBaseRentalFee(BigDecimal.valueOf(7));
            
            customer.getRentals().add(rental);
        }
        
        // Add one overdue rental (due_date="2024-12-31", return_date=null)
        VideoTape overdueTape = new VideoTape();
        overdueTape.setBarcodeId("T0599");
        overdueTape.setTitle("Overdue Movie");
        overdueTape.setAvailable(true);
        system.getInventory().add(overdueTape);
        
        Rental overdueRental = new Rental();
        overdueRental.setRentalId("R0599");
        overdueRental.setCustomer(customer);
        overdueRental.setTape(overdueTape);
        overdueRental.setRentalDate("2024-12-24"); // 7 days before due date
        overdueRental.setDueDate("2024-12-31");
        overdueRental.setReturnDate(null); // Not returned
        overdueRental.setRentalDays(7);
        overdueRental.setBaseRentalFee(BigDecimal.valueOf(7));
        
        customer.getRentals().add(overdueRental);
        system.getCustomers().add(customer);
        
        // Create Tape T005 with active rental by C011
        VideoTape tapeT005 = new VideoTape();
        tapeT005.setBarcodeId("T005");
        tapeT005.setTitle("Movie T005");
        tapeT005.setAvailable(true);
        system.getInventory().add(tapeT005);
        
        // Create Customer C011 who has active rental of T005
        Customer customerC011 = new Customer();
        customerC011.setAccountNumber("C011");
        customerC011.setIdCardNumber("ID011");
        customerC011.setName("Customer Eleven");
        
        Rental activeRental = new Rental();
        activeRental.setRentalId("R011");
        activeRental.setCustomer(customerC011);
        activeRental.setTape(tapeT005);
        activeRental.setRentalDate("2024-12-29");
        activeRental.setDueDate("2025-01-05");
        activeRental.setReturnDate(null); // Active rental
        activeRental.setRentalDays(7);
        activeRental.setBaseRentalFee(BigDecimal.valueOf(7));
        
        customerC011.getRentals().add(activeRental);
        system.getCustomers().add(customerC011);
        
        // Create transaction for C011 with the active rental
        RentalTransaction transaction = new RentalTransaction();
        transaction.setTransactionId("TX011");
        transaction.setCustomer(customerC011);
        transaction.setTransactionDate("2024-12-29");
        transaction.getRentals().add(activeRental);
        system.getTransactions().add(transaction);
        
        // Test: C005 rents tape "T005" with current_date="2025-01-01"
        boolean result = system.addVideoRental("C005", "T005", "2025-01-01", 7, "2025-01-01");
        
        // Verify: Should return false as all conditions fail (20 rentals + overdue + tape unavailable)
        assertFalse("Rental should fail when all conditions are violated", result);
    }
}