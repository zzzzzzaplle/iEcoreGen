import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CR3Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_successfulRental() throws Exception {
        // Setup: Create Customer C001 with 5 active rentals
        Customer customer = new Customer();
        customer.setId("C001");
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Add 5 active rentals with rental dates from 2025-01-01 to 2025-01-05
        for (int i = 0; i < 5; i++) {
            VideoRental rental = new VideoRental();
            Tape tape = new Tape();
            tape.setId("T00" + (i + 100)); // T0100 to T0104
            rental.setTape(tape);
            
            Date rentalDate = dateFormat.parse("2025-01-0" + (i + 1) + " 00:00:00");
            long dueDateMillis = rentalDate.getTime() + TimeUnit.DAYS.toMillis(7);
            rental.setDueDate(new Date(dueDateMillis));
            rental.setReturnDate(null);
            
            customer.getRentals().add(rental);
        }
        
        // Create available Tape T001
        Tape tapeToRent = new Tape();
        tapeToRent.setId("T001");
        
        // Test: Add video tape rental
        boolean result = customer.addVideoTapeRental(tapeToRent, currentDate);
        
        // Verify: Should return true since customer has <20 rentals, no past due amount, and tape is available
        assertTrue("Rental should be successful", result);
    }
    
    @Test
    public void testCase2_customerHas20RentalsMaxLimit() throws Exception {
        // Setup: Create Customer C002 with 20 active rentals
        Customer customer = new Customer();
        customer.setId("C002");
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Add 20 active rentals
        for (int i = 0; i < 20; i++) {
            VideoRental rental = new VideoRental();
            Tape tape = new Tape();
            tape.setId("T00" + (i + 200)); // T0200 to T0219
            rental.setTape(tape);
            
            long dueDateMillis = currentDate.getTime() + TimeUnit.DAYS.toMillis(7);
            rental.setDueDate(new Date(dueDateMillis));
            rental.setReturnDate(null);
            
            customer.getRentals().add(rental);
        }
        
        // Create available Tape T002
        Tape tapeToRent = new Tape();
        tapeToRent.setId("T002");
        
        // Test: Add video tape rental
        boolean result = customer.addVideoTapeRental(tapeToRent, currentDate);
        
        // Verify: Should return false since customer has 20 rentals (max limit)
        assertFalse("Rental should fail due to max rental limit", result);
    }
    
    @Test
    public void testCase3_customerHasOverdueFees() throws Exception {
        // Setup: Create Customer C003 with 1 overdue rental
        Customer customer = new Customer();
        customer.setId("C003");
        
        Date currentDate = dateFormat.parse("2025-01-05 00:00:00");
        Date dueDate = dateFormat.parse("2025-01-02 00:00:00"); // 3 days overdue
        
        // Add overdue rental
        VideoRental overdueRental = new VideoRental();
        Tape overdueTape = new Tape();
        overdueTape.setId("T0300");
        overdueRental.setTape(overdueTape);
        overdueRental.setDueDate(dueDate);
        overdueRental.setReturnDate(null); // Not returned yet
        
        customer.getRentals().add(overdueRental);
        
        // Create available Tape T003
        Tape tapeToRent = new Tape();
        tapeToRent.setId("T003");
        
        // Test: Add video tape rental
        boolean result = customer.addVideoTapeRental(tapeToRent, currentDate);
        
        // Verify: Should return false since customer has overdue fees
        assertFalse("Rental should fail due to overdue fees", result);
    }
    
    @Test
    public void testCase4_tapeIsUnavailable() throws Exception {
        // Setup: Create Customer C004 with 0 rentals
        Customer customer = new Customer();
        customer.setId("C004");
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Create Tape T004 that is currently rented by another customer
        Tape unavailableTape = new Tape();
        unavailableTape.setId("T004");
        
        // Mock the tape as unavailable by overriding isAvailable method
        Tape tapeToRent = new Tape() {
            @Override
            public boolean isAvailable(Date currentDate) {
                return false; // Tape is unavailable
            }
        };
        tapeToRent.setId("T004");
        
        // Test: Add video tape rental
        boolean result = customer.addVideoTapeRental(tapeToRent, currentDate);
        
        // Verify: Should return false since tape is unavailable
        assertFalse("Rental should fail due to tape unavailability", result);
    }
    
    @Test
    public void testCase5_allConditionsFail() throws Exception {
        // Setup: Create Customer C005 with 20 rentals and one overdue rental
        Customer customer = new Customer();
        customer.setId("C005");
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Add 20 active rentals (19 normal + 1 overdue)
        for (int i = 0; i < 19; i++) {
            VideoRental rental = new VideoRental();
            Tape tape = new Tape();
            tape.setId("T00" + (i + 500)); // T0500 to T0518
            rental.setTape(tape);
            
            long dueDateMillis = currentDate.getTime() + TimeUnit.DAYS.toMillis(7);
            rental.setDueDate(new Date(dueDateMillis));
            rental.setReturnDate(null);
            
            customer.getRentals().add(rental);
        }
        
        // Add one overdue rental
        VideoRental overdueRental = new VideoRental();
        Tape overdueTape = new Tape();
        overdueTape.setId("T0519");
        overdueRental.setTape(overdueTape);
        overdueRental.setDueDate(dateFormat.parse("2024-12-31 00:00:00")); // Overdue
        overdueRental.setReturnDate(null);
        customer.getRentals().add(overdueRental);
        
        // Create Tape T005 that is currently rented by another customer
        Tape tapeToRent = new Tape() {
            @Override
            public boolean isAvailable(Date currentDate) {
                return false; // Tape is unavailable
            }
        };
        tapeToRent.setId("T005");
        
        // Test: Add video tape rental
        boolean result = customer.addVideoTapeRental(tapeToRent, currentDate);
        
        // Verify: Should return false since all conditions fail
        assertFalse("Rental should fail due to all conditions failing", result);
    }
}