import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_TapeIsAvailable() throws Exception {
        // Setup: Create Tape with id="T001", no active rentals
        Tape tape = new Tape();
        tape.setId("T001");
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Test tape availability
        boolean result = tape.isAvailable(currentDate);
        
        // Verify tape is available
        assertTrue("Tape T001 should be available when no active rentals exist", result);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() throws Exception {
        // Setup: Create Tape with id="T002", Customer C001 rents T002 with return_date=null
        Tape tape = new Tape();
        tape.setId("T002");
        
        Customer customer = new Customer();
        customer.setId("C001");
        
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental.setReturnDate(null); // Unreturned
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Test tape availability
        boolean result = tape.isAvailable(currentDate);
        
        // Verify tape is unavailable (rented out)
        assertFalse("Tape T002 should be unavailable when actively rented with null return date", result);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() throws Exception {
        // Setup: Create Tape with id="T003", Customer C002 rents T003 but returned
        Tape tape = new Tape();
        tape.setId("T003");
        
        Customer customer = new Customer();
        customer.setId("C002");
        
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2024-12-30 00:00:00"));
        rental.setReturnDate(dateFormat.parse("2024-12-31 00:00:00")); // Returned
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Test tape availability
        boolean result = tape.isAvailable(currentDate);
        
        // Verify tape is available (returned)
        assertTrue("Tape T003 should be available when rental was returned before current date", result);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() throws Exception {
        // Setup: Create Tape with id="T004", Customer C003 rents T004 with return_date=null (overdue)
        Tape tape = new Tape();
        tape.setId("T004");
        
        Customer customer = new Customer();
        customer.setId("C003");
        
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2025-01-01 00:00:00"));
        rental.setReturnDate(null); // Unreturned and overdue
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        
        // Test tape availability
        boolean result = tape.isAvailable(currentDate);
        
        // Verify tape is unavailable (overdue rental)
        assertFalse("Tape T004 should be unavailable when it has an overdue rental", result);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() throws Exception {
        // Setup: Create Tape with id="T005", two customers with different rental scenarios
        Tape tape = new Tape();
        tape.setId("T005");
        
        // First customer C004 - returned rental
        Customer customer1 = new Customer();
        customer1.setId("C004");
        
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-01 00:00:00")); // Returned early
        
        List<VideoRental> rentals1 = new ArrayList<>();
        rentals1.add(rental1);
        customer1.setRentals(rentals1);
        
        // Second customer C005 - active rental
        Customer customer2 = new Customer();
        customer2.setId("C005");
        
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape);
        rental2.setDueDate(dateFormat.parse("2025-01-15 00:00:00"));
        rental2.setReturnDate(null); // Unreturned
        
        List<VideoRental> rentals2 = new ArrayList<>();
        rentals2.add(rental2);
        customer2.setRentals(rentals2);
        
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        
        // Test tape availability - should be false due to second customer's active rental
        boolean result = tape.isAvailable(currentDate);
        
        // Verify tape is unavailable (second rental is active)
        assertFalse("Tape T005 should be unavailable when second rental is active", result);
    }
}