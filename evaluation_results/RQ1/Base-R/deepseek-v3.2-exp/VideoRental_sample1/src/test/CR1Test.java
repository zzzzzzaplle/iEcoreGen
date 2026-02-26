import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.math.BigDecimal;

public class CR1Test {
    
    private Rental rental;
    private Customer customer;
    private VideoTape tape;
    
    @Before
    public void setUp() {
        // Initialize common objects for tests
        customer = new Customer();
        customer.setAccountNumber("C001");
        customer.setName("Test Customer");
        
        tape = new VideoTape();
        tape.setBarcodeId("V001");
        tape.setTitle("Test Movie");
        
        rental = new Rental();
        rental.setRentalId("R001");
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDays(5); // 5-day rental period
    }
    
    @Test
    public void testCase1_Returned1DayLate() {
        // Setup: Create rental with due date 2025-01-06 (5 days after 2025-01-01)
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-06");
        rental.setReturnDate("2025-01-07"); // Returned 1 day late
        
        // Calculate overdue fee with current date (should use return date since it's not null)
        BigDecimal result = rental.calculateOverdueFee("2025-01-10");
        
        // Expected: 1 day * $0.5 = $0.50
        assertEquals(new BigDecimal("0.50"), result);
    }
    
    @Test
    public void testCase2_UnreturnedAnd3DaysOverdue() {
        // Setup: Create rental with due date 2025-01-09 (8 days after 2025-01-01)
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-09");
        rental.setReturnDate(null); // Not returned
        
        // Calculate overdue fee with current date 2025-01-12 (3 days overdue)
        BigDecimal result = rental.calculateOverdueFee("2025-01-12");
        
        // Expected: 3 days * $0.5 = $1.50
        assertEquals(new BigDecimal("1.50"), result);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() {
        // Setup: Create rental with due date 2025-01-06 (5 days after 2025-01-01)
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-06");
        rental.setReturnDate("2025-01-06"); // Returned on due date
        
        // Calculate overdue fee with current date 2025-01-10
        BigDecimal result = rental.calculateOverdueFee("2025-01-10");
        
        // Expected: 0 days overdue = $0.00
        assertEquals(BigDecimal.ZERO.setScale(2), result);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() {
        // Setup: Create rental with due date 2025-01-10 (5 days after 2025-01-05)
        rental.setRentalDate("2025-01-05");
        rental.setDueDate("2025-01-10");
        rental.setReturnDate(null); // Not returned
        
        // Calculate overdue fee with current date 2025-01-06 (4 days before due date)
        BigDecimal result = rental.calculateOverdueFee("2025-01-06");
        
        // Expected: Not overdue = $0.00
        assertEquals(BigDecimal.ZERO.setScale(2), result);
    }
    
    @Test
    public void testCase5_Returned6DaysLate() {
        // Setup: Create rental with due date 2025-01-08 (7 days after 2025-01-01)
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-08");
        rental.setReturnDate(null); // Not returned
        
        // Calculate overdue fee with current date 2025-01-14 (6 days overdue)
        BigDecimal result = rental.calculateOverdueFee("2025-01-14");
        
        // Expected: 6 days * $0.5 = $3.00
        assertEquals(new BigDecimal("3.00"), result);
    }
}