import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_tapeIsAvailable() {
        // Test Case 1: "Tape is available"
        // Input: tape_id="T001", current_date="2025-01-01"
        // Setup: Create Tape with id="T001", No active rentals for T001
        // Expected Output: True
        
        Tape tape = new Tape();
        tape.setId("T001");
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-01 00:00:00", formatter);
        
        boolean result = tape.isAvailable(currentDate);
        
        assertTrue("Tape T001 should be available when no active rentals exist", result);
    }
    
    @Test
    public void testCase2_tapeIsRentedOut() {
        // Test Case 2: "Tape is rented out"
        // Input: tape_id="T002", current_date="2025-01-01"
        // Setup: Create Tape with id="T002". Create Customer C001. 
        //        C001 rents T002 with rental date="2024-12-28", due_date="2025-01-05", return_date=null (unreturned)
        // Expected Output: False
        
        Tape tape = new Tape();
        tape.setId("T002");
        Customer customer = new Customer();
        customer.setId("C001");
        
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-01 00:00:00", formatter);
        LocalDateTime rentalDate = LocalDateTime.parse("2024-12-28 00:00:00", formatter);
        LocalDateTime dueDate = LocalDateTime.parse("2025-01-05 00:00:00", formatter);
        
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        // return_date remains null (unreturned)
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        boolean result = tape.isAvailable(currentDate);
        
        assertFalse("Tape T002 should be unavailable when actively rented and unreturned", result);
    }
    
    @Test
    public void testCase3_tapeWasRentedButReturned() {
        // Test Case 3: "Tape was rented but returned"
        // Input: tape_id="T003", current_date="2025-01-01"
        // Setup: Create Tape with id="T003". Create Customer C002.
        //        C002 rents T003 with rental date="2024-12-25", due_date="2024-12-30", return_date="2024-12-31"
        // Expected Output: True
        
        Tape tape = new Tape();
        tape.setId("T003");
        Customer customer = new Customer();
        customer.setId("C002");
        
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-01 00:00:00", formatter);
        LocalDateTime rentalDate = LocalDateTime.parse("2024-12-25 00:00:00", formatter);
        LocalDateTime dueDate = LocalDateTime.parse("2024-12-30 00:00:00", formatter);
        LocalDateTime returnDate = LocalDateTime.parse("2024-12-31 00:00:00", formatter);
        
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        rental.setReturnDate(returnDate);
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        boolean result = tape.isAvailable(currentDate);
        
        assertTrue("Tape T003 should be available when previously rented but already returned", result);
    }
    
    @Test
    public void testCase4_tapeExistsButHasOverdueRental() {
        // Test Case 4: "Tape exists but has overdue rental"
        // Input: tape_id="T004", current_date="2025-01-10"
        // Setup: Create Tape with id="T004". Create Customer C003.
        //        C003 rents T004 with rental date="2024-12-28", due_date="2025-01-01", return_date=null (unreturned)
        // Expected Output: False
        
        Tape tape = new Tape();
        tape.setId("T004");
        Customer customer = new Customer();
        customer.setId("C003");
        
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-10 00:00:00", formatter);
        LocalDateTime rentalDate = LocalDateTime.parse("2024-12-28 00:00:00", formatter);
        LocalDateTime dueDate = LocalDateTime.parse("2025-01-01 00:00:00", formatter);
        
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        // return_date remains null (unreturned and overdue)
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        boolean result = tape.isAvailable(currentDate);
        
        assertFalse("Tape T004 should be unavailable when overdue and unreturned", result);
    }
    
    @Test
    public void testCase5_tapeWasRentedButReturnedByMultipleCustomers() {
        // Test Case 5: "Tape was rented but returned by multiple customers"
        // Input: tape_id="T005", current_date="2025-01-10"
        // Setup: 
        // 1. Create Tape with id="T005". Create Customer C004, C005.
        // 2. C004 rents T005 with rental date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01" → first rental
        // 3. C005 rents T005 with rental date="2025-01-06", due_date="2025-01-15", return_date=null → second rental
        // Expected Output: The first creation: True. The second creation: False.
        
        Tape tape = new Tape();
        tape.setId("T005");
        
        // First customer C004 - returned rental
        Customer customer1 = new Customer();
        customer1.setId("C004");
        
        LocalDateTime rentalDate1 = LocalDateTime.parse("2025-01-01 00:00:00", formatter);
        LocalDateTime dueDate1 = LocalDateTime.parse("2025-01-05 00:00:00", formatter);
        LocalDateTime returnDate1 = LocalDateTime.parse("2025-01-01 00:00:00", formatter);
        
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape);
        rental1.setDueDate(dueDate1);
        rental1.setReturnDate(returnDate1);
        
        List<VideoRental> rentals1 = new ArrayList<>();
        rentals1.add(rental1);
        customer1.setRentals(rentals1);
        
        // Second customer C005 - active rental
        Customer customer2 = new Customer();
        customer2.setId("C005");
        
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-10 00:00:00", formatter);
        LocalDateTime rentalDate2 = LocalDateTime.parse("2025-01-06 00:00:00", formatter);
        LocalDateTime dueDate2 = LocalDateTime.parse("2025-01-15 00:00:00", formatter);
        
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape);
        rental2.setDueDate(dueDate2);
        // return_date remains null (currently rented)
        
        List<VideoRental> rentals2 = new ArrayList<>();
        rentals2.add(rental2);
        customer2.setRentals(rentals2);
        
        boolean result = tape.isAvailable(currentDate);
        
        assertFalse("Tape T005 should be unavailable when currently rented by another customer", result);
    }
}