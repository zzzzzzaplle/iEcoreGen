import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;

public class CR5Test {
    
    private Customer customer;
    
    @Before
    public void setUp() {
        // Clear static rentals list before each test to ensure isolation
        List<VideoRental> allRentals = VideoRental.getAllRentals();
        if (allRentals instanceof java.util.ArrayList) {
            ((java.util.ArrayList<VideoRental>) allRentals).clear();
        }
    }
    
    @Test
    public void testCase1_NoRentalsExist() {
        // Setup: Create Customer C001 with empty rentals list
        customer = new Customer();
        customer.setId("C001");
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: No active rentals expected
        assertTrue("Expected empty list when no rentals exist", result.isEmpty());
    }
    
    @Test
    public void testCase2_AllTapesReturned() {
        // Setup: Create Customer C002 with one returned rental
        customer = new Customer();
        customer.setId("C002");
        
        Tape tape1 = new Tape();
        tape1.setId("T001");
        
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setReturnDate(LocalDate.of(2025, 1, 1));
        
        customer.getRentals().add(rental1);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: No unreturned rentals expected
        assertTrue("Expected empty list when all tapes are returned", result.isEmpty());
    }
    
    @Test
    public void testCase3_OneUnreturnedTape() {
        // Setup: Create Customer C003 with one unreturned rental
        customer = new Customer();
        customer.setId("C003");
        
        Tape tape1 = new Tape();
        tape1.setId("T001");
        
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setReturnDate(null); // Not returned
        
        customer.getRentals().add(rental1);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: List containing only T001
        assertEquals("Expected exactly one unreturned tape", 1, result.size());
        assertEquals("Expected tape T001 to be in the list", "T001", result.get(0).getId());
    }
    
    @Test
    public void testCase4_MixedReturnedUnreturned() {
        // Setup: Create Customer C004 with one returned and one unreturned rental
        customer = new Customer();
        customer.setId("C004");
        
        Tape tape1 = new Tape();
        tape1.setId("T001");
        
        Tape tape2 = new Tape();
        tape2.setId("T002");
        
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setReturnDate(LocalDate.of(2025, 1, 1)); // Returned
        
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setReturnDate(null); // Not returned
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: List containing only T002
        assertEquals("Expected exactly one unreturned tape", 1, result.size());
        assertEquals("Expected tape T002 to be in the list", "T002", result.get(0).getId());
    }
    
    @Test
    public void testCase5_MultipleUnreturnedTapes() {
        // Setup: Create Customer C005 with two unreturned rentals
        customer = new Customer();
        customer.setId("C005");
        
        Tape tape1 = new Tape();
        tape1.setId("T001");
        
        Tape tape2 = new Tape();
        tape2.setId("T002");
        
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setReturnDate(null); // Not returned
        
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setReturnDate(null); // Not returned
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: List containing T001 and T002
        assertEquals("Expected exactly two unreturned tapes", 2, result.size());
        
        // Check that both tapes are in the result
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
        assertTrue("Expected tape T001 to be in the list", foundT001);
        assertTrue("Expected tape T002 to be in the list", foundT002);
    }
}