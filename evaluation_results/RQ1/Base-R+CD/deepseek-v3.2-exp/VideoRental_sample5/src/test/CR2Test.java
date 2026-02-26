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
    public void testCase1_TapeIsAvailable() {
        // Setup: Create Tape with id="T001", no active rentals
        Tape tape = new Tape();
        tape.setId("T001");
        
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-01 00:00:00", formatter);
        
        // Test tape availability
        boolean result = tape.isAvailable(currentDate);
        
        // Verify tape is available
        assertTrue("Tape T001 should be available when no active rentals exist", result);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() {
        // Setup: Create Tape with id="T002", Customer C001 with active rental
        Tape tape = new Tape();
        tape.setId("T002");
        
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Create active rental for T002
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDateTime.parse("2025-01-05 00:00:00", formatter));
        rental.setReturnDate(null); // Unreturned
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-01 00:00:00", formatter);
        
        // Test tape availability - should be unavailable due to active rental
        boolean result = tape.isAvailable(currentDate);
        
        // Verify tape is unavailable
        assertFalse("Tape T002 should be unavailable when actively rented out", result);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Setup: Create Tape with id="T003", Customer C002 with returned rental
        Tape tape = new Tape();
        tape.setId("T003");
        
        Customer customer = new Customer();
        customer.setId("C002");
        
        // Create returned rental for T003
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDateTime.parse("2024-12-30 00:00:00", formatter));
        rental.setReturnDate(LocalDateTime.parse("2024-12-31 00:00:00", formatter)); // Returned
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-01 00:00:00", formatter);
        
        // Test tape availability
        boolean result = tape.isAvailable(currentDate);
        
        // Verify tape is available (returned before current date)
        assertTrue("Tape T003 should be available when previously rented but returned", result);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Setup: Create Tape with id="T004", Customer C003 with overdue unreturned rental
        Tape tape = new Tape();
        tape.setId("T004");
        
        Customer customer = new Customer();
        customer.setId("C003");
        
        // Create overdue unreturned rental for T004
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDateTime.parse("2025-01-01 00:00:00", formatter));
        rental.setReturnDate(null); // Unreturned and overdue
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-10 00:00:00", formatter);
        
        // Test tape availability
        boolean result = tape.isAvailable(currentDate);
        
        // Verify tape is unavailable due to overdue unreturned rental
        assertFalse("Tape T004 should be unavailable when has overdue unreturned rental", result);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Setup: Create Tape with id="T005", multiple customers with rental history
        Tape tape = new Tape();
        tape.setId("T005");
        
        // First customer: C004 with returned rental
        Customer customer1 = new Customer();
        customer1.setId("C004");
        
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape);
        rental1.setDueDate(LocalDateTime.parse("2025-01-05 00:00:00", formatter));
        rental1.setReturnDate(LocalDateTime.parse("2025-01-01 00:00:00", formatter)); // Returned
        
        List<VideoRental> rentals1 = new ArrayList<>();
        rentals1.add(rental1);
        customer1.setRentals(rentals1);
        
        // Second customer: C005 with active rental
        Customer customer2 = new Customer();
        customer2.setId("C005");
        
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape);
        rental2.setDueDate(LocalDateTime.parse("2025-01-15 00:00:00", formatter));
        rental2.setReturnDate(null); // Unreturned
        
        List<VideoRental> rentals2 = new ArrayList<>();
        rentals2.add(rental2);
        customer2.setRentals(rentals2);
        
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-10 00:00:00", formatter);
        
        // Test tape availability - should be unavailable due to active rental from C005
        boolean result = tape.isAvailable(currentDate);
        
        // Verify tape is unavailable due to current active rental
        assertFalse("Tape T005 should be unavailable due to current active rental from C005", result);
    }
}