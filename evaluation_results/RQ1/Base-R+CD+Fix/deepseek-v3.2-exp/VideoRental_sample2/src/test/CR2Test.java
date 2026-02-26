import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Tape tape;
    private Customer customer;
    
    @Before
    public void setUp() {
        tape = new Tape();
        customer = new Customer();
    }
    
    @Test
    public void testCase1_TapeIsAvailable() {
        // Setup
        tape.setId("T001");
        
        // Test
        boolean result = tape.isAvailable("2025-01-01");
        
        // Verify
        assertTrue("Tape T001 should be available when no active rentals exist", result);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() {
        // Setup
        tape.setId("T002");
        customer.setId("C001");
        
        // Create rental with null return date (unreturned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate("2025-01-05");
        rental.setReturnDate(null);
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Test
        boolean result = tape.isAvailable("2025-01-01");
        
        // Verify
        assertFalse("Tape T002 should be unavailable when actively rented with null return date", result);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Setup
        tape.setId("T003");
        customer.setId("C002");
        
        // Create rental with return date before current date
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate("2024-12-30");
        rental.setReturnDate("2024-12-31");
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Test
        boolean result = tape.isAvailable("2025-01-01");
        
        // Verify
        assertTrue("Tape T003 should be available when previously rented but returned", result);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Setup
        tape.setId("T004");
        customer.setId("C003");
        
        // Create overdue rental with null return date
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate("2025-01-01");
        rental.setReturnDate(null);
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Test
        boolean result = tape.isAvailable("2025-01-10");
        
        // Verify
        assertFalse("Tape T004 should be unavailable when overdue rental exists", result);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Setup for first rental (returned)
        Tape tape1 = new Tape();
        tape1.setId("T005");
        
        Customer customer1 = new Customer();
        customer1.setId("C004");
        
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-01");
        
        List<VideoRental> rentals1 = new ArrayList<>();
        rentals1.add(rental1);
        customer1.setRentals(rentals1);
        
        // Test first rental scenario
        boolean result1 = tape1.isAvailable("2025-01-10");
        assertTrue("Tape T005 should be available after first customer returned it", result1);
        
        // Setup for second rental (unreturned)
        Tape tape2 = new Tape();
        tape2.setId("T005");
        
        Customer customer2 = new Customer();
        customer2.setId("C005");
        
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate(null);
        
        List<VideoRental> rentals2 = new ArrayList<>();
        rentals2.add(rental2);
        customer2.setRentals(rentals2);
        
        // Test second rental scenario
        boolean result2 = tape2.isAvailable("2025-01-10");
        assertFalse("Tape T005 should be unavailable when second customer hasn't returned it", result2);
    }
}