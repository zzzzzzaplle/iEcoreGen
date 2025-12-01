import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CR5Test {
    
    private Customer customer;
    private Tape tape1;
    private Tape tape2;
    private VideoRental rental1;
    private VideoRental rental2;
    
    @Before
    public void setUp() {
        // Common setup for tests that need objects
        customer = new Customer();
        tape1 = new Tape();
        tape2 = new Tape();
        rental1 = new VideoRental();
        rental2 = new VideoRental();
    }
    
    @Test
    public void testCase1_NoRentalsExist() {
        // Setup: Create Customer C001 with empty rentals list
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Execute: Get unreturned tapes
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify: Empty list, no active rentals
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase2_AllTapesReturned() {
        // Setup: Create Customer C002
        Customer customer = new Customer();
        customer.setId("C002");
        
        // Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        
        // Create VideoRental with return_date set
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setReturnDate(LocalDate.parse("2025-01-01"));
        
        // Add rental to customer
        customer.getRentals().add(rental);
        
        // Execute: Get unreturned tapes
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify: Empty list, all rentals returned
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase3_OneUnreturnedTape() {
        // Setup: Create Customer C003
        Customer customer = new Customer();
        customer.setId("C003");
        
        // Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        
        // Create VideoRental with return_date=null
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        
        // Add rental to customer
        customer.getRentals().add(rental);
        
        // Execute: Get unreturned tapes
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify: List containing T001
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain exactly 1 item", 1, result.size());
        assertEquals("Should contain T001", "T001", result.get(0));
    }
    
    @Test
    public void testCase4_MixedReturnedUnreturned() {
        // Setup: Create Customer C004
        Customer customer = new Customer();
        customer.setId("C004");
        
        // Create Tape T001 and T002
        Tape tape1 = new Tape();
        tape1.setId("T001");
        Tape tape2 = new Tape();
        tape2.setId("T002");
        
        // Create VideoRental for T001 with return_date set
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setReturnDate(LocalDate.parse("2025-01-01"));
        
        // Create VideoRental for T002 with return_date=null
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute: Get unreturned tapes
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify: List containing T002
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain exactly 1 item", 1, result.size());
        assertEquals("Should contain T002", "T002", result.get(0));
    }
    
    @Test
    public void testCase5_MultipleUnreturnedTapes() {
        // Setup: Create Customer C005
        Customer customer = new Customer();
        customer.setId("C005");
        
        // Create Tapes T001 and T002
        Tape tape1 = new Tape();
        tape1.setId("T001");
        Tape tape2 = new Tape();
        tape2.setId("T002");
        
        // Create VideoRental for T001 with return_date=null
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        
        // Create VideoRental for T002 with return_date=null
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute: Get unreturned tapes
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify: List containing T001 and T002
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain exactly 2 items", 2, result.size());
        assertTrue("Should contain T001", result.contains("T001"));
        assertTrue("Should contain T002", result.contains("T002"));
    }
}