import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR3Test {
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_successfulRental() throws ParseException {
        // Create Customer C001 with 5 active rentals
        Customer customer = new Customer();
        customer.setId("C001");
        List<VideoRental> rentals = new ArrayList<>();
        
        // Create 5 active rentals (unreturned)
        for (int i = 1; i <= 5; i++) {
            VideoRental rental = new VideoRental();
            Date rentalDate = dateFormat.parse("2025-01-0" + i);
            rental.setDueDate(new Date(rentalDate.getTime() + (7 * 24 * 60 * 60 * 1000))); // Due 7 days later
            rental.setReturnDate(null); // Unreturned
            rental.setTape(new Tape()); // Dummy tape
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        // Create available Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        
        // Current date
        Date currentDate = dateFormat.parse("2025-01-01");
        
        // Attempt to add rental
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Expected: True
        assertTrue(result);
    }
    
    @Test
    public void testCase2_customerHas20RentalsMaxLimit() throws ParseException {
        // Create Customer C002 with 20 active rentals
        Customer customer = new Customer();
        customer.setId("C002");
        List<VideoRental> rentals = new ArrayList<>();
        
        // Create 20 active rentals (unreturned)
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = new VideoRental();
            Date rentalDate = dateFormat.parse("2025-01-01");
            rental.setDueDate(new Date(rentalDate.getTime() + (7 * 24 * 60 * 60 * 1000))); // Due 7 days later
            rental.setReturnDate(null); // Unreturned
            rental.setTape(new Tape()); // Dummy tape
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        // Create available Tape T002
        Tape tape = new Tape();
        tape.setId("T002");
        
        // Current date
        Date currentDate = dateFormat.parse("2025-01-01");
        
        // Attempt to add rental
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Expected: False
        assertFalse(result);
    }
    
    @Test
    public void testCase3_customerHasOverdueFees() throws ParseException {
        // Create Customer C003 with 1 active rental that is overdue
        Customer customer = new Customer();
        customer.setId("C003");
        List<VideoRental> rentals = new ArrayList<>();
        
        // Create 1 overdue rental
        VideoRental rental = new VideoRental();
        Date dueDate = dateFormat.parse("2025-01-05");
        rental.setDueDate(dueDate);
        rental.setReturnDate(null); // Unreturned
        rental.setTape(new Tape()); // Dummy tape
        rentals.add(rental);
        
        customer.setRentals(rentals);
        
        // Create available Tape T003
        Tape tape = new Tape();
        tape.setId("T003");
        
        // Current date is after due date (overdue)
        Date currentDate = dateFormat.parse("2025-01-08");
        
        // Attempt to add rental
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Expected: False
        assertFalse(result);
    }
    
    @Test
    public void testCase4_tapeIsUnavailable() throws ParseException {
        // Create Customer C004 with 0 rentals
        Customer customer = new Customer();
        customer.setId("C004");
        customer.setRentals(new ArrayList<>()); // Empty list
        
        // Create Tape T004 that is unavailable
        Tape tape = new Tape();
        tape.setId("T004");
        
        // Override isAvailable to return false for this test
        Tape unavailableTape = new Tape() {
            @Override
            public boolean isAvailable(Date currentDate) {
                return