import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Customer customer;
    private Tape tape;
    
    @Before
    public void setUp() {
        customer = new Customer();
        tape = new Tape();
    }
    
    @Test
    public void testCase1_SuccessfulRental() {
        // Setup: Create Customer C001 with 5 active rentals
        customer.setId("C001");
        List<VideoRental> rentals = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            VideoRental rental = new VideoRental();
            Tape tempTape = new Tape();
            tempTape.setId("T00" + (i + 100)); // Different tape IDs
            rental.setTape(tempTape);
            rental.setDueDate("2025-01-0" + (i + 7)); // Due date 7 days after rental
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        // Setup: Create available Tape T001 with no active rentals
        tape.setId("T001");
        tape.setRentals(new ArrayList<>());
        
        // Execute: C001 rents tape "T001" with current_date="2025-01-01"
        boolean result = customer.addVideoTapeRental(tape, "2025-01-01");
        
        // Verify: Expected Output: True
        assertTrue(result);
    }
    
    @Test
    public void testCase2_CustomerHas20RentalsMaxLimit() {
        // Setup: Create Customer C002 with 20 active rentals
        customer.setId("C002");
        List<VideoRental> rentals = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = new VideoRental();
            Tape tempTape = new Tape();
            tempTape.setId("T00" + (i + 200)); // Different tape IDs
            rental.setTape(tempTape);
            rental.setDueDate("2025-01-0" + (i + 7)); // Due date 7 days after rental
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        // Setup: Create available Tape T002
        tape.setId("T002");
        tape.setRentals(new ArrayList<>());
        
        // Execute: C002 rents tape "T002" with current_date="2025-01-01"
        boolean result = customer.addVideoTapeRental(tape, "2025-01-01");
        
        // Verify: Expected Output: False
        assertFalse(result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup: Create Customer C003 with 1 active rental, due_date="2025-01-05", return_date=null (3 days overdue)
        customer.setId("C003");
        List<VideoRental> rentals = new ArrayList<>();
        VideoRental rental = new VideoRental();
        Tape tempTape = new Tape();
        tempTape.setId("T300");
        rental.setTape(tempTape);
        rental.setDueDate("2025-01-05"); // Due date is 2025-01-05
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Setup: Create available Tape T003
        tape.setId("T003");
        tape.setRentals(new ArrayList<>());
        
        // Execute: C003 rents tape "T003" with current_date="2025-01-08" (3 days overdue)
        boolean result = customer.addVideoTapeRental(tape, "2025-01-08");
        
        // Verify: Expected Output: False
        assertFalse(result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup: Create Customer C004 with 0 rentals
        customer.setId("C004");
        customer.setRentals(new ArrayList<>());
        
        // Setup: Create Tape T004 with active rental (rented by another customer C010, return_date=null, due_date="2025-01-05")
        tape.setId("T004");
        List<VideoRental> tapeRentals = new ArrayList<>();
        VideoRental activeRental = new VideoRental();
        Tape tempTape = new Tape();
        tempTape.setId("T004");
        activeRental.setTape(tempTape);
        activeRental.setDueDate("2025-01-05");
        // return_date remains null (unreturned)
        tapeRentals.add(activeRental);
        tape.setRentals(tapeRentals);
        
        // Execute: C004 rents tape "T004" with current_date="2025-01-01"
        boolean result = customer.addVideoTapeRental(tape, "2025-01-01");
        
        // Verify: Expected Output: False
        assertFalse(result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup: Create Customer C005 with 20 active rentals and one overdue rental (due_date="2024-12-31", return_date=null, overdue_amount $5.00)
        customer.setId("C005");
        List<VideoRental> rentals = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = new VideoRental();
            Tape tempTape = new Tape();
            tempTape.setId("T00" + (i + 500)); // Different tape IDs
            rental.setTape(tempTape);
            rental.setDueDate("2025-01-0" + (i + 7)); // Due date 7 days after rental
            rentals.add(rental);
        }
        // Add one overdue rental
        VideoRental overdueRental = new VideoRental();
        Tape overdueTape = new Tape();
        overdueTape.setId("T599");
        overdueRental.setTape(overdueTape);
        overdueRental.setDueDate("2024-12-31"); // Overdue due date
        rentals.add(overdueRental);
        customer.setRentals(rentals);
        
        // Setup: Create Tape T005 with active rental (rented by another customer C011, return_date=null, due_date="2025-01-05")
        tape.setId("T005");
        List<VideoRental> tapeRentals = new ArrayList<>();
        VideoRental activeRental = new VideoRental();
        Tape tempTape = new Tape();
        tempTape.setId("T005");
        activeRental.setTape(tempTape);
        activeRental.setDueDate("2025-01-05");
        // return_date remains null (unreturned)
        tapeRentals.add(activeRental);
        tape.setRentals(tapeRentals);
        
        // Execute: C005 rents tape "T005" with current_date="2025-01-01"
        boolean result = customer.addVideoTapeRental(tape, "2025-01-01");
        
        // Verify: Expected Output: False
        assertFalse(result);
    }
}