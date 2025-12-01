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
    }
    
    @Test
    public void testCase1_successfulRental() {
        // Setup
        currentDate = LocalDateTime.parse("2025-01-01 00:00:00", formatter);
        
        // Create Customer C001 with 5 active rentals
        customer = new Customer();
        customer.setId("C001");
        
        List<VideoRental> rentals = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            VideoRental rental = new VideoRental();
            Tape tempTape = new Tape();
            tempTape.setId("TEMP" + i);
            tempTape.setVideoInformation("Temporary Tape " + i);
            rental.setTape(tempTape);
            
            LocalDateTime rentalDate = LocalDateTime.parse("2025-01-0" + i + " 00:00:00", formatter);
            rental.setDueDate(rentalDate.plusDays(7));
            rental.setReturnDate(null);
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        // Create available Tape T001
        tape = new Tape();
        tape.setId("T001");
        tape.setVideoInformation("Available Tape T001");
        
        // Test
        boolean result = customer.addVideoTapeRental(tape, currentDate);
        
        // Verify
        assertTrue("Rental should be successful when customer has <20 rentals and no past due amount", result);
        assertEquals("Customer should now have 6 rentals", 6, customer.getRentals().size());
        
        // Verify the new rental was added correctly
        VideoRental newRental = customer.getRentals().get(5);
        assertEquals("Tape should be T001", "T001", newRental.getTape().getId());
        assertEquals("Due date should be 7 days from current date", 
                     currentDate.plusDays(7), newRental.getDueDate());
        assertNull("Return date should be null for new rental", newRental.getReturnDate());
    }
    
    @Test
    public void testCase2_customerHas20RentalsMaxLimit() {
        // Setup
        currentDate = LocalDateTime.parse("2025-01-01 00:00:00", formatter);
        
        // Create Customer C002 with 20 active rentals
        customer = new Customer();
        customer.setId("C002");
        
        List<VideoRental> rentals = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = new VideoRental();
            Tape tempTape = new Tape();
            tempTape.setId("TEMP" + i);
            tempTape.setVideoInformation("Temporary Tape " + i);
            rental.setTape(tempTape);
            
            LocalDateTime rentalDate = LocalDateTime.parse("2025-01-01 00:00:00", formatter);
            rental.setDueDate(rentalDate.plusDays(7));
            rental.setReturnDate(null);
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        // Create available Tape T002
        tape = new Tape();
        tape.setId("T002");
        tape.setVideoInformation("Available Tape T002");
        
        // Test
        boolean result = customer.addVideoTapeRental(tape, currentDate);
        
        // Verify
        assertFalse("Rental should fail when customer has 20 rentals (max limit)", result);
        assertEquals("Customer should still have 20 rentals", 20, customer.getRentals().size());
    }
    
    @Test
    public void testCase3_customerHasOverdueFees() {
        // Setup
        currentDate = LocalDateTime.parse("2025-01-05 00:00:00", formatter);
        
        // Create Customer C003 with 1 active rental that is 3 days overdue
        customer = new Customer();
        customer.setId("C003");
        
        List<VideoRental> rentals = new ArrayList<>();
        VideoRental overdueRental = new VideoRental();
        Tape overdueTape = new Tape();
        overdueTape.setId("OVERDUE");
        overdueTape.setVideoInformation("Overdue Tape");
        overdueRental.setTape(overdueTape);
        
        // Due date was 3 days ago (2025-01-02), making it 3 days overdue on 2025-01-05
        LocalDateTime dueDate = LocalDateTime.parse("2025-01-02 00:00:00", formatter);
        overdueRental.setDueDate(dueDate);
        overdueRental.setReturnDate(null);
        rentals.add(overdueRental);
        
        customer.setRentals(rentals);
        
        // Create available Tape T003
        tape = new Tape();
        tape.setId("T003");
        tape.setVideoInformation("Available Tape T003");
        
        // Test
        boolean result = customer.addVideoTapeRental(tape, currentDate);
        
        // Verify
        assertFalse("Rental should fail when customer has overdue fees", result);
        assertEquals("Customer should still have 1 rental", 1, customer.getRentals().size());
        
        // Verify overdue amount calculation
        double overdueAmount = customer.calculateTotalPastDueAmount(currentDate);
        assertEquals("Overdue amount should be 1.50 for 3 days overdue", 1.50, overdueAmount, 0.001);
    }
    
    @Test
    public void testCase4_tapeIsUnavailable() {
        // Setup - Create a custom Tape class that overrides isAvailable to return false
        currentDate = LocalDateTime.parse("2025-01-01 00:00:00", formatter);
        
        // Create Customer C004 with 0 rentals
        customer = new Customer();
        customer.setId("C004");
        
        // Create Tape T004 that is unavailable
        tape = new Tape() {
            @Override
            public boolean isAvailable(LocalDateTime currentDate) {
                // Simulate tape being rented by another customer
                return false;
            }
        };
        tape.setId("T004");
        tape.setVideoInformation("Unavailable Tape T004");
        
        // Test
        boolean result = customer.addVideoTapeRental(tape, currentDate);
        
        // Verify
        assertFalse("Rental should fail when tape is unavailable", result);
        assertEquals("Customer should still have 0 rentals", 0, customer.getRentals().size());
    }
    
    @Test
    public void testCase5_allConditionsFail() {
        // Setup
        currentDate = LocalDateTime.parse("2025-01-01 00:00:00", formatter);
        
        // Create Customer C005 with 20 active rentals and one overdue rental
        customer = new Customer();
        customer.setId("C005");
        
        List<VideoRental> rentals = new ArrayList<>();
        
        // Add 19 regular rentals
        for (int i = 1; i <= 19; i++) {
            VideoRental rental = new VideoRental();
            Tape tempTape = new Tape();
            tempTape.setId("TEMP" + i);
            tempTape.setVideoInformation("Temporary Tape " + i);
            rental.setTape(tempTape);
            
            LocalDateTime rentalDate = LocalDateTime.parse("2025-01-01 00:00:00", formatter);
            rental.setDueDate(rentalDate.plusDays(7));
            rental.setReturnDate(null);
            rentals.add(rental);
        }
        
        // Add 1 overdue rental (due date was yesterday)
        VideoRental overdueRental = new VideoRental();
        Tape overdueTape = new Tape();
        overdueTape.setId("OVERDUE");
        overdueTape.setVideoInformation("Overdue Tape");
        overdueRental.setTape(overdueTape);
        
        LocalDateTime overdueDueDate = LocalDateTime.parse("2024-12-31 00:00:00", formatter);
        overdueRental.setDueDate(overdueDueDate);
        overdueRental.setReturnDate(null);
        rentals.add(overdueRental);
        
        customer.setRentals(rentals);
        
        // Create Tape T005 that is unavailable
        tape = new Tape() {
            @Override
            public boolean isAvailable(LocalDateTime currentDate) {
                // Simulate tape being rented by another customer C011
                return false;
            }
        };
        tape.setId("T005");
        tape.setVideoInformation("Unavailable Tape T005");
        
        // Test
        boolean result = customer.addVideoTapeRental(tape, currentDate);
        
        // Verify
        assertFalse("Rental should fail when all conditions fail (20 rentals + overdue + unavailable tape)", result);
        assertEquals("Customer should still have 20 rentals", 20, customer.getRentals().size());
        
        // Verify overdue amount calculation ($5.00 as specified)
        double overdueAmount = customer.calculateTotalPastDueAmount(currentDate);
        assertEquals("Overdue amount should be 0.50 for 1 day overdue", 0.50, overdueAmount, 0.001);
    }
}