import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private RentalStore rentalStore;
    
    @Before
    public void setUp() {
        // Clear the rental store before each test to ensure isolation
        rentalStore = new RentalStore();
        // Clear static rentals list by reflection or reinitialization
        // Since RentalStore.ALL_RENTALS is private static final, we need to reset it
        // For simplicity, we'll create a helper method to clear rentals
        clearRentalStore();
    }
    
    // Helper method to clear the rental store between tests
    private void clearRentalStore() {
        try {
            java.lang.reflect.Field field = RentalStore.class.getDeclaredField("ALL_RENTALS");
            field.setAccessible(true);
            List<VideoRental> rentals = (List<VideoRental>) field.get(null);
            rentals.clear();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clear rental store", e);
        }
    }
    
    @Test
    public void testCase1_TapeIsAvailable() {
        // Test Case 1: "Tape is available"
        // Input: tape_id="T001", current_date="2025-01-01"
        
        // Setup: Create Tape with id="T001", no active rentals for T001
        Tape tape = new Tape();
        tape.setId("T001");
        
        LocalDate currentDate = LocalDate.parse("2025-01-01");
        
        // Execute: Check tape availability
        boolean result = tape.isAvailable(currentDate);
        
        // Verify: Expected Output: True
        assertTrue("Tape T001 should be available when no rentals exist", result);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() {
        // Test Case 2: "Tape is rented out"
        // Input: tape_id="T002", current_date="2025-01-01"
        
        // Setup: Create Tape with id="T002", Create Customer C001
        Tape tape = new Tape();
        tape.setId("T002");
        
        Customer customer = new Customer();
        customer.setId("C001");
        
        // C001 rents T002 with rental date="2024-12-28", due_date="2025-01-05", return_date=null
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.parse("2024-12-28"));
        rental.setDueDate(LocalDate.parse("2025-01-05"));
        rental.setReturnDate(null);
        
        customer.getRentals().add(rental);
        RentalStore.addRental(rental);
        
        LocalDate currentDate = LocalDate.parse("2025-01-01");
        
        // Execute: Check tape availability
        boolean result = tape.isAvailable(currentDate);
        
        // Verify: Expected Output: False
        assertFalse("Tape T002 should not be available when rented out and not returned", result);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Test Case 3: "Tape was rented but returned"
        // Input: tape_id="T003", current_date="2025-01-01"
        
        // Setup: Create Tape with id="T003", Create Customer C002
        Tape tape = new Tape();
        tape.setId("T003");
        
        Customer customer = new Customer();
        customer.setId("C002");
        
        // C002 rents T003 with rental date="2024-12-25", due_date="2024-12-30", return_date="2024-12-31"
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.parse("2024-12-25"));
        rental.setDueDate(LocalDate.parse("2024-12-30"));
        rental.setReturnDate(LocalDate.parse("2024-12-31"));
        
        customer.getRentals().add(rental);
        RentalStore.addRental(rental);
        
        LocalDate currentDate = LocalDate.parse("2025-01-01");
        
        // Execute: Check tape availability
        boolean result = tape.isAvailable(currentDate);
        
        // Verify: Expected Output: True
        assertTrue("Tape T003 should be available when previously rented but returned", result);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Test Case 4: "Tape exists but has overdue rental"
        // Input: tape_id="T004", current_date="2025-01-10"
        
        // Setup: Create Tape with id="T004", Create Customer C003
        Tape tape = new Tape();
        tape.setId("T004");
        
        Customer customer = new Customer();
        customer.setId("C003");
        
        // C003 rents T004 with rental date="2024-12-28", due_date="2025-01-01", return_date=null
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.parse("2024-12-28"));
        rental.setDueDate(LocalDate.parse("2025-01-01"));
        rental.setReturnDate(null);
        
        customer.getRentals().add(rental);
        RentalStore.addRental(rental);
        
        LocalDate currentDate = LocalDate.parse("2025-01-10");
        
        // Execute: Check tape availability
        boolean result = tape.isAvailable(currentDate);
        
        // Verify: Expected Output: False
        assertFalse("Tape T004 should not be available when rented out and overdue", result);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Test Case 5: "Tape was rented but returned by multiple customers"
        // Input: tape_id="T005", current_date="2025-01-10"
        
        // Setup: Create Tape with id="T005", Create Customer C004, C005
        Tape tape = new Tape();
        tape.setId("T005");
        
        Customer customer1 = new Customer();
        customer1.setId("C004");
        
        Customer customer2 = new Customer();
        customer2.setId("C005");
        
        // C004 rents T005 with rental date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01"
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape);
        rental1.setRentalDate(LocalDate.parse("2025-01-01"));
        rental1.setDueDate(LocalDate.parse("2025-01-05"));
        rental1.setReturnDate(LocalDate.parse("2025-01-01"));
        
        customer1.getRentals().add(rental1);
        RentalStore.addRental(rental1);
        
        LocalDate currentDate = LocalDate.parse("2025-01-10");
        
        // First check: The first creation - should be True (tape is available after first rental was returned)
        boolean firstResult = tape.isAvailable(currentDate);
        assertTrue("Tape T005 should be available after first rental was returned", firstResult);
        
        // C005 rents T005 with rental date="2025-01-06", due_date="2025-01-15", return_date=null
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape);
        rental2.setRentalDate(LocalDate.parse("2025-01-06"));
        rental2.setDueDate(LocalDate.parse("2025-01-15"));
        rental2.setReturnDate(null);
        
        customer2.getRentals().add(rental2);
        RentalStore.addRental(rental2);
        
        // Second check: The second creation - should be False (tape is currently rented out)
        boolean secondResult = tape.isAvailable(currentDate);
        assertFalse("Tape T005 should not be available when currently rented out", secondResult);
    }
}