import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
        // Expected Output: True
        
        Tape tape = new Tape();
        tape.setId("T001");
        
        Date currentDate = dateFormat.parse("2025-01-01");
        
        boolean result = tape.isAvailable(currentDate);
        assertTrue("Tape T001 should be available when no active rentals exist", result);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() throws ParseException {
        // Test Case 2: "Tape is rented out"
        // Input: tape_id="T002", current_date="2025-01-01"
        // Setup: Create Tape with id="T002". Create Customer C001. 
        // C001 rents T002 with rental date="2024-12-28", due_date="2025-01-05", return_date=null (unreturned)
        // Expected Output: False
        
        Tape tape = new Tape();
        tape.setId("T002");
        
        Customer customer = new Customer();
        customer.setId("C001");
        
        Date rentalDate = dateFormat.parse("2024-12-28");
        Date dueDate = dateFormat.parse("2025-01-05");
        
        VideoRental rental = new VideoRental(tape, dueDate);
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        Date currentDate = dateFormat.parse("2025-01-01");
        
        boolean result = tape.isAvailable(currentDate);
        assertFalse("Tape T002 should not be available when it has an active rental with null return date", result);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() throws ParseException {
        // Test Case 3: "Tape was rented but returned"
        // Input: tape_id="T003", current_date="2025-01-01"
        // Setup: Create Tape with id="T003". Create Customer C002.
        // C002 rents T003 with rental date="2024-12-25", due_date="2024-12-30", return_date="2024-12-31"
        // Expected Output: True
        
        Tape tape = new Tape();
        tape.setId("T003");
        
        Customer customer = new Customer();
        customer.setId("C002");
        
        Date rentalDate = dateFormat.parse("2024-12-25");
        Date dueDate = dateFormat.parse("2024-12-30");
        Date returnDate = dateFormat.parse("2024-12-31");
        
        VideoRental rental = new VideoRental(tape, dueDate);
        rental.setReturnDate(returnDate);
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        Date currentDate = dateFormat.parse("2025-01-01");
        
        boolean result = tape.isAvailable(currentDate);
        assertTrue("Tape T003 should be available when it was returned before current date", result);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() throws ParseException {
        // Test Case 4: "Tape exists but has overdue rental"
        // Input: tape_id="T004", current_date="2025-01-10"
        // Setup: Create Tape with id="T004". Create Customer C003.
        // C003 rents T004 with rental date="2024-12-28", due_date="2025-01-01", return_date=null (unreturned)
        // Expected Output: False
        
        Tape tape = new Tape();
        tape.setId("T004");
        
        Customer customer = new Customer();
        customer.setId("C003");
        
        Date rentalDate = dateFormat.parse("2024-12-28");
        Date dueDate = dateFormat.parse("2025-01-01");
        
        VideoRental rental = new VideoRental(tape, dueDate);
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        Date currentDate = dateFormat.parse("2025-01-10");
        
        boolean result = tape.isAvailable(currentDate);
        assertFalse("Tape T004 should not be available when it has an overdue rental with null return date", result);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() throws ParseException {
        // Test Case 5: "Tape was rented but returned by multiple customers"
        // Input: tape_id="T005", current_date="2025-01-10"
        // Setup: 
        // 1. Create Tape with id="T005". Create Customer C004, C005.
        // 2. C004 rents T005 with rental date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01" → first rental
        // 3. C005 rents T005 with rental date="2025-01-06", due_date="2025-01-15", return_date=null → second rental
        // Expected Output: The first creation: True. The second creation: False.
        
        Tape tape = new Tape();
        tape.setId("T005");
        
        Customer customer1 = new Customer();
        customer1.setId("C004");
        
        Customer customer2 = new Customer();
        customer2.setId("C005");
        
        // First rental: returned
        Date rentalDate1 = dateFormat.parse("2025-01-01");
        Date dueDate1 = dateFormat.parse("2025-01-05");
        Date returnDate1 = dateFormat.parse("2025-01-01");
        
        VideoRental rental1 = new VideoRental(tape, dueDate1);
        rental1.setReturnDate(returnDate1);
        List<VideoRental> rentals1 = new ArrayList<>();
        rentals1.add(rental1);
        customer1.setRentals(rentals1);
        
        // Second rental: not returned
        Date rentalDate2 = dateFormat.parse("2025-01-06");
        Date dueDate2 = dateFormat.parse("2025-01-15");
        
        VideoRental rental2 = new VideoRental(tape, dueDate2);
        List<VideoRental> rentals2 = new ArrayList<>();
        rentals2.add(rental2);
        customer2.setRentals(rentals2);
        
        Date currentDate = dateFormat.parse("2025-01-10");
        
        boolean result = tape.isAvailable(currentDate);
        assertFalse("Tape T005 should not be available when it has an active rental with null return date", result);
    }
}