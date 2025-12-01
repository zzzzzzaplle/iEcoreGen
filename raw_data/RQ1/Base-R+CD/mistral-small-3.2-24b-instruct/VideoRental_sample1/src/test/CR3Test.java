import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CR3Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SuccessfulRental() throws Exception {
        // Setup: Create Customer C001 with 5 active rentals
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Create 5 active rentals with rental dates from 2025-01-01 to 2025-01-05
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        for (int i = 0; i < 5; i++) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentDate);
            cal.add(Calendar.DAY_OF_MONTH, i);
            Date rentalDate = cal.getTime();
            
            VideoRental rental = new VideoRental();
            Tape tape = new Tape();
            tape.setId("T00" + (i + 10)); // Different tape IDs
            rental.setTape(tape);
            
            Calendar dueCal = Calendar.getInstance();
            dueCal.setTime(rentalDate);
            dueCal.add(Calendar.DAY_OF_MONTH, 7);
            rental.setDueDate(dueCal.getTime());
            // returnDate remains null (unreturned)
            
            customer.getRentals().add(rental);
        }
        
        // Create available Tape T001 with no active rentals
        Tape tapeT001 = new Tape();
        tapeT001.setId("T001");
        
        // Test: C001 rents tape "T001" with current_date="2025-01-01"
        boolean result = customer.addVideoTapeRental(tapeT001, currentDate);
        
        // Verify: Expected Output: True
        assertTrue("Rental should be successful when customer has <20 rentals, no past-due amount, and tape is available", result);
    }
    
    @Test
    public void testCase2_CustomerHas20RentalsMaxLimit() throws Exception {
        // Setup: Create Customer C002 with 20 active rentals
        Customer customer = new Customer();
        customer.setId("C002");
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Create 20 active rentals
        for (int i = 0; i < 20; i++) {
            VideoRental rental = new VideoRental();
            Tape tape = new Tape();
            tape.setId("T00" + (i + 20)); // Different tape IDs
            rental.setTape(tape);
            
            Calendar dueCal = Calendar.getInstance();
            dueCal.setTime(currentDate);
            dueCal.add(Calendar.DAY_OF_MONTH, 7);
            rental.setDueDate(dueCal.getTime());
            // returnDate remains null (unreturned)
            
            customer.getRentals().add(rental);
        }
        
        // Create available Tape T002
        Tape tapeT002 = new Tape();
        tapeT002.setId("T002");
        
        // Test: C002 rents tape "T002" with current_date="2025-01-01"
        boolean result = customer.addVideoTapeRental(tapeT002, currentDate);
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail when customer has 20 rentals (max limit)", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() throws Exception {
        // Setup: Create Customer C003 with 1 active rental, 3 days overdue
        Customer customer = new Customer();
        customer.setId("C003");
        
        Date currentDate = dateFormat.parse("2025-01-05 00:00:00");
        
        // Create overdue rental (due date was 2025-01-02, current date is 2025-01-05)
        VideoRental overdueRental = new VideoRental();
        Tape tapeOverdue = new Tape();
        tapeOverdue.setId("T009");
        overdueRental.setTape(tapeOverdue);
        
        Calendar dueCal = Calendar.getInstance();
        dueCal.setTime(dateFormat.parse("2025-01-02 00:00:00"));
        overdueRental.setDueDate(dueCal.getTime());
        // returnDate remains null (unreturned) - 3 days overdue
        
        customer.getRentals().add(overdueRental);
        
        // Create available Tape T003
        Tape tapeT003 = new Tape();
        tapeT003.setId("T003");
        
        // Test: C003 rents tape "T003" with current_date="2025-01-05"
        boolean result = customer.addVideoTapeRental(tapeT003, currentDate);
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail when customer has overdue fees", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() throws Exception {
        // Setup: Create Customer C004 with 0 rentals
        Customer customer = new Customer();
        customer.setId("C004");
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Create Tape T004 with active rental (rented by another customer C010)
        // Since Tape.isAvailable() implementation assumes empty rentals list,
        // we need to simulate unavailability by creating a mock scenario
        // For this test, we'll assume the tape is unavailable by design
        
        Tape tapeT004 = new Tape() {
            @Override
            public boolean isAvailable(Date currentDate) {
                // Override to simulate unavailable tape
                return false;
            }
        };
        tapeT004.setId("T004");
        
        // Test: C004 rents tape "T004" with current_date="2025-01-01"
        boolean result = customer.addVideoTapeRental(tapeT004, currentDate);
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail when tape is unavailable", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() throws Exception {
        // Setup: Create Customer C005 with 20 active rentals and one overdue rental
        Customer customer = new Customer();
        customer.setId("C005");
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Create 19 active rentals
        for (int i = 0; i < 19; i++) {
            VideoRental rental = new VideoRental();
            Tape tape = new Tape();
            tape.setId("T00" + (i + 30)); // Different tape IDs
            rental.setTape(tape);
            
            Calendar dueCal = Calendar.getInstance();
            dueCal.setTime(currentDate);
            dueCal.add(Calendar.DAY_OF_MONTH, 7);
            rental.setDueDate(dueCal.getTime());
            // returnDate remains null (unreturned)
            
            customer.getRentals().add(rental);
        }
        
        // Add one overdue rental (due date 2024-12-31, current date 2025-01-01)
        VideoRental overdueRental = new VideoRental();
        Tape tapeOverdue = new Tape();
        tapeOverdue.setId("T009");
        overdueRental.setTape(tapeOverdue);
        
        Calendar dueCal = Calendar.getInstance();
        dueCal.setTime(dateFormat.parse("2024-12-31 00:00:00"));
        overdueRental.setDueDate(dueCal.getTime());
        // returnDate remains null (unreturned) - 1 day overdue
        
        customer.getRentals().add(overdueRental);
        
        // Create Tape T005 with active rental (rented by another customer C011)
        Tape tapeT005 = new Tape() {
            @Override
            public boolean isAvailable(Date currentDate) {
                // Override to simulate unavailable tape
                return false;
            }
        };
        tapeT005.setId("T005");
        
        // Test: C005 rents tape "T005" with current_date="2025-01-01"
        boolean result = customer.addVideoTapeRental(tapeT005, currentDate);
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail when all conditions (20 rentals, overdue fees, tape unavailable) are violated", result);
    }
}