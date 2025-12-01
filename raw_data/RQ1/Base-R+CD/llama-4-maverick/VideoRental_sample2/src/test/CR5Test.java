import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CR5Test {
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Clear static rentals list before each test to avoid interference
        VideoRental.getRentals().clear();
    }
    
    @Test
    public void testCase1_NoRentalsExist() {
        // Create Customer C001 with empty rentals list
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Retrieve unreturned tapes
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify empty list is returned
        assertTrue("Expected empty list when no rentals exist", result.isEmpty());
    }
    
    @Test
    public void testCase2_AllTapesReturned() {
        // Create Customer C002
        Customer customer = new Customer();
        customer.setId("C002");
        
        // Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        
        // Create VideoRental with return date (tape is returned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(LocalDate.parse("2025-01-01", formatter));
        
        // Add rental to customer
        customer.getRentals().add(rental);
        
        // Retrieve unreturned tapes
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify empty list is returned since all tapes are returned
        assertTrue("Expected empty list when all tapes are returned", result.isEmpty());
    }
    
    @Test
    public void testCase3_OneUnreturnedTape() {
        // Create Customer C003
        Customer customer = new Customer();
        customer.setId("C003");
        
        // Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        
        // Create VideoRental with null return date (tape is not returned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(null);
        
        // Add rental to customer
        customer.getRentals().add(rental);
        
        // Retrieve unreturned tapes
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify list contains T001
        assertEquals("Expected list size 1", 1, result.size());
        assertTrue("Expected T001 in the list", result.contains("T001"));
    }
    
    @Test
    public void testCase4_MixedReturnedUnreturned() {
        // Create Customer C004
        Customer customer = new Customer();
        customer.setId("C004");
        
        // Create Tape T001 and T002
        Tape tape1 = new Tape();
        tape1.setId("T001");
        Tape tape2 = new Tape();
        tape2.setId("T002");
        
        // Create VideoRental for T001 with return date (returned)
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-01", formatter));
        
        // Create VideoRental for T002 with null return date (not returned)
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDate.parse("2025-01-06", formatter));
        rental2.setReturnDate(null);
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Retrieve unreturned tapes
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify list contains only T002
        assertEquals("Expected list size 1", 1, result.size());
        assertTrue("Expected T002 in the list", result.contains("T002"));
        assertFalse("T001 should not be in the list", result.contains("T001"));
    }
    
    @Test
    public void testCase5_MultipleUnreturnedTapes() {
        // Create Customer C005
        Customer customer = new Customer();
        customer.setId("C005");
        
        // Create Tapes T001 and T002
        Tape tape1 = new Tape();
        tape1.setId("T001");
        Tape tape2 = new Tape();
        tape2.setId("T002");
        
        // Create VideoRental for T001 with null return date (not returned)
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(null);
        
        // Create VideoRental for T002 with null return date (not returned)
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDate.parse("2025-01-06", formatter));
        rental2.setReturnDate(null);
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Retrieve unreturned tapes
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify list contains both T001 and T002
        assertEquals("Expected list size 2", 2, result.size());
        assertTrue("Expected T001 in the list", result.contains("T001"));
        assertTrue("Expected T002 in the list", result.contains("T002"));
    }
}