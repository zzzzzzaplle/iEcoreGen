import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Tape tape;
    private Customer customer;
    private VideoRental rental;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_TapeIsAvailable() {
        // Setup
        tape = new Tape();
        tape.setId("T001");
        
        // Input: current_date="2025-01-01"
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Execute
        boolean result = tape.isAvailable(currentDate);
        
        // Verify
        assertTrue("Tape should be available when no active rentals exist", result);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() {
        // Setup
        tape = new Tape();
        tape.setId("T002");
        
        customer = new Customer();
        customer.setId("C001");
        
        rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        // return_date=null (unreturned)
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Input: current_date="2025-01-01"
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Execute
        boolean result = tape.isAvailable(currentDate);
        
        // Verify
        assertFalse("Tape should not be available when rented and unreturned", result);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Setup
        tape = new Tape();
        tape.setId("T003");
        
        customer = new Customer();
        customer.setId("C002");
        
        rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDate.parse("2024-12-30", formatter));
        rental.setReturnDate(LocalDate.parse("2024-12-31", formatter));
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Input: current_date="2025-01-01"
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Execute
        boolean result = tape.isAvailable(currentDate);
        
        // Verify
        assertTrue("Tape should be available when previously rented but returned", result);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Setup
        tape = new Tape();
        tape.setId("T004");
        
        customer = new Customer();
        customer.setId("C003");
        
        rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDate.parse("2025-01-01", formatter));
        // return_date=null (unreturned)
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Input: current_date="2025-01-10"
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        
        // Execute
        boolean result = tape.isAvailable(currentDate);
        
        // Verify
        assertFalse("Tape should not be available when overdue and unreturned", result);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Setup first rental - returned
        tape = new Tape();
        tape.setId("T005");
        
        Customer customer1 = new Customer();
        customer1.setId("C004");
        
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape);
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-01", formatter));
        
        List<VideoRental> rentals1 = new ArrayList<>();
        rentals1.add(rental1);
        customer1.setRentals(rentals1);
        
        // Setup second rental - unreturned
        Customer customer2 = new Customer();
        customer2.setId("C005");
        
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape);
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        // return_date=null (unreturned)
        
        List<VideoRental> rentals2 = new ArrayList<>();
        rentals2.add(rental2);
        customer2.setRentals(rentals2);
        
        // Input: current_date="2025-01-10"
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        
        // Execute
        boolean result = tape.isAvailable(currentDate);
        
        // Verify
        assertFalse("Tape should not be available when any customer has unreturned rental", result);
    }
}