import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Tape tape;
    private Customer customer;
    private VideoRental rental;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_TapeIsAvailable() {
        // Input: tape_id="T001", current_date="2025-01-01"
        // Setup: Create Tape with id="T001", No active rentals for T001
        tape = new Tape();
        tape.setId("T001");
        
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        boolean result = tape.isAvailable(currentDate);
        
        // Expected Output: True
        assertTrue("Tape T001 should be available when no active rentals exist", result);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() {
        // Input: tape_id="T002", current_date="2025-01-01"
        // Setup: Create Tape with id="T002", Create Customer C001
        tape = new Tape();
        tape.setId("T002");
        
        customer = new Customer();
        customer.setId("C001");
        
        // C001 rents T002 with rental date="2024-12-28", due_date="2025-01-05", return_date=null (unreturned)
        rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(null);
        
        // Add rental to customer's rentals
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        boolean result = tape.isAvailable(currentDate);
        
        // Expected Output: False
        assertFalse("Tape T002 should not be available when it has an active rental with null return date", result);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Input: tape_id="T003", current_date="2025-01-01"
        // Setup: Create Tape with id="T003", Create Customer C002
        tape = new Tape();
        tape.setId("T003");
        
        customer = new Customer();
        customer.setId("C002");
        
        // C002 rents T003 with rental date="2024-12-25", due_date="2024-12-30", return_date="2024-12-31"
        rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDate.parse("2024-12-30", formatter));
        rental.setReturnDate(LocalDate.parse("2024-12-31", formatter));
        
        // Add rental to customer's rentals
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        boolean result = tape.isAvailable(currentDate);
        
        // Expected Output: True
        assertTrue("Tape T003 should be available when all rentals have return dates before current date", result);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Input: tape_id="T004", current_date="2025-01-10"
        // Setup: Create Tape with id="T004", Create Customer C003
        tape = new Tape();
        tape.setId("T004");
        
        customer = new Customer();
        customer.setId("C003");
        
        // C003 rents T004 with rental date="2024-12-28", due_date="2025-01-01", return_date=null (unreturned)
        rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDate.parse("2025-01-01", formatter));
        rental.setReturnDate(null);
        
        // Add rental to customer's rentals
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        boolean result = tape.isAvailable(currentDate);
        
        // Expected Output: False
        assertFalse("Tape T004 should not be available when it has an overdue rental with null return date", result);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Input: tape_id="T005", current_date="2025-01-10"
        // Setup: Create Tape with id="T005", Create Customer C004, C005
        tape = new Tape();
        tape.setId("T005");
        
        Customer customer1 = new Customer();
        customer1.setId("C004");
        
        Customer customer2 = new Customer();
        customer2.setId("C005");
        
        // C004 rents T005 with rental date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01" → first rental
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape);
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-01", formatter));
        
        // C005 rents T005 with rental date="2025-01-06", due_date="2025-01-15", return_date=null → second rental
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape);
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(null);
        
        // Add rentals to respective customers
        List<VideoRental> rentals1 = new ArrayList<>();
        rentals1.add(rental1);
        customer1.setRentals(rentals1);
        
        List<VideoRental> rentals2 = new ArrayList<>();
        rentals2.add(rental2);
        customer2.setRentals(rentals2);
        
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        boolean result = tape.isAvailable(currentDate);
        
        // Expected Output: False (due to the second rental with null return date)
        assertFalse("Tape T005 should not be available when it has an active rental with null return date", result);
    }
}