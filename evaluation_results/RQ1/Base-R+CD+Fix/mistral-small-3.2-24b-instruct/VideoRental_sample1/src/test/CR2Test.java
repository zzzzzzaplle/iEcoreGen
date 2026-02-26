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
        // Setup: Create Tape with id="T001", no active rentals
        Tape tape = new Tape();
        tape.setId("T001");
        
        // Test tape availability for current date "2025-01-01"
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        boolean result = tape.isAvailable(currentDate);
        
        // Expected Output: True
        assertTrue("Tape T001 should be available when no active rentals exist", result);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() {
        // Setup: Create Tape with id="T002", Customer C001
        Tape tape = new Tape();
        tape.setId("T002");
        
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Create rental for T002: rental date="2024-12-28", due_date="2025-01-05", return_date=null
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(null); // unreturned
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Test tape availability for current date "2025-01-01"
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        boolean result = tape.isAvailable(currentDate);
        
        // Expected Output: False
        assertFalse("Tape T002 should be unavailable when rented out and not returned", result);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Setup: Create Tape with id="T003", Customer C002
        Tape tape = new Tape();
        tape.setId("T003");
        
        Customer customer = new Customer();
        customer.setId("C002");
        
        // Create rental for T003: rental date="2024-12-25", due_date="2024-12-30", return_date="2024-12-31"
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDate.parse("2024-12-30", formatter));
        rental.setReturnDate(LocalDate.parse("2024-12-31", formatter)); // returned
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Test tape availability for current date "2025-01-01"
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        boolean result = tape.isAvailable(currentDate);
        
        // Expected Output: True
        assertTrue("Tape T003 should be available when previously rented but returned", result);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Setup: Create Tape with id="T004", Customer C003
        Tape tape = new Tape();
        tape.setId("T004");
        
        Customer customer = new Customer();
        customer.setId("C003");
        
        // Create rental for T004: rental date="2024-12-28", due_date="2025-01-01", return_date=null
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDate.parse("2025-01-01", formatter));
        rental.setReturnDate(null); // unreturned and overdue
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Test tape availability for current date "2025-01-10"
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        boolean result = tape.isAvailable(currentDate);
        
        // Expected Output: False
        assertFalse("Tape T004 should be unavailable when has overdue rental", result);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Setup: Create Tape with id="T005", Customers C004 and C005
        Tape tape = new Tape();
        tape.setId("T005");
        
        Customer customer1 = new Customer();
        customer1.setId("C004");
        Customer customer2 = new Customer();
        customer2.setId("C005");
        
        // First rental: C004 rents T005 with rental date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01"
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape);
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-01", formatter)); // returned
        
        List<VideoRental> rentals1 = new ArrayList<>();
        rentals1.add(rental1);
        customer1.setRentals(rentals1);
        
        // Second rental: C005 rents T005 with rental date="2025-01-06", due_date="2025-01-15", return_date=null
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape);
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(null); // unreturned
        
        List<VideoRental> rentals2 = new ArrayList<>();
        rentals2.add(rental2);
        customer2.setRentals(rentals2);
        
        // Test tape availability for current date "2025-01-10"
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        boolean result = tape.isAvailable(currentDate);
        
        // Expected Output: False (due to second rental being active)
        assertFalse("Tape T005 should be unavailable when currently rented out by another customer", result);
    }
}