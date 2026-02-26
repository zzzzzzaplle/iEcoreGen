import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CR2Test {
    
    private Tape tapeT001;
    private Tape tapeT002;
    private Tape tapeT003;
    private Tape tapeT004;
    private Tape tapeT005;
    
    @Before
    public void setUp() {
        // Clear the rental repository before each test to ensure isolation
        List<VideoRental> allRentals = RentalRepository.getAllRentals();
        // Since ALL_RENTALS is private and static, we need to clear it through reflection or by reinitializing
        // For this test, we'll create a helper method to clear the repository
        clearRentalRepository();
    }
    
    private void clearRentalRepository() {
        // Access the private static field through reflection to clear it
        try {
            java.lang.reflect.Field field = RentalRepository.class.getDeclaredField("ALL_RENTALS");
            field.setAccessible(true);
            java.util.List<VideoRental> list = (java.util.List<VideoRental>) field.get(null);
            list.clear();
        } catch (Exception e) {
            fail("Failed to clear rental repository: " + e.getMessage());
        }
    }
    
    @Test
    public void testCase1_TapeIsAvailable() {
        // Input: tape_id="T001", current_date="2025-01-01"
        // Setup: Create Tape with id="T001", No active rentals for T001
        tapeT001 = new Tape("T001", "Video Information for T001");
        
        // Execute: Check availability
        LocalDate currentDate = LocalDate.parse("2025-01-01");
        boolean isAvailable = tapeT001.isAvailable(currentDate);
        
        // Verify: Expected Output: True
        assertTrue("Tape T001 should be available when no active rentals exist", isAvailable);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() {
        // Input: tape_id="T002", current_date="2025-01-01"
        // Setup: 
        // 1. Create Tape with id="T002". Create Customer C001.
        // 2. C001 rents T002 with rental date="2024-12-28", due_date="2025-01-05", return_date=null (unreturned)
        tapeT002 = new Tape("T002", "Video Information for T002");
        Customer customerC001 = new Customer("C001");
        
        // Create and setup the rental
        VideoRental rental = new VideoRental();
        rental.setTape(tapeT002);
        rental.setRentalStartDate(LocalDate.parse("2024-12-28"));
        rental.setDueDate(LocalDate.parse("2025-01-05"));
        rental.setReturnDate(null);
        
        // Add to customer and repository
        customerC001.getRentals().add(rental);
        RentalRepository.addRental(rental);
        
        // Execute: Check availability
        LocalDate currentDate = LocalDate.parse("2025-01-01");
        boolean isAvailable = tapeT002.isAvailable(currentDate);
        
        // Verify: Expected Output: False
        assertFalse("Tape T002 should not be available when it has an active rental", isAvailable);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Input: tape_id="T003", current_date="2025-01-01"
        // Setup:
        // 1. Create Tape with id="T003". Create Customer C002.
        // 2. C002 rents T003 with rental date="2024-12-25", due_date="2024-12-30", return_date="2024-12-31"
        tapeT003 = new Tape("T003", "Video Information for T003");
        Customer customerC002 = new Customer("C002");
        
        // Create and setup the rental (already returned)
        VideoRental rental = new VideoRental();
        rental.setTape(tapeT003);
        rental.setRentalStartDate(LocalDate.parse("2024-12-25"));
        rental.setDueDate(LocalDate.parse("2024-12-30"));
        rental.setReturnDate(LocalDate.parse("2024-12-31"));
        
        // Add to customer and repository
        customerC002.getRentals().add(rental);
        RentalRepository.addRental(rental);
        
        // Execute: Check availability
        LocalDate currentDate = LocalDate.parse("2025-01-01");
        boolean isAvailable = tapeT003.isAvailable(currentDate);
        
        // Verify: Expected Output: True
        assertTrue("Tape T003 should be available when it has been returned", isAvailable);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Input: tape_id="T004", current_date="2025-01-10"
        // Setup:
        // 1. Create Tape with id="T004". Create Customer C003.
        // 2. C003 rents T004 with rental date="2024-12-28", due_date="2025-01-01", return_date=null (unreturned)
        tapeT004 = new Tape("T004", "Video Information for T004");
        Customer customerC003 = new Customer("C003");
        
        // Create and setup the rental (overdue and unreturned)
        VideoRental rental = new VideoRental();
        rental.setTape(tapeT004);
        rental.setRentalStartDate(LocalDate.parse("2024-12-28"));
        rental.setDueDate(LocalDate.parse("2025-01-01"));
        rental.setReturnDate(null);
        
        // Add to customer and repository
        customerC003.getRentals().add(rental);
        RentalRepository.addRental(rental);
        
        // Execute: Check availability
        LocalDate currentDate = LocalDate.parse("2025-01-10");
        boolean isAvailable = tapeT004.isAvailable(currentDate);
        
        // Verify: Expected Output: False
        assertFalse("Tape T004 should not be available when it has an overdue unreturned rental", isAvailable);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Input: tape_id="T005", current_date="2025-01-10"
        // Setup: 
        // 1. Create Tape with id="T005". Create Customer C004, C005.
        // 2. C004 rents T005 with rental date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01" → first rental
        // 3. C005 rents T005 with rental date="2025-01-06", due_date="2025-01-15", return_date=null → second rental
        tapeT005 = new Tape("T005", "Video Information for T005");
        Customer customerC004 = new Customer("C004");
        Customer customerC005 = new Customer("C005");
        
        // First rental: returned
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tapeT005);
        rental1.setRentalStartDate(LocalDate.parse("2025-01-01"));
        rental1.setDueDate(LocalDate.parse("2025-01-05"));
        rental1.setReturnDate(LocalDate.parse("2025-01-01"));
        
        // Second rental: unreturned
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tapeT005);
        rental2.setRentalStartDate(LocalDate.parse("2025-01-06"));
        rental2.setDueDate(LocalDate.parse("2025-01-15"));
        rental2.setReturnDate(null);
        
        // Add rentals to customers and repository
        customerC004.getRentals().add(rental1);
        customerC005.getRentals().add(rental2);
        RentalRepository.addRental(rental1);
        RentalRepository.addRental(rental2);
        
        // Execute: Check availability after second rental
        LocalDate currentDate = LocalDate.parse("2025-01-10");
        boolean isAvailable = tapeT005.isAvailable(currentDate);
        
        // Verify: Expected Output: False (because second rental is active)
        assertFalse("Tape T005 should not be available when it has an active rental", isAvailable);
    }
}