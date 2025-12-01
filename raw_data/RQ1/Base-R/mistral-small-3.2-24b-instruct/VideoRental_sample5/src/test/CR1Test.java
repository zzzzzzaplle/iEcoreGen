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
    }
    
    @Test
    public void testCase1_ReturnedOneDayLate() {
        // Setup
        Customer c1 = new Customer();
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("V001");
        
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-08", formatter));
        rental.setReturnDate(LocalDate.parse("2025-01-09", formatter));
        
        c1.addRental(rental);
        
        // Execute
        rental.calculatePastDueAmount(LocalDate.parse("2025-01-10", formatter));
        
        // Verify
        assertEquals(0.50, rental.getOverdueAmount(), 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() {
        // Setup
        Customer c2 = new Customer();
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("V002");
        
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-09", formatter));
        rental.setReturnDate(null);
        
        c2.addRental(rental);
        
        // Execute
        LocalDate currentDate = LocalDate.parse("2025-01-12", formatter);
        rental.calculatePastDueAmount(currentDate);
        
        // Verify
        assertEquals(1.50, rental.getOverdueAmount(), 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() {
        // Setup
        Customer c3 = new Customer();
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("V003");
        
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-06", formatter));
        rental.setReturnDate(LocalDate.parse("2025-01-06", formatter));
        
        c3.addRental(rental);
        
        // Execute
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        rental.calculatePastDueAmount(currentDate);
        
        // Verify
        assertEquals(0.00, rental.getOverdueAmount(), 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() {
        // Setup
        Customer c4 = new Customer();
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("V004");
        
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate(LocalDate.parse("2025-01-05", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-10", formatter));
        rental.setReturnDate(null);
        
        c4.addRental(rental);
        
        // Execute
        LocalDate currentDate = LocalDate.parse("2025-01-06", formatter);
        rental.calculatePastDueAmount(currentDate);
        
        // Verify
        assertEquals(0.00, rental.getOverdueAmount(), 0.001);
    }
    
    @Test
    public void testCase5_ReturnedFiveDaysLate() {
        // Setup
        Customer c5 = new Customer();
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("V005");
        
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-08", formatter));
        rental.setReturnDate(null);
        
        c5.addRental(rental);
        
        // Execute
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        rental.calculatePastDueAmount(currentDate);
        
        // Verify
        assertEquals(1.00, rental.getOverdueAmount(), 0.001);
    }
}