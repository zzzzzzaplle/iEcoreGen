import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;

public class CR5Test {
    
    private RentalService rentalService;
    
    @Before
    public void setUp() {
        rentalService = new RentalService();
    }
    
    @Test
    public void testCase1_noRentalsExist() {
        // Setup: Create Customer C001 with empty rentals list
        Customer customer = new Customer();
        customer.setId("C001");
        customer.setName("Customer1");
        rentalService.getCustomers().add(customer);
        
        // Execute
        List<String> result = rentalService.listUnreturnedTapes("C001");
        
        // Verify: Empty list, no active rentals
        assertTrue("Expected empty list when customer has no rentals", result.isEmpty());
    }
    
    @Test
    public void testCase2_allTapesReturned() {
        // Setup: Create Customer C002
        Customer customer = new Customer();
        customer.setId("C002");
        customer.setName("Customer2");
        rentalService.getCustomers().add(customer);
        
        // Setup: Create Tape T001
        VideoTape tape = new VideoTape();
        tape.setTapeId("T001");
        tape.setTitle("Test Movie");
        rentalService.getInventory().add(tape);
        
        // Setup: Create VideoRental with return_date set (tape returned)
        RentalItem rental = new RentalItem();
        rental.setRentalId("R001");
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.of(2025, 1, 1));
        rental.setDueDate(LocalDate.of(2025, 1, 5));
        rental.setReturnDate(LocalDate.of(2025, 1, 1)); // Tape returned
        
        rentalService.getRentals().add(rental);
        customer.getRentals().add(rental);
        
        // Execute
        List<String> result = rentalService.listUnreturnedTapes("C002");
        
        // Verify: Empty list, all rentals returned
        assertTrue("Expected empty list when all tapes are returned", result.isEmpty());
    }
    
    @Test
    public void testCase3_oneUnreturnedTape() {
        // Setup: Create Customer C003
        Customer customer = new Customer();
        customer.setId("C003");
        customer.setName("Customer3");
        rentalService.getCustomers().add(customer);
        
        // Setup: Create Tape T001
        VideoTape tape = new VideoTape();
        tape.setTapeId("T001");
        tape.setTitle("Test Movie");
        rentalService.getInventory().add(tape);
        
        // Setup: Create VideoRental with return_date=null (tape not returned)
        RentalItem rental = new RentalItem();
        rental.setRentalId("R001");
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.of(2025, 1, 1));
        rental.setDueDate(LocalDate.of(2025, 1, 5));
        rental.setReturnDate(null); // Tape not returned
        
        rentalService.getRentals().add(rental);
        customer.getRentals().add(rental);
        
        // Execute
        List<String> result = rentalService.listUnreturnedTapes("C003");
        
        // Verify: List containing T001
        assertEquals("Expected list size 1", 1, result.size());
        assertTrue("Expected T001 in result", result.contains("T001"));
    }
    
    @Test
    public void testCase4_mixedReturnedUnreturned() {
        // Setup: Create Customer C004
        Customer customer = new Customer();
        customer.setId("C004");
        customer.setName("Customer4");
        rentalService.getCustomers().add(customer);
        
        // Setup: Create Tapes T001 and T002
        VideoTape tape1 = new VideoTape();
        tape1.setTapeId("T001");
        tape1.setTitle("Movie1");
        rentalService.getInventory().add(tape1);
        
        VideoTape tape2 = new VideoTape();
        tape2.setTapeId("T002");
        tape2.setTitle("Movie2");
        rentalService.getInventory().add(tape2);
        
        // Setup: Create VideoRental for T001 with return_date set (tape returned)
        RentalItem rental1 = new RentalItem();
        rental1.setRentalId("R001");
        rental1.setCustomer(customer);
        rental1.setTape(tape1);
        rental1.setRentalDate(LocalDate.of(2025, 1, 1));
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 1)); // Tape returned
        
        // Setup: Create VideoRental for T002 with return_date=null (tape not returned)
        RentalItem rental2 = new RentalItem();
        rental2.setRentalId("R002");
        rental2.setCustomer(customer);
        rental2.setTape(tape2);
        rental2.setRentalDate(LocalDate.of(2025, 1, 2));
        rental2.setDueDate(LocalDate.of(2025, 1, 6));
        rental2.setReturnDate(null); // Tape not returned
        
        rentalService.getRentals().add(rental1);
        rentalService.getRentals().add(rental2);
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute
        List<String> result = rentalService.listUnreturnedTapes("C004");
        
        // Verify: List containing T002 only
        assertEquals("Expected list size 1", 1, result.size());
        assertTrue("Expected T002 in result", result.contains("T002"));
        assertFalse("T001 should not be in result", result.contains("T001"));
    }
    
    @Test
    public void testCase5_multipleUnreturnedTapes() {
        // Setup: Create Customer C005
        Customer customer = new Customer();
        customer.setId("C005");
        customer.setName("Customer5");
        rentalService.getCustomers().add(customer);
        
        // Setup: Create Tapes T001 and T002
        VideoTape tape1 = new VideoTape();
        tape1.setTapeId("T001");
        tape1.setTitle("Movie1");
        rentalService.getInventory().add(tape1);
        
        VideoTape tape2 = new VideoTape();
        tape2.setTapeId("T002");
        tape2.setTitle("Movie2");
        rentalService.getInventory().add(tape2);
        
        // Setup: Create VideoRental for T001 with return_date=null (tape not returned)
        RentalItem rental1 = new RentalItem();
        rental1.setRentalId("R001");
        rental1.setCustomer(customer);
        rental1.setTape(tape1);
        rental1.setRentalDate(LocalDate.of(2025, 1, 1));
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(null); // Tape not returned
        
        // Setup: Create VideoRental for T002 with return_date=null (tape not returned)
        RentalItem rental2 = new RentalItem();
        rental2.setRentalId("R002");
        rental2.setCustomer(customer);
        rental2.setTape(tape2);
        rental2.setRentalDate(LocalDate.of(2025, 1, 2));
        rental2.setDueDate(LocalDate.of(2025, 1, 6));
        rental2.setReturnDate(null); // Tape not returned
        
        rentalService.getRentals().add(rental1);
        rentalService.getRentals().add(rental2);
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute
        List<String> result = rentalService.listUnreturnedTapes("C005");
        
        // Verify: List containing T001 and T002
        assertEquals("Expected list size 2", 2, result.size());
        assertTrue("Expected T001 in result", result.contains("T001"));
        assertTrue("Expected T002 in result", result.contains("T002"));
    }
}