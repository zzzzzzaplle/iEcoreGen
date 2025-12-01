import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

public class CR3Test {
    
    private Customer customer;
    private Tape tape;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SuccessfulRental() throws Exception {
        // Setup
        customer = new Customer();
        customer.setId("C001");
        
        // Create 5 active rentals with due dates 7 days after rental
        List<VideoRental> rentals = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            VideoRental rental = new VideoRental();
            Tape rentalTape = new Tape();
            rentalTape.setId("T00" + (i + 10));
            rental.setTape(rentalTape);
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateFormat.parse("2025-01-0" + i + " 00:00:00"));
            cal.add(Calendar.DAY_OF_MONTH, 7);
            rental.setDueDate(cal.getTime());
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        tape = new Tape();
        tape.setId("T001");
        tape.setVideoInformation("Available Tape");
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Execute
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify
        assertTrue("Rental should be successful", result);
    }
    
    @Test
    public void testCase2_CustomerHas20RentalsMaxLimit() throws Exception {
        // Setup
        customer = new Customer();
        customer.setId("C002");
        
        // Create 20 active rentals
        List<VideoRental> rentals = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = new VideoRental();
            Tape rentalTape = new Tape();
            rentalTape.setId("TR" + i);
            rental.setTape(rentalTape);
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateFormat.parse("2025-01-01 00:00:00"));
            cal.add(Calendar.DAY_OF_MONTH, 7);
            rental.setDueDate(cal.getTime());
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        tape = new Tape();
        tape.setId("T002");
        tape.setVideoInformation("Available Tape");
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Execute
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify
        assertFalse("Rental should fail due to max limit", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() throws Exception {
        // Setup
        customer = new Customer();
        customer.setId("C003");
        
        // Create 1 active rental that is 3 days overdue
        List<VideoRental> rentals = new ArrayList<>();
        VideoRental overdueRental = new VideoRental();
        Tape rentalTape = new Tape();
        rentalTape.setId("TR1");
        overdueRental.setTape(rentalTape);
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateFormat.parse("2024-12-29 00:00:00")); // Due date is 2025-01-05
        cal.add(Calendar.DAY_OF_MONTH, 7);
        overdueRental.setDueDate(cal.getTime()); // Due date 2025-01-05
        rentals.add(overdueRental);
        customer.setRentals(rentals);
        
        tape = new Tape();
        tape.setId("T003");
        tape.setVideoInformation("Available Tape");
        
        Date currentDate = dateFormat.parse("2025-01-05 00:00:00"); // Current date is due date
        
        // Execute
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify
        assertFalse("Rental should fail due to overdue fees", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() throws Exception {
        // Setup
        customer = new Customer();
        customer.setId("C004");
        
        tape = new Tape();
        tape.setId("T004");
        tape.setVideoInformation("Unavailable Tape");
        
        // Mock the tape as unavailable by overriding isAvailable method
        Tape unavailableTape = new Tape() {
            @Override
            public boolean isAvailable(Date currentDate) {
                return false; // Tape is unavailable
            }
        };
        unavailableTape.setId("T004");
        unavailableTape.setVideoInformation("Unavailable Tape");
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Execute
        boolean result = customer.addVedioTapeRental(unavailableTape, currentDate);
        
        // Verify
        assertFalse("Rental should fail due to unavailable tape", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() throws Exception {
        // Setup
        customer = new Customer();
        customer.setId("C005");
        
        // Create 20 active rentals
        List<VideoRental> rentals = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = new VideoRental();
            Tape rentalTape = new Tape();
            rentalTape.setId("TR" + i);
            rental.setTape(rentalTape);
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateFormat.parse("2025-01-01 00:00:00"));
            cal.add(Calendar.DAY_OF_MONTH, 7);
            rental.setDueDate(cal.getTime());
            rentals.add(rental);
        }
        
        // Add one overdue rental
        VideoRental overdueRental = new VideoRental();
        Tape overdueTape = new Tape();
        overdueTape.setId("TRO");
        overdueRental.setTape(overdueTape);
        overdueRental.setDueDate(dateFormat.parse("2024-12-31 00:00:00")); // Overdue
        rentals.add(overdueRental);
        
        customer.setRentals(rentals);
        
        tape = new Tape();
        tape.setId("T005");
        tape.setVideoInformation("Unavailable Tape");
        
        // Mock the tape as unavailable
        Tape unavailableTape = new Tape() {
            @Override
            public boolean isAvailable(Date currentDate) {
                return false; // Tape is unavailable
            }
        };
        unavailableTape.setId("T005");
        unavailableTape.setVideoInformation("Unavailable Tape");
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Execute
        boolean result = customer.addVedioTapeRental(unavailableTape, currentDate);
        
        // Verify
        assertFalse("Rental should fail due to all conditions failing", result);
    }
}