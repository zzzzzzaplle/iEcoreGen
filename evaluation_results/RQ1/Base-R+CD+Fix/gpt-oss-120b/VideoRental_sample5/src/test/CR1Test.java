import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CR1Test {
    
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Clear the rental repository before each test to ensure isolation
        RentalRepository.getAllRentals().clear();
    }
    
    @Test
    public void testCase1_ReturnedOneDayLate() {
        // Setup
        Customer c1 = new Customer("C001");
        Tape tape = new Tape("V001", "Test Video 1");
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setRentalStartDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-08", formatter)); // 7 days from rental
        rental.setReturnDate(LocalDate.parse("2025-01-09", formatter)); // 1 day late
        
        // Execute
        double result = rental.calculateOwedPastDueAmount(LocalDate.parse("2025-01-10", formatter));
        
        // Verify
        assertEquals("Returned 1 day late should result in $0.50 fee", 0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() {
        // Setup
        Customer c2 = new Customer("C002");
        Tape tape = new Tape("V002", "Test Video 2");
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setRentalStartDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-08", formatter)); // 7 days from rental
        rental.setReturnDate(null); // Not returned
        
        // Execute
        double result = rental.calculateOwedPastDueAmount(LocalDate.parse("2025-01-12", formatter)); // 4 days overdue
        
        // Verify
        assertEquals("Unreturned and 4 days overdue should result in $2.00 fee", 2.00, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() {
        // Setup
        Customer c3 = new Customer("C003");
        Tape tape = new Tape("V003", "Test Video 3");
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setRentalStartDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-08", formatter)); // 7 days from rental
        rental.setReturnDate(LocalDate.parse("2025-01-08", formatter)); // Returned on due date
        
        // Execute
        double result = rental.calculateOwedPastDueAmount(LocalDate.parse("2025-01-10", formatter));
        
        // Verify
        assertEquals("Returned on due date should result in $0.00 fee", 0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() {
        // Setup
        Customer c4 = new Customer("C004");
        Tape tape = new Tape("V004", "Test Video 4");
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setRentalStartDate(LocalDate.parse("2025-01-05", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-12", formatter)); // 7 days from rental
        rental.setReturnDate(null); // Not returned
        
        // Execute
        double result = rental.calculateOwedPastDueAmount(LocalDate.parse("2025-01-06", formatter)); // Current date before due date
        
        // Verify
        assertEquals("Unreturned but not overdue should result in $0.00 fee", 0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_ReturnedFiveDaysLate() {
        // Setup
        Customer c5 = new Customer("C005");
        Tape tape = new Tape("V005", "Test Video 5");
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setRentalStartDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-08", formatter)); // 7 days from rental
        rental.setReturnDate(null); // Not returned
        
        // Execute
        double result = rental.calculateOwedPastDueAmount(LocalDate.parse("2025-01-14", formatter)); // 6 days overdue
        
        // Verify
        assertEquals("Unreturned and 6 days overdue should result in $3.00 fee", 3.00, result, 0.001);
    }
}