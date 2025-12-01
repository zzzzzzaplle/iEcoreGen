import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR2Test {
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_TapeIsAvailable() throws ParseException {
        // Test Case 1: "Tape is available"
        // Input: tape_id="T001", current_date="2025-01-01"
        
        // Setup: Create Tape with id="T001", No active rentals for T001
        Tape tape = new Tape();
        tape.setId("T001");
        Date currentDate = dateFormat.parse("2025-01-01");
        
        // Expected Output: True
        assertTrue("Tape T001 should be available", tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() throws ParseException {
        // Test Case 2: "Tape is rented out"
        // Input: tape_id="T002", current_date="2025-01-01"
        
        // Setup: Create Tape with id="T002". Create Customer C001.
        Tape tape = new Tape();
        tape.setId("T002");
        Customer customer = new Customer();
        customer.setId("C001");
        Date currentDate = dateFormat.parse("2025-01-01");
        Date rentalDate = dateFormat.parse("2024-12-28");
        
        // C001 rents T002 with rental date="2024-12-28", due_date="2025-01-05", return_date=null (unreturned)
        VideoRental rental = new VideoRental(tape, rentalDate);
        customer.getRentals().add(rental);
        
        // Expected Output: False
        assertFalse("Tape T002 should not be available (rented out)", tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() throws ParseException {
        // Test Case 3: "Tape was rented but returned"
        // Input: tape_id="T003", current_date="2025-01-01"
        
        // Setup: Create Tape with id="T003". Create Customer C002.
        Tape tape = new Tape();
        tape.setId("T003");
        Customer customer = new Customer();
        customer.setId("C002");
        Date currentDate = dateFormat.parse("2025-01-01");
        Date rentalDate = dateFormat.parse("2024-12-25");
        Date returnDate = dateFormat.parse("2024-12-31");
        
        // C002 rents T003 with rental date="2024-12-25", due_date="2024-12-30", return_date="2024-12-31"
        VideoRental rental = new VideoRental(tape, rentalDate);
        rental.setReturnDate(returnDate);
        customer.getRentals().add(rental);
        
        // Expected Output: True
        assertTrue("Tape T003 should be available (already returned)", tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() throws ParseException {
        // Test Case 4: "Tape exists but has overdue rental"
        // Input: tape_id="T004", current_date="2025-01-10"
        
        // Setup: Create Tape with id="T004". Create Customer C003.
        Tape tape = new Tape();
        tape.setId("T004");
        Customer customer = new Customer();
        customer.setId("C003");
        Date currentDate = dateFormat.parse("2025-01-10");
        Date rentalDate = dateFormat.parse("2024-12-28");
        
        // C003 rents T004 with rental date="2024-12-28", due_date="2025-01-01", return_date=null (unreturned)
        VideoRental rental = new VideoRental(tape, rentalDate);
        customer.getRentals().add(rental);
        
        // Expected Output: False
        assertFalse("Tape T004 should not be available (overdue rental)", tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() throws ParseException {
        // Test Case 5: "Tape was rented but returned by multiple customers"
        // Input: tape_id="T005", current_date="2025-01-10"
        
        // Setup: Create Tape with id="T005". Create Customer C004, C005.
        Tape tape = new Tape();
        tape.setId("T005");
        Customer customer1 = new Customer();
        customer1.setId("C004");
        Customer customer2 = new Customer();
        customer2.setId("C005");
        Date currentDate = dateFormat.parse("2025-01-10");
        
        // C004 rents T005 with rental date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01" → first rental
        Date rentalDate1 = dateFormat.parse("2025-01-01");
        Date returnDate1 = dateFormat.parse("2025-01-01");
        VideoRental rental1 = new VideoRental(tape, rentalDate1);
        rental1.setReturnDate(returnDate1);
        customer1.getRentals().add(rental1);
        
        // The first creation: True (tape was returned before current date)
        assertTrue("After first rental return, tape T005 should be available", tape.isAvailable(currentDate));
        
        // C005 rents T005 with rental date="2025-01-06", due_date="2025-01-15", return_date=null → second rental
        Date rentalDate2 = dateFormat.parse("2025-01-06");
        VideoRental rental2 = new VideoRental(tape, rentalDate2);
        customer2.getRentals().add(rental2);
        
        // The second creation: False (tape is currently rented out)
        assertFalse("After second rental, tape T005 should not be available", tape.isAvailable(currentDate));
    }
}