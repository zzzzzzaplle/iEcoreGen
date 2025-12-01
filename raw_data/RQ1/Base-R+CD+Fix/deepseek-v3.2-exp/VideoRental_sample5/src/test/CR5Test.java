import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR5Test {
    
    private Customer customer;
    
    @Before
    public void setUp() {
        customer = new Customer();
    }
    
    @Test
    public void testCase1_NoRentalsExist() {
        // Setup: Create Customer C001 with empty rentals list
        customer.setId("C001");
        
        // Execute: Get unreturned tapes
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify: Empty list, no active rentals
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase2_AllTapesReturned() {
        // Setup: Create Customer C002
        customer.setId("C002");
        
        // Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        
        // Create VideoRental with return date
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate("2025-01-05");
        rental.setReturnDate("2025-01-01");
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Execute: Get unreturned tapes
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify: Empty list, all rentals returned
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_OneUnreturnedTape() {
        // Setup: Create Customer C003
        customer.setId("C003");
        
        // Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        
        // Create VideoRental without return date (unreturned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate("2025-01-05");
        rental.setReturnDate(null);
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Execute: Get unreturned tapes
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify: List containing T001
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("T001", result.get(0));
    }
    
    @Test
    public void testCase4_MixedReturnedUnreturned() {
        // Setup: Create Customer C004
        customer.setId("C004");
        
        // Create Tapes T001 and T002
        Tape tape1 = new Tape();
        tape1.setId("T001");
        Tape tape2 = new Tape();
        tape2.setId("T002");
        
        // Create VideoRental for T001 with return date (returned)
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-01");
        
        // Create VideoRental for T002 without return date (unreturned)
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate("2025-01-06");
        rental2.setReturnDate(null);
        
        // Add rentals to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Execute: Get unreturned tapes
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify: List containing T002 only
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("T002", result.get(0));
    }
    
    @Test
    public void testCase5_MultipleUnreturnedTapes() {
        // Setup: Create Customer C005
        customer.setId("C005");
        
        // Create Tapes T001 and T002
        Tape tape1 = new Tape();
        tape1.setId("T001");
        Tape tape2 = new Tape();
        tape2.setId("T002");
        
        // Create VideoRental for T001 without return date (unreturned)
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate(null);
        
        // Create VideoRental for T002 without return date (unreturned)
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate("2025-01-06");
        rental2.setReturnDate(null);
        
        // Add rentals to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Execute: Get unreturned tapes
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify: List containing T001 and T002
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("T001"));
        assertTrue(result.contains("T002"));
    }
}