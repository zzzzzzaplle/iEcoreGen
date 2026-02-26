import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private VideoRentalSystem system;
    private VideoTape tape1;
    private VideoTape tape2;
    private VideoTape tape3;
    private VideoTape tape4;
    private VideoTape tape5;
    private Customer customer1;
    private Customer customer2;
    private Customer customer3;
    private Customer customer4;
    private Customer customer5;
    
    @Before
    public void setUp() {
        system = new VideoRentalSystem();
        
        // Initialize tapes
        tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        
        tape2 = new VideoTape();
        tape2.setBarcodeId("T002");
        
        tape3 = new VideoTape();
        tape3.setBarcodeId("T003");
        
        tape4 = new VideoTape();
        tape4.setBarcodeId("T004");
        
        tape5 = new VideoTape();
        tape5.setBarcodeId("T005");
        
        // Initialize customers
        customer1 = new Customer();
        customer1.setAccountNumber("C001");
        
        customer2 = new Customer();
        customer2.setAccountNumber("C002");
        
        customer3 = new Customer();
        customer3.setAccountNumber("C003");
        
        customer4 = new Customer();
        customer4.setAccountNumber("C004");
        
        customer5 = new Customer();
        customer5.setAccountNumber("C005");
        
        // Add tapes and customers to system
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape1);
        tapes.add(tape2);
        tapes.add(tape3);
        tapes.add(tape4);
        tapes.add(tape5);
        system.setVideoInventory(tapes);
        
        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);
        customers.add(customer4);
        customers.add(customer5);
        system.setCustomers(customers);
    }
    
    @Test
    public void testCase1_TapeIsAvailable() {
        // Setup: Create Tape with id="T001", No active rentals for T001
        String tapeId = "T001";
        String currentDate = "2025-01-01";
        
        // Execute: Check tape availability
        VideoTape tape = system.findVideoTapeByBarcode(tapeId);
        boolean result = system.isTapeAvailable(tape, currentDate);
        
        // Verify: Tape should be available
        assertTrue("Tape T001 should be available when no active rentals exist", result);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() {
        // Setup: Create Tape with id="T002", Create Customer C001
        // C001 rents T002 with rental date="2024-12-28", due_date="2025-01-05", return_date=null (unreturned)
        String tapeId = "T002";
        String currentDate = "2025-01-01";
        
        // Create rental for T002
        Rental rental = new Rental();
        rental.setTape(tape2);
        rental.setCustomer(customer1);
        rental.setRentalDate("2024-12-28");
        rental.setDueDate("2025-01-05");
        rental.setReturnDate(null);
        rental.setRentalDays(8);
        
        // Add rental to customer and system
        customer1.getRentals().add(rental);
        system.getAllRentals().add(rental);
        
        // Execute: Check tape availability
        VideoTape tape = system.findVideoTapeByBarcode(tapeId);
        boolean result = system.isTapeAvailable(tape, currentDate);
        
        // Verify: Tape should not be available (rented out)
        assertFalse("Tape T002 should not be available when rented out with null return date", result);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Setup: Create Tape with id="T003", Create Customer C002
        // C002 rents T003 with rental date="2024-12-25", due_date="2024-12-30", return_date="2024-12-31"
        String tapeId = "T003";
        String currentDate = "2025-01-01";
        
        // Create rental for T003 that has been returned
        Rental rental = new Rental();
        rental.setTape(tape3);
        rental.setCustomer(customer2);
        rental.setRentalDate("2024-12-25");
        rental.setDueDate("2024-12-30");
        rental.setReturnDate("2024-12-31");
        rental.setRentalDays(5);
        
        // Add rental to customer and system
        customer2.getRentals().add(rental);
        system.getAllRentals().add(rental);
        
        // Execute: Check tape availability
        VideoTape tape = system.findVideoTapeByBarcode(tapeId);
        boolean result = system.isTapeAvailable(tape, currentDate);
        
        // Verify: Tape should be available (returned before current date)
        assertTrue("Tape T003 should be available when rental has been returned", result);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Setup: Create Tape with id="T004", Create Customer C003
        // C003 rents T004 with rental date="2024-12-28", due_date="2025-01-01", return_date=null (unreturned)
        String tapeId = "T004";
        String currentDate = "2025-01-10";
        
        // Create overdue rental for T004
        Rental rental = new Rental();
        rental.setTape(tape4);
        rental.setCustomer(customer3);
        rental.setRentalDate("2024-12-28");
        rental.setDueDate("2025-01-01");
        rental.setReturnDate(null);
        rental.setRentalDays(4);
        
        // Add rental to customer and system
        customer3.getRentals().add(rental);
        system.getAllRentals().add(rental);
        
        // Execute: Check tape availability
        VideoTape tape = system.findVideoTapeByBarcode(tapeId);
        boolean result = system.isTapeAvailable(tape, currentDate);
        
        // Verify: Tape should not be available (overdue rental not returned)
        assertFalse("Tape T004 should not be available when overdue rental exists", result);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Setup: Create Tape with id="T005", Create Customer C004, C005
        // C004 rents T005 with rental date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01" → first rental
        // C005 rents T005 with rental date="2025-01-06", due_date="2025-01-15", return_date=null → second rental
        String tapeId = "T005";
        String currentDate = "2025-01-10";
        
        // Create first rental (returned)
        Rental rental1 = new Rental();
        rental1.setTape(tape5);
        rental1.setCustomer(customer4);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-01");
        rental1.setRentalDays(4);
        
        // Create second rental (not returned)
        Rental rental2 = new Rental();
        rental2.setTape(tape5);
        rental2.setCustomer(customer5);
        rental2.setRentalDate("2025-01-06");
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate(null);
        rental2.setRentalDays(9);
        
        // Add rentals to customers and system
        customer4.getRentals().add(rental1);
        customer5.getRentals().add(rental2);
        system.getAllRentals().add(rental1);
        system.getAllRentals().add(rental2);
        
        // Execute: Check tape availability
        VideoTape tape = system.findVideoTapeByBarcode(tapeId);
        boolean result = system.isTapeAvailable(tape, currentDate);
        
        // Verify: Tape should not be available (has active rental from C005)
        assertFalse("Tape T005 should not be available when active rental exists", result);
    }
}