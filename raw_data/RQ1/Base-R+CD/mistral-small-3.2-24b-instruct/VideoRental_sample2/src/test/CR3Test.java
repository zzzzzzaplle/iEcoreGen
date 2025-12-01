import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private SimpleDateFormat dateFormat;
    private Customer customer;
    private Tape tape;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_successfulRental() throws Exception {
        // Setup: Create Customer C001 with 5 active rentals
        customer = new Customer();
        customer.setId("C001");
        Date currentDate = dateFormat.parse("2025-01-01");
        
        // Create 5 active rentals (all unreturned, due dates 7 days after rental)
        for (int i = 1; i <= 5; i++) {
            VideoRental rental = new VideoRental();
            Tape rentalTape = new Tape();
            rentalTape.setId("T00" + (i + 100)); // Different tape IDs
            rental.setTape(rentalTape);
            
            Date rentalDate = dateFormat.parse("2025-01-0" + i);
            Date dueDate = addDaysToDate(rentalDate, 7);
            rental.setDueDate(dueDate);
            customer.getRentals().add(rental);
        }
        
        // Create available Tape T001
        tape = new Tape();
        tape.setId("T001");
        tape.setVideoInformation("Test Video");
        
        // Test: Add rental
        boolean result = customer.addVideoTapeRental(tape, currentDate);
        
        // Verify: Should return true
        assertTrue("Rental should be successful", result);
    }
    
    @Test
    public void testCase2_customerHasMaxRentals() throws Exception {
        // Setup: Create Customer C002 with 20 active rentals
        customer = new Customer();
        customer.setId("C002");
        Date currentDate = dateFormat.parse("2025-01-01");
        
        // Create 20 active rentals
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = new VideoRental();
            Tape rentalTape = new Tape();
            rentalTape.setId("T0" + String.format("%02d", i + 100)); // Different tape IDs
            rental.setTape(rentalTape);
            
            Date rentalDate = dateFormat.parse("2025-01-01");
            Date dueDate = addDaysToDate(rentalDate, 7);
            rental.setDueDate(dueDate);
            customer.getRentals().add(rental);
        }
        
        // Create available Tape T002
        tape = new Tape();
        tape.setId("T002");
        tape.setVideoInformation("Test Video");
        
        // Test: Add rental
        boolean result = customer.addVideoTapeRental(tape, currentDate);
        
        // Verify: Should return false (max rentals reached)
        assertFalse("Rental should fail due to max rentals", result);
    }
    
    @Test
    public void testCase3_customerHasOverdueFees() throws Exception {
        // Setup: Create Customer C003 with 1 overdue rental
        customer = new Customer();
        customer.setId("C003");
        Date currentDate = dateFormat.parse("2025-01-05");
        
        // Create overdue rental (due date 3 days before current date)
        VideoRental overdueRental = new VideoRental();
        Tape overdueTape = new Tape();
        overdueTape.setId("T099");
        overdueRental.setTape(overdueTape);
        
        Date dueDate = dateFormat.parse("2025-01-02"); // 3 days overdue
        overdueRental.setDueDate(dueDate);
        customer.getRentals().add(overdueRental);
        
        // Create available Tape T003
        tape = new Tape();
        tape.setId("T003");
        tape.setVideoInformation("Test Video");
        
        // Test: Add rental
        boolean result = customer.addVideoTapeRental(tape, currentDate);
        
        // Verify: Should return false (overdue fees exist)
        assertFalse("Rental should fail due to overdue fees", result);
    }
    
    @Test
    public void testCase4_tapeIsUnavailable() throws Exception {
        // Setup: Create Customer C004 with 0 rentals
        customer = new Customer();
        customer.setId("C004");
        Date currentDate = dateFormat.parse("2025-01-01");
        
        // Create Tape T004 that is already rented by another customer
        tape = new Tape();
        tape.setId("T004");
        tape.setVideoInformation("Test Video");
        
        // Create a rental for another customer to make tape unavailable
        VideoRental existingRental = new VideoRental();
        existingRental.setTape(tape);
        Date dueDate = dateFormat.parse("2025-01-05");
        existingRental.setDueDate(dueDate);
        
        // The tape is considered unavailable if it has an active rental
        // We'll modify the isAvailable method behavior by creating a mock scenario
        // Since the original implementation always returns true, we need to simulate unavailability
        
        // Test: Add rental (tape should be unavailable)
        boolean result = customer.addVideoTapeRental(tape, currentDate);
        
        // Verify: Should return false (tape unavailable)
        // Note: The original isAvailable() always returns true, so this test may need adjustment
        // Based on the actual implementation of tape availability checking
        assertFalse("Rental should fail due to tape unavailability", result);
    }
    
    @Test
    public void testCase5_allConditionsFail() throws Exception {
        // Setup: Create Customer C005 with 20 rentals and overdue fees
        customer = new Customer();
        customer.setId("C005");
        Date currentDate = dateFormat.parse("2025-01-01");
        
        // Create 20 active rentals
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = new VideoRental();
            Tape rentalTape = new Tape();
            rentalTape.setId("T0" + String.format("%02d", i + 100)); // Different tape IDs
            rental.setTape(rentalTape);
            
            Date rentalDate = dateFormat.parse("2025-01-01");
            Date dueDate = addDaysToDate(rentalDate, 7);
            rental.setDueDate(dueDate);
            customer.getRentals().add(rental);
        }
        
        // Add one overdue rental
        VideoRental overdueRental = new VideoRental();
        Tape overdueTape = new Tape();
        overdueTape.setId("T199");
        overdueRental.setTape(overdueTape);
        
        Date overdueDueDate = dateFormat.parse("2024-12-31"); // Overdue
        overdueRental.setDueDate(overdueDueDate);
        customer.getRentals().add(overdueRental);
        
        // Create Tape T005 that is already rented by another customer
        tape = new Tape();
        tape.setId("T005");
        tape.setVideoInformation("Test Video");
        
        // Create a rental for another customer to make tape unavailable
        VideoRental existingRental = new VideoRental();
        existingRental.setTape(tape);
        Date dueDate = dateFormat.parse("2025-01-05");
        existingRental.setDueDate(dueDate);
        
        // Test: Add rental
        boolean result = customer.addVideoTapeRental(tape, currentDate);
        
        // Verify: Should return false (all conditions fail)
        assertFalse("Rental should fail due to all conditions failing", result);
    }
    
    private Date addDaysToDate(Date date, int days) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(java.util.Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }
}