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
    private LocalDateTime currentDate;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        currentDate = LocalDateTime.parse("2025-01-01 00:00:00", formatter);
    }
    
    @Test
    public void testCase1_SuccessfulRental() {
        // Setup: Create Customer C001 with 5 active rentals
        customer = new Customer();
        customer.setId("C001");
        
        // Create 5 active rentals with rental dates from 2025-01-01 to 2025-01-05
        List<VideoRental> rentals = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            VideoRental rental = new VideoRental();
            Tape tempTape = new Tape();
            tempTape.setId("TEMP" + i);
            rental.setTape(tempTape);
            
            LocalDateTime rentalDate = LocalDateTime.parse("2025-01-0" + (i+1) + " 00:00:00", formatter);
            rental.setDueDate(rentalDate.plusDays(7));
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        // Create available Tape T001
        tape = new Tape();
        tape.setId("T001");
        tape.setVideoInformation("Available Tape");
        
        // Test: Customer rents tape T001
        boolean result = customer.addVideoTapeRental(tape, currentDate);
        
        // Verify: Should return true (successful rental)
        assertTrue("Rental should be successful when customer has <20 rentals and no past-due amount", result);
    }
    
    @Test
    public void testCase2_CustomerHas20Rentals() {
        // Setup: Create Customer C002 with 20 active rentals
        customer = new Customer();
        customer.setId("C002");
        
        // Create 20 active rentals
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
        
        // Create available Tape T002
        tape = new Tape();
        tape.setId("T002");
        tape.setVideoInformation("Available Tape");
        
        // Test: Customer with 20 rentals tries to rent another tape
        boolean result = customer.addVideoTapeRental(tape, currentDate);
        
        // Verify: Should return false (max limit reached)
        assertFalse("Rental should fail when customer has 20 active rentals", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup: Create Customer C003 with 1 overdue rental
        customer = new Customer();
        customer.setId("C003");
        
        // Create one overdue rental (due date is today, making it overdue)
        List<VideoRental> rentals = new ArrayList<>();
        VideoRental overdueRental = new VideoRental();
        Tape tempTape = new Tape();
        tempTape.setId("TEMP");
        overdueRental.setTape(tempTape);
        
        // Set due date to current date (making it overdue)
        overdueRental.setDueDate(currentDate);
        rentals.add(overdueRental);
        customer.setRentals(rentals);
        
        // Create available Tape T003
        tape = new Tape();
        tape.setId("T003");
        tape.setVideoInformation("Available Tape");
        
        // Test: Customer with overdue fees tries to rent a tape
        boolean result = customer.addVideoTapeRental(tape, currentDate);
        
        // Verify: Should return false (customer has overdue fees)
        assertFalse("Rental should fail when customer has overdue fees", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup: Create Customer C004 with 0 rentals
        customer = new Customer();
        customer.setId("C004");
        
        // Create Tape T004 that is unavailable (simulate by overriding isAvailable method)
        tape = new Tape() {
            @Override
            public boolean isAvailable(LocalDateTime currentDate) {
                return false; // Tape is unavailable
            }
        };
        tape.setId("T004");
        tape.setVideoInformation("Unavailable Tape");
        
        // Test: Customer tries to rent an unavailable tape
        boolean result = customer.addVideoTapeRental(tape, currentDate);
        
        // Verify: Should return false (tape is unavailable)
        assertFalse("Rental should fail when tape is unavailable", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup: Create Customer C005 with 20 rentals and one overdue rental
        customer = new Customer();
        customer.setId("C005");
        
        // Create 20 active rentals including one overdue
        List<VideoRental> rentals = new ArrayList<>();
        for (int i = 0; i < 19; i++) {
            VideoRental rental = new VideoRental();
            Tape tempTape = new Tape();
            tempTape.setId("TEMP" + i);
            rental.setTape(tempTape);
            rental.setDueDate(currentDate.plusDays(7));
            rentals.add(rental);
        }
        
        // Add one overdue rental
        VideoRental overdueRental = new VideoRental();
        Tape overdueTape = new Tape();
        overdueTape.setId("OVERDUE");
        overdueRental.setTape(overdueTape);
        overdueRental.setDueDate(LocalDateTime.parse("2024-12-31 00:00:00", formatter)); // Overdue
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
        tape.setVideoInformation("Unavailable Tape");
        
        // Test: Customer with all failing conditions tries to rent a tape
        boolean result = customer.addVideoTapeRental(tape, currentDate);
        
        // Verify: Should return false (all conditions fail)
        assertFalse("Rental should fail when all conditions are violated", result);
    }
}