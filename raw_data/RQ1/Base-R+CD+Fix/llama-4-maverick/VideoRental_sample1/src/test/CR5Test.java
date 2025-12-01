import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class CR5Test {
    
    private Customer customer;
    private Tape tape1;
    private Tape tape2;
    private VideoRental rental1;
    private VideoRental rental2;
    
    @Before
    public void setUp() {
        customer = new Customer();
        tape1 = new Tape();
        tape2 = new Tape();
        rental1 = new VideoRental();
        rental2 = new VideoRental();
    }
    
    @Test
    public void testCase1_NoRentalsExist() {
        // Setup: Create Customer C001 with empty rentals list
        customer.setId("C001");
        customer.setRentals(new ArrayList<VideoRental>());
        
        // Execute: Get unreturned tapes
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify: Empty list, no active rentals
        assertTrue("Expected empty list for customer with no rentals", result.isEmpty());
    }
    
    @Test
    public void testCase2_AllTapesReturned() {
        // Setup: Create Customer C002
        customer.setId("C002");
        
        // Create Tape T001
        tape1.setId("T001");
        
        // Create VideoRental with return date set (returned)
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 1));
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        customer.setRentals(rentals);
        
        // Execute: Get unreturned tapes
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify: Empty list, all rentals returned
        assertTrue("Expected empty list when all tapes are returned", result.isEmpty());
    }
    
    @Test
    public void testCase3_OneUnreturnedTape() {
        // Setup: Create Customer C003
        customer.setId("C003");
        
        // Create Tape T001
        tape1.setId("T001");
        
        // Create VideoRental with return_date=null (unreturned)
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(null); // Not returned
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        customer.setRentals(rentals);
        
        // Execute: Get unreturned tapes
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify: List containing T001
        assertEquals("Expected 1 unreturned tape", 1, result.size());
        assertTrue("Expected T001 in unreturned list", result.contains("T001"));
    }
    
    @Test
    public void testCase4_MixedReturnedUnreturned() {
        // Setup: Create Customer C004
        customer.setId("C004");
        
        // Create Tapes T001 and T002
        tape1.setId("T001");
        tape2.setId("T002");
        
        // Create VideoRental for T001 with return_date set (returned)
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 1)); // Returned
        
        // Create VideoRental for T002 with return_date=null (unreturned)
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDate.of(2025, 1, 6));
        rental2.setReturnDate(null); // Not returned
        
        // Add rentals to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Execute: Get unreturned tapes
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify: List containing only T002
        assertEquals("Expected 1 unreturned tape", 1, result.size());
        assertTrue("Expected T002 in unreturned list", result.contains("T002"));
        assertFalse("T001 should not be in unreturned list", result.contains("T001"));
    }
    
    @Test
    public void testCase5_MultipleUnreturnedTapes() {
        // Setup: Create Customer C005
        customer.setId("C005");
        
        // Create Tapes T001 and T002
        tape1.setId("T001");
        tape2.setId("T002");
        
        // Create VideoRental for T001 with return_date=null (unreturned)
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(null); // Not returned
        
        // Create VideoRental for T002 with return_date=null (unreturned)
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDate.of(2025, 1, 6));
        rental2.setReturnDate(null); // Not returned
        
        // Add rentals to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Execute: Get unreturned tapes
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify: List containing T001 and T002
        assertEquals("Expected 2 unreturned tapes", 2, result.size());
        assertTrue("Expected T001 in unreturned list", result.contains("T001"));
        assertTrue("Expected T002 in unreturned list", result.contains("T002"));
    }
}