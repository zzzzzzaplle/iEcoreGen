import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Tape tape;
    private Customer customer;
    private VideoRental rental;
    
    @Before
    public void setUp() {
        // Common setup that runs before each test
        tape = new Tape();
        customer = new Customer();
        rental = new VideoRental();
    }
    
    @Test
    public void testCase1_tapeIsAvailable() {
        // Test Case 1: "Tape is available"
        // Input: tape_id="T001", current_date="2025-01-01"
        
        // Setup: Create Tape with id="T001", No active rentals for T001
        tape.setId("T001");
        tape.setRentals(new ArrayList<>()); // No rentals
        
        // Verify tape availability
        Date currentDate = new Date("2025-01-01 00:00:00");
        boolean result = tape.isAvailable(currentDate);
        
        // Expected Output: True
        assertTrue("Tape T001 should be available when no active rentals exist", result);
    }
    
    @Test
    public void testCase2_tapeIsRentedOut() {
        // Test Case 2: "Tape is rented out"
        // Input: tape_id="T002", current_date="2025-01-01"
        
        // Setup: Create Tape with id="T002", Create Customer C001
        tape.setId("T002");
        customer.setId("C001");
        
        // Create rental with return_date=null (unreturned)
        rental.setTape(tape);
        rental.setDueDate(new Date("2025-01-05 00:00:00"));
        rental.setReturnDate(null); // Unreturned
        
        // Add rental to tape's rental history
        List<VideoRental> tapeRentals = new ArrayList<>();
        tapeRentals.add(rental);
        tape.setRentals(tapeRentals);
        
        // Verify tape availability
        Date currentDate = new Date("2025-01-01 00:00:00");
        boolean result = tape.isAvailable(currentDate);
        
        // Expected Output: False
        assertFalse("Tape T002 should be unavailable when it has an active rental with null return date", result);
    }
    
    @Test
    public void testCase3_tapeWasRentedButReturned() {
        // Test Case 3: "Tape was rented but returned"
        // Input: tape_id="T003", current_date="2025-01-01"
        
        // Setup: Create Tape with id="T003", Create Customer C002
        tape.setId("T003");
        customer.setId("C002");
        
        // Create rental that has been returned
        rental.setTape(tape);
        rental.setDueDate(new Date("2024-12-30 00:00:00"));
        rental.setReturnDate(new Date("2024-12-31 00:00:00")); // Returned
        
        // Add rental to tape's rental history
        List<VideoRental> tapeRentals = new ArrayList<>();
        tapeRentals.add(rental);
        tape.setRentals(tapeRentals);
        
        // Verify tape availability
        Date currentDate = new Date("2025-01-01 00:00:00");
        boolean result = tape.isAvailable(currentDate);
        
        // Expected Output: True
        assertTrue("Tape T003 should be available when all rentals have return dates", result);
    }
    
    @Test
    public void testCase4_tapeExistsButHasOverdueRental() {
        // Test Case 4: "Tape exists but has overdue rental"
        // Input: tape_id="T004", current_date="2025-01-10"
        
        // Setup: Create Tape with id="T004", Create Customer C003
        tape.setId("T004");
        customer.setId("C003");
        
        // Create overdue rental with return_date=null (unreturned)
        rental.setTape(tape);
        rental.setDueDate(new Date("2025-01-01 00:00:00")); // Due date has passed
        rental.setReturnDate(null); // Unreturned
        
        // Add rental to tape's rental history
        List<VideoRental> tapeRentals = new ArrayList<>();
        tapeRentals.add(rental);
        tape.setRentals(tapeRentals);
        
        // Verify tape availability
        Date currentDate = new Date("2025-01-10 00:00:00");
        boolean result = tape.isAvailable(currentDate);
        
        // Expected Output: False
        assertFalse("Tape T004 should be unavailable when it has an overdue rental with null return date", result);
    }
    
    @Test
    public void testCase5_tapeWasRentedButReturnedByMultipleCustomers() {
        // Test Case 5: "Tape was rented but returned by multiple customers"
        // Input: tape_id="T005", current_date="2025-01-10"
        
        // Setup: Create Tape with id="T005", Create Customers C004 and C005
        tape.setId("T005");
        Customer customer1 = new Customer();
        customer1.setId("C004");
        Customer customer2 = new Customer();
        customer2.setId("C005");
        
        // First rental: returned
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape);
        rental1.setDueDate(new Date("2025-01-05 00:00:00"));
        rental1.setReturnDate(new Date("2025-01-01 00:00:00")); // Returned
        
        // Second rental: unreturned
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape);
        rental2.setDueDate(new Date("2025-01-15 00:00:00"));
        rental2.setReturnDate(null); // Unreturned
        
        // Add both rentals to tape's rental history
        List<VideoRental> tapeRentals = new ArrayList<>();
        tapeRentals.add(rental1);
        tapeRentals.add(rental2);
        tape.setRentals(tapeRentals);
        
        // Verify tape availability - should be false due to second unreturned rental
        Date currentDate = new Date("2025-01-10 00:00:00");
        boolean result = tape.isAvailable(currentDate);
        
        // Expected Output: False (due to second unreturned rental)
        assertFalse("Tape T005 should be unavailable when it has any active rental with null return date", result);
    }
}