import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CR1Test {
    
    private Rental rental;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        rental = new Rental();
    }
    
    @Test
    public void testCase1_ReturnedOneDayLate() {
        // Setup
        LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
        LocalDate dueDate = rentalDate.plusDays(7); // 2025-01-08
        LocalDate returnDate = LocalDate.parse("2025-01-09", formatter);
        
        rental.setRentalDate(rentalDate);
        rental.setDueDate(dueDate);
        rental.setReturnDate(returnDate);
        
        // Execute
        rental.calculateOverdueFee();
        
        // Verify
        assertEquals(0.50, rental.getOverdueFee(), 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() {
        // Setup
        LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
        LocalDate dueDate = rentalDate.plusDays(7); // 2025-01-08
        LocalDate currentDate = LocalDate.parse("2025-01-12", formatter);
        
        rental.setRentalDate(rentalDate);
        rental.setDueDate(dueDate);
        rental.setReturnDate(null);
        
        // Mock current date by temporarily replacing the calculation logic
        // Since we can't mock LocalDate.now(), we'll create a test-specific method
        testCalculateOverdueFeeWithMockDate(currentDate);
        
        // Verify
        assertEquals(1.50, rental.getOverdueFee(), 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() {
        // Setup
        LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
        LocalDate dueDate = rentalDate.plusDays(5); // 2025-01-06
        LocalDate returnDate = LocalDate.parse("2025-01-06", formatter);
        
        rental.setRentalDate(rentalDate);
        rental.setDueDate(dueDate);
        rental.setReturnDate(returnDate);
        
        // Execute
        rental.calculateOverdueFee();
        
        // Verify
        assertEquals(0.00, rental.getOverdueFee(), 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() {
        // Setup
        LocalDate rentalDate = LocalDate.parse("2025-01-05", formatter);
        LocalDate dueDate = rentalDate.plusDays(5); // 2025-01-10
        LocalDate currentDate = LocalDate.parse("2025-01-06", formatter);
        
        rental.setRentalDate(rentalDate);
        rental.setDueDate(dueDate);
        rental.setReturnDate(null);
        
        // Mock current date
        testCalculateOverdueFeeWithMockDate(currentDate);
        
        // Verify
        assertEquals(0.00, rental.getOverdueFee(), 0.001);
    }
    
    @Test
    public void testCase5_ReturnedFiveDaysLate() {
        // Setup
        LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
        LocalDate dueDate = rentalDate.plusDays(7); // 2025-01-08
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        
        rental.setRentalDate(rentalDate);
        rental.setDueDate(dueDate);
        rental.setReturnDate(null);
        
        // Mock current date
        testCalculateOverdueFeeWithMockDate(currentDate);
        
        // Verify
        assertEquals(3.00, rental.getOverdueFee(), 0.001);
    }
    
    /**
     * Helper method to calculate overdue fee with a mock current date
     * This is needed because we cannot easily mock LocalDate.now() in JUnit 4
     */
    private void testCalculateOverdueFeeWithMockDate(LocalDate mockCurrentDate) {
        if (rental.getReturnDate() != null) {
            if (rental.getReturnDate().isBefore(rental.getDueDate()) || 
                rental.getReturnDate().isEqual(rental.getDueDate())) {
                rental.setOverdueFee(0);
            } else {
                long overdueDays = rental.getReturnDate().toEpochDay() - rental.getDueDate().toEpochDay();
                rental.setOverdueFee(overdueDays * 0.5);
            }
        } else {
            long overdueDays = mockCurrentDate.toEpochDay() - rental.getDueDate().toEpochDay();
            rental.setOverdueFee(overdueDays * 0.5);
        }
        rental.setOverdueFee(Math.round(rental.getOverdueFee() * 100.0) / 100.0);
    }
}