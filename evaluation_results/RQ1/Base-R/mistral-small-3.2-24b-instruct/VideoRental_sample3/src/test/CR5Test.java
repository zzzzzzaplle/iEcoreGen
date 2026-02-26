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
    }
    
    @Test
    public void testCase1_noRentalsExist() {
        // Setup: Create Customer C001 with empty rentals list
        Customer customer = new Customer();
        customer.setAccountNumber("C001");
        
        // Execute: Call listUnreturnedTapes method
        List<String> result = customer.listUnreturnedTapes();
        
        // Verify: Expected Output: Empty list, no active rentals
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty when no rentals exist", result.isEmpty());
    }
    
    @Test
    public void testCase2_allTapesReturned() {
        // Setup: Create Customer C002
        Customer customer = new Customer();
        customer.setAccountNumber("C002");
        
        // Create Tape T001
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T001");
        
        // Create VideoRental with return date set (tape returned)
        VideoRental rental = new VideoRental();
        rental.setVideoTape(tape);
        rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(LocalDate.parse("2025-01-01", formatter));
        
        // Associate rental with customer
        customer.getRentals().add(rental);
        
        // Execute: Call listUnreturnedTapes method
        List<String> result = customer.listUnreturnedTapes();
        
        // Verify: Expected Output: Empty list, all rentals returned
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty when all tapes are returned", result.isEmpty());
    }
    
    @Test
    public void testCase3_oneUnreturnedTape() {
        // Setup: Create Customer C003
        Customer customer = new Customer();
        customer.setAccountNumber("C003");
        
        // Create Tape T001
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T001");
        
        // Create VideoRental with return_date=null (tape not returned)
        VideoRental rental = new VideoRental();
        rental.setVideoTape(tape);
        rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(null);
        
        // Associate rental with customer
        customer.getRentals().add(rental);
        
        // Execute: Call listUnreturnedTapes method
        List<String> result = customer.listUnreturnedTapes();
        
        // Verify: Expected Output: List containing T001
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly one tape", 1, result.size());
        assertTrue("Result should contain T001", result.contains("T001"));
    }
    
    @Test
    public void testCase4_mixedReturnedUnreturned() {
        // Setup: Create Customer C004
        Customer customer = new Customer();
        customer.setAccountNumber("C004");
        
        // Create Tape T001 and T002
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T002");
        
        // Create VideoRental for T001 with return date set (returned)
        VideoRental rental1 = new VideoRental();
        rental1.setVideoTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-01", formatter));
        
        // Create VideoRental for T002 with return_date=null (not returned)
        VideoRental rental2 = new VideoRental();
        rental2.setVideoTape(tape2);
        rental2.setRentalDate(LocalDate.parse("2025-01-02", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-06", formatter));
        rental2.setReturnDate(null);
        
        // Associate rentals with customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute: Call listUnreturnedTapes method
        List<String> result = customer.listUnreturnedTapes();
        
        // Verify: Expected Output: List containing T002
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly one tape", 1, result.size());
        assertTrue("Result should contain T002", result.contains("T002"));
        assertFalse("Result should not contain T001", result.contains("T001"));
    }
    
    @Test
    public void testCase5_multipleUnreturnedTapes() {
        // Setup: Create Customer C005
        Customer customer = new Customer();
        customer.setAccountNumber("C005");
        
        // Create Tapes T001 and T002
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T002");
        
        // Create VideoRental for T001 with return_date=null (not returned)
        VideoRental rental1 = new VideoRental();
        rental1.setVideoTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(null);
        
        // Create VideoRental for T002 with return_date=null (not returned)
        VideoRental rental2 = new VideoRental();
        rental2.setVideoTape(tape2);
        rental2.setRentalDate(LocalDate.parse("2025-01-02", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-06", formatter));
        rental2.setReturnDate(null);
        
        // Associate rentals with customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute: Call listUnreturnedTapes method
        List<String> result = customer.listUnreturnedTapes();
        
        // Verify: Expected Output: List containing T001 and T002
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly two tapes", 2, result.size());
        assertTrue("Result should contain T001", result.contains("T001"));
        assertTrue("Result should contain T002", result.contains("T002"));
    }
}