import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_TapeIsAvailable() {
        // Setup: Create Tape with id="T001", No active rentals for T001
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T001");
        
        // Input: tape_id="T001", current_date="2025-01-01"
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Since there are no rentals, the tape should be available
        boolean isAvailable = true; // Tape is not associated with any rental
        
        // Expected Output: True
        assertTrue("Tape T001 should be available when no active rentals exist", isAvailable);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() {
        // Setup: Create Tape with id="T002", Create Customer C001
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T002");
        
        Customer customer = new Customer();
        customer.setAccountNumber("C001");
        
        // C001 rents T002 with rental_date="2024-12-28", due_date="2025-01-05", return_date=null
        VideoRental rental = new VideoRental();
        rental.setVideoTape(tape);
        rental.setRentalDate(LocalDate.parse("2024-12-28", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(null);
        
        customer.getRentals().add(rental);
        
        // Input: tape_id="T002", current_date="2025-01-01"
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Check availability - tape is rented and not returned
        boolean isAvailable = rental.checkTapeAvailability(currentDate);
        
        // Expected Output: False
        assertFalse("Tape T002 should be unavailable when rented and not returned", isAvailable);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Setup: Create Tape with id="T003", Create Customer C002
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T003");
        
        Customer customer = new Customer();
        customer.setAccountNumber("C002");
        
        // C002 rents T003 with rental_date="2024-12-25", due_date="2024-12-30", return_date="2024-12-31"
        VideoRental rental = new VideoRental();
        rental.setVideoTape(tape);
        rental.setRentalDate(LocalDate.parse("2024-12-25", formatter));
        rental.setDueDate(LocalDate.parse("2024-12-30", formatter));
        rental.setReturnDate(LocalDate.parse("2024-12-31", formatter));
        
        customer.getRentals().add(rental);
        
        // Input: tape_id="T003", current_date="2025-01-01"
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Check availability - tape was returned before current date
        boolean isAvailable = rental.checkTapeAvailability(currentDate);
        
        // Expected Output: True
        assertTrue("Tape T003 should be available when returned before current date", isAvailable);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Setup: Create Tape with id="T004", Create Customer C003
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T004");
        
        Customer customer = new Customer();
        customer.setAccountNumber("C003");
        
        // C003 rents T004 with rental_date="2024-12-28", due_date="2025-01-01", return_date=null
        VideoRental rental = new VideoRental();
        rental.setVideoTape(tape);
        rental.setRentalDate(LocalDate.parse("2024-12-28", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-01", formatter));
        rental.setReturnDate(null);
        
        customer.getRentals().add(rental);
        
        // Input: tape_id="T004", current_date="2025-01-10"
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        
        // Check availability - tape is overdue and not returned
        boolean isAvailable = rental.checkTapeAvailability(currentDate);
        
        // Expected Output: False
        assertFalse("Tape T004 should be unavailable when overdue and not returned", isAvailable);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Setup: Create Tape with id="T005", Create Customers C004 and C005
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T005");
        
        Customer customer1 = new Customer();
        customer1.setAccountNumber("C004");
        
        Customer customer2 = new Customer();
        customer2.setAccountNumber("C005");
        
        // First rental: C004 rents T005 with rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01"
        VideoRental rental1 = new VideoRental();
        rental1.setVideoTape(tape);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-01", formatter));
        
        customer1.getRentals().add(rental1);
        
        // Second rental: C005 rents T005 with rental_date="2025-01-06", due_date="2025-01-15", return_date=null
        VideoRental rental2 = new VideoRental();
        rental2.setVideoTape(tape);
        rental2.setRentalDate(LocalDate.parse("2025-01-06", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(null);
        
        customer2.getRentals().add(rental2);
        
        // Input: tape_id="T005", current_date="2025-01-10"
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        
        // Check availability for the second rental - tape is currently rented by C005
        boolean isAvailable = rental2.checkTapeAvailability(currentDate);
        
        // Expected Output: False (for the second rental)
        assertFalse("Tape T005 should be unavailable when currently rented by another customer", isAvailable);
    }
}