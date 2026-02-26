import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        for (int i = 1; i <= 5; i++) {
            VideoRental rental = new VideoRental();
            Tape rentalTape = new Tape();
            rentalTape.setId("T00" + (i + 1));
            rental.setTape(rentalTape);
            rental.setDueDate(LocalDate.of(2025, 1, i).plusDays(7)); // Due date 7 days after rental
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        // Setup: Create available Tape T001
        tape.setId("T001");
        
        // Test: C001 rents tape "T001" with current_date="2025-01-01"
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Expected Output: True
        assertTrue("Rental should be successful", result);
    }
    
    @Test
    public void testCase2_CustomerHasMaxRentals() {
        // Setup: Create Customer C002 with 20 active rentals
        customer.setId("C002");
        List<VideoRental> rentals = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = new VideoRental();
            Tape rentalTape = new Tape();
            rentalTape.setId("T00" + (i + 10));
            rental.setTape(rentalTape);
            rental.setDueDate(currentDate.plusDays(7)); // Due date 7 days after rental
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        // Setup: Create available Tape T002
        tape.setId("T002");
        
        // Test: C002 rents tape "T002" with current_date="2025-01-01"
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail due to max rentals limit", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup: Create Customer C003 with 1 active rental, 3 days overdue
        customer.setId("C003");
        List<VideoRental> rentals = new ArrayList<>();
        
        VideoRental overdueRental = new VideoRental();
        Tape rentalTape = new Tape();
        rentalTape.setId("T00X");
        overdueRental.setTape(rentalTape);
        overdueRental.setDueDate(LocalDate.of(2025, 1, 2)); // Due date "2025-01-02" (3 days overdue from "2025-01-05")
        // return_date remains null (unreturned)
        rentals.add(overdueRental);
        
        customer.setRentals(rentals);
        
        // Setup: Create available Tape T003
        tape.setId("T003");
        
        // Test: C003 rents tape "T003" with current_date="2025-01-05"
        boolean result = customer.addVedioTapeRental(tape, LocalDate.of(2025, 1, 5));
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail due to overdue fees", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup: Create Customer C004 with 0 rentals
        customer.setId("C004");
        customer.setRentals(new ArrayList<>());
        
        // Setup: Create Tape T004 that is unavailable
        tape.setId("T004");
        // To make the tape unavailable, we need to override the isAvailable method
        // Since we can't modify the original class, we'll create a mock behavior
        // The original implementation always returns true, so we need to simulate unavailability
        
        // For testing purposes, we'll create a custom Tape subclass that simulates unavailability
        Tape unavailableTape = new Tape() {
            @Override
            public boolean isAvailable(LocalDate currentDate) {
                return false; // Tape is unavailable
            }
        };
        unavailableTape.setId("T004");
        
        // Test: C004 rents tape "T004" with current_date="2025-01-01"
        boolean result = customer.addVedioTapeRental(unavailableTape, currentDate);
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail due to tape unavailability", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup: Create Customer C005 with 20 active rentals and one overdue rental
        customer.setId("C005");
        List<VideoRental> rentals = new ArrayList<>();
        
        // Add 20 active rentals
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = new VideoRental();
            Tape rentalTape = new Tape();
            rentalTape.setId("T00" + (i + 20));
            rental.setTape(rentalTape);
            rental.setDueDate(currentDate.plusDays(7));
            rentals.add(rental);
        }
        
        // Add one overdue rental
        VideoRental overdueRental = new VideoRental();
        Tape overdueTape = new Tape();
        overdueTape.setId("T00Y");
        overdueRental.setTape(overdueTape);
        overdueRental.setDueDate(LocalDate.of(2024, 12, 31)); // Overdue rental
        // return_date remains null (unreturned)
        rentals.add(overdueRental);
        
        customer.setRentals(rentals);
        
        // Setup: Create Tape T005 that is unavailable
        tape.setId("T005");
        // Create a custom Tape subclass that simulates unavailability
        Tape unavailableTape = new Tape() {
            @Override
            public boolean isAvailable(LocalDate currentDate) {
                return false; // Tape is unavailable
            }
        };
        unavailableTape.setId("T005");
        
        // Test: C005 rents tape "T005" with current_date="2025-01-01"
        boolean result = customer.addVedioTapeRental(unavailableTape, currentDate);
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail due to all conditions failing", result);
    }
}