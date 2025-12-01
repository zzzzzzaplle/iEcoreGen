import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;

public class CR5Test {
    
    private Customer customer;
    
    @Before
    public void setUp() {
        customer = new Customer();
    }
    
    @Test
    public void testCase1_noRentalsExist() {
        // Setup: Create Customer C001 with empty rentals list
        customer.setId("C001");
        customer.setRentals(new java.util.ArrayList<VideoRental>());
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: No active rentals expected
        assertTrue("Expected empty list when no rentals exist", result.isEmpty());
    }
    
    @Test
    public void testCase2_allTapesReturned() {
        // Setup: Create Customer C002 with one returned rental
        customer.setId("C002");
        
        // Create tape and rental
        Tape tape1 = new Tape();
        tape1.setId("T001");
        
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setReturnDate(LocalDate.of(2025, 1, 1));
        
        // Add rental to customer
        List<VideoRental> rentals = new java.util.ArrayList<VideoRental>();
        rentals.add(rental1);
        customer.setRentals(rentals);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: No unreturned rentals expected
        assertTrue("Expected empty list when all tapes are returned", result.isEmpty());
    }
    
    @Test
    public void testCase3_oneUnreturnedTape() {
        // Setup: Create Customer C003 with one unreturned rental
        customer.setId("C003");
        
        // Create tape and rental
        Tape tape1 = new Tape();
        tape1.setId("T001");
        
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setReturnDate(null); // Not returned
        
        // Add rental to customer
        List<VideoRental> rentals = new java.util.ArrayList<VideoRental>();
        rentals.add(rental1);
        customer.setRentals(rentals);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: List containing T001 expected
        assertEquals("Expected exactly one unreturned tape", 1, result.size());
        assertEquals("Expected tape T001", "T001", result.get(0).getId());
    }
    
    @Test
    public void testCase4_mixedReturnedUnreturned() {
        // Setup: Create Customer C004 with mixed returned and unreturned rentals
        customer.setId("C004");
        
        // Create tapes
        Tape tape1 = new Tape();
        tape1.setId("T001");
        
        Tape tape2 = new Tape();
        tape2.setId("T002");
        
        // Create rentals - one returned, one unreturned
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setReturnDate(LocalDate.of(2025, 1, 1)); // Returned
        
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setReturnDate(null); // Not returned
        
        // Add rentals to customer
        List<VideoRental> rentals = new java.util.ArrayList<VideoRental>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: List containing only T002 expected
        assertEquals("Expected exactly one unreturned tape", 1, result.size());
        assertEquals("Expected tape T002", "T002", result.get(0).getId());
    }
    
    @Test
    public void testCase5_multipleUnreturnedTapes() {
        // Setup: Create Customer C005 with multiple unreturned rentals
        customer.setId("C005");
        
        // Create tapes
        Tape tape1 = new Tape();
        tape1.setId("T001");
        
        Tape tape2 = new Tape();
        tape2.setId("T002");
        
        // Create rentals - both unreturned
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setReturnDate(null); // Not returned
        
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setReturnDate(null); // Not returned
        
        // Add rentals to customer
        List<VideoRental> rentals = new java.util.ArrayList<VideoRental>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: List containing T001 and T002 expected
        assertEquals("Expected exactly two unreturned tapes", 2, result.size());
        
        // Check that both tapes are in the result
        boolean foundT001 = false;
        boolean foundT002 = false;
        for (Tape tape : result) {
            if ("T001".equals(tape.getId())) {
                foundT001 = true;
            } else if ("T002".equals(tape.getId())) {
                foundT002 = true;
            }
        }
        
        assertTrue("Expected to find tape T001", foundT001);
        assertTrue("Expected to find tape T002", foundT002);
    }
}