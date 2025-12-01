import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

public class CR5Test {
    
    private Customer customer;
    private Tape tape1;
    private Tape tape2;
    private VideoRental rental1;
    private VideoRental rental2;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
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
        
        // Execute: Call getUnreturnedTapes method
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify: Expected Output: Empty list, no active rentals
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase2_AllTapesReturned() {
        // Setup: Create Customer C002
        customer.setId("C002");
        List<VideoRental> rentals = new ArrayList<>();
        
        // Create Tape T001
        tape1.setId("T001");
        
        // Create VideoRental with return_date="2025-01-01"
        rental1.setTape(tape1);
        rental1.setReturnDate(LocalDate.parse("2025-01-01", formatter));
        rentals.add(rental1);
        
        customer.setRentals(rentals);
        
        // Execute: Call getUnreturnedTapes method
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify: Expected Output: Empty list, all rentals returned
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase3_OneUnreturnedTape() {
        // Setup: Create Customer C003
        customer.setId("C003");
        List<VideoRental> rentals = new ArrayList<>();
        
        // Create Tape T001
        tape1.setId("T001");
        
        // Create VideoRental with return_date=null
        rental1.setTape(tape1);
        rental1.setReturnDate(null);
        rentals.add(rental1);
        
        customer.setRentals(rentals);
        
        // Execute: Call getUnreturnedTapes method
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify: Expected Output: List containing T001
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly one item", 1, result.size());
        assertEquals("Result should contain T001", "T001", result.get(0));
    }
    
    @Test
    public void testCase4_MixedReturnedUnreturned() {
        // Setup: Create Customer C004
        customer.setId("C004");
        List<VideoRental> rentals = new ArrayList<>();
        
        // Create Tape T001 and T002
        tape1.setId("T001");
        tape2.setId("T002");
        
        // Create VideoRental for T001 with return_date="2025-01-01"
        rental1.setTape(tape1);
        rental1.setReturnDate(LocalDate.parse("2025-01-01", formatter));
        rentals.add(rental1);
        
        // Create VideoRental for T002 with return_date=null
        rental2.setTape(tape2);
        rental2.setReturnDate(null);
        rentals.add(rental2);
        
        customer.setRentals(rentals);
        
        // Execute: Call getUnreturnedTapes method
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify: Expected Output: List containing T002
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly one item", 1, result.size());
        assertEquals("Result should contain T002", "T002", result.get(0));
    }
    
    @Test
    public void testCase5_MultipleUnreturnedTapes() {
        // Setup: Create Customer C005
        customer.setId("C005");
        List<VideoRental> rentals = new ArrayList<>();
        
        // Create Tapes T001 and T002
        tape1.setId("T001");
        tape2.setId("T002");
        
        // Create VideoRental for T001 with return_date=null
        rental1.setTape(tape1);
        rental1.setReturnDate(null);
        rentals.add(rental1);
        
        // Create VideoRental for T002 with return_date=null
        rental2.setTape(tape2);
        rental2.setReturnDate(null);
        rentals.add(rental2);
        
        customer.setRentals(rentals);
        
        // Execute: Call getUnreturnedTapes method
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify: Expected Output: List containing T001 and T002
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly two items", 2, result.size());
        assertTrue("Result should contain T001", result.contains("T001"));
        assertTrue("Result should contain T002", result.contains("T002"));
    }
}