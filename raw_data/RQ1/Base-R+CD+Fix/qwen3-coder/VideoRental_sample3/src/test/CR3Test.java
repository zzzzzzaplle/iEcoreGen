import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CR3Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SuccessfulRental() throws Exception {
        // Setup: Create Customer C001 with 5 active rentals
        Customer customer = new Customer();
        customer.setId("C001");
        
        List<VideoRental> rentals = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            VideoRental rental = new VideoRental();
            Tape tape = new Tape();
            tape.setId("T00" + (i + 5)); // Use different tape IDs to avoid conflict
            tape.setVideoInformation("Movie " + i);
            
            Date rentalDate = dateFormat.parse("2025-01-0" + i + " 10:00:00");
            Calendar cal = Calendar.getInstance();
            cal.setTime(rentalDate);
            cal.add(Calendar.DAY_OF_MONTH, 7);
            Date dueDate = cal.getTime();
            
            rental.setTape(tape);
            rental.setDueDate(dueDate);
            rental.setReturnDate(null);
            rental.setOwnedPastDueAmount(0.0);
            
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        // Setup: Create available Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        tape.setVideoInformation("New Movie");
        
        // Input: C001 rents tape "T001" with current_date="2025-01-01"
        Date currentDate = dateFormat.parse("2025-01-01 10:00:00");
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Expected Output: True
        assertTrue("Rental should be successful when customer has <20 rentals, no past-due amount, and tape is available", result);
    }
    
    @Test
    public void testCase2_CustomerHas20RentalsMaxLimit() throws Exception {
        // Setup: Create Customer C002 with 20 active rentals
        Customer customer = new Customer();
        customer.setId("C002");
        
        List<VideoRental> rentals = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = new VideoRental();
            Tape tape = new Tape();
            tape.setId("T00" + (i + 10)); // Use different tape IDs to avoid conflict
            tape.setVideoInformation("Movie " + i);
            
            Date rentalDate = dateFormat.parse("2025-01-01 10:00:00");
            Calendar cal = Calendar.getInstance();
            cal.setTime(rentalDate);
            cal.add(Calendar.DAY_OF_MONTH, 7);
            Date dueDate = cal.getTime();
            
            rental.setTape(tape);
            rental.setDueDate(dueDate);
            rental.setReturnDate(null);
            rental.setOwnedPastDueAmount(0.0);
            
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        // Setup: Create available Tape T002
        Tape tape = new Tape();
        tape.setId("T002");
        tape.setVideoInformation("New Movie");
        
        // Input: C002 rents tape "T002" with current_date="2025-01-01"
        Date currentDate = dateFormat.parse("2025-01-01 10:00:00");
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Expected Output: False
        assertFalse("Rental should fail when customer has 20 or more rentals", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() throws Exception {
        // Setup: Create Customer C003 with 1 active rental, due_date="2025-01-02", return_date=null (3 days overdue)
        Customer customer = new Customer();
        customer.setId("C003");
        
        List<VideoRental> rentals = new ArrayList<>();
        VideoRental rental = new VideoRental();
        Tape existingTape = new Tape();
        existingTape.setId("T009");
        existingTape.setVideoInformation("Existing Movie");
        
        Date dueDate = dateFormat.parse("2025-01-02 10:00:00");
        
        rental.setTape(existingTape);
        rental.setDueDate(dueDate);
        rental.setReturnDate(null);
        rental.setOwnedPastDueAmount(0.0);
        
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Setup: Create available Tape T003
        Tape tape = new Tape();
        tape.setId("T003");
        tape.setVideoInformation("New Movie");
        
        // Input: C003 rents tape "T003" with current_date="2025-01-05" (3 days after due date)
        Date currentDate = dateFormat.parse("2025-01-05 10:00:00");
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Expected Output: False
        assertFalse("Rental should fail when customer has overdue fees", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() throws Exception {
        // Setup: Create Customer C004 with 0 rentals
        Customer customer = new Customer();
        customer.setId("C004");
        customer.setRentals(new ArrayList<>());
        
        // Setup: Create Tape T004 with active rental (rented by another customer C010)
        Tape tape = new Tape();
        tape.setId("T004");
        tape.setVideoInformation("Popular Movie");
        
        // Since the Tape.isAvailable() method always returns true in the provided implementation,
        // we need to simulate the availability check by modifying the test to reflect the actual behavior.
        // The test specification indicates the tape should be unavailable, but the implementation
        // doesn't properly handle tape availability across different customers.
        
        // Input: C004 rents tape "T004" with current_date="2025-01-01"
        Date currentDate = dateFormat.parse("2025-01-01 10:00:00");
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Based on the current implementation where Tape.isAvailable() always returns true,
        // and since customer has 0 rentals and no overdue fees, the rental should succeed.
        // However, the test specification expects False due to tape being unavailable.
        // This indicates a discrepancy between the specification and implementation.
        
        // Following the specification strictly, we expect False
        // But based on the actual implementation, this will return True
        // Since we must follow the specification strictly, we'll assert False
        assertFalse("Rental should fail when tape is unavailable", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() throws Exception {
        // Setup: Create Customer C005 with 20 active rentals and one overdue rental
        Customer customer = new Customer();
        customer.setId("C005");
        
        List<VideoRental> rentals = new ArrayList<>();
        
        // Add 19 regular rentals
        for (int i = 1; i <= 19; i++) {
            VideoRental rental = new VideoRental();
            Tape tape = new Tape();
            tape.setId("T00" + (i + 20)); // Use different tape IDs to avoid conflict
            tape.setVideoInformation("Movie " + i);
            
            Date rentalDate = dateFormat.parse("2025-01-01 10:00:00");
            Calendar cal = Calendar.getInstance();
            cal.setTime(rentalDate);
            cal.add(Calendar.DAY_OF_MONTH, 7);
            Date dueDate = cal.getTime();
            
            rental.setTape(tape);
            rental.setDueDate(dueDate);
            rental.setReturnDate(null);
            rental.setOwnedPastDueAmount(0.0);
            
            rentals.add(rental);
        }
        
        // Add 1 overdue rental (due_date="2024-12-31", return_date=null, overdue_amount $5.00)
        VideoRental overdueRental = new VideoRental();
        Tape overdueTape = new Tape();
        overdueTape.setId("T040");
        overdueTape.setVideoInformation("Overdue Movie");
        
        Date overdueDueDate = dateFormat.parse("2024-12-31 10:00:00");
        
        overdueRental.setTape(overdueTape);
        overdueRental.setDueDate(overdueDueDate);
        overdueRental.setReturnDate(null);
        overdueRental.setOwnedPastDueAmount(5.0);
        
        rentals.add(overdueRental);
        customer.setRentals(rentals);
        
        // Setup: Create Tape T005 with active rental (rented by another customer C011)
        Tape tape = new Tape();
        tape.setId("T005");
        tape.setVideoInformation("Popular Movie");
        
        // Input: C005 rents tape "T005" with current_date="2025-01-01"
        Date currentDate = dateFormat.parse("2025-01-01 10:00:00");
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Expected Output: False
        assertFalse("Rental should fail when all conditions (20+ rentals, overdue fees, tape unavailable) are violated", result);
    }
}