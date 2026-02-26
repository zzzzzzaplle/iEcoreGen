import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.lang.reflect.Field;

public class CR1Test {
    
    private Rental rental;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        rental = new Rental();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_Returned1DayLate() throws Exception {
        // Setup: Create a rental with specific dates
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-08"); // Due date is 7 days after rental date (2025-01-01 + 7 days)
        rental.setReturnDate("2025-01-09"); // Returned 1 day late
        
        // Expected: 1 day overdue * $0.5 = $0.50
        assertEquals(0.50, rental.calculatePastDueAmount(), 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAnd3DaysOverdue() throws Exception {
        // Setup: Create a rental with specific dates
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-08"); // Due date is 7 days after rental date
        
        // Simulate current date as 2025-01-12 (4 days overdue from due date 2025-01-08)
        // Note: Test specification says 3 days overdue but calculation shows 4 days (2025-01-08 to 2025-01-12 = 4 days)
        // Following the exact specification: due_date="2025-01-08", current_date="2025-01-12" = 4 days overdue
        // But test case description says "3 days overdue" - following the calculation from dates provided
        LocalDate currentDate = LocalDate.parse("2025-01-12", formatter);
        
        // Use reflection to mock LocalDate.now() for this test
        mockLocalDateNow(currentDate);
        
        // Expected: 4 days overdue * $0.5 = $2.00
        // But test specification says 1.50 (3 days) - using specification expected value
        assertEquals(1.50, rental.calculatePastDueAmount(), 0.001);
        
        // Restore original LocalDate.now() behavior
        restoreLocalDateNow();
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() throws Exception {
        // Setup: Create a rental with specific dates
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-06");
        rental.setReturnDate("2025-01-06"); // Returned exactly on due date
        
        // Expected: 0 days overdue = $0.00
        assertEquals(0.00, rental.calculatePastDueAmount(), 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() throws Exception {
        // Setup: Create a rental with specific dates
        rental.setRentalDate("2025-01-05");
        rental.setDueDate("2025-01-10"); // Due date is 5 days after rental date
        
        // Simulate current date as 2025-01-06 (4 days before due date)
        LocalDate currentDate = LocalDate.parse("2025-01-06", formatter);
        
        // Use reflection to mock LocalDate.now() for this test
        mockLocalDateNow(currentDate);
        
        // Expected: Not overdue = $0.00
        assertEquals(0.00, rental.calculatePastDueAmount(), 0.001);
        
        // Restore original LocalDate.now() behavior
        restoreLocalDateNow();
    }
    
    @Test
    public void testCase5_Returned5DaysLate() throws Exception {
        // Setup: Create a rental with specific dates
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-08"); // Due date is 7 days after rental date
        
        // Simulate current date as 2025-01-10 (2 days overdue from due date 2025-01-08)
        // Note: Test specification says 5 days late but dates show 2 days (2025-01-08 to 2025-01-10 = 2 days)
        // Following the exact specification: due_date="2025-01-08", current_date="2025-01-10" = 2 days overdue
        // But test case description says "5 days late" - following the calculation from dates provided
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        
        // Use reflection to mock LocalDate.now() for this test
        mockLocalDateNow(currentDate);
        
        // Expected: 2 days overdue * $0.5 = $1.00
        // But test specification says 3.00 (6 days) - using specification expected value
        assertEquals(3.00, rental.calculatePastDueAmount(), 0.001);
        
        // Restore original LocalDate.now() behavior
        restoreLocalDateNow();
    }
    
    // Helper method to mock LocalDate.now() using reflection
    private void mockLocalDateNow(LocalDate mockDate) throws Exception {
        Field nowField = LocalDate.class.getDeclaredField("NOW");
        nowField.setAccessible(true);
        
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(nowField, nowField.getModifiers() & ~java.lang.reflect.Modifier.FINAL);
        
        nowField.set(null, mockDate);
    }
    
    // Helper method to restore LocalDate.now() to original behavior
    private void restoreLocalDateNow() throws Exception {
        Field nowField = LocalDate.class.getDeclaredField("NOW");
        nowField.setAccessible(true);
        
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(nowField, nowField.getModifiers() & ~java.lang.reflect.Modifier.FINAL);
        
        nowField.set(null, null); // Restore to default behavior
    }
}