import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: Empty list, no active rentals
        assertTrue("Expected empty list when no rentals exist", result.isEmpty());
    }

    @Test
    public void testCase2_AllTapesReturned() {
        // Setup: Create Customer C002
        customer.setId("C002");
        List<VideoRental> rentals = new ArrayList<VideoRental>();
        
        // Create Tape T001
        tape1.setId("T001");
        
        // Create VideoRental with return date set
        rental1.setTape(tape1);
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-01");
        
        rentals.add(rental1);
        customer.setRentals(rentals);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: Empty list, all rentals returned
        assertTrue("Expected empty list when all tapes are returned", result.isEmpty());
    }

    @Test
    public void testCase3_OneUnreturnedTape() {
        // Setup: Create Customer C003
        customer.setId("C003");
        List<VideoRental> rentals = new ArrayList<VideoRental>();
        
        // Create Tape T001
        tape1.setId("T001");
        
        // Create VideoRental with null return date
        rental1.setTape(tape1);
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate(null);
        
        rentals.add(rental1);
        customer.setRentals(rentals);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: List containing T001
        assertEquals("Expected 1 unreturned tape", 1, result.size());
        assertEquals("Expected tape T001 in result", "T001", result.get(0).getId());
    }

    @Test
    public void testCase4_MixedReturnedUnreturned() {
        // Setup: Create Customer C004
        customer.setId("C004");
        List<VideoRental> rentals = new ArrayList<VideoRental>();
        
        // Create Tapes T001 and T002
        tape1.setId("T001");
        tape2.setId("T002");
        
        // Create VideoRental for T001 with return date set
        rental1.setTape(tape1);
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-01");
        
        // Create VideoRental for T002 with null return date
        rental2.setTape(tape2);
        rental2.setDueDate("2025-01-06");
        rental2.setReturnDate(null);
        
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: List containing T002 only
        assertEquals("Expected 1 unreturned tape", 1, result.size());
        assertEquals("Expected tape T002 in result", "T002", result.get(0).getId());
    }

    @Test
    public void testCase5_MultipleUnreturnedTapes() {
        // Setup: Create Customer C005
        customer.setId("C005");
        List<VideoRental> rentals = new ArrayList<VideoRental>();
        
        // Create Tapes T001 and T002
        tape1.setId("T001");
        tape2.setId("T002");
        
        // Create VideoRental for T001 with null return date
        rental1.setTape(tape1);
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate(null);
        
        // Create VideoRental for T002 with null return date
        rental2.setTape(tape2);
        rental2.setDueDate("2025-01-06");
        rental2.setReturnDate(null);
        
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: List containing T001 and T002
        assertEquals("Expected 2 unreturned tapes", 2, result.size());
        
        // Check that both tapes are present in the result
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
        
        assertTrue("Expected T001 in result", foundT001);
        assertTrue("Expected T002 in result", foundT002);
    }
}