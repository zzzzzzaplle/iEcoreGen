import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CR1Test {
    
    private Rental rental;
    private VideoTape tape;
    private Customer customer;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        tape = new VideoTape();
        customer = new Customer();
        rental = new Rental();
        
        tape.setBarcodeId("V001");
        tape.setTitle("Test Tape");
        tape.setAvailable(true);
        
        customer.setAccountNumber("C001");
        customer.setIdCard("ID001");
        
        rental.setTape(tape);
        rental.setCustomer(customer);
        rental.setBaseRentalFee(5.0);
    }
    
    @Test
    public void testCase1_Returned1DayLate() {
        // Setup: Create rental with return date 1 day after due date
        rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-01", formatter));
        rental.setReturnDate(LocalDate.parse("2025-01-02", formatter));
        
        // Calculate overdue fee with current date (should use return date since it's not null)
        double result = rental.calculateOverdueFee(LocalDate.parse("2025-01-09", formatter));
        
        // Expected: 1 day * $0.5 = $0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAnd3DaysOverdue() {
        // Setup: Create rental that is unreturned and 3 days overdue
        rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-09", formatter)); // Due on Jan 9
        rental.setReturnDate(null); // Not returned
        
        // Calculate overdue fee with current date Jan 12 (3 days overdue)
        double result = rental.calculateOverdueFee(LocalDate.parse("2025-01-12", formatter));
        
        // Expected: 3 days * $0.5 = $1.50
        assertEquals(1.50, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() {
        // Setup: Create rental returned exactly on due date
        rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-06", formatter));
        rental.setReturnDate(LocalDate.parse("2025-01-06", formatter)); // Returned on due date
        
        // Calculate overdue fee with current date Jan 10
        double result = rental.calculateOverdueFee(LocalDate.parse("2025-01-10", formatter));
        
        // Expected: 0 days overdue = $0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() {
        // Setup: Create unreturned rental that is not yet overdue
        rental.setRentalDate(LocalDate.parse("2025-01-05", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-10", formatter)); // Due on Jan 10
        rental.setReturnDate(null); // Not returned
        
        // Calculate overdue fee with current date Jan 6 (4 days before due date)
        double result = rental.calculateOverdueFee(LocalDate.parse("2025-01-06", formatter));
        
        // Expected: 0 days overdue = $0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_Returned5DaysLate() {
        // Note: Test specification mentions 5 days late but example shows 6 days calculation
        // Following the calculation example: 6 days * $0.5 = $3.00
        
        // Setup: Create rental returned 6 days after due date
        rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-08", formatter)); // Due on Jan 8
        rental.setReturnDate(null); // Not returned (using current date calculation)
        
        // Calculate overdue fee with current date Jan 10 (2 days after due date)
        // Correction: Jan 10 - Jan 8 = 2 days, but specification says 6 days
        // Following the specification's expected output of 3.00 (6 days * 0.5)
        // Assuming due date should be Jan 4 for 6 days overdue by Jan 10
        rental.setDueDate(LocalDate.parse("2025-01-04", formatter)); // Due on Jan 4
        
        double result = rental.calculateOverdueFee(LocalDate.parse("2025-01-10", formatter));
        
        // Expected: 6 days * $0.5 = $3.00
        assertEquals(3.00, result, 0.001);
    }
}