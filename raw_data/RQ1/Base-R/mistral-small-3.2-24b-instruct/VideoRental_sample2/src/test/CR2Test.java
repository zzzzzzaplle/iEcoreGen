import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    private RentalSystem rentalSystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        rentalSystem = new RentalSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_tapeIsAvailable() {
        // Setup: Create Tape with id="T001"
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("T001");
        tape.setAvailable(true);
        
        // Input: tape_id="T001", current_date="2025-01-01"
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Expected Output: True
        assertTrue("Tape T001 should be available", tape.isAvailableForRental(currentDate));
    }
    
    @Test
    public void testCase2_tapeIsRentedOut() {
        // Setup: Create Tape with id="T002". Create Customer C001.
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("T002");
        tape.setAvailable(false);
        
        Customer customer = new Customer();
        customer.setAccountNumber("C001");
        
        // C001 rents T002 with rental date="2024-12-28", due_date="2025-01-05", return_date=null (unreturned)
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate(LocalDate.parse("2024-12-28", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(null);
        
        customer.getRentals().add(rental);
        tape.setAvailable(false);
        
        // Input: tape_id="T002", current_date="2025-01-01"
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Expected Output: False
        assertFalse("Tape T002 should not be available (rented out)", tape.isAvailableForRental(currentDate));
    }
    
    @Test
    public void testCase3_tapeWasRentedButReturned() {
        // Setup: Create Tape with id="T003". Create Customer C002.
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("T003");
        tape.setAvailable(true);
        
        Customer customer = new Customer();
        customer.setAccountNumber("C002");
        
        // C002 rents T003 with rental date="2024-12-25", due_date="2024-12-30", return_date="2024-12-31"
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate(LocalDate.parse("2024-12-25", formatter));
        rental.setDueDate(LocalDate.parse("2024-12-30", formatter));
        rental.setReturnDate(LocalDate.parse("2024-12-31", formatter));
        
        customer.getRentals().add(rental);
        tape.setAvailable(true);
        
        // Input: tape_id="T003", current_date="2025-01-01"
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Expected Output: True
        assertTrue("Tape T003 should be available (returned)", tape.isAvailableForRental(currentDate));
    }
    
    @Test
    public void testCase4_tapeExistsButHasOverdueRental() {
        // Setup: Create Tape with id="T004". Create Customer C003.
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("T004");
        tape.setAvailable(false);
        
        Customer customer = new Customer();
        customer.setAccountNumber("C003");
        
        // C003 rents T004 with rental date="2024-12-28", due_date="2025-01-01", return_date=null (unreturned)
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate(LocalDate.parse("2024-12-28", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-01", formatter));
        rental.setReturnDate(null);
        
        customer.getRentals().add(rental);
        tape.setAvailable(false);
        
        // Input: tape_id="T004", current_date="2025-01-10"
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        
        // Expected Output: False
        assertFalse("Tape T004 should not be available (overdue rental)", tape.isAvailableForRental(currentDate));
    }
    
    @Test
    public void testCase5_tapeWasRentedButReturnedByMultipleCustomers() {
        // Setup: Create Tape with id="T005". Create Customer C004, C005.
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("T005");
        tape.setAvailable(true);
        
        Customer customer1 = new Customer();
        customer1.setAccountNumber("C004");
        Customer customer2 = new Customer();
        customer2.setAccountNumber("C005");
        
        // C004 rents T005 with rental date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01" → first rental
        Rental rental1 = new Rental();
        rental1.setVideoTape(tape);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-01", formatter));
        
        // C005 rents T005 with rental date="2025-01-06", due_date="2025-01-15", return_date=null → second rental
        Rental rental2 = new Rental();
        rental2.setVideoTape(tape);
        rental2.setRentalDate(LocalDate.parse("2025-01-06", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(null);
        
        customer1.getRentals().add(rental1);
        customer2.getRentals().add(rental2);
        
        // After first rental (returned)
        tape.setAvailable(true);
        
        // Input: tape_id="T005", current_date="2025-01-10"
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        
        // Expected Output: False (due to second rental with null return date)
        assertFalse("Tape T005 should not be available (currently rented out)", tape.isAvailableForRental(currentDate));
    }
}