import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Customer customer;
    private Tape tape;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_successfulRental() {
        // Setup: Create Customer C001 with 5 active rentals
        customer = new Customer();
        customer.setId("C001");
        
        List<VideoRental> rentals = new ArrayList<>();
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-01 00:00:00", formatter);
        
        // Create 5 active rentals with rental dates from 2025-01-01 to 2025-01-05
        for (int i = 0; i < 5; i++) {
            VideoRental rental = new VideoRental();
            Tape rentalTape = new Tape();
            rentalTape.setId("T00" + (i + 10)); // T010, T011, etc.
            rental.setTape(rentalTape);
            rental.setDueDate(currentDate.plusDays(7));
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        // Create available Tape T001
        tape = new Tape();
        tape.setId("T001");
        
        // Execute: C001 rents tape T001
        boolean result = customer.addVideoTapeRental(tape, currentDate);
        
        // Verify: Should return true (successful rental)
        assertTrue("Rental should be successful when customer has <20 rentals and no past-due amount", result);
        assertEquals("Customer should now have 6 rentals", 6, customer.getRentals().size());
    }
    
    @Test
    public void testCase2_customerHasMaxRentals() {
        // Setup: Create Customer C002 with 20 active rentals
        customer = new Customer();
        customer.setId("C002");
        
        List<VideoRental> rentals = new ArrayList<>();
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-01 00:00:00", formatter);
        
        // Create 20 active rentals
        for (int i = 0; i < 20; i++) {
            VideoRental rental = new VideoRental();
            Tape rentalTape = new Tape();
            rentalTape.setId("T0" + String.format("%02d", i + 20)); // T020, T021, etc.
            rental.setTape(rentalTape);
            rental.setDueDate(currentDate.plusDays(7));
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        // Create available Tape T002
        tape = new Tape();
        tape.setId("T002");
        
        // Execute: C002 rents tape T002
        boolean result = customer.addVideoTapeRental(tape, currentDate);
        
        // Verify: Should return false (max rentals reached)
        assertFalse("Rental should fail when customer has 20 rentals", result);
        assertEquals("Customer should still have 20 rentals", 20, customer.getRentals().size());
    }
    
    @Test
    public void testCase3_customerHasOverdueFees() {
        // Setup: Create Customer C003 with 1 overdue rental
        customer = new Customer();
        customer.setId("C003");
        
        List<VideoRental> rentals = new ArrayList<>();
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-05 00:00:00", formatter);
        
        // Create 1 overdue rental (due date was 2025-01-02, 3 days overdue)
        VideoRental overdueRental = new VideoRental();
        Tape overdueTape = new Tape();
        overdueTape.setId("T030");
        overdueRental.setTape(overdueTape);
        overdueRental.setDueDate(LocalDateTime.parse("2025-01-02 00:00:00", formatter));
        // return_date is null (not returned)
        rentals.add(overdueRental);
        
        customer.setRentals(rentals);
        
        // Create available Tape T003
        tape = new Tape();
        tape.setId("T003");
        
        // Execute: C003 rents tape T003
        boolean result = customer.addVideoTapeRental(tape, currentDate);
        
        // Verify: Should return false (customer has overdue fees)
        assertFalse("Rental should fail when customer has overdue fees", result);
        assertEquals("Customer should still have 1 rental", 1, customer.getRentals().size());
    }
    
    @Test
    public void testCase4_tapeIsUnavailable() {
        // Setup: Create Customer C004 with 0 rentals
        customer = new Customer();
        customer.setId("C004");
        
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-01 00:00:00", formatter);
        
        // Create Tape T004 that is unavailable (mocking the unavailable state)
        tape = new Tape() {
            @Override
            public boolean isAvailable(LocalDateTime currentDate) {
                return false; // Tape is unavailable
            }
        };
        tape.setId("T004");
        
        // Execute: C004 rents tape T004
        boolean result = customer.addVideoTapeRental(tape, currentDate);
        
        // Verify: Should return false (tape is unavailable)
        assertFalse("Rental should fail when tape is unavailable", result);
        assertEquals("Customer should still have 0 rentals", 0, customer.getRentals().size());
    }
    
    @Test
    public void testCase5_allConditionsFail() {
        // Setup: Create Customer C005 with 20 rentals and overdue fees
        customer = new Customer();
        customer.setId("C005");
        
        List<VideoRental> rentals = new ArrayList<>();
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-01 00:00:00", formatter);
        
        // Create 20 active rentals
        for (int i = 0; i < 20; i++) {
            VideoRental rental = new VideoRental();
            Tape rentalTape = new Tape();
            rentalTape.setId("T0" + String.format("%02d", i + 40)); // T040, T041, etc.
            rental.setTape(rentalTape);
            rental.setDueDate(currentDate.plusDays(7));
            rentals.add(rental);
        }
        
        // Add one overdue rental (due date was 2024-12-31, 1 day overdue)
        VideoRental overdueRental = new VideoRental();
        Tape overdueTape = new Tape();
        overdueTape.setId("T060");
        overdueRental.setTape(overdueTape);
        overdueRental.setDueDate(LocalDateTime.parse("2024-12-31 00:00:00", formatter));
        // return_date is null (not returned)
        rentals.add(overdueRental);
        
        customer.setRentals(rentals);
        
        // Create Tape T005 that is unavailable
        tape = new Tape() {
            @Override
            public boolean isAvailable(LocalDateTime currentDate) {
                return false; // Tape is unavailable
            }
        };
        tape.setId("T005");
        
        // Execute: C005 rents tape T005
        boolean result = customer.addVideoTapeRental(tape, currentDate);
        
        // Verify: Should return false (all conditions fail)
        assertFalse("Rental should fail when all conditions fail", result);
        assertEquals("Customer should still have 21 rentals", 21, customer.getRentals().size());
    }
}