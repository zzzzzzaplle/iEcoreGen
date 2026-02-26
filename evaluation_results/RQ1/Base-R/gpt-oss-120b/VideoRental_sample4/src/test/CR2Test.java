import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private RentalService rentalService;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        rentalService = new RentalService();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_TapeIsAvailable() {
        // Setup
        VideoTape tape = new VideoTape();
        tape.setTapeId("T001");
        rentalService.getInventory().add(tape);
        
        // Input
        String tapeId = "T001";
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Test
        boolean result = rentalService.isTapeAvailable(tapeId, currentDate);
        
        // Verify
        assertTrue("Tape T001 should be available when no active rentals exist", result);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() {
        // Setup
        VideoTape tape = new VideoTape();
        tape.setTapeId("T002");
        rentalService.getInventory().add(tape);
        
        Customer customer = new Customer();
        customer.setId("C001");
        customer.setName("Test Customer");
        rentalService.getCustomers().add(customer);
        
        RentalItem rental = new RentalItem();
        rental.setRentalId("R001");
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.parse("2024-12-28", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(null); // unreturned
        
        rentalService.getRentals().add(rental);
        customer.getRentals().add(rental);
        
        // Input
        String tapeId = "T002";
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Test
        boolean result = rentalService.isTapeAvailable(tapeId, currentDate);
        
        // Verify
        assertFalse("Tape T002 should not be available when it has an active rental", result);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Setup
        VideoTape tape = new VideoTape();
        tape.setTapeId("T003");
        rentalService.getInventory().add(tape);
        
        Customer customer = new Customer();
        customer.setId("C002");
        customer.setName("Test Customer");
        rentalService.getCustomers().add(customer);
        
        RentalItem rental = new RentalItem();
        rental.setRentalId("R002");
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.parse("2024-12-25", formatter));
        rental.setDueDate(LocalDate.parse("2024-12-30", formatter));
        rental.setReturnDate(LocalDate.parse("2024-12-31", formatter)); // returned
        
        rentalService.getRentals().add(rental);
        customer.getRentals().add(rental);
        
        // Input
        String tapeId = "T003";
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Test
        boolean result = rentalService.isTapeAvailable(tapeId, currentDate);
        
        // Verify
        assertTrue("Tape T003 should be available when it was returned before current date", result);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Setup
        VideoTape tape = new VideoTape();
        tape.setTapeId("T004");
        rentalService.getInventory().add(tape);
        
        Customer customer = new Customer();
        customer.setId("C003");
        customer.setName("Test Customer");
        rentalService.getCustomers().add(customer);
        
        RentalItem rental = new RentalItem();
        rental.setRentalId("R003");
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.parse("2024-12-28", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-01", formatter));
        rental.setReturnDate(null); // unreturned and overdue
        
        rentalService.getRentals().add(rental);
        customer.getRentals().add(rental);
        
        // Input
        String tapeId = "T004";
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        
        // Test
        boolean result = rentalService.isTapeAvailable(tapeId, currentDate);
        
        // Verify
        assertFalse("Tape T004 should not be available when it has an overdue rental", result);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Setup
        VideoTape tape = new VideoTape();
        tape.setTapeId("T005");
        rentalService.getInventory().add(tape);
        
        // First customer and rental (returned)
        Customer customer1 = new Customer();
        customer1.setId("C004");
        customer1.setName("Customer 1");
        rentalService.getCustomers().add(customer1);
        
        RentalItem rental1 = new RentalItem();
        rental1.setRentalId("R004");
        rental1.setCustomer(customer1);
        rental1.setTape(tape);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-01", formatter)); // returned
        
        rentalService.getRentals().add(rental1);
        customer1.getRentals().add(rental1);
        
        // Second customer and rental (unreturned)
        Customer customer2 = new Customer();
        customer2.setId("C005");
        customer2.setName("Customer 2");
        rentalService.getCustomers().add(customer2);
        
        RentalItem rental2 = new RentalItem();
        rental2.setRentalId("R005");
        rental2.setCustomer(customer2);
        rental2.setTape(tape);
        rental2.setRentalDate(LocalDate.parse("2025-01-06", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(null); // unreturned
        
        rentalService.getRentals().add(rental2);
        customer2.getRentals().add(rental2);
        
        // Input
        String tapeId = "T005";
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        
        // Test
        boolean result = rentalService.isTapeAvailable(tapeId, currentDate);
        
        // Verify
        assertFalse("Tape T005 should not be available when it has an active rental from second customer", result);
    }
}