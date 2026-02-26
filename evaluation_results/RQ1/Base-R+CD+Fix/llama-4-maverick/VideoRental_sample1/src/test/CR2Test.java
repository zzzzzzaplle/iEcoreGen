import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    private Customer customer;
    private Tape tape;
    private VideoRental rental;
    
    @Before
    public void setUp() {
        customer = new Customer();
        tape = new Tape();
        rental = new VideoRental();
    }
    
    @Test
    public void testCase1_TapeIsAvailable() {
        // Setup: Create Tape with id="T001", no active rentals
        tape.setId("T001");
        
        // Test tape availability
        LocalDate currentDate = LocalDate.parse("2025-01-01");
        boolean result = tape.isAvailable(currentDate);
        
        // Verify tape is available
        assertTrue("Tape should be available when no active rentals exist", result);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() {
        // Setup: Create Tape with id="T002" and Customer C001
        tape.setId("T002");
        customer.setId("C001");
        
        // Create rental with unreturned status
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDate.parse("2025-01-05"));
        // return_date remains null (unreturned)
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Test tape availability
        LocalDate currentDate = LocalDate.parse("2025-01-01");
        boolean result = tape.isAvailable(currentDate);
        
        // Verify tape is unavailable
        assertFalse("Tape should be unavailable when rented out and unreturned", result);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Setup: Create Tape with id="T003" and Customer C002
        tape.setId("T003");
        customer.setId("C002");
        
        // Create rental with return date before current date
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDate.parse("2024-12-30"));
        rental.setReturnDate(LocalDate.parse("2024-12-31"));
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Test tape availability
        LocalDate currentDate = LocalDate.parse("2025-01-01");
        boolean result = tape.isAvailable(currentDate);
        
        // Verify tape is available
        assertTrue("Tape should be available when previously rented but returned", result);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Setup: Create Tape with id="T004" and Customer C003
        tape.setId("T004");
        customer.setId("C003");
        
        // Create overdue rental with unreturned status
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDate.parse("2025-01-01"));
        // return_date remains null (unreturned and overdue)
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Test tape availability
        LocalDate currentDate = LocalDate.parse("2025-01-10");
        boolean result = tape.isAvailable(currentDate);
        
        // Verify tape is unavailable
        assertFalse("Tape should be unavailable when has overdue unreturned rental", result);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Setup: Create Tape with id="T005" and Customers C004, C005
        tape.setId("T005");
        
        // First customer: C004 with returned rental
        Customer customer1 = new Customer();
        customer1.setId("C004");
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape);
        rental1.setDueDate(LocalDate.parse("2025-01-05"));
        rental1.setReturnDate(LocalDate.parse("2025-01-01"));
        List<VideoRental> rentals1 = new ArrayList<>();
        rentals1.add(rental1);
        customer1.setRentals(rentals1);
        
        // Second customer: C005 with unreturned rental
        Customer customer2 = new Customer();
        customer2.setId("C005");
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape);
        rental2.setDueDate(LocalDate.parse("2025-01-15"));
        // return_date remains null (unreturned)
        List<VideoRental> rentals2 = new ArrayList<>();
        rentals2.add(rental2);
        customer2.setRentals(rentals2);
        
        // Test tape availability after first rental (should be true)
        LocalDate currentDateAfterFirst = LocalDate.parse("2025-01-02");
        boolean resultAfterFirst = tape.isAvailable(currentDateAfterFirst);
        
        // Test tape availability after second rental (should be false)
        LocalDate currentDateAfterSecond = LocalDate.parse("2025-01-10");
        boolean resultAfterSecond = tape.isAvailable(currentDateAfterSecond);
        
        // Verify results
        assertTrue("Tape should be available after first customer returned it", resultAfterFirst);
        assertFalse("Tape should be unavailable while second customer has it rented", resultAfterSecond);
    }
}