import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private VideoRentalSystem system;
    private Customer customerC001;
    private Customer customerC002;
    private Customer customerC003;
    private Customer customerC004;
    private Customer customerC005;
    private Customer customerC010;
    private Customer customerC011;
    private VideoTape tapeT001;
    private VideoTape tapeT002;
    private VideoTape tapeT003;
    private VideoTape tapeT004;
    private VideoTape tapeT005;
    
    @Before
    public void setUp() {
        system = new VideoRentalSystem();
        
        // Create test customers
        customerC001 = new Customer();
        customerC001.setAccountNumber("C001");
        customerC001.setIdCardNumber("ID001");
        customerC001.setName("Customer 001");
        
        customerC002 = new Customer();
        customerC002.setAccountNumber("C002");
        customerC002.setIdCardNumber("ID002");
        customerC002.setName("Customer 002");
        
        customerC003 = new Customer();
        customerC003.setAccountNumber("C003");
        customerC003.setIdCardNumber("ID003");
        customerC003.setName("Customer 003");
        
        customerC004 = new Customer();
        customerC004.setAccountNumber("C004");
        customerC004.setIdCardNumber("ID004");
        customerC004.setName("Customer 004");
        
        customerC005 = new Customer();
        customerC005.setAccountNumber("C005");
        customerC005.setIdCardNumber("ID005");
        customerC005.setName("Customer 005");
        
        customerC010 = new Customer();
        customerC010.setAccountNumber("C010");
        customerC010.setIdCardNumber("ID010");
        customerC010.setName("Customer 010");
        
        customerC011 = new Customer();
        customerC011.setAccountNumber("C011");
        customerC011.setIdCardNumber("ID011");
        customerC011.setName("Customer 011");
        
        // Create test tapes
        tapeT001 = new VideoTape();
        tapeT001.setBarcodeId("T001");
        tapeT001.setTitle("Tape 001");
        tapeT001.setAvailable(true);
        
        tapeT002 = new VideoTape();
        tapeT002.setBarcodeId("T002");
        tapeT002.setTitle("Tape 002");
        tapeT002.setAvailable(true);
        
        tapeT003 = new VideoTape();
        tapeT003.setBarcodeId("T003");
        tapeT003.setTitle("Tape 003");
        tapeT003.setAvailable(true);
        
        tapeT004 = new VideoTape();
        tapeT004.setBarcodeId("T004");
        tapeT004.setTitle("Tape 004");
        tapeT004.setAvailable(true);
        
        tapeT005 = new VideoTape();
        tapeT005.setBarcodeId("T005");
        tapeT005.setTitle("Tape 005");
        tapeT005.setAvailable(true);
        
        // Add customers and tapes to system
        List<Customer> customers = new ArrayList<>();
        customers.add(customerC001);
        customers.add(customerC002);
        customers.add(customerC003);
        customers.add(customerC004);
        customers.add(customerC005);
        customers.add(customerC010);
        customers.add(customerC011);
        system.setCustomers(customers);
        
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tapeT001);
        tapes.add(tapeT002);
        tapes.add(tapeT003);
        tapes.add(tapeT004);
        tapes.add(tapeT005);
        system.setVideoInventory(tapes);
    }
    
    @Test
    public void testCase1_SuccessfulRental() {
        // Setup: Create Customer C001 with 5 active rentals
        for (int i = 1; i <= 5; i++) {
            Rental rental = new Rental();
            rental.setRentalId("RENT" + i);
            rental.setCustomer(customerC001);
            rental.setTape(new VideoTape()); // Dummy tape for setup
            rental.setRentalDate("2025-01-0" + i);
            rental.setDueDate("2025-01-0" + (i + 7));
            rental.setReturnDate(null);
            rental.setRentalDays(7);
            customerC001.getRentals().add(rental);
            system.getAllRentals().add(rental);
        }
        
        // Execute: C001 rents tape "T001" with current_date="2025-01-01"
        boolean result = system.addVideoTapeRental(customerC001, tapeT001, "2025-01-01", 7, "2025-01-01");
        
        // Verify: Expected Output: True
        assertTrue("Rental should be successful when all conditions are met", result);
    }
    
    @Test
    public void testCase2_CustomerHas20RentalsMaxLimit() {
        // Setup: Create Customer C002 with 20 active rentals
        for (int i = 1; i <= 20; i++) {
            Rental rental = new Rental();
            rental.setRentalId("RENT" + i);
            rental.setCustomer(customerC002);
            rental.setTape(new VideoTape()); // Dummy tape for setup
            rental.setRentalDate("2025-01-01");
            rental.setDueDate("2025-01-08");
            rental.setReturnDate(null);
            rental.setRentalDays(7);
            customerC002.getRentals().add(rental);
            system.getAllRentals().add(rental);
        }
        
        // Execute: C002 rents tape "T002" with current_date="2025-01-01"
        boolean result = system.addVideoTapeRental(customerC002, tapeT002, "2025-01-01", 7, "2025-01-01");
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail when customer has 20 rentals", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup: Create Customer C003 with 1 active rental, due_date="2025-01-05", return_date=null (3 days overdue)
        Rental overdueRental = new Rental();
        overdueRental.setRentalId("RENT001");
        overdueRental.setCustomer(customerC003);
        overdueRental.setTape(new VideoTape()); // Dummy tape for setup
        overdueRental.setRentalDate("2024-12-29");
        overdueRental.setDueDate("2025-01-05");
        overdueRental.setReturnDate(null);
        overdueRental.setRentalDays(7);
        customerC003.getRentals().add(overdueRental);
        system.getAllRentals().add(overdueRental);
        
        // Execute: C003 rents tape "T003" with current_date="2025-01-08"
        boolean result = system.addVideoTapeRental(customerC003, tapeT003, "2025-01-08", 7, "2025-01-08");
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail when customer has overdue fees", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup: Create Tape T004 with active rental (rented by another customer C010)
        Rental existingRental = new Rental();
        existingRental.setRentalId("RENT001");
        existingRental.setCustomer(customerC010);
        existingRental.setTape(tapeT004);
        existingRental.setRentalDate("2024-12-29");
        existingRental.setDueDate("2025-01-05");
        existingRental.setReturnDate(null);
        existingRental.setRentalDays(7);
        customerC010.getRentals().add(existingRental);
        system.getAllRentals().add(existingRental);
        
        // Execute: C004 rents tape "T004" with current_date="2025-01-01"
        boolean result = system.addVideoTapeRental(customerC004, tapeT004, "2025-01-01", 7, "2025-01-01");
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail when tape is unavailable", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup: Create Customer C005 with 20 active rentals and one overdue rental
        for (int i = 1; i <= 20; i++) {
            Rental rental = new Rental();
            rental.setRentalId("RENT" + i);
            rental.setCustomer(customerC005);
            rental.setTape(new VideoTape()); // Dummy tape for setup
            rental.setRentalDate("2025-01-01");
            rental.setDueDate("2025-01-08");
            rental.setReturnDate(null);
            rental.setRentalDays(7);
            customerC005.getRentals().add(rental);
            system.getAllRentals().add(rental);
        }
        
        // Add overdue rental
        Rental overdueRental = new Rental();
        overdueRental.setRentalId("RENT021");
        overdueRental.setCustomer(customerC005);
        overdueRental.setTape(new VideoTape()); // Dummy tape for setup
        overdueRental.setRentalDate("2024-12-24");
        overdueRental.setDueDate("2024-12-31");
        overdueRental.setReturnDate(null);
        overdueRental.setRentalDays(7);
        customerC005.getRentals().add(overdueRental);
        system.getAllRentals().add(overdueRental);
        
        // Setup: Create Tape T005 with active rental (rented by another customer C011)
        Rental existingRental = new Rental();
        existingRental.setRentalId("RENT022");
        existingRental.setCustomer(customerC011);
        existingRental.setTape(tapeT005);
        existingRental.setRentalDate("2024-12-29");
        existingRental.setDueDate("2025-01-05");
        existingRental.setReturnDate(null);
        existingRental.setRentalDays(7);
        customerC011.getRentals().add(existingRental);
        system.getAllRentals().add(existingRental);
        
        // Execute: C005 rents tape "T005" with current_date="2025-01-01"
        boolean result = system.addVideoTapeRental(customerC005, tapeT005, "2025-01-01", 7, "2025-01-01");
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail when all conditions fail", result);
    }
}