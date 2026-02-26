import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Customer customer;
    private VideoTape tape;
    private VideoRental rental;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        customer = new Customer();
        tape = new VideoTape();
        rental = new VideoRental();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SuccessfulRental() {
        // Setup: Create Customer C001 with 5 active rentals
        customer.setAccountNumber("C001");
        List<VideoRental> rentals = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            VideoRental r = new VideoRental();
            r.setRentalDate(LocalDate.parse("2025-01-0" + i, formatter));
            r.setDueDate(LocalDate.parse("2025-01-0" + i, formatter).plusDays(7));
            r.setReturnDate(null);
            rentals.add(r);
        }
        customer.setRentals(rentals);
        
        // Setup: Create available Tape T001 with no active rentals
        tape.setBarcodeId("T001");
        tape.setAvailable(true);
        
        // Input: C001 rents tape "T001" with current_date="2025-01-01"
        boolean result = rental.addVideoTapeRental(customer, tape);
        
        // Expected Output: True
        assertTrue("Rental should be successful when customer has <20 rentals, no overdue, and tape is available", result);
    }
    
    @Test
    public void testCase2_CustomerHas20Rentals() {
        // Setup: Create Customer C002 with 20 active rentals
        customer.setAccountNumber("C002");
        List<VideoRental> rentals = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            VideoRental r = new VideoRental();
            r.setRentalDate(LocalDate.parse("2025-01-01", formatter));
            r.setDueDate(LocalDate.parse("2025-01-01", formatter).plusDays(7));
            r.setReturnDate(null);
            rentals.add(r);
        }
        customer.setRentals(rentals);
        
        // Setup: Create available Tape T002
        tape.setBarcodeId("T002");
        tape.setAvailable(true);
        
        // Input: C002 rents tape "T002" with current_date="2025-01-01"
        boolean result = rental.addVideoTapeRental(customer, tape);
        
        // Expected Output: False
        assertFalse("Rental should fail when customer has 20 active rentals", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup: Create Customer C003 with 1 active rental, due_date="2025-01-05", return_date=null (3 days overdue)
        customer.setAccountNumber("C003");
        List<VideoRental> rentals = new ArrayList<>();
        VideoRental overdueRental = new VideoRental();
        overdueRental.setRentalDate(LocalDate.parse("2024-12-29", formatter)); // Rented 7 days before due date
        overdueRental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        overdueRental.setReturnDate(null); // Not returned, making it overdue on 2025-01-05
        rentals.add(overdueRental);
        customer.setRentals(rentals);
        
        // Setup: Create available Tape T003
        tape.setBarcodeId("T003");
        tape.setAvailable(true);
        
        // Input: C003 rents tape "T003" with current_date="2025-01-05"
        boolean result = rental.addVideoTapeRental(customer, tape);
        
        // Expected Output: False
        assertFalse("Rental should fail when customer has overdue fees", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup: Create Customer C004 with 0 rentals
        customer.setAccountNumber("C004");
        customer.setRentals(new ArrayList<>());
        
        // Setup: Create Tape T004 with active rental (rented by another customer C010)
        tape.setBarcodeId("T004");
        tape.setAvailable(false); // Set as unavailable since it's already rented
        
        // Input: C004 rents tape "T004" with current_date="2025-01-01"
        boolean result = rental.addVideoTapeRental(customer, tape);
        
        // Expected Output: False
        assertFalse("Rental should fail when tape is unavailable", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup: Create Customer C005 with 20 active rentals and one overdue rental
        customer.setAccountNumber("C005");
        List<VideoRental> rentals = new ArrayList<>();
        
        // Add 19 regular rentals
        for (int i = 1; i <= 19; i++) {
            VideoRental r = new VideoRental();
            r.setRentalDate(LocalDate.parse("2025-01-01", formatter));
            r.setDueDate(LocalDate.parse("2025-01-01", formatter).plusDays(7));
            r.setReturnDate(null);
            rentals.add(r);
        }
        
        // Add 1 overdue rental (due_date="2024-12-31", return_date=null)
        VideoRental overdueRental = new VideoRental();
        overdueRental.setRentalDate(LocalDate.parse("2024-12-24", formatter)); // Rented 7 days before due date
        overdueRental.setDueDate(LocalDate.parse("2024-12-31", formatter));
        overdueRental.setReturnDate(null); // Not returned, making it overdue
        rentals.add(overdueRental);
        
        customer.setRentals(rentals);
        
        // Setup: Create Tape T005 with active rental (rented by another customer C011)
        tape.setBarcodeId("T005");
        tape.setAvailable(false); // Set as unavailable since it's already rented
        
        // Input: C005 rents tape "T005" with current_date="2025-01-01"
        boolean result = rental.addVideoTapeRental(customer, tape);
        
        // Expected Output: False
        assertFalse("Rental should fail when all conditions (20 rentals, overdue fees, tape unavailable) are violated", result);
    }
}