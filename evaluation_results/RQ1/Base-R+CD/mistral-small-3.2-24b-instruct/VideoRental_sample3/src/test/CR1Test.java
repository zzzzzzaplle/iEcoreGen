import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR1Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_ReturnedOneDayLate() throws ParseException {
        // Setup
        Customer c1 = new Customer();
        Tape tape = new Tape();
        Date rentalDate = dateFormat.parse("2025-01-01");
        
        // Create video rental
        VideoRental rental = new VideoRental(tape, rentalDate);
        Date returnDate = dateFormat.parse("2025-01-09");
        rental.setReturnDate(returnDate);
        
        // Calculate past due amount
        double result = rental.calculateOwedPastDueAmount(returnDate);
        
        // Verify
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() throws ParseException {
        // Setup
        Customer c2 = new Customer();
        Tape tape = new Tape();
        Date rentalDate = dateFormat.parse("2025-01-01");
        
        // Create video rental (not returned)
        VideoRental rental = new VideoRental(tape, rentalDate);
        Date currentDate = dateFormat.parse("2025-01-12");
        
        // Calculate past due amount
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Verify
        assertEquals(1.50, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() throws ParseException {
        // Setup
        Customer c3 = new Customer();
        Tape tape = new Tape();
        Date rentalDate = dateFormat.parse("2025-01-01");
        
        // Create video rental
        VideoRental rental = new VideoRental(tape, rentalDate);
        Date returnDate = dateFormat.parse("2025-01-06");
        rental.setReturnDate(returnDate);
        Date currentDate = dateFormat.parse("2025-01-10");
        
        // Calculate past due amount
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Verify
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() throws ParseException {
        // Setup
        Customer c4 = new Customer();
        Tape tape = new Tape();
        Date rentalDate = dateFormat.parse("2025-01-05");
        
        // Create video rental (not returned)
        VideoRental rental = new VideoRental(tape, rentalDate);
        Date currentDate = dateFormat.parse("2025-01-06");
        
        // Calculate past due amount
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Verify
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_ReturnedFiveDaysLate() throws ParseException {
        // Setup
        Customer c5 = new Customer();
        Tape tape = new Tape();
        Date rentalDate = dateFormat.parse("2025-01-01");
        
        // Create video rental (not returned)
        VideoRental rental = new VideoRental(tape, rentalDate);
        Date currentDate = dateFormat.parse("2025-01-10");
        
        // Calculate past due amount
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Verify
        assertEquals(3.00, result, 0.001);
    }
}