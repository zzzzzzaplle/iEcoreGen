import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;

public class CR2Test {
    
    @Before
    public void setUp() {
        // Clear all rentals before each test to ensure isolation
        VideoRental.getAllRentals().clear();
    }
    
    @After
    public void tearDown() {
        // Clean up after each test
        VideoRental.getAllRentals().clear();
    }
    
    @Test
    public void testCase1_TapeIsAvailable() {
        // Test Case 1: "Tape is available"
        // Setup: Create Tape with id="T001", no active rentals for T001
        Tape tape = new Tape();
        tape.setId("T001");
        
        LocalDate currentDate = LocalDate.of(2025, 1, 1);
        
        // Verify tape is available
        assertTrue(tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() {
        // Test Case 2: "Tape is rented out"
        // Setup: Create Tape with id="T002", Create Customer C001
        Tape tape = new Tape();
        tape.setId("T002");
        
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Create an active rental for T002 with return_date=null
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setReturnDate(null);
        customer.getRentals().add(rental);
        
        LocalDate currentDate = LocalDate.of(2025, 1, 1);
        
        // Verify tape is not available
        assertFalse(tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Test Case 3: "Tape was rented but returned"
        // Setup: Create Tape with id="T003", Create Customer C002
        Tape tape = new Tape();
        tape.setId("T003");
        
        Customer customer = new Customer();
        customer.setId("C002");
        
        // Create a rental for T003 that was returned
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setReturnDate(LocalDate.of(2024, 12, 31));
        customer.getRentals().add(rental);
        
        LocalDate currentDate = LocalDate.of(2025, 1, 1);
        
        // Verify tape is available (returned before current date)
        assertTrue(tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Test Case 4: "Tape exists but has overdue rental"
        // Setup: Create Tape with id="T004", Create Customer C003
        Tape tape = new Tape();
        tape.setId("T004");
        
        Customer customer = new Customer();
        customer.setId("C003");
        
        // Create an overdue rental for T004 with return_date=null
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setReturnDate(null);
        customer.getRentals().add(rental);
        
        LocalDate currentDate = LocalDate.of(2025, 1, 10);
        
        // Verify tape is not available (overdue and unreturned)
        assertFalse(tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Test Case 5: "Tape was rented but returned by multiple customers"
        // Setup: Create Tape with id="T005", Create Customers C004 and C005
        Tape tape = new Tape();
        tape.setId("T005");
        
        Customer customer1 = new Customer();
        customer1.setId("C004");
        
        Customer customer2 = new Customer();
        customer2.setId("C005");
        
        LocalDate currentDate = LocalDate.of(2025, 1, 10);
        
        // First rental: returned before current date
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape);
        rental1.setReturnDate(LocalDate.of(2025, 1, 1));
        customer1.getRentals().add(rental1);
        
        // Verify tape is available after first rental (returned)
        assertTrue(tape.isAvailable(currentDate));
        
        // Second rental: unreturned
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape);
        rental2.setReturnDate(null);
        customer2.getRentals().add(rental2);
        
        // Verify tape is not available after second rental (unreturned)
        assertFalse(tape.isAvailable(currentDate));
    }
}