import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class CR3Test {
    
    private Customer customer;
    private Tape tape;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SuccessfulRental() throws Exception {
        // Setup
        customer = new Customer();
        customer.setId("C001");
        
        // Create 5 active rentals for customer C001
        for (int i = 1; i <= 5; i++) {
            Tape tempTape = new Tape();
            tempTape.setId("T00" + (i + 100)); // Different tape IDs
            Date rentalDate = dateFormat.parse("2025-01-0" + i);
            VideoRental rental = new VideoRental(tempTape, addDays(rentalDate, 7));
            customer.getRentals().add(rental);
        }
        
        // Create available tape T001
        tape = new Tape();
        tape.setId("T001");
        
        Date currentDate = dateFormat.parse("2025-01-01");
        
        // Execute
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify
        assertTrue("Rental should be successful when customer has <20 rentals and no past due amount", result);
        assertEquals("Customer should have 6 rentals after successful rental", 6, customer.getRentals().size());
    }
    
    @Test
    public void testCase2_CustomerHas20RentalsMaxLimit() throws Exception {
        // Setup
        customer = new Customer();
        customer.setId("C002");
        
        // Create 20 active rentals for customer C002
        for (int i = 1; i <= 20; i++) {
            Tape tempTape = new Tape();
            tempTape.setId("T00" + (i + 200)); // Different tape IDs
            Date rentalDate = dateFormat.parse("2025-01-01");
            VideoRental rental = new VideoRental(tempTape, addDays(rentalDate, 7));
            customer.getRentals().add(rental);
        }
        
        // Create available tape T002
        tape = new Tape();
        tape.setId("T002");
        
        Date currentDate = dateFormat.parse("2025-01-01");
        
        // Execute
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify
        assertFalse("Rental should fail when customer has 20 rentals (max limit)", result);
        assertEquals("Customer should still have 20 rentals after failed rental", 20, customer.getRentals().size());
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() throws Exception {
        // Setup
        customer = new Customer();
        customer.setId("C003");
        
        // Create 1 active overdue rental for customer C003 (due date 3 days ago)
        Tape overdueTape = new Tape();
        overdueTape.setId("T099");
        Date dueDate = dateFormat.parse("2025-01-02"); // Due date before current date
        VideoRental overdueRental = new VideoRental(overdueTape, dueDate);
        customer.getRentals().add(overdueRental);
        
        // Create available tape T003
        tape = new Tape();
        tape.setId("T003");
        
        Date currentDate = dateFormat.parse("2025-01-05"); // 3 days overdue
        
        // Execute
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify
        assertFalse("Rental should fail when customer has overdue fees", result);
        assertEquals("Customer should still have 1 rental after failed rental", 1, customer.getRentals().size());
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() throws Exception {
        // Setup
        customer = new Customer();
        customer.setId("C004");
        // Customer C004 has 0 rentals initially
        
        // Create tape T004 that is already rented by another customer C010
        tape = new Tape();
        tape.setId("T004");
        // Tape is unavailable because it's rented by another customer (simulated by isAvailable returning false)
        
        // Create a mock tape that returns false for isAvailable
        Tape unavailableTape = new Tape() {
            @Override
            public boolean isAvailable(Date currentDate) {
                return false; // Tape is unavailable
            }
        };
        unavailableTape.setId("T004");
        
        Date currentDate = dateFormat.parse("2025-01-01");
        
        // Execute
        boolean result = customer.addVedioTapeRental(unavailableTape, currentDate);
        
        // Verify
        assertFalse("Rental should fail when tape is unavailable", result);
        assertEquals("Customer should have 0 rentals after failed rental", 0, customer.getRentals().size());
    }
    
    @Test
    public void testCase5_AllConditionsFail() throws Exception {
        // Setup
        customer = new Customer();
        customer.setId("C005");
        
        // Create 20 active rentals for customer C005
        for (int i = 1; i <= 20; i++) {
            Tape tempTape = new Tape();
            tempTape.setId("T00" + (i + 300)); // Different tape IDs
            Date rentalDate = dateFormat.parse("2025-01-01");
            VideoRental rental = new VideoRental(tempTape, addDays(rentalDate, 7));
            customer.getRentals().add(rental);
        }
        
        // Add one overdue rental (due date before current date)
        Tape overdueTape = new Tape();
        overdueTape.setId("T399");
        Date overdueDueDate = dateFormat.parse("2024-12-31"); // Overdue
        VideoRental overdueRental = new VideoRental(overdueTape, overdueDueDate);
        customer.getRentals().add(overdueRental);
        
        // Create tape T005 that is unavailable (rented by another customer)
        Tape unavailableTape = new Tape() {
            @Override
            public boolean isAvailable(Date currentDate) {
                return false; // Tape is unavailable
            }
        };
        unavailableTape.setId("T005");
        
        Date currentDate = dateFormat.parse("2025-01-01");
        
        // Execute
        boolean result = customer.addVedioTapeRental(unavailableTape, currentDate);
        
        // Verify
        assertFalse("Rental should fail when all conditions (20 rentals, overdue fees, tape unavailable) fail", result);
        assertEquals("Customer should still have 21 rentals after failed rental", 21, customer.getRentals().size());
    }
    
    // Helper method to add days to a Date
    private Date addDays(Date date, int days) {
        return new Date(date.getTime() + (days * 24L * 60 * 60 * 1000));
    }
}