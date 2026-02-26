import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR2Test {
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_TapeIsAvailable() throws Exception {
        // Setup
        Tape tape = new Tape();
        tape.setId("T001");
        Date currentDate = dateFormat.parse("2025-01-01");
        
        // Test tape availability
        boolean result = tape.isAvailable(currentDate);
        
        // Verify
        assertTrue("Tape should be available when no active rentals exist", result);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() throws Exception {
        // Setup
        Tape tape = new Tape();
        tape.setId("T002");
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Create rental with return_date=null (unreturned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2025-01-05"));
        rental.setReturnDate(null);
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        Date currentDate = dateFormat.parse("2025-01-01");
        
        // Test tape availability
        boolean result = tape.isAvailable(currentDate);
        
        // Verify
        assertFalse("Tape should be unavailable when actively rented out", result);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() throws Exception {
        // Setup
        Tape tape = new Tape();
        tape.setId("T003");
        Customer customer = new Customer();
        customer.setId("C002");
        
        // Create rental that has been returned
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2024-12-30"));
        rental.setReturnDate(dateFormat.parse("2024-12-31"));
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        Date currentDate = dateFormat.parse("2025-01-01");
        
        // Test tape availability
        boolean result = tape.isAvailable(currentDate);
        
        // Verify
        assertTrue("Tape should be available when previously rented but returned", result);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() throws Exception {
        // Setup
        Tape tape = new Tape();
        tape.setId("T004");
        Customer customer = new Customer();
        customer.setId("C003");
        
        // Create overdue rental with return_date=null
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2025-01-01"));
        rental.setReturnDate(null);
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        Date currentDate = dateFormat.parse("2025-01-10");
        
        // Test tape availability
        boolean result = tape.isAvailable(currentDate);
        
        // Verify
        assertFalse("Tape should be unavailable when overdue and unreturned", result);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() throws Exception {
        // Setup
        Tape tape = new Tape();
        tape.setId("T005");
        
        // First customer with returned rental
        Customer customer1 = new Customer();
        customer1.setId("C004");
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape);
        rental1.setDueDate(dateFormat.parse("2025-01-05"));
        rental1.setReturnDate(dateFormat.parse("2025-01-01"));
        List<VideoRental> rentals1 = new ArrayList<>();
        rentals1.add(rental1);
        customer1.setRentals(rentals1);
        
        // Second customer with current rental
        Customer customer2 = new Customer();
        customer2.setId("C005");
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape);
        rental2.setDueDate(dateFormat.parse("2025-01-15"));
        rental2.setReturnDate(null); // Currently rented
        List<VideoRental> rentals2 = new ArrayList<>();
        rentals2.add(rental2);
        customer2.setRentals(rentals2);
        
        Date currentDate = dateFormat.parse("2025-01-10");
        
        // Test tape availability
        boolean result = tape.isAvailable(currentDate);
        
        // Verify
        assertFalse("Tape should be unavailable when currently rented by another customer", result);
    }
}