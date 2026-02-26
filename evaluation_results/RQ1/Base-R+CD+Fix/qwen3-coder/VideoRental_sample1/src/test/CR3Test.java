import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR3Test {
    
    private SimpleDateFormat sdf;
    
    @Before
    public void setUp() {
        sdf = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SuccessfulRental() throws Exception {
        // Setup: Create Customer C001 with 5 active rentals
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Create 5 active rentals with rental dates from 2025-01-01 to 2025-01-05
        for (int i = 0; i < 5; i++) {
            VideoRental rental = new VideoRental();
            Tape tape = new Tape();
            tape.setId("T" + (100 + i));
            rental.setTape(tape);
            
            Date rentalDate = sdf.parse("2025-01-0" + (i + 1));
            Date dueDate = new Date(rentalDate.getTime() + 7 * 24 * 60 * 60 * 1000L);
            rental.setDueDate(dueDate);
            rental.setReturnDate(null);
            customer.getRentals().add(rental);
        }
        
        // Create available Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        
        // Input: C001 rents tape "T001" with current_date="2025-01-01"
        Date currentDate = sdf.parse("2025-01-01");
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Expected Output: True
        assertTrue("Rental should be successful when customer has <20 rentals, no past-due, and tape is available", result);
    }
    
    @Test
    public void testCase2_CustomerHas20Rentals() throws Exception {
        // Setup: Create Customer C002 with 20 active rentals
        Customer customer = new Customer();
        customer.setId("C002");
        
        // Create 20 active rentals
        for (int i = 0; i < 20; i++) {
            VideoRental rental = new VideoRental();
            Tape rentalTape = new Tape();
            rentalTape.setId("T" + (200 + i));
            rental.setTape(rentalTape);
            
            Date rentalDate = sdf.parse("2025-01-01");
            Date dueDate = new Date(rentalDate.getTime() + 7 * 24 * 60 * 60 * 1000L);
            rental.setDueDate(dueDate);
            rental.setReturnDate(null);
            customer.getRentals().add(rental);
        }
        
        // Create available Tape T002
        Tape tape = new Tape();
        tape.setId("T002");
        
        // Input: C002 rents tape "T002" with current_date="2025-01-01"
        Date currentDate = sdf.parse("2025-01-01");
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Expected Output: False
        assertFalse("Rental should fail when customer has 20 rentals (max limit)", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() throws Exception {
        // Setup: Create Customer C003 with 1 active rental that is 3 days overdue
        Customer customer = new Customer();
        customer.setId("C003");
        
        VideoRental rental = new VideoRental();
        Tape rentalTape = new Tape();
        rentalTape.setId("T300");
        rental.setTape(rentalTape);
        
        // Due date is 2025-01-02, current date is 2025-01-05 (3 days overdue)
        Date dueDate = sdf.parse("2025-01-02");
        rental.setDueDate(dueDate);
        rental.setReturnDate(null);
        customer.getRentals().add(rental);
        
        // Create available Tape T003
        Tape tape = new Tape();
        tape.setId("T003");
        
        // Input: C003 rents tape "T003" with current_date="2025-01-05"
        Date currentDate = sdf.parse("2025-01-05");
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Expected Output: False
        assertFalse("Rental should fail when customer has overdue fees", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() throws Exception {
        // Setup: Create Customer C004 with 0 rentals
        Customer customer = new Customer();
        customer.setId("C004");
        
        // Create Tape T004 with active rental by another customer
        Tape tape = new Tape();
        tape.setId("T004");
        // Note: The actual availability check is simplified in the Tape class
        // In a real implementation, this would check against system-wide rentals
        
        // Input: C004 rents tape "T004" with current_date="2025-01-01"
        Date currentDate = sdf.parse("2025-01-01");
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Expected Output: False
        // Note: Due to the simplified Tape.isAvailable() implementation that always returns true,
        // this test might pass when it shouldn't. This is a limitation of the provided source code.
        assertFalse("Rental should fail when tape is unavailable", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() throws Exception {
        // Setup: Create Customer C005 with 20 active rentals and one overdue rental
        Customer customer = new Customer();
        customer.setId("C005");
        
        // Create 20 active rentals
        for (int i = 0; i < 20; i++) {
            VideoRental rental = new VideoRental();
            Tape rentalTape = new Tape();
            rentalTape.setId("T" + (500 + i));
            rental.setTape(rentalTape);
            
            Date rentalDate = sdf.parse("2025-01-01");
            Date dueDate = new Date(rentalDate.getTime() + 7 * 24 * 60 * 60 * 1000L);
            rental.setDueDate(dueDate);
            rental.setReturnDate(null);
            customer.getRentals().add(rental);
        }
        
        // Add one overdue rental (due_date="2024-12-31")
        VideoRental overdueRental = new VideoRental();
        Tape overdueTape = new Tape();
        overdueTape.setId("T599");
        overdueRental.setTape(overdueTape);
        overdueRental.setDueDate(sdf.parse("2024-12-31"));
        overdueRental.setReturnDate(null);
        customer.getRentals().add(overdueRental);
        
        // Create Tape T005 with active rental by another customer
        Tape tape = new Tape();
        tape.setId("T005");
        
        // Input: C005 rents tape "T005" with current_date="2025-01-01"
        Date currentDate = sdf.parse("2025-01-01");
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Expected Output: False
        assertFalse("Rental should fail when all conditions (20+ rentals, overdue fees, tape unavailable) are violated", result);
    }
}