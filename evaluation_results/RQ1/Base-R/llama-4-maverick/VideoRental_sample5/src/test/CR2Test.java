import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private VideoRentalSystem videoRentalSystem;
    private List<Customer> customers;
    private List<VideoTape> videoTapes;
    
    @Before
    public void setUp() {
        videoRentalSystem = new VideoRentalSystem();
        customers = new ArrayList<>();
        videoTapes = new ArrayList<>();
    }
    
    @Test
    public void testCase1_TapeIsAvailable() {
        // Setup: Create Tape with id="T001", no active rentals for T001
        VideoTape tape = new VideoTape();
        tape.setId("T001");
        videoTapes.add(tape);
        videoRentalSystem.setVideoTapes(videoTapes);
        
        // Test tape availability for current_date="2025-01-01"
        boolean result = tape.isAvailable("2025-01-01", new ArrayList<Rental>());
        
        // Verify tape is available
        assertTrue("Tape T001 should be available when no rentals exist", result);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() {
        // Setup: Create Tape with id="T002", Customer C001 rents T002 (unreturned)
        VideoTape tape = new VideoTape();
        tape.setId("T002");
        videoTapes.add(tape);
        
        Customer customer = new Customer();
        customer.setId("C001");
        
        Rental rental = new Rental();
        rental.setTapeId("T002");
        rental.setRentalDate("2024-12-28");
        rental.setDueDate("2025-01-05");
        rental.setReturnDate(null); // Unreturned
        
        customer.addRental(rental);
        customers.add(customer);
        
        videoRentalSystem.setVideoTapes(videoTapes);
        videoRentalSystem.setCustomers(customers);
        
        // Test tape availability for current_date="2025-01-01"
        boolean result = tape.isAvailable("2025-01-01", customer.getRentals());
        
        // Verify tape is not available (rented out)
        assertFalse("Tape T002 should not be available when rented and unreturned", result);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Setup: Create Tape with id="T003", Customer C002 rents T003 (returned)
        VideoTape tape = new VideoTape();
        tape.setId("T003");
        videoTapes.add(tape);
        
        Customer customer = new Customer();
        customer.setId("C002");
        
        Rental rental = new Rental();
        rental.setTapeId("T003");
        rental.setRentalDate("2024-12-25");
        rental.setDueDate("2024-12-30");
        rental.setReturnDate("2024-12-31"); // Returned
        
        customer.addRental(rental);
        customers.add(customer);
        
        videoRentalSystem.setVideoTapes(videoTapes);
        videoRentalSystem.setCustomers(customers);
        
        // Test tape availability for current_date="2025-01-01"
        boolean result = tape.isAvailable("2025-01-01", customer.getRentals());
        
        // Verify tape is available (returned)
        assertTrue("Tape T003 should be available when previously rented but returned", result);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Setup: Create Tape with id="T004", Customer C003 rents T004 (unreturned and overdue)
        VideoTape tape = new VideoTape();
        tape.setId("T004");
        videoTapes.add(tape);
        
        Customer customer = new Customer();
        customer.setId("C003");
        
        Rental rental = new Rental();
        rental.setTapeId("T004");
        rental.setRentalDate("2024-12-28");
        rental.setDueDate("2025-01-01");
        rental.setReturnDate(null); // Unreturned and overdue
        
        customer.addRental(rental);
        customers.add(customer);
        
        videoRentalSystem.setVideoTapes(videoTapes);
        videoRentalSystem.setCustomers(customers);
        
        // Test tape availability for current_date="2025-01-10"
        boolean result = tape.isAvailable("2025-01-10", customer.getRentals());
        
        // Verify tape is not available (overdue rental)
        assertFalse("Tape T004 should not be available when rented and overdue", result);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Setup: Create Tape with id="T005", multiple customers rent T005
        VideoTape tape = new VideoTape();
        tape.setId("T005");
        videoTapes.add(tape);
        
        // First customer rents and returns tape
        Customer customer1 = new Customer();
        customer1.setId("C004");
        
        Rental rental1 = new Rental();
        rental1.setTapeId("T005");
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-01"); // Returned immediately
        
        customer1.addRental(rental1);
        
        // Second customer rents tape (unreturned)
        Customer customer2 = new Customer();
        customer2.setId("C005");
        
        Rental rental2 = new Rental();
        rental2.setTapeId("T005");
        rental2.setRentalDate("2025-01-06");
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate(null); // Unreturned
        
        customer2.addRental(rental2);
        
        // Combine rentals from both customers
        List<Rental> allRentals = new ArrayList<>();
        allRentals.addAll(customer1.getRentals());
        allRentals.addAll(customer2.getRentals());
        
        // Test tape availability for current_date="2025-01-10"
        boolean result = tape.isAvailable("2025-01-10", allRentals);
        
        // Verify tape is not available (second rental is active)
        assertFalse("Tape T005 should not be available when second rental is active", result);
    }
}