import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class CR2Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_TapeIsAvailable() throws Exception {
        // Setup
        Tape tape = new Tape();
        tape.setId("T001");
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Execute
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
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        Date rentalDate = dateFormat.parse("2024-12-28 00:00:00");
        Date dueDate = dateFormat.parse("2025-01-05 00:00:00");
        
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        // return_date remains null (unreturned)
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Execute
        boolean result = tape.isAvailable(currentDate);
        
        // Verify
        assertFalse("Tape should not be available when it has an active rental with null return date", result);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() throws Exception {
        // Setup
        Tape tape = new Tape();
        tape.setId("T003");
        
        Customer customer = new Customer();
        customer.setId("C002");
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        Date rentalDate = dateFormat.parse("2024-12-25 00:00:00");
        Date dueDate = dateFormat.parse("2024-12-30 00:00:00");
        Date returnDate = dateFormat.parse("2024-12-31 00:00:00");
        
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        rental.setReturnDate(returnDate);
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Execute
        boolean result = tape.isAvailable(currentDate);
        
        // Verify
        assertTrue("Tape should be available when it was rented but has been returned", result);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() throws Exception {
        // Setup
        Tape tape = new Tape();
        tape.setId("T004");
        
        Customer customer = new Customer();
        customer.setId("C003");
        
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        Date rentalDate = dateFormat.parse("2024-12-28 00:00:00");
        Date dueDate = dateFormat.parse("2025-01-01 00:00:00");
        
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        // return_date remains null (unreturned and overdue)
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Execute
        boolean result = tape.isAvailable(currentDate);
        
        // Verify
        assertFalse("Tape should not be available when it has an overdue rental with null return date", result);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() throws Exception {
        // Setup
        Tape tape = new Tape();
        tape.setId("T005");
        
        Customer customer1 = new Customer();
        customer1.setId("C004");
        
        Customer customer2 = new Customer();
        customer2.setId("C005");
        
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        
        // First rental: returned early
        Date rentalDate1 = dateFormat.parse("2025-01-01 00:00:00");
        Date dueDate1 = dateFormat.parse("2025-01-05 00:00:00");
        Date returnDate1 = dateFormat.parse("2025-01-01 00:00:00");
        
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape);
        rental1.setDueDate(dueDate1);
        rental1.setReturnDate(returnDate1);
        
        List<VideoRental> rentals1 = new ArrayList<>();
        rentals1.add(rental1);
        customer1.setRentals(rentals1);
        
        // Second rental: active (not returned)
        Date rentalDate2 = dateFormat.parse("2025-01-06 00:00:00");
        Date dueDate2 = dateFormat.parse("2025-01-15 00:00:00");
        
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape);
        rental2.setDueDate(dueDate2);
        // return_date remains null
        
        List<VideoRental> rentals2 = new ArrayList<>();
        rentals2.add(rental2);
        customer2.setRentals(rentals2);
        
        // Execute
        boolean result = tape.isAvailable(currentDate);
        
        // Verify
        assertFalse("Tape should not be available when it has an active rental from second customer", result);
    }
}