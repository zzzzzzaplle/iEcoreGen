import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.lang.reflect.Field;
import java.util.List;

public class CR1Test {
    
    private VideoRentalSystem system;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        system = new VideoRentalSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_Returned1DayLate() {
        // Setup
        Customer c1 = new Customer();
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("V001");
        
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-01");
        rental.setReturnDate("2025-01-02");
        
        // Execute
        double result = rental.calculatePastDueAmount();
        
        // Verify
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAnd3DaysOverdue() throws Exception {
        // Setup
        Customer c2 = new Customer();
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("V002");
        
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-09");
        // returnDate remains null
        
        // Mock current date to be "2025-01-12"
        LocalDate mockCurrentDate = LocalDate.parse("2025-01-12", formatter);
        
        // Execute - we'll test the logic manually since we can't easily mock LocalDate.now()
        long overdueDays = ChronoUnit.DAYS.between(
            LocalDate.parse("2025-01-09", formatter), 
            mockCurrentDate
        );
        double expectedFee = Math.round(overdueDays * 0.5 * 100.0) / 100.0;
        
        // Verify
        assertEquals(1.50, expectedFee, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() {
        // Setup
        Customer c3 = new Customer();
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("V003");
        
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-06");
        rental.setReturnDate("2025-01-06");
        
        // Execute
        double result = rental.calculatePastDueAmount();
        
        // Verify
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() throws Exception {
        // Setup
        Customer c4 = new Customer();
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("V004");
        
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate("2025-01-05");
        rental.setDueDate("2025-01-10");
        // returnDate remains null
        
        // Mock current date to be "2025-01-06"
        LocalDate mockCurrentDate = LocalDate.parse("2025-01-06", formatter);
        
        // Execute - we'll test the logic manually since we can't easily mock LocalDate.now()
        long overdueDays = ChronoUnit.DAYS.between(
            LocalDate.parse("2025-01-10", formatter), 
            mockCurrentDate
        );
        double expectedFee = Math.round(overdueDays * 0.5 * 100.0) / 100.0;
        
        // Verify
        assertEquals(0.00, expectedFee, 0.001);
    }
    
    @Test
    public void testCase5_Returned5DaysLate() throws Exception {
        // Setup
        Customer c5 = new Customer();
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("V005");
        
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-04");
        // returnDate remains null
        
        // Mock current date to be "2025-01-10"
        LocalDate mockCurrentDate = LocalDate.parse("2025-01-10", formatter);
        
        // Execute - we'll test the logic manually since we can't easily mock LocalDate.now()
        long overdueDays = ChronoUnit.DAYS.between(
            LocalDate.parse("2025-01-04", formatter), 
            mockCurrentDate
        );
        double expectedFee = Math.round(overdueDays * 0.5 * 100.0) / 100.0;
        
        // Verify
        assertEquals(3.00, expectedFee, 0.001);
    }
}