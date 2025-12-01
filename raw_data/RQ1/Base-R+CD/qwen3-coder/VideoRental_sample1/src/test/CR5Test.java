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
    public void testCase1_noRentalsExist() throws ParseException {
        // Create Customer C001 with empty rentals list
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Call getUnreturnedTapes method
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Expected Output: Empty list, no active rentals
        assertNotNull(unreturnedTapes);
        assertEquals(0, unreturnedTapes.size());
    }
    
    @Test
    public void testCase2_allTapesReturned() throws ParseException {
        // Create Customer C002
        Customer customer = new Customer();
        customer.setId("C002");
        
        // Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        
        // Create VideoRental with rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01", associated with C002 and T001
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental.setReturnDate(dateFormat.parse("2025-01-01 00:00:00"));
        
        // Add rental to customer
        customer.getRentals().add(rental);
        
        // Call getUnreturnedTapes method
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Expected Output: Empty list, all rentals returned
        assertNotNull(unreturnedTapes);
        assertEquals(0, unreturnedTapes.size());
    }
    
    @Test
    public void testCase3_oneUnreturnedTape() throws ParseException {
        // Create Customer C003
        Customer customer = new Customer();
        customer.setId("C003");
        
        // Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        
        // Create VideoRental with rental_date="2025-01-01", due_date="2025-01-05", return_date=null, associated with C003 and T001
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental.setReturnDate(null);
        
        // Add rental to customer
        customer.getRentals().add(rental);
        
        // Call getUnreturnedTapes method
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Expected Output: List containing T001
        assertNotNull(unreturnedTapes);
        assertEquals(1, unreturnedTapes.size());
        assertEquals("T001", unreturnedTapes.get(0).getId());
    }
    
    @Test
    public void testCase4_mixedReturnedUnreturned() throws ParseException {
        // Create Customer C004
        Customer customer = new Customer();
        customer.setId("C004");
        
        // Create Tape T001 and T002
        Tape tape1 = new Tape();
        tape1.setId("T001");
        
        Tape tape2 = new Tape();
        tape2.setId("T002");
        
        // Create VideoRental for T001 with rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01", associated with C004
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-01 00:00:00"));
        
        // Create VideoRental for T002 with rental_date="2025-01-02", due_date="2025-01-06", return_date=null, associated with C004
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-06 00:00:00"));
        rental2.setReturnDate(null);
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Call getUnreturnedTapes method
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Expected Output: List containing T002
        assertNotNull(unreturnedTapes);
        assertEquals(1, unreturnedTapes.size());
        assertEquals("T002", unreturnedTapes.get(0).getId());
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
        
        // Create VideoRental for T001 with rental_date="2025-01-01", due_date="2025-01-05", return_date=null, associated with C005
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(null);
        
        // Create VideoRental for T002 with rental_date="2025-01-02", due_date="2025-01-06", return_date=null, associated with C005
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-06 00:00:00"));
        rental2.setReturnDate(null);
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Call getUnreturnedTapes method
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Expected Output: List containing T001 and T002
        assertNotNull(unreturnedTapes);
        assertEquals(2, unreturnedTapes.size());
        assertEquals("T001", unreturnedTapes.get(0).getId());
        assertEquals("T002", unreturnedTapes.get(1).getId());
    }
}