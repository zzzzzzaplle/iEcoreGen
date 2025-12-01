import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        Date rentalDate = dateFormat.parse("2025-01-01");
        
        // Create 5 active rentals
        for (int i = 1; i <= 5; i++) {
            VideoRental rental = new VideoRental();
            rental.setDueDate(new Date(rentalDate.getTime() + (7 * 24 * 60 * 60 * 1000L))); // 7 days after rental
            rental.setReturnDate(null);
            rental.setOwnedPastDueAmount(0.0);
            Tape tape = new Tape();
            tape.setId("TAPE" + i);
            rental.setTape(tape);
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        // Create available Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        
        // Mock isAvailable to return true
        // Note: The isAvailable method in the provided code always returns true,
        // so we don't need to override it for this test
        
        // Attempt to rent the tape
        Date currentDate = dateFormat.parse("2025-01-01");
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Expected Output: True
        assertTrue(result);
    }
    
    @Test
    public void testCase2_customerHas20RentalsMaxLimit() throws ParseException {
        // Create Customer C002 with 20 active rentals
        Customer customer = new Customer();
        customer.setId("C002");
        
        List<VideoRental> rentals = new ArrayList<>();
        Date rentalDate = dateFormat.parse("2025-01-01");
        
        // Create 20 active rentals
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = new VideoRental();
            rental.setDueDate(new Date(rentalDate.getTime() + (7 * 24 * 60 * 60 * 1000L))); // 7 days after rental
            rental.setReturnDate(null);
            rental.setOwnedPastDueAmount(0.0);
            Tape tape = new Tape();
            tape.setId("TAPE" + i);
            rental.setTape(tape);
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        // Create available Tape T002
        Tape tape = new Tape();
        tape.setId("T002");
        
        // Attempt to rent the tape
        Date currentDate = dateFormat.parse("2025-01-01");
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Expected Output: False
        assertFalse(result);
    }
    
    @Test
    public void testCase3_customerHasOverdueFees() throws ParseException {
        // Create Customer C003 with 1 active rental, due_date="2025-01-05", return_date=null (3 days overdue)
        Customer customer = new Customer();
        customer.setId("C003");
        
        List<VideoRental> rentals = new ArrayList<>();
        Date dueDate = dateFormat.parse("2025-01-05");
        Date rentalDate = new Date(dueDate.getTime() - (7 * 24 * 60 * 60 * 1000L)); // 7 days before due date
        
        VideoRental rental = new VideoRental();
        rental.setDueDate(dueDate);
        rental.setReturnDate(null);
        rental.setOwnedPastDueAmount(0.0);
        Tape tape = new Tape();
        tape.setId("TAPE1");
        rental.setTape(tape);
        rentals.add(rental);
        
        customer.setRentals(rentals);
        
        // Create available Tape T003
        Tape tapeT003 = new Tape();
        tapeT003.setId("T003");
        
        // Attempt to rent the tape with current date "2025-01-05" (3 days overdue)
        Date currentDate = dateFormat.parse("2025-01-08");
        boolean result = customer.addVedioTapeRental(tapeT003, currentDate);
        
        // Expected Output: False
        assertFalse(result);
    }
    
    @Test
    public void testCase4_tapeIsUnavailable() throws ParseException {
        // Create Customer C004 with 0 rentals
        Customer customer = new Customer();
        customer.setId("C004");
        customer.setRentals(new ArrayList<>());
        
        // Create Tape T004 that is unavailable
        Tape tape = new Tape();
        tape.setId("T004");
        
        // Override isAvailable to return false for this test case
        Tape unavailableTape = new Tape() {
            @Override
            public boolean isAvailable(Date currentDate) {
                return false;
            }
        };
        unavailableTape.setId("T004");
        
        // Attempt to rent the tape
        Date currentDate = dateFormat.parse("2025-01-01");
        boolean result = customer.addVedioTapeRental(unavailableTape, currentDate);
        
        // Expected Output: False
        assertFalse(result);
    }
    
    @Test
    public void testCase5_allConditionsFail() throws ParseException {
        // Create Customer C005 with 20 active rentals and one overdue rental
        Customer customer = new Customer();
        customer.setId("C005");
        
        List<VideoRental> rentals = new ArrayList<>();
        Date currentDate = dateFormat.parse("2025-01-01");
        
        // Create 20 active rentals
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = new VideoRental();
            Date dueDate = new Date(currentDate.getTime() + (7 * 24 * 60 * 60 * 1000L)); // 7 days after current date
            rental.setDueDate(dueDate);
            rental.setReturnDate(null);
            rental.setOwnedPastDueAmount(0.0);
            Tape tape = new Tape();
            tape.setId("TAPE" + i);
            rental.setTape(tape);
            rentals.add(rental);
        }
        
        // Add one overdue rental (due_date="2024-12-31", return_date=null)
        VideoRental overdueRental = new VideoRental();
        Date dueDate = dateFormat.parse("2024-12-31");
        overdueRental.setDueDate(dueDate);
        overdueRental.setReturnDate(null);
        overdueRental.setOwnedPastDueAmount(5.00);
        Tape overdueTape = new Tape();
        overdueTape.setId("OVERDUE_TAPE");
        overdueRental.setTape(overdueTape);
        rentals.add(overdueRental);
        
        customer.setRentals(rentals);
        
        // Create Tape T005 that is unavailable
        Tape tape = new Tape() {
            @Override
            public boolean isAvailable(Date currentDate) {
                return false;
            }
        };
        tape.setId("T005");
        
        // Attempt to rent the tape
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Expected Output: False
        assertFalse(result);
    }
}