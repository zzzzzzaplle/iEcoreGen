import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    private VideoRentalSystem system;
    private VideoTape tapeT001;
    private VideoTape tapeT002;
    private VideoTape tapeT003;
    private VideoTape tapeT004;
    private VideoTape tapeT005;
    private Customer customerC001;
    private Customer customerC002;
    private Customer customerC003;
    private Customer customerC004;
    private Customer customerC005;
    
    @Before
    public void setUp() {
        system = new VideoRentalSystem();
        
        // Create tapes for all test cases
        tapeT001 = new VideoTape();
        tapeT001.setBarCodeId("T001");
        tapeT001.setTitle("Test Tape 1");
        
        tapeT002 = new VideoTape();
        tapeT002.setBarCodeId("T002");
        tapeT002.setTitle("Test Tape 2");
        
        tapeT003 = new VideoTape();
        tapeT003.setBarCodeId("T003");
        tapeT003.setTitle("Test Tape 3");
        
        tapeT004 = new VideoTape();
        tapeT004.setBarCodeId("T004");
        tapeT004.setTitle("Test Tape 4");
        
        tapeT005 = new VideoTape();
        tapeT005.setBarCodeId("T005");
        tapeT005.setTitle("Test Tape 5");
        
        // Create customers for test cases
        customerC001 = new Customer();
        customerC001.setAccountNumber("C001");
        customerC001.setName("Customer 1");
        
        customerC002 = new Customer();
        customerC002.setAccountNumber("C002");
        customerC002.setName("Customer 2");
        
        customerC003 = new Customer();
        customerC003.setAccountNumber("C003");
        customerC003.setName("Customer 3");
        
        customerC004 = new Customer();
        customerC004.setAccountNumber("C004");
        customerC004.setName("Customer 4");
        
        customerC005 = new Customer();
        customerC005.setAccountNumber("C005");
        customerC005.setName("Customer 5");
        
        // Add tapes to system inventory
        List<VideoTape> inventory = new ArrayList<>();
        inventory.add(tapeT001);
        inventory.add(tapeT002);
        inventory.add(tapeT003);
        inventory.add(tapeT004);
        inventory.add(tapeT005);
        system.setInventory(inventory);
    }
    
    @Test
    public void testCase1_TapeIsAvailable() {
        // Setup: Create Tape with id="T001", No active rentals for T001
        String currentDate = "2025-01-01";
        
        // Test tape availability
        boolean result = system.checkTapeAvailability(tapeT001, currentDate);
        
        // Verify tape is available
        assertTrue("Tape T001 should be available when no active rentals exist", result);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() {
        // Setup: Create Tape with id="T002", Create Customer C001
        // C001 rents T002 with rental date="2024-12-28", due_date="2025-01-05", return_date=null (unreturned)
        String currentDate = "2025-01-01";
        
        // Create rental for customer C001
        Rental rental = new Rental();
        rental.setTape(tapeT002);
        rental.setCustomer(customerC001);
        rental.setRentalDate(LocalDate.parse("2024-12-28"));
        rental.setDueDate(LocalDate.parse("2025-01-05"));
        rental.setReturnDate(null); // Unreturned
        
        // Add rental to system and customer
        system.getRentals().add(rental);
        customerC001.getRentals().add(rental);
        
        // Test tape availability
        boolean result = system.checkTapeAvailability(tapeT002, currentDate);
        
        // Verify tape is unavailable (rented out)
        assertFalse("Tape T002 should be unavailable when rented out and not returned", result);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Setup: Create Tape with id="T003", Create Customer C002
        // C002 rents T003 with rental date="2024-12-25", due_date="2024-12-30", return_date="2024-12-31"
        String currentDate = "2025-01-01";
        
        // Create rental for customer C002
        Rental rental = new Rental();
        rental.setTape(tapeT003);
        rental.setCustomer(customerC002);
        rental.setRentalDate(LocalDate.parse("2024-12-25"));
        rental.setDueDate(LocalDate.parse("2024-12-30"));
        rental.setReturnDate(LocalDate.parse("2024-12-31")); // Returned
        
        // Add rental to system and customer
        system.getRentals().add(rental);
        customerC002.getRentals().add(rental);
        
        // Test tape availability
        boolean result = system.checkTapeAvailability(tapeT003, currentDate);
        
        // Verify tape is available (already returned)
        assertTrue("Tape T003 should be available when previously rented but returned", result);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Setup: Create Tape with id="T004", Create Customer C003
        // C003 rents T004 with rental date="2024-12-28", due_date="2025-01-01", return_date=null (unreturned)
        String currentDate = "2025-01-10";
        
        // Create rental for customer C003
        Rental rental = new Rental();
        rental.setTape(tapeT004);
        rental.setCustomer(customerC003);
        rental.setRentalDate(LocalDate.parse("2024-12-28"));
        rental.setDueDate(LocalDate.parse("2025-01-01"));
        rental.setReturnDate(null); // Unreturned and overdue
        
        // Add rental to system and customer
        system.getRentals().add(rental);
        customerC003.getRentals().add(rental);
        
        // Test tape availability
        boolean result = system.checkTapeAvailability(tapeT004, currentDate);
        
        // Verify tape is unavailable (overdue rental not returned)
        assertFalse("Tape T004 should be unavailable when rented out and overdue", result);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Setup: Create Tape with id="T005", Create Customer C004, C005
        // C004 rents T005 with rental date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01" → first rental
        // C005 rents T005 with rental date="2025-01-06", due_date="2025-01-15", return_date=null → second rental
        String currentDate = "2025-01-10";
        
        // Create first rental for customer C004 (returned)
        Rental rental1 = new Rental();
        rental1.setTape(tapeT005);
        rental1.setCustomer(customerC004);
        rental1.setRentalDate(LocalDate.parse("2025-01-01"));
        rental1.setDueDate(LocalDate.parse("2025-01-05"));
        rental1.setReturnDate(LocalDate.parse("2025-01-01")); // Returned same day
        
        // Create second rental for customer C005 (unreturned)
        Rental rental2 = new Rental();
        rental2.setTape(tapeT005);
        rental2.setCustomer(customerC005);
        rental2.setRentalDate(LocalDate.parse("2025-01-06"));
        rental2.setDueDate(LocalDate.parse("2025-01-15"));
        rental2.setReturnDate(null); // Unreturned
        
        // Add rentals to system and customers
        system.getRentals().add(rental1);
        system.getRentals().add(rental2);
        customerC004.getRentals().add(rental1);
        customerC005.getRentals().add(rental2);
        
        // Test tape availability - should be false due to second rental being active
        boolean result = system.checkTapeAvailability(tapeT005, currentDate);
        
        // Verify tape is unavailable (second rental is still active)
        assertFalse("Tape T005 should be unavailable when currently rented out", result);
    }
}