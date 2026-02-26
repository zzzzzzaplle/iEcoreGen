import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR2Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_TapeIsAvailable() throws Exception {
        // Setup: Create Tape with id="T001", no active rentals
        Tape tape = new Tape();
        tape.setId("T001");
        
        Date currentDate = dateFormat.parse("2025-01-01");
        
        // Execute and Verify
        assertTrue("Tape T001 should be available when no active rentals exist", 
                   tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() throws Exception {
        // Setup: Create Tape with id="T002", Customer C001 with unreturned rental
        Tape tape = new Tape();
        tape.setId("T002");
        
        Customer customer = new Customer();
        customer.setId("C001");
        
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2025-01-05"));
        rental.setReturnDate(null); // unreturned
        
        customer.getRentals().add(rental);
        
        Date currentDate = dateFormat.parse("2025-01-01");
        
        // Execute and Verify
        assertFalse("Tape T002 should be unavailable when rented out and unreturned", 
                    tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() throws Exception {
        // Setup: Create Tape with id="T003", Customer C002 with returned rental
        Tape tape = new Tape();
        tape.setId("T003");
        
        Customer customer = new Customer();
        customer.setId("C002");
        
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2024-12-30"));
        rental.setReturnDate(dateFormat.parse("2024-12-31")); // returned
        
        customer.getRentals().add(rental);
        
        Date currentDate = dateFormat.parse("2025-01-01");
        
        // Execute and Verify
        assertTrue("Tape T003 should be available when previously rented but returned", 
                   tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() throws Exception {
        // Setup: Create Tape with id="T004", Customer C003 with overdue unreturned rental
        Tape tape = new Tape();
        tape.setId("T004");
        
        Customer customer = new Customer();
        customer.setId("C003");
        
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2025-01-01"));
        rental.setReturnDate(null); // unreturned and overdue
        
        customer.getRentals().add(rental);
        
        Date currentDate = dateFormat.parse("2025-01-10");
        
        // Execute and Verify
        assertFalse("Tape T004 should be unavailable when it has an overdue unreturned rental", 
                    tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() throws Exception {
        // Setup: Create Tape with id="T005", multiple customers with rental history
        Tape tape = new Tape();
        tape.setId("T005");
        
        Customer customer1 = new Customer();
        customer1.setId("C004");
        
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape);
        rental1.setDueDate(dateFormat.parse("2025-01-05"));
        rental1.setReturnDate(dateFormat.parse("2025-01-01")); // returned
        
        Customer customer2 = new Customer();
        customer2.setId("C005");
        
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape);
        rental2.setDueDate(dateFormat.parse("2025-01-15"));
        rental2.setReturnDate(null); // unreturned
        
        customer1.getRentals().add(rental1);
        customer2.getRentals().add(rental2);
        
        Date currentDate = dateFormat.parse("2025-01-10");
        
        // Execute and Verify
        assertFalse("Tape T005 should be unavailable when currently rented out by another customer", 
                    tape.isAvailable(currentDate));
    }
}