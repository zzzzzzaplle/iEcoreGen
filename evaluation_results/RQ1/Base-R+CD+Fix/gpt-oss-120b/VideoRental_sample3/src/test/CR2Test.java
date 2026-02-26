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
        // Clear the static rentals list before each test to ensure isolation
        VideoRental.getAllRentals().clear();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_TapeIsAvailable() throws ParseException {
        // Setup: Create Tape with id="T001", no active rentals for T001
        Tape tape = new Tape("T001", "Video Information for T001");
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Execute: Check availability
        boolean result = tape.isAvailable(currentDate);
        
        // Verify: Tape should be available
        assertTrue("Tape T001 should be available when no active rentals exist", result);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() throws ParseException {
        // Setup: Create Tape T002 and Customer C001
        Tape tape = new Tape("T002", "Video Information for T002");
        Customer customer = new Customer("C001");
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Create rental: rental date="2024-12-28", due_date="2025-01-05", return_date=null
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setRentalDate(dateFormat.parse("2024-12-28 00:00:00"));
        rental.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental.setReturnDate(null);
        
        // Register the rental
        customer.getRentals().add(rental);
        VideoRental.registerRental(rental);
        
        // Execute: Check availability
        boolean result = tape.isAvailable(currentDate);
        
        // Verify: Tape should be unavailable (rented out)
        assertFalse("Tape T002 should be unavailable when actively rented", result);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() throws ParseException {
        // Setup: Create Tape T003 and Customer C002
        Tape tape = new Tape("T003", "Video Information for T003");
        Customer customer = new Customer("C002");
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Create rental: rental date="2024-12-25", due_date="2024-12-30", return_date="2024-12-31"
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setRentalDate(dateFormat.parse("2024-12-25 00:00:00"));
        rental.setDueDate(dateFormat.parse("2024-12-30 00:00:00"));
        rental.setReturnDate(dateFormat.parse("2024-12-31 00:00:00"));
        
        // Register the rental
        customer.getRentals().add(rental);
        VideoRental.registerRental(rental);
        
        // Execute: Check availability
        boolean result = tape.isAvailable(currentDate);
        
        // Verify: Tape should be available (returned)
        assertTrue("Tape T003 should be available when rental has been returned", result);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() throws ParseException {
        // Setup: Create Tape T004 and Customer C003
        Tape tape = new Tape("T004", "Video Information for T004");
        Customer customer = new Customer("C003");
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        
        // Create rental: rental date="2024-12-28", due_date="2025-01-01", return_date=null
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setRentalDate(dateFormat.parse("2024-12-28 00:00:00"));
        rental.setDueDate(dateFormat.parse("2025-01-01 00:00:00"));
        rental.setReturnDate(null);
        
        // Register the rental
        customer.getRentals().add(rental);
        VideoRental.registerRental(rental);
        
        // Execute: Check availability
        boolean result = tape.isAvailable(currentDate);
        
        // Verify: Tape should be unavailable (overdue rental)
        assertFalse("Tape T004 should be unavailable when overdue and not returned", result);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() throws ParseException {
        // Setup: Create Tape T005 and Customers C004, C005
        Tape tape = new Tape("T005", "Video Information for T005");
        Customer customer1 = new Customer("C004");
        Customer customer2 = new Customer("C005");
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        
        // First rental: C004 rents T005 with rental date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01"
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape);
        rental1.setRentalDate(dateFormat.parse("2025-01-01 00:00:00"));
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-01 00:00:00"));
        
        customer1.getRentals().add(rental1);
        VideoRental.registerRental(rental1);
        
        // Check availability after first rental (should be true - returned)
        boolean resultAfterFirst = tape.isAvailable(currentDate);
        assertTrue("Tape T005 should be available after first rental is returned", resultAfterFirst);
        
        // Second rental: C005 rents T005 with rental date="2025-01-06", due_date="2025-01-15", return_date=null
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape);
        rental2.setRentalDate(dateFormat.parse("2025-01-06 00:00:00"));
        rental2.setDueDate(dateFormat.parse("2025-01-15 00:00:00"));
        rental2.setReturnDate(null);
        
        customer2.getRentals().add(rental2);
        VideoRental.registerRental(rental2);
        
        // Check availability after second rental (should be false - currently rented)
        boolean resultAfterSecond = tape.isAvailable(currentDate);
        assertFalse("Tape T005 should be unavailable during second active rental", resultAfterSecond);
    }
}