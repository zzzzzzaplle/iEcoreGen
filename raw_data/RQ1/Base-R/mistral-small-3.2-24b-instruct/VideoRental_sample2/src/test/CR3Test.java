import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    private RentalSystem rentalSystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        rentalSystem = new RentalSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SuccessfulRental() {
        // Setup
        Customer customer = new Customer();
        customer.setAccountNumber("C001");
        
        // Create 5 active rentals for customer C001
        for (int i = 1; i <= 5; i++) {
            Rental rental = new Rental();
            VideoTape tape = new VideoTape();
            tape.setBarCodeId("TEMP" + i);
            tape.setTitle("Temporary Tape " + i);
            rental.setVideoTape(tape);
            
            LocalDate rentalDate = LocalDate.parse("2025-01-0" + i, formatter);
            LocalDate dueDate = rentalDate.plusDays(7);
            
            rental.setRentalDate(rentalDate);
            rental.setDueDate(dueDate);
            rental.setBaseRentalFee(7.0); // 7 days * 1.0 per day
            rental.setOverdueFee(0.0);
            
            customer.addRental(rental);
        }
        
        // Create available Tape T001
        VideoTape tapeT001 = new VideoTape();
        tapeT001.setBarCodeId("T001");
        tapeT001.setTitle("Test Tape 1");
        tapeT001.setAvailable(true);
        
        rentalSystem.getCustomers().add(customer);
        rentalSystem.getVideoTapes().add(tapeT001);
        
        // Test input
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        boolean result = rentalSystem.addVideoTapeRental("C001", "T001", currentDate, currentDate.plusDays(7));
        
        // Verify
        assertTrue("Rental should be successful when customer has <20 rentals, no overdue fees, and tape is available", result);
    }
    
    @Test
    public void testCase2_CustomerHasMaxRentals() {
        // Setup
        Customer customer = new Customer();
        customer.setAccountNumber("C002");
        
        // Create 20 active rentals for customer C002
        for (int i = 1; i <= 20; i++) {
            Rental rental = new Rental();
            VideoTape tape = new VideoTape();
            tape.setBarCodeId("TEMP" + i);
            tape.setTitle("Temporary Tape " + i);
            rental.setVideoTape(tape);
            
            LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
            LocalDate dueDate = rentalDate.plusDays(7);
            
            rental.setRentalDate(rentalDate);
            rental.setDueDate(dueDate);
            rental.setBaseRentalFee(7.0);
            rental.setOverdueFee(0.0);
            
            customer.addRental(rental);
        }
        
        // Create available Tape T002
        VideoTape tapeT002 = new VideoTape();
        tapeT002.setBarCodeId("T002");
        tapeT002.setTitle("Test Tape 2");
        tapeT002.setAvailable(true);
        
        rentalSystem.getCustomers().add(customer);
        rentalSystem.getVideoTapes().add(tapeT002);
        
        // Test input
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        boolean result = rentalSystem.addVideoTapeRental("C002", "T002", currentDate, currentDate.plusDays(7));
        
        // Verify
        assertFalse("Rental should fail when customer has 20 rentals (max limit)", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup
        Customer customer = new Customer();
        customer.setAccountNumber("C003");
        
        // Create 1 active rental with overdue fee for customer C003
        Rental rental = new Rental();
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("TEMP1");
        tape.setTitle("Temporary Tape 1");
        rental.setVideoTape(tape);
        
        LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
        LocalDate dueDate = LocalDate.parse("2025-01-02", formatter); // Due date before current date
        
        rental.setRentalDate(rentalDate);
        rental.setDueDate(dueDate);
        rental.setBaseRentalFee(1.0);
        
        // Calculate overdue fee (3 days overdue from due date 2025-01-02 to current date 2025-01-05)
        rental.setReturnDate(null);
        rental.calculateOverdueAmount(LocalDate.parse("2025-01-05", formatter));
        
        customer.addRental(rental);
        
        // Create available Tape T003
        VideoTape tapeT003 = new VideoTape();
        tapeT003.setBarCodeId("T003");
        tapeT003.setTitle("Test Tape 3");
        tapeT003.setAvailable(true);
        
        rentalSystem.getCustomers().add(customer);
        rentalSystem.getVideoTapes().add(tapeT003);
        
        // Test input
        LocalDate currentDate = LocalDate.parse("2025-01-05", formatter);
        boolean result = rentalSystem.addVideoTapeRental("C003", "T003", currentDate, currentDate.plusDays(7));
        
        // Verify
        assertFalse("Rental should fail when customer has overdue fees", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup
        Customer customerC004 = new Customer();
        customerC004.setAccountNumber("C004");
        
        Customer customerC010 = new Customer();
        customerC010.setAccountNumber("C010");
        
        // Create Tape T004 with active rental by customer C010
        VideoTape tapeT004 = new VideoTape();
        tapeT004.setBarCodeId("T004");
        tapeT004.setTitle("Test Tape 4");
        tapeT004.setAvailable(false);
        
        Rental rental = new Rental();
        rental.setVideoTape(tapeT004);
        LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
        LocalDate dueDate = LocalDate.parse("2025-01-05", formatter);
        rental.setRentalDate(rentalDate);
        rental.setDueDate(dueDate);
        rental.setBaseRentalFee(4.0);
        rental.setOverdueFee(0.0);
        
        customerC010.addRental(rental);
        
        rentalSystem.getCustomers().add(customerC004);
        rentalSystem.getCustomers().add(customerC010);
        rentalSystem.getVideoTapes().add(tapeT004);
        
        // Test input
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        boolean result = rentalSystem.addVideoTapeRental("C004", "T004", currentDate, currentDate.plusDays(7));
        
        // Verify
        assertFalse("Rental should fail when tape is unavailable", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup
        Customer customerC005 = new Customer();
        customerC005.setAccountNumber("C005");
        
        Customer customerC011 = new Customer();
        customerC011.setAccountNumber("C011");
        
        // Create 20 active rentals for customer C005
        for (int i = 1; i <= 20; i++) {
            Rental rental = new Rental();
            VideoTape tape = new VideoTape();
            tape.setBarCodeId("TEMP" + i);
            tape.setTitle("Temporary Tape " + i);
            rental.setVideoTape(tape);
            
            LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
            LocalDate dueDate = rentalDate.plusDays(7);
            
            rental.setRentalDate(rentalDate);
            rental.setDueDate(dueDate);
            rental.setBaseRentalFee(7.0);
            rental.setOverdueFee(0.0);
            
            customerC005.addRental(rental);
        }
        
        // Add one overdue rental for customer C005
        Rental overdueRental = new Rental();
        VideoTape overdueTape = new VideoTape();
        overdueTape.setBarCodeId("OVERDUE");
        overdueTape.setTitle("Overdue Tape");
        overdueRental.setVideoTape(overdueTape);
        
        LocalDate overdueRentalDate = LocalDate.parse("2024-12-31", formatter);
        LocalDate overdueDueDate = LocalDate.parse("2024-12-31", formatter);
        
        overdueRental.setRentalDate(overdueRentalDate);
        overdueRental.setDueDate(overdueDueDate);
        overdueRental.setBaseRentalFee(0.0);
        overdueRental.setReturnDate(null);
        overdueRental.calculateOverdueAmount(LocalDate.parse("2025-01-01", formatter));
        
        customerC005.addRental(overdueRental);
        
        // Create Tape T005 with active rental by customer C011
        VideoTape tapeT005 = new VideoTape();
        tapeT005.setBarCodeId("T005");
        tapeT005.setTitle("Test Tape 5");
        tapeT005.setAvailable(false);
        
        Rental rentalT005 = new Rental();
        rentalT005.setVideoTape(tapeT005);
        LocalDate rentalDateT005 = LocalDate.parse("2025-01-01", formatter);
        LocalDate dueDateT005 = LocalDate.parse("2025-01-05", formatter);
        rentalT005.setRentalDate(rentalDateT005);
        rentalT005.setDueDate(dueDateT005);
        rentalT005.setBaseRentalFee(4.0);
        rentalT005.setOverdueFee(0.0);
        
        customerC011.addRental(rentalT005);
        
        rentalSystem.getCustomers().add(customerC005);
        rentalSystem.getCustomers().add(customerC011);
        rentalSystem.getVideoTapes().add(tapeT005);
        
        // Test input
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        boolean result = rentalSystem.addVideoTapeRental("C005", "T005", currentDate, currentDate.plusDays(7));
        
        // Verify
        assertFalse("Rental should fail when all conditions (max rentals, overdue fees, tape unavailable) are violated", result);
    }
}