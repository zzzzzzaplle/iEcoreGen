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
        // Clear static rentals list before each test
        VideoRental.getRentals().clear();
    }
    
    @Test
    public void testCase1_ReturnedOneDayLate() {
        // Setup
        Customer c1 = new Customer();
        c1.setId("C001");
        
        Tape tape = new Tape();
        tape.setId("V001");
        tape.setVideoInformation("Movie 1");
        
        LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
        LocalDate currentDate = LocalDate.parse("2025-01-09", formatter);
        
        // Add video rental
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(rentalDate.plusDays(1)); // Due date: 2025-01-02
        rental.setReturnDate(LocalDate.parse("2025-01-09", formatter));
        
        c1.getRentals().add(rental);
        
        // Calculate overdue amount
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Verify - 7 days overdue (2025-01-02 to 2025-01-09) * $0.5 = $3.50
        assertEquals(3.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() {
        // Setup
        Customer c2 = new Customer();
        c2.setId("C002");
        
        Tape tape = new Tape();
        tape.setId("V002");
        tape.setVideoInformation("Movie 2");
        
        LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
        LocalDate currentDate = LocalDate.parse("2025-01-12", formatter);
        
        // Add video rental (unreturned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(rentalDate.plusDays(1)); // Due date: 2025-01-02
        // Return date remains null
        
        c2.getRentals().add(rental);
        
        // Calculate overdue amount
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Verify - 10 days overdue (2025-01-02 to 2025-01-12) * $0.5 = $5.00
        assertEquals(5.00, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() {
        // Setup
        Customer c3 = new Customer();
        c3.setId("C003");
        
        Tape tape = new Tape();
        tape.setId("V003");
        tape.setVideoInformation("Movie 3");
        
        LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        
        // Add video rental
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(rentalDate.plusDays(5)); // Due date: 2025-01-06
        rental.setReturnDate(LocalDate.parse("2025-01-06", formatter)); // Returned on due date
        
        c3.getRentals().add(rental);
        
        // Calculate overdue amount
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Verify - Returned on due date, no overdue
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() {
        // Setup
        Customer c4 = new Customer();
        c4.setId("C004");
        
        Tape tape = new Tape();
        tape.setId("V004");
        tape.setVideoInformation("Movie 4");
        
        LocalDate rentalDate = LocalDate.parse("2025-01-05", formatter);
        LocalDate currentDate = LocalDate.parse("2025-01-06", formatter);
        
        // Add video rental (unreturned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(rentalDate.plusDays(5)); // Due date: 2025-01-10
        
        c4.getRentals().add(rental);
        
        // Calculate overdue amount
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Verify - Not overdue yet (current date is before due date)
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_ReturnedFiveDaysLate() {
        // Setup
        Customer c5 = new Customer();
        c5.setId("C005");
        
        Tape tape = new Tape();
        tape.setId("V005");
        tape.setVideoInformation("Movie 5");
        
        LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        
        // Add video rental (unreturned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(rentalDate.plusDays(7)); // Due date: 2025-01-08
        // Return date remains null
        
        c5.getRentals().add(rental);
        
        // Calculate overdue amount
        double result = rental.calculateOwedPastDueAmount(currentDate);
        
        // Verify - 2 days overdue (2025-01-08 to 2025-01-10) * $0.5 = $1.00
        assertEquals(1.00, result, 0.001);
    }
}