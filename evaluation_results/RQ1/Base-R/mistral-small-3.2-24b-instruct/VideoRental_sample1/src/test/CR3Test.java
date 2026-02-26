import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private RentalService rentalService;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        rentalService = new RentalService();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SuccessfulRental() {
        // Setup: Create Customer C001 with 5 active rentals
        Customer customer = new Customer();
        customer.setAccountNumber("C001");
        
        // Create 5 active rentals for the customer
        for (int i = 1; i <= 5; i++) {
            Rental rental = new Rental();
            VideoTape tape = new VideoTape();
            tape.setBarcodeId("T00" + (i + 10)); // Different tape IDs to avoid conflicts
            tape.setTitle("Movie " + i);
            
            rental.setVideoTape(tape);
            rental.setRentalDate(LocalDate.parse("2025-01-0" + i, formatter));
            rental.setDueDate(LocalDate.parse("2025-01-0" + i, formatter).plusDays(7));
            rental.setReturnDate(null);
            rental.setOverdueFee(0.0);
            
            customer.addRental(rental);
        }
        
        // Create available Tape T001 with no active rentals
        VideoTape tapeT001 = new VideoTape();
        tapeT001.setBarcodeId("T001");
        tapeT001.setTitle("Test Movie");
        tapeT001.setAvailable(true);
        
        // Execute: Attempt to rent tape T001
        boolean result = rentalService.addVideoTapeRental(customer, tapeT001);
        
        // Verify: Rental should be successful
        assertTrue("Rental should be successful when customer has <20 rentals, no overdue fees, and tape is available", result);
    }
    
    @Test
    public void testCase2_CustomerHasMaxRentals() {
        // Setup: Create Customer C002 with 20 active rentals
        Customer customer = new Customer();
        customer.setAccountNumber("C002");
        
        // Create 20 active rentals for the customer
        for (int i = 1; i <= 20; i++) {
            Rental rental = new Rental();
            VideoTape tape = new VideoTape();
            tape.setBarcodeId("T0" + String.format("%02d", i + 20)); // Different tape IDs
            tape.setTitle("Movie " + i);
            
            rental.setVideoTape(tape);
            rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
            rental.setDueDate(LocalDate.parse("2025-01-01", formatter).plusDays(7));
            rental.setReturnDate(null);
            rental.setOverdueFee(0.0);
            
            customer.addRental(rental);
        }
        
        // Create available Tape T002
        VideoTape tapeT002 = new VideoTape();
        tapeT002.setBarcodeId("T002");
        tapeT002.setTitle("Test Movie");
        tapeT002.setAvailable(true);
        
        // Execute: Attempt to rent tape T002
        boolean result = rentalService.addVideoTapeRental(customer, tapeT002);
        
        // Verify: Rental should fail due to max rental limit
        assertFalse("Rental should fail when customer has 20 active rentals", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup: Create Customer C003 with 1 active rental that is overdue
        Customer customer = new Customer();
        customer.setAccountNumber("C003");
        
        // Create overdue rental (3 days overdue as of 2025-01-05)
        Rental overdueRental = new Rental();
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T030");
        tape.setTitle("Overdue Movie");
        
        overdueRental.setVideoTape(tape);
        overdueRental.setRentalDate(LocalDate.parse("2024-12-20", formatter));
        overdueRental.setDueDate(LocalDate.parse("2025-01-02", formatter)); // 3 days overdue on 2025-01-05
        overdueRental.setReturnDate(null);
        overdueRental.setOverdueFee(1.50); // 3 days * 0.5 = 1.50
        
        customer.addRental(overdueRental);
        
        // Create available Tape T003
        VideoTape tapeT003 = new VideoTape();
        tapeT003.setBarcodeId("T003");
        tapeT003.setTitle("Test Movie");
        tapeT003.setAvailable(true);
        
        // Execute: Attempt to rent tape T003
        boolean result = rentalService.addVideoTapeRental(customer, tapeT003);
        
        // Verify: Rental should fail due to overdue fees
        assertFalse("Rental should fail when customer has overdue fees", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup: Create Customer C004 with 0 rentals
        Customer customer = new Customer();
        customer.setAccountNumber("C004");
        
        // Create Tape T004 with active rental by another customer
        VideoTape tapeT004 = new VideoTape();
        tapeT004.setBarcodeId("T004");
        tapeT004.setTitle("Test Movie");
        tapeT004.setAvailable(false); // Mark as unavailable since it's rented
        
        // Note: The RentalService checks availability via isAvailable(LocalDate) method
        // Since we can't easily mock the getRentalsForTape() method, we'll rely on 
        // the isAvailable field which is set to false to simulate unavailability
        
        // Execute: Attempt to rent tape T004
        boolean result = rentalService.addVideoTapeRental(customer, tapeT004);
        
        // Verify: Rental should fail due to tape being unavailable
        assertFalse("Rental should fail when tape is unavailable", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup: Create Customer C005 with 20 active rentals and one overdue rental
        Customer customer = new Customer();
        customer.setAccountNumber("C005");
        
        // Create 19 active rentals
        for (int i = 1; i <= 19; i++) {
            Rental rental = new Rental();
            VideoTape tape = new VideoTape();
            tape.setBarcodeId("T0" + String.format("%02d", i + 40)); // Different tape IDs
            tape.setTitle("Movie " + i);
            
            rental.setVideoTape(tape);
            rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
            rental.setDueDate(LocalDate.parse("2025-01-01", formatter).plusDays(7));
            rental.setReturnDate(null);
            rental.setOverdueFee(0.0);
            
            customer.addRental(rental);
        }
        
        // Add one overdue rental
        Rental overdueRental = new Rental();
        VideoTape overdueTape = new VideoTape();
        overdueTape.setBarcodeId("T060");
        overdueTape.setTitle("Overdue Movie");
        
        overdueRental.setVideoTape(overdueTape);
        overdueRental.setRentalDate(LocalDate.parse("2024-12-20", formatter));
        overdueRental.setDueDate(LocalDate.parse("2024-12-31", formatter)); // Overdue on 2025-01-01
        overdueRental.setReturnDate(null);
        overdueRental.setOverdueFee(5.00); // Specified in test case
        
        customer.addRental(overdueRental);
        
        // Create Tape T005 with active rental by another customer
        VideoTape tapeT005 = new VideoTape();
        tapeT005.setBarcodeId("T005");
        tapeT005.setTitle("Test Movie");
        tapeT005.setAvailable(false); // Mark as unavailable since it's rented
        
        // Execute: Attempt to rent tape T005
        boolean result = rentalService.addVideoTapeRental(customer, tapeT005);
        
        // Verify: Rental should fail due to all conditions being violated
        assertFalse("Rental should fail when all conditions (max rentals, overdue fees, tape availability) are violated", result);
    }
}