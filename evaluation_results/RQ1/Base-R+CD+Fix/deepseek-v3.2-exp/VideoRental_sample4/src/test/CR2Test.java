import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Tape tape;
    private Customer customer;
    
    @Before
    public void setUp() {
        // Reset objects before each test
        tape = null;
        customer = null;
    }
    
    @Test
    public void testCase1_TapeIsAvailable() {
        // Test Case 1: "Tape is available"
        // Input: tape_id="T001", current_date="2025-01-01"
        
        // Setup: Create Tape with id="T001", No active rentals for T001
        tape = new Tape();
        tape.setId("T001");
        
        // Expected Output: True
        assertTrue("Tape T001 should be available", tape.isAvailable("2025-01-01"));
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() {
        // Test Case 2: "Tape is rented out"
        // Input: tape_id="T002", current_date="2025-01-01"
        
        // Setup: Create Tape with id="T002", Create Customer C001
        tape = new Tape();
        tape.setId("T002");
        customer = new Customer();
        customer.setId("C001");
        
        // C001 rents T002 with rental date="2024-12-28", due_date="2025-01-05", return_date=null (unreturned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate("2025-01-05");
        rental.setReturnDate(null);
        
        // Add rental to customer
        customer.getRentals().add(rental);
        
        // Add rental to tape
        tape.getRentals().add(rental);
        
        // Expected Output: False
        assertFalse("Tape T002 should not be available (rented out)", tape.isAvailable("2025-01-01"));
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Test Case 3: "Tape was rented but returned"
        // Input: tape_id="T003", current_date="2025-01-01"
        
        // Setup: Create Tape with id="T003", Create Customer C002
        tape = new Tape();
        tape.setId("T003");
        customer = new Customer();
        customer.setId("C002");
        
        // C002 rents T003 with rental date="2024-12-25", due_date="2024-12-30", return_date="2024-12-31"
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate("2024-12-30");
        rental.setReturnDate("2024-12-31");
        
        // Add rental to customer
        customer.getRentals().add(rental);
        
        // Add rental to tape
        tape.getRentals().add(rental);
        
        // Expected Output: True
        assertTrue("Tape T003 should be available (returned)", tape.isAvailable("2025-01-01"));
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Test Case 4: "Tape exists but has overdue rental"
        // Input: tape_id="T004", current_date="2025-01-10"
        
        // Setup: Create Tape with id="T004", Create Customer C003
        tape = new Tape();
        tape.setId("T004");
        customer = new Customer();
        customer.setId("C003");
        
        // C003 rents T004 with rental date="2024-12-28", due_date="2025-01-01", return_date=null (unreturned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate("2025-01-01");
        rental.setReturnDate(null);
        
        // Add rental to customer
        customer.getRentals().add(rental);
        
        // Add rental to tape
        tape.getRentals().add(rental);
        
        // Expected Output: False
        assertFalse("Tape T004 should not be available (overdue rental)", tape.isAvailable("2025-01-10"));
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Test Case 5: "Tape was rented but returned by multiple customers"
        // Input: tape_id="T005", current_date="2025-01-10"
        
        // Setup: Create Tape with id="T005", Create Customer C004, C005
        tape = new Tape();
        tape.setId("T005");
        Customer customer1 = new Customer();
        customer1.setId("C004");
        Customer customer2 = new Customer();
        customer2.setId("C005");
        
        // First rental: C004 rents T005 with rental date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01"
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape);
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-01");
        
        // Add first rental to customer1 and tape
        customer1.getRentals().add(rental1);
        tape.getRentals().add(rental1);
        
        // The first creation: True
        assertTrue("After first rental (returned), tape T005 should be available", 
                  tape.isAvailable("2025-01-10"));
        
        // Second rental: C005 rents T005 with rental date="2025-01-06", due_date="2025-01-15", return_date=null
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape);
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate(null);
        
        // Add second rental to customer2 and tape
        customer2.getRentals().add(rental2);
        tape.getRentals().add(rental2);
        
        // The second creation: False
        assertFalse("After second rental (unreturned), tape T005 should not be available", 
                   tape.isAvailable("2025-01-10"));
    }
}