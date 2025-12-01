import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Customer customer;
    private Tape tape;
    private Date currentDate;
    
    @Before
    public void setUp() {
        customer = new Customer();
        tape = new Tape();
        currentDate = new Date("2025-01-01 00:00:00");
    }
    
    @Test
    public void testCase1_SuccessfulRental() {
        // Setup: Create Customer C001 with 5 active rentals
        customer.setId("C001");
        for (int i = 1; i <= 5; i++) {
            VideoRental rental = new VideoRental();
            Tape tempTape = new Tape();
            tempTape.setId("TEMP" + i);
            rental.setTape(tempTape);
            rental.setDueDate(new Date("2025-01-" + String.format("%02d", i + 7) + " 00:00:00"));
            customer.getRentals().add(rental);
        }
        
        // Setup: Create available Tape T001 with no active rentals
        tape.setId("T001");
        tape.setRentals(new ArrayList<>());
        
        // Test: C001 rents tape "T001" with current_date="2025-01-01"
        boolean result = customer.addVideoTapeRental(tape, currentDate);
        
        // Verify: Expected Output: True
        assertTrue("Rental should be successful when customer has <20 rentals, no past-due amount, and tape is available", result);
    }
    
    @Test
    public void testCase2_CustomerHas20RentalsMaxLimit() {
        // Setup: Create Customer C002 with 20 active rentals
        customer.setId("C002");
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = new VideoRental();
            Tape tempTape = new Tape();
            tempTape.setId("TEMP" + i);
            rental.setTape(tempTape);
            rental.setDueDate(new Date("2025-01-" + String.format("%02d", i + 7) + " 00:00:00"));
            customer.getRentals().add(rental);
        }
        
        // Setup: Create available Tape T002
        tape.setId("T002");
        tape.setRentals(new ArrayList<>());
        
        // Test: C002 rents tape "T002" with current_date="2025-01-01"
        boolean result = customer.addVideoTapeRental(tape, currentDate);
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail when customer has 20 active rentals (max limit)", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup: Create Customer C003 with 1 active rental, due_date="2025-01-02", return_date=null (3 days overdue)
        customer.setId("C003");
        VideoRental overdueRental = new VideoRental();
        Tape tempTape = new Tape();
        tempTape.setId("TEMP1");
        overdueRental.setTape(tempTape);
        overdueRental.setDueDate(new Date("2025-01-02 00:00:00")); // Due date before current date
        customer.getRentals().add(overdueRental);
        
        // Setup: Create available Tape T003
        tape.setId("T003");
        tape.setRentals(new ArrayList<>());
        
        // Test: C003 rents tape "T003" with current_date="2025-01-05" (3 days after due date)
        Date testCurrentDate = new Date("2025-01-05 00:00:00");
        boolean result = customer.addVideoTapeRental(tape, testCurrentDate);
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail when customer has overdue fees", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup: Create Customer C004 with 0 rentals
        customer.setId("C004");
        customer.setRentals(new ArrayList<>());
        
        // Setup: Create Tape T004 with active rental (rented by another customer C010, return_date=null)
        tape.setId("T004");
        VideoRental activeRental = new VideoRental();
        activeRental.setReturnDate(null); // Tape is currently rented out
        activeRental.setDueDate(new Date("2025-01-05 00:00:00"));
        List<VideoRental> tapeRentals = new ArrayList<>();
        tapeRentals.add(activeRental);
        tape.setRentals(tapeRentals);
        
        // Test: C004 rents tape "T004" with current_date="2025-01-01"
        boolean result = customer.addVideoTapeRental(tape, currentDate);
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail when tape is unavailable", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup: Create Customer C005 with 20 active rentals and one overdue rental
        customer.setId("C005");
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = new VideoRental();
            Tape tempTape = new Tape();
            tempTape.setId("TEMP" + i);
            rental.setTape(tempTape);
            if (i == 1) {
                // First rental is overdue (due date before current date)
                rental.setDueDate(new Date("2024-12-31 00:00:00"));
            } else {
                rental.setDueDate(new Date("2025-01-" + String.format("%02d", i + 7) + " 00:00:00"));
            }
            customer.getRentals().add(rental);
        }
        
        // Setup: Create Tape T005 with active rental (rented by another customer C011)
        tape.setId("T005");
        VideoRental activeRental = new VideoRental();
        activeRental.setReturnDate(null); // Tape is currently rented out
        activeRental.setDueDate(new Date("2025-01-05 00:00:00"));
        List<VideoRental> tapeRentals = new ArrayList<>();
        tapeRentals.add(activeRental);
        tape.setRentals(tapeRentals);
        
        // Test: C005 rents tape "T005" with current_date="2025-01-01"
        boolean result = customer.addVideoTapeRental(tape, currentDate);
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail when all conditions (20 rentals, overdue fees, tape unavailable) are violated", result);
    }
}