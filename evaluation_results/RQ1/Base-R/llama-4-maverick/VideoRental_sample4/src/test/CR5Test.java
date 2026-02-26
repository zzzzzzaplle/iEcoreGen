import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR5Test {
    
    private VideoTapeRentalService rentalService;
    
    @Before
    public void setUp() {
        rentalService = new VideoTapeRentalService();
    }
    
    @Test
    public void testCase1_noRentalsExist() {
        // Create Customer C001 with empty rentals list
        Customer customer = new Customer();
        customer.setId("C001");
        customer.setAccountNumber("ACC001");
        
        // Call the method to list unreturned tapes
        List<String> result = rentalService.listUnreturnedTapes(customer);
        
        // Verify expected output: Empty list, no active rentals
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase2_allTapesReturned() {
        // Create Customer C002
        Customer customer = new Customer();
        customer.setId("C002");
        customer.setAccountNumber("ACC002");
        
        // Create Tape T001
        VideoTape tape = new VideoTape();
        tape.setId("T001");
        tape.setBarCodeId("BC001");
        
        // Create VideoRental with return date (returned)
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-05");
        rental.setReturnDate("2025-01-01");
        
        customer.addRental(rental);
        
        // Call the method to list unreturned tapes
        List<String> result = rentalService.listUnreturnedTapes(customer);
        
        // Verify expected output: Empty list, all rentals returned
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_oneUnreturnedTape() {
        // Create Customer C003
        Customer customer = new Customer();
        customer.setId("C003");
        customer.setAccountNumber("ACC003");
        
        // Create Tape T001
        VideoTape tape = new VideoTape();
        tape.setId("T001");
        tape.setBarCodeId("BC001");
        
        // Create VideoRental with return_date=null (unreturned)
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-05");
        rental.setReturnDate(null); // Not returned
        
        customer.addRental(rental);
        
        // Call the method to list unreturned tapes
        List<String> result = rentalService.listUnreturnedTapes(customer);
        
        // Verify expected output: List containing T001
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains("T001"));
    }
    
    @Test
    public void testCase4_mixedReturnedUnreturned() {
        // Create Customer C004
        Customer customer = new Customer();
        customer.setId("C004");
        customer.setAccountNumber("ACC004");
        
        // Create Tape T001 and T002
        VideoTape tape1 = new VideoTape();
        tape1.setId("T001");
        tape1.setBarCodeId("BC001");
        
        VideoTape tape2 = new VideoTape();
        tape2.setId("T002");
        tape2.setBarCodeId("BC002");
        
        // Create VideoRental for T001 with return date (returned)
        Rental rental1 = new Rental();
        rental1.setVideoTape(tape1);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-01");
        
        // Create VideoRental for T002 with return_date=null (unreturned)
        Rental rental2 = new Rental();
        rental2.setVideoTape(tape2);
        rental2.setRentalDate("2025-01-02");
        rental2.setDueDate("2025-01-06");
        rental2.setReturnDate(null); // Not returned
        
        customer.addRental(rental1);
        customer.addRental(rental2);
        
        // Call the method to list unreturned tapes
        List<String> result = rentalService.listUnreturnedTapes(customer);
        
        // Verify expected output: List containing T002
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains("T002"));
        assertFalse(result.contains("T001"));
    }
    
    @Test
    public void testCase5_multipleUnreturnedTapes() {
        // Create Customer C005
        Customer customer = new Customer();
        customer.setId("C005");
        customer.setAccountNumber("ACC005");
        
        // Create Tapes T001 and T002
        VideoTape tape1 = new VideoTape();
        tape1.setId("T001");
        tape1.setBarCodeId("BC001");
        
        VideoTape tape2 = new VideoTape();
        tape2.setId("T002");
        tape2.setBarCodeId("BC002");
        
        // Create VideoRental for T001 with return_date=null (unreturned)
        Rental rental1 = new Rental();
        rental1.setVideoTape(tape1);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate(null); // Not returned
        
        // Create VideoRental for T002 with return_date=null (unreturned)
        Rental rental2 = new Rental();
        rental2.setVideoTape(tape2);
        rental2.setRentalDate("2025-01-02");
        rental2.setDueDate("2025-01-06");
        rental2.setReturnDate(null); // Not returned
        
        customer.addRental(rental1);
        customer.addRental(rental2);
        
        // Call the method to list unreturned tapes
        List<String> result = rentalService.listUnreturnedTapes(customer);
        
        // Verify expected output: List containing T001 and T002
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("T001"));
        assertTrue(result.contains("T002"));
    }
}