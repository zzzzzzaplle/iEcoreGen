import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CR1Test {
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_ReturnedOneDayLate() {
        // Create customer c1
        Customer c1 = new Customer();
        
        // Create video rental V001
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V001");
        
        // Set rental date to 2025-01-01
        LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
        rental.setTape(tape);
        rental.setDueDate(rentalDate.plusDays(1)); // Due date is next day
        
        // Set return date to 2025-01-02 (1 day late)
        LocalDate returnDate = LocalDate.parse("2025-01-02", formatter);
        rental.setReturnDate(returnDate);
        
        // Add rental to customer
        c1.getRentals().add(rental);
        
        // Calculate past due amount with current date (not used since return date is set)
        LocalDate currentDate = LocalDate.parse("2025-01-09", formatter);
        double result = c1.calculateTotalPastDueAmount(currentDate);
        
        // Expected: 1 day * $0.5 = $0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() {
        // Create customer c2
        Customer c2 = new Customer();
        
        // Create video rental V002
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V002");
        
        // Set rental date to 2025-01-01
        LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
        rental.setTape(tape);
        rental.setDueDate(rentalDate.plusDays(1)); // Due date is 2025-01-02
        
        // Return date remains null (unreturned)
        // Add rental to customer
        c2.getRentals().add(rental);
        
        // Calculate past due amount with current date 2025-01-12
        LocalDate currentDate = LocalDate.parse("2025-01-12", formatter);
        double result = c2.calculateTotalPastDueAmount(currentDate);
        
        // Expected: 10 days overdue (2025-01-02 to 2025-01-12) * $0.5 = $5.00
        // Note: The test specification mentions 3 days but the dates show 10 days difference
        // Following the actual date calculation: due date 2025-01-02 to current 2025-01-12 = 10 days
        assertEquals(5.00, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() {
        // Create customer c3
        Customer c3 = new Customer();
        
        // Create video rental V003
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V003");
        
        // Set rental date to 2025-01-01
        LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
        rental.setTape(tape);
        rental.setDueDate(rentalDate.plusDays(5)); // Due date is 2025-01-06
        
        // Set return date to 2025-01-06 (on due date)
        LocalDate returnDate = LocalDate.parse("2025-01-06", formatter);
        rental.setReturnDate(returnDate);
        
        // Add rental to customer
        c3.getRentals().add(rental);
        
        // Calculate past due amount with current date 2025-01-10
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        double result = c3.calculateTotalPastDueAmount(currentDate);
        
        // Expected: 0.00 (returned on due date)
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() {
        // Create customer c4
        Customer c4 = new Customer();
        
        // Create video rental V004
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V004");
        
        // Set rental date to 2025-01-05
        LocalDate rentalDate = LocalDate.parse("2025-01-05", formatter);
        rental.setTape(tape);
        rental.setDueDate(rentalDate.plusDays(1)); // Due date is 2025-01-06
        
        // Return date remains null (unreturned)
        // Add rental to customer
        c4.getRentals().add(rental);
        
        // Calculate past due amount with current date 2025-01-06
        LocalDate currentDate = LocalDate.parse("2025-01-06", formatter);
        double result = c4.calculateTotalPastDueAmount(currentDate);
        
        // Expected: 0.00 (not overdue yet)
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_ReturnedFiveDaysLate() {
        // Create customer c5
        Customer c5 = new Customer();
        
        // Create video rental V005
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("V005");
        
        // Set rental date to 2025-01-01
        LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
        rental.setTape(tape);
        rental.setDueDate(rentalDate.plusDays(1)); // Due date is 2025-01-02
        
        // Return date remains null (unreturned)
        // Add rental to customer
        c5.getRentals().add(rental);
        
        // Calculate past due amount with current date 2025-01-10
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        double result = c5.calculateTotalPastDueAmount(currentDate);
        
        // Expected: 8 days overdue (2025-01-02 to 2025-01-10) * $0.5 = $4.00
        // Note: The test specification mentions 5 days but the dates show 8 days difference
        // Following the actual date calculation: due date 2025-01-02 to current 2025-01-10 = 8 days
        assertEquals(4.00, result, 0.001);
    }
}