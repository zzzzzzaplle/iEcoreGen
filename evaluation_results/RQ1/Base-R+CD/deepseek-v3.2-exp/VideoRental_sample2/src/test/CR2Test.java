import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
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
    public void testCase1_TapeIsAvailable() {
        // Test Case 1: "Tape is available"
        // Input: tape_id="T001", current_date="2025-01-01"
        
        // Setup:
        // 1. Create Tape with id="T001"
        tape.setId("T001");
        
        // 2. No active rentals for T001
        // (No rentals created - tape should be available by default)
        
        LocalDateTime currentDate = DateUtils.parseDate("2025-01-01 00:00:00");
        
        // Expected Output: True
        assertTrue("Tape T001 should be available when no active rentals exist", 
                   tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() {
        // Test Case 2: "Tape is rented out"
        // Input: tape_id="T002", current_date="2025-01-01"
        
        // Setup:
        // 1. Create Tape with id="T002". Create Customer C001.
        tape.setId("T002");
        customer.setId("C001");
        
        // 2. C001 rents T002 with rental date="2024-12-28", due_date="2025-01-05", return_date=null (unreturned)
        rental.setTape(tape);
        rental.setDueDate(DateUtils.parseDate("2025-01-05 00:00:00"));
        rental.setReturnDate(null); // Unreturned
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        LocalDateTime currentDate = DateUtils.parseDate("2025-01-01 00:00:00");
        
        // Expected Output: False
        // Note: The Tape.isAvailable() method currently always returns true
        // This test will fail until the actual availability logic is implemented
        // Following the specification, the expected behavior should be false
        assertFalse("Tape T002 should be unavailable when actively rented out", 
                    tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Test Case 3: "Tape was rented but returned"
        // Input: tape_id="T003", current_date="2025-01-01"
        
        // Setup:
        // 1. Create Tape with id="T003". Create Customer C002.
        tape.setId("T003");
        customer.setId("C002");
        
        // 2. C002 rents T003 with rental date="2024-12-25", due_date="2024-12-30", return_date="2024-12-31"
        rental.setTape(tape);
        rental.setDueDate(DateUtils.parseDate("2024-12-30 00:00:00"));
        rental.setReturnDate(DateUtils.parseDate("2024-12-31 00:00:00")); // Returned
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        LocalDateTime currentDate = DateUtils.parseDate("2025-01-01 00:00:00");
        
        // Expected Output: True
        // Note: The Tape.isAvailable() method currently always returns true
        // This test will pass with current implementation but may need adjustment when real logic is added
        assertTrue("Tape T003 should be available when previously rented but already returned", 
                   tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Test Case 4: "Tape exists but has overdue rental"
        // Input: tape_id="T004", current_date="2025-01-10"
        
        // Setup:
        // 1. Create Tape with id="T004". Create Customer C003.
        tape.setId("T004");
        customer.setId("C003");
        
        // 2. C003 rents T004 with rental date="2024-12-28", due_date="2025-01-01", return_date=null (unreturned)
        rental.setTape(tape);
        rental.setDueDate(DateUtils.parseDate("2025-01-01 00:00:00"));
        rental.setReturnDate(null); // Unreturned and overdue
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        LocalDateTime currentDate = DateUtils.parseDate("2025-01-10 00:00:00");
        
        // Expected Output: False
        // Note: The Tape.isAvailable() method currently always returns true
        // This test will fail until the actual availability logic is implemented
        // Following the specification, the expected behavior should be false
        assertFalse("Tape T004 should be unavailable when it has an overdue rental", 
                    tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Test Case 5: "Tape was rented but returned by multiple customers"
        // Input: tape_id="T005", current_date="2025-01-10"
        
        // Setup:
        // 1. Create Tape with id="T005". Create Customer C004, C005.
        tape.setId("T005");
        Customer customer1 = new Customer();
        customer1.setId("C004");
        Customer customer2 = new Customer();
        customer2.setId("C005");
        
        // 2. C004 rents T005 with rental date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01" → first rental
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape);
        rental1.setDueDate(DateUtils.parseDate("2025-01-05 00:00:00"));
        rental1.setReturnDate(DateUtils.parseDate("2025-01-01 00:00:00")); // Returned early
        
        List<VideoRental> rentals1 = new ArrayList<>();
        rentals1.add(rental1);
        customer1.setRentals(rentals1);
        
        // 3. C005 rents T005 with rental date="2025-01-06", due_date="2025-01-15", return_date=null → second rental
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape);
        rental2.setDueDate(DateUtils.parseDate("2025-01-15 00:00:00"));
        rental2.setReturnDate(null); // Currently rented out
        
        List<VideoRental> rentals2 = new ArrayList<>();
        rentals2.add(rental2);
        customer2.setRentals(rentals2);
        
        LocalDateTime currentDate = DateUtils.parseDate("2025-01-10 00:00:00");
        
        // Expected Output: The first creation: True. The second creation: False.
        // Note: The specification seems to indicate we should check availability at different times
        // However, the test case specifies a single current_date="2025-01-10"
        // Based on the setup, at 2025-01-10, the tape should be unavailable due to C005's active rental
        
        // The tape should be unavailable due to C005's active rental on 2025-01-10
        assertFalse("Tape T005 should be unavailable when currently rented out by another customer", 
                    tape.isAvailable(currentDate));
    }
}