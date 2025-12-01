import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Setup
        Customer customer = new Customer();
        customer.setAccountNumber("C001");
        
        // Rental 1: returned early, no overdue
        VideoRental rental1 = new VideoRental();
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        rental1.setVideoTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-03", formatter));
        
        // Rental 2: returned early, no overdue
        VideoRental rental2 = new VideoRental();
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T002");
        rental2.setVideoTape(tape2);
        rental2.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(LocalDate.parse("2025-01-12", formatter));
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Set current date to 2025-01-20
        // This test case doesn't use current date for calculations since all tapes are returned
        
        // Test
        double totalAmountDue = customer.getTotalAmountDue();
        
        // Verify
        assertEquals(13.0, totalAmountDue, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Setup
        Customer customer = new Customer();
        customer.setAccountNumber("C002");
        
        // Rental 1: returned late with overdue fee
        VideoRental rental1 = new VideoRental();
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T003");
        rental1.setVideoTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-12", formatter));
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add