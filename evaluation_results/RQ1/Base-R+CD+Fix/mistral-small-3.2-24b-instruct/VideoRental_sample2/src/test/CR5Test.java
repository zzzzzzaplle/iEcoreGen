import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CR5Test {
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_noRentalsExist() {
        // Create Customer C001 with empty rentals list
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify empty list
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase2_allTapesReturned() throws ParseException {
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
        customer.getRentals().add(rental);
        
        // Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify empty list
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase3_oneUnreturnedTape() throws ParseException {
        // Create Customer C003
        Customer customer = new Customer();
        customer.setId("C003");
        
        // Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        
        // Create VideoRental without return date (unreturned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental.setReturnDate(null);
        
        // Add rental to customer
        customer.getRentals().add(rental);
        
        // Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify list contains T001
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 1 tape", 1, result.size());
        assertEquals("Should contain T001", "T001", result.get(0).getId());
    }
    
    @Test
    public void testCase4_mixedReturnedUnreturned() throws ParseException {
        // Create Customer C004
        Customer customer = new Customer();
        customer.setId("C004");
        
        // Create Tapes T001 and T002
        Tape tape1 = new Tape();
        tape1.setId("T001");
        
        Tape tape2 = new Tape();
        tape2.setId("T002");
        
        // Create VideoRental for T001 (returned)
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-01 00:00:00"));
        
        // Create VideoRental for T002 (unreturned)
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-06 00:00:00"));
        rental2.setReturnDate(null);
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify list contains only T002
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 1 tape", 1, result.size());
        assertEquals("Should contain T002", "T002", result.get(0).getId());
    }
    
    @Test
    public void testCase5_multipleUnreturnedTapes() throws ParseException {
        // Create Customer C005
        Customer customer = new Customer();
        customer.setId("C005");
        
        // Create Tapes T001 and T002
        Tape tape1 = new Tape();
        tape1.setId("T001");
        
        Tape tape2 = new Tape();
        tape2.setId("T002");
        
        // Create VideoRental for T001 (unreturned)
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(null);
        
        // Create VideoRental for T002 (unreturned)
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-06 00:00:00"));
        rental2.setReturnDate(null);
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify list contains both T001 and T002
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 tapes", 2, result.size());
        assertTrue("Should contain T001", result.stream().anyMatch(t -> "T001".equals(t.getId())));
        assertTrue("Should contain T002", result.stream().anyMatch(t -> "T002".equals(t.getId())));
    }
}