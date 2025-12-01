import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    @Before
    public void setUp() {
        // Clear the static rentals list before each test to ensure isolation
        VideoRental.getRentals().clear();
    }
    
    @Test
    public void testCase1_tapeIsAvailable() {
        // Setup: Create Tape with id="T001"
        Tape tape = new Tape();
        tape.setId("T001");
        
        // Input: tape_id="T001", current_date="2025-01-01"
        LocalDate currentDate = LocalDate.parse("2025-01-01", DATE_FORMATTER);
        
        // Expected Output: True
        assertTrue("Tape T001 should be available", tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase2_tapeIsRentedOut() {
        // Setup: Create Tape with id="T002". Create Customer C001.
        Tape tape = new Tape();
        tape.setId("T002");
        
        Customer customer = new Customer();
        customer.setId("C001");
        
        // C001 rents T002 with rental date="2024-12-28", due_date="2025-01-05", return_date=null (unreturned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDate.parse("2025-01-05", DATE_FORMATTER));
        // return_date remains null (unreturned)
        
        customer.getRentals().add(rental);
        
        // Input: tape_id="T002", current_date="2025-01-01"
        LocalDate currentDate = LocalDate.parse("2025-01-01", DATE_FORMATTER);
        
        // Expected Output: False
        assertFalse("Tape T002 should not be available (rented out)", tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase3_tapeWasRentedButReturned() {
        // Setup: Create Tape with id="T003". Create Customer C002.
        Tape tape = new Tape();
        tape.setId("T003");
        
        Customer customer = new Customer();
        customer.setId("C002");
        
        // C002 rents T003 with rental date="2024-12-25", due_date="2024-12-30", return_date="2024-12-31"
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDate.parse("2024-12-30", DATE_FORMATTER));
        rental.setReturnDate(LocalDate.parse("2024-12-31", DATE_FORMATTER));
        
        customer.getRentals().add(rental);
        
        // Input: tape_id="T003", current_date="2025-01-01"
        LocalDate currentDate = LocalDate.parse("2025-01-01", DATE_FORMATTER);
        
        // Expected Output: True
        assertTrue("Tape T003 should be available (returned)", tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase4_tapeExistsButHasOverdueRental() {
        // Setup: Create Tape with id="T004". Create Customer C003.
        Tape tape = new Tape();
        tape.setId("T004");
        
        Customer customer = new Customer();
        customer.setId("C003");
        
        // C003 rents T004 with rental date="2024-12-28", due_date="2025-01-01", return_date=null (unreturned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDate.parse("2025-01-01", DATE_FORMATTER));
        // return_date remains null (unreturned)
        
        customer.getRentals().add(rental);
        
        // Input: tape_id="T004", current_date="2025-01-10"
        LocalDate currentDate = LocalDate.parse("2025-01-10", DATE_FORMATTER);
        
        // Expected Output: False
        assertFalse("Tape T004 should not be available (overdue rental)", tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase5_tapeWasRentedButReturnedByMultipleCustomers() {
        // Setup: Create Tape with id="T005". Create Customer C004, C005.
        Tape tape = new Tape();
        tape.setId("T005");
        
        Customer customer1 = new Customer();
        customer1.setId("C004");
        
        Customer customer2 = new Customer();
        customer2.setId("C005");
        
        // C004 rents T005 with rental date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01" → first rental
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape);
        rental1.setDueDate(LocalDate.parse("2025-01-05", DATE_FORMATTER));
        rental1.setReturnDate(LocalDate.parse("2025-01-01", DATE_FORMATTER));
        
        customer1.getRentals().add(rental1);
        
        // After first rental: The first creation: True
        LocalDate currentDate1 = LocalDate.parse("2025-01-10", DATE_FORMATTER);
        assertTrue("Tape T005 should be available after first rental (returned)", tape.isAvailable(currentDate1));
        
        // C005 rents T005 with rental date="2025-01-06", due_date="2025-01-15", return_date=null → second rental
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape);
        rental2.setDueDate(LocalDate.parse("2025-01-15", DATE_FORMATTER));
        // return_date remains null (unreturned)
        
        customer2.getRentals().add(rental2);
        
        // After second rental: The second creation: False
        LocalDate currentDate2 = LocalDate.parse("2025-01-10", DATE_FORMATTER);
        assertFalse("Tape T005 should not be available after second rental (currently rented)", tape.isAvailable(currentDate2));
    }
}