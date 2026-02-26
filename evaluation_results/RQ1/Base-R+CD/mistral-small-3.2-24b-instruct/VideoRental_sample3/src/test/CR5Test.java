import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
    public void testCase1_NoRentalsExist() throws Exception {
        // Setup: Create Customer C001 with empty rentals list
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: Empty list, no active rentals
        assertTrue("Expected empty list for customer with no rentals", result.isEmpty());
    }
    
    @Test
    public void testCase2_AllTapesReturned() throws Exception {
        // Setup: Create Customer C002
        Customer customer = new Customer();
        customer.setId("C002");
        
        // Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        
        // Create VideoRental with return date set (tape returned)
        Date rentalDate = dateFormat.parse("2025-01-01 12:00:00");
        VideoRental rental = new VideoRental(tape, rentalDate);
        rental.setReturnDate(dateFormat.parse("2025-01-01 14:00:00"));
        
        // Add rental to customer
        customer.getRentals().add(rental);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: Empty list, all rentals returned
        assertTrue("Expected empty list when all tapes are returned", result.isEmpty());
    }
    
    @Test
    public void testCase3_OneUnreturnedTape() throws Exception {
        // Setup: Create Customer C003
        Customer customer = new Customer();
        customer.setId("C003");
        
        // Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        
        // Create VideoRental with return_date=null (tape not returned)
        Date rentalDate = dateFormat.parse("2025-01-01 12:00:00");
        VideoRental rental = new VideoRental(tape, rentalDate);
        // return_date remains null (not set)
        
        // Add rental to customer
        customer.getRentals().add(rental);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: List containing T001
        assertEquals("Expected exactly 1 unreturned tape", 1, result.size());
        assertEquals("Expected tape T001 in the result", "T001", result.get(0).getId());
    }
    
    @Test
    public void testCase4_MixedReturnedUnreturned() throws Exception {
        // Setup: Create Customer C004
        Customer customer = new Customer();
        customer.setId("C004");
        
        // Create Tape T001 and T002
        Tape tape1 = new Tape();
        tape1.setId("T001");
        Tape tape2 = new Tape();
        tape2.setId("T002");
        
        // Create VideoRental for T001 with return_date set (returned)
        Date rentalDate1 = dateFormat.parse("2025-01-01 12:00:00");
        VideoRental rental1 = new VideoRental(tape1, rentalDate1);
        rental1.setReturnDate(dateFormat.parse("2025-01-01 14:00:00"));
        
        // Create VideoRental for T002 with return_date=null (not returned)
        Date rentalDate2 = dateFormat.parse("2025-01-02 12:00:00");
        VideoRental rental2 = new VideoRental(tape2, rentalDate2);
        // return_date remains null (not set)
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: List containing T002 only
        assertEquals("Expected exactly 1 unreturned tape", 1, result.size());
        assertEquals("Expected tape T002 in the result", "T002", result.get(0).getId());
    }
    
    @Test
    public void testCase5_MultipleUnreturnedTapes() throws Exception {
        // Setup: Create Customer C005
        Customer customer = new Customer();
        customer.setId("C005");
        
        // Create Tapes T001 and T002
        Tape tape1 = new Tape();
        tape1.setId("T001");
        Tape tape2 = new Tape();
        tape2.setId("T002");
        
        // Create VideoRental for T001 with return_date=null (not returned)
        Date rentalDate1 = dateFormat.parse("2025-01-01 12:00:00");
        VideoRental rental1 = new VideoRental(tape1, rentalDate1);
        // return_date remains null (not set)
        
        // Create VideoRental for T002 with return_date=null (not returned)
        Date rentalDate2 = dateFormat.parse("2025-01-02 12:00:00");
        VideoRental rental2 = new VideoRental(tape2, rentalDate2);
        // return_date remains null (not set)
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute: Get unreturned tapes
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify: List containing T001 and T002
        assertEquals("Expected exactly 2 unreturned tapes", 2, result.size());
        assertTrue("Expected tape T001 in the result", 
                  result.stream().anyMatch(t -> "T001".equals(t.getId())));
        assertTrue("Expected tape T002 in the result", 
                  result.stream().anyMatch(t -> "T002".equals(t.getId())));
    }
}