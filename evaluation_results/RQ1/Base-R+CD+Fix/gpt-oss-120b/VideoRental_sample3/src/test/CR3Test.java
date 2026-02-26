import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class CR3Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // Clear all rentals before each test to ensure isolation
        List<VideoRental> allRentals = VideoRental.getAllRentals();
        if (allRentals instanceof ArrayList) {
            ((ArrayList<VideoRental>) allRentals).clear();
        }
    }
    
    @Test
    public void testCase1_SuccessfulRental() throws Exception {
        // Setup: Create Customer C001 with 5 active rentals
        Customer customer = new Customer("C001");
        List<VideoRental> activeRentals = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            VideoRental rental = new VideoRental();
            rental.setRentalDate(dateFormat.parse("2025-01-0" + i));
            rental.setDueDate(dateFormat.parse("2025-01-0" + (i + 7)));
            rental.setReturnDate(null);
            rental.setTape(new Tape("T00" + (i + 10), "Video " + (i + 10))); // Different tape IDs
            activeRentals.add(rental);
            VideoRental.registerRental(rental);
        }
        customer.setRentals(activeRentals);
        
        // Setup: Create available Tape T001 with no active rentals
        Tape tape = new Tape("T001", "Test Video 1");
        
        // Input: C001 rents tape "T001" with current_date="2025-01-01"
        Date currentDate = dateFormat.parse("2025-01-01");
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Expected Output: True
        assertTrue("Rental should succeed when customer has <20 rentals, no overdue fees, and tape is available", result);
    }
    
    @Test
    public void testCase2_CustomerHas20RentalsMaxLimit() throws Exception {
        // Setup: Create Customer C002 with 20 active rentals
        Customer customer = new Customer("C002");
        List<VideoRental> activeRentals = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = new VideoRental();
            rental.setRentalDate(dateFormat.parse("2025-01-01"));
            rental.setDueDate(dateFormat.parse("2025-01-08"));
            rental.setReturnDate(null);
            rental.setTape(new Tape("T00" + (i + 20), "Video " + (i + 20))); // Different tape IDs
            activeRentals.add(rental);
            VideoRental.registerRental(rental);
        }
        customer.setRentals(activeRentals);
        
        // Setup: Create available Tape T002
        Tape tape = new Tape("T002", "Test Video 2");
        
        // Input: C002 rents tape "T002" with current_date="2025-01-01"
        Date currentDate = dateFormat.parse("2025-01-01");
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Expected Output: False
        assertFalse("Rental should fail when customer has exactly 20 active rentals", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() throws Exception {
        // Setup: Create Customer C003 with 1 active rental that is 3 days overdue
        Customer customer = new Customer("C003");
        List<VideoRental> rentals = new ArrayList<>();
        
        VideoRental overdueRental = new VideoRental();
        overdueRental.setRentalDate(dateFormat.parse("2024-12-20"));
        overdueRental.setDueDate(dateFormat.parse("2025-01-02")); // Due 3 days ago from current date
        overdueRental.setReturnDate(null);
        overdueRental.setTape(new Tape("T030", "Overdue Video"));
        rentals.add(overdueRental);
        VideoRental.registerRental(overdueRental);
        
        customer.setRentals(rentals);
        
        // Setup: Create available Tape T003
        Tape tape = new Tape("T003", "Test Video 3");
        
        // Input: C003 rents tape "T003" with current_date="2025-01-05"
        Date currentDate = dateFormat.parse("2025-01-05");
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Expected Output: False
        assertFalse("Rental should fail when customer has overdue fees", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() throws Exception {
        // Setup: Create Customer C004 with 0 rentals
        Customer customer = new Customer("C004");
        customer.setRentals(new ArrayList<>());
        
        // Setup: Create Tape T004 with active rental (rented by another customer C010)
        Tape tape = new Tape("T004", "Test Video 4");
        
        // Create rental for another customer that makes T004 unavailable
        VideoRental otherRental = new VideoRental();
        otherRental.setRentalDate(dateFormat.parse("2024-12-28"));
        otherRental.setDueDate(dateFormat.parse("2025-01-05"));
        otherRental.setReturnDate(null);
        otherRental.setTape(tape);
        VideoRental.registerRental(otherRental);
        
        // Input: C004 rents tape "T004" with current_date="2025-01-01"
        Date currentDate = dateFormat.parse("2025-01-01");
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Expected Output: False
        assertFalse("Rental should fail when tape is unavailable", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() throws Exception {
        // Setup: Create Customer C005 with 20 active rentals and one overdue rental
        Customer customer = new Customer("C005");
        List<VideoRental> rentals = new ArrayList<>();
        
        // Add 20 active rentals
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = new VideoRental();
            rental.setRentalDate(dateFormat.parse("2025-01-01"));
            rental.setDueDate(dateFormat.parse("2025-01-08"));
            rental.setReturnDate(null);
            rental.setTape(new Tape("T00" + (i + 40), "Video " + (i + 40))); // Different tape IDs
            rentals.add(rental);
            VideoRental.registerRental(rental);
        }
        
        // Add one overdue rental
        VideoRental overdueRental = new VideoRental();
        overdueRental.setRentalDate(dateFormat.parse("2024-12-20"));
        overdueRental.setDueDate(dateFormat.parse("2024-12-31")); // Overdue by 5 days
        overdueRental.setReturnDate(null);
        overdueRental.setTape(new Tape("T050", "Overdue Video"));
        rentals.add(overdueRental);
        VideoRental.registerRental(overdueRental);
        
        customer.setRentals(rentals);
        
        // Setup: Create Tape T005 with active rental (rented by another customer C011)
        Tape tape = new Tape("T005", "Test Video 5");
        
        VideoRental otherRental = new VideoRental();
        otherRental.setRentalDate(dateFormat.parse("2024-12-28"));
        otherRental.setDueDate(dateFormat.parse("2025-01-05"));
        otherRental.setReturnDate(null);
        otherRental.setTape(tape);
        VideoRental.registerRental(otherRental);
        
        // Input: C005 rents tape "T005" with current_date="2025-01-01"
        Date currentDate = dateFormat.parse("2025-01-01");
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Expected Output: False
        assertFalse("Rental should fail when all conditions (20+ rentals, overdue fees, tape unavailable) are violated", result);
    }
}