import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

public class CR5Test {
    private Customer customer;
    private RentalSystem rentalSystem;
    private DateTimeFormatter formatter;

    @Before
    public void setUp() {
        customer = new Customer();
        rentalSystem = new RentalSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    @Test
    public void testCase1_noRentalsExist() {
        // Setup: Create Customer C001 with empty rentals list
        customer.setAccountNumber("C001");
        
        // Execute: List unreturned tapes
        List<String> result = customer.listUnreturnedTapes();
        
        // Verify: Empty list, no active rentals
        assertTrue("Should return empty list when no rentals exist", result.isEmpty());
    }

    @Test
    public void testCase2_allTapesReturned() {
        // Setup: Create Customer C002
        customer.setAccountNumber("C002");
        
        // Create Tape T001
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T001");
        
        // Create VideoRental with return date
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(LocalDate.parse("2025-01-01", formatter));
        
        customer.addRental(rental);
        
        // Execute: List unreturned tapes
        List<String> result = customer.listUnreturnedTapes();
        
        // Verify: Empty list, all rentals returned
        assertTrue("Should return empty list when all tapes are returned", result.isEmpty());
    }

    @Test
    public void testCase3_oneUnreturnedTape() {
        // Setup: Create Customer C003
        customer.setAccountNumber("C003");
        
        // Create Tape T001
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T001");
        
        // Create VideoRental with null return date
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(null);
        
        customer.addRental(rental);
        
        // Execute: List unreturned tapes
        List<String> result = customer.listUnreturnedTapes();
        
        // Verify: List containing T001
        assertEquals("Should return list with one unreturned tape", 1, result.size());
        assertEquals("Should contain T001", "T001", result.get(0));
    }

    @Test
    public void testCase4_mixedReturnedUnreturned() {
        // Setup: Create Customer C004
        customer.setAccountNumber("C004");
        
        // Create Tapes T001 and T002
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T002");
        
        // Create VideoRental for T001 with return date (returned)
        Rental rental1 = new Rental();
        rental1.setVideoTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-01", formatter));
        
        // Create VideoRental for T002 with null return date (unreturned)
        Rental rental2 = new Rental();
        rental2.setVideoTape(tape2);
        rental2.setRentalDate(LocalDate.parse("2025-01-02", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-06", formatter));
        rental2.setReturnDate(null);
        
        customer.addRental(rental1);
        customer.addRental(rental2);
        
        // Execute: List unreturned tapes
        List<String> result = customer.listUnreturnedTapes();
        
        // Verify: List containing T002 only
        assertEquals("Should return list with one unreturned tape", 1, result.size());
        assertEquals("Should contain T002", "T002", result.get(0));
    }

    @Test
    public void testCase5_multipleUnreturnedTapes() {
        // Setup: Create Customer C005
        customer.setAccountNumber("C005");
        
        // Create Tapes T001 and T002
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T002");
        
        // Create VideoRental for T001 with null return date
        Rental rental1 = new Rental();
        rental1.setVideoTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(null);
        
        // Create VideoRental for T002 with null return date
        Rental rental2 = new Rental();
        rental2.setVideoTape(tape2);
        rental2.setRentalDate(LocalDate.parse("2025-01-02", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-06", formatter));
        rental2.setReturnDate(null);
        
        customer.addRental(rental1);
        customer.addRental(rental2);
        
        // Execute: List unreturned tapes
        List<String> result = customer.listUnreturnedTapes();
        
        // Verify: List containing T001 and T002
        assertEquals("Should return list with two unreturned tapes", 2, result.size());
        assertTrue("Should contain T001", result.contains("T001"));
        assertTrue("Should contain T002", result.contains("T002"));
    }
}