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
    public void testCase1_successfulRental() {
        // Setup: Create Customer C001 with 5 active rentals
        Customer customer = new Customer();
        customer.setAccountNumber("C001");
        
        // Add 5 active rentals (all unreturned, due dates 7 days after rental)
        for (int i = 1; i <= 5; i++) {
            VideoTape tape = new VideoTape();
            tape.setBarcodeId("VT" + i);
            tape.setTitle("Movie " + i);
            tape.setAvailable(false); // Already rented
            
            Rental rental = new Rental();
            rental.setVideoTape(tape);
            rental.setRentalDate(LocalDate.parse("2025-01-0" + i, formatter));
            rental.setDueDate(LocalDate.parse("2025-01-0" + i, formatter).plusDays(7));
            rental.setReturnDate(null);
            
            customer.addRental(rental);
        }
        
        // Create available Tape T001
        VideoTape tapeT001 = new VideoTape();
        tapeT001.setBarcodeId("T001");
        tapeT001.setTitle("Test Movie 1");
        tapeT001.setAvailable(true);
        
        // Execute: Attempt to rent tape T001
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        boolean result = rentalSystem.addVideoTapeRental(customer, tapeT001, currentDate, currentDate.plusDays(7));
        
        // Verify: Rental should be successful
        assertTrue("Customer with <20 rentals and no overdue should be able to rent", result);
        assertEquals("Tape should be marked as unavailable after rental", false, tapeT001.isAvailable());
        assertEquals("Customer should have 6 rentals after successful rental", 6, customer.getRentals().size());
    }
    
    @Test
    public void testCase2_customerHas20Rentals() {
        // Setup: Create Customer C002 with 20 active rentals
        Customer customer = new Customer();
        customer.setAccountNumber("C002");
        
        // Add 20 active rentals (all unreturned, due dates 7 days after rental)
        for (int i = 1; i <= 20; i++) {
            VideoTape tape = new VideoTape();
            tape.setBarcodeId("VT" + i);
            tape.setTitle("Movie " + i);
            tape.setAvailable(false); // Already rented
            
            Rental rental = new Rental();
            rental.setVideoTape(tape);
            rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
            rental.setDueDate(LocalDate.parse("2025-01-01", formatter).plusDays(7));
            rental.setReturnDate(null);
            
            customer.addRental(rental);
        }
        
        // Create available Tape T002
        VideoTape tapeT002 = new VideoTape();
        tapeT002.setBarcodeId("T002");
        tapeT002.setTitle("Test Movie 2");
        tapeT002.setAvailable(true);
        
        // Execute: Attempt to rent tape T002
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        boolean result = rentalSystem.addVideoTapeRental(customer, tapeT002, currentDate, currentDate.plusDays(7));
        
        // Verify: Rental should fail due to max rental limit
        assertFalse("Customer with 20 rentals should not be able to rent more", result);
        assertEquals("Tape should remain available after failed rental", true, tapeT002.isAvailable());
        assertEquals("Customer should still have 20 rentals", 20, customer.getRentals().size());
    }
    
    @Test
    public void testCase3_customerHasOverdueFees() {
        // Setup: Create Customer C003 with 1 active rental that is overdue
        Customer customer = new Customer();
        customer.setAccountNumber("C003");
        
        // Create overdue rental (due date was 2025-01-02, current date is 2025-01-05 - 3 days overdue)
        VideoTape overdueTape = new VideoTape();
        overdueTape.setBarcodeId("VT1");
        overdueTape.setTitle("Overdue Movie");
        overdueTape.setAvailable(false);
        
        Rental overdueRental = new Rental();
        overdueRental.setVideoTape(overdueTape);
        overdueRental.setRentalDate(LocalDate.parse("2024-12-26", formatter));
        overdueRental.setDueDate(LocalDate.parse("2025-01-02", formatter));
        overdueRental.setReturnDate(null);
        
        // Calculate overdue amount (3 days * 0.5 = 1.50)
        LocalDate currentDate = LocalDate.parse("2025-01-05", formatter);
        overdueRental.calculatePastDueAmount(currentDate);
        
        customer.addRental(overdueRental);
        
        // Create available Tape T003
        VideoTape tapeT003 = new VideoTape();
        tapeT003.setBarcodeId("T003");
        tapeT003.setTitle("Test Movie 3");
        tapeT003.setAvailable(true);
        
        // Execute: Attempt to rent tape T003
        boolean result = rentalSystem.addVideoTapeRental(customer, tapeT003, currentDate, currentDate.plusDays(7));
        
        // Verify: Rental should fail due to overdue fees
        assertFalse("Customer with overdue fees should not be able to rent", result);
        assertEquals("Tape should remain available after failed rental", true, tapeT003.isAvailable());
        assertEquals("Customer should still have 1 rental", 1, customer.getRentals().size());
        assertTrue("Customer should have overdue amounts", customer.hasPastDueAmounts());
    }
    
    @Test
    public void testCase4_tapeIsUnavailable() {
        // Setup: Create Customer C004 with 0 rentals
        Customer customer = new Customer();
        customer.setAccountNumber("C004");
        
        // Create Tape T004 that is already rented by another customer
        VideoTape tapeT004 = new VideoTape();
        tapeT004.setBarcodeId("T004");
        tapeT004.setTitle("Test Movie 4");
        tapeT004.setAvailable(false); // Already rented by C010
        
        // Execute: Attempt to rent unavailable tape T004
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        boolean result = rentalSystem.addVideoTapeRental(customer, tapeT004, currentDate, currentDate.plusDays(7));
        
        // Verify: Rental should fail due to tape unavailability
        assertFalse("Should not be able to rent unavailable tape", result);
        assertEquals("Tape should remain unavailable", false, tapeT004.isAvailable());
        assertEquals("Customer should still have 0 rentals", 0, customer.getRentals().size());
    }
    
    @Test
    public void testCase5_allConditionsFail() {
        // Setup: Create Customer C005 with 20 active rentals and one overdue rental
        Customer customer = new Customer();
        customer.setAccountNumber("C005");
        
        // Add 19 active rentals (all unreturned, due dates 7 days after rental)
        for (int i = 1; i <= 19; i++) {
            VideoTape tape = new VideoTape();
            tape.setBarcodeId("VT" + i);
            tape.setTitle("Movie " + i);
            tape.setAvailable(false);
            
            Rental rental = new Rental();
            rental.setVideoTape(tape);
            rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
            rental.setDueDate(LocalDate.parse("2025-01-01", formatter).plusDays(7));
            rental.setReturnDate(null);
            
            customer.addRental(rental);
        }
        
        // Add 1 overdue rental (due date was 2024-12-31, current date is 2025-01-01 - 1 day overdue)
        VideoTape overdueTape = new VideoTape();
        overdueTape.setBarcodeId("VT20");
        overdueTape.setTitle("Overdue Movie");
        overdueTape.setAvailable(false);
        
        Rental overdueRental = new Rental();
        overdueRental.setVideoTape(overdueTape);
        overdueRental.setRentalDate(LocalDate.parse("2024-12-24", formatter));
        overdueRental.setDueDate(LocalDate.parse("2024-12-31", formatter));
        overdueRental.setReturnDate(null);
        overdueRental.setOverdueAmount(5.00); // Set overdue amount as specified
        
        customer.addRental(overdueRental);
        
        // Create Tape T005 that is already rented by another customer
        VideoTape tapeT005 = new VideoTape();
        tapeT005.setBarcodeId("T005");
        tapeT005.setTitle("Test Movie 5");
        tapeT005.setAvailable(false); // Already rented by C011
        
        // Execute: Attempt to rent tape T005
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        boolean result = rentalSystem.addVideoTapeRental(customer, tapeT005, currentDate, currentDate.plusDays(7));
        
        // Verify: Rental should fail due to all conditions failing
        assertFalse("Rental should fail when all conditions fail", result);
        assertEquals("Tape should remain unavailable", false, tapeT005.isAvailable());
        assertEquals("Customer should still have 20 rentals", 20, customer.getRentals().size());
        assertTrue("Customer should have overdue amounts", customer.hasPastDueAmounts());
        assertFalse("Customer should not have fewer than 20 rentals", customer.hasFewerThan20Rentals());
    }
}