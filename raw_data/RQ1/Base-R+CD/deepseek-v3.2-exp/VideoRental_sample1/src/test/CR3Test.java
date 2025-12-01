import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR3Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() throws Exception {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_successfulRental() throws Exception {
        // Setup
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Create 5 active rentals for customer
        List<VideoRental> rentals = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            VideoRental rental = new VideoRental();
            Tape tape = new Tape();
            tape.setId("T00" + (i + 100)); // Different tape IDs
            rental.setTape(tape);
            rental.setDueDate(dateFormat.parse("2025-01-0" + (i + 7) + " 00:00:00"));
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        // Create available tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Execute test
        boolean result = customer.addVideoTapeRental(tape, currentDate);
        
        // Verify result
        assertTrue("Rental should be successful when customer has <20 rentals, no past due amount, and tape is available", result);
    }
    
    @Test
    public void testCase2_customerHas20Rentals() throws Exception {
        // Setup
        Customer customer = new Customer();
        customer.setId("C002");
        
        // Create 20 active rentals for customer
        List<VideoRental> rentals = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = new VideoRental();
            Tape tape = new Tape();
            tape.setId("T00" + (i + 200)); // Different tape IDs
            rental.setTape(tape);
            rental.setDueDate(dateFormat.parse("2025-01-0" + (i + 7) + " 00:00:00"));
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        // Create available tape T002
        Tape tape = new Tape();
        tape.setId("T002");
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Execute test
        boolean result = customer.addVideoTapeRental(tape, currentDate);
        
        // Verify result
        assertFalse("Rental should fail when customer has 20 rentals (max limit)", result);
    }
    
    @Test
    public void testCase3_customerHasOverdueFees() throws Exception {
        // Setup
        Customer customer = new Customer();
        customer.setId("C003");
        
        // Create 1 active rental that is overdue
        List<VideoRental> rentals = new ArrayList<>();
        VideoRental overdueRental = new VideoRental();
        Tape overdueTape = new Tape();
        overdueTape.setId("T099");
        overdueRental.setTape(overdueTape);
        overdueRental.setDueDate(dateFormat.parse("2025-01-02 00:00:00")); // Due date is before current date
        rentals.add(overdueRental);
        customer.setRentals(rentals);
        
        // Create available tape T003
        Tape tape = new Tape();
        tape.setId("T003");
        
        Date currentDate = dateFormat.parse("2025-01-05 00:00:00"); // 3 days overdue
        
        // Execute test
        boolean result = customer.addVideoTapeRental(tape, currentDate);
        
        // Verify result
        assertFalse("Rental should fail when customer has overdue fees", result);
    }
    
    @Test
    public void testCase4_tapeIsUnavailable() throws Exception {
        // Setup
        Customer customer = new Customer();
        customer.setId("C004");
        
        // Create customer with 0 rentals
        customer.setRentals(new ArrayList<>());
        
        // Create tape T004 with active rental from another customer
        Tape tape = new Tape();
        tape.setId("T004");
        
        // Add active rental to tape (making it unavailable)
        VideoRental activeRental = new VideoRental();
        activeRental.setTape(tape);
        activeRental.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        List<VideoRental> tapeRentals = new ArrayList<>();
        tapeRentals.add(activeRental);
        tape.setRentals(tapeRentals);
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Execute test
        boolean result = customer.addVideoTapeRental(tape, currentDate);
        
        // Verify result
        assertFalse("Rental should fail when tape is unavailable", result);
    }
    
    @Test
    public void testCase5_allConditionsFail() throws Exception {
        // Setup
        Customer customer = new Customer();
        customer.setId("C005");
        
        // Create 20 active rentals for customer
        List<VideoRental> rentals = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = new VideoRental();
            Tape tape = new Tape();
            tape.setId("T00" + (i + 300)); // Different tape IDs
            rental.setTape(tape);
            rental.setDueDate(dateFormat.parse("2025-01-0" + (i + 7) + " 00:00:00"));
            rentals.add(rental);
        }
        
        // Add one overdue rental
        VideoRental overdueRental = new VideoRental();
        Tape overdueTape = new Tape();
        overdueTape.setId("T099");
        overdueRental.setTape(overdueTape);
        overdueRental.setDueDate(dateFormat.parse("2024-12-31 00:00:00")); // Overdue rental
        rentals.add(overdueRental);
        customer.setRentals(rentals);
        
        // Create tape T005 with active rental from another customer
        Tape tape = new Tape();
        tape.setId("T005");
        
        // Add active rental to tape (making it unavailable)
        VideoRental activeRental = new VideoRental();
        activeRental.setTape(tape);
        activeRental.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        List<VideoRental> tapeRentals = new ArrayList<>();
        tapeRentals.add(activeRental);
        tape.setRentals(tapeRentals);
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Execute test
        boolean result = customer.addVideoTapeRental(tape, currentDate);
        
        // Verify result
        assertFalse("Rental should fail when all conditions (20 rentals, overdue amount, and tape unavailable) are violated", result);
    }
}