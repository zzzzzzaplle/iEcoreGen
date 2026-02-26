import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CR1Test {
    private RentalSystem rentalSystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        rentalSystem = new RentalSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_ReturnedOneDayLate() {
        // Test Case 1: "Returned 1 day late"
        // Setup: Create rental with rental date "2025-01-01", due date "2025-01-08", return date "2025-01-09"
        RentalTransaction rental = new RentalTransaction();
        rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-08", formatter));
        rental.setReturnDate(LocalDate.parse("2025-01-09", formatter));
        
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        double result = RentalSystem.calculatePastDueAmount(rental, currentDate);
        
        // Expected Output: 0.50 (1 day * $0.5)
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() {
        // Test Case 2: "Unreturned and 3 days overdue"
        // Setup: Create rental with rental date "2025-01-01", due date "2025-01-08", return date null
        RentalTransaction rental = new RentalTransaction();
        rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-08", formatter));
        rental.setReturnDate(null); // Not returned
        
        LocalDate currentDate = LocalDate.parse("2025-01-12", formatter);
        double result = RentalSystem.calculatePastDueAmount(rental, currentDate);
        
        // Expected Output: 2.00 (4 days * $0.5) - Note: 2025-01-08 to 2025-01-12 is 4 days
        assertEquals(2.00, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() {
        // Test Case 3: "Returned on due date"
        // Setup: Create rental with rental date "2025-01-01", due date "2025-01-08", return date "2025-01-08"
        RentalTransaction rental = new RentalTransaction();
        rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-08", formatter));
        rental.setReturnDate(LocalDate.parse("2025-01-08", formatter));
        
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        double result = RentalSystem.calculatePastDueAmount(rental, currentDate);
        
        // Expected Output: 0.00 (returned on due date)
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() {
        // Test Case 4: "Unreturned but not overdue"
        // Setup: Create rental with rental date "2025-01-05", due date "2025-01-12", return date null
        RentalTransaction rental = new RentalTransaction();
        rental.setRentalDate(LocalDate.parse("2025-01-05", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-12", formatter));
        rental.setReturnDate(null); // Not returned
        
        LocalDate currentDate = LocalDate.parse("2025-01-06", formatter);
        double result = RentalSystem.calculatePastDueAmount(rental, currentDate);
        
        // Expected Output: 0.00 (current date is before due date)
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_ReturnedFiveDaysLate() {
        // Test Case 5: "Returned 5 days late" - Note: Correction to match setup
        // Setup: Create rental with rental date "2025-01-01", due date "2025-01-08", return date null
        RentalTransaction rental = new RentalTransaction();
        rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-08", formatter));
        rental.setReturnDate(null); // Not returned
        
        LocalDate currentDate = LocalDate.parse("2025-01-13", formatter);
        double result = RentalSystem.calculatePastDueAmount(rental, currentDate);
        
        // Expected Output: 2.50 (5 days * $0.5) - Note: 2025-01-08 to 2025-01-13 is 5 days
        assertEquals(2.50, result, 0.001);
    }
}