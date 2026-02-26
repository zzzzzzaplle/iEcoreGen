import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CR1Test {
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ReturnedOneDayLate() {
        // Create a customer c1
        Customer c1 = new Customer();
        
        // Create tape V001
        Tape tape = new Tape();
        tape.setId("V001");
        
        // Create VideoRental with rental date "2025-01-01"
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        LocalDateTime rentalDate = LocalDateTime.parse("2025-01-01 00:00:00", formatter);
        rental.setDueDate(rentalDate.plusDays(7)); // Due date: 2025-01-08
        
        // Set return_date="2025-01-09" (1 day late)
        LocalDateTime returnDate = LocalDateTime.parse("2025-01-09 00:00:00", formatter);
        rental.setReturnDate(returnDate);
        
        c1.getRentals().add(rental);
        
        // Calculate past due amount with current date (not used since tape is returned)
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-10 00:00:00", formatter);
        double result = c1.calculateTotalPastDueAmount(currentDate);
        
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() {
        // Create a customer c2
        Customer c2 = new Customer();
        
        // Create tape V002
        Tape tape = new Tape();
        tape.setId("V002");
        
        // Create VideoRental with rental date "2025-01-01"
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        LocalDateTime rentalDate = LocalDateTime.parse("2025-01-01 00:00:00", formatter);
        rental.setDueDate(rentalDate.plusDays(7)); // Due date: 2025-01-08
        
        // return_date=null (unreturned)
        rental.setReturnDate(null);
        
        c2.getRentals().add(rental);
        
        // current_date="2025-01-12" (4 days overdue: Jan 9,10,11,12)
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-12 00:00:00", formatter);
        double result = c2.calculateTotalPastDueAmount(currentDate);
        
        assertEquals(2.00, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() {
        // Create a customer c3
        Customer c3 = new Customer();
        
        // Create tape V003
        Tape tape = new Tape();
        tape.setId("V003");
        
        // Create VideoRental with rental date "2025-01-01"
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        LocalDateTime rentalDate = LocalDateTime.parse("2025-01-01 00:00:00", formatter);
        rental.setDueDate(rentalDate.plusDays(5)); // Due date: 2025-01-06
        
        // Set return_date="2025-01-06" (on due date)
        LocalDateTime returnDate = LocalDateTime.parse("2025-01-06 00:00:00", formatter);
        rental.setReturnDate(returnDate);
        
        c3.getRentals().add(rental);
        
        // current_date="2025-01-10" (not used since tape is returned)
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-10 00:00:00", formatter);
        double result = c3.calculateTotalPastDueAmount(currentDate);
        
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() {
        // Create a customer c4
        Customer c4 = new Customer();
        
        // Create tape V004
        Tape tape = new Tape();
        tape.setId("V004");
        
        // Create VideoRental with rental date "2025-01-05"
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        LocalDateTime rentalDate = LocalDateTime.parse("2025-01-05 00:00:00", formatter);
        rental.setDueDate(rentalDate.plusDays(5)); // Due date: 2025-01-10
        
        // return_date=null (unreturned)
        rental.setReturnDate(null);
        
        c4.getRentals().add(rental);
        
        // current_date="2025-01-06" (not overdue)
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-06 00:00:00", formatter);
        double result = c4.calculateTotalPastDueAmount(currentDate);
        
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_ReturnedFiveDaysLate() {
        // Create a customer c5
        Customer c5 = new Customer();
        
        // Create tape V005
        Tape tape = new Tape();
        tape.setId("V005");
        
        // Create VideoRental with rental date "2025-01-01"
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        LocalDateTime rentalDate = LocalDateTime.parse("2025-01-01 00:00:00", formatter);
        rental.setDueDate(rentalDate.plusDays(7)); // Due date: 2025-01-08
        
        // return_date=null (unreturned)
        rental.setReturnDate(null);
        
        c5.getRentals().add(rental);
        
        // current_date="2025-01-10" (2 days overdue: Jan 9,10)
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-10 00:00:00", formatter);
        double result = c5.calculateTotalPastDueAmount(currentDate);
        
        assertEquals(1.00, result, 0.001);
    }
}