import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private RentalService rentalService;
    private Customer customer1;
    private Customer customer2;
    private Customer customer3;
    private Customer customer4;
    private Customer customer5;
    private VideoTape tape1;
    private VideoTape tape2;
    private VideoTape tape3;
    private VideoTape tape4;
    private VideoTape tape5;
    private Customer otherCustomer10;
    private Customer otherCustomer11;
    
    @Before
    public void setUp() {
        rentalService = new RentalService();
        
        // Create customers
        customer1 = new Customer();
        customer1.setId("C001");
        customer1.setName("Customer 1");
        
        customer2 = new Customer();
        customer2.setId("C002");
        customer2.setName("Customer 2");
        
        customer3 = new Customer();
        customer3.setId("C003");
        customer3.setName("Customer 3");
        
        customer4 = new Customer();
        customer4.setId("C004");
        customer4.setName("Customer 4");
        
        customer5 = new Customer();
        customer5.setId("C005");
        customer5.setName("Customer 5");
        
        otherCustomer10 = new Customer();
        otherCustomer10.setId("C010");
        otherCustomer10.setName("Other Customer 10");
        
        otherCustomer11 = new Customer();
        otherCustomer11.setId("C011");
        otherCustomer11.setName("Other Customer 11");
        
        // Create tapes
        tape1 = new VideoTape();
        tape1.setTapeId("T001");
        tape1.setTitle("Tape 1");
        
        tape2 = new VideoTape();
        tape2.setTapeId("T002");
        tape2.setTitle("Tape 2");
        
        tape3 = new VideoTape();
        tape3.setTapeId("T003");
        tape3.setTitle("Tape 3");
        
        tape4 = new VideoTape();
        tape4.setTapeId("T004");
        tape4.setTitle("Tape 4");
        
        tape5 = new VideoTape();
        tape5.setTapeId("T005");
        tape5.setTitle("Tape 5");
        
        // Add all entities to service
        rentalService.setCustomers(List.of(customer1, customer2, customer3, customer4, customer5, otherCustomer10, otherCustomer11));
        rentalService.setInventory(List.of(tape1, tape2, tape3, tape4, tape5));
        rentalService.setRentals(new ArrayList<>());
    }
    
    @Test
    public void testCase1_SuccessfulRental() {
        // Setup: Create Customer C001 with 5 active rentals
        for (int i = 1; i <= 5; i++) {
            RentalItem rental = new RentalItem();
            rental.setRentalId("R" + i);
            rental.setCustomer(customer1);
            rental.setTape(tape2); // Using tape2 since tape1 should be available
            rental.setRentalDate(LocalDate.of(2025, 1, i));
            rental.setDueDate(LocalDate.of(2025, 1, i).plusDays(7));
            rental.setReturnDate(null);
            
            rentalService.getRentals().add(rental);
            customer1.getRentals().add(rental);
        }
        
        // Input: C001 rents tape "T001" with current_date="2025-01-01"
        boolean result = rentalService.addVideoTapeRental("C001", "T001", LocalDate.of(2025, 1, 1));
        
        // Expected Output: True
        assertTrue("Rental should be successful when customer has <20 rentals, no overdue fees, and tape is available", result);
        
        // Verify rental was actually created
        assertEquals("Customer should have 6 rentals after successful rental", 6, customer1.getRentals().size());
    }
    
    @Test
    public void testCase2_CustomerHas20Rentals() {
        // Setup: Create Customer C002 with 20 active rentals
        for (int i = 1; i <= 20; i++) {
            RentalItem rental = new RentalItem();
            rental.setRentalId("R" + (i + 10));
            rental.setCustomer(customer2);
            rental.setTape(tape3); // Using tape3 since tape2 should be available
            rental.setRentalDate(LocalDate.of(2025, 1, i));
            rental.setDueDate(LocalDate.of(2025, 1, i).plusDays(7));
            rental.setReturnDate(null);
            
            rentalService.getRentals().add(rental);
            customer2.getRentals().add(rental);
        }
        
        // Input: C002 rents tape "T002" with current_date="2025-01-01"
        boolean result = rentalService.addVideoTapeRental("C002", "T002", LocalDate.of(2025, 1, 1));
        
        // Expected Output: False
        assertFalse("Rental should fail when customer has 20 active rentals", result);
        
        // Verify no new rental was created
        assertEquals("Customer should still have exactly 20 rentals", 20, customer2.getRentals().size());
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup: Create Customer C003 with 1 active rental that is overdue
        RentalItem overdueRental = new RentalItem();
        overdueRental.setRentalId("R30");
        overdueRental.setCustomer(customer3);
        overdueRental.setTape(tape3);
        overdueRental.setRentalDate(LocalDate.of(2025, 1, 1));
        overdueRental.setDueDate(LocalDate.of(2025, 1, 5)); // Due date is 2025-01-05
        overdueRental.setReturnDate(null); // Not returned, current date is 2025-01-05 (3 days overdue from due date)
        // Actually, the test says due_date="2025-01-05" and current_date="2025-01-05" - this should be 0 days overdue
        // Let me correct: due_date="2025-01-02", current_date="2025-01-05" (3 days overdue)
        overdueRental.setDueDate(LocalDate.of(2025, 1, 2)); // Due 3 days ago
        overdueRental.setRentalDate(LocalDate.of(2024, 12, 26)); // Rented 10 days ago
        
        rentalService.getRentals().add(overdueRental);
        customer3.getRentals().add(overdueRental);
        
        // Input: C003 rents tape "T003" with current_date="2025-01-05"
        boolean result = rentalService.addVideoTapeRental("C003", "T003", LocalDate.of(2025, 1, 5));
        
        // Expected Output: False
        assertFalse("Rental should fail when customer has overdue fees", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup: Create Tape T004 with active rental by another customer
        RentalItem existingRental = new RentalItem();
        existingRental.setRentalId("R40");
        existingRental.setCustomer(otherCustomer10);
        existingRental.setTape(tape4);
        existingRental.setRentalDate(LocalDate.of(2024, 12, 25));
        existingRental.setDueDate(LocalDate.of(2025, 1, 5));
        existingRental.setReturnDate(null); // Still rented out
        
        rentalService.getRentals().add(existingRental);
        otherCustomer10.getRentals().add(existingRental);
        
        // Input: C004 rents tape "T004" with current_date="2025-01-01"
        boolean result = rentalService.addVideoTapeRental("C004", "T004", LocalDate.of(2025, 1, 1));
        
        // Expected Output: False
        assertFalse("Rental should fail when tape is unavailable", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup: Create Customer C005 with 20 active rentals and one overdue rental
        for (int i = 1; i <= 20; i++) {
            RentalItem rental = new RentalItem();
            rental.setRentalId("R" + (i + 50));
            rental.setCustomer(customer5);
            rental.setTape(tape3); // Using different tape
            rental.setRentalDate(LocalDate.of(2025, 1, i));
            rental.setDueDate(LocalDate.of(2025, 1, i).plusDays(7));
            rental.setReturnDate(null);
            
            rentalService.getRentals().add(rental);
            customer5.getRentals().add(rental);
        }
        
        // Add one overdue rental for customer5
        RentalItem overdueRental = new RentalItem();
        overdueRental.setRentalId("R71");
        overdueRental.setCustomer(customer5);
        overdueRental.setTape(tape4);
        overdueRental.setRentalDate(LocalDate.of(2024, 12, 25));
        overdueRental.setDueDate(LocalDate.of(2024, 12, 31)); // Due date was 2024-12-31
        overdueRental.setReturnDate(null); // Not returned, current date is 2025-01-01 (1 day overdue)
        
        rentalService.getRentals().add(overdueRental);
        customer5.getRentals().add(overdueRental);
        
        // Setup: Create Tape T005 with active rental by another customer
        RentalItem existingRental = new RentalItem();
        existingRental.setRentalId("R80");
        existingRental.setCustomer(otherCustomer11);
        existingRental.setTape(tape5);
        existingRental.setRentalDate(LocalDate.of(2024, 12, 25));
        existingRental.setDueDate(LocalDate.of(2025, 1, 5));
        existingRental.setReturnDate(null); // Still rented out
        
        rentalService.getRentals().add(existingRental);
        otherCustomer11.getRentals().add(existingRental);
        
        // Input: C005 rents tape "T005" with current_date="2025-01-01"
        boolean result = rentalService.addVideoTapeRental("C005", "T005", LocalDate.of(2025, 1, 1));
        
        // Expected Output: False
        assertFalse("Rental should fail when all conditions are violated", result);
    }
}