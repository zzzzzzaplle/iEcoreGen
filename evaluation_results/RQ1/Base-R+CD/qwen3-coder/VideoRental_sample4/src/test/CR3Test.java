import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR3Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_successfulRental() throws ParseException {
        // Create Customer C001 with 5 active rentals
        Customer customer = new Customer();
        customer.setId("C001");
        List<VideoRental> rentals = new ArrayList<>();
        
        Date currentDate = dateFormat.parse("2025-01-01");
        
        // Add 5 active rentals
        for (int i = 1; i <= 5; i++) {
            VideoRental rental = new VideoRental();
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentDate);
            cal.add(Calendar.DAY_OF_MONTH, i);
            rental.setDueDate(cal.getTime());
            rental.setReturnDate(null);
            rental.setOwnedPastDueAmount(0.0);
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        // Create available Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        tape.setVideoInformation("Test Video");
        
        // Attempt to add rental
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Expected Output: True
        assertTrue(result);
    }
    
    @Test
    public void testCase2_customerHas20Rentals() throws ParseException {
        // Create Customer C002 with 20 active rentals
        Customer customer = new Customer();
        customer.setId("C002");
        List<VideoRental> rentals = new ArrayList<>();
        
        Date currentDate = dateFormat.parse("2025-01-01");
        
        // Add 20 active rentals
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = new VideoRental();
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentDate);
            cal.add(Calendar.DAY_OF_MONTH, i);
            rental.setDueDate(cal.getTime());
            rental.setReturnDate(null);
            rental.setOwnedPastDueAmount(0.0);
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        // Create available Tape T002
        Tape tape = new Tape();
        tape.setId("T002");
        tape.setVideoInformation("Test Video");
        
        // Attempt to add rental
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Expected Output: False
        assertFalse(result);
    }
    
    @Test
    public void testCase3_customerHasOverdueFees() throws ParseException {
        // Create Customer C003 with 1 active rental that is overdue
        Customer customer = new Customer();
        customer.setId("C003");
        List<VideoRental> rentals = new ArrayList<>();
        
        Date currentDate = dateFormat.parse("2025-01-05");
        Date dueDate = dateFormat.parse("2025-01-05");
        
        // Add 1 overdue rental (due today but not returned)
        VideoRental rental = new VideoRental();
        rental.setDueDate(dueDate);
        rental.setReturnDate(null);
        rental.setOwnedPastDueAmount(0.0);
        rentals.add(rental);
        
        customer.setRentals(rentals);
        
        // Create available Tape T003
        Tape tape = new Tape();
        tape.setId("T003");
        tape.setVideoInformation("Test Video");
        
        // Attempt to add rental
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Expected Output: False
        assertFalse(result);
    }
    
    @Test
    public void testCase4_tapeIsUnavailable() throws ParseException {
        // Create Customer C004 with 0 rentals
        Customer customer = new Customer();
        customer.setId("C004");
        customer.setRentals(new ArrayList<>());
        
        Date currentDate = dateFormat.parse("2025-01-01");
        
        // Create Tape T004 that is unavailable (simulate unavailability)
        Tape tape = new Tape();
        tape.setId("T004");
        tape.setVideoInformation("Test Video");
        
        // Override isAvailable to return false for this test
        Tape unavailableTape = new Tape() {
            @Override
            public boolean isAvailable(Date currentDate) {
                return false;
            }
        };
        unavailableTape.setId("T004");
        unavailableTape.setVideoInformation("Test Video");
        
        // Attempt to add rental
        boolean result = customer.addVedioTapeRental(unavailableTape, currentDate);
        
        // Expected Output: False
        assertFalse(result);
    }
    
    @Test
    public void testCase5_allConditionsFail() throws ParseException {
        // Create Customer C005 with 20 active rentals and one overdue rental
        Customer customer = new Customer();
        customer.setId("C005");
        List<VideoRental> rentals = new ArrayList<>();
        
        Date currentDate = dateFormat.parse("2025-01-01");
        Date overdueDate = dateFormat.parse("2024-12-31");
        
        // Add 20 active rentals
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = new VideoRental();
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentDate);
            cal.add(Calendar.DAY_OF_MONTH, i);
            rental.setDueDate(cal.getTime());
            rental.setReturnDate(null);
            rental.setOwnedPastDueAmount(0.0);
            rentals.add(rental);
        }
        
        // Add one overdue rental
        VideoRental overdueRental = new VideoRental();
        overdueRental.setDueDate(overdueDate);
        overdueRental.setReturnDate(null);
        overdueRental.setOwnedPastDueAmount(5.00);
        rentals.add(overdueRental);
        
        customer.setRentals(rentals);
        
        // Create Tape T005 that is unavailable (simulate unavailability)
        Tape unavailableTape = new Tape() {
            @Override
            public boolean isAvailable(Date currentDate) {
                return false;
            }
        };
        unavailableTape.setId("T005");
        unavailableTape.setVideoInformation("Test Video");
        
        // Attempt to add rental
        boolean result = customer.addVedioTapeRental(unavailableTape, currentDate);
        
        // Expected Output: False
        assertFalse(result);
    }
}