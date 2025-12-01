import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR1Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_ReturnedOneDayLate() throws Exception {
        // Setup
        Customer c1 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        rental.setTape(tape);
        
        Date rentalDate = dateFormat.parse("2025-01-01");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(rentalDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date dueDate = calendar.getTime();
        rental.setDueDate(dueDate);
        
        Date returnDate = dateFormat.parse("2025-01-09");
        rental.setReturnDate(returnDate);
        
        c1.getRentals().add(rental);
        
        // Test
        double result = rental.calculateOwedPastDueAmount(dateFormat.parse("2025-01-10"));
        
        // Verify
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() throws Exception {
        // Setup
        Customer c2 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        rental.setTape(tape);
        
        Date rentalDate = dateFormat.parse("2025-01-01");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(rentalDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date dueDate = calendar.getTime();
        rental.setDueDate(dueDate);
        
        rental.setReturnDate(null); // Not returned
        
        c2.getRentals().add(rental);
        
        // Test
        double result = rental.calculateOwedPastDueAmount(dateFormat.parse("2025-01-12"));
        
        // Verify
        assertEquals(1.50, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() throws Exception {
        // Setup
        Customer c3 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        rental.setTape(tape);
        
        Date rentalDate = dateFormat.parse("2025-01-01");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(rentalDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date dueDate = calendar.getTime();
        rental.setDueDate(dueDate);
        
        Date returnDate = dateFormat.parse("2025-01-06");
        rental.setReturnDate(returnDate);
        
        c3.getRentals().add(rental);
        
        // Test
        double result = rental.calculateOwedPastDueAmount(dateFormat.parse("2025-01-10"));
        
        // Verify
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() throws Exception {
        // Setup
        Customer c4 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        rental.setTape(tape);
        
        Date rentalDate = dateFormat.parse("2025-01-05");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(rentalDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date dueDate = calendar.getTime();
        rental.setDueDate(dueDate);
        
        rental.setReturnDate(null); // Not returned
        
        c4.getRentals().add(rental);
        
        // Test
        double result = rental.calculateOwedPastDueAmount(dateFormat.parse("2025-01-06"));
        
        // Verify
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_ReturnedFiveDaysLate() throws Exception {
        // Setup
        Customer c5 = new Customer();
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        rental.setTape(tape);
        
        Date rentalDate = dateFormat.parse("2025-01-01");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(rentalDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date dueDate = calendar.getTime();
        rental.setDueDate(dueDate);
        
        rental.setReturnDate(null); // Not returned
        
        c5.getRentals().add(rental);
        
        // Test
        double result = rental.calculateOwedPastDueAmount(dateFormat.parse("2025-01-10"));
        
        // Verify
        assertEquals(3.00, result, 0.001);
    }
}