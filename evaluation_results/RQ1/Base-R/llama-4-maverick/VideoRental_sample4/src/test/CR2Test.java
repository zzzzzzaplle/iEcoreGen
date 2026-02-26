import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private VideoTapeRentalService service;
    private List<Customer> customers;
    private List<VideoTape> tapes;
    
    @Before
    public void setUp() {
        service = new VideoTapeRentalService();
        customers = new ArrayList<>();
        tapes = new ArrayList<>();
    }
    
    @Test
    public void testCase1_TapeIsAvailable() {
        // Setup
        VideoTape tape = new VideoTape();
        tape.setId("T001");
        tapes.add(tape);
        
        // Test input
        String currentDate = "2025-01-01";
        
        // Execute test
        boolean result = service.isTapeAvailable(tape, currentDate);
        
        // Verify result
        assertTrue("Tape T001 should be available when no active rentals exist", result);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() {
        // Setup
        VideoTape tape = new VideoTape();
        tape.setId("T002");
        tapes.add(tape);
        
        Customer customer = new Customer();
        customer.setId("C001");
        customer.setAccountNumber("ACC001");
        customers.add(customer);
        
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate("2024-12-28");
        rental.setDueDate("2025-01-05");
        rental.setReturnDate(null); // unreturned
        
        customer.addRental(rental);
        
        // Test input
        String currentDate = "2025-01-01";
        
        // Execute test
        boolean result = service.isTapeAvailable(tape, currentDate);
        
        // Verify result
        assertFalse("Tape T002 should not be available when rented out and unreturned", result);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Setup
        VideoTape tape = new VideoTape();
        tape.setId("T003");
        tapes.add(tape);
        
        Customer customer = new Customer();
        customer.setId("C002");
        customer.setAccountNumber("ACC002");
        customers.add(customer);
        
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate("2024-12-25");
        rental.setDueDate("2024-12-30");
        rental.setReturnDate("2024-12-31"); // returned
        
        customer.addRental(rental);
        
        // Test input
        String currentDate = "2025-01-01";
        
        // Execute test
        boolean result = service.isTapeAvailable(tape, currentDate);
        
        // Verify result
        assertTrue("Tape T003 should be available when previously rented but returned", result);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Setup
        VideoTape tape = new VideoTape();
        tape.setId("T004");
        tapes.add(tape);
        
        Customer customer = new Customer();
        customer.setId("C003");
        customer.setAccountNumber("ACC003");
        customers.add(customer);
        
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate("2024-12-28");
        rental.setDueDate("2025-01-01");
        rental.setReturnDate(null); // unreturned and overdue
        
        customer.addRental(rental);
        
        // Test input
        String currentDate = "2025-01-10";
        
        // Execute test
        boolean result = service.isTapeAvailable(tape, currentDate);
        
        // Verify result
        assertFalse("Tape T004 should not be available when it has an overdue rental", result);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Setup
        VideoTape tape = new VideoTape();
        tape.setId("T005");
        tapes.add(tape);
        
        Customer customer1 = new Customer();
        customer1.setId("C004");
        customer1.setAccountNumber("ACC004");
        customers.add(customer1);
        
        Customer customer2 = new Customer();
        customer2.setId("C005");
        customer2.setAccountNumber("ACC005");
        customers.add(customer2);
        
        // First rental - returned
        Rental rental1 = new Rental();
        rental1.setVideoTape(tape);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-01");
        customer1.addRental(rental1);
        
        // Second rental - unreturned
        Rental rental2 = new Rental();
        rental2.setVideoTape(tape);
        rental2.setRentalDate("2025-01-06");
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate(null);
        customer2.addRental(rental2);
        
        // Test input
        String currentDate = "2025-01-10";
        
        // Execute test
        boolean result = service.isTapeAvailable(tape, currentDate);
        
        // Verify result
        assertFalse("Tape T005 should not be available when currently rented out", result);
    }
}