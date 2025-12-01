import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    private Customer customer;
    private Tape tape;
    private LocalDate currentDate;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SuccessfulRental() {
        // Setup: Create Customer C001 with 5 active rentals
        customer = new Customer();
        customer.setId("C001");
        
        // Create 5 active rentals (rental dates: "2025-01-01" to "2025-01-05", all unreturned, due dates 7 days after rental)
        for (int i = 1; i <= 5; i++) {
            VideoRental rental = new VideoRental();
            Tape rentalTape = new Tape();
            rentalTape.setId("T00" + (i + 10)); // Different tape IDs to avoid conflicts
            rental.setTape(rentalTape);
            
            LocalDate rentalDate = LocalDate.parse("2025-01-0" + i, formatter);
            rental.setDueDate(rentalDate.plusDays(7));
            // returnDate remains null (unreturned)
            customer.getRentals().add(rental);
        }
        
        // Create available Tape T001 with no active rentals
        tape = new Tape();
        tape.setId("T001");
        // isAvailable returns true by default (placeholder implementation)
        
        currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Test: C001 rents tape "T001" with current_date="2025-01-01"
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Expected Output: True
        assertTrue("Rental should be successful when customer has <20 rentals, no past-due amount, and tape is available", result);
    }
    
    @Test
    public void testCase2_CustomerHas20RentalsMaxLimit() {
        // Setup: Create Customer C002 with 20 active rentals
        customer = new Customer();
        customer.setId("C002");
        
        // Create 20 active rentals (all unreturned, due dates 7 days after rental)
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = new VideoRental();
            Tape rentalTape = new Tape();
            rentalTape.setId("T00" + (i + 20)); // Different tape IDs to avoid conflicts
            rental.setTape(rentalTape);
            
            LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
            rental.setDueDate(rentalDate.plusDays(7));
            // returnDate remains null (unreturned)
            customer.getRentals().add(rental);
        }
        
        // Create available Tape T002
        tape = new Tape();
        tape.setId("T002");
        // isAvailable returns true by default (placeholder implementation)
        
        currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Test: C002 rents tape "T002" with current_date="2025-01-01"
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail when customer has 20 rentals (max limit)", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup: Create Customer C003 with 1 active rental, due_date="2025-01-05", return_date=null (3 days overdue)
        customer = new Customer();
        customer.setId("C003");
        
        VideoRental overdueRental = new VideoRental();
        Tape rentalTape = new Tape();
        rentalTape.setId("T030");
        overdueRental.setTape(rentalTape);
        
        LocalDate dueDate = LocalDate.parse("2025-01-05", formatter);
        overdueRental.setDueDate(dueDate);
        // returnDate remains null (unreturned)
        customer.getRentals().add(overdueRental);
        
        // Create available Tape T003
        tape = new Tape();
        tape.setId("T003");
        // isAvailable returns true by default (placeholder implementation)
        
        currentDate = LocalDate.parse("2025-01-08", formatter); // 3 days after due date
        
        // Test: C003 rents tape "T003" with current_date="2025-01-05"
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail when customer has overdue fees", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup: Create Customer C004 with 0 rentals
        customer = new Customer();
        customer.setId("C004");
        
        // Create Tape T004 with active rental (rented by another customer C010, return_date=null, due_date="2025-01-05")
        tape = new Tape();
        tape.setId("T004");
        // Override isAvailable method to return false for this test
        Tape unavailableTape = new Tape() {
            @Override
            public boolean isAvailable(LocalDate currentDate) {
                return false; // Tape is unavailable
            }
        };
        unavailableTape.setId("T004");
        
        currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Test: C004 rents tape "T004" with current_date="2025-01-01"
        boolean result = customer.addVedioTapeRental(unavailableTape, currentDate);
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail when tape is unavailable", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup: Create Customer C005 with 20 active rentals and one overdue rental
        customer = new Customer();
        customer.setId("C005");
        
        // Create 20 active rentals
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = new VideoRental();
            Tape rentalTape = new Tape();
            rentalTape.setId("T00" + (i + 40)); // Different tape IDs to avoid conflicts
            rental.setTape(rentalTape);
            
            LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
            rental.setDueDate(rentalDate.plusDays(7));
            // returnDate remains null (unreturned)
            customer.getRentals().add(rental);
        }
        
        // Add one overdue rental (due_date="2024-12-31", return_date=null, overdue_amount $5.00)
        VideoRental overdueRental = new VideoRental();
        Tape overdueTape = new Tape();
        overdueTape.setId("T050");
        overdueRental.setTape(overdueTape);
        overdueRental.setDueDate(LocalDate.parse("2024-12-31", formatter));
        // returnDate remains null (unreturned)
        customer.getRentals().add(overdueRental);
        
        // Create Tape T005 with active rental (rented by another customer C011, return_date=null, due_date="2025-01-05")
        tape = new Tape();
        tape.setId("T005");
        // Override isAvailable method to return false for this test
        Tape unavailableTape = new Tape() {
            @Override
            public boolean isAvailable(LocalDate currentDate) {
                return false; // Tape is unavailable
            }
        };
        unavailableTape.setId("T005");
        
        currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Test: C005 rents tape "T005" with current_date="2025-01-01"
        boolean result = customer.addVedioTapeRental(unavailableTape, currentDate);
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail when all conditions (20 rentals, overdue fees, tape unavailable) are violated", result);
    }
}