import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CR5Test {
    
    private RentalSystem rentalSystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        rentalSystem = new RentalSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_noRentalsExist() {
        // Setup: Create Customer C001 with empty rentals list
        Customer customer = new Customer();
        customer.setAccountNumber("C001");
        rentalSystem.getCustomers().add(customer);
        
        // Execute: Get unreturned tapes for customer C001
        List<VideoTape> result = rentalSystem.getUnreturnedTapes("C001");
        
        // Verify: Expected Output: Empty list, no active rentals
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty when customer has no rentals", result.isEmpty());
    }
    
    @Test
    public void testCase2_allTapesReturned() {
        // Setup: Create Customer C002
        Customer customer = new Customer();
        customer.setAccountNumber("C002");
        rentalSystem.getCustomers().add(customer);
        
        // Create Tape T001
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("T001");
        rentalSystem.getVideoTapes().add(tape);
        
        // Create VideoRental with return_date="2025-01-01"
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(LocalDate.parse("2025-01-01", formatter));
        customer.getRentals().add(rental);
        
        // Execute: Get unreturned tapes for customer C002
        List<VideoTape> result = rentalSystem.getUnreturnedTapes("C002");
        
        // Verify: Expected Output: Empty list, all rentals returned
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty when all tapes are returned", result.isEmpty());
    }
    
    @Test
    public void testCase3_oneUnreturnedTape() {
        // Setup: Create Customer C003
        Customer customer = new Customer();
        customer.setAccountNumber("C003");
        rentalSystem.getCustomers().add(customer);
        
        // Create Tape T001
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("T001");
        rentalSystem.getVideoTapes().add(tape);
        
        // Create VideoRental with return_date=null
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(null); // Unreturned tape
        customer.getRentals().add(rental);
        
        // Execute: Get unreturned tapes for customer C003
        List<VideoTape> result = rentalSystem.getUnreturnedTapes("C003");
        
        // Verify: Expected Output: List containing T001
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain exactly one unreturned tape", 1, result.size());
        assertEquals("The unreturned tape should be T001", "T001", result.get(0).getBarCodeId());
    }
    
    @Test
    public void testCase4_mixedReturnedUnreturned() {
        // Setup: Create Customer C004
        Customer customer = new Customer();
        customer.setAccountNumber("C004");
        rentalSystem.getCustomers().add(customer);
        
        // Create Tapes T001 and T002
        VideoTape tape1 = new VideoTape();
        tape1.setBarCodeId("T001");
        VideoTape tape2 = new VideoTape();
        tape2.setBarCodeId("T002");
        rentalSystem.getVideoTapes().add(tape1);
        rentalSystem.getVideoTapes().add(tape2);
        
        // Create VideoRental for T001 with return_date="2025-01-01" (returned)
        Rental rental1 = new Rental();
        rental1.setVideoTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-01", formatter));
        customer.getRentals().add(rental1);
        
        // Create VideoRental for T002 with return_date=null (unreturned)
        Rental rental2 = new Rental();
        rental2.setVideoTape(tape2);
        rental2.setRentalDate(LocalDate.parse("2025-01-02", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-06", formatter));
        rental2.setReturnDate(null); // Unreturned tape
        customer.getRentals().add(rental2);
        
        // Execute: Get unreturned tapes for customer C004
        List<VideoTape> result = rentalSystem.getUnreturnedTapes("C004");
        
        // Verify: Expected Output: List containing T002
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain exactly one unreturned tape", 1, result.size());
        assertEquals("The unreturned tape should be T002", "T002", result.get(0).getBarCodeId());
    }
    
    @Test
    public void testCase5_multipleUnreturnedTapes() {
        // Setup: Create Customer C005
        Customer customer = new Customer();
        customer.setAccountNumber("C005");
        rentalSystem.getCustomers().add(customer);
        
        // Create Tapes T001 and T002
        VideoTape tape1 = new VideoTape();
        tape1.setBarCodeId("T001");
        VideoTape tape2 = new VideoTape();
        tape2.setBarCodeId("T002");
        rentalSystem.getVideoTapes().add(tape1);
        rentalSystem.getVideoTapes().add(tape2);
        
        // Create VideoRental for T001 with return_date=null
        Rental rental1 = new Rental();
        rental1.setVideoTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(null); // Unreturned tape
        customer.getRentals().add(rental1);
        
        // Create VideoRental for T002 with return_date=null
        Rental rental2 = new Rental();
        rental2.setVideoTape(tape2);
        rental2.setRentalDate(LocalDate.parse("2025-01-02", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-06", formatter));
        rental2.setReturnDate(null); // Unreturned tape
        customer.getRentals().add(rental2);
        
        // Execute: Get unreturned tapes for customer C005
        List<VideoTape> result = rentalSystem.getUnreturnedTapes("C005");
        
        // Verify: Expected Output: List containing T001 and T002
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain exactly two unreturned tapes", 2, result.size());
        
        // Check that both tapes are in the result (order doesn't matter)
        boolean foundT001 = false;
        boolean foundT002 = false;
        for (VideoTape tape : result) {
            if (tape.getBarCodeId().equals("T001")) {
                foundT001 = true;
            } else if (tape.getBarCodeId().equals("T002")) {
                foundT002 = true;
            }
        }
        assertTrue("Result should contain T001", foundT001);
        assertTrue("Result should contain T002", foundT002);
    }
}