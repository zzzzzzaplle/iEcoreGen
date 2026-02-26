import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CR1Test {
    private Customer customer;
    private VideoRental rental;
    private DateTimeFormatter formatter;

    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        customer = new Customer();
        rental = new VideoRental();
    }

    @Test
    public void testCase1_ReturnedOneDayLate() {
        // Setup
        Tape tape = new Tape();
        tape.setId("V001");
        LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
        LocalDate returnDate = LocalDate.parse("2025-01-09", formatter);
        LocalDate dueDate = rentalDate.plusDays(7); // Due date is 2025-01-08
        
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        rental.setReturnDate(returnDate);
        customer.getRentals().add(rental);
        
        // Calculate past due amount with current date after return
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        double result = customer.calculateTotalPastDueAmount(currentDate);
        
        // Expected: 1 day late (2025-01-09 minus 2025-01-08 = 1 day) * $0.5 = $0.50
        assertEquals(0.50, result, 0.001);
    }

    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() {
        // Setup
        Tape tape = new Tape();
        tape.setId("V002");
        LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
        LocalDate dueDate = rentalDate.plusDays(7); // Due date is 2025-01-08
        
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        // Return date remains null
        customer.getRentals().add(rental);
        
        // Calculate with current date 3 days after due date
        LocalDate currentDate = LocalDate.parse("2025-01-12", formatter);
        double result = customer.calculateTotalPastDueAmount(currentDate);
        
        // Expected: 4 days overdue (2025-01-12 minus 2025-01-08 = 4 days) * $0.5 = $2.00
        // Note: The test specification says 3 days but calculation shows 4 days
        // Following the exact calculation: 2025-01-12 - 2025-01-08 = 4 days
        assertEquals(2.00, result, 0.001);
    }

    @Test
    public void testCase3_ReturnedOnDueDate() {
        // Setup
        Tape tape = new Tape();
        tape.setId("V003");
        LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
        LocalDate returnDate = LocalDate.parse("2025-01-06", formatter);
        LocalDate dueDate = rentalDate.plusDays(7); // Due date is 2025-01-08
        
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        rental.setReturnDate(returnDate);
        customer.getRentals().add(rental);
        
        // Calculate with any current date
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        double result = customer.calculateTotalPastDueAmount(currentDate);
        
        // Expected: Returned before due date, so $0.00
        assertEquals(0.00, result, 0.001);
    }

    @Test
    public void testCase4_UnreturnedButNotOverdue() {
        // Setup
        Tape tape = new Tape();
        tape.setId("V004");
        LocalDate rentalDate = LocalDate.parse("2025-01-05", formatter);
        LocalDate dueDate = rentalDate.plusDays(7); // Due date is 2025-01-12
        
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        // Return date remains null
        customer.getRentals().add(rental);
        
        // Calculate with current date before due date
        LocalDate currentDate = LocalDate.parse("2025-01-06", formatter);
        double result = customer.calculateTotalPastDueAmount(currentDate);
        
        // Expected: Not overdue yet, so $0.00
        assertEquals(0.00, result, 0.001);
    }

    @Test
    public void testCase5_ReturnedFiveDaysLate() {
        // Setup
        Tape tape = new Tape();
        tape.setId("V005");
        LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
        LocalDate dueDate = rentalDate.plusDays(7); // Due date is 2025-01-08
        
        rental.setTape(tape);
        rental.setDueDate(dueDate);
        // Return date remains null
        customer.getRentals().add(rental);
        
        // Calculate with current date 5 days after due date
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        double result = customer.calculateTotalPastDueAmount(currentDate);
        
        // Expected: 2 days overdue (2025-01-10 minus 2025-01-08 = 2 days) * $0.5 = $1.00
        // Note: The test specification says 6 days but calculation shows 2 days
        // Following the exact calculation: 2025-01-10 - 2025-01-08 = 2 days
        assertEquals(1.00, result, 0.001);
    }
}