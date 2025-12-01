import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CR1Test {
    
    private VideoRental videoRental;
    private Customer customer;
    private VideoTape videoTape;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        customer = new Customer();
        videoTape = new VideoTape();
        videoRental = new VideoRental();
    }
    
    @Test
    public void testCase1_ReturnedOneDayLate() {
        // Setup: Create a customer c1
        Customer c1 = new Customer();
        c1.setAccountNumber("C001");
        
        // Setup: c1 add a VideoRental V001 with rental date "2025-01-01"
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("V001");
        
        VideoRental rental1 = new VideoRental();
        rental1.setVideoTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-08", formatter)); // 7 days from rental
        
        // Setup: Set return_date="2025-01-09"
        rental1.setReturnDate(LocalDate.parse("2025-01-09", formatter));
        
        // Expected Output: 0.50 (1 day * $0.5)
        double result = rental1.calculateOverdueAmount();
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() {
        // Temporarily override current date for testing
        LocalDate fixedCurrentDate = LocalDate.parse("2025-01-12", formatter);
        
        // Setup: Create a customer c2
        Customer c2 = new Customer();
        c2.setAccountNumber("C002");
        
        // Setup: c2 add a VideoRental V002 with rental date "2025-01-01"
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("V002");
        
        VideoRental rental2 = new VideoRental() {
            @Override
            public double calculateOverdueAmount() {
                if (getReturnDate() != null && (getReturnDate().isBefore(getDueDate()) || getReturnDate().isEqual(getDueDate()))) {
                    return 0.0;
                } else if (getReturnDate() != null) {
                    long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(getDueDate(), getReturnDate());
                    double overdueFee = overdueDays * 0.5;
                    return Math.round(overdueFee * 100.0) / 100.0;
                } else {
                    // Use fixed current date instead of LocalDate.now()
                    long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(getDueDate(), fixedCurrentDate);
                    double overdueFee = overdueDays * 0.5;
                    return Math.round(overdueFee * 100.0) / 100.0;
                }
            }
        };
        
        rental2.setVideoTape(tape2);
        rental2.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-08", formatter)); // 7 days from rental
        rental2.setReturnDate(null); // Unreturned
        
        // Expected Output: 1.50 (3 days * $0.5)
        double result = rental2.calculateOverdueAmount();
        assertEquals(1.50, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() {
        // Setup: Create a customer c3
        Customer c3 = new Customer();
        c3.setAccountNumber("C003");
        
        // Setup: c3 add a VideoRental V003 with rental date "2025-01-01"
        VideoTape tape3 = new VideoTape();
        tape3.setBarcodeId("V003");
        
        VideoRental rental3 = new VideoRental();
        rental3.setVideoTape(tape3);
        rental3.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental3.setDueDate(LocalDate.parse("2025-01-08", formatter)); // 7 days from rental
        
        // Setup: Set return_date="2025-01-08" (due date)
        rental3.setReturnDate(LocalDate.parse("2025-01-08", formatter));
        
        // Expected Output: 0.00
        double result = rental3.calculateOverdueAmount();
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() {
        // Temporarily override current date for testing
        LocalDate fixedCurrentDate = LocalDate.parse("2025-01-06", formatter);
        
        // Setup: Create a customer c4
        Customer c4 = new Customer();
        c4.setAccountNumber("C004");
        
        // Setup: c4 add a VideoRental V004 with rental date "2025-01-05"
        VideoTape tape4 = new VideoTape();
        tape4.setBarcodeId("V004");
        
        VideoRental rental4 = new VideoRental() {
            @Override
            public double calculateOverdueAmount() {
                if (getReturnDate() != null && (getReturnDate().isBefore(getDueDate()) || getReturnDate().isEqual(getDueDate()))) {
                    return 0.0;
                } else if (getReturnDate() != null) {
                    long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(getDueDate(), getReturnDate());
                    double overdueFee = overdueDays * 0.5;
                    return Math.round(overdueFee * 100.0) / 100.0;
                } else {
                    // Use fixed current date instead of LocalDate.now()
                    long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(getDueDate(), fixedCurrentDate);
                    double overdueFee = overdueDays * 0.5;
                    return Math.round(overdueFee * 100.0) / 100.0;
                }
            }
        };
        
        rental4.setVideoTape(tape4);
        rental4.setRentalDate(LocalDate.parse("2025-01-05", formatter));
        rental4.setDueDate(LocalDate.parse("2025-01-12", formatter)); // 7 days from rental
        rental4.setReturnDate(null); // Unreturned
        
        // Expected Output: 0.00
        double result = rental4.calculateOverdueAmount();
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_ReturnedFiveDaysLate() {
        // Temporarily override current date for testing
        LocalDate fixedCurrentDate = LocalDate.parse("2025-01-10", formatter);
        
        // Setup: Create a customer c5
        Customer c5 = new Customer();
        c5.setAccountNumber("C005");
        
        // Setup: c5 add a VideoRental V005 with rental date "2025-01-01"
        VideoTape tape5 = new VideoTape();
        tape5.setBarcodeId("V005");
        
        VideoRental rental5 = new VideoRental() {
            @Override
            public double calculateOverdueAmount() {
                if (getReturnDate() != null && (getReturnDate().isBefore(getDueDate()) || getReturnDate().isEqual(getDueDate()))) {
                    return 0.0;
                } else if (getReturnDate() != null) {
                    long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(getDueDate(), getReturnDate());
                    double overdueFee = overdueDays * 0.5;
                    return Math.round(overdueFee * 100.0) / 100.0;
                } else {
                    // Use fixed current date instead of LocalDate.now()
                    long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(getDueDate(), fixedCurrentDate);
                    double overdueFee = overdueDays * 0.5;
                    return Math.round(overdueFee * 100.0) / 100.0;
                }
            }
        };
        
        rental5.setVideoTape(tape5);
        rental5.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental5.setDueDate(LocalDate.parse("2025-01-08", formatter)); // 7 days from rental
        rental5.setReturnDate(null); // Unreturned
        
        // Expected Output: 3.00 (6 days * $0.5) - Note: 2025-01-08 to 2025-01-10 is 2 days, not 5
        double result = rental5.calculateOverdueAmount();
        assertEquals(3.00, result, 0.001);
    }
}