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
            Tape rentalTape = new Tape();
            rentalTape.setId("T00" + (i + 100)); // Different tape IDs
            rental.setTape(rentalTape);
            rental.setDueDate("2025-01-" + String.format("%02d", i + 7)); // Due date 7 days after rental
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        // Setup: Create available Tape T001
        tape.setId("T001");
        
        // Execute test
        boolean result = customer.addVideoTapeRental(tape, "2025-01-01");
        
        // Verify result
        assertTrue("Rental should be successful when customer has <20 rentals and no overdue fees", result);
    }
    
    @Test
    public void testCase2_CustomerHas20RentalsMaxLimit() {
        // Setup: Create Customer C002 with 20 active rentals
        customer.setId("C002");
        List<VideoRental> rentals = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = new VideoRental();
            Tape rentalTape = new Tape();
            rentalTape.setId("T00" + (i + 100)); // Different tape IDs
            rental.setTape(rentalTape);
            rental.setDueDate("2025-01-" + String.format("%02d", i + 7)); // Due date 7 days after rental
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        // Setup: Create available Tape T002
        tape.setId("T002");
        
        // Execute test
        boolean result = customer.addVideoTapeRental(tape, "2025-01-01");
        
        // Verify result
        assertFalse("Rental should fail when customer has 20 rentals (max limit)", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup: Create Customer C003 with 1 active rental that is 3 days overdue
        customer.setId("C003");
        List<VideoRental> rentals = new ArrayList<>();
        
        VideoRental overdueRental = new VideoRental();
        Tape overdueTape = new Tape();
        overdueTape.setId("T099"); // Different tape ID
        overdueRental.setTape(overdueTape);
        overdueRental.setDueDate("2025-01-02"); // Due date 3 days before current date
        rentals.add(overdueRental);
        
        customer.setRentals(rentals);
        
        // Setup: Create available Tape T003
        tape.setId("T003");
        
        // Execute test (current date is 2025-01-05, rental is 3 days overdue)
        boolean result = customer.addVideoTapeRental(tape, "2025-01-05");
        
        // Verify result
        assertFalse("Rental should fail when customer has overdue fees", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup: Create Customer C004 with 0 rentals
        customer.setId("C004");
        
        // Setup: Mock Tape T004 to be unavailable by overriding isAvailable method
        tape.setId("T004");
        Tape unavailableTape = new Tape() {
            @Override
            public boolean isAvailable(String currentDate) {
                return false; // Tape is not available
            }
        };
        unavailableTape.setId("T004");
        
        // Execute test
        boolean result = customer.addVideoTapeRental(unavailableTape, "2025-01-01");
        
        // Verify result
        assertFalse("Rental should fail when tape is unavailable", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup: Create Customer C005 with 20 active rentals and one overdue rental
        customer.setId("C005");
        List<VideoRental> rentals = new ArrayList<>();
        
        // Add 19 normal rentals
        for (int i = 1; i <= 19; i++) {
            VideoRental rental = new VideoRental();
            Tape rentalTape = new Tape();
            rentalTape.setId("T00" + (i + 100)); // Different tape IDs
            rental.setTape(rentalTape);
            rental.setDueDate("2025-01-" + String.format("%02d", i + 7)); // Due date 7 days after rental
            rentals.add(rental);
        }
        
        // Add 1 overdue rental (due date 2024-12-31, current date 2025-01-01 = 1 day overdue)
        VideoRental overdueRental = new VideoRental();
        Tape overdueTape = new Tape();
        overdueTape.setId("T199");
        overdueRental.setTape(overdueTape);
        overdueRental.setDueDate("2024-12-31"); // Due date before current date
        rentals.add(overdueRental);
        
        customer.setRentals(rentals);
        
        // Setup: Mock Tape T005 to be unavailable
        Tape unavailableTape = new Tape() {
            @Override
            public boolean isAvailable(String currentDate) {
                return false; // Tape is not available
            }
        };
        unavailableTape.setId("T005");
        
        // Execute test
        boolean result = customer.addVideoTapeRental(unavailableTape, "2025-01-01");
        
        // Verify result
        assertFalse("Rental should fail when all conditions are violated", result);
    }
}