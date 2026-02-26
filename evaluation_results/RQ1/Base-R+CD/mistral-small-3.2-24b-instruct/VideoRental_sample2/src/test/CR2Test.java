import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR2Test {
    
    private SimpleDateFormat dateFormat;
    private List<Customer> customers;
    private List<Tape> tapes;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        customers = new ArrayList<>();
        tapes = new ArrayList<>();
    }
    
    @Test
    public void testCase1_TapeIsAvailable() throws Exception {
        // Setup: Create Tape with id="T001", no active rentals for T001
        Tape tape = new Tape();
        tape.setId("T001");
        tapes.add(tape);
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Test the isAvailable method
        boolean result = tape.isAvailable(currentDate);
        
        // Verify tape is available
        assertTrue("Tape T001 should be available when no active rentals exist", result);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() throws Exception {
        // Setup: Create Tape with id="T002", Customer C001 rents T002
        Tape tape = new Tape();
        tape.setId("T002");
        tapes.add(tape);
        
        Customer customer = new Customer();
        customer.setId("C001");
        customers.add(customer);
        
        Date rentalDate = dateFormat.parse("2024-12-28 00:00:00");
        Date dueDate = dateFormat.parse("2025-01-05 00:00:00");
        
        // Create and setup rental
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        // return_date is null (unreturned)
        
        customer.getRentals().add(rental);
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Test the isAvailable method
        boolean result = tape.isAvailable(currentDate);
        
        // Verify tape is not available (rented out)
        assertFalse("Tape T002 should not be available when actively rented", result);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() throws Exception {
        // Setup: Create Tape with id="T003", Customer C002 rents and returns T003
        Tape tape = new Tape();
        tape.setId("T003");
        tapes.add(tape);
        
        Customer customer = new Customer();
        customer.setId("C002");
        customers.add(customer);
        
        Date rentalDate = dateFormat.parse("2024-12-25 00:00:00");
        Date dueDate = dateFormat.parse("2024-12-30 00:00:00");
        Date returnDate = dateFormat.parse("2024-12-31 00:00:00");
        
        // Create and setup rental with return date
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        rental.setReturnDate(returnDate);
        
        customer.getRentals().add(rental);
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Test the isAvailable method
        boolean result = tape.isAvailable(currentDate);
        
        // Verify tape is available (returned)
        assertTrue("Tape T003 should be available when previously rented but returned", result);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() throws Exception {
        // Setup: Create Tape with id="T004", Customer C003 rents T004 (overdue)
        Tape tape = new Tape();
        tape.setId("T004");
        tapes.add(tape);
        
        Customer customer = new Customer();
        customer.setId("C003");
        customers.add(customer);
        
        Date rentalDate = dateFormat.parse("2024-12-28 00:00:00");
        Date dueDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Create and setup rental (unreturned and overdue)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        // return_date is null (unreturned)
        
        customer.getRentals().add(rental);
        
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        
        // Test the isAvailable method
        boolean result = tape.isAvailable(currentDate);
        
        // Verify tape is not available (overdue rental)
        assertFalse("Tape T004 should not be available when has overdue rental", result);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() throws Exception {
        // Setup: Create Tape with id="T005", multiple customers rent T005
        Tape tape = new Tape();
        tape.setId("T005");
        tapes.add(tape);
        
        Customer customer1 = new Customer();
        customer1.setId("C004");
        customers.add(customer1);
        
        Customer customer2 = new Customer();
        customer2.setId("C005");
        customers.add(customer2);
        
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        
        // First rental: C004 rents and returns T005
        Date rentalDate1 = dateFormat.parse("2025-01-01 00:00:00");
        Date dueDate1 = dateFormat.parse("2025-01-05 00:00:00");
        Date returnDate1 = dateFormat.parse("2025-01-01 00:00:00");
        
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape);
        rental1.setDueDate(dueDate1);
        rental1.setReturnDate(returnDate1);
        customer1.getRentals().add(rental1);
        
        // Test availability after first rental (should be true - tape was returned)
        boolean result1 = tape.isAvailable(currentDate);
        assertTrue("Tape T005 should be available after first customer returned it", result1);
        
        // Second rental: C005 rents T005 (unreturned)
        Date rentalDate2 = dateFormat.parse("2025-01-06 00:00:00");
        Date dueDate2 = dateFormat.parse("2025-01-15 00:00:00");
        
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape);
        rental2.setDueDate(dueDate2);
        // return_date is null (unreturned)
        customer2.getRentals().add(rental2);
        
        // Test availability after second rental (should be false - tape is currently rented)
        boolean result2 = tape.isAvailable(currentDate);
        assertFalse("Tape T005 should not be available when second customer has active rental", result2);
    }
}