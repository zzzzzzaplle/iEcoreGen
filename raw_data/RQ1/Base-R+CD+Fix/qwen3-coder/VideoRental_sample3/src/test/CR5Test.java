import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR5Test {
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_noRentalsExist() throws ParseException {
        // Test Case 1: "No rentals exist"
        // Input: customer_id="C001"
        // Setup: Create Customer C001 with empty rentals list
        Customer customer = new Customer();
        customer.setId("C001");
        customer.setRentals(new ArrayList<VideoRental>());
        
        // Expected Output: Empty list, no active rentals.
        List<Tape> result = customer.getUnreturnedTapes();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase2_allTapesReturned() throws ParseException {
        // Test Case 2: "All tapes returned"
        // Input: customer_id="C002"
        // Setup:
        // 1. Create Customer C002
        Customer customer = new Customer();
        customer.setId("C002");
        
        // 2. Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        tape.setVideoInformation("Movie A");
        
        // 3. Create VideoRental with rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01", associated with C002 and T001
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental.setReturnDate(dateFormat.parse("2025-01-01 00:00:00"));
        rental.setOwnedPastDueAmount(0.0);
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Expected Output: Empty list, all rentals returned.
        List<Tape> result = customer.getUnreturnedTapes();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_oneUnreturnedTape() throws ParseException {
        // Test Case 3: "One unreturned tape"
        // Input: customer_id="C003"
        // Setup:
        // 1. Create Customer C003
        Customer customer = new Customer();
        customer.setId("C003");
        
        // 2. Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        tape.setVideoInformation("Movie B");
        
        // 3. Create VideoRental with rental_date="2025-01-01", due_date="2025-01-05", return_date=null, associated with C003 and T001
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental.setReturnDate(null);
        rental.setOwnedPastDueAmount(0.0);
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Expected Output: List containing T001.
        List<Tape> result = customer.getUnreturnedTapes();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("T001", result.get(0).getId());
    }
    
    @Test
    public void testCase4_mixedReturnedUnreturned() throws ParseException {
        // Test Case 4: "Mixed returned/unreturned"
        // Input: customer_id="C004"
        // Setup:
        // 1. Create Customer C004
        Customer customer = new Customer();
        customer.setId("C004");
        
        // 2. Create Tape T001 and T002
        Tape tape1 = new Tape();
        tape1.setId("T001");
        tape1.setVideoInformation("Movie C");
        
        Tape tape2 = new Tape();
        tape2.setId("T002");
        tape2.setVideoInformation("Movie D");
        
        // 3. Create VideoRental for T001 with rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01", associated with C004
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-01 00:00:00"));
        rental1.setOwnedPastDueAmount(0.0);
        
        // 4. Create VideoRental for T002 with rental_date="2025-01-02", due_date="2025-01-06", return_date=null, associated with C004
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-06 00:00:00"));
        rental2.setReturnDate(null);
        rental2.setOwnedPastDueAmount(0.0);
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Expected Output: List containing T002.
        List<Tape> result = customer.getUnreturnedTapes();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("T002", result.get(0).getId());
    }
    
    @Test
    public void testCase5_multipleUnreturnedTapes() throws ParseException {
        // Test Case 5: "Multiple unreturned tapes"
        // Input: customer_id="C005"
        // Setup:
        // 1. Create Customer C005
        Customer customer = new Customer();
        customer.setId("C005");
        
        // 2. Create Tapes T001 and T002
        Tape tape1 = new Tape();
        tape1.setId("T001");
        tape1.setVideoInformation("Movie E");
        
        Tape tape2 = new Tape();
        tape2.setId("T002");
        tape2.setVideoInformation("Movie F");
        
        // 3. Create VideoRental for T001 with rental_date="2025-01-01", due_date="2025-01-05", return_date=null, associated with C005
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(null);
        rental1.setOwnedPastDueAmount(0.0);
        
        // 4. Create VideoRental for T002 with rental_date="2025-01-02", due_date="2025-01-06", return_date=null, associated with C005
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-06 00:00:00"));
        rental2.setReturnDate(null);
        rental2.setOwnedPastDueAmount(0.0);
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Expected Output: List containing T001 and T002.
        List<Tape> result = customer.getUnreturnedTapes();
        assertNotNull(result);
        assertEquals(2, result.size());
        
        // Check that both tapes are in the result
        List<String> tapeIds = Arrays.asList(result.get(0).getId(), result.get(1).getId());
        assertTrue(tapeIds.contains("T001"));
        assertTrue(tapeIds.contains("T002"));
    }
}