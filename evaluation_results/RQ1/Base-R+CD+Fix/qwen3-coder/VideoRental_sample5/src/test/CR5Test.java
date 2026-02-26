import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class CR5Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_noRentalsExist() throws Exception {
        // Setup: Create Customer C001 with empty rentals list
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: Empty list, no active rentals
        assertTrue("Expected empty list when customer has no rentals", result.isEmpty());
        assertEquals("Expected empty list size", 0, result.size());
    }
    
    @Test
    public void testCase2_allTapesReturned() throws Exception {
        // Setup: Create Customer C002
        Customer customer = new Customer();
        customer.setId("C002");
        
        // Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        tape.setVideoInformation("Movie Title");
        
        // Create VideoRental with return date (tape is returned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2025-01-05"));
        rental.setReturnDate(dateFormat.parse("2025-01-01")); // Returned before due date
        
        // Add rental to customer
        customer.getRentals().add(rental);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: Empty list, all rentals returned
        assertTrue("Expected empty list when all tapes are returned", result.isEmpty());
        assertEquals("Expected empty list size", 0, result.size());
    }
    
    @Test
    public void testCase3_oneUnreturnedTape() throws Exception {
        // Setup: Create Customer C003
        Customer customer = new Customer();
        customer.setId("C003");
        
        // Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        tape.setVideoInformation("Movie Title");
        
        // Create VideoRental with null return date (tape is unreturned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2025-01-05"));
        rental.setReturnDate(null); // Not returned
        
        // Add rental to customer
        customer.getRentals().add(rental);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: List containing T001
        assertEquals("Expected list with one unreturned tape", 1, result.size());
        assertEquals("Expected tape T001 in the list", "T001", result.get(0).getId());
    }
    
    @Test
    public void testCase4_mixedReturnedUnreturned() throws Exception {
        // Setup: Create Customer C004
        Customer customer = new Customer();
        customer.setId("C004");
        
        // Create Tape T001 and T002
        Tape tape1 = new Tape();
        tape1.setId("T001");
        tape1.setVideoInformation("Movie Title 1");
        
        Tape tape2 = new Tape();
        tape2.setId("T002");
        tape2.setVideoInformation("Movie Title 2");
        
        // Create VideoRental for T001 with return date (returned)
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05"));
        rental1.setReturnDate(dateFormat.parse("2025-01-01")); // Returned
        
        // Create VideoRental for T002 with null return date (unreturned)
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-06"));
        rental2.setReturnDate(null); // Not returned
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: List containing T002 only
        assertEquals("Expected list with one unreturned tape", 1, result.size());
        assertEquals("Expected tape T002 in the list", "T002", result.get(0).getId());
    }
    
    @Test
    public void testCase5_multipleUnreturnedTapes() throws Exception {
        // Setup: Create Customer C005
        Customer customer = new Customer();
        customer.setId("C005");
        
        // Create Tapes T001 and T002
        Tape tape1 = new Tape();
        tape1.setId("T001");
        tape1.setVideoInformation("Movie Title 1");
        
        Tape tape2 = new Tape();
        tape2.setId("T002");
        tape2.setVideoInformation("Movie Title 2");
        
        // Create VideoRental for T001 with null return date (unreturned)
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05"));
        rental1.setReturnDate(null); // Not returned
        
        // Create VideoRental for T002 with null return date (unreturned)
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-06"));
        rental2.setReturnDate(null); // Not returned
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: List containing T001 and T002
        assertEquals("Expected list with two unreturned tapes", 2, result.size());
        
        // Check that both tapes are in the list
        boolean foundT001 = false;
        boolean foundT002 = false;
        
        for (Tape tape : result) {
            if ("T001".equals(tape.getId())) {
                foundT001 = true;
            }
            if ("T002".equals(tape.getId())) {
                foundT002 = true;
            }
        }
        
        assertTrue("Expected tape T001 in the list", foundT001);
        assertTrue("Expected tape T002 in the list", foundT002);
    }
}