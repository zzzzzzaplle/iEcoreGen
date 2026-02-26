import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR3Test {
    private SimpleDateFormat dateFormat;
    private Customer customer;
    private Tape tape;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SuccessfulRental() throws ParseException {
        // Setup: Create Customer C001 with 5 active rentals
        customer = new Customer();
        customer.setId("C001");
        
        // Create 5 active rentals with rental dates from 2025-01-01 to 2025-01-05
        for (int i = 1; i <= 5; i++) {
            Tape tempTape = new Tape();
            tempTape.setId("TEMP" + i);
            Date rentalDate = dateFormat.parse("2025-01-0" + i);
            VideoRental rental = new VideoRental(tempTape, rentalDate);
            customer.getRentals().add(rental);
        }
        
        // Create available Tape T001
        tape = new Tape();
        tape.setId("T001");
        tape.setVideoInformation("Available Tape");
        
        // Input: C001 rents tape "T001" with current_date="2025-01-01"
        Date currentDate = dateFormat.parse("2025-01-01");
        boolean result = customer.addVideoTapeRental(tape, currentDate);
        
        // Expected Output: True
        assertTrue("Rental should be successful when customer has <20 rentals, no past-due amount, and tape is available", result);
    }
    
    @Test
    public void testCase2_CustomerHas20RentalsMaxLimit() throws ParseException {
        // Setup: Create Customer C002 with 20 active rentals
        customer = new Customer();
        customer.setId("C002");
        
        // Create 20 active rentals
        for (int i = 1; i <= 20; i++) {
            Tape tempTape = new Tape();
            tempTape.setId("TEMP" + i);
            Date rentalDate = dateFormat.parse("2025-01-01");
            VideoRental rental = new VideoRental(tempTape, rentalDate);
            customer.getRentals().add(rental);
        }
        
        // Create available Tape T002
        tape = new Tape();
        tape.setId("T002");
        tape.setVideoInformation("Available Tape");
        
        // Input: C002 rents tape "T002" with current_date="2025-01-01"
        Date currentDate = dateFormat.parse("2025-01-01");
        boolean result = customer.addVideoTapeRental(tape, currentDate);
        
        // Expected Output: False
        assertFalse("Rental should fail when customer has 20 rentals (max limit)", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() throws ParseException {
        // Setup: Create Customer C003 with 1 active rental, 3 days overdue
        customer = new Customer();
        customer.setId("C003");
        
        // Create overdue rental (due date is 2025-01-02, current date is 2025-01-05 = 3 days overdue)
        Tape overdueTape = new Tape();
        overdueTape.setId("OVERDUE_TAPE");
        Date rentalDate = dateFormat.parse("2024-12-26"); // Due date will be 2025-01-02
        VideoRental overdueRental = new VideoRental(overdueTape, rentalDate);
        customer.getRentals().add(overdueRental);
        
        // Create available Tape T003
        tape = new Tape();
        tape.setId("T003");
        tape.setVideoInformation("Available Tape");
        
        // Input: C003 rents tape "T003" with current_date="2025-01-05"
        Date currentDate = dateFormat.parse("2025-01-05");
        boolean result = customer.addVideoTapeRental(tape, currentDate);
        
        // Expected Output: False
        assertFalse("Rental should fail when customer has overdue fees", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() throws ParseException {
        // Setup: Create Customer C004 with 0 rentals
        customer = new Customer();
        customer.setId("C004");
        
        // Create Tape T004 with active rental (simulate unavailable by overriding isAvailable method)
        tape = new Tape() {
            @Override
            public boolean isAvailable(Date currentDate) {
                return false; // Tape is unavailable
            }
        };
        tape.setId("T004");
        tape.setVideoInformation("Unavailable Tape");
        
        // Input: C004 rents tape "T004" with current_date="2025-01-01"
        Date currentDate = dateFormat.parse("2025-01-01");
        boolean result = customer.addVideoTapeRental(tape, currentDate);
        
        // Expected Output: False
        assertFalse("Rental should fail when tape is unavailable", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() throws ParseException {
        // Setup: Create Customer C005 with 20 active rentals and one overdue rental
        customer = new Customer();
        customer.setId("C005");
        
        // Create 19 active rentals
        for (int i = 1; i <= 19; i++) {
            Tape tempTape = new Tape();
            tempTape.setId("TEMP" + i);
            Date rentalDate = dateFormat.parse("2025-01-01");
            VideoRental rental = new VideoRental(tempTape, rentalDate);
            customer.getRentals().add(rental);
        }
        
        // Create one overdue rental (due date 2024-12-31, current date 2025-01-01 = 1 day overdue)
        Tape overdueTape = new Tape();
        overdueTape.setId("OVERDUE_TAPE");
        Date overdueRentalDate = dateFormat.parse("2024-12-24"); // Due date will be 2024-12-31
        VideoRental overdueRental = new VideoRental(overdueTape, overdueRentalDate);
        customer.getRentals().add(overdueRental);
        
        // Create Tape T005 with active rental (simulate unavailable)
        tape = new Tape() {
            @Override
            public boolean isAvailable(Date currentDate) {
                return false; // Tape is unavailable
            }
        };
        tape.setId("T005");
        tape.setVideoInformation("Unavailable Tape");
        
        // Input: C005 rents tape "T005" with current_date="2025-01-01"
        Date currentDate = dateFormat.parse("2025-01-01");
        boolean result = customer.addVideoTapeRental(tape, currentDate);
        
        // Expected Output: False
        assertFalse("Rental should fail when all conditions (20 rentals, overdue fees, tape unavailable) are violated", result);
    }
}