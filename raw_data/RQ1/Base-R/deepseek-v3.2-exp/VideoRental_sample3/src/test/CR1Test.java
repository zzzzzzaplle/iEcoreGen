import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CR1Test {
    
    private VideoRentalSystem system;
    private Customer customer;
    private VideoTape tape;
    private Rental rental;
    
    @Before
    public void setUp() {
        system = new VideoRentalSystem();
        customer = new Customer();
        tape = new VideoTape();
    }
    
    @Test
    public void testCase1_Returned1DayLate() {
        // Setup
        customer.setAccountNumber("C001");
        tape.setBarCodeId("V001");
        
        rental = new Rental();
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.parse("2025-01-01"));
        rental.setDueDate(LocalDate.parse("2025-01-08"));
        rental.setReturnDate(LocalDate.parse("2025-01-09"));
        
        // Test
        double result = system.calculatePastDueAmount(rental, "2025-01-10");
        
        // Verify
        assertEquals("Returned 1 day late should result in $0.50 overdue fee", 0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAnd3DaysOverdue() {
        // Setup
        customer.setAccountNumber("C002");
        tape.setBarCodeId("V002");
        
        rental = new Rental();
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.parse("2025-01-01"));
        rental.setDueDate(LocalDate.parse("2025-01-08"));
        rental.setReturnDate(null); // Not returned
        
        // Test
        double result = system.calculatePastDueAmount(rental, "2025-01-12");
        
        // Verify
        assertEquals("Unreturned and 3 days overdue should result in $1.50 overdue fee", 1.50, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() {
        // Setup
        customer.setAccountNumber("C003");
        tape.setBarCodeId("V003");
        
        rental = new Rental();
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.parse("2025-01-01"));
        rental.setDueDate(LocalDate.parse("2025-01-08"));
        rental.setReturnDate(LocalDate.parse("2025-01-08")); // Returned on due date
        
        // Test
        double result = system.calculatePastDueAmount(rental, "2025-01-10");
        
        // Verify
        assertEquals("Returned on due date should result in $0.00 overdue fee", 0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() {
        // Setup
        customer.setAccountNumber("C004");
        tape.setBarCodeId("V004");
        
        rental = new Rental();
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.parse("2025-01-05"));
        rental.setDueDate(LocalDate.parse("2025-01-12"));
        rental.setReturnDate(null); // Not returned
        
        // Test
        double result = system.calculatePastDueAmount(rental, "2025-01-06");
        
        // Verify
        assertEquals("Unreturned but not overdue should result in $0.00 overdue fee", 0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_Returned5DaysLate() {
        // Setup
        customer.setAccountNumber("C005");
        tape.setBarCodeId("V005");
        
        rental = new Rental();
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.parse("2025-01-01"));
        rental.setDueDate(LocalDate.parse("2025-01-08"));
        rental.setReturnDate(null); // Not returned
        
        // Test
        double result = system.calculatePastDueAmount(rental, "2025-01-14");
        
        // Verify
        assertEquals("Unreturned and 6 days overdue should result in $3.00 overdue fee", 3.00, result, 0.001);
    }
}