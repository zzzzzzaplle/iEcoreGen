import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    @Test
    public void testCase1_TapeIsAvailable() {
        // Create Tape with id="T001"
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T001");
        
        // No active rentals for T001
        // This is handled by the default implementation of getRentalsForTape() which returns empty list
        
        // Test availability for current_date="2025-01-01"
        LocalDate currentDate = LocalDate.parse("2025-01-01");
        boolean result = tape.isAvailable(currentDate);
        
        // Expected Output: True
        assertTrue("Tape T001 should be available when no active rentals exist", result);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() {
        // Create Tape with id="T002"
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T002");
        
        // Create Customer C001
        Customer customer = new Customer();
        customer.setAccountNumber("C001");
        
        // C001 rents T002 with rental date="2024-12-28", due_date="2025-01-05", return_date=null (unreturned)
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate(LocalDate.parse("2024-12-28"));
        rental.setDueDate(LocalDate.parse("2025-01-05"));
        rental.setReturnDate(null);
        
        customer.addRental(rental);
        
        // Override getRentalsForTape to return the rental we just created
        java.lang.reflect.Field rentalsField;
        try {
            rentalsField = VideoTape.class.getDeclaredField("rentals");
            rentalsField.setAccessible(true);
            List<Rental> rentalsForTape = new ArrayList<>();
            rentalsForTape.add(rental);
            rentalsField.set(tape, rentalsForTape);
        } catch (Exception e) {
            fail("Failed to set up test data using reflection");
        }
        
        // Test availability for current_date="2025-01-01"
        LocalDate currentDate = LocalDate.parse("2025-01-01");
        boolean result = tape.isAvailable(currentDate);
        
        // Expected Output: False
        assertFalse("Tape T002 should not be available when it has an active rental", result);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Create Tape with id="T003"
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T003");
        
        // Create Customer C002
        Customer customer = new Customer();
        customer.setAccountNumber("C002");
        
        // C002 rents T003 with rental date="2024-12-25", due_date="2024-12-30", return_date="2024-12-31"
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate(LocalDate.parse("2024-12-25"));
        rental.setDueDate(LocalDate.parse("2024-12-30"));
        rental.setReturnDate(LocalDate.parse("2024-12-31"));
        
        customer.addRental(rental);
        
        // Override getRentalsForTape to return the rental we just created
        java.lang.reflect.Field rentalsField;
        try {
            rentalsField = VideoTape.class.getDeclaredField("rentals");
            rentalsField.setAccessible(true);
            List<Rental> rentalsForTape = new ArrayList<>();
            rentalsForTape.add(rental);
            rentalsField.set(tape, rentalsForTape);
        } catch (Exception e) {
            fail("Failed to set up test data using reflection");
        }
        
        // Test availability for current_date="2025-01-01"
        LocalDate currentDate = LocalDate.parse("2025-01-01");
        boolean result = tape.isAvailable(currentDate);
        
        // Expected Output: True
        assertTrue("Tape T003 should be available when rental was returned before current date", result);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Create Tape with id="T004"
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T004");
        
        // Create Customer C003
        Customer customer = new Customer();
        customer.setAccountNumber("C003");
        
        // C003 rents T004 with rental date="2024-12-28", due_date="2025-01-01", return_date=null (unreturned)
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate(LocalDate.parse("2024-12-28"));
        rental.setDueDate(LocalDate.parse("2025-01-01"));
        rental.setReturnDate(null);
        
        customer.addRental(rental);
        
        // Override getRentalsForTape to return the rental we just created
        java.lang.reflect.Field rentalsField;
        try {
            rentalsField = VideoTape.class.getDeclaredField("rentals");
            rentalsField.setAccessible(true);
            List<Rental> rentalsForTape = new ArrayList<>();
            rentalsForTape.add(rental);
            rentalsField.set(tape, rentalsForTape);
        } catch (Exception e) {
            fail("Failed to set up test data using reflection");
        }
        
        // Test availability for current_date="2025-01-10"
        LocalDate currentDate = LocalDate.parse("2025-01-10");
        boolean result = tape.isAvailable(currentDate);
        
        // Expected Output: False
        assertFalse("Tape T004 should not be available when it has an overdue rental", result);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Create Tape with id="T005"
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T005");
        
        // Create Customer C004, C005
        Customer customer1 = new Customer();
        customer1.setAccountNumber("C004");
        Customer customer2 = new Customer();
        customer2.setAccountNumber("C005");
        
        // C004 rents T005 with rental date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01" → first rental
        Rental rental1 = new Rental();
        rental1.setVideoTape(tape);
        rental1.setRentalDate(LocalDate.parse("2025-01-01"));
        rental1.setDueDate(LocalDate.parse("2025-01-05"));
        rental1.setReturnDate(LocalDate.parse("2025-01-01"));
        
        // C005 rents T005 with rental date="2025-01-06", due_date="2025-01-15", return_date=null → second rental
        Rental rental2 = new Rental();
        rental2.setVideoTape(tape);
        rental2.setRentalDate(LocalDate.parse("2025-01-06"));
        rental2.setDueDate(LocalDate.parse("2025-01-15"));
        rental2.setReturnDate(null);
        
        customer1.addRental(rental1);
        customer2.addRental(rental2);
        
        // Override getRentalsForTape to return both rentals
        java.lang.reflect.Field rentalsField;
        try {
            rentalsField = VideoTape.class.getDeclaredField("rentals");
            rentalsField.setAccessible(true);
            List<Rental> rentalsForTape = new ArrayList<>();
            rentalsForTape.add(rental1);
            rentalsForTape.add(rental2);
            rentalsField.set(tape, rentalsForTape);
        } catch (Exception e) {
            fail("Failed to set up test data using reflection");
        }
        
        // Test availability for current_date="2025-01-10"
        LocalDate currentDate = LocalDate.parse("2025-01-10");
        boolean result = tape.isAvailable(currentDate);
        
        // Expected Output: False (because of the second rental with return_date=null)
        assertFalse("Tape T005 should not be available when it has an active rental", result);
    }
}