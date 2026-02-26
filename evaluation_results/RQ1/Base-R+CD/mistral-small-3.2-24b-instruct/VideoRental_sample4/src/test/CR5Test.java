import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class CR5Test {
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_NoRentalsExist() throws Exception {
        // Create Customer C001 with empty rentals list
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify empty list, no active rentals
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty list", result.isEmpty());
    }
    
    @Test
    public void testCase2_AllTapesReturned() throws Exception {
        // Create Customer C002
        Customer customer = new Customer();
        customer.setId("C002");
        
        // Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        
        // Create VideoRental with return date
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental.setReturnDate(dateFormat.parse("2025-01-01 00:00:00"));
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify empty list, all rentals returned
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty list", result.isEmpty());
    }
    
    @Test
    public void testCase3_OneUnreturnedTape() throws Exception {
        // Create Customer C003
        Customer customer = new Customer();
        customer.setId("C003");
        
        // Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        
        // Create VideoRental with no return date (unreturned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        // return_date remains null
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify list containing T001
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain exactly one tape", 1, result.size());
        assertEquals("Should contain T001", "T001", result.get(0).getId());
    }
    
    @Test
    public void testCase4_MixedReturnedUnreturned() throws Exception {
        // Create Customer C004
        Customer customer = new Customer();
        customer.setId("C004");
        
        // Create Tapes T001 and T002
        Tape tape1 = new Tape();
        tape1.setId("T001");
        Tape tape2 = new Tape();
        tape2.setId("T002");
        
        // Create VideoRental for T001 with return date (returned)
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-01 00:00:00"));
        
        // Create VideoRental for T002 with no return date (unreturned)
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-06 00:00:00"));
        // return_date remains null
        
        // Add rentals to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify list containing T002 only
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain exactly one tape", 1, result.size());
        assertEquals("Should contain T002", "T002", result.get(0).getId());
    }
    
    @Test
    public void testCase5_MultipleUnreturnedTapes() throws Exception {
        // Create Customer C005
        Customer customer = new Customer();
        customer.setId("C005");
        
        // Create Tapes T001 and T002
        Tape tape1 = new Tape();
        tape1.setId("T001");
        Tape tape2 = new Tape();
        tape2.setId("T002");
        
        // Create VideoRental for T001 with no return date (unreturned)
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        // return_date remains null
        
        // Create VideoRental for T002 with no return date (unreturned)
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-06 00:00:00"));
        // return_date remains null
        
        // Add rentals to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify list containing both T001 and T002
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain exactly two tapes", 2, result.size());
        
        // Check that both tapes are in the result
        List<String> tapeIds = new ArrayList<>();
        for (Tape tape : result) {
            tapeIds.add(tape.getId());
        }
        
        assertTrue("Should contain T001", tapeIds.contains("T001"));
        assertTrue("Should contain T002", tapeIds.contains("T002"));
    }
}