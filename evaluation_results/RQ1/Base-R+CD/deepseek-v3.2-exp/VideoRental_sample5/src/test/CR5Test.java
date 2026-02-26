import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CR5Test {
    
    private Customer customer;
    private Tape tape1;
    private Tape tape2;
    private VideoRental rental1;
    private VideoRental rental2;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_NoRentalsExist() {
        // Create Customer C001 with empty rentals list
        customer = new Customer();
        customer.setId("C001");
        
        // Get unreturned tapes
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Verify empty list is returned
        assertTrue("Expected empty list when customer has no rentals", 
                   unreturnedTapes.isEmpty());
    }
    
    @Test
    public void testCase2_AllTapesReturned() {
        // Create Customer C002
        customer = new Customer();
        customer.setId("C002");
        
        // Create Tape T001
        tape1 = new Tape();
        tape1.setId("T001");
        
        // Create VideoRental with return date set (tape is returned)
        rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDateTime.parse("2025-01-05 00:00:00", formatter));
        rental1.setReturnDate(LocalDateTime.parse("2025-01-01 00:00:00", formatter));
        
        // Add rental to customer
        customer.getRentals().add(rental1);
        
        // Get unreturned tapes
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Verify empty list is returned since all tapes are returned
        assertTrue("Expected empty list when all tapes are returned", 
                   unreturnedTapes.isEmpty());
    }
    
    @Test
    public void testCase3_OneUnreturnedTape() {
        // Create Customer C003
        customer = new Customer();
        customer.setId("C003");
        
        // Create Tape T001
        tape1 = new Tape();
        tape1.setId("T001");
        
        // Create VideoRental with return_date=null (tape is unreturned)
        rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDateTime.parse("2025-01-05 00:00:00", formatter));
        rental1.setReturnDate(null); // Tape not returned
        
        // Add rental to customer
        customer.getRentals().add(rental1);
        
        // Get unreturned tapes
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Verify list contains exactly one tape (T001)
        assertEquals("Expected exactly one unreturned tape", 1, unreturnedTapes.size());
        assertEquals("Expected tape T001 to be in unreturned list", 
                     "T001", unreturnedTapes.get(0).getId());
    }
    
    @Test
    public void testCase4_MixedReturnedUnreturned() {
        // Create Customer C004
        customer = new Customer();
        customer.setId("C004");
        
        // Create Tape T001 and T002
        tape1 = new Tape();
        tape1.setId("T001");
        tape2 = new Tape();
        tape2.setId("T002");
        
        // Create VideoRental for T001 with return date set (returned)
        rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDateTime.parse("2025-01-05 00:00:00", formatter));
        rental1.setReturnDate(LocalDateTime.parse("2025-01-01 00:00:00", formatter));
        
        // Create VideoRental for T002 with return_date=null (unreturned)
        rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDateTime.parse("2025-01-06 00:00:00", formatter));
        rental2.setReturnDate(null); // Tape not returned
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Get unreturned tapes
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Verify list contains only T002
        assertEquals("Expected exactly one unreturned tape", 1, unreturnedTapes.size());
        assertEquals("Expected tape T002 to be in unreturned list", 
                     "T002", unreturnedTapes.get(0).getId());
    }
    
    @Test
    public void testCase5_MultipleUnreturnedTapes() {
        // Create Customer C005
        customer = new Customer();
        customer.setId("C005");
        
        // Create Tapes T001 and T002
        tape1 = new Tape();
        tape1.setId("T001");
        tape2 = new Tape();
        tape2.setId("T002");
        
        // Create VideoRental for T001 with return_date=null (unreturned)
        rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDateTime.parse("2025-01-05 00:00:00", formatter));
        rental1.setReturnDate(null); // Tape not returned
        
        // Create VideoRental for T002 with return_date=null (unreturned)
        rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDateTime.parse("2025-01-06 00:00:00", formatter));
        rental2.setReturnDate(null); // Tape not returned
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Get unreturned tapes
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Verify list contains both T001 and T002
        assertEquals("Expected exactly two unreturned tapes", 2, unreturnedTapes.size());
        
        // Check that both tapes are present (order doesn't matter)
        boolean foundT001 = false;
        boolean foundT002 = false;
        for (Tape tape : unreturnedTapes) {
            if ("T001".equals(tape.getId())) {
                foundT001 = true;
            }
            if ("T002".equals(tape.getId())) {
                foundT002 = true;
            }
        }
        assertTrue("Expected tape T001 to be in unreturned list", foundT001);
        assertTrue("Expected tape T002 to be in unreturned list", foundT002);
    }
}