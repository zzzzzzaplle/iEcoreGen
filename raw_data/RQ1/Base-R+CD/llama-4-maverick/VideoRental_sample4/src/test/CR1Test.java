import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CR1Test {
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_ReturnedOneDayLate() {
        // Setup
        Customer c1 = new Customer();
        Tape tape = new Tape();
        tape.setId("V001");
        LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
        
        // Add video rental
        boolean result = c1.addVedioTapeRental(tape, rentalDate);
        assertTrue(result);
        
        // Get the rental and set return date
        VideoRental rental = c1.getRentals().get(0);
        LocalDate returnDate = LocalDate.parse("2025-01-02", formatter);
        rental.setReturnDate(returnDate);
        
        // Calculate past due amount
        double pastDueAmount = rental.calculateOwedPastDueAmount(returnDate);
        
        // Verify expected output
        assertEquals(0.50, pastDueAmount, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() {
        // Setup
        Customer c2 = new Customer();
        Tape tape = new Tape();
        tape.setId("V002");
        LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
        
        // Add video rental
        boolean result = c2.addVedioTapeRental(tape, rentalDate);
        assertTrue(result);
        
        // Get the rental (return date remains null)
        VideoRental rental = c2.getRentals().get(0);
        
        // Calculate past due amount with current date 3 days after due date
        LocalDate currentDate = LocalDate.parse("2025-01-12", formatter);
        double pastDueAmount = rental.calculateOwedPastDueAmount(currentDate);
        
        // Verify expected output
        assertEquals(1.50, pastDueAmount, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() {
        // Setup
        Customer c3 = new Customer();
        Tape tape = new Tape();
        tape.setId("V003");
        LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
        
        // Add video rental
        boolean result = c3.addVedioTapeRental(tape, rentalDate);
        assertTrue(result);
        
        // Get the rental and set return date (same as due date)
        VideoRental rental = c3.getRentals().get(0);
        LocalDate returnDate = LocalDate.parse("2025-01-06", formatter);
        rental.setReturnDate(returnDate);
        
        // Calculate past due amount
        double pastDueAmount = rental.calculateOwedPastDueAmount(returnDate);
        
        // Verify expected output
        assertEquals(0.00, pastDueAmount, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() {
        // Setup
        Customer c4 = new Customer();
        Tape tape = new Tape();
        tape.setId("V004");
        LocalDate rentalDate = LocalDate.parse("2025-01-05", formatter);
        
        // Add video rental
        boolean result = c4.addVedioTapeRental(tape, rentalDate);
        assertTrue(result);
        
        // Get the rental (return date remains null)
        VideoRental rental = c4.getRentals().get(0);
        
        // Calculate past due amount with current date before due date
        LocalDate currentDate = LocalDate.parse("2025-01-06", formatter);
        double pastDueAmount = rental.calculateOwedPastDueAmount(currentDate);
        
        // Verify expected output
        assertEquals(0.00, pastDueAmount, 0.001);
    }
    
    @Test
    public void testCase5_ReturnedFiveDaysLate() {
        // Setup
        Customer c5 = new Customer();
        Tape tape = new Tape();
        tape.setId("V005");
        LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
        
        // Add video rental
        boolean result = c5.addVedioTapeRental(tape, rentalDate);
        assertTrue(result);
        
        // Get the rental (return date remains null)
        VideoRental rental = c5.getRentals().get(0);
        
        // Calculate past due amount with current date 6 days after due date
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        double pastDueAmount = rental.calculateOwedPastDueAmount(currentDate);
        
        // Verify expected output
        assertEquals(3.00, pastDueAmount, 0.001);
    }
}