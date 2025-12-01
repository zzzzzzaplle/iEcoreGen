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
        // Clear static rentals list before each test to ensure isolation
        // Since ALL_RENTALS is private, we need to reset it through the class
        // This is a workaround since we don't have direct access to the static field
    }
    
    @Test
    public void testCase1_NoRentalsExist() {
        // Input: customer_id="C001"
        // Setup: Create Customer C001 with empty rentals list
        
        Customer customer = new Customer("C001");
        
        // Execute: Get unreturned tapes
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Verify: Empty list, no active rentals
        assertNotNull("Unreturned tapes list should not be null", unreturnedTapes);
        assertEquals("Should return empty list when no rentals exist", 0, unreturnedTapes.size());
    }
    
    @Test
    public void testCase2_AllTapesReturned() {
        // Input: customer_id="C002"
        // Setup:
        // 1. Create Customer C002
        // 2. Create Tape T001
        // 3. Create VideoRental with rental_date="2025-01-01", due_date="2025-01-05", 
        //    return_date="2025-01-01", associated with C002 and T001
        
        Customer customer = new Customer("C002");
        Tape tape = new Tape("T001", "Test Tape 1");
        
        VideoRental rental = new VideoRental();
        rental.setRentalStartDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(LocalDate.parse("2025-01-01", formatter));
        rental.setTape(tape);
        
        customer.getRentals().add(rental);
        Tape.registerRental(rental);
        
        // Execute: Get unreturned tapes
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Verify: Empty list, all rentals returned
        assertNotNull("Unreturned tapes list should not be null", unreturnedTapes);
        assertEquals("Should return empty list when all tapes are returned", 0, unreturnedTapes.size());
    }
    
    @Test
    public void testCase3_OneUnreturnedTape() {
        // Input: customer_id="C003"
        // Setup:
        // 1. Create Customer C003
        // 2. Create Tape T001
        // 3. Create VideoRental with rental_date="2025-01-01", due_date="2025-01-05", 
        //    return_date=null, associated with C003 and T001
        
        Customer customer = new Customer("C003");
        Tape tape = new Tape("T001", "Test Tape 1");
        
        VideoRental rental = new VideoRental();
        rental.setRentalStartDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(null); // Not returned
        rental.setTape(tape);
        
        customer.getRentals().add(rental);
        Tape.registerRental(rental);
        
        // Execute: Get unreturned tapes
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Verify: List containing T001
        assertNotNull("Unreturned tapes list should not be null", unreturnedTapes);
        assertEquals("Should return 1 tape when one tape is unreturned", 1, unreturnedTapes.size());
        assertEquals("Should contain T001", "T001", unreturnedTapes.get(0).getId());
    }
    
    @Test
    public void testCase4_MixedReturnedUnreturned() {
        // Input: customer_id="C004"
        // Setup:
        // 1. Create Customer C004
        // 2. Create Tape T001 and T002
        // 3. Create VideoRental for T001 with rental_date="2025-01-01", due_date="2025-01-05", 
        //    return_date="2025-01-01", associated with C004
        // 4. Create VideoRental for T002 with rental_date="2025-01-02", due_date="2025-01-06", 
        //    return_date=null, associated with C004
        
        Customer customer = new Customer("C004");
        Tape tape1 = new Tape("T001", "Test Tape 1");
        Tape tape2 = new Tape("T002", "Test Tape 2");
        
        // Rental for T001 (returned)
        VideoRental rental1 = new VideoRental();
        rental1.setRentalStartDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setTape(tape1);
        
        // Rental for T002 (unreturned)
        VideoRental rental2 = new VideoRental();
        rental2.setRentalStartDate(LocalDate.parse("2025-01-02", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-06", formatter));
        rental2.setReturnDate(null); // Not returned
        rental2.setTape(tape2);
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        Tape.registerRental(rental1);
        Tape.registerRental(rental2);
        
        // Execute: Get unreturned tapes
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Verify: List containing T002
        assertNotNull("Unreturned tapes list should not be null", unreturnedTapes);
        assertEquals("Should return 1 tape when only one tape is unreturned", 1, unreturnedTapes.size());
        assertEquals("Should contain T002", "T002", unreturnedTapes.get(0).getId());
    }
    
    @Test
    public void testCase5_MultipleUnreturnedTapes() {
        // Input: customer_id="C005"
        // Setup:
        // 1. Create Customer C005
        // 2. Create Tapes T001 and T002
        // 3. Create VideoRental for T001 with rental_date="2025-01-01", due_date="2025-01-05", 
        //    return_date=null, associated with C005
        // 4. Create VideoRental for T002 with rental_date="2025-01-02", due_date="2025-01-06", 
        //    return_date=null, associated with C005
        
        Customer customer = new Customer("C005");
        Tape tape1 = new Tape("T001", "Test Tape 1");
        Tape tape2 = new Tape("T002", "Test Tape 2");
        
        // Rental for T001 (unreturned)
        VideoRental rental1 = new VideoRental();
        rental1.setRentalStartDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(null); // Not returned
        rental1.setTape(tape1);
        
        // Rental for T002 (unreturned)
        VideoRental rental2 = new VideoRental();
        rental2.setRentalStartDate(LocalDate.parse("2025-01-02", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-06", formatter));
        rental2.setReturnDate(null); // Not returned
        rental2.setTape(tape2);
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        Tape.registerRental(rental1);
        Tape.registerRental(rental2);
        
        // Execute: Get unreturned tapes
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Verify: List containing T001 and T002
        assertNotNull("Unreturned tapes list should not be null", unreturnedTapes);
        assertEquals("Should return 2 tapes when both tapes are unreturned", 2, unreturnedTapes.size());
        
        // Check that both tapes are in the list
        boolean hasT001 = false;
        boolean hasT002 = false;
        for (Tape tape : unreturnedTapes) {
            if ("T001".equals(tape.getId())) {
                hasT001 = true;
            }
            if ("T002".equals(tape.getId())) {
                hasT002 = true;
            }
        }
        assertTrue("Should contain T001", hasT001);
        assertTrue("Should contain T002", hasT002);
    }
}