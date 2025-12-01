import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Customer customer;
    private Tape tape;
    private LocalDate currentDate;
    
    @Before
    public void setUp() {
        customer = new Customer();
        tape = new Tape();
        currentDate = LocalDate.of(2025, 1, 1);
    }
    
    @Test
    public void testCase1_SuccessfulRental() {
        // Setup: Create Customer C001 with 5 active rentals
        customer.setId("C001");
        List<VideoRental> rentals = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            VideoRental rental = new VideoRental();
            Tape tempTape = new Tape();
            tempTape.setId("TEMP" + i);
            rental.setTape(tempTape);
            rental.setDueDate(currentDate.plusDays(7));
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        // Setup: Create available Tape T001
        tape.setId("T001");
        tape.setVideoInformation("Test Tape 1");
        
        // Execute: C001 rents tape "T001" with current_date="2025-01-01"
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Expected Output: True
        assertTrue("Rental should be successful when customer has <20 rentals, no past due amount, and tape is available", result);
    }
    
    @Test
    public void testCase2_CustomerHas20RentalsMaxLimit() {
        // Setup: Create Customer C002 with 20 active rentals
        customer.setId("C002");
        List<VideoRental> rentals = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            VideoRental rental = new VideoRental();
            Tape tempTape = new Tape();
            tempTape.setId("TEMP" + i);
            rental.setTape(tempTape);
            rental.setDueDate(currentDate.plusDays(7));
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        // Setup: Create available Tape T002
        tape.setId("T002");
        tape.setVideoInformation("Test Tape 2");
        
        // Execute: C002 rents tape "T002" with current_date="2025-01-01"
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail when customer has 20 rentals (max limit)", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup: Create Customer C003 with 1 active rental, due_date="2025-01-05", return_date=null (3 days overdue)
        customer.setId("C003");
        List<VideoRental> rentals = new ArrayList<>();
        VideoRental overdueRental = new VideoRental();
        Tape tempTape = new Tape();
        tempTape.setId("TEMP1");
        overdueRental.setTape(tempTape);
        overdueRental.setDueDate(LocalDate.of(2025, 1, 2)); // Due 3 days ago from current date 2025-01-05
        rentals.add(overdueRental);
        customer.setRentals(rentals);
        
        // Setup: Create available Tape T003
        tape.setId("T003");
        tape.setVideoInformation("Test Tape 3");
        
        // Execute: C003 rents tape "T003" with current_date="2025-01-05"
        LocalDate testCurrentDate = LocalDate.of(2025, 1, 5);
        boolean result = customer.addVedioTapeRental(tape, testCurrentDate);
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail when customer has overdue fees", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup: Create Customer C004 with 0 rentals
        customer.setId("C004");
        
        // Setup: Create Tape T004 with active rental (rented by another customer C010)
        // Since Tape.isAvailable() always returns true in the provided code, we need to mock this behavior
        // For this test, we'll create a custom Tape subclass that overrides isAvailable()
        Tape unavailableTape = new Tape() {
            @Override
            public boolean isAvailable(LocalDate currentDate) {
                return false; // Tape is unavailable
            }
        };
        unavailableTape.setId("T004");
        unavailableTape.setVideoInformation("Test Tape 4");
        
        // Execute: C004 rents tape "T004" with current_date="2025-01-01"
        boolean result = customer.addVedioTapeRental(unavailableTape, currentDate);
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail when tape is unavailable", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup: Create Customer C005 with 20 active rentals and one overdue rental
        customer.setId("C005");
        List<VideoRental> rentals = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            VideoRental rental = new VideoRental();
            Tape tempTape = new Tape();
            tempTape.setId("TEMP" + i);
            rental.setTape(tempTape);
            
            if (i == 0) {
                // First rental is overdue (due_date="2024-12-31", return_date=null)
                rental.setDueDate(LocalDate.of(2024, 12, 31));
            } else {
                rental.setDueDate(currentDate.plusDays(7));
            }
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        // Setup: Create Tape T005 with active rental (rented by another customer C011)
        // Create a custom Tape subclass that overrides isAvailable()
        Tape unavailableTape = new Tape() {
            @Override
            public boolean isAvailable(LocalDate currentDate) {
                return false; // Tape is unavailable
            }
        };
        unavailableTape.setId("T005");
        unavailableTape.setVideoInformation("Test Tape 5");
        
        // Execute: C005 rents tape "T005" with current_date="2025-01-01"
        boolean result = customer.addVedioTapeRental(unavailableTape, currentDate);
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail when all conditions (max rentals, overdue fees, tape availability) fail", result);
    }
}