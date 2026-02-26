import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    private List<Customer> customers;
    private List<Tape> tapes;
    
    @Before
    public void setUp() {
        customers = new ArrayList<>();
        tapes = new ArrayList<>();
    }
    
    /**
     * Test Case 1: "Tape is available"
     * Input: tape_id="T001", current_date="2025-01-01"
     * Setup: No active rentals for T001
     * Expected Output: True
     */
    @Test
    public void testCase1_tapeIsAvailable() {
        // Setup
        Tape tape = new Tape();
        tape.setId("T001");
        String currentDate = "2025-01-01";
        
        // Execute & Verify
        assertTrue(tape.isAvailable(currentDate));
    }
    
    /**
     * Test Case 2: "Tape is rented out"
     * Input: tape_id="T002", current_date="2025-01-01"
     * Setup: T002 is currently rented out (unreturned)
     * Expected Output: False
     */
    @Test
    public void testCase2_tapeIsRentedOut() {
        // Setup
        Tape tape = new Tape();
        tape.setId("T002");
        String currentDate = "2025-01-01";
        
        Customer customer = new Customer();
        customer.setId("C001");
        
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate("2025-01-05");
        rental.setReturnDate(null); // Unreturned
        
        customer.getRentals().add(rental);
        customers.add(customer);
        
        // Execute & Verify
        assertFalse(tape.isAvailable(currentDate));
    }
    
    /**
     * Test Case 3: "Tape was rented but returned"
     * Input: tape_id="T003", current_date="2025-01-01"
     * Setup: T003 was rented and returned before current date
     * Expected Output: True
     */
    @Test
    public void testCase3_tapeWasRentedButReturned() {
        // Setup
        Tape tape = new Tape();
        tape.setId("T003");
        String currentDate = "2025-01-01";
        
        Customer customer = new Customer();
        customer.setId("C002");
        
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate("2024-12-30");
        rental.setReturnDate("2024-12-31"); // Returned before current date
        
        customer.getRentals().add(rental);
        customers.add(customer);
        
        // Execute & Verify
        assertTrue(tape.isAvailable(currentDate));
    }
    
    /**
     * Test Case 4: "Tape exists but has overdue rental"
     * Input: tape_id="T004", current_date="2025-01-10"
     * Setup: T004 is overdue and unreturned
     * Expected Output: False
     */
    @Test
    public void testCase4_tapeHasOverdueRental() {
        // Setup
        Tape tape = new Tape();
        tape.setId("T004");
        String currentDate = "2025-01-10";
        
        Customer customer = new Customer();
        customer.setId("C003");
        
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate("2025-01-01");
        rental.setReturnDate(null); // Unreturned and overdue
        
        customer.getRentals().add(rental);
        customers.add(customer);
        
        // Execute & Verify
        assertFalse(tape.isAvailable(currentDate));
    }
    
    /**
     * Test Case 5: "Tape was rented but returned by multiple customers"
     * Input: tape_id="T005", current_date="2025-01-10"
     * Setup: First rental returned, second rental still active
     * Expected Output: False (due to second active rental)
     */
    @Test
    public void testCase5_tapeRentedByMultipleCustomers() {
        // Setup
        Tape tape = new Tape();
        tape.setId("T005");
        String currentDate = "2025-01-10";
        
        // First customer - returned rental
        Customer customer1 = new Customer();
        customer1.setId("C004");
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape);
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-01"); // Returned
        customer1.getRentals().add(rental1);
        
        // Second customer - active rental
        Customer customer2 = new Customer();
        customer2.setId("C005");
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape);
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate(null); // Still active
        customer2.getRentals().add(rental2);
        
        customers.add(customer1);
        customers.add(customer2);
        
        // Execute & Verify - should be false due to active second rental
        assertFalse(tape.isAvailable(currentDate));
    }
}